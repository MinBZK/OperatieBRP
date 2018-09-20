/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.exceptie;

/**
 * Exceptie die aangeeft dat een opgegeven referentie niet gevonden kon worden. Het gaat hierbij om referenties via
 * codes naar records/entities in de database.
 */
public final class OnbekendeReferentieExceptie extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private static final String ONBEKENDE_REFERENTIE_CODE_GEBRUIKT_VOOR_VELD = "Onbekende referentie/code (waarde: '%s') gebruikt voor veld '%s'.";

    private final ReferentieVeld referentieVeld;
    private final String referentieWaarde;

    /**
     * Standaard constructor die het foutbericht zet en de waarde en het type van de ongeldige referentie.
     * @param veld het type van de ongeldige referentie.
     * @param waarde de waarde van de ongeldige referentie.
     * @param throwable de initiele exceptie (indien aanwezig).
     */
    public OnbekendeReferentieExceptie(final ReferentieVeld veld, final Object waarde, final Throwable throwable) {
        super(String.format(ONBEKENDE_REFERENTIE_CODE_GEBRUIKT_VOOR_VELD, waarde,
                veld.getNaam()), throwable);
        referentieVeld = veld;
        if (waarde == null) {
            referentieWaarde = null;
        } else {
            referentieWaarde = waarde.toString();
        }
    }

    /**
     * Standaard constructor die het foutbericht zet en de waarde en het type van de ongeldige referentie.
     * @param veld het type van de ongeldige referentie.
     * @param waarde de waarde van de ongeldige referentie.
     */
    public OnbekendeReferentieExceptie(final ReferentieVeld veld, final Object waarde) {
        super(String.format(ONBEKENDE_REFERENTIE_CODE_GEBRUIKT_VOOR_VELD, waarde, veld.getNaam()));
        referentieVeld = veld;
        if (waarde == null) {
            referentieWaarde = null;
        } else {
            referentieWaarde = waarde.toString();
        }
    }

    /**
     * Retourneert de (indicatieve) naam van het veld dat fout/onbekend was.
     * @return de (indicatieve) naam van het veld dat fout/onbekend was.
     */
    public String getReferentieVeldNaam() {
        return referentieVeld.getNaam();
    }

    /**
     * Retourneert de waarde van de ongeldige referentie.
     * @return de waarde van de ongeldige referentie.
     */
    public String getReferentieWaarde() {
        return referentieWaarde;
    }

    /**
     * Enumeratie van referentievelden waarvoor de {@link OnbekendeReferentieExceptie}
     * gebruikt kan worden.
     */
    public enum ReferentieVeld {

        /** De gemeente code. */
        GEMEENTECODE("Gemeentecode"),
        /** De partij code. */
        PARTIJCODE("Partijcode"),
        /** De OIN code. */
        OINCODE("OINcode"),
        /** De land / gebied code. */
        LANDGEBIEDCODE("LandGebiedCode"),
        /** De woonplaatsnaam. */
        PLAATSNAAM("Woonplaatsnaam"),
        /** Nationaliteit code. */
        NATIONALITITEITCODE("Nationaliteitcode"),
        /** RedenWijzigingAdres code. */
        REDENWIJZINGADRES("RedenWijzigingAdres"),
        /** AangeverAdreshouding code. */
        AANGEVERADRESHOUDING("AangeverAdreshouding"),
        /** AdellijkeTitel code. */
        ADELLIJKETITEL("AdellijkeTitel"),
        /** Predikaat code. */
        PREDICAAT("Predikaat"),
        /** Reden verkrijgen NL nationaliteit. **/
        REDENVERKRIJGENNLNATION("RedenVerkrijgenNLNationaliteit"),
        /** Reden verlies NL nationaliteit.  **/
        REDENVERLIESNLNATION("RedenVerliesNLNationaliteit"),
        /** Reden vervallen reisdocument.  **/
        REDENVERVALLENREISDOUCMENT("RedenVervallenReisdocument"),
        /** Reden einde relatie. */
        REDENEINDERELATIE("RedenEindeRelatie"),
        /** Soort Document.  **/
        SOORTDOCUMENT("SoortDocument"),
        /** Soort Reisdocument.  **/
        SOORTREISDOCUMENT("SoortReisdocument"),
        /** leveringsautorisatienaam. **/
        LEVERINGSAUTORISATIENAAM("LeveringsautorisatieNaam"),
        /** Elementnaam. **/
        ELEMENTNAAM("Elementnaam"),
        /** Persoon Identifier. */
        PERSOON_ID("PersoonId"),
        /** Dienst Identifier. */
        DIENST_ID("DienstId"),
        /** Soort actie. */
        SOORT_ACTIE("SoortActie"),
        /** Rechtsgrond Code. */
        RECHTSGRONDCODE("RechtsgrondCode"),
        /** Aktenummer. */
        AKTENUMMER("Aktenummer"),
        /** Leveringsautorisatie. */
        LEVERINGSAUTORISATIEID("Leveringautorisatie");

        private final String naam;

        /**
         * Standaard constructor die de (indicatieve) naam van een referentieveld direct zet.
         * @param naam de (indicatieve) naam van een referentieveld.
         */
        ReferentieVeld(final String naam) {
            this.naam = naam;
        }

        /**
         * Retourneert de (indicatieve) naam van het referentieveld.
         * @return de (indicatieve) naam van het referentieveld.
         */
        public String getNaam() {
            return naam;
        }

    }

}
