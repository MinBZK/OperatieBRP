/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.kern;

import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractReadonlyController;
import nl.bzk.brp.beheer.webapp.repository.ReadonlyRepository;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.kern.ElementRepository;
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
 * Element controller.
 */
@RestController
@RequestMapping(value = ControllerConstants.ELEMENT_URI)
public final class ElementController extends AbstractReadonlyController<Element, Integer> {

    /**
     * Filter parameter op soort element.
     */
    public static final String PARAMETER_SOORT = "filterSoort";
    /**
     * Filter parameter op soort element autorisatie.
     */
    public static final String PARAMETER_SOORT_AUTORISATIE = "filterSoortAutorisatie";

    private final ElementRepository repository;

    /**
     * Constructor.
     * @param repository repository
     */
    @Inject
    protected ElementController(@Named("elementRepository") final ReadonlyRepository<Element, Integer> repository) {
        super(repository);
        this.repository = new ElementRepository();
    }

    /**
     * Haal een lijst van items op.
     * @param parameters request parameters
     * @param pageable paginering
     * @return lijst van item (inclusief paginering en sortering)
     */
    @RequestMapping(value = "/groep", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Page<Element> listGroepen(@RequestParam final Map<String, String> parameters, @PageableDefault(size = 10) final Pageable pageable) {
        final String elementSoort = parameters.get(PARAMETER_SOORT);
        if (elementSoort == null) {
            return list(parameters, pageable);
        }
        final List<Element> elementen = repository.filterEnumOpSoort(Integer.valueOf(elementSoort));
        return new PageImpl<>(elementen, pageable, elementen.size());
    }

}
