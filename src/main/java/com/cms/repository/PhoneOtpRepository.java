package com.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.entity.PhoneOtp;

public interface PhoneOtpRepository extends JpaRepository<PhoneOtp, Long> {
	
	PhoneOtp findByPhoneNumberAndOtp(String phoneNumber,String otp);

}
