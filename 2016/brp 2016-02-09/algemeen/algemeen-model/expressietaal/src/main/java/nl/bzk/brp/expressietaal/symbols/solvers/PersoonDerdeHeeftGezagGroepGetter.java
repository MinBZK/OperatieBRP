/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.symbols.solvers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Generated;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.kern.Persoon;

/**
 * Getter voor 'derde.heeft.gezag' in objecttype 'Persoon'.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.SymbolTableGroepGenerator")
public class PersoonDerdeHeeftGezagGroepGetter implements GroepGetter {

    /**
     * {@inheritDoc}
     */
    @Override
    public final Groep getGroep(final BrpObject brpObject) {
        Groep resultaat = null;
        if (brpObject instanceof Persoon) {
            final Persoon v = (Persoon) brpObject;
            resultaat = v.getIndicatieDerdeHeeftGezag().getStandaard();
        }
        return resultaat;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final List<Groep> getHistorischeGroepen(final BrpObject brpObject) {
        final List<Groep> groepen = new ArrayList<Groep>();
        if (brpObject instanceof Persoon) {
            final Groep groep = getGroep(brpObject);
            if (groep != null) {
                groepen.add(groep);
            }
        } else if (brpObject instanceof PersoonHisVolledig) {
            final PersoonHisVolledig p = (PersoonHisVolledig) brpObject;
            final Iterator<nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieDerdeHeeftGezagModel> iterator =
                    p.getIndicatieDerdeHeeftGezag().getPersoonIndicatieHistorie().getHistorie().iterator();
            while (iterator.hasNext()) {
                groepen.add(iterator.next());
            }
        }
        return groepen;
    }

}
