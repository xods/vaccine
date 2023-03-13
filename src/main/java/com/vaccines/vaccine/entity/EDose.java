package com.vaccines.vaccine.entity;

import org.springframework.data.relational.core.sql.In;

public enum EDose {

	I,
	II,
	III,
	IV;

	public static EDose retDose(Integer i) {
		return switch (i) {
			case 2 -> EDose.II;
			case 3 -> EDose.III;
			case 4 -> EDose.IV;
			default -> EDose.I;
		};
	}

	public static Integer retDelay(EDose i) {
		return switch (i) {
			case I, III -> 3;
			case II -> 6;
			case IV -> 0;
		};
	}

}
