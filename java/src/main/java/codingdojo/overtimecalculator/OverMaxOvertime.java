package codingdojo.overtimecalculator;

import codingdojo.Assignment;
import codingdojo.Briefing;
import codingdojo.Overtime;
import org.lambda.query.Queryable;

import java.math.BigDecimal;

import static codingdojo.CompensationCalculator.MAX_OVERTIME_HOURS_RATE_1;

public class OverMaxOvertime implements OvertimeCalculator {

    @Override
    public boolean isValidFor(BigDecimal hoursOvertimeTotal, Assignment assignment, Briefing briefing) {

        var unionized = assignment.isUnionized();
        var a1 = briefing.watcode()
                || briefing.z3()
                || unionized;

        var a2 = !briefing.hbmo() || !unionized;
        var a4 = !briefing.watcode() || !unionized;

        var b1 = (!briefing.hbmo() || !unionized) && (!briefing.watcode() || !unionized);

        var a3 = !(briefing.watcode()
                && !unionized
                && briefing.foreign());
        var a5 = !(briefing.foreign()
                && !unionized);

        var r = Queryable.as(a1, a2, a3, a4, a5);

        var isApplesauce = r.all(b -> b);

        var exceedsMaxOvertime = 1 <= hoursOvertimeTotal.compareTo(MAX_OVERTIME_HOURS_RATE_1);
        return exceedsMaxOvertime && isApplesauce;
    }

    @Override
    public Overtime calculateOvertime(BigDecimal hoursOvertimeTotal, Assignment assignment, Briefing briefing) {
        var hoursOvertimeRate2 = hoursOvertimeTotal.subtract(MAX_OVERTIME_HOURS_RATE_1);
        return new Overtime(MAX_OVERTIME_HOURS_RATE_1, hoursOvertimeRate2);
    }
}
