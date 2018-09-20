/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.algemeen.service.impl;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.levering.algemeen.service.AdministratieveHandelingenOverslaanService;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 * De implementatie van de AdministratieveHandelingenOverslaanService. Deze maakt gebruik van een property om de lijst
 * van administratieve handelingen te bepalen.
 */
@Service
public class AdministratieveHandelingenOverslaanServiceImpl implements AdministratieveHandelingenOverslaanService {

    @Value("${soort.administratieve.handelingen.overslaan:}")
    private String soortAdministratieveHandelingenOverslaan;

    @Override
    public final List<SoortAdministratieveHandeling> geefLijstVanAdministratieveHandelingDieOvergeslagenMoetenWorden() {
        final List<SoortAdministratieveHandeling> resultaat = new ArrayList<>();
        if (!StringUtils.isEmpty(soortAdministratieveHandelingenOverslaan)) {
            for (final String soortAdministratieveHandeling : soortAdministratieveHandelingenOverslaan.split(",")) {
                resultaat.add(SoortAdministratieveHandeling.valueOf(soortAdministratieveHandeling.trim()));
            }
        }
        return resultaat;
    }
}
