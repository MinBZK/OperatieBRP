/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.zoekpersoongeneriek;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.berichtmodel.VerwerkPersoonBericht;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.algemeen.autorisatie.LeveringsautorisatieValidatieService;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import nl.bzk.brp.service.bevraging.algemeen.AbstractMaakBerichtServiceImpl;
import nl.bzk.brp.service.bevraging.algemeen.BevragingResultaat;


/**
 * Implementatie {@link nl.bzk.brp.service.bevraging.algemeen.Bevraging.MaakBerichtService} voor Zoek Persoon.
 * @param <T> verzoek
 */
public abstract class AbstractZoekPersoonMaakBerichtServiceImpl<T extends ZoekPersoonGeneriekVerzoek>
        extends AbstractMaakBerichtServiceImpl<T, BevragingResultaat> {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private ZoekPersoonBerichtFactory zoekPersoonBerichtFactory;
    private ValideerZoekCriteriaService<ZoekPersoonGeneriekVerzoek> valideerZoekCriteriaService;

    /**
     * @param leveringsautorisatieService de leveringsautorisatie validatieservice
     * @param partijService de partij service
     */
    @Inject
    public AbstractZoekPersoonMaakBerichtServiceImpl(final LeveringsautorisatieValidatieService leveringsautorisatieService,
                                                     final PartijService partijService) {
        super(leveringsautorisatieService, partijService);
    }

    @Override
    public final BevragingResultaat maakResultaatObject() {
        return new BevragingResultaat();
    }

    @Override
    public final Logger getLogger() {
        return LOGGER;
    }

    @Override
    public final void voerDienstSpecifiekeStappenUit(final T verzoek, final BevragingResultaat resultaat) throws StapException {
        // controleer regels
        final Set<Melding> meldingen = valideerZoekCriteriaService.valideerZoekCriteria(verzoek, resultaat.getAutorisatiebundel());
        if (!meldingen.isEmpty()) {
            throw new StapMeldingException(new ArrayList<>(meldingen));
        }
        final Set<Melding> dienstSpecifiekeMeldingen = valideerDienstSpecifiek(verzoek, resultaat.getAutorisatiebundel());
        if (!dienstSpecifiekeMeldingen.isEmpty()) {
            throw new StapMeldingException(new ArrayList<>(dienstSpecifiekeMeldingen));
        }

        // ophalen persoon
        final List<Persoonslijst> personen = getOphalenPersoonService().voerStapUit(verzoek, resultaat.getAutorisatiebundel());

        // maak bericht
        final VerwerkPersoonBericht leverBericht = zoekPersoonBerichtFactory.maakZoekPersoonBericht(personen, resultaat.getAutorisatiebundel(), verzoek, null);

        resultaat.setBericht(leverBericht);
    }

    /**
     * Haal de specifieke {@link ZoekPersoon.OphalenPersoonService} implementatie op. Dit is nodig omdat er drie implementaties van deze service zijn, waarvan
     * twee met hetzelfde generieke type. Hierdoor kan het niet meer op dit niveau geïnjecteerd worden.
     * @return de implementatie
     */
    protected abstract ZoekPersoon.OphalenPersoonService<T> getOphalenPersoonService();

    /**
     * @param verzoek verzoek
     * @param autorisatiebundel autorisatiebundel
     * @return meldingen
     */
    protected abstract Set<Melding> valideerDienstSpecifiek(final T verzoek, final Autorisatiebundel autorisatiebundel);

    @Inject
    public void setValideerZoekCriteriaService(final ValideerZoekCriteriaService<ZoekPersoonGeneriekVerzoek> valideerZoekCriteriaService) {
        this.valideerZoekCriteriaService = valideerZoekCriteriaService;
    }

    @Inject
    public void setZoekPersoonBerichtFactory(final ZoekPersoonBerichtFactory zoekPersoonBerichtFactory) {
        this.zoekPersoonBerichtFactory = zoekPersoonBerichtFactory;
    }
}
