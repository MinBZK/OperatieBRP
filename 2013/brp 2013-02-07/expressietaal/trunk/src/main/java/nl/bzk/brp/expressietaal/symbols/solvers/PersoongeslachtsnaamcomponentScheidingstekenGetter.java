/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.symbols.solvers;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.expressietaal.parser.syntaxtree.Expressie;
import nl.bzk.brp.expressietaal.parser.syntaxtree.NullValue;
import nl.bzk.brp.expressietaal.parser.syntaxtree.StringLiteralExpressie;
import nl.bzk.brp.expressietaal.symbols.AttributeGetter;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.PersoonGeslachtsnaamcomponent;


/**
 * Getter voor 'scheidingsteken' in objecttype 'PersoonGeslachtsnaamcomponent'.
 * <p/>
 * Generator: nl.bzk.brp.generatoren.java.SymbolTableGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-31 14:31:48.
 * Gegenereerd op: Thu Jan 31 15:38:26 CET 2013.
 */
public class PersoongeslachtsnaamcomponentScheidingstekenGetter implements AttributeGetter {

    /**
     * Geeft de waarde van attribuut 'scheidingsteken' uit het gegeven (root)object.
     *
     * @param rootObject Object waarvan het attribuut bepaald moet worden.
     * @return Waarde van het attribuut.
     */
    @Override
    public Expressie getAttribuutWaarde(final RootObject rootObject) {
        return getAttribuutWaarde(rootObject, 1);
    }

    /**
     * Geeft de waarde van attribuut 'scheidingsteken' uit een bepaald
     * geïndiceerd attribuut van een (root)object.
     *
     * @param rootObject Object waarvan het attribuut bepaald moet worden.
     * @param index      Index van het attribuut waartoe het gezochte attribuut behoort.
     * @return Waarde van het attribuut.
     */
    @Override
    public Expressie getAttribuutWaarde(final RootObject rootObject, final Integer index) {
        if (rootObject != null && rootObject instanceof Persoon
                && ((Persoon) rootObject).getGeslachtsnaamcomponenten() != null)
        {
            List<PersoonGeslachtsnaamcomponent> collectie = new ArrayList<PersoonGeslachtsnaamcomponent>();
            collectie.addAll(((Persoon) rootObject).getGeslachtsnaamcomponenten());
            if (index >= 1 && index <= collectie.size()) {
                return new StringLiteralExpressie(collectie.get(index - 1).getStandaard().getScheidingsteken()
                        .getWaarde());
            }
        }
        return new NullValue();
    }

    /**
     * Geeft de hoogste indexwaarde voor 'scheidingsteken' uit een bepaald
     * geïndiceerd attribuut van een (root)object.
     * Dit is gelijk aan het aantal elementen in de verzameling.
     *
     * @param rootObject Object waarvan de hoogste indexwaarde bepaald moet worden.
     * @return Maximale index.
     */
    @Override
    public Integer getMaxIndex(final RootObject rootObject) {
        return ((Persoon) rootObject).getGeslachtsnaamcomponenten().size();
    }
}
