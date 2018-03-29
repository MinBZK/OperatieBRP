/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba.ws;

import java.util.Arrays;
import javax.inject.Inject;
import javax.jws.WebService;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.delivery.bevraging.gba.ws.model.AdhocWebserviceVraagBericht;
import nl.bzk.brp.delivery.bevraging.gba.ws.model.WebserviceVraagMapper;
import nl.bzk.brp.delivery.bevraging.gba.ws.validators.AlsHistorischDanNietLeegValidator;
import nl.bzk.brp.delivery.bevraging.gba.ws.validators.DubbeleGevraagdeRubriekenValidator;
import nl.bzk.brp.delivery.bevraging.gba.ws.validators.DubbeleZoekRubriekenValidator;
import nl.bzk.brp.delivery.bevraging.gba.ws.validators.NietToegestaneRubriekenValidator;
import nl.bzk.brp.delivery.bevraging.gba.ws.validators.NumeriekeZoekwaardenValidator;
import nl.bzk.brp.delivery.bevraging.gba.ws.validators.PersoonOfAdresidentificatieValidator;
import nl.bzk.brp.delivery.bevraging.gba.ws.validators.PersoonidentificatieValidator;
import nl.bzk.brp.delivery.bevraging.gba.ws.validators.VraagValidator;
import nl.bzk.brp.delivery.bevraging.gba.ws.validators.ZoekwaardenLengteValidator;
import nl.bzk.brp.delivery.bevraging.gba.ws.vraag.Antwoord;
import nl.bzk.brp.delivery.bevraging.gba.ws.vraag.Vraag;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.gba.domain.bevraging.Adresvraag;
import nl.bzk.brp.gba.domain.bevraging.Persoonsvraag;
import nl.bzk.brp.service.algemeen.PartijCodeResolver;
import nl.bzk.migratiebrp.conversie.vragen.ConversieLo3NaarBrpVragen;
import org.springframework.transaction.annotation.Transactional;

/**
 * Webservice voor Ad Hoc bevragingen (Persoonsvraag en Adresvraag)
 */
@WebService
@Transactional(transactionManager = "masterTransactionManager")
public class AdhocServiceImpl implements AdhocService {

    private ConversieLo3NaarBrpVragen conversieLo3NaarBrpVragen;
    private AdresvraagVerwerker adresvraagVerwerker;
    private PersoonsvraagVerwerker persoonsvraagVerwerker;
    private PartijCodeResolver partijCodeResolver;

    /**
     * Centrale protocolleer methode voor de webservice.
     * @param vraag De binnengekomen vraag
     * @return Het antwoord op de vraag
     */
    @Override
    public Antwoord vraag(final Vraag vraag) {
        BrpNu.set(DatumUtil.nuAlsZonedDateTime());

        AdhocWebserviceVraagBericht vraagBericht = new AdhocWebserviceVraagBericht(vraag);

        if (vraagBericht.isAdresvraag()) {
            return new VraagValidator<>(vraagBericht)
                    .setValidators(
                            Arrays.asList(
                                    new DubbeleGevraagdeRubriekenValidator<>(),
                                    new DubbeleZoekRubriekenValidator<>(),
                                    new NietToegestaneRubriekenValidator<>(),
                                    new PersoonOfAdresidentificatieValidator(),
                                    new NumeriekeZoekwaardenValidator<>(),
                                    new ZoekwaardenLengteValidator<>(),
                                    new AlsHistorischDanNietLeegValidator())
                    )
                    .ifValid(this::verwerkAdresVraag);
        } else {
            return new VraagValidator<>(vraagBericht)
                    .setValidators(
                            Arrays.asList(
                                    new DubbeleGevraagdeRubriekenValidator<>(),
                                    new DubbeleZoekRubriekenValidator<>(),
                                    new NietToegestaneRubriekenValidator<>(),
                                    new PersoonidentificatieValidator(),
                                    new NumeriekeZoekwaardenValidator<>(),
                                    new ZoekwaardenLengteValidator<>(),
                                    new AlsHistorischDanNietLeegValidator())
                    )
                    .ifValid(this::verwerkPersoonsVraag);
        }
    }

    private Antwoord verwerkPersoonsVraag(final AdhocWebserviceVraagBericht vraag) {
        WebserviceVraagMapper<Persoonsvraag> persoonsvraagMapper = new WebserviceVraagMapper<>(Persoonsvraag.class, conversieLo3NaarBrpVragen);
        Persoonsvraag persoonsvraag = persoonsvraagMapper.mapVraag(vraag, partijCodeResolver.get().orElse(null));
        return persoonsvraagVerwerker.verwerk(persoonsvraag, "webservice persoonsvraag");
    }

    private Antwoord verwerkAdresVraag(final AdhocWebserviceVraagBericht vraag) {
        WebserviceVraagMapper<Adresvraag> adresvraagMapper = new WebserviceVraagMapper<>(Adresvraag.class, conversieLo3NaarBrpVragen);
        Adresvraag adresvraag = adresvraagMapper.mapVraag(vraag, partijCodeResolver.get().orElse(null));
        return adresvraagVerwerker.verwerk(adresvraag, "webservice adresvraag");
    }

    /**
     * Zet de adresvraagverwerker.
     * @param adresvraagVerwerker de te zetten verwerker
     */
    @Inject
    public void setAdresvraagVerwerker(final AdresvraagVerwerker adresvraagVerwerker) {
        this.adresvraagVerwerker = adresvraagVerwerker;
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
     * @param persoonsvraagVerwerker de te zetten verwerker
     */
    @Inject
    public void setPersoonsvraagVerwerker(final PersoonsvraagVerwerker persoonsvraagVerwerker) {
        this.persoonsvraagVerwerker = persoonsvraagVerwerker;
    }

    /**
     * Resolver voor partij code gebruikt binnen de webservice.
     * @param partijCodeResolver Resolver voor partij code gebruikt binnen de webservice
     */
    @Inject
    public void setPartijCodeResolver(final PartijCodeResolver partijCodeResolver) {
        this.partijCodeResolver = partijCodeResolver;
    }
}
