package com.shareknot.modules.account;

import java.util.Set;

import com.querydsl.core.types.Predicate;
import com.shareknot.modules.tag.Tag;
import com.shareknot.modules.zone.Zone;

public class AccountPredicates {

	public static Predicate findByTagsAndZones(Set<Tag> tags, Set<Zone> zones) {
		QAccount account = QAccount.account;
		return account.zones.any().in(zones).and(account.tags.any().in(tags));
	}
}
