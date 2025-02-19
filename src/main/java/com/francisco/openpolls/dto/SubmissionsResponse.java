package com.francisco.openpolls.dto;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionsResponse {

	public Map<SubmissionResponse, List<SubmissionAnswerResponse>> data;
	
}
