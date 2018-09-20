/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.handlers.bijhouding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.bzk.brp.business.bedrijfsregels.ActieBedrijfsRegel;
import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegelManager;
import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegelManagerImpl;
import nl.bzk.brp.business.dto.BerichtVerwerkingsResultaat;
import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.business.handlers.AbstractStapTest;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.groep.bericht.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.ActieBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonAdresBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.basis.AbstractPersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortActie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortbericht;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortmelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;


@RunWith(MockitoJUnitRunner.class)
public class BedrijfsregelValidatieStapTest extends AbstractStapTest {

    private static final String        BEDRIJFSREGELMANAGER_FIELDNAME = "bedrijfsRegelManager";

    private BedrijfsregelValidatieStap bedrijfsregelValidatieStap;

    @Mock
    private PersoonRepository          persoonRepository;

    private BedrijfsRegelManager       bedrijfsRegelManager;

    @SuppressWarnings("serial")
    @Before
    public void init() {
        bedrijfsregelValidatieStap = new BedrijfsregelValidatieStap();

        Mockito.when(persoonRepository.findByBurgerservicenummer((Burgerservicenummer) Matchers.anyObject()))
                .thenReturn(new PersoonModel(new AbstractPersoonModel() {
                }));

        ReflectionTestUtils.setField(bedrijfsregelValidatieStap, "persoonRepository", persoonRepository);
    }

    @Test
    public void testBerichtGeldig() {
        bedrijfsRegelManager = new BedrijfsRegelManagerImpl(maakBedrijfsRegels(null, null), null, null);
        ReflectionTestUtils.setField(bedrijfsregelValidatieStap, BEDRIJFSREGELMANAGER_FIELDNAME, bedrijfsRegelManager);

        AbstractBijhoudingsBericht bericht = maakBericht();
        BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(null);
        boolean stapResultaat =
            bedrijfsregelValidatieStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertTrue(stapResultaat);
        Assert.assertTrue(resultaat.getMeldingen().isEmpty());
    }

    @Test
    public void testBerichtOngeldigAntwoordMetWaarschuwing() {
        bedrijfsRegelManager =
            new BedrijfsRegelManagerImpl(maakBedrijfsRegels(Soortmelding.WAARSCHUWING, MeldingCode.ALG0001), null, null);
        ReflectionTestUtils.setField(bedrijfsregelValidatieStap, BEDRIJFSREGELMANAGER_FIELDNAME, bedrijfsRegelManager);

        AbstractBijhoudingsBericht bericht = maakBericht();
        BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(null);
        boolean stapResultaat =
            bedrijfsregelValidatieStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertTrue(stapResultaat);
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(Soortmelding.WAARSCHUWING, resultaat.getMeldingen().get(0).getSoort());
        Assert.assertEquals(MeldingCode.ALG0001, resultaat.getMeldingen().get(0).getCode());

    }

    @Test
    public void testBerichtOngeldigAntwoordMetFoutOnoverRuleBaar() {
        bedrijfsRegelManager =
            new BedrijfsRegelManagerImpl(maakBedrijfsRegels(Soortmelding.FOUT, MeldingCode.ALG0001),
                    null, null);
        ReflectionTestUtils.setField(bedrijfsregelValidatieStap, BEDRIJFSREGELMANAGER_FIELDNAME, bedrijfsRegelManager);

        AbstractBijhoudingsBericht bericht = maakBericht();
        BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(new ArrayList<Melding>());
        boolean stapResultaat =
            bedrijfsregelValidatieStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertTrue(stapResultaat);
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(Soortmelding.FOUT, resultaat.getMeldingen().get(0).getSoort());
        Assert.assertEquals(MeldingCode.ALG0001, resultaat.getMeldingen().get(0).getCode());
    }

    @Test
    public void testBerichtOngeldigAntwoordMetFoutOverRuleBaar() {
        bedrijfsRegelManager =
            new BedrijfsRegelManagerImpl(maakBedrijfsRegels(Soortmelding.OVERRULEBAAR, MeldingCode.ALG0001), null,
                    null);
        ReflectionTestUtils.setField(bedrijfsregelValidatieStap, BEDRIJFSREGELMANAGER_FIELDNAME, bedrijfsRegelManager);

        AbstractBijhoudingsBericht bericht = maakBericht();
        BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(null);
        boolean stapResultaat =
            bedrijfsregelValidatieStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertTrue(stapResultaat);
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(Soortmelding.OVERRULEBAAR, resultaat.getMeldingen().get(0).getSoort());
        Assert.assertEquals(MeldingCode.ALG0001, resultaat.getMeldingen().get(0).getCode());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBerichtOngeldigeActieSoort() {
        bedrijfsRegelManager =
            new BedrijfsRegelManagerImpl(maakBedrijfsRegels(Soortmelding.OVERRULEBAAR, MeldingCode.ALG0001), null,
                    null);
        ReflectionTestUtils.setField(bedrijfsregelValidatieStap, BEDRIJFSREGELMANAGER_FIELDNAME, bedrijfsRegelManager);

        AbstractBijhoudingsBericht bericht = maakBericht();
        ActieBericht actieBericht = (ActieBericht) bericht.getBrpActies().get(0);
        actieBericht.setSoort(SoortActie.DUMMY);
        List<Melding> meldingen = new ArrayList<Melding>();
        BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(meldingen);
        bedrijfsregelValidatieStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);
    }

    @SuppressWarnings("rawtypes")
    @Test
    public void testBerichtWaarGeenHuidigeSituatieNodigIs() {
        ActieBedrijfsRegel actieBedrijfsRegel = Mockito.mock(ActieBedrijfsRegel.class);

        List<ActieBedrijfsRegel> regNat = new ArrayList<ActieBedrijfsRegel>();
        regNat.add(actieBedrijfsRegel);

        HashMap<SoortActie, List<? extends ActieBedrijfsRegel>> bedrijfsRegelsPerSoortActie =
            new HashMap<SoortActie, List<? extends ActieBedrijfsRegel>>();
        bedrijfsRegelsPerSoortActie.put(SoortActie.REGISTRATIE_NATIONALITEIT, regNat);

        bedrijfsRegelManager = new BedrijfsRegelManagerImpl(bedrijfsRegelsPerSoortActie, null, null);
        ReflectionTestUtils.setField(bedrijfsregelValidatieStap, BEDRIJFSREGELMANAGER_FIELDNAME, bedrijfsRegelManager);

        AbstractBijhoudingsBericht bericht = maakBericht();
        ((ActieBericht) bericht.getBrpActies().get(0)).setSoort(SoortActie.REGISTRATIE_NATIONALITEIT);
        BerichtVerwerkingsResultaat resultaat = new BerichtVerwerkingsResultaat(null);
        boolean stapResultaat =
            bedrijfsregelValidatieStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);

        Mockito.verify(actieBedrijfsRegel, Mockito.times(1)).executeer(null, bericht.getBrpActies().get(0).getRootObjecten().get(0), bericht.getBrpActies().get(0));

        Assert.assertTrue(stapResultaat);
        Assert.assertTrue(resultaat.getMeldingen().isEmpty());
    }

    private Map<SoortActie, List<? extends ActieBedrijfsRegel>> maakBedrijfsRegels(final Soortmelding soortMelding,
            final MeldingCode meldingCode)
    {
        List<ActieBedrijfsRegel> regelsVerhuizing = new ArrayList<ActieBedrijfsRegel>();
        regelsVerhuizing.add(new ActieBedrijfsRegel() {

            @Override
            public String getCode() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public List<Melding> executeer(final RootObject testHuidigeSituatiea, final RootObject testNieuweSituatiea,
                    final Actie actie)
            {
                if (soortMelding == null) {
                    return Collections.emptyList();
                }

                List<Melding> meldingen = new ArrayList<Melding>();
                meldingen.add(new Melding(soortMelding, meldingCode));
                return meldingen;
            }
        });

        HashMap<SoortActie, List<? extends ActieBedrijfsRegel>> bedrijfsRegelsPerSoortActie =
            new HashMap<SoortActie, List<? extends ActieBedrijfsRegel>>();

        bedrijfsRegelsPerSoortActie.put(SoortActie.VERHUIZING, regelsVerhuizing);

        return bedrijfsRegelsPerSoortActie;
    }

    private AbstractBijhoudingsBericht maakBericht() {
        ActieBericht actie = new ActieBericht();
        PersoonBericht persoon = new PersoonBericht();
        PersoonAdresBericht nieuwAdres = new PersoonAdresBericht();
        persoon.setAdressen(new ArrayList<PersoonAdresBericht>());
        persoon.getAdressen().add(nieuwAdres);
        persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        persoon.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer("123456782"));
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));
        actie.setSoort(SoortActie.VERHUIZING);

        AbstractBijhoudingsBericht bericht = new AbstractBijhoudingsBericht() {

            @Override
            public Soortbericht getSoortBericht() {
                return null;
            }
        };
        bericht.setBrpActies(Arrays.asList((Actie) actie));
        return bericht;
    }

    // @Test
    // public void testBerichtleegZonderFouten() {
    // BijhoudingsBericht bericht = new VerhuizingBericht();
    // BerichtContext context = new BerichtContext(new BerichtenIds(1L, 1L), 1, new Partij(), "ref");
    // // bericht heeft null overrulare meldingen
    // BerichtResultaat resultaat = new BerichtResultaat(null);
    // // resultaat heeft ook geen meldingen
    //
    // bedrijfsregelValidatieStap.corrigeerVoorOverrulebareFouten(bericht, context, resultaat);
    // Assert.assertEquals(0, resultaat.getMeldingen().size());
    // }
    //
    // @Test
    // public void testBerichtleegZonderMetWarnings() {
    // BijhoudingsBericht bericht = new VerhuizingBericht();
    // BerichtContext context = new BerichtContext(new BerichtenIds(1L, 1L), 1, new Partij(), "ref");
    //
    // // bericht heeft null overrulbare meldingen
    // BerichtResultaat resultaat = new BerichtResultaat(
    // Arrays.asList(new Melding(SoortMelding.INFO, MeldingCode.BRAL0012),
    // new Melding(SoortMelding.INFO, MeldingCode.BRAL2032),
    // new Melding(SoortMelding.WAARSCHUWING, MeldingCode.ALG0001)
    // )
    // );
    // // resultaat heeft meldingen, maar geen fouten of overrulebare fouten.
    //
    // bedrijfsregelValidatieStap.corrigeerVoorOverrulebareFouten(bericht, context, resultaat);
    // Assert.assertEquals(false, resultaat.bevatVerwerkingStoppendeFouten());
    // }
    //
    // @Test
    // public void testBerichtleegMetFouten() {
    // BijhoudingsBericht bericht = new VerhuizingBericht();
    // BerichtContext context = new BerichtContext(new BerichtenIds(1L, 1L), 1, new Partij(), "ref");
    // BerichtResultaat resultaat = new BerichtResultaat(
    // Arrays.asList(new Melding(SoortMelding.INFO, MeldingCode.BRAL0012),
    // new Melding(SoortMelding.INFO, MeldingCode.BRAL2032),
    // new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.AUTH0001),
    // new Melding(SoortMelding.WAARSCHUWING, MeldingCode.ALG0001)
    // )
    // );
    // // resultaat heeft meldingen, maar geen fouten of overrulebare fouten.
    //
    // bedrijfsregelValidatieStap.corrigeerVoorOverrulebareFouten(bericht, context, resultaat);
    // Assert.assertEquals(true, resultaat.bevatVerwerkingStoppendeFouten());
    // }

    // @Test
    // public void testBerichtMetOverruleNull() {
    // BijhoudingsBericht bericht = maakBijhoudingsBericht(MeldingCode.AUTH0001);
    // BerichtContext context = new BerichtContext(new BerichtenIds(1L, 1L), 1, new Partij(), "ref");
    //
    // // bericht heeft null overrulare meldingen
    // BerichtResultaat resultaat = new BerichtResultaat(null
    // );
    //
    // bedrijfsregelValidatieStap.corrigeerVoorOverrulebareFouten(bericht, context, resultaat);
    // // extra bij gekregen nu
    // Assert.assertEquals(true, resultaat.bevatVerwerkingStoppendeFouten());
    // }
    //
    // @Test
    // public void testBerichtMetOverruleMetWaarschuwingen() {
    // BijhoudingsBericht bericht = maakBijhoudingsBericht(MeldingCode.AUTH0001);
    // BerichtContext context = new BerichtContext(new BerichtenIds(1L, 1L), 1, new Partij(), "ref");
    //
    // BerichtResultaat resultaat = new BerichtResultaat(
    // Arrays.asList(new Melding(SoortMelding.INFO, MeldingCode.BRAL0012),
    // new Melding(SoortMelding.INFO, MeldingCode.BRAL2032),
    // // new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.AUTH0001),
    // new Melding(SoortMelding.WAARSCHUWING, MeldingCode.ALG0001)
    // )
    // );
    //
    // bedrijfsregelValidatieStap.corrigeerVoorOverrulebareFouten(bericht, context, resultaat);
    // // extra bij gekregen nu
    // Assert.assertEquals(true, resultaat.bevatVerwerkingStoppendeFouten());
    // }
    //
    // @Test
    // public void testBerichtMetOverrulMetFouten() {
    // BijhoudingsBericht bericht = maakBijhoudingsBericht(MeldingCode.AUTH0001);
    // BerichtContext context = new BerichtContext(new BerichtenIds(1L, 1L), 1, new Partij(), "ref");
    //
    // BerichtResultaat resultaat = new BerichtResultaat(
    // Arrays.asList(new Melding(SoortMelding.INFO, MeldingCode.BRAL0012),
    // new Melding(SoortMelding.INFO, MeldingCode.BRAL2032),
    // new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.AUTH0001),
    // new Melding(SoortMelding.WAARSCHUWING, MeldingCode.ALG0001)
    // )
    // );
    //
    // bedrijfsregelValidatieStap.corrigeerVoorOverrulebareFouten(bericht, context, resultaat);
    // Assert.assertEquals(true, resultaat.bevatVerwerkingStoppendeFouten());
    // }

    // @Test
    // public void testBerichtMetOverrulMetOverrulbareFouten() {
    //
    // // voorlopig is de regelcode bepalend. niet de verzendendId
    // BijhoudingsBericht bericht = maakBijhoudingsBericht(MeldingCode.AUTH0001);
    // BerichtContext context = new BerichtContext(new BerichtenIds(1L, 1L), 1, new Partij(), "ref");
    //
    // BerichtResultaat resultaat = new BerichtResultaat(
    // Arrays.asList(new Melding(SoortMelding.INFO, MeldingCode.BRAL0012),
    // new Melding(SoortMelding.INFO, MeldingCode.BRAL2032),
    // new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.AUTH0001),
    // new Melding(SoortMelding.WAARSCHUWING, MeldingCode.ALG0001)
    // )
    // );
    //
    // bedrijfsregelValidatieStap.corrigeerVoorOverrulebareFouten(bericht, context, resultaat);
    // Assert.assertEquals(false, resultaat.bevatVerwerkingStoppendeFouten());
    // }
    //
    // @Test
    // public void testBerichtMetOverrulMetOverrulbareFouten1() {
    // // voorlopig is de regelcode bepalend. niet de verzendendId
    // BijhoudingsBericht bericht = maakBijhoudingsBericht(MeldingCode.AUTH0001);
    // BerichtContext context = new BerichtContext(new BerichtenIds(1L, 1L), 1, new Partij(), "ref");
    //
    // BerichtResultaat resultaat = new BerichtResultaat(
    // Arrays.asList(new Melding(SoortMelding.INFO, MeldingCode.BRAL0012),
    // new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.AUTH0001),
    // new Melding(SoortMelding.INFO, MeldingCode.BRAL2032),
    // new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.AUTH0001),
    // new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.AUTH0001),
    // new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.BRAL2033),
    // new Melding(SoortMelding.WAARSCHUWING, MeldingCode.ALG0001)
    // )
    // );
    //
    // bedrijfsregelValidatieStap.corrigeerVoorOverrulebareFouten(bericht, context, resultaat);
    // Assert.assertEquals(true, resultaat.bevatVerwerkingStoppendeFouten());
    // }
    //
    // @Test
    // public void testBerichtMetOverrulMetOverrulbareFouten2() {
    // // voorlopig is de regelcode bepalend. niet de verzendendId
    // BijhoudingsBericht bericht = maakBijhoudingsBericht(MeldingCode.AUTH0001);
    // BerichtContext context = new BerichtContext(new BerichtenIds(1L, 1L), 1, new Partij(), "ref");
    //
    // BerichtResultaat resultaat = new BerichtResultaat(
    // Arrays.asList(new Melding(SoortMelding.INFO, MeldingCode.BRAL0012),
    // new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.AUTH0001),
    // new Melding(SoortMelding.INFO, MeldingCode.BRAL2032),
    // new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.AUTH0001),
    // new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.AUTH0001),
    // new Melding(SoortMelding.WAARSCHUWING, MeldingCode.ALG0001)
    // )
    // );
    //
    // bedrijfsregelValidatieStap.corrigeerVoorOverrulebareFouten(bericht, context, resultaat);
    // Assert.assertEquals(false, resultaat.bevatVerwerkingStoppendeFouten());
    // }
    //
    // @Test
    // public void testBerichtMetOverrulMetOverrulbareFouten3() {
    // // voorlopig is de regelcode bepalend. niet de verzendendId
    // BijhoudingsBericht bericht = maakBijhoudingsBericht(MeldingCode.AUTH0001, MeldingCode.ALG0001);
    // BerichtContext context = new BerichtContext(new BerichtenIds(1L, 1L), 1, new Partij(), "ref");
    //
    // BerichtResultaat resultaat = new BerichtResultaat(
    // Arrays.asList(new Melding(SoortMelding.INFO, MeldingCode.BRAL0012),
    // new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.AUTH0001),
    // new Melding(SoortMelding.INFO, MeldingCode.BRAL2032),
    // new Melding(SoortMelding.WAARSCHUWING, MeldingCode.ALG0001)
    // )
    // );
    //
    // bedrijfsregelValidatieStap.corrigeerVoorOverrulebareFouten(bericht, context, resultaat);
    // // er is nog 1 extra overruleMeldig achter gebleven
    // Assert.assertEquals(true, resultaat.bevatVerwerkingStoppendeFouten());
    // }
    //
    // private BijhoudingsBericht maakBijhoudingsBericht(final MeldingCode... meldingCodes) {
    // VerhuizingBericht bericht = new VerhuizingBericht();
    // if (meldingCodes != null) {
    // List<OverruleMelding> overruleMeldingen = new ArrayList<OverruleMelding>();
    // for (MeldingCode meldingCode : meldingCodes) {
    // overruleMeldingen.add(new OverruleMelding(meldingCode.getNaam()));
    // }
    // bericht.setOverruledMeldingen(overruleMeldingen);
    // }
    // return bericht;
    // }

}
