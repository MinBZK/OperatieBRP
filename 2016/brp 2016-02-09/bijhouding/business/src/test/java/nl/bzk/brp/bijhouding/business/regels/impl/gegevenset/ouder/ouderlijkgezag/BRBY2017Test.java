/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.ouder.ouderlijkgezag;

import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.KindHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieDerdeHeeftGezagHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieOnderCurateleHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.util.hisvolledig.kern.OuderHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonIndicatieDerdeHeeftGezagHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonIndicatieOnderCurateleHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;


public class BRBY2017Test {

    private final BRBY2017       brby2017              = new BRBY2017();

    private static final boolean HEEFT_OUDERLIJK_GEZAG = true;
    private static final boolean GEEN_OUDERLIJK_GEZAG  = false;

    private static final boolean HEEFT_OUDERSCHAP      = true;
    private static final boolean GEEN_OUDERSCHAP       = false;

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY2017, new BRBY2017().getRegel());
    }

    @Test
    public void testPersoonGeenIndicaties() {
        final List<BerichtEntiteit> berichtEntiteiten =
            brby2017.voerRegelUit(maakPersoon(null, 2, HEEFT_OUDERLIJK_GEZAG, HEEFT_OUDERSCHAP), maakPersoonBericht());
        Assert.assertTrue(berichtEntiteiten.isEmpty());
    }

    @Test
    public void testPersoonGeenIndicatieDerdeHeeftGezag() {
        final List<BerichtEntiteit> berichtEntiteiten =
            brby2017.voerRegelUit(
                    maakPersoon(SoortIndicatie.INDICATIE_ONDER_CURATELE, 2, HEEFT_OUDERLIJK_GEZAG, HEEFT_OUDERSCHAP),
                    maakPersoonBericht());
        Assert.assertTrue(berichtEntiteiten.isEmpty());
    }

    @Test
    public void testPersoonMetIndicatieDerdeHeeftGezagBijEenOuder() {
        final List<BerichtEntiteit> berichtEntiteiten =
            brby2017.voerRegelUit(
                    maakPersoon(SoortIndicatie.INDICATIE_DERDE_HEEFT_GEZAG, 1, HEEFT_OUDERLIJK_GEZAG, HEEFT_OUDERSCHAP),
                    maakPersoonBericht());
        Assert.assertEquals(0, berichtEntiteiten.size());
    }

    @Test
    public void testPersoonMetIndicatieDerdeHeeftGezagBijTweeOuders() {
        final PersoonBericht persoonBericht = maakPersoonBericht();
        maakPersoon(SoortIndicatie.INDICATIE_DERDE_HEEFT_GEZAG, 2, HEEFT_OUDERLIJK_GEZAG, HEEFT_OUDERSCHAP);
        final List<BerichtEntiteit> berichtEntiteiten =
            brby2017.voerRegelUit(
                    maakPersoon(SoortIndicatie.INDICATIE_DERDE_HEEFT_GEZAG, 2, HEEFT_OUDERLIJK_GEZAG, HEEFT_OUDERSCHAP),
                    persoonBericht);
        Assert.assertEquals(1, berichtEntiteiten.size());
        Assert.assertEquals(persoonBericht, berichtEntiteiten.get(0));
    }

    @Test
    public void testPersoonMetIndicatieDerdeHeeftGezagBijEenOuderGeenOuderschapEnGeenOuderlijkGezag() {
        final List<BerichtEntiteit> berichtEntiteiten =
            brby2017.voerRegelUit(
                    maakPersoon(SoortIndicatie.INDICATIE_DERDE_HEEFT_GEZAG, 1, GEEN_OUDERLIJK_GEZAG, GEEN_OUDERSCHAP),
                    maakPersoonBericht());
        Assert.assertEquals(0, berichtEntiteiten.size());
    }

    @Test
    public void testPersoonMetIndicatieDerdeHeeftGezagBijEenOuderGeenOuderschapEnZonderOuderlijkGezag() {
        final List<BerichtEntiteit> berichtEntiteiten =
            brby2017.voerRegelUit(maakPersoon(SoortIndicatie.INDICATIE_DERDE_HEEFT_GEZAG, 1, null, GEEN_OUDERSCHAP),
                    maakPersoonBericht());
        Assert.assertEquals(0, berichtEntiteiten.size());
    }

    @Test
    public void testPersoonMetIndicatieDerdeHeeftGezagBijEenOuderZonderOuderschapEnZonderOuderlijkGezag() {
        final List<BerichtEntiteit> berichtEntiteiten =
            brby2017.voerRegelUit(
                    maakPersoon(SoortIndicatie.INDICATIE_DERDE_HEEFT_GEZAG, 1, GEEN_OUDERLIJK_GEZAG, null),
                    maakPersoonBericht());
        Assert.assertEquals(0, berichtEntiteiten.size());
    }

    @Test
    public void testPersoonMetIndicatieDerdeHeeftGezagBijEenOuderGeenOuderschap() {
        final List<BerichtEntiteit> berichtEntiteiten =
            brby2017.voerRegelUit(
                    maakPersoon(SoortIndicatie.INDICATIE_DERDE_HEEFT_GEZAG, 1, GEEN_OUDERLIJK_GEZAG, HEEFT_OUDERSCHAP),
                    maakPersoonBericht());
        Assert.assertEquals(0, berichtEntiteiten.size());
    }

    @Test
    public void testPersoonMetIndicatieDerdeHeeftGezagBijEenOuderZonderOuderlijkGezag() {
        final List<BerichtEntiteit> berichtEntiteiten =
            brby2017.voerRegelUit(
                    maakPersoon(SoortIndicatie.INDICATIE_DERDE_HEEFT_GEZAG, 1, HEEFT_OUDERLIJK_GEZAG, GEEN_OUDERSCHAP),
                    maakPersoonBericht());
        Assert.assertEquals(0, berichtEntiteiten.size());
    }

    private PersoonView maakPersoon(final SoortIndicatie indicatieSoort, final int aantalOuders,
            final Boolean heeftOuderlijkGezag, final Boolean heeftOuderschap)
    {
        FamilierechtelijkeBetrekkingHisVolledigImpl familie = new FamilierechtelijkeBetrekkingHisVolledigImpl();
        PersoonHisVolledigImpl kind =
            new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));

        if (indicatieSoort != null) {
            if (indicatieSoort == SoortIndicatie.INDICATIE_DERDE_HEEFT_GEZAG) {
                PersoonIndicatieDerdeHeeftGezagHisVolledigImpl indicatie =
                        new PersoonIndicatieDerdeHeeftGezagHisVolledigImplBuilder()
                                .nieuwStandaardRecord(20110101, null, 20110101).waarde(Ja.J).eindeRecord().build();
                kind.setIndicatieDerdeHeeftGezag(indicatie);
            } else {
                PersoonIndicatieOnderCurateleHisVolledigImpl indicatie =
                        new PersoonIndicatieOnderCurateleHisVolledigImplBuilder()
                                .nieuwStandaardRecord(20110101, null, 20110101).waarde(Ja.J).eindeRecord().build();
                kind.setIndicatieOnderCuratele(indicatie);
            }
        }

        for (int i = 0; i < aantalOuders; i++) {
            PersoonHisVolledigImpl ouder =
                    new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
            OuderHisVolledigImplBuilder ouderBuilder = new OuderHisVolledigImplBuilder(familie, ouder);

            if (heeftOuderlijkGezag != null) {
                ouderBuilder.nieuwOuderlijkGezagRecord(20100101, null, 20100101)
                        .indicatieOuderHeeftGezag(heeftOuderlijkGezag).eindeRecord();
            }
            Integer datumEindeGeldigheid = null;
            if (heeftOuderschap == null) {
                datumEindeGeldigheid = 20100101;
            }
            ouderBuilder.nieuwOuderschapRecord(20100101, datumEindeGeldigheid, 20100101).indicatieOuder(Ja.J)
                    .eindeRecord();
            OuderHisVolledigImpl ouderBetr = ouderBuilder.build();

            familie.getBetrokkenheden().add(ouderBetr);
            ouder.getBetrokkenheden().add(ouderBetr);
        }

        KindHisVolledigImpl kindBetr = new KindHisVolledigImpl(familie, kind);
        familie.getBetrokkenheden().add(kindBetr);
        kind.getBetrokkenheden().add(kindBetr);

        return new PersoonView(kind);
    }

    private PersoonBericht maakPersoonBericht() {
        return new PersoonBericht();
    }

}
