/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.berichtcmds;

import java.util.List;

import nl.bzk.brp.bevraging.business.dto.BerichtVerwerkingsFout;
import nl.bzk.brp.bevraging.business.dto.BrpBerichtContext;
import nl.bzk.brp.bevraging.business.dto.antwoord.BRPAntwoord;
import nl.bzk.brp.bevraging.business.dto.verzoek.BRPVerzoek;
import nl.bzk.brp.bevraging.domein.ber.SoortBericht;


/**
 * Generieke interface voor alle bericht commands. Elk bericht dient uitgevoerd te worden en deze interface biedt
 * dan ook de {@link #voerUit()} methode als standaard methode voor het uitvoeren van een bericht command. Verder
 * bevat dit bericht nog overige informatie aangaande het bericht zoals de context en het verzoek en antwoord
 * object.
 *
 * @param <T> Type van het verzoek object.
 * @param <U> Type van het antwoord object.
 */
public interface BrpBerichtCommand<T extends BRPVerzoek, U extends BRPAntwoord> {

    /**
     * Standaard methode voor het uitvoeren van een bericht command.
     */
    void voerUit();

    /**
     * Retourneert het initiele verzoek object; het object met de parameters voor het verzoek.
     * @return het verzoek object.
     */
    T getVerzoek();

    /**
     * Retourneert het soort bericht.
     * @return het soort bericht.
     */
    SoortBericht getSoortBericht();

    /**
     * Retourneert het antwoord object; het object met daarin de te retourner data.
     * @return het antwoord object.
     */
    U getAntwoord();

    /**
     * Retourneert de context waar binnen het bericht wordt afgehandeld.
     * @return de context van het bericht.
     */
    BrpBerichtContext getContext();

    /**
     * Retourneert een lijst van opgetreden fouten.
     * @return een lijst van opgetreden fouten.
     */
    List<BerichtVerwerkingsFout> getFouten();

    /**
     * Voegt fout toe aan de lijst van fouten.
     * @param fout de opgetreden fout.
     */
    void voegFoutToe(BerichtVerwerkingsFout fout);

}
