/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.bijhouding;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import nl.bzk.brp.bijhouding.business.excepties.BijhoudingExceptie;
import nl.bzk.brp.bijhouding.business.regels.actie.BijhoudingRegelService;
import nl.bzk.brp.bijhouding.business.stappen.AbstractStapTest;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.Resultaat;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.ResultaatMelding;
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.model.RegelParameters;
import nl.bzk.brp.model.SoortFout;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.MeldingtekstAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AangeverCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdellijkeTitelCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NationaliteitcodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PredicaatCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenEindeRelatieCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenVerkrijgingCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenVerliesCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenWijzigingVerblijfCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoorvoegselAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObject;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObjectKern;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdresAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebied;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortMigratie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortMigratieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestAangeverBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestRedenWijzigingVerblijfBuilder;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.basis.BerichtRootObject;
import nl.bzk.brp.model.bericht.ber.AdministratieveHandelingBronBericht;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieBronBericht;
import nl.bzk.brp.model.bericht.kern.ActieCorrectieAdresBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieAdresBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieEindeHuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieGeboorteBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieHuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieNationaliteitBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieOuderBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieOverlijdenBericht;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.DocumentBericht;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.GeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.HandelingAangaanGeregistreerdPartnerschapInNederlandBericht;
import nl.bzk.brp.model.bericht.kern.HandelingAdoptieIngezeteneBericht;
import nl.bzk.brp.model.bericht.kern.HandelingBeeindigingGeregistreerdPartnerschapInNederlandBericht;
import nl.bzk.brp.model.bericht.kern.HandelingErkenningNaGeboorteBericht;
import nl.bzk.brp.model.bericht.kern.HandelingOmzettingGeregistreerdPartnerschapInHuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.HandelingOntbindingHuwelijkInNederlandBericht;
import nl.bzk.brp.model.bericht.kern.HandelingOverlijdenInNederlandBericht;
import nl.bzk.brp.model.bericht.kern.HandelingVerhuizingBinnengemeentelijkBericht;
import nl.bzk.brp.model.bericht.kern.HandelingVerhuizingNaarBuitenlandBericht;
import nl.bzk.brp.model.bericht.kern.HandelingVestigingNietIngeschreveneBericht;
import nl.bzk.brp.model.bericht.kern.HandelingVoltrekkingHuwelijkInNederlandBericht;
import nl.bzk.brp.model.bericht.kern.KindBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.PartnerBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonAdresStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeboorteGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonMigratieGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNaamgebruikGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNationaliteitBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNationaliteitStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonOverlijdenGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonSamengesteldeNaamGroepBericht;
import nl.bzk.brp.model.bericht.kern.RelatieStandaardGroepBericht;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.model.bijhouding.CorrigeerAdresBericht;
import nl.bzk.brp.model.bijhouding.RegistreerAdoptieBericht;
import nl.bzk.brp.model.bijhouding.RegistreerGeboorteBericht;
import nl.bzk.brp.model.bijhouding.RegistreerHuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bijhouding.RegistreerOverlijdenBericht;
import nl.bzk.brp.model.bijhouding.RegistreerVerhuizingBericht;
import nl.bzk.brp.util.PersoonBuilder;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.webservice.business.service.ObjectSleutelService;
import nl.bzk.brp.webservice.business.service.OngeldigeObjectSleutelExceptie;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class BerichtVerrijkingsStapTest extends AbstractStapTest {

    private static final Short                   LANDGEBIED_6033_SHORT = 6033;
    private static final Short                   LANDGEBIED_1234_SHORT = 1234;
    private static final LandGebiedCodeAttribuut LANDGEBIED_6033       = new LandGebiedCodeAttribuut(LANDGEBIED_6033_SHORT);
    private static final LandGebiedCodeAttribuut LANDGEBIED_1234       = new LandGebiedCodeAttribuut(LANDGEBIED_1234_SHORT);

    private static final String                              GEM_34              = "0034";
    private static final String                              LAND_2              = "0002";
    private static final String                              PLAATS_UTRECHT      = "Utrecht";
    private static final String                              NIET_NUMERIEKE_CODE = "A";
    private static final RedenVerkrijgingCodeAttribuut       VERKRIJG_10         =
        new RedenVerkrijgingCodeAttribuut((short) 10);
    private static final RedenVerliesCodeAttribuut           VERLIES_07          =
        new RedenVerliesCodeAttribuut((short) 7);
    private static final RedenWijzigingVerblijfCodeAttribuut WIJZADRES_P         =
        new RedenWijzigingVerblijfCodeAttribuut("P");
    private static final RedenWijzigingVerblijfCodeAttribuut ONJUISTE_WIJZADRES  =
        new RedenWijzigingVerblijfCodeAttribuut("x");

    private static final AangeverCodeAttribuut AANGEVER_VERZORGER =
        new AangeverCodeAttribuut("V");
    private static final AangeverCodeAttribuut ONJUISTE_AANGEVER  =
        new AangeverCodeAttribuut("X");

    @InjectMocks
    private final BerichtVerrijkingsStap berichtVerrijkingsStap = new BerichtVerrijkingsStap();

    @Mock
    private ReferentieDataRepository referentieDataRepository;
    @Mock
    private BijhoudingRegelService   bijhoudingRegelService;
    @Mock
    private ObjectSleutelService     objectSleutelService;
    @Mock
    private MeldingFactory           meldingFactory;

    @Before
    public void init() throws OngeldigeObjectSleutelExceptie {

        when(referentieDataRepository.vindLandOpCode(LandGebiedCodeAttribuut.NEDERLAND))
            .thenReturn(new LandGebied(LandGebiedCodeAttribuut.NEDERLAND, null, null, null, null));
        when(referentieDataRepository.vindLandOpCode(LANDGEBIED_6033))
            .thenReturn(new LandGebied(LANDGEBIED_6033, new NaamEnumeratiewaardeAttribuut("Bahama-eilanden"), null, null, null));
        when(referentieDataRepository.vindLandOpCode(LANDGEBIED_1234))
            .thenThrow(new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.LANDGEBIEDCODE,
                "foo", new BijhoudingExceptie()));

        when(referentieDataRepository.bestaatVoorvoegselScheidingsteken(
            anyString(), anyString())).thenReturn(false);
        when(referentieDataRepository.bestaatVoorvoegselScheidingsteken(
            "van de", " ")).thenReturn(true);
        when(referentieDataRepository.isBestaandeWoonplaats(new NaamEnumeratiewaardeAttribuut(PLAATS_UTRECHT))).thenReturn(true);

        when(referentieDataRepository.vindAangeverAdreshoudingOpCode(AANGEVER_VERZORGER))
            .thenReturn(
                TestAangeverBuilder.maker().metCode(AANGEVER_VERZORGER).metNaam(new NaamEnumeratiewaardeAttribuut(null)).maak());
        when(referentieDataRepository.vindAangeverAdreshoudingOpCode(ONJUISTE_AANGEVER))
            .thenThrow(new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.AANGEVERADRESHOUDING,
                "foo", new BijhoudingExceptie()));

        when(referentieDataRepository.vindRedenWijzingVerblijfOpCode(WIJZADRES_P))
            .thenReturn(
                TestRedenWijzigingVerblijfBuilder.maker().metCode(WIJZADRES_P).metNaam(new NaamEnumeratiewaardeAttribuut(null)).maak());
        when(referentieDataRepository.vindRedenWijzingVerblijfOpCode(ONJUISTE_WIJZADRES))
            .thenThrow(new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.REDENWIJZINGADRES,
                "foo", new BijhoudingExceptie()));

        when(objectSleutelService.bepaalPersoonId(anyString(), anyInt())).thenReturn(1);
    }

    @Test
    public void testVerhuizing() {
        final RegistreerVerhuizingBericht verhuisBericht = new RegistreerVerhuizingBericht();
        voegStuurgegevenToeAanBericht(verhuisBericht);

        final ActieBronBericht bron = new ActieBronBericht();
        bron.setReferentieID("com.id.bron1");
        bron.setDocument(new DocumentBericht());

        final ActieBericht verhuisActie = new ActieRegistratieAdresBericht();
        verhuisActie.setBronnen(new ArrayList<ActieBronBericht>());
        verhuisActie.getBronnen().add(bron);

        final PersoonBericht pers =
            PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 123456789, Geslachtsaanduiding.MAN, 19830404,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA.getWaarde(),
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                "Piet", "van de", "Veldhuijsen");
        pers.setAdressen(new ArrayList<PersoonAdresBericht>());
        verhuisActie.setRootObject(pers);
        verhuisBericht.getStandaard().setAdministratieveHandeling(new HandelingVerhuizingBinnengemeentelijkBericht());
        verhuisBericht.getAdministratieveHandeling().setPartij(new PartijAttribuut(TestPartijBuilder.maker().maak()));
        verhuisBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        verhuisBericht.getAdministratieveHandeling().getActies().add(verhuisActie);

        final PersoonAdresBericht adres = new PersoonAdresBericht();
        final PersoonAdresStandaardGroepBericht standaard = new PersoonAdresStandaardGroepBericht();
        adres.setStandaard(standaard);
        pers.getAdressen().add(adres);

        standaard.setGemeenteCode(GEM_34);
        standaard.setRedenWijzigingCode(WIJZADRES_P.getWaarde());
        standaard.setAangeverAdreshoudingCode(AANGEVER_VERZORGER.getWaarde());
        standaard.setWoonplaatsnaam(new NaamEnumeratiewaardeAttribuut(PLAATS_UTRECHT));

        berichtVerrijkingsStap.voerStapUit(verhuisBericht, bouwBerichtContext(""));
        verify(referentieDataRepository, times(1)).vindGemeenteOpCode(any(GemeenteCodeAttribuut.class));
        verify(referentieDataRepository, times(2)).vindPartijOpCode(any(PartijCodeAttribuut.class));
        verify(referentieDataRepository, times(1)).isBestaandeWoonplaats(any(NaamEnumeratiewaardeAttribuut.class));
        verify(referentieDataRepository, times(1)).vindLandOpCode(any(LandGebiedCodeAttribuut.class));
        verify(referentieDataRepository, times(1)).vindRedenWijzingVerblijfOpCode(any(RedenWijzigingVerblijfCodeAttribuut.class));
        verify(referentieDataRepository, times(1)).vindAangeverAdreshoudingOpCode(any(AangeverCodeAttribuut.class));
        verify(referentieDataRepository, times(1)).vindSoortDocumentOpNaam(any(NaamEnumeratiewaardeAttribuut.class));

        final ActieBronBericht verrijktBronBericht = verhuisActie.getBronnen().get(0);
        assertNotNull(verrijktBronBericht);
        final DocumentBericht verrijktBronBerichtDocument = verrijktBronBericht.getDocument();
        assertNotNull(verrijktBronBerichtDocument);
        assertEquals("soortnaam is overgenomen uit de context", verrijktBronBerichtDocument.getSoortNaam(), "abc");
        assertNotNull(verhuisActie.getPartij());
        assertNotNull(verhuisActie.getTijdstipRegistratie());
        assertEquals(standaard.getLandGebiedCode(), LandGebiedCodeAttribuut.NL_LAND_CODE_STRING);
    }

    @Test
    public void testVerhuizingMetBronCodeMetGevuldeObjectSleutel() {
        final RegistreerVerhuizingBericht verhuisBericht = new RegistreerVerhuizingBericht();
        voegStuurgegevenToeAanBericht(verhuisBericht);

        final ActieBronBericht bron = new ActieBronBericht();
        final String bronSleutelReferentieId = "com.id.bron1MetObjectSleutel";
        bron.setReferentieID(bronSleutelReferentieId);

        final ActieBericht verhuisActie = new ActieRegistratieAdresBericht();
        verhuisActie.setBronnen(new ArrayList<ActieBronBericht>());
        verhuisActie.getBronnen().add(bron);

        final PersoonBericht pers =
            PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 123456789, Geslachtsaanduiding.MAN, 19830404,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA.getWaarde(),
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                "Piet", "van de", "Veldhuijsen");
        pers.setAdressen(new ArrayList<PersoonAdresBericht>());
        verhuisActie.setRootObject(pers);
        verhuisBericht.getStandaard().setAdministratieveHandeling(new HandelingVerhuizingBinnengemeentelijkBericht());
        verhuisBericht.getAdministratieveHandeling().setPartij(new PartijAttribuut(TestPartijBuilder.maker().maak()));
        verhuisBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        verhuisBericht.getAdministratieveHandeling().getActies().add(verhuisActie);

        final PersoonAdresBericht adres = new PersoonAdresBericht();
        final PersoonAdresStandaardGroepBericht standaard = new PersoonAdresStandaardGroepBericht();
        adres.setStandaard(standaard);
        pers.getAdressen().add(adres);

        standaard.setGemeenteCode(GEM_34);
        standaard.setRedenWijzigingCode(WIJZADRES_P.getWaarde());
        standaard.setAangeverAdreshoudingCode(AANGEVER_VERZORGER.getWaarde());
        standaard.setWoonplaatsnaam(new NaamEnumeratiewaardeAttribuut(PLAATS_UTRECHT));

        final BijhoudingBerichtContext context = bouwBerichtContext("");

        // voeg extra bron document toe
        final AdministratieveHandelingBronBericht extraBronBericht = new AdministratieveHandelingBronBericht();
        final DocumentBericht doc = new DocumentBericht();
        doc.setObjectSleutel(bronSleutelReferentieId);
        extraBronBericht.setDocument(doc);

        final ArrayList<BerichtIdentificeerbaar> extraBronnen = new ArrayList<>();
        extraBronnen.add(extraBronBericht);
        context.getIdentificeerbareObjecten().put(bronSleutelReferentieId, extraBronnen);

        berichtVerrijkingsStap.voerStapUit(verhuisBericht, context);
        verify(referentieDataRepository, times(1)).vindGemeenteOpCode(any(GemeenteCodeAttribuut.class));
        verify(referentieDataRepository, times(1)).isBestaandeWoonplaats(any(NaamEnumeratiewaardeAttribuut.class));
        verify(referentieDataRepository, times(1)).vindLandOpCode(any(LandGebiedCodeAttribuut.class));
        verify(referentieDataRepository, times(1)).vindRedenWijzingVerblijfOpCode(any(RedenWijzigingVerblijfCodeAttribuut.class));
        verify(referentieDataRepository, times(1)).vindAangeverAdreshoudingOpCode(any(AangeverCodeAttribuut.class));
        verify(referentieDataRepository, times(1)).vindPartijOpCode(any(PartijCodeAttribuut.class));

        // opzoeken niet nodig aangezien de object sleutel al gevuld is.
        verify(referentieDataRepository, times(0)).vindSoortDocumentOpNaam(any(NaamEnumeratiewaardeAttribuut.class));
        assertNotNull(verhuisActie.getPartij());
        assertNotNull(verhuisActie.getTijdstipRegistratie());
        assertEquals(standaard.getLandGebiedCode(), LandGebiedCodeAttribuut.NL_LAND_CODE_STRING);
    }

    @Test
    public void testVerhuizingZonderBronDocumenten() {
        final RegistreerVerhuizingBericht verhuisBericht = new RegistreerVerhuizingBericht();
        voegStuurgegevenToeAanBericht(verhuisBericht);

        final ActieBronBericht bron = new ActieBronBericht();
        bron.setReferentieID("com.id.bron1");

        final ActieBericht verhuisActie = new ActieRegistratieAdresBericht();
        verhuisActie.setBronnen(new ArrayList<ActieBronBericht>());
        verhuisActie.getBronnen().add(bron);

        verhuisActie.setPartij(new PartijAttribuut(TestPartijBuilder.maker().maak()));

        final PersoonBericht pers =
            PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 123456789, Geslachtsaanduiding.MAN, 19830404,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA.getWaarde(),
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                "Piet", "van de", "Veldhuijsen");
        pers.setAdressen(new ArrayList<PersoonAdresBericht>());
        verhuisActie.setRootObject(pers);
        verhuisBericht.getStandaard().setAdministratieveHandeling(new HandelingVerhuizingBinnengemeentelijkBericht());
        verhuisBericht.getAdministratieveHandeling().setPartij(new PartijAttribuut(TestPartijBuilder.maker().maak()));
        verhuisBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        verhuisBericht.getAdministratieveHandeling().getActies().add(verhuisActie);

        final PersoonAdresBericht adres = new PersoonAdresBericht();
        final PersoonAdresStandaardGroepBericht standaard = new PersoonAdresStandaardGroepBericht();
        adres.setStandaard(standaard);
        pers.getAdressen().add(adres);

        standaard.setGemeenteCode(GEM_34);
        standaard.setRedenWijzigingCode(WIJZADRES_P.getWaarde());
        standaard.setAangeverAdreshoudingCode(AANGEVER_VERZORGER.getWaarde());
        standaard.setWoonplaatsnaam(new NaamEnumeratiewaardeAttribuut(PLAATS_UTRECHT));

        berichtVerrijkingsStap.voerStapUit(verhuisBericht, bouwBerichtContext(""));
    }

    @Test
    public void testVerhuizingZonderBronDocumentSoortNaam() {
        final RegistreerVerhuizingBericht verhuisBericht = new RegistreerVerhuizingBericht();
        voegStuurgegevenToeAanBericht(verhuisBericht);

        final ActieBronBericht bron = new ActieBronBericht();
        bron.setReferentieID("com.id.bron1");
        bron.setDocument(new DocumentBericht());

        final ActieBericht verhuisActie = new ActieRegistratieAdresBericht();
        verhuisActie.setBronnen(new ArrayList<ActieBronBericht>());
        verhuisActie.getBronnen().add(bron);

        verhuisActie.setPartij(new PartijAttribuut(TestPartijBuilder.maker().maak()));

        final PersoonBericht pers =
            PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 123456789, Geslachtsaanduiding.MAN, 19830404,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA.getWaarde(),
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                "Piet", "van de", "Veldhuijsen");
        pers.setAdressen(new ArrayList<PersoonAdresBericht>());
        verhuisActie.setRootObject(pers);
        verhuisBericht.getStandaard().setAdministratieveHandeling(new HandelingVerhuizingBinnengemeentelijkBericht());
        verhuisBericht.getAdministratieveHandeling().setPartij(new PartijAttribuut(
            TestPartijBuilder.maker().maak()));
        verhuisBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        verhuisBericht.getAdministratieveHandeling().getActies().add(verhuisActie);

        final PersoonAdresBericht adres = new PersoonAdresBericht();
        final PersoonAdresStandaardGroepBericht standaard = new PersoonAdresStandaardGroepBericht();
        adres.setStandaard(standaard);
        pers.getAdressen().add(adres);

        standaard.setGemeenteCode(GEM_34);
        standaard.setRedenWijzigingCode(WIJZADRES_P.getWaarde());
        standaard.setAangeverAdreshoudingCode(AANGEVER_VERZORGER.getWaarde());
        standaard.setWoonplaatsnaam(new NaamEnumeratiewaardeAttribuut(PLAATS_UTRECHT));

        berichtVerrijkingsStap.voerStapUit(verhuisBericht, bouwBerichtContextMetSpecifiekeWaarden(null, true, "0034", ""));
    }

    @Test
    public void testVerhuizingZonderBronDocumentStandaardGroep() {
        final RegistreerVerhuizingBericht verhuisBericht = new RegistreerVerhuizingBericht();
        voegStuurgegevenToeAanBericht(verhuisBericht);

        final ActieBronBericht bron = new ActieBronBericht();
        bron.setReferentieID("com.id.bron1");
        bron.setDocument(new DocumentBericht());

        final ActieBericht verhuisActie = new ActieRegistratieAdresBericht();
        verhuisActie.setBronnen(new ArrayList<ActieBronBericht>());
        verhuisActie.getBronnen().add(bron);

        verhuisActie.setPartij(new PartijAttribuut(TestPartijBuilder.maker().maak()));

        final PersoonBericht pers =
            PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 123456789, Geslachtsaanduiding.MAN, 19830404,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA.getWaarde(),
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                "Piet", "van de", "Veldhuijsen");
        pers.setAdressen(new ArrayList<PersoonAdresBericht>());
        verhuisActie.setRootObject(pers);
        verhuisBericht.getStandaard().setAdministratieveHandeling(new HandelingVerhuizingBinnengemeentelijkBericht());
        verhuisBericht.getAdministratieveHandeling().setPartij(new PartijAttribuut(TestPartijBuilder.maker().maak()));
        verhuisBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        verhuisBericht.getAdministratieveHandeling().getActies().add(verhuisActie);

        final PersoonAdresBericht adres = new PersoonAdresBericht();
        final PersoonAdresStandaardGroepBericht standaard = new PersoonAdresStandaardGroepBericht();
        adres.setStandaard(standaard);
        pers.getAdressen().add(adres);

        standaard.setGemeenteCode(GEM_34);
        standaard.setRedenWijzigingCode(WIJZADRES_P.getWaarde());
        standaard.setAangeverAdreshoudingCode(AANGEVER_VERZORGER.getWaarde());
        standaard.setWoonplaatsnaam(new NaamEnumeratiewaardeAttribuut(PLAATS_UTRECHT));

        berichtVerrijkingsStap.voerStapUit(verhuisBericht,
            bouwBerichtContextMetSpecifiekeWaarden("abc",
                false,
                "0034",
                "")
        );
    }

    @Test
    public void testVerhuizingZonderBronDocumentPartijCode() {
        final RegistreerVerhuizingBericht verhuisBericht = new RegistreerVerhuizingBericht();
        voegStuurgegevenToeAanBericht(verhuisBericht);

        final ActieBronBericht bron = new ActieBronBericht();
        bron.setReferentieID("com.id.bron1");
        bron.setDocument(new DocumentBericht());

        final ActieBericht verhuisActie = new ActieRegistratieAdresBericht();
        verhuisActie.setBronnen(new ArrayList<ActieBronBericht>());
        verhuisActie.getBronnen().add(bron);

        verhuisActie.setPartij(new PartijAttribuut(TestPartijBuilder.maker().maak()));

        final PersoonBericht pers =
            PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 123456789, Geslachtsaanduiding.MAN, 19830404,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA.getWaarde(),
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                "Piet", "van de", "Veldhuijsen");
        pers.setAdressen(new ArrayList<PersoonAdresBericht>());
        verhuisActie.setRootObject(pers);
        verhuisBericht.getStandaard().setAdministratieveHandeling(new HandelingVerhuizingBinnengemeentelijkBericht());
        verhuisBericht.getAdministratieveHandeling().setPartij(new PartijAttribuut(TestPartijBuilder.maker().maak()));
        verhuisBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        verhuisBericht.getAdministratieveHandeling().getActies().add(verhuisActie);

        final PersoonAdresBericht adres = new PersoonAdresBericht();
        final PersoonAdresStandaardGroepBericht standaard = new PersoonAdresStandaardGroepBericht();
        adres.setStandaard(standaard);
        pers.getAdressen().add(adres);

        standaard.setGemeenteCode(GEM_34);
        standaard.setRedenWijzigingCode(WIJZADRES_P.getWaarde());
        standaard.setAangeverAdreshoudingCode(AANGEVER_VERZORGER.getWaarde());
        standaard.setWoonplaatsnaam(new NaamEnumeratiewaardeAttribuut(PLAATS_UTRECHT));

        berichtVerrijkingsStap.voerStapUit(verhuisBericht,
            bouwBerichtContextMetSpecifiekeWaarden("abc",
                true,
                null,
                "")
        );
    }

    @Test
    public void testVerhuizingGeenVerrijkingNodig() {
        final RegistreerVerhuizingBericht verhuisBericht = new RegistreerVerhuizingBericht();
        voegStuurgegevenToeAanBericht(verhuisBericht);

        final ActieBericht verhuisActie = new ActieRegistratieAdresBericht();
        final PersoonBericht pers =
            PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 123456789, Geslachtsaanduiding.MAN, 19830404,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA.getWaarde(),
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                "Piet", "van de", "Veldhuijsen");
        pers.setAdressen(new ArrayList<PersoonAdresBericht>());
        verhuisActie.setRootObject(pers);
        verhuisBericht.getStandaard().setAdministratieveHandeling(new HandelingVerhuizingBinnengemeentelijkBericht());
        verhuisBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        verhuisBericht.getAdministratieveHandeling().getActies().add(verhuisActie);

        final PersoonAdresBericht adres = new PersoonAdresBericht();
        final PersoonAdresStandaardGroepBericht standaard = new PersoonAdresStandaardGroepBericht();

        adres.setStandaard(standaard);
        pers.getAdressen().add(adres);

        berichtVerrijkingsStap.voerStapUit(verhuisBericht, bouwBerichtContext(""));
        verify(referentieDataRepository, times(0)).vindGemeenteOpCode(
            any(GemeenteCodeAttribuut.class));
        verify(referentieDataRepository, times(0)).isBestaandeWoonplaats(any(NaamEnumeratiewaardeAttribuut.class));
        verify(referentieDataRepository, times(0)).vindRedenWijzingVerblijfOpCode(
            any(RedenWijzigingVerblijfCodeAttribuut.class));
    }

    @Test
    public void testVerhuizingOnbekendeReferentie() {
        // verifieer dat alle verkeerde codes tot een fourmelding komt.
        final RegistreerVerhuizingBericht verhuisBericht = new RegistreerVerhuizingBericht();
        voegStuurgegevenToeAanBericht(verhuisBericht);

        final ActieBericht verhuisActie = new ActieRegistratieAdresBericht();
        final PersoonBericht pers =
            PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 123456789, Geslachtsaanduiding.MAN, 19830404,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA.getWaarde(),
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                "Piet", "van de", "Veldhuijsen");
        pers.setAdressen(new ArrayList<PersoonAdresBericht>());
        verhuisActie.setRootObject(pers);
        verhuisBericht.getStandaard().setAdministratieveHandeling(new HandelingErkenningNaGeboorteBericht());
        verhuisBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        verhuisBericht.getAdministratieveHandeling().getActies().add(verhuisActie);

        final PersoonAdresBericht adres = new PersoonAdresBericht();
        final PersoonAdresStandaardGroepBericht standaard = new PersoonAdresStandaardGroepBericht();
        standaard.setGemeenteCode("67");
        standaard.setLandGebiedCode("099");
        standaard.setRedenWijzigingCode("w");
        standaard.setAangeverAdreshoudingCode("x");
        standaard.setWoonplaatsnaam(new NaamEnumeratiewaardeAttribuut("888"));
        adres.setStandaard(standaard);
        pers.getAdressen().add(adres);

        when(referentieDataRepository.isBestaandeWoonplaats(any(NaamEnumeratiewaardeAttribuut.class))).thenReturn(false);

        when(referentieDataRepository.vindGemeenteOpCode(any(GemeenteCodeAttribuut.class))).thenThrow(
            new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.GEMEENTECODE, "foo",
                new BijhoudingExceptie())
        );

        when(referentieDataRepository.vindLandOpCode(any(LandGebiedCodeAttribuut.class))).thenThrow(
            new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.LANDGEBIEDCODE, "foo",
                new BijhoudingExceptie())
        );

        when(referentieDataRepository.vindPredicaatOpCode(any(PredicaatCodeAttribuut.class))).thenThrow(
            new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.PREDICAAT, "foo",
                new BijhoudingExceptie())
        );

        when(referentieDataRepository.vindAangeverAdreshoudingOpCode(
            any(AangeverCodeAttribuut.class)))
            .thenThrow(
                new OnbekendeReferentieExceptie(
                    OnbekendeReferentieExceptie.ReferentieVeld.AANGEVERADRESHOUDING, "foo",
                    new BijhoudingExceptie())
            );

        when(referentieDataRepository.vindRedenWijzingVerblijfOpCode(
            any(RedenWijzigingVerblijfCodeAttribuut.class)))
            .thenThrow(
                new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.REDENWIJZINGADRES,
                    "foo",
                    new BijhoudingExceptie())
            );

        when(referentieDataRepository.vindAdellijkeTitelOpCode(
            any(AdellijkeTitelCodeAttribuut.class)))
            .thenThrow(
                new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.ADELLIJKETITEL,
                    "foo", new BijhoudingExceptie())
            );

        // voer de opdracht en verwacht dat we nu fouten krijgen.
        final Resultaat resultaat = berichtVerrijkingsStap.voerStapUit(verhuisBericht, bouwBerichtContext(""));
        final Set<ResultaatMelding> meldingen = resultaat.getMeldingen();
        assertEquals(5, meldingen.size());

        List<Regel> regels = haalRegelsGesorteerdUitMeldingen(resultaat.getMeldingen());
        List<Regel> expected = Arrays
            .asList(Regel.BRAL1001, Regel.BRAL1002, Regel.BRAL1007, Regel.BRAL1008, Regel.BRAL1020);
        assertEquals(expected, regels);
    }

    @Test
    public void testOnbekendePartijEnOnbekendSoortDocument() {
        final RegistreerVerhuizingBericht verhuisBericht = new RegistreerVerhuizingBericht();
        voegStuurgegevenToeAanBericht(verhuisBericht);

        final ActieBericht verhuisActie = new ActieRegistratieAdresBericht();
        final PersoonBericht pers =
            PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 123456789, Geslachtsaanduiding.MAN, 19830404,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA.getWaarde(), StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                "Piet", "van de", "Veldhuijsen");

        pers.setAdressen(new ArrayList<PersoonAdresBericht>());

        final ActieBronBericht bron = new ActieBronBericht();
        bron.setReferentieID("com.id.bron1");
        bron.setDocument(new DocumentBericht());
        verhuisActie.setBronnen(new ArrayList<ActieBronBericht>());
        verhuisActie.getBronnen().add(bron);

        verhuisActie.setRootObject(pers);
        verhuisBericht.getStandaard().setAdministratieveHandeling(new HandelingVerhuizingBinnengemeentelijkBericht());
        verhuisBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        verhuisBericht.getAdministratieveHandeling().getActies().add(verhuisActie);
        verhuisBericht.getAdministratieveHandeling().setPartijCode("000000");

        final PersoonAdresBericht adres = new PersoonAdresBericht();
        final PersoonAdresStandaardGroepBericht standaard = new PersoonAdresStandaardGroepBericht();
        standaard.setGemeenteCode("67");
        standaard.setLandGebiedCode("099");
        standaard.setRedenWijzigingCode("w");
        standaard.setAangeverAdreshoudingCode("x");
        standaard.setWoonplaatsnaam(new NaamEnumeratiewaardeAttribuut("888"));
        adres.setStandaard(standaard);
        pers.getAdressen().add(adres);

        when(referentieDataRepository.vindPartijOpCode(new PartijCodeAttribuut(0)))
            .thenThrow(
                new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.PARTIJCODE, "foo",
                    new BijhoudingExceptie())
            );

        when(referentieDataRepository.vindSoortDocumentOpNaam(any(NaamEnumeratiewaardeAttribuut.class)))
            .thenThrow(
                new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.SOORTDOCUMENT,
                    "foo", new BijhoudingExceptie())
            );

        when(bijhoudingRegelService.getRegelParametersVoorRegel(Mockito.any(Regel.class))).thenReturn(
            new RegelParameters(
                new MeldingtekstAttribuut(Regel.BRAL1002.getOmschrijving()),
                SoortMelding.FOUT, Regel.BRAL1002,
                DatabaseObjectKern.PERSOON, SoortFout.VERWERKING_KAN_DOORGAAN)
        );

        when(meldingFactory.maakResultaatMelding(any(Regel.class), any(BerichtIdentificeerbaar.class), any(DatabaseObject.class))).thenReturn
            (ResultaatMelding.builder().build());

        // voer de opdracht en verwacht dat we nu fouten krijgen.
        final Resultaat resultaat = berichtVerrijkingsStap.voerStapUit(verhuisBericht, bouwBerichtContext(""));
        assertFalse(resultaat.getMeldingen().isEmpty());
        verifieerWillekeurigMeldingToegevoegd(1);
        verifieerSpecifiekeMeldingToegevoegd(Regel.BRAL1002A);
    }

    @Test
    public void testCorrectieAdresNL() {
        final CorrigeerAdresBericht corrigeerAdresBericht = new CorrigeerAdresBericht();
        voegStuurgegevenToeAanBericht(corrigeerAdresBericht);

        final ActieBericht correactieAdresActie = new ActieCorrectieAdresBericht();
        final PersoonBericht pers =
            PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 123456789, Geslachtsaanduiding.MAN, 19830404,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA.getWaarde(),
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                "Piet", "van de", "Veldhuijsen");
        pers.setAdressen(new ArrayList<PersoonAdresBericht>());
        correactieAdresActie.setRootObject(pers);
        corrigeerAdresBericht.getStandaard().setAdministratieveHandeling(new HandelingErkenningNaGeboorteBericht());
        corrigeerAdresBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        corrigeerAdresBericht.getAdministratieveHandeling().getActies().add(correactieAdresActie);

        final PersoonAdresBericht adres = new PersoonAdresBericht();
        final PersoonAdresStandaardGroepBericht standaard = new PersoonAdresStandaardGroepBericht();
        adres.setStandaard(standaard);
        pers.getAdressen().add(adres);

        standaard.setGemeenteCode(GEM_34);
        standaard.setLandGebiedCode(LAND_2);
        standaard.setRedenWijzigingCode(WIJZADRES_P.getWaarde());
        standaard.setAangeverAdreshoudingCode(AANGEVER_VERZORGER.getWaarde());
        standaard.setWoonplaatsnaam(new NaamEnumeratiewaardeAttribuut(PLAATS_UTRECHT));
        standaard.setSoort(new FunctieAdresAttribuut(FunctieAdres.WOONADRES));

        berichtVerrijkingsStap.voerStapUit(corrigeerAdresBericht, bouwBerichtContext(""));
        verify(referentieDataRepository, times(1)).vindGemeenteOpCode(any(GemeenteCodeAttribuut.class));
        verify(referentieDataRepository, times(1)).isBestaandeWoonplaats(any(NaamEnumeratiewaardeAttribuut.class));
        verify(referentieDataRepository, times(1)).vindLandOpCode(any(LandGebiedCodeAttribuut.class));
        verify(referentieDataRepository, times(1)).vindRedenWijzingVerblijfOpCode(
            any(RedenWijzigingVerblijfCodeAttribuut.class));
        verify(referentieDataRepository, times(1)).vindAangeverAdreshoudingOpCode(
            any(AangeverCodeAttribuut.class));
    }

    @Test
    public void testCorrectieAdresNLMetFoutieveCodes() {
        final CorrigeerAdresBericht corrigeerAdresBericht = new CorrigeerAdresBericht();
        voegStuurgegevenToeAanBericht(corrigeerAdresBericht);

        final ActieBericht correactieAdresActie = new ActieCorrectieAdresBericht();
        final PersoonBericht pers =
            PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 123456789, Geslachtsaanduiding.MAN, 19830404,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA.getWaarde(),
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                "Piet", "van de", "Veldhuijsen");
        // strip de bsn nummer, maak een technische sleutel.
        pers.setIdentificatienummers(null);
        pers.setAdressen(new ArrayList<PersoonAdresBericht>());
        correactieAdresActie.setRootObject(pers);
        corrigeerAdresBericht.getStandaard().setAdministratieveHandeling(new HandelingErkenningNaGeboorteBericht());
        corrigeerAdresBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        corrigeerAdresBericht.getAdministratieveHandeling().getActies().add(correactieAdresActie);

        final PersoonAdresBericht adres = new PersoonAdresBericht();
        final PersoonAdresStandaardGroepBericht standaard = new PersoonAdresStandaardGroepBericht();
        adres.setStandaard(standaard);
        pers.getAdressen().add(adres);

        standaard.setIndicatiePersoonAangetroffenOpAdres(null);
        standaard.setGemeenteCode(NIET_NUMERIEKE_CODE);
        standaard.setLandGebiedCode(NIET_NUMERIEKE_CODE);
        standaard.setRedenWijzigingCode(WIJZADRES_P.getWaarde());
        standaard.setAangeverAdreshoudingCode(AANGEVER_VERZORGER.getWaarde());
        standaard.setWoonplaatsnaam(new NaamEnumeratiewaardeAttribuut(NIET_NUMERIEKE_CODE));
        standaard.setSoort(new FunctieAdresAttribuut(FunctieAdres.WOONADRES));

        final Resultaat resultaat = berichtVerrijkingsStap.voerStapUit(corrigeerAdresBericht, bouwBerichtContext(""));
        // wordt niet meer aangevuld.
        // todo: weer aanzetten zodra de code (verrijking identificatienrs) is weer uitgezet
//        Assert.assertEquals(null, pers.getIdentificatienummers());

        verify(referentieDataRepository, Mockito.never()).vindGemeenteOpCode(any(GemeenteCodeAttribuut.class));
        //Let op, woonplaats wordt nu verijkt op basis van een string en niet van een numerieke code, dus wordt altijd
        //aangeroepen:
        verify(referentieDataRepository, times(1)).isBestaandeWoonplaats(any(NaamEnumeratiewaardeAttribuut.class));
        verify(referentieDataRepository, Mockito.never()).vindLandOpCode(any(LandGebiedCodeAttribuut.class));

        assertFalse(resultaat.getMeldingen().isEmpty());
        assertEquals(3, resultaat.getMeldingen().size());
        assertEquals(SoortMelding.FOUT, resultaat.getMeldingen().iterator().next().getSoort());
        assertEquals(Regel.ALG0001, resultaat.getMeldingen().iterator().next().getRegel());

        Assert.assertNull(standaard.getIndicatiePersoonAangetroffenOpAdres());

    }

    @Test
    public void testCorrectieAdresNLGeenVerrijkingNodig() {
        final CorrigeerAdresBericht corrigeerAdresBericht = new CorrigeerAdresBericht();
        voegStuurgegevenToeAanBericht(corrigeerAdresBericht);

        final ActieBericht correactieAdresActie = new ActieCorrectieAdresBericht();
        final PersoonBericht pers =
            PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 123456789, Geslachtsaanduiding.MAN, 19830404,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA.getWaarde(),
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                "Piet", "van de", "Veldhuijsen");
        pers.setAdressen(new ArrayList<PersoonAdresBericht>());
        correactieAdresActie.setRootObject(pers);
        corrigeerAdresBericht.getStandaard().setAdministratieveHandeling(new HandelingErkenningNaGeboorteBericht());
        corrigeerAdresBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        corrigeerAdresBericht.getAdministratieveHandeling().getActies().add(correactieAdresActie);
//        CorrigeerAdresBericht.getAdministratieveHandeling().setPartijCode(GEM_34);

        final PersoonAdresBericht adres = new PersoonAdresBericht();
        final PersoonAdresStandaardGroepBericht standaard = new PersoonAdresStandaardGroepBericht();
        adres.setStandaard(standaard);
        adres.getStandaard().setSoort(new FunctieAdresAttribuut(FunctieAdres.BRIEFADRES));
        pers.getAdressen().add(adres);

        berichtVerrijkingsStap.voerStapUit(corrigeerAdresBericht, bouwBerichtContext(""));
        verify(referentieDataRepository, times(0)).vindGemeenteOpCode(any(GemeenteCodeAttribuut.class));
        verify(referentieDataRepository, times(0)).isBestaandeWoonplaats(any(NaamEnumeratiewaardeAttribuut.class));
        verify(referentieDataRepository, times(0)).vindLandOpCode(LandGebiedCodeAttribuut.NEDERLAND);
        verify(referentieDataRepository, times(0)).vindRedenWijzingVerblijfOpCode(
            any(RedenWijzigingVerblijfCodeAttribuut.class));
    }

    @Test
    public void testEersteInschrijving() {
        final RegistreerGeboorteBericht bericht = new RegistreerGeboorteBericht();
        voegStuurgegevenToeAanBericht(bericht);

        final ActieBericht actie = new ActieRegistratieGeboorteBericht();
        final FamilierechtelijkeBetrekkingBericht relatie = new FamilierechtelijkeBetrekkingBericht();
        actie.setRootObject(relatie);
        bericht.getStandaard().setAdministratieveHandeling(new HandelingErkenningNaGeboorteBericht());
        bericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        bericht.getAdministratieveHandeling().getActies().add(actie);
        bericht.getAdministratieveHandeling().setPartijCode(GEM_34);

        final PersoonBericht kind =
            PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 123456789, Geslachtsaanduiding.MAN, 19830404,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA.getWaarde(),
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                "Piet", "van de", "Veldhuijsen");
        final PersoonGeboorteGroepBericht geboorte = new PersoonGeboorteGroepBericht();

        geboorte.setDatumGeboorte(new DatumEvtDeelsOnbekendAttribuut(19830404));
        geboorte.setGemeenteGeboorteCode(GEM_34);
        geboorte.setWoonplaatsnaamGeboorte(new NaamEnumeratiewaardeAttribuut(PLAATS_UTRECHT));
        geboorte.setLandGebiedGeboorteCode(LAND_2);

        kind.setGeboorte(geboorte);

        kind.setNaamgebruik(PersoonBuilder.bouwPersoonNaamgebruikGroepbericht(
            null, null,
            "voornamen", "van de", " ", "Kamp",
            null, null, null));
        kind.getNaamgebruik().setAdellijkeTitelNaamgebruikCode("J");
        kind.getNaamgebruik().setPredicaatNaamgebruikCode("K");

        relatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        final KindBericht betr = new KindBericht();
        betr.setPersoon(kind);
        relatie.getBetrokkenheden().add(betr);

        final BetrokkenheidBericht betrokkenheidOuder = new OuderBericht();
        betrokkenheidOuder.setPersoon(new PersoonBericht());
        relatie.getBetrokkenheden().add(betrokkenheidOuder);

        ReflectionTestUtils.setField(kind.getGeslachtsnaamcomponenten().get(0).getStandaard(), "predicaatCode", "pred");
        ReflectionTestUtils.setField(kind.getGeslachtsnaamcomponenten().get(0).getStandaard(), "adellijkeTitelCode",
            "adel");

        berichtVerrijkingsStap.voerStapUit(bericht, bouwBerichtContext(""));

        verify(referentieDataRepository, times(1)).vindGemeenteOpCode(any(GemeenteCodeAttribuut.class));
        verify(referentieDataRepository, times(1)).vindPartijOpCode(any(PartijCodeAttribuut.class));
        verify(referentieDataRepository, times(1)).isBestaandeWoonplaats(any(NaamEnumeratiewaardeAttribuut.class));
        verify(referentieDataRepository, times(1)).vindLandOpCode(any(LandGebiedCodeAttribuut.class));
        verify(referentieDataRepository, times(2)).vindPredicaatOpCode(
            any(PredicaatCodeAttribuut.class));
        verify(referentieDataRepository, times(2)).vindAdellijkeTitelOpCode(
            any(AdellijkeTitelCodeAttribuut.class));
    }

    @Test
    public void testEersteInschrijvingVerrijkLandGeboorte() {
        final RegistreerGeboorteBericht bericht = new RegistreerGeboorteBericht();
        voegStuurgegevenToeAanBericht(bericht);

        final ActieBericht actie = new ActieRegistratieGeboorteBericht();
        final FamilierechtelijkeBetrekkingBericht relatie = new FamilierechtelijkeBetrekkingBericht();
        actie.setRootObject(relatie);
        bericht.getStandaard().setAdministratieveHandeling(new HandelingErkenningNaGeboorteBericht());
        bericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        bericht.getAdministratieveHandeling().getActies().add(actie);
        bericht.getAdministratieveHandeling().setPartijCode(GEM_34);

        final PersoonBericht kind =
            PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 123456789, Geslachtsaanduiding.MAN, 19830404,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA.getWaarde(),
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                "Piet", "van de", "Veldhuijsen");
        final PersoonGeboorteGroepBericht geboorte = new PersoonGeboorteGroepBericht();

        geboorte.setDatumGeboorte(new DatumEvtDeelsOnbekendAttribuut(19830404));
        geboorte.setGemeenteGeboorteCode(GEM_34);
        geboorte.setWoonplaatsnaamGeboorte(new NaamEnumeratiewaardeAttribuut(PLAATS_UTRECHT));

        kind.setGeboorte(geboorte);

        kind.setNaamgebruik(PersoonBuilder.bouwPersoonNaamgebruikGroepbericht(
            null, null,
            "voornamen", "van de", " ", "Kamp",
            null, null, null));
        kind.getNaamgebruik().setAdellijkeTitelNaamgebruikCode("J");
        kind.getNaamgebruik().setPredicaatNaamgebruikCode("K");

        relatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        final KindBericht betr = new KindBericht();
        betr.setPersoon(kind);
        relatie.getBetrokkenheden().add(betr);

        ReflectionTestUtils.setField(kind.getGeslachtsnaamcomponenten().get(0).getStandaard(), "predicaatCode", "pred");
        ReflectionTestUtils.setField(kind.getGeslachtsnaamcomponenten().get(0).getStandaard(), "adellijkeTitelCode",
            "adel");

        berichtVerrijkingsStap.voerStapUit(bericht, bouwBerichtContext(""));

        verify(referentieDataRepository, times(1)).vindGemeenteOpCode(
            any(GemeenteCodeAttribuut.class));
        verify(referentieDataRepository, times(1)).vindPartijOpCode(
            any(PartijCodeAttribuut.class));
        verify(referentieDataRepository, times(1)).isBestaandeWoonplaats(any(NaamEnumeratiewaardeAttribuut.class));
        verify(referentieDataRepository, times(1)).vindLandOpCode(
            any(LandGebiedCodeAttribuut.class));
        verify(referentieDataRepository, times(2)).vindPredicaatOpCode(
            any(PredicaatCodeAttribuut.class));
        verify(referentieDataRepository, times(2)).vindAdellijkeTitelOpCode(
            any(AdellijkeTitelCodeAttribuut.class));
    }

    @Test
    public void testEersteInschrijvingGeenVerrijkingNodig() {
        final RegistreerGeboorteBericht bericht = new RegistreerGeboorteBericht();
        voegStuurgegevenToeAanBericht(bericht);

        final ActieBericht actie = new ActieRegistratieGeboorteBericht();
        final FamilierechtelijkeBetrekkingBericht relatie = new FamilierechtelijkeBetrekkingBericht();
        actie.setRootObject(relatie);
        bericht.getStandaard().setAdministratieveHandeling(new HandelingErkenningNaGeboorteBericht());
        bericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        bericht.getAdministratieveHandeling().getActies().add(actie);

        final PersoonBericht kind =
            PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 123456789, Geslachtsaanduiding.MAN, 19830404,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA.getWaarde(),
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                "Piet", "van de", "Veldhuijsen");
        final PersoonGeboorteGroepBericht geboorte = new PersoonGeboorteGroepBericht();
        // BOLIE: haaah, te kort door de bocht. Het feit dat je een 'semi' leeg geboorteGroepBericht aanmaakt, wil niet
        // zeggen dat er dan geen landCode wordt opgehaald.
        // Regel is (tijdelijk) geboorteLand == null => geboorteLand = NL
        // Voorlopig is dit weer in de code opgelost dooe aan te nemen dat de geboorteDatum verplicht niet null is.

        kind.setGeboorte(geboorte);
        relatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        final KindBericht betr = new KindBericht();
        betr.setPersoon(kind);
        relatie.getBetrokkenheden().add(betr);

        berichtVerrijkingsStap
            .voerStapUit(bericht, bouwBerichtContext(""));

        verify(referentieDataRepository, times(0)).vindGemeenteOpCode(
            any(GemeenteCodeAttribuut.class));
        verify(referentieDataRepository, times(0)).isBestaandeWoonplaats(any(NaamEnumeratiewaardeAttribuut.class));
        // BOLIE: geboorteLand = null => automatisch NL.
        verify(referentieDataRepository, times(0)).vindLandOpCode(
            any(LandGebiedCodeAttribuut.class));
        verify(referentieDataRepository, times(0)).vindPredicaatOpCode(
            any(PredicaatCodeAttribuut.class));
        verify(referentieDataRepository, times(0)).vindAdellijkeTitelOpCode(
            any(AdellijkeTitelCodeAttribuut.class));
    }

    @Test
    public void testEersteInschrijvingOnbekendeReferentie() {
        final RegistreerGeboorteBericht bericht = new RegistreerGeboorteBericht();
        voegStuurgegevenToeAanBericht(bericht);

        final ActieBericht actie = new ActieRegistratieGeboorteBericht();
        final FamilierechtelijkeBetrekkingBericht relatie = new FamilierechtelijkeBetrekkingBericht();
        actie.setRootObject(relatie);
        bericht.getStandaard().setAdministratieveHandeling(new HandelingErkenningNaGeboorteBericht());
        bericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        bericht.getAdministratieveHandeling().getActies().add(actie);

        final PersoonBericht kind =
            PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 123456789, Geslachtsaanduiding.MAN, 19830404,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA.getWaarde(),
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                "Piet", "van de", "Veldhuijsen");
        final PersoonGeboorteGroepBericht geboorte = new PersoonGeboorteGroepBericht();

        geboorte.setGemeenteGeboorteCode(GEM_34);
        geboorte.setWoonplaatsnaamGeboorte(new NaamEnumeratiewaardeAttribuut(PLAATS_UTRECHT));
        geboorte.setLandGebiedGeboorteCode(LAND_2);
        geboorte.setCommunicatieID("commid.geboorte");

        kind.setGeboorte(geboorte);
        kind.setNaamgebruik(PersoonBuilder.bouwPersoonNaamgebruikGroepbericht(
            null, null,
            "voornamen", "van de", " ", "Kamp",
            null, null, null));
        kind.getNaamgebruik().setAdellijkeTitelNaamgebruikCode("J");
        kind.getNaamgebruik().setPredicaatNaamgebruikCode("pred");
        kind.getNaamgebruik().setCommunicatieID("commid.naamgebruik");

        relatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        final KindBericht betr = new KindBericht();
        betr.setPersoon(kind);
        relatie.getBetrokkenheden().add(betr);

        kind.getGeslachtsnaamcomponenten().get(0).getStandaard().setPredicaatCode("pred");
        kind.getGeslachtsnaamcomponenten().get(0).getStandaard().setAdellijkeTitelCode("adel");
        kind.getGeslachtsnaamcomponenten().get(0).getStandaard().setCommunicatieID("commid.geslcomp");

        when(referentieDataRepository.isBestaandeWoonplaats(any(NaamEnumeratiewaardeAttribuut.class))).thenReturn(false);

        when(referentieDataRepository.vindGemeenteOpCode(any(GemeenteCodeAttribuut.class))).thenThrow(
            new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.GEMEENTECODE, "foo",
                new BijhoudingExceptie())
        );

        when(referentieDataRepository.vindLandOpCode(any(LandGebiedCodeAttribuut.class))).thenThrow(
            new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.LANDGEBIEDCODE, "foo",
                new BijhoudingExceptie())
        );

        when(referentieDataRepository.vindPredicaatOpCode(any(PredicaatCodeAttribuut.class))).thenThrow(
            new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.PREDICAAT, "foo",
                new BijhoudingExceptie())
        );

        when(referentieDataRepository.vindAdellijkeTitelOpCode(any(AdellijkeTitelCodeAttribuut.class)))
            .thenThrow(
                new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.ADELLIJKETITEL,
                    "foo", new BijhoudingExceptie())
            );

        final Resultaat resultaat = berichtVerrijkingsStap.voerStapUit(bericht, bouwBerichtContext(""));

        assertEquals(7, resultaat.getMeldingen().size());
        List<Regel> regels = haalRegelsGesorteerdUitMeldingen(resultaat.getMeldingen());
        List<Regel> expected = Arrays
            .asList(Regel.BRAL1001, Regel.BRAL1002, Regel.BRAL1015, Regel.BRAL1015, Regel.BRAL1018, Regel.BRAL1018, Regel.BRAL1020);
        assertEquals(expected, regels);
    }

    @Test
    public void testOnbekendeReferentieExceptionAlsResultaat() {
        final RegistreerVerhuizingBericht verhuisBericht = new RegistreerVerhuizingBericht();
        voegStuurgegevenToeAanBericht(verhuisBericht);

        final ActieBericht verhuisActie = new ActieRegistratieAdresBericht();
        final PersoonBericht pers =
            PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 123456789, Geslachtsaanduiding.MAN, 19830404,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA.getWaarde(),
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                "Piet", "van de", "Veldhuijsen");
        pers.setAdressen(new ArrayList<PersoonAdresBericht>());
        verhuisActie.setRootObject(pers);
        verhuisBericht.getStandaard().setAdministratieveHandeling(new HandelingErkenningNaGeboorteBericht());
        verhuisBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        verhuisBericht.getAdministratieveHandeling().getActies().add(verhuisActie);
//        verhuisBericht.getAdministratieveHandeling().setPartijCode(GEM_34);

        final PersoonAdresBericht adres = new PersoonAdresBericht();
        final PersoonAdresStandaardGroepBericht standaard = new PersoonAdresStandaardGroepBericht();
        adres.setStandaard(standaard);
        pers.getAdressen().add(adres);

        standaard.setWoonplaatsnaam(new NaamEnumeratiewaardeAttribuut(PLAATS_UTRECHT));
        standaard.setGemeenteCode(GEM_34);
        standaard.setRedenWijzigingCode(WIJZADRES_P.getWaarde());

        when(referentieDataRepository.isBestaandeWoonplaats(any(NaamEnumeratiewaardeAttribuut.class))).thenReturn(false);

        when(referentieDataRepository.vindGemeenteOpCode(any(GemeenteCodeAttribuut.class))).thenThrow(
            new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.GEMEENTECODE, "foo",
                new BijhoudingExceptie())
        );

//        Mockito.when(referentieDataRepository.vindLandOpCode(Matchers.any(Landcode.class))).thenThrow(
//            new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.LANDCODE, "foo",
//                new BijhoudingExceptie()));

        when(referentieDataRepository.vindRedenWijzingVerblijfOpCode(any(RedenWijzigingVerblijfCodeAttribuut.class)))
            .thenThrow(
                new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.REDENWIJZINGADRES,
                    "foo", new BijhoudingExceptie())
            );

        final Resultaat resultaat = berichtVerrijkingsStap.voerStapUit(verhuisBericht, bouwBerichtContext(""));

        final Set<ResultaatMelding> meldingen = resultaat.getMeldingen();
        assertEquals(3, meldingen.size());
        List<Regel> regels = haalRegelsGesorteerdUitMeldingen(meldingen);
        List<Regel> expected = Arrays
            .asList(Regel.BRAL1002, Regel.BRAL1007, Regel.BRAL1020);
        assertEquals(expected, regels);
    }

    private List<Regel> haalRegelsGesorteerdUitMeldingen(final Set<ResultaatMelding> meldingen) {
        List<Regel> regels = new ArrayList<>();
        for (ResultaatMelding resultaatMelding : meldingen) {
            regels.add(resultaatMelding.getRegel());
        }
        Collections.sort(regels);
        return regels;
    }

    @Test
    public void testRegistratieNationaliteitGeenVerrijkingNodig() {
        final RegistreerGeboorteBericht bericht = new RegistreerGeboorteBericht();
        voegStuurgegevenToeAanBericht(bericht);

        final ActieBericht verhuisActie = new ActieRegistratieNationaliteitBericht();
        final PersoonBericht pers =
            PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 123456789, Geslachtsaanduiding.MAN, 19830404,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA.getWaarde(),
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                "Piet", "van de", "Veldhuijsen");
        verhuisActie.setRootObject(pers);
        bericht.getStandaard().setAdministratieveHandeling(new HandelingErkenningNaGeboorteBericht());
        bericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        bericht.getAdministratieveHandeling().getActies().add(verhuisActie);
        bericht.getAdministratieveHandeling().setPartijCode(GEM_34);

        pers.setNationaliteiten(new ArrayList<PersoonNationaliteitBericht>());
        final PersoonNationaliteitBericht persNation = new PersoonNationaliteitBericht();
        pers.getNationaliteiten().add(persNation);
        final PersoonNationaliteitStandaardGroepBericht gegevens = new PersoonNationaliteitStandaardGroepBericht();
        persNation.setStandaard(gegevens);

        berichtVerrijkingsStap.voerStapUit(bericht, bouwBerichtContext(""));

        verify(referentieDataRepository, times(0)).vindNationaliteitOpCode(
            any(NationaliteitcodeAttribuut.class));
        verify(referentieDataRepository, times(0)).vindRedenVerkregenNlNationaliteitOpCode(
            any(RedenVerkrijgingCodeAttribuut.class));
        verify(referentieDataRepository, times(0)).vindRedenVerliesNLNationaliteitOpCode(
            any(RedenVerliesCodeAttribuut.class));
    }

    @Test
    public void testRegistratieNationaliteit() {
        final RegistreerVerhuizingBericht verhuisBericht = new RegistreerVerhuizingBericht();
        voegStuurgegevenToeAanBericht(verhuisBericht);

        final ActieBericht verhuisActie = new ActieRegistratieNationaliteitBericht();
        final PersoonBericht pers =
            PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 123456789, Geslachtsaanduiding.MAN, 19830404,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA.getWaarde(),
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                "Piet", "van de", "Veldhuijsen");
        verhuisActie.setRootObject(pers);
        verhuisBericht.getStandaard().setAdministratieveHandeling(new HandelingErkenningNaGeboorteBericht());
        verhuisBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        verhuisBericht.getAdministratieveHandeling().getActies().add(verhuisActie);
        verhuisBericht.getAdministratieveHandeling().setPartijCode(GEM_34);

        pers.setNationaliteiten(new ArrayList<PersoonNationaliteitBericht>());
        final PersoonNationaliteitBericht persNation = new PersoonNationaliteitBericht();
        pers.getNationaliteiten().add(persNation);
        persNation.setNationaliteitCode(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE.toString());
        final PersoonNationaliteitStandaardGroepBericht gegevens = new PersoonNationaliteitStandaardGroepBericht();
        gegevens.setRedenVerkrijgingCode(VERKRIJG_10.toString());
        gegevens.setRedenVerliesCode(VERLIES_07.toString());
        persNation.setStandaard(gegevens);

        berichtVerrijkingsStap.voerStapUit(verhuisBericht, bouwBerichtContext(""));

        verify(referentieDataRepository, times(1)).vindNationaliteitOpCode(
            any(NationaliteitcodeAttribuut.class));
        verify(referentieDataRepository, times(1)).vindRedenVerkregenNlNationaliteitOpCode(
            any(RedenVerkrijgingCodeAttribuut.class));
        verify(referentieDataRepository, times(1)).vindRedenVerliesNLNationaliteitOpCode(
            any(RedenVerliesCodeAttribuut.class));
    }

    @Test
    public void testHuwelijk() {
        final RelatieStandaardGroepBericht standaard = new RelatieStandaardGroepBericht();
        standaard.setLandGebiedAanvangCode(LAND_2);
        standaard.setLandGebiedEindeCode(LAND_2);
        standaard.setGemeenteAanvangCode(GEM_34);
        standaard.setGemeenteEindeCode(GEM_34);
        standaard.setWoonplaatsnaamAanvang(new NaamEnumeratiewaardeAttribuut(PLAATS_UTRECHT));
        standaard.setWoonplaatsnaamEinde(new NaamEnumeratiewaardeAttribuut(PLAATS_UTRECHT));

        final nl.bzk.brp.model.bericht.kern.HuwelijkBericht relatieBericht =
            new nl.bzk.brp.model.bericht.kern.HuwelijkBericht();
        relatieBericht.setStandaard(standaard);

        final List<BetrokkenheidBericht> betrokkenheden = new ArrayList<>();
        final BetrokkenheidBericht betrokkenheidMan = new PartnerBericht();
        final PersoonBericht man = new PersoonBericht();
        final List<PersoonGeslachtsnaamcomponentBericht> geslachtsnaamComponenten
            = new ArrayList<>();

        final PersoonGeslachtsnaamcomponentBericht geslachtsnaamcomponent = new PersoonGeslachtsnaamcomponentBericht();
        geslachtsnaamcomponent.setStandaard(new PersoonGeslachtsnaamcomponentStandaardGroepBericht());
        geslachtsnaamComponenten.add(geslachtsnaamcomponent);
        man.setGeslachtsnaamcomponenten(geslachtsnaamComponenten);

        final PersoonSamengesteldeNaamGroepBericht samengesteldeNaam = new PersoonSamengesteldeNaamGroepBericht();
        samengesteldeNaam.setPredicaatCode("P");
        samengesteldeNaam.setAdellijkeTitelCode("P");
        man.setSamengesteldeNaam(samengesteldeNaam);

        betrokkenheidMan.setPersoon(man);
        betrokkenheden.add(betrokkenheidMan);

        final BetrokkenheidBericht betrokkenheidVrouw = new PartnerBericht();
        betrokkenheidVrouw.setPersoon(new PersoonBericht());
        betrokkenheden.add(betrokkenheidVrouw);

        relatieBericht.setBetrokkenheden(betrokkenheden);

        final ActieBericht huwelijkActie = new ActieRegistratieHuwelijkGeregistreerdPartnerschapBericht();
        huwelijkActie.setRootObject(relatieBericht);

        final RegistreerHuwelijkGeregistreerdPartnerschapBericht huwelijkBericht = new RegistreerHuwelijkGeregistreerdPartnerschapBericht();
        voegStuurgegevenToeAanBericht(huwelijkBericht);
        huwelijkBericht.getStandaard().setAdministratieveHandeling(new HandelingErkenningNaGeboorteBericht());
        huwelijkBericht.getAdministratieveHandeling().setPartij(new PartijAttribuut(TestPartijBuilder.maker().maak()));
        huwelijkBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        huwelijkBericht.getAdministratieveHandeling().getActies().add(huwelijkActie);
//        huwelijkBericht.getAdministratieveHandeling().setPartijCode(GEM_34);

        berichtVerrijkingsStap.voerStapUit(huwelijkBericht, bouwBerichtContext(""));
        verify(referentieDataRepository, times(2)).vindLandOpCode(any(LandGebiedCodeAttribuut.class));
        verify(referentieDataRepository, times(2)).vindGemeenteOpCode(any(GemeenteCodeAttribuut.class));
        verify(referentieDataRepository, times(2)).isBestaandeWoonplaats(any(NaamEnumeratiewaardeAttribuut.class));

        assertNotNull(huwelijkActie.getPartij());
        assertNotNull(huwelijkActie.getTijdstipRegistratie());
    }


    @Test
    public void testHuwelijkZonderLand() {
        final RelatieStandaardGroepBericht standaard = new RelatieStandaardGroepBericht();
        standaard.setLandGebiedAanvangCode(null);
        standaard.setLandGebiedEindeCode(LAND_2);
        standaard.setGemeenteAanvangCode(GEM_34);
        standaard.setGemeenteEindeCode(GEM_34);
        standaard.setWoonplaatsnaamAanvang(new NaamEnumeratiewaardeAttribuut(PLAATS_UTRECHT));
        standaard.setWoonplaatsnaamEinde(new NaamEnumeratiewaardeAttribuut(PLAATS_UTRECHT));

        final nl.bzk.brp.model.bericht.kern.HuwelijkBericht relatieBericht =
            new nl.bzk.brp.model.bericht.kern.HuwelijkBericht();
        relatieBericht.setStandaard(standaard);

        final ActieBericht huwelijkActie = new ActieRegistratieHuwelijkGeregistreerdPartnerschapBericht();
        huwelijkActie.setRootObject(relatieBericht);

        final RegistreerHuwelijkGeregistreerdPartnerschapBericht huwelijkBericht = new RegistreerHuwelijkGeregistreerdPartnerschapBericht();
        voegStuurgegevenToeAanBericht(huwelijkBericht);
        huwelijkBericht.getStandaard().setAdministratieveHandeling(new HandelingVoltrekkingHuwelijkInNederlandBericht());
        huwelijkBericht.getAdministratieveHandeling().setPartij(new PartijAttribuut(TestPartijBuilder.maker().maak()));
        huwelijkBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        huwelijkBericht.getAdministratieveHandeling().getActies().add(huwelijkActie);

        berichtVerrijkingsStap.voerStapUit(huwelijkBericht, bouwBerichtContext(""));
        verify(referentieDataRepository, times(2)).vindLandOpCode(any(LandGebiedCodeAttribuut.class));
        verify(referentieDataRepository, times(2)).vindGemeenteOpCode(any(GemeenteCodeAttribuut.class));
        verify(referentieDataRepository, times(2)).isBestaandeWoonplaats(
            any(NaamEnumeratiewaardeAttribuut.class));

        assertNotNull(huwelijkActie.getPartij());
        assertNotNull(huwelijkActie.getTijdstipRegistratie());

    }

    @Test
    public void testHuwelijkTekstIpvCijfers() {
        final RelatieStandaardGroepBericht standaard = new RelatieStandaardGroepBericht();
        standaard.setLandGebiedAanvangCode("abc");
        standaard.setLandGebiedEindeCode("ABC");
        standaard.setGemeenteAanvangCode("GEM_22");
        standaard.setGemeenteEindeCode("GEM_34");
        standaard.setWoonplaatsnaamAanvang(new NaamEnumeratiewaardeAttribuut("PLAATS_234"));
        standaard.setWoonplaatsnaamEinde(new NaamEnumeratiewaardeAttribuut("PLAATS_234"));

        final nl.bzk.brp.model.bericht.kern.HuwelijkBericht relatieBericht =
            new nl.bzk.brp.model.bericht.kern.HuwelijkBericht();
        relatieBericht.setStandaard(standaard);

        final ActieBericht huwelijkActie = new ActieRegistratieHuwelijkGeregistreerdPartnerschapBericht();
        huwelijkActie.setRootObject(relatieBericht);

        final RegistreerHuwelijkGeregistreerdPartnerschapBericht huwelijkBericht = new RegistreerHuwelijkGeregistreerdPartnerschapBericht();
        voegStuurgegevenToeAanBericht(huwelijkBericht);
        huwelijkBericht.getStandaard().setAdministratieveHandeling(new HandelingVoltrekkingHuwelijkInNederlandBericht());
        huwelijkBericht.getAdministratieveHandeling().setPartij(new PartijAttribuut(TestPartijBuilder.maker().maak()));
        huwelijkBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        huwelijkBericht.getAdministratieveHandeling().getActies().add(huwelijkActie);
//        huwelijkBericht.getAdministratieveHandeling().setPartijCode(GEM_34);

        berichtVerrijkingsStap.voerStapUit(huwelijkBericht, bouwBerichtContext(""));
        verify(referentieDataRepository, times(1)).vindLandOpCode(any(LandGebiedCodeAttribuut.class));
        verify(referentieDataRepository, times(0)).vindGemeenteOpCode(any(GemeenteCodeAttribuut.class));
        //Let op, woonplaats wordt nu verijkt op basis van een string en niet van een numerieke code, dus wordt altijd
        //aangeroepen:
        verify(referentieDataRepository, times(2)).isBestaandeWoonplaats(any(NaamEnumeratiewaardeAttribuut.class));

        assertNotNull(huwelijkActie.getPartij());
        assertNotNull(huwelijkActie.getTijdstipRegistratie());
    }

    @Test
    public void testGeregistreerdPartnerschap() {
        final RelatieStandaardGroepBericht standaard = new RelatieStandaardGroepBericht();
        standaard.setLandGebiedAanvangCode(LAND_2);
        standaard.setLandGebiedEindeCode(LAND_2);
        standaard.setGemeenteAanvangCode(GEM_34);
        standaard.setGemeenteEindeCode(GEM_34);
        standaard.setWoonplaatsnaamAanvang(new NaamEnumeratiewaardeAttribuut(PLAATS_UTRECHT));
        standaard.setWoonplaatsnaamEinde(new NaamEnumeratiewaardeAttribuut(PLAATS_UTRECHT));

        final nl.bzk.brp.model.bericht.kern.HuwelijkBericht relatieBericht =
            new nl.bzk.brp.model.bericht.kern.HuwelijkBericht();
        relatieBericht.setStandaard(standaard);

        final List<BetrokkenheidBericht> betrokkenheden = new ArrayList<>();
        final BetrokkenheidBericht betrokkenheidMan = new PartnerBericht();
        final PersoonBericht man = new PersoonBericht();
        final List<PersoonGeslachtsnaamcomponentBericht> geslachtsnaamComponenten =
            new ArrayList<>();

        final PersoonGeslachtsnaamcomponentBericht geslachtsnaamcomponent = new PersoonGeslachtsnaamcomponentBericht();
        geslachtsnaamcomponent.setStandaard(new PersoonGeslachtsnaamcomponentStandaardGroepBericht());
        geslachtsnaamComponenten.add(geslachtsnaamcomponent);
        man.setGeslachtsnaamcomponenten(geslachtsnaamComponenten);

        final PersoonSamengesteldeNaamGroepBericht samengesteldeNaam = new PersoonSamengesteldeNaamGroepBericht();
        samengesteldeNaam.setPredicaatCode("P");
        samengesteldeNaam.setAdellijkeTitelCode("P");
        man.setSamengesteldeNaam(samengesteldeNaam);

        betrokkenheidMan.setPersoon(man);
        betrokkenheden.add(betrokkenheidMan);

        final BetrokkenheidBericht betrokkenheidVrouw = new PartnerBericht();
        betrokkenheidVrouw.setPersoon(new PersoonBericht());
        betrokkenheden.add(betrokkenheidVrouw);

        relatieBericht.setBetrokkenheden(betrokkenheden);

        final ActieBericht huwelijkActie = new ActieRegistratieHuwelijkGeregistreerdPartnerschapBericht();
        huwelijkActie.setRootObject(relatieBericht);

        final RegistreerHuwelijkGeregistreerdPartnerschapBericht huwelijkBericht = new RegistreerHuwelijkGeregistreerdPartnerschapBericht();
        voegStuurgegevenToeAanBericht(huwelijkBericht);
        huwelijkBericht.getStandaard().setAdministratieveHandeling(new HandelingAangaanGeregistreerdPartnerschapInNederlandBericht());
        huwelijkBericht.getAdministratieveHandeling().setPartij(new PartijAttribuut(
            TestPartijBuilder.maker().maak()));
        huwelijkBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        huwelijkBericht.getAdministratieveHandeling().getActies().add(huwelijkActie);
//        huwelijkBericht.getAdministratieveHandeling().setPartijCode(GEM_34);

        berichtVerrijkingsStap.voerStapUit(huwelijkBericht, bouwBerichtContext(""));
        verify(referentieDataRepository, times(2)).vindLandOpCode(any(LandGebiedCodeAttribuut.class));
        verify(referentieDataRepository, times(2)).vindGemeenteOpCode(any(GemeenteCodeAttribuut.class));
        verify(referentieDataRepository, times(2)).isBestaandeWoonplaats(any(NaamEnumeratiewaardeAttribuut.class));

        assertNotNull(huwelijkActie.getPartij());
        assertNotNull(huwelijkActie.getTijdstipRegistratie());
    }

    @Test
    public void testBeeindigingHuwelijk() {
        final RelatieStandaardGroepBericht standaard = new RelatieStandaardGroepBericht();
        standaard.setLandGebiedAanvangCode(LAND_2);
        standaard.setLandGebiedEindeCode(LAND_2);
        standaard.setGemeenteAanvangCode(GEM_34);
        standaard.setGemeenteEindeCode(GEM_34);
        standaard.setWoonplaatsnaamAanvang(new NaamEnumeratiewaardeAttribuut(PLAATS_UTRECHT));
        standaard.setWoonplaatsnaamEinde(new NaamEnumeratiewaardeAttribuut(PLAATS_UTRECHT));
        standaard.setRedenEindeCode("XX");

        final nl.bzk.brp.model.bericht.kern.HuwelijkBericht relatieBericht =
            new nl.bzk.brp.model.bericht.kern.HuwelijkBericht();
        relatieBericht.setStandaard(standaard);

        final ActieBericht huwelijkActie = new ActieRegistratieEindeHuwelijkGeregistreerdPartnerschapBericht();
        huwelijkActie.setRootObject(relatieBericht);

        final RegistreerHuwelijkGeregistreerdPartnerschapBericht huwelijkBericht = new RegistreerHuwelijkGeregistreerdPartnerschapBericht();
        voegStuurgegevenToeAanBericht(huwelijkBericht);
        huwelijkBericht.getStandaard().setAdministratieveHandeling(new HandelingOntbindingHuwelijkInNederlandBericht());
        huwelijkBericht.getAdministratieveHandeling().setPartij(new PartijAttribuut(
            TestPartijBuilder.maker().maak()));
        huwelijkBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        huwelijkBericht.getAdministratieveHandeling().getActies().add(huwelijkActie);

        final Resultaat resultaat = berichtVerrijkingsStap.voerStapUit(huwelijkBericht, bouwBerichtContext(""));
        verify(referentieDataRepository, times(2)).vindLandOpCode(any(LandGebiedCodeAttribuut.class));
        verify(referentieDataRepository, times(2)).vindGemeenteOpCode(any(GemeenteCodeAttribuut.class));
        verify(referentieDataRepository, times(2)).isBestaandeWoonplaats(
            any(NaamEnumeratiewaardeAttribuut.class));

        assertNotNull(huwelijkActie.getPartij());
        assertNotNull(huwelijkActie.getTijdstipRegistratie());
        assertEquals(0, resultaat.getMeldingen().size());
    }

    @Test
    public void testBeeindigingHuwelijkMetOnbekendeReferentie() {
        when(referentieDataRepository.vindRedenEindeRelatieOpCode(
            any(RedenEindeRelatieCodeAttribuut.class)))
            .thenThrow(
                new OnbekendeReferentieExceptie(
                    OnbekendeReferentieExceptie.ReferentieVeld.REDENEINDERELATIE,
                    "foo", new BijhoudingExceptie())
            );

        final RelatieStandaardGroepBericht standaard = new RelatieStandaardGroepBericht();
        standaard.setLandGebiedAanvangCode(LAND_2);
        standaard.setLandGebiedEindeCode(LAND_2);
        standaard.setGemeenteAanvangCode(GEM_34);
        standaard.setGemeenteEindeCode(GEM_34);
        standaard.setWoonplaatsnaamAanvang(new NaamEnumeratiewaardeAttribuut(PLAATS_UTRECHT));
        standaard.setWoonplaatsnaamEinde(new NaamEnumeratiewaardeAttribuut(PLAATS_UTRECHT));
        standaard.setRedenEindeCode("XX");

        final nl.bzk.brp.model.bericht.kern.HuwelijkBericht relatieBericht =
            new nl.bzk.brp.model.bericht.kern.HuwelijkBericht();
        relatieBericht.setStandaard(standaard);

        final ActieBericht huwelijkActie = new ActieRegistratieEindeHuwelijkGeregistreerdPartnerschapBericht();
        huwelijkActie.setRootObject(relatieBericht);

        final RegistreerHuwelijkGeregistreerdPartnerschapBericht huwelijkBericht = new RegistreerHuwelijkGeregistreerdPartnerschapBericht();
        voegStuurgegevenToeAanBericht(huwelijkBericht);
        huwelijkBericht.getStandaard().setAdministratieveHandeling(new HandelingOntbindingHuwelijkInNederlandBericht());
        huwelijkBericht.getAdministratieveHandeling().setPartij(new PartijAttribuut(
            TestPartijBuilder.maker().maak()));
        huwelijkBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        huwelijkBericht.getAdministratieveHandeling().getActies().add(huwelijkActie);

        final Resultaat resultaat = berichtVerrijkingsStap.voerStapUit(huwelijkBericht, bouwBerichtContext(""));
        verify(referentieDataRepository, times(1))
            .vindRedenEindeRelatieOpCode(any(RedenEindeRelatieCodeAttribuut.class));

        assertNotNull(huwelijkActie.getPartij());
        assertNotNull(huwelijkActie.getTijdstipRegistratie());
        assertEquals(1, resultaat.getMeldingen().size());
    }

    @Test
    public void testBeeindigingPartnerschap() {
        final RelatieStandaardGroepBericht standaard = new RelatieStandaardGroepBericht();
        standaard.setLandGebiedAanvangCode(LAND_2);
        standaard.setLandGebiedEindeCode(LAND_2);
        standaard.setGemeenteAanvangCode(GEM_34);
        standaard.setGemeenteEindeCode(GEM_34);
        standaard.setWoonplaatsnaamAanvang(new NaamEnumeratiewaardeAttribuut(PLAATS_UTRECHT));
        standaard.setWoonplaatsnaamEinde(new NaamEnumeratiewaardeAttribuut(PLAATS_UTRECHT));
        standaard.setRedenEindeCode("XX");

        final GeregistreerdPartnerschapBericht relatieBericht =
            new nl.bzk.brp.model.bericht.kern.GeregistreerdPartnerschapBericht();
        relatieBericht.setStandaard(standaard);

        final ActieBericht huwelijkActie = new ActieRegistratieEindeHuwelijkGeregistreerdPartnerschapBericht();
        huwelijkActie.setRootObject(relatieBericht);

        final RegistreerHuwelijkGeregistreerdPartnerschapBericht huwelijkBericht = new RegistreerHuwelijkGeregistreerdPartnerschapBericht();
        voegStuurgegevenToeAanBericht(huwelijkBericht);
        huwelijkBericht.getStandaard().setAdministratieveHandeling(
            new HandelingBeeindigingGeregistreerdPartnerschapInNederlandBericht());
        huwelijkBericht.getAdministratieveHandeling().setPartij(new PartijAttribuut(
            TestPartijBuilder.maker().maak()));
        huwelijkBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        huwelijkBericht.getAdministratieveHandeling().getActies().add(huwelijkActie);

        final Resultaat resultaat = berichtVerrijkingsStap.voerStapUit(huwelijkBericht, bouwBerichtContext(""));
        verify(referentieDataRepository, times(2)).vindLandOpCode(any(LandGebiedCodeAttribuut.class));
        verify(referentieDataRepository, times(2)).vindGemeenteOpCode(any(GemeenteCodeAttribuut.class));
        verify(referentieDataRepository, times(2)).isBestaandeWoonplaats(
            any(NaamEnumeratiewaardeAttribuut.class));

        assertNotNull(huwelijkActie.getPartij());
        assertNotNull(huwelijkActie.getTijdstipRegistratie());
        assertEquals(0, resultaat.getMeldingen().size());
    }

    @Test
    public void testOmzettingPartnerschapInHuwelijk() {
        final RelatieStandaardGroepBericht standaard = new RelatieStandaardGroepBericht();
        standaard.setLandGebiedAanvangCode(LAND_2);
        standaard.setGemeenteAanvangCode(GEM_34);
        standaard.setGemeenteEindeCode(GEM_34);
        standaard.setWoonplaatsnaamAanvang(new NaamEnumeratiewaardeAttribuut(PLAATS_UTRECHT));
        standaard.setWoonplaatsnaamEinde(new NaamEnumeratiewaardeAttribuut(PLAATS_UTRECHT));
        standaard.setRedenEindeCode("XX");

        final GeregistreerdPartnerschapBericht relatieBericht =
            new nl.bzk.brp.model.bericht.kern.GeregistreerdPartnerschapBericht();
        relatieBericht.setStandaard(standaard);

        final ActieBericht huwelijkActie = new ActieRegistratieEindeHuwelijkGeregistreerdPartnerschapBericht();
        huwelijkActie.setRootObject(relatieBericht);

        final RegistreerHuwelijkGeregistreerdPartnerschapBericht huwelijkBericht = new RegistreerHuwelijkGeregistreerdPartnerschapBericht();
        voegStuurgegevenToeAanBericht(huwelijkBericht);
        huwelijkBericht.getStandaard().setAdministratieveHandeling(new HandelingOmzettingGeregistreerdPartnerschapInHuwelijkBericht());
        huwelijkBericht.getAdministratieveHandeling().setPartij(new PartijAttribuut(
            TestPartijBuilder.maker().maak()));
        huwelijkBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        huwelijkBericht.getAdministratieveHandeling().getActies().add(huwelijkActie);

        final Resultaat resultaat = berichtVerrijkingsStap.voerStapUit(huwelijkBericht, bouwBerichtContext(""));

        verify(referentieDataRepository, times(2)).vindLandOpCode(any(LandGebiedCodeAttribuut.class));
        verify(referentieDataRepository, times(2)).vindGemeenteOpCode(any(GemeenteCodeAttribuut.class));
        verify(referentieDataRepository, times(2)).isBestaandeWoonplaats(any(NaamEnumeratiewaardeAttribuut.class));

        assertNotNull(((GeregistreerdPartnerschapBericht) huwelijkActie.getRootObject()).getStandaard().getLandGebiedEinde());
        assertNotNull(huwelijkActie.getPartij());
        assertNotNull(huwelijkActie.getTijdstipRegistratie());
        assertEquals(0, resultaat.getMeldingen().size());
    }

    @Test
    public void testOverlijdenGroepVerrijking() {
        final RegistreerVerhuizingBericht verhuisBericht = new RegistreerVerhuizingBericht();
        voegStuurgegevenToeAanBericht(verhuisBericht);

        final ActieBericht verhuisActie = new ActieRegistratieAdresBericht();

        final PersoonBericht pers =
            PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 123456789, Geslachtsaanduiding.MAN, 19830404,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA.getWaarde(),
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                "Piet", "van de", "Veldhuijsen");
        pers.setAdressen(new ArrayList<PersoonAdresBericht>());
        verhuisActie.setRootObject(pers);
        verhuisBericht.getStandaard().setAdministratieveHandeling(new HandelingErkenningNaGeboorteBericht());
        verhuisBericht.getAdministratieveHandeling().setPartij(new PartijAttribuut(
            TestPartijBuilder.maker().maak()));
        verhuisBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        verhuisBericht.getAdministratieveHandeling().getActies().add(verhuisActie);
//        verhuisBericht.getAdministratieveHandeling().setPartijCode(GEM_34);


        final PersoonOverlijdenGroepBericht overlijden = new PersoonOverlijdenGroepBericht();
        overlijden.setGemeenteOverlijdenCode(GEM_34);
        overlijden.setWoonplaatsnaamOverlijden(new NaamEnumeratiewaardeAttribuut(PLAATS_UTRECHT));
        overlijden.setLandGebiedOverlijdenCode(LAND_2);

        pers.setOverlijden(overlijden);

        berichtVerrijkingsStap.voerStapUit(verhuisBericht, bouwBerichtContext(""));
        verify(referentieDataRepository, times(1)).vindGemeenteOpCode(
            any(GemeenteCodeAttribuut.class));
        verify(referentieDataRepository, times(1)).isBestaandeWoonplaats(
            any(NaamEnumeratiewaardeAttribuut.class));
        verify(referentieDataRepository, times(1)).vindLandOpCode(
            any(LandGebiedCodeAttribuut.class));
        assertNotNull(verhuisActie.getPartij());
        assertNotNull(verhuisActie.getTijdstipRegistratie());
    }

    @Test
    public void testOverlijdenZonderLandcode() {
        final RegistreerOverlijdenBericht overlijdenBericht = new RegistreerOverlijdenBericht();
        voegStuurgegevenToeAanBericht(overlijdenBericht);

        final ActieBericht overlijdenActie = new ActieRegistratieOverlijdenBericht();

        final PersoonBericht pers =
            PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 123456789, Geslachtsaanduiding.MAN, 19830404,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA.getWaarde(),
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                "Piet", "van de", "Veldhuijsen");
        pers.setAdressen(new ArrayList<PersoonAdresBericht>());
        overlijdenActie.setRootObject(pers);
        overlijdenBericht.getStandaard().setAdministratieveHandeling(new HandelingOverlijdenInNederlandBericht());
        overlijdenBericht.getAdministratieveHandeling().setPartij(new PartijAttribuut(
            TestPartijBuilder.maker().maak()));
        overlijdenBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        overlijdenBericht.getAdministratieveHandeling().getActies().add(overlijdenActie);

        final PersoonOverlijdenGroepBericht overlijden = new PersoonOverlijdenGroepBericht();
        overlijden.setGemeenteOverlijdenCode(GEM_34);
        overlijden.setWoonplaatsnaamOverlijden(new NaamEnumeratiewaardeAttribuut(PLAATS_UTRECHT));

        pers.setOverlijden(overlijden);

        berichtVerrijkingsStap.voerStapUit(overlijdenBericht, bouwBerichtContext(""));
        verify(referentieDataRepository, times(1)).vindGemeenteOpCode(
            any(GemeenteCodeAttribuut.class));
        verify(referentieDataRepository, times(1)).isBestaandeWoonplaats(
            any(NaamEnumeratiewaardeAttribuut.class));
        assertNotNull(overlijdenActie.getPartij());
        assertNotNull(overlijdenActie.getTijdstipRegistratie());
    }

    @Test
    public void testOverlijdenGroepVerrijkingVerrijkingNietNodig() {
        final RegistreerVerhuizingBericht verhuisBericht = new RegistreerVerhuizingBericht();
        voegStuurgegevenToeAanBericht(verhuisBericht);

        final ActieBericht verhuisActie = new ActieRegistratieAdresBericht();

        final PersoonBericht pers =
            PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 123456789, Geslachtsaanduiding.MAN, 19830404,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA.getWaarde(),
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                "Piet", "van de", "Veldhuijsen");
        pers.setAdressen(new ArrayList<PersoonAdresBericht>());
        verhuisActie.setRootObject(pers);
        verhuisBericht.getStandaard().setAdministratieveHandeling(new HandelingErkenningNaGeboorteBericht());
        verhuisBericht.getAdministratieveHandeling().setPartij(new PartijAttribuut(
            TestPartijBuilder.maker().maak()));
        verhuisBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        verhuisBericht.getAdministratieveHandeling().getActies().add(verhuisActie);
//        verhuisBericht.getAdministratieveHandeling().setPartijCode(GEM_34);


        final PersoonOverlijdenGroepBericht overlijden = new PersoonOverlijdenGroepBericht();

        pers.setOverlijden(overlijden);

        berichtVerrijkingsStap.voerStapUit(verhuisBericht, bouwBerichtContext(""));
        verify(referentieDataRepository, times(0)).vindGemeenteOpCode(any(GemeenteCodeAttribuut.class));
        verify(referentieDataRepository, times(0)).isBestaandeWoonplaats(
            any(NaamEnumeratiewaardeAttribuut.class));
        verify(referentieDataRepository, times(0)).vindLandOpCode(any(LandGebiedCodeAttribuut.class));
        assertNotNull(verhuisActie.getPartij());
        assertNotNull(verhuisActie.getTijdstipRegistratie());
    }

    @Test
    public void testRegistratieNationaliteitOnbekendeReferentie() {
        final RegistreerVerhuizingBericht verhuisBericht = new RegistreerVerhuizingBericht();
        voegStuurgegevenToeAanBericht(verhuisBericht);
        final ActieBericht verhuisActie = new ActieRegistratieNationaliteitBericht();
        final PersoonBericht pers =
            PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 123456789, Geslachtsaanduiding.MAN, 19830404,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA.getWaarde(),
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                "Piet", "van de", "Veldhuijsen");
        verhuisActie.setRootObject(pers);
        verhuisBericht.getStandaard().setAdministratieveHandeling(new HandelingErkenningNaGeboorteBericht());
        verhuisBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        verhuisBericht.getAdministratieveHandeling().getActies().add(verhuisActie);
        verhuisBericht.getAdministratieveHandeling().setPartijCode(GEM_34);

        pers.setNationaliteiten(new ArrayList<PersoonNationaliteitBericht>());
        final PersoonNationaliteitBericht persNation = new PersoonNationaliteitBericht();
        pers.getNationaliteiten().add(persNation);
        persNation.setNationaliteitCode(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE.toString());
        final PersoonNationaliteitStandaardGroepBericht gegevens = new PersoonNationaliteitStandaardGroepBericht();
        gegevens.setRedenVerkrijgingCode(VERKRIJG_10.toString());
        gegevens.setRedenVerliesCode(VERLIES_07.toString());
        persNation.setStandaard(gegevens);

        when(referentieDataRepository.vindNationaliteitOpCode(any(NationaliteitcodeAttribuut.class)))
            .thenThrow(
                new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.NATIONALITITEITCODE,
                    "foo", new BijhoudingExceptie())
            );

        when(
            referentieDataRepository.vindRedenVerkregenNlNationaliteitOpCode(
                any(RedenVerkrijgingCodeAttribuut.class))
        )
            .thenThrow(
                new OnbekendeReferentieExceptie(
                    OnbekendeReferentieExceptie.ReferentieVeld.REDENVERKRIJGENNLNATION, "foo",
                    new BijhoudingExceptie())
            );

        when(
            referentieDataRepository.vindRedenVerliesNLNationaliteitOpCode(any(RedenVerliesCodeAttribuut.class)))
            .thenThrow(
                new OnbekendeReferentieExceptie(
                    OnbekendeReferentieExceptie.ReferentieVeld.REDENVERLIESNLNATION, "foo",
                    new BijhoudingExceptie())
            );

        final Resultaat resultaat = berichtVerrijkingsStap.voerStapUit(verhuisBericht, bouwBerichtContext(""));

        assertEquals(3, resultaat.getMeldingen().size());
        final Iterator<ResultaatMelding> iterator = resultaat.getMeldingen().iterator();
        final ResultaatMelding bral1017 = iterator.next();
        assertEquals(Regel.BRAL1017, bral1017.getRegel());
        assertEquals("Nationaliteit 0001 bestaat niet.", bral1017.getMeldingTekst());
        final ResultaatMelding bral1021 = iterator.next();
        assertEquals(Regel.BRAL1021, bral1021.getRegel());
        assertEquals("Reden verkrijging Nederlandse nationaliteit 010 bestaat niet.", bral1021.getMeldingTekst());
        final ResultaatMelding bral1022 = iterator.next();
        assertEquals(Regel.BRAL1022, bral1022.getRegel());
        assertEquals("Reden verlies Nederlandse nationaliteit 007 bestaat niet.", bral1022.getMeldingTekst());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testOngeldigeRootObject() {
        final ActieBericht verhuisActie = new ActieRegistratieAdresBericht();
        verhuisActie.setRootObject(mock(BerichtRootObject.class));

        final RegistreerVerhuizingBericht verhuisBericht = new RegistreerVerhuizingBericht();
        voegStuurgegevenToeAanBericht(verhuisBericht);
        verhuisBericht.getStandaard().setAdministratieveHandeling(new HandelingErkenningNaGeboorteBericht());
        verhuisBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        verhuisBericht.getAdministratieveHandeling().getActies().add(verhuisActie);
        verhuisBericht.getAdministratieveHandeling().setPartijCode(GEM_34);

        berichtVerrijkingsStap.voerStapUit(verhuisBericht, bouwBerichtContext(""));
    }

    @Test
    public void testVoorkomenVoorvoegselCorrect() {
        final Resultaat resultaat = berichtVerrijkingsStap.voerStapUit(bouwVoorvoegselTestBericht("van de", " "),
            bouwBerichtContext(""));
        verify(referentieDataRepository, times(1)).
            bestaatVoorvoegselScheidingsteken(anyString(), anyString());
        assertEquals(0, resultaat.getMeldingen().size());
    }

    @Test
    public void testVoorkomenVoorvoegselIncorrect() {
        final Resultaat resultaat = berichtVerrijkingsStap.voerStapUit(bouwVoorvoegselTestBericht("van der", " "),
            bouwBerichtContext(""));
        verify(referentieDataRepository, times(1)).
            bestaatVoorvoegselScheidingsteken(anyString(), anyString());
        assertEquals(1, resultaat.getMeldingen().size());
    }

    @Test
    public void testVoorkomenVoorvoegselIncorrect2() {
        final Resultaat resultaat = berichtVerrijkingsStap.voerStapUit(bouwVoorvoegselTestBericht("van de", "-"),
            bouwBerichtContext(""));
        verify(referentieDataRepository, times(1)).
            bestaatVoorvoegselScheidingsteken(anyString(), anyString());
        assertEquals(1, resultaat.getMeldingen().size());
    }

    @Test
    public void testVoorkomenVoorvoegselIncorrect3() {
        final Resultaat resultaat = berichtVerrijkingsStap.voerStapUit(bouwVoorvoegselTestBericht(null, " "),
            bouwBerichtContext(""));
        verify(referentieDataRepository, times(0)).
            bestaatVoorvoegselScheidingsteken(anyString(), anyString());
        assertEquals(0, resultaat.getMeldingen().size());
    }

    @Test
    public void testVoorkomenVoorvoegselIncorrect4() {
        final Resultaat resultaat = berichtVerrijkingsStap.voerStapUit(bouwVoorvoegselTestBericht("van de", null),
            bouwBerichtContext(""));
        verify(referentieDataRepository, times(0)).
            bestaatVoorvoegselScheidingsteken(anyString(), anyString());
        assertEquals(0, resultaat.getMeldingen().size());
    }

    private BijhoudingsBericht bouwVoorvoegselTestBericht(
        final String voorvoegsel, final String scheidingsteken)
    {
        final PersoonBericht persoon = new PersoonBericht();
        final PersoonNaamgebruikGroepBericht aanschrijving = new PersoonNaamgebruikGroepBericht();
        if (voorvoegsel != null) {
            aanschrijving.setVoorvoegselNaamgebruik(new VoorvoegselAttribuut(voorvoegsel));
        }
        if (scheidingsteken != null) {
            aanschrijving.setScheidingstekenNaamgebruik(new ScheidingstekenAttribuut(scheidingsteken));
        }
        persoon.setNaamgebruik(aanschrijving);

        final RegistreerAdoptieBericht bericht = new RegistreerAdoptieBericht();
        voegStuurgegevenToeAanBericht(bericht);
        final HandelingAdoptieIngezeteneBericht handeling = new HandelingAdoptieIngezeteneBericht();
        handeling.setActies(new ArrayList<ActieBericht>());
        final ActieRegistratieOuderBericht actie = new ActieRegistratieOuderBericht();
        actie.setRootObject(persoon);
        handeling.getActies().add(actie);
        bericht.getStandaard().setAdministratieveHandeling(handeling);

        return bericht;
    }

    @Test
    public void testMigratie() {
        final CorrigeerAdresBericht corrigeerAdresBericht = new CorrigeerAdresBericht();
        voegStuurgegevenToeAanBericht(corrigeerAdresBericht);
        final ActieBericht correctieAdresActie = new ActieCorrectieAdresBericht();

        final PersoonBericht pers =
            PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 123456789, Geslachtsaanduiding.MAN, 19830404,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA.getWaarde(),
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                "Piet", "van de", "Veldhuijsen");
        pers.setAdressen(new ArrayList<PersoonAdresBericht>());

        final PersoonMigratieGroepBericht migratieGroep = new PersoonMigratieGroepBericht();
        migratieGroep.setSoortMigratie(new SoortMigratieAttribuut(SoortMigratie.EMIGRATIE));
        migratieGroep.setLandGebiedMigratieCode(LANDGEBIED_6033.toString());
        migratieGroep.setAangeverMigratieCode(AANGEVER_VERZORGER.getWaarde());
        migratieGroep.setRedenWijzigingMigratieCode(WIJZADRES_P.getWaarde());
        pers.setMigratie(migratieGroep);

        correctieAdresActie.setRootObject(pers);
        corrigeerAdresBericht.getStandaard().setAdministratieveHandeling(new HandelingVerhuizingNaarBuitenlandBericht());
        corrigeerAdresBericht.getAdministratieveHandeling().setPartij(new PartijAttribuut(
            TestPartijBuilder.maker().maak()));
        corrigeerAdresBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        corrigeerAdresBericht.getAdministratieveHandeling().getActies().add(correctieAdresActie);

        berichtVerrijkingsStap.voerStapUit(corrigeerAdresBericht, bouwBerichtContext(""));
        verify(referentieDataRepository, times(1)).vindLandOpCode(any(LandGebiedCodeAttribuut.class));
        assertEquals(migratieGroep.getLandGebiedMigratie().getWaarde().getNaam().getWaarde(), "Bahama-eilanden");
    }

    @Test
    public void testMigratieMetFoutieveAangever() {
        final CorrigeerAdresBericht corrigeerAdresBericht = new CorrigeerAdresBericht();
        voegStuurgegevenToeAanBericht(corrigeerAdresBericht);
        final ActieBericht correctieAdresActie = new ActieCorrectieAdresBericht();

        final PersoonBericht pers =
            PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 123456789, Geslachtsaanduiding.MAN, 19830404,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA.getWaarde(),
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                "Piet", "van de", "Veldhuijsen");
        pers.setAdressen(new ArrayList<PersoonAdresBericht>());

        final PersoonMigratieGroepBericht migratieGroep = new PersoonMigratieGroepBericht();
        migratieGroep.setSoortMigratie(new SoortMigratieAttribuut(SoortMigratie.EMIGRATIE));
        migratieGroep.setLandGebiedMigratieCode(LANDGEBIED_6033.toString());
        migratieGroep.setAangeverMigratieCode(ONJUISTE_AANGEVER.getWaarde());
        migratieGroep.setRedenWijzigingMigratieCode(WIJZADRES_P.getWaarde());
        pers.setMigratie(migratieGroep);

        correctieAdresActie.setRootObject(pers);
        corrigeerAdresBericht.getStandaard().setAdministratieveHandeling(new HandelingVerhuizingNaarBuitenlandBericht());
        corrigeerAdresBericht.getAdministratieveHandeling().setPartij(new PartijAttribuut(
            TestPartijBuilder.maker().maak()));
        corrigeerAdresBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        corrigeerAdresBericht.getAdministratieveHandeling().getActies().add(correctieAdresActie);

        final Resultaat resultaat = berichtVerrijkingsStap.voerStapUit(corrigeerAdresBericht, bouwBerichtContext(""));
        assertFalse(resultaat.getMeldingen().isEmpty());
        assertEquals(1, resultaat.getMeldingen().size());
        assertEquals(Regel.BRAL1008, resultaat.getMeldingen().iterator().next().getRegel());
    }

    @Test
    public void testMigratieMetFoutieveRedenWijzigingVerblijf() {
        final CorrigeerAdresBericht corrigeerAdresBericht = new CorrigeerAdresBericht();
        final ActieBericht correctieAdresActie = new ActieCorrectieAdresBericht();
        voegStuurgegevenToeAanBericht(corrigeerAdresBericht);

        final PersoonBericht pers =
            PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 123456789, Geslachtsaanduiding.MAN, 19830404,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA.getWaarde(),
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                "Piet", "van de", "Veldhuijsen");
        pers.setAdressen(new ArrayList<PersoonAdresBericht>());

        final PersoonMigratieGroepBericht migratieGroep = new PersoonMigratieGroepBericht();
        migratieGroep.setSoortMigratie(new SoortMigratieAttribuut(SoortMigratie.EMIGRATIE));
        migratieGroep.setLandGebiedMigratieCode(LANDGEBIED_6033.toString());
        migratieGroep.setAangeverMigratieCode(AANGEVER_VERZORGER.getWaarde());
        migratieGroep.setRedenWijzigingMigratieCode(ONJUISTE_WIJZADRES.getWaarde());
        pers.setMigratie(migratieGroep);

        correctieAdresActie.setRootObject(pers);
        corrigeerAdresBericht.getStandaard().setAdministratieveHandeling(new HandelingVerhuizingNaarBuitenlandBericht());
        corrigeerAdresBericht.getAdministratieveHandeling().setPartij(new PartijAttribuut(
            TestPartijBuilder.maker().maak()));
        corrigeerAdresBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        corrigeerAdresBericht.getAdministratieveHandeling().getActies().add(correctieAdresActie);

        final Resultaat resultaat = berichtVerrijkingsStap.voerStapUit(corrigeerAdresBericht, bouwBerichtContext(""));
        assertFalse(resultaat.getMeldingen().isEmpty());
        assertEquals(1, resultaat.getMeldingen().size());
        assertEquals(Regel.BRAL1007, resultaat.getMeldingen().iterator().next().getRegel());
    }

    @Test
    public void testMigratieZonderLandGebied() {
        final CorrigeerAdresBericht corrigeerAdresBericht = new CorrigeerAdresBericht();
        voegStuurgegevenToeAanBericht(corrigeerAdresBericht);
        final ActieBericht correctieAdresActie = new ActieCorrectieAdresBericht();

        final PersoonBericht pers =
            PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 123456789, Geslachtsaanduiding.MAN, 19830404,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA.getWaarde(),
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                "Piet", "van de", "Veldhuijsen");
        pers.setAdressen(new ArrayList<PersoonAdresBericht>());

        final PersoonMigratieGroepBericht migratieGroep = new PersoonMigratieGroepBericht();
        migratieGroep.setSoortMigratie(new SoortMigratieAttribuut(SoortMigratie.EMIGRATIE));
        pers.setMigratie(migratieGroep);

        correctieAdresActie.setRootObject(pers);
        corrigeerAdresBericht.getStandaard().setAdministratieveHandeling(new HandelingVerhuizingNaarBuitenlandBericht());
        corrigeerAdresBericht.getAdministratieveHandeling().setPartij(new PartijAttribuut(
            TestPartijBuilder.maker().maak()));
        corrigeerAdresBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        corrigeerAdresBericht.getAdministratieveHandeling().getActies().add(correctieAdresActie);

        berichtVerrijkingsStap.voerStapUit(corrigeerAdresBericht, bouwBerichtContext(""));
        verify(referentieDataRepository, times(0)).vindLandOpCode(any(LandGebiedCodeAttribuut.class));
        Assert.assertNull(migratieGroep.getLandGebiedMigratie());
    }

    @Test
    public void testMigratieMetNietBestaandLandGebied() {
        final CorrigeerAdresBericht corrigeerAdresBericht = new CorrigeerAdresBericht();
        voegStuurgegevenToeAanBericht(corrigeerAdresBericht);
        final ActieBericht correctieAdresActie = new ActieCorrectieAdresBericht();

        final PersoonBericht pers =
            PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 123456789, Geslachtsaanduiding.MAN, 19830404,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA.getWaarde(),
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                "Piet", "van de", "Veldhuijsen");
        pers.setAdressen(new ArrayList<PersoonAdresBericht>());

        final PersoonMigratieGroepBericht migratieGroep = new PersoonMigratieGroepBericht();
        migratieGroep.setSoortMigratie(new SoortMigratieAttribuut(SoortMigratie.EMIGRATIE));
        // niet bestaande landcode
        migratieGroep.setLandGebiedMigratieCode(LANDGEBIED_1234.toString());
        pers.setMigratie(migratieGroep);

        correctieAdresActie.setRootObject(pers);
        corrigeerAdresBericht.getStandaard().setAdministratieveHandeling(new HandelingVerhuizingNaarBuitenlandBericht());
        corrigeerAdresBericht.getAdministratieveHandeling().setPartij(new PartijAttribuut(
            TestPartijBuilder.maker().maak()));
        corrigeerAdresBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        corrigeerAdresBericht.getAdministratieveHandeling().getActies().add(correctieAdresActie);

        final Resultaat resultaat = berichtVerrijkingsStap.voerStapUit(corrigeerAdresBericht, bouwBerichtContext(""));
        verify(referentieDataRepository, times(1)).vindLandOpCode(any(LandGebiedCodeAttribuut.class));

        assertFalse(resultaat.getMeldingen().isEmpty());
        assertEquals(1, resultaat.getMeldingen().size());
        assertEquals(SoortMelding.FOUT, resultaat.getMeldingen().iterator().next().getSoort());
        assertEquals(Regel.BRAL0181, resultaat.getMeldingen().iterator().next().getRegel());
    }

    @Test
    public void testMigratieMetFoutiefLandGebied() {
        final CorrigeerAdresBericht corrigeerAdresBericht = new CorrigeerAdresBericht();
        voegStuurgegevenToeAanBericht(corrigeerAdresBericht);
        final ActieBericht correctieAdresActie = new ActieCorrectieAdresBericht();

        final PersoonBericht pers =
            PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 123456789, Geslachtsaanduiding.MAN, 19830404,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA.getWaarde(),
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                "Piet", "van de", "Veldhuijsen");

        final PersoonMigratieGroepBericht migratieGroep = new PersoonMigratieGroepBericht();
        // foutieve landcode
        migratieGroep.setLandGebiedMigratieCode("ABC");
        pers.setMigratie(migratieGroep);

        correctieAdresActie.setRootObject(pers);
        corrigeerAdresBericht.getStandaard().setAdministratieveHandeling(new HandelingVerhuizingNaarBuitenlandBericht());
        corrigeerAdresBericht.getAdministratieveHandeling().setPartij(new PartijAttribuut(
            TestPartijBuilder.maker().maak()));
        corrigeerAdresBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        corrigeerAdresBericht.getAdministratieveHandeling().getActies().add(correctieAdresActie);

        final Resultaat resultaat = berichtVerrijkingsStap.voerStapUit(corrigeerAdresBericht, bouwBerichtContext(""));
        verify(referentieDataRepository, times(0)).vindLandOpCode(any(LandGebiedCodeAttribuut.class));

        assertFalse(resultaat.getMeldingen().isEmpty());
        assertEquals(1, resultaat.getMeldingen().size());
        assertEquals(SoortMelding.FOUT, resultaat.getMeldingen().iterator().next().getSoort());
        assertEquals(Regel.ALG0001, resultaat.getMeldingen().iterator().next().getRegel());
    }

    @Test
    public void testMigratieSoortMigratieAfleidingVerhuizingNaarBuitenland() {
        final CorrigeerAdresBericht corrigeerAdresBericht = new CorrigeerAdresBericht();
        voegStuurgegevenToeAanBericht(corrigeerAdresBericht);
        final ActieBericht correctieAdresActie = new ActieCorrectieAdresBericht();

        final PersoonBericht pers =
            PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 123456789, Geslachtsaanduiding.MAN, 19830404,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA.getWaarde(),
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                "Piet", "van de", "Veldhuijsen");
        final PersoonMigratieGroepBericht migratieGroep = new PersoonMigratieGroepBericht();
        // foutieve landcode
        migratieGroep.setLandGebiedMigratieCode("ABC");
        pers.setMigratie(migratieGroep);

        correctieAdresActie.setRootObject(pers);
        corrigeerAdresBericht.getStandaard().setAdministratieveHandeling(new HandelingVerhuizingNaarBuitenlandBericht());
        corrigeerAdresBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        corrigeerAdresBericht.getAdministratieveHandeling().getActies().add(correctieAdresActie);

        Assert.assertNull(pers.getMigratie().getSoortMigratie());

        berichtVerrijkingsStap.voerStapUit(corrigeerAdresBericht, bouwBerichtContext(""));

        assertEquals(SoortMigratie.EMIGRATIE, pers.getMigratie().getSoortMigratie().getWaarde());
    }

    @Test
    public void testMigratieSoortMigratieAfleidingVestigingNederland() {
        final CorrigeerAdresBericht corrigeerAdresBericht = new CorrigeerAdresBericht();
        voegStuurgegevenToeAanBericht(corrigeerAdresBericht);
        final ActieBericht correctieAdresActie = new ActieCorrectieAdresBericht();

        final PersoonBericht pers =
            PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 123456789, Geslachtsaanduiding.MAN, 19830404,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA.getWaarde(),
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                "Piet", "van de", "Veldhuijsen");
        final PersoonMigratieGroepBericht migratieGroep = new PersoonMigratieGroepBericht();
        // foutieve landcode
        migratieGroep.setLandGebiedMigratieCode("ABC");
        pers.setMigratie(migratieGroep);

        correctieAdresActie.setRootObject(pers);
        corrigeerAdresBericht.getStandaard().setAdministratieveHandeling(new HandelingVestigingNietIngeschreveneBericht());
        corrigeerAdresBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        corrigeerAdresBericht.getAdministratieveHandeling().getActies().add(correctieAdresActie);

        Assert.assertNull(pers.getMigratie().getSoortMigratie());

        berichtVerrijkingsStap.voerStapUit(corrigeerAdresBericht, bouwBerichtContext(""));

        assertEquals(SoortMigratie.IMMIGRATIE, pers.getMigratie().getSoortMigratie().getWaarde());
    }

    @Test
    public void testMigratieOnbekendeReferentie() {
        // verifieer dat de verkeerde codes voor reden wijziging adres en aangever adreshouding een foutmelding opleveren
        final CorrigeerAdresBericht corrigeerAdresBericht = new CorrigeerAdresBericht();
        voegStuurgegevenToeAanBericht(corrigeerAdresBericht);
        final ActieBericht correctieAdresActie = new ActieCorrectieAdresBericht();

        final PersoonBericht pers =
            PersoonBuilder.bouwPersoon(SoortPersoon.INGESCHREVENE, 123456789, Geslachtsaanduiding.MAN, 19830404,
                StatischeObjecttypeBuilder.GEMEENTE_BREDA.getWaarde(),
                StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(),
                "Piet", "van de", "Veldhuijsen");
        pers.setAdressen(new ArrayList<PersoonAdresBericht>());
        correctieAdresActie.setRootObject(pers);
        corrigeerAdresBericht.getStandaard().setAdministratieveHandeling(new HandelingVerhuizingNaarBuitenlandBericht());
        corrigeerAdresBericht.getAdministratieveHandeling().setActies(new ArrayList<ActieBericht>());
        corrigeerAdresBericht.getAdministratieveHandeling().getActies().add(correctieAdresActie);

        final PersoonMigratieGroepBericht migratie = new PersoonMigratieGroepBericht();
        migratie.setAangeverMigratieCode("x");
        migratie.setRedenWijzigingMigratieCode("x");
        pers.setMigratie(migratie);

        when(referentieDataRepository.vindAangeverAdreshoudingOpCode(
            any(AangeverCodeAttribuut.class)))
            .thenThrow(
                new OnbekendeReferentieExceptie(
                    OnbekendeReferentieExceptie.ReferentieVeld.AANGEVERADRESHOUDING, "foo",
                    new BijhoudingExceptie())
            );

        when(referentieDataRepository.vindRedenWijzingVerblijfOpCode(
            any(RedenWijzigingVerblijfCodeAttribuut.class)))
            .thenThrow(
                new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.REDENWIJZINGADRES,
                    "foo",
                    new BijhoudingExceptie())
            );

        // voer de opdracht en verwacht dat we nu fouten krijgen.
        final Resultaat resultaat = berichtVerrijkingsStap.voerStapUit(corrigeerAdresBericht, bouwBerichtContext(""));
        assertFalse(resultaat.getMeldingen().isEmpty());
        assertEquals(2, resultaat.getMeldingen().size());
        // test dat we nu alle fouten hebben.
        Iterator<ResultaatMelding> rmIterator = resultaat.getMeldingen().iterator();
        assertEquals(Regel.BRAL1007, rmIterator.next().getRegel());
        assertEquals(Regel.BRAL1008, rmIterator.next().getRegel());
    }

    private void voegStuurgegevenToeAanBericht(final BerichtBericht bericht) {
        final BerichtStuurgegevensGroepBericht stuurgegevens = new BerichtStuurgegevensGroepBericht();
        stuurgegevens.setZendendePartijCode(GEM_34);
        bericht.setStuurgegevens(stuurgegevens);
    }

    // TODO fixen: dit is stukje duplicaatcode, kan bijvoorbeeld naar parent verhuizen

    private void verifieerSpecifiekeMeldingToegevoegd(Regel regel) {
        verifieerSpecifiekeMeldingToegevoegd(regel, 1);
    }

    private void verifieerSpecifiekeMeldingToegevoegd(Regel regel, int aantalKeren) {
        verify(meldingFactory, times(aantalKeren)).maakResultaatMelding(eq(regel), any(BerichtIdentificeerbaar.class), any(DatabaseObject.class));
    }

    private void verifieerWillekeurigMeldingToegevoegd(int aantalKeren) {
        verify(meldingFactory, times(aantalKeren)).maakResultaatMelding(any(Regel.class), any(BerichtIdentificeerbaar.class), any(DatabaseObject.class));
    }

}
