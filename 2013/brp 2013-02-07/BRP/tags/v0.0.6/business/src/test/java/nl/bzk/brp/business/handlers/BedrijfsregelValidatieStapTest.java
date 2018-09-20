/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.handlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import nl.bzk.brp.binding.BerichtResultaat;
import nl.bzk.brp.binding.Melding;
import nl.bzk.brp.binding.MeldingCode;
import nl.bzk.brp.binding.SoortMelding;
import nl.bzk.brp.binding.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegel;
import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegelManager;
import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegelManagerImpl;
import nl.bzk.brp.business.handlers.bijhouding.BedrijfsregelValidatieStap;
import nl.bzk.brp.dataaccess.repository.PersoonAdresRepository;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.PersistentRootObject;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.gedeeld.SoortActie;
import nl.bzk.brp.model.logisch.BRPActie;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonAdres;
import nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoon;
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
    private PersoonRepository persoonRepository;

    @Mock
    private PersoonAdresRepository persoonAdresRepository;

    @Mock
    private BedrijfsRegelManager bedrijfsRegelManager;

    @Before
    public void init() {
        bedrijfsregelValidatieStap = new BedrijfsregelValidatieStap();

        Mockito.when(persoonRepository.findByBurgerservicenummer(Matchers.anyString())).thenReturn(
            new PersistentPersoon());

        ReflectionTestUtils.setField(bedrijfsregelValidatieStap, "persoonRepository", persoonRepository);
    }

    @Test
    public void testBerichtGeldig() {
        Mockito.when(persoonAdresRepository.isIemandIngeschrevenOpAdres(Matchers.any(PersoonAdres.class))).thenReturn(
            false);
        ReflectionTestUtils.setField(bedrijfsregelValidatieStap, BEDRIJFSREGELMANAGER_FIELDNAME, bedrijfsRegelManager);

        bedrijfsRegelManager = new BedrijfsRegelManagerImpl(maakBedrijfsRegels(null, null));

        BijhoudingsBericht bericht = maakBericht();
        BerichtResultaat resultaat = new BerichtResultaat(null);
        boolean stapResultaat =
            bedrijfsregelValidatieStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertTrue(stapResultaat);
        Assert.assertNull(resultaat.getMeldingen());
    }

    @Test
    public void testBerichtOngeldigAntwoordMetWaarschuwing() {
        bedrijfsRegelManager =
            new BedrijfsRegelManagerImpl(maakBedrijfsRegels(SoortMelding.WAARSCHUWING, MeldingCode.ALG0001));
        ReflectionTestUtils.setField(bedrijfsregelValidatieStap, BEDRIJFSREGELMANAGER_FIELDNAME, bedrijfsRegelManager);

        BijhoudingsBericht bericht = maakBericht();
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

        BijhoudingsBericht bericht = maakBericht();
        BerichtResultaat resultaat = new BerichtResultaat(new ArrayList<Melding>());
        boolean stapResultaat =
            bedrijfsregelValidatieStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertFalse(stapResultaat);
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(SoortMelding.FOUT_ONOVERRULEBAAR, resultaat.getMeldingen().get(0).getSoort());
        Assert.assertEquals(MeldingCode.ALG0001, resultaat.getMeldingen().get(0).getCode());
    }

    @Test
    public void testBerichtOngeldigAntwoordMetFoutOverRuleBaar() {
        bedrijfsRegelManager =
            new BedrijfsRegelManagerImpl(maakBedrijfsRegels(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.ALG0001));
        ReflectionTestUtils.setField(bedrijfsregelValidatieStap, BEDRIJFSREGELMANAGER_FIELDNAME, bedrijfsRegelManager);

        BijhoudingsBericht bericht = maakBericht();
        BerichtResultaat resultaat = new BerichtResultaat(null);
        boolean stapResultaat =
            bedrijfsregelValidatieStap.voerVerwerkingsStapUitVoorBericht(bericht, bouwBerichtContext(), resultaat);

        Assert.assertFalse(stapResultaat);
        Assert.assertEquals(1, resultaat.getMeldingen().size());
        Assert.assertEquals(SoortMelding.FOUT_OVERRULEBAAR, resultaat.getMeldingen().get(0).getSoort());
        Assert.assertEquals(MeldingCode.ALG0001, resultaat.getMeldingen().get(0).getCode());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBerichtOngeldigeActieSoort() {
        bedrijfsRegelManager =
            new BedrijfsRegelManagerImpl(maakBedrijfsRegels(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.ALG0001));
        ReflectionTestUtils.setField(bedrijfsregelValidatieStap, BEDRIJFSREGELMANAGER_FIELDNAME, bedrijfsRegelManager);

        BijhoudingsBericht bericht = maakBericht();
        bericht.getBrpActies().get(0).setSoort(SoortActie.DUMMY);
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
            public Melding executeer(final PersistentRootObject testHuidigeSituatiea,
                final RootObject testNieuweSituatiea)
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

    private BijhoudingsBericht maakBericht() {
        BRPActie actie = new BRPActie();
        Persoon persoon = new Persoon();
        PersoonAdres nieuwAdres = new PersoonAdres();
        persoon.setAdressen(new HashSet<PersoonAdres>());
        persoon.getAdressen().add(nieuwAdres);
        persoon.setIdentificatienummers(new PersoonIdentificatienummers());
        persoon.getIdentificatienummers().setBurgerservicenummer("123456782");
        actie.setRootObjecten(Arrays.asList((RootObject) persoon));
        actie.setSoort(SoortActie.VERHUIZING);

        BijhoudingsBericht bericht = new BijhoudingsBericht();
        bericht.setBrpActies(Arrays.asList(actie));
        return bericht;
    }

}
