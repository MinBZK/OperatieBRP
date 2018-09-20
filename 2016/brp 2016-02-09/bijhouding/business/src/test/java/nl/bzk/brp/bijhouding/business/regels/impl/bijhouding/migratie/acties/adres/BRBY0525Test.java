/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.migratie.acties.adres;

import java.util.List;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.bijhouding.business.testconfig.AttribuutAdministratieTestConfig;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.bericht.kern.HandelingCorrectieAdresBericht;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.model.bijhouding.RegistreerVerhuizingBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonAdresHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonAdresHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AttribuutAdministratieTestConfig.class })
public class BRBY0525Test {

    private final BRBY0525 bedrijfsregel = new BRBY0525();

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0525, bedrijfsregel.getRegel());
    }

    @Test
    public void testHistorieBevatGeenIncosistenties() {
        final BijhoudingBerichtContext berichtContext = maakBerichtContext();
        final PersoonHisVolledig rootObject = buildBestaandPersoon(false, LandGebiedCodeAttribuut.NL_LAND_CODE_SHORT);

        final List<BerichtIdentificeerbaar> objectenDieDeRegelOvertreden = bedrijfsregel
            .voerRegelUit(maakBericht(), rootObject, berichtContext.getTijdstipVerwerking());
        Assert.assertEquals(0, objectenDieDeRegelOvertreden.size());
    }

    @Test
    public void testHistorieBevatIncosistenties() {
        final BijhoudingsBericht bericht = maakBericht();
        final BijhoudingBerichtContext berichtContext = maakBerichtContext();
        final PersoonHisVolledig rootObject = buildBestaandPersoon(true, LandGebiedCodeAttribuut.NL_LAND_CODE_SHORT);

        final List<BerichtIdentificeerbaar> objectenDieDeRegelOvertreden = bedrijfsregel
            .voerRegelUit(bericht, rootObject, berichtContext.getTijdstipVerwerking());
        Assert.assertEquals(1, objectenDieDeRegelOvertreden.size());
        Assert.assertEquals(bericht.getAdministratieveHandeling(), objectenDieDeRegelOvertreden.get(0));
    }

    @Test
    public void testHistorieBevatIncosistentiesMaarGeenNederlandsAdres() {
        final BijhoudingBerichtContext berichtContext = maakBerichtContext();
        final PersoonHisVolledig rootObject = buildBestaandPersoon(true, StatischeObjecttypeBuilder.LAND_BELGIE.getWaarde().getCode().getWaarde());

        final List<BerichtIdentificeerbaar> objectenDieDeRegelOvertreden = bedrijfsregel
            .voerRegelUit(maakBericht(), rootObject, berichtContext.getTijdstipVerwerking());
        Assert.assertEquals(0, objectenDieDeRegelOvertreden.size());
    }

    private PersoonHisVolledig buildBestaandPersoon(final boolean afwijkend, final Short landCode) {
        Integer datumEindeAdres2 = null;
        if (afwijkend) {
            datumEindeAdres2 = 20120101;
        }
        final PersoonHisVolledigImpl persoon = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        final PersoonAdresHisVolledigImpl adres = new PersoonAdresHisVolledigImplBuilder(persoon)
            .nieuwStandaardRecord(20100101, null, 20100101)
            .datumAanvangAdreshouding(20100101)
            .landGebied(landCode)
            .eindeRecord()
            .nieuwStandaardRecord(20110101, datumEindeAdres2, 20110101)
            .datumAanvangAdreshouding(20110101)
            .landGebied(landCode)
            .eindeRecord()
            .build();
        persoon.getAdressen().add(adres);
        return persoon;
    }

    private BijhoudingsBericht maakBericht() {
        final BijhoudingsBericht bericht = new RegistreerVerhuizingBericht();
        bericht.getStandaard().setAdministratieveHandeling(new HandelingCorrectieAdresBericht());
        return bericht;
    }

    private BijhoudingBerichtContext maakBerichtContext() {
        final BijhoudingBerichtContext berichtContext = new BijhoudingBerichtContext(new BerichtenIds(1L, 2L), null, null, null);
        ReflectionTestUtils.setField(berichtContext, "tijdstipVerwerking", new DatumAttribuut(20110101).toDate());
        return berichtContext;
    }
}
