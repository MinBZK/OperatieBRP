/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.ber;

import javax.validation.Valid;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBerichtAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.basis.CommunicatieIdMap;
import nl.bzk.brp.model.basis.CommunicatieIdMapBevattend;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.logisch.ber.Bericht;


/**
 * (Toekomstig) Bericht zoals verzonden door of ontvangen door de centrale voorzieningen van de BRP.
 * <p/>
 * Berichten worden door de BRP gearchiveerd. Dit betreft enerzijds ontvangen Berichten, anderzijds Berichten die verzonden gaan worden.
 * <p/>
 * 1. Soort bericht (weer) verwijderd uit model als eigenschap van Bericht: reden is dat het op het moment van archiveren nog niet bekend zal zijn. RvdP 8
 * november 2011.
 */
public abstract class BerichtBericht extends AbstractBerichtBericht implements Bericht, CommunicatieIdMapBevattend {

    /**
     * Mapping tussen communicatie id's in het xml bericht en alle objecten die geunmarshalled zijn door JiBX.
     */
    private CommunicatieIdMap communicatieIdMap;

    /**
     * Maakt een bericht aan van de meegegeven soort.
     *
     * @param soort de soort.
     */
    protected BerichtBericht(final SoortBerichtAttribuut soort) {
        setStandaard(new BerichtStandaardGroepBericht());
        setSoort(soort);
    }

    /**
     * Backwards compatibility functie, omdat administratieve handeling bij BMR32 is verplaats vam de Identiteit naar de standaard groep.
     *
     * @return de administratieve handeling.
     */
    public AdministratieveHandelingBericht getAdministratieveHandeling() {
        AdministratieveHandelingBericht administratieveHandeling = null;
        if (getStandaard() != null) {
            administratieveHandeling = getStandaard().getAdministratieveHandeling();
        }
        return administratieveHandeling;
    }

    @Override
    @Valid
    public BerichtStandaardGroepBericht getStandaard() {
        return super.getStandaard();
    }

    @Override
    @Valid
    public BerichtZoekcriteriaPersoonGroepBericht getZoekcriteriaPersoon() {
        return super.getZoekcriteriaPersoon();
    }

    @Override
    public CommunicatieIdMap getCommunicatieIdMap() {
        return communicatieIdMap;
    }

    @Override
    public void setCommunicatieIdMap(final CommunicatieIdMap communicatieIdMap) {
        this.communicatieIdMap = communicatieIdMap;
    }

    /**
     * Controleert of de administratieve handeling van een bepaalde type is. Wordt uiteindelijk gebruikt door jibx om een choice te bepalen.
     *
     * @param type het gewenste type
     * @return true als dit correct is, false als ander type of adm.hand. is null
     */
    protected boolean isAdministratieveHandelingVanType(final SoortAdministratieveHandeling type) {
        return getAdministratieveHandeling() != null && getAdministratieveHandeling().getSoort().getWaarde() == type;
    }

}
