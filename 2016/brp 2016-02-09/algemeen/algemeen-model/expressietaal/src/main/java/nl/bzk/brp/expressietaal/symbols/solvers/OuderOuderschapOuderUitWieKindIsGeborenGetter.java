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
import nl.bzk.brp.model.hisvolledig.kern.OuderHisVolledig;
import nl.bzk.brp.model.logisch.kern.Ouder;

/**
 * Getter voor 'ouderschap.ouder_uit_wie_kind_is_geboren' in objecttype 'Ouder'.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.SymbolTableGenerator")
public class OuderOuderschapOuderUitWieKindIsGeborenGetter implements AttributeGetter {

    /**
     * {@inheritDoc}
     */
    @Override
    public final Expressie getAttribuutWaarde(final BrpObject brpObject) {
        Expressie resultaat = NullValue.getInstance();
        if (brpObject instanceof Ouder) {
            final Attribuut attribuut = getAttribuut(brpObject);
            if (attribuut != null) {
                resultaat = BooleanLiteralExpressie.getExpressie(attribuut.getWaarde());
            }
        } else if (brpObject instanceof OuderHisVolledig) {
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
        if (brpObject instanceof Ouder) {
            final Ouder v = (Ouder) brpObject;
            if (v.getOuderschap() != null) {
                resultaat = v.getOuderschap().getIndicatieOuderUitWieKindIsGeboren();
            }
        } else if (brpObject instanceof OuderHisVolledig) {
            final OuderHisVolledig v = (OuderHisVolledig) brpObject;
            if (v.getOuderOuderschapHistorie() != null && v.getOuderOuderschapHistorie().getActueleRecord() != null) {
                resultaat = v.getOuderOuderschapHistorie().getActueleRecord().getIndicatieOuderUitWieKindIsGeboren();
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
        if (brpObject instanceof Ouder) {
            final Attribuut attribuut = getAttribuut(brpObject);
            if (attribuut != null) {
                attributen.add(attribuut);
            }
        } else if (brpObject instanceof OuderHisVolledig) {
            final OuderHisVolledig p = (OuderHisVolledig) brpObject;
            final Iterator<nl.bzk.brp.model.operationeel.kern.HisOuderOuderschapModel> iterator = p.getOuderOuderschapHistorie().getHistorie().iterator();
            while (iterator.hasNext()) {
                attributen.add(iterator.next().getIndicatieOuderUitWieKindIsGeboren());
            }
        }
        return attributen;
    }

}
