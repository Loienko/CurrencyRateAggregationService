package net.ukr.dreamsicle.model.bank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ukr.dreamsicle.model.atm.ATM;
import net.ukr.dreamsicle.model.office.Office;
import net.ukr.dreamsicle.model.partners.Partner;
import net.ukr.dreamsicle.model.product.Product;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "bank")
public class Bank {

    @Id
    private String id;
    private String bankName;
    private String bankCode;
    private String iban;
    private String state;
    private String city;
    private String street;
    private List<Partner> partners;
    @DBRef
    private List<Product> products;
    @DBRef
    private List<Office> office;
    @DBRef
    private List<ATM> atm;
}
