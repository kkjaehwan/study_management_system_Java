package com.shareknot.modules.zone;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ZoneRepository extends JpaRepository<Zone, Long>{

	Zone findByCityAndProvinceAndCountry(String cityName, String provinceName, String countryName);

}
