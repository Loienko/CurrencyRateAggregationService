package net.ukr.dreamsicle.model.work;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkTimes {

    private DayOfWeek dayOfWeek;
    private String startWork;
    private String endWork;
}
