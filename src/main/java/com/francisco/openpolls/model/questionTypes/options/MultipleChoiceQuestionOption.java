package com.francisco.openpolls.model.questionTypes.options;

import com.francisco.openpolls.model.common.Constants;
import com.francisco.openpolls.model.common.EffectiveModel;
import com.francisco.openpolls.model.questionTypes.MultipleChoiceQuestion;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = Constants.TABLE_PREFIX + "MULTIPLE_CHOICE_QUESTION_OPTION")
public class MultipleChoiceQuestionOption extends EffectiveModel{
	
	private String text;
	
	@ManyToOne
	@JoinColumn(name = "MULTIPLE_CHOICE_QUESTION_ID")
	private MultipleChoiceQuestion multipleChoiceQuestion;

}
