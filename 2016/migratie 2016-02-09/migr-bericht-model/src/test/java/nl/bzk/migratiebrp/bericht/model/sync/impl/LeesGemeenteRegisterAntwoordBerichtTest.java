/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import java.io.IOException;
import java.math.BigInteger;

import junit.framework.Assert;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.MessageIdGenerator;
import nl.bzk.migratiebrp.bericht.model.sync.generated.GemeenteRegisterType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.GemeenteType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.LeesGemeenteRegisterAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;

import org.junit.Test;

public class LeesGemeenteRegisterAntwoordBerichtTest extends AbstractSyncBerichtTest {

    @Test
    public void testOk() throws BerichtInhoudException, ClassNotFoundException, IOException {
        final LeesGemeenteRegisterAntwoordBericht bericht = new LeesGemeenteRegisterAntwoordBericht(maakLeesGemeenteRegisterAntwoordType());
        bericht.setMessageId(MessageIdGenerator.generateId());

        controleerFormatParse(bericht);
        controleerSerialization(bericht);

        Assert.assertEquals("LeesGemeenteRegisterAntwoord", bericht.getBerichtType());
        Assert.assertEquals(StatusType.OK, bericht.getStatus());
        Assert.assertNull(bericht.getStartCyclus());
        Assert.assertNotNull(bericht.getGemeenteRegister());
        Assert.assertEquals(2, bericht.getGemeenteRegister().geefAlleGemeenten().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOngeldigeDatum() throws BerichtInhoudException, ClassNotFoundException, IOException {
        final LeesGemeenteRegisterAntwoordType type = new LeesGemeenteRegisterAntwoordType();
        type.setStatus(StatusType.OK);
        type.setGemeenteRegister(new GemeenteRegisterType());
        type.getGemeenteRegister().getGemeente().add(maakGemeenteType("0001", "580001", BigInteger.valueOf(0)));

        final LeesGemeenteRegisterAntwoordBericht bericht = new LeesGemeenteRegisterAntwoordBericht(type);
        bericht.setMessageId(MessageIdGenerator.generateId());
        bericht.getGemeenteRegister();
    }

    @Test
    public void testLeeg() throws BerichtInhoudException, ClassNotFoundException, IOException {
        final LeesGemeenteRegisterAntwoordBericht bericht = new LeesGemeenteRegisterAntwoordBericht();
        bericht.setMessageId(MessageIdGenerator.generateId());
        bericht.setStatus(StatusType.OK);

        Assert.assertEquals("LeesGemeenteRegisterAntwoord", bericht.getBerichtType());
        Assert.assertEquals(StatusType.OK, bericht.getStatus());
        Assert.assertNull(bericht.getStartCyclus());
        Assert.assertNotNull(bericht.getGemeenteRegister());
        Assert.assertEquals(0, bericht.getGemeenteRegister().geefAlleGemeenten().size());
    }

    private LeesGemeenteRegisterAntwoordType maakLeesGemeenteRegisterAntwoordType() {
        final LeesGemeenteRegisterAntwoordType type = new LeesGemeenteRegisterAntwoordType();
        type.setStatus(StatusType.OK);
        type.setGemeenteRegister(new GemeenteRegisterType());
        type.getGemeenteRegister().getGemeente().add(maakGemeenteType("0001", "580001", BigInteger.valueOf(20090101L)));
        type.getGemeenteRegister().getGemeente().add(maakGemeenteType("0002", "580002", null));

        return type;
    }

    private GemeenteType maakGemeenteType(final String gemeenteCode, final String partijCode, final BigInteger datumOvergangNaarBrp) {
        final GemeenteType gemeente = new GemeenteType();
        gemeente.setGemeenteCode(gemeenteCode);
        gemeente.setPartijCode(partijCode);
        gemeente.setDatumBrp(datumOvergangNaarBrp == null ? null : datumOvergangNaarBrp);
        return gemeente;
    }

}
