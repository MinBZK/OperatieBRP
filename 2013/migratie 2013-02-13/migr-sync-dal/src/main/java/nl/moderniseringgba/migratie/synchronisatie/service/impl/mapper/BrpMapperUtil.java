/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import java.math.BigDecimal;
import java.sql.Timestamp;

import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpAanduidingBijHuisnummerCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpAangeverAdreshoudingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpFunctieAdresCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpLandCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPartijCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPlaatsCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenOpschortingBijhoudingCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenWijzigingAdresCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpReisdocumentAutoriteitVanAfgifte;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpReisdocumentRedenOntbreken;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpReisdocumentSoort;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpVerantwoordelijkeCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpVerblijfsrechtCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpVerdragCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpWijzeGebruikGeslachtsnaamCode;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.AangeverAdreshouding;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.AdellijkeTitel;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.AutoriteitVanAfgifteReisdocument;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.FunctieAdres;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Geslachtsaanduiding;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Land;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Nationaliteit;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Partij;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Plaats;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Predikaat;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.RedenBeeindigingRelatie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.RedenOpschorting;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.RedenVerkrijgingNLNationaliteit;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.RedenVerliesNLNationaliteit;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.RedenVervallenReisdocument;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.RedenWijzigingAdres;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.SoortActie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.SoortBetrokkenheid;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.SoortDocument;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.SoortNederlandsReisdocument;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.SoortRelatie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Verantwoordelijke;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Verblijfsrecht;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Verdrag;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.WijzeGebruikGeslachtsnaam;

/**
 * Mapper utilities.
 */
// CHECKSTYLE:OFF - Fan-out complexity - Logisch gevolg van het mappen van het brp domein op het sync domein. Hierdoor
// ontstaan er veel imports. Kan genegeerd worden.
public final class BrpMapperUtil {
    // CHECKSTYLE:ON

    private BrpMapperUtil() {
        throw new AssertionError("Niet instantieerbaar");
    }

    /**
     * Map naar een BrpAanduidingBijHuisnummerCode.
     * 
     * @param waarde
     *            BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static BrpAanduidingBijHuisnummerCode mapBrpAanduidingBijHuisnummerCode(final String waarde) {
        return waarde == null ? null : new BrpAanduidingBijHuisnummerCode(waarde);
    }

    /**
     * Map naar een BrpAangeverAdreshoudingCode.
     * 
     * @param waarde
     *            BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static BrpAangeverAdreshoudingCode mapBrpAangeverAdreshoudingCode(final AangeverAdreshouding waarde) {
        return waarde == null ? null : new BrpAangeverAdreshoudingCode(waarde.getCode());
    }

    /**
     * Map naar een BrpAdellijkeTitelCode.
     * 
     * @param waarde
     *            BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static BrpAdellijkeTitelCode mapBrpAdellijkeTitelCode(final AdellijkeTitel waarde) {
        return waarde == null ? null : new BrpAdellijkeTitelCode(waarde.getCode());
    }

    /**
     * Map naar een BrpReisdocumentAutoriteitVanAfgifte.
     * 
     * @param waarde
     *            BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static BrpReisdocumentAutoriteitVanAfgifte mapBrpAutoriteitVanAfgifte(
            final AutoriteitVanAfgifteReisdocument waarde) {
        return waarde == null ? null : new BrpReisdocumentAutoriteitVanAfgifte(waarde.getCode());
    }

    /**
     * Map naar een BrpDatumTijd.
     * 
     * @param waarde
     *            BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static BrpDatumTijd mapBrpDatumTijd(final Timestamp waarde) {
        return waarde == null ? null : new BrpDatumTijd(waarde);
    }

    /**
     * Map naar een BrpFunctieAdresCode.
     * 
     * @param waarde
     *            BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static BrpFunctieAdresCode mapBrpFunctieAdresCode(final FunctieAdres waarde) {
        return waarde == null ? null : BrpFunctieAdresCode.valueOf(waarde.getCode());
    }

    /**
     * Map naar een BrpGemeenteCode.
     * 
     * @param waarde
     *            BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static BrpGemeenteCode mapBrpGemeenteCode(final Partij waarde) {
        return waarde == null ? null : new BrpGemeenteCode(waarde.getGemeentecode());
    }

    /**
     * Map naar een BrpGeslachtsaanduidingCode.
     * 
     * @param waarde
     *            BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static BrpGeslachtsaanduidingCode mapBrpGeslachtsaanduidingCode(final Geslachtsaanduiding waarde) {
        return waarde == null ? null : BrpGeslachtsaanduidingCode.valueOfBrpCode(waarde.getCode());
    }

    /**
     * Map naar een BrpLandCode.
     * 
     * @param waarde
     *            BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static BrpLandCode mapBrpLandCode(final Land waarde) {
        return waarde == null ? null : new BrpLandCode(waarde.getLandcode() == null ? null : waarde.getLandcode()
                .intValue());
    }

    /**
     * Map naar een BrpNationaliteitCode.
     * 
     * @param waarde
     *            BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static BrpNationaliteitCode mapBrpNationaliteitCode(final Nationaliteit waarde) {
        return waarde == null ? null : new BrpNationaliteitCode(waarde.getNationaliteitcode().intValue());
    }

    /**
     * Map naar een BrpPartijCode.
     * 
     * @param waarde
     *            BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static BrpPartijCode mapBrpPartijCode(final Partij waarde) {
        return waarde == null ? null : new BrpPartijCode(waarde.getNaam(), waarde.getGemeentecode() == null ? null
                : waarde.getGemeentecode().intValue());
    }

    /**
     * Map naar een BrpPlaatsCode.
     * 
     * @param waarde
     *            BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static BrpPlaatsCode mapBrpPlaatsCode(final Plaats waarde) {
        return waarde == null ? null : new BrpPlaatsCode(waarde.getNaam());
    }

    /**
     * Map naar een BrpPredikaatCode.
     * 
     * @param waarde
     *            BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static BrpPredikaatCode mapBrpPredikaatCode(final Predikaat waarde) {
        return waarde == null ? null : new BrpPredikaatCode(waarde.getCode());
    }

    /**
     * Map naar een BrpRedenEindeRelatieCode.
     * 
     * @param waarde
     *            BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static BrpRedenEindeRelatieCode mapBrpRedenEindeRelatieCode(final RedenBeeindigingRelatie waarde) {
        return waarde == null ? null : new BrpRedenEindeRelatieCode(waarde.getCode());
    }

    /**
     * Map naar een BrpAanduidingBijHuisnummerCode.
     * 
     * @param waarde
     *            BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static BrpRedenOpschortingBijhoudingCode mapBrpRedenOpschortingCode(final RedenOpschorting waarde) {
        return waarde == null ? null : BrpRedenOpschortingBijhoudingCode.valueOfCode(waarde.getCode());
    }

    /**
     * Map naar een BrpRedenVerkrijgingNederlandschapCode.
     * 
     * @param waarde
     *            BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static BrpRedenVerkrijgingNederlandschapCode mapBrpRedenVerkrijgingNederlandschapCode(
            final RedenVerkrijgingNLNationaliteit waarde) {
        return waarde == null ? null : new BrpRedenVerkrijgingNederlandschapCode(waarde.getNaam());
    }

    /**
     * Map naar een BrpRedenVerliesNederlandschapCode.
     * 
     * @param waarde
     *            BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static BrpRedenVerliesNederlandschapCode mapBrpRedenVerliesNederlanderschapCode(
            final RedenVerliesNLNationaliteit waarde) {
        return waarde == null ? null : new BrpRedenVerliesNederlandschapCode(waarde.getNaam());
    }

    /**
     * Map naar een BrpRedenWijzigingAdresCode.
     * 
     * @param waarde
     *            BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static BrpRedenWijzigingAdresCode mapBrpRedenWijzigingAdresCode(final RedenWijzigingAdres waarde) {
        return waarde == null ? null : new BrpRedenWijzigingAdresCode(waarde.getCode());
    }

    /**
     * Map naar een BrpReisdocumentRedenOntbreken.
     * 
     * @param waarde
     *            BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static BrpReisdocumentRedenOntbreken mapBrpReisdocumentRedenOntbreken(
            final RedenVervallenReisdocument waarde) {
        return waarde == null ? null : new BrpReisdocumentRedenOntbreken(waarde.getCode());
    }

    /**
     * Map naar een BrpReisdocumentSoort.
     * 
     * @param waarde
     *            BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static BrpReisdocumentSoort mapBrpReisdocumentSoort(final SoortNederlandsReisdocument waarde) {
        return waarde == null ? null : new BrpReisdocumentSoort(waarde.getCode());
    }

    /**
     * Map naar een BrpSoortActieCode.
     * 
     * @param waarde
     *            BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static BrpSoortActieCode mapBrpSoortActieCode(final SoortActie waarde) {
        return waarde == null ? null : BrpSoortActieCode.valueOfCode(waarde.getNaam());
    }

    /**
     * Map naar een BrpSoortBetrokkenheidCode.
     * 
     * @param waarde
     *            BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static BrpSoortBetrokkenheidCode mapBrpSoortBetrokkenheidCode(final SoortBetrokkenheid waarde) {
        return waarde == null ? null : BrpSoortBetrokkenheidCode.valueOfCode(waarde.getCode());
    }

    /**
     * Map naar een BrpSoortDocumentCode.
     * 
     * @param waarde
     *            BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static BrpSoortDocumentCode mapBrpSoortDocumentCode(final SoortDocument waarde) {
        return waarde == null ? null : new BrpSoortDocumentCode(waarde.getOmschrijving());
    }

    /**
     * Map naar een BrpSoortRelatieCode.
     * 
     * @param waarde
     *            BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static BrpSoortRelatieCode mapBrpSoortRelatieCode(final SoortRelatie waarde) {
        return waarde == null ? null : BrpSoortRelatieCode.valueOfCode(waarde.getCode());
    }

    /**
     * Map naar een BrpVerantwoordelijkeCode.
     * 
     * @param waarde
     *            BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static BrpVerantwoordelijkeCode mapBrpVerantwoordelijkCode(final Verantwoordelijke waarde) {
        return waarde == null ? null : BrpVerantwoordelijkeCode.valueOfCode(waarde.getCode());
    }

    /**
     * Map naar een BrpVerblijfsrechtCode.
     * 
     * @param waarde
     *            BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static BrpVerblijfsrechtCode mapBrpVerblijfsrechtCode(final Verblijfsrecht waarde) {
        return waarde == null ? null : new BrpVerblijfsrechtCode(waarde.getOmschrijving());
    }

    /**
     * Map naar een BrpVerdragCode.
     * 
     * @param waarde
     *            BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static BrpVerdragCode mapBrpverdragCode(final Verdrag waarde) {
        return waarde == null ? null : new BrpVerdragCode(waarde.getOmschrijving());
    }

    /**
     * Map naar een BrpWijzeGebruikGeslachtsnaamCode.
     * 
     * @param waarde
     *            BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static BrpWijzeGebruikGeslachtsnaamCode mapBrpWijzeGebruikGeslachtnaamCode(
            final WijzeGebruikGeslachtsnaam waarde) {
        return waarde == null ? null : BrpWijzeGebruikGeslachtsnaamCode.valueOf(waarde.getCode());
    }

    /**
     * Map naar een Character.
     * 
     * @param waarde
     *            BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static Character mapCharacter(final String waarde) {
        return waarde == null || "".equals(waarde) ? null : waarde.charAt(0);
    }

    /**
     * Map naar een BrpDatum.
     * 
     * @param waarde
     *            BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static BrpDatum mapDatum(final BigDecimal waarde) {
        return waarde == null ? null : new BrpDatum(waarde.intValue());
    }

    /**
     * Map naar een int.
     * 
     * @param waarde
     *            BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static int mapInt(final Long waarde) {
        return waarde.intValue();
    }

    /**
     * Map naar een Integer.
     * 
     * @param waarde
     *            BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static Integer mapInteger(final BigDecimal waarde) {
        return waarde == null ? null : waarde.intValue();
    }

    /**
     * Map naar een Integer.
     * 
     * @param waarde
     *            BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static Integer mapInteger(final String waarde) {
        return waarde == null ? null : Integer.parseInt(waarde);
    }

    /**
     * Map naar een Long.
     * 
     * @param waarde
     *            BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static Long mapLong(final String waarde) {
        return waarde == null ? null : Long.parseLong(waarde);
    }

    /**
     * Map naar een Long.
     * 
     * @param waarde
     *            BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static Long mapLong(final BigDecimal waarde) {
        return waarde == null ? null : waarde.longValueExact();
    }

}
