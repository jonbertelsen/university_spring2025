package app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@ToString
@EqualsAndHashCode
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;
    private LocalDateTime createdAt;
    private String email;
    private String name;
    private LocalDateTime updatedAt;

    @PrePersist
    private void setCreatedAt(){
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    private void setUpdatedAt(){
        updatedAt = LocalDateTime.now();
    }

    // Relations

    @ManyToOne
    @Setter
    @ToString.Exclude
    private Course course;

}
