package codingdojo.overtimecalculator;

import codingdojo.Assignment;
import codingdojo.Briefing;
import codingdojo.Overtime;

import java.math.BigDecimal;

import static codingdojo.overtimecalculator.DoubleOvertime.WHEN_DOUBLE_OVERTIME_STARTS;

public class UnderMaxOvertime implements OvertimeCalculator {
    @Override
    public boolean isValidFor(BigDecimal hoursOvertimeTotal, Assignment assignment, Briefing briefing) {
        return hoursOvertimeTotal.compareTo(WHEN_DOUBLE_OVERTIME_STARTS) < 1;
    }

    @Override
    public Overtime calculateOvertime(BigDecimal hoursOvertimeTotal, Assignment assignment, Briefing briefing) {
        return new Overtime(hoursOvertimeTotal, BigDecimal.ZERO);
    }
}
