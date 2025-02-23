package com.francisco.openpolls.dto;

import com.francisco.openpolls.model.common.Constants;
import com.francisco.openpolls.model.common.EffectiveModel;
import com.francisco.openpolls.model.enums.SubmissionLimitCriteria;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PollCreateRequest extends EffectiveModel{

	@NotNull
	private String name;
	
	private String description;
	
	private SubmissionLimitCriteria submissionLimitCriteria;

}
