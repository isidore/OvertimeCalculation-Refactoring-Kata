package codingdojo;

import codingdojo.overtimecalculator.*;
import com.spun.util.NumberUtils;
import org.lambda.query.Queryable;

import java.math.BigDecimal;

public class CompensationCalculator {

    public static Overtime calculateOvertime(BigDecimal hoursOvertimeTotal, Assignment assignment, Briefing briefing) {


        var overtimes = Queryable.as(
                new NoOvertime(),
                new UnderMaxOvertime(),
                new UnionizedAssignmentOvertime(),
                new DoubleOvertimeUnionized(),
                new DoubleOvertimeZ3(),
                new DoubleOvertimeWatcode()
        );

        for (OvertimeCalculator overtime : overtimes) {
            if (overtime.isValidFor(hoursOvertimeTotal,assignment, briefing)){
                return overtime.calculateOvertime(hoursOvertimeTotal, assignment, briefing);
            }
        }
        return new Overtime(hoursOvertimeTotal, BigDecimal.ZERO);
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
