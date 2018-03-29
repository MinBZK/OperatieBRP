/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.jmxcollector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.management.MalformedObjectNameException;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Metingen die worden opgehaald en doorgestuurd.
 *
 * @TODO AverageMessageSize, MaxMessageSize, MinMessageSize, AverageEnqueueTime, MaxEnqueueTime, MinEnqueueTime
 */
@Component
class Metingen {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String BRP_MESSAGEBROKER_JMX_CONNECTOR = "brpMessagebrokerJmxConnector";
    private static final String BRP_MESSAGEBROKER_QUEUE =
            "nl.bzk.brp:type=Broker,brokerName=BrpMessageBroker,destinationType=Queue,destinationName=%s";
    private static final String ISC_MESSAGEBROKER_JMX_CONNECTOR = "routeringJmxConnector";
    private static final String ISC_MESSAGEBROKER_QUEUE =
            "org.apache.activemq:type=Broker,brokerName=routeringCentrale,destinationType=Queue,destinationName=%s";
    private static final String ISC_MESSAGEBROKER_TOPIC =
            "org.apache.activemq:type=Broker,brokerName=routeringCentrale,destinationType=Topic,destinationName=%s";

    private final Map<String, List<Meting>> metingenMap;

    @Inject
    Metingen() throws MalformedObjectNameException {
        metingenMap = new HashMap<>();
        metingenMap.put(BRP_MESSAGEBROKER_JMX_CONNECTOR, new ArrayList<>());
        Arrays.asList(
                "ArchiveringQueue",
                "AdmhnPublicatieQueue",
                "GbaToevalligeGebeurtenissen",
                "GbaToevalligeGebeurtenissenAntwoorden",
                "ProtocolleringQueue",
                "SelectieTaakQueue",
                "SelectieTaakResultaatQueue",
                "SelectieSchrijfTaakQueue",
                "VerzendingAfnemerQueue",
                "VrijBerichtQueue",
                "GbaAfnemerindicaties",
                "GbaAfnemerindicatiesAntwoorden",
                "GbaAdhocAdresvraagVerzoek",
                "GbaAdhocAdresvraagAntwoord",
                "GbaAdhocPersoonsvraagVerzoek",
                "GbaAdhocPersoonsvraagAntwoord"
        ).forEach(queueNaam -> {
            try {
                voegMetingenToe(BRP_MESSAGEBROKER_JMX_CONNECTOR, queueNaam, BRP_MESSAGEBROKER_QUEUE);
            } catch (MalformedObjectNameException e) {
                LOG.warn("Gevraagde meting kan niet worden aangemaakt", e);
            }
        });

        metingenMap.put(ISC_MESSAGEBROKER_JMX_CONNECTOR, new ArrayList<>());
        Arrays.asList(
                "sync.verzoek",
                "sync.verzoek.dlq",
                "sync.antwoord",
                "sync.antwoord.dlq",
                "voisc.ontvangst",
                "voisc.ontvangst.dlq",
                "voisc.verzenden",
                "voisc.verzenden.dlq",
                "levering",
                "levering.dlq",
                "archivering",
                "archivering.dlq",
                "gemeente.verzoek",
                "gemeente.verzoek.dlq",
                "autorisatie.verzoek",
                "autorisatie.verzoek.dlq").forEach(queueNaam -> {
            try {
                voegMetingenToe(ISC_MESSAGEBROKER_JMX_CONNECTOR, queueNaam, ISC_MESSAGEBROKER_QUEUE);
            } catch (MalformedObjectNameException e) {
                LOG.warn("Gevraagde meting kan niet worden aangemaakt", e);
            }
        });
        Arrays.asList(
                "autorisatie.register",
                "gemeente.register").forEach(queueNaam -> {
            try {
                voegMetingenToe(ISC_MESSAGEBROKER_JMX_CONNECTOR, queueNaam, ISC_MESSAGEBROKER_TOPIC);
            } catch (MalformedObjectNameException e) {
                LOG.warn("Gevraagde meting kan niet worden aangemaakt", e);
            }
        });


    }

    private void voegMetingenToe(final String server, final String queueNaam, final String jmxObject) throws MalformedObjectNameException {
        metingenMap.get(server).add(
                new Meting(queueNaam,
                        String.format(jmxObject, queueNaam),
                        "QueueSize", "EnqueueCount", "DequeueCount",
                        "AverageMessageSize", "MaxMessageSize", "MinMessageSize",
                        "AverageEnqueueTime", "MaxEnqueueTime", "MinEnqueueTime"));
    }

    Map<String, List<Meting>> getMetingenMap() {
        return metingenMap;
    }
}
