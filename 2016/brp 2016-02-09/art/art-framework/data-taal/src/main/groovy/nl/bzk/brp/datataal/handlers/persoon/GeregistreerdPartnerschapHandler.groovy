package nl.bzk.brp.datataal.handlers.persoon

import nl.bzk.brp.datataal.model.GebeurtenisAttributen
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling
import nl.bzk.brp.util.hisvolledig.kern.GeregistreerdPartnerschapHisVolledigImplBuilder
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder

/**
 *
 */
class GeregistreerdPartnerschapHandler extends AbstractHuwelijkPartnerschapHandler {
    /**
     * Constructor.
     *
     * @param m
     * @param builder @param relBuilder
     */
    GeregistreerdPartnerschapHandler(final GebeurtenisAttributen m, final PersoonHisVolledigImplBuilder builder)
    {
        super(m, builder, new GeregistreerdPartnerschapHisVolledigImplBuilder())

        admhnd.soort.waarde = SoortAdministratieveHandeling.AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_NEDERLAND
    }
}
