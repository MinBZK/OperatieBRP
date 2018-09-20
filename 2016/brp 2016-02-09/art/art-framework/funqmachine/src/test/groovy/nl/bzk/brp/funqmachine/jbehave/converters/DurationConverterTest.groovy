package nl.bzk.brp.funqmachine.jbehave.converters

import static org.threeten.bp.temporal.ChronoUnit.SECONDS

import org.jbehave.core.steps.ParameterConverters
import org.junit.Test
import org.threeten.bp.Duration

class DurationConverterTest {
    private DurationConverter converter = new DurationConverter()

    @Test
    void kanConverteren() {
        assert converter.convertDuration('1s') == Duration.of(1, SECONDS)
    }

    @Test(expected = ParameterConverters.ParameterConvertionFailed)
    void fouteConversie() {
        converter.convertDuration('asdf')
    }
}
