/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGeslachtsaanduidingCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPredicaatCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;

/**
 * Naam en/of geslachtswijziging.
 */
public final class BrpToevalligeGebeurtenisNaamGeslacht {

    private final BrpPredicaatCode predicaatCode;
    private final BrpString voornamen;
    private final BrpString voorvoegsel;
    private final BrpCharacter scheidingsteken;
    private final BrpAdellijkeTitelCode adellijkeTitelCode;
    private final BrpString geslachtsnaamstam;

    private final BrpGeslachtsaanduidingCode geslachtsaanduidingCode;

    /**
     * Constructor.
     * @param predicaatCode predicaat
     * @param voornamen voornamen
     * @param voorvoegsel voorvoegsel
     * @param scheidingsteken scheidingsteken
     * @param adellijkeTitelCode adellijke titel
     * @param geslachtsnaamstam geslachtsnaam
     * @param geslachtsaanduidingCode geslacht
     */
    public BrpToevalligeGebeurtenisNaamGeslacht(
            final BrpPredicaatCode predicaatCode,
            final BrpString voornamen,
            final BrpString voorvoegsel,
            final BrpCharacter scheidingsteken,
            final BrpAdellijkeTitelCode adellijkeTitelCode,
            final BrpString geslachtsnaamstam,
            final BrpGeslachtsaanduidingCode geslachtsaanduidingCode) {
        super();
        this.predicaatCode = predicaatCode;
        this.voornamen = voornamen;
        this.voorvoegsel = voorvoegsel;
        this.scheidingsteken = scheidingsteken;
        this.adellijkeTitelCode = adellijkeTitelCode;
        this.geslachtsnaamstam = geslachtsnaamstam;
        this.geslachtsaanduidingCode = geslachtsaanduidingCode;
    }

    /**
     * Geef de waarde van predicaat code van BrpToevalligeGebeurtenisNaamGeslacht.
     * @return de waarde van predicaat code van BrpToevalligeGebeurtenisNaamGeslacht
     */
    public BrpPredicaatCode getPredicaatCode() {
        return predicaatCode;
    }

    /**
     * Geef de waarde van voornamen van BrpToevalligeGebeurtenisNaamGeslacht.
     * @return de waarde van voornamen van BrpToevalligeGebeurtenisNaamGeslacht
     */
    public BrpString getVoornamen() {
        return voornamen;
    }

    /**
     * Geef de waarde van voorvoegsel van BrpToevalligeGebeurtenisNaamGeslacht.
     * @return de waarde van voorvoegsel van BrpToevalligeGebeurtenisNaamGeslacht
     */
    public BrpString getVoorvoegsel() {
        return voorvoegsel;
    }

    /**
     * Geef de waarde van scheidingsteken van BrpToevalligeGebeurtenisNaamGeslacht.
     * @return de waarde van scheidingsteken van BrpToevalligeGebeurtenisNaamGeslacht
     */
    public BrpCharacter getScheidingsteken() {
        return scheidingsteken;
    }

    /**
     * Geef de waarde van adellijke titel code van BrpToevalligeGebeurtenisNaamGeslacht.
     * @return de waarde van adellijke titel code van BrpToevalligeGebeurtenisNaamGeslacht
     */
    public BrpAdellijkeTitelCode getAdellijkeTitelCode() {
        return adellijkeTitelCode;
    }

    /**
     * Geef de waarde van geslachtsnaamstam van BrpToevalligeGebeurtenisNaamGeslacht.
     * @return de waarde van geslachtsnaamstam van BrpToevalligeGebeurtenisNaamGeslacht
     */
    public BrpString getGeslachtsnaamstam() {
        return geslachtsnaamstam;
    }

    /**
     * Geef de waarde van geslachtsaanduiding code van BrpToevalligeGebeurtenisNaamGeslacht.
     * @return de waarde van geslachtsaanduiding code van BrpToevalligeGebeurtenisNaamGeslacht
     */
    public BrpGeslachtsaanduidingCode getGeslachtsaanduidingCode() {
        return geslachtsaanduidingCode;
    }

}
