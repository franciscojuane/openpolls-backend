package com.francisco.openpolls.model.questionTypes;

import java.util.List;

import com.francisco.openpolls.model.Question;
import com.francisco.openpolls.model.common.Constants;
import com.francisco.openpolls.model.questionTypes.options.MultipleChoiceQuestionOption;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
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
@Table(name = Constants.TABLE_PREFIX + "MULTIPLE_CHOICE_QUESTION")
public class MultipleChoiceQuestion extends Question {

	@Default
	private int minAmountOfSelections = 1;
	
	@Default
	private int maxAmountOfSelections = 1;

	@OneToMany(fetch = FetchType.EAGER, mappedBy="multipleChoiceQuestion")
	private List<MultipleChoiceQuestionOption> options;

}
