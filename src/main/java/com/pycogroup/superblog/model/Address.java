package com.pycogroup.superblog.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Address {
	private String city;
	private String country;
	private String streetAddress;

	public Address(
		final String streetAddress,
		final String city,
		final String country) {
		this.streetAddress = streetAddress;
		this.city = city;
		this.country = country;
	}
}
