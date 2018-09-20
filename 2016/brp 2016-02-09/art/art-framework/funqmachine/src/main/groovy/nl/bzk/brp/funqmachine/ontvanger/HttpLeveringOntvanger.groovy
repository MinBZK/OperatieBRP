package nl.bzk.brp.funqmachine.ontvanger

import com.sun.net.httpserver.HttpContext
import com.sun.net.httpserver.HttpServer
import java.util.concurrent.Executors
import javax.annotation.PreDestroy
import nl.bzk.brp.funqmachine.configuratie.Environment
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

/**
 * Simple ontvanger that receives messages and counts them.
 */
@Component
@Scope('singleton')
class HttpLeveringOntvanger implements LeveringOntvanger {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpLeveringOntvanger)

    private static final String VPN_IP_BEGIN = '192.168.212'
    private static final String DOCKER_IP_BEGIN = '172.17.'
    private static final String CONTEXT = '/ontvanger'

    HttpServer server
    LeveringHttpHandler handler

    /**
     * Constructor met een generator dat een portnummer genereert.
     *
     * @param portnumberGenerator
     */
    @Autowired
    HttpLeveringOntvanger(PortnumberGenerator portnumberGenerator) {
        this(portnumberGenerator.generate())
    }

    /**
     * Constructor met een vast portnummer.
     * @param port
     */
    HttpLeveringOntvanger(final int port) {
        LOGGER.info('Ontvanger wordt gestart op poort {}.', port)
        server = HttpServer.create(new InetSocketAddress(bepaalExternIpAdres(), port), 0)

        // threaded ontvangen, anders duurt ontvangen van meerdere berichten erg lang
        server.executor = Executors.newCachedThreadPool()
        handler = new LeveringHttpHandler()

        HttpContext context = server.createContext(CONTEXT, handler)

        server.start()
        final String endpointUrl = getUrl()
        LOGGER.info("Ontvanger gestart op poort $port en url $endpointUrl.")
    }

    /**
     * Stop de ontvanger met een wachttijd.
     * @param waitTime de wachttijd
     */
    void stop(int waitTime) {
        LOGGER.info("Ontvanger wordt gestopt (${server.address})")
        handler?.reset()
        server.removeContext(CONTEXT)
        server.stop(waitTime)
        LOGGER.info("Ontvanger gestopt (${server.address})")
    }

    /**
     * Stop de ontvanger.
     */
    @PreDestroy
    void stop() {
        stop(1)
    }

    /**
     * Geeft de URL waarop deze ontvanger beschikbaar is.
     */
    URL getUrl() {
        InetSocketAddress address = server?.address
        return new URL('http', address.hostString, address.port, CONTEXT)
    }

    /**
     * Geeft het aantal berichten dat de ontvanger heeft gekregen.
     */
    int getReceivedMessages() {
        return handler?.leveringen?.size() ?: 0
    }

    /**
     * Geeft het bericht terug.
     * @param i de index van het bericht
     */
    String getMessage(int i) {
        return handler?.getAt(i - 1)
    }

    /**
     * Geeft de lijst van ontvangen berichten.
     */
    List<String> getMessages() {
        return handler?.leveringen
    }

    /**
     * Reset de ontvanger.
     */
    void reset() {
        handler?.reset()
    }

    /*
     * Deze methode loopt over alle netwerk-interfaces en pakt het externe ip-adres van het VPN-netwerk.
     * Dit doen we zodat we vanaf een lokale machine in het VPN-netwerk tegen een OAP-machine kunnen praten.
     * Of eigelijk: de OAP-machine tegen de lokale machine kan terugpraten.
     *
     * @return Het externe VPN-ip-adres of het default externe adres (als vanaf Jenkins gedraaid wordt).
     */
    private InetAddress bepaalExternIpAdres() {
        final Enumeration<NetworkInterface> netwerkInterfaces = NetworkInterface.getNetworkInterfaces()
        InetAddress vpnAdres = null;
        InetAddress dockerAdres = null;
        for (NetworkInterface netwerkInterface : netwerkInterfaces) {
            for (InterfaceAddress interfaceAdres : netwerkInterface.getInterfaceAddresses()) {
                final InetAddress inetAddress = interfaceAdres.getAddress()

                final String ipAdres = inetAddress.getHostAddress()

                if (ipAdres.startsWith(VPN_IP_BEGIN)) {
                    LOGGER.info("VPN-adres gevonden: $ipAdres")
                    vpnAdres = inetAddress;
                } else if (ipAdres.startsWith(DOCKER_IP_BEGIN)) {
                    LOGGER.info("Docker-adres gevonden: $ipAdres")
                    dockerAdres = inetAddress;
                }

            }
        }

        if (Environment.instance().isIsDockerOmgeving() && Environment.instance().isDockerOmgevingLokaal()) {
            return dockerAdres;
        } else if (vpnAdres != null) {
            return vpnAdres;
        }
        //Geen vpn-adres gevonden, return de default
        LOGGER.info("Geen VPN-adres gevonden, we gebruiken localhost.")
        return InetAddress.getLocalHost()
    }
}
