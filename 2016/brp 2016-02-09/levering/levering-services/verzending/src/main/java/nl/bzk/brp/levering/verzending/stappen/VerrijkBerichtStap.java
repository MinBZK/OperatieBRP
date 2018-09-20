/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.verzending.stappen;

import javax.jms.Message;
import nl.bzk.brp.levering.algemeen.LeveringConstanten;
import nl.bzk.brp.levering.verzending.context.BerichtContext;
import nl.bzk.brp.logging.MDC;
import nl.bzk.brp.logging.MDCVeld;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.internbericht.SynchronisatieBerichtGegevens;
import nl.bzk.brp.serialisatie.JsonStringSerializer;
import org.perf4j.aop.Profiled;
import org.springframework.stereotype.Component;

/**
 * Voorbereiding van de bericht afhandeling door de gegevens uit het bericht om te zetten in Java POJO's en die op de
 * Exchange door te geven. Vervolgens nog verrijking van de berichtinformatie door bijvoorbeeld de Uri van het kanaal
 * op te halen uit de database
 *
 * @brp.bedrijfsregel R1268
 */
@Regels(Regel.R1268)
@Component
public class VerrijkBerichtStap {

    private final JsonStringSerializer<SynchronisatieBerichtGegevens> jsonStringSerialiseerder =
        new JsonStringSerializer<>(SynchronisatieBerichtGegevens.class);

    /**
     * Verrijkt het bericht.
     *
     * @param berichtContext bericht context
     * @throws Exception mogelijke exceptie
     */
    @Profiled(tag = "VerrijkBerichtStap", logFailuresSeparately = true, level = "DEBUG")
    public void process(final BerichtContext berichtContext) throws Exception {
        final Message jmsBericht = berichtContext.getJmsBericht();

        final String xmlBericht =
            jmsBericht.getStringProperty(LeveringConstanten.JMS_PROPERTY_KEY_BERICHT_GEGEVENS);
        final SynchronisatieBerichtGegevens berichtGegevens =
            jsonStringSerialiseerder.deserialiseerVanuitString(xmlBericht);
        berichtContext.setSynchronisatieBerichtGegevens(berichtGegevens);

        MDC.put(MDCVeld.MDC_ADMINISTRATIEVE_HANDELING,
            String.valueOf(berichtGegevens.getAdministratieveHandelingId()));
        MDC.put(MDCVeld.MDC_REFERENTIE_NUMMER,
            String.valueOf(berichtGegevens.getStuurgegevens().getReferentienummer().getWaarde()));
        MDC.put(MDCVeld.MDC_TOEGANG_LEVERINGSAUTORISATIE_ID, String.valueOf(berichtGegevens.getToegangLeveringsautorisatieId()));



        // TODO Controleer of de endpoint-uri de juiste informatie geeft voor een beheerder om te kunnen herzenden en deze ook meegaat naar de DLQ.
//        final String endpointUri = exchange.getFromEndpoint().getEndpointUri();
//        jmsBericht.setHeader(AfnemerVerzendingRouteBuilder.JMS_PROPERTY_QUEUE_NAAM, endpointUri);
    }
}
