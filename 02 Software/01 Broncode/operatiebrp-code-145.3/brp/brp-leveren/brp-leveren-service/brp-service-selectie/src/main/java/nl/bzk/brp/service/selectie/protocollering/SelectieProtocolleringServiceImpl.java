/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.protocollering;

import com.google.common.collect.Lists;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsaantekening;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Selectietaak;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.HistorieVorm;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SelectietaakStatus;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import nl.bzk.algemeenbrp.util.common.serialisatie.SerialisatieExceptie;
import nl.bzk.brp.protocollering.domain.algemeen.LeveringPersoon;
import nl.bzk.brp.protocollering.domain.algemeen.ProtocolleringOpdracht;
import nl.bzk.brp.protocollering.service.algemeen.ProtocolleringService;
import nl.bzk.brp.service.selectie.schrijver.SelectieFileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Service voor het protocolleren van het protocolleringsbestand uit de selectierun.
 */
@Component
@Bedrijfsregel(Regel.R2664)
@Bedrijfsregel(Regel.R2665)
final class SelectieProtocolleringServiceImpl implements SelectieProtocolleringService {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final JsonStringSerializer SERIALIZER = new JsonStringSerializer();

    @Inject
    private SelectieFileService selectieFileService;

    @Inject
    private SelectieProtocolleringDataService selectieProtocolleringDataService;

    @Inject
    private ProtocolleringService protocolleringService;

    @Value("${brp.selectie.protocollering.flushsize:500}")
    private int configFlushSize;
    @Value("${brp.selectie.protocollering.voortgang:500}")
    private int configVoortgangSize;
    @Value("${brp.selectie.protocollering.poolsize:1}")
    private int configPoolsize;
    @Value("${brp.selectie.protocollering.maxlooptijd:1}")
    private int configMaxLooptijd;

    private boolean run;

    private List<Protocolleringbestand> worklist;

    private SelectieProtocolleringServiceImpl() {
    }

    @Override
    @Async("protocolleringjob")
    public void start() {
        LOG.info("Start Protocolleren");
        final ExecutorService executor = Executors.newFixedThreadPool(configPoolsize);
        try {
            run = true;
            this.worklist = selectieProtocolleringDataService.selecteerTeProtocollerenSelectietaken()
                    .stream()
                    .peek(selectietaak -> LOG.info("Te protocolleren taak: {}", selectietaak.getId()))
                    .map(Protocolleringbestand::new)
                    .collect(Collectors.toList());
            LOG.info("Aantal te verwerken taken = {}", worklist.size());
            final ConcurrentLinkedQueue<Protocolleringbestand> queue = new ConcurrentLinkedQueue<>(worklist);
            List<Future<Object>> futures = new ArrayList<>(configPoolsize);
            for (int i = 0; i < configPoolsize; i++) {
                final ProtocolleringTaak taak = new ProtocolleringTaak(queue);
                futures.add(executor.submit(taak));
            }
            for (Future<Object> future : futures) {
                future.get(configMaxLooptijd, TimeUnit.MINUTES);
            }

        } catch (TimeoutException | ExecutionException e) {
            LOG.info("error in uitvoeren protocolleer taak", e);
        } catch (InterruptedException e) {
            LOG.info("interrupt in uitvoeren protocolleer taak", e);
            Thread.currentThread().interrupt();
        } finally {
            LOG.info("Protocolleren gestopt");
            run = false;
            executor.shutdownNow();
        }
    }

    @Override
    public void stop() {
        if (run) {
            LOG.info("Forceer stop");
            run = false;
        }
    }

    @Override
    public boolean isRunning() {
        return run;
    }

    @Override
    public void pauzeerVerwerking(int selectietaak) {
        List<Protocolleringbestand> ref = worklist;
        if (ref != null) {
            ref.stream().filter(protocolleringbestand -> protocolleringbestand.selectietaak.getId() == selectietaak)
                    .findFirst().ifPresent(Protocolleringbestand::notificeerPauzeerVerwerking);
        }
    }


    private final class ProtocolleringTaak implements Callable<Object> {

        final ConcurrentLinkedQueue<Protocolleringbestand> queue;

        private ProtocolleringTaak(final ConcurrentLinkedQueue<Protocolleringbestand> queue) {
            this.queue = queue;
        }

        @Override
        public Object call() throws Exception {

            while (run) {
                Protocolleringbestand protocolleringbestand = null;
                try {
                    protocolleringbestand = queue.poll();
                    if (protocolleringbestand == null) {
                        break;
                    }
                    final boolean kanProtocolleren = protocolleringbestand.bepaalProtocolleerBestand();
                    if (kanProtocolleren) {
                        protocolleringbestand.setStatusProtocolleringInUitvoering();
                        LOG.info("Te protocolleren bestand: {}", protocolleringbestand.protocolleringBestandPath);
                        protocolleringbestand.maakLeveringsaantekening();
                        protocolleringbestand.protocolleer();
                        protocolleringbestand.bepaalEindStatus();
                    }
                } catch (Exception e) {
                    LOG.error("Onbekende fout tijdens protocolleren", e);
                    if (protocolleringbestand != null) {
                        protocolleringbestand.selectietaak = selectieProtocolleringDataService
                                .updateSelectietaakStatus(protocolleringbestand.selectietaak, SelectietaakStatus.PROTOCOLLERING_MISLUKT);
                    }
                }
            }
            return null;
        }
    }


    private final class Protocolleringbestand {

        private Selectietaak selectietaak;
        private Path protocolleringBestandPath;
        private int regelsVerwerkt;
        private int totaalAantalRegels;
        private List<LeveringPersoon> teProtocollerenPersonen = Lists.newArrayList();
        private Leveringsaantekening leveringsaantekening;
        private boolean pauzeerVerwerking;

        Protocolleringbestand(final Selectietaak selectietaak) {
            this.selectietaak = selectietaak;
        }

        /**
         * Selecteer het te protocolleren bestand van de selectietaak.
         */
        @Bedrijfsregel(Regel.R2663)
        private boolean bepaalProtocolleerBestand() {
            protocolleringBestandPath = selectieFileService.getSelectietaakResultaatPath(selectietaak.getId(),
                    DatumUtil.vanDatumNaarInteger(selectietaak.getUitgevoerdIn().getTijdstipStart()))
                    .resolve(selectieFileService.geefProtocolleringBestand());

            if (protocolleringBestandPath == null || !protocolleringBestandPath.toFile().exists()) {
                LOG.info("Geen protocolleringbestand gevonden voor taak: {}", selectietaak.getId());
                selectietaak =
                        selectieProtocolleringDataService.updateSelectietaakStatus(selectietaak, SelectietaakStatus.TE_PROTOCOLLEREN_BESTAND_NIET_GEVONDEN);
                //stop de flow
                return false;
            }
            try {
                bepaalTotaalAantalRegels();
            } catch (IOException e) {
                LOG.error("Protcollering mislukt: Aantal regels in protocolleringbestand kan niet bepaald worden: {}",
                        protocolleringBestandPath, e);
                selectietaak = selectieProtocolleringDataService.updateSelectietaakStatus(selectietaak, SelectietaakStatus.PROTOCOLLERING_MISLUKT);
                //stop de flow
                return false;
            }
            //continueer de flow
            return true;
        }

        private void setStatusProtocolleringInUitvoering() {
            selectietaak = selectieProtocolleringDataService.updateSelectietaakStatus(selectietaak, SelectietaakStatus.PROTOCOLLERING_IN_UITVOERING);
        }

        private void notificeerPauzeerVerwerking() {
            this.pauzeerVerwerking = true;
            LOG.info("Pauzeer verwerking protocolleringbestand voor taak: {}", selectietaak.getId());
        }

        private void bepaalTotaalAantalRegels() throws IOException {
            try (LineNumberReader lnr = getLineNumberReader()) {
                lnr.skip(Integer.MAX_VALUE);
                this.totaalAantalRegels = lnr.getLineNumber();
            }
            LOG.info("Aantal Protocolleringregels voor bestand {} = {}", protocolleringBestandPath, totaalAantalRegels);
        }

        private void protocolleer() {
            try (LineNumberReader lineNumberReader = getLineNumberReader()) {
                //nog te doen ROOD-906, skip naar selectietaak.voortgang indien eerder gepauzeerd
                String line;
                while ((line = lineNumberReader.readLine()) != null && run && !pauzeerVerwerking) {
                    protocolleerRegel(line);
                    regelsVerwerkt = lineNumberReader.getLineNumber();
                }
                flush(true);
            } catch (IOException e) {
                LOG.error("Protocollering mislukt: Fout tijdens inlezen protolleringbestand {}", protocolleringBestandPath, e);
                selectietaak = selectieProtocolleringDataService.updateSelectietaakStatus(selectietaak, SelectietaakStatus.PROTOCOLLERING_MISLUKT);
            }
        }

        private void bepaalEindStatus() {
            if (SelectietaakStatus.parseId((int) selectietaak.getStatus()) == SelectietaakStatus.PROTOCOLLERING_MISLUKT) {
                return;
            }
            SelectietaakStatus nieuweStatus = SelectietaakStatus.PROTOCOLLERING_UITGEVOERD;
            if (!run) {
                nieuweStatus = SelectietaakStatus.PROTOCOLLERING_AFGEBROKEN;
            } else if (pauzeerVerwerking) {
                //nog te doen ROOD-906, status pauze ontbreekt + voortgang kolom database
                nieuweStatus = SelectietaakStatus.UITVOERING_AFGEBROKEN;
            }
            selectietaak = selectieProtocolleringDataService.updateSelectietaakStatus(selectietaak, nieuweStatus);
        }

        private LineNumberReader getLineNumberReader() throws IOException {
            return new LineNumberReader(new BufferedReader(
                    new InputStreamReader(Files.newInputStream(protocolleringBestandPath, StandardOpenOption.READ), StandardCharsets.UTF_8)));
        }

        private void protocolleerRegel(final String line) throws IOException {
            try {
                final LeveringPersoon leveringPersoon = SERIALIZER.deserialiseerVanuitString(line, LeveringPersoon.class);
                teProtocollerenPersonen.add(leveringPersoon);
                flush(false);
            } catch (SerialisatieExceptie se) {
                throw new IOException("Fout tijdens deserialisatie: " + line, se);
            }
        }

        private void flush(final boolean immediate) {
            if (!teProtocollerenPersonen.isEmpty() && (immediate || teProtocollerenPersonen.size() % configFlushSize == 0)) {
                protocolleringService.protocolleerPersonenBijLeveringaantekening(leveringsaantekening, Lists.newArrayList(teProtocollerenPersonen));
                teProtocollerenPersonen.clear();
            }
            if (immediate || (regelsVerwerkt == totaalAantalRegels || (regelsVerwerkt) % configVoortgangSize == 0)) {
                LOG.debug("Voortgang selectietaakId {} = {}/{}", selectietaak.getId(), regelsVerwerkt, totaalAantalRegels);
                selectieProtocolleringDataService.setVoortgang(selectietaak, regelsVerwerkt, totaalAantalRegels);
            }
        }

        @Bedrijfsregel(Regel.R1617)
        @Bedrijfsregel(Regel.R1618)
        @Bedrijfsregel(Regel.R1619)
        @Bedrijfsregel(Regel.R1620)
        @Bedrijfsregel(Regel.R2568)
        private void maakLeveringsaantekening() {
            final ProtocolleringOpdracht opdracht = new ProtocolleringOpdracht();

            //R1617,R1618
            if (selectietaak.getPeilmomentMaterieelResultaat() != null) {
                if (HistorieVorm.GEEN == HistorieVorm.parseId(selectietaak.getDienst().getHistorievormSelectie())) {
                    opdracht.setDatumAanvangMaterielePeriodeResultaat(selectietaak.getPeilmomentMaterieelResultaat());
                }
                final LocalDate localDate = DatumUtil.vanIntegerNaarLocalDate(selectietaak.getPeilmomentMaterieelResultaat());
                opdracht.setDatumEindeMaterielePeriodeResultaat(DatumUtil.vanDatumNaarInteger(localDate.plusDays(1)));
            }

            //R1619, R1620
            if (selectietaak.getPeilmomentFormeelResultaat() != null) {
                if (EnumSet.of(HistorieVorm.GEEN, HistorieVorm.MATERIEEL).contains(HistorieVorm.parseId(selectietaak.getDienst().getHistorievormSelectie()))) {
                    opdracht.setDatumTijdAanvangFormelePeriodeResultaat(
                            DatumUtil.vanTimestampNaarZonedDateTime(selectietaak.getPeilmomentFormeelResultaat()));
                }

                opdracht.setDatumTijdEindeFormelePeriodeResultaat(
                        DatumUtil.vanTimestampNaarZonedDateTime(selectietaak.getPeilmomentFormeelResultaat()));
            } else {
                opdracht.setDatumTijdEindeFormelePeriodeResultaat(
                        DatumUtil.vanIntegerNaarZonedDateTime(selectietaak.getDatumUitvoer()));
            }

            //R2568
            opdracht.setToegangLeveringsautorisatieId(selectietaak.getToegangLeveringsAutorisatie().getId());
            opdracht.setDienstId(selectietaak.getDienst().getId());
            opdracht.setDatumTijdKlaarzettenLevering(
                    DatumUtil.vanTimestampNaarZonedDateTime(selectietaak.getUitgevoerdIn().getTijdstipStart())
            );

            opdracht.setSoortDienst(SoortDienst.SELECTIE);
            this.leveringsaantekening = protocolleringService.protocolleer(opdracht);
        }
    }
}
