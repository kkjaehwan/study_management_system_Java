package com.shareknot.modules.account;

import java.util.Collection;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
public class AccountRole {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accountrole_id_generator")
	@SequenceGenerator(name = "accountrole_id_generator", sequenceName = "accountrole_id_seq", allocationSize = 1)
	private Long id;

	@Column(unique = true)
	private String name;
	
	@ManyToMany(mappedBy = "roles")
	private Set<Account> accounts;

	@ManyToMany
	@JoinTable(name = "roles_privileges", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
	private Set<AccountPrivilege> privileges;
}
