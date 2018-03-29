/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd.exception;

/**
 * UnknownOpcodeException.
 */
public final class UnknownOpcodeException extends ParseException {

    private static final long serialVersionUID = 3173122875823667922L;

    private final String opcode;

    /**
     * Constructor.
     * @param opcode opcode
     */
    public UnknownOpcodeException(final String opcode) {
        super();
        this.opcode = opcode;
    }

    /**
     * Constructor.
     * @param opcode opcode
     * @param t inner exception
     */
    public UnknownOpcodeException(final String opcode, final Throwable t) {
        super(t);
        this.opcode = opcode;
    }

    public String getOpcode() {
        return opcode;
    }
}
