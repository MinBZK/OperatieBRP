/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;


/**
 * De klasse Geboorte.
 */
public class Geboorte extends AdministratieveHandeling {

    /**
     * De nieuwgeborene.
     */
    private Persoon nieuwgeborene;

    /**
     * De ouder1.
     */
    private Persoon ouder1;

    /**
     * De ouder2.
     */
    private Persoon ouder2;


    /**
     * Instantieert een nieuwe geboorte.
     *
     * @param administratieveHandeling de administratieve handeling
     */
    public Geboorte(final AdministratieveHandeling administratieveHandeling) {
        super(administratieveHandeling);
    }

    /**
     * Haalt een nieuwgeborene op.
     *
     * @return nieuwgeborene
     */
    public Persoon getNieuwgeborene() {
        return nieuwgeborene;
    }

    /**
     * Instellen van nieuwgeborene.
     *
     * @param nieuwgeborene de nieuwe nieuwgeborene
     */
    public void setNieuwgeborene(final Persoon nieuwgeborene) {
        this.nieuwgeborene = nieuwgeborene;
    }

    /**
     * Haalt een ouder1 op.
     *
     * @return ouder1
     */
    public Persoon getOuder1() {
        return ouder1;
    }

    /**
     * Instellen van ouder1.
     *
     * @param ouder1 de nieuwe ouder1
     */
    public void setOuder1(final Persoon ouder1) {
        this.ouder1 = ouder1;
    }

    /**
     * Haalt een ouder2 op.
     *
     * @return ouder2
     */
    public Persoon getOuder2() {
        return ouder2;
    }

    /**
     * Instellen van ouder2.
     *
     * @param ouder2 de nieuwe ouder2
     */
    public void setOuder2(final Persoon ouder2) {
        this.ouder2 = ouder2;
    }

}
