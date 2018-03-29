/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.common.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3Lg01BerichtWaarde;
import org.apache.commons.io.IOUtils;

/**
 * Tekst reader.
 */
public final class TextReader implements Reader {

    @Override
    public String readFile(final File file) throws IOException {
        try (java.io.Reader reader = ReaderUtil.getReader(file)) {
            return IOUtils.toString(reader);
        }
    }

    @Override
    public List<Lo3Lg01BerichtWaarde> readFileAsLo3CategorieWaarde(final File file) throws IOException, Lo3SyntaxException {
        final String berichtString = IOUtils.toString(new FileInputStream(file));
        try {
            final List<Lo3CategorieWaarde> categorieen = Lo3Inhoud.parseInhoud(berichtString);
            return Arrays.asList(new Lo3Lg01BerichtWaarde(categorieen));
        } catch (final BerichtSyntaxException e) {
            throw new Lo3SyntaxException(e);
        }
    }

    @Override
    public List<Map<String, Object>> readFileAsSqlOutput(final File file) throws IOException {
        throw new IOException("Operation not supported");
    }
}
