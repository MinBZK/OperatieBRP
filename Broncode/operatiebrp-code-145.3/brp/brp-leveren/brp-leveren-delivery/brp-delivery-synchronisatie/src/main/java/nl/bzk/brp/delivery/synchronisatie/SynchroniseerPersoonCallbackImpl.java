/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.synchronisatie;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.service.algemeen.bericht.BerichtException;
import nl.bzk.brp.service.algemeen.bericht.BerichtWriterTemplate;
import nl.bzk.brp.domain.berichtmodel.SynchroniseerPersoonAntwoordBericht;
import nl.bzk.brp.service.algemeen.MaakAntwoordBerichtException;
import nl.bzk.brp.service.synchronisatie.persoon.SynchroniseerPersoonService;

/**
 * Callback voor het schrijven van synchronisatieberichten.
 */
public final class SynchroniseerPersoonCallbackImpl implements SynchroniseerPersoonService.SynchronisatieCallback<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private String xml;

    @Override
    public void verwerkResultaat(final SynchroniseerPersoonAntwoordBericht bericht) {
        final BerichtWriterTemplate template = new BerichtWriterTemplate("lvg_synGeefSynchronisatiePersoon_R")
                .metResultaat(writer -> BerichtWriterTemplate.DEFAULT_RESULTAAT_CONSUMER.accept(bericht, writer));
        try {
            xml = template.toXML(bericht);
        } catch (final BerichtException e) {
            final MaakAntwoordBerichtException re = new MaakAntwoordBerichtException("FATAL: Maken antwoordbericht mislukt", e);
            LOGGER.error(re.getMessage(), re);
            throw re;
        }
    }

    @Override
    public String getResultaat() {
        return xml;
    }
}
