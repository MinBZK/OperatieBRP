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
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.factory.Lo3BerichtFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lg01Bericht;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3Lg01BerichtWaarde;
import org.apache.commons.io.IOUtils;

/**
 * Leest een Lg01-bestand uit.
 */
public final class Lg01Reader implements Reader {

    @Override
    public String readFile(final File file) throws IOException {
        return IOUtils.toString(new FileInputStream(file));
    }

    @Override
    public List<Lo3Lg01BerichtWaarde> readFileAsLo3CategorieWaarde(final File file) throws IOException, Lo3SyntaxException {
        final String berichtString = IOUtils.toString(new FileInputStream(file));

        final Lo3BerichtFactory bf = new Lo3BerichtFactory();
        final Lo3Bericht lo3Bericht = bf.getBericht(berichtString);

        final List<Lo3CategorieWaarde> categorieen = ((Lg01Bericht) lo3Bericht).formatInhoud();

        return Arrays.asList(new Lo3Lg01BerichtWaarde(categorieen));
    }

    @Override
    public List<Map<String, Object>> readFileAsSqlOutput(final File file) throws IOException {
        throw new IOException("Operation not supported");
    }

}
