package net.ukr.dreamsicle.model.office;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ukr.dreamsicle.model.work.WorkTimes;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "office")
public class Office {

    @Id
    private ObjectId id;
    private String bankCode;
    private String name;
    private String state;
    private String city;
    private String street;
    private Set<WorkTimes> workTimes;
}
