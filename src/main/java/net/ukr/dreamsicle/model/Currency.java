package net.ukr.dreamsicle.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "currency_id_seq")
    @Column(unique = true, nullable = false)
    private int id;

    private String bankName;
    private String currencyCode;
    private String purchaseCurrency;
    private String saleOfCurrency;
}
