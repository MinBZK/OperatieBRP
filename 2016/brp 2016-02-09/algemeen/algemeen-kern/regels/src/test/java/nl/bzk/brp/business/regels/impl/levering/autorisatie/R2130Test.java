/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels.impl.levering.autorisatie;

import nl.bzk.brp.business.regels.context.AutorisatieRegelContext;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Assert;
import org.junit.Test;


public class R2130Test {

    private final R2130 regel = new R2130();

    private final PersoonView persoon = maakHuidigeSituatie();

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.R2130, regel.getRegel());
    }

    @Test
    public void testGetContextType() {
        Assert.assertEquals(AutorisatieRegelContext.class, regel.getContextType());
    }

    @Test
    public void isGeauthoriseerdVoorDeDienstAfnemerindicatiePlaatsen() {
        final Leveringsautorisatie la = TestLeveringsautorisatieBuilder.metDienst(SoortDienst.PLAATSEN_AFNEMERINDICATIE);
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie(la).maak();
        final AutorisatieRegelContext regelContext =
            new AutorisatieRegelContext(toegangLeveringsautorisatie, la.geefDiensten().iterator().next(), persoon,
                SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE);

        final boolean resultaat = regel.valideer(regelContext);

        Assert.assertTrue(resultaat);
    }

    @Test
    public void isGeauthoriseerdVoorDeDienstAfnemderindicatieVerwijderen() {
        final Leveringsautorisatie la = TestLeveringsautorisatieBuilder.metDienst(SoortDienst.VERWIJDEREN_AFNEMERINDICATIE);
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie(la).maak();
        final AutorisatieRegelContext regelContext =
            new AutorisatieRegelContext(toegangLeveringsautorisatie, la.geefDiensten().iterator().next(), persoon,
                SoortAdministratieveHandeling.VERWIJDERING_AFNEMERINDICATIE);

        final boolean resultaat = regel.valideer(regelContext);

        Assert.assertTrue(resultaat);
    }

    @Test
    public void isGeauthoriseerdVoorDeDienstSynchroniserenPersoon() {
        final Leveringsautorisatie la = TestLeveringsautorisatieBuilder.metDienst(SoortDienst.SYNCHRONISATIE_PERSOON);
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie(la).maak();
        final AutorisatieRegelContext regelContext =
            new AutorisatieRegelContext(toegangLeveringsautorisatie, la.geefDiensten().iterator().next(), persoon,
                SoortAdministratieveHandeling.SYNCHRONISATIE_PERSOON);

        final boolean resultaat = regel.valideer(regelContext);

        Assert.assertTrue(resultaat);
    }

    @Test
    public void isNietGeauthoriseerdVoorDeDienstAfnemerindicatiePlaatsen() {
        final Leveringsautorisatie la = TestLeveringsautorisatieBuilder.metDienst(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE);
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie(la).maak();
        final AutorisatieRegelContext regelContext =
            new AutorisatieRegelContext(toegangLeveringsautorisatie, la.geefDiensten().iterator().next(), persoon,
                SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE);

        final boolean resultaat = regel.valideer(regelContext);

        Assert.assertFalse(resultaat);
    }

    @Test
    public void isNietGeauthoriseerdVoorDeDienstAfnemerindicatieVerwijderen() {
        final Leveringsautorisatie la = TestLeveringsautorisatieBuilder.metDienst(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE);
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie(la).maak();
        final AutorisatieRegelContext regelContext =
            new AutorisatieRegelContext(toegangLeveringsautorisatie, la.geefDiensten().iterator().next(), persoon,
                SoortAdministratieveHandeling.VERWIJDERING_AFNEMERINDICATIE);
        final boolean resultaat = regel.valideer(regelContext);
        Assert.assertFalse(resultaat);
    }

    @Test
    public void isNietGeauthoriseerdVoorDeDienstSynchroniseerPersoon() {
        final Leveringsautorisatie la = TestLeveringsautorisatieBuilder.metDienst(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING);
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie(la).maak();
        final AutorisatieRegelContext regelContext =
            new AutorisatieRegelContext(toegangLeveringsautorisatie, la.geefDiensten().iterator().next(), persoon,
                SoortAdministratieveHandeling.SYNCHRONISATIE_PERSOON);

        final boolean resultaat = regel.valideer(regelContext);

        Assert.assertFalse(resultaat);
    }


    private PersoonView maakHuidigeSituatie() {
        // Ter test of de logging nu ook de BSN vanuit de huidige situatie vermeld.
        return new PersoonView(TestPersoonJohnnyJordaan.maak());
    }
}
