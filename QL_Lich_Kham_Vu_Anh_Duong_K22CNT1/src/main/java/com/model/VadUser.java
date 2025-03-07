package com.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Vad_users")
public class VadUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "Vadfullname", nullable = false)
	private String fullname;

	@Column(name = "Vademail", nullable = false)
	private String email;

	@Column(name = "Vadphone", nullable = false)
	private String phone;

	@Column(name = "Vadpassword", nullable = false)
	private String password;
	
	@Enumerated(EnumType.STRING)
	 @Column(name = "Vadrole")
	private vadRole vadrole;

	
	// Enum Role nên viết hoa theo chuẩn Java
	public enum vadRole {
	    admin, patient
	}

	// Constructor mặc định
	public VadUser() {}

	// Constructor có tham số
	public VadUser(String fullname, String email, String phone, String password, vadRole vadrole) {
		this.fullname = fullname;
		this.email = email;
		this.phone = phone;
		this.password = password;
		this.vadrole = vadrole;
	}

	// Getter và Setter
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }

	public String getFullname() { return fullname; }
	public void setFullname(String fullname) { this.fullname = fullname; }

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	public String getPhone() { return phone; }
	public void setPhone(String phone) { this.phone = phone; }

	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }

	public vadRole getVadrole() { return vadrole; }
	public void setVadrole(vadRole vadrole) { this.vadrole = vadrole; }
}
