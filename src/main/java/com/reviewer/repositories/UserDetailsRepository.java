package com.reviewer.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reviewer.pojos.UserDetails;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long>{

	public UserDetails findByEmail(String email);
	public boolean existsByEmail(String email);
	public boolean existsByMobileNumber(String mobileNumber);

}
