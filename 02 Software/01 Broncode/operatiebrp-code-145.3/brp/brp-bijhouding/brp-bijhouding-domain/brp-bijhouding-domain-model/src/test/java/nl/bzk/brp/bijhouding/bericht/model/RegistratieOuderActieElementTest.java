/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Nationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponent;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingSituatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.junit.Before;
import org.junit.Test;

public class RegistratieOuderActieElementTest extends AbstractElementTest {

    private static final String PERSOON_OBJECTSLEUTEL = "1234";
    private static final String OUWKIG_OBJECTSLEUTEL = "123456";
    private static final String OUDER_OBJECTSLEUTEL = "9876";
    private ElementBuilder builder;
    private AdministratieveHandeling ah = mock(AdministratieveHandeling.class);
    private BijhoudingVerzoekBericht bericht = mock(BijhoudingVerzoekBericht.class);
    private final Timestamp timestamp = new Timestamp(DatumUtil.nu().getTime());
    private BijhoudingPersoon bijhoudingPersoon;
    private BijhoudingPersoon bijhoudingOuder;
    private BijhoudingPersoon ouwkig;

    @Before
    public void setUp() {
        builder = new ElementBuilder();
        bijhoudingPersoon = new BijhoudingPersoon(new Persoon(SoortPersoon.INGESCHREVENE));
        bijhoudingPersoon.setBijhoudingSituatie(BijhoudingSituatie.AUTOMATISCHE_FIAT);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PERSOON_OBJECTSLEUTEL)).thenReturn(bijhoudingPersoon);

        ouwkig = new BijhoudingPersoon(new Persoon(SoortPersoon.INGESCHREVENE));
        ouwkig.setBijhoudingSituatie(BijhoudingSituatie.AUTOMATISCHE_FIAT);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, OUWKIG_OBJECTSLEUTEL)).thenReturn(ouwkig);

        final Persoon regOuder = new Persoon(SoortPersoon.INGESCHREVENE);
        bijhoudingOuder = new BijhoudingPersoon(regOuder);
        final PersoonGeslachtsnaamcomponent ouderGcomp = new PersoonGeslachtsnaamcomponent(regOuder, 1);
        ouderGcomp.setStam("registratieOuder");
        regOuder.getPersoonGeslachtsnaamcomponentSet().add(ouderGcomp);

        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, OUDER_OBJECTSLEUTEL)).thenReturn(bijhoudingOuder);
        when(ah.getDatumTijdRegistratie()).thenReturn(timestamp);
        when(bericht.getDatumOntvangst()).thenReturn(new DatumElement(20010102));
        when(bericht.getTijdstipOntvangst()).thenReturn(new DatumTijdElement(DatumUtil.nuAlsZonedDateTime()));

        when(ah.getPartij()).thenReturn(new Partij("partij", "100011"));
        when(getDynamischeStamtabelRepository().getNationaliteitByNationaliteitcode("0016")).thenReturn(new Nationaliteit("0016", "0016"));

        when(getDynamischeStamtabelRepository().getLandOfGebiedByCode(LandOfGebied.CODE_NEDERLAND)).thenReturn(new LandOfGebied("6030", "Nederland"));

        final ElementBuilder.AdministratieveHandelingParameters ahPara = new ElementBuilder.AdministratieveHandelingParameters();
        ahPara.partijCode(Z_PARTIJ.getCode());
        ahPara.soort(AdministratieveHandelingElementSoort.GEBOORTE_IN_NEDERLAND_MET_ERKENNING_NA_GEBOORTEDATUM);
        ahPara.acties(new LinkedList<>());
        when(bericht.getAdministratieveHandeling()).thenReturn(builder.maakAdministratieveHandelingElement("mock", ahPara));
    }


    @Test
    public void verWerkingTestMetIndicatieBVP() {
        PersoonIndicatie indicatie = new PersoonIndicatie(bijhoudingOuder, SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE);
        final PersoonIndicatieHistorie indicatieHistorie = new PersoonIndicatieHistorie(indicatie, true);
        indicatie.addPersoonIndicatieHistorie(indicatieHistorie);
        bijhoudingOuder.addPersoonIndicatie(indicatie);
        RegistratieOuderActieElement actie = maakRegistratieOuderActieElement(2001_01_07, false, 2001_01_01);
        builder.initialiseerVerzoekBericht(bericht);
        assertEquals(actie.getOuders().size(), actie.getNouwkigs().size());
        assertTrue(actie.getPersoonElementen().isEmpty());
        assertEquals(2001_01_07, actie.getPeilDatum().getWaarde().intValue());
        assertEquals(0, maakRegGeboreneActieEnKoppelBeideAanAdministratieveHandeling(actie, 2001_01_01, null).size());
        actie.setVerzoekBericht(bericht);
        controleerBetrokkenheden(2, 1, 1);
        actie.verwerk(bericht, ah);
        controleerBetrokkenheden(3, 2, 1);
        final BijhoudingPersoon kind = bericht.getAdministratieveHandeling().getHoofdActie().getHoofdPersonen().get(0);
        assertTrue(kind.getActueleIndicatie(SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE) != null);
    }

    @Test
    public void verWerkingTestMetOuderIndicatieBVPEnRegistratieNederlandseNationaliteitInBericht() {
        PersoonIndicatie indicatie = new PersoonIndicatie(bijhoudingOuder, SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE);
        final PersoonIndicatieHistorie indicatieHistorie = new PersoonIndicatieHistorie(indicatie, true);
        indicatie.addPersoonIndicatieHistorie(indicatieHistorie);
        bijhoudingOuder.addPersoonIndicatie(indicatie);
        RegistratieOuderActieElement actie = maakRegistratieOuderActieElement(2001_01_07, false, 2001_01_01);
        final RegistratieNationaliteitActieElement actieNationaliteit = maakRegistratieNationaliteitElement("0001");
        builder.initialiseerVerzoekBericht(bericht);
        assertEquals(actie.getOuders().size(), actie.getNouwkigs().size());
        assertTrue(actie.getPersoonElementen().isEmpty());
        assertEquals(2001_01_07, actie.getPeilDatum().getWaarde().intValue());
        assertEquals(0, maakRegGeboreneActieEnKoppelBeideAanAdministratieveHandeling(actie, 2001_01_01, actieNationaliteit).size());
        actie.setVerzoekBericht(bericht);
        controleerBetrokkenheden(2, 1, 1);
        actie.verwerk(bericht, ah);
        controleerBetrokkenheden(3, 2, 1);
        final BijhoudingPersoon kind = bericht.getAdministratieveHandeling().getHoofdActie().getHoofdPersonen().get(0);
        assertTrue(kind.getActueleIndicatie(SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE) == null);
    }

    @Test
    public void verWerkingTestMetOuderIndicatieBVPEnRegistratieNietNederlandseNationaliteitInBericht() {
        PersoonIndicatie indicatie = new PersoonIndicatie(bijhoudingOuder, SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE);
        final PersoonIndicatieHistorie indicatieHistorie = new PersoonIndicatieHistorie(indicatie, true);
        indicatie.addPersoonIndicatieHistorie(indicatieHistorie);
        bijhoudingOuder.addPersoonIndicatie(indicatie);
        RegistratieOuderActieElement actie = maakRegistratieOuderActieElement(2001_01_07, false, 2001_01_01);
        final RegistratieNationaliteitActieElement actieNationaliteit = maakRegistratieNationaliteitElement("0002");
        builder.initialiseerVerzoekBericht(bericht);
        assertEquals(actie.getOuders().size(), actie.getNouwkigs().size());
        assertTrue(actie.getPersoonElementen().isEmpty());
        assertEquals(2001_01_07, actie.getPeilDatum().getWaarde().intValue());
        assertEquals(0, maakRegGeboreneActieEnKoppelBeideAanAdministratieveHandeling(actie, 2001_01_01, actieNationaliteit).size());
        actie.setVerzoekBericht(bericht);
        controleerBetrokkenheden(2, 1, 1);
        actie.verwerk(bericht, ah);
        controleerBetrokkenheden(3, 2, 1);
        final BijhoudingPersoon kind = bericht.getAdministratieveHandeling().getHoofdActie().getHoofdPersonen().get(0);
        assertTrue(kind.getActueleIndicatie(SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE) != null);
    }

    @Test
    public void MeerdereOudersTest() {
        final BijhoudingPersoon bijhoudingPersoonKind = mock(BijhoudingPersoon.class);
        when(bijhoudingPersoonKind.getActueleOuders()).thenReturn(createLijstMetOuders(2));
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, COMM_ID_KIND)).thenReturn(bijhoudingPersoonKind);

        final BijhoudingPersoon bijhoudingPersoonOuder = mock(BijhoudingPersoon.class);
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "ouderSleutel")).thenReturn(bijhoudingPersoonOuder);

        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, COMM_ID_KIND)).thenReturn(bijhoudingPersoonKind);
        final RegistratieOuderActieElement actie = maakRegistratieOuderActie(DatumUtil.vandaag(), COMM_ID_KIND, null);
        final List<MeldingElement> meldingen = actie.valideerSpecifiekeInhoud();
        assertEquals(1, meldingen.size());
    }

    @Test
    public void testDatumErkenningOpVandaag() {
        final RegistratieOuderActieElement actie = maakRegistratieOuderActieElement(DatumUtil.vandaag(), false, DatumUtil.datumRondVandaag(1));
        builder.initialiseerVerzoekBericht(bericht);
        final List<MeldingElement> meldingen = maakRegGeboreneActieEnKoppelBeideAanAdministratieveHandeling(actie, DatumUtil.datumRondVandaag(1), null);
        assertTrue(meldingen.isEmpty());
    }


    @Bedrijfsregel(Regel.R2450)
    @Test
    public void GeboorteDatumGelijkAanDatumErkenningTest() {
        RegistratieOuderActieElement actie = maakRegistratieOuderActieElement(2001_01_01, false, 2001_01_01);
        builder.initialiseerVerzoekBericht(bericht);
        final List<MeldingElement> meldingen = maakRegGeboreneActieEnKoppelBeideAanAdministratieveHandeling(actie, 2001_01_01, null);
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2450, meldingen.get(0).getRegel());
    }

    @Bedrijfsregel(Regel.R2450)
    @Test
    public void GeboorteDatumNaDatumErkenningTest() {
        RegistratieOuderActieElement actie = maakRegistratieOuderActieElement(2001_01_01, false, 2001_01_02);
        final List<MeldingElement> meldingen = maakRegGeboreneActieEnKoppelBeideAanAdministratieveHandeling(actie, 2001_01_02, null);
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2450, meldingen.get(0).getRegel());
    }


    @Bedrijfsregel(Regel.R2450)
    @Test
    public void GeboorteDatumNaDatumErkenningTest_erkenning_geenGeboorteHistorie() {
        final BijhoudingPersoon persoon = mock(BijhoudingPersoon.class);
        when(persoon.getActueleDatumGeboorte()).thenReturn(20001229);
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, COMM_ID_KIND)).thenReturn(persoon);
        final List<MeldingElement> meldingen = maakRegOuderEnKoppelErkenningAH(2001_01_01, 1, false);
        assertEquals(0, meldingen.size());
    }

    @Bedrijfsregel(Regel.R1748)
    @Test
    public void GeboorteDatumNaDatumErkenningTest_erkenning_OuderDan7JaarNietDeNederlandseNationaliteit() {
        final BijhoudingPersoon persoon = mock(BijhoudingPersoon.class);
        when(persoon.getActueleDatumGeboorte()).thenReturn(20001231);
        when(persoon.heeftNederlandseNationaliteit(anyInt())).thenReturn(false);
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, COMM_ID_KIND)).thenReturn(persoon);
        final List<MeldingElement> meldingen = maakRegOuderEnKoppelErkenningAH(2008_01_01, 1, false);
        assertEquals(0, meldingen.size());
    }

    @Bedrijfsregel(Regel.R1748)
    @Test
    public void GeboorteDatumNaDatumErkenningTest_erkenning_JongerDan7JaarNietDeNederlandseNationaliteit() {
        final BijhoudingPersoon persoon = mock(BijhoudingPersoon.class);
        when(persoon.getActueleDatumGeboorte()).thenReturn(20041231);
        when(persoon.heeftNederlandseNationaliteit(anyInt())).thenReturn(false);
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, COMM_ID_KIND)).thenReturn(persoon);
        final BijhoudingPersoon ouder = mock(BijhoudingPersoon.class);
        when(ouder.heeftNederlandseNationaliteit(anyInt())).thenReturn(false);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, OUDER_OBJECTSLEUTEL)).thenReturn(ouder);
        final List<MeldingElement> meldingen = maakRegOuderEnKoppelErkenningAH(2008_01_01, 1, false);
        assertEquals(0, meldingen.size());
    }

    @Bedrijfsregel(Regel.R1748)
    @Test
    public void GeboorteDatumNaDatumErkenningTest_erkenning_JongerDan7JaarWelDeNederlandseNationaliteit() {
        final BijhoudingPersoon persoon = mock(BijhoudingPersoon.class);
        when(persoon.getActueleDatumGeboorte()).thenReturn(20041231);
        when(persoon.heeftNederlandseNationaliteit(anyInt())).thenReturn(false);
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, COMM_ID_KIND)).thenReturn(persoon);
        final BijhoudingPersoon ouder = mock(BijhoudingPersoon.class);
        when(ouder.heeftNederlandseNationaliteit(anyInt())).thenReturn(true);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, OUDER_OBJECTSLEUTEL)).thenReturn(ouder);
        final List<MeldingElement> meldingen = maakRegOuderEnKoppelErkenningAH(2008_01_01, 1, false);
        controleerRegels(meldingen, Regel.R1748);
    }

    @Bedrijfsregel(Regel.R1748)
    @Test
    public void GeboorteDatumNaDatumErkenningTest_erkenning_JongerDan7JaarWelDeNederlandseNationaliteitMaarOokInBericht() {
        final BijhoudingPersoon persoon = mock(BijhoudingPersoon.class);
        when(persoon.getActueleDatumGeboorte()).thenReturn(20041231);
        when(persoon.heeftNederlandseNationaliteit(anyInt())).thenReturn(true);
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, COMM_ID_KIND)).thenReturn(persoon);
        final BijhoudingPersoon ouder = mock(BijhoudingPersoon.class);
        when(ouder.heeftNederlandseNationaliteit(anyInt())).thenReturn(true);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, OUDER_OBJECTSLEUTEL)).thenReturn(ouder);
        final List<MeldingElement> meldingen = maakRegOuderEnKoppelErkenningAH(2008_01_01, 1, true);
        controleerRegels(meldingen);
    }

    @Bedrijfsregel(Regel.R1734)
    @Test
    public void ouderOverledenTest() {
        final BijhoudingPersoon bijhoudingPersoonKind = mock(BijhoudingPersoon.class);
        when(bijhoudingPersoonKind.getActueleOuders()).thenReturn(createLijstMetOuders(1));
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, COMM_ID_KIND)).thenReturn(bijhoudingPersoonKind);
        final PersoonBijhoudingHistorie bijhoudingHistorie =
                new PersoonBijhoudingHistorie(bijhoudingOuder, Z_PARTIJ, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.OVERLEDEN);
        bijhoudingHistorie.setDatumAanvangGeldigheid(2000_01_01);
        bijhoudingOuder.getPersoonBijhoudingHistorieSet().add(bijhoudingHistorie);

        final List<MeldingElement> meldingen = maakRegOuderEnKoppelErkenningAH(2001_01_01, 2000_01_01, false);
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1734, meldingen.get(0).getRegel());
    }

    @Test
    @Bedrijfsregel(Regel.R2783)
    public void testErkenningMeerDanAchtDagenNaGeboorte() {
        final RegistratieOuderActieElement actie = maakRegistratieOuderActieElement(DatumUtil.vandaag(), false, DatumUtil.datumRondVandaag(9));
        builder.initialiseerVerzoekBericht(bericht);
        final List<MeldingElement> meldingen = maakRegGeboreneActieEnKoppelBeideAanAdministratieveHandeling(actie, DatumUtil.datumRondVandaag(9), null);
        controleerRegels(meldingen, Regel.R2783);
    }

    @Test
    @Bedrijfsregel(Regel.R2783)
    public void testErkenningMinderDanAchtDagenNaGeboorte() {
        final BijhoudingPersoon bijhoudingPersoonKind = mock(BijhoudingPersoon.class);
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, COMM_ID_KIND)).thenReturn(bijhoudingPersoonKind);
        when(bijhoudingPersoonKind.getActueleDatumGeboorte()).thenReturn(2001_01_01);
        controleerRegels(maakRegOuderEnKoppelErkenningAH(2001_01_05, 2001_01_01, false));
    }

    private Set<Betrokkenheid> createLijstMetOuders(final int aantalOuders) {
        Set<Betrokkenheid> result = new HashSet<>();
        for (int i = 0; i < aantalOuders; i++) {
            result.add(mock(Betrokkenheid.class));
        }
        return result;
    }

    private RegistratieOuderActieElement maakRegistratieOuderActieElement(final int datumAanvangErkenning, final boolean isHoofdactie,
                                                                          final int geboorteDatumKind) {
        final PersoonGegevensElement ouder = builder.maakPersoonGegevensElement("pes_ouder", OUDER_OBJECTSLEUTEL);
        ouder.setVerzoekBericht(bericht);
        final BetrokkenheidElement
                betrokkenheid_ouder = builder.maakBetrokkenheidElement("betr_ouder", BetrokkenheidElementSoort.OUDER, ouder, null);
        betrokkenheid_ouder.setVerzoekBericht(bericht);
        List<BetrokkenheidElement> ouder_betrokkenheid = Collections.singletonList(betrokkenheid_ouder);

        FamilierechtelijkeBetrekkingElement famBetrekking;
        if (false) {
            famBetrekking = builder.maakFamilierechtelijkeBetrekkingElement("fam_b_ouder", PERSOON_OBJECTSLEUTEL, null, null, ouder_betrokkenheid);
        } else {
            famBetrekking = builder.maakFamilierechtelijkeBetrekkingElement("fam_b_ouder", null, "fam" + geboorteDatumKind, null, ouder_betrokkenheid);
        }
        final BetrokkenheidElement kindBetrokkenheid =
                builder.maakBetrokkenheidElement("betrKind", null, "b1" + geboorteDatumKind, BetrokkenheidElementSoort.KIND, famBetrekking);

        final String referentieId = isHoofdactie ? null : COMM_ID_KIND + geboorteDatumKind;
        final String objectSleutel = isHoofdactie ? COMM_ID_KIND : null;

        final PersoonRelatieElement kind =
                builder.maakPersoonRelatieElement("pers_relatie", referentieId, objectSleutel, Collections.singletonList(kindBetrokkenheid));
        kind.setVerzoekBericht(getBericht());
        final RegistratieOuderActieElement actie = builder.maakRegistratieOuderActieElement("regOuder", datumAanvangErkenning, kind);
        actie.setVerzoekBericht(getBericht());
        return actie;
    }


    private void controleerBetrokkenheden(
            final int totaalAantal, final int aantalOuders, final int aantalKinderen) {
        final Persoon kind = bericht.getAdministratieveHandeling().getHoofdActie().getHoofdPersonen().get(0).getDelegates().get(0);
        final Relatie relatie = kind.getBetrokkenheidSet().iterator().next().getRelatie();
        assertEquals(totaalAantal, relatie.getBetrokkenheidSet().size());
        assertEquals(aantalOuders, relatie.getBetrokkenheidSet(SoortBetrokkenheid.OUDER).size());
        assertEquals(aantalKinderen, relatie.getBetrokkenheidSet(SoortBetrokkenheid.KIND).size());
    }

    private List<MeldingElement> maakRegOuderEnKoppelErkenningAH(final int datumAanvang, final int geboorteDatumKind,
                                                                 final boolean voegRegistratieNederlandseNationaliteitToe) {
        final ElementBuilder builder = new ElementBuilder();
        RegistratieOuderActieElement actie = maakRegistratieOuderActieElement(datumAanvang, true, geboorteDatumKind);
        actie.getHoofdPersonen().get(0).registreerPersoonElement(actie.getPersoonElement());
        final ElementBuilder.AdministratieveHandelingParameters ahPara = new ElementBuilder.AdministratieveHandelingParameters();
        ahPara.partijCode(Z_PARTIJ.getCode());
        ahPara.soort(AdministratieveHandelingElementSoort.ERKENNING);

        final List<ActieElement> acties = new LinkedList<>();
        acties.add(actie);

        if (voegRegistratieNederlandseNationaliteitToe) {
            final ElementBuilder.PersoonParameters persoonNatParameters = new ElementBuilder.PersoonParameters();
            persoonNatParameters.nationaliteiten(Collections.singletonList(builder.maakNationaliteitElement("nationaliteit", "0001", "017")));
            final PersoonGegevensElement natPersoonGegevens = builder.maakPersoonGegevensElement("nat_kind", COMM_ID_KIND, null, persoonNatParameters);
            actie.getHoofdPersonen().get(0).registreerPersoonElement(natPersoonGegevens);
            acties.add(builder.maakRegistratieNationaliteitActieElement("reg_nat", datumAanvang, natPersoonGegevens));
        }
        ahPara.acties(acties);

        when(bericht.getAdministratieveHandeling()).thenReturn(builder.maakAdministratieveHandelingElement("comah", ahPara));

        actie.setVerzoekBericht(bericht);
        return actie.valideerSpecifiekeInhoud();
    }

    private RegistratieNationaliteitActieElement maakRegistratieNationaliteitElement(final String natCode) {
        // Nevenactie
        final NationaliteitElement natElement = builder.maakNationaliteitElement("natElement", natCode, null);
        final ElementBuilder.PersoonParameters persParams = new ElementBuilder.PersoonParameters();
        persParams.nationaliteiten(Collections.singletonList(natElement));
        final PersoonGegevensElement persoonElement = builder.maakPersoonGegevensElement("persNat", null, "nat_kind", persParams);

        return builder.maakRegistratieNationaliteitActieElement("regNationaliteitActie", 20160101, Collections.emptyList(), persoonElement);
    }

    private List<MeldingElement> maakRegGeboreneActieEnKoppelBeideAanAdministratieveHandeling(final RegistratieOuderActieElement ouderActie,
                                                                                              final Integer geboorteDatum,
                                                                                              final RegistratieNationaliteitActieElement nationaliteitActie) {
        final RegistratieGeboreneActieElement geboreneActie = maakActieGeboreneMetAlleenOuwkig(geboorteDatum, builder);
        geboreneActie.getFamilierechtelijkeBetrekking().getBetrokkenheidElementen(BetrokkenheidElementSoort.OUDER).get(0).getPersoon()
                .setVerzoekBericht(bericht);
        final List<ActieElement> acties = new LinkedList<>();
        acties.add(geboreneActie);
        acties.add(ouderActie);
        if (nationaliteitActie != null) {
            acties.add(nationaliteitActie);
        }

        final ElementBuilder.AdministratieveHandelingParameters ahPara = new ElementBuilder.AdministratieveHandelingParameters();
        ahPara.partijCode(Z_PARTIJ.getCode());
        ahPara.soort(AdministratieveHandelingElementSoort.GEBOORTE_IN_NEDERLAND_MET_ERKENNING_NA_GEBOORTEDATUM);
        ahPara.acties(acties);

        when(bericht.getAdministratieveHandeling()).thenReturn(builder.maakAdministratieveHandelingElement("comah", ahPara));
        geboreneActie.setVerzoekBericht(bericht);
        builder.initialiseerVerzoekBericht(bericht);
        geboreneActie.verwerk(bericht, ah);

        //Kind ophalen
        final PersoonElement
                inschrijvingKind = geboreneActie.getPersoonElementen().get(0);
        final String commIdKind = inschrijvingKind.getAttributen().get(BmrAttribuutEnum.COMMUNICATIE_ID_ATTRIBUUT.toString());
        final Map<String, BmrGroep> referenties = new HashMap<>();
        referenties.put(commIdKind, inschrijvingKind);

        final FamilierechtelijkeBetrekkingElement fbrGebActie = geboreneActie.getFamilierechtelijkeBetrekking();
        final String commFbrGebActie = fbrGebActie.getAttributen().get(BmrAttribuutEnum.COMMUNICATIE_ID_ATTRIBUUT.toString());

        referenties.put(commFbrGebActie, fbrGebActie);
        ouderActie.getPersoonRelatieElement().initialiseer(referenties);
        ouderActie.getPersoonRelatieElement().getBetrokkenheden().get(0).getFamilierechtelijkeBetrekking().initialiseer(referenties);
        ouderActie.setVerzoekBericht(bericht);

        return ouderActie.valideerSpecifiekeInhoud();
    }

}
