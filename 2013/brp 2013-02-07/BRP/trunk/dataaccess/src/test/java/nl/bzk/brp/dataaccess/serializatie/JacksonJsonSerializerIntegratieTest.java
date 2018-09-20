/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.serializatie;

import static org.hamcrest.Matchers.isOneOf;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.dataaccess.repository.PersoonHisVolledigRepository;
import nl.bzk.brp.model.hisvolledig.JacksonJsonSerializer;
import nl.bzk.brp.model.hisvolledig.PersoonHisVolledigSerializer;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonNationaliteitHisVolledig;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * tests voor {@link JacksonJsonSerializer}.
 */
public class JacksonJsonSerializerIntegratieTest extends AbstractRepositoryTestCase {
    Logger log = LoggerFactory.getLogger(JacksonJsonSerializerIntegratieTest.class);

    private PersoonHisVolledigSerializer serializer = new JacksonJsonSerializer();

    @Inject
    protected PersoonHisVolledigRepository repository;

    @Override
    protected List<String> getInitieleDataBestanden() {
        return Arrays.asList(
                "/data/stamgegevensStatisch.xml",
                "/data/blob/statisch.xml",
                "/data/blob/dataset.xml");
    }

    @Test
    @Ignore("heeft geen (unit)test waarde")
    public void leesAllePersonenEnPrintAlsJSONDemo() throws Exception {

        StringBuilder sb = new StringBuilder("{\"personen\":[");

        for (int i = 1; i <= 20; i++) {
            PersoonHisVolledig persoonHisVolledig = repository.haalPersoonOp(i);

            byte[] data = serializer.serializeer(persoonHisVolledig);
            if (i > 1) {
                sb.append(',');
            }

            sb.append(new String(data));
        }
        sb.append("]}");

        log.info("{}", sb);
    }

    @Test
    public void serializatieRoundtripIsSuccesvol() throws Exception {
        // given
        PersoonHisVolledig persoon = repository.haalPersoonOp(13);
        byte[] data = serializer.serializeer(persoon);
        log.info("JSON String: \n-----------------------\n{}\n----------------------", new String(data));

        // when
        PersoonHisVolledig gelezenPersoon = serializer.deserializeer(data);

        // then
        assertEquals("Nederlandse",
                     persoon.getNationaliteiten().iterator().next().getNationaliteit().getNaam().getWaarde());
        assertEquals("Roos",
                     persoon.getGeslachtsnaamcomponenten().iterator().next().getHisPersoonGeslachtsnaamcomponentLijst()
                             .iterator().next().getNaam().getWaarde());

        String voornaam =
                gelezenPersoon.getVoornamen().iterator().next().getHisPersoonVoornaamLijst().iterator().next().getNaam()
                        .getWaarde();
        assertThat(voornaam, isOneOf("Johanna", "Isabella", "Hendrika", "Gerarda"));

        assertEquals(1, gelezenPersoon.getAdressen().size());
        assertEquals(3, gelezenPersoon.getAdressen().iterator().next().getHisPersoonAdresLijst().size());

        assertEquals(2, gelezenPersoon.getIndicaties().size());
    }

    @Test
    public void serializatieBevatGeenActies() throws Exception {
        PersoonHisVolledig persoon = repository.haalPersoonOp(19);
        byte[] data = serializer.serializeer(persoon);

        // when
        PersoonHisVolledig gelezenPersoon = serializer.deserializeer(data);

        // then
        PersoonNationaliteitHisVolledig nation = gelezenPersoon.getNationaliteiten().iterator().next();
        assertThat(nation.getHisPersoonNationaliteitLijst().iterator().next().getMaterieleHistorie().getActieInhoud(),
                   nullValue());
    }
}
