/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.injection;

import java.util.List;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.factory.Lo3BerichtFactory;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaardeUtil;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.exception.TestException;

/**
 * Extractor voor VOSPG berichten.
 *
 * Key: header:<naam> of inhoud of inhoud:<cat>,<volgnr>,<groep><element>
 */
public class ExtractorVospg implements Extractor {

    private static final Lo3BerichtFactory FACTORY = new Lo3BerichtFactory();

    private static final String HEADER_PREFIX = "header";
    private static final int HEADER_OFFSET = HEADER_PREFIX.length() + 1;
    private static final String INHOUD_PREFIX = "inhoud";
    private static final int INHOUD_OFFSET = INHOUD_PREFIX.length() + 1;

    @Override
    public final String extract(final Context context, final Bericht bericht, final String key) throws TestException {
        final String resultaat;

        final Lo3Bericht lo3Bericht = FACTORY.getBericht(bericht.getInhoud());
        if (key.toLowerCase().startsWith(HEADER_PREFIX)) {
            resultaat = extractHeader(lo3Bericht, key.substring(HEADER_OFFSET));
        } else if (key.toLowerCase().startsWith(INHOUD_PREFIX)) {
            if (lo3Bericht.getHeader() == null) {
                throw new TestException("Geparsed LO3 bericht bevat geen header configuratie.");
            }
            final String[] headers;
            try {
                headers = lo3Bericht.getHeader().parseHeaders(bericht.getInhoud());
            } catch (final BerichtSyntaxException e) {
                throw new TestException("Onverwacht probleem tijdens herparsen headers", e);
            }
            int length = 0;
            for (final String header : headers) {
                length += header.length();
            }

            final String lo3Inhoud = bericht.getInhoud().substring(length);
            if (INHOUD_PREFIX.equals(key.toLowerCase())) {
                resultaat = lo3Inhoud;
            } else {
                try {
                    resultaat = extractInhoud(lo3Inhoud, key.substring(INHOUD_OFFSET));
                } catch (
                    Lo3SyntaxException
                    | BerichtSyntaxException e)
                {
                    throw new TestException("Kon inhoud niet bepalen", e);
                }
            }
        } else {
            throw new TestException("Ongeldige configuratie: " + key);
        }

        return resultaat;
    }

    private String extractHeader(final Lo3Bericht lo3Bericht, final String naam) throws TestException {
        final Lo3HeaderVeld header = Lo3HeaderVeld.valueOf(naam.toUpperCase());
        return lo3Bericht.getHeader(header);
    }

    private String extractInhoud(final String lo3Inhoud, final String config) throws Lo3SyntaxException, BerichtSyntaxException {
        final List<Lo3CategorieWaarde> categorieen = Lo3Inhoud.parseInhoud(lo3Inhoud);
        final Lo3CategorieEnum categorie = Lo3CategorieEnum.getLO3Categorie(config.substring(0, 2));
        final int stapel = Integer.parseInt(config.substring(3, 5));
        final Lo3ElementEnum element = Lo3ElementEnum.getLO3Element(config.substring(6, 4));
        return Lo3CategorieWaardeUtil.getElementWaarde(categorieen, categorie, stapel, 0, element);
    }
}
