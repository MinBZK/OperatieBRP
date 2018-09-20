/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.staatloos;

import static nl.bzk.brp.util.StatischeObjecttypeBuilder.NATIONALITEIT_NEDERLANDS;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.KindBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.KindHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieDerdeHeeftGezagHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieStaatloosHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonNationaliteitHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.FamilierechtelijkeBetrekkingView;
import nl.bzk.brp.util.hisvolledig.kern.PersoonIndicatieDerdeHeeftGezagHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonIndicatieStaatloosHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonNationaliteitHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Unit test voor de {@link BRAL0219} bedrijfsregel.
 */
public class BRAL0219Test {

    private static final Integer DATUM_AANVANG_NATIONALITEIT = 20101010;

    private BRAL0219 bral0219;

    @Before
    public void init() {
        bral0219 = new BRAL0219();
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRAL0219, bral0219.getRegel());
    }

    @Test
    public void testKindZonderNationaliteitEnZonderIndicatieStaatsloos() {
        final List<BerichtEntiteit> berichtEntiteiten =
            bral0219.voerRegelUit(maakFamilie(SoortIndicatie.INDICATIE_DERDE_HEEFT_GEZAG, null), maakFamilieBericht());
        Assert.assertEquals(1, berichtEntiteiten.size());
    }

    @Test
    public void testKindZonderNationaliteitEnMetIndicatieStaatsloos() {
        final List<BerichtEntiteit> berichtEntiteiten =
            bral0219.voerRegelUit(maakFamilie(SoortIndicatie.INDICATIE_STAATLOOS, NATIONALITEIT_NEDERLANDS.getWaarde()), maakFamilieBericht());
        Assert.assertEquals(0, berichtEntiteiten.size());
    }

    @Test
    public void testKindMetNationaliteitEnZonderIndicatieStaatsloos() {
        final List<BerichtEntiteit> berichtEntiteiten =
            bral0219.voerRegelUit(maakFamilie(SoortIndicatie.INDICATIE_DERDE_HEEFT_GEZAG, NATIONALITEIT_NEDERLANDS.getWaarde()), maakFamilieBericht());
        Assert.assertEquals(0, berichtEntiteiten.size());
    }

    @Test
    public void testKindMetNationaliteitEnMetIndicatieStaatsloos() {
        final List<BerichtEntiteit> berichtEntiteiten =
            bral0219.voerRegelUit(maakFamilie(SoortIndicatie.INDICATIE_STAATLOOS, NATIONALITEIT_NEDERLANDS.getWaarde()), maakFamilieBericht());
        Assert.assertEquals(0, berichtEntiteiten.size());
    }

    private FamilierechtelijkeBetrekkingView maakFamilie(final SoortIndicatie indicatieSoort, final Nationaliteit nationaliteit) {
        final PersoonHisVolledigImpl persoonHisVolledig =
                new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));

        if (indicatieSoort != null) {
            if (indicatieSoort == SoortIndicatie.INDICATIE_DERDE_HEEFT_GEZAG) {
                final PersoonIndicatieDerdeHeeftGezagHisVolledigImpl indicatie =
                        new PersoonIndicatieDerdeHeeftGezagHisVolledigImplBuilder()
                                .nieuwStandaardRecord(20110101, null, 20110101).waarde(Ja.J).eindeRecord().build();
                persoonHisVolledig.setIndicatieDerdeHeeftGezag(indicatie);
            } else {
                final PersoonIndicatieStaatloosHisVolledigImpl indicatie =
                        new PersoonIndicatieStaatloosHisVolledigImplBuilder()
                                .nieuwStandaardRecord(20110101, null, 20110101).waarde(Ja.J).eindeRecord().build();
                persoonHisVolledig.setIndicatieStaatloos(indicatie);
            }
        }

        if (nationaliteit != null) {
            final PersoonNationaliteitHisVolledigImpl persNationaliteit =
                new PersoonNationaliteitHisVolledigImplBuilder(persoonHisVolledig, nationaliteit)
                        .nieuwStandaardRecord(DATUM_AANVANG_NATIONALITEIT, null,
                                DATUM_AANVANG_NATIONALITEIT).eindeRecord().build();
            persoonHisVolledig.getNationaliteiten().add(persNationaliteit);
        }

        final FamilierechtelijkeBetrekkingHisVolledigImpl familie = new FamilierechtelijkeBetrekkingHisVolledigImpl();
        final KindHisVolledigImpl kind = new KindHisVolledigImpl(familie, persoonHisVolledig);
        familie.getBetrokkenheden().add(kind);
        return new FamilierechtelijkeBetrekkingView(familie, DatumTijdAttribuut.nu(), DatumAttribuut.vandaag());
    }

    private FamilierechtelijkeBetrekkingBericht maakFamilieBericht() {
        final FamilierechtelijkeBetrekkingBericht familie = new FamilierechtelijkeBetrekkingBericht();
        final KindBericht kindBetrokkenheid = new KindBericht();
        final PersoonBericht kind = new PersoonBericht();
        kindBetrokkenheid.setPersoon(kind);
        familie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        familie.getBetrokkenheden().add(kindBetrokkenheid);
        return familie;
    }

}
