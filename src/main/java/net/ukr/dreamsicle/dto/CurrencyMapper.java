package net.ukr.dreamsicle.dto;

import net.ukr.dreamsicle.model.Currency;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
@Service
public interface CurrencyMapper {

    CurrencyDTO toCurrencyDto(Currency currency);

    List<CurrencyDTO> toCurrencyDTOs(Page<Currency> currencies);

    Currency toCurrency(CurrencyDTO currencyDTO);
}
