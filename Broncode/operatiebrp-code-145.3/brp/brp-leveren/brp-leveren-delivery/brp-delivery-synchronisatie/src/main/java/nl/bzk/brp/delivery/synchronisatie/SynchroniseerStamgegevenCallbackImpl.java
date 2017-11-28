/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.synchronisatie;

import static nl.bzk.brp.service.algemeen.bericht.BerichtWriterTemplate.DEFAULT_RESULTAAT_CONSUMER;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.service.algemeen.bericht.BerichtException;
import nl.bzk.brp.service.algemeen.bericht.BerichtWriterTemplate;
import nl.bzk.brp.domain.berichtmodel.SynchroniseerStamgegevenBericht;
import nl.bzk.brp.service.algemeen.MaakAntwoordBerichtException;
import nl.bzk.brp.service.synchronisatie.stamgegeven.SynchroniseerStamgegevenCallback;

/**
 * StamgegevenCallbackImpl.
 */
public final class SynchroniseerStamgegevenCallbackImpl implements SynchroniseerStamgegevenCallback<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private String xml;

    @Override
    public void verwerkResultaat(final SynchroniseerStamgegevenBericht bericht) {
        final BerichtWriterTemplate template = new BerichtWriterTemplate(SoortBericht.LVG_SYN_GEEF_SYNCHRONISATIE_STAMGEGEVEN_R.getIdentifier())
                .metResultaat(writer1 -> DEFAULT_RESULTAAT_CONSUMER.accept(bericht, writer1))
                .metInvullingDienstSpecifiekDeel(writer -> {
                    writer.startElement("stamgegevens");
                    BerichtStamgegevenWriter.write(bericht.getBerichtStamgegevens(), writer);
                    writer.endElement();
                });
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
