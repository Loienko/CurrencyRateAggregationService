package net.ukr.dreamsicle.dto.gbsr;

import net.ukr.dreamsicle.dto.atm.AtmDTO;
import net.ukr.dreamsicle.dto.bank.BankDTO;
import net.ukr.dreamsicle.dto.office.OfficeDTO;
import net.ukr.dreamsicle.dto.product.ProductDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Mapper
@Service
public interface BankDataMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    default BankDTO bankDataToBankDto(BankDataDTO bankDataDTO, BankDTO bankDTO) {
        if (bankDataDTO == null) {
            return null;
        }

        if (bankDataDTO.getBankCode() == null) {
            return null;
        }

        if (bankDataDTO.getBankName() != null) {
            bankDTO.setBankName(bankDataDTO.getBankName());
        }

        if (bankDataDTO.getIban() != null) {
            bankDTO.setIban(bankDataDTO.getIban());
        }

        if (bankDataDTO.getCountryName() != null) {
            bankDTO.setState(bankDataDTO.getCountryName());
        }

        if (bankDataDTO.getCityName() != null) {
            bankDTO.setCity(bankDataDTO.getCityName());
        }

        if (bankDataDTO.getStreetInformation() != null) {
            bankDTO.setStreet(bankDataDTO.getStreetInformation());
        }

        bankDTO.setOffices(Arrays.asList(OfficeDTO.builder().bankCode(bankDataDTO.getBankCode()).state("").city("").street("").workTimes(new HashSet<>()).build()));
        bankDTO.setProducts(Arrays.asList(ProductDTO.builder().bankCode(bankDataDTO.getBankCode()).type("").description("").build()));
        bankDTO.setAtms(Arrays.asList(AtmDTO.builder().bankCode(bankDataDTO.getBankCode()).state("").city("").street("").workTimes(new HashSet<>()).build()));
        return bankDTO;
    }
}
