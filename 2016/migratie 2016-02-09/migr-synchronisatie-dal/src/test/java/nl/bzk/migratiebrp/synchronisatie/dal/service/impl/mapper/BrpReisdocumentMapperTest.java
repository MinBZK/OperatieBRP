/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import java.util.ArrayList;
import javax.inject.Inject;
import junit.framework.Assert;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingInhoudingOfVermissingReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpReisdocumentAutoriteitVanAfgifteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortNederlandsReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpReisdocumentInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AanduidingInhoudingOfVermissingReisdocument;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Onderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonReisdocument;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonReisdocumentHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortNederlandsReisdocument;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapperImpl;
import org.junit.Test;

public class BrpReisdocumentMapperTest extends BrpAbstractTest {

    private final BrpOnderzoekMapper brpOnderzoekMapper = new BrpOnderzoekMapperImpl(new ArrayList<Onderzoek>());

    @Inject
    private BrpReisdocumentMapper.BrpReisdocumentInhoudMapper mapper;

    @Test
    public void testMapInhoud() {
        final PersoonReisdocumentHistorie historie =
                new PersoonReisdocumentHistorie(
                    20000101,
                    20000101,
                    20170601,
                    "1494-XSD-123",
                    "0518",
                    new PersoonReisdocument(new Persoon(SoortPersoon.INGESCHREVENE), new SoortNederlandsReisdocument("PP", "Paspoort")));
        historie.setDatumInhoudingOfVermissing(20120403);
        historie.setAanduidingInhoudingOfVermissingReisdocument(new AanduidingInhoudingOfVermissingReisdocument('I', "Ingehouden, ingeleverd"));

        final BrpReisdocumentInhoud result = mapper.mapInhoud(historie, brpOnderzoekMapper);

        Assert.assertNotNull(result);
        Assert.assertEquals(new BrpSoortNederlandsReisdocumentCode("PP"), result.getSoort());
        Assert.assertEquals(new BrpString("1494-XSD-123", null), result.getNummer());
        Assert.assertEquals(new BrpDatum(20000101, null), result.getDatumIngangDocument());
        Assert.assertEquals(new BrpDatum(20000101, null), result.getDatumUitgifte());
        Assert.assertEquals(new BrpReisdocumentAutoriteitVanAfgifteCode("0518"), result.getAutoriteitVanAfgifte());
        Assert.assertEquals(new BrpDatum(20170601, null), result.getDatumEindeDocument());
        Assert.assertEquals(new BrpDatum(20120403, null), result.getDatumInhoudingOfVermissing());
        Assert.assertEquals(new BrpAanduidingInhoudingOfVermissingReisdocumentCode('I'), result.getAanduidingInhoudingOfVermissing());
    }
}
