package echo.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "files")
public class FileInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 스프링 2.0이상 부터 GenerationType.AUTO에 문제가 있어 IDENTITY로 수정
    private Long id;
    
    @Column(unique = true)
    private String name;

    private String downloadUri; // 간단한 자기소개

    private String type;

    private Long size;

    // @OneToOne(fetch = FetchType.LAZY, optional = false)
    // @JoinColumn(name = "file_id", nullable = false)
    // private Profile profile;

    // @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "fileInfo")
    // private Set<Account> accounts = new HashSet<>();

    // @ManyToOne(fetch = FetchType.LAZY, optional = false)
    // @JoinColumn(name = "account_id", nullable = false, updatable = false)
    // @OnDelete(action = OnDeleteAction.CASCADE)
    // @JsonIgnore
    // private Account account;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Builder
    public FileInfo(String name, String downloadUri, String type, Long size) {
        this.name = name;
        this.downloadUri = downloadUri;
        this.type = type;
        this.size = size;
    }

    public FileInfo build(String name, String downloadUri, String type, Long size) {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setName(name);
        fileInfo.setDownloadUri(downloadUri);
        fileInfo.setType(type);
        fileInfo.setSize(size);
        return fileInfo;
    }
}
