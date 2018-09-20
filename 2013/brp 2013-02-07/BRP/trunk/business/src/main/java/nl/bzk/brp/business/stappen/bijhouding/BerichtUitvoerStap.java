/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.bijhouding;

import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.business.actie.AbstractActieUitvoerder;
import nl.bzk.brp.business.actie.ActieFactory;
import nl.bzk.brp.business.stappen.BerichtContext;
import nl.bzk.brp.business.stappen.BerichtVerwerkingsResultaat;
import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.business.stappen.AbstractBerichtVerwerkingsStap;
import nl.bzk.brp.dataaccess.repository.AdministratieveHandelingRepository;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * De stap in de uitvoering van een bericht waarin de werkelijke executie van het bericht wordt uitgevoerd. In deze
 * stap wordt de door het bericht opgegeven verzoek uitgevoerd en het antwoord/resultaat geformuleerd/aangevuld.
 */
public class BerichtUitvoerStap
        extends AbstractBerichtVerwerkingsStap<AbstractBijhoudingsBericht, BerichtVerwerkingsResultaat>
{

    private static final Logger LOGGER = LoggerFactory.getLogger(BerichtUitvoerStap.class);

    @Inject
    private ActieFactory actieFactory;

    @Inject
    private AdministratieveHandelingRepository administratieveHandelingRepository;

    @Override
    public boolean voerVerwerkingsStapUitVoorBericht(final AbstractBijhoudingsBericht bericht,
                                                     final BerichtContext context,
                                                     final BerichtVerwerkingsResultaat resultaat)
    {
        if (resultaat.bevatVerwerkingStoppendeFouten()) {
            // MAG niet doorgaan als de status fout is.
            // Dit is een extra safety check, ga niet afhangen van de beslissing in de Berichtverwerker.
            return STOP_VERWERKING;
        } else {
            // sla de adminstratieve handeling op
            if (bericht.getAdministratieveHandeling() != null) {
                // TODO: als AdmHand subtypes heeft hoeft de soort adm hand. niet gezet te worden met de handeling in bericht
                AdministratieveHandelingBericht administratieveHandelingBericht = bericht.getAdministratieveHandeling();
//                if (administratieveHandelingBericht.getSoort() == null) {
//                    administratieveHandelingBericht.setSoort(SoortAdministratieveHandeling.REGISTRATIE_OVERLIJDEN_NEDERLAND);
//                }
                AdministratieveHandelingModel administratieveHandelingModel =
                        administratieveHandelingRepository
                                .opslaanNieuwAdministratieveHandeling(administratieveHandelingBericht);
                administratieveHandelingBericht
                        .setTechnischeSleutel(administratieveHandelingModel.getTechnischeSleutel());

                if (bericht.getAdministratieveHandeling().getActies() != null) {
                    for (Actie actie : bericht.getAdministratieveHandeling().getActies()) {
                        AbstractActieUitvoerder uitvoerder = actieFactory.getActieUitvoerder(actie);
                        if (uitvoerder != null) {
                            // id of de hele administratieve handeling meegeven?
                            List<Melding> actieMeldingen =
                                    uitvoerder.voerUit(actie, context, administratieveHandelingModel);
                            if (actieMeldingen != null) {
                                resultaat.voegMeldingenToe(actieMeldingen);
                            }
                        } else {
                            LOGGER.error("Berichtverwerker kan geen ActieUitvoerder vinden voor actie: " + actie);

                            Melding melding =
                                    new Melding(SoortMelding.FOUT, MeldingCode.ALG0001,
                                                "Systeem kan actie niet uitvoeren vanwege onbekende configuratie.");
                            resultaat.voegMeldingToe(melding);
                        }
                    }
                }
            }
            return DOORGAAN_MET_VERWERKING;
        }

    }

    @Override
    public boolean voerStapUit(final AbstractBijhoudingsBericht onderwerp, final BerichtContext context,
                               final BerichtVerwerkingsResultaat resultaat)
    {
        return voerVerwerkingsStapUitVoorBericht(onderwerp, context,resultaat);
    }

}
