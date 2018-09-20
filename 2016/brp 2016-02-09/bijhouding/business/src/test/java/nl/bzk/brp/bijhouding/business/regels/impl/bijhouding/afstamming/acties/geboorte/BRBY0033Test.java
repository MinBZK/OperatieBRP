/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.geboorte;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.definitieregels.BRBY0002;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornamenAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GeslachtsaanduidingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
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
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsaanduidingGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonSamengesteldeNaamGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(PowerMockRunner.class)
public class BRBY0033Test {

    @Mock
    private BRBY0002 brby0002;

    @InjectMocks
    private final BRBY0033 brby0033 = new BRBY0033();

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0033, brby0033.getRegel());
    }

    @Test
    public void testMoederHeeftGeenTechnischeSleutel() {
        final FamilierechtelijkeBetrekkingBericht relatie =
            maakFamilieRelatie(null, maakOuderBetrokkenheid(null, null, JaNeeAttribuut.JA), null);

        final List<BerichtEntiteit> berichtEntiteiten = brby0033.voerRegelUit(null, relatie, null, null);

        Assert.assertEquals(0, berichtEntiteiten.size());
    }

    @Test
    public void testOpgegevenVaderGeenKandidaat() {
        final BetrokkenheidBericht vaderBetrokkenheid = maakOuderBetrokkenheid("dlkfgjdfkl", 456, null);
        final FamilierechtelijkeBetrekkingBericht relatie =
            maakFamilieRelatie(vaderBetrokkenheid, maakOuderBetrokkenheid("dfgk8", 123, JaNeeAttribuut.JA), maakKindBetrokkenheid());

        final Map<String, PersoonView> bestaandeBetrokkenen = new HashMap<>();
        bestaandeBetrokkenen
            .put("123", new PersoonView(new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE))));

        final PersoonView gevondenVader = maakPersoonView(999);

        Mockito.when(brby0002
            .bepaalKandidatenVader(Matchers.any(PersoonView.class),
                Matchers.any(DatumEvtDeelsOnbekendAttribuut.class)))
            .thenReturn(Collections.singletonList(gevondenVader));

        final List<BerichtEntiteit> berichtEntiteiten = brby0033.voerRegelUit(null, relatie, null, bestaandeBetrokkenen);

        Mockito.verify(brby0002, Mockito.times(1))
            .bepaalKandidatenVader(Matchers.any(PersoonView.class),
                Matchers.any(DatumEvtDeelsOnbekendAttribuut.class));

        Assert.assertEquals(1, berichtEntiteiten.size());
        Assert.assertEquals(vaderBetrokkenheid.getPersoon(), berichtEntiteiten.get(0));
    }

    @Test
    public void testOpgegevenVaderIsKandidaat() {
        final FamilierechtelijkeBetrekkingBericht relatie =
            maakFamilieRelatie(maakOuderBetrokkenheid("kgjf0dfg", 456, null),
                maakOuderBetrokkenheid("dfgf89d", 123, JaNeeAttribuut.JA),
                                   maakKindBetrokkenheid());

        final Map<String, PersoonView> bestaandeBetrokkenen = new HashMap<>();
        bestaandeBetrokkenen
                .put("dfgf89d", new PersoonView(new PersoonHisVolledigImpl(
                        new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE))));

        final PersoonView gevondenVader0 = maakPersoonView(null);
        final PersoonView gevondenVader1 = maakPersoonView(789);
        final PersoonView gevondenVader2 = maakPersoonView(456);


        Mockito.when(brby0002.bepaalKandidatenVader(
                Matchers.any(PersoonView.class), Matchers.any(DatumEvtDeelsOnbekendAttribuut.class)))
                .thenReturn(Arrays.asList(gevondenVader0, gevondenVader1, gevondenVader2));

        final List<BerichtEntiteit> berichtEntiteiten = brby0033.voerRegelUit(null, relatie, null, bestaandeBetrokkenen);

        Mockito.verify(brby0002, Mockito.times(1)).bepaalKandidatenVader(Matchers.any(PersoonView.class),
                Matchers.any(DatumEvtDeelsOnbekendAttribuut.class));

        Assert.assertEquals(0, berichtEntiteiten.size());
    }

    @Test
    public void testOpgegevenNietIngeschrevenIsKandidaatVader() {
        final FamilierechtelijkeBetrekkingBericht relatie =
                maakFamilieRelatie(maakOuderBetrokkenheid(null, null, null), maakOuderBetrokkenheid("sdfwe4d",
                                                                                                    123, JaNeeAttribuut.JA),
                        maakKindBetrokkenheid());

        final Map<String, PersoonView> bestaandeBetrokkenen = new HashMap<>();
        bestaandeBetrokkenen
                .put("123", new PersoonView(new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE))));

        final PersoonView gevondenVader0 = maakPersoonView(null);
        final PersoonView gevondenVader1 = maakPersoonView(789);
        final PersoonView gevondenVader2 = maakPersoonView(456);


        Mockito.when(brby0002.bepaalKandidatenVader(
                Matchers.any(PersoonView.class), Matchers.any(DatumEvtDeelsOnbekendAttribuut.class)))
                .thenReturn(Arrays.asList(gevondenVader0, gevondenVader1, gevondenVader2));

        final List<BerichtEntiteit> berichtEntiteiten = brby0033.voerRegelUit(null, relatie, null, bestaandeBetrokkenen);

        Mockito.verify(brby0002, Mockito.times(1)).bepaalKandidatenVader(Matchers.any(PersoonView.class),
                Matchers.any(DatumEvtDeelsOnbekendAttribuut.class));

        Assert.assertEquals(0, berichtEntiteiten.size());
    }

    @Test
    public void testGeenKandidatenVaders() {
        final FamilierechtelijkeBetrekkingBericht relatie =
                maakFamilieRelatie(maakOuderBetrokkenheid("sdfsdif0", 456, null), maakOuderBetrokkenheid("sdfwe4", 123, JaNeeAttribuut.JA),
                        maakKindBetrokkenheid());

        final Map<String, PersoonView> bestaandeBetrokkenen = new HashMap<>();
        bestaandeBetrokkenen
                .put("123", new PersoonView(new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE))));

        Mockito.when(brby0002.bepaalKandidatenVader(Matchers.any(PersoonView.class),
                Matchers.any(DatumEvtDeelsOnbekendAttribuut.class)))
                .thenReturn(new ArrayList<PersoonView>());

        final List<BerichtEntiteit> berichtEntiteiten = brby0033.voerRegelUit(null, relatie, null, bestaandeBetrokkenen);

        Mockito.verify(brby0002, Mockito.times(1)).bepaalKandidatenVader(Matchers.any(PersoonView.class),
                Matchers.any(DatumEvtDeelsOnbekendAttribuut.class));

        Assert.assertEquals(1, berichtEntiteiten.size());
    }

    /**
     * Maakt een persoon his volledig view.
     *
     * @param databaseId databaseId
     * @return persoon his volledig view
     */
    private PersoonView maakPersoonView(final Integer databaseId) {
        final PersoonHisVolledigImpl persoonHisVolledig;
        if (databaseId != null) {
            persoonHisVolledig = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                    .nieuwIdentificatienummersRecord(19800101, null, 19800101).burgerservicenummer(databaseId).eindeRecord()
                    .build();
            ReflectionTestUtils.setField(persoonHisVolledig, "iD", databaseId);
        } else {
            final PersoonGeslachtsaanduidingGroepBericht geslaanduiding = maakGeslachtsaanduiding();
            final PersoonSamengesteldeNaamGroepBericht samengesteldeNaam = maakSamengesteldenaam();
            final PersoonGeboorteGroepBericht geboorte = maakGeboorte();

            persoonHisVolledig = new PersoonHisVolledigImplBuilder(SoortPersoon.NIET_INGESCHREVENE)
                    .nieuwGeslachtsaanduidingRecord(19800101, null, 19800101)
                    .geslachtsaanduiding(geslaanduiding.getGeslachtsaanduiding().getWaarde()).eindeRecord()
                    .nieuwSamengesteldeNaamRecord(19800101, null, 19800101)
                    .voornamen(samengesteldeNaam.getVoornamen()).eindeRecord()
                    .nieuwGeboorteRecord(19800101)
                    .datumGeboorte(geboorte.getDatumGeboorte())
                    .gemeenteGeboorte(geboorte.getGemeenteGeboorte().getWaarde()).eindeRecord()
                    .build();
        }

        return new PersoonView(persoonHisVolledig);
    }

    /**
     * Maakt een ouder betrokkenheid.
     *
     * @param objectSleutelPersoon technische sleutel
     * @param ouderUitWieKindIsGeboren       ouderUitWieKindIsGeboren
     * @return betrokkenheid bericht
     */
    private BetrokkenheidBericht maakOuderBetrokkenheid(final String objectSleutelPersoon, final Integer databaseIdPersoon,
                                                        final JaNeeAttribuut ouderUitWieKindIsGeboren)
    {
        final PersoonBericht persoonBericht = new PersoonBericht();
        if (objectSleutelPersoon != null) {
            persoonBericht.setObjectSleutel(objectSleutelPersoon);
            persoonBericht.setObjectSleutelDatabaseID(databaseIdPersoon);
            persoonBericht.setIdentificerendeSleutel("identificerendeSleutel=" + objectSleutelPersoon);
            persoonBericht.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        } else {
            persoonBericht.setSoort(new SoortPersoonAttribuut(SoortPersoon.NIET_INGESCHREVENE));
            persoonBericht.setSamengesteldeNaam(maakSamengesteldenaam());
            persoonBericht.setGeslachtsaanduiding(maakGeslachtsaanduiding());
            persoonBericht.setGeboorte(maakGeboorte());
        }

        final OuderBericht betrokkenheid = new OuderBericht();
        betrokkenheid.setOuderschap(new OuderOuderschapGroepBericht());
        if (ouderUitWieKindIsGeboren != null) {
            betrokkenheid.getOuderschap().setIndicatieOuderUitWieKindIsGeboren(ouderUitWieKindIsGeboren);
        }
        betrokkenheid.setPersoon(persoonBericht);

        return betrokkenheid;
    }

    /**
     * Maakt een kind betrokkenheid.
     *
     * @return kind bericht
     */
    private KindBericht maakKindBetrokkenheid() {
        final PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setGeboorte(new PersoonGeboorteGroepBericht());

        final KindBericht betrokkenheid = new KindBericht();
        betrokkenheid.setPersoon(persoonBericht);
        return betrokkenheid;
    }

    /**
     * Maakt een familierechtelijke betrekking bericht.
     *
     * @param vader  vader
     * @param moeder moeder
     * @param kind   kind
     * @return familierechtelijke betrekking bericht
     */
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


    private PersoonGeboorteGroepBericht maakGeboorte() {
        final PersoonGeboorteGroepBericht persoonGeboorteGroepBericht = new PersoonGeboorteGroepBericht();
        persoonGeboorteGroepBericht.setDatumGeboorte(new DatumEvtDeelsOnbekendAttribuut(20101010));
        persoonGeboorteGroepBericht.setGemeenteGeboorte(StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM);

        return persoonGeboorteGroepBericht;
    }

    private PersoonGeslachtsaanduidingGroepBericht maakGeslachtsaanduiding() {
        final PersoonGeslachtsaanduidingGroepBericht persoonGeslachtsaanduidingGroepBericht =
                new PersoonGeslachtsaanduidingGroepBericht();
        persoonGeslachtsaanduidingGroepBericht.setGeslachtsaanduiding(new GeslachtsaanduidingAttribuut(Geslachtsaanduiding.MAN));

        return persoonGeslachtsaanduidingGroepBericht;
    }

    private PersoonSamengesteldeNaamGroepBericht maakSamengesteldenaam() {
        final PersoonSamengesteldeNaamGroepBericht samengesteldeNaamGroepBericht = new PersoonSamengesteldeNaamGroepBericht();
        samengesteldeNaamGroepBericht.setVoornamen(new VoornamenAttribuut("voornaam"));

        return samengesteldeNaamGroepBericht;
    }
}
