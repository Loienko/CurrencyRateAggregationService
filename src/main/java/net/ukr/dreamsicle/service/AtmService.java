package net.ukr.dreamsicle.service;

import lombok.AllArgsConstructor;
import net.ukr.dreamsicle.dto.atm.AtmDTO;
import net.ukr.dreamsicle.dto.atm.AtmMapper;
import net.ukr.dreamsicle.dto.bank.BankDTO;
import net.ukr.dreamsicle.exception.ResourceNotFoundException;
import net.ukr.dreamsicle.repository.AtmRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AtmService {
    private AtmRepository atmRepository;
    private AtmMapper atmMapper;

    public Page<AtmDTO> getAll(Pageable pageable) {
        return null;
    }

    public AtmDTO findById(long id) {
        return null;
    }

    public AtmDTO create(AtmDTO atmDTO) {
        return null;
    }

    public AtmDTO update(long id, AtmDTO atmDTO) {
        return null;
    }
}
