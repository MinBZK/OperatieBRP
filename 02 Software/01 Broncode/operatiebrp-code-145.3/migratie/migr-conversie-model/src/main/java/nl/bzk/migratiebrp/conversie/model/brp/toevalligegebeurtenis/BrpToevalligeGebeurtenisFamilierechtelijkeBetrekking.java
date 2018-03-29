/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis;

import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;

/**
 * Familierechtelijke betrekking.
 */
public final class BrpToevalligeGebeurtenisFamilierechtelijkeBetrekking {

    // Optioneel
    private final BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingNaam naamsWijziging;

    // Choice, min 1 max 1
    private final BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingOuder erkenning;
    private final BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingOuder ontkenning;
    private final BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingAdoptie adoptie;
    private final BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingOuder vernietiging;

    /**
     * Constructor.
     * @param naamsWijziging naamswijziging
     * @param erkenning erkenning
     * @param ontkenning ontkenning
     * @param adoptie adoptie
     * @param vernietiging vernietiging
     */
    public BrpToevalligeGebeurtenisFamilierechtelijkeBetrekking(
            final BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingNaam naamsWijziging,
            final BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingOuder erkenning,
            final BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingOuder ontkenning,
            final BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingAdoptie adoptie,
            final BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingOuder vernietiging) {
        super();
        this.naamsWijziging = naamsWijziging;
        this.erkenning = erkenning;
        this.ontkenning = ontkenning;
        this.adoptie = adoptie;
        this.vernietiging = vernietiging;

        ValidationUtils.checkExactEen(
                "Er moet precies 1 van erkenning, ontkenning, adoptie of vernietiging gevuld zijn",
                erkenning,
                ontkenning,
                adoptie,
                vernietiging);
    }

    /**
     * Geef de waarde van naams wijziging van BrpToevalligeGebeurtenisFamilierechtelijkeBetrekking.
     * @return de waarde van naams wijziging van BrpToevalligeGebeurtenisFamilierechtelijkeBetrekking
     */
    public BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingNaam getNaamsWijziging() {
        return naamsWijziging;
    }

    /**
     * Geef de waarde van erkenning van BrpToevalligeGebeurtenisFamilierechtelijkeBetrekking.
     * @return de waarde van erkenning van BrpToevalligeGebeurtenisFamilierechtelijkeBetrekking
     */
    public BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingOuder getErkenning() {
        return erkenning;
    }

    /**
     * Geef de waarde van ontkenning van BrpToevalligeGebeurtenisFamilierechtelijkeBetrekking.
     * @return de waarde van ontkenning van BrpToevalligeGebeurtenisFamilierechtelijkeBetrekking
     */
    public BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingOuder getOntkenning() {
        return ontkenning;
    }

    /**
     * Geef de waarde van adoptie van BrpToevalligeGebeurtenisFamilierechtelijkeBetrekking.
     * @return de waarde van adoptie van BrpToevalligeGebeurtenisFamilierechtelijkeBetrekking
     */
    public BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingAdoptie getAdoptie() {
        return adoptie;
    }

    /**
     * Geef de waarde van vernietiging van BrpToevalligeGebeurtenisFamilierechtelijkeBetrekking.
     * @return de waarde van vernietiging van BrpToevalligeGebeurtenisFamilierechtelijkeBetrekking
     */
    public BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingOuder getVernietiging() {
        return vernietiging;
    }

}
