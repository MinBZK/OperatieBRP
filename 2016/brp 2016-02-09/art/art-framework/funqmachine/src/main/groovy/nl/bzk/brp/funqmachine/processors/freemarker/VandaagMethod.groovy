package nl.bzk.brp.funqmachine.processors.freemarker

import freemarker.core.Environment
import freemarker.template.SimpleNumber
import freemarker.template.TemplateException

class VandaagMethod extends DatumFormattingMethod {

    @Override
    void valideerParameters(final List arguments) {
        boolean valid = true
        arguments.each {
            if (!(it instanceof SimpleNumber)) {
                valid = false
            }
        }

        if (!(valid && arguments.size() <= 3)) {
            throw new TemplateException("Argumenten $arguments zijn niet valide", Environment.currentEnvironment)
        }
    }

    @Override
    String getFormat() {
        return 'yyyy-MM-dd'
    }

}
