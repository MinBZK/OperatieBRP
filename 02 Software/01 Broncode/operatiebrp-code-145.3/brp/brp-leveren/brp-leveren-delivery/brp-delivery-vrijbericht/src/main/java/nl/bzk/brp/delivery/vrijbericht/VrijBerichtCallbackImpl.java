/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.vrijbericht;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.service.algemeen.bericht.BerichtException;
import nl.bzk.brp.service.algemeen.bericht.BerichtWriterTemplate;
import nl.bzk.brp.domain.berichtmodel.VrijBerichtAntwoordBericht;
import nl.bzk.brp.service.algemeen.MaakAntwoordBerichtException;
import nl.bzk.brp.service.algemeen.ServiceCallback;

/**
 * VrijBerichtCallback.
 */
public class VrijBerichtCallbackImpl implements ServiceCallback<VrijBerichtAntwoordBericht, String> {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private String xmlResultaat;

    @Override
    public void verwerkBericht(final VrijBerichtAntwoordBericht bericht) {
        try {
            xmlResultaat = new BerichtWriterTemplate(SoortBericht.VRB_VRB_STUUR_VRIJ_BERICHT_R.getIdentifier())
                    .metResultaat(writer -> BerichtWriterTemplate.DEFAULT_RESULTAAT_CONSUMER.accept(bericht, writer))
                    .toXML(bericht);
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

}
