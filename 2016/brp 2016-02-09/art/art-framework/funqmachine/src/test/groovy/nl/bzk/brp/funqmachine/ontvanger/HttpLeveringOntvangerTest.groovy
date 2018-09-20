package nl.bzk.brp.funqmachine.ontvanger

import static groovyx.net.http.ContentType.TEXT
import static groovyx.net.http.Method.GET
import static groovyx.net.http.Method.POST

import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import org.apache.http.HttpStatus
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class HttpLeveringOntvangerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpLeveringOntvangerTest.class)

    HttpLeveringOntvanger service

    @Before
    void setUp() {
        service = new HttpLeveringOntvanger(8889)
    }

    @After
    void tearDown() { service?.stop() }

    @Test
    void testOntvangerMetRandomPort() {
        assert new HttpLeveringOntvanger(new RandomPortnumberGenerator()).url.toString() =~ 'http://\\d+.\\d+.\\d+.\\d+:12\\d{3}/ontvanger'
    }

    @Test
    void naVerzoekIsCountEenHoger() {
        assert service.receivedMessages == 0

        def http = new HTTPBuilder(service.getUrl())
        http.request(POST, TEXT) {
            body = '<request><body>foo</body></request>'
            response.success = { resp, data ->
                assert resp.status == HttpStatus.SC_ACCEPTED
            }
        }

        assert service.receivedMessages == 1
    }

    @Test
    void testMijnIpAdres() {
        final Enumeration<NetworkInterface> netwerkInterfaces = NetworkInterface.getNetworkInterfaces()

        for (NetworkInterface netwerkInterface : netwerkInterfaces) {
            for (InterfaceAddress interfaceAdres : netwerkInterface.getInterfaceAddresses()) {
                final InetAddress inetAddress = interfaceAdres.getAddress()

                final String ipAdres = inetAddress.getHostAddress()

                if (ipAdres.startsWith("192.168.212")) {
                    LOGGER.info("VPN-adres gevonden: $ipAdres")
                }

            }
        }
    }
}
