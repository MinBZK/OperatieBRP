/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import java.io.StringWriter;
import java.io.Writer;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.VrijBerichtPartij;
import nl.bzk.brp.beheer.webapp.JsonUtil;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test voor {@link VrijBerichtSerializer}.
 */
public class VrijBerichtPartijSerializerTest {

    private final VrijBerichtPartijSerializer serializer = new VrijBerichtPartijSerializer();
    private final Writer writer = new StringWriter();
    private JsonGenerator generator;

    @Before
    public void voorTest() throws Exception {
        generator = new JsonFactory().createGenerator(writer);
    }

    @Test
    public void testSerialize() throws Exception {
        final VrijBerichtPartij vrijBerichtPartij = new VrijBerichtPartij(null, new Partij("partijnaam", "000444"));

        serializer.serialize(vrijBerichtPartij, generator, null);

        generator.close();
        assertThat(JsonUtil.jsonEquals(writer.toString(), "{\"partijCode\":\"000444\",\"partijNaam\":\"partijnaam\"}"), is(true));
    }

}