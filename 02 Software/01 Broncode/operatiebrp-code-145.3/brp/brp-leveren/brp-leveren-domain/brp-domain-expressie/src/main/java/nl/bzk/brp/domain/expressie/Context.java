/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie;

import com.google.common.collect.Maps;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;


/**
 * Definieert de context voor de evaluatie van een expressie. Dit omvat de afbeelding van identifiers op waarden (expressies). Een context bevat alle
 * externe of tijdelijke variabelen die gebruikt kunnen worden binnen een expressie. Daarnaast bepaalt de context hoe BRP-attributen uit een BRP-object
 * worden gehaald tijdens de evaluatie van een expressie.
 */
public final class Context {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private Context parent;
    private final Map<String, Expressie> definities;
    private final Map<String, ExpressieType> declaraties;
    private Map<String, Object> sharedProperties;

    /**
     * Constructor.
     */
    public Context() {
        this.definities = Maps.newHashMap();
        this.declaraties = Maps.newHashMap();
    }

    /**
     * Constructor.
     *
     * @param context de parent {@link Context}
     */
    public Context(final Context context) {
        this();
        this.parent = context;
        this.sharedProperties = context.sharedProperties;
    }

    /**
     * @return de parent context.
     */
    public Context getParent() {
        return this.parent;
    }

    /**
     * Declareert een identifier en zijn type. Als de identifier al een waarde heeft, wordt die overschreven.
     *
     * @param identifier Naam van identifier.
     * @param type       Type van identifier.
     */
    public void declareer(final String identifier, final ExpressieType type) {
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
    public void definieer(final String identifier, final Expressie waarde) {
        if (declaraties.containsKey(identifier)) {
            declaraties.remove(identifier);
        }
        if (ExpressieLogger.IS_DEBUG_ENABLED) {
            LOGGER.debug("Definieer identifier {} met waarde {}", identifier, waarde);
        }
        definities.put(identifier, waarde);
    }

    /**
     * Geeft TRUE als de identifier gedefinieerd is in deze context.
     *
     * @param identifier Te onderzoeken identifier.
     * @return TRUE als de identifier gedefinieerd is in deze context.
     */
    public boolean definieert(final String identifier) {
        return declaraties.containsKey(identifier) || definities.containsKey(identifier);
    }

    /**
     * Zoekt een identifier op en geeft de waarde terug, indien de identifier is gevonden. Zo niet, dan is het resultaat NULL.
     *
     * @param identifier Te zoeken identifier.
     * @return Waarde van de identifier of NULL, indien niet gevonden.
     */
    public Expressie zoekWaarde(final String identifier) {
        Expressie value = definities.get(identifier);
        if (value == null && parent != null) {
            value = parent.zoekWaarde(identifier);
        }
        return value;
    }

    /**
     * Controleert of identifier verwijst naar declaratie.
     *
     * @param declaratie de declaratie
     * @return indicatie of de declaratie bestaat in de ze context
     */
    public boolean isDeclaratie(final String declaratie) {
        ExpressieType value = declaraties.get(declaratie);
        if (value == null && parent != null) {
            value = parent.declaraties.get(declaratie);
        }
        return value != null;
    }

    /**
     * Zoekt het type van een identifier. Het type wordt bepaald door een declaratie of een toegekende waarde. Als het de identifier niet gevonden is, is
     * het resultaat NULL.
     *
     * @param identifier Te zoeken identifier.
     * @return Type van de identifier.
     */
    public ExpressieType zoekType(final String identifier) {
        ExpressieType type = declaraties.get(identifier);
        if (type == null) {
            final Expressie waarde = definities.get(identifier);
            if (waarde != null) {
                type = waarde.getType(null);
            }
        }
        if (type == null && parent != null) {
            type = parent.zoekType(identifier);
        }
        return type;
    }

    /**
     * Geeft de lijst met alle in de context gedefinieerde identifiers.
     *
     * @return Lijst met alle in de context gedefinieerde identifiers.
     */
    public Set<String> identifiers() {
        final Set<String> result = new HashSet<>();
        if (parent != null) {
            result.addAll(parent.identifiers());
        }
        result.addAll(declaraties.keySet().stream().filter(s -> !result.contains(s)).collect(Collectors.toList()));
        result.addAll(definities.keySet().stream().filter(s -> !result.contains(s)).collect(Collectors.toList()));
        return result;
    }

    /**
     * Voegt een property toe aan de Context.
     *
     * @param key   key van de property
     * @param value value van de property
     */
    public void addProperty(final String key, final Object value) {
        if (sharedProperties == null) {
            sharedProperties = Maps.newHashMap();
        }
        sharedProperties.put(key, value);
    }

    /**
     * Geeft de waarde van de property.
     *
     * @param key de key van de property
     * @param <T> type parameter van de value
     * @return de waarde
     */
    public <T> T getProperty(final String key) {
        T val = null;
        if (sharedProperties != null) {
            val = (T) sharedProperties.get(key);
        }
        return val;
    }
}
