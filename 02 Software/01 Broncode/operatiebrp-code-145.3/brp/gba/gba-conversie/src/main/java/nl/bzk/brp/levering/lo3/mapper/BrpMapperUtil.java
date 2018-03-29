/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import java.time.ZonedDateTime;
import java.util.Date;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenBeeindigingRelatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortDocument;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AdellijkeTitel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPredicaatCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortDocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;

/**
 * Mapper utilities.
 */
public interface BrpMapperUtil {

    /**
     * Map naar een BrpBoolean.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpBoolean mapBrpBoolean(final Boolean attribuut, final Lo3Onderzoek onderzoek) {
        BrpBoolean result = null;
        if (attribuut != null) {
            result = new BrpBoolean(attribuut, onderzoek);
        }
        return result;
    }

    /**
     * Map naar een BrpCharacter.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpCharacter mapBrpCharacter(final Character attribuut, final Lo3Onderzoek onderzoek) {
        BrpCharacter resultaat = null;
        if (attribuut != null) {
            resultaat = new BrpCharacter(attribuut, onderzoek);
        }
        return resultaat;
    }

    /**
     * Map naar een BrpDatum.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpDatum mapBrpDatum(final Integer attribuut, final Lo3Onderzoek onderzoek) {
        BrpDatum resultaat = null;
        if (attribuut != null) {
            resultaat = new BrpDatum(attribuut, onderzoek);
        }
        return resultaat;
    }

    /**
     * Map naar een BrpDatumTijd.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpDatumTijd mapBrpDatumTijd(final ZonedDateTime attribuut, final Lo3Onderzoek onderzoek) {
        BrpDatumTijd resultaat = null;
        if (attribuut != null) {
            resultaat = new BrpDatumTijd(Date.from(attribuut.toInstant()), onderzoek);
        }
        return resultaat;
    }

    /**
     * Map naar een BrpInteger.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpInteger mapBrpInteger(final Integer attribuut, final Lo3Onderzoek onderzoek) {
        BrpInteger resultaat = null;
        if (attribuut != null) {
            resultaat = new BrpInteger(attribuut, onderzoek);
        }
        return resultaat;
    }

    /**
     * Map naar een BrpLong.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpLong mapBrpLong(final Long attribuut, final Lo3Onderzoek onderzoek) {
        BrpLong resultaat = null;
        if (attribuut != null) {
            resultaat = new BrpLong(attribuut, onderzoek);
        }
        return resultaat;
    }

    /**
     * Map naar een BrpString.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpString mapBrpString(final String attribuut, final Lo3Onderzoek onderzoek) {
        BrpString resultaat = null;
        if (attribuut != null) {
            resultaat = new BrpString(attribuut, onderzoek);
        }
        return resultaat;
    }

    /* ************************************************************************************************************** */
    /* *** Attribuut conversie (specifiek) ************************************************************************** */
    /* ************************************************************************************************************** */

    /**
     * Map nadere aanduiding verval naar een BRP conversiemodel character.
     * @param nadereAanduidingVerval nadere aanduiding verval
     * @return brp character
     */
    static BrpCharacter mapNadereAanduidingVerval(final String nadereAanduidingVerval) {
        BrpCharacter resultaat = null;
        if (nadereAanduidingVerval != null) {
            resultaat = new BrpCharacter(nadereAanduidingVerval.charAt(0), null);
        }
        return resultaat;
    }

    /**
     * Map naar een BrpAdellijkeTitelCode.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpAdellijkeTitelCode mapBrpAdellijkeTitelCode(final AdellijkeTitel attribuut, final Lo3Onderzoek onderzoek) {
        BrpAdellijkeTitelCode resultaat = null;
        if (attribuut != null) {
            resultaat = new BrpAdellijkeTitelCode(attribuut.getCode(), onderzoek);
        }
        return resultaat;
    }

    /**
     * Map naar een BrpGemeenteCode.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpGemeenteCode mapBrpGemeenteCode(final Gemeente attribuut, final Lo3Onderzoek onderzoek) {
        BrpGemeenteCode resultaat = null;
        if (attribuut != null) {
            resultaat = new BrpGemeenteCode(attribuut.getCode(), onderzoek);
        }
        return resultaat;
    }

    /**
     * Map naar een BrpGeslachtsaanduidingCode.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpGeslachtsaanduidingCode mapBrpGeslachtsaanduidingCode(final Geslachtsaanduiding attribuut, final Lo3Onderzoek onderzoek) {
        BrpGeslachtsaanduidingCode resultaat = null;
        if (attribuut != null) {
            resultaat = new BrpGeslachtsaanduidingCode(attribuut.getCode(), onderzoek);
        }
        return resultaat;
    }

    /**
     * Map naar een BrpLandOfGebiedCode.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpLandOfGebiedCode mapBrpLandOfGebiedCode(final LandOfGebied attribuut, final Lo3Onderzoek onderzoek) {
        BrpLandOfGebiedCode resultaat = null;
        if (attribuut != null) {
            resultaat = new BrpLandOfGebiedCode(attribuut.getCode(), onderzoek);
        }
        return resultaat;
    }

    /**
     * Map naar een BrpPartijCode.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpPartijCode mapBrpPartijCode(final String attribuut, final Lo3Onderzoek onderzoek) {
        BrpPartijCode resultaat = null;
        if (attribuut != null) {
            resultaat = new BrpPartijCode(attribuut, onderzoek);
        }
        return resultaat;
    }

    /**
     * Map naar een BrpPartijCode.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpPartijCode mapBrpPartijCode(final Partij attribuut, final Lo3Onderzoek onderzoek) {
        BrpPartijCode resultaat = null;
        if (attribuut != null) {
            resultaat = new BrpPartijCode(attribuut.getCode(), onderzoek);
        }
        return resultaat;
    }

    /**
     * Map naar een BrpPredicaatCode.
     * @param attr BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpPredicaatCode mapBrpPredicaatCode(final Predicaat attr, final Lo3Onderzoek onderzoek) {
        BrpPredicaatCode resultaat = null;
        if (attr != null) {
            resultaat = new BrpPredicaatCode(attr.getCode(), onderzoek);
        }
        return resultaat;
    }

    /**
     * Map naar een BrpRedenEindeRelatieCode.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpRedenEindeRelatieCode mapBrpRedenEindeRelatieCode(final RedenBeeindigingRelatie attribuut, final Lo3Onderzoek onderzoek) {
        BrpRedenEindeRelatieCode resultaat = null;
        if (attribuut != null) {
            resultaat = new BrpRedenEindeRelatieCode(attribuut.getCode(), onderzoek);
        }
        return resultaat;
    }

    /**
     * Map naar een BrpSoortActieCode.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpSoortActieCode mapBrpSoortActieCode(final SoortActie attribuut, final Lo3Onderzoek onderzoek) {
        BrpSoortActieCode resultaat = null;
        if (attribuut != null && attribuut.getNaam() != null) {
            resultaat = new BrpSoortActieCode(attribuut.getNaam(), onderzoek);
        }
        return resultaat;
    }

    /**
     * Map naar een BrpSoortDocumentCode.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpSoortDocumentCode mapBrpSoortDocumentCode(final SoortDocument attribuut, final Lo3Onderzoek onderzoek) {
        BrpSoortDocumentCode resultaat = null;
        if (attribuut != null) {
            resultaat = new BrpSoortDocumentCode(attribuut.getNaam(), onderzoek);
        }
        return resultaat;
    }

    /**
     * Map naar een BrpSoortDocumentCode.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpSoortDocumentCode mapBrpSoortDocumentCode(final String attribuut, final Lo3Onderzoek onderzoek) {
        BrpSoortDocumentCode resultaat = null;
        if (attribuut != null) {
            resultaat = new BrpSoortDocumentCode(attribuut, onderzoek);
        }
        return resultaat;
    }

    /**
     * Map naar een BrpSoortNederlandsReisdocumentCode.
     * @param attribuut BRP database waarde
     * @param onderzoek LO3 Onderzoek
     * @return BRP conversiemodel waarde
     */
    static BrpSoortRelatieCode mapBrpSoortRelatieCode(final SoortRelatie attribuut, final Lo3Onderzoek onderzoek) {
        BrpSoortRelatieCode resultaat = null;
        if (attribuut != null) {
            resultaat = new BrpSoortRelatieCode(attribuut.getCode(), onderzoek);
        }
        return resultaat;
    }

}
