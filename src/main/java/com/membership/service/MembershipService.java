package com.membership.service;

import com.membership.domain.Location;
import com.membership.domain.Membership;
import com.membership.domain.Plan;
import com.membership.domain.Transaction;

import java.util.List;

public interface MembershipService 
{
	public List<Membership> findAll();
	public Membership save(Membership membership);
	public Membership findById(Long id);
	public List<Transaction> getTransactions(Membership membership);
	public List<Transaction> getTransactionsByMembershipId(Long membershipId);
	public Plan getPlan(Membership membership);
	public Plan getPlanByMembershipId(Long membershipId);
	public Location getLocation(Membership membership);
	public Location getLocationByMembershipId(Long membershipId);
	public Membership addLocation(Long id, Location location);
	public Membership addPlanForMembership( Long id,Plan plan);
}