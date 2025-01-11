package org.example.sheltercare.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Volunteer
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String contactInfo;  // Contact information of the volunteer

    @ManyToMany(mappedBy = "volunteers")
    private List<Animal> animals;  // Set of animals that the volunteer cares for


}
