package nl.bzk.brp.soapui.utils

import com.eviware.soapui.model.testsuite.TestCaseRunContext
import groovy.sql.Sql
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

import static nu.xom.tests.XOMTestCase.assertEquals
import static org.mockito.Matchers.contains
import static org.mockito.Mockito.when

class ReferentieDataInvullerTest {

    @Mock
    TestCaseRunContext context

    @Before
    void setUp() {
        MockitoAnnotations.initMocks(this)

        when(context.expand(contains('ds_user'))).thenReturn('brp')
        when(context.expand(contains('ds_password'))).thenReturn('brp')
        when(context.expand(contains('ds_url'))).thenReturn('jdbc:h2:~/mymemdb')
        when(context.expand(contains('jdbcDriver'))).thenReturn('org.h2.Driver')
        when(context.expand(contains('${DataSource Values#|objectid.persoon1|}'))).thenReturn('700137038')
        when(context.expand(contains('${DataSource Values#|objectid.persoon2|}'))).thenReturn('800011375')
        when(context.expand(contains('${DataSource Values#|objectid.persoon3|}'))).thenReturn('123')
    }

    @BeforeClass
    static void makeDB() {
        def props = [user: 'brp', password: 'brp', allowMultiQueries: 'true'] as Properties

        Sql sql = Sql.newInstance('jdbc:h2:~/mymemdb', props, 'org.h2.Driver')

        sql.execute("""
DROP SCHEMA IF EXISTS kern;
DROP SCHEMA IF EXISTS kern;
CREATE SCHEMA kern;
  CREATE TABLE kern.pers (id integer, bsn integer);

INSERT INTO kern.pers (id, bsn) values (11, 700137038);
INSERT INTO kern.pers (id, bsn) values (12, 800011375);
""")
    }

    @Test
    public void testBsnNaarId() {
        String resultaat = ReferentieDataInvuller.vervangReferentieData(context, "query met een id:"
                + " persoon_id_voor_bsn(\${DataSource Values#|objectid.persoon1|}) blablabla")

        assertEquals("query met een id: 11 blablabla", resultaat)
    }

    @Test
    public void testBsnNaarIdMeerdere() {
        String resultaat = ReferentieDataInvuller.vervangReferentieData(context, "query met een id:"
                + " persoon_id_voor_bsn(\${DataSource Values#|objectid.persoon1|}) "
                + "blablabla persoon_id_voor_bsn(\${DataSource Values#|objectid.persoon2|}) eindebericht")

        assertEquals("query met een id: 11 blablabla 12 eindebericht", resultaat)
    }

    @Test
    public void testBsnNaarIdMeerdereEnDubbele() {
        String resultaat = ReferentieDataInvuller.vervangReferentieData(context, "query met een id:"
                + " persoon_id_voor_bsn(\${DataSource Values#|objectid.persoon1|}), "
                + "persoon_id_voor_bsn(\${DataSource Values#|objectid.persoon1|}) "
                + "blablabla persoon_id_voor_bsn(\${DataSource Values#|objectid.persoon2|}) eindebericht")

        assertEquals("query met een id: 11, 11 blablabla 12 eindebericht", resultaat)
    }

    @Test(expected = IllegalStateException.class)
    public void testBsnNaarIdNietGevonden() {
        ReferentieDataInvuller.vervangReferentieData(context, "query met een id:"
                + " persoon_id_voor_bsn(\${DataSource Values#|objectid.persoon3|}) blablabla")
    }

    @Test(expected = IllegalStateException.class)
    public void testBsnNaarIdNietBestaandBsn() {
        ReferentieDataInvuller.vervangReferentieData(context, "query met een id:"
                + " persoon_id_voor_bsn(abc) blablabla")
    }

}
