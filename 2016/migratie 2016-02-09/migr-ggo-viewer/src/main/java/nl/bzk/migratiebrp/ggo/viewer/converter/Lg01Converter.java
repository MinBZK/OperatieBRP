/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.converter;

import java.util.List;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.factory.Lo3BerichtFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.OnbekendBericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.OngeldigBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.ggo.viewer.log.FoutMelder;
import org.springframework.stereotype.Component;

/**
 * Converteer Lg01 bericht String naar een Lo3Persoonslijst model.
 */
@Component
public class Lg01Converter {
    private static final int LG01_BODY_OFFSET = 49;

    /**
     * Converteert de Lg01 body naar een een lijst van Lo3CategorieWaarde.
     *
     * @param lg01
     *            String
     * @param foutMelder
     *            FoutMelder
     * @return lo3Inhoud List<Lo3CategorieWaarde>
     * @throws BerichtSyntaxException
     *             berichtSyntaxException
     */
    public final List<Lo3CategorieWaarde> converteerLg01NaarLo3CategorieWaarde(final String lg01, final FoutMelder foutMelder)
        throws BerichtSyntaxException
    {
        if (lg01 == null || "".equals(lg01)) {
            foutMelder.log(LogSeverity.ERROR, "Fout bij het lezen van lg01", "Bestand is leeg.");
        } else {
            final Lo3BerichtFactory bf = new Lo3BerichtFactory();
            final Lo3Bericht lo3Bericht = bf.getBericht(lg01);

            if (lo3Bericht instanceof OngeldigBericht) {
                foutMelder.log(LogSeverity.ERROR, "Ongeldig bericht", ((OngeldigBericht) lo3Bericht).getMelding());
            } else if (lo3Bericht instanceof OnbekendBericht) {
                foutMelder.log(LogSeverity.ERROR, "Onbekend bericht", ((OnbekendBericht) lo3Bericht).getMelding());
            } else {
                return Lo3Inhoud.parseInhoud(lg01.substring(LG01_BODY_OFFSET));
            }
        }
        return null;
    }
}
