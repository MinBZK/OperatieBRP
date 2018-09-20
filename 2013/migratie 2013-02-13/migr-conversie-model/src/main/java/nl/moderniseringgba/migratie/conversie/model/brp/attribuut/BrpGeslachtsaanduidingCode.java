/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.attribuut;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpAttribuut;

/**
 * Deze enum representeert de BRP geslachtsaanduiding.
 * 
 */
public enum BrpGeslachtsaanduidingCode implements BrpAttribuut {

    /** Man. */
    MAN("M"),
    /** Vrouw. */
    VROUW("V"),
    /** Onbekend. */
    ONBEKEND("O");

    private final String code;

    private BrpGeslachtsaanduidingCode(final String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    /**
     * Vertaalt de gegeven BRP code voor geslachtsaanduiding (M(Man), V2(Vrouw), O(Onbekend) in een
     * BrpGeslachtsaanduidingCode.
     * 
     * @param code
     *            M, V, O
     * @return een BrpGeslachtsaanduidingCode object
     */
    public static final BrpGeslachtsaanduidingCode valueOfBrpCode(final String code) {
        for (final BrpGeslachtsaanduidingCode geslachtsaanduiding : BrpGeslachtsaanduidingCode.values()) {
            if (geslachtsaanduiding.getCode().equals(code)) {
                return geslachtsaanduiding;
            }
        }
        throw new IllegalArgumentException("Onbekende BRP code: " + code);
    }
}
