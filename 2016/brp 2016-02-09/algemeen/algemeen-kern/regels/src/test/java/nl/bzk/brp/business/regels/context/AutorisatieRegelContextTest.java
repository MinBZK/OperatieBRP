/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels.context;

import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;

//FIXME DENNIS
public class AutorisatieRegelContextTest {

//    @Test
//    public void testGetLeveringautorisatie() {
//        final Leveringsautorisatie la = TestLeveringsautorisatieBuilder.maker().maak();
//        TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie(la).maak();
//        final Leveringinformatie li = new Leveringinformatie(l);
//        final AutorisatieRegelContext autorisatieRegelContext = new AutorisatieRegelContext(la, null);
//        final Leveringsautorisatie resultaat = autorisatieRegelContext.getLeveringsautorisatie();
//        Assert.assertEquals(la, resultaat);
//    }
//
//    @Test
//    public void testGetDiensten() {
//        final List<Dienst> diensten = new ArrayList<>();
//        final AutorisatieRegelContext autorisatieRegelContext = new AutorisatieRegelContext(null, diensten);
//        final Collection<Dienst> resultaat = autorisatieRegelContext.getDiensten();
//        Assert.assertEquals(diensten, resultaat);
//    }
//
//    @Test
//    public void testGetHuidigeSituatie() {
//        final Leveringsautorisatie leveringsautorisatie = TestLeveringsautorisatieBuilder.maker().maak();
//        final List<Dienst> diensten = new ArrayList<>();
//        final PersoonView persoon = maakHuidigeSituatie();
//        final AutorisatieRegelContext autorisatieRegelContext = new AutorisatieRegelContext(leveringsautorisatie, diensten, persoon);
//        final PersoonView resultaat = autorisatieRegelContext.getHuidigeSituatie();
//
//        Assert.assertEquals(persoon, resultaat);
//        Assert.assertEquals(persoon, autorisatieRegelContext.getSituatie());
//    }
//
//    @Test
//    public void testGetSoortAdministratieveHandeling() {VerwerkRegelsStap
//        final Leveringsautorisatie leveringsautorisatie = TestLeveringsautorisatieBuilder.maker().maak();
//        final PersoonView persoon = maakHuidigeSituatie();
//        final List<Dienst> diensten = new ArrayList<>();
//        final AutorisatieRegelContext autorisatieRegelContext =
//            new AutorisatieRegelContext(leveringsautorisatie, diensten, persoon, SoortAdministratieveHandeling.DUMMY, null);
//        final SoortAdministratieveHandeling resultaat = autorisatieRegelContext.getSoortAdministratieveHandeling();
//
//        Assert.assertEquals(SoortAdministratieveHandeling.DUMMY, resultaat);
//
//    }
//
//    @Test
//    public void testGetToegangLeveringsautorisatieModel() {
//        final ToegangLeveringsautorisatie tla = TestToegangLeveringautorisatieBuilder.maker().maak();
//        final AutorisatieRegelContext autorisatieRegelContext =
//            new AutorisatieRegelContext(null, null, null, null, tla);
//        final ToegangLeveringsautorisatie tlaResultaat = autorisatieRegelContext.getToegangLeveringsautorisatie();
//        Assert.assertEquals(tla, tlaResultaat);
//
//    }
//

    /**
     * Maakt een huidige situatie..
     *
     * @return persoon view
     */
    private PersoonView maakHuidigeSituatie() {
        return new PersoonView(TestPersoonJohnnyJordaan.maak());
    }
}
