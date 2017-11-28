/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.detailspersoon;

import nl.bzk.brp.domain.algemeen.Melding;

/**
 * ConveerterBevragingVerzoekElementException.
 */
final class ConverteerBevragingVerzoekElementException extends Exception {

    private static final long serialVersionUID = -2256404558768370854L;

    private static final String FOUT_MELDING = "Conversie fout opgetreden: ";
    private final Melding melding;

    /**
     * Constructor.
     * @param melding de foutmeldig die optreedt tijdens converteren
     */
    ConverteerBevragingVerzoekElementException(final Melding melding) {
        super(FOUT_MELDING + melding.getRegel().getMelding());
        this.melding = melding;
    }

    /**
     * Constructor.
     * @param melding de foutmeldig die optreedt tijdens converteren
     * @param exception exception
     */
    ConverteerBevragingVerzoekElementException(final Melding melding, final Exception exception) {
        super(FOUT_MELDING + melding.getRegel().getMelding(), exception);
        this.melding = melding;
    }

    /**
     * @return de foutmelding
     */
    public Melding getMelding() {
        return melding;
    }

}
