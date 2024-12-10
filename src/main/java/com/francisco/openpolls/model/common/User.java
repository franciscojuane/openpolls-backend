package com.francisco.openpolls.model.common;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = Constants.TABLE_PREFIX + "USERS")
@Entity
public class User extends EffectiveModel {

	@Column(length=50)
	private String firstName;
	
	@Column(length=50)
	private String lastName;
	
	@Column
	private String password;
}
