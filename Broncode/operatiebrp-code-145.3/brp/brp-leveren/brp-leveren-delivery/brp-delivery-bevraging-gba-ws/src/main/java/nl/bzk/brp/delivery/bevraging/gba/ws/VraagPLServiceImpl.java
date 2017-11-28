/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba.ws;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import javax.inject.Inject;
import javax.jws.WebService;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.brp.delivery.bevraging.gba.ws.model.Antwoorden;
import nl.bzk.brp.delivery.bevraging.gba.ws.model.VraagPLWebserviceVraagBericht;
import nl.bzk.brp.delivery.bevraging.gba.ws.model.WebserviceVraagMapper;
import nl.bzk.brp.delivery.bevraging.gba.ws.validators.DubbeleZoekRubriekenValidator;
import nl.bzk.brp.delivery.bevraging.gba.ws.validators.NietToegestaneOpvragenPLRubriekenValidator;
import nl.bzk.brp.delivery.bevraging.gba.ws.validators.NumeriekeZoekwaardenValidator;
import nl.bzk.brp.delivery.bevraging.gba.ws.validators.OpvragenPLidentificatieValidator;
import nl.bzk.brp.delivery.bevraging.gba.ws.validators.VraagValidator;
import nl.bzk.brp.delivery.bevraging.gba.ws.validators.ZoekwaardenLengteValidator;
import nl.bzk.brp.delivery.bevraging.gba.ws.vertaler.AntwoordBerichtResultaat;
import nl.bzk.brp.delivery.bevraging.gba.ws.vraag.Antwoord;
import nl.bzk.brp.delivery.bevraging.gba.ws.vraag.VraagPL;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.gba.domain.bevraging.Persoonsvraag;
import nl.bzk.brp.service.algemeen.PartijCodeResolver;
import nl.bzk.brp.service.cache.PartijCache;
import nl.bzk.migratiebrp.conversie.vragen.ConversieLo3NaarBrpVragen;
import org.springframework.transaction.annotation.Transactional;

/**
 * Webservice voor Vragen PL.
 */
@WebService
@Transactional(transactionManager = "masterTransactionManager")
public class VraagPLServiceImpl implements VraagPLService {

    private ConversieLo3NaarBrpVragen conversieLo3NaarBrpVragen;
    private OpvragenPLVerwerker opvragenPLVerwerker;
    private PartijCodeResolver partijCodeResolver;
    private PartijCache partijCache;

    /**
     * Centrale protocolleer methode voor de webservice.
     * @param vraag De binnengekomen vraag
     * @return Het antwoord op de vraag
     */
    @Override
    public Antwoord vraagPL(final VraagPL vraag) {
        BrpNu.set();

        String partijCode = partijCodeResolver.get().orElse(null);
        if (partijCode == null || partijCache.geefPartij(partijCode) == null || !isBijhouder(partijCache.geefPartij(partijCode).getPartijRolSet())) {
            return Antwoorden.foutief(AntwoordBerichtResultaat.TECHNISCHE_FOUT_027);
        }

        VraagPLWebserviceVraagBericht vraagBericht = new VraagPLWebserviceVraagBericht(vraag);

        return new VraagValidator<>(vraagBericht)
                .setValidators(
                        Arrays.asList(
                                new DubbeleZoekRubriekenValidator<>(),
                                new NumeriekeZoekwaardenValidator<>(),
                                new ZoekwaardenLengteValidator<>(),
                                new OpvragenPLidentificatieValidator(),
                                new NietToegestaneOpvragenPLRubriekenValidator()))
                .ifValid(this::verwerkVraagPL);
    }

    private Antwoord verwerkVraagPL(final VraagPLWebserviceVraagBericht vraag) {
        String partijCode = partijCodeResolver.get().orElse(null);
        final WebserviceVraagMapper<Persoonsvraag> persoonsvraagMapper = new WebserviceVraagMapper<>(Persoonsvraag.class, conversieLo3NaarBrpVragen);
        Persoonsvraag persoonsvraag = persoonsvraagMapper.mapVraag(vraag, partijCode);
        return opvragenPLVerwerker.verwerk(
                persoonsvraag,
                geefBijhouderRol(partijCache.geefPartij(partijCode).getPartijRolSet()).orElseThrow(IllegalStateException::new), "webservice opvragenPL");
    }

    private boolean isBijhouder(final Set<PartijRol> partijRolSet) {
        return geefBijhouderRol(partijRolSet).isPresent();
    }

    private Optional<Rol> geefBijhouderRol(final Set<PartijRol> partijRolSet) {
        return partijRolSet.stream()
                .filter(rol -> rol.getRol() == Rol.BIJHOUDINGSORGAAN_COLLEGE
                        || rol.getRol() == Rol.BIJHOUDINGSORGAAN_MINISTER).findFirst().map(PartijRol::getRol);
    }

    /**
     * Zet de conversieLo3NaarBrpVragen.
     * @param conversieLo3NaarBrpVragen de te zetten conversieLo3NaarBrpVragen
     */
    @Inject
    public void setConversieLo3NaarBrpVragen(final ConversieLo3NaarBrpVragen conversieLo3NaarBrpVragen) {
        this.conversieLo3NaarBrpVragen = conversieLo3NaarBrpVragen;
    }

    /**
     * Zet de verwerker.
     * @param opvragenPLVerwerker de te zetten verwerker
     */
    @Inject
    public void setOpvragenPLVerwerker(final OpvragenPLVerwerker opvragenPLVerwerker) {
        this.opvragenPLVerwerker = opvragenPLVerwerker;
    }

    /**
     * Resolver voor partij code gebruikt binnen de webservice.
     * @param partijCodeResolver Resolver voor partij code gebruikt binnen de webservice
     */
    @Inject
    public void setPartijCodeResolver(final PartijCodeResolver partijCodeResolver) {
        this.partijCodeResolver = partijCodeResolver;
    }

    /**
     * Zet de partijCache.
     * @param partijCache de te zetten partij cache
     */
    @Inject
    public void setPartijCache(final PartijCache partijCache) {
        this.partijCache = partijCache;
    }
}
