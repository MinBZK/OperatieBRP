/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.common.serialisatie;

/**
 * Exceptie die gebruikt wordt bij fouten in het (de-) serialiseren van objecten.
 */
public class SerialisatieExceptie extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * De constructor voor deze klasse.
     *
     * @param melding De melding.
     * @param oorzaak De oorzaak exceptie.
     */
    public SerialisatieExceptie(final String melding, final Throwable oorzaak) {
        super(melding, oorzaak);
    }

}
