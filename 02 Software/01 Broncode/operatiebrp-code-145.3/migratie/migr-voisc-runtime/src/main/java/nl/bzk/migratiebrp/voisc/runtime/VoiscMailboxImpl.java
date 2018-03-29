/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.logging.MDCProcessor;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.DeliveryReport;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.StatusReport;
import nl.bzk.migratiebrp.bericht.model.sync.generated.RichtingType;
import nl.bzk.migratiebrp.util.common.logging.FunctioneleMelding;
import nl.bzk.migratiebrp.util.common.logging.MDCVeld;
import nl.bzk.migratiebrp.voisc.database.entities.Bericht;
import nl.bzk.migratiebrp.voisc.database.entities.Mailbox;
import nl.bzk.migratiebrp.voisc.database.entities.StatusEnum;
import nl.bzk.migratiebrp.voisc.database.repository.BerichtRepository;
import nl.bzk.migratiebrp.voisc.database.repository.MailboxRepository;
import nl.bzk.migratiebrp.voisc.mailbox.client.Connection;
import nl.bzk.migratiebrp.voisc.mailbox.client.MailboxClient;
import nl.bzk.migratiebrp.voisc.mailbox.client.exception.ConnectionException;
import nl.bzk.migratiebrp.voisc.runtime.exceptions.VoiscMailboxException;
import nl.bzk.migratiebrp.voisc.runtime.exceptions.VoiscQueueException;
import nl.bzk.migratiebrp.voisc.runtime.utils.PasswordGenerator;
import nl.bzk.migratiebrp.voisc.spd.SpdConstants;
import nl.bzk.migratiebrp.voisc.spd.exception.MailboxServerPasswordException;
import nl.bzk.migratiebrp.voisc.spd.exception.VoaException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Wrapper voor bewerkingen op de mailbox.
 */
public final class VoiscMailboxImpl implements VoiscMailbox {
    private static final String VOISC_TRANSACTION_MANAGER = "voiscTransactionManager";
    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final int TWO_MONTHS_AGO = -60;

    private MailboxClient mailboxClient;
    private MailboxRepository mailboxRepository;
    private BerichtRepository berichtRepository;
    private VoiscQueue archiveringService;

    /**
     * Constructor.
     * @param mailboxClient mailboxClient
     * @param mailboxRepository mailboxRepository
     * @param berichtRepository berichtRepository
     * @param archiveringService archiveringService
     */
    @Inject
    public VoiscMailboxImpl(final MailboxClient mailboxClient,
                            final MailboxRepository mailboxRepository,
                            final BerichtRepository berichtRepository,
                            final VoiscQueue archiveringService) {
        this.mailboxClient = mailboxClient;
        this.mailboxRepository = mailboxRepository;
        this.berichtRepository = berichtRepository;
        this.archiveringService = archiveringService;
    }

    @Override
    public Connection connectToMailboxServer() throws VoiscMailboxException {
        try {
            return mailboxClient.connect();
        } catch (final RuntimeException e /* Connect gooit allerlei (ook runtime)excepties. Deze worden allen gewrapped */) {
            throw new VoiscMailboxException("Kan geen verbinding maken met de mailbox server.", e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.NEVER, value = VOISC_TRANSACTION_MANAGER)
    public void login(final Connection connection, final Mailbox mailbox) throws VoiscMailboxException {
        if (mailbox.getMailboxpwd() == null || "".equals(mailbox.getMailboxpwd())) {
            LOGGER.warn("[Mailbox {}]: Geen mailbox wachtwoord beschikbaar.", mailbox.getMailboxnr());
            throw new VoiscMailboxException("Geen mailbox wachtwoord beschikbaar voor mailbox: " + mailbox.getMailboxnr());
        } else {
            try {
                mailboxClient.logOn(connection, mailbox);
                LOGGER.debug("[Mailbox {}]: Log-on succesvol.", mailbox.getMailboxnr());
                checkPasswordChange(connection, mailbox);
            } catch (final ConnectionException cone) {
                LOGGER.warn("[Mailbox {}]: Onverwachte connectie exceptie bij aanmelden.", mailbox.getMailboxnr(), cone);
                throw new VoiscMailboxException("Connectie exceptie bij aanmelden.", cone);
            } catch (final MailboxServerPasswordException mbpwe) {
                LOGGER.warn("[Mailbox {}]: Verkeerde mailbox wachtwoord; aanmelden mislukt.", mailbox.getMailboxnr(), mbpwe);
                throw new VoiscMailboxException("Verkeerde mailbox password.", mbpwe);
            } catch (final VoaException spde) {
                LOGGER.warn("[Mailbox {}]: Onverwachte SPD fout bij aanmelden bij mailbox.", mailbox.getMailboxnr(), spde);
                throw new VoiscMailboxException("Onverwachte spd-protocol fout bij inloggen (of wijzigen wachtwoord).", spde);
            }
        }
    }

    private void checkPasswordChange(final Connection connection, final Mailbox mailbox) throws VoiscMailboxException {
        if (shouldChangePassword(mailbox)) {
            LOGGER.debug("[Mailbox {}]: Wachtwoord automatisch wijzigen.", mailbox.getMailboxnr());
            final String newPwd = PasswordGenerator.generate();
            changeMBPassword(connection, mailbox, newPwd);
        } else {
            LOGGER.debug("[Mailbox {}]: Geen wachtwoord wijziging nodig.", mailbox.getMailboxnr());
        }
    }

    private boolean shouldChangePassword(final Mailbox mailbox) {
        if (mailbox.getLaatsteWijzigingPwd() == null) {
            LOGGER.debug("[Mailbox {}]: Check mailbox password validity; datum leeg", mailbox.getMailboxnr());
            return true;
        }

        final Calendar checkCalendar = Calendar.getInstance();
        checkCalendar.add(Calendar.DATE, TWO_MONTHS_AGO);
        final Date checkDate = checkCalendar.getTime();

        final Date laatsteWijzigingPwdDt = mailbox.getLaatsteWijzigingPwd();
        return checkDate.compareTo(laatsteWijzigingPwdDt) > 0;
    }

    /**
     * Change password of the mailboxserver.
     * @param newPW het nieuw in te stellen password
     * @throws VoiscMailboxException Als het wijzigen van het wachtwoord niet lukt.
     */
    private void changeMBPassword(final Connection connection, final Mailbox mailbox, final String newPW) throws VoiscMailboxException {
        try {
            LOGGER.debug("[Mailbox {}]: Changing mailbox password", mailbox.getMailboxnr());
            mailboxClient.changePassword(connection, mailbox, newPW);

            LOGGER.debug("[Mailbox {}]: Saving new mailbox password", mailbox.getMailboxnr());
            mailbox.setMailboxpwd(newPW);
            mailbox.setLaatsteWijzigingPwd(new Timestamp(System.currentTimeMillis()));
            mailboxRepository.save(mailbox);

            LOGGER.info("[Mailbox {}]: Mailbox password succesfully changed.", mailbox.getMailboxnr());
        } catch (final VoaException mbe) {
            LOGGER.error("[Mailbox {}]: Onverwachte fout changing mailbox password.", mailbox.getMailboxnr(), mbe);
            throw new VoiscMailboxException("Kan mailbox wachtwoord niet wijzigen", mbe);
        } catch (final ConnectionException cone) {
            LOGGER.warn("[Mailbox {}]: Onverwachte connectie exceptie bij wijzigen wachtwoord.", mailbox.getMailboxnr(), cone);
            throw new VoiscMailboxException("Connectie exceptie bij wijzigen wachtwoord.", cone);
        }
    }

    /**
     * Send all messages to the mailbox. All berichten in the database marked as to be send are sent tot the mailbox.
     * This method will will send one message and than update the status of the message in the database. If it is not
     * possible to update the status of the message, because of database failures (i.e. database connection is lost)
     * this method will retry a given number of times to update the message. But the message itself will be send only
     * once to the Mailbox Server. We assume we are logged in to the mailbox server. I.e. login() must be called before
     * summarize() is called (and logout() somewhere after)
     * @param connection De SSL connectie met de mailboxserver
     * @param mailbox Mailbox from which the messages are received and stored
     * @param messagesToSend Lijst met berichten dat verstuurd moet worden
     */
    @Override
    @Transactional(propagation = Propagation.NEVER, value = VOISC_TRANSACTION_MANAGER)
    public void sendMessagesToMailbox(final Connection connection, final Mailbox mailbox, final List<Bericht> messagesToSend) {
        for (final Bericht bericht : messagesToSend) {
            MDCProcessor.extra(MDCVeld.VOISC_BERICHT_ID, bericht.getId()).run(() -> sendMessageToMailbox(connection, mailbox, bericht));
        }
    }

    private void sendMessageToMailbox(final Connection connection, final Mailbox mailbox, final Bericht bericht) {
        try {
            // check of recipient bestaat.
            if (isRecipientTemporaryBlocked(bericht)) {
                bericht.setStatus(StatusEnum.RECEIVED_FROM_ISC);
                LOGGER.info("[Mailbox {}; Bericht {}]: Bericht heeft een tijdelijk geblokkeerde recipient.", mailbox.getMailboxnr(), bericht.getId());
                berichtRepository.save(bericht);
            } else {
                // Stuur bericht en sla o.a. het dispatchnummer op wat we van de MBS terug kijgen.
                mailboxClient.putMessage(connection, bericht);

                bericht.setStatus(StatusEnum.SENT_TO_MAILBOX);
                bericht.setTijdstipVerzonden(new Timestamp(System.currentTimeMillis()));
                berichtRepository.save(bericht);
                LOGGER.info(FunctioneleMelding.VOISC_MAILBOX_VERSTUURD);
                archiveerUitgaandBericht(bericht);
            }
        } catch (final VoaException spe) {
            LOGGER.error(
                    "[Mailbox {}; Bericht {}]: Onverwachte SPD protocol fout bij verzenden van bericht.", mailbox.getMailboxnr(), bericht.getId(), spe);
        } catch (final ConnectionException cone) {
            LOGGER.warn(
                    "[Mailbox {}; Bericht {}]: Onverwachte connectie exceptie bij verzenden van bericht.", mailbox.getMailboxnr(), bericht.getId(),
                    cone);
        }
    }

    private void archiveerUitgaandBericht(final Bericht bericht) {
        try {
            archiveringService.archiveerBericht(bericht, RichtingType.UITGAAND);
        } catch (final VoiscQueueException e) {
            LOGGER.error("Uitgaand bericht kon niet verstuurd worden naar archivering queue!", e);
        }
    }

    private void archiveerIngaandBericht(final Bericht bericht) {
        try {
            archiveringService.archiveerBericht(bericht, RichtingType.INGAAND);
        } catch (final VoiscQueueException e) {
            LOGGER.error("Ingaand bericht kon niet verstuurd worden naar archivering queue!", e);
        }
    }

    /**
     * Controleer of de recipient tijdelijk geblokkeerd is.
     * @param bericht Het bericht waar de recipient van gecontroleerd wordt.
     * @return true, als de mailbox van de recipient tijdelijk geblokkeerd is.
     */
    private boolean isRecipientTemporaryBlocked(final Bericht bericht) {
        final Mailbox mailbox = mailboxRepository.getMailboxByNummer(bericht.getRecipient());

        return mailbox != null && isBlokkeringGestart(mailbox) && isBlokkeringNietGeeindigd(mailbox);
    }

    private boolean isBlokkeringGestart(final Mailbox mailbox) {
        return mailbox.getStartBlokkering() != null && mailbox.getStartBlokkering().getTime() < System.currentTimeMillis();
    }

    private boolean isBlokkeringNietGeeindigd(final Mailbox mailbox) {
        return mailbox.getEindeBlokkering() == null || mailbox.getEindeBlokkering().getTime() > System.currentTimeMillis();
    }

    @Override
    @Transactional(propagation = Propagation.NEVER, value = VOISC_TRANSACTION_MANAGER)
    public void receiveMessagesFromMailbox(final Connection connection, final Mailbox mailbox) throws VoiscMailboxException {
        int nextSeqNr = mailbox.getLaatsteMsSequenceNumber() == null ? 0 : mailbox.getLaatsteMsSequenceNumber() + 1;
        LOGGER.debug("[Mailbox {}]: Start ontvangst bij MSSequenceNumber {}", mailbox.getMailboxnr(), nextSeqNr);
        long msgReceivedOk = 0;
        long msgReceivedNok = 0;

        final int limitNumber = mailbox.getLimitNumber();
        final List<Integer> seqNummers = new ArrayList<>(limitNumber);
        try {
            do {
                seqNummers.clear();
                nextSeqNr = mailboxClient.listMessages(connection, nextSeqNr, seqNummers, limitNumber, "012", SpdConstants.Priority.defaultValue());
                if (!seqNummers.isEmpty()) {
                    LOGGER.info(
                            "[Mailbox {}]: Lijst met {} berichten onvangen en vervolg sequence {}.", mailbox.getMailboxnr(), seqNummers.size(), nextSeqNr);
                }

                msgReceivedOk = seqNummers.stream().map(seqNr -> processBericht(connection, mailbox, seqNr)).filter(Objects::nonNull).count();
                msgReceivedNok = seqNummers.size() - msgReceivedOk;
            } while (nextSeqNr != 0);
        } catch (final VoaException spe) {
            LOGGER.error("[Mailbox {}]: Onverwachte SPD protocol fout bij het opvragen van de lijst van berichten", mailbox.getMailboxnr(), spe);
            throw new VoiscMailboxException("Onverwachte SPD protocol fout bij het opvragen van de lijst van berichten", spe);
        } catch (final ConnectionException cone) {
            LOGGER.warn("[Mailbox {}]: Onverwachte connectie exceptie bij opvragen lijst van berichten.", mailbox.getMailboxnr(), cone);
            throw new VoiscMailboxException("Connectie exceptie bij opvragen lijst van berichten.", cone);
        } finally {
            if (msgReceivedOk > 0 || msgReceivedNok > 0) {
                LOGGER.info(
                        "[Mailbox {}]: {} (waarvan {} foutief) berichten ontvangen voor mailbox ",
                        mailbox.getMailboxnr(), msgReceivedNok + msgReceivedOk, msgReceivedNok);
            }
        }
    }

    private Bericht processBericht(final Connection connection, final Mailbox mailbox, int seqNr) {
        Bericht resultaat ;
        try {
            final Bericht bericht = mailboxClient.getMessage(connection, seqNr);
            if (bericht != null) {
                MDCProcessor.startVerwerking().metVeld(MDCVeld.VOISC_BERICHT_ID, bericht.getId()).run(() -> save(bericht, mailbox, seqNr));
            }
            resultaat = bericht;
        } catch (final VoaException spe) {
            LOGGER.error("[Mailbox {}; MsSeqNr {}]: Onverwachte SPD protocol fout bij ontvangen van bericht.", mailbox.getMailboxnr(), seqNr, spe);
            resultaat = null;
        } catch (final ConnectionException cone) {
            LOGGER.warn("[Mailbox {}; MsSeqNr {}]: Onverwachte connectie exceptie bij ontvangen van bericht.", mailbox.getMailboxnr(), seqNr, cone);
            resultaat = null;
        }
        return resultaat;
    }

    private void save(final Bericht bericht, final Mailbox mailbox, final Integer seqNr) {
        // Bewaar bericht
        bericht.setStatus(StatusEnum.RECEIVED_FROM_MAILBOX);
        bericht.setTijdstipOntvangst(new Timestamp(System.currentTimeMillis()));
        bericht.setVerwerkingsCode(MDCProcessor.getVerwerkingsCode());

        // Speciale berichten
        if (bericht.getNonDeliveryReason() != null) {
            verwerkDeliveryReport(bericht, mailbox);
        } else if (bericht.getNotificationType() != null) {
            verwerkStatusReport(bericht);
        }

        berichtRepository.save(bericht);

        // Bewaar laatst succesvol gelezen mssequencenumber
        mailbox.setLaatsteMsSequenceNumber(seqNr);
        mailboxRepository.save(mailbox);
        LOGGER.debug("[Mailbox {}]: Succesvol ontvangen MSSequenceNumber {}", mailbox.getMailboxnr(), seqNr);

        LOGGER.info(FunctioneleMelding.VOISC_MAILBOX_ONTVANGEN);
        archiveerIngaandBericht(bericht);
    }

    private void verwerkDeliveryReport(final Bericht bericht, final Mailbox mailbox) {
        LOGGER.debug("[Bericht {}] Delivery-report verwerken", bericht.getId());

        // Bij Non-delivery doen we als deze mailbox de recipient is.
        bericht.setRecipient(mailbox.getMailboxnr());

        // Bepaal correlatie
        final Bericht gecorreleerdBericht =
                berichtRepository.zoekObvDispatchSequenceNumber(bericht.getDispatchSequenceNumber(), bericht.getRecipient(), bericht.getOriginator());

        if (gecorreleerdBericht != null) {
            LOGGER.debug(
                    "[Bericht {}] Correlatie op dispatch sequence number gevonden. "
                            + "Bericht-id: {}, Message-id: {}",
                    new Object[]{bericht.getId(), gecorreleerdBericht.getId(), gecorreleerdBericht.getMessageId(),});
            bericht.setCorrelationId(gecorreleerdBericht.getMessageId());
        } else {
            LOGGER.warn(
                    "[Bericht {}] Geen correlatie op dispatch sequence number gevonden voor delivery report. Bericht wordt genegeerd.",
                    bericht.getId());
            bericht.setStatus(StatusEnum.IGNORED);
        }

        // Bepaal inhoud
        final DeliveryReport deliveryReport = new DeliveryReport();
        deliveryReport.setNonDeliveryReason(Integer.parseInt(bericht.getNonDeliveryReason()));
        bericht.setBerichtInhoud(deliveryReport.format());
    }

    private void verwerkStatusReport(final Bericht bericht) {
        LOGGER.debug("[Bericht {}] Status-report verwerken", bericht.getId());

        // Bepaal inhoud
        final StatusReport statusReport = new StatusReport();
        statusReport.setNotificationType(Integer.parseInt(bericht.getNotificationType()));
        bericht.setBerichtInhoud(statusReport.format());
    }

    /**
     * logout from mailbox and disconnect from the mailboxserver.
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, value = VOISC_TRANSACTION_MANAGER)
    public void logout(final Connection connection) {
        try {
            mailboxClient.logOff(connection);
            LOGGER.info("Succesvol afgemeld bij mailbox server.");
        } catch (final VoaException spe) {
            // we log it, but no further action
            LOGGER.error("Onverwachte SPD protocol fout bij afmelden bij mailbox server.", spe);
        }
    }

}
