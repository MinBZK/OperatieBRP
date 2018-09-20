/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels;

import nl.bzk.brp.model.basis.BerichtRootObject;
import nl.bzk.brp.model.basis.HisVolledigModelRootObject;


/**
 * Interface voor (bedrijfs)regels die uitgevoerd/gecontroleerd worden op actie niveau en wel na de verwerking van de
 * actie. Deze regel werkt op een volledige persoonslijst van de huidige persoon in de database.
 * Deze interface is bedoeld voor regels die per see historie nodig hebben om hun logica te kunnen checken.
 * Dat zal maar bij een enkele regel nodig zijn. Als een momentopname voldoende is om de regel te checken,
 * gebruik dan de VoorActieRegelMetMomentopname.
 *
 * @param <M> Type van het root object his volledig dat binnen de actie gecontroleerd wordt.
 * @param <B> Type van het bericht specifieke root object behorende bij de actie van deze regel.
 */
public interface VoorActieRegelMetHistorischBesef<M extends HisVolledigModelRootObject, B extends BerichtRootObject>
        extends VoorActieRegel<M, B>
{

    // Geen functies nodig. Deze interface is echter zelf wel nodig als een soort 'tagging' interface,
    // zodat de aanroeper weet welke parameter verwacht wordt.

}
