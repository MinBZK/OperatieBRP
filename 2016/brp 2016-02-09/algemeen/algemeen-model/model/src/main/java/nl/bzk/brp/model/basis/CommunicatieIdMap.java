/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Dit is een map voor het registreren/administreren en bijhouden van bericht model instanties (objecttypes en groepen) gemapt naar hun communicatie id.
 * Hierbij kan een key (de communicatie id) meerdere values bevatten. De key is niet hoofdletter gevoelig.
 */
@SuppressWarnings("serial")
public final class CommunicatieIdMap extends HashMap<String, List<BerichtIdentificeerbaar>> {

    @Override
    public List<BerichtIdentificeerbaar> get(final Object key) {
        if (key instanceof String) {
            return super.get(maakKey((String) key));
        }
        return super.get(key);
    }

    /**
     * Voeg het identificeerbaar object toe aan de map met als key de communicatieId van het identificeerbaar object. Aan een key kunnen meerdere objecten
     * gekoppeld zijn.
     *
     * @param identificeerbaarObject de identificieerbaar object
     */
    public void put(final BerichtIdentificeerbaar identificeerbaarObject) {
        final String communicatieID = haalOpLowerCaseCommunicatieID(identificeerbaarObject);
        if (!containsKey(communicatieID)) {
            voegNieuweSleutelEnObjectToe(communicatieID, identificeerbaarObject);
        } else {
            voegObjectToeAanBestaandeSleutel(communicatieID, identificeerbaarObject);
        }
    }

    /**
     * Koppel de opgegeven value aan de key in deze map. Als de lijst al gekoppeld is aan de key dan wordt de lijst vervangen.
     *
     * @param key  case insensitive sleutel
     * @param list lijst van BerichtIdentificeerbaar objecten
     * @return geeft een object terug zoals het wordt gedaan in HashMap
     */
    @Override
    public List<BerichtIdentificeerbaar> put(final String key, final List<BerichtIdentificeerbaar> list) {
        return super.put(maakKey(key), list);
    }

    /**
     * Voegt een nieuw item toe aan de communicatie map met opgegeven communicatie id en het identificeerbaar object waarnaar de communicatie id verwijst.
     *
     * @param communicatieId         de communicatie id zoals gebruikt in het bericht voor het identificeerbare object.
     * @param identificeerbaarObject het identificeerbare object waarnaar verwezen wordt.
     */
    private void voegNieuweSleutelEnObjectToe(final String communicatieId,
        final BerichtIdentificeerbaar identificeerbaarObject)
    {
        final List<BerichtIdentificeerbaar> identificeerbaarObjecten = new ArrayList<>();
        identificeerbaarObjecten.add(identificeerbaarObject);

        super.put(communicatieId, identificeerbaarObjecten);
    }

    /**
     * Voegt een object toe aan de communicatie map voor de opgegeven sleutel.
     *
     * @param communicatieId         de communicatie id zoals gebruikt in het bericht voor het identificeerbare object.
     * @param identificeerbaarObject het identificeerbare object waarnaar verwezen wordt.
     */
    private void voegObjectToeAanBestaandeSleutel(final String communicatieId,
        final BerichtIdentificeerbaar identificeerbaarObject)
    {
        super.get(communicatieId).add(identificeerbaarObject);
    }

    /**
     * Haalt de gecorrigeerde (lower case en ontdaan van spaties) communicatie id uit het identificeerbare object.
     *
     * @param identificeerbaarObject het identificeerbare object waarnaar verwezen wordt.
     * @return de communicatie id van het identificeerbare object.
     */
    private String haalOpLowerCaseCommunicatieID(final BerichtIdentificeerbaar identificeerbaarObject) {
        return maakKey(identificeerbaarObject.getCommunicatieID());
    }

    /**
     * Maakt een nieuwe sleutel aan door de waarde netjes lowercase te maken en te trimmen.
     *
     * @param waarde de waarde van de sleutel
     * @return een lowercase en van spaties ontdane sleutel.
     */
    private String maakKey(final String waarde) {
        if (waarde == null) {
            return null;
        } else {
            return waarde.toLowerCase().trim();
        }
    }

}
