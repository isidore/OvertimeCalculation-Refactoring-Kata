package codingdojo;

import codingdojo.overtimecalculator.OvertimeCalculator;

import java.math.BigDecimal;

import static codingdojo.CompensationCalculator.MAX_OVERTIME_HOURS_RATE_1;
import static codingdojo.CompensationCalculator.isApplesauce;

public class UnderMaxOvertime implements OvertimeCalculator {
    @Override
    public boolean isValidFor(BigDecimal hoursOvertimeTotal, Assignment assignment, Briefing briefing) {
        return !isApplesauce(assignment, briefing) && hoursOvertimeTotal.compareTo(MAX_OVERTIME_HOURS_RATE_1) < 1;
    }

    @Override
    public Overtime calculateOvertime(BigDecimal hoursOvertimeTotal, Assignment assignment, Briefing briefing) {
        return new Overtime(hoursOvertimeTotal, BigDecimal.ZERO);
    }
}
