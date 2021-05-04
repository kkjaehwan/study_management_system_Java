package com.shareknot.modules.event;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;

import com.shareknot.modules.account.Account;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@NamedEntityGraph(
        name = "Enrolment.withEventAndStudy",
        attributeNodes = {
                @NamedAttributeNode(value = "event", subgraph = "party")
        },
        subgraphs = @NamedSubgraph(name = "party", attributeNodes = @NamedAttributeNode("party"))
)
@Setter
@Getter
@EqualsAndHashCode(of = "id")
@Entity
public class Enrolment {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	private Event event;

	@ManyToOne
	private Account account;

	private LocalDateTime enroledAt;

	private boolean accepted;

	private boolean attended;
}
