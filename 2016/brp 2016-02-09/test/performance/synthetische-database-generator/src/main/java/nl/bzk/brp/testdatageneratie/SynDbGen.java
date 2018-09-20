/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import nl.bzk.brp.testdatageneratie.csv.dto.ArtHuishoudingDto;
import nl.bzk.brp.testdatageneratie.csv.dto.ArtPersDto;
import nl.bzk.brp.testdatageneratie.csv.model.PersCsvLoader;
import nl.bzk.brp.testdatageneratie.dataaccess.BronnenRepo;
import nl.bzk.brp.testdatageneratie.dataaccess.HibernateSessionFactoryProvider;
import nl.bzk.brp.testdatageneratie.dataaccess.Ids;
import nl.bzk.brp.testdatageneratie.dataaccess.MetaRepo;
import nl.bzk.brp.testdatageneratie.dataaccess.PersonenIds;
import nl.bzk.brp.testdatageneratie.datagenerators.ActieGenerator;
import nl.bzk.brp.testdatageneratie.datagenerators.DocGenerator;
import nl.bzk.brp.testdatageneratie.datagenerators.FamilieGenerator;
import nl.bzk.brp.testdatageneratie.datagenerators.GeneratorAdminData;
import nl.bzk.brp.testdatageneratie.datagenerators.MultirealiteitGenerator;
import nl.bzk.brp.testdatageneratie.datagenerators.OnderzoekGenerator;
import nl.bzk.brp.testdatageneratie.datagenerators.PersoonGenerator;
import nl.bzk.brp.testdatageneratie.datagenerators.RegelverantwoordingGenerator;
import nl.bzk.brp.testdatageneratie.datagenerators.RelatieGenerator;
import nl.bzk.brp.testdatageneratie.datagenerators.RelatieHistorieGenerator;
import nl.bzk.brp.testdatageneratie.domain.kern.Actie;
import nl.bzk.brp.testdatageneratie.domain.kern.Betr;
import nl.bzk.brp.testdatageneratie.domain.kern.Pers;
import nl.bzk.brp.testdatageneratie.domain.kern.Relatie;
import nl.bzk.brp.testdatageneratie.helper.Huwelijksboot;
import nl.bzk.brp.testdatageneratie.utils.PerformanceLogger;
import nl.bzk.brp.testdatageneratie.utils.SequenceUtil;

import org.apache.log4j.Logger;
import org.hibernate.Session;


/**
 * Synthetische database generator klasse die opgestart dient te worden voor de generatie van de database.
 */
public final class SynDbGen extends AbstractMain {

    private static final int HISRELATIES_PER_PERSOON = 10;
    private static final int MULTIREALITEIT_PER_PERSOON = 54;
    private static final int ONDERZOEKEN_PER_PERSOON = 1000;
    private static final int REGELVERANTWOORDINGEN_PER_PERSOON = 22;
    private static final long DUIZEND = 1000;
    private static Logger                          log             = Logger.getLogger(SynDbGen.class);

    private static final int ACTIES_PER_PERSOON = 10;

    private static final String TRUNCATE_DB =
            "truncate helper.brppers cascade;\n"
                    + "truncate helper.brprelatie cascade;\n"
                    + "truncate kern.his_persbijhouding cascade;\n"
                    + "truncate kern.his_persgeboorte cascade;\n"
                    + "truncate kern.his_persgeslachtsaand cascade;\n"
                    + "truncate kern.his_persids cascade;\n"
                    + "truncate kern.his_persmigratie cascade;\n"
                    + "truncate kern.his_persinschr cascade;\n"
                    + "truncate kern.his_persoverlijden cascade;\n"
                    + "truncate kern.his_perspk cascade;\n"
                    + "truncate kern.his_perssamengesteldenaam cascade;\n"
                    + "truncate kern.his_persuitslkiesr cascade;\n"
                    + "truncate kern.his_persverblijfsr cascade;\n"
                    + "truncate kern.his_persadres cascade;\n"
                    + "truncate kern.persadres cascade;\n"
                    + "truncate kern.his_persgeslnaamcomp cascade;\n"
                    + "truncate kern.persgeslnaamcomp cascade;\n"
                    + "truncate kern.his_persindicatie cascade;\n"
                    + "truncate kern.persindicatie cascade;\n"
                    + "truncate kern.his_persnation cascade;\n"
                    + "truncate kern.persnation cascade;\n"
                    + "truncate kern.his_persreisdoc cascade;\n"
                    + "truncate kern.persreisdoc cascade;\n"
                    + "truncate kern.his_persverificatie cascade;\n"
                    + "truncate kern.persverificatie cascade;\n"
                    + "truncate kern.his_persvoornaam cascade;\n"
                    + "truncate kern.persvoornaam cascade;\n"
                    + "truncate kern.his_multirealiteitregel cascade;\n"
                    + "truncate kern.multirealiteitregel cascade;\n"
                    + "truncate kern.personderzoek cascade;\n"
                    + "truncate kern.his_erkenningongeborenvrucht cascade;\n"
                    + "truncate kern.his_huwelijkgeregistreerdpar cascade;\n"
                    + "truncate kern.his_naamskeuzeongeborenvruch cascade;\n"
                    + "truncate kern.his_ouderouderlijkgezag cascade;\n"
                    + "truncate kern.his_ouderouderschap cascade;\n"
                    + "truncate kern.his_doc cascade;\n"
                    + "truncate kern.doc cascade;\n"
                    + "truncate kern.actiebron cascade;\n"
                    + "truncate kern.betr cascade;\n"
                    + "truncate kern.admhnd cascade;\n"
                    + "truncate kern.actie cascade;\n"
                    + "truncate kern.relatie cascade;\n"
                    + "truncate kern.his_persnaamgebruik cascade;\n"
                    + "truncate kern.pers cascade;\n";


    private static ExecutorService                 executor;

    private static List<Callable<GeneratorAdminData>>         taskList;

    /**
     * The constant ACTIE_IDS.
     */
    public static final Ids ACTIE_IDS = new Ids(Actie.class);

    /**
     * Private constructor.
     */
    private SynDbGen() {
        super();
    }

    /**
     * Hoofdmethode om generatie te starten.
     *
     * @param args argumenten (worden niet gebruikt)
     */
    public static void main(final String[] args) {
        try {
            doit();
        } catch (Exception e) {
            log.error("", e);
        } finally {
            PerformanceLogger.quitLogger();

            try {
                if (executor != null) {
                    executor.shutdown();
                }
            } catch (RuntimeException e) {
                log.error("", e);
            } finally {
                try {
                    sluitHibernate();
                } catch (RuntimeException e) {
                    log.error("", e);
                }
            }
        }
    }

    /**
     * Lees csv template personen in.
     *
     * @param artHuishoudingDto the art huishouding dto
     * @throws IOException the iO exception
     */
    private static void leesCsvTemplatePersonenIn(final ArtHuishoudingDto artHuishoudingDto) throws IOException {
        String path = "csv";
        PersCsvLoader test = new PersCsvLoader(path);
        artHuishoudingDto.setArtPersonenTemplateDto(test.leesPersonen());
        artHuishoudingDto.setArtRelatiesTemplateDto(test.leesRelatie());
    }

    /**
     * Doet het daadwerkelijke vullen van de database.
     *
     * @throws Exception exceptie
     */
    private static void doit() throws Exception {
        final int personenPerThread = getProperty("personenPerThread");
        final int batchBlockSize = getProperty("batchBlockSize");
        // rangeSize: 25000
        final int rangeSize = getProperty("rangeSize");
        final int numberOfThreads = getProperty("numberOfThreads");
        final int aantalAfgebrokenThreads = getProperty("aantalAfgebrokenThreads");
        log.info("personenPerThread=" + personenPerThread + ", batchBlockSize=" + batchBlockSize
                         + ", numberOfThreads=" + numberOfThreads);

        final int maxSet = getProperty("maxSet");
        if (maxSet == 0) {
            log.error("maxSet moet > 0 zijn");
            return;
        }

        long startProcess = System.currentTimeMillis();

        final Huwelijksboot[] huwelijksboten = new Huwelijksboot[numberOfThreads];
        final ArtHuishoudingDto[] brpHuishouding = new ArtHuishoudingDto[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            brpHuishouding[i] = new ArtHuishoudingDto();
            leesCsvTemplatePersonenIn(brpHuishouding[i]);
        }

        startHibernate();

        executor = Executors.newFixedThreadPool(numberOfThreads + 2);
        taskList = new ArrayList<>();

        truncateTables();

        BronnenRepo.initIfNeeded();
        MetaRepo.initIfNeeded();

        int aantalPersonen = personenPerThread * numberOfThreads;
        SequenceUtil.init();

        int actiesTotaal = 0;
        int actiesPerThread = personenPerThread * ACTIES_PER_PERSOON;
        for (int i = 0; i < numberOfThreads; i++) {
            taskList.add(new ActieGenerator(actiesPerThread, batchBlockSize, i));
            actiesTotaal += actiesPerThread;
        }
        genereer(actiesTotaal, "acties");
        ACTIE_IDS.init(batchBlockSize);

        // Vanaf startpunt bsn (100000000) tot eindpunt (999999999) zijn er 899999999 potentiele bsns,
        // dit bereik dient verdeeld te worden over de threads met gelijke blokken om overlap te voorkomen.
        final int threadBsnBlockSize = 899999999 / numberOfThreads;

        int cumulatiefAantalThreads = aantalAfgebrokenThreads + numberOfThreads;
        for (int i = aantalAfgebrokenThreads, j = 0; i < cumulatiefAantalThreads; i++, j++) {
            huwelijksboten[j] = new Huwelijksboot(personenPerThread);
            taskList.add(new PersoonGenerator(personenPerThread, batchBlockSize, j, numberOfThreads,
                    rangeSize, huwelijksboten[j], brpHuishouding[j], maxSet, threadBsnBlockSize));
        }
        genereer(aantalPersonen, "personen");

        List<Integer> listBrpPersonen = bouwARTpersonenIds(brpHuishouding);
        PersonenIds persoonIds = new PersonenIds(Pers.class, listBrpPersonen);
        persoonIds.init(batchBlockSize);

        int hisRelatiesTotaal = 0;
        for (int i = 0; i < numberOfThreads; i++) {
            int hisRelatiesPerThread = personenPerThread / HISRELATIES_PER_PERSOON;
            taskList.add(new RelatieHistorieGenerator(i, persoonIds, hisRelatiesPerThread, batchBlockSize));
            hisRelatiesTotaal += hisRelatiesPerThread;
        }
        genereer(hisRelatiesTotaal, "hisrelaties");

        for (int i = 0; i < numberOfThreads; i++) {
            taskList.add(new FamilieGenerator(persoonIds, i, numberOfThreads, brpHuishouding[i]));
        }
        genereer(aantalPersonen, "families");

        int huwelijkenTotaal = 0;
        for (int i = 0; i < numberOfThreads; i++) {
            taskList.add(new RelatieGenerator(persoonIds, huwelijksboten[i], i, numberOfThreads, brpHuishouding[i]));
            huwelijkenTotaal += huwelijksboten[i].getAantalHuwelijken();
        }
        genereer(huwelijkenTotaal, "huwelijken");

        Ids relatieIds = new Ids(Relatie.class);
        relatieIds.init(batchBlockSize);

        Ids betrokkenheidIds = new Ids(Betr.class);
        betrokkenheidIds.init(batchBlockSize);

        // TODO multirealiteit voor art-testdata implementeren.
        int multirealiteitTotaal = 0;
        for (int i = 0; i < numberOfThreads; i++) {
            int multirealiteitPerThread = personenPerThread / MULTIREALITEIT_PER_PERSOON;
            taskList.add(new MultirealiteitGenerator(multirealiteitPerThread, batchBlockSize, i,
                    persoonIds, relatieIds, betrokkenheidIds));
            multirealiteitTotaal += multirealiteitPerThread;
        }
        genereer(multirealiteitTotaal, "multirealiteiten");

        // TODO persoon reisdocumenten voor art-testdata implementeren.
        int docsTotaal = 0;
        for (int i = 0; i < numberOfThreads; i++) {
            taskList.add(new DocGenerator(actiesPerThread, batchBlockSize, i, ACTIE_IDS));
            docsTotaal += actiesPerThread;
        }
        genereer(docsTotaal, "documenten");

        // TODO persoon in onderzoek voor art-testdata implementeren.
        int onderzoekenTotaal = 0;
        for (int i = 0; i < numberOfThreads; i++) {
            int onderzoekenPerThread = personenPerThread / ONDERZOEKEN_PER_PERSOON;
            taskList.add(new OnderzoekGenerator(onderzoekenPerThread, batchBlockSize, i, persoonIds));
            onderzoekenTotaal += onderzoekenPerThread;
        }
        genereer(onderzoekenTotaal, "onderzoeken");

        int regelverantwoordingenTotaal = 0;
        for (int i = 0; i < numberOfThreads; i++) {
            // 22, want BRM schrijft 1.250.000 regelverantwoordingen voor bij 27.000.000 personen (initieel + 5*inserts)
            int regelverantwoordingenPerThread = personenPerThread / REGELVERANTWOORDINGEN_PER_PERSOON;
            taskList.add(new RegelverantwoordingGenerator(regelverantwoordingenPerThread,
                                                          batchBlockSize, i, ACTIE_IDS));
            regelverantwoordingenTotaal += regelverantwoordingenPerThread;
        }
        genereer(regelverantwoordingenTotaal, "regelverantwoordingen");

        // Weghalen en plaatsen in finally?
        PerformanceLogger.quitLogger();
        long stopProcess = System.currentTimeMillis();
        log.info("---------------------- Einde --------------------------");
        log.info("Start : " + new Date(startProcess)
                         + " Einde " + new Date(stopProcess)
                         + " heeft geduurd " + (stopProcess - startProcess) / DUIZEND + " sec.");
    }

    /**
     * Truncate tables.
     */
    private static void truncateTables() {
        Session kernSession = HibernateSessionFactoryProvider.getInstance().getKernFactory().openSession();
        kernSession.beginTransaction();
        kernSession.createSQLQuery(TRUNCATE_DB).executeUpdate();
        kernSession.getTransaction().commit();
        kernSession.close();
        log.info("Tables truncated");
    }

    /**
     * Bouwt ART personen ids.
     *
     * @param brpHuishouding brp huishouding
     * @return the list
     */
    private static List<Integer> bouwARTpersonenIds(final ArtHuishoudingDto[] brpHuishouding) {
        List<Integer> listPersId = new ArrayList<>();
        for (int i = 0; i < brpHuishouding.length; i++) {
            Map<Integer, List<ArtPersDto>> mp = brpHuishouding[i].getArtPersonenDto();
            for (Integer sesNr : mp.keySet()) {
                List<ArtPersDto> personen = mp.get(sesNr);
                for (ArtPersDto p : personen) {
                    listPersId.add(p.getPersId());
                }
            }
        }
        return listPersId;
    }

    /**
     * Genereert de data.
     *
     * @param aantalTeGenereren aantal te genereren
     * @param type type data
     * @throws InterruptedException onderbroken exceptie
     */
    private static void genereer(final int aantalTeGenereren, final String type) throws InterruptedException {
        long start = System.currentTimeMillis();

        log.info("generereer: " + type);
        List<Future<GeneratorAdminData>> futures = executor.invokeAll(taskList);
        long ms = System.currentTimeMillis() - start;
        if (ms == 0) {
            ms = 1;
        }
        log.info(aantalTeGenereren + " " + type + " gegenereerd in " + ms
                + " ms speed " + aantalTeGenereren * DUIZEND / ms  + "/s");
        for (int i = 0; i < futures.size(); i++) {
            try {
                log.debug(i + ": gemiddelde " + futures.get(i).get());
            } catch (Throwable e) {
                log.error(i + ":", e);
                while (e.getCause() != null) {
                    e = e.getCause();
                }
                if (e instanceof SQLException) {
                    SQLException ex = (SQLException) e;
                    while (ex.getNextException() != null) {
                        ex = ex.getNextException();
                        log.error(i + ": Next Exception", ex);
                    }
                } else {
                    log.error(e);
                }
            }
        }

        taskList.clear();
    }
}
