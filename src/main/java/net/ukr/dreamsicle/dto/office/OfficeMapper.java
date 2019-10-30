package net.ukr.dreamsicle.dto.office;

import net.ukr.dreamsicle.model.office.Office;
import org.springframework.data.domain.Page;

public interface OfficeMapper {

    OfficeDTO toOfficeDto(Office office);

    Office toOffice(OfficeDTO officeDTO);

    default Page<OfficeDTO> toOfficeDTOs(Page<Office> officePage) {
        return officePage.map(this::toOfficeDto);
    }
}