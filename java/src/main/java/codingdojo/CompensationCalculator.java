package codingdojo;

import codingdojo.overtimecalculator.UnionizedAssignmentOvertime;
import codingdojo.overtimecalculator.NoOvertime;
import codingdojo.overtimecalculator.OvertimeCalculator;

import java.math.BigDecimal;
import java.time.Duration;

public class CompensationCalculator {

    public final static BigDecimal MAX_OVERTIME_HOURS_RATE_1 = BigDecimal.TEN;
    public static final int THRESHOLD_OVERTIME_HOURS_RATE_2 = 6;

    public static Overtime calculateOvertime(BigDecimal hoursOvertimeTotal, Assignment assignment, Briefing briefing) {


        var overtimes = new OvertimeCalculator[]{new NoOvertime(),
                new UnderMaxOvertime(),
                new UnionizedAssignmentOvertime()
                };
        for (OvertimeCalculator overtime : overtimes) {
            if (overtime.isValidFor(hoursOvertimeTotal,assignment, briefing)){
                return overtime.calculateOvertime(hoursOvertimeTotal, assignment, briefing);
            }
        }

        if (!isApplesauce(assignment, briefing)) {
            var hoursOvertimeRate2 = hoursOvertimeTotal.subtract(MAX_OVERTIME_HOURS_RATE_1);
            return new Overtime(MAX_OVERTIME_HOURS_RATE_1, hoursOvertimeRate2);
        } else {
            return new Overtime(hoursOvertimeTotal, BigDecimal.ZERO);
        }
    }

    public static boolean isApplesauce(Assignment assignment, Briefing briefing) {
        boolean isWatcodeUnion = briefing.watcode() && assignment.isUnionized();
        boolean isWatcodeNonUnionForeign = briefing.watcode() && !assignment.isUnionized() && briefing.foreign();

        var isApplesauce = (!briefing.watcode() && !briefing.z3() && !assignment.isUnionized())
                || (briefing.hbmo() && assignment.isUnionized())
                || isWatcodeNonUnionForeign
                || isWatcodeUnion
                || (briefing.foreign() && !assignment.isUnionized());
        return isApplesauce;
    }


}
