/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.bericht;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.brp.domain.algemeen.DatumFormatterUtil;
import nl.bzk.brp.domain.berichtmodel.BerichtStuurgegevens;
import org.apache.commons.lang3.StringUtils;

/**
 * Hulpklasse voor het schrijven van BerichtStuurgegevens.
 */
public final class BerichtStuurgegevensWriter {

    private BerichtStuurgegevensWriter() {
    }

    /**
     * Schrijft een BerichtStuurgegevens.
     * @param writer de writer.
     * @param stuurgegevens een BerichtStuurgegevens.
     */
    public static void write(final BerichtWriter writer, final BerichtStuurgegevens stuurgegevens) {
        writer.startElement("stuurgegevens");
        writer.element("zendendePartij", StringUtils.leftPad(String.valueOf(stuurgegevens.getZendendePartij().getCode()),
                BerichtConstants.PARTIJ_CODE_PADDING_POSITIES, BerichtConstants.PADDING_WAARDE_0));
        writer.element("zendendeSysteem", stuurgegevens.getZendendeSysteem());
        final Partij ontvangendePartij = stuurgegevens.getOntvangendePartij();
        if (ontvangendePartij != null) {
            writer.element("ontvangendePartij", StringUtils.leftPad(String.valueOf(ontvangendePartij.getCode()),
                    BerichtConstants.PARTIJ_CODE_PADDING_POSITIES, BerichtConstants.PADDING_WAARDE_0));
        }
        writer.element("referentienummer", stuurgegevens.getReferentienummer());
        if (stuurgegevens.getCrossReferentienummer() != null) {
            writer.element("crossReferentienummer", stuurgegevens.getCrossReferentienummer());
        }
        writer.element("tijdstipVerzending", DatumFormatterUtil.vanZonedDateTimeNaarXsdDateTime(stuurgegevens.getTijdstipVerzending()));
        writer.endElement();

    }
}
