/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import java.math.BigDecimal;

import javax.inject.Inject;

import junit.framework.Assert;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpReisdocumentAutoriteitVanAfgifte;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpReisdocumentRedenOntbreken;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpReisdocumentSoort;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpReisdocumentInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.AutoriteitVanAfgifteReisdocument;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonReisdocument;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonReisdocumentHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.RedenVervallenReisdocument;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.SoortNederlandsReisdocument;

import org.junit.Ignore;
import org.junit.Test;

public class BrpReisdocumentMapperTest extends BrpAbstractTest {

    @Inject
    private BrpReisdocumentMapper.BrpReisdocumentInhoudMapper mapper;

    @Ignore("Ongeldig totdat datum ingang document in het BRP database model is opgenomen")
    @Test
    public void testMapInhoud() {
        final PersoonReisdocumentHistorie historie = new PersoonReisdocumentHistorie();
        historie.setPersoonReisdocument(new PersoonReisdocument());
        historie.getPersoonReisdocument().setSoortNederlandsReisdocument(new SoortNederlandsReisdocument());
        historie.getPersoonReisdocument().getSoortNederlandsReisdocument().setCode("PP");
        historie.setNummer("1494-XSD-123");
        // historie.setDatumIngangDocument
        historie.setDatumUitgifte(new BigDecimal("20000101"));
        historie.setAutoriteitVanAfgifteReisdocument(new AutoriteitVanAfgifteReisdocument());
        historie.getAutoriteitVanAfgifteReisdocument().setCode("0518");
        historie.setDatumVoorzieneEindeGeldigheid(new BigDecimal("20170601"));
        historie.setDatumInhoudingVermissing(new BigDecimal("20120403"));
        historie.setRedenVervallenReisdocument(new RedenVervallenReisdocument());
        historie.getRedenVervallenReisdocument().setCode("Vermissing-032");
        historie.setLengteHouder(new BigDecimal("178"));

        final BrpReisdocumentInhoud result = mapper.mapInhoud(historie);

        Assert.assertNotNull(result);
        Assert.assertEquals(new BrpReisdocumentSoort("PP"), result.getSoort());
        Assert.assertEquals("1494-XSD-123", result.getNummer());
        // datumIngang
        Assert.assertEquals(new BrpDatum(20000101), result.getDatumUitgifte());
        Assert.assertEquals(new BrpReisdocumentAutoriteitVanAfgifte("0518"), result.getAutoriteitVanAfgifte());
        Assert.assertEquals(new BrpDatum(20170601), result.getDatumVoorzieneEindeGeldigheid());
        Assert.assertEquals(new BrpDatum(20120403), result.getDatumInhoudingVermissing());
        Assert.assertEquals(new BrpReisdocumentRedenOntbreken("Vermissing-032"), result.getRedenOntbreken());
        Assert.assertEquals(Integer.valueOf(178), result.getLengteHouder());
    }
}
