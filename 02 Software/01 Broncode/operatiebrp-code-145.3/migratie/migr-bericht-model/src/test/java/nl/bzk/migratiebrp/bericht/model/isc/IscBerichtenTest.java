/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.isc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.MessageIdGenerator;
import nl.bzk.migratiebrp.bericht.model.isc.factory.IscBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.isc.impl.OngeldigBericht;
import nl.bzk.migratiebrp.bericht.model.isc.impl.Uc811Bericht;
import nl.bzk.migratiebrp.bericht.model.isc.impl.Uc812Bericht;
import org.junit.Assert;
import org.junit.Test;

public class IscBerichtenTest {

    private final IscBerichtFactory factory = IscBerichtFactory.SINGLETON;

    @Test
    public void testUc811Bericht() throws BerichtInhoudException, ClassNotFoundException, IOException {
        final Uc811Bericht input = new Uc811Bericht();
        input.setGemeenteCode("0001");
        input.setANummer(1234567890L);
        testFormatAndParseBericht(input, false);
    }

    @Test
    public void testUc811BerichtConversie() throws BerichtInhoudException, ClassNotFoundException, IOException {
        final Uc811Bericht input = new Uc811Bericht();
        input.setANummer(null);
        Assert.assertNull(input.getAnummer());
    }

    @Test
    public void testOngeldigBericht() throws BerichtInhoudException, ClassNotFoundException, IOException {
        final OngeldigBericht input = new OngeldigBericht("BERICHT", "MELDING");
        testFormatAndParseBericht(input, true);

        final OngeldigBericht input2 = new OngeldigBericht("BERICHT", "MELDING");
        input2.setMessageId(input.getMessageId());
        input2.setCorrelationId(input.getCorrelationId());
        Assert.assertEquals(input, input2);

        final OngeldigBericht input3 = new OngeldigBericht("ANDERS", "MELDING");
        Assert.assertFalse(input.equals(input3));
    }

    @Test
    public void testUc812Bericht() throws BerichtInhoudException, ClassNotFoundException, IOException {
        final Uc812Bericht input = new Uc812Bericht();
        input.setBulkSynchronisatievraag("0001,1234567890\n0001,1234567891");
        testFormatAndParseBericht(input, false);
    }

    private void testFormatAndParseBericht(final IscBericht bericht, final boolean skipParse) throws BerichtInhoudException, IOException,
            ClassNotFoundException {

        bericht.setMessageId(MessageIdGenerator.generateId());

        final String formatted = bericht.format();

        Assert.assertNotNull(formatted);

        if (!skipParse) {
            final IscBericht parsed = factory.getBericht(formatted);

            Assert.assertNotNull(parsed);

            parsed.setMessageId(bericht.getMessageId());
            parsed.setCorrelationId(bericht.getCorrelationId());

            Assert.assertEquals(bericht, parsed);
        }

        // Serialize
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(bericht);

        final byte[] data = baos.toByteArray();

        final ByteArrayInputStream bais = new ByteArrayInputStream(data);
        final ObjectInputStream ois = new ObjectInputStream(bais);

        final Object deserialized = ois.readObject();
        Assert.assertEquals(bericht, deserialized);

        Assert.assertEquals(bericht.getMessageId(), ((IscBericht) deserialized).getMessageId());
    }

}
