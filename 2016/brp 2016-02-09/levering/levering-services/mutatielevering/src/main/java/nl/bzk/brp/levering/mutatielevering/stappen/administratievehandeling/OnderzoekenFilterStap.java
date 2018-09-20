/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.administratievehandeling;

import javax.inject.Inject;
import nl.bzk.brp.levering.business.stappen.administratievehandeling.AdministratieveHandelingVerwerkingContext;
import nl.bzk.brp.levering.business.toegang.gegevensfilter.OnderzoekenFilterService;
import nl.bzk.brp.levering.mutatielevering.stappen.AbstractAdministratieveHandelingVerwerkingStap;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingMutatie;
import nl.bzk.brp.levering.mutatielevering.stappen.context.AdministratieveHandelingVerwerkingResultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import org.perf4j.aop.Profiled;

/**
 * Deze stap zorgt voor filtering in onderzoeken. Nu filtert het de gegevens in onderzoek die verwijzen naar ontbrekende gegevens.
 *
 * @brp.bedrijfsregel R2063
 * @brp.bedrijfsregel R2065
 */
@Regels({Regel.R2065, Regel.R2063})
public class OnderzoekenFilterStap extends AbstractAdministratieveHandelingVerwerkingStap {

    @Inject
    private OnderzoekenFilterService onderzoekenFilterService;

    @Override
    @Profiled(tag = "OnderzoekenFilterStap", logFailuresSeparately = true, level = "DEBUG")
    public final boolean voerStapUit(final AdministratieveHandelingMutatie onderwerp, final AdministratieveHandelingVerwerkingContext context,
        final AdministratieveHandelingVerwerkingResultaat resultaat)
    {
        onderzoekenFilterService.filterOnderzoekGegevensNaarOntbrekendeGegevens(context.getBijgehoudenPersonenVolledig());
        return DOORGAAN;
    }


}
