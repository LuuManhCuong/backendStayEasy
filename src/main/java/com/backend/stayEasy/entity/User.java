package com.backend.stayEasy.entity;

import com.backend.stayEasy.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "users")
public class User implements UserDetails {

	@Id
	@Column(name = "id", unique = true, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Column(name = "email", nullable = false, unique = true)
	String email;

	@Column(name = "password", nullable = false)
	String password;

	@Column(name = "firstName", nullable = true)
	String firstName;

	@Column(name = "lastName")
	String lastName;
<<<<<<< HEAD

=======
	
>>>>>>> origin/namhh-update-infor
	@Column(name = "phone")
	String phone;
	
	@Column(name = "address")
	String address;

	@Column(name = "address")
	String address;

	String avatar;

	Date createdAt;

	Date updatedAt;

	@Enumerated(EnumType.STRING)
	private Role role;

	@OneToMany(mappedBy = "user")
	private List<Token> tokens;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return role.getAuthorities();
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}