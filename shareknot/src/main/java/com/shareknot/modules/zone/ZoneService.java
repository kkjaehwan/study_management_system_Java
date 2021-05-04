package com.shareknot.modules.zone;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ZoneService {

	private final ZoneRepository zoneRepository;

	@PostConstruct
	// 빈 초기화 후 실행
	public void initZoneData() throws IOException {
		if (zoneRepository.count() == 0) {
			List<Zone> zonesKr = extractZone("zones_kr.csv");
			zoneRepository.saveAll(zonesKr);

			List<Zone> zonesCa = extractZone("zones_ca.csv");
			zoneRepository.saveAll(zonesCa);
		}

	}

	private List<Zone> extractZone(String fileName) throws IOException {
		Resource resource = new ClassPathResource(fileName);

		List<Zone> zoneList = Files.readAllLines(resource.getFile().toPath(), StandardCharsets.UTF_8)
				.stream()
				.map(line -> {
					String[] split = line.split(",");
					return Zone.builder().city(split[0]).localNameOfCity(split[1]).province(split[2]).country(split[3]).build();
				})
				.collect(Collectors.toList());
		return zoneList;
	}
}
