package net.ukr.dreamsicle.dto.bank;

import net.ukr.dreamsicle.model.bank.Bank;
import org.springframework.data.domain.Page;

public interface BankMapper {

    BankDTO toBankDto(Bank bank);

    Bank toBank(BankDTO bankDTO);

    BankCreateDTO toBankCreateDto(Bank bank);

    Bank toBank(BankCreateDTO bankCreateDTO);

    default Page<BankDTO> toBankDTOs(Page<Bank> bankPage) {
        return bankPage.map(this::toBankDto);
    }
}
