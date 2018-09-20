/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.autorisatie;

import java.util.Set;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpPartij;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpPartijInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PartijHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.util.TimestampUtil;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;

public class PartijMapperTest extends AbstractBrpNaarEntityMapperTest {

    @InjectMocks
    private PartijMapper subject;

    // TODO BRM44
    // @Mock
    // private Abonnement abonnement;

    @Test
    public void test() {

        // Input
        final BrpPartij input = maak(dynamischeStamtabelRepository);

        // Execute
        final Partij result = subject.mapVanMigratie(input);

        // A-laag
        Assert.assertNotNull(result);
        Assert.assertEquals("Partij", result.getNaam());
        // TODO BMR44
        Assert.assertNull(result.getSoortPartij());
        Assert.assertEquals(876543, result.getCode());
        Assert.assertEquals(Integer.valueOf(19800101), result.getDatumIngang());
        Assert.assertEquals(Integer.valueOf(19810101), result.getDatumEinde());
        Assert.assertFalse(result.isIndicatieVerstrekkingsbeperkingMogelijk());

        // C-laag
        final Set<PartijHistorie> historieSet = result.getHisPartijen();
        Assert.assertEquals(1, historieSet.size());
        final PartijHistorie historie = historieSet.iterator().next();

        Assert.assertEquals(Integer.valueOf(19800101).intValue(), historie.getDatumIngang());
        Assert.assertEquals(Integer.valueOf(19810101), historie.getDatumEinde());
        Assert.assertFalse(historie.isIndicatieVerstrekkingsbeperkingMogelijk());
        Assert.assertEquals(TimestampUtil.maakTimestamp(1990, 0, 2, 2, 0, 0), historie.getDatumTijdRegistratie());
    }

    public static BrpPartij maak(final DynamischeStamtabelRepository dynamischeStamtabelRepositoryMock) {
        return new BrpPartij(null, "Partij", new BrpPartijCode(876543), stapel(new BrpPartijInhoud(new BrpDatum(19800101, null), new BrpDatum(
            19810101,
            null), false, true)));

    }
}
