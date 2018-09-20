/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie;

import java.io.IOException;
import java.util.Properties;

import nl.bzk.brp.testdatageneratie.dataaccess.HibernateSessionFactoryProvider;
import org.apache.log4j.Logger;
import org.hibernate.Session;


public class Main {

    private static Logger         log      = Logger.getLogger(Main.class);
    private static Session        kernSession;

    private static final String[] tabellen = {
        "aangadresh",
        "actie",
        "adellijketitel",
        "autvanafgiftereisdoc",
        "betr",
        "bron",
        "categoriepersonen",
        "categoriesrtactie",
        "categoriesrtdoc",
        "dbobject",
        "doc",
        "element",
        "functieadres",
//        "gegeveninonderzoek", wordt wel gevuld maar is zeer zeldzaam
        "geslachtsaand",
        "his_betrouderlijkgezag",
        "his_betrouderschap",
        "his_doc",
        "his_multirealiteitregel",
//        "his_onderzoek", wordt wel gevuld maar is zeer zeldzaam
//        "his_partij", stamtabel
//        "his_partijgem", stamtabel
        "his_persaanschr",
        "his_persadres",
        "his_persbijhgem",
        "his_persbijhverantwoordelijk",
        "his_perseuverkiezingen",
        "his_persgeboorte",
        "his_persgeslachtsaand",
        "his_persgeslnaamcomp",
        "his_persids",
        "his_persimmigratie",
        "his_persindicatie",
        "his_persinschr",
        "his_persnation",
        "his_persopschorting",
        "his_persoverlijden",
        "his_perspk",
        "his_persreisdoc",
        "his_perssamengesteldenaam",
        "his_persuitslnlkiesr",
        "his_persverblijfsr",
        "his_persverificatie",
        "his_persvoornaam",
        "his_relatie",
        "land",
        "multirealiteitregel",
        "nation",
//        "onderzoek", wordt wel gevuld maar is zeer zeldzaam
        "partij",
//        "partijrol", stamtabel
        "pers",
        "persadres",
        "persgeslnaamcomp",
        "persindicatie",
        "persnation",
//        "personderzoek", wordt wel gevuld maar is zeer zeldzaam
        "persreisdoc",
        "persverificatie",
        "persvoornaam",
        "plaats",
        "predikaat",
        "rdnbeeindrelatie",
        "rdnopschorting",
        "rdnverknlnation",
        "rdnverliesnlnation",
        "rdnvervallenreisdoc",
        "rdnwijzadres",
        "regel",
        "regelverantwoording",
        "relatie",
        "rol",
//        "sector", stamtabel
        "srtactie",
        "srtbetr",
        "srtdbobject",
        "srtdoc",
        "srtelement",
        "srtindicatie",
        "srtmultirealiteitregel",
        "srtnlreisdoc",
        "srtpartij",
        "srtpers",
        "srtrelatie",
        "srtverificatie",
        "verantwoordelijke",
        "verblijfsr",
        "verdrag",
        "wijzegebruikgeslnaam",
        };

    /**
     * @param args
     */
    public static void main(final String[] args) {
        SynDbGen.main(args);
        initHibernate();
        test();
        log.info("---------------------- Einde Test ------------------------------");
    }

    private static void test() {
        kernSession = HibernateSessionFactoryProvider.getInstance().getKernFactory().openSession();
        kernSession.beginTransaction();
        boolean hasErrors = false;
        for (String tabel : tabellen) {
            hasErrors |= checkTabel("kern." + tabel);
        }
        kernSession.getTransaction().commit();
        kernSession.close();
        if (hasErrors) {
            log.error("Testfailures", new RuntimeException("Niet alles is gevuld"));
        }
    }

    private static boolean checkTabel(final String tabel) {
        if (kernSession.createSQLQuery("select id from " + tabel + " limit 1").uniqueResult() == null) {
            log.error(tabel + " is nog leeg !!!!!!!!!!!!!!!!!!!!");
            return true;
        }
        return false;
    }

    private static void initHibernate() {
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
