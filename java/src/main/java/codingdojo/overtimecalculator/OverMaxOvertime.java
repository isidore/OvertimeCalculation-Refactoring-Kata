package codingdojo.overtimecalculator;

import codingdojo.Assignment;
import codingdojo.Briefing;
import codingdojo.Overtime;

import java.math.BigDecimal;

import static codingdojo.CompensationCalculator.MAX_OVERTIME_HOURS_RATE_1;

public class OverMaxOvertime implements OvertimeCalculator {

    @Override
    public boolean isValidFor(BigDecimal hoursOvertimeTotal, Assignment assignment, Briefing briefing) {
        boolean isWatcodeUnion = briefing.watcode() && assignment.isUnionized();
        boolean isWatcodeNonUnionForeign = briefing.watcode() && !assignment.isUnionized() && briefing.foreign();

        var isApplesauce = (!briefing.watcode() && !briefing.z3() && !assignment.isUnionized())
                || (briefing.hbmo() && assignment.isUnionized())
                || isWatcodeNonUnionForeign
                || isWatcodeUnion
                || (briefing.foreign() && !assignment.isUnionized());

        var exceedsMaxOvertime = 1 <= hoursOvertimeTotal.compareTo(MAX_OVERTIME_HOURS_RATE_1);
        return !isApplesauce && exceedsMaxOvertime;
    }

    @Override
    public Overtime calculateOvertime(BigDecimal hoursOvertimeTotal, Assignment assignment, Briefing briefing) {
        var hoursOvertimeRate2 = hoursOvertimeTotal.subtract(MAX_OVERTIME_HOURS_RATE_1);
        return new Overtime(MAX_OVERTIME_HOURS_RATE_1, hoursOvertimeRate2);
    }
}
