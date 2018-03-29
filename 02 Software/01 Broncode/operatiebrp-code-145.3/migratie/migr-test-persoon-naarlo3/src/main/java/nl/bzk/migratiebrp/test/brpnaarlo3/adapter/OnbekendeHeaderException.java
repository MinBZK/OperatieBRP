/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.brpnaarlo3.adapter;


/**
 * Wordt gegooid als er een header in {@link DataObject} zit, die de converter niet kent.
 */
public final class OnbekendeHeaderException extends RuntimeException {

    private static final long serialVersionUID = 1724655179204639610L;

    /**
     * Constructor voor {@link OnbekendeHeaderException}.
     * @param header header die onbekend is
     * @param converterName converter object waarin de exception gegooid wordt.
     */
    public OnbekendeHeaderException(final String header, final String converterName) {
        super(String.format("Onverwachte element (%1s) voor %2s.", header, converterName));
    }
}
