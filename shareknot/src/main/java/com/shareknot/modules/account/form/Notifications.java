package com.shareknot.modules.account.form;

import com.shareknot.modules.account.Account;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Notifications {
	private boolean partyCreatedByEmail;

	private boolean partyCreatedByWeb;

	private boolean partyEnrolmentResultByEmail;

	private boolean partyEnrolmentResultByWeb;

	private boolean partyUpdatedByEmail;

	private boolean partyUpdatedByWeb;
	
//	public Notifications(Account account) {
//		this.partyCreatedByEmail = account.isPartyCreatedByEmail();
//		this.partyCreatedByWeb = account.isPartyCreatedByWeb();
//		this.partyEnrolmentResultByEmail = account.isPartyEnrolmentResultByEmail();
//		this.partyEnrolmentResultByWeb = account.isPartyEnrolmentResultByWeb();
//		this.partyUpdatedByEmail = account.isPartyUpdatedByEmail();
//		this.partyUpdatedByWeb = account.isPartyUpdatedByWeb();
//	}

}
