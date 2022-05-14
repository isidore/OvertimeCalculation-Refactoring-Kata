package codingdojo.overtimecalculator;

import codingdojo.Assignment;
import codingdojo.BigDecimalUtils;
import codingdojo.Briefing;
import codingdojo.Overtime;

import java.math.BigDecimal;

import static codingdojo.BigDecimalUtils.*;

public class NoOvertime implements OvertimeCalculator {
    @Override
    public boolean isValidFor(BigDecimal hoursOvertimeTotal, Assignment assignment, Briefing briefing) {
        return isALessThanOrEqualToB(hoursOvertimeTotal, BigDecimal.ZERO);
    }

    @Override
    public Overtime calculateOvertime(BigDecimal hoursOvertimeTotal, Assignment assignment, Briefing briefing) {
        return new Overtime(BigDecimal.ZERO, BigDecimal.ZERO);
    }
}
