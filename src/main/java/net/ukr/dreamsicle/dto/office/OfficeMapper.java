package net.ukr.dreamsicle.dto.office;

import net.ukr.dreamsicle.model.office.Office;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Mapper
@Service
public interface OfficeMapper {

    OfficeDTO toOfficeDto(Office office);

    Office toOffice(OfficeDTO officeDTO);

    default Page<OfficeDTO> toOfficeDTOs(Page<Office> officePage) {
        return officePage.map(this::toOfficeDto);
    }
}
