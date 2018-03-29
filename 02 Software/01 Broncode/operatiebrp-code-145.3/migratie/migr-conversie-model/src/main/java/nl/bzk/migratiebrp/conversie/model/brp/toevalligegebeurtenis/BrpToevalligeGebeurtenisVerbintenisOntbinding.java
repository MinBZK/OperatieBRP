/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenEindeRelatieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;

/**
 * Verbintenis ontbinding.
 */
public final class BrpToevalligeGebeurtenisVerbintenisOntbinding {

    private final BrpDatum datum;

    private final BrpGemeenteCode gemeenteCode;
    private final BrpString buitenlandsePlaats;
    private final BrpLandOfGebiedCode landOfGebiedCode;
    private final BrpString omschrijvingLocatie;

    private final BrpRedenEindeRelatieCode redenEindeRelatieCode;

    /**
     * Constructor.
     * @param datum datum
     * @param gemeenteCode gemeente
     * @param buitenlandsePlaats buitenlandse plaats
     * @param landOfGebiedCode land
     * @param omschrijvingLocatie omschrijving locatie
     * @param redenEindeRelatieCode reden einde relatie
     */
    public BrpToevalligeGebeurtenisVerbintenisOntbinding(
            final BrpDatum datum,
            final BrpGemeenteCode gemeenteCode,
            final BrpString buitenlandsePlaats,
            final BrpLandOfGebiedCode landOfGebiedCode,
            final BrpString omschrijvingLocatie,
            final BrpRedenEindeRelatieCode redenEindeRelatieCode) {
        super();
        this.datum = datum;
        this.gemeenteCode = gemeenteCode;
        this.buitenlandsePlaats = buitenlandsePlaats;
        this.landOfGebiedCode = landOfGebiedCode;
        this.omschrijvingLocatie = omschrijvingLocatie;
        this.redenEindeRelatieCode = redenEindeRelatieCode;

        ValidationUtils.checkNietNull("Datum mag niet null zijn", this.datum);
        ValidationUtils.checkExactEen(
                "GemeenteCode, BuitenlandsePlaats of OmschrijvingLocatie moet gevuld zijn",
                this.gemeenteCode,
                this.buitenlandsePlaats,
                this.omschrijvingLocatie);
        ValidationUtils.checkNietNull("LandOfGebiedCode mag niet null zijn", this.landOfGebiedCode);
        ValidationUtils.checkNietNull("RedenEindeRelatieCode mag niet null zijn", this.redenEindeRelatieCode);
    }

    /**
     * Geef de waarde van datum van BrpToevalligeGebeurtenisVerbintenisOntbinding.
     * @return de waarde van datum van BrpToevalligeGebeurtenisVerbintenisOntbinding
     */
    public BrpDatum getDatum() {
        return datum;
    }

    /**
     * Geef de waarde van gemeente code van BrpToevalligeGebeurtenisVerbintenisOntbinding.
     * @return de waarde van gemeente code van BrpToevalligeGebeurtenisVerbintenisOntbinding
     */
    public BrpGemeenteCode getGemeenteCode() {
        return gemeenteCode;
    }

    /**
     * Geef de waarde van buitenlandse plaats van BrpToevalligeGebeurtenisVerbintenisOntbinding.
     * @return de waarde van buitenlandse plaats van BrpToevalligeGebeurtenisVerbintenisOntbinding
     */
    public BrpString getBuitenlandsePlaats() {
        return buitenlandsePlaats;
    }

    /**
     * Geef de waarde van land of gebied code van BrpToevalligeGebeurtenisVerbintenisOntbinding.
     * @return de waarde van land of gebied code van BrpToevalligeGebeurtenisVerbintenisOntbinding
     */
    public BrpLandOfGebiedCode getLandOfGebiedCode() {
        return landOfGebiedCode;
    }

    /**
     * Geef de waarde van reden einde relatie code van BrpToevalligeGebeurtenisVerbintenisOntbinding.
     * @return de waarde van reden einde relatie code van BrpToevalligeGebeurtenisVerbintenisOntbinding
     */
    public BrpRedenEindeRelatieCode getRedenEindeRelatieCode() {
        return redenEindeRelatieCode;
    }

}
