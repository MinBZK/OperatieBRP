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
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpInschrijvingInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonInschrijvingHistorie;

import org.junit.Test;

public class BrpInschrijvingMapperTest extends BrpAbstractTest {

    @Inject
    private BrpInschrijvingMapper mapper;

    @Test
    public void testMapInhoud() {
        final PersoonInschrijvingHistorie historie = new PersoonInschrijvingHistorie();
        historie.setDatumInschrijving(new BigDecimal("19721205"));
        historie.setVersienummer(2003L);

        final BrpInschrijvingInhoud result = mapper.mapInhoud(historie);

        Assert.assertNotNull(result);
        Assert.assertEquals(new BrpDatum(19721205), result.getDatumInschrijving());
        Assert.assertEquals(new Integer(2003), result.getVersienummer());
        Assert.assertNull(result.getVorigAdministratienummer());
        Assert.assertNull(result.getVolgendAdministratienummer());
    }

    @Test
    public void testMapInhoudMetVorigeEnVolgende() {
        final PersoonInschrijvingHistorie historie = new PersoonInschrijvingHistorie();
        historie.setDatumInschrijving(new BigDecimal("19721205"));
        historie.setVersienummer(2003L);
        historie.setVorigePersoon(new Persoon());
        historie.getVorigePersoon().setAdministratienummer(new BigDecimal("1000000002"));
        historie.setVolgendePersoon(new Persoon());
        historie.getVolgendePersoon().setAdministratienummer(new BigDecimal("2233440001"));

        final BrpInschrijvingInhoud result = mapper.mapInhoud(historie);

        Assert.assertNotNull(result);
        Assert.assertEquals(new BrpDatum(19721205), result.getDatumInschrijving());
        Assert.assertEquals(new Integer(2003), result.getVersienummer());
        Assert.assertEquals(Long.valueOf(1000000002L), result.getVorigAdministratienummer());
        Assert.assertEquals(Long.valueOf(2233440001L), result.getVolgendAdministratienummer());
    }
}
