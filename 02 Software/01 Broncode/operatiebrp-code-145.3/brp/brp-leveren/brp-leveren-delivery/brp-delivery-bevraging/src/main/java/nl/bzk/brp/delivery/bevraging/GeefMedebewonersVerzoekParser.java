/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging;

import static nl.bzk.brp.delivery.algemeen.AbstractGeneriekeBerichtParser.BRP_NAMESPACE_PREFIX;
import static nl.bzk.brp.delivery.algemeen.AbstractGeneriekeBerichtParser.SLASH;

import java.lang.reflect.Field;
import javax.xml.xpath.XPath;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.service.algemeen.request.BrpVerzoekElement;
import nl.bzk.brp.service.algemeen.request.BrpVerzoekParameter;
import nl.bzk.brp.service.bevraging.geefmedebewoners.GeefMedebewonersVerzoek;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;

/**
 * De parser voor GeefMedebewoners berichten.
 */
final class GeefMedebewonersVerzoekParser extends AbstractBevragingVerzoekParser<GeefMedebewonersVerzoek> {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public String getPrefix() {
        return SLASH + BRP_NAMESPACE_PREFIX + SoortBericht.LVG_BVG_GEEF_MEDEBEWONERS.getIdentifier();
    }

    @Override
    public void vulParameters(final GeefMedebewonersVerzoek verzoek, final Node node, final XPath xPath) {
        super.vulParameters(verzoek, node, xPath);
        //peilmoment materieel
        final String peilmomentMaterieel = getNodeTextContent(getPrefix() + "/brp:parameters/brp:peilmomentMaterieel", xPath, node);
        if (!StringUtils.isEmpty(peilmomentMaterieel)) {
            verzoek.getParameters().setPeilmomentMaterieel(peilmomentMaterieel);
        }
    }

    @Override
    public void vulDienstSpecifiekeGegevens(final GeefMedebewonersVerzoek verzoek, final Node node, final XPath xPath) {
        verzoek.setSoortDienst(SoortDienst.GEEF_MEDEBEWONERS_VAN_PERSOON);

        final BrpVerzoekElement verzoekElement = GeefMedebewonersVerzoek.Identificatiecriteria.class.getAnnotation(BrpVerzoekElement.class);
        if (verzoekElement == null) {
            throw new UnsupportedOperationException("Geen BrpVerzoekElement annotatie gevonden op GeefMedebewonersVerzoek.Identificatiecriteria");
        }
        final String locatie = getPrefix() + SLASH + BRP_NAMESPACE_PREFIX + verzoekElement.element() + SLASH;
        final String communicatieId = getNodeTextContent(locatie + "@brp:communicatieID", xPath, node);
        verzoek.getIdentificatiecriteria().setCommunicatieId(communicatieId);

        final Field[] fields = GeefMedebewonersVerzoek.Identificatiecriteria.class.getDeclaredFields();
        for (final Field field : fields) {
            zetVeldWaarde(verzoek, node, xPath, locatie, field);
        }
    }

    private static void zetVeldWaarde(final GeefMedebewonersVerzoek verzoek, final Node node, final XPath xPath, final String locatie, final Field field) {
        final BrpVerzoekParameter verzoekParameter = field.getAnnotation(BrpVerzoekParameter.class);
        if (verzoekParameter != null) {
            final String elementNaam = "".equals(verzoekParameter.element()) ? field.getName() : verzoekParameter.element();
            final String content = getNodeTextContent(locatie + BRP_NAMESPACE_PREFIX + elementNaam, xPath, node);
            if (content != null) {
                try {
                    field.setAccessible(true);
                    field.set(verzoek.getIdentificatiecriteria(), content);
                } catch (final IllegalAccessException e) {
                    LOGGER.error("Fout bij het parsen van verzoek met elementNaam {}. Element wordt genegeerd.", elementNaam, e);
                }
            }
        }
    }
}
