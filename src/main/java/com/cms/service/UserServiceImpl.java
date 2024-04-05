package com.cms.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cms.binding.PersonCounts;
import com.cms.entity.PhoneOtp;
import com.cms.entity.UserEntity;
import com.cms.exception.UserNotValidException;
import com.cms.repository.PhoneOtpRepository;
import com.cms.repository.UserRepository;
import com.cms.utils.EmailUtils;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

@Service
public class UserServiceImpl {
	
	

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmailUtils emailUtils;
	
	@Autowired
	private PhoneOtpRepository phoneOtpRepo;
	
	


	public UserEntity savePerson(UserEntity person, MultipartFile imageFile) throws IOException {

		if (imageFile != null && !imageFile.isEmpty()) {
			person.setProfilePic(imageFile.getBytes());
		}
		

		simulatePayment(person);

		return userRepository.save(person);
	}

	public PersonCounts getCountOfPersons() {
		List<UserEntity> allPersons = userRepository.findAll();

		Integer maleCount = (int) allPersons.stream().filter(person -> person.getGender().equalsIgnoreCase("Male"))
				.count();

		Integer femaleCount = (int) allPersons.stream().filter(person -> person.getGender().equalsIgnoreCase("Female"))
				.count();

		Integer childrenCount = (int) allPersons.stream().filter(person -> person.getAge() < 18).count();

		Integer seniorCitizenCount = (int) allPersons.stream().filter(person -> person.getAge() >= 60).count();

		return new PersonCounts(maleCount, femaleCount, childrenCount, seniorCitizenCount);
	}
	
	
	

	public  void sendOTP(String fullName, String numbers, String route) throws Exception {

		UserEntity nameAndPhoneNumber = userRepository.findByFullNameAndPhoneNumber(fullName, numbers);

		if (nameAndPhoneNumber == null) {
			throw new UserNotValidException("UserNot found...");
		}
		String apiKey = "S4oGJgY1R20MTPEzaDL3wNKIqCkZVUABlWjdpH9nXrtbFOiy5uuFWL9YZMkmJlP7Azsjy6Xo18r2nCid";

		String variablesValues = generateOTP();

		// Saving OTP message to database
		PhoneOtp otpMessage = new PhoneOtp();
		otpMessage.setOtp(variablesValues);
		otpMessage.setPhoneNumber(numbers);
		otpMessage.setFullName(fullName);
		phoneOtpRepo.save(otpMessage);

		// Sending OTP
		HttpResponse<String> response = Unirest.post("https://www.fast2sms.com/dev/bulkV2")
				.header("authorization", apiKey).header("Content-Type", "application/x-www-form-urlencoded")
				.body("variables_values=" + variablesValues + "&route=" + route + "&numbers=" + numbers).asString();

		// Printing response
		System.out.println(response.getBody());

	}

	private String generateOTP() {
		Random random = new Random();
		// Generate a 4-digit OTP (you can adjust the length as needed)
		int otp = 1000 + random.nextInt(9000);
		return String.valueOf(otp);
	}

	// verify number and otp

	public String verifyOtp(String numbers, String otp) {
		PhoneOtp entity = phoneOtpRepo.findByPhoneNumberAndOtp(numbers, otp);
		if (entity != null) {
			return "Otp Verified";
		}
		return "Invalid OTP";

	}

	public byte[] getImageDataById(Long id) {
		Optional<UserEntity> optinal = userRepository.findById(id);
		if (optinal.isPresent()) {
			UserEntity userEntity = optinal.get();
			return userEntity.getProfilePic();
		}
		 throw new UserNotValidException("UserNot found...");
	}


	// Method to simulate a dummy payment
	private void simulatePayment(UserEntity person) {
		// dummy logic or external payment API

		System.out.println("Dummy payment processed for user: " + person.getFullName() + ", Amount: "
				+ person.getPrice() + " USD");

		person.setPaidAmount(person.getPrice());
	}
	
	@Scheduled(cron = "0 * * * * *") // Cron expression for running every minute
	 public void sendSubscriptionExpirationNotifications() throws Exception {
        System.out.println("Request to :: Notification...");
        
        List<UserEntity> users = userRepository.findAll();
        System.out.println(" All User Entity dtls");
     
        LocalDate today = LocalDate.now();
        System.out.println("Request to :: today..." + today);

        for (UserEntity user : users) {
            LocalDate subscriptionEndDate = user.getSubscriptionEndDate();
            System.out.println("Request to :: subscriptionEndDate..." + subscriptionEndDate);

            if (subscriptionEndDate != null && subscriptionEndDate.isEqual(today)) {
                System.out.println("Sending notification for user: " + user.getId());
                String emailMessage = "Dear " + user.getFullName() + ", Your subscription will expire today. Please renew your subscription.";

                // Send email
                emailUtils.sendEmail(user.getEmail(), "Subscription Expiration Notification", emailMessage);
            } else {
                System.out.println("Subscription end date is null or not today for user: " + user.getId());
            }
        }
    }
/*
   	
	public void sendSMS(String numbers,String route) throws Exception {
	    Optional<UserEntity> userOptional = userRepository.findByPhoneNumber(numbers);
	    if (!userOptional.isPresent()) {
	        throw new UserNotValidException("User not found for phone number: " + numbers);
	    }

	    UserEntity userEntity = userOptional.get();
	    String fullName = userEntity.getFullName();
	    
	    // Construct the SMS message
	    String smsMessage = "Dear " + fullName + ", Your subscription will expire today. Please renew your subscription.";
	    
	    // Sending SMS
	    String apiKey = "S4oGJgY1R20MTPEzaDL3wNKIqCkZVUABlWjdpH9nXrtbFOiy5uuFWL9YZMkmJlP7Azsjy6Xo18r2nCid";
	    HttpResponse<String> response = Unirest.post("https://www.fast2sms.com/dev/bulkV2")
	            .header("authorization", apiKey)
	            .header("Content-Type", "application/x-www-form-urlencoded")
	            .body("message=" + smsMessage  +"&route=" + route + "&numbers=" + numbers)
	            .asString();

	    // Printing response
	    System.out.println(response.getBody());
	}
	*/
	
	}