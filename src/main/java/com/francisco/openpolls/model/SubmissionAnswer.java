package com.francisco.openpolls.model;

import com.francisco.openpolls.model.common.AbstractModel;
import com.francisco.openpolls.model.common.Constants;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = Constants.TABLE_PREFIX + "SUBMISSION_ANSWER")
public class SubmissionAnswer extends AbstractModel{

	@ManyToOne
	@JoinColumn(name="QUESTION_ID")
	private Question question;
	
	@ManyToOne
	@JoinColumn(name="SUBMISSION_ID")
	private Submission submission;
	
	private String answer;
	
}
