package codingdojo.overtimecalculator;

import codingdojo.Assignment;
import codingdojo.Briefing;
import codingdojo.CompensationCalculator;

import java.math.BigDecimal;

public class NoOvertime {
    public static boolean isaBoolean(BigDecimal hoursOvertimeTotal, Assignment assignment, Briefing briefing) {
        return !CompensationCalculator.isApplesauce(assignment, briefing) && hoursOvertimeTotal.compareTo(BigDecimal.ZERO) < 1;
    }
}
