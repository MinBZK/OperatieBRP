/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels.context;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;


/**
 * De interface voor de context voor bedrijfsregels die voornamelijk gegevens uit het bericht valideren.
 */
public class BerichtRegelContext extends AbstractRegelContext implements RegelContext {

    private BerichtIdentificeerbaar       nieuweSituatie;
    private SoortAdministratieveHandeling soortAdministratieveHandeling;
    private BerichtBericht                bericht;

    /**
     * Instantieert een nieuwe autorisatie regel context.
     *
     * @param nieuweSituatie                de nieuwe situatie
     * @param soortAdministratieveHandeling de soort administratieve handeling
     * @param bericht                       het bericht
     */
    public BerichtRegelContext(final BerichtIdentificeerbaar nieuweSituatie,
            final SoortAdministratieveHandeling soortAdministratieveHandeling,
            final BerichtBericht bericht)
    {
        this.nieuweSituatie = nieuweSituatie;
        this.soortAdministratieveHandeling = soortAdministratieveHandeling;
        this.bericht = bericht;
    }

    /**
     * Instantieert een nieuwe autorisatie regel context.
     *
     * @param nieuweSituatie                de nieuwe situatie
     * @param soortAdministratieveHandeling de soort administratieve handeling
     */
    public BerichtRegelContext(final BerichtIdentificeerbaar nieuweSituatie,
            final SoortAdministratieveHandeling soortAdministratieveHandeling)
    {
        this.nieuweSituatie = nieuweSituatie;
        this.soortAdministratieveHandeling = soortAdministratieveHandeling;
    }

    /**
     * Instantieert een nieuwe autorisatie regel context.
     *
     * @param nieuweSituatie de nieuwe situatie
     */
    public BerichtRegelContext(final BerichtIdentificeerbaar nieuweSituatie) {
        this.nieuweSituatie = nieuweSituatie;
    }

    /**
     * Instantieert een nieuwe autorisatie regel context.
     *
     * @param bericht het bericht
     */
    public BerichtRegelContext(final BerichtBericht bericht) {
        this.bericht = bericht;
    }

    public final BerichtIdentificeerbaar getNieuweSituatie() {
        return nieuweSituatie;
    }

    public final SoortAdministratieveHandeling getSoortAdministratieveHandeling() {
        return soortAdministratieveHandeling;
    }

    public final BerichtBericht getBericht() {
        return bericht;
    }

    @Override
    public final BrpObject getSituatie() {
        return bericht;
    }
}
