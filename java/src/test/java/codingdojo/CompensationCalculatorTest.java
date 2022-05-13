package codingdojo;

import org.approvaltests.combinations.CombinationApprovals;
import org.junit.jupiter.api.Test;
import org.lambda.query.Queryable;

import java.math.BigDecimal;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompensationCalculatorTest {
    @Test
    void fixme() {


        var overtimes = Queryable.as(0.0, 0.5, 1.0, 5.0, 20.0).select(BigDecimal::new).asArray();
        Boolean[] booleans = new Boolean[]{true, false};
        Duration[] durations = Queryable.as(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10).select(Duration::ofHours).asArray();
        CombinationApprovals.verifyAllCombinations(
                this::callOvertime,
                 overtimes, booleans, durations, booleans, booleans, booleans, booleans);

    }

    private Object callOvertime(BigDecimal bigDecimal, boolean isUnionized, Duration duration, boolean watcode, boolean z3, boolean foreign, boolean hbmo) {
        return CompensationCalculator.calculateOvertime(
                bigDecimal,
                new Assignment(isUnionized, duration),
                new Briefing(watcode, z3, foreign, hbmo)
        );
    }

}
