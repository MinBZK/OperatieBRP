/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPredicaatCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;

/**
 * Naamswijziging bij familierechtelijke betrekking.
 */
public final class BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingNaam {

    private final BrpPredicaatCode predicaatCode;
    private final BrpString voornamen;
    private final BrpString voorvoegsel;
    private final BrpCharacter scheidingsteken;
    private final BrpAdellijkeTitelCode adellijkeTitelCode;
    private final BrpString geslachtsnaamstam;

    /**
     * Constructor.
     * @param predicaatCode predicaat
     * @param voornamen voornamen
     * @param voorvoegsel voorvoegsel
     * @param scheidingsteken scheidingsteken
     * @param adellijkeTitelCode adellijke titel
     * @param geslachtsnaamstam geslachtsnaam
     */
    public BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingNaam(
            final BrpPredicaatCode predicaatCode,
            final BrpString voornamen,
            final BrpString voorvoegsel,
            final BrpCharacter scheidingsteken,
            final BrpAdellijkeTitelCode adellijkeTitelCode,
            final BrpString geslachtsnaamstam) {
        super();
        this.predicaatCode = predicaatCode;
        this.voornamen = voornamen;
        this.voorvoegsel = voorvoegsel;
        this.scheidingsteken = scheidingsteken;
        this.adellijkeTitelCode = adellijkeTitelCode;
        this.geslachtsnaamstam = geslachtsnaamstam;
    }

    /**
     * Geef de waarde van predicaat code van BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingNaam.
     * @return de waarde van predicaat code van BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingNaam
     */
    public BrpPredicaatCode getPredicaatCode() {
        return predicaatCode;
    }

    /**
     * Geef de waarde van voornamen van BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingNaam.
     * @return de waarde van voornamen van BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingNaam
     */
    public BrpString getVoornamen() {
        return voornamen;
    }

    /**
     * Geef de waarde van voorvoegsel van BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingNaam.
     * @return de waarde van voorvoegsel van BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingNaam
     */
    public BrpString getVoorvoegsel() {
        return voorvoegsel;
    }

    /**
     * Geef de waarde van scheidingsteken van BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingNaam.
     * @return de waarde van scheidingsteken van BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingNaam
     */
    public BrpCharacter getScheidingsteken() {
        return scheidingsteken;
    }

    /**
     * Geef de waarde van adellijke titel code van BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingNaam.
     * @return de waarde van adellijke titel code van BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingNaam
     */
    public BrpAdellijkeTitelCode getAdellijkeTitelCode() {
        return adellijkeTitelCode;
    }

    /**
     * Geef de waarde van geslachtsnaamstam van BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingNaam.
     * @return de waarde van geslachtsnaamstam van BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingNaam
     */
    public BrpString getGeslachtsnaamstam() {
        return geslachtsnaamstam;
    }

}
