/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.gbaws;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.test.common.vergelijk.SorteerXml;
import nl.bzk.migratiebrp.test.common.vergelijk.VergelijkXml;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.AbstractKanaal;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.KanaalException;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.TestCasusContext;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Required;


/**
 * Basis voor Gba Webservice kanalen.
 */
public class GbaWsKanaal extends AbstractKanaal {
    private static final Logger LOG = LoggerFactory.getLogger();
    private static final Charset UTF8 = Charset.forName("UTF-8");

    private final Map<String, Bericht> antwoorden = new HashMap<>();

    private String url;
    private String kanaal;

    @Required
    public void setUrl(final String url) {
        this.url = url;
    }

    @Override
    public String getKanaal() {
        return kanaal;
    }

    @Required
    public void setKanaal(final String kanaal) {
        this.kanaal = kanaal;
    }

    @Override
    public final void verwerkUitgaand(final TestCasusContext testCasus, final Bericht bericht) throws KanaalException {
        LOG.info("Aanroepen GBA Webservice ...");
        String verzendendePartij = bericht.getVerzendendePartij();

        final HttpClient client = HttpClientBuilder.create().build();
        final HttpPost request = new HttpPost(url);
        request.setHeader("partij-code", verzendendePartij);
        request.setEntity(new StringEntity(bericht.getInhoud(), UTF8));

        final String responseContents;
        try {
            final HttpResponse response = client.execute(request);
            final HttpEntity entity = response.getEntity();
            responseContents = EntityUtils.toString(entity);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                LOG.error("Webservice response body: {}", responseContents);
                throw new KanaalException("Webservice niet succesvol; HTTP error: " + response.getStatusLine().getStatusCode() + ", msg: "
                        + response.getStatusLine().getReasonPhrase() + ", query: " + request.toString());
            }
        } catch (IOException e) {
            throw new KanaalException("Webservice kon niet aangeroepen worden: " + request.getURI(), e);
        } finally {
            request.releaseConnection();
        }
        LOG.info("Webservice aangeroepen; registreren antwoord.");

        final Bericht antwoord = new Bericht();
        antwoord.setInhoud(responseContents);
        antwoord.setCorrelatieReferentie(antwoord.getBerichtReferentie());

        antwoorden.put(bericht.getBerichtReferentie(), antwoord);
    }


    @Override
    public final Bericht verwerkInkomend(final TestCasusContext testCasus, final Bericht verwachtBericht) {
        LOG.info("Ophalen geregistreerd antwoord voor GBA webservice");
        final Bericht ontvangenBericht = antwoorden.remove(verwachtBericht.getCorrelatieReferentie());

        if (ontvangenBericht != null) {
            ontvangenBericht.setBerichtReferentie(verwachtBericht.getBerichtReferentie());
        }

        return ontvangenBericht;
    }

    @Override
    protected final boolean vergelijkInhoud(final String verwachteInhoud, final String ontvangenInhoud) {
        return VergelijkXml.vergelijkXml(SorteerXml.sorteer(verwachteInhoud), SorteerXml.sorteer(ontvangenInhoud));
    }

    @Override
    public final List<Bericht> naTestcase(final TestCasusContext testCasus) {
        final List<Bericht> result = new ArrayList<>(antwoorden.values());
        antwoorden.clear();

        return result;
    }

}
