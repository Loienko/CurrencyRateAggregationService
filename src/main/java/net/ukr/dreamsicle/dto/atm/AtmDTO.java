package net.ukr.dreamsicle.dto.atm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ukr.dreamsicle.model.bank.Bank;
import net.ukr.dreamsicle.model.work.WorkTimes;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtmDTO {
    private String id;

    private String name;
    private String city;
    private String state;
    private String street;
    private Set<WorkTimes> workTimes;
    private Bank bank;
}
