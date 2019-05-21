package echo.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 스프링 2.0이상 부터 GenerationType.AUTO에 문제가 있어 IDENTITY로 수정
    private Long id;

    private String name;

    private String bio; // 간단한 자기소개

    private String company;

    private String address;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = true)
    private Account account;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "profile_dbfiles", joinColumns = @JoinColumn(name = "profile_id"), inverseJoinColumns = @JoinColumn(name = "dbfile_id"))
    private Set<DBFile> dbFiles = new HashSet<>(); // 다중 권한 부여 가능 하도록 HashSet 사용

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Profile(String name, String bio, String company, String address) {
        this.name = name;
        this.bio = bio;
        this.company = company;
        this.address = address;
    }
}
