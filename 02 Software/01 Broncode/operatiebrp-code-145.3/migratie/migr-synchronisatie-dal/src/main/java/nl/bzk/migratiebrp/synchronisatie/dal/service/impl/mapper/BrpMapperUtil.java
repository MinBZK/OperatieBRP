/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import java.sql.Timestamp;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AanduidingInhoudingOfVermissingReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Aangever;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AutoriteitAfgifteBuitenlandsPersoonsnummer;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Nationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenBeeindigingRelatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerkrijgingNLNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerliesNLNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenWijzigingVerblijf;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortDocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortNederlandsReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortPartij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Verblijfsrecht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AdellijkeTitel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.EffectAfnemerindicaties;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Naamgebruik;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Protocolleringsniveau;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingBijHuisnummerCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingInhoudingOfVermissingReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAangeverCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNaamgebruikCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNadereBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPredicaatCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenWijzigingVerblijfCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpReisdocumentAutoriteitVanAfgifteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortAdresCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortBetrokkenheidCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortNederlandsReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpVerblijfsrechtCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.autorisatie.BrpEffectAfnemerindicatiesCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.autorisatie.BrpProtocolleringsniveauCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.autorisatie.BrpStelselCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;

/**
 * Mapper utilities.
 */
public final class BrpMapperUtil {

    private BrpMapperUtil() {
        throw new AssertionError("Niet instantieerbaar");
    }

    /**
     * Map naar een BrpAanduidingBijHuisnummerCode.
     * @param waarde BRP database waarde
     * @param onderzoek LO3 Onderzoek object om aan het BrpAanduidingBijHuisnummerCode attribuut te koppelen. Mag null zijn.
     * @return BRP conversiemodel waarde
     */
    public static BrpAanduidingBijHuisnummerCode mapBrpAanduidingBijHuisnummerCode(final String waarde, final Lo3Onderzoek onderzoek) {
        if (waarde == null && onderzoek == null) {
            return null;
        } else {
            return new BrpAanduidingBijHuisnummerCode(waarde, onderzoek);
        }
    }

    /**
     * Map naar een BrpAangeverCode.
     * @param waarde BRP database waarde
     * @param onderzoek LO3 Onderzoek object om aan het BrpAangeverCode attribuut te koppelen. Mag null zijn.
     * @return BRP conversiemodel waarde
     */
    public static BrpAangeverCode mapBrpAangeverCode(final Aangever waarde, final Lo3Onderzoek onderzoek) {
        if (waarde == null && onderzoek == null) {
            return null;
        } else {
            final Character code = waarde != null ? waarde.getCode() : null;
            return new BrpAangeverCode(code, onderzoek);
        }
    }

    /**
     * Map naar een BrpAdellijkeTitelCode.
     * @param waarde BRP database waarde
     * @param onderzoek LO3 Onderzoek object om aan het BrpAdellijkeTitelCode attribuut te koppelen. Mag null zijn.
     * @return BRP conversiemodel waarde
     */
    public static BrpAdellijkeTitelCode mapBrpAdellijkeTitelCode(final AdellijkeTitel waarde, final Lo3Onderzoek onderzoek) {
        if (waarde == null && onderzoek == null) {
            return null;
        } else {
            final String code = waarde != null ? waarde.getCode() : null;
            return new BrpAdellijkeTitelCode(code, onderzoek);
        }
    }

    /**
     * Map naar een BrpReisdocumentAutoriteitVanAfgifteCode.
     * @param waarde BRP database waarde
     * @param onderzoek LO3 Onderzoek object om aan het BrpReisdocumentAutoriteitVanAfgifteCode attribuut te koppelen. Mag null zijn.
     * @return BRP conversiemodel waarde
     */
    public static BrpReisdocumentAutoriteitVanAfgifteCode mapBrpAutoriteitVanAfgifte(final String waarde, final Lo3Onderzoek onderzoek) {
        if (waarde == null && onderzoek == null) {
            return null;
        } else {
            return new BrpReisdocumentAutoriteitVanAfgifteCode(waarde, onderzoek);
        }
    }

    /**
     * Map naar een BrpBijhoudingsaardCode met een optioneel onderzoek.
     * @param waarde BRP database waarde
     * @param onderzoek LO3 Onderzoek object om aan het BrpDatum attribuut te koppelen. Mag null zijn.
     * @return BRP conversiemodel waarde
     */
    public static BrpBijhoudingsaardCode mapBrpBijhoudingsaard(final Bijhoudingsaard waarde, final Lo3Onderzoek onderzoek) {
        if (waarde == null && onderzoek == null) {
            return null;
        } else {
            final String code = waarde != null ? waarde.getCode() : null;
            return new BrpBijhoudingsaardCode(code, onderzoek);
        }
    }

    /**
     * Map naar een BrpBoolean met een optioneel onderzoek.
     * @param waarde BRP database waarde
     * @param onderzoek LO3 Onderzoek object om aan het BrpBoolean attribuut te koppelen. Mag null zijn.
     * @return BRP conversiemodel waarde
     */
    public static BrpBoolean mapBrpBoolean(final Boolean waarde, final Lo3Onderzoek onderzoek) {
        return BrpBoolean.wrap(waarde, onderzoek);
    }

    /**
     * Map naar een BrpLong met een optioneel onderzoek.
     * @param waarde BRP database waarde
     * @param onderzoek LO3 Onderzoek object om aan het BrpLong attribuut te koppelen. Mag null zijn.
     * @return BRP conversiemodel waarde
     */
    public static BrpLong mapBrpLong(final Long waarde, final Lo3Onderzoek onderzoek) {
        return BrpLong.wrap(waarde, onderzoek);
    }

    /**
     * Map naar een BrpCharacter met een optioneel onderzoek.
     * @param waarde BRP database waarde
     * @param onderzoek LO3 Onderzoek object om aan het BrpCharacter attribuut te koppelen. Mag null zijn.
     * @return BRP conversiemodel waarde
     */
    public static BrpCharacter mapBrpCharacter(final Character waarde, final Lo3Onderzoek onderzoek) {
        return BrpCharacter.wrap(waarde, onderzoek);
    }

    /**
     * Map naar een BrpString met een optioneel onderzoek.
     * @param waarde BRP database waarde
     * @param onderzoek LO3 Onderzoek object om aan het BrpString attribuut te koppelen. Mag null zijn.
     * @return BRP conversiemodel waarde
     */
    public static BrpString mapBrpString(final String waarde, final Lo3Onderzoek onderzoek) {
        return BrpString.wrap(waarde, onderzoek);
    }

    /**
     * Map naar een BrpDatumTijd.
     * @param waarde BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static BrpDatumTijd mapBrpDatumTijd(final Timestamp waarde) {
        return mapBrpDatumTijd(waarde, null);
    }

    /**
     * Map naar een BrpDatumTijd met optioneel onderzoek.
     * @param waarde BRP database waarde
     * @param onderzoek LO3 Onderzoek object om aan het BrpLong attribuut te koppelen. Mag null zijn.
     * @return BRP conversiemodel waarde
     */
    public static BrpDatumTijd mapBrpDatumTijd(final Timestamp waarde, final Lo3Onderzoek onderzoek) {
        return waarde == null && onderzoek == null ? null : new BrpDatumTijd(waarde, onderzoek);
    }

    /**
     * Map naar een BrpSoortAdresCode.
     * @param waarde BRP database waarde
     * @param onderzoek LO3 Onderzoek object om aan het BrpLong attribuut te koppelen. Mag null zijn.
     * @return BRP conversiemodel waarde
     */
    public static BrpSoortAdresCode mapBrpSoortAdresCode(final SoortAdres waarde, final Lo3Onderzoek onderzoek) {
        if (waarde == null && onderzoek == null) {
            return null;
        } else {
            final String code = waarde != null ? waarde.getCode() : null;
            return new BrpSoortAdresCode(code, onderzoek);
        }
    }

    /**
     * Map naar een BrpGemeenteCode met een optioneel onderzoek.
     * @param waarde BRP database waarde
     * @param onderzoek LO3 Onderzoek object om aan het BrpGemeenteCode attribuut te koppelen. Mag null zijn.
     * @return BRP conversiemodel waarde
     */
    public static BrpGemeenteCode mapBrpGemeenteCode(final Gemeente waarde, final Lo3Onderzoek onderzoek) {
        if (waarde == null && onderzoek == null) {
            return null;
        } else {
            final String code = waarde != null ? waarde.getCode() : null;
            return new BrpGemeenteCode(code, onderzoek);
        }
    }

    /**
     * Map naar een BrpGeslachtsaanduidingCode.
     * @param waarde BRP database waarde
     * @param onderzoek LO3 Onderzoek object om aan het BrpGeslachtsaanduidingCode attribuut te koppelen. Mag null zijn.
     * @return BRP conversiemodel waarde
     */
    public static BrpGeslachtsaanduidingCode mapBrpGeslachtsaanduidingCode(final Geslachtsaanduiding waarde, final Lo3Onderzoek onderzoek) {
        if (waarde == null && onderzoek == null) {
            return null;
        } else {
            final String code = waarde != null ? waarde.getCode() : null;
            return new BrpGeslachtsaanduidingCode(code, onderzoek);
        }
    }

    /**
     * Map naar een BrpLandOfGebiedCode met een optioneel onderzoek.
     * @param waarde BRP database waarde
     * @param onderzoek LO3 Onderzoek object om aan het BrpDatum attribuut te koppelen. Mag null zijn.
     * @return BRP conversiemodel waarde
     */
    public static BrpLandOfGebiedCode mapBrpLandOfGebiedCode(final LandOfGebied waarde, final Lo3Onderzoek onderzoek) {
        if (waarde == null && onderzoek == null) {
            return null;
        } else {
            final String code = waarde != null ? waarde.getCode() : null;
            return new BrpLandOfGebiedCode(code, onderzoek);
        }
    }

    /**
     * Map naar een BrpNationaliteitCode.
     * @param waarde BRP database waarde
     * @param onderzoek LO3 Onderzoek object om aan het BrpDatum attribuut te koppelen. Mag null zijn.
     * @return BRP conversiemodel waarde
     */
    public static BrpNationaliteitCode mapBrpNationaliteitCode(final Nationaliteit waarde, final Lo3Onderzoek onderzoek) {
        if (waarde == null && onderzoek == null) {
            return null;
        } else {
            final String code = waarde != null ? waarde.getCode() : null;
            return new BrpNationaliteitCode(code, onderzoek);
        }
    }

    /**
     * Map naar een BrpPartijCode.
     * @param waarde BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static BrpPartijCode mapBrpPartijCode(final Partij waarde) {
        return mapBrpPartijCode(waarde, null);
    }

    /**
     * Map naar een BrpPartijCode met een optioneel onderzoek..
     * @param waarde BRP database waarde
     * @param onderzoek LO3 Onderzoek object om aan het BrpDatum attribuut te koppelen. Mag null zijn.
     * @return BRP conversiemodel waarde
     */
    public static BrpPartijCode mapBrpPartijCode(final Partij waarde, final Lo3Onderzoek onderzoek) {
        if (waarde == null && onderzoek == null) {
            return null;
        } else {
            final String code = waarde != null ? waarde.getCode() : null;
            return new BrpPartijCode(code, onderzoek);
        }
    }

    /**
     * Map naar een BrpPredikaatCode.
     * @param waarde BRP database waarde
     * @param onderzoek LO3 Onderzoek object om aan het BrpPredikaatCode attribuut te koppelen. Mag null zijn.
     * @return BRP conversiemodel waarde
     */
    public static BrpPredicaatCode mapBrpPredicaatCode(final Predicaat waarde, final Lo3Onderzoek onderzoek) {
        if (waarde == null && onderzoek == null) {
            return null;
        } else {
            final String code = waarde != null ? waarde.getCode() : null;
            return new BrpPredicaatCode(code, onderzoek);
        }
    }

    /**
     * Map naar een BrpProtocolleringsniveauCode.
     * @param waarde BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static BrpProtocolleringsniveauCode mapBrpProtocolleringsniveauCode(final Protocolleringsniveau waarde) {
        return waarde == null ? null : new BrpProtocolleringsniveauCode(waarde.getCode());
    }

    /**
     * Map naar een BrpEffectAfnemerindicatiesCode.
     * @param waarde BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static BrpEffectAfnemerindicatiesCode mapBrpEffectAfnemersindicatiesCode(final EffectAfnemerindicaties waarde) {
        return waarde == null ? null : new BrpEffectAfnemerindicatiesCode((short) waarde.getId());
    }

    /**
     * Map naar een BrpStelselCode.
     * @param waarde BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static BrpStelselCode mapBrpStelselCode(final Stelsel waarde) {
        return waarde == null ? null : new BrpStelselCode((short) waarde.getId());
    }

    /**
     * Map naar een BrpRedenEindeRelatieCode.
     * @param waarde BRP database waarde
     * @param onderzoek LO3 Onderzoek object om aan het BrpRedenEindeRelatieCode attribuut te koppelen. Mag null zijn.
     * @return BRP conversiemodel waarde
     */
    public static BrpRedenEindeRelatieCode mapBrpRedenEindeRelatieCode(final RedenBeeindigingRelatie waarde, final Lo3Onderzoek onderzoek) {
        if (waarde == null && onderzoek == null) {
            return null;
        } else {
            final Character code = waarde != null ? waarde.getCode() : null;
            return new BrpRedenEindeRelatieCode(code, onderzoek);
        }
    }

    /**
     * Map naar een BrpNadereBijhoudingsaardCode met een optioneel onderzoek.
     * @param waarde BRP database waarde
     * @param onderzoek LO3 Onderzoek object om aan het BrpDatum attribuut te koppelen. Mag null zijn.
     * @return BRP conversiemodel waarde
     */
    public static BrpNadereBijhoudingsaardCode mapBrpNadereBijhoudingsaard(final NadereBijhoudingsaard waarde, final Lo3Onderzoek onderzoek) {
        if (waarde == null && onderzoek == null) {
            return null;
        } else {
            final String code = waarde != null ? waarde.getCode() : null;
            return new BrpNadereBijhoudingsaardCode(code, onderzoek);
        }
    }

    /**
     * Map naar een BrpRedenVerkrijgingNederlandschapCode.
     * @param waarde BRP database waarde
     * @param onderzoek LO3 Onderzoek object om aan het BrpDatum attribuut te koppelen. Mag null zijn.
     * @return BRP conversiemodel waarde
     */
    public static BrpRedenVerkrijgingNederlandschapCode mapBrpRedenVerkrijgingNederlandschapCode(
            final RedenVerkrijgingNLNationaliteit waarde,
            final Lo3Onderzoek onderzoek) {
        if (waarde == null && onderzoek == null) {
            return null;
        } else {
            final String code = waarde != null ? waarde.getCode() : null;
            return new BrpRedenVerkrijgingNederlandschapCode(code, onderzoek);
        }
    }

    /**
     * Map naar een BrpRedenVerliesNederlandschapCode.
     * @param waarde BRP database waarde
     * @param onderzoek LO3 Onderzoek object om aan het BrpDatum attribuut te koppelen. Mag null zijn.
     * @return BRP conversiemodel waarde
     */
    public static BrpRedenVerliesNederlandschapCode mapBrpRedenVerliesNederlanderschapCode(
            final RedenVerliesNLNationaliteit waarde,
            final Lo3Onderzoek onderzoek) {
        if (waarde == null && onderzoek == null) {
            return null;
        } else {
            final String code = waarde != null ? waarde.getCode() : null;
            return new BrpRedenVerliesNederlandschapCode(code, onderzoek);
        }
    }

    /**
     * Map naar een BrpRedenWijzigingVerblijfCode.
     * @param waarde BRP database waarde
     * @param onderzoek LO3 Onderzoek object om aan het BrpDatum attribuut te koppelen. Mag null zijn.
     * @return BRP conversiemodel waarde
     */
    public static BrpRedenWijzigingVerblijfCode mapBrpRedenWijzigingVerblijfCode(final RedenWijzigingVerblijf waarde, final Lo3Onderzoek onderzoek) {
        if (waarde == null && onderzoek == null) {
            return null;
        } else {
            final Character code = waarde != null ? waarde.getCode() : null;
            return new BrpRedenWijzigingVerblijfCode(code, onderzoek);
        }
    }

    /**
     * Map naar een BrpAanduidingInhoudingOfVermissingReisdocumentCode.
     * @param waarde BRP database waarde
     * @param onderzoek LO3 Onderzoek object om aan het BrpAanduidingInhoudingOfVermissingReisdocumentCode attribuut te koppelen. Mag null zijn.
     * @return BRP conversiemodel waarde
     */
    public static BrpAanduidingInhoudingOfVermissingReisdocumentCode mapBrpAanduidingInhoudingOfVermissingReisdocument(
            final AanduidingInhoudingOfVermissingReisdocument waarde,
            final Lo3Onderzoek onderzoek) {
        if (waarde == null && onderzoek == null) {
            return null;
        } else {
            final Character code = waarde != null ? waarde.getCode() : null;
            return new BrpAanduidingInhoudingOfVermissingReisdocumentCode(code, onderzoek);
        }
    }

    /**
     * Map naar een BrpSoortNederlandsReisdocumentCode.
     * @param waarde BRP database waarde
     * @param onderzoek LO3 Onderzoek object om aan het BrpAanduidingInhoudingOfVermissingReisdocumentCode attribuut te koppelen. Mag null zijn.
     * @return BRP conversiemodel waarde
     */
    public static BrpSoortNederlandsReisdocumentCode mapBrpReisdocumentSoort(final SoortNederlandsReisdocument waarde, final Lo3Onderzoek onderzoek) {
        if (waarde == null && onderzoek == null) {
            return null;
        } else {
            final String code = waarde != null ? waarde.getCode() : null;
            return new BrpSoortNederlandsReisdocumentCode(code, onderzoek);
        }
    }

    /**
     * Map naar een BrpSoortActieCode.
     * @param waarde BRP database waarde
     * @param onderzoek LO3 Onderzoek object om aan het BrpSoortDocumentCode attribuut te koppelen. Mag null zijn.
     * @return BRP conversiemodel waarde
     */
    public static BrpSoortActieCode mapBrpSoortActieCode(final SoortActie waarde, final Lo3Onderzoek onderzoek) {
        if (waarde == null && onderzoek == null) {
            return null;
        } else {
            final String code = waarde != null ? waarde.getNaam() : null;
            return new BrpSoortActieCode(code, onderzoek);
        }
    }

    /**
     * Map naar een BrpSoortBetrokkenheidCode.
     * @param waarde BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static BrpSoortBetrokkenheidCode mapBrpSoortBetrokkenheidCode(final SoortBetrokkenheid waarde) {
        return waarde == null ? null : new BrpSoortBetrokkenheidCode(waarde.getCode(), waarde.getNaam());
    }

    /**
     * Map naar een BrpSoortDocumentCode.
     * @param waarde BRP database waarde
     * @param onderzoek LO3 Onderzoek object om aan het BrpSoortDocumentCode attribuut te koppelen. Mag null zijn.
     * @return BRP conversiemodel waarde
     */
    public static BrpSoortDocumentCode mapBrpSoortDocumentCode(final SoortDocument waarde, final Lo3Onderzoek onderzoek) {
        if (waarde == null && onderzoek == null) {
            return null;
        } else {
            final String naam = waarde != null ? waarde.getNaam() : null;
            return new BrpSoortDocumentCode(naam, onderzoek);
        }
    }

    /**
     * Map naar een BrpSoortPartijCode.
     * @param waarde BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static BrpSoortPartijCode mapBrpSoortPartijCode(final SoortPartij waarde) {
        return waarde == null ? null : new BrpSoortPartijCode(waarde.getNaam());
    }

    /**
     * Map naar een BrpSoortRelatieCode.
     * @param waarde BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static BrpSoortRelatieCode mapBrpSoortRelatieCode(final SoortRelatie waarde) {
        return waarde == null ? null : new BrpSoortRelatieCode(waarde.getCode());
    }

    /**
     * Map naar een BrpVerblijfsrechtCode.
     * @param waarde BRP database waarde
     * @param onderzoek LO3 Onderzoek object om aan het BrpVerblijfsrechtCode attribuut te koppelen. Mag null zijn.
     * @return BRP conversiemodel waarde
     */
    public static BrpVerblijfsrechtCode mapBrpVerblijfsrechtCode(final Verblijfsrecht waarde, final Lo3Onderzoek onderzoek) {
        if (waarde == null && onderzoek == null) {
            return null;
        } else {
            final String code = waarde != null ? waarde.getCode() : null;
            return new BrpVerblijfsrechtCode(code, onderzoek);
        }
    }

    /**
     * Map naar een BrpNaamgebruikCode.
     * @param waarde BRP database waarde
     * @param onderzoek LO3 Onderzoek object om aan het BrpNaamgebruikCode attribuut te koppelen. Mag null zijn.
     * @return BRP conversiemodel waarde
     */
    public static BrpNaamgebruikCode mapBrpNaamgebruikCode(final Naamgebruik waarde, final Lo3Onderzoek onderzoek) {
        if (waarde == null && onderzoek == null) {
            return null;
        } else {
            final String code = waarde != null ? waarde.getCode() : null;
            return new BrpNaamgebruikCode(code, onderzoek);
        }
    }

    /**
     * Map naar een Character.
     * @param waarde BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static Character mapCharacter(final String waarde) {
        return waarde == null || "".equals(waarde) ? null : waarde.charAt(0);
    }

    /**
     * Map naar een BrpDatum.
     * @param waarde BRP database waarde
     * @return BRP conversiemodel waarde
     */
    public static BrpDatum mapDatum(final Integer waarde) {
        return mapDatum(waarde, null);
    }

    /**
     * Map naar een BrpDatum met een optioneel onderzoek.
     * @param waarde BRP database waarde
     * @param onderzoek LO3 Onderzoek object om aan het BrpDatum attribuut te koppelen. Mag null zijn.
     * @return BRP conversiemodel waarde
     */
    public static BrpDatum mapDatum(final Integer waarde, final Lo3Onderzoek onderzoek) {
        if (waarde == null && onderzoek == null) {
            return null;
        } else {
            return new BrpDatum(waarde, onderzoek);
        }
    }

    /**
     * Mapt een {@link AutoriteitAfgifteBuitenlandsPersoonsnummer} naar een
     * {@link BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer} met een optioneel {@link Lo3Onderzoek}.
     * @param autoriteitAfgifteBuitenlandsPersoonsnummer BRP database waarde
     * @param onderzoek LO3 Onderzoek. Null als het object niet in onderzoek staat
     * @return een {@link BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer}
     */
    public static BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer mapBrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer(
            final AutoriteitAfgifteBuitenlandsPersoonsnummer autoriteitAfgifteBuitenlandsPersoonsnummer,
            final Lo3Onderzoek onderzoek) {
        final String autoriteitVanAfgifte =
                autoriteitAfgifteBuitenlandsPersoonsnummer == null ? null : autoriteitAfgifteBuitenlandsPersoonsnummer.getNationaliteit().getCode();
        if (autoriteitAfgifteBuitenlandsPersoonsnummer == null && onderzoek == null) {
            return null;
        } else {
            return new BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer(autoriteitVanAfgifte, onderzoek);
        }
    }

}
