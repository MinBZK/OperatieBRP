/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.api;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.brp.beheer.service.autorisatie.DienstDTO;
import nl.bzk.brp.beheer.service.autorisatie.DienstService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST service implementatie voor diensten.
 */
@RestController
@RequestMapping("/dienst")
public class DienstController {

    private final DienstService dienstService;

    @Inject
    DienstController(DienstService dienstService) {
        this.dienstService = dienstService;
    }

    /**
     * Geef de dienst met een bepaalde id.
     * @param id het id
     * @return de {@link Dienst}
     */
    @RequestMapping("/{id}")
    public DienstDTO getDienst(@PathVariable Integer id) {
        return dienstService.getDienst(id);
    }
}
