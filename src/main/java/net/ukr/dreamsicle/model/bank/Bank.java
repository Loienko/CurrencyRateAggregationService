package net.ukr.dreamsicle.model.bank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ukr.dreamsicle.model.atm.ATM;
import net.ukr.dreamsicle.model.office.Office;
import net.ukr.dreamsicle.model.product.Product;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
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
    private ObjectId id;

    @Indexed(unique = true)
    private String bankName;

    @Indexed(unique = true)
    private String bankCode;
    private String iban;
    private String state;
    private String city;
    private String street;
    private List<Product> products;
    private List<Office> offices;
    private List<ATM> atms;
}
