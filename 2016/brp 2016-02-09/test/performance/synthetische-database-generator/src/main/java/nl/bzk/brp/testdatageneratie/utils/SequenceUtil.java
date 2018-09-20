/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.bzk.brp.testdatageneratie.dataaccess.HibernateSessionFactoryProvider;
import org.apache.log4j.Logger;
import org.hibernate.Session;

/**
 * Sequence utility klasse.
 */
public final class SequenceUtil {
    private static Logger log = Logger.getLogger(SequenceUtil.class);
    private static boolean isInit = false;

    private static Map<String, Long> maxMap = new HashMap<>();

    static {
        init();
    }

    /**
     * Private constructor.
     */
    private SequenceUtil() {

    }

    /**
     * Initialiseer de sequence utility klasse.
     */
    public static void init() {
        if (!isInit) {
            log.debug("Start initializing SequenceUtil");

            Session kernSession = HibernateSessionFactoryProvider.getInstance().getKernFactory().openSession();
            kernSession.beginTransaction();

            List<String> tabelNamen = geefTabelNamenKern();

            for (final String tabelNaam : tabelNamen) {
                Number maxTabel = (Number) kernSession
                        .createSQLQuery("SELECT Coalesce(MAX(id), 1) FROM kern." + tabelNaam + ";")
                        .uniqueResult();
                maxMap.put(tabelNaam, maxTabel.longValue());
            }

            kernSession.getTransaction().rollback();
            log.debug("Finished initializing SequenceUtil");
            isInit = true;
        }
    }

    private static List<String> geefTabelNamenKern() {
        final String query =
                "SELECT table_name FROM information_schema.tables WHERE table_schema='kern' order by table_name ASC";
        final List<String> tabelNamenKern = new ArrayList<>();

        final Session kernSession = HibernateSessionFactoryProvider.getInstance().getKernFactory().openSession();
        kernSession.beginTransaction();

        if (kernSession.createSQLQuery(query).list() == null
                || !kernSession.createSQLQuery(query).list().isEmpty()) {
            for (final Object tabelNaamObject : kernSession.createSQLQuery(query).list()) {
                tabelNamenKern.add((String) tabelNaamObject);
            }
        } else {
            log.error("Schema bestaat niet of heeft geen tabellen");
        }
        kernSession.getTransaction().rollback();

        return tabelNamenKern;
    }

    public static synchronized Long getMax(final String tabelNaam) {
        // Lowercase ivm case sensitive compare.
        String tabelNaamLowercase = tabelNaam.toLowerCase();

        // Tabelnamen van hisrecords wijken af van hun domein klasse namen.
        if (tabelNaamLowercase.contains("his")) {
            tabelNaamLowercase = tabelNaamLowercase.replace("his", "his_");
        }

        // Speciaal geval
        if (tabelNaamLowercase.equals("persoon")) {
            tabelNaamLowercase = "pers";
        }

        Long maxTabel = maxMap.get(tabelNaamLowercase);
        if (maxTabel != null) {
            maxTabel++;
            maxMap.put(tabelNaamLowercase, maxTabel);
            return maxTabel;
        } else {
            log.error("Tabel heeft geen inhoud: " + tabelNaamLowercase);
            return 0L;
        }
    }

}
