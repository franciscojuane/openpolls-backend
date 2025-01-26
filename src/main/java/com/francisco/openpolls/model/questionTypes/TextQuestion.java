package com.francisco.openpolls.model.questionTypes;

import com.francisco.openpolls.model.Question;
import com.francisco.openpolls.model.common.Constants;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = Constants.TABLE_PREFIX + "TEXT_QUESTION")
public class TextQuestion extends Question {
	
	private int minLength;
	
	private int maxLength;

}
