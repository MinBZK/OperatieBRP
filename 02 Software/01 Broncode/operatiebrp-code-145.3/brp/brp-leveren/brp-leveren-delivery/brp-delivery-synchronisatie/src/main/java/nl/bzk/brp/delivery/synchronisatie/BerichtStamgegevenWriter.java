/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.synchronisatie;

import com.google.common.base.CaseFormat;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.ElementBasisType;
import nl.bzk.brp.service.algemeen.bericht.BerichtConstants;
import nl.bzk.brp.service.algemeen.bericht.BerichtWriter;
import nl.bzk.brp.domain.algemeen.DatumFormatterUtil;
import nl.bzk.brp.domain.algemeen.StamtabelGegevens;
import nl.bzk.brp.domain.berichtmodel.BerichtStamgegevens;
import nl.bzk.brp.domain.element.AttribuutElement;
import org.apache.commons.lang3.StringUtils;

/**
 * StamgegevenWriter.
 */
public final class BerichtStamgegevenWriter {

    private BerichtStamgegevenWriter() {
    }

    /**
     * @param berichtStamgegevens stamgegevens
     * @param writer de writer
     */
    public static void write(final BerichtStamgegevens berichtStamgegevens, BerichtWriter writer) {
        if (berichtStamgegevens.getStamtabelGegevens() == null) {
            return;
        }
        final String lowerCamelCaseNaam = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, berichtStamgegevens
                .getStamtabelGegevens().getStamgegevenTabel().getNaam());
        writer.startElement(lowerCamelCaseNaam + StamtabelGegevens.TABEL_POSTFIX);

        for (Map<String, Object> stringObjectMap : berichtStamgegevens.getStamtabelGegevens().getStamgegevens()) {
            //xml stamgegeven tabel
            writer.startElement(lowerCamelCaseNaam);
            writer.attribute("objecttype", berichtStamgegevens.getStamtabelGegevens().getStamgegevenTabel().getNaam());
            //elementen
            for (AttribuutElement attribuutElement : berichtStamgegevens.getStamtabelGegevens().getStamgegevenTabel()
                    .getStamgegevenAttributenInBericht()) {
                schrijfStamgegevenWaarde(writer, stringObjectMap, attribuutElement);
            }
            writer.endElement();
        }
        writer.endElement();
    }

    private static void schrijfStamgegevenWaarde(final BerichtWriter berichtWriter, final Map<String, Object> stringObjectMap,
                                                 final AttribuutElement attribuutElement) {
        final Object waarde = stringObjectMap
                .get(attribuutElement.getElement().getElementWaarde().getIdentdb().toLowerCase());
        if (waarde == null || StringUtils.isEmpty(waarde.toString())) {
            return;
        }
        final String waardeFormatted = bepaalWaarde(waarde, attribuutElement);
        final String lowerCamelCaseAttribuutNaam = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, attribuutElement.getXmlNaam());
        berichtWriter.element(lowerCamelCaseAttribuutNaam, StringUtils.defaultString(waardeFormatted));
    }

    private static String bepaalWaarde(final Object waarde, final AttribuutElement attribuutElement) {
        final String waardeFormatted;
        if (ElementBasisType.DATUM == attribuutElement.getDatatype()) {
            waardeFormatted = DatumFormatterUtil.datumAlsGetalNaarDatumAlsString((Integer) waarde);
        } else if (attribuutElement.getMininumLengte() != null) {
            //voor overschreven refs is de db info niet meer correct. Concreet id ref naar stamtabel is vervangen door code/naam van gerefereerd
            // stamgegeven. Altijd string. Netter misschien om ook db info aan te passen
            if (attribuutElement.isGetal() && !(waarde instanceof String)) {
                waardeFormatted = getalNaarStringMetVoorloopnullen((Number) waarde, attribuutElement.getMininumLengte());
            } else {
                waardeFormatted = stringNaarStringMetVoorloopnullen(waarde.toString(), attribuutElement.getMininumLengte());
            }
        } else {
            waardeFormatted = waarde.toString();
        }
        return waardeFormatted;
    }

    private static String getalNaarStringMetVoorloopnullen(final Number getal, final int stringLengte) {
        String string = null;
        if (getal != null) {
            string = StringUtils.leftPad(String.valueOf(getal), stringLengte, BerichtConstants.PADDING_WAARDE_0);
        }
        return string;
    }

    private static String stringNaarStringMetVoorloopnullen(final String string, final int stringLengte) {
        return StringUtils.leftPad(string, stringLengte, BerichtConstants.PADDING_WAARDE_0);
    }


}
