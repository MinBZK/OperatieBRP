/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels.impl.levering;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;


/**
 * Abstracte klasse voor regels rondom leveringen. Deze klasse bevat helper-methodes voor de regels.
 */
public abstract class AbstractLeveringRegel {

    private static final Map<SoortAdministratieveHandeling, SoortDienst> SOORT_ADMHND_SOORTDIENST_MAP =
        new HashMap<SoortAdministratieveHandeling, SoortDienst>() {
            {
                put(SoortAdministratieveHandeling.PLAATSING_AFNEMERINDICATIE,
                    SoortDienst.PLAATSEN_AFNEMERINDICATIE);
                put(SoortAdministratieveHandeling.VERWIJDERING_AFNEMERINDICATIE,
                    SoortDienst.VERWIJDEREN_AFNEMERINDICATIE);
                put(SoortAdministratieveHandeling.SYNCHRONISATIE_PERSOON,
                    SoortDienst.SYNCHRONISATIE_PERSOON);
                put(SoortAdministratieveHandeling.SYNCHRONISATIE_STAMGEGEVEN,
                    SoortDienst.SYNCHRONISATIE_STAMGEGEVEN);
                put(SoortAdministratieveHandeling.GEEF_DETAILS_PERSOON,
                    SoortDienst.GEEF_DETAILS_PERSOON);
                put(SoortAdministratieveHandeling.ZOEK_PERSOON,
                        SoortDienst.ZOEK_PERSOON);
                put(SoortAdministratieveHandeling.GEEF_MEDEBEWONERS,
                        SoortDienst.GEEF_MEDEBEWONERS_VAN_PERSOON);
            }
        };

    /**
     * Bepaalt de aangeroepen categorie dienst op basis van de soort administratieve handeling.
     *
     * @param soortAdministratieveHandeling De soort administratieve handeling.
     * @return De categorie dienst.
     */
    protected final SoortDienst bepaalSoortDienst(
        final SoortAdministratieveHandeling soortAdministratieveHandeling)
    {
        return SOORT_ADMHND_SOORTDIENST_MAP.get(soortAdministratieveHandeling);
    }
}
