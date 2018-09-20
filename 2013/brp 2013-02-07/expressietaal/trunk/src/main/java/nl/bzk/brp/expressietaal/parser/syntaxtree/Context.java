/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.parser.syntaxtree;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

import nl.bzk.brp.expressietaal.symbols.AttributeSolver;
import nl.bzk.brp.expressietaal.symbols.Attributes;
import nl.bzk.brp.model.RootObject;

/**
 * Afbeelding van identifiers op root-objecten.
 */
public class Context {
    private final Dictionary<String, RootObject> mapping;
    private final AttributeSolver solver;

    /**
     * Constructor.
     *
     * @param solver Het solver-object dat gebruikt wordt om de waarden van attributen te bepalen.
     */
    public Context(final AttributeSolver solver) {
        mapping = new Hashtable<String, RootObject>();
        this.solver = solver;
    }

    /**
     * Constructor.
     *
     * @param context Context die als startpunt voor de nieuwe context wordt gebruikt.
     */
    public Context(final Context context) {
        this(context.solver);
        Enumeration<String> keys = context.mapping.keys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            put(key, context.get(key));
        }
    }

    /**
     * Beeldt een identifier af op een waarde (RootObject).
     *
     * @param identifier Af te beelden identifier.
     * @param value      Waarde van de identifier.
     */
    public final void put(final String identifier, final RootObject value) {
        mapping.put(identifier.toUpperCase(), value);
    }

    /**
     * Zoekt een identifier op en geeft de waarde terug, indien de identifier is gevonden.
     *
     * @param identifier Te zoeken identifier.
     * @return Waarde van de identifier of NULL, indien niet gevonden.
     */
    final RootObject get(final String identifier) {
        return mapping.get(identifier.toUpperCase());
    }

    /**
     * Probeert de waarde van een attribuut te bepalen gegeven een object waartoe dat attribuut zou moeten behoren.
     *
     * @param identifier Identifier voor het object.
     * @param attribute  Attribuut dat bepaald moet worden.
     * @return Resultaat van de evaluatie: de waarde of gevonden fouten.
     */
    public EvaluatieResultaat solve(final String identifier, final Attributes attribute) {
        RootObject obj = get(identifier);
        if (obj != null) {
            return solver.solve(obj, attribute);
        } else {
            return null;
        }
    }

    /**
     * Probeert de waarde van een attribuut behorend tot een geïndiceerd attribuut (zoals adressen en voornamen) te
     * bepalen gegeven een object waartoe dat attribuut zou moeten behoren.
     *
     * @param identifier Identifier voor het object.
     * @param attribute  Het te bepalen attribuut.
     * @param index      De index van het attribuut.
     * @return Resultaat van de evaluatie: de waarde of gevonden fouten.
     */
    public EvaluatieResultaat solve(final String identifier, final Attributes attribute, final int index) {
        RootObject obj = get(identifier);
        if (obj != null) {
            return solver.solve(obj, attribute, index);
        } else {
            return null;
        }
    }

    /**
     * Bepaalt de maximale index van een geïndiceerd attribuut (oftewel het aantal elementen) van een object.
     *
     * @param identifier Identifier voor het object.
     * @param attribute  Het attribuut waarvoor de maximale index bepaald moet worden.
     * @return Maximale index of de gevonden fouten.
     */
    public EvaluatieResultaat getMaxIndex(final String identifier, final Attributes attribute) {
        RootObject obj = get(identifier);
        if (obj != null) {
            return solver.getMaxIndex(obj, attribute);
        } else {
            return null;
        }
    }
}
