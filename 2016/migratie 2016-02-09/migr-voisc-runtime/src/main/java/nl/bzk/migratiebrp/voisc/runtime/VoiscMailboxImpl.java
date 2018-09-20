/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.DeliveryReport;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.StatusReport;
import nl.bzk.migratiebrp.bericht.model.sync.generated.RichtingType;
import nl.bzk.migratiebrp.util.common.logging.FunctioneleMelding;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.util.common.logging.MDC;
import nl.bzk.migratiebrp.util.common.logging.MDC.MDCCloser;
import nl.bzk.migratiebrp.util.common.logging.MDCVeld;
import nl.bzk.migratiebrp.voisc.database.entities.Bericht;
import nl.bzk.migratiebrp.voisc.database.entities.Mailbox;
import nl.bzk.migratiebrp.voisc.database.entities.StatusEnum;
import nl.bzk.migratiebrp.voisc.database.repository.BerichtRepository;
import nl.bzk.migratiebrp.voisc.database.repository.MailboxRepository;
import nl.bzk.migratiebrp.voisc.runtime.exceptions.VoiscMailboxException;
import nl.bzk.migratiebrp.voisc.runtime.exceptions.VoiscQueueException;
import nl.bzk.migratiebrp.voisc.runtime.utils.PasswordGenerator;
import nl.bzk.migratiebrp.voisc.spd.MailboxClient;
import nl.bzk.migratiebrp.voisc.spd.constants.SpdConstants;
import nl.bzk.migratiebrp.voisc.spd.exception.MailboxServerPasswordException;
import nl.bzk.migratiebrp.voisc.spd.exception.SpdProtocolException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Wrapper voor bewerkingen op de mailbox.
 */
public final class VoiscMailboxImpl implements VoiscMailbox {
    private static final String VOISC_TRANSACTION_MANAGER = "voiscTransactionManager";
    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final int TWO_MONTHS_AGO = -60;

    @Inject
    private MailboxClient mailboxClient;
    @Inject
    private MailboxRepository mailboxRepository;
    @Inject
    private BerichtRepository berichtRepository;
    @Inject
    private VoiscQueue archiveringService;

    @Override
    @SuppressWarnings("checkstyle:illegalcatch")
    public void connectToMailboxServer() throws VoiscMailboxException {
        try {
            mailboxClient.connect();
        } catch (final Exception e /* Connect gooit allerlei (ook runtime)excepties. Deze worden allen gewrapped */) {
            throw new VoiscMailboxException("Kan geen verbinding maken met de mailbox server.", e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.NEVER, value = VOISC_TRANSACTION_MANAGER)
    public void login(final Mailbox mailbox) throws VoiscMailboxException {
        if (mailbox.getMailboxpwd() == null || "".equals(mailbox.getMailboxpwd())) {
            LOGGER.warn("[Mailbox {}]: Geen mailbox wachtwoord beschikbaar.", mailbox.getMailboxnr());
            throw new VoiscMailboxException("Geen mailbox wachtwoord beschikbaar voor mailbox: " + mailbox.getMailboxnr());
        } else {
            try {
                mailboxClient.logOn(mailbox);
                LOGGER.debug("[Mailbox {}]: Succesfully logged on.", mailbox.getMailboxnr());

                if (checkPasswordChange(mailbox)) {
                    final String newPwd = PasswordGenerator.generate();
                    changeMBPassword(mailbox, newPwd);
                }
            } catch (final MailboxServerPasswordException mbpwe) {
                LOGGER.info("[Mailbox {}]: Verkeerde mailbox wachtwoord; aanmelden mislukt.", mailbox.getMailboxnr(), mbpwe);
                throw new VoiscMailboxException("Verkeerde mailbox password.", mbpwe);
            } catch (final SpdProtocolException spde) {
                LOGGER.warn("[Mailbox {}]: Onverwachte SPD fout bij aanmelden bij mailbox.", mailbox.getMailboxnr(), spde);
                throw new VoiscMailboxException("Onverwachte spd-protocol fout bij inloggen (of wijzigen wachtwoord).", spde);
            }
        }
    }

    private boolean checkPasswordChange(final Mailbox mailbox) {
        if (mailbox.getLaatsteWijzigingPwd() == null) {
            return true;
        }

        final Calendar checkDate = Calendar.getInstance();
        checkDate.add(Calendar.DATE, TWO_MONTHS_AGO);
        final Date laatsteWijzigingPwdDt = mailbox.getLaatsteWijzigingPwd();
        final Calendar laatsteWijzigingPwdCal = Calendar.getInstance();
        laatsteWijzigingPwdCal.setTime(laatsteWijzigingPwdDt);

        return laatsteWijzigingPwdCal.before(checkDate);
    }

    /**
     * Change password of the mailboxserver.
     *
     * @param newPW
     *            het nieuw in te stellen password
     * @throws VoiscMailboxException
     *             Als het wijzigen van het wachtwoord niet lukt.
     */
    private void changeMBPassword(final Mailbox mailbox, final String newPW) throws VoiscMailboxException {
        try {
            LOGGER.debug("[Mailbox {}]: Changing mailbox password", mailbox.getMailboxnr());
            mailboxClient.changePassword(mailbox, newPW);

            LOGGER.debug("[Mailbox {}]: Saving new mailbox password", mailbox.getMailboxnr());
            mailbox.setMailboxpwd(newPW);
            mailbox.setLaatsteWijzigingPwd(new Date());
            mailboxRepository.save(mailbox);

            LOGGER.info("[Mailbox {}]: Mailbox password succesfully changed.", mailbox.getMailboxnr());
        } catch (final
            MailboxServerPasswordException
            | SpdProtocolException mbe)
        {
            LOGGER.error("[Mailbox {}]: Onverwachte fout changing mailbox password.", mailbox.getMailboxnr(), mbe);
            throw new VoiscMailboxException("Kan mailbox wachtwoord niet wijzigen", mbe);
        }
    }

    /**
     * Send all messages to the mailbox. All berichten in the database marked as to be send are sent tot the mailbox.
     * This method will will send one message and than update the status of the message in the database. If it is not
     * possible to update the status of the message, because of database failures (i.e. database connection is lost)
     * this method will retry a given number of times to update the message. But the message itself will be send only
     * once to the Mailbox Server. We assume we are logged in to the mailbox server. I.e. login() must be called before
     * summarize() is called (and logout() somewhere after)
     *
     * @param regel
     *            Logboekregel zodat het aantal verzonden berichten vastgelegd kan worden
     * @param messagesToSend
     *            Lijst met berichten dat verstuurd moet worden
     */
    @Override
    @Transactional(propagation = Propagation.NEVER, value = VOISC_TRANSACTION_MANAGER)
    public void sendMessagesToMailbox(final Mailbox mailbox, final List<Bericht> messagesToSend) {
        for (final Bericht bericht : messagesToSend) {
            try (MDCCloser berichtIdCloser = MDC.put(MDCVeld.VOISC_BERICHT_ID, bericht.getId())) {
                // check of recipient bestaat.
                if (isRecipientTemporaryBlocked(bericht)) {
                    bericht.setStatus(StatusEnum.RECEIVED_FROM_ISC);
                    LOGGER.info("[Mailbox {}; Bericht {}]: Bericht heeft een tijdelijk geblokkeerde recipient.", mailbox.getMailboxnr(), bericht.getId());
                    berichtRepository.save(bericht);
                } else {
                    // Stuur bericht en sla o.a. het dispatchnummer op wat we van de MBS terug kijgen.
                    mailboxClient.putMessage(bericht);

                    bericht.setStatus(StatusEnum.SENT_TO_MAILBOX);
                    bericht.setTijdstipVerzonden(new Date());
                    berichtRepository.save(bericht);
                    LOGGER.info(FunctioneleMelding.VOISC_MAILBOX_VERSTUURD);

                    try {
                        archiveringService.archiveerBericht(bericht, RichtingType.UITGAAND);
                    } catch (final VoiscQueueException e) {
                        LOGGER.error("Uitgaand bericht kon niet verstuurd worden naar archivering queue!");
                    }
                }
            } catch (final SpdProtocolException spe) {
                LOGGER.error(
                    "[Mailbox {}; Bericht {}]: Onverwachte SPD protocol fout bij verzenden van bericht.",
                    new Object[] {mailbox.getMailboxnr(), bericht.getId(), spe, });
            }
        }

    }

    /**
     * Controleer of de recipient tijdelijk geblokkeerd is.
     *
     * @param bericht
     *            Het bericht waar de recipient van gecontroleerd wordt.
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
    public void receiveMessagesFromMailbox(final Mailbox mailbox) throws VoiscMailboxException {
        int nextSeqNr = mailbox.getLaatsteMsSequenceNumber() == null ? 0 : mailbox.getLaatsteMsSequenceNumber() + 1;
        LOGGER.debug("[Mailbox {}]: Start ontvangst bij MSSequenceNumber {}", mailbox.getMailboxnr(), nextSeqNr);
        int msgReceivedOk = 0;
        int msgReceivedNok = 0;

        final int limitNumber = mailbox.getLimitNumber();
        final List<Integer> seqNummers = new ArrayList<>(limitNumber);
        try {
            do {
                seqNummers.clear();
                nextSeqNr = mailboxClient.listMessages(nextSeqNr, seqNummers, limitNumber, "012", SpdConstants.PRIORITY);
                if (!seqNummers.isEmpty()) {
                    LOGGER.info(
                        "[Mailbox {}]: Lijst met {} berichten onvangen en vervolg sequence {}.",
                        new Object[] {mailbox.getMailboxnr(), seqNummers.size(), nextSeqNr, });
                }

                for (final Integer seqNr : seqNummers) {
                    try {
                        LOGGER.info("[Mailbox {}]: Start ontvangen bericht MSSequenceNumber {}", mailbox.getMailboxnr(), seqNr);
                        final Bericht bericht = mailboxClient.getMessage(seqNr);
                        if (bericht != null) {
                            save(bericht, mailbox, seqNr);
                            try (MDCCloser berichtIdCloser = MDC.put(MDCVeld.VOISC_BERICHT_ID, bericht.getId())) {
                                LOGGER.info(FunctioneleMelding.VOISC_MAILBOX_ONTVANGEN);
                                try {
                                    archiveringService.archiveerBericht(bericht, RichtingType.INGAAND);
                                } catch (final VoiscQueueException e) {
                                    LOGGER.error("Ingaand bericht kon niet verstuurd worden naar archivering queue!");
                                }
                            }

                            msgReceivedOk++;
                        } else {
                            msgReceivedNok++;
                        }
                    } catch (final SpdProtocolException spe) {
                        LOGGER.error(
                            "[Mailbox {}; MsSeqNr {}]: Onverwachte SPD protocol fout bij ontvangen van bericht.",
                            new Object[] {mailbox.getMailboxnr(), seqNr, spe, });
                        msgReceivedNok++;
                    }
                }
            } while (nextSeqNr != 0);
        } catch (final SpdProtocolException spe) {
            LOGGER.error("[Mailbox {}]: Onverwachte SPD protocol fout bij het opvragen van de lijst van berichten", mailbox.getMailboxnr(), spe);
            throw new VoiscMailboxException("Onverwachte SPD protocol fout bij het opvragen van de lijst van berichten", spe);
        } finally {
            if (msgReceivedOk > 0 || msgReceivedNok > 0) {
                LOGGER.info(
                    "[Mailbox {}]: {} (waarvan {} foutief) berichten ontvangen voor mailbox ",
                    new Object[] {mailbox.getMailboxnr(), msgReceivedNok + msgReceivedOk, msgReceivedNok, });
            }
        }
    }

    private void save(final Bericht bericht, final Mailbox mailbox, final Integer seqNr) {
        // Bewaar bericht
        bericht.setStatus(StatusEnum.RECEIVED_FROM_MAILBOX);
        bericht.setTijdstipOntvangst(new Date());

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
    }

    private void verwerkDeliveryReport(final Bericht bericht, final Mailbox mailbox) {
        LOGGER.info("[Bericht {}] Delivery-report verwerken", bericht.getId());

        // Bij Non-delivery doen we als deze mailbox de recipient is.
        bericht.setRecipient(mailbox.getMailboxnr());

        // Bepaal correlatie
        final Bericht gecorreleerdBericht =
                berichtRepository.zoekObvDispatchSequenceNumber(bericht.getDispatchSequenceNumber(), bericht.getRecipient(), bericht.getOriginator());

        if (gecorreleerdBericht != null) {
            LOGGER.info(
                "[Bericht {}] Correlatie op dispatch sequence number gevonden. "
                        + "Bericht-id: {}, Message-id: {}",
                new Object[] {bericht.getId(), gecorreleerdBericht.getId(), gecorreleerdBericht.getMessageId(), });
            bericht.setCorrelationId(gecorreleerdBericht.getMessageId());
        } else {
            LOGGER.info("[Bericht {}] Geen correlatie op dispatch sequence number gevonden. Bericht wordt genegeerd.", bericht.getId());
            bericht.setStatus(StatusEnum.IGNORED);
        }

        // Bepaal inhoud
        final DeliveryReport deliveryReport = new DeliveryReport();
        deliveryReport.setNonDeliveryReason(Integer.parseInt(bericht.getNonDeliveryReason()));
        bericht.setBerichtInhoud(deliveryReport.format());
    }

    private void verwerkStatusReport(final Bericht bericht) {
        LOGGER.info("[Bericht {}] Status-report verwerken", bericht.getId());

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
    public void logout() {
        try {
            mailboxClient.logOff();
            LOGGER.debug("Succesvol afgemeld bij mailbox server.");
        } catch (final SpdProtocolException spe) {
            // we log it, but no further action
            LOGGER.error("Onverwachte SPD protocol fout bij afmelden bij mailbox server.", spe);
        }
    }

}
