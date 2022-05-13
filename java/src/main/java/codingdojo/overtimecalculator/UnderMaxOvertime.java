package codingdojo.overtimecalculator;

import codingdojo.Assignment;
import codingdojo.Briefing;
import codingdojo.Overtime;
import codingdojo.overtimecalculator.OvertimeCalculator;

import java.math.BigDecimal;

import static codingdojo.CompensationCalculator.MAX_OVERTIME_HOURS_RATE_1;

public class UnderMaxOvertime implements OvertimeCalculator {
    @Override
    public boolean isValidFor(BigDecimal hoursOvertimeTotal, Assignment assignment, Briefing briefing) {
        return hoursOvertimeTotal.compareTo(MAX_OVERTIME_HOURS_RATE_1) < 1;
    }

    @Override
    public Overtime calculateOvertime(BigDecimal hoursOvertimeTotal, Assignment assignment, Briefing briefing) {
        return new Overtime(hoursOvertimeTotal, BigDecimal.ZERO);
    }
}
