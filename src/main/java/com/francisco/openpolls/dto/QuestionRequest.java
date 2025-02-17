package com.francisco.openpolls.dto;

import java.util.List;

import com.francisco.openpolls.model.Poll;
import com.francisco.openpolls.model.common.AbstractModel;
import com.francisco.openpolls.model.enums.QuestionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequest extends AbstractModel {

	private int rank;

	private String text;

	private String subText;

	private QuestionType questionType;

	private Poll poll;

	/*
	 * For Multiple Choice Questions
	 */
	private Integer minAmountOfSelections;

	/*
	 * For Multiple Choice Questions
	 */
	private Integer maxAmountOfSelections;

	/*
	 * For Multiple Choice Questions
	 */
	private List<String> options;

	/*
	 * For Numeric and Scale Questions
	 */
	private Integer minValue;

	/*
	 * For Numeric and Scale Questions
	 */
	private Integer maxValue;

	/*
	 * For Scale Questions
	 */
	private Integer scale;

	/*
	 * For Text Questions
	 */
	private Integer minLength;

	/*
	 * For Text Questions
	 */
	private Integer maxLength;

}
