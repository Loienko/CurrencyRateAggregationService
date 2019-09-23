package net.ukr.dreamsicle.dto;

import net.ukr.dreamsicle.model.Currency;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Mapper
@Service
public interface CurrencyMapper {

    CurrencyDTO toCurrencyDto(Currency currency);

    List<CurrencyDTO> toCurrencyDTOs(List<Currency> currencies);

    Currency toCurrency(CurrencyDTO currencyDTO);

    default <T> T unwrapOptional(Optional<T> optional) {
        return optional.orElse(null);
    }
}
