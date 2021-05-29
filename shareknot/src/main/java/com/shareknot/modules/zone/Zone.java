package com.shareknot.modules.zone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

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
@Table(uniqueConstraints={
	    @UniqueConstraint(columnNames = {"city", "province","country"})
	}) 
public class Zone {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "zone_id_generator")
	@SequenceGenerator(name = "zone_id_generator", sequenceName = "zone_id_seq", allocationSize = 1)
	private Long id;

	@Column(nullable = false)
	private String city;

	@Column(nullable = false)
	private String localNameOfCity;

	@Column(nullable = true)
	private String province;

	@Column(nullable = false)
	private String country;

	@Override
	public String toString() {
		return String.format("%s(%s)/%s_%s", city, localNameOfCity, province,country);
	}

}
