/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.handlers.bijhouding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegel;
import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegelManager;
import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegelManagerImpl;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtResultaat;
import nl.bzk.brp.business.dto.BerichtenIds;
import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.business.dto.bijhouding.VerhuizingBericht;
import nl.bzk.brp.business.handlers.AbstractStapTest;
import nl.bzk.brp.dataaccess.repository.PersoonMdlRepository;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.groep.bericht.PersoonIdentificatieNummersGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.ActieBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonAdresBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.basis.AbstractPersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortActie;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.OverruleMelding;
import nl.bzk.brp.model.validatie.SoortMelding;
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

    private static final String BEDRIJFSREGELMANAGER_FIELDNAME = "bedrijfsRegelManager";

    private BedrijfsregelValidatieStap bedrijfsregelValidatieStap;

    @Mock
    private PersoonMdlRepository persoonRepository;

    @Mock
    private BedrijfsRegelManager bedrijfsRegelManager;

    @Before
    public void init() {
        bedrijfsregelValidatieStap = new BedrijfsregelValidatieStap();

        Mockito.when(persoonRepository.findByBurgerservicenummer((Burgerservicenummer) Matchers.anyObject())).thenReturn(
            new PersoonModel(new AbstractPersoonModel() {
            }));

        ReflectionTestUtils.setField(bedrijfsregelValidatieStap, "persoonRepository", persoonRepository);
    }

    @Test
    public void testBerichtGeldig() {
        ReflectionTestUtils.setField(bedrijfsregelValidatieStap, BEDRIJFSREGELMANAGER_FIELDNAME, bedrijfsRegelManager);

        bedrijfsRegelManager = new BedrijfsRegelManagerImpl(maakBedrijfsRegels(null, null));

        AbstractBijhoudingsBericht bericht = maakBericht();
        BerichtResultaat resultaat = new BerichtResultaat(null);
        boolean stapResultaat =
            bedrijfsregelValidatieStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertTrue(stapResultaat);
        Assert.assertTrue(resultaat.getMeldingen().isEmpty());
    }

    @Test
    public void testBerichtOngeldigAntwoordMetWaarschuwing() {
        bedrijfsRegelManager =
            new BedrijfsRegelManagerImpl(maakBedrijfsRegels(SoortMelding.WAARSCHUWING, MeldingCode.ALG0001));
        ReflectionTestUtils.setField(bedrijfsregelValidatieStap, BEDRIJFSREGELMANAGER_FIELDNAME, bedrijfsRegelManager);

        AbstractBijhoudingsBericht bericht = maakBericht();
        BerichtResultaat resultaat = new BerichtResultaat(null);
        boolean stapResultaat =
            bedrijfsregelValidatieStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertTrue(stapResultaat);
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(SoortMelding.WAARSCHUWING, resultaat.getMeldingen().get(0).getSoort());
        Assert.assertEquals(MeldingCode.ALG0001, resultaat.getMeldingen().get(0).getCode());

    }

    @Test
    public void testBerichtOngeldigAntwoordMetFoutOnoverRuleBaar() {
        bedrijfsRegelManager =
            new BedrijfsRegelManagerImpl(maakBedrijfsRegels(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0001));
        ReflectionTestUtils.setField(bedrijfsregelValidatieStap, BEDRIJFSREGELMANAGER_FIELDNAME, bedrijfsRegelManager);

        AbstractBijhoudingsBericht bericht = maakBericht();
        BerichtResultaat resultaat = new BerichtResultaat(new ArrayList<Melding>());
        boolean stapResultaat =
            bedrijfsregelValidatieStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertTrue(stapResultaat);
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(SoortMelding.FOUT_ONOVERRULEBAAR, resultaat.getMeldingen().get(0).getSoort());
        Assert.assertEquals(MeldingCode.ALG0001, resultaat.getMeldingen().get(0).getCode());
    }

    @Test
    public void testBerichtOngeldigAntwoordMetFoutOverRuleBaar() {
        bedrijfsRegelManager =
            new BedrijfsRegelManagerImpl(maakBedrijfsRegels(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.ALG0001));
        ReflectionTestUtils.setField(bedrijfsregelValidatieStap, BEDRIJFSREGELMANAGER_FIELDNAME, bedrijfsRegelManager);

        AbstractBijhoudingsBericht bericht = maakBericht();
        BerichtResultaat resultaat = new BerichtResultaat(null);
        boolean stapResultaat =
            bedrijfsregelValidatieStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertTrue(stapResultaat);
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(SoortMelding.FOUT_OVERRULEBAAR, resultaat.getMeldingen().get(0).getSoort());
        Assert.assertEquals(MeldingCode.ALG0001, resultaat.getMeldingen().get(0).getCode());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBerichtOngeldigeActieSoort() {
        bedrijfsRegelManager =
            new BedrijfsRegelManagerImpl(maakBedrijfsRegels(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.ALG0001));
        ReflectionTestUtils.setField(bedrijfsregelValidatieStap, BEDRIJFSREGELMANAGER_FIELDNAME, bedrijfsRegelManager);

        AbstractBijhoudingsBericht bericht = maakBericht();
        ActieBericht actieBericht = (ActieBericht) bericht.getBrpActies().get(0);
        actieBericht.setSoort(SoortActie.DUMMY);
        List<Melding> meldingen = new ArrayList<Melding>();
        BerichtResultaat resultaat = new BerichtResultaat(meldingen);
        bedrijfsregelValidatieStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);
    }

    private Map<SoortActie, List<? extends BedrijfsRegel>> maakBedrijfsRegels(final SoortMelding soortMelding,
        final MeldingCode meldingCode)
    {
        List<BedrijfsRegel> regelsVerhuizing = new ArrayList<BedrijfsRegel>();
        regelsVerhuizing.add(new BedrijfsRegel() {

            @Override
            public String getCode() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override
            public Melding executeer(final RootObject testHuidigeSituatiea,
                final RootObject testNieuweSituatiea, final Datum datumAanvangGeldigheid)
            {
                if (soortMelding == null) {
                    return null;
                }

                return new Melding(soortMelding, meldingCode);
            }
        });

        HashMap<SoortActie, List<? extends BedrijfsRegel>> bedrijfsRegelsPerSoortActie =
            new HashMap<SoortActie, List<? extends BedrijfsRegel>>();

        bedrijfsRegelsPerSoortActie.put(SoortActie.VERHUIZING, regelsVerhuizing);

        return bedrijfsRegelsPerSoortActie;
    }

    private AbstractBijhoudingsBericht maakBericht() {
        ActieBericht actie = new ActieBericht();
        PersoonBericht persoon = new PersoonBericht();
        PersoonAdresBericht nieuwAdres = new PersoonAdresBericht();
        persoon.setAdressen(new ArrayList<PersoonAdresBericht>());
        persoon.getAdressen().add(nieuwAdres);
        persoon.setIdentificatieNummers(new PersoonIdentificatieNummersGroepBericht());
        persoon.getIdentificatieNummers().setBurgerServiceNummer(new Burgerservicenummer("123456782"));
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));
        actie.setSoort(SoortActie.VERHUIZING);

        AbstractBijhoudingsBericht bericht = new AbstractBijhoudingsBericht() { };
        bericht.setBrpActies(Arrays.asList((Actie) actie));
        return bericht;
    }

    @Test
    public void testBerichtleegZonderFouten() {
        AbstractBijhoudingsBericht bericht = new VerhuizingBericht();
        BerichtContext context = new BerichtContext(new BerichtenIds(1L, 1L), 1, new Partij(), "ref");
        // bericht heeft null overrulare meldingen
        BerichtResultaat resultaat = new BerichtResultaat(null);
        // resultaat heeft ook geen meldingen

        bedrijfsregelValidatieStap.corrigeerVoorOverrulebareFouten(bericht, context, resultaat);
        Assert.assertEquals(0,  resultaat.getMeldingen().size());
    }

    @Test
    public void testBerichtleegZonderMetWarnings() {
        AbstractBijhoudingsBericht bericht = new VerhuizingBericht();
        BerichtContext context = new BerichtContext(new BerichtenIds(1L, 1L), 1, new Partij(), "ref");
        // bericht heeft null overrulbare meldingen
        BerichtResultaat resultaat = new BerichtResultaat(
            Arrays.asList(new Melding(SoortMelding.INFO, MeldingCode.BRAL0012),
                    new Melding(SoortMelding.INFO, MeldingCode.BRAL2032),
                    new Melding(SoortMelding.WAARSCHUWING, MeldingCode.ALG0001)
            )
        );
        // resultaat heeft meldingen, maar geen fouten of overrulebare fouten.

        bedrijfsregelValidatieStap.corrigeerVoorOverrulebareFouten(bericht, context, resultaat);
        Assert.assertEquals(false,  resultaat.bevatVerwerkingStoppendeFouten());
    }

    @Test
    public void testBerichtleegMetFouten() {
        AbstractBijhoudingsBericht bericht = new VerhuizingBericht();
        BerichtContext context = new BerichtContext(new BerichtenIds(1L, 1L), 1, new Partij(), "ref");
        BerichtResultaat resultaat = new BerichtResultaat(
            Arrays.asList(new Melding(SoortMelding.INFO, MeldingCode.BRAL0012),
                    new Melding(SoortMelding.INFO, MeldingCode.BRAL2032),
                    new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.AUTH0001),
                    new Melding(SoortMelding.WAARSCHUWING, MeldingCode.ALG0001)
            )
        );
        // resultaat heeft meldingen, maar geen fouten of overrulebare fouten.

        bedrijfsregelValidatieStap.corrigeerVoorOverrulebareFouten(bericht, context, resultaat);
        Assert.assertEquals(true,  resultaat.bevatVerwerkingStoppendeFouten());
    }

    @Test
    public void testBerichtMetOverruleNull() {
        AbstractBijhoudingsBericht bericht = new VerhuizingBericht();
        BerichtContext context = new BerichtContext(new BerichtenIds(1L, 1L), 1, new Partij(), "ref");
        bericht.setOverruledMeldingen(Arrays.asList(
                new OverruleMelding(MeldingCode.AUTH0001.getNaam())
        ));
        // bericht heeft null overrulare meldingen
        BerichtResultaat resultaat = new BerichtResultaat(null
//            Arrays.asList(new Melding(SoortMelding.INFO, MeldingCode.BRAL0012),
//                    new Melding(SoortMelding.INFO, MeldingCode.BRAL2032),
//                    new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.AUTH0001),
//                    new Melding(SoortMelding.WAARSCHUWING, MeldingCode.ALG0001)
//            )
        );

        bedrijfsregelValidatieStap.corrigeerVoorOverrulebareFouten(bericht, context, resultaat);
        // extra bij gekregen nu
        Assert.assertEquals(true,  resultaat.bevatVerwerkingStoppendeFouten());
    }

    @Test
    public void testBerichtMetOverruleMetWaarschuwingen() {
        AbstractBijhoudingsBericht bericht = new VerhuizingBericht();
        BerichtContext context = new BerichtContext(new BerichtenIds(1L, 1L), 1, new Partij(), "ref");
        bericht.setOverruledMeldingen(Arrays.asList(
                new OverruleMelding(MeldingCode.AUTH0001.getNaam())
        ));
        BerichtResultaat resultaat = new BerichtResultaat(
            Arrays.asList(new Melding(SoortMelding.INFO, MeldingCode.BRAL0012),
                    new Melding(SoortMelding.INFO, MeldingCode.BRAL2032),
//                    new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.AUTH0001),
                    new Melding(SoortMelding.WAARSCHUWING, MeldingCode.ALG0001)
            )
        );

        bedrijfsregelValidatieStap.corrigeerVoorOverrulebareFouten(bericht, context, resultaat);
        // extra bij gekregen nu
        Assert.assertEquals(true,  resultaat.bevatVerwerkingStoppendeFouten());
    }

    @Test
    public void testBerichtMetOverrulMetFouten() {
        AbstractBijhoudingsBericht bericht = new VerhuizingBericht();
        BerichtContext context = new BerichtContext(new BerichtenIds(1L, 1L), 1, new Partij(), "ref");
        bericht.setOverruledMeldingen(Arrays.asList(
                new OverruleMelding(MeldingCode.AUTH0001.getNaam())
        ));
        BerichtResultaat resultaat = new BerichtResultaat(
            Arrays.asList(new Melding(SoortMelding.INFO, MeldingCode.BRAL0012),
                    new Melding(SoortMelding.INFO, MeldingCode.BRAL2032),
                    new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.AUTH0001),
                    new Melding(SoortMelding.WAARSCHUWING, MeldingCode.ALG0001)
            )
        );

        bedrijfsregelValidatieStap.corrigeerVoorOverrulebareFouten(bericht, context, resultaat);
        Assert.assertEquals(true,  resultaat.bevatVerwerkingStoppendeFouten());
    }

    @Test
    public void testBerichtMetOverrulMetOverrulbareFouten() {

        // voorlopig is de regelcode bepalend. niet de verzendendId
        AbstractBijhoudingsBericht bericht = new VerhuizingBericht();
        BerichtContext context = new BerichtContext(new BerichtenIds(1L, 1L), 1, new Partij(), "ref");
        bericht.setOverruledMeldingen(Arrays.asList(
                new OverruleMelding(MeldingCode.AUTH0001.getNaam())
        ));
        BerichtResultaat resultaat = new BerichtResultaat(
            Arrays.asList(new Melding(SoortMelding.INFO, MeldingCode.BRAL0012),
                    new Melding(SoortMelding.INFO, MeldingCode.BRAL2032),
                    new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.AUTH0001),
                    new Melding(SoortMelding.WAARSCHUWING, MeldingCode.ALG0001)
            )
        );

        bedrijfsregelValidatieStap.corrigeerVoorOverrulebareFouten(bericht, context, resultaat);
        Assert.assertEquals(false,  resultaat.bevatVerwerkingStoppendeFouten());
    }

    @Test
    public void testBerichtMetOverrulMetOverrulbareFouten1() {
        // voorlopig is de regelcode bepalend. niet de verzendendId
        AbstractBijhoudingsBericht bericht = new VerhuizingBericht();
        BerichtContext context = new BerichtContext(new BerichtenIds(1L, 1L), 1, new Partij(), "ref");
        bericht.setOverruledMeldingen(Arrays.asList(
                new OverruleMelding(MeldingCode.AUTH0001.getNaam())
        ));
        BerichtResultaat resultaat = new BerichtResultaat(
            Arrays.asList(new Melding(SoortMelding.INFO, MeldingCode.BRAL0012),
                    new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.AUTH0001),
                    new Melding(SoortMelding.INFO, MeldingCode.BRAL2032),
                    new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.AUTH0001),
                    new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.AUTH0001),
                    new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.BRAL2033),
                    new Melding(SoortMelding.WAARSCHUWING, MeldingCode.ALG0001)
            )
        );

        bedrijfsregelValidatieStap.corrigeerVoorOverrulebareFouten(bericht, context, resultaat);
        Assert.assertEquals(true,  resultaat.bevatVerwerkingStoppendeFouten());
    }

    @Test
    public void testBerichtMetOverrulMetOverrulbareFouten2() {
        // voorlopig is de regelcode bepalend. niet de verzendendId
        AbstractBijhoudingsBericht bericht = new VerhuizingBericht();
        BerichtContext context = new BerichtContext(new BerichtenIds(1L, 1L), 1, new Partij(), "ref");
        bericht.setOverruledMeldingen(Arrays.asList(
                new OverruleMelding(MeldingCode.AUTH0001.getNaam())
        ));
        BerichtResultaat resultaat = new BerichtResultaat(
            Arrays.asList(new Melding(SoortMelding.INFO, MeldingCode.BRAL0012),
                    new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.AUTH0001),
                    new Melding(SoortMelding.INFO, MeldingCode.BRAL2032),
                    new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.AUTH0001),
                    new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.AUTH0001),
                    new Melding(SoortMelding.WAARSCHUWING, MeldingCode.ALG0001)
            )
        );

        bedrijfsregelValidatieStap.corrigeerVoorOverrulebareFouten(bericht, context, resultaat);
        Assert.assertEquals(false,  resultaat.bevatVerwerkingStoppendeFouten());
    }

    @Test
    public void testBerichtMetOverrulMetOverrulbareFouten3() {
        // voorlopig is de regelcode bepalend. niet de verzendendId
        AbstractBijhoudingsBericht bericht = new VerhuizingBericht();
        BerichtContext context = new BerichtContext(new BerichtenIds(1L, 1L), 1, new Partij(), "ref");
        bericht.setOverruledMeldingen(Arrays.asList(
                new OverruleMelding(MeldingCode.AUTH0001.getNaam()),
                new OverruleMelding(MeldingCode.ALG0001.getNaam())
        ));
        BerichtResultaat resultaat = new BerichtResultaat(
            Arrays.asList(new Melding(SoortMelding.INFO, MeldingCode.BRAL0012),
                    new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.AUTH0001),
                    new Melding(SoortMelding.INFO, MeldingCode.BRAL2032),
                    new Melding(SoortMelding.WAARSCHUWING, MeldingCode.ALG0001)
            )
        );

        bedrijfsregelValidatieStap.corrigeerVoorOverrulebareFouten(bericht, context, resultaat);
        // er is nog 1 extra overruleMeldig achter gebleven
        Assert.assertEquals(true,  resultaat.bevatVerwerkingStoppendeFouten());
    }

}
