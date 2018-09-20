/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.ggo.viewer.log.FoutMelder;
import nl.bzk.migratiebrp.ggo.viewer.model.GgoPersoonslijstGroep;
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
        assertTrue(jsonBody.contains("\"aNummer\" : null,"));
        assertTrue(jsonBody.contains("\"volledigeNaam\" : null,"));
        assertTrue(jsonBody.contains("\"ggoLo3PL\" : null,"));
        assertTrue(jsonBody.contains("\"ggoLo3PLTerugConversie\" : null,"));
        assertTrue(jsonBody.contains("\"ggoBrpPL\" : null,"));
        assertTrue(jsonBody.contains("\"meldingen\" : null"));
        assertTrue(jsonBody.contains("\"vergelijking\" : null,"));
        assertTrue(jsonBody.contains("\"foutLog\" : null"));
        assertTrue(jsonBody.contains("\"foutRegels\" : [ ],"));
        assertTrue(jsonBody.contains("\"success\" : \"true\""));
    }

    private ViewerResponse createViewerResponse() {
        final FoutMelder foutMelder = new FoutMelder();
        final List<GgoPersoonslijstGroep> persoonslijstGroepen = new ArrayList<>();
        persoonslijstGroepen.add(new GgoPersoonslijstGroep(null, null, null, null, null, null, null, null, null));

        return new ViewerResponse(persoonslijstGroepen, foutMelder.getFoutRegels());
    }
}
