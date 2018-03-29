/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.autaut;

import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractReadonlyController;
import nl.bzk.brp.beheer.webapp.repository.ReadonlyRepository;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.autaut.SoortDienstRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Soort Dienst controller.
 */
@RestController
@RequestMapping(value = ControllerConstants.SOORT_DIENST_URI)
public final class SoortDienstController extends AbstractReadonlyController<SoortDienst, Integer> {

    /**
     * Filter parameter op stelsel.
     */
    private static final String PARAMETER_STELSEL = "stelsel";

    private final SoortDienstRepository soortDienstRepository;

    /**
     * Constructor.
     *
     * @param repository
     *            soort dienst repository
     */
    @Inject
    protected SoortDienstController(@Named("soortDienstRepository") final ReadonlyRepository<SoortDienst, Integer> repository) {
        super(repository);
        this.soortDienstRepository = new SoortDienstRepository();
    }

    /**
     * Haal een lijst van items op.
     *
     * @param parameters
     *            request parameters
     * @param pageable
     *            paginering
     * @return lijst van item (inclusief paginering en sortering)
     */
    @RequestMapping(value = ControllerConstants.SOORT_DIENSTEN_URI, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Page<SoortDienst> listSoortDienst(@RequestParam final Map<String, String> parameters, @PageableDefault(size = 10) final Pageable pageable) {
        final List<SoortDienst> soortDiensten =
                soortDienstRepository.filterEnumOpStelsel(Integer.valueOf(Stelsel.GBA.getId()).equals(Integer.parseInt(parameters.get(PARAMETER_STELSEL))));
        return new PageImpl<>(soortDiensten, pageable, soortDiensten.size());
    }
}
