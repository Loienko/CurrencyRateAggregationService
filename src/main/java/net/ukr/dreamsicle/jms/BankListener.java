package net.ukr.dreamsicle.jms;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import net.ukr.dreamsicle.dto.bank.BankDTO;
import net.ukr.dreamsicle.dto.bank.BankMapper;
import net.ukr.dreamsicle.dto.gbsr.BankData;
import net.ukr.dreamsicle.dto.gbsr.BankDataMapper;
import net.ukr.dreamsicle.repository.BankRepository;
import net.ukr.dreamsicle.service.BankService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class BankListener {
    private final BankService bankService;
    private final BankDataMapper bankDataMapper;
    private final BankRepository bankRepository;
    private final BankMapper bankMapper;

    @JmsListener(destination = "${app.bankQueueServer}")
    public void receive(String data) {
        var bankDTO = bankDataMapper.bankDataToBankDto(new Gson().fromJson(data, BankData.class), BankDTO.builder().build());
        bankRepository.findBankByBankCode(bankDTO.getBankCode()).ifPresentOrElse(
                bank -> bankService.update(bank.getId(), bankMapper.toBankUpdateDTO(bankDTO)),
                () -> bankService.create(bankDTO)
        );
    }
}
