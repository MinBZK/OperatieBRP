/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * Deze klasse wordt gebruikt bij het omzetten van het ViewerResponse object naar JSON welke naar de pagina gaat.
 */
@Component
public class JsonFormatter {

    /**
     * Response object alvast omzetten naar JSON.
     *
     * @param uploadResponse
     *            ViewerResponse
     * @return ResponseEntity<String> JSON
     */
    public final ResponseEntity<String> format(final ViewerResponse uploadResponse) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

            return new ResponseEntity<>(mapper.writeValueAsString(uploadResponse), HttpStatus.OK);
        } catch (final IOException ioe) {
            throw new IllegalArgumentException(ioe);
        }
    }
}
