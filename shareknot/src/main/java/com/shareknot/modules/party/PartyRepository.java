package com.shareknot.modules.party;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.shareknot.modules.account.Account;

@Transactional(readOnly = true)
public interface PartyRepository extends JpaRepository<Party, Long>, PartyRepositoryExtension {

	boolean existsByPath(String path);

	@EntityGraph(value = "Party.withAll", type = EntityGraphType.LOAD)
	Party findByPath(String path);

	@EntityGraph(value = "Party.withTagsAndManagers", type = EntityGraphType.FETCH)
	// @EntityGraph(attributePaths = {"tags", "managers"})
	Party findPartyWithTagsByPath(String path);

	@EntityGraph(value = "Party.withZonesAndManagers", type = EntityGraphType.FETCH)
	// @EntityGraph(attributePaths = {"zones", "managers"})
	Party findPartyWithZonesByPath(String path);

	@EntityGraph(value = "Party.withManagers", type = EntityGraphType.FETCH)
	// @EntityGraph(attributePaths = {"managers"})
	Party findPartyWithManagerByPath(String path);

	@EntityGraph(value = "Party.withMembers", type = EntityGraphType.FETCH)
	// @EntityGraph(attributePaths = {"managers"})
	Party getPartyWithMemberByPath(String path);

	Party findPartyOnlyByPath(String path);

	@EntityGraph(value = "Party.withTagsAndZones", type = EntityGraphType.FETCH)
	// @EntityGraph(attributePaths = { "zones", "tags" })
	Party findPartyWithTagsAndZonesById(Long id);

	@EntityGraph(attributePaths = { "members", "managers" })
	Party findStudyWithManagersAndMemebersById(Long id);
	
    @EntityGraph(attributePaths = {"zones", "tags"})
	List<Party> findFirst9ByPublishedAndClosedOrderByPublishedDateTimeDesc(boolean published, boolean closed);

    List<Party> findAllByManagersContainingOrderByPublishedDateTimeDesc(Account account);

    List<Party> findFirst5ByMembersContainingAndClosedOrderByPublishedDateTimeDesc(Account account, boolean closed);


}
