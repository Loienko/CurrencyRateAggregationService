package net.ukr.dreamsicle.util.gbrs;


import net.ukr.dreamsicle.dto.atm.AtmDTO;
import net.ukr.dreamsicle.dto.bank.BankDTO;
import net.ukr.dreamsicle.dto.gbsr.BankDataDTO;
import net.ukr.dreamsicle.dto.office.OfficeDTO;
import net.ukr.dreamsicle.dto.product.ProductDTO;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class BankDataProvider {
    public static final ObjectId ID = new ObjectId("5dde29e7c24a7c3eecef4919");
    public static final String NAME = "PrivatBanks";
    public static final String BANK_CODE = "654321";
    public static final String IBAN = "77685bd8fa99fs4a4cgacf5ae4426";
    public static final String STATE = "Germany";
    public static final String CITY = "Berlin";
    public static final String STREET = "Street";
    public static final ProductDTO PRODUCT_DTO = ProductDTO.builder().id(ID.toHexString()).bankCode(BANK_CODE).type("").description("").build();
    public static final OfficeDTO OFFICE_DTO = OfficeDTO.builder().id(ID.toHexString()).bankCode(BANK_CODE).name("").city("").state("").street("").workTimes(new HashSet<>()).build();
    public static final AtmDTO ATM_DTO = AtmDTO.builder().id(ID.toHexString()).bankCode(BANK_CODE).name("").city("").state("").street("").workTimes(new HashSet<>()).build();
    public static final List<ProductDTO> PRODUCT_DTO_LIST = new ArrayList<>() {{
        add(PRODUCT_DTO);
    }};
    public static final List<OfficeDTO> OFFICE_DTO_LIST = new ArrayList<>() {{
        add(OFFICE_DTO);
    }};
    public static final List<AtmDTO> ATM_DTO_LIST = new ArrayList<>() {{
        add(ATM_DTO);
    }};

    public static String BANK_DATA_STRING_JSON_FORMAT = "{" +
            "\n\t\"bankName\": \"" + NAME + "\"," +
            "\n\t\"bankCode\": \"" + BANK_CODE + "\"," +
            "\n\t\"iban\": \"" + IBAN + "\"," +
            "\n\t\"countryName\": \"" + STATE + "\"," +
            "\n\t\"cityName\": \"" + CITY + "\"," +
            "\n\t\"streetInformation\": \"" + STREET + "\"" +
            "\n}";

    public static BankDataDTO getBankDataProvider() {
        return BankDataDTO.builder()
                .bankName(NAME)
                .bankCode(BANK_CODE)
                .iban(IBAN)
                .countryName(STATE)
                .cityName(CITY)
                .streetInformation(STREET)
                .build();
    }

    public static BankDTO getBankDtoProvider() {
        return BankDTO.builder()
                .id(ID.toHexString())
                .bankName(NAME)
                .bankCode(BANK_CODE)
                .iban(IBAN)
                .state(STATE)
                .city(CITY)
                .street(STREET)
                .products(PRODUCT_DTO_LIST)
                .offices(OFFICE_DTO_LIST)
                .atms(ATM_DTO_LIST)
                .build();
    }
}
