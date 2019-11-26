package net.ukr.dreamsicle.service;

import lombok.RequiredArgsConstructor;
import net.ukr.dreamsicle.dto.partner.PartnerDTO;
import net.ukr.dreamsicle.model.partners.Partner;
import net.ukr.dreamsicle.repository.PartnerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(isolation = Isolation.SERIALIZABLE)
public class PartnerService {

    private final PartnerRepository partnerRepository;

    //TODO
    // Will implement later
    public Page<PartnerDTO> getAll(Pageable page) {
        return null;
    }

    //TODO
    // Will implement later
    public PartnerDTO findById(String id) {
        return null;
    }

    //TODO
    // Will implement later
    public PartnerDTO create(PartnerDTO partnerDTO) {
        return null;
    }

    //TODO
    // Will implement later
    public PartnerDTO update(String id, PartnerDTO partnerDTO) {
        return null;
    }

    //TODO
    // Will implement later
    public List<Partner> createPartners(List<Partner> partners) {
        return partners.stream()
                .map(this::savePartner)
                .collect(Collectors.toList());
    }

    //TODO
    // Will implement later
    private Partner savePartner(Partner partner) {
        return partnerRepository.save(Partner.builder()
                .bankList(partner.getBankList())
                .atmList(partner.getAtmList())
                .build());
    }
}
