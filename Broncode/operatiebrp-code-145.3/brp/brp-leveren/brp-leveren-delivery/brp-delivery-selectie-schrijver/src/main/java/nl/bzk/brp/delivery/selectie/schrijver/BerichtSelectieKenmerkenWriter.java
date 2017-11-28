/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.selectie.schrijver;

import java.sql.Timestamp;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.service.algemeen.bericht.BerichtWriter;
import nl.bzk.brp.domain.algemeen.DatumFormatterUtil;
import nl.bzk.brp.domain.berichtmodel.SelectieKenmerken;

/**
 * Hulpklasse voor het schrijven van SelectieKenmerken.
 */
final class BerichtSelectieKenmerkenWriter {

    private BerichtSelectieKenmerkenWriter() {
    }

    /**
     * Schrijft selectieKenmerken.
     * @param writer de writer.
     * @param selectieKenmerken de selectieKenmerken
     */
    static void write(final BerichtWriter writer, final SelectieKenmerken selectieKenmerken) {
        writer.startElement("selectiekenmerken");
        writer.element("soortSelectieresultaatSet", selectieKenmerken.getSoortSelectieresultaatSet());
        writer.element("volgnummerSelectieresultaatSet", String.valueOf(selectieKenmerken.getSoortSelectieresultaatVolgnummer()));
        writer.element("soortSelectie", selectieKenmerken.getSoortSelectie().getNaam());
        writer.element("leveringsautorisatieIdentificatie", String.valueOf(selectieKenmerken.getLeveringsautorisatie().getId()));
        writer.element("dienstIdentificatie", String.valueOf(selectieKenmerken.getDienst().getId()));
        writer.element("selectietaakIdentificatie", String.valueOf(selectieKenmerken.getSelectietaakId()));
        writer.element("selectieDatum", DatumFormatterUtil.datumAlsGetalNaarDatumAlsString(selectieKenmerken.getDatumUitvoer()));
        final Integer peilmomentMaterieelResultaat = selectieKenmerken.getPeilmomentMaterieelResultaat();
        if (peilmomentMaterieelResultaat != null) {
            writer.element("peilmomentMaterieelResultaat", DatumFormatterUtil.datumAlsGetalNaarDatumAlsString(peilmomentMaterieelResultaat));
        }
        final Timestamp peilmomentFormeelResultaat = selectieKenmerken.getPeilmomentFormeelResultaat();
        if (peilmomentFormeelResultaat != null) {
            writer.element("peilmomentFormeelResultaat", DatumFormatterUtil
                    .vanZonedDateTimeNaarXsdDateTime(DatumUtil.vanTimestampNaarZonedDateTime(peilmomentFormeelResultaat)));
        }
        writer.endElement();
    }
}
