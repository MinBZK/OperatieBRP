/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.dataaccess.exceptie;

/**
 * Exceptie die aangeeft dat een opgegeven referentie niet gevonden kon worden. Het gaat hierbij om referenties via
 * codes naar records/entities in de database.
 */
public class OnbekendeReferentieExceptie extends RuntimeException {

    private final ReferentieVeld referentieVeld;
    private final String referentieWaarde;

    /**
     * Standaard constructor die het foutbericht zet en de waarde en het type van de ongeldige referentie.
     * @param veld het type van de ongeldige referentie.
     * @param waarde de waarde van de ongeldige referentie.
     * @param throwable de initiele exceptie (indien aanwezig).
     */
    public OnbekendeReferentieExceptie(final ReferentieVeld veld, final Object waarde, final Throwable throwable) {
        super(String.format("Onbekende referentie/code (waarde: '%s') gebruikt voor veld '%s'.", waarde,
                veld.getNaam()), throwable);
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
     * Enumeratie van referentievelden waarvoor de {@link nl.bzk.copy.dataaccess.exceptie.OnbekendeReferentieExceptie} gebruikt kan worden.
     */
    public enum ReferentieVeld {

        /** De gemeente code. */
        GEMEENTECODE("Gemeentecode"),
        /** De land code. */
        LANDCODE("Landcode"),
        /** De woonplaats code. */
        PLAATSCODE("Woonplaatscode"),
        /** Nationaliteit code. */
        NATIONALITITEITCODE("Nationaliteitcode"),
        /** RedenWijzigingAdres code. */
        REDENWIJZINGADRES("RedenWijzigingAdres"),
        /** AangeverAdreshouding code. */
        AAANGEVERADRESHOUDING("AangeverAdreshouding"),
        /** AdellijkeTitel code. */
        ADELLIJKETITEL("AdellijkeTitel"),
        /** Predikaat code. */
        PREDIKAAT("Predikaat"),
        /** Reden verkrijgen NL nationaliteit. **/
        REDENVERKRIJGENNLNATION("RedenVerkrijgenNLNationaliteit"),
        /** Reden verlies NL nationaliteit.  **/
        REDENVERLIESNLNATION("RedenVerliesNLNationaliteit");

        private final String naam;

        /**
         * Standaard constructor die de (indicatieve) naam van een referentieveld direct zet.
         * @param naam de (indicatieve) naam van een referentieveld.
         */
        private ReferentieVeld(final String naam) {
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
