package nl.bzk.brp.funqmachine.jbehave.steps

import java.util.concurrent.ForkJoinPool
import nl.bzk.brp.funqmachine.configuratie.Environment
import nl.bzk.brp.funqmachine.configuratie.JmxCommand
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

class JmxConnectorTest {

    JmxConnectorService service;
    ForkJoinPool pool;

    @Before
    void setUp() {
        service = new JmxConnector()
    }

    @Ignore
    @Test
    void testEnvironment() {
        Environment.env = new Environment("oap11")
        JmxCommand jmxCommand = Environment.instance().getApplicationBrokerObject()
    }

    @Ignore
    @Test
    void testConnection() {
        Environment.env = new Environment("oap11")
        service.purgeQueues()
    }
}
