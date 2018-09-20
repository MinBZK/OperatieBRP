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
import nl.bzk.brp.model.hisvolledig.kern.OuderHisVolledig;
import nl.bzk.brp.model.logisch.kern.OuderHisMoment;

/**
 * Getter voor groepattribuut 'ouderschap.verantwoordingInhoud.datum_ontlening' in objecttype 'Actie'.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.SymbolTableGenerator")
public class OuderOuderschapVerantwoordinginhoudDatumOntleningGetter implements AttributeGetter {

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
        if (brpObject instanceof OuderHisMoment) {
            final OuderHisMoment v = (OuderHisMoment) brpObject;
            if (v.getOuderschap() != null && v.getOuderschap().getVerantwoordingInhoud() != null) {
                resultaat = v.getOuderschap().getVerantwoordingInhoud().getDatumOntlening();
            }
        } else if (brpObject instanceof OuderHisVolledig) {
            final OuderHisVolledig v = (OuderHisVolledig) brpObject;
            if (v.getOuderOuderschapHistorie() != null
                && v.getOuderOuderschapHistorie().getActueleRecord() != null
                && v.getOuderOuderschapHistorie().getActueleRecord().getVerantwoordingInhoud() != null)
            {
                resultaat = v.getOuderOuderschapHistorie().getActueleRecord().getVerantwoordingInhoud().getDatumOntlening();
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
        if (brpObject instanceof OuderHisVolledig) {
            final OuderHisVolledig p = (OuderHisVolledig) brpObject;
            final Iterator<nl.bzk.brp.model.operationeel.kern.HisOuderOuderschapModel> iterator = p.getOuderOuderschapHistorie().getHistorie().iterator();
            while (iterator.hasNext()) {
                final nl.bzk.brp.model.operationeel.kern.HisOuderOuderschapModel hisModel = iterator.next();
                if (hisModel.getVerantwoordingInhoud() != null) {
                    attributen.add(hisModel.getVerantwoordingInhoud().getDatumOntlening());
                }
            }
        }
        return attributen;
    }

}
