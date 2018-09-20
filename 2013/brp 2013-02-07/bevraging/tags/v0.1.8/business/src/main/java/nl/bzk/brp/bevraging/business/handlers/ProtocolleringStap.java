/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;


import javax.inject.Inject;

import nl.bzk.brp.bevraging.business.dto.BerichtContext;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtAntwoord;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFout;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFoutCode;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtVerwerkingsFoutZwaarte;
import nl.bzk.brp.bevraging.business.dto.verzoek.BerichtVerzoek;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * De stap in de uitvoering van een bericht waarin de protocollering van een bericht wordt afgehandeld.
 */
public class ProtocolleringStap extends AbstractBerichtVerwerkingsStap {

    private static final Logger        LOGGER = LoggerFactory.getLogger(ProtocolleringStap.class);

    @Inject
    private ProtocolleringStapExecutorInterface executor;

    /**
     * {@inheritDoc}
     *
     * Protocolleert het BerichtCommand in de tabellen, Levering, Levering/Persoon.
     *
     * Merk op dat dit gebeurd door de geinjecteerde (en dus gemanagde) instantie van de class
     * {@link ProtocolleringStapExecutor} aan te roepen. Dit daar de transactie van de protocollering zelf door Spring
     * moet worden gemanaged, maar dat excepties hierin wel door deze class moeten worden afgehandeld. Dit gaat
     * niet door de code van de {@link ProtocolleringStapExecutor} direct in deze methode op te nemen met een
     * <code>try { ... } catch { ... }</code> block er om heen, daar de flush (die de fout oplevert) pas gebeurd
     * bij het committen van de transactie, welke pas wordt uitgevoerd als de methode is verlaten (en dus niet
     * in de methode). Het aanroepen vanuit deze methode van een andere methode in dezelfde class kan ook niet,
     * daar deze standaard dan niet door Spring worden geproxied, wat nodig is voor de transactie management.
     */
    @Override
    public final <T extends BerichtAntwoord> boolean voerVerwerkingsStapUitVoorBericht(final BerichtVerzoek<T> verzoek,
            final BerichtContext context, final T antwoord)
    {
        try {
            // Zie javadoc waarom de code van de levering via een geinjecteerde inner class wordt aangeroepen.
            Long leveringId = executor.protocolleer(antwoord.getPersonen(), context, verzoek.getBeschouwing());
            antwoord.setLeveringId(leveringId);

            return DOORGAAN_MET_VERWERKING;
        } catch (Throwable t) {
            LOGGER.error("Fout opgetreden bij de protocollering; bericht wordt niet verder verwerkt en fout wordt "
                + "verstuurd.", t);
            antwoord.voegFoutToe(new BerichtVerwerkingsFout(
                    BerichtVerwerkingsFoutCode.BRVE0008_01_PROTOCOLLERING_MISLUKT, BerichtVerwerkingsFoutZwaarte.FOUT));
            return STOP_VERWERKING;
        }
    }
}
