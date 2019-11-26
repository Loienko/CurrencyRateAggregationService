package net.ukr.dreamsicle.model.work;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ukr.dreamsicle.util.Constants;
import net.ukr.dreamsicle.util.ConstantsRegex;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.DayOfWeek;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkTimes {

    private DayOfWeek dayOfWeek;

    @NotBlank(message = Constants.FILL_THE_START_WORK)
    @Pattern(regexp = ConstantsRegex.INPUT_WORK_TIMES_REGEX, message = Constants.VALID_DATA_FOR_START_WORK)
    private String startWork;

    @NotBlank(message = Constants.FILL_THE_END_WORK)
    @Pattern(regexp = ConstantsRegex.INPUT_WORK_TIMES_REGEX, message = Constants.VALID_DATA_FOR_END_WORK)
    private String endWork;
}
