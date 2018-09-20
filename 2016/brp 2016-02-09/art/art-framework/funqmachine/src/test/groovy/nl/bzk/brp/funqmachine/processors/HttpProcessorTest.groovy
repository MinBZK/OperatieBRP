package nl.bzk.brp.funqmachine.processors

import org.junit.Ignore
import org.junit.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory

public class HttpProcessorTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpProcessorTest.class)

    @Test
    @Ignore
    public void testGetApplicationServerTime() {
        // Er dient wel een hosturl geconfigureerd te zijn, maar voor de test rekenen we niet op draaiende server, vandaar Google
        assert HttpProcessor.hostUrl != null
        LOGGER.debug("Eigenlijk host url: {}", HttpProcessor.hostUrl)

        HttpProcessor.hostUrl = "http://www.microsoft.com"

        def time = HttpProcessor.getApplicationServerTime();
        assert time != null
    }
}
