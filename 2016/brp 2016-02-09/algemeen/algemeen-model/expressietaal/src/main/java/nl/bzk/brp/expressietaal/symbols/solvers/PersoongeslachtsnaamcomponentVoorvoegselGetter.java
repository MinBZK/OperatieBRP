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
import nl.bzk.brp.expressietaal.expressies.LijstExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.NullValue;
import nl.bzk.brp.expressietaal.expressies.literals.StringLiteralExpressie;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.hisvolledig.kern.PersoonGeslachtsnaamcomponentHisVolledig;
import nl.bzk.brp.model.logisch.kern.PersoonGeslachtsnaamcomponent;

/**
 * Getter voor 'voorvoegsel' in objecttype 'PersoonGeslachtsnaamcomponent'.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.SymbolTableGenerator")
public class PersoongeslachtsnaamcomponentVoorvoegselGetter implements AttributeGetter {

    /**
     * {@inheritDoc}
     */
    @Override
    public final Expressie getAttribuutWaarde(final BrpObject brpObject) {
        Expressie resultaat = NullValue.getInstance();
        if (brpObject instanceof PersoonGeslachtsnaamcomponent) {
            final Attribuut attribuut = getAttribuut(brpObject);
            if (attribuut != null) {
                resultaat = new StringLiteralExpressie(attribuut.getWaarde());
            }
        } else if (brpObject instanceof PersoonGeslachtsnaamcomponentHisVolledig) {
            final List<Attribuut> attributen = getHistorischeAttributen(brpObject);
            if (attributen != null) {
                final List<Expressie> elementen = new ArrayList<Expressie>();
                for (final Attribuut attribuut : attributen) {
                    if (attribuut != null) {
                        elementen.add(new StringLiteralExpressie(attribuut.getWaarde()));
                    }
                }
                resultaat = new LijstExpressie(elementen);
            }
        }
        return resultaat;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Attribuut getAttribuut(final BrpObject brpObject) {
        Attribuut resultaat = null;
        if (brpObject instanceof PersoonGeslachtsnaamcomponent) {
            final PersoonGeslachtsnaamcomponent v = (PersoonGeslachtsnaamcomponent) brpObject;
            if (v.getStandaard() != null) {
                resultaat = v.getStandaard().getVoorvoegsel();
            }
        } else if (brpObject instanceof PersoonGeslachtsnaamcomponentHisVolledig) {
            final PersoonGeslachtsnaamcomponentHisVolledig v = (PersoonGeslachtsnaamcomponentHisVolledig) brpObject;
            if (v.getPersoonGeslachtsnaamcomponentHistorie() != null && v.getPersoonGeslachtsnaamcomponentHistorie().getActueleRecord() != null) {
                resultaat = v.getPersoonGeslachtsnaamcomponentHistorie().getActueleRecord().getVoorvoegsel();
            }
        }
        return resultaat;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final List<Attribuut> getHistorischeAttributen(final BrpObject brpObject) {
        final List<Attribuut> attributen = new ArrayList<Attribuut>();
        if (brpObject instanceof PersoonGeslachtsnaamcomponent) {
            final Attribuut attribuut = getAttribuut(brpObject);
            if (attribuut != null) {
                attributen.add(attribuut);
            }
        } else if (brpObject instanceof PersoonGeslachtsnaamcomponentHisVolledig) {
            final PersoonGeslachtsnaamcomponentHisVolledig p = (PersoonGeslachtsnaamcomponentHisVolledig) brpObject;
            final Iterator<nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsnaamcomponentModel> iterator =
                    p.getPersoonGeslachtsnaamcomponentHistorie().getHistorie().iterator();
            while (iterator.hasNext()) {
                attributen.add(iterator.next().getVoorvoegsel());
            }
        }
        return attributen;
    }

}
