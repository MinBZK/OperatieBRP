/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

/**
 * Bericht model specifieke interface waarmee vanuit het Bericht geinstantieerde objecten kunnen worden geidentificeerd in het bericht. Middels deze
 * identificatie in het bericht (communicatie id) kan dus ook gerefereerd worden naar die objecten vanuit andere objecten (zie ook {@link
 * nl.bzk.brp.model.basis.BerichtEntiteit#getReferentieID()}), of die gebruikt kan worden om aan te geven welke elementen in een bericht nu een
 * fout/melding veroorzaken.
 */
public interface BerichtIdentificeerbaar {

    /**
     * Setter voor de communicatie id van een bericht element; attribuut waarmee naar een groep of object in het bericht kan worden gerefereerd.
     *
     * @param communicatieId de te zetten id.
     */
    void setCommunicatieID(String communicatieId);

    /**
     * Getter voor de communicatie id van een bericht element; attribuut waarmee naar een groep of object in het bericht kan worden gerefereerd.
     *
     * @return de communicatie id van een object/groep.
     */
    String getCommunicatieID();

}
