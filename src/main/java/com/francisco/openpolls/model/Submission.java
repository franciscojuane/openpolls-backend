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
@Table(name = Constants.TABLE_PREFIX + "SUBMISSION")
public class Submission extends AbstractModel{

	private String ipAddress;
	
	private String emailAddress;
	
	@ManyToOne
	@JoinColumn(name = "POLL_ID")
	private Poll poll;
}
