/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.stuf;

import java.util.Map;
import nl.bzk.brp.tooling.apitest.dto.XmlVerzoek;

/**
 * VerstuurBerichtApiService.
 */
public interface VerstuurStufBerichtApiService {
    void verzoekVerstuurStufBericht(Map<String, String> map);

    void verstuurStufBericht(XmlVerzoek xmlVerzoek);
}
