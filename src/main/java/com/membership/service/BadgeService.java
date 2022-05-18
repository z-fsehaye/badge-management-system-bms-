package com.membership.service;

import com.membership.domain.Badge;
import com.membership.domain.Member;

import java.util.List;


public interface BadgeService {
	public List<Badge> findAll();
	public Badge findById(Long id);
	public Badge save(Badge badge);
	public Badge update(Long badgeId, Badge updatedBadge);
	public void deleteById(Long id);
	public Member findBadgeMember(Long badgeId);
	public boolean isAuthorized(Long badgeId, Long locationId);
}
