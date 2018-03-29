/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.services.objectsleutel;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;

/**
 * Exception class die duidt op een ongeldige object sleutel. Bijvoorbeeld: niet te decrypten, niet te deserializen,
 * etc.
 */
public final class OngeldigeObjectSleutelException extends Exception {

    private static final long serialVersionUID = 1L;
    private final Regel regel;

    /**
     * Maakt een specifieke exceptie voor ongeldige object sleutel op basis van de optredende regel..
     *
     * @param regel
     *            de regel die getriggered.
     */
    public OngeldigeObjectSleutelException(final Regel regel) {
        this(regel, null);
    }

    /**
     * Maakt een specifieke exceptie voor ongeldige object sleutel op basis van de optredende regel..
     *
     * @param regel
     *            de regel die getriggered.
     * @param t
     *            de onderliggende exceptie die opgetreden is
     */
    public OngeldigeObjectSleutelException(final Regel regel, final Throwable t) {
        super(regel.getMelding(), t);
        this.regel = regel;
    }

    /**
     * @return Geeft de regel terug waardoor deze exceptie is getriggered
     */
    public Regel getRegel() {
        return regel;
    }
}
