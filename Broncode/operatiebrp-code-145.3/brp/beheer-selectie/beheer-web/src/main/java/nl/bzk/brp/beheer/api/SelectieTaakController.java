/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.api;

import java.util.Collection;
import javax.inject.Inject;
import javax.validation.Valid;
import nl.bzk.brp.beheer.service.selectie.SelectiePeriodeDTO;
import nl.bzk.brp.beheer.service.selectie.SelectieTaakDTO;
import nl.bzk.brp.beheer.service.selectie.SelectieTaakService;
import nl.bzk.brp.beheer.service.validation.SelectiePeriode;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST service implementatie voor selectietaken.
 */
@RestController
@Validated
@RequestMapping("/selectietaak")
public class SelectieTaakController {

    private final SelectieTaakService selectieTaakService;

    /**
     * Constructor.
     * @param selectieTaakService de {@link SelectieTaakService}
     */
    @Inject
    public SelectieTaakController(SelectieTaakService selectieTaakService) {
        this.selectieTaakService = selectieTaakService;
    }

    /**
     * Geef alle opgeslagen selectietaken binnen een periode.
     * @param selectiePeriode de selectieperiode
     * @return de opgeslagen selectietaken
     */
    @RequestMapping(method = RequestMethod.GET)
    public Collection<SelectieTaakDTO> getSelectieTakenBinnenPeriode(@SelectiePeriode SelectiePeriodeDTO selectiePeriode) {
        return selectieTaakService.getSelectieTaken(selectiePeriode);
    }

    /**
     * Sla een selectietaak op.
     * @param selectieTaak de selectietaak
     * @return de opgeslagen selectietaak
     */
    @RequestMapping(method = RequestMethod.POST)
    public SelectieTaakDTO slaSelectieTaakOp(@Valid @RequestBody SelectieTaakDTO selectieTaak) {
        return selectieTaakService.slaSelectieTaakOp(selectieTaak);
    }
}
