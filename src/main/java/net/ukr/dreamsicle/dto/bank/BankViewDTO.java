package net.ukr.dreamsicle.dto.bank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankViewDTO {

    private String bankName;
    private String state;
    private String city;
    private String street;
}
