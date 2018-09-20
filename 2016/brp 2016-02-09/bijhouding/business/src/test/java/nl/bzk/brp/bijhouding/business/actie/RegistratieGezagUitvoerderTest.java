/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.actie;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.Verwerkingsregel;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieGezagBericht;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.KindBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.KindHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieDerdeHeeftGezagHisVolledigImpl;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class RegistratieGezagUitvoerderTest
    extends AbstractRegistratieIndicatieUitvoerderTest<ActieRegistratieGezagBericht>
{

    @Override
    protected SoortIndicatie getSoortIndicatie() {
        return SoortIndicatie.INDICATIE_DERDE_HEEFT_GEZAG;
    }

    @Override
    protected AbstractRegistratieIndicatieUitvoerder maakNieuweUitvoerder() {
        return new RegistratieGezagUitvoerder();
    }

    @Override
    protected ActieRegistratieGezagBericht maakNieuwActieBericht() {
        return new ActieRegistratieGezagBericht();
    }

    @Override
    protected void voegNieuweIndicatieHisVolledigToe() {
        getPersoon().setIndicatieDerdeHeeftGezag(new PersoonIndicatieDerdeHeeftGezagHisVolledigImpl(getPersoon()));
    }

    @Override
    protected void verwijderIndicatie() {
        getPersoon().setIndicatieDerdeHeeftGezag(null);
    }


    @Test
    @SuppressWarnings("unchecked")
    public void testOuderBetrokkenheid() {
        RegistratieGezagUitvoerder uitvoerder = new RegistratieGezagUitvoerder();
        ReflectionTestUtils.setField(uitvoerder, "context", Mockito.mock(BijhoudingBerichtContext.class));
        maakPersoonBericht(uitvoerder, 1234);
        maakPersoonModel(uitvoerder, 4321, 1234);
        uitvoerder.verzamelVerwerkingsregels();
        Assert.assertEquals(1, ((List<Verwerkingsregel>) ReflectionTestUtils
                .getField(uitvoerder, "verwerkingsregels")).size());
    }

    @Test(expected = IllegalStateException.class)
    public void testOuderBetrokkenheidNietgevonden() {
        RegistratieGezagUitvoerder uitvoerder = new RegistratieGezagUitvoerder();
        maakPersoonBericht(uitvoerder, 1234);
        maakPersoonModel(uitvoerder, 4321);
        uitvoerder.verzamelVerwerkingsregels();
    }

    private void maakPersoonBericht(final RegistratieGezagUitvoerder uitvoerder, final int ... ouderSleutels) {
        PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        if (ouderSleutels.length > 0) {
            KindBericht kind = new KindBericht();
            persoonBericht.getBetrokkenheden().add(kind);
            FamilierechtelijkeBetrekkingBericht familie = new FamilierechtelijkeBetrekkingBericht();
            familie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
            familie.getBetrokkenheden().add(kind);
            kind.setRelatie(familie);
            for (int ouderSleutel : ouderSleutels) {
                OuderBericht ouder = new OuderBericht();
                ouder.setObjectSleutel("" + ouderSleutel);
                familie.getBetrokkenheden().add(ouder);
            }
        }
        ReflectionTestUtils.setField(uitvoerder, "berichtRootObject", persoonBericht);
    }

    private void maakPersoonModel(final RegistratieGezagUitvoerder uitvoerder, final int ... ouderSleutels) {
        PersoonHisVolledigImpl persoon = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).build();
        if (ouderSleutels.length > 0) {
            FamilierechtelijkeBetrekkingHisVolledigImpl familie = new FamilierechtelijkeBetrekkingHisVolledigImpl();
            KindHisVolledigImpl kind = new KindHisVolledigImpl(familie, persoon);
            persoon.getBetrokkenheden().add(kind);
            familie.getBetrokkenheden().add(kind);
            for (int ouderSleutel : ouderSleutels) {
                OuderHisVolledigImpl ouder = new OuderHisVolledigImpl(familie, null);
                ReflectionTestUtils.setField(ouder, "iD", ouderSleutel);
                familie.getBetrokkenheden().add(ouder);
            }
        }
        ReflectionTestUtils.setField(uitvoerder, "modelRootObject", persoon);
    }

}
