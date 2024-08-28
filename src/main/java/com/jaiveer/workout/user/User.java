package com.jaiveer.workout.user;

import com.jaiveer.workout.program.WorkoutProgram;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Long userid;

    @Column(unique = true,nullable  = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    private int age;
    private String gender;
    private double weight;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WorkoutProgram> workoutPrograms;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<Role> roles = new HashSet<>(Collections.singleton(Role.ROLE_USER));

    @Override
    public String toString(){
        return username + " " + firstname + " " + lastname + " " + age + " " + gender + " " + weight + " " + email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                        .collect(Collectors.toSet());
    }
}
