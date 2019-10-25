package net.ukr.dreamsicle.model;

import lombok.*;

import javax.persistence.*;

/**
 * A simple Entity object that creates a 'currency' table in a database
 * Used by {@link Lombok} to create template methods of an object like getters. setters, etc.
 *
 * @author yurii.loienko
 * @version 1.0
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String bankName;
    private String currencyCode;
    private String purchaseCurrency;
    private String saleOfCurrency;
}
