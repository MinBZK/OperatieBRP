package nl.bzk.brp.datataal.handlers.persoon

import nl.bzk.brp.datataal.model.GebeurtenisAttributen
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling
import nl.bzk.brp.util.hisvolledig.kern.HuwelijkHisVolledigImplBuilder
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder

/**
 *
 */
class HuwelijkHandler extends AbstractHuwelijkPartnerschapHandler {

    /**
     * Constructor.
     *
     * @param m
     * @param builder @param relBuilder
     */
    HuwelijkHandler(final GebeurtenisAttributen m, final PersoonHisVolledigImplBuilder builder) {
        super(m, builder, new HuwelijkHisVolledigImplBuilder())

        admhnd.soort.waarde = SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND
    }
}
