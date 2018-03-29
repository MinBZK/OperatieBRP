/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;

/**
 * Verbintenis sluiting.
 */
public final class BrpToevalligeGebeurtenisVerbintenisSluiting {

    private final BrpDatum datum;

    private final BrpSoortRelatieCode relatieCode;
    private final BrpGemeenteCode gemeenteCode;
    private final BrpString buitenlandsePlaats;
    private final BrpLandOfGebiedCode landOfGebiedCode;
    private final BrpString omschrijvingLocatie;

    /**
     * Constructor.
     * @param relatieCode soort verbintenis
     * @param datum datum
     * @param gemeenteCode gemeente
     * @param buitenlandsePlaats buitenlandse plaats
     * @param landOfGebiedCode land
     * @param omschrijvingLocatie omschrijving locatie
     */
    public BrpToevalligeGebeurtenisVerbintenisSluiting(
            final BrpSoortRelatieCode relatieCode,
            final BrpDatum datum,
            final BrpGemeenteCode gemeenteCode,
            final BrpString buitenlandsePlaats,
            final BrpLandOfGebiedCode landOfGebiedCode,
            final BrpString omschrijvingLocatie) {
        super();
        this.relatieCode = relatieCode;
        this.datum = datum;
        this.gemeenteCode = gemeenteCode;
        this.buitenlandsePlaats = buitenlandsePlaats;
        this.landOfGebiedCode = landOfGebiedCode;
        this.omschrijvingLocatie = omschrijvingLocatie;

        ValidationUtils.checkNietNull("Datum mag niet null zijn", this.datum);
        ValidationUtils.checkExactEen(
                "GemeenteCode, BuitenlandsePlaats of OmschrijvingLocatie moet gevuld zijn",
                this.gemeenteCode,
                this.buitenlandsePlaats,
                this.omschrijvingLocatie);
        ValidationUtils.checkNietNull("LandOfGebiedCode mag niet null zijn", this.landOfGebiedCode);
    }

    /**
     * Geef de waarde van datum van BrpToevalligeGebeurtenisVerbintenisSluiting.
     * @return de waarde van datum van BrpToevalligeGebeurtenisVerbintenisSluiting
     */
    public BrpDatum getDatum() {
        return datum;
    }

    /**
     * Geef de waarde van gemeente code van BrpToevalligeGebeurtenisVerbintenisSluiting.
     * @return de waarde van gemeente code van BrpToevalligeGebeurtenisVerbintenisSluiting
     */
    public BrpGemeenteCode getGemeenteCode() {
        return gemeenteCode;
    }

    /**
     * Geef de waarde van buitenlandse plaats van BrpToevalligeGebeurtenisVerbintenisSluiting.
     * @return de waarde van buitenlandse plaats van BrpToevalligeGebeurtenisVerbintenisSluiting
     */
    public BrpString getBuitenlandsePlaats() {
        return buitenlandsePlaats;
    }

    /**
     * Geef de waarde van land of gebied code van BrpToevalligeGebeurtenisVerbintenisSluiting.
     * @return de waarde van land of gebied code van BrpToevalligeGebeurtenisVerbintenisSluiting
     */
    public BrpLandOfGebiedCode getLandOfGebiedCode() {
        return landOfGebiedCode;
    }

    /**
     * Geef de waarde van relatie code van BrpToevalligeGebeurtenisVerbintenisSluiting.
     * @return de waarde van relatie code van BrpToevalligeGebeurtenisVerbintenisSluiting
     */
    public BrpSoortRelatieCode getRelatieCode() {
        return relatieCode;
    }

    /**
     * Geef de waarde van omschrijving locatie van BrpToevalligeGebeurtenisVerbintenisSluiting.
     * @return de waarde van omschrijving locatie van BrpToevalligeGebeurtenisVerbintenisSluiting
     */
    public BrpString getOmschrijvingLocatie() {
        return omschrijvingLocatie;
    }

}
