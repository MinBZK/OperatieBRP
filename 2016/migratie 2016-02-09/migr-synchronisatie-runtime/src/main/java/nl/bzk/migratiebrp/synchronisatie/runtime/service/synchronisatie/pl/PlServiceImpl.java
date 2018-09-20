/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.pl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3PersoonslijstFormatter;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerBrpNaarLo3Service;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Bericht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.PersoonslijstPersisteerResultaat;
import org.springframework.stereotype.Component;

/**
 * Persoonslijst service implementatie.
 */
@Component(value = "plService")
public final class PlServiceImpl implements PlService {

    @Inject
    private BrpNotificator notificator;

    @Inject
    private BrpDalService brpDalService;

    @Inject
    private ConverteerBrpNaarLo3Service converteerBrpNaarLo3Service;

    @Override
    public List<BrpPersoonslijst> zoekPersoonslijstenOpActueelAnummer(final long anummer) {
        final BrpPersoonslijst brpPersoonslijst = brpDalService.zoekPersoonOpAnummer(anummer);
        return brpPersoonslijst == null ? Collections.<BrpPersoonslijst>emptyList() : Collections.singletonList(brpPersoonslijst);
    }

    @Override
    public BrpPersoonslijst zoekNietFoutievePersoonslijstOpActueelAnummer(final long anummer) {
        return brpDalService.zoekPersoonOpAnummerFoutiefOpgeschortUitsluiten(anummer);
    }

    @Override
    public List<BrpPersoonslijst> zoekPersoonslijstenOpHistorischAnummer(final long anummer) {
        return brpDalService.zoekPersonenOpHistorischAnummer(anummer);
    }

    @Override
    public List<Long> persisteerPersoonslijst(final BrpPersoonslijst brpPersoonslijst, final Lo3Bericht lo3Bericht) {
        final PersoonslijstPersisteerResultaat persisteerResultaat = brpDalService.persisteerPersoonslijst(brpPersoonslijst, lo3Bericht);

        return verwerkPersisteerResultaat(persisteerResultaat);
    }

    @Override
    public List<Long> persisteerPersoonslijst(
        final BrpPersoonslijst brpPersoonslijst,
        final long teVervangenAnummer,
        final boolean isANummerWijziging,
        final Lo3Bericht lo3Bericht)
    {
        final PersoonslijstPersisteerResultaat persisteerResultaat =
                brpDalService.persisteerPersoonslijst(brpPersoonslijst, teVervangenAnummer, isANummerWijziging, lo3Bericht);

        return verwerkPersisteerResultaat(persisteerResultaat);
    }

    private List<Long> verwerkPersisteerResultaat(final PersoonslijstPersisteerResultaat resultaat) {
        final Persoon persoon = resultaat.getPersoon();
        final Set<AdministratieveHandeling> administratieveHandelingen = resultaat.getAdministratieveHandelingen();
        notificator.stuurGbaBijhoudingBerichten(administratieveHandelingen, persoon.getId());

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

    @Override
    public String converteerKandidaat(final BrpPersoonslijst persoonslijst) {
        final Lo3Persoonslijst lo3Persoonslijst = converteerBrpNaarLo3Service.converteerBrpPersoonslijst(persoonslijst);
        final List<Lo3CategorieWaarde> categorieen = new Lo3PersoonslijstFormatter().format(lo3Persoonslijst);
        return Lo3Inhoud.formatInhoud(categorieen);
    }

    @Override
    public String[] converteerKandidaten(final List<BrpPersoonslijst> persoonslijsten) {
        final List<String> result = new ArrayList<>(persoonslijsten.size());

        for (final BrpPersoonslijst persoonslijst : persoonslijsten) {
            result.add(converteerKandidaat(persoonslijst));
        }
        return result.toArray(new String[persoonslijsten.size()]);
    }

}
