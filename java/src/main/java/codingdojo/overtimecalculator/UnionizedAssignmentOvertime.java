package codingdojo.overtimecalculator;

import codingdojo.Assignment;
import codingdojo.Briefing;
import codingdojo.CompensationCalculator;
import codingdojo.Overtime;

import java.math.BigDecimal;

import static codingdojo.CompensationCalculator.*;

public class UnionizedAssignmentOvertime implements OvertimeCalculator {
    @Override
    public boolean isValidFor(BigDecimal hoursOvertimeTotal, Assignment assignment, Briefing briefing) {
        return !isApplesauce(assignment, briefing) && assignment.isUnionized();
    }

    @Override
    public Overtime calculateOvertime(BigDecimal hoursOvertimeTotal, Assignment assignment, Briefing briefing) {
        BigDecimal threshold = CompensationCalculator.calculateThreshold(assignment, THRESHOLD_OVERTIME_HOURS_RATE_2);
        var hoursOvertimeRate2 = hoursOvertimeTotal.subtract(MAX_OVERTIME_HOURS_RATE_1);
        hoursOvertimeRate2 = hoursOvertimeRate2.min(threshold);
        return new Overtime(MAX_OVERTIME_HOURS_RATE_1, hoursOvertimeRate2);
    }
}
