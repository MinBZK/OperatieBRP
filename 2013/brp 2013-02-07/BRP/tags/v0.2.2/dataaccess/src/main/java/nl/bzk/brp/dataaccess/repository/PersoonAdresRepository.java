/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.objecttype.logisch.PersoonAdres;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonAdresModel;


/**
 * Repository voor de {@link nl.bzk.brp.model.objecttype.operationeel.PersoonAdresModel} class.
 */
public interface PersoonAdresRepository {

    /**
     * Slaat het opgegeven nieuwe adres op en overschrijft daarmee een eventueel bestaand huidig adres, afhankelijk
     * van opgegeven geldigheids data. <br/>
     * <br/>
     * Deze methode houdt rekening met historie en voert, indien nodig, ook de benodigde wijzigingen door in de
     * historische data.
     *
     * @param persoonAdres het adres dat opgeslagen dient te worden.
     * @param actie De actie die leidt tot deze adres wijziging.
     * @param datumAanvangGeldigheid Datum dat het nieuwe adres ingaat.
     * @return Opgeslagen PersoonAdres.
     */
    PersoonAdresModel opslaanNieuwPersoonAdres(final PersoonAdresModel persoonAdres, final ActieModel actie,
            final Datum datumAanvangGeldigheid);

    /**
     * Voer een adres correctie door.
     *
     * @param adres Het adres.
     * @param actie De actie die leidt tot deze adres correctie. Hierin staan tevens de datum aanvang en datum einde.
     * @param datumAanvangGeldigheid Datum dat het nieuwe adres ingaat.
     * @param datumEindeGeldigheid Datum einde geldigheid van het adres.
     */
    void voerCorrectieAdresUit(final PersoonAdresModel adres, final ActieModel actie,
            final Datum datumAanvangGeldigheid, final Datum datumEindeGeldigheid);

    /**
     * Controleert in de BRP database of er iemand momenteel ingeschreven staat op een adres. Hierbij wordt een
     * exacte match verwacht qua adres. Lege velden dienen dus niet als wildcard te worden gezien.
     *
     * @param persoonAdres Het adres dat gecontroleerd wordt.
     * @return true indien er iemand ingeschreven staat anders false.
     */
    boolean isIemandIngeschrevenOpAdres(final PersoonAdres persoonAdres);

    /**
     * Zoekt het huidige adres op van een persoon.
     *
     * @param bsn bsn van de persoon waar het omgaat.
     * @return Het huidige adres van de persoon.
     */
    PersoonAdresModel vindHuidigWoonAdresVoorPersoon(final Burgerservicenummer bsn);

    /**
     * Haal de historie op van een PersoonAdres en plaats deze in het adres object.
     *
     * @param adres het te completeren adres
     * @param inclFormeleHistorie includief de formele historie
     * @return de gewijzigde adres object
     */
    PersoonAdresModel vulaanAdresMetHistorie(final PersoonAdresModel adres, final boolean inclFormeleHistorie);
}
