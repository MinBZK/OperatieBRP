/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.controle.rapport;

import java.util.Date;

/**
 * Bevate alle opties die meegegeven kunnen worden aan de controle.
 */
public class Opties {

    private Date vanaf;
    private Date tot;
    private ControleTypeEnum controleType;
    private String gemeenteCode;
    private ControleNiveauEnum controleNiveau;

    /**
     * @return the vanaf
     */
    public final Date getVanaf() {
        return vanaf;
    }

    /**
     * @param vanaf
     *            the vanaf to set
     */
    public final void setVanaf(final Date vanaf) {
        this.vanaf = vanaf;
    }

    /**
     * @return the tot
     */
    public final Date getTot() {
        return tot;
    }

    /**
     * @param tot
     *            the tot to set
     */
    public final void setTot(final Date tot) {
        this.tot = tot;
    }

    /**
     * @return the controleType
     */
    public final ControleTypeEnum getControleType() {
        return controleType;
    }

    /**
     * @param controleType
     *            the controleType to set
     */
    public final void setControleType(final ControleTypeEnum controleType) {
        this.controleType = controleType;
    }

    /**
     * @return the gemeenteCode
     */
    public final String getGemeenteCode() {
        return gemeenteCode;
    }

    /**
     * @param gemeenteCode
     *            the gemeenteCode to set
     */
    public final void setGemeenteCode(final String gemeenteCode) {
        this.gemeenteCode = gemeenteCode;
    }

    /**
     * @return the controleNiveau
     */
    public final ControleNiveauEnum getControleNiveau() {
        return controleNiveau;
    }

    /**
     * @param controleNiveau
     *            the controleNiveau to set
     */
    public final void setControleNiveau(final ControleNiveauEnum controleNiveau) {
        this.controleNiveau = controleNiveau;
    }

}
