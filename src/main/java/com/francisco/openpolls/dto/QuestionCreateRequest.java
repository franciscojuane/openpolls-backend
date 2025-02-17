package com.francisco.openpolls.dto;

import java.util.List;

import com.francisco.openpolls.model.Poll;
import com.francisco.openpolls.model.QuestionOption;
import com.francisco.openpolls.model.enums.QuestionType;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

public class QuestionCreateRequest {

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
	@OneToMany(fetch = FetchType.EAGER, mappedBy="question")
	private List<QuestionOption> options;
	
	
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
