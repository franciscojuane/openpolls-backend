package com.francisco.openpolls.model;
import java.util.HashSet;
import java.util.Set;

import com.francisco.openpolls.model.common.AbstractModel;
import com.francisco.openpolls.model.common.Constants;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = Constants.TABLE_PREFIX + "ROLE")
public class Role extends AbstractModel{

	private String name;
	
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = Constants.TABLE_PREFIX + "ROLE_PERMISSIONS",
        joinColumns = @JoinColumn(name = "role_id"),
        inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
	@Default
    private Set<Permission> permissions = new HashSet<>();
	
}
