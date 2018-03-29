/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.robuustheid;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.apache.activemq.broker.BrokerService;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(locations = {"classpath:brp-broker-context.xml"})
public class BrokerPerformanceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final String MVN_CMD = "mvn";
    private static final String PLUGIN_GROUP = "org.apache.activemq.tooling";
    private static final String PLUGIN_ARTIFACT = "activemq-perf-maven-plugin";
    private static final String PLUGIN_VERSION = "5.12.1";
    private static final String PLUGIN_CONSUMER_GOAL = "consumer";
    private static final String PLUGIN_PRODUCER_GOAL = "producer";
    private static final String CONSUME_CMD = PLUGIN_GROUP + ":" + PLUGIN_ARTIFACT + ":" + PLUGIN_VERSION + ":" + PLUGIN_CONSUMER_GOAL;
    private static final String PRODUCER_CMD = PLUGIN_GROUP + ":" + PLUGIN_ARTIFACT + ":" + PLUGIN_VERSION + ":" + PLUGIN_PRODUCER_GOAL;


    @Autowired
    private BrokerService broker;

    @Value("${brp.jms.client.url}")
    private String brokerURL;

    private Map<String, Process> processMap = new HashMap<>();

    private static File parentOutputDir;
    private File testOutputDir;

    @BeforeClass
    public static void beforeClass() {
        parentOutputDir = new File("target", "perftest");
        if (!parentOutputDir.exists()) {
            Assert.isTrue(parentOutputDir.mkdirs(), "Kan de directory niet aanmaken: " + parentOutputDir.getAbsolutePath());
        }
    }

    @Before
    public void voorTest() throws InterruptedException, IOException {
        while (!broker.isStarted()) {
            LOGGER.info("Wacht tot activemq broker gestart is...");
            TimeUnit.SECONDS.sleep(1);
        }
        LOGGER.info("Broker gestart op URI: ", brokerURL);
        broker.deleteAllMessages();
    }

    @After
    public void naTest() throws Exception {
        for (Map.Entry<String, Process> entry : processMap.entrySet()) {
            final Process proces = entry.getValue();
            if (proces.isAlive()) {
                LOGGER.info("Kill process: {}", entry.getKey());
                proces.destroyForcibly();
                proces.waitFor();
            }
        }
    }

    @Test(timeout = 1000 * 60)
    public void test() throws IOException, InterruptedException {
        setTestName("basistest");

        final Map<String, String> consumerMap = new HashMap<>();
        consumerMap.put("consumer.recvDuration", "10000");
        consumerMap.put("consumer.sessTransacted", "true");
        consumerMap.put("consumer.destName", "queue://myqueue");
        final Process consumerProcess = maakProcess(true, consumerMap, "consumer");

        final Map<String, String> producerMap = new HashMap<>();
        producerMap.put("producer.sendDuration", "10000");
        producerMap.put("producer.sessTransacted", "true");
        producerMap.put("producer.sessAckMode", "transacted");
        producerMap.put("producer.deliveryMode", "persistent");
        producerMap.put("producer.destName", "queue://myqueue");
        final Process producerProcess = maakProcess(false, producerMap, "producer");
        consumerProcess.waitFor();
    }

    @Test
    public void testVerzendingAfnemerQueue() throws IOException, InterruptedException {
        setTestName("VerzendingAfnemerQueueTest");

        final Map<String, String> consumerMap = new HashMap<>();
        consumerMap.put("consumer.recvDuration", "30000");
        consumerMap.put("consumer.sessTransacted", "true");
        consumerMap.put("consumer.destName", "queue://VerzendingAfnemerQueue");
        consumerMap.put("consumer.recvDelay", "30");
        final Process consumerProcess = maakProcess(true, consumerMap, "consumer");

        final Map<String, String> producerMap = new HashMap<>();
        producerMap.put("producer.sendDuration", "30000");
        producerMap.put("producer.messageSize", "200000");
        producerMap.put("producer.sessTransacted", "true");
        producerMap.put("producer.sessAckMode", "transacted");
        producerMap.put("producer.deliveryMode", "persistent");
        producerMap.put("producer.destName", "queue://VerzendingAfnemerQueue");
        final Process producerProcess = maakProcess(false, producerMap, "producer");
        consumerProcess.waitFor();
    }

    private Process maakProcess(final boolean isConsumer, final Map<String, String> cmdMap, final String procesNaam) throws IOException {
        final List<String> cmdList = new ArrayList<>();
        cmdList.add(MVN_CMD);
        cmdList.add(isConsumer ? CONSUME_CMD : PRODUCER_CMD);
        for (Map.Entry<String, String> entry : cmdMap.entrySet()) {
            cmdList.add(String.format("-D%s=%s", entry.getKey(), entry.getValue()));
        }
        final ProcessBuilder processBuilder = new ProcessBuilder(cmdList);
        processBuilder.redirectErrorStream(true);
        final File outputFile = new File(testOutputDir, procesNaam + ".txt");
        if (outputFile.exists()) {
            Assert.isTrue(outputFile.delete(), "Kan de bestaande outputfile verwijderen: " + outputFile.getAbsolutePath());
        }
        processBuilder.redirectOutput(ProcessBuilder.Redirect.to(outputFile));
        final Process p = processBuilder.start();
        processMap.put(procesNaam, p);
        LOGGER.info("Process gestart: {}", procesNaam);
        return p;
    }

    public void setTestName(final String testName) {
        LOGGER.info("Start test {}", testName);
        this.testOutputDir = new File(parentOutputDir, testName);
        if (!this.testOutputDir.exists()) {
            Assert.isTrue(this.testOutputDir.mkdir(), "Kan de directory niet aanmaken: " + testOutputDir.getAbsolutePath());
        }
    }
}
