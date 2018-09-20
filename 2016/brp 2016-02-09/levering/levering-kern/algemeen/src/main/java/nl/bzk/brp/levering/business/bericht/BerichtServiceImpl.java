/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.bericht;

import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.brp.levering.business.bepalers.BerichtVerwerkingssoortZetter;
import nl.bzk.brp.levering.business.bepalers.LegeBerichtBepaler;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.SynchronisatieBericht;
import org.springframework.stereotype.Service;

/**
 * Implementatie van de bericht service.
 *
 * @brp.bedrijfsregel VR00089
 * @brp.bedrijfsregel R1989
 */
@Service
public class BerichtServiceImpl implements BerichtService {

    private static final Logger           LOGGER = LoggerFactory.getLogger();

    @Inject
    @Named("berichtVerwerkingssoortToevoeger")
    private BerichtVerwerkingssoortZetter berichtVerwerkingssoortToevoeger;

    @Inject
    @Named("berichtVerwerkingssoortVerwijderaar")
    private BerichtVerwerkingssoortZetter berichtVerwerkingssoortVerwijderaar;

    @Inject
    private LegeBerichtBepaler            legeBerichtBepaler;

    @Regels({ Regel.VR00089, Regel.R1989 })
    @Override
    public final void filterLegePersonen(final SynchronisatieBericht bericht) {

        final List<PersoonHisVolledigView> personen = bericht.getAdministratieveHandeling().getBijgehoudenPersonen();

        final Iterator<PersoonHisVolledigView> iterator = personen.iterator();

        while (iterator.hasNext()) {
            final PersoonHisVolledigView view = iterator.next();

            if (!legeBerichtBepaler.magPersoonGeleverdWorden(view)) {
                iterator.remove();
                LOGGER.debug("Persoon met id {} mag niet geleverd worden, verwijderd uit bericht.", view.getID());
            }
        }

        bericht.getAdministratieveHandeling().setBijgehoudenPersonen(personen);
    }

    @Override
    public final void voegVerwerkingssoortenToe(final List<PersoonHisVolledigView> bijgehoudenPersoonViews, final Long administratieveHandelingId) {
        berichtVerwerkingssoortToevoeger.voegVerwerkingssoortenToe(bijgehoudenPersoonViews, administratieveHandelingId);
    }

    @Override
    public final void verwijderVerwerkingssoortenUitPersonen(final List<PersoonHisVolledigView> persoonHisVolledigViews) {
        for (final PersoonHisVolledigView persoonHisVolledigView : persoonHisVolledigViews) {
            // Hier null voor de adminstratieve handeling, omdat we aan de interface voor de toevoeger moeten voldoen,
            // maar dit element eigenlijk niet nodig hebben.
            berichtVerwerkingssoortVerwijderaar.voegVerwerkingssoortenToe(persoonHisVolledigView, null);
        }
    }
}
