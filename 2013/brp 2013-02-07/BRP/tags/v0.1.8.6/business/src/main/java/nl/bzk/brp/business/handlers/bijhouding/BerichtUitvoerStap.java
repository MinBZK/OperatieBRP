/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.handlers.bijhouding;

import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.business.actie.AbstractActieUitvoerder;
import nl.bzk.brp.business.actie.ActieFactory;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtResultaat;
import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.business.handlers.AbstractBerichtVerwerkingsStap;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * De stap in de uitvoering van een bericht waarin de werkelijke executie van het bericht wordt uitgevoerd. In deze
 * stap wordt de door het bericht opgegeven verzoek uitgevoerd en het antwoord/resultaat geformuleerd/aangevuld.
 */
public class BerichtUitvoerStap extends AbstractBerichtVerwerkingsStap<AbstractBijhoudingsBericht, BerichtResultaat> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BerichtUitvoerStap.class);

    @Inject
    private ActieFactory actieFactory;

    @Override
    public boolean voerVerwerkingsStapUitVoorBericht(final AbstractBijhoudingsBericht bericht,
        final BerichtContext context, final BerichtResultaat resultaat)
    {
        if (resultaat.bevatVerwerkingStoppendeFouten()) {
            // MAG niet doorgaan als de status fout is.
            // Dit is een extra safety check, ga niet afhangen van de beslissing in de Berichtverwerker.
            return STOP_VERWERKING;
        } else {
            if (bericht.getBrpActies() != null) {
                for (Actie actie : bericht.getBrpActies()) {
                    AbstractActieUitvoerder uitvoerder = actieFactory.getActieUitvoerder(actie);
                    if (uitvoerder != null) {
                        List<Melding> actieMeldingen = uitvoerder.voerUit(actie, context);
                        if (actieMeldingen != null) {
                            resultaat.voegMeldingenToe(actieMeldingen);
                        }
                    } else {
                        LOGGER.error("Berichtverwerker kan geen ActieUitvoerder vinden voor actie: " + actie);

                        Melding melding =
                            new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0001,
                                "Systeem kan actie niet uitvoeren vanwege onbekende configuratie.");
                        resultaat.voegMeldingToe(melding);
                    }
                }
            }
            return DOORGAAN_MET_VERWERKING;
        }

    }
}
