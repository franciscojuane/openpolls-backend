package com.francisco.openpolls.model.questionTypes;

import com.francisco.openpolls.model.Question;
import com.francisco.openpolls.model.common.Constants;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = Constants.TABLE_PREFIX + "NUMERIC_QUESTION")
public class NumericQuestion extends Question {

	private int minValue;
	
	private int maxValue;
	
	@Default
	private int scale = 1;
}
