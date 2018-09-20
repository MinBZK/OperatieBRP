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
import nl.bzk.brp.model.hisvolledig.kern.PersoonVoornaamHisVolledig;
import nl.bzk.brp.model.logisch.kern.PersoonVoornaamHisMoment;

/**
 * Getter voor groepattribuut 'datum_aanvang_geldigheid' in objecttype 'HisPersoonVoornaam'.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.SymbolTableGenerator")
public class PersoonvoornaamDatumAanvangGeldigheidGetter implements AttributeGetter {

    /**
     * {@inheritDoc}
     */
    @Override
    public final Expressie getAttribuutWaarde(final BrpObject brpObject) {
        Expressie resultaat = NullValue.getInstance();
        final Attribuut attribuut = getAttribuut(brpObject);
        if (attribuut != null) {
            resultaat = new DatumLiteralExpressie(attribuut.getWaarde());
        }
        return resultaat;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Attribuut getAttribuut(final BrpObject brpObject) {
        Attribuut resultaat = null;
        if (brpObject instanceof PersoonVoornaamHisVolledig) {
            final PersoonVoornaamHisVolledig v = (PersoonVoornaamHisVolledig) brpObject;
            if (v.getPersoonVoornaamHistorie() != null && v.getPersoonVoornaamHistorie().getActueleRecord() != null) {
                resultaat = v.getPersoonVoornaamHistorie().getActueleRecord().getDatumAanvangGeldigheid();
            }
        } else if (brpObject instanceof PersoonVoornaamHisMoment) {
            final PersoonVoornaamHisMoment v = (PersoonVoornaamHisMoment) brpObject;
            if (v.getStandaard() != null) {
                resultaat = v.getStandaard().getDatumAanvangGeldigheid();
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
        if (brpObject instanceof PersoonVoornaamHisVolledig) {
            final PersoonVoornaamHisVolledig p = (PersoonVoornaamHisVolledig) brpObject;
            final Iterator<nl.bzk.brp.model.operationeel.kern.HisPersoonVoornaamModel> iterator = p.getPersoonVoornaamHistorie().getHistorie().iterator();
            while (iterator.hasNext()) {
                attributen.add(iterator.next().getDatumAanvangGeldigheid());
            }
        }
        return attributen;
    }

}
