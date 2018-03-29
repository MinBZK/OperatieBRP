/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Bericht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Voorkomen;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonInschrijvingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Lo3BerichtenBron;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.Sleutel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.DeltaEntiteitPaar;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.EntiteitSleutel;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test voor {@link DeltaBepalingContext}.
 */
public class DeltaBepalingContextTest extends AbstractDeltaTest {

    private Persoon bestaandPersoon;
    private Persoon nieuwPersoon;
    private Lo3Bericht lo3Bericht;
    private DeltaBepalingContext context;
    private AdministratieveHandeling administratieveHandeling;

    @Before
    public void setUp() {
        bestaandPersoon = maakPersoon(true);
        nieuwPersoon = maakPersoon(false);
        administratieveHandeling =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(nieuwPersoon.getPersoonAfgeleidAdministratiefHistorieSet())
                        .getAdministratieveHandeling();
        lo3Bericht = new Lo3Bericht("referentie", Lo3BerichtenBron.INITIELE_VULLING, Timestamp.valueOf("2005-01-01 00:00:00.000"), "data", true);
        context = new DeltaBepalingContext(nieuwPersoon, bestaandPersoon, lo3Bericht, false);
    }

    @Test
    public void testConstructor() {
        assertEquals(bestaandPersoon, context.getBestaandePersoon());
        assertEquals(nieuwPersoon, context.getNieuwePersoon());
        assertEquals(lo3Bericht, context.getLo3Bericht());

        final BRPActie actieVervalTbvLeveringMuts = context.getActieVervalTbvLeveringMuts();
        assertNotNull(actieVervalTbvLeveringMuts);
        assertEquals(SoortActie.CONVERSIE_GBA, actieVervalTbvLeveringMuts.getSoortActie());
        assertEquals(administratieveHandeling.getPartij(), actieVervalTbvLeveringMuts.getPartij());
        assertEquals(administratieveHandeling.getDatumTijdRegistratie(), actieVervalTbvLeveringMuts.getDatumTijdRegistratie());

        assertEquals(administratieveHandeling, context.getAdministratieveHandelingGekoppeldAanActies());
        assertTrue(context.getActieHerkomstMap().isEmpty());
        assertTrue(context.getDeltaEntiteitPaarSet().isEmpty());
        assertTrue(context.getDeltaRootEntiteitMatches().isEmpty());
        assertNull(context.getActieConsolidatieData());
    }

    @Test
    public void testUpdateBijhoudingSoort() {
        final AdministratieveHandeling administratieveHandeling = context.getAdministratieveHandelingGekoppeldAanActies();
        assertEquals(SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL, administratieveHandeling.getSoort());
        assertTrue(context.isBijhoudingActueel());
        assertFalse(context.isBijhoudingOverig());
        assertFalse(isInfrastructureleWijziging(context));
        assertFalse(isAfvoeren(context));
        assertFalse(isAnummerWijziging(context));

        context.setBijhoudingInfrastructureleWijziging();
        assertEquals(SoortAdministratieveHandeling.GBA_INFRASTRUCTURELE_WIJZIGING, administratieveHandeling.getSoort());
        assertFalse(context.isBijhoudingActueel());
        assertFalse(context.isBijhoudingOverig());
        assertTrue(isInfrastructureleWijziging(context));
        assertFalse(isAfvoeren(context));
        assertFalse(isAnummerWijziging(context));

        context.setBijhoudingOverig();
        assertEquals(SoortAdministratieveHandeling.GBA_BIJHOUDING_OVERIG, administratieveHandeling.getSoort());
        assertFalse(context.isBijhoudingActueel());
        assertTrue(context.isBijhoudingOverig());
        assertFalse(isInfrastructureleWijziging(context));
        assertFalse(isAfvoeren(context));
        assertFalse(isAnummerWijziging(context));

        context.setBijhoudingInfrastructureleWijziging();
        assertEquals(SoortAdministratieveHandeling.GBA_BIJHOUDING_OVERIG, administratieveHandeling.getSoort());
        assertFalse(context.isBijhoudingActueel());
        assertTrue(context.isBijhoudingOverig());
        assertFalse(isInfrastructureleWijziging(context));
        assertFalse(isAfvoeren(context));
        assertFalse(isAnummerWijziging(context));

        context.setBijhoudingAfgevoerd();
        assertEquals(SoortAdministratieveHandeling.GBA_AFVOEREN_PL, administratieveHandeling.getSoort());
        assertTrue(isAfvoeren(context));
        assertFalse(context.isBijhoudingActueel());
        assertFalse(context.isBijhoudingOverig());
        assertFalse(isInfrastructureleWijziging(context));
        assertFalse(isAnummerWijziging(context));

        context.setBijhoudingOverig();
        assertEquals(SoortAdministratieveHandeling.GBA_AFVOEREN_PL, administratieveHandeling.getSoort());
        assertTrue(isAfvoeren(context));
        assertFalse(context.isBijhoudingActueel());
        assertFalse(context.isBijhoudingOverig());
        assertFalse(isInfrastructureleWijziging(context));
        assertFalse(isAnummerWijziging(context));

        context.setBijhoudingInfrastructureleWijziging();
        assertEquals(SoortAdministratieveHandeling.GBA_AFVOEREN_PL, administratieveHandeling.getSoort());
        assertTrue(isAfvoeren(context));
        assertFalse(context.isBijhoudingActueel());
        assertFalse(context.isBijhoudingOverig());
        assertFalse(isInfrastructureleWijziging(context));
        assertFalse(isAnummerWijziging(context));
    }

    @Test
    public void testBijhoudingAnummerWijzigingNaarOverig() {
        final AdministratieveHandeling administratieveHandeling = context.getAdministratieveHandelingGekoppeldAanActies();
        assertEquals(SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL, administratieveHandeling.getSoort());
        assertTrue(context.isBijhoudingActueel());
        assertFalse(context.isBijhoudingOverig());
        assertFalse(isAnummerWijziging(context));
        assertEquals(1, context.getAdministratieveHandelingen().size());

        context.setBijhoudingAnummerWijziging();
        assertTrue(isAnummerWijziging(context));
        assertFalse(context.isBijhoudingActueel());
        assertFalse(context.isBijhoudingOverig());
        assertEquals(1, context.getAdministratieveHandelingen().size());

        context.setBijhoudingOverig();
        assertTrue(context.isBijhoudingOverig());
        assertFalse(context.isBijhoudingActueel());
        assertFalse(isAnummerWijziging(context));
        assertEquals(2, context.getAdministratieveHandelingen().size());

        context.setBijhoudingAnummerWijziging();
        assertEquals(2, context.getAdministratieveHandelingen().size());
    }

    @Test
    public void testBijhoudingAnummerWijzigingNaarAfgevoerd() {
        final AdministratieveHandeling administratieveHandeling = context.getAdministratieveHandelingGekoppeldAanActies();
        assertEquals(SoortAdministratieveHandeling.GBA_BIJHOUDING_ACTUEEL, administratieveHandeling.getSoort());
        assertTrue(context.isBijhoudingActueel());
        assertFalse(isAfvoeren(context));
        assertFalse(isAnummerWijziging(context));
        assertEquals(1, context.getAdministratieveHandelingen().size());

        context.setBijhoudingAnummerWijziging();
        assertTrue(isAnummerWijziging(context));
        assertFalse(context.isBijhoudingActueel());
        assertFalse(isAfvoeren(context));
        assertEquals(1, context.getAdministratieveHandelingen().size());

        context.setBijhoudingAfgevoerd();
        assertTrue(isAfvoeren(context));
        assertFalse(context.isBijhoudingActueel());
        assertFalse(isAnummerWijziging(context));
        assertEquals(2, context.getAdministratieveHandelingen().size());

        context.setBijhoudingAnummerWijziging();
        assertEquals(2, context.getAdministratieveHandelingen().size());
    }

    @Test
    public void testAddActieHerkomstMapInhoud() {
        assertTrue(context.getActieHerkomstMap().isEmpty());
        final BRPActie brpActie = maakBrpActie(administratieveHandeling, Timestamp.valueOf("2005-01-01 00:00:00.000"));
        final Lo3Voorkomen lo3Voorkomen = new Lo3Voorkomen(lo3Bericht, "01");
        final Map<BRPActie, Lo3Voorkomen> map = new HashMap<>();
        map.put(brpActie, lo3Voorkomen);
        context.addActieHerkomstMapInhoud(map);

        final Map<BRPActie, Lo3Voorkomen> actualMap = context.getActieHerkomstMap();
        assertFalse(actualMap.isEmpty());
        assertTrue(actualMap.containsKey(brpActie));
        assertEquals(lo3Voorkomen, actualMap.get(brpActie));
    }

    @Test
    public void testAddActieConsolidatieData() {
        final BRPActie brpActie = maakBrpActie(administratieveHandeling, Timestamp.valueOf("2005-01-01 00:00:00.000"));
        final BRPActie brpActie2 = maakBrpActie(administratieveHandeling, Timestamp.valueOf("2008-01-01 00:00:00.000"));

        final ConsolidatieData actieConsolidatieData = context.getActieConsolidatieData();
        assertNull(actieConsolidatieData);
        final ActieConsolidatieData data = new ActieConsolidatieData();
        data.koppelNieuweActieMetOudeActie(brpActie, brpActie2);
        context.addAllActieConsolidatieData(data);
        final ConsolidatieData contextActieConsolidatieData = context.getActieConsolidatieData();
        assertNotNull(contextActieConsolidatieData);
        assertEquals(
                "{BRPActie[id=<null>,soortActie=CONVERSIE_GBA,administratieveHandeling=AdministratieveHandeling[id=<null>,soort=GBA_BIJHOUDING_ACTUEEL],"
                        + "partij=Partij[id=<null>,code=051801,naam=Gemeente 's-Gravenhage],datumTijdRegistratie=2005-01-01 00:00:00.0,datumOntlening=<null>,"
                        + "lo3Voorkomen=<null>]=[BRPActie[id=<null>,soortActie=CONVERSIE_GBA,administratieveHandeling=AdministratieveHandeling[id=<null>,"
                        + "soort=GBA_BIJHOUDING_ACTUEEL],partij=Partij[id=<null>,code=051801,naam=Gemeente 's-Gravenhage],datumTijdRegistratie=2008-01-01 "
                        + "00:00:00.0,datumOntlening=<null>,lo3Voorkomen=<null>]]}",
                contextActieConsolidatieData.toString());
    }

    @Test
    public void testDeltaRootEntiteitMatches() {
        assertTrue(context.getDeltaRootEntiteitMatches().isEmpty());
        context.setDeltaRootEntiteitMatches(Collections.singleton(new DeltaRootEntiteitMatch(bestaandPersoon, nieuwPersoon, null, null)));
        final Collection<DeltaRootEntiteitMatch> deltaRootEntiteitMatches = context.getDeltaRootEntiteitMatches();
        assertTrue(deltaRootEntiteitMatches.size() == 1);
        assertEquals(bestaandPersoon, deltaRootEntiteitMatches.iterator().next().getBestaandeRootEntiteit());

        context.setDeltaRootEntiteitMatches(Collections.singleton(new DeltaRootEntiteitMatch(nieuwPersoon, bestaandPersoon, null, null)));
        final Collection<DeltaRootEntiteitMatch> deltaRootEntiteitMatches2 = context.getDeltaRootEntiteitMatches();
        assertTrue(deltaRootEntiteitMatches.size() == 1);
        assertEquals(nieuwPersoon, deltaRootEntiteitMatches2.iterator().next().getBestaandeRootEntiteit());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddDeltaEntiteitPaarSetInhoud() {
        assertTrue(context.getDeltaEntiteitPaarSet().isEmpty());
        context.addDeltaEntiteitPaarSetInhoud(Collections.singleton(new DeltaEntiteitPaar(bestaandPersoon, nieuwPersoon)));
        assertTrue(context.getDeltaEntiteitPaarSet().size() == 1);
        context.addDeltaEntiteitPaarSetInhoud(Collections.singleton(new DeltaEntiteitPaar(nieuwPersoon, bestaandPersoon)));
        assertTrue(context.getDeltaEntiteitPaarSet().size() == 2);

        context.getDeltaEntiteitPaarSet().add(new DeltaEntiteitPaar(bestaandPersoon, nieuwPersoon));
    }

    @Test
    public void testMarkeerBestaandRijAlsMRij() {
        final PersoonInschrijvingHistorie bestaandInschrijvingHistorie =
                new PersoonInschrijvingHistorie(bestaandPersoon, 20150101, 1L, Timestamp.valueOf("2015-01-01 00:00:00.000"));
        final PersoonInschrijvingHistorie nieuwInschrijvingHistorie =
                new PersoonInschrijvingHistorie(nieuwPersoon, 20150101, 2L, Timestamp.valueOf("2015-02-01 00:00:00.000"));
        final DeltaEntiteitPaar deltaEntiteitPaar = new DeltaEntiteitPaar(bestaandInschrijvingHistorie, nieuwInschrijvingHistorie);
        assertFalse(deltaEntiteitPaar.wordtBestaandMRij());

        context.addDeltaEntiteitPaarSetInhoud(Collections.singletonList(deltaEntiteitPaar));
        assertTrue(context.getDeltaEntiteitPaarSet().size() == 1);

        context.markeerBestaandeRijAlsMRij(bestaandInschrijvingHistorie);
        assertTrue(deltaEntiteitPaar.wordtBestaandMRij());
    }

    @Test
    public void testVerbreekDeltaEntiteitPaar() {
        final PersoonBijhoudingHistorie bestaandeHistorie =
                new PersoonBijhoudingHistorie(bestaandPersoon, maakPartij(), Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL);
        final PersoonBijhoudingHistorie nieuweHistorie =
                new PersoonBijhoudingHistorie(nieuwPersoon, maakPartij(), Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL);
        context.addDeltaEntiteitPaarSetInhoud(Collections.singleton(new DeltaEntiteitPaar(bestaandeHistorie, nieuweHistorie)));
        assertTrue(context.getDeltaEntiteitPaarSet().size() == 1);

        context.verbreekDeltaEntiteitPaar(bestaandeHistorie);
        assertTrue(context.getDeltaEntiteitPaarSet().isEmpty());
    }

    private boolean isInfrastructureleWijziging(final DeltaBepalingContext context) {
        return SoortAdministratieveHandeling.GBA_INFRASTRUCTURELE_WIJZIGING.equals(context.getAdministratieveHandelingGekoppeldAanActies().getSoort());
    }

    private boolean isAfvoeren(final DeltaBepalingContext context) {
        return SoortAdministratieveHandeling.GBA_AFVOEREN_PL.equals(context.getAdministratieveHandelingGekoppeldAanActies().getSoort());
    }

    private boolean isAnummerWijziging(final DeltaBepalingContext context) {
        return SoortAdministratieveHandeling.GBA_A_NUMMER_WIJZIGING.equals(context.getAdministratieveHandelingGekoppeldAanActies().getSoort());
    }

    @Test
    public void testInfraAlleenEigenPersoonWijzigingen() throws ReflectiveOperationException {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final DeltaRootEntiteitMatch match = new DeltaRootEntiteitMatch(persoon, persoon, null, null);
        final VergelijkerResultaat vergelijkerResultaat = new DeltaVergelijkerResultaat();
        final Sleutel sleutel = new EntiteitSleutel(Persoon.class, "test");
        vergelijkerResultaat.voegToeOfVervangVerschil(new Verschil(sleutel, null, null, VerschilType.RIJ_TOEGEVOEGD, null, null));
        match.setVergelijkerResultaat(vergelijkerResultaat);

        context.setDeltaRootEntiteitMatches(new HashSet<>(Collections.singletonList(match)));
        assertTrue(context.heeftAlleenPersoonsWijzigingen());

        final DeltaRootEntiteitMatch andereMatch =
                new DeltaRootEntiteitMatch(
                        new Relatie(SoortRelatie.HUWELIJK),
                        new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING),
                        persoon,
                        "relatie");
        context.setDeltaRootEntiteitMatches(new HashSet<>(Arrays.asList(match, andereMatch)));
        assertTrue(context.heeftAlleenPersoonsWijzigingen());

        andereMatch.setVergelijkerResultaat(new DeltaVergelijkerResultaat());
        context.setDeltaRootEntiteitMatches(new HashSet<>(Arrays.asList(match, andereMatch)));
        assertTrue(context.heeftAlleenPersoonsWijzigingen());
    }

    @Test
    public void testInfraMeerDanPersoonsWijzigingen() throws ReflectiveOperationException {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final DeltaRootEntiteitMatch match = new DeltaRootEntiteitMatch(persoon, persoon, null, null);
        final VergelijkerResultaat vergelijkerResultaat = new DeltaVergelijkerResultaat();
        final Sleutel sleutel = new EntiteitSleutel(Persoon.class, "test");
        vergelijkerResultaat.voegToeOfVervangVerschil(new Verschil(sleutel, null, null, VerschilType.RIJ_TOEGEVOEGD, null, null));
        match.setVergelijkerResultaat(vergelijkerResultaat);

        final DeltaRootEntiteitMatch andereMatch =
                new DeltaRootEntiteitMatch(
                        new Relatie(SoortRelatie.HUWELIJK),
                        new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING),
                        persoon,
                        "relatie");
        andereMatch.setVergelijkerResultaat(vergelijkerResultaat);
        context.setDeltaRootEntiteitMatches(new HashSet<>(Arrays.asList(match, andereMatch)));
        assertFalse(context.heeftAlleenPersoonsWijzigingen());
    }

    @Test
    public void testGetEigenPersoonMatch() throws ReflectiveOperationException {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final DeltaRootEntiteitMatch match = new DeltaRootEntiteitMatch(persoon, persoon, null, null);

        final DeltaRootEntiteitMatch andereMatch =
                new DeltaRootEntiteitMatch(
                        new Relatie(SoortRelatie.HUWELIJK),
                        new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING),
                        persoon,
                        "relatie");
        context.setDeltaRootEntiteitMatches(new HashSet<>(Arrays.asList(match, andereMatch)));
        assertEquals(match, context.getEigenPersoonMatch());

        context.setDeltaRootEntiteitMatches(new HashSet<>(Collections.singletonList(andereMatch)));
        assertNull(context.getEigenPersoonMatch());
    }

    @Test
    public void testDeltaContextAnummerWijziging() {
        assertFalse(context.isAnummerWijziging());
        final DeltaBepalingContext anrWijzigingContext = new DeltaBepalingContext(nieuwPersoon, bestaandPersoon, lo3Bericht, true);
        assertTrue(anrWijzigingContext.isAnummerWijziging());
    }

    @Test
    public void testVoegAnummerWijzigingAHToeIsOverig() {
        context.setBijhoudingOverig();
        assertEquals(1, context.getAdministratieveHandelingen().size());
        context.setBijhoudingAnummerWijziging();
        assertEquals(2, context.getAdministratieveHandelingen().size());
    }

    @Test
    public void testVoegAnummerWijzigingAHToeIsInfraWijziging() {
        context.setBijhoudingInfrastructureleWijziging();
        assertEquals(1, context.getAdministratieveHandelingen().size());
        context.setBijhoudingAnummerWijziging();
        assertEquals(2, context.getAdministratieveHandelingen().size());
    }

    @Test
    public void testGetAdministratieveHandelingenAsString() {
        assertEquals("GBA - Bijhouding actueel", context.getAdministratieveHandelingenAlsString());
        context.setBijhoudingOverig();
        context.setBijhoudingAnummerWijziging();
        assertEquals("GBA - A-nummer wijziging, GBA - Bijhouding overig", context.getAdministratieveHandelingenAlsString());

    }
}
