/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.stappen.persoon;

import javax.inject.Inject;
import nl.bzk.brp.levering.afnemerindicaties.service.OnderhoudAfnemerindicatiesBerichtContext;
import nl.bzk.brp.levering.afnemerindicaties.service.OnderhoudAfnemerindicatiesResultaat;
import nl.bzk.brp.levering.business.bericht.BerichtFactory;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.levering.VolledigBericht;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.synchronisatie.RegistreerAfnemerindicatieBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.webservice.business.stappen.AbstractBerichtVerwerkingStap;


/**
 * De stap waarin het maximale bericht wordt gemaakt. Deze kan in verdere stappen gefilterd worden.
 */
@Regels(Regel.R1350)
public class MaakMaxBerichtStap extends AbstractBerichtVerwerkingStap<RegistreerAfnemerindicatieBericht,
        OnderhoudAfnemerindicatiesBerichtContext, OnderhoudAfnemerindicatiesResultaat>
{

    @Inject
    private BerichtFactory berichtFactory;

    @Override
    public final boolean voerStapUit(final RegistreerAfnemerindicatieBericht onderwerp,
                               final OnderhoudAfnemerindicatiesBerichtContext context,
                               final OnderhoudAfnemerindicatiesResultaat resultaat)
    {
        if (SoortAdministratieveHandeling.VERWIJDERING_AFNEMERINDICATIE
                == onderwerp.getAdministratieveHandeling().getSoort().getWaarde())
        {
            // Voor verwijderen afnemerindicatie sturen we geen bericht, het verwerken kan stoppen
            return STOPPEN;
        }

        final PersoonBericht persoonBericht = (PersoonBericht) onderwerp.getAdministratieveHandeling().getHoofdActie().getRootObject();
        final DatumEvtDeelsOnbekendAttribuut datumAanvangMaterielePeriode =
                persoonBericht.getAfnemerindicaties().get(0).getStandaard().getDatumAanvangMaterielePeriode();

        final Leveringinformatie leveringAutorisatie = context.getLeveringinformatie();

        final PartijAttribuut afnemer = new PartijAttribuut(context.getZendendePartij());
        final AdministratieveHandelingModel administratieveHandelingModel =
                new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
                        SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE), afnemer, null, DatumTijdAttribuut.nu());

        DatumAttribuut materieelVanaf = null;
        if (datumAanvangMaterielePeriode != null) {
            materieelVanaf = new DatumAttribuut(datumAanvangMaterielePeriode);
        }
        final VolledigBericht bericht = berichtFactory.maakVolledigBericht(context.getPersoonHisVolledig(), leveringAutorisatie,
                                                                           administratieveHandelingModel,
                                                                           materieelVanaf);

        if (bericht != null) {
            context.setVolledigBericht(bericht);
            return DOORGAAN;
        } else {
            resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.ALG0001, "Het bericht kon niet worden gemaakt."));
            return STOPPEN;
        }
    }
}
