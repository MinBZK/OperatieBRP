/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.writer;

import java.io.File;
import java.util.regex.Pattern;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

/**
 * Abstract I/O Factory.
 */
public abstract class AbstractIOFactory {
    /**
     * Logger object.
     */
    protected static final Logger LOG = LoggerFactory.getLogger();
    /**
     * Reguliere expressie om de extensie van een bestand te kunnen achterhalen.
     */
    protected static final Pattern PATTERN_EXTENSIE = Pattern.compile("\\.([a-z0-9]{3,}$)");
    /**
     * Extensie voor een CSV-bestand.
     */
    protected static final String INPUT_EXTENSIE_CSV = "csv";

    /**
     * Extensie voor een TXT-bestand.
     */
    protected static final String INPUT_EXTENSIE_TXT = "txt";

    /**
     * Controleert of de factory een I/O (reader/writer) heeft die de aangeboden file kan verwerken.
     * 
     * @param file
     *            het te lezen bestand
     * @return true als er I/O voor mogelijk is, false als dit niet zo is.
     */
    public abstract boolean accept(final File file);

}
