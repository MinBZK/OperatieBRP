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
import nl.bzk.brp.expressietaal.expressies.literals.DatumLiteralExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.NullValue;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.logisch.kern.BetrokkenheidHisMoment;

/**
 * Getter voor groepattribuut 'verantwoordingInhoud.datum_ontlening' in objecttype 'Actie'.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.SymbolTableGenerator")
public class BetrokkenheidVerantwoordinginhoudDatumOntleningGetter implements AttributeGetter {

    /**
     * {@inheritDoc}
     */
    @Override
    public final Expressie getAttribuutWaarde(final BrpObject brpObject) {
        final Attribuut attribuut = getAttribuut(brpObject);
        Expressie resultaat = NullValue.getInstance();
        if (attribuut != null) {
            resultaat = new DatumLiteralExpressie(getAttribuut(brpObject).getWaarde());
        }
        return resultaat;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Attribuut getAttribuut(final BrpObject brpObject) {
        Attribuut resultaat = null;
        if (brpObject instanceof BetrokkenheidHisMoment) {
            final BetrokkenheidHisMoment v = (BetrokkenheidHisMoment) brpObject;
            if (v.getIdentiteit() != null && v.getIdentiteit().getVerantwoordingInhoud() != null) {
                resultaat = v.getIdentiteit().getVerantwoordingInhoud().getDatumOntlening();
            }
        } else if (brpObject instanceof BetrokkenheidHisVolledig) {
            final BetrokkenheidHisVolledig v = (BetrokkenheidHisVolledig) brpObject;
            if (v.getBetrokkenheidHistorie() != null
                && v.getBetrokkenheidHistorie().getActueleRecord() != null
                && v.getBetrokkenheidHistorie().getActueleRecord().getVerantwoordingInhoud() != null)
            {
                resultaat = v.getBetrokkenheidHistorie().getActueleRecord().getVerantwoordingInhoud().getDatumOntlening();
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
        if (brpObject instanceof BetrokkenheidHisVolledig) {
            final BetrokkenheidHisVolledig p = (BetrokkenheidHisVolledig) brpObject;
            final Iterator<nl.bzk.brp.model.operationeel.kern.HisBetrokkenheidModel> iterator = p.getBetrokkenheidHistorie().getHistorie().iterator();
            while (iterator.hasNext()) {
                final nl.bzk.brp.model.operationeel.kern.HisBetrokkenheidModel hisModel = iterator.next();
                if (hisModel.getVerantwoordingInhoud() != null) {
                    attributen.add(hisModel.getVerantwoordingInhoud().getDatumOntlening());
                }
            }
        }
        return attributen;
    }

}
