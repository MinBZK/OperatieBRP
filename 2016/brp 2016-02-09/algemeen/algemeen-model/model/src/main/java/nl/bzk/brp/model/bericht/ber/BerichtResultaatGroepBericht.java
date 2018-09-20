/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.ber;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingsresultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.VerwerkingsresultaatAttribuut;
import nl.bzk.brp.model.logisch.ber.BerichtResultaatGroep;


/**
 *
 *
 */
public final class BerichtResultaatGroepBericht extends AbstractBerichtResultaatGroepBericht
    implements BerichtResultaatGroep
{

    /**
     * Het is belangrijk dat voorantwoord berichten de code is ingevuld. Want in de xml communiceren we met codes. Dit om "missing required object"
     * meldingen te voorkomen.
     *
     * @param verwerking x.
     */
    @Override
    public void setVerwerking(final VerwerkingsresultaatAttribuut verwerking) {
        super.setVerwerking(verwerking);
        if (verwerking == null) {
            // zet deze op 'G', voor bevraging wordt de verwerking nooit gezet
            setVerwerking(new VerwerkingsresultaatAttribuut(Verwerkingsresultaat.GESLAAGD));
        }
    }

}
