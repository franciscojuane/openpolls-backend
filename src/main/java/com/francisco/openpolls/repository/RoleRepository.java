package com.francisco.openpolls.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.francisco.openpolls.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
