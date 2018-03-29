/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijHistorie;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.algemeenbrp.test.dal.TimestampUtil;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpPartij;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpPartijInhoud;
import org.junit.Test;
import org.mockito.InjectMocks;

public class PartijMapperTest extends AbstractBrpNaarEntityMapperTest {

    @InjectMocks
    private PartijMapper subject;

    @Test
    public void test() {

        // Input
        final BrpPartij input = maak(dynamischeStamtabelRepository);

        // Execute
        final Partij result = subject.mapVanMigratie(input);

        // A-laag
        assertNotNull(result);
        assertEquals("Partij", result.getNaam());
        assertNull(result.getSoortPartij());
        assertEquals("876543", result.getCode());

        // C-laag
        final Set<PartijHistorie> historieSet = result.getHisPartijen();
        assertEquals(1, historieSet.size());
        final PartijHistorie historie = historieSet.iterator().next();

        assertEquals(Integer.valueOf(19800101).intValue(), historie.getDatumIngang());
        assertEquals(Integer.valueOf(19810101), historie.getDatumEinde());
        assertFalse(historie.isIndicatieVerstrekkingsbeperkingMogelijk());
        assertEquals(TimestampUtil.maakTimestamp(1990, 0, 2, 2, 0, 0), historie.getDatumTijdRegistratie());
    }

    public static BrpPartij maak(final DynamischeStamtabelRepository dynamischeStamtabelRepositoryMock) {
        return new BrpPartij(null, "Partij", new BrpPartijCode("876543"),
                stapel(new BrpPartijInhoud(new BrpDatum(19800101, null), new BrpDatum(19810101, null), false, true)));

    }
}
