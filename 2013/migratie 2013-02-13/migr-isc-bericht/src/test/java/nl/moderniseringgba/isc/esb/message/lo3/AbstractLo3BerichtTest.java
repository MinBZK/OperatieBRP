/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import nl.moderniseringgba.isc.esb.message.lo3.impl.Ib01Bericht;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper;

import org.junit.Assert;

public class AbstractLo3BerichtTest {

    private final Lo3BerichtFactory factory = new Lo3BerichtFactory();

    protected void testFormatAndParseBericht(final Lo3Bericht bericht) throws Exception {
        System.out.println("Bericht:           " + bericht);

        final String formatted = bericht.format();
        System.out.println("Bericht.formatted: " + formatted);
        Assert.assertNotNull(formatted);

        final Lo3Bericht parsed = factory.getBericht(formatted);
        Assert.assertNotNull(parsed);

        parsed.setMessageId(bericht.getMessageId());
        parsed.setCorrelationId(bericht.getCorrelationId());
        parsed.setBronGemeente(bericht.getBronGemeente());
        parsed.setDoelGemeente(bericht.getDoelGemeente());
        System.out.println("Bericht.parsed:    " + parsed);

        if (bericht instanceof Ib01Bericht) {
            final StringBuilder sb = new StringBuilder();
            Lo3StapelHelper.vergelijkPersoonslijst(sb, ((Ib01Bericht) bericht).getLo3Persoonslijst(),
                    ((Ib01Bericht) parsed).getLo3Persoonslijst(), true);
            System.out.println(sb);
        }

        Assert.assertEquals(bericht, parsed);

        // TODO: aparte test

        // Serialize
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(parsed);

        final byte[] data = baos.toByteArray();

        final ByteArrayInputStream bais = new ByteArrayInputStream(data);
        final ObjectInputStream ois = new ObjectInputStream(bais);

        final Object deserialized = ois.readObject();
        Assert.assertEquals(parsed, deserialized);

        Assert.assertEquals(parsed.getMessageId(), ((Lo3Bericht) deserialized).getMessageId());
    }

}
