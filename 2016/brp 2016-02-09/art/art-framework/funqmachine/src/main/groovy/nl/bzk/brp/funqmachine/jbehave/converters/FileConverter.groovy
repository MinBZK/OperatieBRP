package nl.bzk.brp.funqmachine.jbehave.converters

import org.jbehave.core.annotations.AsParameterConverter
import org.jbehave.core.steps.ParameterConverters

/**
 * Converter component ten behoeve van JBehave, om
 * als ParameterConverter te fungeren.
 */
@Converter
class FileConverter {

    /**
     * Converteert een waarde naar een file.
     * @param value de te converteren waarde
     * @return de geconverteerde waarde
     */
    @AsParameterConverter
    public File convertFile(String value) {
        try {
            URL url = getClass().getResource(value)
            return new File(url.toURI())
        } catch (Exception e) {
            throw new ParameterConverters.ParameterConvertionFailed("Expected file [$value] to exist.", e)
        }
    }
}
