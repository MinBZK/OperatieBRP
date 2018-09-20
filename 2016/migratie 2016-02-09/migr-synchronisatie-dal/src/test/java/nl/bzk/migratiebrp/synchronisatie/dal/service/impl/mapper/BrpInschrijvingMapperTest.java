/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import javax.inject.Inject;
import junit.framework.Assert;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpInschrijvingInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Onderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonInschrijvingHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapperImpl;
import org.junit.Test;

public class BrpInschrijvingMapperTest extends BrpAbstractTest {

    private final BrpOnderzoekMapper brpOnderzoekMapper = new BrpOnderzoekMapperImpl(new ArrayList<Onderzoek>());

    public static final Timestamp TIMESTAMP = new Timestamp(BrpDatumTijd.fromDatum(20070102, null).getJavaDate().getTime());

    @Inject
    private BrpInschrijvingMapper mapper;

    @Test
    public void testMapInhoud() {
        final PersoonInschrijvingHistorie historie =
                new PersoonInschrijvingHistorie(new Persoon(SoortPersoon.INGESCHREVENE), 19721205, 2003L, TIMESTAMP);

        final BrpInschrijvingInhoud result = mapper.mapInhoud(historie, brpOnderzoekMapper);

        Assert.assertNotNull(result);
        Assert.assertEquals(new BrpDatum(19721205, null), result.getDatumInschrijving());
        Assert.assertEquals(new BrpLong(2003L, null), result.getVersienummer());
    }

    @Test
    public void testMapInhoudMetVorigeEnVolgende() {
        final PersoonInschrijvingHistorie historie =
                new PersoonInschrijvingHistorie(new Persoon(SoortPersoon.INGESCHREVENE), 19721205, 2003L, TIMESTAMP);

        final BrpInschrijvingInhoud result = mapper.mapInhoud(historie, brpOnderzoekMapper);

        Assert.assertNotNull(result);
        Assert.assertEquals(new BrpDatum(19721205, null), result.getDatumInschrijving());
        Assert.assertEquals(new BrpLong(2003L, null), result.getVersienummer());
    }
}
