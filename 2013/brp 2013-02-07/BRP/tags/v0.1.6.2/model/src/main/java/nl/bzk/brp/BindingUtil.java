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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortRelatie;
import org.apache.commons.lang.StringUtils;


/** Utility class gebruikt door Jibx databinding. */
public final class BindingUtil {

    private static final int TIEN     = 10;
    private static final int VEERTIEN = 14;
    private static final int ZESTIEN  = 16;


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
     * Retourmeert een nieuwe {@link RelatieBericht} van de soort {@link SoortRelatie#FAMILIERECHTELIJKE_BETREKKING}.
     *
     * @return een nieuwe relatie van de soort familie rechtelijke betrekking.
     */
    public static RelatieBericht newFamilieRechtelijkeBetrekking() {
        RelatieBericht familieRechtelijkeBetrekking = new RelatieBericht();
        familieRechtelijkeBetrekking.setSoort(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        return familieRechtelijkeBetrekking;
    }

    /**
     * Retourneert een nieuwe {@link BetrokkenheidBericht} van de soort {@link SoortBetrokkenheid#KIND}.
     *
     * @return een nieuwe betrokkenheid van de soort 'kind'.
     */
    public static BetrokkenheidBericht newKindBetrokkenheid() {
        BetrokkenheidBericht betrokkenheid = new BetrokkenheidBericht();
        betrokkenheid.setRol(SoortBetrokkenheid.KIND);
        return betrokkenheid;
    }

    /**
     * Retourneert een nieuwe {@link BetrokkenheidBericht} van de soort {@link SoortBetrokkenheid#OUDER}.
     *
     * @return een nieuwe betrokkenheid van de soort 'ouder'.
     */
    public static BetrokkenheidBericht newOuderBetrokkenheid() {
        BetrokkenheidBericht betrokkenheid = new BetrokkenheidBericht();
        betrokkenheid.setRol(SoortBetrokkenheid.OUDER);
        return betrokkenheid;
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
