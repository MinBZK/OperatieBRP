/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;

import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import uk.co.datumedge.hamcrest.json.SameJSONAs;

public class JacksonJsonSerializerTest {
    private final PersoonHisVolledigSerializer serializer = new JacksonJsonSerializer();

    private final String jsonData = "{}";

    @Test
    public void kanLegeJsonObjectDeserializeren() throws IOException {
        // when
        PersoonHisVolledig persoon = this.serializer.deserializeer(this.jsonData.getBytes());

        // then
        assertThat(persoon, not(nullValue()));
        assertThat(persoon.getID(), nullValue());
    }

    @Test
    public void kanEenVolledigeJsonStringDeserializeren() throws IOException {
        // given
        Resource resource = new ClassPathResource("/data/persoonvolledig-13.json");
        byte[] data = IOUtils.toByteArray(resource.getInputStream());

        // when
        PersoonHisVolledig persoon = this.serializer.deserializeer(data);

        // then
        assertThat(persoon, not(nullValue()));
        assertThat(persoon.getID(), is(13));
        assertThat(persoon.getAdressen().size(), is(1));
    }

    @Ignore
    @Test
    public void kanEenSerializatieRoundTripMaken() throws Exception {
        // given
        Resource resource = new ClassPathResource("/data/persoonvolledig-13.json");
        byte[] data = IOUtils.toByteArray(resource.getInputStream());

        PersoonHisVolledig persoon = this.serializer.deserializeer(data);

        // when
        byte[] newData = this.serializer.serializeer(persoon);

        // then - TODO fix test
        assertThat(data.length, not(equalTo(newData.length)));
        assertThat(new String(data), SameJSONAs.sameJSONAs(new String(newData)).allowingAnyArrayOrdering());
    }
}
