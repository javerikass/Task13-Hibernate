package ru.clevertec.ecl.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private UUID uuid;

    @Column(nullable = false)
    private String area;
    @Column(nullable = false)
    private String country;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String street;
    @Column(nullable = false)
    private String number;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", updatable = false)
    @CreationTimestamp
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<Person> residents = new HashSet<>();

    @ManyToMany(mappedBy = "houses", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Person> owners = new HashSet<>();

    @PrePersist
    public void onPrePersist() {
        uuid = UUID.randomUUID();
        createDate = LocalDateTime.now();
    }

}
