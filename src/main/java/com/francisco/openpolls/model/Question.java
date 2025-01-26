package com.francisco.openpolls.model;

import com.francisco.openpolls.model.common.Constants;
import com.francisco.openpolls.model.common.EffectiveModel;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = Constants.TABLE_PREFIX + "QUESTION")
@Inheritance(strategy = InheritanceType.JOINED)
public class Question extends EffectiveModel {
	
	private int rank;
	
	private String text;
	
	private String subText;

}
