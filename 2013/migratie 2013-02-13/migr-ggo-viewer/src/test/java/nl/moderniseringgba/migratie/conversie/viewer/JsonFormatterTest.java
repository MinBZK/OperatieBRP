/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.viewer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.viewer.log.FoutMelder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class JsonFormatterTest {

    private final JsonFormatter jsonFormatter = new JsonFormatter();

    @Test
    public void testHeaders() {
        final ViewerResponse viewerResponse = createViewerResponse();
        final ResponseEntity<String> jsonResultaat = jsonFormatter.format(viewerResponse);

        assertEquals("Alleen ContentType is aanwezig", 1, jsonResultaat.getHeaders().size());
        assertEquals("char-set must be utf-8", "UTF-8", jsonResultaat.getHeaders().getContentType().getCharSet()
                .name());
        assertEquals("type must be application", "application", jsonResultaat.getHeaders().getContentType().getType());
        assertEquals("subtype must be json", "json", jsonResultaat.getHeaders().getContentType().getSubtype());
    }

    @Test
    public void testStatusCode() {
        final ViewerResponse viewerResponse = createViewerResponse();
        final ResponseEntity<String> jsonResultaat = jsonFormatter.format(viewerResponse);

        assertEquals("StatusCode must be 200 OK", HttpStatus.OK, jsonResultaat.getStatusCode());
    }

    @Test
    public void testBody() {
        final ViewerResponse viewerResponse = createViewerResponse();
        final ResponseEntity<String> jsonResultaat = jsonFormatter.format(viewerResponse);

        final String jsonBody = jsonResultaat.getBody();
        assertTrue(jsonBody.contains("\"persoonslijstGroepen\" : [ {"));
        assertTrue(jsonBody.contains("\"lo3Persoonslijst\" : null,"));
        assertTrue(jsonBody.contains("\"brpPersoonslijst\" : null,"));
        assertTrue(jsonBody.contains("\"teruggeconverteerd\" : null,"));
        assertTrue(jsonBody.contains("\"vergelijking\" : null,"));
        assertTrue(jsonBody.contains("\"precondities\" : null,"));
        assertTrue(jsonBody.contains("\"bcmSignaleringen\" : null"));
        assertTrue(jsonBody.contains("\"foutRegels\" : [ ],"));
        assertTrue(jsonBody.contains("\"success\" : \"true\""));
    }

    private ViewerResponse createViewerResponse() {
        final FoutMelder foutMelder = new FoutMelder();
        final List<PersoonslijstGroep> persoonslijstGroepen = new ArrayList<PersoonslijstGroep>();
        persoonslijstGroepen.add(new PersoonslijstGroep(null, null, null, null, null, null));

        return new ViewerResponse(persoonslijstGroepen, foutMelder.getFoutRegels());
    }
}
