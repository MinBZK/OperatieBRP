/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.mailbox;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.migratiebrp.test.isc.environment.correlatie.Correlator;
import nl.bzk.migratiebrp.test.isc.environment.id.IdGenerator;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.AbstractKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.KanaalException;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.LazyLoadingKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.TestCasusContext;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.util.common.operatie.ExceptionWrapper;
import nl.bzk.migratiebrp.util.common.operatie.Herhaal;
import nl.bzk.migratiebrp.util.common.operatie.HerhaalException;
import nl.bzk.migratiebrp.voisc.database.entities.Mailbox;
import nl.bzk.migratiebrp.voisc.database.entities.StatusEnum;
import nl.bzk.migratiebrp.voisc.database.repository.MailboxRepository;
import nl.bzk.migratiebrp.voisc.spd.MailboxClient;
import nl.bzk.migratiebrp.voisc.spd.constants.SpdConstants;
import nl.bzk.migratiebrp.voisc.spd.exception.SpdProtocolException;
import nl.bzk.migratiebrp.voisc.spd.exception.VoaException;
import org.junit.Assert;

/**
 * Mailbox kanaal.
 */
public final class MailboxKanaal extends LazyLoadingKanaal {
    /** Kanaal naam. */
    public static final String KANAAL = "mailbox";

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Constructor.
     */
    public MailboxKanaal() {
        super(new Worker(), new Configuration(
            "classpath:configuratie.xml",
            "classpath:infra-db-voisc.xml",
            "classpath:infra-em-voisc.xml",
            "classpath:infra-jta.xml",
            "classpath:infra-mailbox.xml"));
    }

    /**
     * Verwerker.
     */
    public static final class Worker extends AbstractKanaal {

        private static final String ERROR_MAILBOX_NIET_GEVONDEN = "Mailbox voor instantie '%s' niet gevonden.";
        private static final String STANDAARD_MAILBOX_PASSWORD = "GbaGemWw";

        @Inject
        private Correlator correlator;
        @Inject
        private IdGenerator idGenerator;
        @Inject
        private MailboxClient mailboxServerProxy;
        @Inject
        private MailboxRepository mailboxRepository;

        private final Set<String> instanties = new HashSet<>();
        private final List<Bericht> berichten = new ArrayList<>();

        /*
         * (non-Javadoc)
         * 
         * @see nl.bzk.migratiebrp.test.isc.environment.kanaal.Kanaal#getKanaal()
         */
        @Override
        public String getKanaal() {
            return KANAAL;
        }

        @Override
        public void verwerkUitgaand(final TestCasusContext testCasus, final Bericht bericht) throws KanaalException {
            // We moeten een bericht versturen via de mailbox van de bronInstantie
            LOG.info("Start versturen bericht namens instantie {} naar instantie {}.", bericht.getVerzendendePartij(), bericht.getOntvangendePartij());
            if (bericht.getVerzendendePartij() == null || bericht.getOntvangendePartij() == null) {
                throw new KanaalException("Verzendende ("
                                          + bericht.getVerzendendePartij()
                                          + ") en ontvangende ("
                                          + bericht.getOntvangendePartij()
                                          + ") partij moeten beide gevuld zijn.");
            }

            instanties.add(bericht.getVerzendendePartij());
            instanties.add(bericht.getOntvangendePartij());
            final Mailbox mailbox = mailboxRepository.getMailboxByInstantiecode(Integer.parseInt(bericht.getVerzendendePartij()));
            LOG.info("Verwerk uitgaand bericht voor mailbox: {}", mailbox);
            Assert.assertNotNull(String.format(ERROR_MAILBOX_NIET_GEVONDEN, bericht.getVerzendendePartij()), mailbox);

            final Mailbox recipient = mailboxRepository.getMailboxByInstantiecode(Integer.parseInt(bericht.getOntvangendePartij()));
            Assert.assertNotNull(String.format(ERROR_MAILBOX_NIET_GEVONDEN, bericht.getOntvangendePartij()), mailbox);

            // Controleer correlatie
            if (bericht.getCorrelatieReferentie() != null) {
                correlator.controleerInkomend(bericht.getCorrelatieReferentie(), getKanaal());
            }

            // Genereer ID
            final String berichtReferentie = idGenerator.generateId();
            correlator.registreerUitgaand(bericht.getBerichtReferentie(), getKanaal(), berichtReferentie);

            final nl.bzk.migratiebrp.voisc.database.entities.Bericht mailboxBericht = new nl.bzk.migratiebrp.voisc.database.entities.Bericht();
            mailboxBericht.setMessageId(correlator.getBerichtReferentie(bericht.getBerichtReferentie()));
            mailboxBericht.setCorrelationId(correlator.getBerichtReferentie(bericht.getCorrelatieReferentie()));
            mailboxBericht.setBerichtInhoud(bericht.getInhoud());
            mailboxBericht.setOriginator(mailbox.getMailboxnr());
            mailboxBericht.setRecipient(recipient.getMailboxnr());
            mailboxBericht.setStatus(StatusEnum.SENDING_TO_MAILBOX);

            // SSL verbinding opbouwen
            mailboxServerProxy.connect();

            try {
                // Inloggen op de Mailbox
                mailboxServerProxy.logOn(mailbox);

                // Versturen berichten naar Mailbox
                mailboxServerProxy.putMessage(mailboxBericht);
            } catch (final VoaException e) {
                throw new RuntimeException("Fout bij versturen bericht.", e);
            } finally {
                // Logout
                try {
                    mailboxServerProxy.logOff();
                } catch (final SpdProtocolException e) {
                    throw new RuntimeException("Fout bij uitloggen na versturen bericht.", e);
                }
            }
        }

        @Override
        public Bericht verwerkInkomend(final TestCasusContext testCasus, final Bericht verwachtBericht) throws KanaalException {

            // Controleer correlatie
            if (verwachtBericht.getCorrelatieReferentie() != null) {
                correlator.controleerUitgaand(verwachtBericht.getCorrelatieReferentie(), getKanaal());

                // Hack(?)
                verwachtBericht.setCorrelatieReferentie(correlator.getBerichtReferentie(verwachtBericht.getCorrelatieReferentie()));
            }

            LOG.info("Ontvangen bericht voor instantie: {}", verwachtBericht.getOntvangendePartij());
            final Mailbox mailbox = mailboxRepository.getMailboxByInstantiecode(Integer.parseInt(verwachtBericht.getOntvangendePartij()));
            LOG.info("Mailbox: {}", mailbox);
            if (mailbox.getMailboxpwd() == null || "".equals(mailbox.getMailboxpwd())) {
                mailbox.setMailboxpwd(STANDAARD_MAILBOX_PASSWORD);
            }

            Bericht result = null;
            try {
                final Herhaal herhaal = verwachtBericht.getTestBericht().maakHerhaling();
                final OntvangMailbox ontvangMailbox =
                        new OntvangMailbox(mailbox, mailboxServerProxy, mailboxRepository, berichten, verwachtBericht, testCasus);
                herhaal.herhaal(ontvangMailbox);
                result = ontvangMailbox.getResult();
            } catch (final HerhaalException e) {
                throw new KanaalException("Kan bericht niet ophalen bij mailbox. ", e);
            }

            return result;
        }

        /**
         * Verzendende en ontvangende partij ook vergelijken bij inkomend bericht.
         *
         * @return false
         */
        @Override
        protected boolean negeerPartijen() {
            return false;
        }

        /**
         * Correlatie referenties ook vergelijken bij inkomend bericht.
         *
         * @return false
         */
        @Override
        protected boolean negeerCorrelatie() {
            return false;
        }

        @Override
        public List<Bericht> naTestcase(final TestCasusContext testCasus) {
            for (final String instantie : instanties) {
                final Mailbox mailbox = mailboxRepository.getMailboxByInstantiecode(Integer.parseInt(instantie));
                try {
                    final OntvangMailbox ontvangMailbox = new OntvangMailbox(mailbox, mailboxServerProxy, mailboxRepository, berichten, null, testCasus);
                    ontvangMailbox.ontvangBerichten();
                } catch (final ExceptionWrapper e) {
                    LOG.debug("Kon geen berichten ontvangen (na testcase fase)", e);
                    // Ignore
                }
            }

            final List<Bericht> result = new ArrayList<>(berichten);

            berichten.clear();
            instanties.clear();

            return result;
        }

        /**
         * OntvangMailbox.
         */
        public final class OntvangMailbox implements Runnable {
            private static final int MAXIMUM_AANTAL_BERICHTEN = 171;
            private final Mailbox mailbox;
            private final MailboxClient mailboxServerProxy;
            private final MailboxRepository mailboxRepository;
            private final List<Bericht> berichten;
            private final Bericht verwachtBericht;
            private final TestCasusContext testCasus;
            private Bericht result;

            /**
             * Constructor.
             *
             * @param mailbox
             *            Mailbox
             * @param mailboxServerProxy
             *            De mailbox proxy
             * @param mailboxRepository
             *            De mailbox repository
             * @param berichten
             *            De lijst met ontvangen berichten
             * @param verwachtBericht
             *            Het verwachte bericht
             * @param testCasus
             *            De huidige test casus
             */
            public OntvangMailbox(
                final Mailbox mailbox,
                final MailboxClient mailboxServerProxy,
                final MailboxRepository mailboxRepository,
                final List<Bericht> berichten,
                final Bericht verwachtBericht,
                final TestCasusContext testCasus)
            {
                this.mailbox = mailbox;
                this.mailboxServerProxy = mailboxServerProxy;
                this.mailboxRepository = mailboxRepository;
                this.berichten = berichten;
                this.verwachtBericht = verwachtBericht;
                this.testCasus = testCasus;
            }

            @Override
            public void run() {
                ontvangBerichten();

                result = zoekBericht();
                if (result != null) {
                    correlator.registreerInkomend(verwachtBericht.getBerichtReferentie(), getKanaal(), result.getBerichtReferentie());
                }

                if (result == null) {
                    if ("$$geenBericht$$".equals(verwachtBericht.getInhoud())) {
                        LOG.info("Geen bericht gevonden bij mailbox. Berichtinhoud van verwacht bericht is '$$geenBericht$$', dus verwachting is geen bericht. "
                                 + "Resultaat -> ok");
                        result = verwachtBericht;
                    } else {
                        throw new ExceptionWrapper(new KanaalException("Kan bericht niet ophalen bij mailbox."));
                    }
                }

            }

            private void ontvangBerichten() {

                // SSL verbinding opbouwen
                mailboxServerProxy.connect();

                try {
                    // Inloggen op de Mailbox
                    LOG.info("Aanmelden op mailbox: " + mailbox.getMailboxnr());
                    mailboxServerProxy.logOn(mailbox);

                    // Versturen berichten naar Mailbox
                    final List<Integer> seqNummers = new ArrayList<>();

                    int nextSequenceNumber = 0;
                    do {
                        nextSequenceNumber =
                                mailboxServerProxy.listMessages(nextSequenceNumber, seqNummers, MAXIMUM_AANTAL_BERICHTEN, "001", SpdConstants.PRIORITY);

                        for (final Integer seqNr : seqNummers) {
                            final nl.bzk.migratiebrp.voisc.database.entities.Bericht mailboxBericht = mailboxServerProxy.getMessage(seqNr);

                            LOG.info("Ontvangen mailbox bericht: " + mailboxBericht);
                            final Bericht ontvangenBericht = new Bericht();
                            ontvangenBericht.setOntvangendePartij(mailbox.getFormattedInstantiecode());
                            final Mailbox originator = mailboxRepository.getMailboxByNummer(mailboxBericht.getOriginator());
                            ontvangenBericht.setVerzendendePartij(originator.getFormattedInstantiecode());

                            // Registreer EREF
                            ontvangenBericht.setBerichtReferentie(mailboxBericht.getMessageId());

                            if (mailboxBericht.getCorrelationId() != null) {
                                LOG.info("Bepaal correlatie id voor ontvangen bericht EREF2 {}", mailboxBericht.getCorrelationId());
                                ontvangenBericht.setCorrelatieReferentie(mailboxBericht.getCorrelationId());
                            }

                            ontvangenBericht.setInhoud(mailboxBericht.getBerichtInhoud());
                            berichten.add(ontvangenBericht);
                        }
                    } while (nextSequenceNumber != 0);
                } catch (final VoaException e) {
                    throw new ExceptionWrapper(new KanaalException("Fout bij ontvangen bericht.", e));
                } finally {
                    // Logout
                    try {
                        mailboxServerProxy.logOff();
                    } catch (final SpdProtocolException e) {
                        throw new ExceptionWrapper(new KanaalException("Fout bij uitloggen na ontvangen bericht.", e));
                    }
                }

            }

            private Bericht zoekBericht() {
                final Iterator<Bericht> berichtenIterator = berichten.iterator();

                while (berichtenIterator.hasNext()) {
                    final Bericht bericht = berichtenIterator.next();
                    if (controleerInkomend(testCasus, verwachtBericht, bericht)) {
                        berichtenIterator.remove();
                        return bericht;
                    }
                }

                return null;
            }

            /**
             * Geef de waarde van result.
             *
             * @return result
             */
            public Bericht getResult() {
                return result;
            }

        }

    }

}
