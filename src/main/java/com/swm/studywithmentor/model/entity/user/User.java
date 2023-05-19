package com.swm.studywithmentor.model.entity.user;

import com.swm.studywithmentor.model.entity.enrollment.Enrollment;
import com.swm.studywithmentor.model.entity.course.Course;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "[User]")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Column
    @Version
    private Long version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Date birthdate;
    private String profileImage;
    @Enumerated(EnumType.ORDINAL)
    private Gender gender;
    @Enumerated(EnumType.ORDINAL)
    private Role role;
    @OneToOne(mappedBy = "user")
    @PrimaryKeyJoinColumn
    private Student student;
    @OneToOne(mappedBy = "user")
    @PrimaryKeyJoinColumn
    private Mentor mentor;
    @OneToMany(mappedBy = "user")
    private Set<Course> courses;
    @OneToMany(mappedBy = "user")
    private Set<Enrollment> enrollments;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
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
