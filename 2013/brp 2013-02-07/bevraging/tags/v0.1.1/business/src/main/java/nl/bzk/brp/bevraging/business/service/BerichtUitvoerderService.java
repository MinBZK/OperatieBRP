/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.service;

import nl.bzk.brp.bevraging.business.berichtcmds.BrpBerichtCommand;


/**
 * Interface voor het uitvoeren (executeren) van {@link BrpBerichtCommand} instanties. Deze service vormt het hart
 * van de BRP bevragingsservice en wordt vanuit de webservice laag aangeroepen met een uit te voeren BRP bericht
 * command instantie. Dit bericht/command wordt door de service uitgevoerd, waarbij de service ook zorg draagt voor
 * de generieke afhandeling van het bericht en dus zaken als validatie, autorisatie, logging en protocollering rondom
 * het bericht regelt.
 */
public interface BerichtUitvoerderService {

    /**
     * Voert het opgegeven bericht uit, waarbij ook de overige standaard stappen, zoals protocollering, validatie,
     * autorisatie etc. worden verzorgd.
     * @param bericht het bericht dat dient te worden uitgevoerd, inclusief context etc.
     */
    void voerBerichtUit(BrpBerichtCommand bericht);

}
