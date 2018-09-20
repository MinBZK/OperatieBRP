/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.notificator.service.impl;

import java.util.List;
import javax.inject.Inject;
import nl.bzk.brp.serialisatie.notificator.exceptions.CommandException;
import nl.bzk.brp.serialisatie.notificator.repository.PersoonIdRepository;
import nl.bzk.brp.serialisatie.notificator.runnable.PlaatsPersoonOpQueueRunnable;
import nl.bzk.brp.serialisatie.notificator.service.JmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

/**
 * Implementatie van JmsService interface.
 */
@Service
public class JmsServiceImpl implements JmsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JmsServiceImpl.class);
    private static final String QUEUE_ONGECONFIGUREERD = "queue://";
    private static final int MILLIS_IN_SECONDE = 1000;

    @Inject
    private PersoonIdRepository persoonIdRepository;

    @Inject
    private JmsTemplate persoonSerialisatieJmsTemplate;

    @Inject
    private ThreadPoolTaskExecutor executor;

    @Override
    public final void creeerEnPubliceerJmsBerichtenVoorPersonen(final List<Integer> persoonIdLijst) {
        controleerPersoonSerialisatieNotificatorConfiguratie();

        try {
            publiceerJmsBerichten(persoonIdLijst);
        } catch (NumberFormatException e) {
            throw new CommandException("Fout opgetreden bij converteren van persoon identifiers. Deze dienen "
                    + "numerieke waarden te zijn.", e);
        }
    }

    @Override
    public final void creeerEnPubliceerJmsBerichtenVoorAllePersonen(
            final Integer aantalIdsPerBatch, final Integer tijdInSecondenTussenBatches, final Integer vanafPersoonId)
    {
        LOGGER.info("Publiceer JMS berichten voor alle personen aangeroepen. Aantal id's per batch: {} wachttijd: {} "
                + "seconde", aantalIdsPerBatch, tijdInSecondenTussenBatches);
        controleerPersoonSerialisatieNotificatorConfiguratie();
        LOGGER.info("JMS configuratie ok, nu wordt de grootte van de queue bepaald.");

        boolean isNogNietKlaar = true;
        int vanafPersoonIdDezeBatch = vanafPersoonId;

        while (isNogNietKlaar) {
            LOGGER.info("Persoon id's boven {} worden opgezocht met een limiet van {}.",
                    vanafPersoonIdDezeBatch, aantalIdsPerBatch);

            final List<Integer> persoonIds = haalPersoonIdsOpVoorDezeBatch(vanafPersoonIdDezeBatch, aantalIdsPerBatch);

            final boolean persoonIdsGevonden = persoonIds.size() > 0;
            if (persoonIdsGevonden) {
                vanafPersoonIdDezeBatch = getLaatsteIdInHuidigeBatch(persoonIds) + 1;
            } else {
                LOGGER.info("Geen id's meer gevonden, alles verwerkt. Laatste id was {}.", vanafPersoonIdDezeBatch);
                isNogNietKlaar = false;
            }

            publiceerJmsBerichten(persoonIds);
            LOGGER.info("Alle id's voor de huidige batch zijn op de queue gezet. Laatste id was {}",
                    vanafPersoonIdDezeBatch);

            wachtVoordatVolgendBatchBegint(tijdInSecondenTussenBatches, vanafPersoonIdDezeBatch);
        }
    }

    /**
     * Verkrijgt het laatste id in de huidige batch.
     *
     * @param persoonIds De lijst van persoon id's in de huidige batch.
     * @return Het laatste id in de huidige batch.
     */
    private Integer getLaatsteIdInHuidigeBatch(final List<Integer> persoonIds) {
        return persoonIds.get(persoonIds.size() - 1);
    }

    /**
     * Haalt de lijst van persoon id's op voor de huidige batch.
     *
     * @param laatstePersoonId De laatste persoon id van de vorige batch, of 0 als dit de eerste batch is.
     * @param aantalIdsPerKeer Het aantal id's dat per batch opgehaalt moet worden.
     * @return De lijst van persoon id's voor de huidige batch.
     */
    private List<Integer> haalPersoonIdsOpVoorDezeBatch(final int laatstePersoonId, final int aantalIdsPerKeer) {
        final List<Integer> persoonIds = persoonIdRepository.vindPersoonIds(laatstePersoonId, aantalIdsPerKeer);
        LOGGER.info("Aantal gevonden id's is {}.", persoonIds.size());
        return persoonIds;
    }

    /**
     * Controleert de configuratie van de persoon serialisatie notificator.
     */
    private void controleerPersoonSerialisatieNotificatorConfiguratie() {
        if (!isPersoonSerialisatieNotificatorGeconfigureerd(persoonSerialisatieJmsTemplate)) {
            throw new CommandException("Er is geen configuratie ingesteld voor de PersoonSerialisatieNotificator.");
        }
    }

    /**
     * Is persoon serialisatie notificator geconfigureerd.
     *
     * @param jmsTemplate the jms template
     * @return the boolean
     */
    private boolean isPersoonSerialisatieNotificatorGeconfigureerd(final JmsTemplate jmsTemplate) {
        return jmsTemplate.getConnectionFactory() != null
                && jmsTemplate.getDefaultDestination() != null
                && !jmsTemplate.getDefaultDestination().toString()
                .equals(QUEUE_ONGECONFIGUREERD);
    }

    /**
     * Publiceer de JMS berichten naar de JMS queue. Dit wordt gedaan op basis van een lijst met persoon ids via
     * meerdere threads die parallel aan elkaar berichten op de queue plaatsen.
     *
     * @param persoonIdIntLijst de persoon identifier lijst
     */
    private void publiceerJmsBerichten(final List<Integer> persoonIdIntLijst) {
        for (Integer persoonId : persoonIdIntLijst) {
            final PlaatsPersoonOpQueueRunnable plaatsPersoonOpQueueRunnable = new PlaatsPersoonOpQueueRunnable();
            plaatsPersoonOpQueueRunnable.setPersoonId(persoonId);
            plaatsPersoonOpQueueRunnable.setPersoonSerialisatieJmsTemplate(persoonSerialisatieJmsTemplate);
            executor.execute(plaatsPersoonOpQueueRunnable);
        }
    }

    /**
     * Wacht voordat volgend batch begint.
     *
     * @param tijdInSecondenTussenBatchesParameter tijd in seconden tussen batches parameter
     * @param laatstePersoonId laatste persoon id
     */
    private void wachtVoordatVolgendBatchBegint(final int tijdInSecondenTussenBatchesParameter,
                                                final int laatstePersoonId)
    {
        try {
            Thread.sleep(tijdInSecondenTussenBatchesParameter * MILLIS_IN_SECONDE);
        } catch (InterruptedException e) {
            LOGGER.error("De thread sleep is mislukt. Laatste id was {}.", laatstePersoonId);
        }
    }

}
