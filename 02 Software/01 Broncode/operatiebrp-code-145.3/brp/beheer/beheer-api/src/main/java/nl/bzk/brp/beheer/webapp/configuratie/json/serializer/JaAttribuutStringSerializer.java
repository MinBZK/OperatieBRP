/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;

/**
 * Partij serializer.
 */
public class JaAttribuutStringSerializer extends JsonSerializer<Boolean> {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public final void serialize(final Boolean value, final JsonGenerator jgen, final SerializerProvider provider)
            throws IOException {
        LOG.info("Waarde {} wordt door serialisatie vertaald naar {}", value, value == null || !value ? "Nee" : "Ja");
        jgen.writeString(value == null || !value ? "Nee" : "Ja");
    }
}
