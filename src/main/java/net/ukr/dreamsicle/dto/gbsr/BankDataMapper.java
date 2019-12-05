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
    default BankDTO bankDataToBankDto(BankData bankData, BankDTO bankDTO) {
        if (bankData == null) {
            return null;
        }

        if (bankData.getBankName() != null) {
            bankDTO.setBankName(bankData.getBankName());
        }

        if (bankData.getIban() != null) {
            bankDTO.setIban(bankData.getIban());
        }

        if (bankData.getBankCode() != null) {
            bankDTO.setBankCode(bankData.getBankCode());
        } else {
            return null;
        }

        if (bankData.getCountryName() != null) {
            bankDTO.setState(bankData.getCountryName());
        }

        if (bankData.getCityName() != null) {
            bankDTO.setCity(bankData.getCityName());
        }

        if (bankData.getStreetInformation() != null) {
            bankDTO.setStreet(bankData.getStreetInformation());
        }

        bankDTO.setOffices(Arrays.asList(OfficeDTO.builder().bankCode(bankData.getBankCode()).state("").city("").street("").workTimes(new HashSet<>()).build()));
        bankDTO.setProducts(Arrays.asList(ProductDTO.builder().bankCode(bankData.getBankCode()).type("").description("").build()));
        bankDTO.setAtms(Arrays.asList(AtmDTO.builder().bankCode(bankData.getBankCode()).state("").city("").street("").workTimes(new HashSet<>()).build()));
        return bankDTO;
    }
}
