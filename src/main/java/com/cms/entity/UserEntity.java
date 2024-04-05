package com.cms.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "UserDtls")
@ToString
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String fullName;
	private String familyName;
	private LocalDate dateOfBirth;
	private String gender;
	private Integer age;
	private String profession;
	private String email;
	private String phoneNumber;
	private String disability;
	private String nationality;
	private String landmark;
	private String maritalStatus;
	private String husbandOrWifeName;
	private String fatherName;
	private String motherName;

	private String subscription;// premium

	private Integer subscriptionDuration; // 1 year, 2 years, 5 years, .

	private LocalDate subscriptionStartDate; // Start date of subscription

	private LocalDate subscriptionEndDate; // End date of subscription

	private Double price;

	private Double discount;

	private Double paidAmount;

	@Lob
	private byte[] profilePic;

}
