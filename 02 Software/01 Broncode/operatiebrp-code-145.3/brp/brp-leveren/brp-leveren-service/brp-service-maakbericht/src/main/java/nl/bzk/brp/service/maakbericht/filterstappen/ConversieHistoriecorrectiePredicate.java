/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.filterstappen;

import java.util.function.Predicate;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import org.springframework.util.Assert;

/**
 * Filtert voorkomens met voorkomenLeveringMutatie?=Ja.
 * <p>
 * <pre><code>&nbsp;&nbsp;[g] Persoon.Inschrijving
 * &nbsp;&nbsp;&nbsp;[r] id=90165
 * &nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Inschrijving.ActieInhoud, '90890'
 * &nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Inschrijving.ActieVerval, '90890'
 * &nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Inschrijving.ActieVervalTbvLeveringMutaties, '90896'
 * &nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Inschrijving.Datum, '19900101'
 * &nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Inschrijving.Datumtijdstempel, 'Mon Jan 01 01:00:00 CET 1990'
 * &nbsp;&nbsp;&nbsp;&nbsp;[a] <b>Persoon.Inschrijving.IndicatieVoorkomenTbvLeveringMutaties, 'true'</b>
 * &nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Inschrijving.TijdstipRegistratie, 'Mon Jan 01 02:00:00 CET 1990'
 * &nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Inschrijving.TijdstipVerval, 'Mon Jan 01 02:00:00 CET 1990'
 * &nbsp;&nbsp;&nbsp;&nbsp;[a] Persoon.Inschrijving.Versienummer, '1'</code></pre>
 * <p>
 * Dit predikaat dient enkel gebruikt te worden voor volledigberichten.
 */
@Bedrijfsregel(Regel.R1353)
final class ConversieHistoriecorrectiePredicate implements Predicate<MetaRecord> {

    @Override
    public boolean test(final MetaRecord record) {
        Assert.notNull(record, "Record voor predicaat mag niet null zijn.");
        return !Boolean.TRUE.equals(record.isIndicatieTbvLeveringMutaties());
    }
}
