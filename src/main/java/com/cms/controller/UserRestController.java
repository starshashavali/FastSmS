package com.cms.controller;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cms.binding.PersonCounts;
import com.cms.entity.UserEntity;
import com.cms.exception.UserNotValidException;
import com.cms.repository.UserRepository;
import com.cms.service.UserServiceImpl;

@RestController
public class UserRestController {

	@Autowired
	private UserServiceImpl userServiceImpl;

	@Autowired
	private UserRepository userRepository;


	
  


	@PostMapping("/savePerson")
	public ResponseEntity<UserEntity> savePerson(@RequestParam("file") MultipartFile imageFile,
			@RequestParam("fullName") String fullName, @RequestParam("familyName") String familyName,
			@RequestParam("gender") String gender, @RequestParam("dateOfBirth") LocalDate dateOfBirth,
			@RequestParam("age") Integer age, @RequestParam("profession") String profession,
			@RequestParam("email") String email, @RequestParam("phoneNumber") String phoneNumber,
			@RequestParam("disability") String disability, @RequestParam("nationality") String nationality,
			@RequestParam("landmark") String landmark, @RequestParam("maritalStatus") String maritalStatus,
			@RequestParam("husbandWifeName") String husbandWifeName, @RequestParam("fatherName") String fatherName,
			@RequestParam("motherName") String motherName) {

		try {
			UserEntity person = new UserEntity();
			person.setFullName(fullName);
			person.setFamilyName(familyName);
			person.setGender(gender);
			person.setDateOfBirth(dateOfBirth);
			person.setAge(age);
			person.setProfession(profession);
			person.setEmail(email);
			person.setPhoneNumber(phoneNumber);
			person.setDisability(disability);
			person.setNationality(nationality);
			person.setLandmark(landmark);
			person.setMaritalStatus(maritalStatus);
			person.setHusbandOrWifeName(husbandWifeName);
			person.setFatherName(fatherName);
			person.setMotherName(motherName);

			// Save the person with image file
			UserEntity savedPerson = userServiceImpl.savePerson(person, imageFile);

			return ResponseEntity.status(HttpStatus.CREATED).body(savedPerson);
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@PostMapping("/send-otp")
	public String sendOTP(@RequestParam String fullName, @RequestParam String phoneNumber, @RequestParam String route) {
		try {
			userServiceImpl.sendOTP(fullName, phoneNumber, route);
			return "OTP sent successfully.";
		} catch (Exception e) {
			return "Failed to send OTP: " + e.getMessage();
		}
	}

	@PostMapping("/verify-otp")
	public ResponseEntity<String> verifyOtp(@RequestParam String numbers, @RequestParam String otp) {
		String verifyOtp = userServiceImpl.verifyOtp(numbers, otp);
		return ResponseEntity.status(HttpStatus.OK).body(verifyOtp);
	}

	@GetMapping("/counts")
	public ResponseEntity<?> getPersonCounts() {
		PersonCounts countOfPersons = userServiceImpl.getCountOfPersons();
		return ResponseEntity.status(HttpStatus.OK).body(countOfPersons);
	}

	@GetMapping("/images/{id}")
	public ResponseEntity<byte[]> getImageById(@PathVariable Long id) {
		byte[] imageData = userServiceImpl.getImageDataById(id);

		if (imageData == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok().body(imageData);
	}
	
			}
