package net.ukr.dreamsicle.model;

import lombok.*;

import javax.persistence.*;

/**
 * A simple Entity object that creates a 'user_details' table in a database
 * Used by {@link Lombok} to create template methods of an object like getters. setters, etc.
 *
 * @author yurii.loienko
 * @version 1.0
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user_details")
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String surname;

    private String phone;

    private String description;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
}
