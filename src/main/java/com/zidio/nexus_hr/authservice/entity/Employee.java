package com.zidio.nexus_hr.authservice.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "employees")
public class Employee {

    @Id    
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private String id;

    @Column(nullable = false, unique = true, length = 20)
    private String employeeCode;

    @Column(nullable = false, length = 100)
    private String firstName;

    @Column(nullable = false, length = 100)
    private String lastName;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(length = 15)
    private String phoneNumber;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private LocalDate dateOfJoining;

    private LocalDate dateOfExit;

    @Column(length = 100)
    private String designation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EmploymentStatus status = EmploymentStatus.ACTIVE;

    @Column(precision = 12, scale = 2)
    private java.math.BigDecimal baseSalary;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public enum EmploymentStatus {
        ACTIVE, ON_LEAVE, SUSPENDED, OFFBOARDED
    }
    

	public Employee() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public LocalDate getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(LocalDate dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public LocalDate getDateOfExit() {
		return dateOfExit;
	}

	public void setDateOfExit(LocalDate dateOfExit) {
		this.dateOfExit = dateOfExit;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public EmploymentStatus getStatus() {
		return status;
	}

	public void setStatus(EmploymentStatus status) {
		this.status = status;
	}

	public java.math.BigDecimal getBaseSalary() {
		return baseSalary;
	}

	public void setBaseSalary(java.math.BigDecimal baseSalary) {
		this.baseSalary = baseSalary;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public int hashCode() {
		return Objects.hash(baseSalary, createdAt, dateOfBirth, dateOfExit, dateOfJoining, designation, email,
				employeeCode, firstName, id, lastName, phoneNumber, status, updatedAt);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(baseSalary, other.baseSalary) && Objects.equals(createdAt, other.createdAt)
				&& Objects.equals(dateOfBirth, other.dateOfBirth) && Objects.equals(dateOfExit, other.dateOfExit)
				&& Objects.equals(dateOfJoining, other.dateOfJoining) && Objects.equals(designation, other.designation)
				&& Objects.equals(email, other.email) && Objects.equals(employeeCode, other.employeeCode)
				&& Objects.equals(firstName, other.firstName) && Objects.equals(id, other.id)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(phoneNumber, other.phoneNumber)
				&& status == other.status && Objects.equals(updatedAt, other.updatedAt);
	}
}
