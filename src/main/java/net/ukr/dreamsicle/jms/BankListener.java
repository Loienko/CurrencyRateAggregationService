package net.ukr.dreamsicle.jms;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import net.ukr.dreamsicle.dto.bank.BankDTO;
import net.ukr.dreamsicle.dto.bank.BankMapper;
import net.ukr.dreamsicle.dto.gbsr.BankData;
import net.ukr.dreamsicle.dto.gbsr.BankDataMapper;
import net.ukr.dreamsicle.service.BankService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@AllArgsConstructor
@Component
public class BankListener {
    private final BankService bankService;
    private final BankDataMapper bankDataMapper;
    private final BankMapper bankMapper;

    @JmsListener(destination = "${app.bankQueueServer}")
    public void receive(String data) throws IOException {
        BankDTO bankDTO = bankDataMapper.bankDataToBankDto(new ObjectMapper().readValue(data, BankData.class), BankDTO.builder().build());
        bankService.bankDataByBankCode(bankDTO).ifPresentOrElse(
                bank -> bankService.update(bank.getId(), bankMapper.toBankUpdateDTO(bankDTO)),
                () -> bankService.create(bankDTO)
        );
    }
}
