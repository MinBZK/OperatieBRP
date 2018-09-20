/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.serializatie;

import nl.bzk.brp.model.operationeel.kern.PersoonVolledig;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tests voor {@link JacksonSmileSerializer}.
 */
public class JacksonSmileSerializerTest {
    Logger log = LoggerFactory.getLogger(JacksonSmileSerializerTest.class);

    private JacksonSmileSerializer serializer = new JacksonSmileSerializer();

    @Test
    public void doTest() throws Exception {
        // given
        PersoonVolledig persoon = new PersoonVolledig(1);

        // when
        byte[] bytes = serializer.serializeer(persoon);

        // then
        Assert.assertEquals(18, bytes.length);
    }
}
