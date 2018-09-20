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
 * Interface voor (bedrijfs)regels die uitgevoerd/gecontroleerd worden na de verwerking.
 *
 * @param <M> Type van het (database) root object dat binnen de actie gecontroleerd wordt.
 * @param <B> Type van het bericht specifieke object behorende bij de actie van deze regel.
 */
public interface NaVerwerkingRegel<M extends RootObject, B extends BerichtBericht> extends RegelInterface {

    /**
     * Functie die de bedrijfsregel implementatie bevat voor uitvoer.
     *
     * @param modelObject                   de situatie van het root object van de actie na verwerking van de actie.
     * @param bericht                       het bericht
     * @param soortAdministratieveHandeling de soort administratieve handeling die uitgevoerd wordt.
     * @return Lijst van objecten die de regel overtreden.
     */
    List<BerichtIdentificeerbaar> voerRegelUit(M modelObject, B bericht,
            SoortAdministratieveHandeling soortAdministratieveHandeling);

}
