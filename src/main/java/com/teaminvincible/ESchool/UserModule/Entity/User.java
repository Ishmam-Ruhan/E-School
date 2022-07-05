package com.teaminvincible.ESchool.UserModule.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.teaminvincible.ESchool.Enums.Role;
import com.teaminvincible.ESchool.UserDescriptionModule.Entity.UserDescription;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36,columnDefinition = "VARCHAR(255)", nullable = false, updatable = false, unique = true)
    private String userId;

    @Column(name = "email", nullable = false,unique = true)
    private String email;

    @Column(name = "phone", nullable = false)
    private String phone;


    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Role role;

    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE}, orphanRemoval = true,mappedBy = "user")
    private UserDescription userDescription;

    @Temporal(TemporalType.DATE)
    @CreatedDate
    @Column(nullable = false,updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date joinedDate;

    public User() {
    }

    public User(String userId, String email, String phone, String password, Role role) {
        this.userId = userId;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role;
    }

    @PreRemove
    public void beforeRemovingObject(){
        userDescription = null;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserDescription getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(UserDescription userDescription) {
        this.userDescription = userDescription;
    }

    public Date getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(Date joinedDate) {
        this.joinedDate = joinedDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
