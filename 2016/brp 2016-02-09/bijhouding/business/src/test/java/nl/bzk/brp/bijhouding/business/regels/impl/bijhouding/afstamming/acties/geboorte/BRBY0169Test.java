/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.geboorte;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Bijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.KindBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.OuderOuderschapGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class BRBY0169Test {

    private static final String MOEDER_SLEUTEL = "111111111";
    private static final String VADER_SLEUTEL = "222222222";

    private BRBY0169 brby0169;

    @Before
    public void init() {
        brby0169 = new BRBY0169();
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0169, brby0169.getRegel());
    }

    @Test
    public void testGeenOuwkiv() {
        final List<BerichtEntiteit> overtreders = brby0169.voerRegelUit(null, maakNieuweSituatie(false), null, maakBestaandeBetrokkenen(true, true));
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testWelOuwkivWelIngezetene() {
        final List<BerichtEntiteit> overtreders = brby0169.voerRegelUit(null, maakNieuweSituatie(true), null, maakBestaandeBetrokkenen(true, true));
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testWelOuwkivNietIngezeteneWelIngeschrevene() {
        final List<BerichtEntiteit> overtreders = brby0169.voerRegelUit(null, maakNieuweSituatie(true), null, maakBestaandeBetrokkenen(true, false));
        Assert.assertEquals(1, overtreders.size());
    }

    @Test
    public void testWelOuwkivNietIngezeteneNietIngeschrevene() {
        final List<BerichtEntiteit> overtreders = brby0169.voerRegelUit(null, maakNieuweSituatie(true), null, maakBestaandeBetrokkenen(false, false));
        Assert.assertEquals(1, overtreders.size());
    }

    private FamilierechtelijkeBetrekkingBericht maakNieuweSituatie(final boolean metOuwkiv) {
        final FamilierechtelijkeBetrekkingBericht familie = new FamilierechtelijkeBetrekkingBericht();
        familie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());

        final PersoonBericht moeder = new PersoonBericht();
        moeder.setIdentificerendeSleutel(MOEDER_SLEUTEL);
        final OuderBericht moederOuder = new OuderBericht();
        moederOuder.setPersoon(moeder);
        familie.getBetrokkenheden().add(moederOuder);
        if (metOuwkiv) {
            final OuderOuderschapGroepBericht ouderschap = new OuderOuderschapGroepBericht();
            ouderschap.setIndicatieOuderUitWieKindIsGeboren(JaNeeAttribuut.JA);
            moederOuder.setOuderschap(ouderschap);
        }

        final PersoonBericht vader = new PersoonBericht();
        vader.setIdentificerendeSleutel(VADER_SLEUTEL);
        final OuderBericht vaderOuder = new OuderBericht();
        vaderOuder.setPersoon(moeder);
        familie.getBetrokkenheden().add(vaderOuder);

        final PersoonBericht kind = new PersoonBericht();
        final KindBericht kindKind = new KindBericht();
        kindKind.setPersoon(kind);
        familie.getBetrokkenheden().add(kindKind);

        return familie;
    }

    private Map<String, PersoonView> maakBestaandeBetrokkenen(final boolean moederIsIngeschreve, final boolean moederIsIngezetene) {
        final Map<String, PersoonView> bestaandeBetrokkenen = new HashMap<>();
        // Vader zit er voor spek en bonen ook in.
        bestaandeBetrokkenen.put(VADER_SLEUTEL, new PersoonView(new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build()));

        SoortPersoon soortPersoon = SoortPersoon.INGESCHREVENE;
        if (!moederIsIngeschreve) {
            soortPersoon = SoortPersoon.NIET_INGESCHREVENE;
        }
        final PersoonHisVolledigImplBuilder moederBuilder = new PersoonHisVolledigImplBuilder(soortPersoon);
        Bijhoudingsaard bijhoudingsaard = Bijhoudingsaard.INGEZETENE;
        NadereBijhoudingsaard nadereBijhoudingsaard = NadereBijhoudingsaard.ACTUEEL;

        if (!moederIsIngezetene) {
            bijhoudingsaard = Bijhoudingsaard.NIET_INGEZETENE;
            nadereBijhoudingsaard = NadereBijhoudingsaard.EMIGRATIE;
        }

        moederBuilder.nieuwBijhoudingRecord(20010101, null, 20010101)
                .bijhoudingsaard(bijhoudingsaard)
                .nadereBijhoudingsaard(nadereBijhoudingsaard)
                .eindeRecord();

        // Niet ingeschrevenen komen niet in de bestaande betrokkenen map.
        if (moederIsIngeschreve) {
            bestaandeBetrokkenen.put(MOEDER_SLEUTEL, new PersoonView(moederBuilder.build()));
        }
        return bestaandeBetrokkenen;
    }

}
