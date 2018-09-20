/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.dataaccess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import nl.bzk.brp.testdatageneratie.SynDbGen;
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
import nl.bzk.brp.testdatageneratie.utils.RandomUtil;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

/**
 * Bronnen repo.
 */
public final class BronnenRepo {

    /**
     * Bronnen array.
     */
    private static final Class<?>[] BRONNEN = {
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


    private static final Logger LOG = Logger.getLogger(BronnenRepo.class);
    private static final Map<Class<?>, BronTable<?>> CACHE;
    private static final int MAX_RESULTS_PER_BRON = SynDbGen.getProperty("maxResultsPerBron");

    static {
        Session bronnenSession = null;
        try {
            bronnenSession = HibernateSessionFactoryProvider.getInstance().getBronnenFactory().openSession();

            bronnenSession.beginTransaction();
            bronnenSession.setDefaultReadOnly(true);

            CACHE = creeerCache(bronnenSession);
        } finally {
            if (bronnenSession != null) {
                try {
                    bronnenSession.close();
                } catch (RuntimeException e) {
                    LOG.error("", e);
                }
            }
        }
    }

    /**
     * Instantieert Bronnen repo.
     */
    private BronnenRepo() {
    }

    /**
     * Initialiseer wanneer benodigd.
     */
    public static void initIfNeeded() {
        LOG.info(String.format("%s %s in cache", CACHE.size(), BronTable.class.getSimpleName()));
    }

    /**
     * Creeert cache.
     *
     * @param bronnenSession bronnen sessie
     * @return map
     */
    private static Map<Class<?>, BronTable<?>> creeerCache(final Session bronnenSession) {
        final Map<Class<?>, BronTable<?>> map = new HashMap<Class<?>, BronTable<?>>();

        for (Class<?> bron : BRONNEN) {
            Class<? extends Bron> klasse = (Class<? extends Bron>) bron;
            map.put(klasse, creeerBron(klasse, bronnenSession));
        }

        return map;
    }

    /**
     * Creeert bron.
     *
     * @param <T> type
     * @param klasse klasse
     * @param bronnenSession bronnen sessie
     * @return bron table
     */
    private static <T extends Bron> BronTable<T> creeerBron(final Class<T> klasse, final Session bronnenSession) {
        final Criteria criteria = bronnenSession.createCriteria(klasse);
        criteria.addOrder(Order.asc("totEnMet"));
        if (MAX_RESULTS_PER_BRON != -1) {
            criteria.setMaxResults(MAX_RESULTS_PER_BRON);
        }

        @SuppressWarnings("unchecked")
        final List<T> list = criteria.list();

        final BronTable<T> bronTable = new BronTable<T>(klasse, list);
        LOG.info("bron: " + bronTable);

        return bronTable;
    }

    /**
     * Geeft bron.
     *
     * @param <T> type
     * @param clazz clazz
     * @return bron
     */
    public static <T extends Bron> T getBron(final Class<T> clazz) {
        return getBron(clazz, RandomUtil.random);
    }

    /**
     * Geeft bron.
     *
     * @param <T> type
     * @param clazz clazz
     * @param random random
     * @return bron
     */
    public static <T extends Bron> T getBron(final Class<T> clazz, final Random random) {

        @SuppressWarnings("unchecked")
        final BronTable<? extends T> table = (BronTable<T>) CACHE.get(clazz);

        if (table == null) {
            LOG.error("No value found in BronnenRepo MAP for: " + clazz);
        }
        return table.getBron(random);
    }

}
