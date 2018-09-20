/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.afnemerindicaties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import nl.bzk.brp.testdatageneratie.dataaccess.HibernateSessionFactoryProvider;
import nl.bzk.brp.testdatageneratie.dataaccess.Ids;
import nl.bzk.brp.testdatageneratie.domain.kern.Actie;
import org.apache.log4j.Logger;
import org.hibernate.Session;

public class AfnemerIndicatiesGenerator {

    private static final Logger LOGGER = Logger.getLogger(AfnemerIndicatiesGenerator.class);

    private List<Integer>       allePersoonIds;
    private List<List<Integer>> allePersoonIdsVerdeeldInKleineLijsten;

    private List<AbonnementMetPartij> afnemersDieIedereenVolgen = new ArrayList<>();
    private List<AbonnementMetPartij> afnemersDieDeHelftVolgen  = new ArrayList<>();
    private List<AbonnementMetPartij> overigeAfnemers           = new ArrayList<>();

    private Ids actieIds = new Ids(Actie.class);

    private int batchGroote;
    private int aantalThreads;

    private int           minAfnemersPerPersoon       = 18;
    private int           maxAfnemersPerPersoon       = 22;
    private List<Integer> afnemerIdsDieIedereenVolgen = Arrays.asList(new Integer[]{1430, 831});
    private List<Integer> afnemerIdsDieDeHelftVolgen  = Arrays.asList(new Integer[]{690, 974});

    private ExecutorService executor;

    public AfnemerIndicatiesGenerator(final int batchGroote, final int aantalThreads) {
        this.batchGroote = batchGroote;
        this.aantalThreads = aantalThreads;
    }

    public void start() {
        long startTijd = System.currentTimeMillis();

        LOGGER.info("Gaat gegevens uit database halen.");
        haalGegevensOpUitDatabase();
        LOGGER.info("Klaar met ophalen gegevens uit database.");

        LOGGER.info("Start shuffle personen.");
        //Zorgt voor een random verspreiding van de records
        Collections.shuffle(allePersoonIds);
        LOGGER.info("Klaar met shuffle personen.");

        LOGGER.info("Verdeel persoon ids in batches.");
        verdeelPersoonIdsInLijstenVanBatchSize();
        LOGGER.info("Klaar met verdelen in batches.");

        executor = Executors.newFixedThreadPool(aantalThreads);

        List<Callable<Boolean>> takenLijst = new ArrayList<>();
        TaakMonitor taakMonitor = new TaakMonitor(MaakRandomAfnemerIndicatiesTaak.class,
                allePersoonIdsVerdeeldInKleineLijsten.size());
        for (List<Integer> persoonIdsInBatch : allePersoonIdsVerdeeldInKleineLijsten) {
            MaakRandomAfnemerIndicatiesTaak maakRandomAfnemerIndicatiesTaak =
                    new MaakRandomAfnemerIndicatiesTaak(taakMonitor, persoonIdsInBatch, afnemersDieIedereenVolgen,
                            afnemersDieDeHelftVolgen, overigeAfnemers, minAfnemersPerPersoon, maxAfnemersPerPersoon,
                            actieIds);

            takenLijst.add(maakRandomAfnemerIndicatiesTaak);
        }

        LOGGER.info("Start verwerking batches.");
        try {
            executor.invokeAll(takenLijst);
            executor.shutdown();
        } catch (InterruptedException e) {
            LOGGER.error("Fout bij genereren afnemerinidicaties", e);
        }
        long runTijdInSeconden = (System.currentTimeMillis() - startTijd) / 1000;
        LOGGER.info("Klaar met verwerking batches. Totale tijd (s): " + runTijdInSeconden);
    }

    private void verdeelPersoonIdsInLijstenVanBatchSize() {
        int aantalBatches = delenDoorEnAfrondenNaarBoven(allePersoonIds.size(), batchGroote);

        allePersoonIdsVerdeeldInKleineLijsten = new ArrayList<>();
        for (int i = 0; i < aantalBatches; i++) {
            int batchStartElement = i * batchGroote;
            int batchEindElement = batchStartElement + batchGroote - 1;
            if (batchEindElement > allePersoonIds.size()) {
                batchEindElement = allePersoonIds.size() - 1;
            }

            ArrayList<Integer> persoonIdsInBatch =
                    new ArrayList<>(allePersoonIds.subList(batchStartElement, batchEindElement));
            allePersoonIdsVerdeeldInKleineLijsten.add(persoonIdsInBatch);
        }
    }

    private void haalGegevensOpUitDatabase() {
        Session kernSession = openDbSessie();
        haalAlleAbonnementenOp(kernSession);
        haalAllePersonenOp(kernSession);
        actieIds.init(batchGroote);
        commitEnSluitDbSessie(kernSession);
    }

    private void haalAllePersonenOp(final Session kernSession) {
        //Alle persoon-ids ophalen
        String queryAlleNietOverledenIngeschrevenPersonen = "SELECT kp.id FROM kern.pers kp WHERE kp.bijhaard = 1\n" +
                "AND kp.bsn IS NOT NULL \n" +
                "AND kp.srt=1\n" +
                "AND kp.naderebijhaard = 1;";
        allePersoonIds = kernSession.createSQLQuery(queryAlleNietOverledenIngeschrevenPersonen).list();
    }

    private void haalAlleAbonnementenOp(final Session kernSession) {
        //Alle abonnement-ids ophalen met dienst afnemerindicatie
        String queryAbonnementIdEnAfnemerIdOphalen = "select abonnement.id, ta.partij from autaut.abonnement\n" +
                "join autaut.dienst on (abonnement.id = dienst.abonnement)\n" +
                "join autaut.toegangabonnement ta on (ta.abonnement = abonnement.id)\n" +
                "where dienst.catalogusoptie = 2;";
        List<Object> abonnementIdEnAfnemerIdLijst =
                kernSession.createSQLQuery(queryAbonnementIdEnAfnemerIdOphalen).list();

        for (Object abonnementIdEnAfnemerIdObject : abonnementIdEnAfnemerIdLijst) {
            Object[] abonnementIdEnAfnemerId = (Object[]) abonnementIdEnAfnemerIdObject;
            AbonnementMetPartij abonnementMetPartij =
                    new AbonnementMetPartij(
                            (Integer) abonnementIdEnAfnemerId[0], Integer.valueOf((Short) abonnementIdEnAfnemerId[1]));

            if (afnemerIdsDieIedereenVolgen.contains(abonnementMetPartij.getPartijId())) {
                afnemersDieIedereenVolgen.add(abonnementMetPartij);
            } else if (afnemerIdsDieDeHelftVolgen.contains(abonnementMetPartij.getPartijId())) {
                afnemersDieDeHelftVolgen.add(abonnementMetPartij);
            } else {
                overigeAfnemers.add(abonnementMetPartij);
            }

        }
    }

    private void commitEnSluitDbSessie(final Session kernSession) {
        kernSession.getTransaction().commit();
        kernSession.close();
    }

    private Session openDbSessie() {
        Session kernSession = HibernateSessionFactoryProvider.getInstance().getKernFactory().openSession();
        kernSession.beginTransaction();
        return kernSession;
    }

    private int delenDoorEnAfrondenNaarBoven(Integer numInt, Integer divisorInt) {
        long num = numInt.longValue();
        long divisor = divisorInt.longValue();

        Long resultaat = (num + divisor - 1) / divisor;

        return resultaat.intValue();
    }

}
