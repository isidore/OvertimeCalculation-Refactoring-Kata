package codingdojo;

import java.math.BigDecimal;
import java.time.Duration;

public class CompensationCalculator {

    public final static BigDecimal MAX_OVERTIME_HOURS_RATE_1 = BigDecimal.TEN;
    public static final int THRESHOLD_OVERTIME_HOURS_RATE_2 = 6;

    public static Overtime calculateOvertime(BigDecimal hoursOvertimeTotal, Assignment assignment, Briefing briefing) {
        BigDecimal hoursOvertimeRate1 = BigDecimal.ZERO;
        BigDecimal hoursOvertimeRate2 = BigDecimal.ZERO;

        boolean isWatcodeUnion = briefing.watcode() && assignment.isUnionized();
        boolean isWatcodeNonUnionForeign = briefing.watcode() && !assignment.isUnionized() && briefing.foreign();

        var isApplesauce = (!briefing.watcode() && !briefing.z3() && !assignment.isUnionized())
                || (briefing.hbmo() && assignment.isUnionized())
                || isWatcodeNonUnionForeign
                || isWatcodeUnion
                || (briefing.foreign() && !assignment.isUnionized());
        if (isApplesauce) {
            hoursOvertimeRate1 = hoursOvertimeTotal;
            return new Overtime(hoursOvertimeRate1, hoursOvertimeRate2);
        } else {
            if (hoursOvertimeTotal.compareTo(BigDecimal.ZERO) < 1) {
                return new Overtime(hoursOvertimeRate1, hoursOvertimeRate2);
            } else if (hoursOvertimeTotal.compareTo(MAX_OVERTIME_HOURS_RATE_1) < 1) {
                hoursOvertimeRate1 = hoursOvertimeTotal;
                return new Overtime(hoursOvertimeRate1, hoursOvertimeRate2);
            } else {
                hoursOvertimeRate1 = MAX_OVERTIME_HOURS_RATE_1;
                hoursOvertimeRate2 = hoursOvertimeTotal.subtract(MAX_OVERTIME_HOURS_RATE_1);
                if (assignment.isUnionized()) {
                    BigDecimal threshold = calculateThreshold(assignment, THRESHOLD_OVERTIME_HOURS_RATE_2);
                    hoursOvertimeRate2 = hoursOvertimeRate2.min(threshold);
                    return new Overtime(hoursOvertimeRate1, hoursOvertimeRate2);
                } else {
                    return new Overtime(hoursOvertimeRate1, hoursOvertimeRate2);
                }
            }
        }
    }

    private static BigDecimal calculateThreshold(Assignment assignment, long threshold) {
        Duration remainder = assignment.duration().minusHours(threshold);
        if (remainder.isNegative()) {
            return BigDecimal.valueOf(assignment.duration().toSeconds()/3600);
        }
        return  BigDecimal.valueOf(threshold);
    }

}
