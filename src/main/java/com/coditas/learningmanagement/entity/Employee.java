package com.coditas.learningmanagement.entity;

import com.coditas.learningmanagement.enums.DepartmentType;
import com.coditas.learningmanagement.enums.EmployeeStatus;
import com.coditas.learningmanagement.enums.RoleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;
    private String username;
    private String password;

    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private EmployeeStatus employeeStatus;

    @Enumerated(EnumType.STRING)
    private DepartmentType department;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<RoleType> roles;

    @OneToMany(mappedBy = "createdBy")
    private List<Course> createdCoursesList=new ArrayList<>();

    @OneToMany(mappedBy = "enrolledBy")
    private List<Enrollment>enrollments=new ArrayList<>();

    @OneToMany(mappedBy = "employee")
    private List<LectureProgress>lectureProgressList=new ArrayList<>();

    @OneToMany(mappedBy = "issuedTo")
    private List<Certificate>certificates=new ArrayList<>();




    @Override
    @NullMarked
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(roleType -> new SimpleGrantedAuthority("ROLE_"+roleType.name())).toList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
