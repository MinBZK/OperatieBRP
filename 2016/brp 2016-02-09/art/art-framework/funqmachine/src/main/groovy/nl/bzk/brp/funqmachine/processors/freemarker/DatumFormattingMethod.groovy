package nl.bzk.brp.funqmachine.processors.freemarker

import freemarker.core.Environment
import freemarker.template.SimpleNumber
import freemarker.template.TemplateException
import freemarker.template.TemplateMethodModelEx
import freemarker.template.TemplateModelException
import java.text.SimpleDateFormat

abstract class DatumFormattingMethod implements TemplateMethodModelEx {

    @Override
    Object exec(final List arguments) throws TemplateModelException {
        valideerParameters(arguments)

        def date = Environment.currentEnvironment.getDataModel()['timestamp']
        return format(bereken(date?.getAsDate() ?: new Date(), arguments))
    }

    /**
     * Valideert de argumenten van de functie.
     *
     * @param argumenents de argumenten om te valideren
     * @throws TemplateException als de argumenten niet valide zijn
     */
    abstract void valideerParameters(List argumenents) throws TemplateException;

    protected Calendar bereken(Date datum, List<SimpleNumber> diff) {
        Calendar cal = Calendar.instance
        cal.timeInMillis = datum.time

        diff.eachWithIndex { SimpleNumber entry, int i ->
            def value = entry.asNumber as int
            switch (i) {
                case 0:
                    cal.add(Calendar.YEAR, value); break
                case 1:
                    cal.add(Calendar.MONTH, value); break
                case 2:
                    cal.add(Calendar.DATE, value); break
                case 3:
                    cal.add(Calendar.HOUR, value); break
                case 4:
                    cal.add(Calendar.MINUTE, value); break
            }
        }

        cal
    }

    /**
     * Geeft het datum format voor deze functie.
     *
     * @return een string compatible met {@link SimpleDateFormat}
     */
    abstract String getFormat();

    protected String format(Calendar cal) {
        def pattern = getFormat()

        SimpleDateFormat df = new SimpleDateFormat(pattern)
        df.setLenient(false)

        df.format(cal.time)
    }

}
