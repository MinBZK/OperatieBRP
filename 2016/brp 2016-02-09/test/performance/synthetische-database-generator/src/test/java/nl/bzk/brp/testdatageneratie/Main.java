/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import nl.bzk.brp.testdatageneratie.dataaccess.HibernateSessionFactoryProvider;

import org.apache.log4j.Logger;
import org.hibernate.Session;


public class Main {

    private static Logger         log      = Logger.getLogger(Main.class);
    private static Session        kernSession;

    private static List<String> tabellenKern;

    /**
     * @param args
     */
    public static void main(final String[] args) throws Exception {
        SynDbGen.main(args);
        initHibernate();
        test();
        log.info("---------------------- Einde Test ------------------------------");
    }

    protected static void test() {
        kernSession = HibernateSessionFactoryProvider.getInstance().getKernFactory().openSession();
        kernSession.beginTransaction();
        boolean hasErrors = false;

        tabellenKern = geefTabelNamenKern();

        for (final String tabel : tabellenKern) {
            hasErrors |= checkTabel("kern." + tabel);
        }
        kernSession.getTransaction().commit();
        kernSession.close();
        if (hasErrors) {
            log.error("Testfailures, niet alles is gevuld");
        }
    }

    private static List<String> geefTabelNamenKern() {
        final String query =
                "SELECT table_name FROM information_schema.tables WHERE table_schema='kern' order by table_name ASC";
        final List<String> tabelNamenKern = new ArrayList<>();

        if (kernSession.createSQLQuery(query).list() == null
                || !kernSession.createSQLQuery(query).list().isEmpty()) {
            for (final Object tabelNaamObject : kernSession.createSQLQuery(query).list()) {
                tabelNamenKern.add((String) tabelNaamObject);
            }
        } else {
            log.error("Schema bestaat niet of heeft geen tabellen");
        }
        return tabelNamenKern;
    }

    private static boolean checkTabel(final String tabel) {
        if (kernSession.createSQLQuery("select id from " + tabel + " limit 1").uniqueResult() == null) {
            log.error(tabel + " is nog leeg");
            return true;
        }
        return false;
    }

    protected static void initHibernate() {
        Properties properties = new Properties();
        try {
            properties.load(ClassLoader.getSystemResourceAsStream(SynDbGen.PROPERTIES_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
        HibernateSessionFactoryProvider hibernateSessionFactoryProvider =
            new HibernateSessionFactoryProvider(properties);
        HibernateSessionFactoryProvider.setInstance(hibernateSessionFactoryProvider);
    }

}
