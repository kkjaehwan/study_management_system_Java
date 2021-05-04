package com.shareknot.modules.party.event;

import com.shareknot.modules.party.Party;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PartyCreatedEvent {

	private final Party party;

}
