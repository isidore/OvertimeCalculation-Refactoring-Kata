package codingdojo.overtimecalculator;

import codingdojo.Assignment;
import codingdojo.Briefing;
import codingdojo.Overtime;

import java.math.BigDecimal;

public interface OvertimeCalculator {
    boolean isValidFor(BigDecimal hoursOvertimeTotal, Assignment assignment, Briefing briefing);

    Overtime calculateOvertime(BigDecimal hoursOvertimeTotal, Assignment assignment, Briefing briefing);
}
