/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import java.io.StringWriter;
import java.io.Writer;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortVrijBericht;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Unit test voor {@link VrijBerichtDeserializer}.
 */
public class VrijBerichtDeserializerTest {

    private final VrijBerichtDeserializer serializer = new VrijBerichtDeserializer();
    private final SoortVrijBericht soortVrijBericht = new SoortVrijBericht("Beheermelding");
    private final Writer writer = new StringWriter();
    private JsonGenerator generator;

    @Before
    public void voorTest() throws Exception {
        ReflectionTestUtils.setField(soortVrijBericht, "id", (short) 100);
        generator = new JsonFactory().createGenerator(writer);
    }

    @Test
    public void testDetailSerializer() throws Exception {
//        final VrijBericht vrijBericht = new VrijBericht(SoortBerichtVrijBericht.VERWERK_VRIJ_BERICHT, soortVrijBericht,
//                DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(ZonedDateTime.of(2017, 2, 21, 13, 0, 5, 0, ZoneId.of("UTC"))), "Inhoud vrij bericht", false);
//        final VrijBerichtPartij partij1 = new VrijBerichtPartij(vrijBericht, new Partij("test1", 123));
//        final VrijBerichtPartij partij2 = new VrijBerichtPartij(vrijBericht, new Partij("test2", 456));
//        final List<VrijBerichtPartij> partijList = asList(partij1, partij2);
//        ReflectionTestUtils.setField(vrijBericht, "vrijBerichtPartijen", partijList);
//
//        serializer.serialize(new VrijBerichtDetailView(vrijBericht), generator, null);
//
//        generator.close();
//        final String result = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/json/vrijbericht_details.json"))).lines()
//                .parallel().collect(Collectors.joining("\n"));
//        assertThat(JsonUtil.jsonEquals(writer.toString(), result), is(true));
    }

}