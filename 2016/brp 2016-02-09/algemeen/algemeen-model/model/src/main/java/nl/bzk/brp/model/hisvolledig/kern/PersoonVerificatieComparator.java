/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.kern;

import java.io.Serializable;
import java.util.Comparator;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.AbstractPersoonComparator;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVerificatieModel;


/**
 * Comparator om persoon verificaties te sorteren, wordt gebruikt in bevraging om de verificaties van een persoon in een vaste volgorde in het response te
 * tonen.
 */
@Regels(Regel.VR00092)
public class PersoonVerificatieComparator extends AbstractPersoonComparator implements Comparator<PersoonVerificatieHisVolledig>, Serializable {

    @Override
    public int compare(final PersoonVerificatieHisVolledig persVeri1, final PersoonVerificatieHisVolledig persVeri2) {
        final HisPersoonVerificatieModel record1 = getTeGebruikenHistorieRecord(persVeri1.getPersoonVerificatieHistorie());
        final HisPersoonVerificatieModel record2 = getTeGebruikenHistorieRecord(persVeri2.getPersoonVerificatieHistorie());

        if (record1 != null && record2 != null) {
            final PartijCodeAttribuut partij1code = record1.getPersoonVerificatie().getPartij().getWaarde().getCode();
            final PartijCodeAttribuut partij2code = record2.getPersoonVerificatie().getPartij().getWaarde().getCode();

            final String soort1 = record1.getPersoonVerificatie().getSoort().getWaarde();
            final String soort2 = record2.getPersoonVerificatie().getSoort().getWaarde();

            int resultaat = partij1code.compareTo(partij2code);
            if (resultaat == 0) {
                resultaat = soort1.compareTo(soort2);
            }
            if (resultaat == 0) {
                resultaat = record1.getTijdstipRegistratie().compareTo(record2.getTijdstipRegistratie()) * -1;
            }

            return resultaat;
        }

        return -1;
    }
}
