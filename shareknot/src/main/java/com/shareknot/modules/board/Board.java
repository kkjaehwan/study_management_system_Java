package com.shareknot.modules.board;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_id_generator")
	@SequenceGenerator(name = "board_id_generator", sequenceName = "board_id_seq", allocationSize = 1)
	private Long id;

	@Column(unique = true)
	private String title;

	@OneToMany(mappedBy = "board", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	private Set<Post> posts = new HashSet<>();

	public String getEncodedTitle() {
		return URLEncoder.encode(this.title, StandardCharsets.UTF_8);
	}
}
