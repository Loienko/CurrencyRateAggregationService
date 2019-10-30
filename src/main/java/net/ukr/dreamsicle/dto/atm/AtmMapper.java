package net.ukr.dreamsicle.dto.atm;

import net.ukr.dreamsicle.model.atm.ATM;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Mapper
@Service
public interface AtmMapper {

    AtmDTO toAtmDto(ATM atm);

    ATM toAtm(AtmDTO atmDTO);

    default Page<AtmDTO> toAtmDTOs(Page<ATM> atmPage) {
        return atmPage.map(this::toAtmDto);
    }
}