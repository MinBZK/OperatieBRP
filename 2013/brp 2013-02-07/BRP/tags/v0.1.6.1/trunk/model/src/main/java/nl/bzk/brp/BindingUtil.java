/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.xml.namespace.QName;

import nl.bzk.brp.model.gedeeld.SoortActie;
import nl.bzk.brp.model.gedeeld.SoortBetrokkenheid;
import nl.bzk.brp.model.gedeeld.SoortRelatie;
import nl.bzk.brp.model.logisch.Betrokkenheid;
import nl.bzk.brp.model.logisch.Relatie;
import org.apache.commons.lang.StringUtils;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;
import org.jibx.runtime.impl.UnmarshallingContext;


/** Utility class gebruikt door Jibx databinding. */
public final class BindingUtil {

    private static final int TIEN     = 10;
    private static final int VEERTIEN = 14;
    private static final int ZESTIEN  = 16;

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
     * Retourmeert een nieuwe {@link Relatie} van de soort {@link SoortRelatie#FAMILIERECHTELIJKE_BETREKKING}.
     *
     * @return een nieuwe relatie van de soort familie rechtelijke betrekking.
     */
    public static Relatie newFamilieRechtelijkeBetrekking() {
        Relatie familieRechtelijkeBetrekking = new Relatie();
        familieRechtelijkeBetrekking.setSoortRelatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        return familieRechtelijkeBetrekking;
    }

    /**
     * Retourneert een nieuwe {@link Betrokkenheid} van de soort {@link SoortBetrokkenheid#KIND}.
     *
     * @return een nieuwe betrokkenheid van de soort 'kind'.
     */
    public static Betrokkenheid newKindBetrokkenheid() {
        Betrokkenheid betrokkenheid = new Betrokkenheid();
        betrokkenheid.setSoortBetrokkenheid(SoortBetrokkenheid.KIND);
        return betrokkenheid;
    }

    /**
     * Retourneert een nieuwe {@link Betrokkenheid} van de soort {@link SoortBetrokkenheid#OUDER}.
     *
     * @return een nieuwe betrokkenheid van de soort 'ouder'.
     */
    public static Betrokkenheid newOuderBetrokkenheid() {
        Betrokkenheid betrokkenheid = new Betrokkenheid();
        betrokkenheid.setSoortBetrokkenheid(SoortBetrokkenheid.OUDER);
        return betrokkenheid;
    }

    /**
     * Bepaalt de soort actie aan de hand van de binding Unmarshalling context. zie ook
     * {@link BindingUtil#XMLELEMENTNAARSOORTACTIEMAPPING}
     *
     * @param ctx De unmarshalling context.
     * @return De soortactie.
     * @throws JiBXException Indien een functie wordt aangeroepen op de context die niet past bij de huidige state
     * van de context.
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
        if (b != null && b) {
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
        return "J".equals(s);
    }

    /**
     * Converteer Date objet naar String DatumTijd formaat.
     *
     * @param date datum
     * @return tijd in de formaat yyyyMMddHHmmss[honderdsteseconden]
     */
    public static String dateNaarDatumTijd(final Date date) {
        String resultaat;

        if (date != null) {
            //Milliseconden verwaarloosbaar
            resultaat = (new SimpleDateFormat("yyyyMMddHHmmssSSS").format(date)).substring(0, ZESTIEN);
        } else {
            resultaat = "";
        }

        return resultaat;
    }

    /**
     * Converteer DatumTijd naar Date object.
     *
     * @param datumTijd datum tijd in het formaat yyyyMMddHHmmss[honderdsteseconden]
     * @return Date object
     * @throws ParseException als de invoer niet klopt
     */
    public static Date datumTijdNaarDate(final String datumTijd) throws ParseException {
        Date resultaat;

        if (StringUtils.isNotBlank(datumTijd)) {
            Date date = new SimpleDateFormat("yyyyMMddHHmmssSSS").parse(datumTijd);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            //Omzetten naar milliseconden
            String hondersteSeconden = datumTijd.substring(VEERTIEN, ZESTIEN);
            cal.set(Calendar.MILLISECOND, Integer.parseInt(hondersteSeconden) * TIEN);

            resultaat = cal.getTime();

        } else {
            resultaat = null;
        }

        return resultaat;
    }
}
