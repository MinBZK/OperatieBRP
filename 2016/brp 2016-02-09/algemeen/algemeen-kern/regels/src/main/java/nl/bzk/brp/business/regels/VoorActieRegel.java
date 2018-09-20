/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels;

import java.util.List;
import java.util.Map;

import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtRootObject;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;


/**
 * Interface voor (bedrijfs)regels die uitgevoerd/gecontroleerd worden op actie niveau en wel voorafgaand aan de
 * verwerking van de actie.
 *
 * @param <M> Type van het (database) root object dat binnen de actie gecontroleerd wordt.
 * @param <B> Type van het bericht specifieke root object dat binnen de actie gecontroleerd wordt.
 */
public interface VoorActieRegel<M extends RootObject, B extends BerichtRootObject> extends RegelInterface {

    /**
     * Functie die de bedrijfsregel implementatie bevat voor uitvoer.
     *
     * @param huidigeSituatie      De huidige situatie zoals bekend in de BRP database.
     * @param nieuweSituatie       De nieuwe situatie die wordt meegegeven in het binnenkomende BRP bericht.
     * @param actie                De actie waarop de bedrijfsregel van toepassing is en waarbinnen deze
     *                             wordt uitgevoerd.
     * @param bestaandeBetrokkenen map van in het bericht opgenomen betrokkenen/personen die reeds bestaan in het
     *                             systeem en gemapt worden op basis van de identificerende sleutel zoals deze voorkomt
     *                             in het bericht.
     * @return Lijst van objecten die de regel overtreden.
     */
    List<BerichtEntiteit> voerRegelUit(M huidigeSituatie, B nieuweSituatie, Actie actie,
            Map<String, PersoonView> bestaandeBetrokkenen);

}
