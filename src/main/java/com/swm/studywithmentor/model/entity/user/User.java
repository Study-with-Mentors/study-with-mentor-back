package com.swm.studywithmentor.model.entity.user;

import com.swm.studywithmentor.model.entity.BaseEntity;
import com.swm.studywithmentor.model.entity.enrollment.Enrollment;
import com.swm.studywithmentor.model.entity.course.Course;
import javax.persistence.*;

import lombok.*;
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
@EqualsAndHashCode(callSuper = true)
@AttributeOverride(name = "id", column = @Column(name = "user_id") )
public class User extends BaseEntity implements UserDetails {
    @Column
    @Version
    private Long version;

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Date birthdate;
    private String profileImage;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToOne(mappedBy = "user")
    @PrimaryKeyJoinColumn
    private Student student;
    @OneToOne(mappedBy = "user")
    @PrimaryKeyJoinColumn
    private Mentor mentor;
    @OneToMany(mappedBy = "mentor")
    private Set<Course> courses;
    @OneToMany(mappedBy = "student")
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
