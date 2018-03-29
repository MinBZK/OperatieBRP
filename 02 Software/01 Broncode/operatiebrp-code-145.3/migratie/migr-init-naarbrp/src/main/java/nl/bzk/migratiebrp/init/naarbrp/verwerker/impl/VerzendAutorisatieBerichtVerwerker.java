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
import nl.bzk.migratiebrp.bericht.model.sync.impl.AutorisatieBericht;
import nl.bzk.migratiebrp.init.naarbrp.domein.ConversieResultaat;
import nl.bzk.migratiebrp.init.naarbrp.repository.AutorisatieRepository;
import nl.bzk.migratiebrp.init.naarbrp.verwerker.BerichtVerwerker;
import nl.bzk.migratiebrp.util.common.logging.InitieleVullingVeld;
import org.springframework.jms.core.JmsTemplate;

/**
 * Verstuurt AutorisatieBerichten.
 */
public class VerzendAutorisatieBerichtVerwerker implements BerichtVerwerker<AutorisatieBericht> {

    private static final String BERICHTEN_ZIJN_AL_VERZONDEN = "Berichten zijn al verzonden";
    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String MSG_ID_FORMAT = "%s-%s";

    private final AutorisatieRepository autorisatieRepository;
    private final List<AutorisatieBericht> autorisatieBerichten;
    private final Destination destination;
    private final JmsTemplate jmsTemplate;
    private final AtomicLong aantalVerzonden = new AtomicLong(0);
    private boolean verzonden;

    /**
     * Constructor.
     * @param destination {@link Destination} queue waarop berichten geplaatst moeten worden.
     * @param jmsTemplate {@link JmsTemplate}
     * @param autorisatieRepository De AutorisatieRepository waaruit de autorisatie berichten moeten worden gelezen
     */
    public VerzendAutorisatieBerichtVerwerker(final Destination destination, final JmsTemplate jmsTemplate, final AutorisatieRepository autorisatieRepository) {
        autorisatieBerichten = new ArrayList<>();
        this.destination = destination;
        this.jmsTemplate = jmsTemplate;
        this.autorisatieRepository = autorisatieRepository;
    }

    @Override
    public final void voegBerichtToe(final AutorisatieBericht autorisatieBericht) {
        if (verzonden) {
            throw new IllegalStateException(BERICHTEN_ZIJN_AL_VERZONDEN);
        }
        autorisatieBerichten.add(autorisatieBericht);
    }

    @Override
    public final int aantalBerichten() {
        return autorisatieBerichten.size();
    }

    @Override
    public final void verwerkBerichten() {
        if (verzonden) {
            throw new IllegalStateException(BERICHTEN_ZIJN_AL_VERZONDEN);
        }
        verzonden = true;

        LOG.info("Aantal te verwerken autorisatie berichten: {}", autorisatieBerichten.size());
        if (!autorisatieBerichten.isEmpty()) {
            LOG.info("Versturen berichten");
            verstuurAutorisatieBericht(autorisatieBerichten);

            LOG.info("Update status naar verzonden");
            final List<String> afnemerCodes = new ArrayList<>(autorisatieBerichten.size());
            for (final AutorisatieBericht autorisatieBericht : autorisatieBerichten) {
                afnemerCodes.add(autorisatieBericht.getAutorisatie().getAfnemersindicatie());
            }
            autorisatieRepository.updateAutorisatieBerichtStatus(afnemerCodes, ConversieResultaat.VERZONDEN);
        }
        LOG.info("Berichten verwerkt");
    }

    private void verstuurAutorisatieBericht(final List<AutorisatieBericht> teVersturenBerichten) {
        jmsTemplate.execute(destination, (session, producer) -> {
            LOG.info("aantal te versturen berichten: {}", teVersturenBerichten.size());
            for (final AutorisatieBericht autorisatieBericht : teVersturenBerichten) {
                MDCProcessor.startVerwerking().metVeld(InitieleVullingVeld.MDC_AFNEMERSINDICATIE, autorisatieBericht.getAutorisatie().getAfnemersindicatie()).
                        run(() -> {
                            final Message message = session.createTextMessage(autorisatieBericht.format());
                            MDCProcessor.registreerVerwerkingsCode(message);
                            message.setStringProperty(JMSConstants.BERICHT_REFERENTIE,
                                    String.format(MSG_ID_FORMAT, SyncBerichtType.AUTORISATIE.getType(), autorisatieBericht.getMessageId()));
                            producer.send(message);
                            aantalVerzonden.getAndIncrement();
                        });
            }
            LOG.info("Berichten verstuurd");
            return null;
        });
    }

    @Override
    public final long aantalVerzonden() {
        return aantalVerzonden.get();
    }
}
