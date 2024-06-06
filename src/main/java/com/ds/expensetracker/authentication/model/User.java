package com.ds.expensetracker.authentication.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Users", uniqueConstraints = {@UniqueConstraint(columnNames = "emailId")})
public class User extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //1,2,3,4
    @NotNull
    private long userPkId;

    @NotNull
    private String name;

    @NotNull
    @Email
    @Column(unique = true)
    private String emailId;
    private String password;

    @Temporal(TemporalType.DATE) //Will only store Date
    private Date birthDate;
    private byte[] profilePic;


//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
//    private List<Cashbook> cashbooks;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //returning empty as we don't require role based now
        return List.of();
    }

    @Override
    public String getUsername() {
        //We are considering username as email.
        return emailId;
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
