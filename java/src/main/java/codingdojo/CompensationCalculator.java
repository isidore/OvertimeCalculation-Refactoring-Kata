package codingdojo;

import java.math.BigDecimal;
import java.time.Duration;

public class CompensationCalculator {

    public final static BigDecimal MAX_OVERTIME_HOURS_RATE_1 = BigDecimal.TEN;
    public static final int THRESHOLD_OVERTIME_HOURS_RATE_2 = 6;

    public static Overtime calculateOvertime(BigDecimal hoursOvertimeTotal, Assignment assignment, Briefing briefing) {

        boolean isWatcodeUnion = briefing.watcode() && assignment.isUnionized();
        boolean isWatcodeNonUnionForeign = briefing.watcode() && !assignment.isUnionized() && briefing.foreign();

        var isApplesauce = (!briefing.watcode() && !briefing.z3() && !assignment.isUnionized())
                || (briefing.hbmo() && assignment.isUnionized())
                || isWatcodeNonUnionForeign
                || isWatcodeUnion
                || (briefing.foreign() && !assignment.isUnionized());
        if (isApplesauce) {
            return new Overtime(hoursOvertimeTotal, BigDecimal.ZERO);
        } else if (hoursOvertimeTotal.compareTo(BigDecimal.ZERO) < 1) {
            return new Overtime(BigDecimal.ZERO, BigDecimal.ZERO);
        } else if (hoursOvertimeTotal.compareTo(MAX_OVERTIME_HOURS_RATE_1) < 1) {
            return new Overtime(hoursOvertimeTotal, BigDecimal.ZERO);
        } else if (assignment.isUnionized()) {
            BigDecimal hoursOvertimeRate3 = BigDecimal.ZERO;
            BigDecimal threshold = calculateThreshold(assignment, THRESHOLD_OVERTIME_HOURS_RATE_2);
            hoursOvertimeRate3 = hoursOvertimeTotal.subtract(MAX_OVERTIME_HOURS_RATE_1);
            hoursOvertimeRate3 = hoursOvertimeRate3.min(threshold);
            return new Overtime(MAX_OVERTIME_HOURS_RATE_1, hoursOvertimeRate3);
        } else {
            var hoursOvertimeRate3 = hoursOvertimeTotal.subtract(MAX_OVERTIME_HOURS_RATE_1);
            return new Overtime(MAX_OVERTIME_HOURS_RATE_1, hoursOvertimeRate3);
        }
    }


    private static BigDecimal calculateThreshold(Assignment assignment, long threshold) {
        Duration remainder = assignment.duration().minusHours(threshold);
        if (remainder.isNegative()) {
            return BigDecimal.valueOf(assignment.duration().toSeconds() / 3600);
        }
        return BigDecimal.valueOf(threshold);
    }

}
