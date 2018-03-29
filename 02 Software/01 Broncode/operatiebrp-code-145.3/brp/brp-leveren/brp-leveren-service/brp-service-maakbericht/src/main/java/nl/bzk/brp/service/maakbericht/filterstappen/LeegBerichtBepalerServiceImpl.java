/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.HistoriePatroon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Verwerkingssoort;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.leveringmodel.MetaModel;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import org.springframework.stereotype.Component;

/**
 * Bepaalt of het bericht 'leeg' is. Indien dit het geval is mag het niet geleverd worden. De leegbepaling wordt gedaan aan de hand van de eerder bepaalde
 * verwerkingssoorten op {@link MetaRecord}s.
 * <p>
 * Bij onderstaande set aan records en verwerkingssoorten wordt het bericht beschouwd als leeg. Er zijn enkel records met verwerkingssoort {@link
 * Verwerkingssoort#IDENTIFICATIE} en records van type Persoon.AfgeleidAdministratief:
 * <pre><code>
 * &nbsp;&nbsp;[r] Persoon.AfgeleidAdministratief [TOEVOEGING]
 * &nbsp;&nbsp;[r] Persoon.AfgeleidAdministratief [WIJZIGING]
 * &nbsp;&nbsp;[r] Persoon.AfgeleidAdministratief [VERVAL]
 * &nbsp;&nbsp;[r] Persoon.Identificatienummers [IDENTIFICATIE]
 * &nbsp;&nbsp;[r] Persoon.Geboorte [IDENTIFICATIE]
 * &nbsp;&nbsp;[r] Persoon.Geslachtsaanduiding [IDENTIFICATIE]
 * &nbsp;&nbsp;[r] Persoon.Samengesteldenaam [IDENTIFICATIE]
 * </code></pre>
 * <p>
 * In onderstaand voorbeeld is er een <tt>Persoon.Migratie</tt> record toegevoegd. Dit resulteert in bepaling leeg=false
 * <pre><code>
 * &nbsp;&nbsp;[r] Persoon.AfgeleidAdministratief [TOEVOEGING]
 * &nbsp;&nbsp;[r] Persoon.AfgeleidAdministratief [WIJZIGING]
 * &nbsp;&nbsp;[r] Persoon.AfgeleidAdministratief [VERVAL]
 * &nbsp;&nbsp;[r] Persoon.Identificatienummers [IDENTIFICATIE]
 * &nbsp;&nbsp;[r] Persoon.Geboorte [IDENTIFICATIE]
 * &nbsp;&nbsp;[r] Persoon.Geslachtsaanduiding [IDENTIFICATIE]
 * &nbsp;&nbsp;[r] Persoon.Samengesteldenaam [IDENTIFICATIE]
 * &nbsp;&nbsp;[r] <b>Persoon.Migratie [TOEVOEGING]</b>
 * </code></pre>
 */
@Component
@Bedrijfsregel(Regel.R1989)
final class LeegBerichtBepalerServiceImpl implements MaakBerichtStap {

    @Override
    public void execute(final Berichtgegevens berichtgegevens) {
        if (berichtgegevens.isMutatiebericht()) {
            berichtgegevens.setLeegBericht(isLeeg(berichtgegevens));
        } else {
            berichtgegevens.setLeegBericht(berichtgegevens.getGeautoriseerdeObjecten().isEmpty());
        }
    }

    private boolean isLeeg(final Berichtgegevens berichtgegevens) {
        // uitzondering : bij leegberichtbepaling worden identificerende gegevens genegeerd, echter voor mutatiebericht met melding verstr.beperking
        // worden juist uitsluitend identificerende gegevens (mits geautoriseerd) geleverd
        if (berichtgegevens.isMutatieberichtMetMeldingVerstrekkingsbeperking() && !berichtgegevens.getGeautoriseerdeRecords().isEmpty()) {
            return false;
        }

        final Map<MetaModel, Verwerkingssoort> verwerkingssoortMap = berichtgegevens.getVerwerkingssoortMap();
        //voor leeg bericht bepaling zijn alleen records relevant
        final List<MetaModel> recordList = Lists.newLinkedList(
                verwerkingssoortMap.keySet().stream().filter(abstractMetaModel -> abstractMetaModel instanceof MetaRecord).collect(Collectors.toSet())
        );

        final Iterator<MetaModel> iterator = recordList.iterator();
        while (iterator.hasNext()) {
            final MetaRecord record = (MetaRecord) iterator.next();
            if (!berichtgegevens.isGeautoriseerd(record)) {
                iterator.remove();
                continue;
            }
            final GroepElement groepElement = record.getParentGroep().getGroepElement();
            if (Element.PERSOON_AFGELEIDADMINISTRATIEF == groepElement.getElement()
                    || Verwerkingssoort.IDENTIFICATIE == verwerkingssoortMap.get(record)
                    || HistoriePatroon.G == groepElement.getHistoriePatroon()) {
                iterator.remove();
            } else {
                return false;
            }
        }
        return recordList.isEmpty();
    }
}
