package codingdojo.overtimecalculator;

import codingdojo.Assignment;
import codingdojo.Briefing;
import codingdojo.CompensationCalculator;
import codingdojo.Overtime;

import java.math.BigDecimal;

public class NoOvertime implements OvertimeCalculator {
    @Override
    public boolean isValidFor(BigDecimal hoursOvertimeTotal, Assignment assignment, Briefing briefing) {
        return !CompensationCalculator.isApplesauce(assignment, briefing) && hoursOvertimeTotal.compareTo(BigDecimal.ZERO) < 1;
    }

    @Override
    public Overtime calculateOvertime(BigDecimal hoursOvertimeTotal, Assignment assignment, Briefing briefing) {
        return new Overtime(BigDecimal.ZERO, BigDecimal.ZERO);
    }
}
