package com.teaminvincible.ESchool.UserDescriptionModule.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.teaminvincible.ESchool.Enums.Role;
import com.teaminvincible.ESchool.UserModule.Entity.User;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users_description")
public class UserDescription {

    @Id
    private String userId;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public Role role;

    @OneToOne
    @MapsId
    private User user;

    public UserDescription() {
    }

    public UserDescription(String userId, String name, Role role, User user) {
        this.userId = userId;
        this.name = name;
        this.role = role;
        this.user = user;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserDescription{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", role=" + role +
                ", user=" + user +
                '}';
    }
}
