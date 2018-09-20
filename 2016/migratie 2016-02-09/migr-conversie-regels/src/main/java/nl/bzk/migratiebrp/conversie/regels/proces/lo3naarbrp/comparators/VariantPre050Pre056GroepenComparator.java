/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.comparators;

import java.io.Serializable;
import java.util.Comparator;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;

/**
 * Sorteer de migratie groepen; op datum ingang geldigheid van oud naar nieuw.
 */
public final class VariantPre050Pre056GroepenComparator implements Comparator<TussenGroep<?>>, Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public int compare(final TussenGroep<?> arg0, final TussenGroep<?> arg1) {
        int resultaat;

        // Sorteer op 85.10 ingangsdatum geldigheid van oud naar nieuw
        resultaat = arg0.getHistorie().getIngangsdatumGeldigheid().compareTo(arg1.getHistorie().getIngangsdatumGeldigheid());

        // sorteer op lo3 volgorde, hoger voorkomen nummer betekend ouder, van oud naar nieuw
        if (resultaat == 0) {
            resultaat = arg1.getLo3Herkomst().getVoorkomen() - arg0.getLo3Herkomst().getVoorkomen();
        }

        return resultaat;
    }
}
