package com.francisco.openpolls.dto;

import com.francisco.openpolls.model.common.AbstractModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionResponse extends AbstractModel {
	
	private String ipAddress;

	private String emailAddress;

	private Long pollId;
}
