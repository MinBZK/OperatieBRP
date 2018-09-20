/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.binding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import nl.bzk.brp.model.administratie.CommunicatieIdMap;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.basis.VasteWaardeAttribuutType;
import org.apache.commons.lang.StringUtils;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;
import org.jibx.runtime.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/** Utility class gebruikt door Jibx databinding. */
public final class BindingUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(BindingUtil.class);

    /** tijd in het formaat yyyy-MM-ddTHH:mm:ss.[honderdsteseconden] . */
    private static final String DATUMTIJDSTIP_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    private static final int    LENGTE_DATUM         = 8;
    private static final int    LENGTE_JAAR          = 4;
    private static final int    LENGTE_MAAND         = 2;

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
     * Retourneert een string representatie van een getal.
     *
     * @param getal het getal waarvoor de string representatie gewenst is.
     * @return de string representatie van het getal.
     */
    public static String getalNaarString(final Number getal) {
        String string = null;
        if (getal != null) {
            string = String.valueOf(getal);
        }
        return string;
    }

    /**
     * Retourneert een string representatie van een getal, waarbij de string eventueel wordt aangevuld met
     * voorloopnullen naar de minimumlengte, indien het getal minder lang is.
     *
     * @param getal het getal waarvoor de string representatie gewenst is.
     * @param stringLengte de minumlengte van de string. Indien het getal kleiner is, wordt de string opgevuld met
     * voorloopnullen.
     * @return de string representatie van het getal, met de opgegeven minimum lengte, aangevuld met eventueel
     *         voorloopnullen.
     */
    public static String getalNaarStringMetVoorloopnullen(final Number getal, final int stringLengte) {
        String string = null;
        if (getal != null) {
            string = StringUtils.leftPad(String.valueOf(getal), stringLengte, "0");
        }
        return string;
    }

    /**
     * Zet een tekstuele representatie van een getal om naar een {@link java.lang.Long}.
     *
     * @param string de string die omgezet dient te worden.
     * @return een long.
     */
    public static Long stringNaarLong(final String string) {
        Long resultaat = null;
        if (StringUtils.isNotBlank(string)) {
            assertIsNumeriek(string);
            resultaat = Long.parseLong(string);
        }
        return resultaat;
    }

    /**
     * Zet een tekstuele representatie van een getal om naar een {@link java.lang.Integer}.
     *
     * @param string de string die omgezet dient te worden.
     * @return een integer.
     */
    public static Integer stringNaarInteger(final String string) {
        Integer resultaat = null;
        if (StringUtils.isNotBlank(string)) {
            assertIsNumeriek(string);
            resultaat = Integer.parseInt(string);
        }
        return resultaat;
    }

    /**
     * Zet een tekstuele representatie van een getal om naar een {@link java.lang.Short}.
     *
     * @param string de string die omgezet dient te worden.
     * @return een short.
     */
    public static Short stringNaarShort(final String string) {
        Short resultaat = null;
        if (StringUtils.isNotBlank(string)) {
            assertIsNumeriek(string);
            resultaat = Short.parseShort(string);
        }
        return resultaat;
    }

    /**
     * Dwingt af dat een string een numerieke inhoud heeft.
     * Indien dat niet zo is, wordt er een IllegalArgumentException gegooit.
     *
     * @param string de input string
     */
    private static void assertIsNumeriek(final String string) {
        if (!StringUtils.isNumeric(string)) {
            final String bericht = String.format("'%s' is geen numerieke waarde.", string);
            LOGGER.error(bericht);
            throw new IllegalArgumentException(bericht);
        }
    }

    /**
     * Zet een {@link java.lang.Boolean} om naar zijn tekstuele representatie ('J' of 'N').
     *
     * @param bool de boolean die omgezet dient te worden.
     * @return een tekstuele representatie van de boolean.
     */
    public static String booleanNaarString(final Boolean bool) {
        String resultaat = null;
        if (bool != null) {
            if (bool) {
                resultaat = "J";
            } else {
                resultaat = "N";
            }
        }
        return resultaat;
    }

    /**
     * Zet een een tekstuele representatie van een boolean om naar de boolean waarde.
     *
     * @param string de tekstuele representatie van een boolean ('J' of 'N').
     * @return een boolean.
     */
    public static Boolean stringNaarBoolean(final String string) {
        Boolean resultaat = null;
        if (StringUtils.isNotBlank(string)) {
            if ("J".equals(string)) {
                resultaat = true;
            } else if ("N".equals(string)) {
                resultaat = false;
            } else {
                final String bericht = "Conversie onmogelijk vanwege ongeldig waarde voor veld: " + string;
                LOGGER.error(bericht);
                throw new IllegalArgumentException(bericht);
            }
        }
        return resultaat;
    }

    /**
     * Retourneert de tekstuele representatie (de waarde) van de opgegeven enumeratie waarde.
     *
     * @param enumeratiewaarde de enumeratie waarde.
     * @return de tekstuele representatie (de waarde) van de enumeratie waarde.
     */
    public static String enumeratiewaardeNaarString(final VasteWaardeAttribuutType<?> enumeratiewaarde) {
        final String resultaat;
        if (enumeratiewaarde == null) {
            resultaat = null;
        } else {
            resultaat = enumeratiewaarde.getWaarde().toString();
        }
        return resultaat;
    }

    /**
     * Zet een tekstuele representatie van een enumeratiewaarde van een opgegeven enumeratie om naar zijn enumeratie
     * instantie.
     *
     * @param waarde de tekstuele representatie van een enumeratie waarde.
     * @param clazz de enumeratie klasse.
     * @param <T> het type van de enumeratie klasse.
     * @return een enumeratie waarde.
     */
    public static <T extends Enum<T> & VasteWaardeAttribuutType<?>> T stringNaarEnumeratiewaarde(
            final String waarde, final Class<T> clazz)
    {
        T enumWaarde = null;

        if (waarde != null) {
            for (T enumConstante : clazz.getEnumConstants()) {
                if (waarde.equals(enumConstante.getWaarde().toString())) {
                    enumWaarde = enumConstante;
                    break;
                }
            }
        }
        return enumWaarde;
    }

    /**
     * Zet een getal repesentatie van een (deels onbekende) datum om naar een tekstuele representatie dat dezelfde
     * datum representeert.
     *
     * @param datum een tekstuele representatie van een (deels onbekende) datum.
     * @return een getal representatie van een (deels onbekende) datum.
     */
    public static String datumAlsGetalNaarDatumAlsString(final Integer datum) {
        String resultaat = null;
        if (datum != null) {
            resultaat = "";
            String dateString = String.valueOf(datum);
            String jaar = dateString.substring(0, LENGTE_JAAR);
            String maand = dateString.substring(LENGTE_JAAR, LENGTE_JAAR + LENGTE_MAAND);
            String dag = dateString.substring(LENGTE_JAAR + LENGTE_MAAND, LENGTE_DATUM);
            resultaat += jaar;
            if (!maand.equals("00")) {
                resultaat += "-";
                resultaat += maand;
                if (!dag.equals("00")) {
                    resultaat += "-";
                    resultaat += dag;
                }
            }
        }
        return resultaat;
    }

    /**
     * Zet een tekstuele repesentatie van een (deels onbekende) datum om naar een getal dat dezelfde datum
     * representeert.
     *
     * @param datum een tekstuele representatie van een (deels onbekende) datum.
     * @return een getal representatie van een (deels onbekende) datum.
     */
    public static Integer datumAlsStringNaarDatumAlsGetal(final String datum) {
        final Integer resultaat;
        if (StringUtils.isNotBlank(datum)) {
            String dateString = datum.replace("-", "");
            if (dateString.length() != LENGTE_DATUM) {
                dateString = StringUtils.rightPad(dateString, LENGTE_DATUM, '0');
            }
            resultaat = Integer.parseInt(dateString);
        } else {
            resultaat = null;
        }
        return resultaat;
    }

    /**
     * Zet een {@link java.util.Date} instantie om naar een tekstuele representatie, waarbij het formaat dat gebruikt
     * wordt voldoet aan de W3C en de koppelvlak standaard (en daarmee aan de StUF standaard).
     *
     * @param datumTijd het tijdstip dat omgezet dient te worden.
     * @return een W3C compliant datum formaat string.
     */
    public static String javaDateNaarW3cDatumString(final Date datumTijd) {
        final String resultaat;
        if (datumTijd != null) {
            resultaat = new SimpleDateFormat(DATUMTIJDSTIP_FORMAT).format(datumTijd);
        } else {
            resultaat = "";
        }
        return resultaat;
    }

    /**
     * Zet een W3C compliant datum/tijdstip string om naar een {@link java.util.Date} instantie.
     *
     * @param tijdstip het tijdstip (W3C compliant formaat) dat omgezet dient te worden.
     * @return een datum instantie.
     */
    public static Date w3cDatumStringNaarJavaDate(final String tijdstip) {
        final Date datum;
        if (StringUtils.isBlank(tijdstip)) {
            datum = null;
        } else {
            try {
                datum = Utility.deserializeDateTime(tijdstip);
            } catch (JiBXException e) {
                final String bericht = "Conversie onmogelijk vanwege ongeldig datum/tijdstip formaat: " + tijdstip;
                LOGGER.error(bericht, e);
                throw new IllegalArgumentException(bericht);
            }
        }
        return datum;
    }

    /**
     * Zet de opgegeven {@link Ja} instantie om naar een String representatie.
     *
     * @param ja het Ja veld dat omgezet dient te worden.
     * @return de String variant van het opgegeven Ja veld.
     */
    public static String jaNaarString(final Ja ja) {
        final String resultaat;
        if (ja == null) {
            resultaat = null;
        } else {
            resultaat = booleanNaarString(ja.getWaarde());
        }
        return resultaat;
    }

    /**
     * Zet de opgegeven string om naar een {@link Ja} instantie.
     *
     * @param string de string die omgezet dient te worden.
     * @return een geldige {@link Ja} instantie.
     */
    public static Ja stringNaarJa(final String string) {
        final Ja resultaat;
        if (StringUtils.isBlank(string)) {
            resultaat = null;
        } else {
            final Boolean bool = stringNaarBoolean(string);
            if (Ja.J.getWaarde().equals(bool)) {
                resultaat = Ja.J;
            } else {
                final String bericht = "Conversie onmogelijk vanwege ongeldige waarde voor Ja: " + bool;
                LOGGER.error(bericht);
                throw new IllegalArgumentException(bericht);
            }
        }
        return resultaat;
    }

    /**
     * Voegt een identificerend object toe aan de jibx context.
     *
     * @param ctx    context van jibx
     * @param object het object dat toegoevoegd moet worden aan de context
     */
    public static void voegToeIdentificeerbaarObjectAanJixbUserContext(final IUnmarshallingContext ctx,
        final Identificeerbaar object)
    {
        if (ctx.getUserContext() == null) {
            CommunicatieIdMap communicatieIds = new CommunicatieIdMap();
            communicatieIds.put(object);
            ctx.setUserContext(communicatieIds);
        } else {
            ((CommunicatieIdMap) ctx.getUserContext()).put(object);
        }
    }
}
