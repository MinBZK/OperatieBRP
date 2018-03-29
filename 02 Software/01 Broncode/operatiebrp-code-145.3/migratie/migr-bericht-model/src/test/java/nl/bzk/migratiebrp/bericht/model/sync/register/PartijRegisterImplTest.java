/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.register;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 * test.
 */
public class PartijRegisterImplTest {

    private static final String AMSTERDAM = "062601";
    private static final String DEN_HAAG = "059901";
    private static final String ROTTERDAM = "032301";
    private static final String LUTJEBROEK = "012301";
    private static final Date DATUM_OVERGANG_BRP = new Date();
    private static final Partij PARTIJ_AMSTERDAM = new Partij(AMSTERDAM, AMSTERDAM, DATUM_OVERGANG_BRP, Collections.emptyList());
    private static final Partij PARTIJ_DEN_HAAG = new Partij(DEN_HAAG, DEN_HAAG, null, Collections.emptyList());
    private static final Partij PARTIJ_ROTTERDAM = new Partij(ROTTERDAM, ROTTERDAM, null, Collections.emptyList());
    private static final List<Partij> ALLE_PARTIJEN = Arrays.asList(PARTIJ_AMSTERDAM, PARTIJ_DEN_HAAG, PARTIJ_ROTTERDAM);

    @Test
    public void testPartijRegister() {
        PartijRegister partijRegister = new PartijRegisterImpl(ALLE_PARTIJEN);
        Assert.assertEquals(ALLE_PARTIJEN, partijRegister.geefAllePartijen());
        Assert.assertEquals(PARTIJ_AMSTERDAM, partijRegister.zoekPartijOpPartijCode(AMSTERDAM));
        Assert.assertNull(partijRegister.zoekPartijOpPartijCode(LUTJEBROEK));
        Assert.assertNull(partijRegister.zoekPartijOpPartijCode(null));

    }
}