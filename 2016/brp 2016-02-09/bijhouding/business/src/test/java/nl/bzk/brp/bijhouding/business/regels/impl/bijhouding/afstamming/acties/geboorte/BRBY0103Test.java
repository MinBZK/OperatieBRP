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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdres;
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
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonAdresHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.util.hisvolledig.kern.PersoonAdresHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class BRBY0103Test {

    private static final String  MOEDER_SLEUTEL            = "111111111";
    private static final String  VADER_SLEUTEL             = "222222222";

    private static final boolean MOEDER_IS_ADRESGEVEND     = true;

    private static final boolean MOEDER_IS_INGEZETENE      = true;
    private static final boolean MOEDER_IS_GEEN_INGEZETENE = false;

    private static final boolean MOEDER_HEEFT_BRIEFADRES   = true;
    private static final boolean MOEDER_HEEFT_WOONADRES    = false;

    private BRBY0103             brby0103;

    @Before
    public void init() {
        brby0103 = new BRBY0103();
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0103, brby0103.getRegel());
    }

    @Test
    public void testRegelGaanAf() {
        final List<BerichtEntiteit> overtreders =
            brby0103.voerRegelUit(null, maakNieuweSituatie(MOEDER_IS_ADRESGEVEND), null,
                    maakBestaandeBetrokkenen(MOEDER_SLEUTEL, MOEDER_IS_INGEZETENE, MOEDER_HEEFT_BRIEFADRES));
        Assert.assertEquals(1, overtreders.size());
    }

    @Test
    public void testGeenAdresgevend() {
        final List<BerichtEntiteit> overtreders = brby0103.voerRegelUit(null, maakNieuweSituatie(false), null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testWelAdresgevendGeenBrpMoeder() {
        final List<BerichtEntiteit> overtreders =
            brby0103.voerRegelUit(null, maakNieuweSituatie(MOEDER_IS_ADRESGEVEND), null,
                    maakBestaandeBetrokkenen(null, MOEDER_IS_INGEZETENE, MOEDER_HEEFT_BRIEFADRES));
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testWelAdresgevendWelIngezetene() {
        final List<BerichtEntiteit> overtreders =
            brby0103.voerRegelUit(null, maakNieuweSituatie(MOEDER_IS_ADRESGEVEND), null,
                    maakBestaandeBetrokkenen(MOEDER_SLEUTEL, MOEDER_IS_INGEZETENE, MOEDER_HEEFT_WOONADRES));
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testWelAdresgevendNietIngezetene() {
        final List<BerichtEntiteit> overtreders =
            brby0103.voerRegelUit(null, maakNieuweSituatie(MOEDER_IS_ADRESGEVEND), null,
                    maakBestaandeBetrokkenen(MOEDER_SLEUTEL, MOEDER_IS_GEEN_INGEZETENE, MOEDER_HEEFT_WOONADRES));
        Assert.assertEquals(0, overtreders.size());
    }

    private FamilierechtelijkeBetrekkingBericht maakNieuweSituatie(final boolean metAdresgevend) {
        final FamilierechtelijkeBetrekkingBericht familie = new FamilierechtelijkeBetrekkingBericht();
        familie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());

        final PersoonBericht moeder = new PersoonBericht();
        moeder.setIdentificerendeSleutel(MOEDER_SLEUTEL);
        final OuderBericht moederOuder = new OuderBericht();
        moederOuder.setPersoon(moeder);
        familie.getBetrokkenheden().add(moederOuder);
        if (metAdresgevend) {
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

    private Map<String, PersoonView> maakBestaandeBetrokkenen(final String sleutelBetrokkenen,
            final boolean isIngezetene, final boolean heeftBriefadres)
    {
        final PersoonHisVolledigImplBuilder moederBuilder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        Bijhoudingsaard bijhoudingsaard = Bijhoudingsaard.INGEZETENE;
        NadereBijhoudingsaard nadereBijhoudingsaard = NadereBijhoudingsaard.ACTUEEL;

        if (!isIngezetene) {
            bijhoudingsaard = Bijhoudingsaard.NIET_INGEZETENE;
            nadereBijhoudingsaard = NadereBijhoudingsaard.EMIGRATIE;
        }

        FunctieAdres soortAdres = FunctieAdres.WOONADRES;
        if (heeftBriefadres) {
            soortAdres = FunctieAdres.BRIEFADRES;
        }
        final PersoonAdresHisVolledigImpl persAdres =
            new PersoonAdresHisVolledigImplBuilder().nieuwStandaardRecord(20010101, null, 20010101).soort(soortAdres)
                    .eindeRecord().build();

        moederBuilder.nieuwBijhoudingRecord(20010101, null, 20010101)
                     .bijhoudingsaard(bijhoudingsaard)
                     .nadereBijhoudingsaard(nadereBijhoudingsaard)
                     .eindeRecord().voegPersoonAdresToe(persAdres);

        final Map<String, PersoonView> bestaandeBetrokkenen = new HashMap<>();
        bestaandeBetrokkenen.put(sleutelBetrokkenen, new PersoonView(moederBuilder.build()));

        return bestaandeBetrokkenen;
    }
}
