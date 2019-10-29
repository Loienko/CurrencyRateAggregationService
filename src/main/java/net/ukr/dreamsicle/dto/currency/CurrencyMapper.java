package net.ukr.dreamsicle.dto.currency;

import lombok.Lombok;
import net.ukr.dreamsicle.model.currency.Currency;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * The generation of a implementation of {@link CurrencyDTO} to {@link Currency} and back via MapStruct.
 * Used by {@link Lombok} to create template methods of an object like getters. setters, etc.
 *
 * @author yurii.loienko
 * @version 1.0
 */
@Mapper
@Service
public interface CurrencyMapper {

    CurrencyDTO toCurrencyDto(Currency currency);

    Currency toCurrency(CurrencyDTO currencyDTO);

    default Page<CurrencyDTO> toCurrencyDTOs(Page<Currency> currencyPage) {
        return currencyPage.map(this::toCurrencyDto);
    }
}
