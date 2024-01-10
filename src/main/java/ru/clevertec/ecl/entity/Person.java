package ru.clevertec.ecl.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @GeneratedValue
    private UUID uuid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Sex sex;

    @Column(name = "passport_series", unique = true, nullable = false)
    private String passportSeries;

    @Column(name = "passport_number", unique = true, nullable = false)
    private String passportNumber;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date", updatable = false, nullable = false)
    @CreationTimestamp
    private LocalDateTime createDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date")
    @UpdateTimestamp
    private LocalDateTime updateDate;

    @ManyToOne
    @JoinColumn(name = "house_id", nullable = false)
    private House house;

    @ManyToMany
    @JoinTable(
        name = "person_house",
        joinColumns = @JoinColumn(name = "person_id"),
        inverseJoinColumns = @JoinColumn(name = "house_id")
    )
    @ToString.Exclude
    private Set<House> houses = new HashSet<>();

    @PrePersist
    public void onPrePersist() {
        uuid = UUID.randomUUID();
        updateDate = createDate = LocalDateTime.now();
    }

}
