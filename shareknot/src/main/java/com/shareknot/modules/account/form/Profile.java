package com.shareknot.modules.account.form;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.shareknot.modules.account.Account;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Profile {

	@Length(max = 35)
	private String bio;

	@Length(max = 50)
	private String url;

	@Length(max = 50)
	private String occupation;

	@Length(max = 50)
	private String location;

	private String profileImage;

//	public Profile(Account account) {
//		this.bio = account.getBio();
//		this.url = account.getUrl();
//		this.occupation = account.getOccupation();
//		this.location = account.getLocation();
//		this.profileImage = account.getProfileImage();
//	}
}
