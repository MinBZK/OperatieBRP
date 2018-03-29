/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.afnemerindicatie;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.service.algemeen.bericht.BerichtConstants;
import nl.bzk.brp.service.algemeen.bericht.BerichtException;
import nl.bzk.brp.service.algemeen.bericht.BerichtWriter;
import nl.bzk.brp.service.algemeen.bericht.BerichtWriterTemplate;
import nl.bzk.brp.domain.algemeen.DatumFormatterUtil;
import nl.bzk.brp.domain.berichtmodel.BerichtAfnemerindicatie;
import nl.bzk.brp.domain.berichtmodel.OnderhoudAfnemerindicatieAntwoordBericht;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.service.afnemerindicatie.RegistreerAfnemerindicatieCallback;
import nl.bzk.brp.service.algemeen.MaakAntwoordBerichtException;
import org.apache.commons.lang3.StringUtils;

/**
 * RegistreerAfnemerindicatieCallback.
 */
public final class RegistreerAfnemerindicatieCallbackImpl implements RegistreerAfnemerindicatieCallback<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    /**
     * de string waarde voor voorloopnullen.
     */
    private static final String NUL_ALS_STRING = "0";
    private String xml;

    @Override
    public void verwerkResultaat(final SoortDienst soortDienst, final OnderhoudAfnemerindicatieAntwoordBericht bericht) {

        if (soortDienst != SoortDienst.PLAATSING_AFNEMERINDICATIE && soortDienst != SoortDienst.VERWIJDERING_AFNEMERINDICATIE) {
            throw new UnsupportedOperationException("Incorrecte dienst voor Registreer Afnemerindicatie");
        }
        final BerichtWriterTemplate template = new BerichtWriterTemplate(SoortBericht.LVG_SYN_REGISTREER_AFNEMERINDICATIE_R.getIdentifier())
                .metResultaat(writer -> BerichtWriterTemplate.DEFAULT_RESULTAAT_CONSUMER.accept(bericht, writer))
                .metInvullingDienstSpecifiekDeel(writer -> {
                    writer.startElement(soortDienst == SoortDienst.PLAATSING_AFNEMERINDICATIE ? "plaatsingAfnemerindicatie" : "verwijderingAfnemerindicatie");
                    writer.attribute(BerichtConstants.OBJECTTYPE, ElementHelper.getObjectElement(Element.ADMINISTRATIEVEHANDELING.getId()).getXmlNaam());
                    write(writer, bericht.getBerichtAfnemerindicatie());
                    writer.endElement();
                }
        );
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

    static void write(final BerichtWriter writer, final BerichtAfnemerindicatie berichtAfnemerindicatie) {
        writer.element("partijCode",
                StringUtils.leftPad(berichtAfnemerindicatie.getPartijCode(), BerichtConstants.PARTIJ_CODE_PADDING_POSITIES, NUL_ALS_STRING));
        writer.element("tijdstipRegistratie", DatumFormatterUtil.vanZonedDateTimeNaarXsdDateTime(berichtAfnemerindicatie.getTijdstipRegistratie()));
        writer.startElement("bijgehoudenPersonen");
        writer.startElement("persoon");
        writer.attribute(BerichtConstants.OBJECTTYPE, "Persoon");
        writer.startElement("identificatienummers");
        writer.element("burgerservicenummer", berichtAfnemerindicatie.getBsn());
        writer.endElement();
        writer.endElement();
        writer.endElement();
    }
}
