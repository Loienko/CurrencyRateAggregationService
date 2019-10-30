package net.ukr.dreamsicle.model.work;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;

@AllArgsConstructor
@NoArgsConstructor
public class WorkTimes {

    private DayOfWeek dayOfWeek;
    private String startWork;
    private String endWork;
}
