package com.francisco.openpolls.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum QuestionType {
	MULTIPLE_CHOICE,
	NUMERIC,
	SCALE,
	TEXT;
	
	public String getName() {
		return name();
	}
	
	@JsonCreator
	public static QuestionType forValues(@JsonProperty("name") String name) {
		try {
			return QuestionType.valueOf(name);
		} catch (NullPointerException e) {
			return null;
		}
	}
}
