/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.stuf;

import java.io.IOException;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.service.algemeen.bericht.BerichtException;
import nl.bzk.brp.service.algemeen.bericht.BerichtWriter;
import nl.bzk.brp.service.algemeen.bericht.BerichtWriterTemplate;
import nl.bzk.brp.domain.berichtmodel.BerichtStufTransformatieResultaat;
import nl.bzk.brp.domain.berichtmodel.BerichtStufVertaling;
import nl.bzk.brp.domain.berichtmodel.StufAntwoordBericht;
import nl.bzk.brp.service.algemeen.MaakAntwoordBerichtException;
import nl.bzk.brp.service.algemeen.ServiceCallback;

/**
 * StufBerichtCallbackImpl.
 */
public class StufBerichtCallbackImpl implements ServiceCallback<StufAntwoordBericht, String> {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private String xmlResultaat;

    @Override
    public void verwerkBericht(final StufAntwoordBericht bericht) {
        BerichtWriterTemplate template = new BerichtWriterTemplate(SoortBericht.STV_STV_GEEF_STUFBG_BERICHT_R.getIdentifier())
                .metResultaat(writer -> BerichtWriterTemplate.DEFAULT_RESULTAAT_CONSUMER.accept(bericht, writer))
                .metInvullingDienstSpecifiekDeel(writer -> write(writer, bericht.getStufVertaling()));
        try {
            xmlResultaat = template.toXML(bericht);
        } catch (final BerichtException e) {
            final MaakAntwoordBerichtException re = new MaakAntwoordBerichtException("FATAL: Maken antwoordbericht mislukt", e);
            LOGGER.error(re.getMessage(), re);
            throw re;
        }
    }

    @Override
    public String getBerichtResultaat() {
        return xmlResultaat;
    }

    /**
     * @param writer berichtEntiteitWriter
     * @param berichtStufVertaling berichtStufVertaling
     */
    static void write(final BerichtWriter writer, final BerichtStufTransformatieResultaat berichtStufVertaling) {
        //schrijf bericht vertalingen als string zodat niet xml stuf bericht volledig geparst hoeft te worden
        writer.flush();
        try {
            writer.getOutputWriter().flush();
            for (BerichtStufVertaling vertaling : berichtStufVertaling.getStufVertalingen()) {
                if (vertaling.getCommunicatieId() != null) {
                    writer.getOutputWriter().write(String.format("<brp:berichtVertaling brp:communicatieID=\"%s\">", vertaling.getCommunicatieId()));
                } else {
                    writer.getOutputWriter().write("<brp:berichtVertaling>");
                }
                writer.getOutputWriter().write("<brp:vertaaldeBericht>");
                writer.getOutputWriter().write(vertaling.getVertaling());
                writer.getOutputWriter().write("</brp:vertaaldeBericht>");
                writer.getOutputWriter().write("</brp:berichtVertaling>");
            }
            writer.getOutputWriter().flush();
        } catch (IOException e) {
            throw new BerichtException(e);
        }
        writer.flush();
    }

}
