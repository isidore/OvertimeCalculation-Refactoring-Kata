package codingdojo.overtimecalculator;

import codingdojo.Assignment;
import codingdojo.Briefing;
import codingdojo.Overtime;

import java.math.BigDecimal;

import static codingdojo.BigDecimalUtils.isALessThanB;

public class DoubleOvertime implements OvertimeCalculator {

    public final static BigDecimal WHEN_DOUBLE_OVERTIME_STARTS = BigDecimal.TEN;

    @Override
    public boolean isValidFor(BigDecimal hoursOvertimeTotal, Assignment assignment, Briefing briefing) {
        return exceedsStandardOvertime(hoursOvertimeTotal) && isEligibleForDoubleOvertime(assignment, briefing);
    }

    private boolean exceedsStandardOvertime(BigDecimal hoursOvertimeTotal) {
        return isALessThanB(WHEN_DOUBLE_OVERTIME_STARTS, hoursOvertimeTotal);
    }

    private boolean isEligibleForDoubleOvertime(Assignment assignment, Briefing briefing) {
        var standardZ3 = briefing.z3() && !(assignment.isUnionized() || briefing.foreign());
        var standardWatcode = briefing.watcode() && !(assignment.isUnionized() || briefing.z3() || briefing.foreign());
        var standardUnionized = assignment.isUnionized() && !(briefing.watcode() || briefing.hbmo());
        return (standardZ3 || standardWatcode || standardUnionized);
    }

    @Override
    public Overtime calculateOvertime(BigDecimal hoursOvertimeTotal, Assignment assignment, Briefing briefing) {
        var hoursOvertimeRate2 = hoursOvertimeTotal.subtract(WHEN_DOUBLE_OVERTIME_STARTS);
        return new Overtime(WHEN_DOUBLE_OVERTIME_STARTS, hoursOvertimeRate2);
    }
}
