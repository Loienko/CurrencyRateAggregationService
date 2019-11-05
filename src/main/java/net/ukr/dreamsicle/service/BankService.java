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

    //TODO
    //will be implemented later
    public Page<BankDTO> getAll(Pageable pageable) {
        return null;
    }

    //TODO
    //will be implemented later
    public BankDTO findById(long id) {
        return null;
    }

    //TODO
    //will be implemented later
    public BankViewDTO createBank(BankViewDTO bankViewDTO) {
        return null;
    }

    //TODO
    //will be implemented later
    public BankViewDTO updateBank(long id, BankViewDTO bankViewDTO) {
        return null;
    }

    //TODO
    //will be implemented later
    public Page<BankDTO> search(Pageable page) {
        return null;
    }
}
