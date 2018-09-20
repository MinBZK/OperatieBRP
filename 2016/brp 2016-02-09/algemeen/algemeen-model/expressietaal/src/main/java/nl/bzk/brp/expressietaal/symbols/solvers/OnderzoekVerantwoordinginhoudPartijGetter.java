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
import nl.bzk.brp.expressietaal.expressies.literals.GetalLiteralExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.NullValue;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.hisvolledig.kern.OnderzoekHisVolledig;
import nl.bzk.brp.model.logisch.kern.OnderzoekHisMoment;

/**
 * Getter voor groepattribuut 'verantwoordingInhoud.partij' in objecttype 'Actie'.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.SymbolTableGenerator")
public class OnderzoekVerantwoordinginhoudPartijGetter implements AttributeGetter {

    /**
     * {@inheritDoc}
     */
    @Override
    public final Expressie getAttribuutWaarde(final BrpObject brpObject) {
        final Attribuut attribuut = getAttribuut(brpObject);
        Expressie resultaat = NullValue.getInstance();
        if (attribuut != null) {
            resultaat = new GetalLiteralExpressie(((PartijAttribuut) attribuut).getWaarde().getCode().getWaarde());
        }
        return resultaat;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Attribuut getAttribuut(final BrpObject brpObject) {
        Attribuut resultaat = null;
        if (brpObject instanceof OnderzoekHisMoment) {
            final OnderzoekHisMoment v = (OnderzoekHisMoment) brpObject;
            if (v.getStandaard() != null && v.getStandaard().getVerantwoordingInhoud() != null) {
                resultaat = v.getStandaard().getVerantwoordingInhoud().getPartij();
            }
        } else if (brpObject instanceof OnderzoekHisVolledig) {
            final OnderzoekHisVolledig v = (OnderzoekHisVolledig) brpObject;
            if (v.getOnderzoekHistorie() != null
                && v.getOnderzoekHistorie().getActueleRecord() != null
                && v.getOnderzoekHistorie().getActueleRecord().getVerantwoordingInhoud() != null)
            {
                resultaat = v.getOnderzoekHistorie().getActueleRecord().getVerantwoordingInhoud().getPartij();
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
        if (brpObject instanceof OnderzoekHisVolledig) {
            final OnderzoekHisVolledig p = (OnderzoekHisVolledig) brpObject;
            final Iterator<nl.bzk.brp.model.operationeel.kern.HisOnderzoekModel> iterator = p.getOnderzoekHistorie().getHistorie().iterator();
            while (iterator.hasNext()) {
                final nl.bzk.brp.model.operationeel.kern.HisOnderzoekModel hisModel = iterator.next();
                if (hisModel.getVerantwoordingInhoud() != null) {
                    attributen.add(hisModel.getVerantwoordingInhoud().getPartij());
                }
            }
        }
        return attributen;
    }

}
