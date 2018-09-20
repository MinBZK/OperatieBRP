/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.kern;

import java.io.Serializable;
import java.util.Comparator;
import nl.bzk.brp.model.AbstractPersoonComparator;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.operationeel.kern.HisOnderzoekModel;


/**
 * Comparator om persoon onderzoeken te sorteren, wordt gebruikt in bevraging om de onderzoeken van een persoon in een vaste volgorde in het response te
 * tonen.
 */
@Regels(Regel.VR00092)
public final class PersoonOnderzoekComparator extends AbstractPersoonComparator implements Comparator<PersoonOnderzoekHisVolledig>, Serializable {

    @Override
    public int compare(final PersoonOnderzoekHisVolledig persVeri1, final PersoonOnderzoekHisVolledig persVeri2) {
        final HisOnderzoekModel record1 = getTeGebruikenHistorieRecord(persVeri1.getOnderzoek().getOnderzoekHistorie());
        final HisOnderzoekModel record2 = getTeGebruikenHistorieRecord(persVeri2.getOnderzoek().getOnderzoekHistorie());
        int resultaat;
        if (record1 == null && record2 == null) {
            resultaat = 0;
        } else if (record1 == null) {
            resultaat = -1;
        } else if (record2 == null) {
            resultaat = 1;
        } else {
            resultaat = record1.getDatumAanvang().compareTo(record2.getDatumAanvang());
            if (resultaat == 0) {
                resultaat = record1.getVerwachteAfhandeldatum().compareTo(record2.getVerwachteAfhandeldatum());
            }
            if (resultaat == 0) {
                resultaat = record1.getOmschrijving().compareTo(record2.getOmschrijving());
            }
        }
        return resultaat;
    }
}
