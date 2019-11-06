package net.ukr.dreamsicle.dto.bank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ukr.dreamsicle.model.atm.ATM;
import net.ukr.dreamsicle.model.office.Office;
import net.ukr.dreamsicle.model.partners.Partner;
import net.ukr.dreamsicle.model.product.Product;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankDTO {

    private String id;
    private String bankName;
    private String bankCode;
    private String iban;
    private String state;
    private String city;
    private String street;
    private List<Partner> partners;
    private List<Product> products;
    private List<Office> offices;
    private List<ATM> atms;
}
