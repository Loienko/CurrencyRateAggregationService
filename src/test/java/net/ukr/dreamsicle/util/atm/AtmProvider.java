package net.ukr.dreamsicle.util.atm;

import net.ukr.dreamsicle.dto.atm.AtmDTO;
import net.ukr.dreamsicle.model.atm.ATM;
import net.ukr.dreamsicle.model.work.WorkTimes;
import org.bson.types.ObjectId;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AtmProvider {
    public static final ObjectId ID = new ObjectId("5dde29e7c24a7c3eecef4919");
    public static final String NAME = "Pumbs";
    public static final String BANK_CODE = "123456";
    public static final String STATE = "Germany";
    public static final String CITY = "Berlin";
    public static final String STREET = "Street";
    public static final String START_WORK = "9 AM";
    public static final String END_WORK = "7 PM";
    public static final Set<WorkTimes> WORK_TIMES = new HashSet<>(Arrays.asList(WorkTimes.builder().dayOfWeek(DayOfWeek.MONDAY).startWork(START_WORK).endWork(END_WORK).build()));

    public static ATM getAtmProvider() {
        return ATM.builder()
                .bankCode(BANK_CODE)
                .name(NAME)
                .state(STATE)
                .city(CITY)
                .street(STREET)
                .workTimes(WORK_TIMES)
                .build();
    }

    public static AtmDTO getAtmDtoProvider() {
        return AtmDTO.builder()
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
