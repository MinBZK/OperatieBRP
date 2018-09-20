package nl.bzk.brp.datataal.handlers.persoon

import groovy.transform.InheritConstructors
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Bijhoudingsaard
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaard
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling

/**
 * Handler voor de administratieve handeling GBA Bijhouding Overig.
 */
@InheritConstructors
class GBABijhoudingOverigHandler extends VerhuizingHandler {

    /**
     * Conversie GBA.
     *
     * @return de builder
     */
    def conversieGBA() {
        verhuizingActie.soort.waarde = SoortActie.CONVERSIE_G_B_A

        builder.nieuwBijhoudingRecord(verhuizingActie)
            .bijhoudingspartij(admhnd.partij.waarde)
            .bijhoudingsaard(Bijhoudingsaard.INGEZETENE)
            .nadereBijhoudingsaard(NadereBijhoudingsaard.ACTUEEL)
            .indicatieOnverwerktDocumentAanwezig(JaNeeAttribuut.NEE)
            .eindeRecord()
    }

    void eindeGebeurtenis() {
        admhnd.soort.waarde = SoortAdministratieveHandeling.G_B_A_BIJHOUDING_OVERIG
    }
}
