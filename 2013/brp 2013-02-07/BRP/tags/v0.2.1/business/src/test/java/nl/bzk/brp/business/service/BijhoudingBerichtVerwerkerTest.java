/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.bzk.brp.business.dto.BRPBericht;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtResultaat;
import nl.bzk.brp.business.dto.BerichtenIds;
import nl.bzk.brp.business.dto.ResultaatCode;
import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
import nl.bzk.brp.business.dto.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.business.handlers.AbstractBerichtVerwerkingsStap;
import nl.bzk.brp.business.handlers.bijhouding.BijhoudingOverruleMeldingenValidatieStap;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.objecttype.bericht.ActieBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortActie;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortbericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.OverruleMelding;
import nl.bzk.brp.model.validatie.SoortMelding;
import nl.bzk.brp.model.validatie.SoortOverruleMelding;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


/** Unit test voor de bericht verwerker. */
public class BijhoudingBerichtVerwerkerTest {

    private BijhoudingsBerichtVerwerkerImpl berichtVerwerker;

    // stap1 en stap3 zijn mock stappen om te verifieren dat de 1e altijd gebeurt, de 3 soms wel/niet gebeurt.
    @Mock
    private AbstractBerichtVerwerkingsStap<BRPBericht, BerichtResultaat> stap1;

    @Mock
    private AbstractBerichtVerwerkingsStap<BRPBericht, BerichtResultaat> stap3;

    @Mock
    private BerichtResultaatFactory berichtResultaatFactory;

    private BerichtContext berichtContext = null;

    @Test
    @SuppressWarnings("unchecked")
    public void testVerwerkingMeEenCorrectOverrule() {
        // 1 bericht met Overrulebare foutmelding, id1, en deze fout treedt ook op in stap2
        BijhoudingsBericht foutBerichtMetOverrule = maakNieuwStandaardBericht(
                Arrays.asList(
                        maakOverrule("id1", MeldingCode.BRBY0521, null)),
                maakStandaardActie());

        // Conditioneer de berichtverwerker
        berichtVerwerker.setStappen(Arrays.asList(
                stap1,
                maakStapMetFoutResultaten(
                        maaktMelding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.BRBY0521, "id1")),
                stap3));

        BerichtResultaat resultaat = berichtVerwerker.verwerkBericht(foutBerichtMetOverrule, berichtContext);
        Assert.assertEquals(ResultaatCode.GOED, resultaat.getResultaatCode());

        // meldingen bestaat uit 1 fout die gedowngrade is naar waarschuwing
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(SoortMelding.WAARSCHUWING, resultaat.getMeldingen().get(0).getSoort());

        // en de overrule melding uit het bericht is 'gemoved' naar resultaat.overruleMeldingen.
        Assert.assertEquals(1, resultaat.getOverruleMeldingen().size());

        // Alle overrules zijn weg.
        Assert.assertEquals(0, foutBerichtMetOverrule.getOverruledMeldingen().size());

        // stap1 altijd, en stap 3 wel uitgevoerd omdat fout gedowngrade is.
        verifyStap1EnStap3Uitgevoerd(1, 1);
    }


    @Test
    @SuppressWarnings("unchecked")
    public void testVerwerkingMeEenOverruleMetVerkeerdeId() {
        // 1 bericht met Overrulebare foutmelding, id1, en deze fout treedt ook op in stap2
        BijhoudingsBericht foutBerichtMetOverrule = maakNieuwStandaardBericht(
                Arrays.asList(
                    maakOverrule("id1.extra", MeldingCode.BRBY0521, null)),
                maakStandaardActie());

        // Conditioneer de berichtverwerker
        berichtVerwerker.setStappen(Arrays.asList(
                stap1,
                maakStapMetFoutResultaten(
                        maaktMelding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.BRBY0521, "id1")),
                stap3));

        BerichtResultaat resultaat = berichtVerwerker.verwerkBericht(foutBerichtMetOverrule, berichtContext);

        // meldingen bestaat uit 1 fout
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(SoortMelding.FOUT_OVERRULEBAAR, resultaat.getMeldingen().get(0).getSoort());

        // en de overrule melding uit het bericht is daar gebleven
        Assert.assertEquals(0, resultaat.getOverruleMeldingen().size());

        Assert.assertEquals(ResultaatCode.FOUT, resultaat.getResultaatCode());

        // overrules zijn daar gebeleven.
        Assert.assertEquals(1, foutBerichtMetOverrule.getOverruledMeldingen().size());

        // stap1 altijd, en stap 3 NIET uitgevoerd omdat fout niet gedowngrade is.
        verifyStap1EnStap3Uitgevoerd(1, 0);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testVerwerkingMeEenOverruleMetNullId() {
        // 1 bericht met Overrulebare foutmelding, id1, en deze fout treedt ook op in stap2
        BijhoudingsBericht foutBerichtMetOverrule = maakNieuwStandaardBericht(
                Arrays.asList(
                        maakOverrule(null, MeldingCode.BRBY0521, null)),
                maakStandaardActie());

        // Conditioneer de berichtverwerker
        berichtVerwerker.setStappen(Arrays.asList(
                stap1,
                maakStapMetFoutResultaten(
                        maaktMelding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.BRBY0521, "id1")),
                stap3));

        BerichtResultaat resultaat = berichtVerwerker.verwerkBericht(foutBerichtMetOverrule, berichtContext);

        // meldingen bestaat uit 1 fout
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(SoortMelding.FOUT_OVERRULEBAAR, resultaat.getMeldingen().get(0).getSoort());

        // en de overrule melding uit het bericht is daar gebleven
        Assert.assertEquals(0, resultaat.getOverruleMeldingen().size());

        Assert.assertEquals(ResultaatCode.FOUT, resultaat.getResultaatCode());

        // overrules zijn daar gebleven.
        Assert.assertEquals(1, foutBerichtMetOverrule.getOverruledMeldingen().size());

        // stap1 altijd, en stap 3 NIET uitgevoerd omdat fout niet gedowngrade is.
        verifyStap1EnStap3Uitgevoerd(1, 0);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testVerwerkingMeEenVerkeerdCode() {
        // 1 bericht met verkeerde Overrulebare foutmelding, id1, en deze fout treedt ook op in stap2
        BijhoudingsBericht foutBerichtMetOverrule = maakNieuwStandaardBericht(
                Arrays.asList(
                    maakOverrule("id1", MeldingCode.BRBY0531, null)),
                maakStandaardActie());

        // Conditioneer de berichtverwerker
        berichtVerwerker.setStappen(Arrays.asList(
                stap1,
                maakStapMetFoutResultaten(
                        maaktMelding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.BRBY0521, "id1")),
                stap3));

        BerichtResultaat resultaat = berichtVerwerker.verwerkBericht(foutBerichtMetOverrule, berichtContext);

        // meldingen bestaat uit 1 fout
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(SoortMelding.FOUT_OVERRULEBAAR, resultaat.getMeldingen().get(0).getSoort());

        // en de overrule melding uit het bericht is daar gebleven
        Assert.assertEquals(0, resultaat.getOverruleMeldingen().size());

        Assert.assertEquals(ResultaatCode.FOUT, resultaat.getResultaatCode());

        // Alle overrules zijn gebleven.
        Assert.assertEquals(1, foutBerichtMetOverrule.getOverruledMeldingen().size());

        // stap1 altijd, en stap 3 NIET uitgevoerd omdat fout niet gedowngrade is.
        verifyStap1EnStap3Uitgevoerd(1, 0);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testVerwerkingMeTweeOverrules() {
        // 2 bericht met Overrulebare foutmelding, id1, en deze fout treedt ook op in stap2
        BijhoudingsBericht foutBerichtMetOverrule = maakNieuwStandaardBericht(Arrays.asList(
                    maakOverrule("id1", MeldingCode.BRBY0521, null),
                    maakOverrule("id2", MeldingCode.BRBY0521, null)),
                maakStandaardActie());

        // Conditioneer de berichtverwerker
        berichtVerwerker.setStappen(Arrays.asList(
                stap1,
                maakStapMetFoutResultaten(
                        maaktMelding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.BRBY0521, "id1")),
                stap3));

        BerichtResultaat resultaat = berichtVerwerker.verwerkBericht(foutBerichtMetOverrule, berichtContext);

        // meldingen bestaat uit 1 fout
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(SoortMelding.WAARSCHUWING, resultaat.getMeldingen().get(0).getSoort());
        Assert.assertEquals("id1", resultaat.getMeldingen().get(0).getVerzendendId());


        // en de overrule melding uit het bericht is daar gebleven
        Assert.assertEquals(1, resultaat.getOverruleMeldingen().size());

        Assert.assertEquals(ResultaatCode.GOED, resultaat.getResultaatCode());

        // Let op, id2 zit nog steeds in het bericht.
        Assert.assertEquals(1, foutBerichtMetOverrule.getOverruledMeldingen().size());

        // stap1 altijd, en stap 3 WEL uitgevoerd omdat fout wel gedowngrade is (geen test op leftovers).
        verifyStap1EnStap3Uitgevoerd(1, 1);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testVerwerkingMeTweeOverrulesEnTweeOvertredingen() {
        // bericht met Overrulebare foutmeldingen
        // LETOP: de final test (niet geraakte overrulemelding test) zit niet in de lijst van stappen !

        BijhoudingsBericht foutBerichtMetOverrule = maakNieuwStandaardBericht(Arrays.asList(
                    maakOverrule("id1", MeldingCode.BRBY0521, null),
                    maakOverrule("id2", MeldingCode.BRBY0521, null)),
                maakStandaardActie());

        // Conditioneer de berichtverwerker
        berichtVerwerker.setStappen(Arrays.asList(
                stap1,
                maakStapMetFoutResultaten(
                        maaktMelding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.BRBY0521, "id1"),
                        maaktMelding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.BRBY0521, "id2")),
                stap3));

        BerichtResultaat resultaat = berichtVerwerker.verwerkBericht(foutBerichtMetOverrule, berichtContext);

        // meldingen bestaat uit 1 fout
        Assert.assertEquals(2, resultaat.getMeldingen().size());
        Assert.assertEquals(SoortMelding.WAARSCHUWING, resultaat.getMeldingen().get(0).getSoort());
        Assert.assertEquals("id1", resultaat.getMeldingen().get(0).getVerzendendId());
        Assert.assertEquals(SoortMelding.WAARSCHUWING, resultaat.getMeldingen().get(1).getSoort());
        Assert.assertEquals("id2", resultaat.getMeldingen().get(1).getVerzendendId());


        // en de overrule melding uit het bericht is daar gebleven
        Assert.assertEquals(2, resultaat.getOverruleMeldingen().size());

        Assert.assertEquals(ResultaatCode.GOED, resultaat.getResultaatCode());

        // Alle overrules zijn weg.
        Assert.assertEquals(0, foutBerichtMetOverrule.getOverruledMeldingen().size());

        // stap1 altijd, en stap 3 WEL uitgevoerd omdat fout gedowngrade is.
        verifyStap1EnStap3Uitgevoerd(1, 1);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testVerwerkingMeTeWeiningOverrule() {
        // bericht met Overrulebare foutmeldingen
        // LETOP: de final test (niet geraakte overrulemelding test) zit niet in de lijst van stappen !

        BijhoudingsBericht foutBerichtMetOverrule = maakNieuwStandaardBericht(Arrays.asList(
                    maakOverrule("id1", MeldingCode.BRBY0521, null)),
                maakStandaardActie());

        // Conditioneer de berichtverwerker
        berichtVerwerker.setStappen(Arrays.asList(
                stap1,
                maakStapMetFoutResultaten(
                        maaktMelding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.BRBY0521, "id1"),
                        maaktMelding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.BRBY0521, "id2")),
                stap3));

        BerichtResultaat resultaat = berichtVerwerker.verwerkBericht(foutBerichtMetOverrule, berichtContext);

        // meldingen bestaat uit 1 fout, 1 waarschuwing
        Assert.assertEquals(2, resultaat.getMeldingen().size());
        Assert.assertEquals(SoortMelding.FOUT_OVERRULEBAAR, resultaat.getMeldingen().get(0).getSoort());
        Assert.assertEquals("id2", resultaat.getMeldingen().get(0).getVerzendendId());
        Assert.assertEquals(SoortMelding.WAARSCHUWING, resultaat.getMeldingen().get(1).getSoort());
        Assert.assertEquals("id1", resultaat.getMeldingen().get(1).getVerzendendId());

        // en de overrule melding uit het bericht is daar gebleven
        Assert.assertEquals(1, resultaat.getOverruleMeldingen().size());

        Assert.assertEquals(ResultaatCode.FOUT, resultaat.getResultaatCode());

        // Alle overrules zijn weg.
        Assert.assertEquals(0, foutBerichtMetOverrule.getOverruledMeldingen().size());

        // stap1 altijd, en stap 3 NIET uitgevoerd omdat fout niet gedowngrade is.
        verifyStap1EnStap3Uitgevoerd(1, 0);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testVerwerkingMeTweeOverrulesMetLeftoverStap() {
        // 2 bericht met Overrulebare foutmelding, id1, en deze fout treedt ook op in stap2

        BijhoudingsBericht foutBerichtMetOverrule = maakNieuwStandaardBericht(Arrays.asList(
                    maakOverrule("id1", MeldingCode.BRBY0521, null),
                    maakOverrule("id2", MeldingCode.BRBY0525, SoortOverruleMelding.NABEWERKING_VALIDATIE_MELDING)),
                maakStandaardActie());

        // Conditioneer de berichtverwerker
        berichtVerwerker.setStappen(Arrays.asList(
                stap1,
                maakStapMetFoutResultaten(
                        maaktMelding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.BRBY0521, "id1")),
                maakStapVoorOverruleStapWrapper(),
                stap3));

        BerichtResultaat resultaat = berichtVerwerker.verwerkBericht(foutBerichtMetOverrule, berichtContext);

        // meldingen bestaat uit 2 fout
        Assert.assertEquals(2, resultaat.getMeldingen().size());
        Assert.assertEquals(SoortMelding.FOUT_ONOVERRULEBAAR, resultaat.getMeldingen().get(0).getSoort());
        Assert.assertEquals(MeldingCode.ALG0001, resultaat.getMeldingen().get(0).getCode());
        Assert.assertEquals("id2", resultaat.getMeldingen().get(0).getVerzendendId());
        Assert.assertEquals(SoortMelding.WAARSCHUWING, resultaat.getMeldingen().get(1).getSoort());
        Assert.assertEquals("id1", resultaat.getMeldingen().get(1).getVerzendendId());


        // en de overrule melding uit het bericht is daar gebleven
        Assert.assertEquals(1, resultaat.getOverruleMeldingen().size());

        Assert.assertEquals(ResultaatCode.FOUT, resultaat.getResultaatCode());

        // Let op, id2 zit nog steeds in het bericht.
        Assert.assertEquals(1, foutBerichtMetOverrule.getOverruledMeldingen().size());

        // stap1 altijd, en stap 3 WEL uitgevoerd, stap 2a wordt pas uitgevoers op de terugweg.
        verifyStap1EnStap3Uitgevoerd(1, 1);
    }



    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        berichtContext = bouwBerichtContext();
        berichtVerwerker = new BijhoudingsBerichtVerwerkerImpl();
        ReflectionTestUtils.setField(berichtVerwerker, "berichtResultaatFactory", berichtResultaatFactory);

        Mockito.when(berichtResultaatFactory.creeerBerichtResultaat(Matchers.any(BRPBericht.class),
            Matchers.any(BerichtContext.class))).thenReturn(new BerichtResultaat(null));

        Mockito.when(berichtResultaatFactory.creeerBerichtResultaat(Matchers.any(BRPBericht.class),
                Matchers.any(BerichtContext.class))).thenReturn(new BerichtResultaat(null));

        Mockito.when(stap1.voerVerwerkingsStapUitVoorBericht(Matchers.any(BijhoudingsBericht.class),
                Matchers.any(BerichtContext.class), Matchers.any(BerichtResultaat.class))).thenReturn(true);

        Mockito.when(stap3.voerVerwerkingsStapUitVoorBericht(Matchers.any(BijhoudingsBericht.class),
            Matchers.any(BerichtContext.class), Matchers.any(BerichtResultaat.class))).thenReturn(true);
    }

    private Actie maakStandaardActie() {
        ActieBericht actie = new ActieBericht();
        actie.setSoort(SoortActie.VERHUIZING);
        List<RootObject> personen = new ArrayList<RootObject>();
        personen.add(new PersoonBericht());
        actie.setRootObjecten(personen);
        return actie;

    }

    private Melding maaktMelding(final SoortMelding soot, final MeldingCode meldingCode, final String verzId) {
        Melding melding = new Melding(soot, meldingCode, "oms");
        melding.setVerzendendId(verzId);
        return melding;
    }

    private AbstractBerichtVerwerkingsStap<BRPBericht, BerichtResultaat> maakStapMetFoutResultaten(
            final Melding... meldingen)
    {
        AbstractBerichtVerwerkingsStap<BRPBericht, BerichtResultaat> stap =
            new AbstractBerichtVerwerkingsStap<BRPBericht, BerichtResultaat>() {
                @Override
                public boolean voerVerwerkingsStapUitVoorBericht(
                        final BRPBericht bericht, final BerichtContext context,
                        final BerichtResultaat resultaat)
                {
                    if (meldingen != null && meldingen.length > 0) {
                        for (Melding m : meldingen) {
                            resultaat.voegMeldingToe(m);
                        }
                    }

                    return true;
                }

            };
        return stap;
    }

    private void verifyStap1EnStap3Uitgevoerd(final int times1, final int times3) {

        Mockito.verify(stap1, Mockito.times(times1)).voerVerwerkingsStapUitVoorBericht(
                Matchers.any(BijhoudingsBericht.class), Matchers.any(BerichtContext.class),
                Matchers.any(BerichtResultaat.class));

        Mockito.verify(stap3, Mockito.times(times3)).voerVerwerkingsStapUitVoorBericht(
            Matchers.any(BijhoudingsBericht.class), Matchers.any(BerichtContext.class),
            Matchers.any(BerichtResultaat.class));
    }

    private OverruleMelding maakOverrule(final String verzId, final MeldingCode meldingsCode,
            final SoortOverruleMelding soort)
    {
        OverruleMelding melding = new OverruleMelding(meldingsCode);
        melding.setVerzendendId(verzId);
        if (soort != null) {
            // standaard staat hij al goed, NIET op null zetten!
            melding.setSoort(soort);
        }
        return melding;
    }

    /**
     * Instantieert een bericht met de opgegeven acties.
     *
     * @param acties de acties waaruit het bericht dient te bestaan.
     * @return een nieuw bericht met opgegeven acties.
     */
    private BijhoudingsBericht maakNieuwStandaardBericht(final List<OverruleMelding> overrules, final Actie... acties) {
        AbstractBijhoudingsBericht bericht = new AbstractBijhoudingsBericht() {
            @Override
            public Soortbericht getSoortBericht() {
                return null;
            }
        };
        if (overrules != null) {
            for (OverruleMelding ovm : overrules) {
                bericht.getOverruledMeldingen().add(ovm);
            }
        }

        if (acties == null) {
            bericht.setBrpActies(null);
        } else {
            bericht.setBrpActies(Arrays.asList(acties));
        }
        return bericht;
    }

    /**
     * Bouwt en retourneert een standaard {@link nl.bzk.brp.business.dto.BerichtContext} instantie, met ingevulde in-
     * en
     * uitgaande bericht ids, een authenticatiemiddel id en een partij.
     *
     * @return een geldig bericht context.
     */
    private BerichtContext bouwBerichtContext() {
        BerichtenIds ids = new BerichtenIds(2L, 3L);
        Partij partij = new Partij();
        ReflectionTestUtils.setField(partij, "id", Short.valueOf((short) 5));
        return new BerichtContext(ids, 4, partij, "ref");
    }

    private AbstractBerichtVerwerkingsStap<BRPBericht, BerichtResultaat> maakStapVoorOverruleStapWrapper() {
        AbstractBerichtVerwerkingsStap<BRPBericht, BerichtResultaat> stap =
                new AbstractBerichtVerwerkingsStap<BRPBericht, BerichtResultaat>()
            {
                private final BijhoudingOverruleMeldingenValidatieStap echteStap =
                        new BijhoudingOverruleMeldingenValidatieStap();
                @Override
                public boolean voerVerwerkingsStapUitVoorBericht(
                        final BRPBericht bericht, final BerichtContext context,
                        final BerichtResultaat resultaat)
                {
                    return echteStap.voerVerwerkingsStapUitVoorBericht((BijhoudingsBericht) bericht,
                        context, resultaat);
                }

                @Override
                public void naVerwerkingsStapVoorBericht(final BRPBericht bericht, final BerichtContext context,
                        final BerichtResultaat resultaat)
                {
                    echteStap.naVerwerkingsStapVoorBericht((BijhoudingsBericht) bericht, context, resultaat);
                }

            };
        return stap;
    }

}
