package net.ukr.dreamsicle.util.office;

import net.ukr.dreamsicle.dto.office.OfficeDTO;
import net.ukr.dreamsicle.model.office.Office;
import net.ukr.dreamsicle.model.work.WorkTimes;
import org.bson.types.ObjectId;

import java.time.DayOfWeek;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class OfficeProvider {
    public static final ObjectId ID = new ObjectId("5dde29e7c24a7c3eecef4912");
    public static final String NAME = "Pumbs";
    public static final String BANK_CODE = "123456";
    public static final String STATE = "Germany";
    public static final String CITY = "Berlin";
    public static final String STREET = "Street";
    public static final String START_WORK = "9 AM";
    public static final String END_WORK = "7 PM";
    public static final Set<WorkTimes> WORK_TIMES = new HashSet<>(Collections.singletonList(WorkTimes.builder().dayOfWeek(DayOfWeek.MONDAY).startWork(START_WORK).endWork(END_WORK).build()));

    public static Office getOfficeProvider() {
        return Office.builder()
                .bankCode(BANK_CODE)
                .name(NAME)
                .state(STATE)
                .city(CITY)
                .street(STREET)
                .workTimes(WORK_TIMES)
                .build();
    }

    public static OfficeDTO getOfficeDTOProvider() {
        return OfficeDTO.builder()
                .id(ID.toHexString())
                .bankCode(BANK_CODE)
                .name(NAME)
                .state(STATE)
                .city(CITY)
                .street(STREET)
                .workTimes(WORK_TIMES)
                .build();
    }
}
