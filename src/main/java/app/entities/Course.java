package app.entities;

import app.enums.CourseName;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.boot.model.process.internal.UserTypeResolution;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private CourseName courseName;
    private String description;
    private LocalDate endDate;
    private LocalDate startDate;

    // Relations
    @Builder.Default
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "course", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Student> students = new HashSet<>();

    @ManyToOne
    @Setter
    @ToString.Exclude
    private Teacher teacher;

    // Bi-directional update

    public void addStudent(Student student)
    {
        this.students.add(student);
        if (student != null)
        {
            student.setCourse(this);
        }
    }
}
