package com.francisco.openpolls.model;

import java.util.List;

import com.francisco.openpolls.model.common.Constants;
import com.francisco.openpolls.model.common.EffectiveModel;
import com.francisco.openpolls.model.enums.QuestionType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = Constants.TABLE_PREFIX + "QUESTION")
@Inheritance(strategy = InheritanceType.JOINED)
public class Question extends EffectiveModel {
	
	private int rank;
	
	private String text;
	
	private String subText;
	
	private QuestionType questionType;
	
	@ManyToOne
	@JoinColumn(name = "POLL_ID")
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
	@OneToMany(fetch = FetchType.EAGER, mappedBy="question", cascade = CascadeType.ALL, orphanRemoval = true)
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
