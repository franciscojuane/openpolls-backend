package com.francisco.openpolls.dto;

import com.francisco.openpolls.model.common.Constants;
import com.francisco.openpolls.model.common.EffectiveModel;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = Constants.TABLE_PREFIX + "POLL")
@Entity
public class PollResponse extends EffectiveModel{

	@NotNull
	private String name;
	
	private String description;
	
	private Long createdByUserId;

}
