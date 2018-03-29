/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.schrijver;

import com.google.common.collect.Lists;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.SelectieKenmerken;
import nl.bzk.brp.domain.berichtmodel.SelectieResultaatBericht;
import nl.bzk.brp.domain.internbericht.selectie.MaakSelectieResultaatTaak;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.service.algemeen.autorisatie.LeveringsautorisatieService;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import nl.bzk.brp.service.selectie.algemeen.Configuratie;

/**
 * Maakt alle XML berichten voor een gegeven selectieautorisatie. Dit kan er effectief maar 1 zijn.
 */
final class PersoonFragmentConsumer implements Callable<Integer> {

    static final String POISON = "STOP";

    private static final int BYTES_IN_MB = 1024;
    private static final int OVERHEAD_BYTES = 1024;
    //een soort van dvd grootte
    private static final int DEFAULT_MAX_GROOTTE = BYTES_IN_MB * BYTES_IN_MB * 4690;

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private PartijService partijService;
    private LeveringsautorisatieService leveringsautorisatieService;
    private SelectieResultaatWriterFactory selectieResultaatWriterFactory;
    private MaakSelectieResultaatTaak maakSelectieResultaatTaak;

    private Dienst dienst;
    private ToegangLeveringsAutorisatie toegangLeveringsAutorisatie;
    private int berichtMaxPersonen;
    private int berichtMaxBytes;


    private final List<PersoonResultaatSet> setList = Lists.newArrayList();
    private PersoonResultaatSet currentSet;

    //werk queue
    private BlockingQueue<String> queue;
    private BasisBerichtGegevens basisBerichtGegevens;
    private AtomicInteger counter;


    @Override
    public Integer call() throws Exception {
        BrpNu.set();
        LOGGER.info("Start verwerker persoon fragmenten");
        try {
            //haal autorisatie informatie uit cache voor het maken van resultaat bericht
            prepare();
            poll();
            LOGGER.info("Einde verwerker persoon fragmenten");
            return setList.stream().mapToInt(persoonResultaatSet -> persoonResultaatSet.personenInBericht).sum();
        } finally {
            complete();
        }
    }

    private void prepare() {
        this.toegangLeveringsAutorisatie =
                leveringsautorisatieService.geefToegangLeveringsAutorisatie(maakSelectieResultaatTaak.getToegangLeveringsAutorisatieId());
        final Partij ontvangendePartij = toegangLeveringsAutorisatie.getGeautoriseerde().getPartij();
        this.dienst = AutAutUtil.zoekDienst(toegangLeveringsAutorisatie.getLeveringsautorisatie(), maakSelectieResultaatTaak.getDienstId());
        if (this.dienst == null) {
            throw new NullPointerException("dienst mag niet null zijn voor taak");
        }
        berichtMaxPersonen = dienst.getMaxAantalPersonenPerSelectiebestand() == null
                ? Integer.MAX_VALUE : dienst.getMaxAantalPersonenPerSelectiebestand();
        berichtMaxBytes = dienst.getMaxGrootteSelectiebestand() == null
                ? DEFAULT_MAX_GROOTTE : (dienst.getMaxGrootteSelectiebestand() * BYTES_IN_MB * BYTES_IN_MB) - OVERHEAD_BYTES;

        basisBerichtGegevens = BasisBerichtGegevens.builder()
                .metStuurgegevens()
                .metReferentienummer(UUID.randomUUID().toString())
                .metTijdstipVerzending(DatumUtil.nuAlsZonedDateTime())
                .metZendendePartij(partijService.geefBrpPartij())
                .metZendendeSysteem(BasisBerichtGegevens.BRP_SYSTEEM)
                .metOntvangendePartij(ontvangendePartij)
                .eindeStuurgegevens().build();


    }

    private void poll() throws InterruptedException, SelectieResultaatVerwerkException {
        boolean klaar = false;
        while (!klaar) {
            final String fragment = queue.poll(Configuratie.QUEUE_POLLING_TIMEOUT_MS, TimeUnit.MILLISECONDS);
            if (fragment != null) {
                if (POISON.equals(fragment)) {
                    klaar = true;
                } else {
                    verwerkFragment(fragment);
                }
            }
        }
    }

    /**
     * @param queue queue
     */
    public void setQueue(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    /**
     * @param maakSelectieResultaatTaak maakSelectieResultaatTaak
     */
    public void setMaakSelectieResultaatTaak(MaakSelectieResultaatTaak maakSelectieResultaatTaak) {
        this.maakSelectieResultaatTaak = maakSelectieResultaatTaak;
    }

    /**
     * @param selectieResultaatWriterFactory selectieResultaatWriterFactory
     */
    public void setSelectieResultaatWriterFactory(final SelectieResultaatWriterFactory selectieResultaatWriterFactory) {
        this.selectieResultaatWriterFactory = selectieResultaatWriterFactory;
    }

    /**
     * @param partijService partijService
     */
    public void setPartijService(PartijService partijService) {
        this.partijService = partijService;
    }

    /**
     * @param counter counter
     */
    public void setCounter(AtomicInteger counter) {
        this.counter = counter;
    }

    /**
     * @param leveringsautorisatieService leveringsautorisatieService
     */
    public void setLeveringsautorisatieService(LeveringsautorisatieService leveringsautorisatieService) {
        this.leveringsautorisatieService = leveringsautorisatieService;
    }

    private void verwerkFragment(String fragment) throws SelectieResultaatVerwerkException {
        if (currentSet == null) {
            currentSet = new PersoonResultaatSet();
            setList.add(currentSet);
        }
        if (!append(fragment)) {
            currentSet.eindeBericht();
            currentSet = null;
            verwerkFragment(fragment);
        }
    }

    private boolean append(String encodedFragment) throws SelectieResultaatVerwerkException {
        //voor de berekening van bytes nemen we de header en footer van bericht niet mee
        //controleer of huidige inhoud + toevoeging groter is dan max toegestaan
        final byte[] decodedXmlBytes = Base64.getDecoder().decode(encodedFragment.getBytes(StandardCharsets.UTF_8));
        if (decodedXmlBytes.length > berichtMaxBytes) {
            throw new IllegalArgumentException("");
        }
        boolean pastInBestand = (currentSet.personenInBericht + 1) <= berichtMaxPersonen
                && (currentSet.bytesInBericht + decodedXmlBytes.length) <= berichtMaxBytes;
        if (pastInBestand) {
            currentSet.appendPersoon(decodedXmlBytes);
        }
        return pastInBestand;
    }

    private void complete() throws SelectieResultaatVerwerkException {
        if (currentSet != null) {
            currentSet.eindeBericht();
        }
        currentSet = null;
    }


    /**
     * Boekhouding value object voor selectie resultaat bestand.
     * Van belang voor het maken van nieuwe persoon files.
     */
    private final class PersoonResultaatSet {

        private int personenInBericht;
        private long bytesInBericht;
        private final SelectieResultaatWriterFactory.PersoonBestandWriter persoonBestandWriter;
        private final int berichtId;

        PersoonResultaatSet() throws SelectieResultaatVerwerkException {
            berichtId = counter.incrementAndGet();
            final SelectieResultaatSchrijfInfo info = new SelectieResultaatSchrijfInfo();
            info.setBerichtId(berichtId);
            info.setSelectieRunId(maakSelectieResultaatTaak.getSelectieRunId());
            info.setDienstId(maakSelectieResultaatTaak.getDienstId());
            info.setToegangLeveringsAutorisatieId(maakSelectieResultaatTaak.getToegangLeveringsAutorisatieId());
            final SelectieKenmerken selectieKenmerken = maakSelectieKenmerken(berichtId)
                    .metSoortSelectieresultaatSet("Resultaatset personen")
                    .build();
            final SelectieResultaatBericht bericht = new SelectieResultaatBericht(basisBerichtGegevens, selectieKenmerken);
            if (toegangLeveringsAutorisatie.getLeveringsautorisatie().getStelsel() == Stelsel.BRP) {
                persoonBestandWriter = selectieResultaatWriterFactory.persoonWriterBrp(info, bericht);
            } else {
                persoonBestandWriter = selectieResultaatWriterFactory.persoonWriterGba(info, bericht);
            }
        }

        private void eindeBericht() throws SelectieResultaatVerwerkException {
            persoonBestandWriter.eindeBericht();
        }

        private void appendPersoon(byte[] decodedFragmentBytes) throws SelectieResultaatVerwerkException {
            final String decodedFragment = new String(decodedFragmentBytes, StandardCharsets.UTF_8);
            persoonBestandWriter.voegPersoonToe(decodedFragment);
            bytesInBericht += decodedFragmentBytes.length;
            personenInBericht++;
        }

        private SelectieKenmerken.Builder maakSelectieKenmerken(final int volgnummer) {
            return SelectieKenmerken.builder()
                    .metDienst(dienst)
                    .metLeveringsautorisatie(toegangLeveringsAutorisatie.getLeveringsautorisatie())
                    .metSelectietaakId(maakSelectieResultaatTaak.getSelectietaakId())
                    .metDatumUitvoer(maakSelectieResultaatTaak.getDatumUitvoer())
                    .metPeilmomentMaterieelResultaat(maakSelectieResultaatTaak.getPeilmomentMaterieelResultaat())
                    .metPeilmomentFormeelResultaat(maakSelectieResultaatTaak.getPeilmomentFormeelResultaat())
                    .metSoortSelectie(maakSelectieResultaatTaak.getSoortSelectie())
                    .metSoortSelectieresultaatVolgnummer(volgnummer);
        }
    }
}
