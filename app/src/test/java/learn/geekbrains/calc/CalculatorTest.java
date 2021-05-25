package learn.geekbrains.calc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @CsvSource({
            "0, 0",
            "2., 2",
            "42.00, 42",
            "42.0050, 42.005",
            "4200, 4200"
    })

    @ParameterizedTest
    void testNormalizeCompleteOperand(String source, String result) {
        Assertions.assertEquals(result, calculator.normalizeCompleteOperand(new StringBuilder(source)).toString());
    }
}