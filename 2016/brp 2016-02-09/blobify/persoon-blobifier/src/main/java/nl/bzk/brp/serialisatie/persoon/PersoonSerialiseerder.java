/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.persoon;

import nl.bzk.brp.vergrendeling.VergrendelFout;

/**
 * Interface voor service die op basis van een gegeven persoonsleutel de persoon omzet naar een blob en opslaat in de
 * persoon cache.
 */
public interface PersoonSerialiseerder {


    /**
     * Serialiseer de persoon die bij deze sleutel hoort.
     *
     * @param persoonSleutel Sleutel van de persoon die geserialiseert moet worden.
     * @throws VergrendelFout de fout die optreedt bij Vergrendeling
     */
    void serialiseerPersoon(Integer persoonSleutel) throws VergrendelFout;

}

