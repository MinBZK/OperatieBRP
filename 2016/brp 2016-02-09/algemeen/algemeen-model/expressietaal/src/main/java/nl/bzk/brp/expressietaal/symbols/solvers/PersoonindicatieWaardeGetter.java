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
import nl.bzk.brp.expressietaal.expressies.literals.BooleanLiteralExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.NullValue;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.hisvolledig.kern.PersoonIndicatieHisVolledig;
import nl.bzk.brp.model.logisch.kern.PersoonIndicatie;

/**
 * Getter voor 'waarde' in objecttype 'PersoonIndicatie'.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.SymbolTableGenerator")
public class PersoonindicatieWaardeGetter implements AttributeGetter {

    /**
     * {@inheritDoc}
     */
    @Override
    public final Expressie getAttribuutWaarde(final BrpObject brpObject) {
        Expressie resultaat = NullValue.getInstance();
        if (brpObject instanceof PersoonIndicatie) {
            final Attribuut attribuut = getAttribuut(brpObject);
            if (attribuut != null) {
                resultaat = BooleanLiteralExpressie.getExpressie(attribuut.getWaarde());
            }
        } else if (brpObject instanceof PersoonIndicatieHisVolledig) {
            final List<Attribuut> attributen = getHistorischeAttributen(brpObject);
            if (attributen != null) {
                final List<Expressie> elementen = new ArrayList<Expressie>();
                for (final Attribuut attribuut : attributen) {
                    if (attribuut != null) {
                        elementen.add(BooleanLiteralExpressie.getExpressie(attribuut.getWaarde()));
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
        if (brpObject instanceof PersoonIndicatie) {
            final PersoonIndicatie v = (PersoonIndicatie) brpObject;
            if (v.getStandaard() != null) {
                resultaat = v.getStandaard().getWaarde();
            }
        } else if (brpObject instanceof PersoonIndicatieHisVolledig) {
            final PersoonIndicatieHisVolledig v = (PersoonIndicatieHisVolledig) brpObject;
            if (v.getPersoonIndicatieHistorie() != null && v.getPersoonIndicatieHistorie().getActueleRecord() != null) {
                resultaat = ((nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieModel) v.getPersoonIndicatieHistorie().getActueleRecord()).getWaarde();
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
        if (brpObject instanceof PersoonIndicatie) {
            final Attribuut attribuut = getAttribuut(brpObject);
            if (attribuut != null) {
                attributen.add(attribuut);
            }
        } else if (brpObject instanceof PersoonIndicatieHisVolledig) {
            final PersoonIndicatieHisVolledig p = (PersoonIndicatieHisVolledig) brpObject;
            final Iterator<nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieModel> iterator =
                    p.getPersoonIndicatieHistorie().getHistorie().iterator();
            while (iterator.hasNext()) {
                attributen.add(iterator.next().getWaarde());
            }
        }
        return attributen;
    }

}
