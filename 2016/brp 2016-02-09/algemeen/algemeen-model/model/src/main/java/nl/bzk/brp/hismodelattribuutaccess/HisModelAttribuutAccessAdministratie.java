/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.hismodelattribuutaccess;

import java.util.Map;
import java.util.Set;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;


/**
 * Administratie van welke attributen van C/D laag entiteiten opgevraagd zijn.
 * <p/>
 * Een administratie kan wel of niet actief zijn. Dit, ivm dat het raken van attributen alleen vanuit regels bijgehouden
 * moet worden. Tevens kan de huidige Regel aangegeven worden en kunnen de resultaten opgevraagd worden.
 */
public interface HisModelAttribuutAccessAdministratie {

    /**
     * Geeft aan of deze administratie op dit moment actief is, dat wil zeggen,
     * ontvankelijk voor registraties.
     *
     * @return of de administratie actief is of niet
     */
    boolean isActief();

    /**
     * Activeer deze administratie.
     */
    void activeer();

    /**
     * Deactiveer deze administratie.
     */
    void deactiveer();

    /**
     * Zet de huidige regel op de meegegeven regel.
     * <p/>
     * NB: Deze methode mag alleen aangeroepen worden als de administratie actief is.
     *
     * @param regel de nieuwe huidige regel
     */
    void setHuidigeRegel(final Regel regel);

    /**
     * Methode om aan te geven dat een bepaald attribuut geraakt is.
     * Deze informatie wordt gekoppeld aan de huidige regel.
     * <p/>
     * NB: Deze methode mag alleen aangeroepen worden als de administratie actief is.
     *
     * @param groepDbObjectId        het db object id van de bijbehorende groep
     * @param voorkomenId            het voorkomen id van de groep in de C/D laag (kan null zijn)
     * @param attribuutDbObjectId    het db object id van het attribuut
     * @param datumAanvangGeldigheid de datum aanvang geldigheid van het voorkomen (kan null zijn)
     */
    void attribuutGeraakt(final int groepDbObjectId, final Long voorkomenId, final int attribuutDbObjectId,
            final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid);

    /**
     * Geef de bijgehouden regel attribuut access terug.
     *
     * @return de regel attribuut access map
     */
    Map<Regel, Set<AttribuutAccess>> getRegelAttribuutAccess();

}
