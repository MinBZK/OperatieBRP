/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.logging.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import nl.gba.gbav.impl.util.configuration.ServiceLocatorSpringImpl;
import nl.gba.gbav.lo3.PLData;
import nl.gba.gbav.lo3.util.PLBuilderFactory;
import nl.gba.gbav.spontaan.GebeurtenisAnalyse;
import nl.gba.gbav.spontaan.impl.DatumKey;
import nl.gba.gbav.spontaan.impl.Gebeurtenis;
import nl.gba.gbav.spontaan.impl.GebeurtenisAnalyseImpl;
import nl.gba.gbav.spontaan.impl.SpontaanRapportage;
import nl.gba.gbav.spontaan.verschilanalyse.PlDiffResult;
import nl.gba.gbav.spontaan.verschilanalyse.PlVerschilAnalyse;
import nl.gba.gbav.spontaan.verschilanalyse.impl.PlVerschilAnalyseImpl;
import nl.gba.gbav.util.configuration.ServiceLocator;
import nl.ictu.spg.domain.lo3.util.LO3LelijkParser;
import nl.ictu.spg.domain.pl.util.PLAssembler;
import nl.moderniseringgba.migratie.logging.domein.entities.FingerPrint;
import nl.moderniseringgba.migratie.logging.domein.entities.InitVullingLog;
import nl.moderniseringgba.migratie.logging.repository.InitVullingLogRepository;
import nl.moderniseringgba.migratie.logging.service.LoggingService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementatie voor de BrpDalService.
 */
@Service
public final class LoggingServiceImpl implements LoggingService {

    private static final Integer LG01_TYPE = 1111;
    private static final Integer LA01_TYPE = 1112;

    static {
        // Nodig voor de PLData builder.
        final String id = System.getProperty("gbav.deployment.id", "vergelijk");
        final String context =
                System.getProperty("gbav.deployment.context", "classpath:gbavconfig/deploymentContext.xml");
        ServiceLocator.initialize(new ServiceLocatorSpringImpl(context, id));
    }

    private final InitVullingLogRepository logRepository;

    /**
     * Default constructor.
     *
     * @param logRepository
     *            log repository voor het bevragen en opslaan van log regels
     */
    @Inject
    LoggingServiceImpl(final InitVullingLogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Transactional(value = "loggingTransactionManager", propagation = Propagation.REQUIRED)
    @Override
    public void persisteerInitVullingLog(final InitVullingLog log) {
        logRepository.persistLog(log);
    }

    @Transactional(value = "loggingTransactionManager", propagation = Propagation.REQUIRED)
    @Override
    public void createAndStoreDiff(final InitVullingLog log) {
        
        if (log.getBrpLo3Bericht() != null && log.getLo3Bericht() != null) {
            final PlVerschilAnalyse pda = new PlVerschilAnalyseImpl();
            // Gbav pl in LO3 formaat
            final PLData plGbav = createPLData(log, log.getLo3Bericht());
            // Brp pl in LO3 formaat
            final PLData plBrp = createPLData(log, log.getBrpLo3Bericht());

            // Bepaal de verschillen tussen beide PL-en
            final SpontaanRapportage rapportage = new SpontaanRapportage();
            final PlDiffResult diffResult = pda.bepaalVerschillen(rapportage, plGbav, plBrp, false);
            final GebeurtenisAnalyse gebAnalyzer = new GebeurtenisAnalyseImpl();
            final Map<DatumKey, List<Gebeurtenis>> gebeurtenissen = gebAnalyzer.determineGebeurtenissen(diffResult);

            if (gebeurtenissen != null) {
                for (final List<Gebeurtenis> gebeurtenissenOpDatum : gebeurtenissen.values()) {
                    if (gebeurtenissenOpDatum != null) {
                        for (final Gebeurtenis gebeurtenis : gebeurtenissenOpDatum) {
                            final String fingerPrintVerschil = FingerPrintGenerator.createFingerprint(gebeurtenis);
                            final FingerPrint fingerPrint = new FingerPrint();
                            fingerPrint.setVoorkomenVerschil(fingerPrintVerschil);
                            log.addFingerPrint(fingerPrint);
                        }
                    }
                }
            }
            log.setBerichtDiff(diffResult.toString());
        }
        logRepository.persistLog(log);
    }

    private PLData createPLData(final InitVullingLog log, final String pl) {
        final PLData plData = PLBuilderFactory.getPLDataBuilder().create();
        if (pl != null) {
            final PLAssembler assembler = new PLAssembler();
            assembler.startOfTraversal(plData);
            final LO3LelijkParser parser = new LO3LelijkParser();
            if (log.getBerichtType().equals(LG01_TYPE)) {
                parser.parse(pl, assembler);
            } else if (log.getBerichtType().equals(LA01_TYPE)) {
                parser.parse(pl, assembler);
            }
        }
        return plData;
    }

    @Transactional(value = "loggingTransactionManager", propagation = Propagation.REQUIRED)
    @Override
    public InitVullingLog findLog(final Long anummer) {
        return logRepository.findLog(anummer);
    }

    @Override
    public List<Long> findLogs(final Date vanaf, final Date tot, final String gemeenteCode) {
        return logRepository.findLogs(vanaf, tot, gemeenteCode);
    }
}
