/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.levering.vergelijker.job;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import nl.bzk.migratiebrp.tools.levering.vergelijker.entity.BrpBericht;
import nl.bzk.migratiebrp.tools.levering.vergelijker.entity.GbaBericht;
import nl.bzk.migratiebrp.tools.levering.vergelijker.entity.LeveringsVergelijkingBerichtCorrelatieBrp;
import nl.bzk.migratiebrp.tools.levering.vergelijker.entity.LeveringsVergelijkingBerichtCorrelatieGbav;
import nl.bzk.migratiebrp.tools.levering.vergelijker.entity.LeveringsVergelijkingResultaatInhoud;
import nl.bzk.migratiebrp.tools.levering.vergelijker.entity.LeveringsVergelijkingResultaatKop;
import nl.bzk.migratiebrp.tools.levering.vergelijker.repository.BrpBerichtRepository;
import nl.bzk.migratiebrp.tools.levering.vergelijker.repository.GbaBerichtRepository;
import nl.bzk.migratiebrp.tools.levering.vergelijker.repository.LeveringsVergelijkingBerichtCorrelatieBrpRepository;
import nl.bzk.migratiebrp.tools.levering.vergelijker.repository.LeveringsVergelijkingBerichtCorrelatieGbavRepository;
import nl.bzk.migratiebrp.tools.levering.vergelijker.repository.LeveringsVergelijkingResultaatInhoudRepository;
import nl.bzk.migratiebrp.tools.levering.vergelijker.repository.LeveringsVergelijkingResultaatKopRepository;
import nl.bzk.migratiebrp.tools.levering.vergelijker.service.LeveringsVergelijkingService;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Main klasse voor het starten van het levering vergelijker proces.
 *
 */
public final class LeveringVergelijkerJob {

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Main methode.
     *
     * @param context
     *            De meegegeven context.
     */
    public void execute(final ConfigurableApplicationContext context) {

        LOG.info("Start job: " + this.getClass().getName());
        final LeveringsVergelijkingService leveringVergelijkerService =
                context.getBean("leveringsVergelijkingService", LeveringsVergelijkingService.class);
        final BrpBerichtRepository brpBerichtRepository = context.getBean("brpBerichtRepository", BrpBerichtRepository.class);
        final GbaBerichtRepository gbaBerichtRepository = context.getBean("gbaBerichtRepository", GbaBerichtRepository.class);
        final LeveringsVergelijkingBerichtCorrelatieGbavRepository leveringsVergelijkingBerichtCorrelatieGbavRepository =
                context.getBean("leveringsVergelijkingBerichtCorrelatieGbaRepository", LeveringsVergelijkingBerichtCorrelatieGbavRepository.class);
        final LeveringsVergelijkingBerichtCorrelatieBrpRepository leveringsVergelijkingBerichtCorrelatieBrpRepository =
                context.getBean("leveringsVergelijkingBerichtCorrelatieBrpRepository", LeveringsVergelijkingBerichtCorrelatieBrpRepository.class);
        final LeveringsVergelijkingResultaatKopRepository leveringsVergelijkingResultaatKopRepository =
                context.getBean("leveringsVergelijkingResultaatKopRepository", LeveringsVergelijkingResultaatKopRepository.class);
        final LeveringsVergelijkingResultaatInhoudRepository leveringsVergelijkingResultaatInhoudRepository =
                context.getBean("leveringsVergelijkingResultaatInhoudRepository", LeveringsVergelijkingResultaatInhoudRepository.class);

        final List<LeveringsVergelijkingBerichtCorrelatieGbav> leveringsVergelijkingBerichtCorrelatieGbav =
                leveringsVergelijkingBerichtCorrelatieGbavRepository.haalLeveringVergelijkingBerichtenGbavOp();

        LOG.info("Aantal te controleren berichten (gabv): " + leveringsVergelijkingBerichtCorrelatieGbav.size());
        final List<Runnable> inputs = new ArrayList<>();

        for (final LeveringsVergelijkingBerichtCorrelatieGbav huidigeLeveringVergelijkerBerichtenCorrelatie : leveringsVergelijkingBerichtCorrelatieGbav) {
            inputs.add(new LeveringVergelijkerVerwerker(
                brpBerichtRepository,
                gbaBerichtRepository,
                leveringsVergelijkingResultaatInhoudRepository,
                leveringsVergelijkingResultaatKopRepository,
                leveringsVergelijkingBerichtCorrelatieBrpRepository,
                huidigeLeveringVergelijkerBerichtenCorrelatie,
                leveringVergelijkerService));
        }

        try {

            final ExecutorService executorService = Executors.newFixedThreadPool(16);

            for (final Runnable input : inputs) {
                executorService.execute(input);
            }

            executorService.shutdown();
            while (!executorService.awaitTermination(1, TimeUnit.MINUTES)) {
                LOG.info("Berichten worden nog vergeleken.");
            }

        } catch (final InterruptedException e) {
            LOG.error("Vergelijken is onderbroken.");
        }

        LOG.info("Vergelijken levering berichten beëindigd.");

        LOG.debug("Einde job: " + this.getClass().getName());

    }

    /**
     * Inner class voor multithreaded verwerking.
     */
    private final class LeveringVergelijkerVerwerker implements Runnable {

        private final BrpBerichtRepository brpBerichtRepository;
        private final GbaBerichtRepository gbaBerichtRepository;
        private final LeveringsVergelijkingResultaatInhoudRepository leveringsVergelijkingResultaatInhoudRepository;
        private final LeveringsVergelijkingResultaatKopRepository leveringsVergelijkingResultaatKopRepository;
        private final LeveringsVergelijkingBerichtCorrelatieBrpRepository leveringsVergelijkingBerichtCorrelatieBrpRepository;
        private final LeveringsVergelijkingBerichtCorrelatieGbav leveringsVergelijkingBerichtCorrelatieGbav;
        private final LeveringsVergelijkingService leveringsVergelijkingService;

        /**
         * Default constructor.
         *
         * @param brpBerichtRepository
         *            De BRP bericht repository.
         * @param gbaBerichtRepository
         *            De GBAV bericht repository.
         * @param leveringsVergelijkingResultaatInhoudRepository
         *            De repository voor het inhoudelijke vergelijkingsresultaat.
         * @param leveringsVergelijkingResultaatKopRepository
         *            De repository voor het kop vergelijkingsresultaat.
         * @param leveringsVergelijkingBerichtCorrelatieBrpRepository
         *            De repository voor de BRP bericht correlatie.
         * @param leveringsVergelijkingBerichtCorrelatieGbav
         *            De repository voor de GBAV bericht correlatie.
         * @param leveringsVergelijkingService
         *            De vergelijkings service.
         */
        public LeveringVergelijkerVerwerker(
            final BrpBerichtRepository brpBerichtRepository,
            final GbaBerichtRepository gbaBerichtRepository,
            final LeveringsVergelijkingResultaatInhoudRepository leveringsVergelijkingResultaatInhoudRepository,
            final LeveringsVergelijkingResultaatKopRepository leveringsVergelijkingResultaatKopRepository,
            final LeveringsVergelijkingBerichtCorrelatieBrpRepository leveringsVergelijkingBerichtCorrelatieBrpRepository,
            final LeveringsVergelijkingBerichtCorrelatieGbav leveringsVergelijkingBerichtCorrelatieGbav,
            final LeveringsVergelijkingService leveringsVergelijkingService)
        {
            this.brpBerichtRepository = brpBerichtRepository;
            this.gbaBerichtRepository = gbaBerichtRepository;
            this.leveringsVergelijkingResultaatInhoudRepository = leveringsVergelijkingResultaatInhoudRepository;
            this.leveringsVergelijkingResultaatKopRepository = leveringsVergelijkingResultaatKopRepository;
            this.leveringsVergelijkingBerichtCorrelatieBrpRepository = leveringsVergelijkingBerichtCorrelatieBrpRepository;
            this.leveringsVergelijkingBerichtCorrelatieGbav = leveringsVergelijkingBerichtCorrelatieGbav;
            this.leveringsVergelijkingService = leveringsVergelijkingService;
        }

        @Override
        public void run() {
            try {
                // Let op: de proefsynchronistatie verstuurt het bnijhoudingsbericht met als EREF het bericht ID uit
                // GBAV.
                final List<LeveringsVergelijkingBerichtCorrelatieBrp> leveringsVergelijkingBerichtCorrelatieBrp =
                        leveringsVergelijkingBerichtCorrelatieBrpRepository.haalLeveringsVergelijkingBerichtCorrelatiesBrpOp(
                            leveringsVergelijkingBerichtCorrelatieGbav.getBijhoudingBerichtId(),
                            leveringsVergelijkingBerichtCorrelatieGbav.getAfnemerCode());

                final GbaBericht gbaLeveringBericht = gbaBerichtRepository.haalGbaLeveringBerichtOp(leveringsVergelijkingBerichtCorrelatieGbav);

                if (leveringsVergelijkingBerichtCorrelatieBrp != null) {
                    for (final LeveringsVergelijkingBerichtCorrelatieBrp huidigeBerichtCorrelatieBrp : leveringsVergelijkingBerichtCorrelatieBrp) {

                        final BrpBericht brpLeveringBericht = brpBerichtRepository.haalBrpLeveringBerichtOp(huidigeBerichtCorrelatieBrp);

                        final LeveringsVergelijkingResultaatKop gevondenVerschillenKopLevering = new LeveringsVergelijkingResultaatKop();
                        List<LeveringsVergelijkingResultaatInhoud> gevondenVerschillenInhoudLevering = new ArrayList<>();

                        if (gbaLeveringBericht != null
                                && brpLeveringBericht != null
                                && gbaLeveringBericht.getBerichtType().equals(brpLeveringBericht.getBerichtType()))
                        {
                            gevondenVerschillenInhoudLevering =
                                    leveringsVergelijkingService.vergelijkInhoudLeveringsBerichten(
                                        gbaLeveringBericht.getBerichtInhoud(),
                                        brpLeveringBericht.getBerichtInhoud());

                            for (final LeveringsVergelijkingResultaatInhoud huidigGevondenVerschilLevering : gevondenVerschillenInhoudLevering) {
                                huidigGevondenVerschilLevering.setAfnemerCode(huidigeBerichtCorrelatieBrp.getAfnemerCode());
                                huidigGevondenVerschilLevering.setBijhoudingBerichtEref(huidigeBerichtCorrelatieBrp.getBijhoudingBerichtMessageId());
                                huidigGevondenVerschilLevering.setBijhoudingBerichtIdGbav(leveringsVergelijkingBerichtCorrelatieGbav.getBijhoudingBerichtId());
                                huidigGevondenVerschilLevering.setBijhoudingBerichtIdBrp(huidigeBerichtCorrelatieBrp.getBijhoudingBerichtId());
                                huidigGevondenVerschilLevering.setLeveringBerichtIdBrp(huidigeBerichtCorrelatieBrp.getLeveringBerichtId());
                                huidigGevondenVerschilLevering.setLeveringBerichtIdGbav(leveringsVergelijkingBerichtCorrelatieGbav.getLeveringBerichtId());
                                huidigGevondenVerschilLevering.setBerichtNummer(brpLeveringBericht.getBerichtType());
                            }

                            gevondenVerschillenKopLevering.setAfwijkingen(leveringsVergelijkingService.vergelijkKopLeveringsBerichten(
                                gbaLeveringBericht.getBericht(),
                                brpLeveringBericht.getBericht()));
                        }

                        if (gevondenVerschillenKopLevering.getAfwijkingen() != null && !"".equals(gevondenVerschillenKopLevering.getAfwijkingen())) {
                            gevondenVerschillenKopLevering.setAfnemerCode(huidigeBerichtCorrelatieBrp.getAfnemerCode());
                            gevondenVerschillenKopLevering.setBijhoudingBerichtEref(huidigeBerichtCorrelatieBrp.getBijhoudingBerichtMessageId());
                            gevondenVerschillenKopLevering.setBijhoudingBerichtIdGbav(leveringsVergelijkingBerichtCorrelatieGbav.getBijhoudingBerichtId());
                            gevondenVerschillenKopLevering.setBijhoudingBerichtIdBrp(huidigeBerichtCorrelatieBrp.getBijhoudingBerichtId());
                            gevondenVerschillenKopLevering.setLeveringBerichtIdBrp(huidigeBerichtCorrelatieBrp.getLeveringBerichtId());
                            gevondenVerschillenKopLevering.setLeveringBerichtIdGbav(leveringsVergelijkingBerichtCorrelatieGbav.getLeveringBerichtId());
                            gevondenVerschillenKopLevering.setBerichtNummer(brpLeveringBericht.getBerichtType());
                            leveringsVergelijkingResultaatKopRepository.opslaanLeveringsVergelijkingKopResultaat(gevondenVerschillenKopLevering);
                        }
                        leveringsVergelijkingResultaatInhoudRepository.opslaanLeveringsVergelijkingInhoudResultaten(gevondenVerschillenInhoudLevering);

                    }
                }
            } finally {
                LOG.debug("Vergelijking voor leveringsVergelijkingBerichtCorrelatieGbav met id "
                        + leveringsVergelijkingBerichtCorrelatieGbav.getId()
                        + " voltooid.");
            }
        }
    }
}
