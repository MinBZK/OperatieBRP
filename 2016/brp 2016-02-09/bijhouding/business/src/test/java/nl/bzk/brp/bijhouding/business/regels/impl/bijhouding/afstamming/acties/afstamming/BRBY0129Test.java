/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.afstamming;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.KindBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;

public class BRBY0129Test {

    private PersoonBericht kindBericht;

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0129, new BRBY0129().getRegel());
    }

    @Test
    public void testHappyFlow() {
        final PersoonHisVolledig moeder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwGeboorteRecord(19830101).datumGeboorte(19860101).eindeRecord().build();
        final PersoonHisVolledig vader = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwGeboorteRecord(19830101).datumGeboorte(19830101).eindeRecord().build();


        final FamilierechtelijkeBetrekkingBericht familierechtelijkeBetrekkingBericht =
                maakFamilieRechtelijkeBetrekkingBericht(20120101);

        final Map<String, PersoonView> bestaandeBetrokkenen = new HashMap<>();
        bestaandeBetrokkenen.put("1", new PersoonView(moeder));
        bestaandeBetrokkenen.put("2", new PersoonView(vader));

        final List<BerichtEntiteit> berichtEntiteiten =
                new BRBY0129().voerRegelUit(null, familierechtelijkeBetrekkingBericht, null, bestaandeBetrokkenen);

        Assert.assertTrue(berichtEntiteiten.isEmpty());
    }


    @Test
    public void testGeboorteDatumMoederNaGeboorteDatumKind() {
        final PersoonHisVolledig moeder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwGeboorteRecord(19830101).datumGeboorte(20120101).eindeRecord().build();
        final PersoonHisVolledig vader = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwGeboorteRecord(19830101).datumGeboorte(19830101).eindeRecord().build();


        final FamilierechtelijkeBetrekkingBericht familierechtelijkeBetrekkingBericht =
                maakFamilieRechtelijkeBetrekkingBericht(20110101);

        final Map<String, PersoonView> bestaandeBetrokkenen = new HashMap<>();
        bestaandeBetrokkenen.put("1", new PersoonView(moeder));
        bestaandeBetrokkenen.put("2", new PersoonView(vader));

        final List<BerichtEntiteit> berichtEntiteiten =
                new BRBY0129().voerRegelUit(null, familierechtelijkeBetrekkingBericht, null, bestaandeBetrokkenen);

        Assert.assertEquals(1, berichtEntiteiten.size());
        Assert.assertEquals(kindBericht, berichtEntiteiten.get(0));
    }

    @Test
    public void testGeboorteDatumMoederEnVaderNaGeboorteDatumKind() {
        final PersoonHisVolledig moeder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwGeboorteRecord(19830101).datumGeboorte(19850101).eindeRecord().build();
        final PersoonHisVolledig vader = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwGeboorteRecord(19830101).datumGeboorte(19830101).eindeRecord().build();


        final FamilierechtelijkeBetrekkingBericht familierechtelijkeBetrekkingBericht =
                maakFamilieRechtelijkeBetrekkingBericht(19810101);

        final Map<String, PersoonView> bestaandeBetrokkenen = new HashMap<>();
        bestaandeBetrokkenen.put("1", new PersoonView(moeder));
        bestaandeBetrokkenen.put("2", new PersoonView(vader));

        final List<BerichtEntiteit> berichtEntiteiten =
                new BRBY0129().voerRegelUit(null, familierechtelijkeBetrekkingBericht, null, bestaandeBetrokkenen);

        Assert.assertEquals(1, berichtEntiteiten.size());
        Assert.assertEquals(kindBericht, berichtEntiteiten.get(0));
    }

    private FamilierechtelijkeBetrekkingBericht maakFamilieRechtelijkeBetrekkingBericht(final int geboorteDatumKind) {
        final FamilierechtelijkeBetrekkingBericht familierechtelijkeBetrekkingBericht =
                new FamilierechtelijkeBetrekkingBericht();
        kindBericht = new PersoonBericht();
        kindBericht.setIdentificerendeSleutel("99");
        kindBericht.setGeboorte(new PersoonGeboorteGroepBericht());
        kindBericht.getGeboorte().setDatumGeboorte(new DatumEvtDeelsOnbekendAttribuut(geboorteDatumKind));
        final KindBericht kindBerichtBetr = new KindBericht();
        kindBerichtBetr.setPersoon(kindBericht);
        familierechtelijkeBetrekkingBericht.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        familierechtelijkeBetrekkingBericht.getBetrokkenheden().add(kindBerichtBetr);

        final PersoonBericht moederBericht = new PersoonBericht();
        moederBericht.setIdentificerendeSleutel("1");
        moederBericht.setGeboorte(new PersoonGeboorteGroepBericht());
        moederBericht.getGeboorte().setDatumGeboorte(new DatumEvtDeelsOnbekendAttribuut(20110101));
        final OuderBericht moederBerichtBetr = new OuderBericht();
        moederBerichtBetr.setPersoon(moederBericht);
        familierechtelijkeBetrekkingBericht.getBetrokkenheden().add(moederBerichtBetr);

        final PersoonBericht vaderBericht = new PersoonBericht();
        vaderBericht.setIdentificerendeSleutel("2");
        vaderBericht.setGeboorte(new PersoonGeboorteGroepBericht());
        vaderBericht.getGeboorte().setDatumGeboorte(new DatumEvtDeelsOnbekendAttribuut(20110101));
        final OuderBericht vaderBerichtBetr = new OuderBericht();
        vaderBerichtBetr.setPersoon(vaderBericht);
        familierechtelijkeBetrekkingBericht.getBetrokkenheden().add(vaderBerichtBetr);
        return familierechtelijkeBetrekkingBericht;
    }
}
