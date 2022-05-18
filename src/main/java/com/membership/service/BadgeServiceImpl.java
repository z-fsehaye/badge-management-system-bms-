package com.membership.service;

import com.membership.domain.*;
import com.membership.repository.BadgeRepository;
import com.membership.repository.TimeSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class BadgeServiceImpl implements BadgeService {

	@Autowired
	private BadgeRepository badgeRepository;
	@Autowired
	private TimeSlotRepository timeSlotRepository;
	@Autowired
	private MemberService memberService;
	@Autowired
	private TransactionService transactionService;

	@Override
	public List<Badge> findAll() {
		return badgeRepository.findAll();
	}

	@Override
	public Badge findById(Long id) {
		return badgeRepository.findById(id).get();
	}

	@Override
	public Badge save(Badge badge) {
		return badgeRepository.save(badge);
	}

	@Override
	public Badge update(Long badgeId, Badge updatedBadge) {
		Badge oldBadge = findById(badgeId);
		if (updatedBadge.getIssueDate() != null)
			oldBadge.setIssueDate(updatedBadge.getIssueDate());
		if (updatedBadge.getExpirationDate() != null)
			oldBadge.setExpirationDate(updatedBadge.getExpirationDate());
		oldBadge.setActive(updatedBadge.isActive());

		save(oldBadge);

		return oldBadge;
	}

	@Override
	public void deleteById(Long id) {
		badgeRepository.deleteById(id);
	}

	@Override
	public Member findBadgeMember(Long badgeId) {
		return findById(badgeId).getMember();
	}

	@Override
	public boolean isAuthorized(Long badgeId, Long locationId) {
		Badge badge = findById(badgeId);
		Member member = badge.getMember();
		Membership membership = member.getMemberships().stream()
				.filter(membersh -> membersh.getLocation().getId() == locationId)
				.findFirst()
				.orElse(null);

		if (badge == null || member == null || membership == null)
			return false; // Not authorized
		if (!badge.isActive() || badge.getExpirationDate().isBefore(LocalDate.now()))
			return false; // Not authorized
		if (membership.getEndDate().isBefore(LocalDate.now()))
			return false; // Membersip is expired

		Plan plan = membership.getPlan();
		Location location = membership.getLocation();
		Integer dayOfTheWeek = LocalDate.now().getDayOfWeek().getValue();
		List<TimeSlot> dayTimeSlot = location.getTimeSlots()
				.stream()
				.filter(s -> s.getDayOfWeek().valueOfTheDay() == dayOfTheWeek)
				.collect(Collectors.toList());

		LocalTime currentTime = LocalTime.now();
		Optional<TimeSlot> timeSlot = dayTimeSlot.stream()
				.filter(s -> s.getStartTime().isBefore(currentTime) && s.getEndTime().isAfter(currentTime)).findFirst();
		if (!timeSlot.isPresent())
			return false; // this means out of time or not opened yet;

		if (!allowedRoleFoundInMember(member, plan))
			return false;

		if (plan.isLimited()) {
			long successfulTransactionsCount = membership.getTransactions().stream()
					.filter(t -> t.getDateTime().getMonthValue() == LocalDate.now().getMonthValue())
					.filter(t -> t.getDateTime().getYear() == LocalDate.now().getYear()).filter(t -> t.isSuccessful())
					.count();

			if (plan.getQuota() <= successfulTransactionsCount)
				return false;// quota overflowed
		}

		Transaction transaction = new Transaction();
		transaction.setSuccessful(true);
		transaction.setDateTime(LocalDate.now());
//		transaction.setMember(member);
//		transaction.setMembership(membership);
		transaction.setActivityType(timeSlot.get().getActivityType());
		membership.addTransaction(transaction);
		member.addTransaction(transaction);
//		memberService.save(member);
		transactionService.save(transaction);

		return true;
	}

	public boolean allowedRoleFoundInMember(Member member, Plan plan) {
		Set<Role> allowedRoles = plan.getRoles();
		for (Role role : allowedRoles)
			if (member.getRoles().contains(role))
				return true;
		return false;
	}
}
