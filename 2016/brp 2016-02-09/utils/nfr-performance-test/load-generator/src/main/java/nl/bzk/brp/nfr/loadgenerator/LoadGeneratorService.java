/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.nfr.loadgenerator;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface LoadGeneratorService {

    @Transactional
    void maakBlob(int persoonId);

    List<Handeling> geefTeVerwerkenHandelingen();

    //http://stackoverflow.com/questions/7872720/convert-date-from-long-time-postgres
    void markeerStart(long admhnd);
}
