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
import nl.bzk.brp.expressietaal.expressies.literals.GetalLiteralExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.NullValue;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerkrijgingNLNationaliteitAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.hisvolledig.kern.PersoonNationaliteitHisVolledig;
import nl.bzk.brp.model.logisch.kern.PersoonNationaliteit;

/**
 * Getter voor 'reden_verkrijging' in objecttype 'PersoonNationaliteit'.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.SymbolTableGenerator")
public class PersoonnationaliteitRedenVerkrijgingGetter implements AttributeGetter {

    /**
     * {@inheritDoc}
     */
    @Override
    public final Expressie getAttribuutWaarde(final BrpObject brpObject) {
        Expressie resultaat = NullValue.getInstance();
        if (brpObject instanceof PersoonNationaliteit) {
            final Attribuut attribuut = getAttribuut(brpObject);
            if (attribuut != null) {
                resultaat = new GetalLiteralExpressie(((RedenVerkrijgingNLNationaliteitAttribuut) attribuut).getWaarde().getCode().getWaarde());
            }
        } else if (brpObject instanceof PersoonNationaliteitHisVolledig) {
            final List<Attribuut> attributen = getHistorischeAttributen(brpObject);
            if (attributen != null) {
                final List<Expressie> elementen = new ArrayList<Expressie>();
                for (final Attribuut attribuut : attributen) {
                    if (attribuut != null) {
                        elementen.add(new GetalLiteralExpressie(((RedenVerkrijgingNLNationaliteitAttribuut) attribuut).getWaarde().getCode().getWaarde()));
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
        if (brpObject instanceof PersoonNationaliteit) {
            final PersoonNationaliteit v = (PersoonNationaliteit) brpObject;
            if (v.getStandaard() != null) {
                resultaat = v.getStandaard().getRedenVerkrijging();
            }
        } else if (brpObject instanceof PersoonNationaliteitHisVolledig) {
            final PersoonNationaliteitHisVolledig v = (PersoonNationaliteitHisVolledig) brpObject;
            if (v.getPersoonNationaliteitHistorie() != null && v.getPersoonNationaliteitHistorie().getActueleRecord() != null) {
                resultaat = v.getPersoonNationaliteitHistorie().getActueleRecord().getRedenVerkrijging();
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
        if (brpObject instanceof PersoonNationaliteit) {
            final Attribuut attribuut = getAttribuut(brpObject);
            if (attribuut != null) {
                attributen.add(attribuut);
            }
        } else if (brpObject instanceof PersoonNationaliteitHisVolledig) {
            final PersoonNationaliteitHisVolledig p = (PersoonNationaliteitHisVolledig) brpObject;
            final Iterator<nl.bzk.brp.model.operationeel.kern.HisPersoonNationaliteitModel> iterator =
                    p.getPersoonNationaliteitHistorie().getHistorie().iterator();
            while (iterator.hasNext()) {
                attributen.add(iterator.next().getRedenVerkrijging());
            }
        }
        return attributen;
    }

}
