package com.francisco.openpolls.model;

import com.francisco.openpolls.model.common.Constants;
import com.francisco.openpolls.model.common.EffectiveModel;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class Poll extends EffectiveModel{

	@NotNull
	private String name;
	
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User createdByUser;
	
	
}
