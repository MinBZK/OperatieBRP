/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.pl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3PersoonslijstFormatter;
import nl.bzk.migratiebrp.bericht.model.sync.generated.SynchroniseerNaarBrpAntwoordType.Kandidaat;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerBrpNaarLo3Service;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerLo3NaarBrpService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpPersoonslijstService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.PersoonslijstPersisteerResultaat;
import nl.bzk.migratiebrp.synchronisatie.dal.service.TeLeverenAdministratieveHandelingenAanwezigException;
import org.springframework.stereotype.Component;

/**
 * Persoonslijst service implementatie.
 */
@Component(value = "plService")
public final class PlServiceImpl implements PlService {

    private final BrpDalService brpDalService;
    private final BrpPersoonslijstService brpPersoonslijstService;
    private final ConverteerBrpNaarLo3Service converteerBrpNaarLo3Service;
    private final ConverteerLo3NaarBrpService converteerLo3NaarBrpService;

    /**
     * Constructor.
     * @param brpDalService brp dal service
     * @param brpPersoonslijstService brp persoonslijst service
     * @param converteerBrpNaarLo3Service converteer brp naar lo3 service
     * @param converteerLo3NaarBrpService converteer lo3 naar brp service
     */
    @Inject
    public PlServiceImpl(final BrpDalService brpDalService,
                         final BrpPersoonslijstService brpPersoonslijstService,
                         final ConverteerBrpNaarLo3Service converteerBrpNaarLo3Service,
                         final ConverteerLo3NaarBrpService converteerLo3NaarBrpService) {
        this.brpDalService = brpDalService;
        this.brpPersoonslijstService = brpPersoonslijstService;
        this.converteerBrpNaarLo3Service = converteerBrpNaarLo3Service;
        this.converteerLo3NaarBrpService = converteerLo3NaarBrpService;
    }

    /* *************************************************** */
    /* *************************************************** */
    /* *** ZOEKEN **************************************** */
    /* *************************************************** */
    /* *************************************************** */

    @Override
    public BrpPersoonslijst zoekPersoonslijstOpTechnischeSleutel(final Long technischeSleutel) {
        return brpPersoonslijstService.bevraagPersoonslijstOpTechnischeSleutelFoutiefOpgeschortUitsluiten(technischeSleutel);
    }

    @Override
    public BrpPersoonslijst zoekNietFoutievePersoonslijstOpActueelAnummer(final String anummer) {
        return brpPersoonslijstService.zoekPersoonOpAnummerFoutiefOpgeschortUitsluiten(anummer);
    }

    @Override
    public List<BrpPersoonslijst> zoekNietFoutievePersoonslijstenOpHistorischAnummer(String anummer) {
        return brpPersoonslijstService.zoekPersonenOpHistorischAnummerFoutiefOpgeschortUitsluiten(anummer);
    }

    @Override
    public BrpPersoonslijst zoekNietFoutievePersoonslijstOpActueelBurgerservicenummer(String burgerservicenummer) {
        return brpPersoonslijstService.zoekPersoonOpBsnFoutiefOpgeschortUitsluiten(burgerservicenummer);
    }

    @Override
    public List<BrpPersoonslijst> zoekNietFoutievePersoonslijstenOpHistorischBurgerservicenummer(String burgerservicenummer) {
        return brpPersoonslijstService.zoekPersoonOpHistorischBsnFoutiefOpgeschortUitsluiten(burgerservicenummer);
    }

    @Override
    public List<BrpPersoonslijst> zoekNietFoutievePersoonslijstenOpActueelVolgendeAnummer(String anummer) {
        return brpPersoonslijstService.zoekPersoonOpVolgendeAnummerFoutiefOpgeschortUitsluiten(anummer);
    }

    @Override
    public List<BrpPersoonslijst> zoekNietFoutievePersoonslijstenOpHistorischVolgendeAnummer(String anummer) {
        return brpPersoonslijstService.zoekPersoonOpHistorischVolgendeAnummerFoutiefOpgeschortUitsluiten(anummer);
    }

    @Override
    public List<BrpPersoonslijst> zoekNietFoutievePersoonslijstenOpActueelVorigeAnummer(String anummer) {
        return brpPersoonslijstService.zoekPersoonOpVorigeAnummerFoutiefOpgeschortUitsluiten(anummer);
    }

    @Override
    public List<BrpPersoonslijst> zoekNietFoutievePersoonslijstenOpHistorischVorigeAnummer(String anummer) {
        return brpPersoonslijstService.zoekPersoonOpHistorischVorigeAnummerFoutiefOpgeschortUitsluiten(anummer);
    }

    /* *************************************************** */
    /* *************************************************** */
    /* *** PERSISTEREN *********************************** */
    /* *************************************************** */
    /* *************************************************** */

    @Override
    public List<Long> persisteerPersoonslijst(final BrpPersoonslijst brpPersoonslijst, final Lo3Bericht lo3Bericht) {
        final PersoonslijstPersisteerResultaat persisteerResultaat = brpPersoonslijstService.persisteerPersoonslijst(brpPersoonslijst, lo3Bericht);

        return verwerkPersisteerResultaat(persisteerResultaat);
    }

    @Override
    public List<Long> persisteerPersoonslijst(final BrpPersoonslijst brpPersoonslijst, final Long teVervangenPersoonslijstId, final Lo3Bericht lo3Bericht)
            throws TeLeverenAdministratieveHandelingenAanwezigException {
        final PersoonslijstPersisteerResultaat persisteerResultaat =
                brpPersoonslijstService.persisteerPersoonslijst(brpPersoonslijst, teVervangenPersoonslijstId, lo3Bericht);

        return verwerkPersisteerResultaat(persisteerResultaat);
    }

    private List<Long> verwerkPersisteerResultaat(final PersoonslijstPersisteerResultaat resultaat) {
        resultaat.getPersoon();
        final Set<AdministratieveHandeling> administratieveHandelingen = resultaat.getAdministratieveHandelingen();

        final List<Long> administratieveHandelingIds = new ArrayList<>();
        for (final AdministratieveHandeling administratieveHandeling : administratieveHandelingen) {
            administratieveHandelingIds.add(administratieveHandeling.getId());
        }
        return administratieveHandelingIds;
    }

    @Override
    public void persisteerLogging(final Lo3Bericht bericht) {
        brpDalService.persisteerLo3Bericht(bericht);
    }

    /* *************************************************** */
    /* *************************************************** */
    /* *** CONVERTEREN *********************************** */
    /* *************************************************** */
    /* *************************************************** */

    @Override
    public BrpPersoonslijst converteerLo3PersoonlijstNaarBrpPersoonslijst(final Lo3Persoonslijst lo3Persoonslijst) {
        return converteerLo3NaarBrpService.converteerLo3Persoonslijst(lo3Persoonslijst);
    }

    @Override
    public String converteerKandidaat(final BrpPersoonslijst persoonslijst) {
        final Lo3Persoonslijst lo3Persoonslijst = converteerBrpNaarLo3Service.converteerBrpPersoonslijst(persoonslijst);
        final List<Lo3CategorieWaarde> categorieen = new Lo3PersoonslijstFormatter().format(lo3Persoonslijst);
        return Lo3Inhoud.formatInhoud(categorieen);
    }

    @Override
    public Kandidaat[] converteerKandidaten(final List<BrpPersoonslijst> persoonslijsten) {
        final List<Kandidaat> result = new ArrayList<>(persoonslijsten.size());

        for (final BrpPersoonslijst persoonslijst : persoonslijsten) {
            final Kandidaat huidigeKandidaat = new Kandidaat();
            huidigeKandidaat.setPersoonId(persoonslijst.getPersoonId());
            huidigeKandidaat.setVersie(persoonslijst.getAdministratieveHandelingId());
            huidigeKandidaat.setLo3PersoonslijstAlsTeletexString(converteerKandidaat(persoonslijst));
            result.add(huidigeKandidaat);
        }
        return result.toArray(new Kandidaat[persoonslijsten.size()]);
    }
}
