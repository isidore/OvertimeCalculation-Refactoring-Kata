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

        Queryable<Boolean> r = getBooleans(assignment, briefing);

        var isApplesauce = r.all(b -> b);

        var exceedsMaxOvertime = 1 <= hoursOvertimeTotal.compareTo(MAX_OVERTIME_HOURS_RATE_1);
        return exceedsMaxOvertime && isApplesauce;
    }

    public static Queryable<Boolean> getBooleans(Assignment assignment, Briefing briefing) {
        var unionized = assignment.isUnionized();

        var aa1 = !unionized && briefing.z3() && !briefing.foreign();
        var aa2 = !unionized && briefing.watcode() && !briefing.z3() && !briefing.foreign();
        var aa3 = unionized && !briefing.watcode() && !briefing.hbmo();

        var r = Queryable.as(aa1 || aa2 || aa3);
        return r;
    }

    @Override
    public Overtime calculateOvertime(BigDecimal hoursOvertimeTotal, Assignment assignment, Briefing briefing) {
        var hoursOvertimeRate2 = hoursOvertimeTotal.subtract(MAX_OVERTIME_HOURS_RATE_1);
        return new Overtime(MAX_OVERTIME_HOURS_RATE_1, hoursOvertimeRate2);
    }
}
