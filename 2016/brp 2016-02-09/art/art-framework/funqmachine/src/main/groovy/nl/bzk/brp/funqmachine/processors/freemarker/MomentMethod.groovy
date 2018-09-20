package nl.bzk.brp.funqmachine.processors.freemarker

import freemarker.core.Environment
import freemarker.template.SimpleNumber
import freemarker.template.TemplateException

class MomentMethod extends DatumFormattingMethod {

    @Override
    void valideerParameters(final List argumenents) {
        boolean valid = true
        argumenents.each {
            if (!(it instanceof SimpleNumber)) {
                valid = false
            }
        }

        if (!(valid && argumenents.size() <= 5)) {
            throw new TemplateException("Argumenten $argumenents zijn niet valide", Environment.currentEnvironment)
        }
    }

    @Override
    String getFormat() {
        return 'yyyy-MM-dd\'T\'HH:mm:ss.SSSXXX'
    }

}
