package com.francisco.openpolls.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.francisco.openpolls.model.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long>{

}
