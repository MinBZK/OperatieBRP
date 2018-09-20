/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import nl.bzk.brp.model.hisvolledig.SerialisatieExceptie;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.serialisatie.persoon.PersoonHisVolledigSmileSerializer;
import nl.bzk.brp.serialisatie.persoon.PersoonHisVolledigStringSerializer;
import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;


public class JacksonJsonSerializerPersoonHisVolledigTest {

    private static final JacksonJsonSerializer<PersoonHisVolledigImpl> SERIALIZER =
        new PersoonHisVolledigStringSerializer();

    private static final String JSON_DATA = "{}";

    @Test(expected = SerialisatieExceptie.class)
    public void errorBijDeserialiserenOnbekendeStringMetStringSerializer() throws IOException {
        final String onbekendeString = ":)\\012\\003\\372";

        new PersoonHisVolledigSmileSerializer().deserialiseer(onbekendeString.getBytes());
    }

    @Test(expected = SerialisatieExceptie.class)
    public void errorBijHetDeserialiserenJsonStringInSmileSerializer() throws IOException {
        new PersoonHisVolledigSmileSerializer().deserialiseer(JSON_DATA.getBytes());
    }

    @Test
    @Ignore // het nut van deze test ontgaat ons; graag commentaar erbij als hij weer wordt aangezet
    public void kanLegeJsonObjectDeserializeren() throws IOException {
        // when
        final PersoonHisVolledigImpl persoon = SERIALIZER.deserialiseer(JSON_DATA.getBytes());

        // then
        assertThat(persoon, not(nullValue()));
        assertThat(persoon.getID(), nullValue());
    }

    @Test
    public void kanEenVolledigeJsonStringDeserializeren() throws IOException {
        // given
        final Resource resource = new ClassPathResource("/data/persoonvolledig-14.json");
        final byte[] data = IOUtils.toByteArray(resource.getInputStream());

        // when
        final PersoonHisVolledigImpl persoon = SERIALIZER.deserialiseer(data);

        // then
        assertThat(persoon, not(nullValue()));
        assertThat(persoon.getID(), is(14));
        assertThat(persoon.getAdressen().size(), is(1));

        assertThat(persoon.getPersoonGeboorteHistorie().getHistorie().iterator().next().getVerantwoordingInhoud(),
            notNullValue());
        assertThat(persoon.getPersoonGeslachtsaanduidingHistorie().getHistorie().iterator().next()
            .getVerantwoordingInhoud(), notNullValue());
    }

}
