package com.membership.repository;


import com.membership.domain.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BadgeRepository extends JpaRepository<Badge, Long>{
//	@Query("select badge, member, membership, plan, location from Badge badge join badge.member member join "
//			+ " member.memberships membership join membership.plan plan "
//			+ " join membership.location location "
//			+ " where badge.id=:badgeId and location.locationId=:locationId")
//	public List<Object[]> findBadgeMemberMembershipPlanByBadgeIdAndBarcodeReaderLocationId(
//			@Param("badgeId") Long badgeId,
//			@Param("locationId") long locationId);
}
