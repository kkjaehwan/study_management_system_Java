package com.shareknot.modules.party;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shareknot.modules.account.Account;
import com.shareknot.modules.party.event.PartyCreatedEvent;
import com.shareknot.modules.party.event.PartyUpdateEvent;
import com.shareknot.modules.party.form.PartyDescriptionForm;
import com.shareknot.modules.tag.Tag;
import com.shareknot.modules.zone.Zone;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PartyService {

	private final PartyRepository partyRepository;
	private final ModelMapper modelMapper;
	private final ApplicationEventPublisher eventPublisher;

	public Party createNewParty(Party party, Account account) {
		Party newParty = partyRepository.save(party);
		newParty.addManager(account);
		return newParty;
	}

	public Party getPartyToUpdate(Account account, String path) {
		Party party = this.getParty(path);

		if (!party.isManagerBy(account)) {
			throw new AccessDeniedException("The feature is not available.");

		}
		return party;
	}

	public Party getPartyToUpdateTag(Account account, String path) {
		Party party = partyRepository.findPartyWithTagsByPath(path);

		if (!party.isManagerBy(account)) {
			throw new AccessDeniedException("The feature is not available.");

		}
		return party;
	}

	public Party getPartyToUpdateZone(Account account, String path) {
		Party party = partyRepository.findPartyWithZonesByPath(path);

		if (!party.isManagerBy(account)) {
			throw new AccessDeniedException("The feature is not available.");

		}
		return party;
	}

	public Party getPartyToUpdateStatus(Account account, String path) {
		Party party = partyRepository.findPartyWithManagerByPath(path);

		if (!party.isManagerBy(account)) {
			throw new AccessDeniedException("The feature is not available.");

		}
		return party;
	}

	public Party getParty(String path) {
		Party party = partyRepository.findByPath(path);
		if (party == null) {
			throw new IllegalArgumentException("There are no parties in the path : " + path);
		}
		return party;
	}

	private void checkIfExistingParty(String path, Party party) {
		if (party == null) {
			throw new IllegalArgumentException("There are no parties in the path " + path);
		}
	}

	public void updatePartyDescription(Party party, @Valid PartyDescriptionForm partyDescriptionForm) {
		modelMapper.map(partyDescriptionForm, party);

	}

	public void enableBanner(Party party) {
		party.setUseBanner(true);
	}

	public void disableBanner(Party party) {
		party.setUseBanner(false);
	}

	public void updatePartyImage(Party party, String image) {
		party.setImage(image);

	}

	public void addTag(Party party, Tag tag) {
		party.getTags().add(tag);

	}

	public void removeTag(Party party, Tag tag) {
		party.getTags().remove(tag);

	}

	public void addZone(Party party, Zone zone) {
		party.getZones().add(zone);

	}

	public void removeZone(Party party, Zone zone) {
		party.getZones().remove(zone);

	}

	public void allowSearch(Party party) {
		party.allowSearch();
		eventPublisher.publishEvent(new PartyCreatedEvent(party));
	}

	public void denySearch(Party party) {
		party.denySearch();

	}

	public void allowRecruit(Party party) {
		party.allowRecruit();
		eventPublisher.publishEvent(new PartyUpdateEvent(party,"start recruiting party member"));

	}

	public void denyRecruit(Party party) {
		party.denyRecruit();
		eventPublisher.publishEvent(new PartyUpdateEvent(party,"stop recruiting party member"));
	}

	public void updatePath(Party party, String path) {
		party.setPath(path);
	}

	public void updateTitle(Party party, String title) {
		party.setTitle(title);
	}

	public void remove(Party party) {
		partyRepository.delete(party);
	}

	public void addMember(Party party, Account account) {
		party.addMember(account);

	}

	public void removeMember(Party party, Account account) {
		party.removeMember(account);

	}

	public Party getPartyToEnrol(String path) {
		Party party = partyRepository.findPartyOnlyByPath(path);
		checkIfExistingParty(path, party);
		return party;
	}

}
