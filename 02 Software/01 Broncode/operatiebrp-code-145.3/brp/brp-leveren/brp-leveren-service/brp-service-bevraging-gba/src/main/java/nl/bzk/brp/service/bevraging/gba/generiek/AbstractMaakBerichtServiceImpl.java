/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.gba.generiek;


import java.util.Collections;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.service.algemeen.autorisatie.LeveringsautorisatieValidatieService;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.AbstractZoekPersoonMaakBerichtServiceImpl;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.ValideerZoekCriteriaService;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.ZoekPersoon;

/**
 * Implementatie {@link nl.bzk.brp.service.bevraging.algemeen.Bevraging.MaakBerichtService} voor GBA
 * Ad hoc (adres)vraag.
 * @param <T> specifiek verzoek type
 */
public abstract class AbstractMaakBerichtServiceImpl<T extends ZoekPersoonGeneriekGbaVerzoek> extends AbstractZoekPersoonMaakBerichtServiceImpl<T> {

    protected final ValideerZoekCriteriaService<T> valideerZoekCriteriaService;
    private final ValideerGbaAutorisatieService valideerGbaAutorisatieService;
    private final ZoekPersoon.OphalenPersoonService<T> zoekPersoonOphalenPersoonService;

    /**
     * Constructor.
     * @param leveringsautorisatieService de leveringsautorisatie validatieservice
     * @param partijService de partij service
     * @param valideerZoekCriteriaService valideer de zoekvraag tegen brp
     * @param valideerGbaAutorisatieService valideer gba autorisatie service
     * @param zoekPersoonOphalenPersoonService zoek persoon ophalen service
     */
    @Inject
    public AbstractMaakBerichtServiceImpl(final LeveringsautorisatieValidatieService leveringsautorisatieService, final PartijService partijService,
                                          final ValideerZoekCriteriaService<T> valideerZoekCriteriaService,
                                          final ValideerGbaAutorisatieService valideerGbaAutorisatieService,
                                          final ZoekPersoon.OphalenPersoonService<T> zoekPersoonOphalenPersoonService) {
        super(leveringsautorisatieService, partijService);
        this.valideerZoekCriteriaService = valideerZoekCriteriaService;
        this.valideerGbaAutorisatieService = valideerGbaAutorisatieService;
        this.zoekPersoonOphalenPersoonService = zoekPersoonOphalenPersoonService;
    }

    @Override
    protected ZoekPersoon.OphalenPersoonService<T> getOphalenPersoonService() {
        return zoekPersoonOphalenPersoonService;
    }

    @Override
    protected Set<Melding> valideerDienstSpecifiek(final T verzoek, final Autorisatiebundel autorisatiebundel) {
        final Set<Melding> resultaat = valideerZoekCriteriaService.valideerZoekCriteria(verzoek, autorisatiebundel);
        resultaat.addAll(valideerGbaAutorisatieService.valideer(verzoek, autorisatiebundel));
        return Collections.unmodifiableSet(resultaat);
    }
}
