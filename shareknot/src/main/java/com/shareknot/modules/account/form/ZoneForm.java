package com.shareknot.modules.account.form;

import com.shareknot.modules.zone.Zone;

import lombok.Data;

@Data
public class ZoneForm {

	private String zoneName;

	public String getCityName() {
		return zoneName.substring(0, zoneName.indexOf("("));
	}

	public String getProvinceName() {
		return zoneName.substring(zoneName.indexOf("/") + 1,zoneName.indexOf("_"));
	}

	public String getCountryName() {
		return zoneName.substring(zoneName.indexOf("_") + 1);
	}

	public String getLocalNameOfCity() {
		return zoneName.substring(zoneName.indexOf("(") + 1, zoneName.indexOf(")"));
	}

	public Zone getZone() {
		return Zone.builder()
				.city(this.getCityName())
				.localNameOfCity(this.getLocalNameOfCity())
				.province(this.getProvinceName())
				.country(this.getCountryName())
				.build();
	}

}
