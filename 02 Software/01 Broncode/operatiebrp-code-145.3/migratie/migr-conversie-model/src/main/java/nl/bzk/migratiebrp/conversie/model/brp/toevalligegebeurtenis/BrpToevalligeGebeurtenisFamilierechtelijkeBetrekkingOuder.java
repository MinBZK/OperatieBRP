/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;

/**
 * Ouder gegevens.
 */
public final class BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingOuder {

    private final BrpToevalligeGebeurtenisPersoon nieuweOuder;
    private final BrpToevalligeGebeurtenisPersoon oudeOuder;
    private final BrpDatum datumIngangFamilierechtelijkeBetrekking;

    /**
     * Constructor.
     * @param nieuweOuder nieuwe ouder
     * @param oudeOuder oude ouder
     * @param datumIngangFamilierechtelijkeBetrekking datum ingang familierechtelijke betrekking
     */
    public BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingOuder(
            final BrpToevalligeGebeurtenisPersoon nieuweOuder,
            final BrpToevalligeGebeurtenisPersoon oudeOuder,
            final BrpDatum datumIngangFamilierechtelijkeBetrekking) {
        super();
        this.nieuweOuder = nieuweOuder;
        this.oudeOuder = oudeOuder;
        this.datumIngangFamilierechtelijkeBetrekking = datumIngangFamilierechtelijkeBetrekking;
    }

    /**
     * Geef de waarde van nieuwe ouder van BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingOuder.
     * @return de waarde van nieuwe ouder van BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingOuder
     */
    public BrpToevalligeGebeurtenisPersoon getNieuweOuder() {
        return nieuweOuder;
    }

    /**
     * Geef de waarde van oude ouder van BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingOuder.
     * @return de waarde van oude ouder van BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingOuder
     */
    public BrpToevalligeGebeurtenisPersoon getOudeOuder() {
        return oudeOuder;
    }

    /**
     * Geef de waarde van datum ingang familierechtelijke betrekking van BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingOuder.
     * @return de waarde van datum ingang familierechtelijke betrekking van BrpToevalligeGebeurtenisFamilierechtelijkeBetrekkingOuder
     */
    public BrpDatum getDatumIngangFamilierechtelijkeBetrekking() {
        return datumIngangFamilierechtelijkeBetrekking;
    }

}
