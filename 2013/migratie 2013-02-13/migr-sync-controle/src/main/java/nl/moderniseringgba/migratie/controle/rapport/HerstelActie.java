/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.controle.rapport;

/**
 * Bevat de gegevens voor een herstelactie.
 */
public class HerstelActie {

    private Long anummer;
    private HerstelActieEnum actie;
    private String verschil;

    /**
     * @return the anummer
     */
    public final Long getAnummer() {
        return anummer;
    }

    /**
     * @param anummer
     *            the anummer to set
     */
    public final void setAnummer(final Long anummer) {
        this.anummer = anummer;
    }

    /**
     * @return the actie
     */
    public final HerstelActieEnum getActie() {
        return actie;
    }

    /**
     * @param actie
     *            the actie to set
     */
    public final void setActie(final HerstelActieEnum actie) {
        this.actie = actie;
    }

    /**
     * @return the verschil
     */
    public final String getVerschil() {
        return verschil;
    }

    /**
     * @param verschil
     *            the verschil to set
     */
    public final void setVerschil(final String verschil) {
        this.verschil = verschil;
    }

}
