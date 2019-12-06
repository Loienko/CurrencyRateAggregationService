package net.ukr.dreamsicle.jms;

import net.ukr.dreamsicle.dto.bank.BankDTO;
import net.ukr.dreamsicle.dto.bank.BankMapper;
import net.ukr.dreamsicle.dto.bank.BankUpdateDTO;
import net.ukr.dreamsicle.dto.gbsr.BankDataDTO;
import net.ukr.dreamsicle.dto.gbsr.BankDataMapper;
import net.ukr.dreamsicle.exception.ResourceNotFoundException;
import net.ukr.dreamsicle.model.bank.Bank;
import net.ukr.dreamsicle.service.BankService;
import net.ukr.dreamsicle.util.bank.BankProvider;
import net.ukr.dreamsicle.util.gbrs.BankDataProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Optional;

import static net.ukr.dreamsicle.util.bank.BankProvider.getBankUpdateDtoProvider;
import static net.ukr.dreamsicle.util.gbrs.BankDataProvider.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class BankListenerTest {

    @InjectMocks
    private BankListener bankListener;
    @Mock
    private BankService bankService;
    @Mock
    private BankDataMapper bankDataMapper;
    @Mock
    private BankMapper bankMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUpdateReceiveData() throws IOException {
        Bank bank = BankProvider.getBankProvider();
        BankDataDTO bankDataDTO = getBankDataProvider();
        BankUpdateDTO bankUpdateDTO = getBankUpdateDtoProvider();
        BankDTO bankDTO = BankDataProvider.getBankDtoProvider();
        bank.setId(ID);
        when(bankDataMapper.bankDataToBankDto(bankDataDTO, BankDTO.builder().build())).thenReturn(bankDTO);
        when(bankService.bankDataByBankCode(bankDTO)).thenReturn(Optional.of(bank));
        when(bankMapper.toBankUpdateDTO(bankDTO)).thenReturn(bankUpdateDTO);
        when(bankService.update(bank.getId(), bankUpdateDTO)).thenReturn(bankDTO);

        bankListener.receive(BANK_DATA_STRING_JSON_FORMAT);

        verify(bankService).update(ID, bankUpdateDTO);
        verify(bankService, never()).create(bankDTO);
    }

    @Test
    public void testUpdateReceiveDataThrowResourceNotFoundExceptionForTryToUpdateData() {
        Bank bank = BankProvider.getBankProvider();
        BankUpdateDTO bankUpdateDTO = getBankUpdateDtoProvider();
        BankDTO bankDTO = BankDataProvider.getBankDtoProvider();
        bank.setId(ID);
        when(bankDataMapper.bankDataToBankDto(getBankDataProvider(), BankDTO.builder().build())).thenReturn(bankDTO);
        when(bankService.bankDataByBankCode(bankDTO)).thenReturn(Optional.of(bank));
        when(bankMapper.toBankUpdateDTO(bankDTO)).thenReturn(bankUpdateDTO);
        when(bankService.update(bank.getId(), bankUpdateDTO)).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> bankListener.receive(BANK_DATA_STRING_JSON_FORMAT));
    }

    @Test
    public void testReceiveDataThrowResourceNotFoundExceptionForTryToFindDataByBankCode() {
        BankDTO bankDTO = BankDataProvider.getBankDtoProvider();
        when(bankDataMapper.bankDataToBankDto(getBankDataProvider(), BankDTO.builder().build())).thenReturn(bankDTO);
        when(bankService.bankDataByBankCode(bankDTO)).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> bankListener.receive(BANK_DATA_STRING_JSON_FORMAT));
    }

    @Test
    public void testCreateReceiveData() throws IOException {
        Bank bank = BankProvider.getBankProvider();
        BankUpdateDTO bankUpdateDTO = getBankUpdateDtoProvider();
        BankDTO bankDTO = BankDataProvider.getBankDtoProvider();
        bank.setId(ID);
        when(bankDataMapper.bankDataToBankDto(getBankDataProvider(), BankDTO.builder().build())).thenReturn(bankDTO);
        when(bankService.bankDataByBankCode(bankDTO)).thenReturn(Optional.empty());
        when(bankService.create(bankDTO)).thenReturn(bankDTO);

        bankListener.receive(BANK_DATA_STRING_JSON_FORMAT);

        verify(bankService).create(bankDTO);
        verify(bankService, never()).update(ID, bankUpdateDTO);
    }

    @Test
    public void testCreateReceiveDataThrowResourceNotFoundExceptionForTryToCreateData() {
        BankDTO bankDTO = BankDataProvider.getBankDtoProvider();
        when(bankDataMapper.bankDataToBankDto(getBankDataProvider(), BankDTO.builder().build())).thenReturn(bankDTO);
        when(bankService.bankDataByBankCode(bankDTO)).thenReturn(Optional.empty());
        when(bankService.create(bankDTO)).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> bankListener.receive(BANK_DATA_STRING_JSON_FORMAT));
    }
}
