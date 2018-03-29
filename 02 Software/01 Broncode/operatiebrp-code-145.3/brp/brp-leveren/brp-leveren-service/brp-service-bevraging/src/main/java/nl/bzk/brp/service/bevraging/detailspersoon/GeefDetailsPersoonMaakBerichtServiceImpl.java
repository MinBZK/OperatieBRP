/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.detailspersoon;

import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.bevraging.algemeen.PeilmomentValidatieService;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.algemeen.autorisatie.LeveringsautorisatieValidatieService;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import nl.bzk.brp.service.bevraging.algemeen.AbstractMaakBerichtServiceImpl;
import nl.bzk.brp.service.bevraging.algemeen.BevragingResultaat;
import org.springframework.stereotype.Service;


/**
 * Implementatie MaakGeefDetailsPersoonBerichtService : maakt een GeefDetailsPersoon bericht.
 */
@Service
final class GeefDetailsPersoonMaakBerichtServiceImpl extends AbstractMaakBerichtServiceImpl<GeefDetailsPersoonVerzoek, BevragingResultaat> {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private GeefDetailsPersoon.OphalenPersoonService ophalenPersoonService;
    private GeefDetailsPersoonBerichtFactory geefDetailsPersoonBerichtFactory;
    private ConverteerBevragingElementenService converteerBevragingElementenService;
    private PeilmomentValidatieService peilmomentValidatieService;

    /**
     * @param leveringsautorisatieService de leveringsautorisatie validatieservice
     * @param partijService de partij service
     */
    @Inject
    GeefDetailsPersoonMaakBerichtServiceImpl(final LeveringsautorisatieValidatieService leveringsautorisatieService, final PartijService partijService) {
        super(leveringsautorisatieService, partijService);
    }

    @Override
    protected BevragingResultaat maakResultaatObject() {
        return new BevragingResultaat();
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    @Override
    protected String getDienstSpecifiekeLoggingString() {
        return "geef details persoon";
    }

    @Override
    protected void voerDienstSpecifiekeStappenUit(final GeefDetailsPersoonVerzoek bevragingVerzoek, final BevragingResultaat resultaat)
            throws StapException {
        try {
            //valideer historiefilter parameters
            valideerHistorieFilterParameters(bevragingVerzoek);
            //converteer en controleer gevraagde scoping als aanwezig
            final Set<AttribuutElement> scopingElementen = converteerScopingElementen(bevragingVerzoek.getScopingElementen(), resultaat.getAutorisatiebundel());
            //ophalen persoon
            final Persoonslijst persoon = ophalenPersoonService.voerStapUit(bevragingVerzoek, resultaat.getAutorisatiebundel());
            //maak bericht
            final VerwerkPersoonBericht leverBericht = geefDetailsPersoonBerichtFactory.maakGeefDetailsPersoonBericht(persoon,
                    resultaat.getAutorisatiebundel(), bevragingVerzoek, scopingElementen);
            resultaat.setBericht(leverBericht);
        } catch (ConverteerBevragingVerzoekElementException e) {
            throw new StapMeldingException(e.getMelding(), e);
        }
    }

    private Set<AttribuutElement> converteerScopingElementen(final GeefDetailsPersoonVerzoek.ScopingElementen scopingElementen,
                                                             final Autorisatiebundel autorisatiebundel)
            throws ConverteerBevragingVerzoekElementException {
        return converteerBevragingElementenService.converteerBevragingElementen(scopingElementen.getElementen(), autorisatiebundel);
    }

    private void valideerHistorieFilterParameters(final GeefDetailsPersoonVerzoek bevragingVerzoek) throws StapMeldingException {
        if (bevragingVerzoek.getParameters() != null && bevragingVerzoek.getParameters().getHistorieFilterParameters() != null) {
            GeefDetailsPersoonVerzoek.HistorieFilterParameters params = bevragingVerzoek.getParameters().getHistorieFilterParameters();
            peilmomentValidatieService.valideerFormeelEnMaterieel(params.getPeilMomentMaterieelResultaat(), params.getPeilMomentFormeelResultaat());
        }
    }

    @Inject
    void setOphalenPersoonService(final GeefDetailsPersoon.OphalenPersoonService ophalenPersoonService) {
        this.ophalenPersoonService = ophalenPersoonService;
    }

    @Inject
    void setGeefDetailsPersoonBerichtFactory(final GeefDetailsPersoonBerichtFactory geefDetailsPersoonBerichtFactory) {
        this.geefDetailsPersoonBerichtFactory = geefDetailsPersoonBerichtFactory;
    }

    @Inject
    void setConverteerBevragingElementenService(
            final ConverteerBevragingElementenService converteerBevragingElementenService) {
        this.converteerBevragingElementenService = converteerBevragingElementenService;
    }

    @Inject
    void setPeilmomentValidatieService(final PeilmomentValidatieService peilmomentValidatieService) {
        this.peilmomentValidatieService = peilmomentValidatieService;
    }


}
