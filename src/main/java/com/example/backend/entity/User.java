package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String fullName;
    String username;
    String email;
    String password;
    @CreationTimestamp
    LocalDateTime createdAt;
    @UpdateTimestamp
    LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user")
    List<Transaction> transactions;

    @OneToMany(mappedBy = "user")
    List<Category> categories;

    @OneToMany(mappedBy = "user")
    List<Budget> budgets;

    @OneToMany(mappedBy = "user")
    List<Debt> debts;

    @OneToMany(mappedBy = "user")
    List<Reminder> reminders;

    @OneToMany(mappedBy = "user")
    List<Report> reports;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}
