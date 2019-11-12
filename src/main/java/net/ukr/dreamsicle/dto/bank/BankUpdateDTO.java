package net.ukr.dreamsicle.dto.bank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankUpdateDTO {

    private String id;
    private String bankName;
    private String state;
    private String city;
    private String street;
}
