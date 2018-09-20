package groovy.runtime.metaclass

import nl.bzk.brp.model.basis.AbstractAttribuut
import nl.bzk.brp.model.basis.FormeleHistorie
import nl.bzk.brp.model.basis.MaterieleHistorie
import org.codehaus.groovy.runtime.typehandling.GroovyCastException

/**
 * Groovy Extension Module voor klasses in {@link nl.bzk.brp.model.basis} packages.
 */
class ModelBasisExtensions {

    /**
     * Geef direct de waarde van het attribuut datumTijdVerval.
     *
     * @param self
     * @return
     */
    static Date getVerval(FormeleHistorie self) {
        return self.getDatumTijdVerval()?.getWaarde()
    }

    /**
     * Geef direct de waarde van het attribuut tijdstipRegistratie.
     * @param self
     * @return
     */
    static Date getRegistratie(FormeleHistorie self) {
        return self.getTijdstipRegistratie()?.getWaarde()
    }

    /**
     * Geef direct de waarde van het attribuut datumAanvangGeldigheid.
     * @param self
     * @return
     */
    static Integer getAanvangGeldigheid(MaterieleHistorie self) {
        return self.getDatumAanvangGeldigheid()?.getWaarde()
    }

    /**
     * Geef direct de waarde van het attribuut datumEindeGeldigheid.
     * @param self
     * @return
     */
    static Integer getEindeGeldigheid(MaterieleHistorie self) {
        return self.getDatumEindeGeldigheid()?.getWaarde()
    }

    static <T> T asType(AbstractAttribuut self, Class<T> clazz) {
        if (self.waarde.class.isAssignableFrom(clazz)) {
            return (T) self.waarde
        }

        throw new GroovyCastException("Error casting attribuut ${self.class} to ${clazz.name}")
    }
}
