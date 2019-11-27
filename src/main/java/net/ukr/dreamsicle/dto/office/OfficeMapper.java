package net.ukr.dreamsicle.dto.office;

import net.ukr.dreamsicle.model.office.Office;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Mapper
@Service
public interface OfficeMapper {

    default OfficeDTO toOfficeDto(Office office) {
        return OfficeDTO.builder()
                .id(office.getId().toHexString())
                .bankCode(office.getBankCode())
                .name(office.getName())
                .city(office.getCity())
                .state(office.getState())
                .street(office.getStreet())
                .workTimes(office.getWorkTimes())
                .build();
    }

    default Office toOffice(OfficeDTO officeDTO) {
        return Office.builder()
                .bankCode(officeDTO.getBankCode())
                .name(officeDTO.getName())
                .city(officeDTO.getCity())
                .state(officeDTO.getState())
                .street(officeDTO.getStreet())
                .workTimes(officeDTO.getWorkTimes())
                .build();
    }

    default Page<OfficeDTO> toOfficeDTOs(Page<Office> officePage) {
        return officePage.map(this::toOfficeDto);
    }
}
