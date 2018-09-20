/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.service;

import nl.bzk.brp.business.stappen.BerichtenIds;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.logisch.ber.BerichtParametersGroep;
import nl.bzk.brp.model.logisch.ber.BerichtResultaatGroep;
import nl.bzk.brp.model.logisch.ber.BerichtStuurgegevensGroep;

public interface ArchiveringService {

    /**
     * Archiveert een inkomend bericht en maakt een leeg uitgaand bericht aan dat eraan gerelateerd is.
     *
     * @param data het bericht dat gearchiveerd dient te worden.
     * @return een id paar (nooit null) bestaande uit het inkomende bericht id en het uitgaande bericht id.
     */
    BerichtenIds archiveer(String data);

    /**
     * Werkt de data van een bericht bij in de database.
     *
     * @param uitgaandBerichtId Id van het uitgaande bericht wat bijgewerkt moet worden.
     * @param nieuweData De data die bij het uitgaande bericht hoort.
     */
    void werkDataBij(final Long uitgaandBerichtId, final String nieuweData);

    /**
     * Werk de ingaande bericht info bij in de database. Ingaand bericht is al deels opgeslagen tijdens archivering.
     *
     * @param ingaandBerichtId Id van het ingaande bericht.
     * @param berichtStuurgegevens Bericht stuurgegevens.
     * @param berichtParameters bericht parameters
     * @param soortBericht Het soort binnenkomend bericht.
     */
    void werkIngaandBerichtInfoBij(final Long ingaandBerichtId,
                                   final BerichtStuurgegevensGroep berichtStuurgegevens,
                                   final BerichtParametersGroep berichtParameters,
                                   final SoortBericht soortBericht);

    /**
     * Werk de uitgaande bericht info bij in de database. Uitgaand bericht is al deels opgeslagen tijdens archivering.
     *
     * @param uitgaandBerichtId Id van het uitgaande bericht.
     * @param stuurgegevensGroep De stuurgegevens van het uitgaande bericht.
     * @param berichtResultaatGroep Het resultaat van de verwerking.
     */
    void werkUitgaandBerichtInfoBij(final Long uitgaandBerichtId,
                                    final BerichtStuurgegevensGroep stuurgegevensGroep,
                                    final BerichtResultaatGroep berichtResultaatGroep);



}
