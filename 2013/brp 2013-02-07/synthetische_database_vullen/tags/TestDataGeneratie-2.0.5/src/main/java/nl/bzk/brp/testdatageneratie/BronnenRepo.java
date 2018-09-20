/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import nl.bzk.brp.testdatageneratie.domain.bronnen.AangifteAdreshoudingOms;
import nl.bzk.brp.testdatageneratie.domain.bronnen.AdresHuisletter;
import nl.bzk.brp.testdatageneratie.domain.bronnen.AdresHuisnummer;
import nl.bzk.brp.testdatageneratie.domain.bronnen.AdresHuisnummertoevoeging;
import nl.bzk.brp.testdatageneratie.domain.bronnen.AdresNor;
import nl.bzk.brp.testdatageneratie.domain.bronnen.AdresPostcode;
import nl.bzk.brp.testdatageneratie.domain.bronnen.AkteRegisterGemeenteCode;
import nl.bzk.brp.testdatageneratie.domain.bronnen.BijhoudingOpschortReden;
import nl.bzk.brp.testdatageneratie.domain.bronnen.Bron;
import nl.bzk.brp.testdatageneratie.domain.bronnen.GeboorteDatum;
import nl.bzk.brp.testdatageneratie.domain.bronnen.Geboorteplaats;
import nl.bzk.brp.testdatageneratie.domain.bronnen.GemeenteDeel;
import nl.bzk.brp.testdatageneratie.domain.bronnen.Geslachtsnaam;
import nl.bzk.brp.testdatageneratie.domain.bronnen.NaamGebruik;
import nl.bzk.brp.testdatageneratie.domain.bronnen.NationaliteitCode;
import nl.bzk.brp.testdatageneratie.domain.bronnen.NationaliteitVerkrijgReden;
import nl.bzk.brp.testdatageneratie.domain.bronnen.NationaliteitVerliesReden;
import nl.bzk.brp.testdatageneratie.domain.bronnen.Onderzoek;
import nl.bzk.brp.testdatageneratie.domain.bronnen.ReisDocAutoriteit;
import nl.bzk.brp.testdatageneratie.domain.bronnen.ReisDocSoort;
import nl.bzk.brp.testdatageneratie.domain.bronnen.RelatieDuur;
import nl.bzk.brp.testdatageneratie.domain.bronnen.RelatieEindReden;
import nl.bzk.brp.testdatageneratie.domain.bronnen.RelatieStartPlaats;
import nl.bzk.brp.testdatageneratie.domain.bronnen.TitelPredikaat;
import nl.bzk.brp.testdatageneratie.domain.bronnen.VerbintenisSoort;
import nl.bzk.brp.testdatageneratie.domain.bronnen.Verblijfstitel;
import nl.bzk.brp.testdatageneratie.domain.bronnen.VertrekLandCode;
import nl.bzk.brp.testdatageneratie.domain.bronnen.Voornaam;
import nl.bzk.brp.testdatageneratie.domain.bronnen.Voorvoegsel;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

public class BronnenRepo {

    private static final Class<?>[] bronnen = {
        AangifteAdreshoudingOms.class,
        AdresHuisletter.class,
        AdresHuisnummer.class,
        AdresHuisnummertoevoeging.class,
        AdresNor.class,
        AdresPostcode.class,
        AkteRegisterGemeenteCode.class,
        BijhoudingOpschortReden.class,
        GeboorteDatum.class,
        Geboorteplaats.class,
        GemeenteDeel.class,
        Geslachtsnaam.class,
        NaamGebruik.class,
        NationaliteitCode.class,
        NationaliteitVerkrijgReden.class,
        NationaliteitVerliesReden.class,
        Onderzoek.class,
        ReisDocAutoriteit.class,
        ReisDocSoort.class,
        RelatieDuur.class,
        RelatieEindReden.class,
        RelatieStartPlaats.class,
        TitelPredikaat.class,
        VerbintenisSoort.class,
        Verblijfstitel.class,
        VertrekLandCode.class,
        Voornaam.class,
        Voorvoegsel.class,
    };


    private static final Logger log = Logger.getLogger(BronnenRepo.class);
    private static final Map<Class<?>, BronTable<?>> CACHE;
    private static final int maxResultsPerBron = SynDbGen.getProperty("maxResultsPerBron");

    static {
        Session bronnenSession = null;
        try {
            bronnenSession = HibernateSessionFactoryProvider.getInstance().getBronnenFactory().openSession();

            bronnenSession.beginTransaction();
            bronnenSession.setDefaultReadOnly(true);

            CACHE = creeerCache(bronnenSession);
        } finally {
            if (bronnenSession != null) try {
                bronnenSession.close();
            } catch (RuntimeException e) {
                log.error("", e);
            }
        }
    }

    private BronnenRepo() {}

    public static void initIfNeeded() {
        log.info(String.format("%s %s in cache", CACHE.size(), BronTable.class.getSimpleName()));
    }

    private static Map<Class<?>, BronTable<?>> creeerCache(final Session bronnenSession) {
        final Map<Class<?>, BronTable<?>> map = new HashMap<Class<?>, BronTable<?>>();

        for (int i = 0; i < bronnen.length; i++) {

            @SuppressWarnings("unchecked")
            Class<? extends Bron> klasse = (Class<? extends Bron>) bronnen[i];

            map.put(klasse, creeerBron(klasse, bronnenSession));
        }

        return map;
    }

    private static <T extends Bron> BronTable<T> creeerBron(final Class<T> klasse, final Session bronnenSession) {
        Criteria criteria = bronnenSession.createCriteria(klasse);
        criteria.addOrder(Order.asc("totEnMet"));
        if (maxResultsPerBron != -1) {
            criteria.setMaxResults(maxResultsPerBron);
        }

        @SuppressWarnings("unchecked")
        List<T> list = criteria.list();

        BronTable<T> bronTable = new BronTable<T>(klasse, list);
        log.info("bron: " + bronTable);

        return bronTable;
    }

    public static <T extends Bron> T getBron(final Class<T> clazz) {
        return getBron(clazz, RandomService.random);
    }

    public static <T extends Bron> T getBron(final Class<T> clazz, final Random random) {

        @SuppressWarnings("unchecked")
        BronTable<? extends T> table = (BronTable<T>) CACHE.get(clazz);

        if (table == null) {
            log.error("No value found in BronnenRepo MAP for: " + clazz);
        }
        return table.getBron(random);
    }

}
