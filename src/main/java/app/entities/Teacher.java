package app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;
    private String email;
    private String name;
    private String zoom;

    // Relations

    @Builder.Default
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "teacher", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Course> courses = new HashSet<>();

    // Bi-directional update

    public void addCourse(Course course)
    {
        this.courses.add(course);
        if (course != null)
        {
            course.setTeacher(this);
        }
    }

}
