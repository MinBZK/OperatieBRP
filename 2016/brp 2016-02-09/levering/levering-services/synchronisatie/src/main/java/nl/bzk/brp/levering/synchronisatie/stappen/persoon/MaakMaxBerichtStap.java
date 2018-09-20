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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.levering.VolledigBericht;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.synchronisatie.GeefSynchronisatiePersoonBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.webservice.business.stappen.AbstractBerichtVerwerkingStap;

/**
 * De stap waarin het maximale bericht wordt gemaakt. Deze kan in verdere stappen gefilterd worden.
 */
@Regels(Regel.R1982)
public class MaakMaxBerichtStap extends AbstractBerichtVerwerkingStap<GeefSynchronisatiePersoonBericht,
        SynchronisatieBerichtContext, SynchronisatieResultaat>
{

    @Inject
    private BerichtFactory berichtFactory;

    @Override
    public final boolean voerStapUit(final GeefSynchronisatiePersoonBericht onderwerp,
                               final SynchronisatieBerichtContext context,
                               final SynchronisatieResultaat resultaat)
    {
        final Leveringinformatie leveringAutorisatie = context.getLeveringinformatie();

        final AdministratieveHandelingModel administratieveHandelingModel = new AdministratieveHandelingModel(new
                SoortAdministratieveHandelingAttribuut(SoortAdministratieveHandeling.SYNCHRONISATIE_PERSOON),
                onderwerp.getStuurgegevens().getZendendePartij(), null,
                DatumTijdAttribuut.nu()
        );

        final VolledigBericht bericht = berichtFactory.maakVolledigBericht(context.getPersoonHisVolledig(),
                                                                           leveringAutorisatie,
                                                                           administratieveHandelingModel, null);

        if (bericht != null) {
            context.setVolledigBericht(bericht);
            return DOORGAAN;
        } else {
            resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.ALG0001,
                    "Het maximale bericht kon niet worden gemaakt."));
            return STOPPEN;
        }
    }
}
