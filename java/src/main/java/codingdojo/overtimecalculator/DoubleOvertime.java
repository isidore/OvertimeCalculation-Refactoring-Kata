package codingdojo.overtimecalculator;

import codingdojo.Assignment;
import codingdojo.Briefing;
import codingdojo.Overtime;

import java.math.BigDecimal;

import static codingdojo.CompensationCalculator.MAX_OVERTIME_HOURS_RATE_1;

public class DoubleOvertime implements OvertimeCalculator {

    @Override
    public boolean isValidFor(BigDecimal hoursOvertimeTotal, Assignment assignment, Briefing briefing) {
        return exceedsStandardOvertime(hoursOvertimeTotal) && isEligableForDoubleOvertime(assignment, briefing);
    }

    private boolean exceedsStandardOvertime(BigDecimal hoursOvertimeTotal) {
        return isALessThanB(MAX_OVERTIME_HOURS_RATE_1, hoursOvertimeTotal);
    }

    private boolean isEligableForDoubleOvertime(Assignment assignment, Briefing briefing) {
        var standardZ3 = briefing.z3() && !(assignment.isUnionized() || briefing.foreign());
        var standardWatcode = briefing.watcode() && !(assignment.isUnionized() || briefing.z3() || briefing.foreign());
        var standardUnionized = assignment.isUnionized() && !(briefing.watcode() || briefing.hbmo());
        var isEligableForDoubleOvertime = (standardZ3 || standardWatcode || standardUnionized);
        return isEligableForDoubleOvertime;
    }

    public static boolean isALessThanB(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) < 0;
    }

    @Override
    public Overtime calculateOvertime(BigDecimal hoursOvertimeTotal, Assignment assignment, Briefing briefing) {
        var hoursOvertimeRate2 = hoursOvertimeTotal.subtract(MAX_OVERTIME_HOURS_RATE_1);
        return new Overtime(MAX_OVERTIME_HOURS_RATE_1, hoursOvertimeRate2);
    }
}
