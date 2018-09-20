/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.namespace.QName;

import nl.bzk.brp.model.gedeeld.SoortActie;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;
import org.jibx.runtime.impl.UnmarshallingContext;


/** Utility class gebruikt door Jibx databinding. */
public final class BindingUtil {

    /**
     * Een mapping van element QName naar een soortActie. De BRP xml berichten kennen geen veld waarin de soort actie
     * wordt meegegeven. Dit moeten we afleiden uit xml elementen ofwel QName's. Deze map wordt hiervoor gebruikt.
     */
    private static final Map<QName, SoortActie> XMLELEMENTNAARSOORTACTIEMAPPING;

    static {
        XMLELEMENTNAARSOORTACTIEMAPPING = new HashMap<QName, SoortActie>();
        XMLELEMENTNAARSOORTACTIEMAPPING.put(new QName("http://www.bprbzk.nl/BRP/0001", "verhuizing"),
                SoortActie.VERHUIZING);
        XMLELEMENTNAARSOORTACTIEMAPPING.put(new QName("http://www.bprbzk.nl/BRP/0001", "inschrijvingGeboorte"),
                SoortActie.AANGIFTE_GEBOORTE);
        XMLELEMENTNAARSOORTACTIEMAPPING.put(new QName("http://www.bprbzk.nl/BRP/0001", "registratieNationaliteit"),
                SoortActie.REGISTRATIE_NATIONALITEIT);

    }

    /** Constructor. Private toegang omdat dit een utility class is. */
    private BindingUtil() {
    }

    /**
     * Retourneert een List implementatie die gebruikt wordt door Jibx databinding.
     * 
     * @param <T> type van de list
     * @return ArrayList implementatie
     */
    public static <T> List<T> newListInstance() {
        return new ArrayList<T>();
    }

    /**
     * Retourneert een Set implementatie die gebruikt wordt door Jibx databinding.
     * 
     * @param <T> type van de set
     * @return HashSet implementatie
     */
    public static <T> Set<T> newSetInstance() {
        return new HashSet<T>();
    }

    /**
     * Retourneert een Set implementatie die gebruikt wordt door Jibx databinding.
     * 
     * @param <T> type van de set
     * @return TreeSet implementatie
     */
    public static <T> Set<T> newTreeSetInstance() {
        return new TreeSet<T>();
    }

    /**
     * Bepaalt de soort actie aan de hand van de binding Unmarshalling context. zie ook
     * {@link BindingUtil.XMLELEMENTNAARSOORTACTIEMAPPING}
     * 
     * @param ctx De unmarshalling context.
     * @return De soortactie.
     * @throws JiBXException Indien een functie wordt aangeroepen op de context die niet past bij de huidige state
     *             van de context.
     */
    public static SoortActie initSoort(final IUnmarshallingContext ctx) throws JiBXException {
        final UnmarshallingContext context = (UnmarshallingContext) ctx;
        context.isEnd();
        final QName huidigeParserPositie = new QName(context.getNamespace(), context.getElementName());
        return XMLELEMENTNAARSOORTACTIEMAPPING.get(huidigeParserPositie);
    }

    /**
     * Converteert boolean naar J of N.
     *
     * @param b de boolean dat geconverteerd moeten worden
     * @return J als b true bevat anders N
     */
    public static String booleanNaarJaNee(final Boolean b) {
        if (b) {
            return "J";
        } else {
            return "N";
        }
    }

    /**
     * Converteerd J of N naar een boolean.
     *
     * @param s de String dat geconverteerd moet worden
     * @return true a s J bevat en anders false
     */
    public static Boolean jaNeeNaarBoolean(final String s) {
        if ("J".equals(s)) {
            return true;
        } else {
            return false;
        }
    }
}
