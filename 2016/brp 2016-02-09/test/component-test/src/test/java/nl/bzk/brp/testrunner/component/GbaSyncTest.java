/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.component;

import junit.framework.TestCase;
import nl.bzk.brp.testrunner.component.services.datatoegang.VerzoekMetJdbcTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;

@RunWith(JUnit4.class)
public class GbaSyncTest extends TestCase{
//
//    @Test
//    public void story1() {
//        Embedder embedder = new BrpEmbedder();
////        embedder.runStoriesAsPaths(Collections.singletonList("test.story"));
//
//        embedder.runStoriesAsPaths(Arrays.asList(
//            "gbasync/per_categorie/categorie01.story",
//            "gbasync/per_categorie/categorie01_deel1.story"
//            //"gbasync/per_categorie/categorie01_deel2.story",
////            "gbasync/per_categorie/categorie02.story",
////            "gbasync/per_categorie/categorie04a_1.story",
////            "gbasync/per_categorie/categorie04a_2.story",
////            "gbasync/per_categorie/categorie04a_3.story",
////            "gbasync/per_categorie/categorie04a_4.story",
//            //"gbasync/per_categorie/categorie04a_5.story"
//        ));
//    }

    @Test
    public void omgevingTest() throws Exception {

        final BrpOmgeving omgeving = new OmgevingBouwer().metLegeDatabase().maak();
        omgeving.start();
        omgeving.wachtTotFunctioneelBeschikbaar();

        //assert geen personen in database
        omgeving.database().voerUit(new VerzoekMetJdbcTemplate() {
            @Override
            public void voerUitMet(final JdbcTemplate jdbcTemplate) {
                final Integer aantalPersonen = jdbcTemplate.queryForObject("select count(*) from kern.pers", Integer.class);
                assertTrue(aantalPersonen == 0);
            }
        });

        //voer de pl op
        omgeving.gba().plMetSync(new ClassPathResource("/pl/test_pl.xls"));

        //assert dat de database gevuld is
        omgeving.database().voerUit(new VerzoekMetJdbcTemplate() {
            @Override
            public void voerUitMet(final JdbcTemplate jdbcTemplate) {
                final Integer aantalPersonen = jdbcTemplate.queryForObject("select count(*) from kern.pers", Integer.class);
                System.err.println("aantal personen:" + aantalPersonen);
                assertTrue(aantalPersonen > 0);
            }
        });
        omgeving.stop();
    }
}
