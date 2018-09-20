package nl.bzk.brp.soapui.handlers

import com.eviware.soapui.model.testsuite.TestCaseRunContext
import groovy.sql.Sql
import org.apache.log4j.Logger
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

import static org.mockito.Matchers.contains
import static org.mockito.Mockito.when

class ObjectSleutelHandlerTest {
    @Mock
    TestCaseRunContext context
    Logger log = Logger.getLogger(ObjectSleutelHandlerTest.class)

    @Before
    void setUp() {
        MockitoAnnotations.initMocks(this)

        when(context.expand(contains('ds_user'))).thenReturn('brp')
        when(context.expand(contains('ds_password'))).thenReturn('brp')
        when(context.expand(contains('ds_url'))).thenReturn('jdbc:h2:~/mymemdb')
        when(context.expand(contains('jdbcDriver'))).thenReturn('org.h2.Driver')
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
    void kanSleutelGenereren() {
        ObjectSleutelHandler handler = new ObjectSleutelHandler()

        def sleutel = handler.genereerObjectSleutelString(123, 36101, 1394047676684L)

        assert sleutel == 'NBZy4+h1Ky6GoaX3AoE5Nkp/oG2qBb/u'
    }

    @Test
    void vervangtBsnsDoorSleutelsInLevBericht() {
        def file = new File(getClass().getResource('/templates/LEV_VerzoekSynchronisatiePersoon_request_template.xml').toURI())

        when(context.expand('${DataSource Values#zendendePartij_slB1}')).thenReturn('036101')

        def result = ObjectSleutelHandler.vervangBsnsDoorObjectSleutels(context, file, log)

        assert result != null
    }

    @Test
    void vervangtBsnsDoorSleutels() {
        def file = new File(getClass().getResource('/templates/ART-technisch_request_template_042.xml').toURI())

        when(context.expand('${DataSource Values#zendendePartij_zsH0}')).thenReturn('036101')
        when(context.expand(contains('#burgerservicenummer_'))).thenReturn('700137038')

        def result = ObjectSleutelHandler.vervangBsnsDoorObjectSleutels(context, file, log)

        def matcher = result =~ /brp:objectSleutel=".{32}"/
        assert matcher.count == 3
    }
}
