/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels;

import java.util.List;

import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtRootObject;


/**
 * Interface voor (bedrijfs)regels die uitgevoerd/gecontroleerd worden op actie niveau en wel na de verwerking van de
 * actie.
 *
 * @param <M> Type van het (database) root object dat binnen de actie gecontroleerd wordt.
 * @param <B> Type van het bericht specifieke root object behorende bij de actie van deze regel.
 */
public interface NaActieRegel<M extends RootObject, B extends BerichtRootObject> extends RegelInterface {

    /**
     * Functie die de bedrijfsregel implementatie bevat voor uitvoer.
     *
     * @param modelObject     de situatie van het root object van de actie na verwerking van de actie.
     * @param berichtEntiteit de (root) bericht entiteit van de actie waarvoor de regel geldt.
     * @return Lijst van objecten die de regel overtreden.
     */
    List<BerichtEntiteit> voerRegelUit(M modelObject, B berichtEntiteit);

}
