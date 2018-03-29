/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.api;

import java.util.Collection;
import javax.inject.Inject;
import nl.bzk.brp.beheer.service.stamdata.StatischeStamdataDTO;
import nl.bzk.brp.beheer.service.stamdata.StatischeStamdataService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST service implementatie voor statische stamdata
 */
@RestController
@RequestMapping("/stamdata/statisch")
public class StatischeStamdataController {

    private StatischeStamdataService statischeStamdataService;

    /**
     * Constructor.
     * @param statischeStamdataService de {@link StatischeStamdataService}
     */
    @Inject
    public StatischeStamdataController(StatischeStamdataService statischeStamdataService) {
        this.statischeStamdataService = statischeStamdataService;
    }

    /**
     * Geef statische stamdata voor een bepaalde tabel.
     * @param tabel de tabel
     * @return de statische stamdata
     */
    @RequestMapping(method = RequestMethod.GET)
    public Collection<StatischeStamdataDTO> getStamdata(@RequestParam("tabel") String tabel) {
        return statischeStamdataService.getStatischeStamdata(tabel);
    }
}
