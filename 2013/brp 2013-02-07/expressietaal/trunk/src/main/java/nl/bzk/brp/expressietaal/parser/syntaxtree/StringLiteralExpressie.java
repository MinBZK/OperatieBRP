/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.syntaxtree;

import java.util.Date;

/**
 * Representeert een stringconstante.
 */
public class StringLiteralExpressie extends AbstractLiteralExpressie {

    private final String value;

    /**
     * Constructor.
     *
     * @param value De waarde van het vaste-waardeobject.
     */
    public StringLiteralExpressie(final String value) {
        this.value = value;
    }

    /**
     * Constructor.
     *
     * @param value De waarde van het vaste-waardeobject.
     */
    public StringLiteralExpressie(final Date value) {
        this.value = value.toString();
        //TODO: DateTime type voor expressies
    }

    /**
     * Constructor.
     *
     * @param value De waarde van het vaste-waardeobject.
     */
    public StringLiteralExpressie(final int value) {
        this.value = String.valueOf(value);
        //TODO: Later aanpassen als BMR-code opnieuw gegenereerd is.
    }

    /**
     * Constructor.
     *
     * @param value De waarde van het vaste-waardeobject.
     */
    public StringLiteralExpressie(final long value) {
        this.value = String.valueOf(value);
        //TODO: Later aanpassen als BMR-code opnieuw gegenereerd is.
    }


    public final String getValue() {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final ExpressieType getType() {
        return ExpressieType.STRING;
    }

    @Override
    public final boolean isRootObject() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String alsLeesbareString() {
        return "\"" + value + "\"";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String alsFormeleString() {
        return alsLeesbareString();
    }
}

