package net.ukr.dreamsicle.dto.bank;

import net.ukr.dreamsicle.dto.atm.AtmDTO;
import net.ukr.dreamsicle.dto.office.OfficeDTO;
import net.ukr.dreamsicle.dto.product.ProductDTO;
import net.ukr.dreamsicle.exception.ResourceNotFoundException;
import net.ukr.dreamsicle.model.atm.ATM;
import net.ukr.dreamsicle.model.bank.Bank;
import net.ukr.dreamsicle.model.office.Office;
import net.ukr.dreamsicle.model.product.Product;
import net.ukr.dreamsicle.model.product.TypeProduct;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Mapper
@Service
public interface BankMapper {

    default BankDTO toBankDto(Bank bank) {
        Optional.ofNullable(bank).orElseThrow(ResourceNotFoundException::new);
        return BankDTO.builder()
                .id(bank.getId().toHexString())
                .bankName(bank.getBankName())
                .bankCode(bank.getBankCode())
                .iban(bank.getIban())
                .state(bank.getState())
                .city(bank.getCity())
                .street(bank.getStreet())
                .products(bank.getProducts().stream().map(this::toProductDto).collect(Collectors.toList()))
                .offices(bank.getOffices().stream().map(this::toOfficeDto).collect(Collectors.toList()))
                .atms(bank.getAtms().stream().map(this::toAtmDto).collect(Collectors.toList()))
                .build();
    }

    default ProductDTO toProductDto(Product product) {
        return ProductDTO.builder()
                .id(product.getId().toHexString())
                .bankCode(product.getBankCode())
                .type(product.getType().getName())
                .description(product.getDescription())
                .build();
    }

    default AtmDTO toAtmDto(ATM atm) {
        return AtmDTO.builder()
                .id(atm.getId().toHexString())
                .bankCode(atm.getBankCode())
                .name(atm.getName())
                .city(atm.getCity())
                .state(atm.getState())
                .street(atm.getStreet())
                .workTimes(atm.getWorkTimes())
                .build();
    }

    default OfficeDTO toOfficeDto(Office office) {
        return OfficeDTO.builder()
                .id(office.getId().toHexString())
                .bankCode(office.getBankCode())
                .name(office.getName())
                .city(office.getCity())
                .state(office.getState())
                .street(office.getStreet())
                .workTimes(office.getWorkTimes())
                .build();
    }

    default Bank toBank(BankDTO bankDTO) {
        return Bank.builder()
                .bankName(bankDTO.getBankName())
                .state(bankDTO.getState())
                .city(bankDTO.getCity())
                .street(bankDTO.getStreet())
                .products(bankDTO.getProducts().stream().map(this::toProduct).collect(Collectors.toList()))
                .offices(bankDTO.getOffices().stream().map(this::toOffice).collect(Collectors.toList()))
                .atms(bankDTO.getAtms().stream().map(this::toAtm).collect(Collectors.toList()))
                .build();
    }

    default Product toProduct(ProductDTO productDTO) {
        return Product.builder()
                .bankCode(productDTO.getBankCode())
                .type(TypeProduct.getEnumFromString(productDTO.getType()))
                .description(productDTO.getDescription())
                .build();
    }

    default Office toOffice(OfficeDTO officeDTO) {
        return Office.builder()
                .bankCode(officeDTO.getBankCode())
                .name(officeDTO.getName())
                .city(officeDTO.getCity())
                .state(officeDTO.getState())
                .street(officeDTO.getStreet())
                .workTimes(officeDTO.getWorkTimes())
                .build();
    }

    default ATM toAtm(AtmDTO atmDTO) {
        return ATM.builder()
                .bankCode(atmDTO.getBankCode())
                .name(atmDTO.getName())
                .city(atmDTO.getCity())
                .state(atmDTO.getState())
                .street(atmDTO.getStreet())
                .workTimes(atmDTO.getWorkTimes())
                .build();
    }

    default Bank toBank(BankUpdateDTO bankUpdateDTO) {
        return Bank.builder()
                .bankName(bankUpdateDTO.getBankName())
                .state(bankUpdateDTO.getState())
                .city(bankUpdateDTO.getCity())
                .street(bankUpdateDTO.getStreet())
                .build();
    }

    default Page<BankDTO> toBankDTOs(Page<Bank> bankPage) {
        return bankPage.map(this::toBankDto);
    }
}
