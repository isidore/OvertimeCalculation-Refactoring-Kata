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
    private static final int THRESHOLD_OVERTIME_HOURS_RATE_2_FOREIGN = 4;

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
        var doubleOvertime = hoursOvertimeTotal.subtract(DoubleOvertime.WHEN_DOUBLE_OVERTIME_STARTS);
        doubleOvertime = doubleOvertime.min(calculateMaxDoubleOvertimeAllowed(assignment, briefing));
        return new Overtime(DoubleOvertime.WHEN_DOUBLE_OVERTIME_STARTS, doubleOvertime);
    }

    public static BigDecimal calculateMaxDoubleOvertimeAllowed(Assignment assignment, Briefing briefing) {
        var thresholdOvertimeHoursRate2 = briefing.foreign() ? THRESHOLD_OVERTIME_HOURS_RATE_2_FOREIGN : THRESHOLD_OVERTIME_HOURS_RATE_2;
        Duration remainder = assignment.duration().minusHours(thresholdOvertimeHoursRate2);
        if (remainder.isNegative()) {
            return BigDecimal.valueOf(assignment.duration().toSeconds() / 3600);
        } else {
            return BigDecimal.valueOf(thresholdOvertimeHoursRate2);
        }
    }
}
