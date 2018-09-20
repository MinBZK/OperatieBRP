/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.service;

import nl.bzk.brp.bevraging.business.dto.BerichtContext;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtAntwoord;
import nl.bzk.brp.bevraging.business.dto.verzoek.BerichtVerzoek;


/**
 * Interface voor het uitvoeren (executeren) van een bericht, op basis van het {@link BerichtVerzoek} en de
 * {@link BerichtContext}. Deze service vormt het hart van de BRP bevragingsservice en wordt vanuit de webservice
 * laag aangeroepen met de vermelde parameters. Dit bericht wordt door de service uitgevoerd, waarbij de service ook
 * zorg draagt voor de generieke afhandeling van het bericht en dus zaken als validatie, autorisatie, logging en
 * protocollering rondom het bericht regelt.
 */
public interface BerichtUitvoerderService {

    /**
     * Voert het opgegeven bericht uit, waarbij ook de overige standaard stappen, zoals protocollering, validatie,
     * autorisatie etc. worden verzorgd.
     *
     * @param <T> Type van het antwoord bericht.
     * @param verzoek het bericht verzoek dat dient te worden uitgevoerd.
     * @param context de context waarbinnen het bericht dient te worden uitgevoerd.
     * @return een antwoord bericht na verwerking met daarin eventuele opgetreden fouten.
     */
    <T extends BerichtAntwoord> T voerBerichtUit(BerichtVerzoek<T> verzoek, BerichtContext context);

}
