package net.ukr.dreamsicle.model.partners;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "partners")
public class Partner {

    @Id
    private String id;
    //TODO
    // Will implement later
//    private List<Bank> bankList;
//    private List<ATM> atmList;
    //TODO
    // Just for test app
    private String bankList;
    private String atmList;
}
