package codingdojo.overtimecalculator;

import codingdojo.Assignment;
import codingdojo.Briefing;
import codingdojo.Overtime;

import java.math.BigDecimal;
import java.time.Duration;

import static codingdojo.overtimecalculator.DoubleOvertime.WHEN_DOUBLE_OVERTIME_STARTS;
import static codingdojo.BigDecimalUtils.isALessThanB;

public class UnionizedAssignmentOvertime implements OvertimeCalculator {
    public static final int THRESHOLD_OVERTIME_HOURS_RATE_2 = 6;

    public static BigDecimal calculateThreshold(Assignment assignment, long threshold) {
        Duration remainder = assignment.duration().minusHours(threshold);
        if (remainder.isNegative()) {
            return BigDecimal.valueOf(assignment.duration().toSeconds() / 3600);
        }
        return BigDecimal.valueOf(threshold);
    }

    @Override
    public boolean isValidFor(BigDecimal hoursOvertimeTotal, Assignment assignment, Briefing briefing) {
        var isNonExempt = !briefing.hbmo() && !briefing.watcode();
        var exceedsMaxOvertime = isALessThanB(WHEN_DOUBLE_OVERTIME_STARTS, hoursOvertimeTotal);
        return assignment.isUnionized() && isNonExempt && exceedsMaxOvertime;
    }

    @Override
    public Overtime calculateOvertime(BigDecimal hoursOvertimeTotal, Assignment assignment, Briefing briefing) {
        BigDecimal threshold = calculateThreshold(assignment, THRESHOLD_OVERTIME_HOURS_RATE_2);
        var hoursOvertimeRate2 = hoursOvertimeTotal.subtract(DoubleOvertime.WHEN_DOUBLE_OVERTIME_STARTS);
        hoursOvertimeRate2 = hoursOvertimeRate2.min(threshold);
        return new Overtime(DoubleOvertime.WHEN_DOUBLE_OVERTIME_STARTS, hoursOvertimeRate2);
    }
}
