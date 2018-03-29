/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortVrijBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.VrijBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.VrijBerichtPartij;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBerichtVrijBericht;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.beheer.webapp.JsonUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Unit test voor {@link VrijBerichtSerializer}.
 */
public class VrijBerichtSerializerTest {

    private final VrijBerichtSerializer serializer = new VrijBerichtSerializer();
    private final SoortVrijBericht soortVrijBericht = new SoortVrijBericht("Beheermelding");
    private final Writer writer = new StringWriter();
    private JsonGenerator generator;

    @Before
    public void voorTest() throws Exception {
        ReflectionTestUtils.setField(soortVrijBericht, "id", (short) 100);
        generator = new JsonFactory().createGenerator(writer);
    }

    @Test
    public void testSerializeUitgaandEnkelePartij() throws Exception {
        final VrijBericht vrijBericht = new VrijBericht(SoortBerichtVrijBericht.STUUR_VRIJ_BERICHT, soortVrijBericht,
                DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(ZonedDateTime.of(2017, 2, 21, 13, 0, 5, 0, ZoneId.of("UTC"))), "Inhoud vrij bericht", false);
        final VrijBerichtPartij partij = new VrijBerichtPartij(vrijBericht, new Partij("test", "000123"));
        final List<VrijBerichtPartij> partijList = singletonList(partij);
        ReflectionTestUtils.setField(vrijBericht, "vrijBerichtPartijen", partijList);

        serializer.serialize(vrijBericht, generator, null);

        generator.close();
        final String result = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/json/vrijbericht_lijst_1partij.json"))).lines()
                .parallel().collect(Collectors.joining("\n"));
        assertThat(JsonUtil.jsonEquals(writer.toString(), result), is(true));
    }

    @Test
    public void testSerializeUitgaandMeerderePartijen() throws Exception {
        final VrijBericht vrijBericht = new VrijBericht(SoortBerichtVrijBericht.STUUR_VRIJ_BERICHT, soortVrijBericht,
                DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(ZonedDateTime.of(2017, 2, 21, 13, 0, 5, 0, ZoneId.of("UTC"))), "Inhoud vrij bericht", false);
        final VrijBerichtPartij partij1 = new VrijBerichtPartij(vrijBericht, new Partij("test1", "000123"));
        final VrijBerichtPartij partij2 = new VrijBerichtPartij(vrijBericht, new Partij("test2", "000456"));
        final List<VrijBerichtPartij> partijList = asList(partij1, partij2);
        ReflectionTestUtils.setField(vrijBericht, "vrijBerichtPartijen", partijList);

        serializer.serialize(vrijBericht, generator, null);

        generator.close();
        final String result = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/json/vrijbericht_lijst_2partijen.json"))).lines()
                .parallel().collect(Collectors.joining("\n"));
        assertThat(JsonUtil.jsonEquals(writer.toString(), result), is(true));
    }

    @Test
    public void testSerializeInkomend() throws Exception {
        final VrijBericht vrijBericht = new VrijBericht(SoortBerichtVrijBericht.VERWERK_VRIJ_BERICHT, soortVrijBericht,
                DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(ZonedDateTime.of(2017, 2, 21, 13, 0, 5, 0, ZoneId.of("UTC"))), "Inhoud vrij bericht", false);
        final VrijBerichtPartij partij = new VrijBerichtPartij(vrijBericht, new Partij("test", "000123"));
        final List<VrijBerichtPartij> partijList = singletonList(partij);
        ReflectionTestUtils.setField(vrijBericht, "vrijBerichtPartijen", partijList);

        serializer.serialize(vrijBericht, generator, null);

        generator.close();
        final String result = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/json/vrijbericht_inkomend.json"))).lines()
                .parallel().collect(Collectors.joining("\n"));
        assertThat(JsonUtil.jsonEquals(writer.toString(), result), is(true));
    }

}