/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis;

/**
 * Adoptie.
 */
public final class BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingAdoptie {

    private final BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingOuder ouder1;
    private final BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingOuder ouder2;

    /**
     * Constructor.
     * @param ouder1 eerste ouder
     * @param ouder2 tweede ouder
     */
    public BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingAdoptie(
            final BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingOuder ouder1,
            final BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingOuder ouder2) {
        super();
        this.ouder1 = ouder1;
        this.ouder2 = ouder2;
    }

    /**
     * Geef de waarde van ouder1 van BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingAdoptie.
     * @return de waarde van ouder1 van BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingAdoptie
     */
    public BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingOuder getOuder1() {
        return ouder1;
    }

    /**
     * Geef de waarde van ouder2 van BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingAdoptie.
     * @return de waarde van ouder2 van BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingAdoptie
     */
    public BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingOuder getOuder2() {
        return ouder2;
    }
}
