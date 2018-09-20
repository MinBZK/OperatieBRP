/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.ber;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingsresultaat;
import nl.bzk.brp.model.bericht.ber.basis.AbstractBerichtResultaatGroepBericht;
import nl.bzk.brp.model.logisch.ber.BerichtResultaatGroep;


/**
 *
 *
 */
public class BerichtResultaatGroepBericht extends AbstractBerichtResultaatGroepBericht implements BerichtResultaatGroep
{

    /**
     * @TODO Kijken of we dit kunnen genereren of op een andere manier oplossen.
     * Het is belangrijk dat voor antwoord berichten de code is ingevuld. Want in de xml communiceren we met codes.
     * Dit om "missing required object" meldingen te voorkomen. (Meer info: Oussama)
     * @param verwerking x.
     */
    @Override
    public void setVerwerking(final Verwerkingsresultaat verwerking) {
        super.setVerwerking(verwerking);
        if (verwerking == null) {
            // bolie: zet deze op 'G', blijkbaar wordt voor bevraging de verwerking nooit gezet
            setVerwerking(Verwerkingsresultaat.VERWERKING_GESLAAGD);
        }
    }

}
