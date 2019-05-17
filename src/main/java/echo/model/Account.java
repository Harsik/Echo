package echo.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.validation.constraints.Email;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 스프링 2.0이상 부터 GenerationType.AUTO에 문제가 있어 IDENTITY로 수정
    private Long id;

    @Email
    private String email;

    private String password;
    
    private String name;

    private String bio; // 간단한 자기소개

    private String company;

    private String address;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "account_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>(); // 다중 권한 부여 가능 하도록 HashSet 사용

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

    // @Builder
    // public Account(String email, String password){
    // this.email=email;
    // this.password=password;
    // this.createdAt=LocalDateTime.now();
    // this.updateAt=LocalDateTime.now();
    // }
    
    @Builder
    public Account(String email, String password, String name, String bio, String company, String address){
    this.email=email;
    this.password=password;
    this.name=name;
    this.bio=bio;
    this.company=company;
    this.address=address;
    this.createdAt=LocalDateTime.now();
    this.updateAt=LocalDateTime.now();
    }
}
