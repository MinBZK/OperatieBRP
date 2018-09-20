/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.synchronisatie.regels.autorisatie;

import java.util.HashSet;
import nl.bzk.brp.business.regels.context.AutorisatieRegelContext;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.hisvolledig.impl.autaut.PersoonAfnemerindicatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class BRLV0040Test {

    private BRLV0040 regel = new BRLV0040();

    @Test
    public final void testGetRegel() {
        Assert.assertEquals(Regel.BRLV0040, regel.getRegel());
    }

    @Test
    public final void valideOmdatAfnemerindicatieBestaat() {
        final Leveringsautorisatie la = TestLeveringsautorisatieBuilder
            .metDienst(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE);
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie
            (la).maak();

        final PersoonView persoon = maakHuidigeSituatie(true);

        //beetje magic, maar bewerk één van de indicaties van johnny
        final Integer id = persoon.getAfnemerindicaties().iterator().next().getLeveringsautorisatie().getWaarde().getID();
        ReflectionTestUtils.setField(la, "iD", id);


        final AutorisatieRegelContext regelContext = new AutorisatieRegelContext(toegangLeveringsautorisatie, la.geefDiensten().iterator().next(),
            persoon);
        final boolean isValide = regel.valideer(regelContext);

        Assert.assertTrue(isValide);
    }

    @Test
    public final void invalideOmdatAfnemerindicatieNietBestaat() {
        final Leveringsautorisatie la = TestLeveringsautorisatieBuilder
            .metDienst(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE);
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie
                (la).maak();
        final PersoonView persoon = maakHuidigeSituatie(false);
        final AutorisatieRegelContext regelContext = new AutorisatieRegelContext(toegangLeveringsautorisatie, la.geefDiensten().iterator().next(),
            persoon);
        Assert.assertFalse(regel.valideer(regelContext));
    }

    @Test
    public final void valideOmdatAfnemerindicatieNietBestaatMaarDienstMutatieleveringObvAfnemerindicatieOokNiet() {
        final Leveringsautorisatie la = TestLeveringsautorisatieBuilder
                .metDienst(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING);
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie
                (la).maak();
        final PersoonView persoon = maakHuidigeSituatie(false);
        final AutorisatieRegelContext regelContext = new AutorisatieRegelContext(toegangLeveringsautorisatie, la.geefDiensten().iterator().next(),
                persoon);
        Assert.assertTrue(regel.valideer(regelContext));
    }

    /**
     * Maakt de huidige situatie.
     *
     * @param heefAfnemerIndicaties De testpersoon heeft afnemerindicaties of niet.
     * @return De persoon view.
     */
    private PersoonView maakHuidigeSituatie(final boolean heefAfnemerIndicaties) {
        final PersoonHisVolledigImpl testPersoonVolledig = TestPersoonJohnnyJordaan.maak();
        if (!heefAfnemerIndicaties) {
            ReflectionTestUtils.setField(testPersoonVolledig, "afnemerindicaties", new HashSet<PersoonAfnemerindicatieHisVolledigImpl>());
        }
        return new PersoonView(testPersoonVolledig);
    }
}
