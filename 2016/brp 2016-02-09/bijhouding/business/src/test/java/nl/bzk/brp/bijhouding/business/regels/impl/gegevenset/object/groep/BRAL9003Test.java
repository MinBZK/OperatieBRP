/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.object.groep;

import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.util.ActieBerichtBuilder;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test voor de {@link nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.object.groep.BRAL9003} klasse en bijbehorende bedrijfsregel.
 */
public class BRAL9003Test {

    private final BRAL9003 bral9003 = new BRAL9003();

    private PersoonView persoonView = null;

    @Before
    public void init() {
        final PersoonHisVolledigImplBuilder persoonHisVolledigImplBuilder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        persoonHisVolledigImplBuilder.nieuwBijhoudingRecord(20120505, null, 20000101)
            .nadereBijhoudingsaard(NadereBijhoudingsaard.OVERLEDEN)
            .eindeRecord();
        final PersoonHisVolledig persoon = persoonHisVolledigImplBuilder.build();
        persoonView = new PersoonView(persoon);
    }

    @Test
    public void testHuidigeSituatieLeeg() {
        final PersoonBericht nieuweSituatie = new PersoonBericht();
        final Actie actie = ActieBerichtBuilder.bouwNieuweActie(SoortActie.REGISTRATIE_NATIONALITEIT)
            .setDatumAanvang(new DatumEvtDeelsOnbekendAttribuut(20150101)).getActie();

        final List<BerichtEntiteit> fouteObjecten = bral9003.voerRegelUit(null, nieuweSituatie, actie, null);

        Assert.assertEquals(0, fouteObjecten.size());
    }

    @Test
    public void testWijzigingDatumVoorNadereBijhoudingAardDatum() {
        final PersoonBericht nieuweSituatie = new PersoonBericht();
        final Actie actie = ActieBerichtBuilder.bouwNieuweActie(SoortActie.REGISTRATIE_NATIONALITEIT)
            .setDatumAanvang(new DatumEvtDeelsOnbekendAttribuut(20120504)).getActie();

        final List<BerichtEntiteit> fouteObjecten = bral9003.voerRegelUit(persoonView, nieuweSituatie, actie, null);

        Assert.assertEquals(0, fouteObjecten.size());
    }

    @Test
    public void testWijzigingDatumOpNadereBijhoudingAardDatum() {
        final PersoonBericht nieuweSituatie = new PersoonBericht();
        final Actie actie = ActieBerichtBuilder.bouwNieuweActie(SoortActie.REGISTRATIE_NATIONALITEIT)
            .setDatumAanvang(20120505).getActie();

        final List<BerichtEntiteit> fouteObjecten = bral9003.voerRegelUit(persoonView, nieuweSituatie, actie, null);

        Assert.assertEquals(1, fouteObjecten.size());
        Assert.assertEquals(nieuweSituatie, fouteObjecten.get(0));
    }

    @Test
    public void testWijzigingDatumNaNadereBijhoudingAardDatum() {
        final PersoonBericht nieuweSituatie = new PersoonBericht();
        final Actie actie = ActieBerichtBuilder.bouwNieuweActie(SoortActie.REGISTRATIE_NATIONALITEIT)
            .setDatumAanvang(new DatumEvtDeelsOnbekendAttribuut(20120506)).getActie();

        final List<BerichtEntiteit> fouteObjecten = bral9003.voerRegelUit(persoonView, nieuweSituatie, actie, null);

        Assert.assertEquals(1, fouteObjecten.size());
        Assert.assertEquals(nieuweSituatie, fouteObjecten.get(0));
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals("BRAL9003", bral9003.getRegel().getCode());
    }
}
