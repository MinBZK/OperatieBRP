/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.synchronisatie.stappen.persoon;

import javax.inject.Inject;
import nl.bzk.brp.levering.business.bericht.BerichtFactory;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.synchronisatie.dto.synchronisatie.SynchronisatieBerichtContext;
import nl.bzk.brp.levering.synchronisatie.dto.synchronisatie.SynchronisatieResultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.bericht.ber.BerichtParametersGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.levering.VolledigBericht;
import nl.bzk.brp.model.synchronisatie.GeefSynchronisatiePersoonBericht;
import nl.bzk.brp.webservice.business.stappen.AbstractBerichtVerwerkingStap;

/**
 * De stap waarin de stuurgegevens toegevoegd worden.
 */
public class VoegStuurgegevensToeStap extends AbstractBerichtVerwerkingStap<GeefSynchronisatiePersoonBericht,
        SynchronisatieBerichtContext, SynchronisatieResultaat>
{
    @Inject
    private BerichtFactory berichtFactory;

    @Override
    public final boolean voerStapUit(final GeefSynchronisatiePersoonBericht onderwerp,
                               final SynchronisatieBerichtContext context,
                               final SynchronisatieResultaat resultaat)
    {

        final VolledigBericht bericht = context.getVolledigBericht();
        final Partij partij = onderwerp.getStuurgegevens().getZendendePartij().getWaarde();
        final String referentieNummer = onderwerp.getStuurgegevens().getReferentienummer().getWaarde();

        final Leveringinformatie leveringAutorisatie = context.getLeveringinformatie();

        final BerichtStuurgegevensGroepBericht stuurgegevensGroepBericht = berichtFactory.maakStuurgegevens(partij,
                referentieNummer);
        final BerichtParametersGroepBericht parametersGroepBericht =
                berichtFactory.maakParameters(leveringAutorisatie, SoortSynchronisatie.VOLLEDIGBERICHT);

        bericht.setStuurgegevens(stuurgegevensGroepBericht);
        bericht.setBerichtParameters(parametersGroepBericht);

        return DOORGAAN;
    }
}
