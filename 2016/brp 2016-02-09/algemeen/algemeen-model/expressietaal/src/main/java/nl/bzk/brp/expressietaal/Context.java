/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import nl.bzk.brp.expressietaal.expressies.literals.BrpObjectExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.NullValue;
import nl.bzk.brp.expressietaal.symbols.AttributeSolver;
import nl.bzk.brp.expressietaal.symbols.DefaultGroepSolver;
import nl.bzk.brp.expressietaal.symbols.DefaultSolver;
import nl.bzk.brp.expressietaal.symbols.ExpressieAttribuut;
import nl.bzk.brp.expressietaal.symbols.ExpressieGroep;
import nl.bzk.brp.expressietaal.symbols.GroepSolver;
import nl.bzk.brp.model.basis.BrpObject;

/**
 * Definieert de context voor de evaluatie van een expressie. Dit omvat de afbeelding van identifiers op waarden (expressies). Een context bevat alle
 * externe of tijdelijke variabelen die gebruikt kunnen worden binnen een expressie. Daarnaast bepaalt de context hoe BRP-attributen uit een BRP-object
 * worden gehaald tijdens de evaluatie van een expressie.
 */
public class Context {

    private final Map<String, Expressie>     definities;
    private final Map<String, ExpressieType> declaraties;
    private final AttributeSolver            solver;
    private final GroepSolver                groepSolver;
    private       Context                    omslotenContext;

    /**
     * Constructor.
     */
    public Context() {
        this.definities = new HashMap<>();
        this.declaraties = new HashMap<>();
        this.solver = DefaultSolver.getInstance();
        this.groepSolver = DefaultGroepSolver.getInstance();
        this.omslotenContext = null;
    }

    /**
     * Constructor.
     *
     * @param context Context die als startpunt voor de nieuwe context wordt gebruikt.
     */
    public Context(final Context context) {
        this();
        this.omslotenContext = context;
    }

    public final Context getOmslotenContext() {
        return this.omslotenContext;
    }

    /**
     * Declareert een identifier en zijn type. Als de identifier al een waarde heeft, wordt die overschreven.
     *
     * @param identifier Naam van identifier.
     * @param type       Type van identifier.
     */
    public final void declareer(final String identifier, final ExpressieType type) {
        if (definities.containsKey(identifier)) {
            definities.remove(identifier);
        }
        declaraties.put(identifier, type);
    }

    /**
     * Beeldt een identifier af op een waarde (Expressie). Als de identifier al gedeclareerd is, wordt die declaratie overschreven.
     *
     * @param identifier Af te beelden identifier.
     * @param waarde     Waarde van de identifier.
     */
    public final void definieer(final String identifier, final Expressie waarde) {
        if (declaraties.containsKey(identifier)) {
            declaraties.remove(identifier);
        }
        definities.put(identifier, waarde);
    }

    /**
     * Beeldt een identifier af op een waarde (Expressie).
     *
     * @param identifier Af te beelden identifier.
     * @param waarde     Waarde van de identifier.
     */
    public final void definieer(final Identifier identifier, final Expressie waarde) {
        definieer(identifier.getSyntax(), waarde);
    }

    /**
     * Geeft TRUE als de identifier gedefinieerd is in deze context.
     *
     * @param identifier Te onderzoeken identifier.
     * @return TRUE als de identifier gedefinieerd is in deze context.
     */
    public final boolean definieert(final String identifier) {
        return declaraties.containsKey(identifier) || definities.containsKey(identifier);
    }

    /**
     * Zoekt een identifier op en geeft de waarde terug, indien de identifier is gevonden. Zo niet, dan is het resultaat NULL.
     *
     * @param identifier Te zoeken identifier.
     * @return Waarde van de identifier of NULL, indien niet gevonden.
     */
    public final Expressie zoekWaarde(final String identifier) {
        Expressie value = definities.get(identifier);
        if (value == null && omslotenContext != null) {
            value = omslotenContext.zoekWaarde(identifier);
        }
        return value;
    }

    /**
     * Zoekt het type van een identifier. Het type wordt bepaald door een declaratie of een toegekende waarde. Als het de identifier niet gevonden is, is
     * het resultaat NULL.
     *
     * @param identifier Te zoeken identifier.
     * @return Type van de identifier.
     */
    public final ExpressieType zoekType(final String identifier) {
        ExpressieType type = declaraties.get(identifier);
        if (type == null) {
            final Expressie waarde = definities.get(identifier);
            if (waarde != null) {
                type = waarde.getType(null);
            }
        }
        if (type == null && omslotenContext != null) {
            type = omslotenContext.zoekType(identifier);
        }
        return type;
    }

    /**
     * Probeert de waarde van een attribuut te bepalen gegeven een object waartoe dat attribuut zou moeten behoren. Als de waarde niet beschikbaar is of
     * als het object niet verwijst naar een BrpObject (bijvoorbeeld omdat het null is), is het resultaat een NullValue.
     *
     * @param identifier Identifier voor het object.
     * @param attribute  Attribuut dat bepaald moet worden.
     * @return Resultaat van de evaluatie: de gevonden waarde (expressie) of NULL.
     */
    public final Expressie bepaalAttribuutWaarde(final String identifier, final ExpressieAttribuut attribute) {
        final Expressie value = zoekWaarde(identifier);
        Expressie gevondenWaarde = NullValue.getInstance();
        if (value instanceof BrpObjectExpressie) {
            final BrpObject obj = ((BrpObjectExpressie) value).getBrpObject();
            if (obj != null && solver != null) {
                gevondenWaarde = solver.bepaalWaarde(obj, attribute);
            }
        }
        return gevondenWaarde;
    }

    /**
     * Probeert een (concreet) attribuut te bepalen gegeven een object waartoe dat attribuut zou moeten behoren.
     *
     * @param identifier Identifier voor het object.
     * @param attribute  Attribuut dat bepaald moet worden.
     * @return Resultaat van de evaluatie: het gevonden attribuut (expressie) of NULL.
     */
    public final Expressie bepaalAttribuut(final String identifier, final ExpressieAttribuut attribute) {
        final Expressie value = zoekWaarde(identifier);
        Expressie gevondenAttribuut = null;
        if (value instanceof BrpObjectExpressie) {
            final BrpObject obj = ((BrpObjectExpressie) value).getBrpObject();
            if (obj != null && solver != null) {
                gevondenAttribuut = solver.bepaalAttribuut(obj, attribute);
            }
        }
        return gevondenAttribuut;
    }

    /**
     * Probeert een (concrete) groep te bepalen gegeven een object waartoe deze groep zou moeten behoren.
     *
     * @param identifier Identifier voor het object.
     * @param groep  Groep dat bepaald moet worden.
     * @return Resultaat van de evaluatie: de gevonden groep (expressie) of NULL.
     */
    public final Expressie bepaalGroep(final String identifier, final ExpressieGroep groep) {
        final Expressie value = zoekWaarde(identifier);
        Expressie gevondenGroep = null;
        if (value instanceof BrpObjectExpressie) {
            final BrpObject obj = ((BrpObjectExpressie) value).getBrpObject();
            if (obj != null && groepSolver != null) {
                gevondenGroep = groepSolver.bepaalGroep(obj, groep);
            }
        }
        return gevondenGroep;
    }

    /**
     * Geeft de lijst met alle in de context gedefinieerde identifiers.
     *
     * @return Lijst met alle in de context gedefinieerde identifiers.
     */
    public final Set<String> identifiers() {
        final Set<String> result = new HashSet<>();
        if (omslotenContext != null) {
            result.addAll(omslotenContext.identifiers());
        }

        for (final String id : declaraties.keySet()) {
            if (!result.contains(id)) {
                result.add(id);
            }
        }
        for (final String id : definities.keySet()) {
            if (!result.contains(id)) {
                result.add(id);
            }
        }
        return result;
    }
}
