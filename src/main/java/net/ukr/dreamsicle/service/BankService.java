package net.ukr.dreamsicle.service;

import lombok.AllArgsConstructor;
import net.ukr.dreamsicle.dto.bank.BankDTO;
import net.ukr.dreamsicle.dto.bank.BankViewDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BankService {

    public Page<BankDTO> getAll(Pageable pageable) {
        return null;
    }

    public BankDTO findById(long id) {
        return null;
    }

    public BankViewDTO createBank(BankViewDTO bankViewDTO) {
        return null;
    }

    public BankViewDTO updateBank(long id, BankViewDTO bankViewDTO) {
        return null;
    }


    public Page<BankDTO> search(Pageable page) {
        return null;
    }
}
