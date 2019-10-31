package net.ukr.dreamsicle.dto.partner;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import net.ukr.dreamsicle.model.atm.ATM;
import net.ukr.dreamsicle.model.bank.Bank;

import java.util.List;

@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PartnerDTO {

    private List<Bank> bankList;
    private List<ATM> atmList;
}
