/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis;

import java.util.Arrays;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AdellijkeTitel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpRelatie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenis;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisPersoon;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisVerbintenis;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisVerbintenisSluiting;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle.ControleException;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle.GeboorteVergelijker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle.GeslachtVergelijker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle.IdentificatieNummerVergelijker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle.SamengesteldeNaamVergelijker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Helper klasse voor het inhoudelijk controleren van de relatievinder.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({BrpToevalligeGebeurtenis.class, BrpPersoonslijst.class})
public final class RelatieVinderTest {

    private final IdentificatieNummerVergelijker identificatieNummerVergelijker = new IdentificatieNummerVergelijker();
    private final SamengesteldeNaamVergelijker samengesteldeNaamVergelijker = new SamengesteldeNaamVergelijker();
    private final GeboorteVergelijker geboorteVergelijker = new GeboorteVergelijker();
    private final GeslachtVergelijker geslachtVergelijker = new GeslachtVergelijker();

    @Mock
    private BrpToevalligeGebeurtenis brpToevalligeGebeurtenis;

    @Mock
    private BrpPersoonslijst brpPersoonslijst;

    @Mock
    private BrpPersoonslijst brpPersoonslijstPartner;

    private final RelatieVinder
            subject =
            new RelatieVinder(identificatieNummerVergelijker, samengesteldeNaamVergelijker, geboorteVergelijker, geslachtVergelijker);

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGelijk() throws ControleException {
        final boolean vervallenRelatie = false;
        final boolean vervallenGeboorte = false;
        final boolean vervallenGeslacht = false;
        final boolean vervallenBijhouding = false;
        final boolean vervallenSamengesteldeNaam = false;
        final boolean geenVervallenReden = true;
        maakPersoon(vervallenRelatie, vervallenGeboorte, vervallenGeslacht, vervallenBijhouding, vervallenSamengesteldeNaam, geenVervallenReden);
        final BrpToevalligeGebeurtenisPersoon tgbPersoon =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
                        "2354545000",
                        "456245245",
                        null,
                        "Henk",
                        "van",
                        ' ',
                        null,
                        "Heusden",
                        19581512,
                        "0599",
                        "6030",
                        null,
                        null,
                        new BrpGeslachtsaanduidingCode(Geslachtsaanduiding.MAN.getCode()));
        final BrpToevalligeGebeurtenisPersoon partner =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
                        null,
                        null,
                        null,
                        "Truus",
                        null,
                        null,
                        new BrpAdellijkeTitelCode(AdellijkeTitel.M.getCode()),
                        "Vries",
                        19581501,
                        "0599",
                        "6030",
                        null,
                        null,
                        new BrpGeslachtsaanduidingCode(Geslachtsaanduiding.VROUW.getCode()));
        final BrpToevalligeGebeurtenisVerbintenisSluiting sluiting =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakSluiting(BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP, 20110101, "0599", null, "6030");
        final BrpToevalligeGebeurtenisVerbintenisSluiting omzetting =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakSluiting(BrpSoortRelatieCode.HUWELIJK, 20161001, "0600", null, "6030");
        final BrpToevalligeGebeurtenisVerbintenis verbintenis = new BrpToevalligeGebeurtenisVerbintenis(partner, sluiting, null, omzetting);
        VerwerkToevalligeGebeurtenisVerzoekHelper.maakRelatie(
                2L,
                BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP,
                new BrpDatum(20110101, null),
                new BrpGemeenteCode("0599"),
                new BrpLandOfGebiedCode("6030"),
                null,
                new BrpDatum(20161001, null),
                new BrpGemeenteCode("0600"),
                new BrpLandOfGebiedCode("6030"),
                null,
                null,
                brpPersoonslijstPartner.getGeboorteStapel(),
                brpPersoonslijstPartner.getGeslachtsaanduidingStapel(),
                brpPersoonslijstPartner.getSamengesteldeNaamStapel(),
                true,
                vervallenRelatie);

        PowerMockito.when(brpToevalligeGebeurtenis.getPersoon()).thenReturn(tgbPersoon);
        PowerMockito.when(brpToevalligeGebeurtenis.getVerbintenis()).thenReturn(verbintenis);
        PowerMockito.when(brpToevalligeGebeurtenis.getPersoon()).thenReturn(tgbPersoon);
        PowerMockito.when(brpToevalligeGebeurtenis.getDatumAanvang()).thenReturn(new BrpDatum(20161001, null));
        PowerMockito.when(brpToevalligeGebeurtenis.getRegisterGemeente()).thenReturn(new BrpPartijCode("060001"));
        PowerMockito.when(brpToevalligeGebeurtenis.getNummerAkte()).thenReturn(new BrpString("5H", null));
        PowerMockito.when(brpToevalligeGebeurtenis.getDoelPartijCode()).thenReturn(new BrpPartijCode("060001"));
        final BrpRelatie relatie = subject.vindRelatie(brpPersoonslijst, brpToevalligeGebeurtenis);
        Assert.assertNotNull("resultaat mag niet null zijn", relatie);
    }

    @Test(expected = ControleException.class)
    public void testSluitingOngelijk() throws ControleException {
        final boolean vervallenRelatie = false;
        final boolean vervallenGeboorte = false;
        final boolean vervallenGeslacht = false;
        final boolean vervallenBijhouding = false;
        final boolean vervallenSamengesteldeNaam = false;
        final boolean geenVervallenReden = false;
        maakPersoon(vervallenRelatie, vervallenGeboorte, vervallenGeslacht, vervallenBijhouding, vervallenSamengesteldeNaam, geenVervallenReden);
        final BrpToevalligeGebeurtenisPersoon tgbPersoon =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
                        "2354545000",
                        "456245245",
                        null,
                        "Henk",
                        "van",
                        ' ',
                        null,
                        "Heusden",
                        19581512,
                        "0599",
                        "6030",
                        null,
                        null,
                        new BrpGeslachtsaanduidingCode(Geslachtsaanduiding.MAN.getCode()));
        final BrpToevalligeGebeurtenisPersoon partner =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
                        "2354545000",
                        "456245245",
                        null,
                        "Truus",
                        null,
                        null,
                        new BrpAdellijkeTitelCode(AdellijkeTitel.M.getCode()),
                        "Vries",
                        19581501,
                        "0599",
                        "6030",
                        null,
                        null,
                        new BrpGeslachtsaanduidingCode(Geslachtsaanduiding.VROUW.getCode()));
        final BrpToevalligeGebeurtenisVerbintenisSluiting sluiting =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakSluiting(BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP, 20120101, "0599", null, "6030");
        final BrpToevalligeGebeurtenisVerbintenisSluiting omzetting =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakSluiting(BrpSoortRelatieCode.HUWELIJK, 20171001, "0600", null, "6030");
        final BrpToevalligeGebeurtenisVerbintenis verbintenis = new BrpToevalligeGebeurtenisVerbintenis(partner, sluiting, null, omzetting);
        VerwerkToevalligeGebeurtenisVerzoekHelper.maakRelatie(
                2L,
                BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP,
                new BrpDatum(20110101, null),
                new BrpGemeenteCode("0599"),
                new BrpLandOfGebiedCode("6030"),
                null,
                new BrpDatum(20171001, null),
                new BrpGemeenteCode("0600"),
                new BrpLandOfGebiedCode("6030"),
                null,
                null,
                brpPersoonslijstPartner.getGeboorteStapel(),
                brpPersoonslijstPartner.getGeslachtsaanduidingStapel(),
                brpPersoonslijstPartner.getSamengesteldeNaamStapel(),
                true,
                vervallenRelatie);

        PowerMockito.when(brpToevalligeGebeurtenis.getPersoon()).thenReturn(tgbPersoon);
        PowerMockito.when(brpToevalligeGebeurtenis.getVerbintenis()).thenReturn(verbintenis);
        PowerMockito.when(brpToevalligeGebeurtenis.getPersoon()).thenReturn(tgbPersoon);
        PowerMockito.when(brpToevalligeGebeurtenis.getDatumAanvang()).thenReturn(new BrpDatum(20161001, null));
        PowerMockito.when(brpToevalligeGebeurtenis.getRegisterGemeente()).thenReturn(new BrpPartijCode("060001"));
        PowerMockito.when(brpToevalligeGebeurtenis.getNummerAkte()).thenReturn(new BrpString("5H", null));
        PowerMockito.when(brpToevalligeGebeurtenis.getDoelPartijCode()).thenReturn(new BrpPartijCode("060001"));
        subject.vindRelatie(brpPersoonslijst, brpToevalligeGebeurtenis);
    }

    @Test(expected = ControleException.class)
    public void testGeenActueleRelatie() throws ControleException {
        final boolean vervallenRelatie = true;
        final boolean vervallenGeboorte = false;
        final boolean vervallenGeslacht = false;
        final boolean vervallenBijhouding = false;
        final boolean vervallenSamengesteldeNaam = false;
        final boolean geenVervallenReden = false;
        maakPersoon(vervallenRelatie, vervallenGeboorte, vervallenGeslacht, vervallenBijhouding, vervallenSamengesteldeNaam, geenVervallenReden);
        final BrpToevalligeGebeurtenisPersoon tgbPersoon =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
                        "2354545000",
                        "456245245",
                        null,
                        "Henk",
                        "van",
                        ' ',
                        null,
                        "Heusden",
                        19581512,
                        "0599",
                        "6030",
                        null,
                        null,
                        new BrpGeslachtsaanduidingCode(Geslachtsaanduiding.MAN.getCode()));
        final BrpToevalligeGebeurtenisPersoon partner =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
                        "2354545000",
                        "456245245",
                        null,
                        "Truus",
                        null,
                        null,
                        new BrpAdellijkeTitelCode(AdellijkeTitel.M.getCode()),
                        "Vries",
                        19581501,
                        "0599",
                        "6030",
                        null,
                        null,
                        new BrpGeslachtsaanduidingCode(Geslachtsaanduiding.VROUW.getCode()));
        final BrpToevalligeGebeurtenisVerbintenisSluiting sluiting =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakSluiting(BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP, 20110101, "0599", null, "6030");
        final BrpToevalligeGebeurtenisVerbintenisSluiting omzetting =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakSluiting(BrpSoortRelatieCode.HUWELIJK, 20161001, "0600", null, "6030");
        final BrpToevalligeGebeurtenisVerbintenis verbintenis = new BrpToevalligeGebeurtenisVerbintenis(partner, sluiting, null, omzetting);
        VerwerkToevalligeGebeurtenisVerzoekHelper.maakRelatie(
                2L,
                BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP,
                new BrpDatum(20110101, null),
                new BrpGemeenteCode("0599"),
                new BrpLandOfGebiedCode("6030"),
                null,
                new BrpDatum(20161001, null),
                new BrpGemeenteCode("0600"),
                new BrpLandOfGebiedCode("6030"),
                null,
                null,
                brpPersoonslijstPartner.getGeboorteStapel(),
                brpPersoonslijstPartner.getGeslachtsaanduidingStapel(),
                brpPersoonslijstPartner.getSamengesteldeNaamStapel(),
                true,
                vervallenRelatie);

        PowerMockito.when(brpToevalligeGebeurtenis.getPersoon()).thenReturn(tgbPersoon);
        PowerMockito.when(brpToevalligeGebeurtenis.getVerbintenis()).thenReturn(verbintenis);
        PowerMockito.when(brpToevalligeGebeurtenis.getPersoon()).thenReturn(tgbPersoon);
        PowerMockito.when(brpToevalligeGebeurtenis.getDatumAanvang()).thenReturn(new BrpDatum(20161001, null));
        PowerMockito.when(brpToevalligeGebeurtenis.getRegisterGemeente()).thenReturn(new BrpPartijCode("060001"));
        PowerMockito.when(brpToevalligeGebeurtenis.getNummerAkte()).thenReturn(new BrpString("5H", null));
        PowerMockito.when(brpToevalligeGebeurtenis.getDoelPartijCode()).thenReturn(new BrpPartijCode("060001"));
        subject.vindRelatie(brpPersoonslijst, brpToevalligeGebeurtenis);
    }

    @Test(expected = ControleException.class)
    public void testIkBetrokkenheid() throws ControleException {
        final boolean vervallenRelatie = false;
        final boolean vervallenGeboorte = false;
        final boolean vervallenGeslacht = false;
        final boolean vervallenBijhouding = false;
        final boolean vervallenSamengesteldeNaam = true;
        final boolean geenVervallenReden = true;
        maakPersoon(vervallenRelatie, vervallenGeboorte, vervallenGeslacht, vervallenBijhouding, vervallenSamengesteldeNaam, geenVervallenReden);
        final BrpToevalligeGebeurtenisPersoon tgbPersoon =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
                        "2354545000",
                        "456245245",
                        null,
                        "Henk",
                        "van",
                        ' ',
                        null,
                        "Heusden",
                        19581512,
                        "0599",
                        "6030",
                        null,
                        null,
                        new BrpGeslachtsaanduidingCode(Geslachtsaanduiding.MAN.getCode()));
        final BrpToevalligeGebeurtenisPersoon partner =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
                        "2354545000",
                        "456245245",
                        null,
                        "Truus",
                        null,
                        null,
                        new BrpAdellijkeTitelCode(AdellijkeTitel.M.getCode()),
                        "Vries",
                        19581501,
                        "0599",
                        "6030",
                        null,
                        null,
                        new BrpGeslachtsaanduidingCode(Geslachtsaanduiding.VROUW.getCode()));
        final BrpToevalligeGebeurtenisVerbintenisSluiting sluiting =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakSluiting(BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP, 20110101, "0599", null, "6030");
        final BrpToevalligeGebeurtenisVerbintenisSluiting omzetting =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakSluiting(BrpSoortRelatieCode.HUWELIJK, 20161001, "0600", null, "6030");
        final BrpToevalligeGebeurtenisVerbintenis verbintenis = new BrpToevalligeGebeurtenisVerbintenis(partner, sluiting, null, omzetting);
        VerwerkToevalligeGebeurtenisVerzoekHelper.maakRelatie(
                2L,
                BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP,
                new BrpDatum(20110101, null),
                new BrpGemeenteCode("0599"),
                new BrpLandOfGebiedCode("6030"),
                null,
                new BrpDatum(20161001, null),
                new BrpGemeenteCode("0600"),
                new BrpLandOfGebiedCode("6030"),
                null,
                null,
                brpPersoonslijstPartner.getGeboorteStapel(),
                brpPersoonslijstPartner.getGeslachtsaanduidingStapel(),
                brpPersoonslijstPartner.getSamengesteldeNaamStapel(),
                false,
                vervallenRelatie);

        PowerMockito.when(brpToevalligeGebeurtenis.getPersoon()).thenReturn(tgbPersoon);
        PowerMockito.when(brpToevalligeGebeurtenis.getVerbintenis()).thenReturn(verbintenis);
        PowerMockito.when(brpToevalligeGebeurtenis.getPersoon()).thenReturn(tgbPersoon);
        PowerMockito.when(brpToevalligeGebeurtenis.getDatumAanvang()).thenReturn(new BrpDatum(20161001, null));
        PowerMockito.when(brpToevalligeGebeurtenis.getRegisterGemeente()).thenReturn(new BrpPartijCode("060001"));
        PowerMockito.when(brpToevalligeGebeurtenis.getNummerAkte()).thenReturn(new BrpString("5H", null));
        PowerMockito.when(brpToevalligeGebeurtenis.getDoelPartijCode()).thenReturn(new BrpPartijCode("060001"));
        subject.vindRelatie(brpPersoonslijst, brpToevalligeGebeurtenis);
    }

    @Test(expected = ControleException.class)
    public void testOngelijkGeboorte() throws ControleException {
        final boolean vervallenRelatie = false;
        final boolean vervallenGeboorte = false;
        final boolean vervallenGeslacht = false;
        final boolean vervallenBijhouding = false;
        final boolean vervallenSamengesteldeNaam = false;
        final boolean geenVervallenReden = false;
        maakPersoon(vervallenRelatie, vervallenGeboorte, vervallenGeslacht, vervallenBijhouding, vervallenSamengesteldeNaam, geenVervallenReden);
        final BrpToevalligeGebeurtenisPersoon tgbPersoon =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
                        "2354545000",
                        "456245245",
                        null,
                        "Henk",
                        "van",
                        ' ',
                        null,
                        "Heusden",
                        19581512,
                        "0599",
                        "6030",
                        null,
                        null,
                        new BrpGeslachtsaanduidingCode(Geslachtsaanduiding.MAN.getCode()));
        final BrpToevalligeGebeurtenisPersoon partner =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
                        "2354545000",
                        "456245245",
                        null,
                        "Truus",
                        null,
                        null,
                        new BrpAdellijkeTitelCode(AdellijkeTitel.M.getCode()),
                        "Vries",
                        19591501,
                        "0599",
                        "6030",
                        null,
                        null,
                        new BrpGeslachtsaanduidingCode(Geslachtsaanduiding.VROUW.getCode()));
        final BrpToevalligeGebeurtenisVerbintenisSluiting sluiting =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakSluiting(BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP, 20110101, "0599", null, "6030");
        final BrpToevalligeGebeurtenisVerbintenisSluiting omzetting =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakSluiting(BrpSoortRelatieCode.HUWELIJK, 20161001, "0600", null, "6030");
        final BrpToevalligeGebeurtenisVerbintenis verbintenis = new BrpToevalligeGebeurtenisVerbintenis(partner, sluiting, null, omzetting);
        VerwerkToevalligeGebeurtenisVerzoekHelper.maakRelatie(
                2L,
                BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP,
                new BrpDatum(20110101, null),
                new BrpGemeenteCode("0599"),
                new BrpLandOfGebiedCode("6030"),
                null,
                new BrpDatum(20161001, null),
                new BrpGemeenteCode("0600"),
                new BrpLandOfGebiedCode("6030"),
                null,
                null,
                brpPersoonslijstPartner.getGeboorteStapel(),
                brpPersoonslijstPartner.getGeslachtsaanduidingStapel(),
                brpPersoonslijstPartner.getSamengesteldeNaamStapel(),
                true,
                vervallenRelatie);

        PowerMockito.when(brpToevalligeGebeurtenis.getPersoon()).thenReturn(tgbPersoon);
        PowerMockito.when(brpToevalligeGebeurtenis.getVerbintenis()).thenReturn(verbintenis);
        PowerMockito.when(brpToevalligeGebeurtenis.getPersoon()).thenReturn(tgbPersoon);
        PowerMockito.when(brpToevalligeGebeurtenis.getDatumAanvang()).thenReturn(new BrpDatum(20161001, null));
        PowerMockito.when(brpToevalligeGebeurtenis.getRegisterGemeente()).thenReturn(new BrpPartijCode("060001"));
        PowerMockito.when(brpToevalligeGebeurtenis.getNummerAkte()).thenReturn(new BrpString("5H", null));
        PowerMockito.when(brpToevalligeGebeurtenis.getDoelPartijCode()).thenReturn(new BrpPartijCode("060001"));
        subject.vindRelatie(brpPersoonslijst, brpToevalligeGebeurtenis);
    }

    @Test(expected = ControleException.class)
    public void testOngelijkGeboorteGeenActueel() throws ControleException {
        final boolean vervallenRelatie = false;
        final boolean vervallenGeboorte = true;
        final boolean vervallenGeslacht = false;
        final boolean vervallenBijhouding = false;
        final boolean vervallenSamengesteldeNaam = false;
        final boolean geenVervallenReden = false;
        maakPersoon(vervallenRelatie, vervallenGeboorte, vervallenGeslacht, vervallenBijhouding, vervallenSamengesteldeNaam, geenVervallenReden);
        final BrpToevalligeGebeurtenisPersoon tgbPersoon =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
                        "2354545000",
                        "456245245",
                        null,
                        "Henk",
                        "van",
                        ' ',
                        null,
                        "Heusden",
                        19581512,
                        "0599",
                        "6030",
                        null,
                        null,
                        new BrpGeslachtsaanduidingCode(Geslachtsaanduiding.MAN.getCode()));
        final BrpToevalligeGebeurtenisPersoon partner =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
                        "2354545000",
                        "456245245",
                        null,
                        "Truus",
                        null,
                        null,
                        new BrpAdellijkeTitelCode(AdellijkeTitel.M.getCode()),
                        "Vries",
                        19591501,
                        "0599",
                        "6030",
                        null,
                        null,
                        new BrpGeslachtsaanduidingCode(Geslachtsaanduiding.VROUW.getCode()));
        final BrpToevalligeGebeurtenisVerbintenisSluiting sluiting =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakSluiting(BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP, 20110101, "0599", null, "6030");
        final BrpToevalligeGebeurtenisVerbintenisSluiting omzetting =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakSluiting(BrpSoortRelatieCode.HUWELIJK, 20161001, "0600", null, "6030");
        final BrpToevalligeGebeurtenisVerbintenis verbintenis = new BrpToevalligeGebeurtenisVerbintenis(partner, sluiting, null, omzetting);
        VerwerkToevalligeGebeurtenisVerzoekHelper.maakRelatie(
                2L,
                BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP,
                new BrpDatum(20110101, null),
                new BrpGemeenteCode("0599"),
                new BrpLandOfGebiedCode("6030"),
                null,
                new BrpDatum(20161001, null),
                new BrpGemeenteCode("0600"),
                new BrpLandOfGebiedCode("6030"),
                null,
                null,
                brpPersoonslijstPartner.getGeboorteStapel(),
                brpPersoonslijstPartner.getGeslachtsaanduidingStapel(),
                brpPersoonslijstPartner.getSamengesteldeNaamStapel(),
                true,
                vervallenRelatie);

        PowerMockito.when(brpToevalligeGebeurtenis.getPersoon()).thenReturn(tgbPersoon);
        PowerMockito.when(brpToevalligeGebeurtenis.getVerbintenis()).thenReturn(verbintenis);
        PowerMockito.when(brpToevalligeGebeurtenis.getPersoon()).thenReturn(tgbPersoon);
        PowerMockito.when(brpToevalligeGebeurtenis.getDatumAanvang()).thenReturn(new BrpDatum(20161001, null));
        PowerMockito.when(brpToevalligeGebeurtenis.getRegisterGemeente()).thenReturn(new BrpPartijCode("060001"));
        PowerMockito.when(brpToevalligeGebeurtenis.getNummerAkte()).thenReturn(new BrpString("5H", null));
        PowerMockito.when(brpToevalligeGebeurtenis.getDoelPartijCode()).thenReturn(new BrpPartijCode("060001"));
        subject.vindRelatie(brpPersoonslijst, brpToevalligeGebeurtenis);
    }

    @Test(expected = ControleException.class)
    public void testOngelijkSamengesteldeNaam() throws ControleException {
        final boolean vervallenRelatie = false;
        final boolean vervallenGeboorte = false;
        final boolean vervallenGeslacht = false;
        final boolean vervallenBijhouding = false;
        final boolean vervallenSamengesteldeNaam = false;
        final boolean geenVervallenReden = false;
        maakPersoon(vervallenRelatie, vervallenGeboorte, vervallenGeslacht, vervallenBijhouding, vervallenSamengesteldeNaam, geenVervallenReden);
        final BrpToevalligeGebeurtenisPersoon tgbPersoon =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
                        "2354545000",
                        "456245245",
                        null,
                        "Henk",
                        "van",
                        ' ',
                        null,
                        "Heusden",
                        19581512,
                        "0599",
                        "6030",
                        null,
                        null,
                        new BrpGeslachtsaanduidingCode(Geslachtsaanduiding.MAN.getCode()));
        final BrpToevalligeGebeurtenisPersoon partner =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
                        "2354545000",
                        "456245245",
                        null,
                        "Truus",
                        null,
                        null,
                        new BrpAdellijkeTitelCode(AdellijkeTitel.M.getCode()),
                        "Fries",
                        19581501,
                        "0599",
                        "6030",
                        null,
                        null,
                        new BrpGeslachtsaanduidingCode(Geslachtsaanduiding.VROUW.getCode()));
        final BrpToevalligeGebeurtenisVerbintenisSluiting sluiting =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakSluiting(BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP, 20110101, "0599", null, "6030");
        final BrpToevalligeGebeurtenisVerbintenisSluiting omzetting =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakSluiting(BrpSoortRelatieCode.HUWELIJK, 20161001, "0600", null, "6030");
        final BrpToevalligeGebeurtenisVerbintenis verbintenis = new BrpToevalligeGebeurtenisVerbintenis(partner, sluiting, null, omzetting);
        VerwerkToevalligeGebeurtenisVerzoekHelper.maakRelatie(
                2L,
                BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP,
                new BrpDatum(20110101, null),
                new BrpGemeenteCode("0599"),
                new BrpLandOfGebiedCode("6030"),
                null,
                new BrpDatum(20161001, null),
                new BrpGemeenteCode("0600"),
                new BrpLandOfGebiedCode("6030"),
                null,
                null,
                brpPersoonslijstPartner.getGeboorteStapel(),
                brpPersoonslijstPartner.getGeslachtsaanduidingStapel(),
                brpPersoonslijstPartner.getSamengesteldeNaamStapel(),
                true,
                vervallenRelatie);

        PowerMockito.when(brpToevalligeGebeurtenis.getPersoon()).thenReturn(tgbPersoon);
        PowerMockito.when(brpToevalligeGebeurtenis.getVerbintenis()).thenReturn(verbintenis);
        PowerMockito.when(brpToevalligeGebeurtenis.getPersoon()).thenReturn(tgbPersoon);
        PowerMockito.when(brpToevalligeGebeurtenis.getDatumAanvang()).thenReturn(new BrpDatum(20161001, null));
        PowerMockito.when(brpToevalligeGebeurtenis.getRegisterGemeente()).thenReturn(new BrpPartijCode("060001"));
        PowerMockito.when(brpToevalligeGebeurtenis.getNummerAkte()).thenReturn(new BrpString("5H", null));
        PowerMockito.when(brpToevalligeGebeurtenis.getDoelPartijCode()).thenReturn(new BrpPartijCode("060001"));
        subject.vindRelatie(brpPersoonslijst, brpToevalligeGebeurtenis);
    }

    @Test(expected = ControleException.class)
    public void testOngelijkSamengesteldeNaamGeenActueel() throws ControleException {
        final boolean vervallenRelatie = false;
        final boolean vervallenGeboorte = false;
        final boolean vervallenGeslacht = false;
        final boolean vervallenBijhouding = false;
        final boolean vervallenSamengesteldeNaam = true;
        final boolean geenVervallenReden = false;
        maakPersoon(vervallenRelatie, vervallenGeboorte, vervallenGeslacht, vervallenBijhouding, vervallenSamengesteldeNaam, geenVervallenReden);
        final BrpToevalligeGebeurtenisPersoon tgbPersoon =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
                        "2354545000",
                        "456245245",
                        null,
                        "Henk",
                        "van",
                        ' ',
                        null,
                        "Heusden",
                        19581512,
                        "0599",
                        "6030",
                        null,
                        null,
                        new BrpGeslachtsaanduidingCode(Geslachtsaanduiding.MAN.getCode()));
        final BrpToevalligeGebeurtenisPersoon partner =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
                        "2354545000",
                        "456245245",
                        null,
                        "Truus",
                        null,
                        null,
                        new BrpAdellijkeTitelCode(AdellijkeTitel.M.getCode()),
                        "Fries",
                        19581501,
                        "0599",
                        "6030",
                        null,
                        null,
                        new BrpGeslachtsaanduidingCode(Geslachtsaanduiding.VROUW.getCode()));
        final BrpToevalligeGebeurtenisVerbintenisSluiting sluiting =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakSluiting(BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP, 20110101, "0599", null, "6030");
        final BrpToevalligeGebeurtenisVerbintenisSluiting omzetting =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakSluiting(BrpSoortRelatieCode.HUWELIJK, 20161001, "0600", null, "6030");
        final BrpToevalligeGebeurtenisVerbintenis verbintenis = new BrpToevalligeGebeurtenisVerbintenis(partner, sluiting, null, omzetting);
        VerwerkToevalligeGebeurtenisVerzoekHelper.maakRelatie(
                2L,
                BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP,
                new BrpDatum(20110101, null),
                new BrpGemeenteCode("0599"),
                new BrpLandOfGebiedCode("6030"),
                null,
                new BrpDatum(20161001, null),
                new BrpGemeenteCode("0600"),
                new BrpLandOfGebiedCode("6030"),
                null,
                null,
                brpPersoonslijstPartner.getGeboorteStapel(),
                brpPersoonslijstPartner.getGeslachtsaanduidingStapel(),
                brpPersoonslijstPartner.getSamengesteldeNaamStapel(),
                true,
                vervallenRelatie);

        PowerMockito.when(brpToevalligeGebeurtenis.getPersoon()).thenReturn(tgbPersoon);
        PowerMockito.when(brpToevalligeGebeurtenis.getVerbintenis()).thenReturn(verbintenis);
        PowerMockito.when(brpToevalligeGebeurtenis.getPersoon()).thenReturn(tgbPersoon);
        PowerMockito.when(brpToevalligeGebeurtenis.getDatumAanvang()).thenReturn(new BrpDatum(20161001, null));
        PowerMockito.when(brpToevalligeGebeurtenis.getRegisterGemeente()).thenReturn(new BrpPartijCode("060001"));
        PowerMockito.when(brpToevalligeGebeurtenis.getNummerAkte()).thenReturn(new BrpString("5H", null));
        PowerMockito.when(brpToevalligeGebeurtenis.getDoelPartijCode()).thenReturn(new BrpPartijCode("060001"));
        subject.vindRelatie(brpPersoonslijst, brpToevalligeGebeurtenis);
    }

    @Test(expected = ControleException.class)
    public void testOngelijkGeslacht() throws ControleException {
        final boolean vervallenRelatie = false;
        final boolean vervallenGeboorte = false;
        final boolean vervallenGeslacht = false;
        final boolean vervallenBijhouding = false;
        final boolean vervallenSamengesteldeNaam = false;
        final boolean geenVervallenReden = false;
        maakPersoon(vervallenRelatie, vervallenGeboorte, vervallenGeslacht, vervallenBijhouding, vervallenSamengesteldeNaam, geenVervallenReden);
        final BrpToevalligeGebeurtenisPersoon tgbPersoon =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
                        "2354545000",
                        "456245245",
                        null,
                        "Henk",
                        "van",
                        ' ',
                        null,
                        "Heusden",
                        19581512,
                        "0599",
                        "6030",
                        null,
                        null,
                        new BrpGeslachtsaanduidingCode(Geslachtsaanduiding.MAN.getCode()));
        final BrpToevalligeGebeurtenisPersoon partner =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
                        "2354545000",
                        "456245245",
                        null,
                        "Truus",
                        null,
                        null,
                        new BrpAdellijkeTitelCode(AdellijkeTitel.M.getCode()),
                        "Vries",
                        19581501,
                        "0599",
                        "6030",
                        null,
                        null,
                        new BrpGeslachtsaanduidingCode(Geslachtsaanduiding.MAN.getCode()));
        final BrpToevalligeGebeurtenisVerbintenisSluiting sluiting =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakSluiting(BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP, 20110101, "0599", null, "6030");
        final BrpToevalligeGebeurtenisVerbintenisSluiting omzetting =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakSluiting(BrpSoortRelatieCode.HUWELIJK, 20161001, "0600", null, "6030");
        final BrpToevalligeGebeurtenisVerbintenis verbintenis = new BrpToevalligeGebeurtenisVerbintenis(partner, sluiting, null, omzetting);
        VerwerkToevalligeGebeurtenisVerzoekHelper.maakRelatie(
                2L,
                BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP,
                new BrpDatum(20110101, null),
                new BrpGemeenteCode("0599"),
                new BrpLandOfGebiedCode("6030"),
                null,
                new BrpDatum(20161001, null),
                new BrpGemeenteCode("0600"),
                new BrpLandOfGebiedCode("6030"),
                null,
                null,
                brpPersoonslijstPartner.getGeboorteStapel(),
                brpPersoonslijstPartner.getGeslachtsaanduidingStapel(),
                brpPersoonslijstPartner.getSamengesteldeNaamStapel(),
                true,
                vervallenRelatie);

        PowerMockito.when(brpToevalligeGebeurtenis.getPersoon()).thenReturn(tgbPersoon);
        PowerMockito.when(brpToevalligeGebeurtenis.getVerbintenis()).thenReturn(verbintenis);
        PowerMockito.when(brpToevalligeGebeurtenis.getPersoon()).thenReturn(tgbPersoon);
        PowerMockito.when(brpToevalligeGebeurtenis.getDatumAanvang()).thenReturn(new BrpDatum(20161001, null));
        PowerMockito.when(brpToevalligeGebeurtenis.getRegisterGemeente()).thenReturn(new BrpPartijCode("060001"));
        PowerMockito.when(brpToevalligeGebeurtenis.getNummerAkte()).thenReturn(new BrpString("5H", null));
        PowerMockito.when(brpToevalligeGebeurtenis.getDoelPartijCode()).thenReturn(new BrpPartijCode("060001"));

        subject.vindRelatie(brpPersoonslijst, brpToevalligeGebeurtenis);
    }

    @Test(expected = ControleException.class)
    public void testOngelijkGeslachtGeenActueel() throws ControleException {
        final boolean vervallenRelatie = false;
        final boolean vervallenGeboorte = false;
        final boolean vervallenGeslacht = true;
        final boolean vervallenBijhouding = false;
        final boolean vervallenSamengesteldeNaam = false;
        final boolean geenVervallenReden = false;
        maakPersoon(vervallenRelatie, vervallenGeboorte, vervallenGeslacht, vervallenBijhouding, vervallenSamengesteldeNaam, geenVervallenReden);
        final BrpToevalligeGebeurtenisPersoon tgbPersoon =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
                        "2354545000",
                        "456245245",
                        null,
                        "Henk",
                        "van",
                        ' ',
                        null,
                        "Heusden",
                        19581512,
                        "0599",
                        "6030",
                        null,
                        null,
                        new BrpGeslachtsaanduidingCode(Geslachtsaanduiding.MAN.getCode()));
        final BrpToevalligeGebeurtenisPersoon partner =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakPersoon(
                        "2354545000",
                        "456245245",
                        null,
                        "Truus",
                        null,
                        null,
                        new BrpAdellijkeTitelCode(AdellijkeTitel.M.getCode()),
                        "Vries",
                        19581501,
                        "0599",
                        "6030",
                        null,
                        null,
                        new BrpGeslachtsaanduidingCode(Geslachtsaanduiding.MAN.getCode()));
        final BrpToevalligeGebeurtenisVerbintenisSluiting sluiting =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakSluiting(BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP, 20110101, "0599", null, "6030");
        final BrpToevalligeGebeurtenisVerbintenisSluiting omzetting =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakSluiting(BrpSoortRelatieCode.HUWELIJK, 20161001, "0600", null, "6030");
        final BrpToevalligeGebeurtenisVerbintenis verbintenis = new BrpToevalligeGebeurtenisVerbintenis(partner, sluiting, null, omzetting);
        VerwerkToevalligeGebeurtenisVerzoekHelper.maakRelatie(
                2L,
                BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP,
                new BrpDatum(20110101, null),
                new BrpGemeenteCode("0599"),
                new BrpLandOfGebiedCode("6030"),
                null,
                new BrpDatum(20161001, null),
                new BrpGemeenteCode("0600"),
                new BrpLandOfGebiedCode("6030"),
                null,
                null,
                brpPersoonslijstPartner.getGeboorteStapel(),
                brpPersoonslijstPartner.getGeslachtsaanduidingStapel(),
                brpPersoonslijstPartner.getSamengesteldeNaamStapel(),
                true,
                vervallenRelatie);

        PowerMockito.when(brpToevalligeGebeurtenis.getPersoon()).thenReturn(tgbPersoon);
        PowerMockito.when(brpToevalligeGebeurtenis.getVerbintenis()).thenReturn(verbintenis);
        PowerMockito.when(brpToevalligeGebeurtenis.getPersoon()).thenReturn(tgbPersoon);
        PowerMockito.when(brpToevalligeGebeurtenis.getDatumAanvang()).thenReturn(new BrpDatum(20161001, null));
        PowerMockito.when(brpToevalligeGebeurtenis.getRegisterGemeente()).thenReturn(new BrpPartijCode("060001"));
        PowerMockito.when(brpToevalligeGebeurtenis.getNummerAkte()).thenReturn(new BrpString("5H", null));
        PowerMockito.when(brpToevalligeGebeurtenis.getDoelPartijCode()).thenReturn(new BrpPartijCode("060001"));

        subject.vindRelatie(brpPersoonslijst, brpToevalligeGebeurtenis);
    }

    private void maakPersoon(
            final boolean vervallenRelatie,
            final boolean vervallenGeboorte,
            final boolean vervallenGeslacht,
            final boolean vervallenBijhouding,
            final boolean vervallenSamengesteldeNaam,
            final boolean geenVervallenReden) {
        final BrpPersoonslijstBuilder builderPersoon = new BrpPersoonslijstBuilder();
        final BrpHistorie hisPersoon = BrpStapelHelper.his(19581512);
        final BrpHistorie hisPersoonVervallen = BrpStapelHelper.his(19581512, 20160101, 19581512, 20160101);
        final BrpActie actiePersoon = BrpStapelHelper.act(2, 20161501);
        final BrpStapel<BrpSamengesteldeNaamInhoud> samengesteldeNaamPartner =
                BrpStapelHelper.stapel(
                        BrpStapelHelper.groep(
                                BrpStapelHelper.samengesteldnaam("Truus", "Vries", null, AdellijkeTitel.M.getCode()),
                                vervallenSamengesteldeNaam ? hisPersoonVervallen : hisPersoon,
                                actiePersoon));
        final BrpStapel<BrpGeboorteInhoud> geboortePartner =
                BrpStapelHelper.stapel(
                        BrpStapelHelper.groep(BrpStapelHelper.geboorte(19581501, "0599"), vervallenGeboorte ? hisPersoonVervallen : hisPersoon, actiePersoon));
        final BrpStapel<BrpGeslachtsaanduidingInhoud> geslachtPartner =
                BrpStapelHelper.stapel(
                        BrpStapelHelper.groep(BrpStapelHelper.geslacht("V"), vervallenGeslacht ? hisPersoonVervallen : hisPersoon, actiePersoon));
        final BrpStapel<BrpBijhoudingInhoud> bijhoudingPartner =
                BrpStapelHelper.stapel(
                        BrpStapelHelper.groep(
                                BrpStapelHelper.bijhouding("060001", null, null),
                                vervallenBijhouding ? hisPersoonVervallen : hisPersoon,
                                actiePersoon));
        builderPersoon.geboorteStapel(geboortePartner);
        builderPersoon.geslachtsaanduidingStapel(geslachtPartner);
        builderPersoon.samengesteldeNaamStapel(samengesteldeNaamPartner);
        builderPersoon.bijhoudingStapel(bijhoudingPartner);
        brpPersoonslijstPartner = builderPersoon.build();

        final BrpRelatie relatieSluiting =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakRelatie(
                        1L,
                        BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP,
                        new BrpDatum(20110101, null),
                        new BrpGemeenteCode("0599"),
                        new BrpLandOfGebiedCode("6030"),
                        null,
                        new BrpDatum(20160101, null),
                        new BrpGemeenteCode("0600"),
                        new BrpLandOfGebiedCode("6030"),
                        null,
                        geenVervallenReden ? null : BrpRedenEindeRelatieCode.OMZETTING,
                        geboortePartner,
                        geslachtPartner,
                        samengesteldeNaamPartner,
                        false,
                        vervallenRelatie);
        final BrpRelatie relatieOmzetting =
                VerwerkToevalligeGebeurtenisVerzoekHelper.maakRelatie(
                        2L,
                        BrpSoortRelatieCode.HUWELIJK,
                        new BrpDatum(20160101, null),
                        new BrpGemeenteCode("0600"),
                        new BrpLandOfGebiedCode("6030"),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        geboortePartner,
                        geslachtPartner,
                        samengesteldeNaamPartner,
                        false,
                        vervallenRelatie);
        builderPersoon.persoonId(1L);
        builderPersoon.geboorteStapel(BrpStapelHelper.stapel(BrpStapelHelper.groep(BrpStapelHelper.geboorte(19581512, "0599"), hisPersoon, actiePersoon)));
        builderPersoon.geslachtsaanduidingStapel(BrpStapelHelper.stapel(BrpStapelHelper.groep(BrpStapelHelper.geslacht("M"), hisPersoon, actiePersoon)));
        builderPersoon.identificatienummersStapel(
                BrpStapelHelper.stapel(BrpStapelHelper.groep(BrpStapelHelper.identificatie("12345", "56246242"), hisPersoon, actiePersoon)));
        builderPersoon.samengesteldeNaamStapel(
                BrpStapelHelper.stapel(
                        BrpStapelHelper.groep(
                                BrpStapelHelper.samengesteldnaam("Jan", "Heusden", Predicaat.H.getCode(), AdellijkeTitel.B.getCode()),
                                hisPersoon,
                                actiePersoon)));
        final BrpStapel<BrpBijhoudingInhoud> bijhouding =
                BrpStapelHelper.stapel(BrpStapelHelper.groep(BrpStapelHelper.bijhouding("060001", null, null), hisPersoon, actiePersoon));
        builderPersoon.bijhoudingStapel(bijhouding);
        builderPersoon.relaties(Arrays.asList(relatieSluiting, relatieOmzetting));
        brpPersoonslijst = builderPersoon.build();
    }
}
