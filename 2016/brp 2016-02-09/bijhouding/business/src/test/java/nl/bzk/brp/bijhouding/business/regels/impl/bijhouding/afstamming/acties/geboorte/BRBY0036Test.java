/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.geboorte;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.definitieregels.BRBY0002;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.KindBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.OuderOuderschapGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


public class BRBY0036Test {

    public static final String TECHNISCHE_SLEUTEL = "123";
    @Mock
    private BRBY0002 brby0002;

    @InjectMocks
    private final BRBY0036 brby0036 = new BRBY0036();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testMoederHeeftGeenTechnischeSleutel() {
        final FamilierechtelijkeBetrekkingBericht relatie =
            maakFamilieRelatie(null, maakOuderBetrokkenheid(null, JaNeeAttribuut.JA), null);

        final List<BerichtEntiteit> berichtEntiteiten = brby0036.voerRegelUit(null, relatie, null, null);

        Assert.assertEquals(0, berichtEntiteiten.size());
    }

    @Test
    public void testVaderGevondenMaarNietOpgegeven() {
        final FamilierechtelijkeBetrekkingBericht relatie =
            maakFamilieRelatie(null, maakOuderBetrokkenheid(TECHNISCHE_SLEUTEL, JaNeeAttribuut.JA), maakKindBetrokkenheid());

        final Map<String, PersoonView> bestaandeBetrokkenen = new HashMap<>();
        bestaandeBetrokkenen
            .put(TECHNISCHE_SLEUTEL, new PersoonView(new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE))));

        final PersoonView gevondenVader = maakPersoonView(999);

        Mockito.when(brby0002.bepaalKandidatenVader(Matchers.any(PersoonView.class),
            Matchers.any(DatumEvtDeelsOnbekendAttribuut.class)))
            .thenReturn(Collections.singletonList(gevondenVader));

        final List<BerichtEntiteit> berichtEntiteiten = brby0036.voerRegelUit(null, relatie, null, bestaandeBetrokkenen);

        Mockito.verify(brby0002, Mockito.times(1)).bepaalKandidatenVader(Matchers.any(PersoonView.class),
            Matchers.any(DatumEvtDeelsOnbekendAttribuut.class));

        Assert.assertEquals(1, berichtEntiteiten.size());

    }

    private PersoonView maakPersoonView(final Integer bsn) {
        final PersoonHisVolledigImpl persoonHisVolledig = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwIdentificatienummersRecord(19800101, null, 19800101).burgerservicenummer(bsn).eindeRecord()
                .build();

        return new PersoonView(persoonHisVolledig);
    }

    private BetrokkenheidBericht maakOuderBetrokkenheid(final String technischeSleutel, final JaNeeAttribuut ouwkig) {
        final PersoonBericht persoonBericht = new PersoonBericht();
        if (technischeSleutel != null) {
            persoonBericht.setObjectSleutel(technischeSleutel);
            persoonBericht.setIdentificerendeSleutel("technischeSleutel=" + technischeSleutel);
        }

        final OuderBericht betrokkenheid = new OuderBericht();
        betrokkenheid.setOuderschap(new OuderOuderschapGroepBericht());
        betrokkenheid.getOuderschap().setIndicatieOuderUitWieKindIsGeboren(ouwkig);
        betrokkenheid.setPersoon(persoonBericht);

        return betrokkenheid;
    }

    private KindBericht maakKindBetrokkenheid() {
        final PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setGeboorte(new PersoonGeboorteGroepBericht());

        final KindBericht betrokkenheid = new KindBericht();
        betrokkenheid.setPersoon(persoonBericht);
        return betrokkenheid;
    }

    private FamilierechtelijkeBetrekkingBericht maakFamilieRelatie(final BetrokkenheidBericht vader,
                                                                   final BetrokkenheidBericht moeder,
                                                                   final BetrokkenheidBericht kind)
    {
        final List<BetrokkenheidBericht> betrokkenheden = new ArrayList<>();
        if (vader != null) {
            betrokkenheden.add(vader);
        }
        if (moeder != null) {
            betrokkenheden.add(moeder);
        }
        if (kind != null) {
            betrokkenheden.add(kind);
        }

        final FamilierechtelijkeBetrekkingBericht relatieBericht = new FamilierechtelijkeBetrekkingBericht();
        relatieBericht.setBetrokkenheden(betrokkenheden);

        return relatieBericht;
    }
}
