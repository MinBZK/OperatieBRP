/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.symbols.solvers;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.expressies.LijstExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.BrpObjectExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.NullValue;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.logisch.kern.Persoon;

/**
 * Getter voor collectie 'betrokkenheden' in objecttype 'Betrokkenheid'.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.SymbolTableGenerator")
public class PersoonBetrokkenhedenGetter implements AttributeGetter {

    /**
     * {@inheritDoc}
     */
    @Override
    public final Expressie getAttribuutWaarde(final BrpObject brpObject) {
        Expressie resultaat = NullValue.getInstance();
        if (brpObject instanceof Persoon && ((Persoon) brpObject).getBetrokkenheden() != null) {
            final List<Expressie> elementen = new ArrayList<Expressie>();
            for (Betrokkenheid element : ((Persoon) brpObject).getBetrokkenheden()) {
                elementen.add(new BrpObjectExpressie(element, ExpressieType.BETROKKENHEID));
            }
            resultaat = new LijstExpressie(elementen);
        } else if (brpObject instanceof PersoonHisVolledig && ((PersoonHisVolledig) brpObject).getBetrokkenheden() != null) {
            final List<Expressie> elementen = new ArrayList<Expressie>();
            for (BetrokkenheidHisVolledig element : ((PersoonHisVolledig) brpObject).getBetrokkenheden()) {
                elementen.add(new BrpObjectExpressie(element, ExpressieType.BETROKKENHEID));
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
