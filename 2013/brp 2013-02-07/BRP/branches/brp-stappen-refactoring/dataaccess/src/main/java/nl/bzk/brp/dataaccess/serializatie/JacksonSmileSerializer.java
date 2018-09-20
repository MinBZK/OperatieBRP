/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.serializatie;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.dataformat.smile.SmileFactory;
import com.fasterxml.jackson.dataformat.smile.SmileGenerator;
import org.springframework.stereotype.Component;

/**
 * Serializer die gebruik maakt van de <a href="http://jackson.codehaus.org/">Jackson</a> library en
 * het <a href="http://wiki.fasterxml.com/JacksonForSmile">Smile</a> formaat.
 */
@Component
public class JacksonSmileSerializer extends JacksonJsonSerializer {

    /**
     *
     */
    public JacksonSmileSerializer() {
        super(createFactory());
    }

    /**
     * Creeer een SmileFactory.
     *
     * @return factory
     */
    private static JsonFactory createFactory() {
        SmileFactory f = new SmileFactory();
        f.enable(SmileGenerator.Feature.CHECK_SHARED_NAMES);
        f.enable(SmileGenerator.Feature.CHECK_SHARED_STRING_VALUES);
        f.enable(SmileGenerator.Feature.WRITE_HEADER);

        return f;
    }
}
