/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.service;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import nl.bzk.brp.binding.BRPBericht;
import nl.bzk.brp.binding.BerichtResultaat;
import nl.bzk.brp.binding.Melding;
import nl.bzk.brp.binding.MeldingCode;
import nl.bzk.brp.binding.SoortMelding;
import nl.bzk.brp.business.actie.ActieFactory;
import nl.bzk.brp.business.actie.ActieUitvoerder;
import nl.bzk.brp.model.logisch.BRPActie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Standaard implementatie van de BerichtVerwerker.
 */
@Service
public class BerichtVerwerkerImpl implements BerichtVerwerker {

    private static final Logger LOGGER = LoggerFactory.getLogger(BerichtVerwerkerImpl.class);

    @Inject
    private ActieFactory actieFactory;

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public BerichtResultaat verwerkBericht(final BRPBericht bericht) {
        LOGGER.debug("Berichtverwerker start verwerking bericht: " + bericht);

        List<Melding> meldingen = null;
        try {
            if (bericht.getBrpActies() != null) {
                for (BRPActie actie : bericht.getBrpActies()) {
                    if (meldingen == null) {
                        meldingen = new ArrayList<Melding>();
                    }
                    meldingen.addAll(voerActieUit(actie));
                }
            }
        } catch (Throwable t) {
            LOGGER.error("Fout opgetreden in de bericht verwerking.", t);
            meldingen = new ArrayList<Melding>();
            meldingen.add(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0001));
        }

        return new BerichtResultaat(meldingen);
    }

    /**
     * Executeert de actie, door de juiste {@link ActieUitvoerder} instantie op te halen, en retourneert de meldingen
     * die zijn opgetreden tijdens het uitvoeren van de actie. Indien er geen meldingen zijn, wordt er een lege lijst
     * van meldingen geretourneerd.
     *
     * @param actie de actie die moet worden doorgevoerd.
     * @return de meldingen die zijn opgetreden tijdens het uitvoeren van de actie.
     */
    private List<Melding> voerActieUit(final BRPActie actie) {
        ActieUitvoerder uitvoerder = actieFactory.getActieUitvoerder(actie);
        List<Melding> meldingen = new ArrayList<Melding>();

        if (uitvoerder != null) {
            List<Melding> actieMeldingen = uitvoerder.voerUit(actie);
            if (actieMeldingen != null) {
                meldingen.addAll(actieMeldingen);
            }
        } else {
            LOGGER.error("Berichtverwerker kan geen ActieUitvoerder vinden voor actie: " + actie);

            Melding melding = new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0001,
                    "Systeem kan actie niet uitvoeren vanwege onbekende configuratie.");
            meldingen.add(melding);
        }
        return meldingen;
    }

}
