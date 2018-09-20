/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.afnemerindicaties;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

import nl.bzk.brp.testdatageneratie.dataaccess.HibernateSessionFactoryProvider;
import nl.bzk.brp.testdatageneratie.dataaccess.Ids;
import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 * Deze klasse maakt voor een x aantal personen random afnemerindicaties aan. Hierbij zijn er een aantal afnemers die
 * iedereen volgt, een aantal die de helft van de personen volgt en een aantal 'overige' afnemers. Deze overige afnemers
 * worden random geselecteerd.
 */
public class MaakRandomAfnemerIndicatiesTaak implements Callable<Boolean> {

    private static Logger LOGGER = Logger.getLogger(MaakRandomAfnemerIndicatiesTaak.class);

    private List<AbonnementMetPartij> afnemersDieIedereenVolgen;
    private List<AbonnementMetPartij> afnemersDieDeHelftVolgen;
    private List<AbonnementMetPartij> overigeAfnemers;
    private List<Integer>             persoonIds;
    private Ids                       actieIds;
    private TaakMonitor taakMonitor;

    private int minAfnemersPerPersoon;
    private int maxAfnemersPerPersoon;

    private Session dbSessie;

    private Random random = new Random();

    public MaakRandomAfnemerIndicatiesTaak(final TaakMonitor taakMonitor,
                                           final List<Integer> persoonIds,
                                           final List<AbonnementMetPartij> afnemersDieIedereenVolgen,
                                           final List<AbonnementMetPartij> afnemersDieDeHelftVolgen,
                                           final List<AbonnementMetPartij> overigeAfnemers,
                                           final int minAfnemersPerPersoon,
                                           final int maxAfnemersPerPersoon,
                                           final Ids actieIds)
    {
        this.persoonIds = persoonIds;
        this.afnemersDieIedereenVolgen = afnemersDieIedereenVolgen;
        this.afnemersDieDeHelftVolgen = afnemersDieDeHelftVolgen;
        this.overigeAfnemers = overigeAfnemers;
        this.minAfnemersPerPersoon = minAfnemersPerPersoon;
        this.maxAfnemersPerPersoon = maxAfnemersPerPersoon;
        this.actieIds = actieIds;
        this.taakMonitor = taakMonitor;
    }

    @Override
    public Boolean call() {
        long startTijd = System.currentTimeMillis();

        openDbSessie();
        for (Integer persoonId : persoonIds) {
            voegAfnemerIndicatiesToe(persoonId);
        }
        commitEnSluitDbSessie();

        taakMonitor.taakVoltooid((System.currentTimeMillis() - startTijd));

        return true;
    }

    private void voegAfnemerIndicatiesToe(final Integer persoonId) {
        int totaalAantalVolgersVoorDezePersoon =
                geefRandomGetalTussenMinEnMax(minAfnemersPerPersoon, maxAfnemersPerPersoon);
        int huidigAantalVolgersVoorDezePersoon = 0;

        huidigAantalVolgersVoorDezePersoon += voegStandaardAfnemersToe(persoonId);

        Collections.shuffle(overigeAfnemers);
        for (AbonnementMetPartij abonnementMetPartij : overigeAfnemers) {
            if (huidigAantalVolgersVoorDezePersoon < totaalAantalVolgersVoorDezePersoon) {
                startVolgen(abonnementMetPartij, persoonId);
                huidigAantalVolgersVoorDezePersoon++;
            }
        }
    }

    private int voegStandaardAfnemersToe(int persoonId) {
        int aantalToegevoegdeAfnemers = 0;
        for (AbonnementMetPartij abonnementMetPartij : afnemersDieIedereenVolgen) {
            startVolgen(abonnementMetPartij, persoonId);
            aantalToegevoegdeAfnemers++;
        }
        for (AbonnementMetPartij abonnementMetPartij : afnemersDieDeHelftVolgen) {
            if (geef50ProcentKansBoolean()) {
                startVolgen(abonnementMetPartij, persoonId);
                aantalToegevoegdeAfnemers++;
            }
        }
        return aantalToegevoegdeAfnemers;
    }

    private void startVolgen(final AbonnementMetPartij abonnementMetPartij, final int persoonId) {
        LOGGER.debug("Afnemer " + abonnementMetPartij.partijId + " volgt persoon " + persoonId + ".");

        //selecteer een random actie
        long actieInhoud = actieIds.selecteerId();

        String queryInsertAfnemerIndicatieMetHistorieIn1Keer = "DO $$ \n" +
                "DECLARE lastid bigint; \n" +
                "BEGIN\n" +
                "  INSERT INTO autaut.persafnemerindicatie(pers, afnemer, abonnement)\n" +
                "  VALUES (%s, %s, %s)\n" +
                "  RETURNING id INTO lastid; \n" +
                "\n" +
                "  insert into autaut.his_persafnemerindicatie \n" +
                "  (persafnemerindicatie, tsreg, actieinh)\n" +
                "  values\n" +
                "  (lastid, now(), %s); \n" +
                "\n" +
                "END $$;";

        Object[] queryParameters =
                new Object[]{persoonId, abonnementMetPartij.getPartijId(), abonnementMetPartij.getAbonnementId(),
                        actieInhoud};
        String ingevuldeQueryOmdatPreparedStatementNietWerkt =
                String.format(queryInsertAfnemerIndicatieMetHistorieIn1Keer, queryParameters);

        SQLQuery query = dbSessie.createSQLQuery(ingevuldeQueryOmdatPreparedStatementNietWerkt);

        query.executeUpdate();
    }

    private int geefRandomGetalTussenMinEnMax(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    private boolean geef50ProcentKansBoolean() {
        return geefRandomGetalTussenMinEnMax(0, 1) == 1;
    }

    private void commitEnSluitDbSessie() {
        dbSessie.getTransaction().commit();
        dbSessie.close();
    }

    private void openDbSessie() {
        dbSessie = HibernateSessionFactoryProvider.getInstance().getAutautFactory().openSession();
        dbSessie.beginTransaction();
    }

}
