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
import nl.bzk.brp.expressietaal.expressies.literals.NullValue;
import nl.bzk.brp.expressietaal.expressies.literals.StringLiteralExpressie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.kern.PersoonHisMoment;

/**
 * Getter voor groepattribuut 'geslachtsaanduiding.verantwoordingVerval.soort' in objecttype 'Actie'.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.SymbolTableGenerator")
public class PersoonGeslachtsaanduidingVerantwoordingvervalSoortGetter implements AttributeGetter {

    /**
     * {@inheritDoc}
     */
    @Override
    public final Expressie getAttribuutWaarde(final BrpObject brpObject) {
        final Attribuut attribuut = getAttribuut(brpObject);
        Expressie resultaat = NullValue.getInstance();
        if (attribuut != null) {
            resultaat = new StringLiteralExpressie(((SoortActieAttribuut) attribuut).getWaarde().getNaam());
        }
        return resultaat;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Attribuut getAttribuut(final BrpObject brpObject) {
        Attribuut resultaat = null;
        if (brpObject instanceof PersoonHisMoment) {
            final PersoonHisMoment v = (PersoonHisMoment) brpObject;
            if (v.getGeslachtsaanduiding() != null && v.getGeslachtsaanduiding().getVerantwoordingVerval() != null) {
                resultaat = v.getGeslachtsaanduiding().getVerantwoordingVerval().getSoort();
            }
        } else if (brpObject instanceof PersoonHisVolledig) {
            final PersoonHisVolledig v = (PersoonHisVolledig) brpObject;
            if (v.getPersoonGeslachtsaanduidingHistorie() != null
                && v.getPersoonGeslachtsaanduidingHistorie().getActueleRecord() != null
                && v.getPersoonGeslachtsaanduidingHistorie().getActueleRecord().getVerantwoordingVerval() != null)
            {
                resultaat = v.getPersoonGeslachtsaanduidingHistorie().getActueleRecord().getVerantwoordingVerval().getSoort();
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
        if (brpObject instanceof PersoonHisVolledig) {
            final PersoonHisVolledig p = (PersoonHisVolledig) brpObject;
            final Iterator<nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsaanduidingModel> iterator =
                    p.getPersoonGeslachtsaanduidingHistorie().getHistorie().iterator();
            while (iterator.hasNext()) {
                final nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsaanduidingModel hisModel = iterator.next();
                if (hisModel.getVerantwoordingVerval() != null) {
                    attributen.add(hisModel.getVerantwoordingVerval().getSoort());
                }
            }
        }
        return attributen;
    }

}
