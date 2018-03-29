/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdresHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeboorteHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponent;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponentHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonOverlijdenHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenBeeindigingRelatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenWijzigingVerblijf;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RelatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import org.junit.Test;

/**
 * Unittest voor {@link FamilierechtelijkeBetrekkingElement}.
 */
public class FamilierechtelijkeBetrekkingElementTest extends AbstractElementTest {

    private static final RedenBeeindigingRelatie REDEN_BEEINDIGING_RELATIE_OUWKIG_SCHEIDING = new RedenBeeindigingRelatie('S', "Scheiding");
    private static final RedenBeeindigingRelatie REDEN_BEEINDIGING_RELATIE_OUWKIG_OVERLEIDEN = new RedenBeeindigingRelatie('O', "overlijden");
    private static final String GESLACHTSNAAM = "STAM";
    private static final LandOfGebied NEDERLAND = new LandOfGebied("6030", "NL");


    private final ElementBuilder elementBuilder = new ElementBuilder();

    @Test
    public void testGeboorteDatumtKindEnOudersCorrect() {

        FamilierechtelijkeBetrekkingElement familierechtelijkeBetrekkingElement = maakFamilierechtelijkeBetrekkingElement(20160101, 19890101, null, null, false,
                false, null, "12345", SoortPersoon.INGESCHREVENE, null, new Integer[]{}, null, null);
        List<MeldingElement> meldingen = familierechtelijkeBetrekkingElement.valideerSpecifiekeInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    @Bedrijfsregel(Regel.R1468)
    public void testGeboorteDatumtKindEnOudersR1468() {
        FamilierechtelijkeBetrekkingElement familierechtelijkeBetrekkingElement = maakFamilierechtelijkeBetrekkingElement(19490101, 19890101, null, null, false,
                false, null, "12345", SoortPersoon.INGESCHREVENE, null, new Integer[]{}, null, null);
        List<MeldingElement> meldingen = familierechtelijkeBetrekkingElement.valideerSpecifiekeInhoud();
        assertEquals(2, meldingen.size());
        assertEquals(meldingen.get(0).getRegel(), Regel.R1468);
        assertEquals(meldingen.get(1).getRegel(), Regel.R1745);
    }

    @Test
    @Bedrijfsregel(Regel.R1745)
    public void testKindOuderverschil13() {
        FamilierechtelijkeBetrekkingElement familierechtelijkeBetrekkingElement = maakFamilierechtelijkeBetrekkingElement(19990101, 19850102, null, null, false,
                false, null, "12345", SoortPersoon.INGESCHREVENE, null, new Integer[]{}, null, null);
        List<MeldingElement> meldingen = familierechtelijkeBetrekkingElement.valideerSpecifiekeInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(meldingen.get(0).getRegel(), Regel.R1745);
    }

    @Test
    @Bedrijfsregel(Regel.R1745)
    public void testKindOuderverschil51() {
        FamilierechtelijkeBetrekkingElement familierechtelijkeBetrekkingElement = maakFamilierechtelijkeBetrekkingElement(19990101, 19380102, null, null, false,
                false, null, "12345", SoortPersoon.INGESCHREVENE, null, new Integer[]{}, null, null);
        List<MeldingElement> meldingen = familierechtelijkeBetrekkingElement.valideerSpecifiekeInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(meldingen.get(0).getRegel(), Regel.R1745);
    }

    @Test
    public void testGetHoofdPersonen() throws Exception {
        FamilierechtelijkeBetrekkingElement familierechtelijkeBetrekkingElement = maakFamilierechtelijkeBetrekkingElement(20160101, 19900101, null, null, false,
                false, null, "12345", SoortPersoon.INGESCHREVENE, null, new Integer[]{}, null, null);
        assertEquals(1, familierechtelijkeBetrekkingElement.getHoofdPersonen().size());
    }

    @Test
    public void testGetPersoonElementen() throws Exception {
        FamilierechtelijkeBetrekkingElement familierechtelijkeBetrekkingElement = maakFamilierechtelijkeBetrekkingElement(20160101, 19900101, null, null, false,
                false, null, "12345", SoortPersoon.INGESCHREVENE, null, new Integer[]{}, null, null);
        assertEquals(3, familierechtelijkeBetrekkingElement.getPersoonElementen().size());
    }

    @Test
    @Bedrijfsregel(Regel.R1721)
    public void testOUWKIGRelatieBinnen306DagenBeeindigdDoorOverlijden() {
        FamilierechtelijkeBetrekkingElement
                familierechtelijkeBetrekkingElement =
                maakFamilierechtelijkeBetrekkingElement(20160101, 19890101, new RedenBeeindigingRelatie('O', "overlijden"), 20150301,
                        true, false, null, "12345", SoortPersoon.INGESCHREVENE, null, new Integer[]{}, null, null);
        List<MeldingElement> meldingen = familierechtelijkeBetrekkingElement.valideerSpecifiekeInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(meldingen.get(0).getRegel(), Regel.R1721);
    }

    @Test
    @Bedrijfsregel(Regel.R1721)
    public void testOUWKIGRelatieBinnen306DagenBeeindigdDoorOverlijdenMetDeelsOnbekendeGeboorteDatum() {
        FamilierechtelijkeBetrekkingElement
                familierechtelijkeBetrekkingElement =
                maakFamilierechtelijkeBetrekkingElement(20160000, 19890101, new RedenBeeindigingRelatie('O', "overlijden"), 20150301,
                        true, false, null, "12345", SoortPersoon.INGESCHREVENE, null, new Integer[]{}, null, null);
        List<MeldingElement> meldingen = familierechtelijkeBetrekkingElement.valideerSpecifiekeInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(meldingen.get(0).getRegel(), Regel.R1721);
    }


    @Test
    @Bedrijfsregel(Regel.R1721)
    public void testOUWKIGRelatievoor306DagenBeeindigdDoorOverlijden() {
        FamilierechtelijkeBetrekkingElement familierechtelijkeBetrekkingElement = maakFamilierechtelijkeBetrekkingElement(20160101, 19890101,
                REDEN_BEEINDIGING_RELATIE_OUWKIG_OVERLEIDEN, 20150228,
                true, false, null, "12345", SoortPersoon.INGESCHREVENE, null, new Integer[]{}, null, null);
        List<MeldingElement> meldingen = familierechtelijkeBetrekkingElement.valideerSpecifiekeInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    @Bedrijfsregel(Regel.R1721)
    public void testOUWKIGRelatievoor306DagenBeeindigdDoorScheiding() {
        FamilierechtelijkeBetrekkingElement familierechtelijkeBetrekkingElement = maakFamilierechtelijkeBetrekkingElement(20160101, 19880101,
                REDEN_BEEINDIGING_RELATIE_OUWKIG_SCHEIDING, 20150301,
                true, false, null, "12345", SoortPersoon.INGESCHREVENE, null, new Integer[]{}, null, null);
        List<MeldingElement> meldingen = familierechtelijkeBetrekkingElement.valideerSpecifiekeInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    @Bedrijfsregel(Regel.R1721)
    public void testOUWKIGRelatievNietBeeindigd() {
        FamilierechtelijkeBetrekkingElement familierechtelijkeBetrekkingElement = maakFamilierechtelijkeBetrekkingElement(20160101, 19890101, null, null,
                true, false, null, "12345", SoortPersoon.INGESCHREVENE, null, new Integer[]{}, null, null);
        List<MeldingElement> meldingen = familierechtelijkeBetrekkingElement.valideerSpecifiekeInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1721, meldingen.get(0).getRegel());
    }

    @Test
    @Bedrijfsregel(Regel.R1725)
    public void testOUWKIGmetBriefAdres() {
        FamilierechtelijkeBetrekkingElement familierechtelijkeBetrekkingElement = maakFamilierechtelijkeBetrekkingElement(20160101, 19890101, null, null,
                false, true, null, "12345", SoortPersoon.INGESCHREVENE, null, new Integer[]{}, null, null);
        List<MeldingElement> meldingen = familierechtelijkeBetrekkingElement.valideerSpecifiekeInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1725, meldingen.get(0).getRegel());
    }

    @Test
    @Bedrijfsregel(Regel.R1753)
    public void testOUWKIGmetVerhuizingNaGeboorte() {
        FamilierechtelijkeBetrekkingElement familierechtelijkeBetrekkingElement = maakFamilierechtelijkeBetrekkingElement(20160101, 19890101, null, null,
                false, false, null, "12345", SoortPersoon.INGESCHREVENE, null, new Integer[]{20160102}, null, null);
        List<MeldingElement> meldingen = familierechtelijkeBetrekkingElement.valideerSpecifiekeInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1753, meldingen.get(0).getRegel());
    }


    @Test
    @Bedrijfsregel(Regel.R1743)
    public void testOUWKIGBijhoudingsaardOnbekend() {
        FamilierechtelijkeBetrekkingElement familierechtelijkeBetrekkingElement = maakFamilierechtelijkeBetrekkingElement(20160101, 19890101, null, null,
                false, false, null, "12345", SoortPersoon.INGESCHREVENE, null, new Integer[]{}, null, Bijhoudingsaard.ONBEKEND);
        List<MeldingElement> meldingen = familierechtelijkeBetrekkingElement.valideerSpecifiekeInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1743, meldingen.get(0).getRegel());
    }

    @Test
    @Bedrijfsregel(Regel.R1743)
    public void testOUWKIGBijhoudingsaardIngezeteneBuitenlandsAdres() {
        FamilierechtelijkeBetrekkingElement familierechtelijkeBetrekkingElement = maakFamilierechtelijkeBetrekkingElement(20160101, 19890101, null, null,
                false, false, null, "12345", SoortPersoon.INGESCHREVENE, null, new Integer[]{}, "0036", Bijhoudingsaard.INGEZETENE);
        List<MeldingElement> meldingen = familierechtelijkeBetrekkingElement.valideerSpecifiekeInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1743, meldingen.get(0).getRegel());
    }


    @Test
    @Bedrijfsregel(Regel.R1746)
    public void testOUWKIGMetAnderKindBinnen306dagen() {
        FamilierechtelijkeBetrekkingElement familierechtelijkeBetrekkingElement = maakFamilierechtelijkeBetrekkingElement(20160101, 19890101, null, null,
                false, false, null, "12345", SoortPersoon.INGESCHREVENE, new Integer[]{20150302, 20140606}, new Integer[]{}, "6030", null);
        List<MeldingElement> meldingen = familierechtelijkeBetrekkingElement.valideerSpecifiekeInhoud();
        controleerRegels(meldingen, Regel.R1746);
    }

    @Test
    @Bedrijfsregel(Regel.R1746)
    public void testOUWKIGMetAnderKindBinnen306dagen_erkenning() {
        FamilierechtelijkeBetrekkingElement familierechtelijkeBetrekkingElement = maakFamilierechtelijkeBetrekkingElement(2016_01_01, 19890101, null, null,
                false, false, null, "12345", SoortPersoon.INGESCHREVENE, new Integer[]{20150302, 20140606}, new Integer[]{}, "6030", null);
        final BetrokkenheidElement betrokkenheidElement = elementBuilder.maakBetrokkenheidElement("betr", "456", null, BetrokkenheidElementSoort.KIND, familierechtelijkeBetrekkingElement);
        betrokkenheidElement.setVerzoekBericht(getBericht());
        final PersoonRelatieElement persoonRelatieElement = elementBuilder.maakPersoonRelatieElement("persoon", null, "123", Collections.singletonList(betrokkenheidElement));
        final RegistratieOuderActieElement actie = elementBuilder.maakRegistratieOuderActieElement("actieOuder", 2016_01_01, persoonRelatieElement);

        final ElementBuilder.AdministratieveHandelingParameters ahParams = new ElementBuilder.AdministratieveHandelingParameters();
        ahParams.acties(Collections.singletonList(actie));
        ahParams.soort(AdministratieveHandelingElementSoort.ERKENNING);
        ahParams.partijCode("052901");
        final AdministratieveHandelingElement ahElement = elementBuilder.maakAdministratieveHandelingElement("ahErkenning", ahParams);
        when(getBericht().getAdministratieveHandeling()).thenReturn(ahElement);
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "123")).thenReturn(new BijhoudingPersoon());

        final Persoon persoon = mock(Persoon.class);
        final Betrokkenheid betrokkenheid = new Betrokkenheid(SoortBetrokkenheid.KIND, new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING));
        betrokkenheid.setPersoon(persoon);
        when(persoon.getPersoonGeboorteHistorieSet()).thenReturn(Collections.singleton(new PersoonGeboorteHistorie(persoon, 2016_01_01, NEDERLAND)));
        final BijhoudingBetrokkenheid bijhoudingBetrokkenheid = BijhoudingBetrokkenheid.decorate(betrokkenheid);
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingBetrokkenheid.class, "456")).thenReturn(bijhoudingBetrokkenheid);

        final List<MeldingElement> meldingen = familierechtelijkeBetrekkingElement.valideerSpecifiekeInhoud();
        controleerRegels(meldingen);
    }

    @Test
    @Bedrijfsregel(Regel.R1746)
    public void testOUWKIGMetTweelingBinnen306dagen() {
        FamilierechtelijkeBetrekkingElement familierechtelijkeBetrekkingElement = maakFamilierechtelijkeBetrekkingElement(20160101, 19890101, null, null,
                false, false, null, "12345", SoortPersoon.INGESCHREVENE, new Integer[]{20150302, 20150302}, new Integer[]{}, "6030", null);
final         List<MeldingElement> meldingen = familierechtelijkeBetrekkingElement.valideerSpecifiekeInhoud();
        controleerRegels(meldingen, Regel.R1746);
    }


    @Test
    @Bedrijfsregel(Regel.R1746)
    public void testOUWKIGMetAnderKindOpZelfdeDag() {
        FamilierechtelijkeBetrekkingElement familierechtelijkeBetrekkingElement = maakFamilierechtelijkeBetrekkingElement(20160101, 19890101, null, null,
                false, false, null, "12345", SoortPersoon.INGESCHREVENE, new Integer[]{20160101, 20140606}, new Integer[]{}, "6030", null);
        List<MeldingElement> meldingen = familierechtelijkeBetrekkingElement.valideerSpecifiekeInhoud();
        controleerRegels(meldingen);
    }


    @Test
    @Bedrijfsregel(Regel.R1733)
    public void testVerwantschapNouwkigVerwant() {
        FamilierechtelijkeBetrekkingElement familierechtelijkeBetrekkingElement = maakFamilierechtelijkeBetrekkingElement(20160101, 19890101, null, null,
                false, false, null, "12345", SoortPersoon.INGESCHREVENE, new Integer[]{20150606, 20140606}, new Integer[]{}, "6030", null);
        List<MeldingElement> meldingen = familierechtelijkeBetrekkingElement.valideerSpecifiekeInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1746, meldingen.get(0).getRegel());
    }

    @Test
    public void testGetOuders() {
        PersoonGegevensElement kindPE = elementBuilder.maakPersoonGegevensElement("P.kind","1234");
        PersoonGegevensElement ouder1PE = elementBuilder.maakPersoonGegevensElement("P.ouder1","12345");
        PersoonGegevensElement ouder2PE = elementBuilder.maakPersoonGegevensElement("P.ouder2","12346");
        final BetrokkenheidElement kind = elementBuilder.maakBetrokkenheidElement("kind", BetrokkenheidElementSoort.KIND, kindPE, null);
        final BetrokkenheidElement ouder1 = elementBuilder.maakBetrokkenheidElement("ouder1", BetrokkenheidElementSoort.OUDER, ouder1PE, null);
        final BetrokkenheidElement ouder2 = elementBuilder.maakBetrokkenheidElement("ouder2", BetrokkenheidElementSoort.OUDER, ouder2PE, null);

        final FamilierechtelijkeBetrekkingElement
                fbrElement =
                elementBuilder.maakFamilierechtelijkeBetrekkingElement("fbrElement", null, Arrays.asList(kind, ouder1, ouder2));
        assertEquals(3, fbrElement.getPersoonElementen().size());
        assertEquals(2, fbrElement.getOuders().size());
    }

    @Test
    public void testGetOudersZonderPersoonElementen() {
        final BetrokkenheidElement kind = elementBuilder.maakBetrokkenheidElement("kind", BetrokkenheidElementSoort.KIND, null, null);
        final OuderschapElement ouwkig = elementBuilder.maakOuderschapElement("ouw",true);
        final OuderschapElement nouwkig = elementBuilder.maakOuderschapElement("nouw",false);
        final BetrokkenheidElement ouder1 = elementBuilder.maakBetrokkenheidElement("ouder1", BetrokkenheidElementSoort.OUDER, null, ouwkig);
        final BetrokkenheidElement ouder2 = elementBuilder.maakBetrokkenheidElement("ouder2", BetrokkenheidElementSoort.OUDER, null, nouwkig);

        final FamilierechtelijkeBetrekkingElement
                fbrElement =
                elementBuilder.maakFamilierechtelijkeBetrekkingElement("fbrElement", null, Arrays.asList(kind, ouder1, ouder2));
        assertEquals(0, fbrElement.getPersoonElementen().size());
        assertEquals(0, fbrElement.getOuders().size());
        assertNotNull(fbrElement.getOuderBetrokkenheidElement(true));
        assertNotNull(fbrElement.getOuderBetrokkenheidElement(false));
    }

    @Test
    public void testMaakRelatieEnBetrokkenen() {
        final FamilierechtelijkeBetrekkingElement
                familierechtelijkeBetrekkingElement =
                maakFamilierechtelijkeBetrekkingElement(2016_01_01, 1980_01_01, null, null, false, true, null, "12345", SoortPersoon.INGESCHREVENE, null,
                        new Integer[]{}, null, null);
        final BRPActie actie = getActie();
        final int datumAanvangGeldigheid = 2016_01_01;
        final BijhoudingRelatie relatie = familierechtelijkeBetrekkingElement.maakRelatieEntiteitEnBetrokkenen(actie, datumAanvangGeldigheid);
        elementBuilder.initialiseerVerzoekBericht(getBericht());
        assertNotNull(relatie);
        assertEquals(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING, relatie.getSoortRelatie());
        assertEquals(3, relatie.getBetrokkenheidSet().size());
    }

    @Test
    @Bedrijfsregel(Regel.R2480)
    public void testOverledenOuwkig() {
        final FamilierechtelijkeBetrekkingElement familierechtelijkeBetrekkingElement = maakFamilierechtelijkeBetrekkingElement(20160101, 19880101,
                REDEN_BEEINDIGING_RELATIE_OUWKIG_SCHEIDING, 20150301,
                true, false, null, "12345", SoortPersoon.INGESCHREVENE,
                null, new Integer[]{}, null, null, true, true, false);
        final List<MeldingElement> meldingen = familierechtelijkeBetrekkingElement.valideerSpecifiekeInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2480, meldingen.get(0).getRegel());
    }

    @Test
    @Bedrijfsregel(Regel.R2455)
    public void testMeedereOuwkigElementen() {
        final FamilierechtelijkeBetrekkingElement familierechtelijkeBetrekkingElement = maakFamilierechtelijkeBetrekkingElement(20160101, 19880101,
                REDEN_BEEINDIGING_RELATIE_OUWKIG_SCHEIDING, 20150301,
                true, false, null, "12345", SoortPersoon.INGESCHREVENE,
                null, new Integer[]{}, null, null, true, false, true);
        final List<MeldingElement> meldingen = familierechtelijkeBetrekkingElement.valideerSpecifiekeInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2455, meldingen.get(0).getRegel());
    }

    public FamilierechtelijkeBetrekkingElement maakFamilierechtelijkeBetrekkingElement(final Integer geboorteDatumKind, final Integer geboorteDatumOuder,
                                                                                       final RedenBeeindigingRelatie redenBeeindigingRelatieOUWKIG,
                                                                                       final Integer datumEindeRelatieOUWKIG,
                                                                                       final boolean voegBestaandHuwelijkToe, final boolean briefAdres,
                                                                                       final GeslachtsnaamcomponentElement geslachtsnaamComponent,
                                                                                       final String ouwkigObjectSleutel,
                                                                                       final SoortPersoon soortPersoon,
                                                                                       final Integer[] geboorteDatumsAndereKinderen,
                                                                                       final Integer[] eindDatumAdressen,
                                                                                       final String landCodeAdresOuwkig,
                                                                                       final Bijhoudingsaard ouwkigBijhoudingsaard) {
        final FamilierechtelijkeBetrekkingElement
                familierechtelijkeBetrekkingElement =
                maakFamilierechtelijkeBetrekkingElement(geboorteDatumKind, geboorteDatumOuder, redenBeeindigingRelatieOUWKIG, datumEindeRelatieOUWKIG,
                        voegBestaandHuwelijkToe,
                        briefAdres, geslachtsnaamComponent, ouwkigObjectSleutel, soortPersoon, geboorteDatumsAndereKinderen, eindDatumAdressen,
                        landCodeAdresOuwkig,
                        ouwkigBijhoudingsaard, false, false, false);
        familierechtelijkeBetrekkingElement.setVerzoekBericht(getBericht());
        return familierechtelijkeBetrekkingElement;
    }


    /**
     * indien redenBeeindiging niet null is, word ouder2 leeg gelaten en een huwelijk aan ouder 1 toegevoegd.
     * @param geboorteDatumKind geboorte datum kind
     * @param geboorteDatumOuder geboorte datum ouder
     * @param redenBeeindigingRelatieOUWKIG reden beeeindigng Huwelijk
     * @param datumEindeRelatieOUWKIG datum Einde relatie
     * @return FamilierechtelijkeBetrekkingElement
     */
    public FamilierechtelijkeBetrekkingElement maakFamilierechtelijkeBetrekkingElement(final Integer geboorteDatumKind, final Integer geboorteDatumOuder,
                                                                                       final RedenBeeindigingRelatie redenBeeindigingRelatieOUWKIG,
                                                                                       final Integer datumEindeRelatieOUWKIG,
                                                                                       final boolean voegBestaandHuwelijkToe, final boolean briefAdres,
                                                                                       final GeslachtsnaamcomponentElement geslachtsnaamComponent,
                                                                                       final String ouwkigObjectSleutel,
                                                                                       final SoortPersoon soortPersoon,
                                                                                       final Integer[] geboorteDatumsAndereKinderen,
                                                                                       final Integer[] eindDatumAdressen,
                                                                                       final String landCodeAdresOuwkig,
                                                                                       final Bijhoudingsaard ouwkigBijhoudingsaard,
                                                                                       final boolean isNouwkigVerwant, final boolean isOuwkigOverleden,
                                                                                       final boolean meerdereOuwkigElementen) {
        final List<BetrokkenheidElement> betrokkenheden = new ArrayList<>();

        final BijhoudingVerzoekBericht verzoekBerichtMock = mock(BijhoudingVerzoekBericht.class);

        //Kind
        final ElementBuilder.GeboorteParameters geboorteParametersKind = new ElementBuilder.GeboorteParameters().datum(geboorteDatumKind);
        final ElementBuilder.PersoonParameters
                persoonParamsKind =
                new ElementBuilder.PersoonParameters().geboorte(elementBuilder.maakGeboorteElement("com_geb_kind", geboorteParametersKind));
        final List<GeslachtsnaamcomponentElement> geslachtsnaamComponenten = new LinkedList<>();
        if (geslachtsnaamComponent != null) {
            geslachtsnaamComponenten.add(geslachtsnaamComponent);
        } else {
            geslachtsnaamComponenten.add(elementBuilder.maakGeslachtsnaamcomponentElement("gn_comp", null, null, null, null, GESLACHTSNAAM));
        }

        persoonParamsKind.geslachtsnaamcomponenten(geslachtsnaamComponenten);
        final PersoonGegevensElement kindElement = elementBuilder.maakPersoonGegevensElement("com_kind", null, null, persoonParamsKind);
        betrokkenheden.add(elementBuilder.maakBetrokkenheidElement("com_betr_kind", BetrokkenheidElementSoort.KIND, kindElement, null));
        //Ouder 1
        final PersoonGegevensElement ouder1Element = maakOuwkig(ouwkigObjectSleutel, verzoekBerichtMock);
        createPersoonMock(geboorteDatumOuder, redenBeeindigingRelatieOUWKIG, datumEindeRelatieOUWKIG, voegBestaandHuwelijkToe, briefAdres, verzoekBerichtMock,
                ouwkigObjectSleutel, soortPersoon, geboorteDatumsAndereKinderen, eindDatumAdressen, landCodeAdresOuwkig, ouwkigBijhoudingsaard,
                isNouwkigVerwant, isOuwkigOverleden);
        final OuderschapElement ouderschapElement = new OuderschapElement(new HashMap<>(), new BooleanElement(Boolean.TRUE));

        final BetrokkenheidElement betrOuder1 = elementBuilder.maakBetrokkenheidElement("com_betr_ouder1", BetrokkenheidElementSoort.OUDER, ouder1Element,
                ouderschapElement);
        betrOuder1.setVerzoekBericht(getBericht());
        betrokkenheden.add(betrOuder1);

        if (meerdereOuwkigElementen) {
            final BetrokkenheidElement
                    betr_ouder1a =
                    elementBuilder.maakBetrokkenheidElement("com_betr_ouder1a", BetrokkenheidElementSoort.OUDER, ouder1Element,
                            ouderschapElement);
            betr_ouder1a.setVerzoekBericht(getBericht());
            betrokkenheden.add(betr_ouder1a);
        }

        //Ouder 2
        voegHuwelijkToeAanOuwkig(geboorteDatumOuder, voegBestaandHuwelijkToe, betrokkenheden, verzoekBerichtMock);
        final FamilierechtelijkeBetrekkingElement
                familierechtelijkeBetrekkingElement =
                elementBuilder.maakFamilierechtelijkeBetrekkingElement("com_frb", null, betrokkenheden);
        //verzoekbericht.getAH
        final ElementBuilder.AdministratieveHandelingParameters administratieveHandelingParameters = new ElementBuilder.AdministratieveHandelingParameters();
        administratieveHandelingParameters.partijCode("1");
        administratieveHandelingParameters.soort(AdministratieveHandelingElementSoort.GEBOORTE_IN_NEDERLAND);
        final List<ActieElement> acties = new LinkedList<>();
        acties.add(elementBuilder.maakRegistratieGeboreneActieElement("actie", 20100101, familierechtelijkeBetrekkingElement));
        administratieveHandelingParameters.acties(acties);
        final AdministratieveHandelingElement
                administratieveHandelingElement =
                elementBuilder.maakAdministratieveHandelingElement("ah", administratieveHandelingParameters);
        when(verzoekBerichtMock.getAdministratieveHandeling()).thenReturn(administratieveHandelingElement);
        familierechtelijkeBetrekkingElement.setVerzoekBericht(verzoekBerichtMock);
        return familierechtelijkeBetrekkingElement;
    }

    private void voegHuwelijkToeAanOuwkig(final Integer geboorteDatumOuder, final boolean voegBestaandHuwelijkToe,
                                          final List<BetrokkenheidElement> betrokkenheden, final BijhoudingVerzoekBericht verzoekBerichtMock) {
        if (!voegBestaandHuwelijkToe) {
            final ElementBuilder.PersoonParameters persoonParamsOuder2 = new ElementBuilder.PersoonParameters();
            persoonParamsOuder2
                    .geboorte(elementBuilder.maakGeboorteElement("com_ouder2_geboorte",
                            new ElementBuilder.GeboorteParameters().datum(geboorteDatumOuder).landGebiedCode("6030")));
            final PersoonGegevensElement ouder2Element = elementBuilder.maakPersoonGegevensElement("com_ouder2", "6666", null, persoonParamsOuder2);
            ouder2Element.setVerzoekBericht(verzoekBerichtMock);
            final OuderschapElement ouderschapElement2 = new OuderschapElement(new HashMap<>(), new BooleanElement(Boolean.FALSE));
            final BetrokkenheidElement betr_ouder2 = elementBuilder.maakBetrokkenheidElement("com_betr_ouder2", BetrokkenheidElementSoort.OUDER,
                    ouder2Element, ouderschapElement2);
            betr_ouder2.setVerzoekBericht(getBericht());
            betrokkenheden.add(betr_ouder2);

            when(getDynamischeStamtabelRepository().getLandOfGebiedByCode(anyString())).thenReturn(NEDERLAND);

            BijhoudingPersoon nouwkigPersoon = mock(BijhoudingPersoon.class);
            when(nouwkigPersoon.getId()).thenReturn(6666L);
            PersoonGeboorteHistorie gebHistorie = new PersoonGeboorteHistorie(nouwkigPersoon, 1988_01_01, new LandOfGebied("0010", "land"));
            when(nouwkigPersoon.getPersoonGeboorteHistorieSet()).thenReturn(new HashSet<>(Collections.singletonList(gebHistorie)));
            when(verzoekBerichtMock.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "6666")).thenReturn(nouwkigPersoon);
        }
    }

    private PersoonGegevensElement maakOuwkig(final String ouwkigObjectSleutel, final BijhoudingVerzoekBericht verzoekBerichtMock) {
        final ElementBuilder.PersoonParameters persoonParamsOuder1 = new ElementBuilder.PersoonParameters();
        if (ouwkigObjectSleutel == null) {

            final ElementBuilder.GeboorteParameters geboorteParametersOuder = new ElementBuilder.GeboorteParameters();
            geboorteParametersOuder.datum(19800101);
            persoonParamsOuder1.geboorte(elementBuilder.maakGeboorteElement("oudergeb", geboorteParametersOuder));
        }

        final PersoonGegevensElement ouder1Element = elementBuilder.maakPersoonGegevensElement("com_ouder1", ouwkigObjectSleutel, null, persoonParamsOuder1);
        ouder1Element.setVerzoekBericht(verzoekBerichtMock);
        return ouder1Element;
    }

    private void createPersoonMock(final Integer geboorteDatumOuder, final RedenBeeindigingRelatie redenBeeindigingRelatieOUWKIG,
                                   final Integer datumEindeRelatieOUWKIG, final boolean voegBestaandHuwelijkToe, final boolean briefAdres,
                                   final BijhoudingVerzoekBericht verzoekBerichtMock, final String ouwkigObjectSleutel,
                                   final SoortPersoon soortPersoon, final Integer[] geboorteDatumsAndereKinderen, final Integer[] eindDatumsAdressen,
                                   final String landCodeAdres, final Bijhoudingsaard bijhoudingsaard, final boolean isNouwkigVerwant,
                                   final boolean isOuwkigOverleden) {
        BijhoudingPersoon persoonMock = mock(BijhoudingPersoon.class);
        PersoonGeboorteHistorie
                persoonGeboorteHistorie =
                new PersoonGeboorteHistorie(persoonMock, geboorteDatumOuder, new LandOfGebied("0023", "LandOfGebied"));
        final List<PersoonElement> persoonElementen = new LinkedList<>();
        persoonElementen.add(elementBuilder.maakPersoonGegevensElement("persoon_mock", ouwkigObjectSleutel));
        when(persoonMock.getPersoonElementen()).thenReturn(persoonElementen);
        when(persoonMock.getSoortPersoon()).thenReturn(soortPersoon);

        when(persoonMock.getPersoonGeboorteHistorieSet()).thenReturn(new HashSet<>(Collections.singleton(persoonGeboorteHistorie)));
        Set<PersoonGeslachtsnaamcomponent> persoonGeslachtsnaamcomponentSet = new LinkedHashSet<>();
        PersoonGeslachtsnaamcomponent persoonGeslachtsnaamcomponent = new PersoonGeslachtsnaamcomponent(persoonMock, 1);
        final PersoonGeslachtsnaamcomponentHistorie
                persoonGeslachtsnaamcomponentHistorie = new PersoonGeslachtsnaamcomponentHistorie(persoonGeslachtsnaamcomponent, GESLACHTSNAAM);

        persoonGeslachtsnaamcomponent.addPersoonGeslachtsnaamcomponentHistorie(persoonGeslachtsnaamcomponentHistorie);
        persoonGeslachtsnaamcomponentSet.add(persoonGeslachtsnaamcomponent);
        when(persoonMock.getPersoonGeslachtsnaamcomponentSet()).thenReturn(persoonGeslachtsnaamcomponentSet);

        final Set<PersoonAdres> adresSet = new HashSet<>();
        for (Integer eindDatum : eindDatumsAdressen) {
            voegAdresToe(briefAdres, persoonMock, adresSet, eindDatum, landCodeAdres);
        }
        voegAdresToe(briefAdres, persoonMock, adresSet, null, landCodeAdres);
        when(persoonMock.getPersoonAdresSet()).thenReturn(adresSet);

        if (voegBestaandHuwelijkToe) {
            voegHuwelijkToeAanOUWKIG(redenBeeindigingRelatieOUWKIG, datumEindeRelatieOUWKIG, persoonMock);
        }

        PersoonBijhoudingHistorie persoonBijhoudingHistorie = new PersoonBijhoudingHistorie(persoonMock, new Partij("partij", "000001"),
                bijhoudingsaard != null ? bijhoudingsaard : Bijhoudingsaard.INGEZETENE,
                NadereBijhoudingsaard.ACTUEEL);
        Set<PersoonBijhoudingHistorie> ouwkigBijhoudingHistorie = new LinkedHashSet<>();
        ouwkigBijhoudingHistorie.add(persoonBijhoudingHistorie);
        when(persoonMock.getPersoonBijhoudingHistorieSet()).thenReturn(ouwkigBijhoudingHistorie);

        persoonMock.getPersoonGeboorteHistorieSet().add(persoonGeboorteHistorie);
        when(verzoekBerichtMock.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, ouwkigObjectSleutel)).thenReturn(persoonMock);
        if (geboorteDatumsAndereKinderen != null) {
            final Set<Betrokkenheid> kinderen = new LinkedHashSet<>();
            for (Integer geboorteDatum : geboorteDatumsAndereKinderen) {
                final Betrokkenheid betrokkenheid = new Betrokkenheid(SoortBetrokkenheid.KIND, new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING));
                final Persoon kind = new Persoon(SoortPersoon.INGESCHREVENE);
                final PersoonGeboorteHistorie geboorteHistorie = new PersoonGeboorteHistorie(kind, geboorteDatum, NEDERLAND);
                kind.addPersoonGeboorteHistorie(geboorteHistorie);
                betrokkenheid.setPersoon(kind);
                kinderen.add(betrokkenheid);
            }

            when(persoonMock.getActueleKinderen()).thenReturn(kinderen);
        }

        if (isNouwkigVerwant) {
            Persoon ouderMock = mock(BijhoudingPersoon.class);
            when(ouderMock.getId()).thenReturn(6666L);
            Relatie ouderRelatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
            Betrokkenheid ouderBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.OUDER, ouderRelatie);
            ouderBetrokkenheid.setPersoon(ouderMock);
            when(persoonMock.getActueleOuders()).thenReturn(new HashSet<>(Collections.singletonList(ouderBetrokkenheid)));
        }

        if (isOuwkigOverleden) {
            PersoonOverlijdenHistorie persoonOverlijdenHistorie = new PersoonOverlijdenHistorie(persoonMock, 20000101,
                    new LandOfGebied("0001", "landOfGebied"));
            when(persoonMock.getNadereBijhoudingsaard()).thenReturn(NadereBijhoudingsaard.OVERLEDEN);
            when(persoonMock.getPersoonOverlijdenHistorieSet()).thenReturn(new HashSet<>(Collections.singletonList(persoonOverlijdenHistorie)));
        }

    }

    private void voegAdresToe(final boolean briefAdres, final BijhoudingPersoon persoonMock, final Set<PersoonAdres> adresSet, final Integer eindDatum,
                              final String landCode) {
        final PersoonAdres persoonAdres = new PersoonAdres(persoonMock);
        final PersoonAdresHistorie
                persoonAdresHistorie =
                new PersoonAdresHistorie(persoonAdres, briefAdres ? SoortAdres.BRIEFADRES : SoortAdres.WOONADRES,
                        new LandOfGebied(landCode != null ? landCode : "6030", "NL"),
                        new RedenWijzigingVerblijf('P', "ben weg"));
        persoonAdresHistorie.setDatumEindeGeldigheid(eindDatum);
        persoonAdres.addPersoonAdresHistorie(persoonAdresHistorie);
        persoonAdres.setSoortAdres(briefAdres ? SoortAdres.BRIEFADRES : SoortAdres.WOONADRES);
        adresSet.add(persoonAdres);
    }

    public void voegHuwelijkToeAanOUWKIG(final RedenBeeindigingRelatie redenBeeindigingRelatieOUWKIG, final Integer datumEindeRelatieOuwkig,
                                         final BijhoudingPersoon persoonMock) {
        final Set<Betrokkenheid> partners = new HashSet<>();

        final Relatie relatie = new Relatie(SoortRelatie.HUWELIJK);
        final RelatieHistorie relatieHistorie = new RelatieHistorie(relatie);
        relatieHistorie.setRedenBeeindigingRelatie(redenBeeindigingRelatieOUWKIG);
        relatieHistorie.setDatumEinde(datumEindeRelatieOuwkig);
        relatie.getRelatieHistorieSet().add(relatieHistorie);
        final Betrokkenheid partner = new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatie);
        partners.add(partner);
        when(persoonMock.getActuelePartners()).thenReturn(partners);

    }
}
