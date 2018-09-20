/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.brp.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;

import nl.moderniseringgba.isc.esb.message.brp.BrpBericht;
import nl.moderniseringgba.isc.esb.message.brp.BrpBerichtFactory;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;

import org.junit.Assert;
import org.junit.Test;

public class WijzigingANummerSignaalBerichtTest {

    private final BrpBerichtFactory factory = BrpBerichtFactory.SINGLETON;

    @Test
    public void testFormatEnParse() throws Exception {

        final WijzigingANummerSignaalBericht bericht = new WijzigingANummerSignaalBericht();
        bericht.setBrpGemeente(new BrpGemeenteCode(BigDecimal.valueOf(1234L)));
        bericht.setOudANummer(1234567890L);
        bericht.setNieuwANummer(9876543210L);
        bericht.setDatumGeldigheid(new BrpDatum(20000101));

        final String formatted = bericht.format();
        System.out.println("Bericht.formatted: " + formatted);
        Assert.assertNotNull(formatted);

        final BrpBericht parsed = factory.getBericht(formatted);
        System.out.println("Bericht.parsed: " + parsed);
        controleerFormatEnParse(bericht, parsed);

        final WijzigingANummerSignaalBericht parsedBericht = (WijzigingANummerSignaalBericht) parsed;
        Assert.assertEquals(new BrpGemeenteCode(BigDecimal.valueOf(1234L)), parsedBericht.getBrpGemeente());
        Assert.assertEquals(Long.valueOf(1234567890L), parsedBericht.getOudANummer());
        Assert.assertEquals(Long.valueOf(9876543210L), parsedBericht.getNieuwANummer());
        Assert.assertEquals(new BrpDatum(20000101), parsedBericht.getDatumGeldigheid());
    }

    private void controleerFormatEnParse(final BrpBericht bericht, final BrpBericht parsed) throws IOException,
            ClassNotFoundException {
        Assert.assertNotNull(parsed);

        parsed.setMessageId(bericht.getMessageId());
        parsed.setCorrelationId(bericht.getCorrelationId());

        Assert.assertEquals(bericht, parsed);

        // Serialize
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(parsed);

        final byte[] data = baos.toByteArray();

        final ByteArrayInputStream bais = new ByteArrayInputStream(data);
        final ObjectInputStream ois = new ObjectInputStream(bais);

        final Object deserialized = ois.readObject();
        Assert.assertEquals(parsed, deserialized);

        Assert.assertEquals(parsed.getMessageId(), ((BrpBericht) deserialized).getMessageId());
    }

}
