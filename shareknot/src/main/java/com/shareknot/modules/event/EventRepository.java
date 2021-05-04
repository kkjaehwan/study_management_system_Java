package com.shareknot.modules.event;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.shareknot.modules.party.Party;

@Transactional(readOnly = true)
public interface EventRepository extends JpaRepository<Event, Long> {

	@EntityGraph(value = "Event.withEnrolments", type = EntityGraphType.LOAD)
	List<Event> findByPartyOrderByStartDateTime(Party study);

}
