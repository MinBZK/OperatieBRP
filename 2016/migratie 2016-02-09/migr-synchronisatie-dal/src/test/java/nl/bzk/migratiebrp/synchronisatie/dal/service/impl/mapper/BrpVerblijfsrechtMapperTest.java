/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import java.util.ArrayList;

import javax.inject.Inject;

import junit.framework.Assert;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpVerblijfsrechtCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerblijfsrechtInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Onderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonVerblijfsrechtHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Verblijfsrecht;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapperImpl;

import org.junit.Test;

public class BrpVerblijfsrechtMapperTest extends BrpAbstractTest {

    private final BrpOnderzoekMapper brpOnderzoekMapper = new BrpOnderzoekMapperImpl(new ArrayList<Onderzoek>());

    @Inject
    private BrpVerblijfsrechtMapper mapper;

    @Test
    public void testMapInhoud() {
        final PersoonVerblijfsrechtHistorie historie =
                new PersoonVerblijfsrechtHistorie(
                    new Persoon(SoortPersoon.INGESCHREVENE),
                    new Verblijfsrecht((short) 9, "Art. 9 van de Vreemdelingenwet"),
                    20000202,
                    20000202);
        historie.setDatumVoorzienEindeVerblijfsrecht(20140202);

        final BrpVerblijfsrechtInhoud result = mapper.mapInhoud(historie, brpOnderzoekMapper);

        Assert.assertNotNull(result);
        Assert.assertEquals(new BrpVerblijfsrechtCode((short) 9), result.getAanduidingVerblijfsrechtCode());
        Assert.assertEquals(new BrpDatum(20000202, null), result.getDatumMededelingVerblijfsrecht());
        Assert.assertEquals(new BrpDatum(20140202, null), result.getDatumVoorzienEindeVerblijfsrecht());
    }
}
