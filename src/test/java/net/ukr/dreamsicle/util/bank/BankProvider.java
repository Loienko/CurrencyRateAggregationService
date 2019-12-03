package net.ukr.dreamsicle.util.bank;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import net.ukr.dreamsicle.dto.atm.AtmDTO;
import net.ukr.dreamsicle.dto.bank.BankDTO;
import net.ukr.dreamsicle.dto.bank.BankUpdateDTO;
import net.ukr.dreamsicle.dto.office.OfficeDTO;
import net.ukr.dreamsicle.dto.product.ProductDTO;
import net.ukr.dreamsicle.model.atm.ATM;
import net.ukr.dreamsicle.model.bank.Bank;
import net.ukr.dreamsicle.model.office.Office;
import net.ukr.dreamsicle.model.product.Product;
import net.ukr.dreamsicle.model.product.TypeProduct;
import net.ukr.dreamsicle.model.work.WorkTimes;
import net.ukr.dreamsicle.modelQ.bank.QBank;
import org.bson.types.ObjectId;

import java.time.DayOfWeek;
import java.util.*;

public class BankProvider {
    public static final String SEARCH = "Ukraine";
    public static final ObjectId ID = new ObjectId("5dde29e7c24a7c3eecef4918");
    public static final String BANK_NAME = "PUMB";
    public static final String BANK_CODE = "123456";
    public static final String IBAN = "77685bd8-a99f-4a4c-acf5-e4426";
    public static final String STATE = "Ukraine";
    public static final String CITY = "Ukraine";
    public static final String STREET = "street";
    public static final String DESCRIPTION = "description";
    public static final Set<WorkTimes> WORK_TIMES = new HashSet<>(Arrays.asList(new WorkTimes(DayOfWeek.MONDAY, "9AM", "7PM")));
    public static final Product PRODUCT = Product.builder().bankCode(BANK_CODE).type(TypeProduct.CASH_COLLECTION).description(DESCRIPTION).build();
    public static final Product PRODUCT_ID = Product.builder().id(ID).bankCode(BANK_CODE).type(TypeProduct.CASH_COLLECTION).description(DESCRIPTION).build();
    public static final List<Product> PRODUCT_LIST = new ArrayList<Product>() {{
        add(PRODUCT);
        add(PRODUCT);
    }};
    public static final List<Product> PRODUCT_LIST_WITH_ID = new ArrayList<Product>() {{
        add(PRODUCT_ID);
    }};
    public static final ProductDTO PRODUCT_DTO = ProductDTO.builder().id(ID.toHexString()).bankCode(BANK_CODE).type(TypeProduct.CASH_COLLECTION.getName()).description(DESCRIPTION).build();
    public static final List<ProductDTO> PRODUCT_DTO_LIST = new ArrayList<ProductDTO>() {{
        add(PRODUCT_DTO);
        add(PRODUCT_DTO);
    }};
    public static final Office OFFICE = Office.builder().bankCode(BANK_CODE).name(BANK_NAME).city(CITY).state(STATE).street(STREET).workTimes(WORK_TIMES).build();
    public static final Office OFFICE_ID = Office.builder().id(ID).bankCode(BANK_CODE).name(BANK_NAME).city(CITY).state(STATE).street(STREET).workTimes(WORK_TIMES).build();
    public static final List<Office> OFFICE_LIST = new ArrayList<Office>() {{
        add(OFFICE);
        add(OFFICE);
    }};
    public static final List<Office> OFFICE_LIST_WITH_ID = new ArrayList<Office>() {{
        add(OFFICE_ID);
    }};
    public static final OfficeDTO OFFICE_DTO = OfficeDTO.builder().id(ID.toHexString()).bankCode(BANK_CODE).name(BANK_NAME).city(CITY).state(STATE).street(STREET).workTimes(WORK_TIMES).build();
    public static final List<OfficeDTO> OFFICE_DTO_LIST = new ArrayList<OfficeDTO>() {{
        add(OFFICE_DTO);
        add(OFFICE_DTO);
    }};
    public static final ATM ATMs = ATM.builder().bankCode(BANK_CODE).name(BANK_NAME).city(CITY).state(STATE).street(STREET).workTimes(WORK_TIMES).build();
    public static final ATM ATMs_ID = ATM.builder().id(ID).bankCode(BANK_CODE).name(BANK_NAME).city(CITY).state(STATE).street(STREET).workTimes(WORK_TIMES).build();
    public static final List<ATM> ATM_LIST = new ArrayList<ATM>() {{
        add(ATMs);
        add(ATMs);
    }};
    public static final List<ATM> ATM_LIST_WITH_ID = new ArrayList<ATM>() {{
        add(ATMs_ID);
    }};
    public static final AtmDTO ATM_DTO = AtmDTO.builder().id(ID.toHexString()).bankCode(BANK_CODE).name(BANK_NAME).city(CITY).state(STATE).street(STREET).workTimes(WORK_TIMES).build();
    public static final List<AtmDTO> ATM_DTO_LIST = new ArrayList<AtmDTO>() {{
        add(ATM_DTO);
        add(ATM_DTO);
    }};

    public static Bank getBankProvider() {
        return Bank.builder()
                .bankName(BANK_NAME)
                .bankCode(BANK_CODE)
                .iban(IBAN)
                .state(STATE)
                .city(CITY)
                .street(STREET)
                .products(PRODUCT_LIST)
                .offices(OFFICE_LIST)
                .atms(ATM_LIST)
                .build();
    }

    public static BankDTO getBankDtoProvider() {
        return BankDTO.builder()
                .id(ID.toHexString())
                .bankName(BANK_NAME)
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

    public static BankUpdateDTO getBankUpdateDtoProvider() {
        return BankUpdateDTO.builder()
                .id(ID.toHexString())
                .bankName(BANK_NAME)
                .state(STATE)
                .city(CITY)
                .street(STREET)
                .build();
    }

    public static Bank getBankProviderWithIdForAtmAndOfficeAndProduct() {
        return Bank.builder()
                .bankName(BANK_NAME)
                .bankCode(BANK_CODE)
                .iban(IBAN)
                .state(STATE)
                .city(CITY)
                .street(STREET)
                .products(PRODUCT_LIST_WITH_ID)
                .offices(OFFICE_LIST_WITH_ID)
                .atms(ATM_LIST_WITH_ID)
                .build();
    }

    public static BooleanBuilder getSearchPredicate(String search) {
        QBank bank = QBank.bank;

        BooleanExpression id = bank.id.containsIgnoreCase(search);
        BooleanExpression bankName = bank.bankName.containsIgnoreCase(search);
        BooleanExpression bankCode = bank.bankCode.containsIgnoreCase(search);
        BooleanExpression iban = bank.iban.containsIgnoreCase(search);
        BooleanExpression state = bank.state.containsIgnoreCase(search);
        BooleanExpression city = bank.city.containsIgnoreCase(search);
        BooleanExpression street = bank.street.containsIgnoreCase(search);

        return new BooleanBuilder().andAnyOf(id, bankName, bankCode, iban, state, city, street);
    }
}
