/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.jmxcollector;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * De meter vericht op gezette intervallen de metingen gedefineerd in Metingen.
 */
@Component
class Meter {

    private static final Logger LOG = LoggerFactory.getLogger();
    private final ApplicationContext context;
    private final Metingen metingen;
    private final String graphiteHost;
    private final Integer graphitePort;

    @Inject
    Meter(final ApplicationContext context,
          final Metingen metingen,
          @Value("${graphite.host}") final String graphiteHost,
          @Value("${graphite.port}") final Integer graphitePort) {
        this.context = context;
        this.metingen = metingen;
        LOG.info("Aantal servers die gelezen wordt: " + metingen.getMetingenMap().size());
        this.graphiteHost = graphiteHost;
        this.graphitePort = graphitePort;
    }

    @Scheduled(fixedDelay = 2000)
    void voerMetingenUit() {
        for (final String server : metingen.getMetingenMap().keySet()) {
            LOG.info(String.format("Verwerken %s", server));
            try(final JMXConnector connector = context.getBean(server, JMXConnector.class)) {
                connector.connect();
                final MBeanServerConnection connectie = connector.getMBeanServerConnection();
                final List<MetingResultaat> meterResultaten =
                        metingen.getMetingenMap()
                                .get(server)
                                .stream()
                                .map(meting -> voerMetingUit(connectie, meting))
                                .flatMap(Collection::stream)
                                .collect(Collectors.toList());
                if (!meterResultaten.isEmpty()) {
                    verstuurMeetResultaten(meterResultaten);
                }
            } catch (final IOException e) {
                LOG.warn(String.format("Server %s is niet bereikbaar", server));
                LOG.debug(String.format("Server %s is niet bereikbaar", server), e);
            }
            LOG.info(String.format("%s verwerkt", server));
        }
    }

    private List<MetingResultaat> voerMetingUit(final MBeanServerConnection connector, final Meting meting) {
        try {
            return meting.haalMetingOp(connector);
        } catch (MetingException e) {
            LOG.warn(String.format("Meting %s is mislukt", meting.getNaam()));
            LOG.debug(String.format("Meting %s is mislukt", meting.getNaam()), e);
        }
        return new ArrayList<>();
    }

    private void verstuurMeetResultaten(final List<MetingResultaat> meterResultaten) {
        try (Socket socket = new Socket(graphiteHost, graphitePort);
             Writer writer = new OutputStreamWriter(socket.getOutputStream(), Charset.forName("UTF-8"))) {
            for (final MetingResultaat meting : meterResultaten) {
                LOG.debug("Resultaat: " + meting.getResultaat());
                writer.write(meting.getResultaat());
            }
            writer.flush();
        } catch (IOException e) {
            LOG.warn("Meting kon niet worden verstuurd naar de server");
            LOG.debug("Meting kon niet worden verstuurd naar de server", e);
        }
    }
}
