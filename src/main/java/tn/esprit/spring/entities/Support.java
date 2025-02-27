package tn.esprit.spring.entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Support {
	SKI, SNOWBOARD, SURF, KITESURF,;

	@JsonCreator
	public static Support fromString(String value) {
		try {
			return Support.valueOf(value.toUpperCase()); // Convertir en majuscules
		} catch (IllegalArgumentException e) {
			return SKI; // Valeur par défaut si la valeur est inconnue
		}
	}

	@JsonValue
	public String toLowerCase() {
		return this.name().toLowerCase();
	}
}