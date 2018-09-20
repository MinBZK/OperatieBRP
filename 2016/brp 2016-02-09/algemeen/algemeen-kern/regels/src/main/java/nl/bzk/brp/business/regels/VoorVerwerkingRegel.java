/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels;

import java.util.List;

import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;


/**
 * Interface voor (bedrijfs)regels die uitgevoerd/gecontroleerd worden voorafgaand aan de verwerking.
 *
 * @param <M> Type van het (database) root object dat binnen de actie gecontroleerd wordt.
 * @param <B> Type van het bericht specifieke root object dat binnen de actie gecontroleerd wordt.
 */
public interface VoorVerwerkingRegel<M extends RootObject, B extends BerichtBericht> extends RegelInterface {

    /**
     * Functie die de bedrijfsregel implementatie bevat voor uitvoer.
     *
     * @param huidigeSituatie               De huidige situatie zoals bekend in de BRP database.
     * @param nieuweSituatie                De nieuwe situatie die wordt meegegeven in het binnenkomende BRP bericht.
     *                                      systeem en gemapt worden op basis van de identificerende sleutel zoals deze voorkomt in het bericht.
     * @param soortAdministratieveHandeling de soort administratieve handeling die uitgevoerd wordt.
     * @param bericht                       het bericht root object.
     * @return Lijst van objecten die de regel overtreden.
     */
    List<BerichtIdentificeerbaar> voerRegelUit(M huidigeSituatie, B nieuweSituatie,
        SoortAdministratieveHandeling soortAdministratieveHandeling,
        BerichtIdentificeerbaar bericht);
}
