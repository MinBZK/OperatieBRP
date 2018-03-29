/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3Lg01BerichtWaarde;
import org.apache.commons.io.IOUtils;

/**
 * Leest SQL-bestanden in en slaat de commentaar regels over.
 */
public final class SqlReader implements Reader {
    private static final Pattern PATTERN_EXTENSIE = Pattern.compile("^\\s*--");
    private static final String OPERATION_NOT_SUPPORTED = "Operation not supported";

    /**
     * Leest een SQL-bestand in. Als er commentaar in staat, dan wordt dit overgeslagen.
     *
     * {@inheritDoc}
     */
    @Override
    public String readFile(final File file) throws IOException {
        final List<String> lines = IOUtils.readLines(new FileInputStream(file), "UTF-8");
        final StringBuilder sb = new StringBuilder();
        for (final String line : lines) {
            // Als de regel commentaar is, dan skippen we deze
            final Matcher matcher = PATTERN_EXTENSIE.matcher(line.toLowerCase());
            if (!matcher.find()) {
                sb.append(line).append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     *
     * Deze methode is niet geimplementeerd.
     */
    @Override
    public List<Lo3Lg01BerichtWaarde> readFileAsLo3CategorieWaarde(final File file) throws IOException, Lo3SyntaxException {
        throw new IOException(OPERATION_NOT_SUPPORTED);
    }

    /**
     * {@inheritDoc}
     *
     * Deze methode is niet geimplementeerd.
     */
    @Override
    public List<Map<String, Object>> readFileAsSqlOutput(final File file) throws IOException {
        throw new IOException(OPERATION_NOT_SUPPORTED);
    }
}
