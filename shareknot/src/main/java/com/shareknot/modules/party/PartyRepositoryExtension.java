package com.shareknot.modules.party;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import com.shareknot.modules.tag.Tag;
import com.shareknot.modules.zone.Zone;

@Transactional(readOnly = true)
public interface PartyRepositoryExtension {

	Page<Party> findByKeyword(String keyword, Pageable pageable);

	List<Party> findByAccount(Set<Tag> tags, Set<Zone> zones);


}
