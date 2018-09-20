/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpRelatie;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpAanduidingBijHuisnummerCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpAangeverAdreshoudingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpFunctieAdresCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpLandCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPlaatsCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenOpschortingBijhoudingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenWijzigingAdresCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpReisdocumentAutoriteitVanAfgifte;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpReisdocumentRedenOntbreken;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpReisdocumentSoort;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpVerantwoordelijkeCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpVerblijfsrechtCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpWijzeGebruikGeslachtsnaamCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpAanschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpAdresInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBehandeldAlsNederlanderIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBelemmeringVerstrekkingReisdocumentIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBezitBuitenlandsReisdocumentIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBijhoudingsgemeenteInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBijhoudingsverantwoordelijkheidInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpEuropeseVerkiezingenInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeslachtsnaamcomponentInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpImmigratieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpInschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOnderCurateleIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOpschortingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOverlijdenInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpPersoonskaartInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpReisdocumentInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpStatenloosIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpUitsluitingNederlandsKiesrechtInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVastgesteldNietNederlanderIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVerblijfsrechtInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVoornaamInhoud;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.logging.LogRegel;
import nl.moderniseringgba.migratie.conversie.model.logging.LogSeverity;
import nl.moderniseringgba.migratie.conversie.model.logging.LogType;
import nl.moderniseringgba.migratie.conversie.proces.logging.Logging;
import nl.moderniseringgba.migratie.conversie.validatie.InputValidationException;
import nl.moderniseringgba.migratie.synchronisatie.AbstractDatabaseTest;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.AdellijkeTitel;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Predikaat;
import nl.moderniseringgba.migratie.synchronisatie.domein.logging.entity.BerichtLog;
import nl.moderniseringgba.migratie.synchronisatie.service.BrpDalService;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.LoggingMapper;
import nl.moderniseringgba.migratie.synchronisatie.util.DBUnit.ExpectedAfter;
import nl.moderniseringgba.migratie.synchronisatie.util.DBUnit.InsertBefore;
import nl.moderniseringgba.migratie.synchronisatie.util.DBUnit.NotExpectedAfter;

import org.junit.Test;

public class ServiceIntegratieTest extends AbstractDatabaseTest {

    private static final BrpPlaatsCode PLAATS_CODE_ALMEREBUITEN = new BrpPlaatsCode("Almere");
    private static final BrpNationaliteitCode NATIONALITEIT_CODE_NEDERLAND = new BrpNationaliteitCode(
            Integer.valueOf("0001"));
    private static final BrpLandCode LAND_CODE_DUITSLAND = new BrpLandCode(Integer.valueOf("6029"));
    private static final BrpLandCode LAND_CODE_NEDERLAND = new BrpLandCode(Integer.valueOf("6030"));
    private static final BrpGemeenteCode GEMEENTE_CODE_ALMERE = new BrpGemeenteCode(new BigDecimal("0034"));

    // Man / Vader
    private static final BrpSamengesteldeNaamInhoud MAN_NAAM = new BrpSamengesteldeNaamInhoud(null, "Kees Jan",
            "van", ' ', null, "Mannen", false, false);
    private static final long MAN_BSN = 755443322L;
    private static final long MAN_A_NUMMER = 223344557L;
    private static final BrpDatum MAN_GEBOORTEDATUM = new BrpDatum(19800101);
    private static final BrpGeboorteInhoud MAN_GEBOORTE = new BrpGeboorteInhoud(MAN_GEBOORTEDATUM,
            GEMEENTE_CODE_ALMERE, null, null, null, LAND_CODE_NEDERLAND, null);

    // Vader 2
    private static final BrpSamengesteldeNaamInhoud MAN2_NAAM = new BrpSamengesteldeNaamInhoud(null, "Karel", null,
            null, null, "Vader2", false, false);
    private static final long MAN2_BSN = 756443322L;
    private static final long MAN2_A_NUMMER = 223344657L;
    private static final BrpDatum MAN2_GEBOORTEDATUM = new BrpDatum(19830202);
    private static final BrpGeboorteInhoud MAN2_GEBOORTE = new BrpGeboorteInhoud(MAN2_GEBOORTEDATUM,
            GEMEENTE_CODE_ALMERE, null, null, null, LAND_CODE_NEDERLAND, null);

    // Vrouw / Moeder
    private static final BrpSamengesteldeNaamInhoud VROUW_NAAM = new BrpSamengesteldeNaamInhoud(null, "Marie", null,
            null, null, "Vrouwen", false, false);
    private static final long VROUW_BSN = 855443322L;
    private static final long VROUW_A_NUMMER = 223344558L;
    private static final BrpDatum VROUW_GEBOORTEDATUM = new BrpDatum(19820303);
    private static final BrpGeboorteInhoud VROUW_GEBOORTE = new BrpGeboorteInhoud(VROUW_GEBOORTEDATUM,
            GEMEENTE_CODE_ALMERE, null, null, null, LAND_CODE_NEDERLAND, null);

    // Trouw gegevens
    private static final BrpDatum TROUWDATUM = new BrpDatum(20101010);
    private static final BrpGemeenteCode TROUW_GEMEENTE = new BrpGemeenteCode(new BigDecimal("0162"));
    private static final BrpGemeenteCode TROUW_GEMEENTE2 = new BrpGemeenteCode(new BigDecimal("0163"));

    // Kind 1 gegevens
    private static final BrpSamengesteldeNaamInhoud KIND1_NAAM = new BrpSamengesteldeNaamInhoud(null, "Pietje", null,
            null, null, "Bell", false, false);
    private static final long KIND1_BSN = 855446622L;
    private static final long KIND1_A_NUMMER = 226644558L;
    private static final BrpDatum KIND1_GEBOORTEDATUM = new BrpDatum(20050303);
    private static final BrpGeboorteInhoud KIND1_GEBOORTE = new BrpGeboorteInhoud(KIND1_GEBOORTEDATUM,
            GEMEENTE_CODE_ALMERE, null, null, null, LAND_CODE_NEDERLAND, null);

    // Kind 2 gegevens
    private static final BrpSamengesteldeNaamInhoud KIND2_NAAM = new BrpSamengesteldeNaamInhoud(null, "Kees", null,
            null, null, "Bell", false, false);
    private static final long KIND2_BSN = 888446622L;
    private static final long KIND2_A_NUMMER = 226644888L;
    private static final BrpDatum KIND2_GEBOORTEDATUM = new BrpDatum(20070606);
    private static final BrpGeboorteInhoud KIND2_GEBOORTE = new BrpGeboorteInhoud(KIND2_GEBOORTEDATUM,
            GEMEENTE_CODE_ALMERE, null, null, null, LAND_CODE_NEDERLAND, null);

    @Inject
    private BrpDalService service;

    /**
     * Met deze test wordt zowel de mapping als de opslag via JPA getest middels het opslaan van een goed gevulde
     * BrpPersoonslijst middels de BrpDalService.
     */
    @InsertBefore("/sql/data/dbunitStamgegevens.xml")
    @ExpectedAfter({ "ServiceIntegratieTest.xml", "ServiceIntegratieTestLogging.xml" })
    @Test
    public void testToevoegenBrpPersoonslijstMetLogging() throws InputValidationException {
        final BrpPersoonslijst brpPersoonslijst = maakBrpPersoonslijst();
        final BerichtLog berichtLog =
                maakBerichtLog(BigDecimal.valueOf(brpPersoonslijst.getActueelAdministratienummer()));
        final Persoon persoon = service.persisteerPersoonslijst(brpPersoonslijst);
        new LoggingMapper().mapLogging(maakLogging(), berichtLog, persoon);
        service.persisteerBerichtLog(berichtLog);
    }

    /**
     * Test dat 2 lijsten opslaan resulteert in 2 entries in berichtlog en in dubbele logregels.
     */
    @InsertBefore("/sql/data/dbunitStamgegevens.xml")
    @ExpectedAfter("ServiceIntegratieTestLogging-2.xml")
    @Test
    public void testLoggingMeerdereBerichten() throws InputValidationException {
        final Persoon persoon1 = service.persisteerPersoonslijst(maakBrpPersoonslijst());
        final BerichtLog persoon1Log = maakBerichtLog(persoon1.getAdministratienummer());
        new LoggingMapper().mapLogging(maakLogging(), persoon1Log, persoon1);
        service.persisteerBerichtLog(persoon1Log);

        final Persoon persoon2 = service.persisteerPersoonslijst(maakVrouwPersoonslijst(false));
        final BerichtLog persoon2Log = maakBerichtLog(persoon2.getAdministratienummer());
        new LoggingMapper().mapLogging(maakLogging(), persoon2Log, persoon2);
        service.persisteerBerichtLog(persoon2Log);
    }

    /**
     * Test het opslaan van twee gerelateerde personen waarbij eerst de man wordt opgeslagen en daarna de vrouw. De
     * verwachting is dat de man een relatie heeft met zijn vrouw als niet ingeschrevene omdat ten tijden van opslaan de
     * vrouw nog niet bestond in de brp. Daarnaast wordt verwacht dat de relatie van de vrouw met de man wel een 'echte'
     * relatie in de BRP is.<br/>
     * <ul>
     * <li>Kees Jan <-> Marie</li>
     * </ul>
     * 
     * @throws InputValidationException
     *             als de testdata incorrect is
     */
    @InsertBefore("/sql/data/dbunitStamgegevens.xml")
    @ExpectedAfter("ServiceIntegratieTest-2.xml")
    @NotExpectedAfter("ServiceIntegratieTest-2-not.xml")
    @Test
    public void testToevoegenGerelateerden() throws InputValidationException {
        final BrpPersoonslijst manPersoonslijst = maakManMetVrouwPersoonslijst(TROUW_GEMEENTE, false);
        final BrpPersoonslijst vrouwPersoonslijst = maakVrouwMetManPersoonslijst(TROUW_GEMEENTE, false);

        service.persisteerPersoonslijst(manPersoonslijst);
        service.persisteerPersoonslijst(vrouwPersoonslijst);
    }

    /**
     * Na het opslaan van het huwelijk mono huwelijk wordt het ontkend door de man:<br/>
     * <ul>
     * <li>Kees Jan <-> Marie</li>
     * <li>Kees Jan <- Marie</li>
     * </ul>
     * 
     * @throws InputValidationException
     *             als de testdata incorrect is
     */
    @InsertBefore("/sql/data/dbunitStamgegevens.xml")
    @ExpectedAfter("ServiceIntegratieTest-3.xml")
    @Test
    public void testOntkenningVanRelatie() throws InputValidationException {
        final BrpPersoonslijst manPersoonslijst = maakManMetVrouwPersoonslijst(TROUW_GEMEENTE, false);
        final BrpPersoonslijst vrouwPersoonslijst = maakVrouwMetManPersoonslijst(TROUW_GEMEENTE, false);

        service.persisteerPersoonslijst(manPersoonslijst);
        service.persisteerPersoonslijst(vrouwPersoonslijst);

        // ontken huwelijk vanuit man
        final List<BrpRelatie> relaties = manPersoonslijst.getRelaties(BrpSoortRelatieCode.HUWELIJK);
        assertEquals(1, relaties.size());
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder(manPersoonslijst);
        builder.relaties(Collections.<BrpRelatie>emptyList());
        service.persisteerPersoonslijst(builder.build());
    }

    /**
     * Na het opslaan van het huwelijk mono huwelijk wordt het ontkend door de man en daarna nogmaals erkend door de
     * vrouw:<br/>
     * <ul>
     * <li>Kees Jan <-> Marie</li>
     * <li>Kees Jan <- Marie</li>
     * </ul>
     * 
     * @throws InputValidationException
     *             als de testdata incorrect is
     */
    @InsertBefore("/sql/data/dbunitStamgegevens.xml")
    @ExpectedAfter("ServiceIntegratieTest-4.xml")
    @NotExpectedAfter("ServiceIntegratieTest-4-not.xml")
    @Test
    public void testOntkenningEnBevestigingDoorVrouwVanRelatie() throws InputValidationException {
        final BrpPersoonslijst manPersoonslijst = maakManMetVrouwPersoonslijst(TROUW_GEMEENTE, false);
        final BrpPersoonslijst vrouwPersoonslijst = maakVrouwMetManPersoonslijst(TROUW_GEMEENTE, false);

        service.persisteerPersoonslijst(manPersoonslijst);
        service.persisteerPersoonslijst(vrouwPersoonslijst);

        // ontken huwelijk vanuit man
        final List<BrpRelatie> relaties = manPersoonslijst.getRelaties(BrpSoortRelatieCode.HUWELIJK);
        assertEquals(1, relaties.size());
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder(manPersoonslijst);
        builder.relaties(Collections.<BrpRelatie>emptyList());
        service.persisteerPersoonslijst(builder.build());

        service.persisteerPersoonslijst(vrouwPersoonslijst);
    }

    /**
     * Na het opslaan van het huwelijk mono huwelijk wordt het ontkend door de man en daarna nogmaals ontkend door de
     * man:<br/>
     * <ul>
     * <li>Kees Jan <-> Marie</li>
     * <li>Kees Jan <- Marie</li>
     * </ul>
     * 
     * @throws InputValidationException
     *             als de testdata incorrect is
     */
    @InsertBefore("/sql/data/dbunitStamgegevens.xml")
    @ExpectedAfter("ServiceIntegratieTest-6.xml")
    @NotExpectedAfter("ServiceIntegratieTest-6-not.xml")
    @Test
    public void testOntkenningEnBevestigingDoorManVanRelatie() throws InputValidationException {
        final BrpPersoonslijst manPersoonslijst = maakManMetVrouwPersoonslijst(TROUW_GEMEENTE, false);
        final BrpPersoonslijst vrouwPersoonslijst = maakVrouwMetManPersoonslijst(TROUW_GEMEENTE, false);

        service.persisteerPersoonslijst(manPersoonslijst);
        service.persisteerPersoonslijst(vrouwPersoonslijst);

        // ontken huwelijk vanuit man
        final List<BrpRelatie> relaties = manPersoonslijst.getRelaties(BrpSoortRelatieCode.HUWELIJK);
        assertEquals(1, relaties.size());
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder(manPersoonslijst);
        builder.relaties(Collections.<BrpRelatie>emptyList());
        final BrpPersoonslijst manPersoonslijstOntkenning = builder.build();
        service.persisteerPersoonslijst(manPersoonslijstOntkenning);

        service.persisteerPersoonslijst(manPersoonslijstOntkenning);
    }

    /**
     * Na het opslaan van het huwelijk mono huwelijk wordt het ontkend door de man, waarna het in de volgende sync weer
     * wordt erkend door de man:<br/>
     * <ul>
     * <li>Kees Jan <-> Marie</li>
     * <li>Kees Jan <- Marie</li>
     * <li>Kees Jan <-> Marie</li>
     * </ul>
     * 
     * @throws InputValidationException
     *             als de testdata incorrect is
     */
    @InsertBefore("/sql/data/dbunitStamgegevens.xml")
    @ExpectedAfter("ServiceIntegratieTest-5.xml")
    @NotExpectedAfter("ServiceIntegratieTest-5-not.xml")
    @Test
    public void testVerwijderenMultiRealiteitRegelVanRelatie() throws InputValidationException {
        final BrpPersoonslijst manPersoonslijst = maakManMetVrouwPersoonslijst(TROUW_GEMEENTE, false);
        final BrpPersoonslijst vrouwPersoonslijst = maakVrouwMetManPersoonslijst(TROUW_GEMEENTE, false);

        service.persisteerPersoonslijst(manPersoonslijst);
        service.persisteerPersoonslijst(vrouwPersoonslijst);

        // ontken huwelijk vanuit man
        final List<BrpRelatie> relaties = manPersoonslijst.getRelaties(BrpSoortRelatieCode.HUWELIJK);
        assertEquals(1, relaties.size());
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder(manPersoonslijst);
        builder.relaties(Collections.<BrpRelatie>emptyList());
        service.persisteerPersoonslijst(builder.build());

        service.persisteerPersoonslijst(manPersoonslijst);
    }

    /**
     * Na het synchroniseren van de man en vrouw - waardoor een monorealiteit realtie is ontstaan - wordt vanuit de man
     * de relatiegegevens gewijzigd en vervolgens gesynchroniseerd.
     * <ul>
     * <li>Kees Jan <-1-> Marie</li>
     * <li>Kees Jan <-1- Marie</li>
     * <li>Kees Jan -2-> Marie</li>
     * </ul>
     * 
     * @throws InputValidationException
     *             als de testdata incorrect is
     */
    @InsertBefore("/sql/data/dbunitStamgegevens.xml")
    @ExpectedAfter("ServiceIntegratieTest-7.xml")
    @NotExpectedAfter("ServiceIntegratieTest-7-not.xml")
    @Test
    public void testWijzigGegevensRelatie() throws InputValidationException {
        final BrpPersoonslijst manPersoonslijst = maakManMetVrouwPersoonslijst(TROUW_GEMEENTE, false);
        final BrpPersoonslijst vrouwPersoonslijst = maakVrouwMetManPersoonslijst(TROUW_GEMEENTE, false);

        service.persisteerPersoonslijst(manPersoonslijst);
        service.persisteerPersoonslijst(vrouwPersoonslijst);

        final BrpPersoonslijst manPersoonslijst2 = maakManMetVrouwPersoonslijst(TROUW_GEMEENTE2, false);
        service.persisteerPersoonslijst(manPersoonslijst2);
    }

    /**
     * Na het synchroniseren van de man en vrouw - waardoor een monorealiteit realtie is ontstaan - wordt vanuit de man
     * de relatiegegevens gewijzigd en vervolgens gesynchroniseerd. Hierdoor ontstaan twee relaties met MR. Hierna wordt
     * vervolgens de relatie van de vrouw gewijzigt zodat deze weer gelijk is aan de nieuwe relatie vanuit de man
     * gezien. Hierdoor wordt de MR op relatie 2 opgeheven en ontstaat dubbele MR op relatie 1. Hierdoor wordt relatie 1
     * verwijderd.
     * 
     * <ul>
     * <li>Kees Jan <-1-> Marie</li>
     * <li>===</li>
     * <li>Kees Jan <-1- Marie</li>
     * <li>Kees Jan -2-> Marie</li>
     * <li>===</li>
     * <li>Kees Jan <-2-> Marie</li>
     * </ul>
     * 
     * @throws InputValidationException
     *             als de testdata incorrect is
     */
    @InsertBefore("/sql/data/dbunitStamgegevens.xml")
    @ExpectedAfter("ServiceIntegratieTest-8.xml")
    @NotExpectedAfter("ServiceIntegratieTest-8-not.xml")
    @Test
    public void testWijzigGegevensBijBeidePartnersRelatie() throws InputValidationException {
        final BrpPersoonslijst manPersoonslijst = maakManMetVrouwPersoonslijst(TROUW_GEMEENTE, false);
        final BrpPersoonslijst vrouwPersoonslijst = maakVrouwMetManPersoonslijst(TROUW_GEMEENTE, false);

        service.persisteerPersoonslijst(manPersoonslijst);
        service.persisteerPersoonslijst(vrouwPersoonslijst);

        final BrpPersoonslijst manPersoonslijst2 = maakManMetVrouwPersoonslijst(TROUW_GEMEENTE2, false);
        service.persisteerPersoonslijst(manPersoonslijst2);
        final BrpPersoonslijst vrouwPersoonslijst2 = maakVrouwMetManPersoonslijst(TROUW_GEMEENTE2, false);
        service.persisteerPersoonslijst(vrouwPersoonslijst2);
    }

    /**
     * Na het opslaan van de man is er een ingeschreven man in de BRP opgeslagen met een relatie met een NI vrouw.
     * Hierna wordt nogmaals de man opgeslagen met iets gewijzigde relatie gegevens. Dit heeft tot gevolg dat de oude
     * relatie wordt verwijderd en een nieuwe relatie wordt aangemaakt met een nieuwe niet-ingeschreven persoon.
     * 
     * <ul>
     * <li>Kees Jan <-1-> Marie (NI)</li>
     * <li>===</li>
     * <li>Kees Jan <-2-> Marie (NI)</li>
     * </ul>
     * 
     * @throws InputValidationException
     *             als de testdata incorrect is
     */
    @InsertBefore("/sql/data/dbunitStamgegevens.xml")
    @ExpectedAfter("ServiceIntegratieTest-9.xml")
    @Test
    public void testWijzigGegevensBijMan() throws InputValidationException {
        final BrpPersoonslijst manPersoonslijst = maakManMetVrouwPersoonslijst(TROUW_GEMEENTE, false);
        final BrpPersoonslijst manPersoonslijst2 = maakManMetVrouwPersoonslijst(TROUW_GEMEENTE, true);

        service.persisteerPersoonslijst(manPersoonslijst);
        service.persisteerPersoonslijst(manPersoonslijst2);
    }

    /**
     * Na het opslaan van de vader is er een ingeschreven man in de BRP opgeslagen met een relatie met een NI kind.
     * 
     * <ul>
     * <li>Kees Jan -1-> Pietje Bell (NI)</li>
     * </ul>
     * 
     * @throws InputValidationException
     *             als de testdata incorrect is
     */
    @InsertBefore("/sql/data/dbunitStamgegevens.xml")
    @ExpectedAfter("ServiceIntegratieTest-10.xml")
    @Test
    public void testManMetKind() throws InputValidationException {
        final BrpPersoonslijst vaderPersoonslijst = maakVaderMetKindPersoonslijst(false);

        service.persisteerPersoonslijst(vaderPersoonslijst);
    }

    /**
     * Na het opslaan van de vader is er een ingeschreven man in de BRP opgeslagen met een relatie met een NI kind.
     * Vervolgens wordt de vader nogmaals opgeslagen en moet er niets aan de relatie zijn gewijzgid.
     * 
     * <ul>
     * <li>Kees Jan -1-> Pietje Bell (NI)</li>
     * <li>==========</li>
     * <li>Kees Jan -1-> Pietje Bell (NI)</li>
     * </ul>
     * 
     * @throws InputValidationException
     *             als de testdata incorrect is
     */
    @InsertBefore("/sql/data/dbunitStamgegevens.xml")
    @ExpectedAfter("ServiceIntegratieTest-11.xml")
    @Test
    public void testManMetKindDubbel() throws InputValidationException {
        final BrpPersoonslijst vaderPersoonslijst = maakVaderMetKindPersoonslijst(false);

        service.persisteerPersoonslijst(vaderPersoonslijst);
        service.persisteerPersoonslijst(vaderPersoonslijst);
    }

    /**
     * Na het opslaan van de vader is er een ingeschreven man in de BRP opgeslagen met een relatie met een NI kind.
     * Vervolgens wordt een relatie met een tweede kind toegevoegd en opgeslagen in de BRP.
     * 
     * <ul>
     * <li>Kees Jan -1-> Pietje Bell (NI)</li>
     * <li>===</li>
     * <li>Kees Jan -1-> Pietje Bell (NI)</li>
     * <li>Kees Jan -2-> Kees Bell (NI)</li>
     * </ul>
     * 
     * @throws InputValidationException
     *             als de testdata incorrect is
     */
    @InsertBefore("/sql/data/dbunitStamgegevens.xml")
    @ExpectedAfter("ServiceIntegratieTest-12.xml")
    @Test
    public void testManMetTweedeKind() throws InputValidationException {
        final BrpPersoonslijst vaderPersoonslijst = maakVaderMetKindPersoonslijst(false);
        final BrpPersoonslijst vaderPersoonslijst2 = maakVaderMetTweeKinderenPersoonslijst(false);

        service.persisteerPersoonslijst(vaderPersoonslijst);
        service.persisteerPersoonslijst(vaderPersoonslijst2);
    }

    /**
     * Na het opslaan van de vader is er een ingeschreven man in de BRP opgeslagen met twee relaties met twee NI
     * kinderen. Vervolgens wordt de relatie met het tweede kind verwijderd en opgeslagen in de BRP.
     * 
     * <ul>
     * <li>Kees Jan -1-> Pietje Bell (NI)</li>
     * <li>Kees Jan -2-> Kees Bell (NI)</li>
     * <li>===</li>
     * <li>Kees Jan -1-> Pietje Bell (NI)</li>
     * </ul>
     * 
     * @throws InputValidationException
     *             als de testdata incorrect is
     */
    @InsertBefore("/sql/data/dbunitStamgegevens.xml")
    @ExpectedAfter("ServiceIntegratieTest-13.xml")
    @NotExpectedAfter("ServiceIntegratieTest-13-not.xml")
    @Test
    public void testManVerwijderTweedeKind() throws InputValidationException {
        final BrpPersoonslijst vaderPersoonslijst = maakVaderMetTweeKinderenPersoonslijst(false);
        final BrpPersoonslijst vaderPersoonslijst2 = maakVaderMetKindPersoonslijst(false);

        service.persisteerPersoonslijst(vaderPersoonslijst);
        service.persisteerPersoonslijst(vaderPersoonslijst2);

    }

    /**
     * Na het opslaan van het kind is er een ingeschreven kind in de BRP opgeslagen met een relatie met twee
     * niet-ingeschreven ouders.
     * 
     * <ul>
     * <li>Pietje Bell -1> Kees Jan (NI)</li>
     * <li>Pietje Bell -1> Marie (NI)</li>
     * </ul>
     * 
     * @throws InputValidationException
     *             als de testdata incorrect is
     */
    @InsertBefore("/sql/data/dbunitStamgegevens.xml")
    @ExpectedAfter("ServiceIntegratieTest-14.xml")
    @Test
    public void testKindMetOuders() throws InputValidationException {
        final BrpPersoonslijst kindPersoonslijst = maakKindMetVaderEnMoederPersoonslijst(false, false);
        service.persisteerPersoonslijst(kindPersoonslijst);
    }

    /**
     * Na het opslaan van het kind is er een ingeschreven kind in de BRP opgeslagen met een relatie met twee
     * niet-ingeschreven ouders. Vervolgens wordt dezelfde persoonslijst nogmaal opgeslagen.
     * 
     * <ul>
     * <li>Pietje Bell -1> Kees Jan (NI)</li>
     * <li>Pietje Bell -1> Marie (NI)</li>
     * <li>=============</li>
     * <li>Pietje Bell -1> Kees Jan (NI)</li>
     * <li>Pietje Bell -1> Marie (NI)</li>
     * </ul>
     * 
     * @throws InputValidationException
     *             als de testdata incorrect is
     */
    @InsertBefore("/sql/data/dbunitStamgegevens.xml")
    @ExpectedAfter("ServiceIntegratieTest-15.xml")
    @NotExpectedAfter("ServiceIntegratieTest-15-not.xml")
    @Test
    public void testKindMetOudersDubbelOpslaan() throws InputValidationException {
        final BrpPersoonslijst kindPersoonslijst = maakKindMetVaderEnMoederPersoonslijst(false, false);
        service.persisteerPersoonslijst(kindPersoonslijst);
        service.persisteerPersoonslijst(kindPersoonslijst);
    }

    /**
     * Na het opslaan van het kind is er een ingeschreven kind in de BRP opgeslagen met een relatie met twee
     * niet-ingeschreven ouders. Vervolgens wordt dezelfde persoonslijst nogmaal opgeslagen.
     * 
     * <ul>
     * <li>Pietje Bell -1> Kees Jan (NI)</li>
     * <li>Pietje Bell -1> Marie (NI)</li>
     * <li>=============</li>
     * <li>Pietje Bell -1> Kees Jan (NI)</li>
     * </ul>
     * 
     * @throws InputValidationException
     *             als de testdata incorrect is
     */
    @InsertBefore("/sql/data/dbunitStamgegevens.xml")
    @ExpectedAfter("ServiceIntegratieTest-16.xml")
    @NotExpectedAfter("ServiceIntegratieTest-16-not.xml")
    @Test
    public void testVerwijderenOuder() throws InputValidationException {
        final BrpPersoonslijst kindPersoonslijst = maakKindMetVaderEnMoederPersoonslijst(false, false);
        final BrpPersoonslijst kindPersoonslijst2 = maakKindMetVaderPersoonslijst(false, false);
        service.persisteerPersoonslijst(kindPersoonslijst);
        service.persisteerPersoonslijst(kindPersoonslijst2);
    }

    /**
     * Na het opslaan van het kind is er een ingeschreven kind in de BRP opgeslagen met een relatie met twee
     * niet-ingeschreven ouders. Vervolgens wordt de ouder 1 opgeslagen en wordt dus de vader ook een ingeschrevene en
     * wordt de relatie gemerged.
     * 
     * <ul>
     * <li>Pietje Bell -1> Kees Jan (NI)</li>
     * <li>Pietje Bell -1> Marie (NI)</li>
     * <li>=============</li>
     * <li>Pietje Bell <-1> Kees Jan (I)</li>
     * <li>Pietje Bell -1> Marie (NI)</li>
     * </ul>
     * 
     * @throws InputValidationException
     *             als de testdata incorrect is
     */
    @InsertBefore("/sql/data/dbunitStamgegevens.xml")
    @ExpectedAfter("ServiceIntegratieTest-17.xml")
    @NotExpectedAfter("ServiceIntegratieTest-17-not.xml")
    @Test
    public void testOpslaanKindEnBevestigingVader() throws InputValidationException {
        final BrpPersoonslijst kindPersoonslijst = maakKindMetVaderEnMoederPersoonslijst(false, false);
        final BrpPersoonslijst vaderPersoonslijst = maakVaderMetKindPersoonslijst(false);

        service.persisteerPersoonslijst(kindPersoonslijst);
        service.persisteerPersoonslijst(vaderPersoonslijst);
    }

    /**
     * Na het opslaan van het kind is er een ingeschreven kind in de BRP opgeslagen met een relatie met twee
     * niet-ingeschreven ouders. Vervolgens wordt de ouder 1 opgeslagen en wordt dus de vader ook een ingeschrevene en
     * wordt de relatie gemerged.
     * 
     * <ul>
     * <li>Pietje Bell -1> Kees Jan (NI)</li>
     * <li>Pietje Bell -1> Marie (NI)</li>
     * <li>=============</li>
     * <li>Pietje Bell <-1> Kees Jan (I)</li>
     * <li>Pietje Bell -1> Marie (NI)</li>
     * <li>=============</li>
     * <li>Pietje Bell <-1> Kees Jan (I)</li>
     * <li>Pietje Bell <-1> Marie (I)</li>
     * </ul>
     * 
     * @throws InputValidationException
     *             als de testdata incorrect is
     */
    @InsertBefore("/sql/data/dbunitStamgegevens.xml")
    @ExpectedAfter("ServiceIntegratieTest-18.xml")
    @NotExpectedAfter("ServiceIntegratieTest-18-not.xml")
    @Test
    public void testOpslaanKindEnBevestigingVaderMoeder() throws InputValidationException {
        final BrpPersoonslijst kindPersoonslijst = maakKindMetVaderEnMoederPersoonslijst(false, false);
        final BrpPersoonslijst vaderPersoonslijst = maakVaderMetKindPersoonslijst(false);
        final BrpPersoonslijst moederPersoonslijst = maakMoederMetKindPersoonslijst(false);

        service.persisteerPersoonslijst(kindPersoonslijst);
        service.persisteerPersoonslijst(vaderPersoonslijst);
        service.persisteerPersoonslijst(moederPersoonslijst);
    }

    /**
     * Na het opslaan van het kind is er een ingeschreven kind in de BRP opgeslagen met een relatie met twee
     * niet-ingeschreven ouders. Vervolgens wordt de ouder 1 opgeslagen en wordt dus de vader ook een ingeschrevene en
     * wordt de relatie gemerged.
     * 
     * <ul>
     * <li>Pietje Bell -1> Kees Jan (NI)</li>
     * <li>Pietje Bell -1> Marie (NI)</li>
     * <li>=============</li>
     * <li>Pietje Bell -1> Kees Jan (I)</li>
     * <li>Pietje Bell -1> Marie (NI)</li>
     * </ul>
     * 
     * @throws InputValidationException
     *             als de testdata incorrect is
     */
    @InsertBefore("/sql/data/dbunitStamgegevens.xml")
    @ExpectedAfter("ServiceIntegratieTest-19.xml")
    @NotExpectedAfter("ServiceIntegratieTest-19-not.xml")
    @Test
    public void testOpslaanKindEnOntkenningVader() throws InputValidationException {
        final BrpPersoonslijst kindPersoonslijst = maakKindMetVaderEnMoederPersoonslijst(false, false);
        final BrpPersoonslijst vaderPersoonslijst = maakVaderPersoonslijst(false);

        service.persisteerPersoonslijst(kindPersoonslijst);
        service.persisteerPersoonslijst(vaderPersoonslijst);
    }

    /**
     * Na het opslaan van het kind is er een ingeschreven kind in de BRP opgeslagen met een relatie met twee
     * niet-ingeschreven ouders. Vervolgens wordt de ouder 1 opgeslagen en wordt dus de vader ook een ingeschrevene en
     * wordt de relatie gemerged. Eerst wordt de vader zonder kind opgeslagen - wat MR opleverd - en daarna met relatie
     * waardoor de MR weer wordt verwijderd.
     * 
     * <ul>
     * <li>Pietje Bell -1> Kees Jan (NI)</li>
     * <li>Pietje Bell -1> Marie (NI)</li>
     * <li>=============</li>
     * <li>Pietje Bell -1> Kees Jan (I)</li>
     * <li>Pietje Bell -1> Marie (NI)</li>
     * </ul>
     * 
     * @throws InputValidationException
     *             als de testdata incorrect is
     */
    @InsertBefore("/sql/data/dbunitStamgegevens.xml")
    @ExpectedAfter("ServiceIntegratieTest-20.xml")
    @NotExpectedAfter("ServiceIntegratieTest-20-not.xml")
    @Test
    public void testOpslaanKindEnOntkenningGevolgdDoorBevestigingVader() throws InputValidationException {
        final BrpPersoonslijst kindPersoonslijst = maakKindMetVaderEnMoederPersoonslijst(false, false);
        final BrpPersoonslijst vaderPersoonslijst = maakVaderPersoonslijst(false);
        final BrpPersoonslijst vaderPersoonslijst2 = maakVaderMetKindPersoonslijst(false);

        service.persisteerPersoonslijst(kindPersoonslijst);
        service.persisteerPersoonslijst(vaderPersoonslijst);
        service.persisteerPersoonslijst(vaderPersoonslijst2);
    }

    /**
     * Sla eerst de de PL van de vader op met een relatie met het kind. Vervolgens wordt de PL van het kind opgeslagen
     * die alleen een relatie met de moeder heeft. De verwachting is dat er 1 familie rechtelijke betrekking wordt
     * aangemaakt waarbij het kind de betrokkenheid met de vader ontkend.
     * 
     * <ul>
     * <li>Kees Jan -1-> Pietje Bell (NI)</li>
     * <li>=============</li>
     * <li>Pietje Bell (I) <-1 Kees Jan</li>
     * <li>Pietje Bell (I) -1> Marie</li>
     * <li>=============</li>
     * </ul>
     * 
     * @throws InputValidationException
     *             als de testdata incorrect is
     */
    @InsertBefore("/sql/data/dbunitStamgegevens.xml")
    @ExpectedAfter("ServiceIntegratieTest-21.xml")
    @NotExpectedAfter("ServiceIntegratieTest-21-not.xml")
    @Test
    public void testVaderMetKindEnKindMetMoeder() throws InputValidationException {
        final BrpPersoonslijst vaderPersoonslijst = maakVaderMetKindPersoonslijst(false);
        final BrpPersoonslijst kindPersoonslijst = maakKindMetMoederPersoonslijst(false, false);

        service.persisteerPersoonslijst(vaderPersoonslijst);
        service.persisteerPersoonslijst(kindPersoonslijst);
    }

    /**
     * Sla eerst de de PL van de vader op met een relatie met het kind. Vervolgens wordt de PL van het kind opgeslagen
     * die alleen een relatie met de moeder heeft. De verwachting is dat er 1 familie rechtelijke betrekking wordt
     * aangemaakt waarbij het kind de betrokkenheid met de vader ontkend. Hierna wordt het kind nogmaal opgeslagen waar
     * het kind beide ouders erkend, hierdoor wordt de multireatiteit weer opgegeven.
     * 
     * <ul>
     * <li>Kees Jan -1-> Pietje Bell (NI)</li>
     * <li>=============</li>
     * <li>Pietje Bell (I) <-1 Kees Jan (I)</li>
     * <li>Pietje Bell (I) -1> Marie (NI)</li>
     * <li>=============</li>
     * <li>Pietje Bell (I) <-1-> Kees Jan (I)</li>
     * <li>Pietje Bell (I) -1> Marie (NI)</li>
     * </ul>
     * 
     * @throws InputValidationException
     *             als de testdata incorrect is
     */
    @InsertBefore("/sql/data/dbunitStamgegevens.xml")
    @ExpectedAfter("ServiceIntegratieTest-22.xml")
    @NotExpectedAfter("ServiceIntegratieTest-22-not.xml")
    @Test
    public void testVaderMetKindEnKindMetVaderEnMoeder() throws InputValidationException {
        final BrpPersoonslijst vaderPersoonslijst = maakVaderMetKindPersoonslijst(false);
        final BrpPersoonslijst kindPersoonslijst = maakKindMetMoederPersoonslijst(false, false);
        final BrpPersoonslijst kindPersoonslijst2 = maakKindMetVaderEnMoederPersoonslijst(false, false);

        service.persisteerPersoonslijst(vaderPersoonslijst);
        service.persisteerPersoonslijst(kindPersoonslijst);
        service.persisteerPersoonslijst(kindPersoonslijst2);
    }

    /**
     * Na het opslaan van het kind is er een ingeschreven kind in de BRP opgeslagen met een relatie met twee
     * niet-ingeschreven ouders. Daarna wordt de historie van de betrokkenheid van de vader gewijzigd.
     * 
     * <ul>
     * <li>Pietje Bell -1> Kees Jan (NI)</li>
     * <li>Pietje Bell -1> Marie (NI)</li>
     * <li>==========================</li>
     * <li>Pietje Bell -1> Kees Jan (NI)</li>
     * <li>Pietje Bell -1> Marie (NI)</li>
     * </ul>
     * 
     * @throws InputValidationException
     *             als de testdata incorrect is
     */
    @InsertBefore("/sql/data/dbunitStamgegevens.xml")
    @ExpectedAfter("ServiceIntegratieTest-23.xml")
    @NotExpectedAfter("ServiceIntegratieTest-23-not.xml")
    @Test
    public void testKindMetOudersEnWijzigHistorieVader() throws InputValidationException {
        final BrpPersoonslijst kindPersoonslijst = maakKindMetVaderEnMoederPersoonslijst(false, false);
        final BrpPersoonslijst kindPersoonslijst2 = maakKindMetVaderEnMoederPersoonslijst(false, true);
        service.persisteerPersoonslijst(kindPersoonslijst);
        service.persisteerPersoonslijst(kindPersoonslijst2);
    }

    /**
     * Na het opslaan van het kind is er een ingeschreven kind in de BRP opgeslagen met een relatie met twee
     * niet-ingeschreven ouders. Daarna wordt de historie van de betrokkenheid van de vader verwijderd.
     * 
     * <ul>
     * <li>Pietje Bell -1> Kees Jan (NI)</li>
     * <li>Pietje Bell -1> Marie (NI)</li>
     * <li>==========================</li>
     * <li>Pietje Bell -1> Kees Jan (NI)</li>
     * <li>Pietje Bell -1> Marie (NI)</li>
     * </ul>
     * 
     * @throws InputValidationException
     *             als de testdata incorrect is
     */
    @InsertBefore("/sql/data/dbunitStamgegevens.xml")
    @ExpectedAfter("ServiceIntegratieTest-24.xml")
    @NotExpectedAfter("ServiceIntegratieTest-24-not.xml")
    @Test
    public void testKindMetOudersEnVerwijderHistorieVader() throws InputValidationException {
        final BrpPersoonslijst kindPersoonslijst = maakKindMetVaderEnMoederPersoonslijst(false, true);
        final BrpPersoonslijst kindPersoonslijst2 = maakKindMetVaderEnMoederPersoonslijst(false, false);
        service.persisteerPersoonslijst(kindPersoonslijst);
        service.persisteerPersoonslijst(kindPersoonslijst2);
    }

    /**
     * Na het opslaan van de vader en de moeder die elke een relatie hebben met hetzelfde kind, bestaan de ouders als
     * ingeschrevenen en het kind twee keer als niet ingeschrevene in de database. Vervolgens wordt de PL van het kind
     * opgeslagen. Hierdoor ontstaan 3 ingeschreven personen die samen 1 familierechtelijke betrekking met elkaar
     * aangaan. De overbodige niet-ingeschreven personen , relaties en betrokkenen moeten zijn verwijderd.
     * 
     * <ul>
     * <li>Kees Jan (I) -1> Pietje Bell (NI)</li>
     * <li>==========================</li>
     * <li>Marie (I) -2> Pietje Bell (NI)</li>
     * <li>==========================</li>
     * <li>Pietje Bell (I) <-1-> Kees Jan (I)</li>
     * <li>Pietje Bell (I) <-1-> Marie (I)</li>
     * </ul>
     * 
     * @throws InputValidationException
     *             als de testdata incorrect is
     */
    @InsertBefore("/sql/data/dbunitStamgegevens.xml")
    @ExpectedAfter("ServiceIntegratieTest-25.xml")
    @NotExpectedAfter("ServiceIntegratieTest-25-not.xml")
    @Test
    public void testMergenNietIngeschrevenKinderen() throws InputValidationException {
        final BrpPersoonslijst vaderPersoonslijst = maakVaderMetKindPersoonslijst(false);
        final BrpPersoonslijst moederPersoonslijst = maakMoederMetKindPersoonslijst(false);
        final BrpPersoonslijst kindPersoonslijst = maakKindMetVaderEnMoederPersoonslijst(false, false);

        service.persisteerPersoonslijst(vaderPersoonslijst);
        service.persisteerPersoonslijst(moederPersoonslijst);
        service.persisteerPersoonslijst(kindPersoonslijst);
    }

    /**
     * Na het opslaan van het kind is er een ingeschreven kind in de BRP opgeslagen met een relatie met twee
     * niet-ingeschreven ouders. Vervolgens wordt de ouder 1 opgeslagen en wordt dus de vader ook een ingeschrevene en
     * wordt de relatie gemerged. Doordat de vader de relatie ontkend is er MR op de betrokkenheid van de vader.
     * <p/>
     * Het dubbel opslaan van de vader heeft tot gevolg dat de bestaande MR wordt overgenomen. Het doel van deze test.
     * <ul>
     * <li>Pietje Bell -1> Kees Jan (NI)</li>
     * <li>Pietje Bell -1> Marie (NI)</li>
     * <li>=============</li>
     * <li>Pietje Bell -1> Kees Jan (I)</li>
     * <li>Pietje Bell -1> Marie (NI)</li>
     * </ul>
     * 
     * @throws InputValidationException
     *             als de testdata incorrect is
     */
    @InsertBefore("/sql/data/dbunitStamgegevens.xml")
    @ExpectedAfter("ServiceIntegratieTest-26.xml")
    @NotExpectedAfter("ServiceIntegratieTest-26-not.xml")
    @Test
    public void testOpslaanKindEnDubbelOntkenningVader() throws InputValidationException {
        final BrpPersoonslijst kindPersoonslijst = maakKindMetVaderEnMoederPersoonslijst(false, false);
        final BrpPersoonslijst vaderPersoonslijst = maakVaderPersoonslijst(false);

        service.persisteerPersoonslijst(kindPersoonslijst);
        service.persisteerPersoonslijst(vaderPersoonslijst);
        service.persisteerPersoonslijst(vaderPersoonslijst);
    }

    /**
     * Na het opslaan van het kind is er een ingeschreven kind in de BRP opgeslagen met een relatie met twee
     * niet-ingeschreven ouders. Vervolgens wordt de moeder opgeslagen waardoor dit een ingeschrevene wordt, maar de
     * moeder ontkend het kind. Vervolgens wordt het kind nogmaals opgeslagen waarbij het kind de moeder ook ontkend. De
     * relatie met de moeder moet dan zijn verwijderd.
     * 
     * <ul>
     * <li>Pietje Bell -1> Kees Jan (NI)</li>
     * <li>Pietje Bell -1> Marie (NI)</li>
     * <li>=============</li>
     * <li>Pietje Bell -1> Kees Jan (NI)</li>
     * </ul>
     * 
     * @throws InputValidationException
     *             als de testdata incorrect is
     */
    @InsertBefore("/sql/data/dbunitStamgegevens.xml")
    @ExpectedAfter("ServiceIntegratieTest-27.xml")
    @NotExpectedAfter("ServiceIntegratieTest-27-not.xml")
    @Test
    public void testVerwijderenIngeschrevenOuder() throws InputValidationException {
        final BrpPersoonslijst kindPersoonslijst = maakKindMetVaderEnMoederPersoonslijst(false, false);
        final BrpPersoonslijst moederPersoonslijst = maakVrouwPersoonslijst(false);
        final BrpPersoonslijst kindPersoonslijst2 = maakKindMetVaderPersoonslijst(false, false);

        service.persisteerPersoonslijst(kindPersoonslijst);
        service.persisteerPersoonslijst(moederPersoonslijst);
        service.persisteerPersoonslijst(kindPersoonslijst2);
    }

    /**
     * Test het opslaan van een kind met een punt ouder.
     * 
     * <ul>
     * <li>Pietje Bell -1> ?</li>
     * </ul>
     * 
     * @throws InputValidationException
     *             als de testdata incorrect is
     */
    @InsertBefore("/sql/data/dbunitStamgegevens.xml")
    @ExpectedAfter("ServiceIntegratieTest-28.xml")
    @Test
    public void testKindMetPuntOuder() throws InputValidationException {
        final BrpPersoonslijst kindPersoonslijst = maakKindMetPuntOuderPersoonslijst(false, false);
        service.persisteerPersoonslijst(kindPersoonslijst);
    }

    /**
     * Test het dubbel opslaan van een kind met een punt ouder.
     * 
     * <ul>
     * <li>Pietje Bell -1> ?</li>
     * </ul>
     * 
     * @throws InputValidationException
     *             als de testdata incorrect is
     */
    @InsertBefore("/sql/data/dbunitStamgegevens.xml")
    @ExpectedAfter("ServiceIntegratieTest-29.xml")
    @NotExpectedAfter("ServiceIntegratieTest-29-not.xml")
    @Test
    public void testKindMetPuntOuderDubbelOpslaan() throws InputValidationException {
        final BrpPersoonslijst kindPersoonslijst = maakKindMetPuntOuderPersoonslijst(false, false);
        service.persisteerPersoonslijst(kindPersoonslijst);
        service.persisteerPersoonslijst(kindPersoonslijst);
    }

    /**
     * Test het wijzigen van de vader in een familierechtelijk betrekking.
     * 
     * <ul>
     * <li>Pietje Bell -1> Kees Jan (NI)</li>
     * <li>Pietje Bell -1> Marie (NI)</li>
     * <li>=============</li>
     * <li>Pietje Bell -1> Kees Jan (NI)</li>
     * <li>Pietje Bell <-1> Marie (I)</li>
     * <li>=============</li>
     * <li>Pietje Bell -1> Kees Jan (NI)</li>
     * <li>Pietje Bell <-1> Marie (I)</li>
     * <li>Pietje Bell <1- Karel (I)</li>
     * <li>=============</li>
     * <li>Pietje Bell <-1> Marie (I)</li>
     * <li>Pietje Bell <-1> Karel (I)</li>
     * </ul>
     * 
     * @throws InputValidationException
     *             als de testdata incorrect is
     */
    @InsertBefore("/sql/data/dbunitStamgegevens.xml")
    @ExpectedAfter("ServiceIntegratieTest-30.xml")
    @NotExpectedAfter("ServiceIntegratieTest-30-not.xml")
    @Test
    public void testKindMetVaderDieWijzigt() throws InputValidationException {
        final BrpPersoonslijst kindPersoonslijst = maakKindMetVaderEnMoederPersoonslijst(false, false);
        service.persisteerPersoonslijst(kindPersoonslijst);
        final BrpPersoonslijst moederPersoonslijst = maakMoederMetKindPersoonslijst(false);
        service.persisteerPersoonslijst(moederPersoonslijst);
        final BrpPersoonslijst nieuweVaderPersoonslijst = maakVader2MetKindPersoonslijst(false);
        service.persisteerPersoonslijst(nieuweVaderPersoonslijst);
        final BrpPersoonslijst kindMetAndereVaderPersoonslijst = maakKindMetVader2EnMoederPersoonslijst(false, false);
        service.persisteerPersoonslijst(kindMetAndereVaderPersoonslijst);
    }

    /**
     * Test het wijzigen van een a-nummer. In deze testcase wordt de pl van de vader vervangen door die van de moeder.
     * Dit mag in het echt niet voorkomen maar deze test heeft puur als doel om aan te tonen dat het vervangen van een
     * persoon mogelijk is. Dit ter ondersteuning van een a-nummer wijziging.
     * 
     * @throws InputValidationException
     *             als de testdata incorrect is
     */
    @InsertBefore("/sql/data/dbunitStamgegevens.xml")
    @ExpectedAfter("ServiceIntegratieTest-31.xml")
    @NotExpectedAfter("ServiceIntegratieTest-31-not.xml")
    @Test
    public void testANummerWijziging() throws InputValidationException {
        final BrpPersoonslijst moederPersoonslijst = maakMoederMetKindPersoonslijst(false);
        final BrpPersoonslijst vaderPersoonslijst = maakVaderMetKindPersoonslijst(false);

        service.persisteerPersoonslijst(vaderPersoonslijst);
        service.persisteerPersoonslijst(moederPersoonslijst,
                BigDecimal.valueOf(vaderPersoonslijst.getActueelAdministratienummer()));
    }

    /**
     * Test het wijzigen van een a-nummer. In deze testcase wordt de pl van de vader vervangen door die van de moeder.
     * Dit mag in het echt niet voorkomen maar deze test heeft puur als doel om aan te tonen dat het vervangen van een
     * persoon mogelijk is. Dit ter ondersteuning van een a-nummer wijziging. Het doel van deze test is aan te tonen dat
     * relaties met ingeschrevene overgaan naar de nieuwe persoon die de bestaande vervangt.
     * 
     * @throws InputValidationException
     *             als de testdata incorrect is
     */
    @InsertBefore("/sql/data/dbunitStamgegevens.xml")
    @ExpectedAfter("ServiceIntegratieTest-32.xml")
    @NotExpectedAfter("ServiceIntegratieTest-32-not.xml")
    @Test
    public void testANummerWijzigingMetRelatieIngeschrevene() throws InputValidationException {
        final BrpPersoonslijst vaderPersoonslijst = maakVaderMetKindPersoonslijst(false);
        final BrpPersoonslijst kindPersoonslijst = maakKindMetVaderPersoonslijst(false, true);
        final BrpPersoonslijst moederPersoonslijst = maakMoederMetKindPersoonslijst(false);

        service.persisteerPersoonslijst(vaderPersoonslijst);
        service.persisteerPersoonslijst(kindPersoonslijst);
        service.persisteerPersoonslijst(moederPersoonslijst,
                BigDecimal.valueOf(vaderPersoonslijst.getActueelAdministratienummer()));
    }

    private BrpPersoonslijst maakVaderPersoonslijst(final boolean laatRelatieVervallen)
            throws InputValidationException {
        return new BrpPersoonslijstTestDataBuilder()
                .addGroepMetHistorieA(new BrpIdentificatienummersInhoud(MAN_A_NUMMER, MAN_BSN))
                .addGroepMetHistorieA(new BrpGeslachtsaanduidingInhoud(BrpGeslachtsaanduidingCode.MAN))
                .addGroepMetHistorieA(new BrpInschrijvingInhoud(null, null, MAN_GEBOORTEDATUM, 0))
                .addGroepMetHistorieA(MAN_NAAM)
                .addGroepMetHistorieA(new BrpNationaliteitInhoud(NATIONALITEIT_CODE_NEDERLAND, null, null))
                .addGroepMetHistorieA(MAN_GEBOORTE).build();
    }

    private BrpPersoonslijst maakMoederMetKindPersoonslijst(final boolean laatRelatieVervallen)
            throws InputValidationException {
        return new BrpPersoonslijstTestDataBuilder()
                .addGroepMetHistorieA(new BrpIdentificatienummersInhoud(VROUW_A_NUMMER, VROUW_BSN))
                .addGroepMetHistorieA(new BrpGeslachtsaanduidingInhoud(BrpGeslachtsaanduidingCode.VROUW))
                .addGroepMetHistorieA(new BrpInschrijvingInhoud(null, null, VROUW_GEBOORTEDATUM, 0))
                .addGroepMetHistorieA(VROUW_NAAM)
                .addGroepMetHistorieA(new BrpNationaliteitInhoud(NATIONALITEIT_CODE_NEDERLAND, null, null))
                .addGroepMetHistorieA(VROUW_GEBOORTE)
                .addRelatieMetKind(KIND1_A_NUMMER, KIND1_BSN, BrpGeslachtsaanduidingCode.MAN, KIND1_GEBOORTE,
                        KIND1_NAAM, laatRelatieVervallen).build();
    }

    private BrpPersoonslijst maakVaderMetKindPersoonslijst(final boolean laatRelatieVervallen)
            throws InputValidationException {
        return new BrpPersoonslijstTestDataBuilder()
                .addGroepMetHistorieA(new BrpIdentificatienummersInhoud(MAN_A_NUMMER, MAN_BSN))
                .addGroepMetHistorieA(new BrpGeslachtsaanduidingInhoud(BrpGeslachtsaanduidingCode.MAN))
                .addGroepMetHistorieA(new BrpInschrijvingInhoud(null, null, MAN_GEBOORTEDATUM, 0))
                .addGroepMetHistorieA(MAN_NAAM)
                .addGroepMetHistorieA(new BrpNationaliteitInhoud(NATIONALITEIT_CODE_NEDERLAND, null, null))
                .addGroepMetHistorieA(MAN_GEBOORTE)
                .addRelatieMetKind(KIND1_A_NUMMER, KIND1_BSN, BrpGeslachtsaanduidingCode.MAN, KIND1_GEBOORTE,
                        KIND1_NAAM, laatRelatieVervallen).build();
    }

    private BrpPersoonslijst maakVader2MetKindPersoonslijst(final boolean laatRelatieVervallen)
            throws InputValidationException {
        return new BrpPersoonslijstTestDataBuilder()
                .addGroepMetHistorieA(new BrpIdentificatienummersInhoud(MAN2_A_NUMMER, MAN2_BSN))
                .addGroepMetHistorieA(new BrpGeslachtsaanduidingInhoud(BrpGeslachtsaanduidingCode.MAN))
                .addGroepMetHistorieA(new BrpInschrijvingInhoud(null, null, MAN2_GEBOORTEDATUM, 0))
                .addGroepMetHistorieA(MAN2_NAAM)
                .addGroepMetHistorieA(new BrpNationaliteitInhoud(NATIONALITEIT_CODE_NEDERLAND, null, null))
                .addGroepMetHistorieA(MAN2_GEBOORTE)
                .addRelatieMetKind(KIND1_A_NUMMER, KIND1_BSN, BrpGeslachtsaanduidingCode.MAN, KIND1_GEBOORTE,
                        KIND1_NAAM, laatRelatieVervallen).build();
    }

    private BrpPersoonslijst maakVaderMetTweeKinderenPersoonslijst(final boolean laatRelatieVervallen)
            throws InputValidationException {
        return new BrpPersoonslijstTestDataBuilder()
                .addGroepMetHistorieA(new BrpIdentificatienummersInhoud(MAN_A_NUMMER, MAN_BSN))
                .addGroepMetHistorieA(new BrpGeslachtsaanduidingInhoud(BrpGeslachtsaanduidingCode.MAN))
                .addGroepMetHistorieA(new BrpInschrijvingInhoud(null, null, MAN_GEBOORTEDATUM, 0))
                .addGroepMetHistorieA(MAN_NAAM)
                .addGroepMetHistorieA(new BrpNationaliteitInhoud(NATIONALITEIT_CODE_NEDERLAND, null, null))
                .addGroepMetHistorieA(MAN_GEBOORTE)
                .addRelatieMetKind(KIND1_A_NUMMER, KIND1_BSN, BrpGeslachtsaanduidingCode.MAN, KIND1_GEBOORTE,
                        KIND1_NAAM, laatRelatieVervallen)
                .addRelatieMetKind(KIND2_A_NUMMER, KIND2_BSN, BrpGeslachtsaanduidingCode.MAN, KIND2_GEBOORTE,
                        KIND2_NAAM, laatRelatieVervallen).build();
    }

    private BrpPersoonslijst maakKindMetVaderEnMoederPersoonslijst(
            final boolean laatRelatieVervallen,
            final boolean ouder1HeeftOuderlijkGezag) throws InputValidationException {
        return new BrpPersoonslijstTestDataBuilder()
                .addGroepMetHistorieA(new BrpIdentificatienummersInhoud(KIND1_A_NUMMER, KIND1_BSN))
                .addGroepMetHistorieA(new BrpGeslachtsaanduidingInhoud(BrpGeslachtsaanduidingCode.MAN))
                .addGroepMetHistorieA(new BrpInschrijvingInhoud(null, null, KIND1_GEBOORTEDATUM, 0))
                .addGroepMetHistorieA(KIND1_NAAM)
                .addGroepMetHistorieA(new BrpNationaliteitInhoud(NATIONALITEIT_CODE_NEDERLAND, null, null))
                .addGroepMetHistorieA(KIND1_GEBOORTE)
                .addRelatieMetOuders(MAN_A_NUMMER, MAN_BSN, BrpGeslachtsaanduidingCode.MAN, MAN_GEBOORTE, MAN_NAAM,
                        VROUW_A_NUMMER, VROUW_BSN, BrpGeslachtsaanduidingCode.VROUW, VROUW_GEBOORTE, VROUW_NAAM,
                        laatRelatieVervallen, ouder1HeeftOuderlijkGezag).build();
    }

    private BrpPersoonslijst maakKindMetVader2EnMoederPersoonslijst(
            final boolean laatRelatieVervallen,
            final boolean ouder1HeeftOuderlijkGezag) throws InputValidationException {
        return new BrpPersoonslijstTestDataBuilder()
                .addGroepMetHistorieA(new BrpIdentificatienummersInhoud(KIND1_A_NUMMER, KIND1_BSN))
                .addGroepMetHistorieA(new BrpGeslachtsaanduidingInhoud(BrpGeslachtsaanduidingCode.MAN))
                .addGroepMetHistorieA(new BrpInschrijvingInhoud(null, null, KIND1_GEBOORTEDATUM, 0))
                .addGroepMetHistorieA(KIND1_NAAM)
                .addGroepMetHistorieA(new BrpNationaliteitInhoud(NATIONALITEIT_CODE_NEDERLAND, null, null))
                .addGroepMetHistorieA(KIND1_GEBOORTE)
                .addRelatieMetOuders(MAN2_A_NUMMER, MAN2_BSN, BrpGeslachtsaanduidingCode.MAN, MAN2_GEBOORTE,
                        MAN2_NAAM, VROUW_A_NUMMER, VROUW_BSN, BrpGeslachtsaanduidingCode.VROUW, VROUW_GEBOORTE,
                        VROUW_NAAM, laatRelatieVervallen, ouder1HeeftOuderlijkGezag).build();
    }

    private BrpPersoonslijst maakKindMetVaderPersoonslijst(
            final boolean laatRelatieVervallen,
            final boolean ouder1HeeftOuderlijkGezag) throws InputValidationException {
        return new BrpPersoonslijstTestDataBuilder()
                .addGroepMetHistorieA(new BrpIdentificatienummersInhoud(KIND1_A_NUMMER, KIND1_BSN))
                .addGroepMetHistorieA(new BrpGeslachtsaanduidingInhoud(BrpGeslachtsaanduidingCode.MAN))
                .addGroepMetHistorieA(new BrpInschrijvingInhoud(null, null, KIND1_GEBOORTEDATUM, 0))
                .addGroepMetHistorieA(KIND1_NAAM)
                .addGroepMetHistorieA(new BrpNationaliteitInhoud(NATIONALITEIT_CODE_NEDERLAND, null, null))
                .addGroepMetHistorieA(KIND1_GEBOORTE)
                .addRelatieMetOuders(MAN_A_NUMMER, MAN_BSN, BrpGeslachtsaanduidingCode.MAN, MAN_GEBOORTE, MAN_NAAM,
                        null, null, null, null, null, laatRelatieVervallen, ouder1HeeftOuderlijkGezag).build();
    }

    private BrpPersoonslijst maakKindMetMoederPersoonslijst(
            final boolean laatRelatieVervallen,
            final boolean ouder1HeeftOuderlijkGezag) throws InputValidationException {
        return new BrpPersoonslijstTestDataBuilder()
                .addGroepMetHistorieA(new BrpIdentificatienummersInhoud(KIND1_A_NUMMER, KIND1_BSN))
                .addGroepMetHistorieA(new BrpGeslachtsaanduidingInhoud(BrpGeslachtsaanduidingCode.MAN))
                .addGroepMetHistorieA(new BrpInschrijvingInhoud(null, null, KIND1_GEBOORTEDATUM, 0))
                .addGroepMetHistorieA(KIND1_NAAM)
                .addGroepMetHistorieA(new BrpNationaliteitInhoud(NATIONALITEIT_CODE_NEDERLAND, null, null))
                .addGroepMetHistorieA(KIND1_GEBOORTE)
                .addRelatieMetOuders(VROUW_A_NUMMER, VROUW_BSN, BrpGeslachtsaanduidingCode.VROUW, VROUW_GEBOORTE,
                        VROUW_NAAM, null, null, null, null, null, laatRelatieVervallen, ouder1HeeftOuderlijkGezag)
                .build();
    }

    private BrpPersoonslijst maakKindMetPuntOuderPersoonslijst(
            final boolean laatRelatieVervallen,
            final boolean ouder1HeeftOuderlijkGezag) throws InputValidationException {
        return new BrpPersoonslijstTestDataBuilder()
                .addGroepMetHistorieA(new BrpIdentificatienummersInhoud(KIND1_A_NUMMER, KIND1_BSN))
                .addGroepMetHistorieA(new BrpGeslachtsaanduidingInhoud(BrpGeslachtsaanduidingCode.MAN))
                .addGroepMetHistorieA(new BrpInschrijvingInhoud(null, null, KIND1_GEBOORTEDATUM, 0))
                .addGroepMetHistorieA(KIND1_NAAM)
                .addGroepMetHistorieA(new BrpNationaliteitInhoud(NATIONALITEIT_CODE_NEDERLAND, null, null))
                .addGroepMetHistorieA(KIND1_GEBOORTE)
                .addRelatieMetPuntOuder(laatRelatieVervallen, ouder1HeeftOuderlijkGezag).build();
    }

    private BrpPersoonslijst maakManMetVrouwPersoonslijst(
            final BrpGemeenteCode trouwGemeenteCode,
            final boolean laatRelatieVervallen) throws InputValidationException {
        return new BrpPersoonslijstTestDataBuilder()
                .addGroepMetHistorieA(new BrpIdentificatienummersInhoud(MAN_A_NUMMER, MAN_BSN))
                .addGroepMetHistorieA(new BrpGeslachtsaanduidingInhoud(BrpGeslachtsaanduidingCode.MAN))
                .addGroepMetHistorieA(new BrpInschrijvingInhoud(null, null, MAN_GEBOORTEDATUM, 0))
                .addGroepMetHistorieA(MAN_NAAM)
                .addGroepMetHistorieA(new BrpNationaliteitInhoud(NATIONALITEIT_CODE_NEDERLAND, null, null))
                .addGroepMetHistorieA(MAN_GEBOORTE)
                .addHuwelijkRelatie(VROUW_A_NUMMER, VROUW_BSN, BrpGeslachtsaanduidingCode.VROUW, VROUW_GEBOORTE,
                        VROUW_NAAM, TROUWDATUM, trouwGemeenteCode, laatRelatieVervallen).build();
    }

    private BrpPersoonslijst maakVrouwMetManPersoonslijst(
            final BrpGemeenteCode trouwGemeenteCode,
            final boolean laatRelatieVervallen) throws InputValidationException {
        return new BrpPersoonslijstTestDataBuilder()
                .addGroepMetHistorieA(new BrpIdentificatienummersInhoud(VROUW_A_NUMMER, VROUW_BSN))
                .addGroepMetHistorieA(new BrpGeslachtsaanduidingInhoud(BrpGeslachtsaanduidingCode.VROUW))
                .addGroepMetHistorieA(new BrpInschrijvingInhoud(null, null, VROUW_GEBOORTEDATUM, 0))
                .addGroepMetHistorieA(VROUW_NAAM)
                .addGroepMetHistorieA(new BrpNationaliteitInhoud(NATIONALITEIT_CODE_NEDERLAND, null, null))
                .addGroepMetHistorieA(VROUW_GEBOORTE)
                .addHuwelijkRelatie(MAN_A_NUMMER, MAN_BSN, BrpGeslachtsaanduidingCode.MAN, MAN_GEBOORTE, MAN_NAAM,
                        TROUWDATUM, trouwGemeenteCode, laatRelatieVervallen).build();
    }

    private BrpPersoonslijst maakVrouwPersoonslijst(final boolean laatRelatieVervallen)
            throws InputValidationException {
        return new BrpPersoonslijstTestDataBuilder()
                .addGroepMetHistorieA(new BrpIdentificatienummersInhoud(VROUW_A_NUMMER, VROUW_BSN))
                .addGroepMetHistorieA(new BrpGeslachtsaanduidingInhoud(BrpGeslachtsaanduidingCode.VROUW))
                .addGroepMetHistorieA(new BrpInschrijvingInhoud(null, null, VROUW_GEBOORTEDATUM, 0))
                .addGroepMetHistorieA(VROUW_NAAM)
                .addGroepMetHistorieA(new BrpNationaliteitInhoud(NATIONALITEIT_CODE_NEDERLAND, null, null))
                .addGroepMetHistorieA(VROUW_GEBOORTE).build();
    }

    private BrpPersoonslijst maakBrpPersoonslijst() throws InputValidationException {
        return new BrpPersoonslijstTestDataBuilder()
                .addDefaultTestStapels()
                .addGroepMetHistorieA(new BrpNationaliteitInhoud(NATIONALITEIT_CODE_NEDERLAND, null, null))
                .addGroepMetHistorieB(new BrpImmigratieInhoud(LAND_CODE_DUITSLAND, new BrpDatum(20110101)))
                .addGroepMetHistorieB(
                        new BrpOpschortingInhoud(new BrpDatum(20050202), BrpRedenOpschortingBijhoudingCode.FOUT))
                .addGroepMetHistorieC(
                        new BrpOverlijdenInhoud(new BrpDatum(20110101), GEMEENTE_CODE_ALMERE,
                                PLAATS_CODE_ALMEREBUITEN, null, null, LAND_CODE_NEDERLAND, null))
                .addGroepMetHistorieC(
                        new BrpGeboorteInhoud(new BrpDatum(19830405), GEMEENTE_CODE_ALMERE, null, null, null,
                                LAND_CODE_NEDERLAND, null))
                .addGroepMetHistorieA(
                        new BrpAanschrijvingInhoud(BrpWijzeGebruikGeslachtsnaamCode.E, null, true, null, null,
                                "Noam", null, ' ', "Chomsky"))
                .addGroepMetHistorieB(
                        new BrpBijhoudingsgemeenteInhoud(GEMEENTE_CODE_ALMERE, new BrpDatum(20100505), false))
                .addGroepMetHistorieB(
                        new BrpBijhoudingsverantwoordelijkheidInhoud(BrpVerantwoordelijkeCode.COLLEGE_B_EN_W,
                                new BrpDatum(20110708)))
                .addGroepMetHistorieA(new BrpPersoonskaartInhoud(GEMEENTE_CODE_ALMERE, true))
                .addGroepMetHistorieD(
                        new BrpEuropeseVerkiezingenInhoud(true, new BrpDatum(20110505), new BrpDatum(20110606)))
                .addGroepMetHistorieA(new BrpUitsluitingNederlandsKiesrechtInhoud(true, new BrpDatum(20140101)))
                .addGroepMetHistorieB(
                        new BrpVerblijfsrechtInhoud(new BrpVerblijfsrechtCode("Onbekend"), new BrpDatum(20110303),
                                new BrpDatum(20110404), new BrpDatum(20110505)))
                .addGroepMetHistorieB(createBrpAdresInhoud())
                .addGroepMetHistorieC(new BrpBehandeldAlsNederlanderIndicatieInhoud(true))
                .addGroepMetHistorieD(new BrpBelemmeringVerstrekkingReisdocumentIndicatieInhoud(true))
                .addGroepMetHistorieA(new BrpBezitBuitenlandsReisdocumentIndicatieInhoud(true))
                .addGroepMetHistorieD(new BrpOnderCurateleIndicatieInhoud(true))
                .addGroepMetHistorieB(new BrpStatenloosIndicatieInhoud(true))
                .addGroepMetHistorieC(new BrpVastgesteldNietNederlanderIndicatieInhoud(true))
                .addGroepMetHistorieA(createBrpReisdocumentInhoud())
                .addGroepMetHistorieB(createBrpGeslachtsnaamcomponentInhoud())
                .addGroepMetHistorieC(new BrpVoornaamInhoud("Piet", 1))
                .addGroepMetHistorieE(new BrpVoornaamInhoud("Klaas", 2)).addKindRelatie().build();
    }

    private BrpAdresInhoud createBrpAdresInhoud() {
        final BrpFunctieAdresCode functieAdresCode = BrpFunctieAdresCode.B;
        final BrpRedenWijzigingAdresCode redenWijzigingAdresCode = new BrpRedenWijzigingAdresCode("P");
        final BrpAangeverAdreshoudingCode aangeverAdreshoudingCode = new BrpAangeverAdreshoudingCode("G");
        final BrpDatum datumAanvangAdreshouding = new BrpDatum(20120606);
        final String adresseerbaarObject = "object";
        final String identificatiecodeNummeraanduiding = "id01";
        final BrpGemeenteCode gemeenteCode = GEMEENTE_CODE_ALMERE;
        final String naamOpenbareRuimte = "New Yorkweg";
        final String afgekorteNaamOpenbareRuimte = "New Yorkweg";
        final String gemeentedeel = "Almere";
        final Integer huisnummer = Integer.valueOf(73);
        final Character huisletter = Character.valueOf('A');
        final String huisnummertoevoeging = "sous";
        final String postcode = "1334NA";
        final BrpPlaatsCode plaatsCode = PLAATS_CODE_ALMEREBUITEN;
        final BrpAanduidingBijHuisnummerCode locatieTovAdres = new BrpAanduidingBijHuisnummerCode("to");
        final String locatieOmschrijving = "omschrijving locatie";
        final String buitenlandsAdresRegel1 = "adres1";
        final String buitenlandsAdresRegel2 = "adres2";
        final String buitenlandsAdresRegel3 = "adres3";
        final String buitenlandsAdresRegel4 = "adres4";
        final String buitenlandsAdresRegel5 = "adres5";
        final String buitenlandsAdresRegel6 = "adres6";
        final BrpLandCode landCode = LAND_CODE_DUITSLAND;
        final BrpDatum datumVertrekUitNederland = new BrpDatum(20120707);
        return new BrpAdresInhoud(functieAdresCode, redenWijzigingAdresCode, aangeverAdreshoudingCode,
                datumAanvangAdreshouding, adresseerbaarObject, identificatiecodeNummeraanduiding, gemeenteCode,
                naamOpenbareRuimte, afgekorteNaamOpenbareRuimte, gemeentedeel, huisnummer, huisletter,
                huisnummertoevoeging, postcode, plaatsCode, locatieTovAdres, locatieOmschrijving,
                buitenlandsAdresRegel1, buitenlandsAdresRegel2, buitenlandsAdresRegel3, buitenlandsAdresRegel4,
                buitenlandsAdresRegel5, buitenlandsAdresRegel6, landCode, datumVertrekUitNederland);
    }

    private BrpGeslachtsnaamcomponentInhoud createBrpGeslachtsnaamcomponentInhoud() {
        final String voorvoegsel = "van";
        final Character scheidingsteken = ' ';
        final String naam = "Pietersen";
        final BrpPredikaatCode predikaatCode = new BrpPredikaatCode(Predikaat.H.toString());
        final BrpAdellijkeTitelCode adellijkeTitelCode = new BrpAdellijkeTitelCode(AdellijkeTitel.M.toString());
        final int volgnummer = 1;
        return new BrpGeslachtsnaamcomponentInhoud(voorvoegsel, scheidingsteken, naam, predikaatCode,
                adellijkeTitelCode, volgnummer);
    }

    private BrpReisdocumentInhoud createBrpReisdocumentInhoud() {
        final BrpReisdocumentSoort soort = new BrpReisdocumentSoort("?");
        final String nummer = "P12345678";
        final BrpDatum datumIngangDocument = new BrpDatum(20120102);
        final BrpDatum datumUitgifte = new BrpDatum(20120101);
        final BrpReisdocumentAutoriteitVanAfgifte autoriteitVanAfgifte =
                new BrpReisdocumentAutoriteitVanAfgifte("BI0518");
        final BrpDatum datumVoorzieneEindeGeldigheid = new BrpDatum(20220101);
        final BrpDatum datumInhoudingVermissing = new BrpDatum(20120301);
        final BrpReisdocumentRedenOntbreken redenOntbreken = new BrpReisdocumentRedenOntbreken("I");
        final Integer lengteHouder = Integer.valueOf(185);
        final BrpReisdocumentInhoud result =
                new BrpReisdocumentInhoud(soort, nummer, datumIngangDocument, datumUitgifte, autoriteitVanAfgifte,
                        datumVoorzieneEindeGeldigheid, datumInhoudingVermissing, redenOntbreken, lengteHouder);
        return result;
    }

    private Logging maakLogging() {
        final Logging logging = new Logging(new ArrayList<LogRegel>());
        logging.addLogRegel(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_01, 4, 2), LogSeverity.INFO,
                LogType.BIJZONDERE_SITUATIE, "TEST-01", "Test logregel nummer een.");
        logging.addLogRegel(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_02, 3, 1), LogSeverity.ERROR,
                LogType.PRECONDITIE, "TEST-02", "Test logregel nummer twee.");
        logging.addLogRegel(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_03, 2, 4), LogSeverity.WARNING,
                LogType.STRUCTUUR, "TEST-03", "Test logregel nummer drie.");
        logging.addLogRegel(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_04, 1, 3), LogSeverity.WARNING,
                LogType.VERWERKING, "TEST-04", "Test logregel nummer vier.");

        return logging;
    }

    private BerichtLog maakBerichtLog(final BigDecimal administratienummer) {
        final BerichtLog berichtLog =
                new BerichtLog("bericht-uuid", "service.integratie.test", new Timestamp(new GregorianCalendar(2012,
                        11, 27, 13, 13, 13).getTimeInMillis()));
        berichtLog.setAdministratienummer(administratienummer);
        berichtLog.setBerichtData("<testBericht/>");
        return berichtLog;
    }
}
