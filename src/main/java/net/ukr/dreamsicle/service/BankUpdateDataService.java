package net.ukr.dreamsicle.service;

import lombok.AllArgsConstructor;
import net.ukr.dreamsicle.exception.ResourceNotFoundException;
import net.ukr.dreamsicle.model.atm.ATM;
import net.ukr.dreamsicle.model.bank.Bank;
import net.ukr.dreamsicle.model.office.Office;
import net.ukr.dreamsicle.model.product.Product;
import net.ukr.dreamsicle.repository.BankRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional(isolation = Isolation.SERIALIZABLE)
public class BankUpdateDataService {

    private final BankRepository bankRepository;

    // I need help with this method. I'm not sure that I correctly implemented this method with switch case
    public <T> void addDataToBank(String bankCode, T model) {
        Bank bank = bankRepository.findBankByBankCode(bankCode).orElseThrow(ResourceNotFoundException::new);
        switch (model.getClass().getSimpleName()) {
            case "ATM":
                List<ATM> atms = bank.getAtms();
                atms.add((ATM) model);
                bank.setAtms(atms);
                bankRepository.save(bank);
                break;
            case "Office":
                List<Office> offices = bank.getOffices();
                offices.add((Office) model);
                bank.setOffices(offices);
                bankRepository.save(bank);
                break;
            case "Product":
                List<Product> products = bank.getProducts();
                products.add((Product) model);
                bank.setProducts(products);
                bankRepository.save(bank);
                break;
            default:
                break;
        }
    }

    // I need help with this method. Some as above.
    public <T> void updateDateToBank(String id, String bankCode, T model) {
        Bank bank = bankRepository.findBankByBankCode(bankCode).orElseThrow(ResourceNotFoundException::new);
        switch (model.getClass().getSimpleName()) {
            case "ATM":
                List<ATM> atms = bank.getAtms();
                atms.stream()
                        .filter(atm -> atm.getId().equals(id))
                        .forEach(atm -> {
                            atm.setName(((ATM) model).getName());
                            atm.setState(((ATM) model).getState());
                            atm.setCity(((ATM) model).getCity());
                            atm.setStreet(((ATM) model).getStreet());
                            atm.setWorkTimes(((ATM) model).getWorkTimes());
                        });
                bank.setAtms(atms);
                bankRepository.save(bank);
                break;
            case "Office":
                List<Office> offices = bank.getOffices();
                offices.stream()
                        .filter(office -> office.getId().equals(id))
                        .forEach(office -> {
                            office.setName(((Office) model).getName());
                            office.setState(((Office) model).getState());
                            office.setCity(((Office) model).getCity());
                            office.setStreet(((Office) model).getStreet());
                            office.setWorkTimes(((Office) model).getWorkTimes());
                        });
                bank.setOffices(offices);
                bankRepository.save(bank);
                break;
            case "Product":
                List<Product> products = bank.getProducts();
                products.stream()
                        .filter(product -> product.getId().equals(id))
                        .forEach(product -> {
                            product.setType(((Product) model).getType());
                            product.setDescription(((Product) model).getDescription());
                        });
                bank.setProducts(products);
                bankRepository.save(bank);
                break;
            default:
                break;
        }
    }
}
