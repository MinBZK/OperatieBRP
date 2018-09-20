package groovy.runtime.metaclass

import nl.bzk.brp.model.operationeel.kern.ActieModel
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel

/**
 * Groovy Extension Module voor klasses in {@link nl.bzk.brp.model.operationeel} packages.
 */
class ModelOperationeelExtensions {

    /**
     * Zijn twee administratieve handelingen gelijk, op basis van hun attributen.
     * De {@code equals()} methode vergelijkt alleen op id-attribuut.
     *
     * @param self
     * @param other
     * @return {@code true} als beide gelijk zijn
     * @see AdministratieveHandelingModel#equals(java.lang.Object)
     */
    static boolean gelijk(AdministratieveHandelingModel self, AdministratieveHandelingModel other) {
        if (other == null || other.class != AdministratieveHandelingModel) { return  false }

        return self.soort.equals(other.soort)  &&
            self.tijdstipRegistratie.equals(other.tijdstipRegistratie) &&
            self.toelichtingOntlening.equals(other.toelichtingOntlening) &&
            self.partij.waarde.code.equals(other.partij.waarde.code)
    }

    /**
     * Zijn twee acties gelijk, op basis van hun attributen.
     * De {@code equals()} methode vergelijkt alleen op id-attribuut.
     *
     * @param self
     * @param other
     * @return {@code true} als beide gelijk zijn
     * @see ActieModel#equals(java.lang.Object)
     */
    static boolean gelijk(ActieModel self, ActieModel other) {
        if (other == null || other.class != ActieModel) { return  false }

        return self.soort.equals(other.soort) &&
            self.tijdstipRegistratie.equals(other.tijdstipRegistratie) &&
            self.partij.waarde.code.equals(other.partij.waarde.code) &&
            self.datumOntlening.equals(other.datumOntlening) &&
            ((self.administratieveHandeling == null && other.administratieveHandeling == null) || self.administratieveHandeling.gelijk(other.administratieveHandeling))
    }
}
