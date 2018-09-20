package nl.bzk.brp.funqmachine.jbehave.converters

import org.jbehave.core.annotations.AsParameterConverter
import org.jbehave.core.steps.ParameterConverters
import org.threeten.bp.Duration

/**
 * Converter die fungeert als JBehave ParameterConverter voor
 * conversie naar {@link Duration} instances.
 */
@Converter
class DurationConverter {

    /**
     * Converteert een waarde naar een duration.
     *
     * @param value de te converteren waarde
     * @return geconverteerde waarde
     */
    @AsParameterConverter
    Duration convertDuration(String value) {
        try {
            return Duration.parse("PT$value")
        } catch (Exception e) {
            throw new ParameterConverters.ParameterConvertionFailed("Can not convert $value to a Duration", e)
        }
    }
}
