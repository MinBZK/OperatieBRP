/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.berichtcmds;

import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtAntwoord;
import nl.bzk.brp.bevraging.business.dto.verzoek.BerichtVerzoek;


/**
 * Generieke interface voor alle bericht commands. Elk bericht dient uitgevoerd te worden en deze interface biedt
 * dan ook de {@link BerichtCommand#voerUit(BerichtAntwoord)} methode als standaard methode voor het uitvoeren van
 * een bericht command.
 *
 * @param <T> Type van het verzoek object dat dit command kan verwerken..
 * @param <U> Type van het antwoord object dat dit command object retourneert.
 */
public interface BerichtCommand<T extends BerichtVerzoek<U>, U extends BerichtAntwoord> {

    /**
     * Standaard methode voor het uitvoeren van een bericht command. In de uitvoering wordt, voor zover relevant
     * het opgegeven antwoord bericht aangepast op basis van de uitvoering van het command.
     *
     * @param antwoord het antwoord object dat reeds geinitialiseerd is.
     */
    void voerUit(U antwoord);

}
