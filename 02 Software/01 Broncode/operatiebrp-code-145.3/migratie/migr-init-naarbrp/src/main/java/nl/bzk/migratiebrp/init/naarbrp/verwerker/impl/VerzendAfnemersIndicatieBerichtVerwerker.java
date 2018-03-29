/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarbrp.verwerker.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import javax.jms.Destination;
import javax.jms.Message;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.logging.MDCProcessor;
import nl.bzk.migratiebrp.bericht.model.JMSConstants;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBerichtType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AfnemersindicatiesBericht;
import nl.bzk.migratiebrp.init.naarbrp.domein.ConversieResultaat;
import nl.bzk.migratiebrp.init.naarbrp.repository.AfnemersIndicatieRepository;
import nl.bzk.migratiebrp.init.naarbrp.verwerker.BerichtVerwerker;
import nl.bzk.migratiebrp.util.common.logging.InitieleVullingVeld;
import org.springframework.jms.core.JmsTemplate;

/**
 * Verstuurt AfnemersindicatiesBerichten.
 */
public class VerzendAfnemersIndicatieBerichtVerwerker implements BerichtVerwerker<AfnemersindicatiesBericht> {

    private static final String BERICHTEN_ZIJN_AL_VERZONDEN = "Berichten zijn al verzonden";
    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String MSG_ID_FORMAT = "%s-%s";

    private final AfnemersIndicatieRepository afnemersIndicatieRepository;
    private final List<AfnemersindicatiesBericht> afnemersIndicatieBerichten;
    private final Destination destination;
    private final JmsTemplate jmsTemplate;
    private final AtomicLong aantalVerzonden = new AtomicLong(0);
    private boolean verzonden;

    /**
     * Constructor.
     * @param destination {@link Destination} queue waarop berichten geplaatst moeten worden.
     * @param jmsTemplate {@link JmsTemplate}
     * @param afnemersIndicatieRepository De AfnemersIndicatieRepository waaruit de afnemersindicaties moeten worden gelezen
     */
    public VerzendAfnemersIndicatieBerichtVerwerker(final Destination destination, final JmsTemplate jmsTemplate,
                                                    final AfnemersIndicatieRepository afnemersIndicatieRepository) {
        afnemersIndicatieBerichten = new ArrayList<>();
        this.destination = destination;
        this.jmsTemplate = jmsTemplate;
        this.afnemersIndicatieRepository = afnemersIndicatieRepository;
    }

    @Override
    public final void voegBerichtToe(final AfnemersindicatiesBericht afnemersIndicatieBericht) {
        if (verzonden) {
            throw new IllegalStateException(BERICHTEN_ZIJN_AL_VERZONDEN);
        }
        afnemersIndicatieBerichten.add(afnemersIndicatieBericht);
    }

    @Override
    public final int aantalBerichten() {
        return afnemersIndicatieBerichten.size();
    }

    @Override
    public final long aantalVerzonden() {
        return aantalVerzonden.get();
    }

    @Override
    public final void verwerkBerichten() {
        if (verzonden) {
            throw new IllegalStateException(BERICHTEN_ZIJN_AL_VERZONDEN);
        }
        verzonden = true;

        LOG.debug("Aantal te verwerken afnemersindicaties berichten: {}", afnemersIndicatieBerichten.size());

        if (!afnemersIndicatieBerichten.isEmpty()) {
            LOG.info("Versturen berichten");
            final List<String> aNummers = new ArrayList<>(afnemersIndicatieBerichten.size());
            verstuurAfnemersIndicatieBericht(aNummers, afnemersIndicatieBerichten);

            LOG.info("Update status naar verzonden");
            afnemersIndicatieRepository.updateAfnemersIndicatiesBerichtStatus(aNummers, ConversieResultaat.VERZONDEN);
        }
        LOG.info("Berichten verwerkt");
    }

    private void verstuurAfnemersIndicatieBericht(final List<String> aNummers, final List<AfnemersindicatiesBericht> afnemersIndicatiesBerichten) {
        jmsTemplate.execute(destination, (session, producer) -> {
            for (final AfnemersindicatiesBericht afnIndBericht : afnemersIndicatiesBerichten) {
                final String aNummer = afnIndBericht.getAfnemersindicaties().getANummer();
                MDCProcessor.startVerwerking().metVeld(InitieleVullingVeld.MDC_ADMINISTRATIE_NUMMER, aNummer).run(() -> {
                    final Message message = session.createTextMessage(afnIndBericht.format());
                    MDCProcessor.registreerVerwerkingsCode(message);
                    message.setStringProperty(JMSConstants.BERICHT_REFERENTIE,
                            String.format(MSG_ID_FORMAT, SyncBerichtType.AFNEMERINDICATIE.getType(), aNummer));
                    producer.send(message);
                    aNummers.add(aNummer);
                    aantalVerzonden.getAndIncrement();
                });
            }
            return null;
        });
    }
}
