package net.ukr.dreamsicle.model.atm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ukr.dreamsicle.model.work.WorkTimes;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "atm")
public class ATM {

    @Id
    private String id;
    private String bankCode;
    private String name;
    private String state;
    private String city;
    private String street;
    private Set<WorkTimes> workTimes;
}
