/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.autorisatie;

/**
 * Exceptie die gegooid wordt als de partij niet gevonden kan worden. Dit is en runtime fout omdat het een technische fout betreft die verder niet
 * afgehandeld dient te worden.
 */
public class PartijNietGevondenExceptie extends RuntimeException {

    private static final long serialVersionUID = 5370540891261235453L;

    /**
     * Constructor.
     * @param message het foutbericht
     */
    public PartijNietGevondenExceptie(final String message) {
        super(message);
    }
}
