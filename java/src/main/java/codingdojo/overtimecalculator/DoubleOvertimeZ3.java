package codingdojo.overtimecalculator;

import codingdojo.Assignment;
import codingdojo.Briefing;
import codingdojo.Overtime;

import java.math.BigDecimal;

import static codingdojo.ComparableUtils.isALessThanB;
import static codingdojo.overtimecalculator.DoubleOvertime.WHEN_DOUBLE_OVERTIME_STARTS;
import static codingdojo.overtimecalculator.DoubleOvertime.getOvertime;

public class DoubleOvertimeZ3 implements OvertimeCalculator {

     @Override
    public boolean isValidFor(BigDecimal hoursOvertimeTotal, Assignment assignment, Briefing briefing) {

        return isALessThanB(WHEN_DOUBLE_OVERTIME_STARTS, hoursOvertimeTotal)
                && briefing.z3() && !(assignment.isUnionized() || briefing.foreign());
    }

    @Override
    public Overtime calculateOvertime(BigDecimal hoursOvertimeTotal, Assignment assignment, Briefing briefing) {
        return getOvertime(hoursOvertimeTotal);
    }
}
