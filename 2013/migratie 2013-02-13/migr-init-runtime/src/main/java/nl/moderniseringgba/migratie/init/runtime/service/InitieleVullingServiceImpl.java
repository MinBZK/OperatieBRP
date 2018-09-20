/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.init.runtime.service;

import java.io.File;
import java.io.FileInputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import nl.moderniseringgba.isc.esb.message.JMSConstants;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Inhoud;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Lg01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.parser.Lo3PersoonslijstParser;
import nl.moderniseringgba.isc.esb.message.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.moderniseringgba.migratie.adapter.excel.ExcelAdapter;
import nl.moderniseringgba.migratie.adapter.excel.ExcelData;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.init.runtime.domein.ConversieResultaat;
import nl.moderniseringgba.migratie.init.runtime.repository.GbavRepository;
import nl.moderniseringgba.migratie.init.runtime.repository.Lo3BerichtVerwerker;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;
import nl.moderniseringgba.migratie.operatie.Herhaal;
import nl.moderniseringgba.migratie.operatie.HerhaalException;

import org.apache.commons.io.FileUtils;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.ProducerCallback;
import org.springframework.stereotype.Service;

/**
 * Deze service wordt gebruikt voor de acties van initiele vulling. In dit geval: - Het lezen en zetten van berichten op
 * de queue. - Het aanmaken van een initiele vulling tabel adhv de gbav database
 */
// CHECKSTYLE:OFF
@Service("initieleVullingService")
public final class InitieleVullingServiceImpl implements InitieleVullingService {
    // CHECKSTYLE:ON

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final int VERSTUUR_LOG_INTERVAL = 1000;

    private static final int QUEUE_SEND_THREAD_POOL_SIZE = 8;

    private static final int QUEUE_BATCH_SIZE = 100;

    @Inject
    private GbavRepository gbavRepository;

    @Inject
    private JmsTemplate jmsTemplate;

    @Inject
    private Destination destination;

    @Inject
    private ExcelAdapter excelAdapter;

    private final Lo3PersoonslijstParser parser = new Lo3PersoonslijstParser();

    /**
     * {@inheritDoc}
     */
    @Override
    public void leesLo3BerichtenEnVerstuur(final Properties config) throws ParseException {
        LOG.info("Start lezen en versturen LO3 berichten.");

        final ThreadPoolExecutor executor =
                new ThreadPoolExecutor(QUEUE_SEND_THREAD_POOL_SIZE, QUEUE_SEND_THREAD_POOL_SIZE, 0L,
                        TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(4 * QUEUE_SEND_THREAD_POOL_SIZE),
                        new ThreadPoolExecutor.CallerRunsPolicy());

        final VerzendLo3BerichtVerwerker verwerker =
                new VerzendLo3BerichtVerwerker(executor, ConversieResultaat.VERZONDEN);

        gbavRepository.verwerkLo3Berichten(ConversieResultaat.TE_VERZENDEN, config, verwerker, QUEUE_BATCH_SIZE);

        executor.shutdown();
        try {
            executor.awaitTermination(2, TimeUnit.HOURS);
        } catch (final InterruptedException e) {
            LOG.warn("InterruptedException tijdens wachten op het klaar zijn van de queue vuller");
        }

        LOG.info("Klaar met versturen van Lo3Berichten.");
        LOG.info("Totaal verwerkte berichten: {}", verwerker.getVerwerkTeller());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createInitieleVullingTable(final Properties config) throws ParseException {
        LOG.info("De initiele vulling tabel wordt nu aangemaakt en gevuld, dit kan even duren!");
        gbavRepository.createAndFillInitVullingTable();
        LOG.info("De initiele vulling tabel is aangemaakt.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void vulBerichtenTabelExcel(final Properties config, final String excelFolder) {
        LOG.info("Lees de Excel bestanden in en maak op basis hiervan een initiele vulling tabel");
        final List<File> files = bepaalExcelFiles(excelFolder);
        LOG.info("Aanmaken tabel als deze nog niet bestaat");
        gbavRepository.createInitVullingTable();
        LOG.info("Aantal gevonden excel files: " + files.size());
        verwerkExcelBestanden(files);
    }

    private void verwerkExcelBestanden(final List<File> files) {
        for (final File file : files) {
            try {

                // Lees excel
                final List<ExcelData> excelDatas = excelAdapter.leesExcelBestand(new FileInputStream(file));

                // Parsen input *ZONDER* syntax en precondite controles
                final List<Lo3Persoonslijst> lo3Persoonslijsten = new ArrayList<Lo3Persoonslijst>();
                for (final ExcelData excelData : excelDatas) {
                    lo3Persoonslijsten.add(parser.parse(excelData.getCategorieLijst()));
                }

                for (final Lo3Persoonslijst pl : lo3Persoonslijsten) {
                    final String lg01 = formateerAlsLg01(pl);
                    final Lo3GemeenteCode gemeenteVanInschrijving =
                            pl.getVerblijfplaatsStapel().getMeestRecenteElement().getInhoud()
                                    .getGemeenteInschrijving();
                    final Integer gemeenteVanInschrijvingCode;
                    if (gemeenteVanInschrijving.isValideNederlandseGemeenteCode()) {
                        gemeenteVanInschrijvingCode = Integer.parseInt(gemeenteVanInschrijving.getCode());
                    } else {
                        gemeenteVanInschrijvingCode = null;
                    }
                    gbavRepository.saveLg01(lg01, pl.getActueelAdministratienummer(), gemeenteVanInschrijvingCode,
                            ConversieResultaat.TE_VERZENDEN);
                }
                // CHECKSTYLE:OFF
            } catch (final Exception e) {
                // CHECKSTYLE:ON
                e.printStackTrace();
                LOG.error("Probleem bij het inlezen van van de persoonslijst in file: " + file
                        + ". Inlezen wordt voortgezet.");
            }
        }
    }

    private List<File> bepaalExcelFiles(final String excelFolder) {
        final File inputFolder = new File(excelFolder);
        LOG.info(String.format("Input folder %s %s gevonden", inputFolder, inputFolder.exists() ? "is" : "niet"));
        final List<File> files = new ArrayList<File>(FileUtils.listFiles(inputFolder, new String[] { "xls" }, true));
        Collections.sort(files);
        return files;
    }

    private boolean verstuurBericht(final List<SyncNaarBrpBericht> lo3Berichten) {
        try {
            Herhaal.herhaalOperatie(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    jmsTemplate.execute(destination, new ProducerCallback<Object>() {
                        @Override
                        public Object doInJms(final Session session, final MessageProducer producer)
                                throws JMSException {
                            for (final SyncNaarBrpBericht lo3Bericht : lo3Berichten) {
                                final Message message = session.createTextMessage(lo3Bericht.getBericht());
                                message.setStringProperty(JMSConstants.BERICHT_REFERENTIE, lo3Bericht.getMessageId());
                                producer.send(message);
                            }
                            session.commit();
                            return null;
                        }
                    });
                    return null;
                }
            });
        } catch (final HerhaalException e) {
            LOG.error("Kon berichten niet verzenden.", e);
            return false;
        }
        return true;
    }

    private String formateerAlsLg01(final Lo3Persoonslijst lo3) {
        final Lg01Bericht lg01 = new Lg01Bericht();
        lg01.setLo3Persoonslijst(lo3);
        return Lo3Inhoud.formatInhoud(lg01.formatInhoud());
    }

    /**
     * Verstuurt Lo3Berichten.
     */
    private class VerzendLo3BerichtVerwerker implements Lo3BerichtVerwerker {
        private List<SyncNaarBrpBericht> lo3Berichten = new ArrayList<SyncNaarBrpBericht>(QUEUE_BATCH_SIZE);
        private final AtomicInteger verwerkTeller = new AtomicInteger(0);
        private final AtomicInteger verwerktTotaal = new AtomicInteger(0);
        private final ThreadPoolExecutor executor;
        private final ConversieResultaat verwerktStatus;

        public VerzendLo3BerichtVerwerker(final ThreadPoolExecutor executor, final ConversieResultaat verwerktStatus) {
            this.executor = executor;
            this.verwerktStatus = verwerktStatus;
        }

        @Override
        public void addLo3Bericht(final String lo3Bericht, final long aNummer) {
            lo3Berichten.add(new SyncNaarBrpBericht(aNummer, lo3Bericht));
        }

        @Override
        public Void call() throws Exception {
            final List<SyncNaarBrpBericht> teVersturenBerichten = lo3Berichten;
            lo3Berichten = new ArrayList<SyncNaarBrpBericht>(QUEUE_BATCH_SIZE);

            executor.submit(new Runnable() {
                @Override
                public void run() {
                    final boolean verstuurd = verstuurBericht(teVersturenBerichten);

                    final int verwerktCount = verwerkTeller.addAndGet(teVersturenBerichten.size());
                    if (verwerktCount >= VERSTUUR_LOG_INTERVAL) {
                        LOG.info("Verwerkte Lo3Berichten: {}", verwerktTotaal.addAndGet(VERSTUUR_LOG_INTERVAL));
                        verwerkTeller.addAndGet(-VERSTUUR_LOG_INTERVAL);
                    }

                    if (verstuurd) {
                        final List<Long> aNummers = new ArrayList<Long>(lo3Berichten.size());
                        for (final Long aNummer : aNummers) {
                            aNummers.add(aNummer);
                        }
                        gbavRepository.updateLo3BerichtStatus(aNummers, verwerktStatus);
                    }
                }
            });

            return null;
        }

        public int getVerwerkTeller() {
            return verwerkTeller.intValue() + verwerktTotaal.intValue();
        }
    }

    /**
     * Houder voor LO3 bericht text en A-Nummer.
     */
    private static final class SyncNaarBrpBericht {
        private final long aNummer;
        private final String bericht;

        private SyncNaarBrpBericht(final long aNummer, final String bericht) {
            this.aNummer = aNummer;
            this.bericht = new SynchroniseerNaarBrpVerzoekBericht(bericht).format();
        }

        public String getBericht() {
            return bericht;
        }

        public String getMessageId() {
            return String.valueOf(aNummer);
        }
    }
}
