/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.jbehave.context;

/**
 * Threadlocal voor het vasthouden van de actuele leveringautorisaties.
 */
public final class AutAutContext {
    private static final ThreadLocal<AutAutContext> CONTEXT = ThreadLocal.withInitial(AutAutContext::new);

    private VerzoekAutorisatie autorisatieVoorVerzoek;

    /**
     * Geeft de {@link AutAutContext} terug.
     * 
     * @return een {@link AutAutContext}
     */
    public static AutAutContext get() {
        return CONTEXT.get();
    }

    /**
     * Reset alle autorisaties voor levering.
     */
    public void reset() {
        autorisatieVoorVerzoek = null;
    }

    /**
     * Geeft de autorisatie voor het verzoek terug.
     * 
     * @return autorisatie voor het verzoek
     */
    public VerzoekAutorisatie getAutorisatieVoorVerzoek() {
        return autorisatieVoorVerzoek;
    }

    /**
     * zet de autorisatie voor het verzoek.
     *  @param oinOndertekenaar OIN ondertekenaar
     * @param oinTransporteur OIN transporteur
     */
    public void setVerzoekBijhoudingsautorisatie(final String oinOndertekenaar, final String oinTransporteur) {
        autorisatieVoorVerzoek = new VerzoekAutorisatie();
        autorisatieVoorVerzoek.oinOndertekenaar = oinOndertekenaar;
        autorisatieVoorVerzoek.oinTransporteur = oinTransporteur;
    }

    /**
     * Autorisatie voor een verzoek.
     */
    public static class VerzoekAutorisatie {

        private String geautoriseerde;
        private String oinOndertekenaar;
        private String oinTransporteur;

        /**
         * Geeft de geautoriseerde terug.
         * 
         * @return de geautoriseerde
         */
        public String getGeautoriseerde() {
            return geautoriseerde;
        }

        /**
         * set de geautoriseerde.
         * 
         * @param geautoriseerde de geautoriseerde
         */
        public void setGeautoriseerde(final String geautoriseerde) {
            this.geautoriseerde = geautoriseerde;
        }

        /**
         * zet de OIN van de ondertekenaar.
         * 
         * @return de OIN
         */
        public String getOinOndertekenaar() {
            return oinOndertekenaar;
        }

        /**
         * zet de OIN van de transporteur.
         * 
         * @return de OIN
         */
        public String getOinTransporteur() {
            return oinTransporteur;
        }
    }
}
