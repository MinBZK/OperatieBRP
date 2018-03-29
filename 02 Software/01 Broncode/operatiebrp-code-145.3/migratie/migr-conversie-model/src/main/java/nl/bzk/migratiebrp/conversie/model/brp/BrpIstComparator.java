/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp;

import java.io.Serializable;
import java.util.Comparator;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstGroepInhoud;

/**
 * Comparator om de {@link BrpGroep} met een {@link BrpIstGroepInhoud} te sorteren.
 */
public class BrpIstComparator implements Comparator<BrpGroep<? extends BrpGroepInhoud>>, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public int compare(final BrpGroep<? extends BrpGroepInhoud> o1, final BrpGroep<? extends BrpGroepInhoud> o2) {
        final BrpIstGroepInhoud i1 = (BrpIstGroepInhoud) o1.getInhoud();
        final BrpIstGroepInhoud i2 = (BrpIstGroepInhoud) o2.getInhoud();

        final int cat1 = i1.getCategorie().getCategorieAsInt();
        final int cat2 = i2.getCategorie().getCategorieAsInt();
        int result = cat1 - cat2;

        if (result == 0) {
            final int stapel1 = i1.getStapel();
            final int stapel2 = i2.getStapel();
            result = stapel1 - stapel2;
        }

        if (result == 0) {
            final int voorkomen1 = i1.getVoorkomen();
            final int voorkomen2 = i2.getVoorkomen();
            result = voorkomen1 - voorkomen2;
        }
        return result;
    }
}
