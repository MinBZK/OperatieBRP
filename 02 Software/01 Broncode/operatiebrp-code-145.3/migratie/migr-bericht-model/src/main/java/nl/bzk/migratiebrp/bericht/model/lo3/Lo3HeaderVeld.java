/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3;

import java.util.regex.Pattern;

/**
 * LO3 header veld.
 */
public enum Lo3HeaderVeld {

    /* ************************************** */
    /* ************************************** */
    /* ************************************** */

    /**
     * Random key.
     */
    RANDOM_KEY("Random key", 8, LengteType.VAST_GEDEFINIEERDE_LENGTE, Type.NUMERIEK),

    /**
     * Berichtnummer.
     */
    BERICHTNUMMER("Berichtnummer", 4, LengteType.VAST_GEDEFINIEERDE_LENGTE, Type.ALPHANUMERIEK),

    /* ************************************** */
    /* ************************************** */
    /* ************************************** */

    /**
     * (Nieuwe) A-nummer.
     */
    A_NUMMER("A-nummer", 10, LengteType.VAST_GEDEFINIEERDE_LENGTE, Type.NUMERIEK),

    /**
     * Aantal.
     */
    AANTAL("Aantal", 3, LengteType.VAST_GEDEFINIEERDE_LENGTE, Type.NUMERIEK),

    /**
     * Adresfunctie.
     */
    ADRESFUNCTIE("Adresfunctie", 1, LengteType.VAST_GEDEFINIEERDE_LENGTE, Type.ALPHANUMERIEK),

    /**
     * Aktenummer.
     */
    AKTENUMMER("Aktenummer", 7, LengteType.VAST_GEDEFINIEERDE_LENGTE, Type.ALPHANUMERIEK),

    /**
     * Berichtinhoud (Vrij bericht alleen).
     */
    BERICHT("Bericht", 99_999, LengteType.VAST_GEDEFINIEERDE_LENGTE, Type.ALPHANUMERIEK),

    /**
     * Datum.
     */
    DATUM("Datum", 8, LengteType.VAST_GEDEFINIEERDE_LENGTE, Type.NUMERIEK),

    /**
     * Datum.
     */
    DATUM_GELDIGHEID("Datum geldigheid", 8, LengteType.VAST_GEDEFINIEERDE_LENGTE, Type.NUMERIEK),

    /**
     * Datum/Tijd.
     */
    DATUM_TIJD("Datum/Tijd", 17, LengteType.VAST_GEDEFINIEERDE_LENGTE, Type.NUMERIEK),

    /**
     * Foutreden.
     */
    FOUTREDEN("Foutreden", 1, LengteType.VAST_GEDEFINIEERDE_LENGTE, Type.ALPHANUMERIEK),

    /**
     * Gemeente.
     */
    GEMEENTE("Gemeente", 4, LengteType.VAST_GEDEFINIEERDE_LENGTE, Type.NUMERIEK),

    /**
     * Gevraagde rubrieken.
     */
    GEVRAAGDE_RUBRIEKEN("Gevraagde rubrieken", 6, LengteType.VARIABEL_GEDEFINIEERDE_LENGTE, Type.NUMERIEK),

    /**
     * Gezochte persoon.
     */
    GEZOCHTE_PERSOON("Gezochte persoon", 1, LengteType.VAST_GEDEFINIEERDE_LENGTE, Type.ALPHANUMERIEK),

    /**
     * Herhaling.
     */
    HERHALING("Herhaling", 1, LengteType.VAST_GEDEFINIEERDE_LENGTE, Type.NUMERIEK),

    /**
     * Identificatie.
     */
    IDENTIFICATIE("Identificatie", 1, LengteType.VAST_GEDEFINIEERDE_LENGTE, Type.ALPHANUMERIEK),

    /**
     * Identificerende rubrieken (Tb02 bericht).
     */
    IDENTIFICERENDE_RUBRIEKEN("Identificerende rubrieken", 6, LengteType.VARIABEL_GEDEFINIEERDE_LENGTE, Type.NUMERIEK),

    /**
     * Lengte bericht (Vrij bericht alleen).
     */
    LENGTE_BERICHT("Lengte bericht", 5, LengteType.VAST_GEDEFINIEERDE_LENGTE, Type.NUMERIEK),

    /**
     * Oud A-nummer.
     */
    OUD_A_NUMMER("Oud A-nummer", 10, LengteType.VAST_GEDEFINIEERDE_LENGTE, Type.NUMERIEK),

    /**
     * Status.
     */
    STATUS("Status", 1, LengteType.VAST_GEDEFINIEERDE_LENGTE, Type.ALPHANUMERIEK),

    /* ************************************** */
    /* ************************************** */
    /* ************************************** */

    /**
     * Notification type (Status Report).
     */
    NOTIFICATION_TYPE("Notification type", 1, LengteType.VAST_GEDEFINIEERDE_LENGTE, Type.NUMERIEK),

    /**
     * Non delivery reason (Delivery Report).
     */
    NON_DELIVERY_REASON("Non delivery reason", 4, LengteType.VAST_GEDEFINIEERDE_LENGTE, Type.NUMERIEK);

    private final String omschrijving;
    private final int elementLengte;
    private final LengteType lengte;
    private final Type type;

    Lo3HeaderVeld(final String omschrijving, final int elementLengte, final LengteType lengte, final Type type) {
        this.omschrijving = omschrijving;
        this.elementLengte = elementLengte;
        this.lengte = lengte;
        this.type = type;
    }

    /**
     * Geeft de omschrijving terug.
     * @return omschrijving
     */
    public String getOmschrijving() {
        return omschrijving;
    }

    /**
     * Geeft de lengte terug.
     * @param aantalElementen Het aantal elementen waaruit de header bestaat.
     * @return De lengte.
     */
    public int getLengte(final int aantalElementen) {
        return LengteType.VAST_GEDEFINIEERDE_LENGTE.equals(lengte) ? elementLengte : aantalElementen * elementLengte;
    }

    /**
     * Geeft de lengte terug van een element.
     * @return De lengte.
     */
    public int getElementLengte() {
        return elementLengte;
    }

    /**
     * Geeft de lengtetype terug van een element.
     * @return De lengtetype.
     */
    public LengteType getLengteType() {
        return lengte;
    }

    /**
     * Geeft het type terug.
     * @return Het type.
     */
    public Type getType() {
        return type;
    }

    /**
     * Header veld lengte type.
     */
    public enum LengteType {
        /**
         * Variabel defenieerde lengte (afhankelijk van het header veld AANTAL en de element lengte).
         */
        VARIABEL_GEDEFINIEERDE_LENGTE,

        /**
         * Vast defenieerde lengte (onafhankelijk van het header veld AANTAL en is gelijk aan de element lengte).
         */
        VAST_GEDEFINIEERDE_LENGTE

    }

    /**
     * Controleer of de header syntactisch valide is (geen lengte controle).
     * @param value waarde
     * @return true als de header syntactisch valide is, anders false
     */
    public boolean isValide(final String value) {
        return type.isValide(value);
    }

    /**
     * Formateer een header veld.
     * @param value veld waarde
     * @return geformateerd veld waarde (vaste lengte)
     */
    public String format(final String value) {
        return type.format(value, elementLengte, lengte);
    }

    /**
     * Header veld type.
     */
    private enum Type {
        /**
         * Numeriek.
         */
        NUMERIEK(Pattern.compile("^[0-9]*$")) {
            @Override
            String format(final String waarde, final int elementVeldLengte, final LengteType lengteType) {
                if (LengteType.VAST_GEDEFINIEERDE_LENGTE.equals(lengteType)) {

                    // Vaste lengte.

                    if (waarde != null && waarde.length() > elementVeldLengte) {
                        throw new IllegalArgumentException(
                                "Te lange waarde voor numeriek header veld (lengte=" + elementVeldLengte + ", waarde= " + waarde + " ).");
                    }

                    final StringBuilder result = new StringBuilder(elementVeldLengte);
                    if (waarde != null) {
                        result.append(waarde);
                    }

                    while (result.length() < elementVeldLengte) {
                        result.insert(0, '0');
                    }

                    return result.toString();
                } else {
                    return verwerkVariableLengte(waarde, elementVeldLengte);


                }
            }
        },

        /**
         * Alphanumeriek.
         */
        ALPHANUMERIEK(Pattern.compile("^[A-Za-z0-9 ]*$")) {
            @Override
            String format(final String waarde, final int elementVeldLengte, final LengteType lengteType) {
                if (LengteType.VAST_GEDEFINIEERDE_LENGTE.equals(lengteType)) {

                    // Vaste lengte.

                    if (waarde != null && waarde.length() > elementVeldLengte) {
                        throw new IllegalArgumentException(
                                "Te lange waarde voor alphanumeriek header veld (lengte=" + elementVeldLengte + ", waarde=" + waarde + ").");
                    }

                    final StringBuilder result = new StringBuilder(elementVeldLengte);
                    if (waarde != null) {
                        result.append(waarde);
                    }

                    while (result.length() < elementVeldLengte) {
                        result.insert(0, '0');
                    }

                    return result.toString();
                } else {
                    return verwerkVariableLengte(waarde, elementVeldLengte);
                }

            }
        };

        private final Pattern pattern;

        Type(final Pattern pattern) {
            this.pattern = pattern;
        }

        abstract String format(final String waarde, final int elementVeldLengte, final LengteType lengteType);

        private static String verwerkVariableLengte(final String waarde, final int elementVeldLengte) {
            // Variabele lengte.

            final int aantalElementen =
                    waarde == null ? 0
                            : waarde.length() % elementVeldLengte > 0 ? 1 + waarde.length() / elementVeldLengte
                                    : waarde.length() / elementVeldLengte;
            final StringBuilder result = new StringBuilder(aantalElementen * elementVeldLengte);

            if (waarde != null) {
                result.append(waarde);
            }

            while (result.length() < aantalElementen * elementVeldLengte) {
                result.insert(0, '0');
            }

            return result.toString();
        }

        /**
         * Geeft aan of de waarde geldig is voor dit headerveld.
         * @param value De waarde die gecontroleerd dient te worden.
         * @return True indien de waarde geldig is, false in alle andere gevallen.
         */
        public boolean isValide(final String value) {
            return pattern.matcher(value).matches();
        }
    }

}
