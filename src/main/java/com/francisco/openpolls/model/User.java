package com.francisco.openpolls.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.francisco.openpolls.model.common.Constants;
import com.francisco.openpolls.model.common.EffectiveModel;

import jakarta.persistence.Column;
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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = Constants.TABLE_PREFIX + "USER")
@Entity
@Builder
@EqualsAndHashCode(callSuper = false)
public class User extends EffectiveModel implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false, length = 50)
	private String firstName;

	@Column(nullable = false, length = 50)
	private String lastName;

	@Column(nullable = false)
	@JsonIgnore
	private String password;

	@Column(nullable = false, unique = true)
	private String email;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = Constants.TABLE_PREFIX + "USER_ROLES", 
			   joinColumns = @JoinColumn(name = "user_id"), 
			   inverseJoinColumns = @JoinColumn(name = "role_id"))
	@Default
	private Set<Role> roles = new HashSet<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authoritiesList = new ArrayList<>();
		for(Role role: this.getRoles()) {
			role.getPermissions().forEach(p -> authoritiesList.addAll(role.getPermissions().stream().map(x -> new SimpleGrantedAuthority(x.getName())).collect(Collectors.toList())));
		}
		return authoritiesList;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.isEffective();
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
