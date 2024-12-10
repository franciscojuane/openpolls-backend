package com.francisco.openpolls.model.common;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbstractModel implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	
	public LocalDateTime creationTime;
	
	public LocalDateTime updateTime;
	
	@Version
	public long version = 1;
	
	@PreUpdate
	private void preUpdate() {
		updateTime = LocalDateTime.now();
	}

	@PrePersist
	private void prePersist() {
		if (creationTime == null) {
			creationTime = LocalDateTime.now();
			updateTime = LocalDateTime.now();
		}
	}
}
