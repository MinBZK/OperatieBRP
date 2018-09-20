/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.voisc.database.entities.Bericht;
import nl.bzk.migratiebrp.voisc.database.entities.Mailbox;
import nl.bzk.migratiebrp.voisc.database.entities.StatusEnum;
import nl.bzk.migratiebrp.voisc.runtime.exceptions.VoiscDatabaseException;
import nl.bzk.migratiebrp.voisc.runtime.exceptions.VoiscMailboxException;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * De algemene ingang in de services die de Voisc biedt.
 */
public final class VoiscServiceImpl implements VoiscService {
    private static final String DATE_OUDER_DAN_MAG_NIET_NULL_ZIJN = "Date 'ouderDan' mag niet 'null' zijn.";
    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final int BERICHTEN_LIMIT = 100;

    @Inject
    private VoiscDatabase voiscDatabase;
    @Inject
    private VoiscMailbox voiscMailbox;
    @Inject
    private VoiscIsc voiscIsc;
    @Inject
    private MailboxConfiguratie voiscConfiguratie;

    @Override
    @Transactional(propagation = Propagation.NEVER, value = "voiscTransactionManager")
    @ManagedOperation(description = "Verstuur berichten van VOISC (database) naar ISC.")
    public void berichtenVerzendenNaarIsc() {
        final List<Bericht> messagesToSend = voiscDatabase.leesEnLockNaarIscTeVerzendenBericht(BERICHTEN_LIMIT);
        if (!messagesToSend.isEmpty()) {
            LOGGER.info("Versturen " + messagesToSend.size() + " berichten naar ISC.");
            voiscIsc.verstuurBerichtenNaarIsc(messagesToSend);
        }

    }

    @Override
    @SuppressWarnings("checkstyle:illegalcatch")
    @Transactional(propagation = Propagation.NEVER, value = "voiscTransactionManager")
    public void berichtenVerzendenNaarEnOntvangenVanMailbox() {
        LOGGER.debug("Start berichten verzenden naar en ontvangen van mailbox");
        // a. Ophalen mailboxen
        final Set<Mailbox> mailboxes = voiscConfiguratie.bepaalMailboxen();

        // b. SSL verbinding opbouwen naar MBS
        LOGGER.debug("Maak verbinding met mailbox server.");
        try {
            voiscMailbox.connectToMailboxServer();
        } catch (final VoiscMailboxException e) {
            LOGGER.error("Kan geen verbinding maken met mailbox server", e);

            // Einde oefening
            return;
        }

        // Per mailbox
        for (final Mailbox mailbox : mailboxes) {
            LOGGER.debug("[Mailbox {}]: Start verwerking voor mailbox.", mailbox.getMailboxnr());

            try {
                LOGGER.debug("[Mailbox {}]: Uitlezen te versturen berichten.", mailbox.getMailboxnr());
                // 1. uitlezen tabel voaBericht met nog te versturen berichten.
                final List<Bericht> berichten = voiscDatabase.leesEnLockNaarMailboxTeVerzendenBericht(mailbox.getMailboxnr());

                LOGGER.debug("[Mailbox {}]: Aanmelden", mailbox.getMailboxnr());
                // 2. Inloggen op de Mailbox van de gemeente
                voiscMailbox.login(mailbox);

                if (!berichten.isEmpty()) {
                    LOGGER.info("[Mailbox {}]: {} berichten te versturen.", mailbox.getMailboxnr(), berichten.size());
                }

                // 3. Versturen berichten naar Mailbox
                voiscMailbox.sendMessagesToMailbox(mailbox, berichten);

                LOGGER.debug("[Mailbox {}]: Ontvangen berichten.", mailbox.getMailboxnr());
                // 4, 5. Berichten uit mailbox ophalen en opslaan.
                voiscMailbox.receiveMessagesFromMailbox(mailbox);

                LOGGER.debug("[Mailbox {}]: Verwerking gereed.", mailbox.getMailboxnr());
            } catch (final Exception e /* Proces mag er niet uit gaan vanwege een onverwachte fout */) {
                LOGGER.debug("[Mailbox {}]: fout opgetreden.", mailbox.getMailboxnr(), e);
            }
        }

        LOGGER.debug("Sluit verbinding met mailbox server.");
        voiscMailbox.logout();
        LOGGER.debug("Einde berichten verzenden naar en ontvangen van mailbox");
    }

    @Override
    public int opschonenVoiscBerichten(final Date ouderDan) {
        LOGGER.debug("Start opschonen verwerkte berichten");

        int aantalOpgeschoondeBerichten = 0;
        if (ouderDan == null) {
            LOGGER.error(DATE_OUDER_DAN_MAG_NIET_NULL_ZIJN);
            return 0;
        }

        final Set<StatusEnum> statussen = new HashSet<>();
        statussen.add(StatusEnum.SENT_TO_ISC);
        statussen.add(StatusEnum.SENT_TO_MAILBOX);
        statussen.add(StatusEnum.IGNORED);
        statussen.add(StatusEnum.PROCESSED_IMMEDIATELY);
        statussen.add(StatusEnum.ERROR);

        try {
            aantalOpgeschoondeBerichten = voiscDatabase.verwijderVerwerkteBerichtenOuderDan(ouderDan, statussen);
        } catch (final VoiscDatabaseException e) {
            LOGGER.error("Er is een foutopgetreden bij het opschonen van de verwerkte berichten.", e);
        }

        if (aantalOpgeschoondeBerichten > 0) {
            LOGGER.info("{} berichten opgeschoond.", aantalOpgeschoondeBerichten);
        }

        LOGGER.debug("Einde opschonen verwerkte berichten");
        return aantalOpgeschoondeBerichten;
    }

    @Override
    @SuppressWarnings("checkstyle:illegalcatch")
    @Transactional(propagation = Propagation.REQUIRED, value = "voiscTransactionManager")
    public int herstellenVoiscBerichten(final Date ouderDan) {
        LOGGER.debug("Start herstellen berichten");

        int aantalHersteldeBerichten = 0;
        if (ouderDan == null) {
            LOGGER.error(DATE_OUDER_DAN_MAG_NIET_NULL_ZIJN);
            return 0;
        }

        try {
            aantalHersteldeBerichten += voiscDatabase.herstelBerichten(ouderDan, StatusEnum.SENDING_TO_MAILBOX, StatusEnum.RECEIVED_FROM_ISC);
            aantalHersteldeBerichten += voiscDatabase.herstelBerichten(ouderDan, StatusEnum.SENDING_TO_ISC, StatusEnum.RECEIVED_FROM_MAILBOX);
        } catch (final Exception e /* Proces mag er niet uit gaan vanwege een onverwachte fout */) {
            LOGGER.error("Er is een foutopgetreden bij het herstellen van berichten.", e);
        }

        if (aantalHersteldeBerichten > 0) {
            LOGGER.info("{} berichten hersteld.", aantalHersteldeBerichten);
        }

        LOGGER.debug("Einde herstellen berichten");
        return aantalHersteldeBerichten;
    }
}
