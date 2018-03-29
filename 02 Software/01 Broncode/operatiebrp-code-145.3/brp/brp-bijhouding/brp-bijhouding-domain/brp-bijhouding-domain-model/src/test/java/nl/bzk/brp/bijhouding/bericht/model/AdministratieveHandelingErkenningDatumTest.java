/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.mockito.Matchers.anyInt;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.MaterieleHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteitHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AdministratieveHandelingErkenningDatumTest extends AbstractAdministratieveHandelingElementTest {
    public static final String PARTIJ_000001 = "000001";
    public static final Partij PARTIJ = new Partij("partij", PARTIJ_000001);
    public static final Partij PARTIJ2 = new Partij("partij", PARTIJ_000001);
    public static final Timestamp DATUM_TIJD_REGISTRATIE = new Timestamp(DatumUtil.nu().getTime());
    public static final LandOfGebied NEDERLAND = new LandOfGebied(LandOfGebied.CODE_NEDERLAND, "Nl");
    private ElementBuilder builder;
    private List<ActieElement> acties;

    @Mock
    BijhoudingVerzoekBericht bericht;
    @Mock
    BijhoudingPersoon kind = mock(BijhoudingPersoon.class);
    @Mock
    BijhoudingPersoon ouder = mock(BijhoudingPersoon.class);

    @Before
    public void setup() {
        builder = new ElementBuilder();
        acties = new LinkedList<>();
        setUpVerzoekBericht();
        setUpKindEnOuderBijhoudingsPersoon();
        when(bericht.getAdministratieveHandeling()).thenReturn(maakAdministratieveHandelingErkenning("mockAH"));
        PARTIJ2.addPartijRol(new PartijRol(PARTIJ2, Rol.BIJHOUDINGSORGAAN_COLLEGE));

        when(getDynamischeStamtabelRepository().getPartijByCode("000001")).thenReturn(PARTIJ);

    }

    @Test
    @Bedrijfsregel(Regel.R1815)
    public void TestNationaliteitNietInBerichtEnNietInDB() {
        when(kind.getActueleBijhoudingspartij()).thenReturn(PARTIJ);
        final BijhoudingPersoon kindPersoon = mock(BijhoudingPersoon.class);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1234")).thenReturn(kindPersoon);
        maakRegistratieOuderActieElementEnVoegToeAanActies(20100101);
        final AdministratieveHandelingElement administratieveHandelingElement = maakAdministratieveHandelingErkenning("ah");
        builder.initialiseerVerzoekBericht(bericht);
        final List<MeldingElement> meldingen = administratieveHandelingElement.valideerInhoud();
        controleerRegels(meldingen, Regel.R1815);

    }

    @Test
    @Bedrijfsregel(Regel.R1815)
    public void testR1815NationaliteitInBericht() {
        when(kind.getActueleBijhoudingspartij()).thenReturn(PARTIJ);
        final BijhoudingPersoon kindPersoon = mock(BijhoudingPersoon.class);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1234")).thenReturn(kindPersoon);
        maakRegistratieOuderActieElementEnVoegToeAanActies(20100101);
        maakRegistratieNationaliteitEnVoegToeAanActiesErkenning(20100101, 1);
        final AdministratieveHandelingElement administratieveHandelingElement = maakAdministratieveHandelingErkenning("ah");
        builder.initialiseerVerzoekBericht(bericht);
        final List<MeldingElement> meldingen = administratieveHandelingElement.valideerInhoud();
        controleerRegels(meldingen);
    }

    @Test
    @Bedrijfsregel(Regel.R1815)
    public void testR1815NationaliteitInBerichtMaarBeeindigtInBericht() {
        when(kind.getActueleBijhoudingspartij()).thenReturn(PARTIJ);
        final BijhoudingPersoon kindPersoon = mock(BijhoudingPersoon.class);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1234")).thenReturn(kindPersoon);
        maakRegistratieOuderActieElementEnVoegToeAanActies(20100101);
        maakRegistratieNationaliteitEnVoegToeAanActiesErkenning(20100101, 1);
        maakBeeindigNationaliteitEnVoegToeAanActiesErkenning(20110101, 1, false);
        final AdministratieveHandelingElement administratieveHandelingElement = maakAdministratieveHandelingErkenning("ah");
        builder.initialiseerVerzoekBericht(bericht);
        final List<MeldingElement> meldingen = administratieveHandelingElement.valideerInhoud();
        controleerRegels(meldingen, Regel.R1815);
    }

    @Test
    @Bedrijfsregel(Regel.R1815)
    public void testR1815NationaliteitInBerichtMaarUitDBBeeindigtInBericht() {
        when(kind.getActueleBijhoudingspartij()).thenReturn(PARTIJ);
        final BijhoudingPersoon kindPersoon = mock(BijhoudingPersoon.class);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1234")).thenReturn(kindPersoon);
        maakRegistratieOuderActieElementEnVoegToeAanActies(20100101);
        maakRegistratieNationaliteitEnVoegToeAanActiesErkenning(20100101, 1);
        maakBeeindigNationaliteitEnVoegToeAanActiesErkenning(20110101, 1, true);
        final AdministratieveHandelingElement administratieveHandelingElement = maakAdministratieveHandelingErkenning("ah");
        builder.initialiseerVerzoekBericht(bericht);
        final List<MeldingElement> meldingen = administratieveHandelingElement.valideerInhoud();
        controleerRegels(meldingen);
    }

    @Test
    @Bedrijfsregel(Regel.R1815)
    public void testR1815GeenNationaliteitInBerichtMaarInDB() {
        when(kind.getActueleBijhoudingspartij()).thenReturn(PARTIJ);
        final BijhoudingPersoon kindPersoon = mock(BijhoudingPersoon.class);
        when(kindPersoon.heeftNogActueleNationaliteitenOfIndicatieStaatLoos(anyInt())).thenReturn(true);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1234")).thenReturn(kindPersoon);
        maakRegistratieOuderActieElementEnVoegToeAanActies(20100101);
        final AdministratieveHandelingElement administratieveHandelingElement = maakAdministratieveHandelingErkenning("ah");
        builder.initialiseerVerzoekBericht(bericht);
        final List<MeldingElement> meldingen = administratieveHandelingElement.valideerInhoud();
        controleerRegels(meldingen);
    }

    @Test
    @Bedrijfsregel(Regel.R1815)
    public void testR1815GeenNationaliteitWelIndicatieStaatloos() {
        when(kind.getActueleBijhoudingspartij()).thenReturn(PARTIJ);
        final BijhoudingPersoon kindPersoon = mock(BijhoudingPersoon.class);
        when(kindPersoon.heeftNogActueleNationaliteitenOfIndicatieStaatLoos(anyInt())).thenReturn(false);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1234")).thenReturn(kindPersoon);
        maakRegistratieOuderActieElementEnVoegToeAanActies(20100101);
        maakRegistratieStaatLoosEnVoegToeAanActies(20100101);
        final AdministratieveHandelingElement administratieveHandelingElement = maakAdministratieveHandelingErkenning("ah");
        builder.initialiseerVerzoekBericht(bericht);
        final List<MeldingElement> meldingen = administratieveHandelingElement.valideerInhoud();
        controleerRegels(meldingen);
    }

    @Test
    @Bedrijfsregel(Regel.R1726)
    public void testR1726GeslachtsnaamAndersDanOuder() {
        when(kind.getActueleBijhoudingspartij()).thenReturn(PARTIJ);

        PersoonSamengesteldeNaamHistorie persoonSamengesteldeHistorieHistorie = new PersoonSamengesteldeNaamHistorie(ouder, "anders", false, false);
        persoonSamengesteldeHistorieHistorie.setDatumAanvangGeldigheid(2001_01_01);

        when(ouder.getPersoonGeslachtsnaamcomponentSet()).thenReturn(Collections.emptySet());
        final Set<PersoonSamengesteldeNaamHistorie> samengesteldeNaamHistorieSet = new HashSet<>();
        samengesteldeNaamHistorieSet.add(persoonSamengesteldeHistorieHistorie);
        when(ouder.getPersoonSamengesteldeNaamHistorieSet()).thenReturn(samengesteldeNaamHistorieSet);
        final BijhoudingPersoon kindPersoon = mock(BijhoudingPersoon.class);
        when(kindPersoon.heeftNogActueleNationaliteitenOfIndicatieStaatLoos(anyInt())).thenReturn(false);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1234")).thenReturn(kindPersoon);
        when(kindPersoon.heeftNederlandseNationaliteit(anyInt())).thenReturn(true);

        maakRegistratieOuderActieElementEnVoegToeAanActies(20150101);
        maakRegistratieGeslachtnaamVoornaamEnVoegToeAanActies(20150101, 1);
        maakRegistratieStaatLoosEnVoegToeAanActies(2015_01_01);

        final AdministratieveHandelingElement administratieveHandelingElement = maakAdministratieveHandelingErkenning("ah");
        builder.initialiseerVerzoekBericht(bericht);
        final List<MeldingElement> meldingen = administratieveHandelingElement.valideerInhoud();
        controleerRegels(meldingen, Regel.R1726);
    }

    @Test
    @Bedrijfsregel(Regel.R2489)
    /*regel 1815 wordt wel getrigger maar test is dat R2489 niet getriggerd word */
    public void TestErkenningPartijNietRolBijhouder() {
        when(getDynamischeStamtabelRepository().getPartijByCode("000001")).thenReturn(PARTIJ);
        when(kind.aantalNationaliteitenOfIndicatieStaatloos()).thenReturn(1L);
        maakRegistratieOuderActieElementEnVoegToeAanActies(20100101);

        final AdministratieveHandelingElement administratieveHandelingElement = maakAdministratieveHandelingErkenning("ah");
        final List<MeldingElement> meldingen = administratieveHandelingElement.valideerInhoud();
        controleerRegels(meldingen, Regel.R1815);
    }

    @Test
    @Bedrijfsregel(Regel.R2489)
    /*regel 1815 wordt wel getrigger maar test is dat R2489 ook getriggerd word */
    public void TestErkenningPartijRolBijhouder() {
        when(kind.aantalNationaliteitenOfIndicatieStaatloos()).thenReturn(1L);
        maakRegistratieOuderActieElementEnVoegToeAanActies(20100101);
        when(getDynamischeStamtabelRepository().getPartijByCode("000001")).thenReturn(PARTIJ2);
        final AdministratieveHandelingElement administratieveHandelingElement = maakAdministratieveHandelingErkenning("ah");
        final List<MeldingElement> meldingen = administratieveHandelingElement.valideerInhoud();
        controleerRegels(meldingen, Regel.R1815, Regel.R2489);
    }

    @Test
    @Bedrijfsregel(Regel.R2489)
    /*regel 1815 wordt wel getrigger maar test is dat R2489 niet getriggerd word */
    public void TestErkenningPartijGeboorte() {

        when(kind.aantalNationaliteitenOfIndicatieStaatloos()).thenReturn(1L);
        when(kind.getActuelePartijGeboorteGemeente()).thenReturn(PARTIJ);
        maakRegistratieOuderActieElementEnVoegToeAanActies(20100101);

        final AdministratieveHandelingElement administratieveHandelingElement = maakAdministratieveHandelingErkenning("ah");
        final List<MeldingElement> meldingen = administratieveHandelingElement.valideerInhoud();
        controleerRegels(meldingen, Regel.R1815);
    }

    @Test
    @Bedrijfsregel(Regel.R2489)
    /*regel 1815 wordt wel getrigger maar test is dat R2489 niet getriggerd word */
    public void TestErkenningPartijBijhouding() {

        when(kind.aantalNationaliteitenOfIndicatieStaatloos()).thenReturn(1L);
        when(kind.getActueleBijhoudingspartij()).thenReturn(PARTIJ);
        maakRegistratieOuderActieElementEnVoegToeAanActies(20100101);

        final AdministratieveHandelingElement administratieveHandelingElement = maakAdministratieveHandelingErkenning("ah");
        final List<MeldingElement> meldingen = administratieveHandelingElement.valideerInhoud();
        controleerRegels(meldingen, Regel.R1815);

    }


    public void setUpKindEnOuderBijhoudingsPersoon() {
        final Set<PersoonNationaliteit> nationaliteiten = new HashSet<>();
        final PersoonNationaliteit persNat = new PersoonNationaliteit(new Persoon(SoortPersoon.INGESCHREVENE), NEDERLANDS);
        final PersoonNationaliteitHistorie persNatHis = new PersoonNationaliteitHistorie(persNat);
        persNatHis.setDatumTijdRegistratie(DATUM_TIJD_REGISTRATIE);
        persNatHis.setActieInhoud(createBrpActie());
        persNatHis.setDatumAanvangGeldigheid(20100101);
        MaterieleHistorie.voegNieuweActueleToe(persNatHis, persNat.getPersoonNationaliteitHistorieSet());
        nationaliteiten.add(persNat);
        when(kind.getPersoonNationaliteitSet()).thenReturn(nationaliteiten);

        when(ouder.bepaalLeeftijd(anyInt())).thenReturn(30);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "1234")).thenReturn(kind);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, "5555")).thenReturn(ouder);
    }

    private void maakRegistratieGeslachtnaamVoornaamEnVoegToeAanActies(int dag, int index) {
        final ElementBuilder.PersoonParameters persPara = new ElementBuilder.PersoonParameters();
        persPara.geslachtsnaamcomponenten(Collections.singletonList(builder.maakGeslachtsnaamcomponentElement("com_geslachtsnaam" + index, null, null, "van", '-', "stam")));
        final PersoonGegevensElement persoon = builder.maakPersoonGegevensElement("nat_pers" + index, "1234", null, persPara);
        acties.add(builder.maakRegistratieGeslachtsnaamVoornaamActieElement("regGeslachtsnaam" + index, dag, Collections.emptyList(), persoon));
    }

    private void maakRegistratieNationaliteitEnVoegToeAanActiesErkenning(final int dag, final int index) {
        final ElementBuilder.PersoonParameters persPara = new ElementBuilder.PersoonParameters();
        persPara.nationaliteiten(Collections.singletonList(builder.maakNationaliteitElement("nat" + index, "0014", null)));
        final PersoonGegevensElement persoon = builder.maakPersoonGegevensElement("nat_pers" + index, "1234", null, persPara);
        acties.add(
                builder.maakRegistratieNationaliteitActieElement("reg" + index, dag, Collections.emptyList(), persoon));
    }


    private void maakRegistratieStaatLoosEnVoegToeAanActies(final int dag) {
        final ElementBuilder.PersoonParameters persPara = new ElementBuilder.PersoonParameters();
        persPara.indicaties(Collections.singletonList(builder.maakStaatloosIndicatieElement("ind_staat", true)));
        final PersoonGegevensElement persoon = builder.maakPersoonGegevensElement("nat_pers", "1234", null, persPara);
        acties.add(
                builder.maakRegistratieStaatloosActieElement("reg_st", dag, Collections.emptyList(), persoon));
    }


    private void maakBeeindigNationaliteitEnVoegToeAanActiesErkenning(final int dag, final int index, final boolean isErkenning) {
        final ElementBuilder.PersoonParameters persPara = new ElementBuilder.PersoonParameters();
        final String objectsleutel = isErkenning ? "0014" : null;
        final String referentie = !isErkenning ? "nat" + index : null;
        persPara.nationaliteiten(Collections.singletonList(builder.maakNationaliteitElementVerlies("beeindig" + index, objectsleutel, referentie, null)));
        final PersoonGegevensElement persoon = builder.maakPersoonGegevensElement("einde_nat_pers" + index, "1234", null, persPara);
        acties.add(builder.maakBeeindigNationaliteitActieElement("beein" + index, Collections.emptyList(), persoon, dag));
    }

    public void maakRegistratieOuderActieElementEnVoegToeAanActies(final int dag) {
        final List<BetrokkenheidElement> betrokkenheden = new LinkedList<>();
        final ElementBuilder.PersoonParameters persoonParameters = new ElementBuilder.PersoonParameters();
        final List<BetrokkenheidElement> betrokkenen = new LinkedList<>();
        final PersoonGegevensElement persoon = builder.maakPersoonGegevensElement("ouder_pers", "5555");
        persoon.setVerzoekBericht(bericht);
        final BetrokkenheidElement ouder_betr = builder.maakBetrokkenheidElement("ouder_betr", BetrokkenheidElementSoort.OUDER, persoon, null);
        ouder_betr.setVerzoekBericht(bericht);
        betrokkenen.add(ouder_betr);
        final FamilierechtelijkeBetrekkingElement familieRechtelijkeBetrekking = builder.maakFamilierechtelijkeBetrekkingElement("fam_betr", null, betrokkenen);
        betrokkenheden.add(builder.maakBetrokkenheidElement("kind_betr", "1111", null, BetrokkenheidElementSoort.KIND, familieRechtelijkeBetrekking));
        final PersoonRelatieElement persoonRelatieElement = builder.maakPersoonRelatieElement("pers_kind", null, "1234", betrokkenheden);
        acties.add(builder.maakRegistratieOuderActieElement("reg_ouder", dag, persoonRelatieElement));
        persoonRelatieElement.setVerzoekBericht(bericht);
    }

    public AdministratieveHandelingElement maakAdministratieveHandelingErkenning(final String comid) {
        final ElementBuilder.AdministratieveHandelingParameters ahPara = new ElementBuilder.AdministratieveHandelingParameters();
        ahPara.soort(AdministratieveHandelingElementSoort.ERKENNING);
        ahPara.acties(acties);
        ahPara.partijCode(Z_PARTIJ.getCode());
        final AdministratieveHandelingElement administratieveHandelingElement = builder.maakAdministratieveHandelingElement(comid, ahPara);
        administratieveHandelingElement.setVerzoekBericht(bericht);
        return administratieveHandelingElement;
    }

    public void setUpVerzoekBericht() {
        final Partij partij = new Partij("zpartij", PARTIJ_000001);
        partij.getRollen().add(Rol.BIJHOUDINGSORGAAN_COLLEGE);
        when(bericht.getZendendePartij()).thenReturn(partij);
        final ElementBuilder.StuurgegevensParameters stuurParameters = new ElementBuilder.StuurgegevensParameters();
        stuurParameters.zendendePartij(PARTIJ_000001);
        stuurParameters.zendendeSysteem("BRP");
        stuurParameters.referentienummer("123456789");
        stuurParameters.tijdstipVerzending("2016-03-21T08:32:03.234+01:00");
        final StuurgegevensElement stuurgegevens = builder.maakStuurgegevensElement("stuur", stuurParameters);
        when(bericht.getStuurgegevens()).thenReturn(stuurgegevens);
    }

    private BRPActie createBrpActie() {
        final AdministratieveHandeling
                administratieveHandeling =
                new AdministratieveHandeling(Z_PARTIJ, SoortAdministratieveHandeling.ERKENNING, DATUM_TIJD_REGISTRATIE);
        return
                new BRPActie(
                        SoortActie.REGISTRATIE_AANVANG_HUWELIJK,
                        administratieveHandeling,
                        administratieveHandeling.getPartij(),
                        administratieveHandeling.getDatumTijdRegistratie());
    }
}
