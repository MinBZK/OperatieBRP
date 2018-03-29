/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.levering.vergelijker.service.impl;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.tools.levering.vergelijker.entity.LeveringsVergelijkingResultaatInhoud;
import nl.bzk.migratiebrp.tools.levering.vergelijker.service.LeveringsVergelijkingService;
import nl.bzk.migratiebrp.tools.levering.vergelijker.util.Lo3CategorieWaardenVergelijker;
import nl.bzk.migratiebrp.tools.levering.vergelijker.util.Lo3HeaderUtils;

import org.apache.commons.lang.exception.ExceptionUtils;

/**
 * Implementatieklasse voor het inhoudelijk controleren van twee leveringberichten.
 */
public final class LeveringsVergelijkingServiceImpl implements LeveringsVergelijkingService {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public List<LeveringsVergelijkingResultaatInhoud> vergelijkInhoudLeveringsBerichten(final String gbaBericht, final String brpBericht) {

        // Vergelijk bericht types en vergelijk inhoud 'dom'.
        if (!gbaBericht.equals(brpBericht)) {

            // Vergelijk inhoud per stapel, voorkomen, categorie, rubriek & element.
            try {
                final List<Lo3CategorieWaarde> gbaBerichtInhoud = Lo3Inhoud.parseInhoud(gbaBericht);
                final List<Lo3CategorieWaarde> brpBerichtInhoud = Lo3Inhoud.parseInhoud(brpBericht);

                final StringBuilder vergelijkingsLog = new StringBuilder();
                final List<LeveringsVergelijkingResultaatInhoud> vergelijkingResultaten =
                        Lo3CategorieWaardenVergelijker.vergelijk(
                                vergelijkingsLog,
                                gbaBerichtInhoud,
                                brpBerichtInhoud,
                                new ArrayList<LeveringsVergelijkingResultaatInhoud>());

                if (vergelijkingResultaten != null && vergelijkingResultaten.size() > 0) {
                    return vergelijkingResultaten;
                }

            } catch (final BerichtSyntaxException berichtSynctaxException) {
                LOG.error(ExceptionUtils.getStackTrace(berichtSynctaxException));
            }

        }

        return new ArrayList<>();
    }

    @Override
    public String vergelijkKopLeveringsBerichten(final String gbaBericht, final String brpBericht) {

        final String[] headersGbaBericht = Lo3HeaderUtils.haalHeadersUitBericht(gbaBericht);
        final String[] headersBrpBericht = Lo3HeaderUtils.haalHeadersUitBericht(brpBericht);

        return Lo3HeaderUtils.vergelijkHeaders(headersGbaBericht, headersBrpBericht);
    }

}
