/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.serializatie;

import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

import nl.bzk.brp.model.hisvolledig.JacksonSmileSerializer;
import nl.bzk.brp.model.hisvolledig.PersoonHisVolledigSerializer;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tests voor {@link JacksonSmileSerializer}.
 */
public class JacksonSmileSerializerIntegratieTest extends JacksonJsonSerializerIntegratieTest {
    Logger log = LoggerFactory.getLogger(JacksonSmileSerializerIntegratieTest.class);

    private PersoonHisVolledigSerializer serializer = new JacksonSmileSerializer();

    @Test
    public void serializatieIsNaarByteArray() throws Exception {
        // given
        PersoonHisVolledig persoon = repository.haalPersoonOp(7);

        // when
        byte[] data = serializer.serializeer(persoon);

        // then
        assertThat(data.length, lessThan(3000));
    }
}
