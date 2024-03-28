package com.backend.stayEasy.entity;

import java.time.LocalDateTime;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Data //tự động tạo get/set  
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="[User]")
public class User {
	@Id
	@Column(name = "user_id")
	private UUID userId;
	
	@PrePersist
    public void prePersist() {
        if (this.userId == null) {
            this.userId = UUID.randomUUID();
        }
    }
	
	
    private String username;
    private String password;
    private String email;
    private String address;
    private String phone;
    private String avatar;
    @Column(name = "create_at")
    private LocalDateTime createAt;
    @Column(name = "update_at")
    private LocalDateTime updateAt;
	
    @OneToMany(mappedBy = "user")
    private Set<Booking> bookings;
    
    @OneToMany(mappedBy = "user")
    private Set<Like> likes;
    
    @OneToMany(mappedBy = "user")
    private Set<Feedback> feedbacks;
    
    @OneToMany(mappedBy = "user")
    private Set<Property> properties;
    
    @ManyToMany
    @JoinTable(
    		name = "user_role",
    		joinColumns = @JoinColumn(name ="user_id"),
    		inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Roles> roles;
    
  

    
}
