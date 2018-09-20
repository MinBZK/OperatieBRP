/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.dashboard;

/**
 * Foutmelding uit het response bericht.
 */
public class Melding {

    private MeldingSoort soort;

    private String       tekst;

    /**
     * Constructor.
     */
    public Melding() {
    }

    /**
     * Constructor van dashboard melding op basis van een BRP melding.
     *
     * @param melding BRP melding
     */
    public Melding(final nl.bzk.brp.model.validatie.Melding melding) {
        switch (melding.getSoort()) {
            case INFORMATIE:
                soort = MeldingSoort.INFORMATIE;
                break;
            case WAARSCHUWING:
                soort = MeldingSoort.WAARSCHUWING;
                break;
            case OVERRULEBAAR:
                soort = MeldingSoort.OVERRULEBAAR;
                break;
            case FOUT:
                soort = MeldingSoort.FOUT;
                break;
            default:
                throw new RuntimeException("onbekende Soortmelding;: " + melding.getSoort().name());
        }
        tekst = melding.getOmschrijving();
    }

    public MeldingSoort getSoort() {
        return soort;
    }

    public void setSoort(final MeldingSoort soort) {
        this.soort = soort;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(final String tekst) {
        this.tekst = tekst;
    }

}
