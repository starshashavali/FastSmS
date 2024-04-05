package com.cms.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cms.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	UserEntity findByFullName(String fullName);
	
	UserEntity findByFullNameAndPhoneNumber(String fullName, String phoneNumber);
	
	Optional<UserEntity> findByPhoneNumber(String phone);
	
    //List<UserEntity> findBySubscriptionEndDateBefore(LocalDate date);
    
    List<UserEntity> findBySubscriptionEndDateBefore(LocalDate date);
    
    @Query("SELECT u FROM UserEntity u WHERE u.subscriptionEndDate = ?1")
    List<UserEntity> findUsersBySubscriptionEndDate(LocalDate endDate);
    
    List<UserEntity> findByFullNameStartingWith(String prefix);
}
	
	

