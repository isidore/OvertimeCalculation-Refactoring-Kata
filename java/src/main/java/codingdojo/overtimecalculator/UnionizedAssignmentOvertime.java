package codingdojo.overtimecalculator;

import codingdojo.Assignment;
import codingdojo.Briefing;
import codingdojo.Overtime;

import java.math.BigDecimal;
import java.time.Duration;

import static codingdojo.BigDecimalUtils.isALessThanB;
import static codingdojo.BigDecimalUtils.isALessThanOrEqualToB;
import static codingdojo.overtimecalculator.DoubleOvertime.WHEN_DOUBLE_OVERTIME_STARTS;

public class UnionizedAssignmentOvertime implements OvertimeCalculator {
    public static final int MAX_DOUBLE_OVERTIME_DOMESTIC = 6;
    private static final int MAX_DOUBLE_OVERTIME_FOREIGN = 4;
    
    @Override
    public boolean isValidFor(BigDecimal hoursOvertimeTotal, Assignment assignment, Briefing briefing) {
        var isNonExempt = !briefing.hbmo() && !briefing.watcode();
        var exceedsMaxOvertime = isALessThanB(WHEN_DOUBLE_OVERTIME_STARTS, hoursOvertimeTotal);
        return assignment.isUnionized() && isNonExempt && exceedsMaxOvertime;
    }

    @Override
    public Overtime calculateOvertime(BigDecimal hoursOvertimeTotal, Assignment assignment, Briefing briefing) {
        var doubleOvertime = hoursOvertimeTotal.subtract(DoubleOvertime.WHEN_DOUBLE_OVERTIME_STARTS);
        doubleOvertime = doubleOvertime.min(calculateMaxDoubleOvertimeAllowed(assignment, briefing));
        return new Overtime(DoubleOvertime.WHEN_DOUBLE_OVERTIME_STARTS, doubleOvertime);
    }

    public static BigDecimal calculateMaxDoubleOvertimeAllowed(Assignment assignment, Briefing briefing) {
        var max = briefing.foreign() ? MAX_DOUBLE_OVERTIME_FOREIGN : MAX_DOUBLE_OVERTIME_DOMESTIC;
        if (isALessThanOrEqualToB(assignment.duration(), Duration.ofHours(max))) {
            return BigDecimal.valueOf(assignment.duration().toHours());
        }
        return BigDecimal.valueOf(max);
    }
}
