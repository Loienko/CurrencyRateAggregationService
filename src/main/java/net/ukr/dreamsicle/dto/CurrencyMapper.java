package net.ukr.dreamsicle.dto;

import net.ukr.dreamsicle.model.Currency;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Mapper
@Service
public interface CurrencyMapper {

    CurrencyDTO toCurrencyDto(Currency currency);

    Currency toCurrency(CurrencyDTO currencyDTO);

    default Page<CurrencyDTO> toCurrencyDTOs(Page<Currency> currencyPage) {
        return currencyPage.map(this::toCurrencyDto);
    }
}
