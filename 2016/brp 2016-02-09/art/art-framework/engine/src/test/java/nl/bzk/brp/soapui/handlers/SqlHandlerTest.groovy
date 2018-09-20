package nl.bzk.brp.soapui.handlers

import com.eviware.soapui.model.testsuite.TestCaseRunContext
import groovy.sql.Sql
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

import static org.mockito.Matchers.contains
import static org.mockito.Mockito.when

class SqlHandlerTest {
    @Mock
    TestCaseRunContext context

    @Before
    void setUp() {
        MockitoAnnotations.initMocks(this)

        when(context.expand(contains('ds_user'))).thenReturn('brp')
        when(context.expand(contains('ds_password'))).thenReturn('brp')
        when(context.expand(contains('ds_url'))).thenReturn('jdbc:h2:mem:mymemdb')
        when(context.expand(contains('mq_url'))).thenReturn('jdbc:h2:mem:mymemdb')
        when(context.expand(contains('jdbcDriver'))).thenReturn('org.h2.Driver')
    }

    @Test
    void kanSqlInstanceMaken() {
        Sql sql = SqlHandler.makeSql(context)

        assert sql != null
    }

    @Test
    void kanSqlInstanceMakenVoorActiveMQDatabase() {
        Sql sql = SqlHandler.makeActiveMqSql(context)

        assert sql != null
    }

    @Test
    void kanConnectionStringMaken() {
        String connection = SqlHandler.buildConnectionString(context, 'select count(id) from kern.pers')

        assert connection.contains('ds_url')
    }

    @Test
    void kanConnectionStringMakenVoorActiveMQDatabase() {
        String connection = SqlHandler.buildConnectionString(context, 'MQ::select count(id) from kern.pers')

        assert connection.contains('mq_url')
    }

}
