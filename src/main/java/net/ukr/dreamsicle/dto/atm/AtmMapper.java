package net.ukr.dreamsicle.dto.atm;

import net.ukr.dreamsicle.model.atm.ATM;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Mapper
@Service
public interface AtmMapper {

    default AtmDTO toAtmDto(ATM atm) {
        return AtmDTO.builder()
                .id(atm.getId().toHexString())
                .bankCode(atm.getBankCode())
                .name(atm.getName())
                .city(atm.getCity())
                .state(atm.getState())
                .street(atm.getStreet())
                .workTimes(atm.getWorkTimes())
                .build();
    }

    default ATM toAtm(AtmDTO atmDTO) {
        return ATM.builder()
                .bankCode(atmDTO.getBankCode())
                .name(atmDTO.getName())
                .city(atmDTO.getCity())
                .state(atmDTO.getState())
                .street(atmDTO.getStreet())
                .workTimes(atmDTO.getWorkTimes())
                .build();
    }

    default Page<AtmDTO> toAtmDTOs(Page<ATM> atmPage) {
        return atmPage.map(this::toAtmDto);
    }
}
