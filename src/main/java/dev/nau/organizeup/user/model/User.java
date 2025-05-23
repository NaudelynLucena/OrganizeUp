package dev.nau.organizeup.user.model;

import dev.nau.organizeup.group.model.Group;
import jakarta.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;
import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "accumulated_points")
    private int accumulatedPoints = 0;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "managed_account")
    private boolean managedAccount = false;

    @ManyToOne
    @JoinColumn(name = "guardian_id")
    private User guardian;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "access_code", unique = true)
    private String accessCode;

    @ManyToMany(mappedBy = "members")
    private Set<Group> groups = new HashSet<>();

    public User() {
    }

    public User(Long id, String name, String email, String password, int accumulatedPoints, LocalDate birthDate,
            boolean managedAccount, User guardian, Role role, String accessCode, Set<Group> groups) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.accumulatedPoints = accumulatedPoints;
        this.birthDate = birthDate;
        this.managedAccount = managedAccount;
        this.guardian = guardian;
        this.role = role;
        this.accessCode = accessCode;
        this.groups = groups;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAccumulatedPoints() {
        return accumulatedPoints;
    }

    public void setAccumulatedPoints(int accumulatedPoints) {
        this.accumulatedPoints = accumulatedPoints;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isManagedAccount() {
        return managedAccount;
    }

    public void setManagedAccount(boolean managedAccount) {
        this.managedAccount = managedAccount;
    }

    public User getGuardian() {
        return guardian;
    }

    public void setGuardian(User guardian) {
        this.guardian = guardian;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }
}
