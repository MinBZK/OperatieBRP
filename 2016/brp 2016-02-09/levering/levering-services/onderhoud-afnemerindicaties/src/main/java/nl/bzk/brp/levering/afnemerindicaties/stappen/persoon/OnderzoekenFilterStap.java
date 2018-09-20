/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.stappen.persoon;

import java.util.Collections;
import javax.inject.Inject;
import nl.bzk.brp.levering.afnemerindicaties.service.OnderhoudAfnemerindicatiesBerichtContext;
import nl.bzk.brp.levering.afnemerindicaties.service.OnderhoudAfnemerindicatiesResultaat;
import nl.bzk.brp.levering.business.toegang.gegevensfilter.OnderzoekenFilterService;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.synchronisatie.RegistreerAfnemerindicatieBericht;
import nl.bzk.brp.webservice.business.stappen.AbstractBerichtVerwerkingStap;

/**
 * Deze stap zorgt voor filtering in onderzoeken. Nu filtert het de gegevens in onderzoek die verwijzen naar ontbrekende gegevens.
 *
 * @brp.bedrijfsregel R2063, R2065
 */
@Regels({Regel.R2065, Regel.R2063})
public class OnderzoekenFilterStap extends AbstractBerichtVerwerkingStap<RegistreerAfnemerindicatieBericht,
    OnderhoudAfnemerindicatiesBerichtContext, OnderhoudAfnemerindicatiesResultaat>
{

    @Inject
    private OnderzoekenFilterService onderzoekenFilterService;

    @Override
    public final boolean voerStapUit(final RegistreerAfnemerindicatieBericht onderwerp, final OnderhoudAfnemerindicatiesBerichtContext context,
        final OnderhoudAfnemerindicatiesResultaat resultaat)
    {
        onderzoekenFilterService.filterOnderzoekGegevensNaarOntbrekendeGegevens(Collections.singletonList(context.getPersoonHisVolledig()));
        return DOORGAAN;
    }
}
