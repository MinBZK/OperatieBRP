/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;

/**
 * Overlijden.
 */
public final class BrpToevalligeGebeurtenisOverlijden {

    private final BrpDatum datum;
    private final BrpGemeenteCode gemeenteCode;
    private final BrpString buitenlandsePlaats;
    private final BrpLandOfGebiedCode landOfGebiedCode;
    private final BrpString omschrijvingLocatie;

    /**
     * Constructor.
     * @param datum datum
     * @param gemeenteCode gemeente
     * @param buitenlandsePlaats buitenlandse plaats
     * @param landOfGebiedCode land
     * @param omschrijvingLocatie omschrijving locatie
     */
    public BrpToevalligeGebeurtenisOverlijden(
            final BrpDatum datum,
            final BrpGemeenteCode gemeenteCode,
            final BrpString buitenlandsePlaats,
            final BrpLandOfGebiedCode landOfGebiedCode,
            final BrpString omschrijvingLocatie) {
        super();
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
     * Geef de waarde van datum van BrpToevalligeGebeurtenisOverlijden.
     * @return de waarde van datum van BrpToevalligeGebeurtenisOverlijden
     */
    public BrpDatum getDatum() {
        return datum;
    }

    /**
     * Geef de waarde van gemeente code van BrpToevalligeGebeurtenisOverlijden.
     * @return de waarde van gemeente code van BrpToevalligeGebeurtenisOverlijden
     */
    public BrpGemeenteCode getGemeenteCode() {
        return gemeenteCode;
    }

    /**
     * Geef de waarde van buitenlandse plaats van BrpToevalligeGebeurtenisOverlijden.
     * @return de waarde van buitenlandse plaats van BrpToevalligeGebeurtenisOverlijden
     */
    public BrpString getBuitenlandsePlaats() {
        return buitenlandsePlaats;
    }

    /**
     * Geef de waarde van land of gebied code van BrpToevalligeGebeurtenisOverlijden.
     * @return de waarde van land of gebied code van BrpToevalligeGebeurtenisOverlijden
     */
    public BrpLandOfGebiedCode getLandOfGebiedCode() {
        return landOfGebiedCode;
    }
}
