package nl.bzk.brp.funqmachine.ontvanger

import static java.net.HttpURLConnection.HTTP_ACCEPTED

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpHandler
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicInteger
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * HttpHandler die ontvangen berichten in een lijst houdt, en dus beschikbaar heeft
 * ter introspectie.
 */
class LeveringHttpHandler implements HttpHandler {
    private final Logger logger = LoggerFactory.getLogger(LeveringHttpHandler)

    List<String> leveringen = new CopyOnWriteArrayList<>()

    /**
     * Handelt het request af. Om als fake soap-endpoint te fungeren,
     * wordt er een http_accepted antwoord terug gegeven om de zendende partij
     * het juiste antwoord te geven.
     *
     * @param exchange
     * @throws IOException
     */
    @Override
    void handle(final HttpExchange exchange) throws IOException {
        exchange.responseHeaders.set('Content-Type', 'text/plain')

        exchange.sendResponseHeaders(HTTP_ACCEPTED, 0)
        exchange.responseBody.withStream { OutputStream stream ->
            doExecute(exchange.requestBody, stream)
        }

        exchange.close()
    }

    /*
     * Sla de ontvangen soap:Body op voor later gebruik.
     */
    protected void doExecute(InputStream input, OutputStream output) {
        def envelope = new XmlParser(namespaceAware: true, trimWhitespace: false, keepIgnorableWhitespace: true).parse(input)

        Node node = envelope.'soap:Body'.'brp:lvg_synVerwerkPersoon'[0]
        if (!node) {
            node = envelope.'soap:Body'.'brp:bhg_fiaNotificeerBijhoudingsplan'[0]
        }

        StringWriter out = new StringWriter()
        def printer = new XmlNodePrinter(new IndentPrinter(out, '', false))
        printer.preserveWhitespace = true
        printer.print(node)
        leveringen.add(out.toString())
    }

    /**
     * Geeft het bericht op gegeven index.
     * @param i de index
     */
    String getAt(int i) {
        return leveringen.get(i)
    }

    /**
     * Geeft alle ontvangen berichten.
     */
    List<String> getLeveringen() {
        return Collections.unmodifiableList(leveringen)
    }

    /**
     * 'Reset' deze handler: zet de counter op 0 en verwijder ontvangen berichten.
     */
    void reset() {
        int current = leveringen.size()
        leveringen.clear()
        logger.info 'COUNTER at {}, reset to 0', current
    }
}
