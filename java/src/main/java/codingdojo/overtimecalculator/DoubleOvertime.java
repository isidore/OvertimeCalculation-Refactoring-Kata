package codingdojo.overtimecalculator;

import codingdojo.Overtime;

import java.math.BigDecimal;

public class DoubleOvertime{

    public final static BigDecimal WHEN_DOUBLE_OVERTIME_STARTS = BigDecimal.TEN;

    public static Overtime getOvertime(BigDecimal hoursOvertimeTotal) {
        var hoursOvertimeRate2 = hoursOvertimeTotal.subtract(WHEN_DOUBLE_OVERTIME_STARTS);
        return new Overtime(WHEN_DOUBLE_OVERTIME_STARTS, hoursOvertimeRate2);
    }
}
