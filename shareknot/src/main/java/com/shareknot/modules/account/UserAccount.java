package com.shareknot.modules.account;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

@Getter
public class UserAccount extends User {

	private Account account;

	public UserAccount(Account account) {
		super(account.getNickname(), account.getPassword(), getRolesToAuthrity(account.getRoles()));
//		super(account.getNickname(), account.getPassword(),
//				List.of(new SimpleGrantedAuthority("ROLE_USER")));
		this.account = account;
	}

	private static List<GrantedAuthority> getRolesToAuthrity(Collection<AccountRole> roles) {
		List<GrantedAuthority> authorities = new ArrayList<>();

		for (Iterator iterator = roles.iterator(); iterator.hasNext();) {
			AccountRole accountRole = (AccountRole) iterator.next();
			authorities.add(new SimpleGrantedAuthority(accountRole.getName()));
		}

		return authorities;
	}

	private Collection<? extends GrantedAuthority> getAuthorities(Collection<AccountRole> roles) {

		return getGrantedAuthorities(getPrivileges(roles));
	}

	private List<String> getPrivileges(Collection<AccountRole> roles) {

		List<String> privileges = new ArrayList<>();
		List<AccountPrivilege> collection = new ArrayList<>();
		for (AccountRole role : roles) {
			collection.addAll(role.getPrivileges());
		}
		for (AccountPrivilege item : collection) {
			privileges.add(item.getName());
		}
		return privileges;
	}

	private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (String privilege : privileges) {
			authorities.add(new SimpleGrantedAuthority(privilege));
		}
		return authorities;
	}

}
