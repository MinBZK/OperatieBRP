/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.stappen.persoon;

import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.brp.levering.afnemerindicaties.service.OnderhoudAfnemerindicatiesBerichtContext;
import nl.bzk.brp.levering.afnemerindicaties.service.OnderhoudAfnemerindicatiesResultaat;
import nl.bzk.brp.levering.business.toegang.gegevensfilter.OnderzoekAutorisatieService;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.synchronisatie.RegistreerAfnemerindicatieBericht;
import nl.bzk.brp.webservice.business.stappen.AbstractBerichtVerwerkingStap;

/**
 * Deze stap zorgt voor filtering in onderzoeken die naar persoonsgegevens wijzen waarvoor de afnemer niet geautoriseerd is.
 *
 * @brp.bedrijfsregel R1561
 * @brp.bedrijfsregel R1562
 */
@Regels({ Regel.R1561, Regel.R1562 })
public class AutoriseerOnderzoekenStap extends AbstractBerichtVerwerkingStap<RegistreerAfnemerindicatieBericht,
    OnderhoudAfnemerindicatiesBerichtContext, OnderhoudAfnemerindicatiesResultaat>
{
    @Inject
    private OnderzoekAutorisatieService onderzoekAutorisatieService;

    @Override
    public final boolean voerStapUit(final RegistreerAfnemerindicatieBericht onderwerp, final OnderhoudAfnemerindicatiesBerichtContext context,
                                     final OnderhoudAfnemerindicatiesResultaat resultaat)
    {
        autoriseerOnderzoekenVanPersonen(context);

        return DOORGAAN;
    }

    /**
     * Filter ongeautoriseerde onderzoeken, dit zijn onderzoeken die naar gegevens wijzen die vanwege autorisatie filters niet in het bericht staan.
     *
     * @param context de context
     */
    private void autoriseerOnderzoekenVanPersonen(final OnderhoudAfnemerindicatiesBerichtContext context) {
        final List<PersoonHisVolledigView> bijgehoudenPersonen = context.getVolledigBericht().getAdministratieveHandeling().getBijgehoudenPersonen();

        for (final PersoonHisVolledigView persoonHisVolledigView : bijgehoudenPersonen) {
            autoriseerOnderzoeken(persoonHisVolledigView, context);
        }
    }

    /**
     * Autoriseer de onderzoeken op basis van de autorisatie van het gegeven dat in onderzoek staat.
     *  @param persoonHisVolledigView persoon his volledig view
     * @param context context
     */
    private void autoriseerOnderzoeken(final PersoonHisVolledigView persoonHisVolledigView, final OnderhoudAfnemerindicatiesBerichtContext context) {
        final Map<Integer, Map<Integer, List<Attribuut>>> persoonOnderzoekenMap = context.getPersoonOnderzoekenMap();

        onderzoekAutorisatieService.autoriseerOnderzoeken(persoonHisVolledigView, persoonOnderzoekenMap);
    }
}
