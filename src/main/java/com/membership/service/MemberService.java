package com.membership.service;

import com.membership.domain.*;

import java.util.List;
import java.util.Set;

public interface MemberService {
	public List<Member> findAll();
	public Member save(Member member);
	public Member findById(Long id);
	public Member addMemberBadge(Long memberId, Badge badge);
	public Set<Badge> findAllMemberBadges(Long memberId);
	public Set<Role> findAllMemberRoles(Long memberId);
	public Member addMemberRole(Long memberId, Long roleId);
	public Member updateMember(Long memberId, Member updatedMember);
	public Member addMembership(Long memberId, Membership membership);
	public Set<Membership> findAllMemberMemberships(Long memberId);
	public List<Plan> findAllMemberPlans(Long memberId);
	public Set<Transaction> findAllMemberTransactions(Long id);
}
