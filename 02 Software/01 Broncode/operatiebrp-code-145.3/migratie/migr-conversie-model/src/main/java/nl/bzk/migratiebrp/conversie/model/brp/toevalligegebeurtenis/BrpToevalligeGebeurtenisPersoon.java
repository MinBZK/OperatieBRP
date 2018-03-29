/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPredicaatCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;

/**
 * Persoon.
 */
public final class BrpToevalligeGebeurtenisPersoon {

    private final BrpString administratienummer;
    private final BrpString burgerservicenummer;

    private final BrpPredicaatCode predicaatCode;
    private final BrpString voornamen;
    private final BrpString voorvoegsel;
    private final BrpCharacter scheidingsteken;
    private final BrpAdellijkeTitelCode adellijkeTitelCode;
    private final BrpString geslachtsnaamstam;

    private final BrpDatum geboorteDatum;
    private final BrpGemeenteCode geboorteGemeenteCode;
    private final BrpString geboorteBuitenlandsePlaats;
    private final BrpLandOfGebiedCode geboorteLandOfGebiedCode;
    private final BrpString geboorteOmschrijvingLocatie;

    private final BrpGeslachtsaanduidingCode geslachtsaanduidingCode;

    /**
     * Constructor.
     * @param administratienummer anummer
     * @param burgerservicenummer bsn
     * @param predicaatCode predicaat
     * @param voornamen voornamen
     * @param voorvoegsel voorvoegsel
     * @param scheidingsteken scheidingsteken
     * @param adellijkeTitelCode adellijke titel
     * @param geslachtsnaamstam geslachtsnaam
     * @param geboorteDatum geboorte datum
     * @param geboorteGemeenteCode geboorte gemeente
     * @param geboorteBuitenlandsePlaats geboorte buitenlandse plaats
     * @param geboorteLandOfGebiedCode geboorte land
     * @param geboorteOmschrijvingLocatie geboorte omschrijving locatie
     * @param geslachtsaanduidingCode geslacht
     */
    public BrpToevalligeGebeurtenisPersoon(
            final BrpString administratienummer,
            final BrpString burgerservicenummer,
            final BrpPredicaatCode predicaatCode,
            final BrpString voornamen,
            final BrpString voorvoegsel,
            final BrpCharacter scheidingsteken,
            final BrpAdellijkeTitelCode adellijkeTitelCode,
            final BrpString geslachtsnaamstam,
            final BrpDatum geboorteDatum,
            final BrpGemeenteCode geboorteGemeenteCode,
            final BrpString geboorteBuitenlandsePlaats,
            final BrpLandOfGebiedCode geboorteLandOfGebiedCode,
            final BrpString geboorteOmschrijvingLocatie,
            final BrpGeslachtsaanduidingCode geslachtsaanduidingCode) {
        super();
        this.administratienummer = administratienummer;
        this.burgerservicenummer = burgerservicenummer;
        this.predicaatCode = predicaatCode;
        this.voornamen = voornamen;
        this.voorvoegsel = voorvoegsel;
        this.scheidingsteken = scheidingsteken;
        this.adellijkeTitelCode = adellijkeTitelCode;
        this.geslachtsnaamstam = geslachtsnaamstam;
        this.geboorteDatum = geboorteDatum;
        this.geboorteGemeenteCode = geboorteGemeenteCode;
        this.geboorteBuitenlandsePlaats = geboorteBuitenlandsePlaats;
        this.geboorteLandOfGebiedCode = geboorteLandOfGebiedCode;
        this.geboorteOmschrijvingLocatie = geboorteOmschrijvingLocatie;
        this.geslachtsaanduidingCode = geslachtsaanduidingCode;
    }

    /**
     * Geef de waarde van administratienummer van BrpToevalligeGebeurtenisPersoon.
     * @return de waarde van administratienummer van BrpToevalligeGebeurtenisPersoon
     */
    public BrpString getAdministratienummer() {
        return administratienummer;
    }

    /**
     * Geef de waarde van burgerservicenummer van BrpToevalligeGebeurtenisPersoon.
     * @return de waarde van burgerservicenummer van BrpToevalligeGebeurtenisPersoon
     */
    public BrpString getBurgerservicenummer() {
        return burgerservicenummer;
    }

    /**
     * Geef de waarde van predicaat code van BrpToevalligeGebeurtenisPersoon.
     * @return de waarde van predicaat code van BrpToevalligeGebeurtenisPersoon
     */
    public BrpPredicaatCode getPredicaatCode() {
        return predicaatCode;
    }

    /**
     * Geef de waarde van voornamen van BrpToevalligeGebeurtenisPersoon.
     * @return de waarde van voornamen van BrpToevalligeGebeurtenisPersoon
     */
    public BrpString getVoornamen() {
        return voornamen;
    }

    /**
     * Geef de waarde van voorvoegsel van BrpToevalligeGebeurtenisPersoon.
     * @return de waarde van voorvoegsel van BrpToevalligeGebeurtenisPersoon
     */
    public BrpString getVoorvoegsel() {
        return voorvoegsel;
    }

    /**
     * Geef de waarde van scheidingsteken van BrpToevalligeGebeurtenisPersoon.
     * @return de waarde van scheidingsteken van BrpToevalligeGebeurtenisPersoon
     */
    public BrpCharacter getScheidingsteken() {
        return scheidingsteken;
    }

    /**
     * Geef de waarde van adellijke titel code van BrpToevalligeGebeurtenisPersoon.
     * @return de waarde van adellijke titel code van BrpToevalligeGebeurtenisPersoon
     */
    public BrpAdellijkeTitelCode getAdellijkeTitelCode() {
        return adellijkeTitelCode;
    }

    /**
     * Geef de waarde van geslachtsnaamstam van BrpToevalligeGebeurtenisPersoon.
     * @return de waarde van geslachtsnaamstam van BrpToevalligeGebeurtenisPersoon
     */
    public BrpString getGeslachtsnaamstam() {
        return geslachtsnaamstam;
    }

    /**
     * Geef de waarde van geboorte datum van BrpToevalligeGebeurtenisPersoon.
     * @return de waarde van geboorte datum van BrpToevalligeGebeurtenisPersoon
     */
    public BrpDatum getGeboorteDatum() {
        return geboorteDatum;
    }

    /**
     * Geef de waarde van geboorte gemeente code van BrpToevalligeGebeurtenisPersoon.
     * @return de waarde van geboorte gemeente code van BrpToevalligeGebeurtenisPersoon
     */
    public BrpGemeenteCode getGeboorteGemeenteCode() {
        return geboorteGemeenteCode;
    }

    /**
     * Geef de waarde van geboorte buitenlandse plaats van BrpToevalligeGebeurtenisPersoon.
     * @return de waarde van geboorte buitenlandse plaats van BrpToevalligeGebeurtenisPersoon
     */
    public BrpString getGeboorteBuitenlandsePlaats() {
        return geboorteBuitenlandsePlaats;
    }

    /**
     * Geef de waarde van geboorte land of gebied code van BrpToevalligeGebeurtenisPersoon.
     * @return de waarde van geboorte land of gebied code van BrpToevalligeGebeurtenisPersoon
     */
    public BrpLandOfGebiedCode getGeboorteLandOfGebiedCode() {
        return geboorteLandOfGebiedCode;
    }

    /**
     * Geef de waarde van geboorte omschrijving locatie van BrpToevalligeGebeurtenisPersoon.
     * @return de waarde van geboorte omschrijving locatie van BrpToevalligeGebeurtenisPersoon
     */
    public BrpString getGeboorteOmschrijvingLocatie() {
        return geboorteOmschrijvingLocatie;
    }

    /**
     * Geef de waarde van geslachtsaanduiding code van BrpToevalligeGebeurtenisPersoon.
     * @return de waarde van geslachtsaanduiding code van BrpToevalligeGebeurtenisPersoon
     */
    public BrpGeslachtsaanduidingCode getGeslachtsaanduidingCode() {
        return geslachtsaanduidingCode;
    }

}
