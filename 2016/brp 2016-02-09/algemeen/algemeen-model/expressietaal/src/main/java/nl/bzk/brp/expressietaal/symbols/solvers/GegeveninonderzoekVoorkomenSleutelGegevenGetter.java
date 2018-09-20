/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.symbols.solvers;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.expressies.literals.GetalLiteralExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.NullValue;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.hisvolledig.kern.GegevenInOnderzoekHisVolledig;
import nl.bzk.brp.model.logisch.kern.GegevenInOnderzoek;

/**
 * Getter voor 'voorkomen_sleutel_gegeven' in objecttype 'GegevenInOnderzoek'.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.SymbolTableGenerator")
public class GegeveninonderzoekVoorkomenSleutelGegevenGetter implements AttributeGetter {

    /**
     * {@inheritDoc}
     */
    @Override
    public final Expressie getAttribuutWaarde(final BrpObject brpObject) {
        Expressie resultaat = NullValue.getInstance();
        if (brpObject instanceof GegevenInOnderzoek || brpObject instanceof GegevenInOnderzoekHisVolledig) {
            final Attribuut attribuut = getAttribuut(brpObject);
            if (attribuut != null) {
                resultaat = new GetalLiteralExpressie(attribuut.getWaarde());
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
        if (brpObject instanceof GegevenInOnderzoek) {
            final GegevenInOnderzoek v = (GegevenInOnderzoek) brpObject;
            resultaat = v.getVoorkomenSleutelGegeven();
        } else if (brpObject instanceof GegevenInOnderzoekHisVolledig) {
            final GegevenInOnderzoekHisVolledig v = (GegevenInOnderzoekHisVolledig) brpObject;
            resultaat = v.getVoorkomenSleutelGegeven();
        }
        return resultaat;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final List<Attribuut> getHistorischeAttributen(final BrpObject brpObject) {
        final List<Attribuut> attributen = new ArrayList<Attribuut>();
        final Attribuut attribuut = getAttribuut(brpObject);
        if (attribuut != null) {
            attributen.add(attribuut);
        }
        return attributen;
    }

}
