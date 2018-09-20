/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.service.impl;

import java.util.Collections;
import java.util.List;

import nl.bzk.brp.bevraging.business.berichtcmds.BrpBerichtCommand;
import nl.bzk.brp.bevraging.business.dto.BerichtVerwerkingsFout;
import nl.bzk.brp.bevraging.business.dto.BerichtVerwerkingsFoutCode;
import nl.bzk.brp.bevraging.business.dto.BerichtVerwerkingsFoutZwaarte;
import nl.bzk.brp.bevraging.business.dto.antwoord.BRPAntwoord;
import nl.bzk.brp.bevraging.business.dto.verzoek.BRPVerzoek;
import nl.bzk.brp.bevraging.business.handlers.BerichtVerwerkingsStap;
import nl.bzk.brp.bevraging.business.service.BerichtUitvoerderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * Standaard service implementatie voor het uitvoeren van een bericht. Deze implementatie voert sequentieel de
 * verschillende (standaard) stappen uit die doorlopen moeten worden voor alle berichten. Een van deze stappen is de
 * werkelijke uitvoering van het bericht, waarbij de overige stappen zaken regelen als protocollering, autorisatie etc.
 */
@Service
public class StandaardBerichtUitvoerderService implements BerichtUitvoerderService {

    private static final Logger          LOGGER = LoggerFactory.getLogger(StandaardBerichtUitvoerderService.class);

    private List<BerichtVerwerkingsStap> stappen;

    /**
     * {@inheritDoc} <br/>
     * Hiervoor wordt elke afzonderlijke stap uitgevoerd, tot verwerking eindigt (functioneel of exceptioneel), waarna
     * daarna voor elke uitgevoerde stap de naverwerking wordt aangeroepen.
     */
    @Override
    public final void voerBerichtUit(final BrpBerichtCommand bericht) {
        if (bericht == null) {
            LOGGER.error("Bericht dat moet worden uitgevoerd was null");
            throw new IllegalArgumentException("Bericht is leeg");
        }

        if (!isBerichtCorrectGeinitialiseerd(bericht)) {
            bericht.voegFoutToe(new BerichtVerwerkingsFout(BerichtVerwerkingsFoutCode.ONBEKENDE_FOUT,
                    BerichtVerwerkingsFoutZwaarte.SYSTEEM, "Bericht niet correct geinitialiseerd door het systeem."));
        } else {
            int laatsteStapIndex = voerStappenUit(bericht);
            voerNaVerwerkingStappenUit(laatsteStapIndex, bericht);
        }
    }

    /**
     * Controleert of het bericht correct is geinitialiseerd: het dient daarvoor te beschikken over een context en een
     * verzoek object. Tevens dient de context te zijn voorzien van een partij id en een bericht id.
     *
     * @param bericht het bericht dat wordt gecontroleerd.
     * @return een indicatie of het bericht correct is geinitialiseerd of niet.
     */
    private boolean isBerichtCorrectGeinitialiseerd(final BrpBerichtCommand bericht) {
        return bericht.getContext() != null && bericht.getVerzoek() != null
            && bericht.getContext().getPartijId() != null && bericht.getContext().getBerichtId() != null;
    }

    /**
     * Voert de verschillende stappen uit, waarbij elke stap afzonderlijk wordt aangeroepen met het bericht dat
     * verwerkt dient te worden. De verwerking gaat door totdat een stap retourneert dat de verwerking dient
     * te stoppen, een exceptie gooit of totdat alle stappen zijn doorlopen. De index van de laatst verwerkte
     * stap wordt geretourneerd.
     *
     * @param bericht het bericht dat dient verwerkt te worden.
     * @return de index van de laatst verwerkte stap.
     */
    private int voerStappenUit(final BrpBerichtCommand<? extends BRPVerzoek, ? extends BRPAntwoord> bericht) {
        int stapInUitvoering = -1;

        if (stappen == null) {
            StandaardBerichtUitvoerderService.LOGGER.warn("Geen stappen geconfigureerd om uit te voeren");
        } else {
            for (BerichtVerwerkingsStap stap : stappen) {
                stapInUitvoering++;
                try {
                    if (stap.voerVerwerkingsStapUitVoorBericht(bericht) == BerichtVerwerkingsStap.STOP_VERWERKING) {
                        StandaardBerichtUitvoerderService.LOGGER.info(
                                "Logische fout/stop in uitvoerende stap {} gedetecteerd.", stapInUitvoering);
                        break;
                    }
                } catch (Throwable t) {
                    bericht.voegFoutToe(new BerichtVerwerkingsFout(BerichtVerwerkingsFoutCode.ONBEKENDE_FOUT,
                            BerichtVerwerkingsFoutZwaarte.SYSTEEM, "Onbekende Fout opgetreden"));
                    StandaardBerichtUitvoerderService.LOGGER.error(
                            "Onbekende fout in uitvoerende stap {} gedetecteerd.", stapInUitvoering, t);
                    break;
                }
            }
        }
        return stapInUitvoering;
    }

    /**
     * Voor alle stappen vanaf de opgegeven index en terug naar de eerste stap, wordt de naverwerking van die stap
     * uitgevoerd. Eventuele excepties in de naverwerking van een stap worden gelogd en als fout toegevoegd aan het
     * bericht. Let wel dat als een exceptie optreedt in de naverwerking van een stap, deze wordt afgevangen en de
     * nog resterende naverwerkingsstappen nog steeds worden uitgevoerd.
     *
     * @param laatsteStapIndex index van de als laatste uitgevoerde stap en waarmee qua naverwerking dus begonnen
     *            moet worden.
     * @param bericht bericht wat wordt uitgevoerd.
     */
    private void voerNaVerwerkingStappenUit(final int laatsteStapIndex, final BrpBerichtCommand bericht) {
        for (int i = laatsteStapIndex; i >= 0; i--) {
            try {
                stappen.get(i).naVerwerkingsStapVoorBericht(bericht);
            } catch (Throwable t) {
                bericht.voegFoutToe(new BerichtVerwerkingsFout(BerichtVerwerkingsFoutCode.ONBEKENDE_FOUT,
                        BerichtVerwerkingsFoutZwaarte.INFO, "Onbekende Fout opgetreden"));
                StandaardBerichtUitvoerderService.LOGGER.error(
                        "Onbekende fout in naverwerking van stap {} gedetecteerd.", i, t);
            }
        }
    }

    /**
     * Retourneert een onaanpasbare lijst van de stappen die deze service doorloopt bij het verwerking van een bericht.
     *
     * @return de stappen.
     */
    public List<BerichtVerwerkingsStap> getStappen() {
        if (stappen == null) {
            return null;
        }
        return Collections.unmodifiableList(stappen);
    }

    /**
     * Zet de stappen die de service doorloopt in de verwerking van een bericht.
     *
     * @param stappen de stappen die de service doorloopt in de verwerking van een bericht.
     */
    public void setStappen(final List<BerichtVerwerkingsStap> stappen) {
        this.stappen = stappen;
    }

}
