/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Onderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonIndicatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonIndicatieHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortIndicatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapperImpl;
import org.junit.Test;

public class BrpBijzondereVerblijfsrechtelijkePositieMapperTest extends BrpAbstractTest {

    private final BrpOnderzoekMapper brpOnderzoekMapper = new BrpOnderzoekMapperImpl(new ArrayList<Onderzoek>());

    @Inject
    private BrpBijzondereVerblijfsrechtelijkePositieIndicatieMapper mapper;

    @Test
    public void testMapInhoudPersoonBijzondereVerblijfsrechtelijkePositieHistorie() {
        final PersoonIndicatieHistorie historie =
                new PersoonIndicatieHistorie(new PersoonIndicatie(
                    new Persoon(SoortPersoon.INGESCHREVENE),
                    SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE), true);

        final BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud result = mapper.mapInhoud(historie, brpOnderzoekMapper);

        assertNotNull(result);
        assertTrue(result.heeftIndicatie());
    }
}
