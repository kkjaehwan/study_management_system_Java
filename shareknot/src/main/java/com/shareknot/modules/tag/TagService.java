package com.shareknot.modules.tag;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TagService {

	private final TagRepository tagRepository;

	public Tag findOrCreateNew(String title) {
		Tag tag = tagRepository.findByTitle(title);
		if (tag == null) {
			tag = tagRepository.save(Tag.builder().title(title).build());
		}
		return tag;
	}

}
