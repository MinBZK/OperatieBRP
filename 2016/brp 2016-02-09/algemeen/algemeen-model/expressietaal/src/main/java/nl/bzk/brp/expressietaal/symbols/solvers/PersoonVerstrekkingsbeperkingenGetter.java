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
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.expressies.LijstExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.BrpObjectExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.NullValue;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVerstrekkingsbeperkingHisVolledig;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.PersoonVerstrekkingsbeperking;

/**
 * Getter voor collectie 'verstrekkingsbeperkingen' in objecttype 'PersoonVerstrekkingsbeperking'.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.SymbolTableGenerator")
public class PersoonVerstrekkingsbeperkingenGetter implements AttributeGetter {

    /**
     * {@inheritDoc}
     */
    @Override
    public final Expressie getAttribuutWaarde(final BrpObject brpObject) {
        Expressie resultaat = NullValue.getInstance();
        if (brpObject instanceof Persoon && ((Persoon) brpObject).getVerstrekkingsbeperkingen() != null) {
            final List<Expressie> elementen = new ArrayList<Expressie>();
            for (PersoonVerstrekkingsbeperking element : ((Persoon) brpObject).getVerstrekkingsbeperkingen()) {
                elementen.add(new BrpObjectExpressie(element, ExpressieType.VERSTREKKINGSBEPERKING));
            }
            resultaat = new LijstExpressie(elementen);
        } else if (brpObject instanceof PersoonHisVolledig && ((PersoonHisVolledig) brpObject).getVerstrekkingsbeperkingen() != null) {
            final List<Expressie> elementen = new ArrayList<Expressie>();
            for (PersoonVerstrekkingsbeperkingHisVolledig element : ((PersoonHisVolledig) brpObject).getVerstrekkingsbeperkingen()) {
                final Iterator<nl.bzk.brp.model.operationeel.kern.HisPersoonVerstrekkingsbeperkingModel> iterator =
                        element.getPersoonVerstrekkingsbeperkingHistorie().iterator();
                final List<Expressie> historie = new ArrayList<Expressie>();
                while (iterator.hasNext()) {
                    historie.add(new BrpObjectExpressie(iterator.next().getPersoonVerstrekkingsbeperking(), ExpressieType.VERSTREKKINGSBEPERKING));
                }
                elementen.add(new LijstExpressie(historie));
            }
            resultaat = new LijstExpressie(elementen);
        }
        return resultaat;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Attribuut getAttribuut(final BrpObject brpObject) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final List<Attribuut> getHistorischeAttributen(final BrpObject brpObject) {
        return null;
    }

}
