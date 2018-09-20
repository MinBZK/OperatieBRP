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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartijOnderzoekAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.hisvolledig.kern.PartijOnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PartijOnderzoekView;
import nl.bzk.brp.model.logisch.kern.PartijOnderzoek;

/**
 * Getter voor 'partijonderzoek.rol' in objecttype 'PartijOnderzoek'.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.SymbolTableGenerator")
public class PartijonderzoekRolGetter implements AttributeGetter {

    /**
     * {@inheritDoc}
     */
    @Override
    public final Expressie getAttribuutWaarde(final BrpObject brpObject) {
        Expressie resultaat = NullValue.getInstance();
        if (brpObject instanceof PartijOnderzoek) {
            final Attribuut attribuut = getAttribuut(brpObject);
            if (attribuut != null) {
                resultaat = new StringLiteralExpressie(((SoortPartijOnderzoekAttribuut) attribuut).getWaarde().getNaam());
            }
        } else if (brpObject instanceof PartijOnderzoekHisVolledig) {
            final List<Attribuut> attributen = getHistorischeAttributen(brpObject);
            if (attributen != null) {
                final List<Expressie> elementen = new ArrayList<Expressie>();
                for (Attribuut attribuut : attributen) {
                    if (attribuut != null) {
                        elementen.add(new StringLiteralExpressie(((SoortPartijOnderzoekAttribuut) attribuut).getWaarde().getNaam()));
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
        if (brpObject instanceof PartijOnderzoek) {
            final PartijOnderzoek v = (PartijOnderzoek) brpObject;
            if (v.getStandaard() != null) {
                resultaat = v.getStandaard().getRol();
            }
        } else if (brpObject instanceof PartijOnderzoekHisVolledig) {
            final PartijOnderzoek x = new PartijOnderzoekView((PartijOnderzoekHisVolledig) brpObject, new DatumTijdAttribuut(), new DatumAttribuut());
            resultaat = getAttribuut(x);
        }
        return resultaat;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final List<Attribuut> getHistorischeAttributen(final BrpObject brpObject) {
        final List<Attribuut> attributen = new ArrayList<Attribuut>();
        if (brpObject instanceof PartijOnderzoek) {
            final Attribuut attribuut = getAttribuut(brpObject);
            if (attribuut != null) {
                attributen.add(attribuut);
            }
        } else if (brpObject instanceof PartijOnderzoekHisVolledig) {
            final PartijOnderzoekHisVolledig p = (PartijOnderzoekHisVolledig) brpObject;
            final Iterator<nl.bzk.brp.model.operationeel.kern.HisPartijOnderzoekModel> iterator = p.getPartijOnderzoekHistorie().getHistorie().iterator();
            while (iterator.hasNext()) {
                attributen.add(iterator.next().getRol());
            }
        }
        return attributen;
    }

}
