package net.ukr.dreamsicle.dto.bank;

import net.ukr.dreamsicle.model.bank.Bank;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Mapper
@Service
public interface BankMapper {

    BankDTO toBankDto(Bank bank);

    Bank toBank(BankDTO bankDTO);

    BankViewDTO toBankViewDto(Bank bank);

    Bank toBank(BankViewDTO bankViewDTO);

    default Page<BankDTO> toBankDTOs(Page<Bank> bankPage) {
        return bankPage.map(this::toBankDto);
    }
}
