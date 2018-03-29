/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel.R1630;
import static nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel.R1861;
import static nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel.R1865;
import static nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel.R1867;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenBeeindigingRelatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RelatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.services.objectsleutel.OngeldigeObjectSleutelException;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.junit.Before;
import org.junit.Test;

/**
 */
public class HuwelijkOfGpElementTest extends AbstractElementTest {

    private static final String SLEUTEL_10_JAN_1992_P1 = "212121";
    private static final String SLEUTEL_11_JAN_1992_P1 = "212122";
    private static final String SLEUTEL_12_JAN_1992_P1 = "212123";
    private static final String SLEUTEL_ONGELDIG_P1 = "212124";
    private static final String SLEUTEL_CURATELE_P1 = "212125";
    private static final String SLEUTEL_10_JAN_1992_P2 = "212131";
    private static final String SLEUTEL_11_JAN_1992_P2 = "212132";
    private static final String SLEUTEL_12_JAN_1992_P2 = "212133";
    private static final String SLEUTEL_ONGELDIG_P2 = "212134";
    private static final String SLEUTEL_CURATELE_P2 = "212135";
    private static final String SLEUTEL_HUWELIJK_P1 = "212136";
    private static final String SLEUTEL_HUWELIJK_P2 = "212137";
    private static final String SLEUTEL_HUWELIJK_P3 = "212138";
    private static final String SLEUTEL_HUWELIJK_P4 = "212139";
    private static final String SLEUTEL_HUWELIJK_P5 = "212140";
    private static final String SLEUTEL_PERSOON_1 ="212141";
    private static final String SLEUTEL_PERSOON_2 ="212142";

    private static final int DATUM_GEBOORTE_10_JAN_1992 = 19920110;
    private static final int DATUM_GEBOORTE_11_JAN_1992 = 19920111;
    private static final int DATUM_GEBOORTE_12_JAN_1992 = 19920112;
    private static final DatumElement DATUM_HUWELIJK_11_JAN_1992 = new DatumElement(20100111);
    private static final DatumElement DATUM_HUWELIJK_CURATELE_IN_P1 = new DatumElement(20110611);
    private static final DatumElement DATUM_HUWELIJK_CURATELE_TUSSEN_P1_P2 = new DatumElement(20120311);
    private static final DatumElement DATUM_HUWELIJK_CURATELE_IN_P3 = new DatumElement(20150111);

    private static final Integer[] CURATELE_PERIODE_1 = {20110101, 20120101};
    private static final Integer[] CURATELE_PERIODE_2 = {20120501, 20120901};
    private static final Integer[] CURATELE_PERIODE_3 = {20140101};
    private static final Partij PARTIJ = new Partij("partij", "053001");
    private static final Partij PARTIJ_2 = new Partij("partij2", "051801");

    private static final Gemeente DENHAAG = new Gemeente(Short.parseShort("521"), "'s-Gravenhage", "0518", (new Partij("Den Haag", "000522")));
    private static final Gemeente HELLEVOETSLUIS = new Gemeente(Short.parseShort("533"), "Hellevoetsluis", "0530", new Partij("Hellevoetsluis", "000534"));
    private static final Gemeente ZEDERIK = new Gemeente(Short.parseShort("710"), "'ZEDERIK", "0707", new Partij("ZEDERIK", "000711"));
    private static final Gemeente VOLENDAM = new Gemeente(Short.parseShort("811"), "'VOLENDAM", "0811", new Partij("VOLENDAM", "000811"));
    private static final RedenBeeindigingRelatie REDEN_BEEINDIGING_RELATIE = new RedenBeeindigingRelatie('O', "Ontbinding");
    private static final LandOfGebied LAND_OF_GEBIED = new LandOfGebied("0001", "landGebiedAanvangCode");

    @Before
    public void setupHuwelijkElementTest() throws OngeldigeObjectSleutelException {
        when(getObjectSleutelIndex().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, SLEUTEL_10_JAN_1992_P1)).thenReturn(
                BijhoudingPersoon.decorate(createPersoon(SLEUTEL_10_JAN_1992_P1, DATUM_GEBOORTE_10_JAN_1992, false, null)));

        when(getObjectSleutelIndex().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, SLEUTEL_11_JAN_1992_P1)).thenReturn(
                BijhoudingPersoon.decorate(createPersoon(SLEUTEL_11_JAN_1992_P1, DATUM_GEBOORTE_11_JAN_1992, false, null)));

        when(getObjectSleutelIndex().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, SLEUTEL_12_JAN_1992_P1)).thenReturn(
                BijhoudingPersoon.decorate(createPersoon(SLEUTEL_12_JAN_1992_P1, DATUM_GEBOORTE_12_JAN_1992, false, null)));

        when(getObjectSleutelIndex().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, SLEUTEL_CURATELE_P1)).thenReturn(
                BijhoudingPersoon.decorate(createPersoon(SLEUTEL_CURATELE_P1, DATUM_GEBOORTE_12_JAN_1992, true, null)));

        when(getObjectSleutelIndex().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, SLEUTEL_10_JAN_1992_P2)).thenReturn(
                BijhoudingPersoon.decorate(createPersoon(SLEUTEL_10_JAN_1992_P2, DATUM_GEBOORTE_10_JAN_1992, false, null)));

        when(getObjectSleutelIndex().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, SLEUTEL_11_JAN_1992_P2)).thenReturn(
                BijhoudingPersoon.decorate(createPersoon(SLEUTEL_11_JAN_1992_P2, DATUM_GEBOORTE_11_JAN_1992, false, null)));

        when(getObjectSleutelIndex().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, SLEUTEL_12_JAN_1992_P2)).thenReturn(
                BijhoudingPersoon.decorate(createPersoon(SLEUTEL_12_JAN_1992_P2, DATUM_GEBOORTE_12_JAN_1992, false, null)));

        when(getObjectSleutelIndex().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, SLEUTEL_CURATELE_P2)).thenReturn(
                BijhoudingPersoon.decorate(createPersoon(SLEUTEL_CURATELE_P2, DATUM_GEBOORTE_12_JAN_1992, true, null)));

        when(getObjectSleutelIndex().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, SLEUTEL_PERSOON_1)).thenReturn(
                BijhoudingPersoon.decorate(createPersoon(SLEUTEL_PERSOON_1, DATUM_GEBOORTE_12_JAN_1992, false, PARTIJ_2)));
        when(getObjectSleutelIndex().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, SLEUTEL_PERSOON_2)).thenReturn(
                BijhoudingPersoon.decorate(createPersoon(SLEUTEL_PERSOON_2, DATUM_GEBOORTE_12_JAN_1992, false, PARTIJ_2)));

        when(getDynamischeStamtabelRepository().getGemeenteByPartijcode("053001")).thenReturn(
                new Gemeente(Short.parseShort("533"), "Hellevoetsluis", "0530", PARTIJ));

        when(getDynamischeStamtabelRepository().getGemeenteByGemeentecode("518")).thenReturn(DENHAAG);

        when(getDynamischeStamtabelRepository().getLandOfGebiedByCode(LAND_OF_GEBIED.getNaam())).thenReturn(LAND_OF_GEBIED);

        Gemeente lexmond = new Gemeente(Short.parseShort("554"), "'Lexmond", "0551", new Partij("Lexmond", "000555"));
        lexmond.setDatumEindeGeldigheid(20010101);
        lexmond.setVoortzettendeGemeente(ZEDERIK);
        lexmond.getPartij().setId((short) 101);
        lexmond.setId((short) 10);
        ZEDERIK.getPartij().setId((short) 102);

        Gemeente eindhoven = new Gemeente(Short.parseShort("123"), "'Eindhoven", "0123", new Partij("Eindhoven", "000100"));
        eindhoven.setDatumEindeGeldigheid(20010101);
        eindhoven.setVoortzettendeGemeente(VOLENDAM);
        eindhoven.setId((short) 11);
        eindhoven.getPartij().setId((short) 112);
        VOLENDAM.getPartij().setId((short) 112);

        Gemeente hellendoorn = new Gemeente(Short.parseShort("166"), "Hellendoorn", "0163", new Partij("Hellendoorn", "000167"));
        hellendoorn.setDatumEindeGeldigheid(20010101);
        hellendoorn.setVoortzettendeGemeente(HELLEVOETSLUIS);
        hellendoorn.setId((short) 12);
        hellendoorn.getPartij().setId((short) 121);
        HELLEVOETSLUIS.getPartij().setId((short) 122);

        when(getDynamischeStamtabelRepository().getGemeenteByGemeentecode("0551")).thenReturn(lexmond);
        when(getDynamischeStamtabelRepository().getGemeenteByGemeentecode("0707")).thenReturn(ZEDERIK);
        when(getDynamischeStamtabelRepository().getGemeenteByGemeentecode("0163")).thenReturn(hellendoorn);
        when(getDynamischeStamtabelRepository().getGemeenteByGemeentecode("0530")).thenReturn(HELLEVOETSLUIS);
        when(getDynamischeStamtabelRepository().getGemeenteByGemeentecode("0123")).thenReturn(eindhoven);
        when(getDynamischeStamtabelRepository().getGemeenteByGemeentecode("0518")).thenReturn(DENHAAG);
        when(getDynamischeStamtabelRepository().getGemeenteByGemeentecode("0811")).thenReturn(VOLENDAM);
        when(getDynamischeStamtabelRepository().getGemeenteByGemeentecode("0518")).thenReturn(DENHAAG);
        when(getDynamischeStamtabelRepository().getGemeenteByGemeentecode("0811")).thenReturn(VOLENDAM);

        when(getDynamischeStamtabelRepository().getRedenBeeindigingRelatieByCode('O')).thenReturn(REDEN_BEEINDIGING_RELATIE);

        Persoon algetrouwd1 = createPersoon(SLEUTEL_HUWELIJK_P1, DATUM_GEBOORTE_10_JAN_1992, false, null);
        Persoon algetrouwd2 = createPersoon(SLEUTEL_HUWELIJK_P2, DATUM_GEBOORTE_10_JAN_1992, false, null);
        Persoon algetrouwd3 = createPersoon(SLEUTEL_HUWELIJK_P3, DATUM_GEBOORTE_10_JAN_1992, false, null);
        Persoon algetrouwd4 = createPersoon(SLEUTEL_HUWELIJK_P4, DATUM_GEBOORTE_10_JAN_1992, false, null);
        Persoon algetrouwd5 = createPersoon(SLEUTEL_HUWELIJK_P5, DATUM_GEBOORTE_10_JAN_1992, false, null);
        final Relatie relatie = new Relatie(SoortRelatie.HUWELIJK);
        relatie.getRelatieHistorieSet().add(new RelatieHistorie(relatie));
        addBetrokkenheidEnVoegIdHistorieToe(algetrouwd1, new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatie));
        addBetrokkenheidEnVoegIdHistorieToe(algetrouwd1, new Betrokkenheid(SoortBetrokkenheid.OUDER, new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING)));
        addBetrokkenheidEnVoegIdHistorieToe(algetrouwd1, new Betrokkenheid(SoortBetrokkenheid.OUDER, new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING)));

        Relatie relatie2 = new Relatie(SoortRelatie.HUWELIJK);
        final RelatieHistorie relatieHistorie = new RelatieHistorie(relatie);
        relatieHistorie.setDatumAanvang(relatie2.getDatumAanvang());
        relatieHistorie.setDatumEinde(19900808);
        relatie2.getRelatieHistorieSet().add(relatieHistorie);
        relatie2.setDatumEinde(19900808);
        addBetrokkenheidEnVoegIdHistorieToe(algetrouwd2, new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatie2));

        final Relatie GP = new Relatie(SoortRelatie.GEREGISTREERD_PARTNERSCHAP);
        GP.getRelatieHistorieSet().add(new RelatieHistorie(GP));
        addBetrokkenheidEnVoegIdHistorieToe(algetrouwd3, new Betrokkenheid(SoortBetrokkenheid.PARTNER, GP));

        Relatie relatie4 = new Relatie(SoortRelatie.GEREGISTREERD_PARTNERSCHAP);
        final RelatieHistorie rhis4 = new RelatieHistorie(relatie4);
        rhis4.setDatumAanvang(relatie4.getDatumAanvang());
        rhis4.setDatumEinde(19900808);
        relatie4.getRelatieHistorieSet().add(rhis4);
        relatie4.setDatumEinde(19900808);
        addBetrokkenheidEnVoegIdHistorieToe(algetrouwd4, new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatie4));

        addBetrokkenheidEnVoegIdHistorieToe(algetrouwd5, new Betrokkenheid(SoortBetrokkenheid.OUDER, new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING)));
        addBetrokkenheidEnVoegIdHistorieToe(algetrouwd5, new Betrokkenheid(SoortBetrokkenheid.OUDER, new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING)));

        when(getObjectSleutelIndex().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, SLEUTEL_HUWELIJK_P1)).thenReturn(
                BijhoudingPersoon.decorate(algetrouwd1));
        when(getObjectSleutelIndex().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, SLEUTEL_HUWELIJK_P2)).thenReturn(
                BijhoudingPersoon.decorate(algetrouwd2));
        when(getObjectSleutelIndex().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, SLEUTEL_HUWELIJK_P3)).thenReturn(
                BijhoudingPersoon.decorate(algetrouwd3));
        when(getObjectSleutelIndex().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, SLEUTEL_HUWELIJK_P4)).thenReturn(
                BijhoudingPersoon.decorate(algetrouwd4));
        when(getObjectSleutelIndex().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, SLEUTEL_HUWELIJK_P5)).thenReturn(
                BijhoudingPersoon.decorate(algetrouwd5));
    }

    private static void addBetrokkenheidEnVoegIdHistorieToe(final Persoon persoon, final Betrokkenheid betrokkenheid) {
        final SoortBetrokkenheid soortAndereBetrokkenheid;
        if (SoortBetrokkenheid.OUDER.equals(betrokkenheid.getSoortBetrokkenheid())) {
            soortAndereBetrokkenheid = SoortBetrokkenheid.KIND;
        } else if (SoortBetrokkenheid.KIND.equals(betrokkenheid.getSoortBetrokkenheid())) {
            soortAndereBetrokkenheid = SoortBetrokkenheid.OUDER;
        } else {
            soortAndereBetrokkenheid = SoortBetrokkenheid.PARTNER;
        }
        final Betrokkenheid andereBetrokkenheid = new Betrokkenheid(soortAndereBetrokkenheid, betrokkenheid.getRelatie());
        final BetrokkenheidHistorie historie = new BetrokkenheidHistorie(betrokkenheid);
        final BetrokkenheidHistorie andereHistorie = new BetrokkenheidHistorie(andereBetrokkenheid);
        betrokkenheid.addBetrokkenheidHistorie(historie);
        andereBetrokkenheid.addBetrokkenheidHistorie(andereHistorie);
        persoon.addBetrokkenheid(betrokkenheid);
        betrokkenheid.getRelatie().addBetrokkenheid(betrokkenheid);
        betrokkenheid.getRelatie().addBetrokkenheid(andereBetrokkenheid);
    }

    private Persoon createPersoon(final String id, final int geboorteDatum, final boolean onderCuratele, final Partij partij) {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        persoon.setId(Long.valueOf(id));
        persoon.setDatumGeboorte(geboorteDatum);
        if (onderCuratele) {
            persoon.addPersoonIndicatie(createIndicatieOnderCuratele(persoon));
        }

        final Partij teGebruikenPartij = partij == null ? PARTIJ : partij;
        persoon.addPersoonBijhoudingHistorie(
                new PersoonBijhoudingHistorie(persoon, teGebruikenPartij, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL));
        return persoon;
    }

    private PersoonIndicatie createIndicatieOnderCuratele(final Persoon persoon) {
        PersoonIndicatie indicatie = new PersoonIndicatie(persoon, SoortIndicatie.ONDER_CURATELE);
        PersoonIndicatieHistorie his1 = new PersoonIndicatieHistorie(indicatie, true);
        his1.setDatumAanvangGeldigheid(CURATELE_PERIODE_1[0]);
        his1.setDatumEindeGeldigheid(CURATELE_PERIODE_1[1]);
        PersoonIndicatieHistorie his2 = new PersoonIndicatieHistorie(indicatie, true);
        his2.setDatumAanvangGeldigheid(CURATELE_PERIODE_2[0]);
        his2.setDatumEindeGeldigheid(CURATELE_PERIODE_2[1]);
        PersoonIndicatieHistorie his3 = new PersoonIndicatieHistorie(indicatie, true);
        his3.setDatumAanvangGeldigheid(CURATELE_PERIODE_3[0]);
        indicatie.addPersoonIndicatieHistorie(his1);
        indicatie.addPersoonIndicatieHistorie(his2);
        indicatie.addPersoonIndicatieHistorie(his3);
        return indicatie;
    }

    @Test
    public void regel1865_beide_exact18() {
        final String[] geboorteDatums = {SLEUTEL_11_JAN_1992_P1, SLEUTEL_11_JAN_1992_P2};
        assertEquals(0, testLeeftijd(geboorteDatums, DATUM_HUWELIJK_11_JAN_1992, null));
    }

    @Test
    public void regel1865_beide_jonger_dan_18() {
        final String[] geboorteDatums = {SLEUTEL_12_JAN_1992_P1, SLEUTEL_12_JAN_1992_P2};
        assertEquals(2, testLeeftijd(geboorteDatums, DATUM_HUWELIJK_11_JAN_1992, null, R1865.getCode(), R1865.getCode()));
    }

    @Test
    public void regel1865_een_jonger_dan_18() {
        final String[] geboorteDatums = {SLEUTEL_10_JAN_1992_P1, SLEUTEL_12_JAN_1992_P2};
        assertEquals(1, testLeeftijd(geboorteDatums, DATUM_HUWELIJK_11_JAN_1992, null, R1865.getCode()));
    }

    @Test
    public void regel1865_ongeldige_sleutel_en_jonger_dan_18() {
        final String[] geboorteDatums = {SLEUTEL_ONGELDIG_P1, SLEUTEL_12_JAN_1992_P2};
        assertEquals(1, testLeeftijd(geboorteDatums, DATUM_HUWELIJK_11_JAN_1992, null, R1865.getCode()));
    }

    @Test
    public void regel1865_ongeldige_sleutel_en_18plus() {
        final String[] geboorteDatums = {SLEUTEL_ONGELDIG_P1, SLEUTEL_10_JAN_1992_P2};
        assertEquals(0, testLeeftijd(geboorteDatums, DATUM_HUWELIJK_11_JAN_1992, null));
    }

    @Test
    public void regel1865_twee_ongeldige_sleutels() {
        final String[] geboorteDatums = {SLEUTEL_ONGELDIG_P1, SLEUTEL_ONGELDIG_P2};
        assertEquals(0, testLeeftijd(geboorteDatums, DATUM_HUWELIJK_11_JAN_1992, null));
    }

    @Test
    public void regel1867_historyEenOnderCuratele() {
        final String[] geboorteDatums = {SLEUTEL_CURATELE_P1, SLEUTEL_10_JAN_1992_P2};
        assertEquals(1, testLeeftijd(geboorteDatums, DATUM_HUWELIJK_CURATELE_IN_P1, null, R1867.getCode()));
    }

    @Test
    public void regel1867_historyTussentweePeriodesOnderCuratele() {
        final String[] geboorteDatums = {SLEUTEL_CURATELE_P1, SLEUTEL_10_JAN_1992_P2};
        assertEquals(0, testLeeftijd(geboorteDatums, DATUM_HUWELIJK_CURATELE_TUSSEN_P1_P2, null));
    }

    @Test
    public void regel1867_historyCurateleZonderEindDatum() {
        final String[] geboorteDatums = {SLEUTEL_CURATELE_P1, SLEUTEL_10_JAN_1992_P2};
        assertEquals(1, testLeeftijd(geboorteDatums, DATUM_HUWELIJK_CURATELE_IN_P3, null, R1867.getCode()));
    }

    @Test
    public void regel1867_historyBeideOnderCuratele() {
        final String[] geboorteDatums = {SLEUTEL_CURATELE_P1, SLEUTEL_CURATELE_P2};
        assertEquals(2, testLeeftijd(geboorteDatums, DATUM_HUWELIJK_CURATELE_IN_P1, null, R1867.getCode(), R1867.getCode()));
    }

    @Test
    public void regel1630_iemand_trouwtMetZichZelf() {
        final String[] geboorteDatums = {SLEUTEL_10_JAN_1992_P1, SLEUTEL_10_JAN_1992_P1};
        assertEquals(1, testLeeftijd(geboorteDatums, DATUM_HUWELIJK_11_JAN_1992, null, R1630.getCode()));
    }

    @Test
    public void regel1861_registratie_betrokkenGemeente() {
        assertEquals(0, testRelatie("0530"));
    }

    @Test
    public void regel1861_registratie_niet_doorbetrokkenGemeente_maarDoorBijhoudendePartijPersoon() {
        assertEquals(0, testRelatie("0518"));
    }

    @Test
    public void regel1861_registratie_niet_doorbetrokkenGemeente_maarDoorBijhoudendeTweedePartijPersoon() {
        final String[] geboorteDatums = {SLEUTEL_PERSOON_1, SLEUTEL_PERSOON_2};
        assertEquals(1, testLeeftijd(geboorteDatums, new DatumElement(2016_01_11), "0163", R1861.getCode()));
    }

    @Test
    public void regel1861_Einde_Huwelijk() {
        final ElementBuilder builder = new ElementBuilder();
        final String relatieSleutel = "sleutel";
        final Relatie relatie = new Relatie(SoortRelatie.HUWELIJK);
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final Betrokkenheid betrokkenheid = new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatie);
        persoon.addBetrokkenheid(betrokkenheid);
        relatie.addBetrokkenheid(betrokkenheid);

        final BijhoudingRelatie bijhoudingRelatie = BijhoudingRelatie.decorate(relatie);
        when(getObjectSleutelIndex().getEntiteitVoorObjectSleutel(BijhoudingRelatie.class, relatieSleutel)).thenReturn(bijhoudingRelatie);
        final ElementBuilder.RelatieGroepParameters relatieGroepParameters = new ElementBuilder.RelatieGroepParameters();
        relatieGroepParameters.gemeenteEindeCode("0518");

        final RelatieGroepElement relatieGroep = builder.maakRelatieGroepElement("relatieGroep", relatieGroepParameters);
        final HuwelijkElement huwelijk = builder.maakHuwelijkElement("huwelijk", relatieSleutel, relatieGroep, Collections.emptyList());
        huwelijk.setVerzoekBericht(getBericht());
        final List<MeldingElement> meldingen = huwelijk.valideerSpecifiekeInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1861, meldingen.get(0).getRegel());
    }

    @Test
    public void regel1869_Persoon1AlGetrouwd() {
        final String[] geboorteDatums = {SLEUTEL_HUWELIJK_P1, SLEUTEL_10_JAN_1992_P2};
        assertEquals(1, testLeeftijd(geboorteDatums, DATUM_HUWELIJK_11_JAN_1992, null, Regel.R1869.getCode()));
    }

    @Test
    public void regel1869_Persoon2VerledenGetrouwd() {
        final String[] geboorteDatums = {SLEUTEL_10_JAN_1992_P1, SLEUTEL_HUWELIJK_P2};
        assertEquals(0, testLeeftijd(geboorteDatums, DATUM_HUWELIJK_11_JAN_1992, null));
    }

    @Test
    public void regel1869_Persoon1AlGerigistreerdPartnerschap() {
        final String[] geboorteDatums = {SLEUTEL_HUWELIJK_P3, SLEUTEL_10_JAN_1992_P2};
        assertEquals(1, testLeeftijd(geboorteDatums, DATUM_HUWELIJK_11_JAN_1992, null, Regel.R1869.getCode()));
    }

    @Test
    public void regel1869_Persoon2VerledenGereistreerdPartnerschap() {
        final String[] geboorteDatums = {SLEUTEL_10_JAN_1992_P1, SLEUTEL_HUWELIJK_P4};
        assertEquals(0, testLeeftijd(geboorteDatums, DATUM_HUWELIJK_11_JAN_1992, null));
    }

    @Test
    public void regel1869_Persoon2NietGetrouwdAlleenKinderen() {
        final String[] geboorteDatums = {SLEUTEL_10_JAN_1992_P1, SLEUTEL_HUWELIJK_P5};
        assertEquals(0, testLeeftijd(geboorteDatums, DATUM_HUWELIJK_11_JAN_1992, null));
    }

    @Test
    public void regel1869_PersonenBeideGetrouwd() {
        final String[] geboorteDatums = {SLEUTEL_HUWELIJK_P1, SLEUTEL_HUWELIJK_P3};
        assertEquals(2, testLeeftijd(geboorteDatums, DATUM_HUWELIJK_11_JAN_1992, null, Regel.R1869.getCode(), Regel.R1869.getCode()));
    }

    @Test
    public void testMaakRelatieLeeg() {
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        new AbstractBmrGroep.AttributenBuilder().build(),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        final HuwelijkOfGpElement huwelijkOfGpElement =
                new HuwelijkElement(new AbstractBmrGroep.AttributenBuilder().objecttype("").build(), relatie, Collections.emptyList());
        final Relatie relatieEntiteit = huwelijkOfGpElement.maakRelatieEntiteitEnBetrokkenen(getActie(), DatumUtil.vandaag());
        assertNotNull(relatieEntiteit);
        final RelatieHistorie relatieHistorie = FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(relatieEntiteit.getRelatieHistorieSet());
        assertNotNull(relatieHistorie);
    }

    @Test
    public void testMaakRelatieGevuld() throws OngeldigeWaardeException {
        final DatumElement datumAanvang = DatumElement.parseWaarde("2016-01-01");
        final StringElement gemeenteAanvangCode = StringElement.getInstance(String.valueOf(DENHAAG.getCode()));
        final StringElement woonplaatsnaamAanvang = StringElement.getInstance("woonplaatsnaamAanvang");
        final CharacterElement redenEindeCode = new CharacterElement(REDEN_BEEINDIGING_RELATIE.getCode());
        final DatumElement datumEinde = DatumElement.parseWaarde("2016-02-02");
        final StringElement gemeenteEindeCode = StringElement.getInstance(String.valueOf(VOLENDAM.getCode()));
        final StringElement woonplaatsnaaamEinde = StringElement.getInstance("woonplaatsnaaamEinde");

        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        new AbstractBmrGroep.AttributenBuilder().build(),
                        datumAanvang,
                        gemeenteAanvangCode,
                        woonplaatsnaamAanvang,
                        redenEindeCode,
                        datumEinde,
                        gemeenteEindeCode,
                        woonplaatsnaaamEinde,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        final HuwelijkOfGpElement huwelijkOfGpElement =
                new HuwelijkElement(new AbstractBmrGroep.AttributenBuilder().objecttype("").build(), relatie, Collections.emptyList());
        final Relatie relatieEntiteit = huwelijkOfGpElement.maakRelatieEntiteitEnBetrokkenen(getActie(), DatumUtil.vandaag());
        assertNotNull(relatieEntiteit);
        final RelatieHistorie relatieHistorie = FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(relatieEntiteit.getRelatieHistorieSet());
        assertNotNull(relatieHistorie);
        assertEquals(datumAanvang.getWaarde(), relatieHistorie.getDatumAanvang());
        assertEquals(DENHAAG, relatieHistorie.getGemeenteAanvang());
        assertEquals(woonplaatsnaamAanvang.getWaarde(), relatieHistorie.getWoonplaatsnaamAanvang());

    }

    @Test
    public void testMaakRelatieGevuldAanvangBuitenland() throws OngeldigeWaardeException {
        final DatumElement datumAanvang = DatumElement.parseWaarde("2016-01-01");
        final RelatieGroepElement relatie =
                new RelatieGroepElement(
                        new AbstractBmrGroep.AttributenBuilder().build(),
                        datumAanvang,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        new StringElement("buitenlandsePlaatsAanvang"),
                        new StringElement("buitenlandseRegioAanvang"),
                        new StringElement("omschrijvingLocatieAanvang"),
                        new StringElement("landGebiedAanvangCode"),
                        null,
                        null,
                        null,
                        null);
        final HuwelijkOfGpElement huwelijkOfGpElement =
                new HuwelijkElement(new AbstractBmrGroep.AttributenBuilder().objecttype("").build(), relatie, Collections.emptyList());
        final Relatie relatieEntiteit = huwelijkOfGpElement.maakRelatieEntiteitEnBetrokkenen(getActie(), DatumUtil.vandaag());
        assertNotNull(relatieEntiteit);
        final RelatieHistorie relatieHistorie = FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(relatieEntiteit.getRelatieHistorieSet());
        assertNotNull(relatieHistorie);
        assertEquals(datumAanvang.getWaarde(), relatieHistorie.getDatumAanvang());
        assertEquals("buitenlandseRegioAanvang", relatieHistorie.getBuitenlandseRegioAanvang());
        assertEquals("buitenlandsePlaatsAanvang", relatieHistorie.getBuitenlandsePlaatsAanvang());
        assertEquals("omschrijvingLocatieAanvang", relatieHistorie.getOmschrijvingLocatieAanvang());
        assertEquals(LAND_OF_GEBIED, relatieHistorie.getLandOfGebiedAanvang());

    }

    private int testRelatie(final String gemeenteAanvangCode, String... meldingCodes) {
        final String[] geboorteDatums = {SLEUTEL_10_JAN_1992_P1, SLEUTEL_10_JAN_1992_P2};
        final BijhoudingVerzoekBericht bericht = getUitgebreidBericht();
        final List<BetrokkenheidElement> betrokkenen2 = createBetrokkenen(bericht, geboorteDatums);
        final RelatieGroepElement relatie = createRelatie(DATUM_HUWELIJK_11_JAN_1992, gemeenteAanvangCode);
        final ElementBuilder builder = new ElementBuilder();
        final HuwelijkElement element = builder.maakHuwelijkElement("ci_huwelijk_element", relatie, betrokkenen2);
        element.setVerzoekBericht(bericht);
        return testValidatie(element, meldingCodes);
    }

    private int testLeeftijd(String[] objectsleutels, DatumElement aanvangRelatie, final String gemeenteAanvangCode, String... meldingCodes) {
        final BijhoudingVerzoekBericht bericht = getUitgebreidBericht();
        final List<BetrokkenheidElement> betrokkenen2 = createBetrokkenen(bericht, objectsleutels);
        final RelatieGroepElement relatie;
        if (gemeenteAanvangCode == null) {
            relatie = createRelatie(aanvangRelatie, "0530");
        } else {
            relatie = createRelatie(aanvangRelatie, gemeenteAanvangCode);
        }
        final ElementBuilder builder = new ElementBuilder();
        final HuwelijkElement element2 = builder.maakHuwelijkElement("ci_huwelijk_element", relatie, betrokkenen2);
        element2.setVerzoekBericht(bericht);
        return testValidatie(element2, meldingCodes);
    }

    private int testValidatie(final HuwelijkElement element2, final String[] meldingCodes) {
        final List<MeldingElement> meldingen = element2.valideerInhoud();
        assertEquals("aantal meldingen komt niet overeen ", meldingCodes.length, meldingen.size());
        List<String> verwachteCodes = Arrays.asList(meldingCodes);
        for (MeldingElement verkregenMelding : meldingen) {
            assertTrue(
                    "meldingen bevat meldingscode "
                            + verkregenMelding.getRegelCode().getWaarde(),
                    verwachteCodes.contains(verkregenMelding.getRegelCode().getWaarde()));
        }
        return meldingen.size();
    }

    private RelatieGroepElement createRelatie(DatumElement datum, String gemeenteAanvang) {
        final ElementBuilder builder = new ElementBuilder();
        final String gemeenteCode;
        if (gemeenteAanvang == null) {
            gemeenteCode = "1234";
        } else {
            gemeenteCode = gemeenteAanvang;
        }
        final RelatieGroepElement
                result =
                builder.maakRelatieGroepElement("ci_relatie_groep",
                        new ElementBuilder.RelatieGroepParameters().gemeenteAanvangCode(gemeenteCode).datumAanvang(datum.getWaarde()));
        result.setVerzoekBericht(getBericht());
        return result;
    }

    private List<BetrokkenheidElement> createBetrokkenen(
            final BijhoudingVerzoekBericht bericht,
            final String[] objectSleutels) {
        final ElementBuilder builder = new ElementBuilder();
        final List<BetrokkenheidElement> betrokkenen = new ArrayList<>();
        for (int i = 0; i < objectSleutels.length; i++) {
            final PersoonGegevensElement persoon;
            if (i == 0) {
                persoon =
                        builder.maakPersoonGegevensElement("ci_persoon_" + (i + 1), objectSleutels[i],
                                null, new ElementBuilder.PersoonParameters());
            } else {
                persoon =
                        builder.maakPersoonGegevensElement("ci_persoon_" + (i + 1), objectSleutels[i],
                                null, new ElementBuilder.PersoonParameters());
            }
            persoon.setVerzoekBericht(bericht);
            final BetrokkenheidElement betrokkene = builder.maakBetrokkenheidElement("ci_betrokkenheid_" + (i + 1), BetrokkenheidElementSoort.PARTNER, persoon,
                    null);
            betrokkene.setVerzoekBericht(bericht);
            betrokkenen.add(betrokkene);
        }
        return betrokkenen;
    }

}
