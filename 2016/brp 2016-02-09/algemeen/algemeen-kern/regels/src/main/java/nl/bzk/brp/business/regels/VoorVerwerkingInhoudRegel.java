/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels;

import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;


/**
 * Interface voor (bedrijfs)regels die uitgevoerd/gecontroleerd worden voorafgaand aan de verwerking en die te maken
 * hebben met de inhoud van een bericht.
 *
 * @param <M> Type van het (database) root object dat binnen de actie gecontroleerd wordt.
 * @param <B> Type van het bericht specifieke root object dat binnen de actie gecontroleerd wordt.
 */
public interface VoorVerwerkingInhoudRegel<M extends RootObject, B extends BerichtBericht>
        extends VoorVerwerkingRegel<M, B>
{

}
