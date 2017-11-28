/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import java.util.Arrays;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.ElementBasisType;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.leveringmodel.MetaAttribuut;
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
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNaamgebruikCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNaamgebruikGeslachtsnaamstam;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNadereBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPredicaatCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenWijzigingVerblijfCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpReisdocumentAutoriteitVanAfgifteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortAdresCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortMigratieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortNederlandsReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpVerblijfsrechtCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;

/**
 * Mapper utilities voor MetaAtributen.
 */
public interface BrpMetaAttribuutMapper {

    /**
     * Check of een datatype van een MetaAttribuut van het verwachte type is.
     * @param attribuut attribuut wat gecheckt wordt
     * @param verwachteDatatypen de verwachtte typen
     */
    static void assertDataType(final MetaAttribuut attribuut, final ElementBasisType... verwachteDatatypen) {
        for (final ElementBasisType verwachtDatatype : verwachteDatatypen) {
            if (attribuut.getAttribuutElement().getDatatype() == verwachtDatatype) {
                return;
            }
        }

        throw new IllegalArgumentException(
                String.format(
                        "Attribuut %s met is niet van het verwachte datatype %s, maar %s.",
                        attribuut.getAttribuutElement(),
                        Arrays.asList(verwachteDatatypen),
                        attribuut.getAttribuutElement().getDatatype()));
    }

    /**
     * Map naar een BrpString.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpString mapBrpString(final MetaAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        BrpString resultaat = null;
        if (attribuut != null && attribuut.getWaarde() != null) {
            assertDataType(attribuut, ElementBasisType.STRING);
            resultaat = new BrpString(attribuut.getWaarde(), onderzoek);
        }
        return resultaat;
    }

    /**
     * Map naar een BrpNadereBijhoudingsaardCode.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpNadereBijhoudingsaardCode mapBrpNadereBijhoudingsaardCode(final MetaAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        BrpNadereBijhoudingsaardCode resultaat = null;
        if (attribuut != null && attribuut.getWaarde() != null) {
            assertDataType(attribuut, ElementBasisType.STRING);
            if (!attribuut.<String>getWaarde().isEmpty()) {
                resultaat = new BrpNadereBijhoudingsaardCode(attribuut.getWaarde(), onderzoek);
            }
        }
        return resultaat;
    }

    /**
     * Map naar een BrpVerblijfsrechtCode.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpVerblijfsrechtCode mapBrpVerblijfsrechtCode(final MetaAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        BrpVerblijfsrechtCode resultaat = null;
        if (attribuut != null && attribuut.getWaarde() != null) {
            assertDataType(attribuut, ElementBasisType.STRING);
            resultaat = new BrpVerblijfsrechtCode(attribuut.<String>getWaarde(), onderzoek);
        }
        return resultaat;
    }

    /**
     * Map naar een BrpAanduidingInhoudingOfVermissingReisdocumentCode.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpAanduidingInhoudingOfVermissingReisdocumentCode mapBrpAanduidingInhoudingOfVermissingReisdocumentCode(
            final MetaAttribuut attribuut,
            final Lo3Onderzoek onderzoek) {
        BrpAanduidingInhoudingOfVermissingReisdocumentCode resultaat = null;
        if (attribuut != null && attribuut.getWaarde() != null) {
            assertDataType(attribuut, ElementBasisType.STRING);
            if (!attribuut.<String>getWaarde().isEmpty()) {
                resultaat = new BrpAanduidingInhoudingOfVermissingReisdocumentCode(attribuut.<String>getWaarde().charAt(0), onderzoek);
            }
        }
        return resultaat;
    }

    /**
     * Map naar een BrpInteger.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpInteger mapBrpInteger(final MetaAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        BrpInteger resultaat = null;
        if (attribuut != null && attribuut.getWaarde() != null) {
            assertDataType(attribuut, ElementBasisType.GETAL);
            resultaat = new BrpInteger(attribuut.<Number>getWaarde().intValue(), onderzoek);
        }
        return resultaat;
    }

    /**
     * Map naar een BrpRedenVerkrijgingNederlandschapCode.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpRedenVerkrijgingNederlandschapCode mapBrpRedenVerkrijgingNederlandschapCode(final MetaAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        BrpRedenVerkrijgingNederlandschapCode resultaat = null;
        if (attribuut != null && attribuut.getWaarde() != null) {
            assertDataType(attribuut, ElementBasisType.STRING);
            resultaat = new BrpRedenVerkrijgingNederlandschapCode(attribuut.<String>getWaarde(), onderzoek);
        }
        return resultaat;
    }

    /* ************************************************************************************************************** */
    /* *** Attribuut conversie (algemeen) *************************************************************************** */
    /* ************************************************************************************************************** */

    /**
     * Map naar een BrpBoolean (als Ja).
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpBoolean mapBrpBooleanJa(final MetaAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        BrpBoolean resultaat = null;
        if (attribuut != null && attribuut.getWaarde() != null) {
            assertDataType(attribuut, ElementBasisType.BOOLEAN);
            if (Boolean.TRUE.equals(attribuut.<Boolean>getWaarde())) {
                resultaat = new BrpBoolean(Boolean.TRUE, onderzoek);
            }
        }
        return resultaat;
    }

    /**
     * Map naar een BrpReisdocumentAutoriteitVanAfgifteCode.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpReisdocumentAutoriteitVanAfgifteCode mapBrpReisdocumentAutoriteitVanAfgifteCode(
            final MetaAttribuut attribuut,
            final Lo3Onderzoek onderzoek) {
        BrpReisdocumentAutoriteitVanAfgifteCode resultaat = null;
        if (attribuut != null && attribuut.getWaarde() != null) {
            assertDataType(attribuut, ElementBasisType.STRING);
            if (!attribuut.<String>getWaarde().isEmpty()) {
                resultaat = new BrpReisdocumentAutoriteitVanAfgifteCode(attribuut.getWaarde(), onderzoek);
            }
        }
        return resultaat;
    }

    /**
     * Map naar een BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer mapBrpBuitenlandsPersoonsnummerAutoriteitVanAfgifteCode(
            final MetaAttribuut attribuut,
            final Lo3Onderzoek onderzoek) {
        BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer resultaat = null;
        if (attribuut != null && attribuut.getWaarde() != null) {
            assertDataType(attribuut, ElementBasisType.STRING);
            resultaat = new BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer(attribuut.<String>getWaarde(), onderzoek);
        }
        return resultaat;
    }

    /**
     * Map naar een BrpNaamgebruikCode.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpNaamgebruikCode mapBrpNaamgebruikCode(final MetaAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        BrpNaamgebruikCode resultaat = null;
        if (attribuut != null && attribuut.getWaarde() != null) {
            assertDataType(attribuut, ElementBasisType.STRING);
            if (!attribuut.<String>getWaarde().isEmpty()) {
                resultaat = new BrpNaamgebruikCode(attribuut.getWaarde(), onderzoek);
            }
        }
        return resultaat;
    }

    /**
     * Map naar een BrpNaamgebruikCode.
     * @param attribuut BRP database waarde
     * @param onderzoeken LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpNaamgebruikGeslachtsnaamstam mapBrpNaamgebruikGeslachtsnaamstam(final MetaAttribuut attribuut, final Set<Lo3Onderzoek> onderzoeken) {
        BrpNaamgebruikGeslachtsnaamstam resultaat = null;
        if (attribuut != null && attribuut.getWaarde() != null) {
            assertDataType(attribuut, ElementBasisType.STRING);
            if (!attribuut.<String>getWaarde().isEmpty()) {
                resultaat = new BrpNaamgebruikGeslachtsnaamstam(attribuut.getWaarde(), onderzoeken);
            }
        }
        return resultaat;
    }

    /**
     * Map een scheidingsteken naar een BrpCharacter; omdat een enkele spatie niet gemapped wordt door het leveringmodel
     * moet deze uitzondering bepaald worden adhv het voorvoegsel.
     * @param scheidingstekenAttribuut BRP database waarde
     * @param voorvoegselAttribuut BRP database waarde voor het voorvoegsel waar het scheidingsteken bij hoort
     * @param onderzoek LO3 Onderzoek
     * @return brp scheidingsteken
     */
    static BrpCharacter mapBrpCharacterScheidingsteken(
            final MetaAttribuut scheidingstekenAttribuut,
            final MetaAttribuut voorvoegselAttribuut,
            final Lo3Onderzoek onderzoek) {
        BrpCharacter resultaat = null;
        if (scheidingstekenAttribuut == null
                || scheidingstekenAttribuut.getWaarde() == null
                || scheidingstekenAttribuut.getWaarde().toString().isEmpty()) {
            if (voorvoegselAttribuut != null && voorvoegselAttribuut.getWaarde() != null && !"".equals(voorvoegselAttribuut.getWaarde())) {
                resultaat = new BrpCharacter(' ', onderzoek);
            }
        } else {
            assertDataType(scheidingstekenAttribuut, ElementBasisType.STRING);
            return new BrpCharacter(scheidingstekenAttribuut.getWaarde().toString().charAt(0), onderzoek);
        }
        return resultaat;
    }

    /**
     * Map naar een BrpCharacter.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpCharacter mapBrpCharacter(final MetaAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        BrpCharacter resultaat = null;
        if (attribuut != null && attribuut.getWaarde() != null && !attribuut.<String>getWaarde().isEmpty()) {
            assertDataType(attribuut, ElementBasisType.STRING);
            resultaat = new BrpCharacter(attribuut.<String>getWaarde().charAt(0), onderzoek);
        }
        return resultaat;
    }

    /**
     * Map naar een BrpSoortNederlandsReisdocumentCode.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpSoortNederlandsReisdocumentCode mapBrpSoortNederlandsReisdocumentCode(final MetaAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        BrpSoortNederlandsReisdocumentCode resultaat = null;
        if (attribuut != null && attribuut.getWaarde() != null) {
            assertDataType(attribuut, ElementBasisType.STRING);
            if (!attribuut.<String>getWaarde().isEmpty()) {
                resultaat = new BrpSoortNederlandsReisdocumentCode(attribuut.getWaarde(), onderzoek);
            }
        }
        return resultaat;
    }

    /**
     * Map naar een BrpAanduidingBijHuisnummerCode.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpAanduidingBijHuisnummerCode mapBrpAanduidingBijHuisnummerCode(final MetaAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        BrpAanduidingBijHuisnummerCode resultaat = null;
        if (attribuut != null && attribuut.getWaarde() != null) {
            assertDataType(attribuut, ElementBasisType.STRING);
            resultaat = new BrpAanduidingBijHuisnummerCode(attribuut.getWaarde(), onderzoek);
        }
        return resultaat;
    }

    /**
     * Map naar een BrpGemeenteCode.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpGemeenteCode mapBrpGemeenteCode(final MetaAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        BrpGemeenteCode resultaat = null;
        if (attribuut != null && attribuut.getWaarde() != null) {
            assertDataType(attribuut, ElementBasisType.STRING);
            resultaat = new BrpGemeenteCode(attribuut.<String>getWaarde(), onderzoek);
        }
        return resultaat;
    }

    /**
     * Map naar een BrpNationaliteitCode.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpNationaliteitCode mapBrpNationaliteitCode(final MetaAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        BrpNationaliteitCode resultaat = null;
        if (attribuut != null && attribuut.getWaarde() != null) {
            assertDataType(attribuut, ElementBasisType.STRING);
            resultaat = new BrpNationaliteitCode(attribuut.<String>getWaarde(), onderzoek);
        }
        return resultaat;
    }

    /**
     * Map naar een BrpRedenVerliesNederlandschapCode.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpRedenVerliesNederlandschapCode mapBrpRedenVerliesNederlanderschapCode(final MetaAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        BrpRedenVerliesNederlandschapCode resultaat = null;
        if (attribuut != null && attribuut.getWaarde() != null) {
            assertDataType(attribuut, ElementBasisType.STRING);
            resultaat = new BrpRedenVerliesNederlandschapCode(attribuut.<String>getWaarde(), onderzoek);
        }
        return resultaat;
    }

    /**
     * Map naar een BrpSoortNederlandsReisdocumentCode.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpSoortRelatieCode mapBrpSoortRelatieCode(final MetaAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        BrpSoortRelatieCode resultaat = null;
        if (attribuut != null && attribuut.getWaarde() != null) {
            assertDataType(attribuut, ElementBasisType.STRING);
            resultaat = new BrpSoortRelatieCode(attribuut.getWaarde(), onderzoek);
        }
        return resultaat;
    }

    /**
     * Map naar een BrpLong.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpLong mapBrpLong(final MetaAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        BrpLong resultaat = null;
        if (attribuut != null && attribuut.getWaarde() != null) {
            assertDataType(attribuut, ElementBasisType.GETAL, ElementBasisType.GROOTGETAL);
            resultaat = new BrpLong(attribuut.<Number>getWaarde().longValue(), onderzoek);
        }
        return resultaat;
    }

    /**
     * Map naar een BrpAdellijkeTitelCode.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpAdellijkeTitelCode mapBrpAdellijkeTitelCode(final MetaAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        BrpAdellijkeTitelCode resultaat = null;
        if (attribuut != null && attribuut.getWaarde() != null) {
            assertDataType(attribuut, ElementBasisType.STRING);
            if (!attribuut.<String>getWaarde().isEmpty()) {
                resultaat = new BrpAdellijkeTitelCode(attribuut.getWaarde(), onderzoek);
            }
        }
        return resultaat;
    }

    /**
     * Map naar een BrpGeslachtsaanduidingCode.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpGeslachtsaanduidingCode mapBrpGeslachtsaanduidingCode(final MetaAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        BrpGeslachtsaanduidingCode resultaat = null;
        if (attribuut != null && attribuut.getWaarde() != null) {
            assertDataType(attribuut, ElementBasisType.STRING);
            if (!attribuut.<String>getWaarde().isEmpty()) {
                resultaat = new BrpGeslachtsaanduidingCode(attribuut.getWaarde(), onderzoek);
            }
        }
        return resultaat;
    }

    /**
     * Map naar een BrpBoolean (als Ja/Nee).
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpBoolean mapBrpBooleanJaNee(final MetaAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        BrpBoolean resultaat = null;
        if (attribuut != null && attribuut.getWaarde() != null) {
            assertDataType(attribuut, ElementBasisType.BOOLEAN);
            resultaat = new BrpBoolean(attribuut.<Boolean>getWaarde(), onderzoek);
        }
        return resultaat;
    }

    /**
     * Map naar een BrpBijhoudingsaardCode.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpBijhoudingsaardCode mapBrpBijhoudingsaardCode(final MetaAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        BrpBijhoudingsaardCode resultaat = null;
        if (attribuut != null && attribuut.getWaarde() != null) {
            assertDataType(attribuut, ElementBasisType.STRING);
            if (!attribuut.<String>getWaarde().isEmpty()) {
                resultaat = new BrpBijhoudingsaardCode(attribuut.getWaarde(), onderzoek);
            }
        }
        return resultaat;
    }

    /**
     * Map naar een BrpAangeverCode.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpAangeverCode mapBrpAangeverCode(final MetaAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        BrpAangeverCode resultaat = null;
        if (attribuut != null && attribuut.getWaarde() != null) {
            assertDataType(attribuut, ElementBasisType.STRING);
            if (!attribuut.<String>getWaarde().isEmpty()) {
                resultaat = new BrpAangeverCode(attribuut.<String>getWaarde().charAt(0), onderzoek);
            }
        }
        return resultaat;
    }

    /**
     * Map naar een BrpBoolean (als Ja/Nee).
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpBoolean mapBrpBooleanNee(final MetaAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        BrpBoolean resultaat = null;
        if (attribuut != null && attribuut.getWaarde() != null) {
            assertDataType(attribuut, ElementBasisType.BOOLEAN);
            if (Boolean.FALSE.equals(attribuut.getWaarde())) {
                resultaat = new BrpBoolean(Boolean.FALSE, onderzoek);
            }
        }
        return resultaat;
    }

    /**
     * Map naar een BrpDatum.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpDatum mapBrpDatum(final MetaAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        BrpDatum resultaat = null;
        if (attribuut != null && attribuut.getWaarde() != null) {
            assertDataType(attribuut, ElementBasisType.DATUM);
            resultaat = new BrpDatum(attribuut.<Integer>getWaarde(), onderzoek);
        }
        return resultaat;
    }

    /**
     * Map naar een BrpRedenEindeRelatieCode.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpRedenEindeRelatieCode mapBrpRedenEindeRelatieCode(final MetaAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        BrpRedenEindeRelatieCode resultaat = null;
        if (attribuut != null && attribuut.<String>getWaarde() != null) {
            assertDataType(attribuut, ElementBasisType.STRING);
            resultaat = new BrpRedenEindeRelatieCode(attribuut.<String>getWaarde().charAt(0), onderzoek);
        }
        return resultaat;
    }

    /**
     * Map naar een BrpSoortAdresCode.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpSoortAdresCode mapBrpSoortAdresCode(final MetaAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        BrpSoortAdresCode resultaat = null;
        if (attribuut != null && attribuut.getWaarde() != null) {
            assertDataType(attribuut, ElementBasisType.STRING);
            if (!attribuut.<String>getWaarde().isEmpty()) {
                resultaat = new BrpSoortAdresCode(attribuut.getWaarde(), onderzoek);
            }
        }
        return resultaat;
    }

    /**
     * Map naar een BrpDatumTijd.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpDatumTijd mapBrpDatumTijd(final MetaAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        BrpDatumTijd resultaat = null;
        if (attribuut != null && attribuut.getWaarde() != null) {
            assertDataType(attribuut, ElementBasisType.DATUMTIJD);
            resultaat = new BrpDatumTijd(DatumUtil.vanDateTimeNaarDate(attribuut.getWaarde()), onderzoek);
        }
        return resultaat;
    }

    /**
     * Map naar een BrpSoortMigratieCode.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpSoortMigratieCode mapBrpSoortMigratieCode(final MetaAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        BrpSoortMigratieCode resultaat = null;
        if (attribuut != null && attribuut.getWaarde() != null) {
            assertDataType(attribuut, ElementBasisType.STRING);
            if (!attribuut.<String>getWaarde().isEmpty()) {
                resultaat = new BrpSoortMigratieCode(attribuut.getWaarde(), onderzoek);
            }
        }
        return resultaat;
    }

    /**
     * Map naar een BrpPredicaatCode.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpPredicaatCode mapBrpPredicaatCode(final MetaAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        BrpPredicaatCode resultaat = null;
        if (attribuut != null && attribuut.getWaarde() != null) {
            assertDataType(attribuut, ElementBasisType.STRING);
            if (!attribuut.<String>getWaarde().isEmpty()) {
                resultaat = new BrpPredicaatCode(attribuut.getWaarde(), onderzoek);
            }
        }
        return resultaat;
    }

    /**
     * Map naar een BrpPartijCode.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpPartijCode mapBrpPartijCode(final MetaAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        BrpPartijCode resultaat = null;
        if (attribuut != null && attribuut.getWaarde() != null) {
            assertDataType(attribuut, ElementBasisType.STRING);
            resultaat = new BrpPartijCode(attribuut.<String>getWaarde(), onderzoek);
        }
        return resultaat;
    }

    /**
     * Map naar een BrpLandOfGebiedCode.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpLandOfGebiedCode mapBrpLandOfGebiedCode(final MetaAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        BrpLandOfGebiedCode resultaat = null;
        if (attribuut != null && attribuut.getWaarde() != null) {
            assertDataType(attribuut, ElementBasisType.STRING);
            resultaat = new BrpLandOfGebiedCode(attribuut.<String>getWaarde(), onderzoek);
        }
        return resultaat;
    }

    /**
     * Map naar een BrpRedenWijzigingVerblijfCode.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpRedenWijzigingVerblijfCode mapBrpRedenWijzigingVerblijfCode(final MetaAttribuut attribuut, final Lo3Onderzoek onderzoek) {
        BrpRedenWijzigingVerblijfCode resultaat = null;
        if (attribuut != null && attribuut.getWaarde() != null) {
            assertDataType(attribuut, ElementBasisType.STRING);
            if (!attribuut.<String>getWaarde().isEmpty()) {
                resultaat = new BrpRedenWijzigingVerblijfCode(attribuut.<String>getWaarde().charAt(0), onderzoek);
            }
        }
        return resultaat;
    }
}
