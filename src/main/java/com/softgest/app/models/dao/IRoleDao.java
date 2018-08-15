package com.softgest.app.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softgest.app.models.entity.Role;
@Repository
public interface IRoleDao extends JpaRepository<Role, Long> {

	//@Query("INSERT INTO `authorities` (user_id, authority) VALUES (?1,?2)")
	//void save(Long user_id, String authority);

	
}
