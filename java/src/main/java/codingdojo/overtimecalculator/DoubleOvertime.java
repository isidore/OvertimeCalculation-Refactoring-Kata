package codingdojo.overtimecalculator;

import codingdojo.Assignment;
import codingdojo.Briefing;
import codingdojo.Overtime;

import java.math.BigDecimal;

import static codingdojo.CompensationCalculator.MAX_OVERTIME_HOURS_RATE_1;

public class DoubleOvertime implements OvertimeCalculator {

    @Override
    public boolean isValidFor(BigDecimal hoursOvertimeTotal, Assignment assignment, Briefing briefing) {

        var unionized = assignment.isUnionized();

        var standardZ3 = briefing.z3() && !unionized && !briefing.foreign();
        var standardWatcode = briefing.watcode() && !unionized && !briefing.z3() && !briefing.foreign();
        var standardUnionized = unionized && !briefing.watcode() && !briefing.hbmo();

        var isEligableForDoubleOvertime = (standardZ3 || standardWatcode || standardUnionized);


        var exceedsMaxOvertime = isALessThanB(MAX_OVERTIME_HOURS_RATE_1, hoursOvertimeTotal);
        return exceedsMaxOvertime && isEligableForDoubleOvertime;
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
