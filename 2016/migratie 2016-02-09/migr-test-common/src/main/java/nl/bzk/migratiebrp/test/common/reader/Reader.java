/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.reader;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3Lg01BerichtWaarde;

/**
 * Reader.
 */
public interface Reader {
    /**
     * Lees de inhoud van een bestand.
     * 
     * @param file
     *            bestand
     * @return inhoud
     * @throws IOException
     *             bij lees fouten
     */
    String readFile(File file) throws IOException;

    /**
     * Leest de inhoud van een bestand en geeft een lijst met
     * {@link nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3Lg01BerichtWaarde}.
     * 
     * @param file
     *            bestand
     * @return een lijst met lijsten van {@link nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3Lg01BerichtWaarde}
     * @throws IOException
     *             bij leesfouten
     * @throws Lo3SyntaxException
     *             exception wordt gegooid als er geen geldige Lo3 syntax wordt herkend
     */
    List<Lo3Lg01BerichtWaarde> readFileAsLo3CategorieWaarde(final File file) throws IOException, Lo3SyntaxException;

    /**
     * Leest de inhoud van een bestand in en retourneert een lijst met daarin een map van header/value.
     * 
     * @param file
     *            bestand
     * @return een lijst met header/value maps
     * @throws IOException
     *             bij leesfouten
     */
    List<Map<String, Object>> readFileAsSqlOutput(final File file) throws IOException;

}
