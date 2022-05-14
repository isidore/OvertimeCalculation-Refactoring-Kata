package codingdojo.overtimecalculator;

import codingdojo.Assignment;
import codingdojo.Briefing;
import codingdojo.Overtime;

import java.math.BigDecimal;
import java.time.Duration;

import static codingdojo.BigDecimalUtils.isALessThanB;
import static codingdojo.overtimecalculator.DoubleOvertime.WHEN_DOUBLE_OVERTIME_STARTS;

public class UnionizedAssignmentOvertime implements OvertimeCalculator {
    public static final int THRESHOLD_OVERTIME_HOURS_RATE_2 = 6;
    private static final int THRESHOLD_OVERTIME_HOURS_RATE_2_FOREIGN = THRESHOLD_OVERTIME_HOURS_RATE_2;

    public static BigDecimal calculateThreshold(Assignment assignment, long threshold) {
        Duration remainder = assignment.duration().minusHours(threshold);
        if (remainder.isNegative()) {
            return BigDecimal.valueOf(assignment.duration().toSeconds() / 3600);
        }
        return BigDecimal.valueOf(threshold);
    }

    // The threshold for overtime hours at rate 2 should be changed from 6 to 4
    // if the assignment is unionized and the briefing is foreign.

    @Override
    public boolean isValidFor(BigDecimal hoursOvertimeTotal, Assignment assignment, Briefing briefing) {
        var isNonExempt = !briefing.hbmo() && !briefing.watcode();
        var exceedsMaxOvertime = isALessThanB(WHEN_DOUBLE_OVERTIME_STARTS, hoursOvertimeTotal);
        return assignment.isUnionized() && isNonExempt && exceedsMaxOvertime;
    }

    @Override
    public Overtime calculateOvertime(BigDecimal hoursOvertimeTotal, Assignment assignment, Briefing briefing) {
        var thresholdOvertimeHoursRate2 = briefing.foreign() ? THRESHOLD_OVERTIME_HOURS_RATE_2_FOREIGN : THRESHOLD_OVERTIME_HOURS_RATE_2;
        BigDecimal threshold = calculateThreshold(assignment, thresholdOvertimeHoursRate2);
        var hoursOvertimeRate2 = hoursOvertimeTotal.subtract(DoubleOvertime.WHEN_DOUBLE_OVERTIME_STARTS);
        hoursOvertimeRate2 = hoursOvertimeRate2.min(threshold);
        return new Overtime(DoubleOvertime.WHEN_DOUBLE_OVERTIME_STARTS, hoursOvertimeRate2);
    }
}
