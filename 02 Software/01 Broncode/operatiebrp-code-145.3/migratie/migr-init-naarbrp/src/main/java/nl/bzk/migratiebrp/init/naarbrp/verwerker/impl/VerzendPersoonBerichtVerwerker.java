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
import nl.bzk.migratiebrp.init.naarbrp.domein.ConversieResultaat;
import nl.bzk.migratiebrp.init.naarbrp.repository.PersoonRepository;
import nl.bzk.migratiebrp.init.naarbrp.service.bericht.SyncNaarBrpBericht;
import nl.bzk.migratiebrp.init.naarbrp.verwerker.BerichtVerwerker;
import nl.bzk.migratiebrp.util.common.logging.InitieleVullingVeld;
import org.springframework.jms.core.JmsTemplate;

/**
 * Verstuurt Lo3Berichten.
 */
public class VerzendPersoonBerichtVerwerker implements BerichtVerwerker<SyncNaarBrpBericht> {

    private static final String BERICHTEN_ZIJN_AL_VERZONDEN = "Berichten zijn al verzonden";

    private static final Logger LOG = LoggerFactory.getLogger();

    private final PersoonRepository persoonRepository;

    private final List<SyncNaarBrpBericht> lo3Berichten;
    private final Destination destination;
    private final JmsTemplate jmsTemplate;
    private final AtomicLong aantalVerzonden = new AtomicLong(0);
    private boolean verzonden;

    /**
     * Constructor.
     * @param destination {@link Destination} queue waarop berichten geplaatst moeten worden.
     * @param jmsTemplate {@link JmsTemplate}
     * @param persoonRepository De PersoonRepository waaruit de PLen moeten worden gelezen
     */
    public VerzendPersoonBerichtVerwerker(final Destination destination, final JmsTemplate jmsTemplate, final PersoonRepository persoonRepository) {
        lo3Berichten = new ArrayList<>();
        this.destination = destination;
        this.jmsTemplate = jmsTemplate;
        this.persoonRepository = persoonRepository;
    }

    @Override
    public final void voegBerichtToe(final SyncNaarBrpBericht bericht) {
        if (verzonden) {
            throw new IllegalStateException(BERICHTEN_ZIJN_AL_VERZONDEN);
        }
        lo3Berichten.add(bericht);
    }

    @Override
    public final int aantalBerichten() {
        return lo3Berichten.size();
    }

    @Override
    public final void verwerkBerichten() {
        if (verzonden) {
            throw new IllegalStateException(BERICHTEN_ZIJN_AL_VERZONDEN);
        }
        verzonden = true;

        LOG.debug("Aantal te verwerken persoons berichten: {}", lo3Berichten.size());
        if (!lo3Berichten.isEmpty()) {
            LOG.info("Versturen berichten");
            verstuurBericht(lo3Berichten);

            LOG.info("Update status naar verzonden");
            final List<String> aNummers = new ArrayList<>(lo3Berichten.size());
            for (final SyncNaarBrpBericht naarBrpBericht : lo3Berichten) {
                aNummers.add(naarBrpBericht.getANummer());
            }
            persoonRepository.updateLo3BerichtStatus(aNummers, ConversieResultaat.VERZONDEN);
        }
        LOG.info("Berichten verwerkt");
    }

    private void verstuurBericht(final List<SyncNaarBrpBericht> teVersturenBerichten) {
        jmsTemplate.execute(destination, (session, producer) -> {
            for (final SyncNaarBrpBericht lo3Bericht : teVersturenBerichten) {
                MDCProcessor.startVerwerking().metVeld(InitieleVullingVeld.MDC_ADMINISTRATIE_NUMMER, lo3Bericht.getANummer()).
                        run(() -> {
                            final Message message = session.createTextMessage(lo3Bericht.getBericht());
                            MDCProcessor.registreerVerwerkingsCode(message);
                            message.setStringProperty(JMSConstants.BERICHT_REFERENTIE, lo3Bericht.getMessageId());
                            producer.send(message);
                            LOG.info("Bericht naar BRP verzonden");
                            aantalVerzonden.getAndIncrement();
                        });
            }
            return null;
        });
    }

    @Override
    public final long aantalVerzonden() {
        return aantalVerzonden.get();
    }
}
