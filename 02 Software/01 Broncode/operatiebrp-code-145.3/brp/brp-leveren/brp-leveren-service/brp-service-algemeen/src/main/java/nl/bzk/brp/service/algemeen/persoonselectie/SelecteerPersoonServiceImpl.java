/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.persoonselectie;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.RegelValidatie;
import nl.bzk.brp.service.algemeen.RegelValidatieService;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.algemeen.VerstrekkingsbeperkingService;
import nl.bzk.brp.service.algemeen.blob.PersoonslijstService;
import nl.bzk.brp.service.algemeen.expressie.ExpressieService;
import nl.bzk.brp.service.dalapi.GeefDetailsPersoonRepository;
import org.springframework.stereotype.Service;

/**
 * SelecteerPersoonServiceImpl.
 */
@Bedrijfsregel(Regel.R1539)
@Bedrijfsregel(Regel.R1339)
@Bedrijfsregel(Regel.R1401)
@Bedrijfsregel(Regel.R1403)
@Bedrijfsregel(Regel.R1983)

@Service
final class SelecteerPersoonServiceImpl implements SelecteerPersoonService {

    @Inject
    private PersoonslijstService persoonslijstService;
    @Inject
    private GeefDetailsPersoonRepository persoonRepository;
    @Inject
    private RegelValidatieService regelValidatieService;
    @Inject
    private ExpressieService expressieService;
    @Inject
    private VerstrekkingsbeperkingService verstrekkingsbeperkingService;

    private SelecteerPersoonServiceImpl() {

    }

    @Override
    public Persoonslijst selecteerPersoonMetBsn(final String bsn, final Autorisatiebundel autorisatiebundel) throws StapMeldingException {
        final List<Long> persoonIds = persoonRepository.zoekIdsPersoonMetBsn(bsn);
        return selecteerPersoon(autorisatiebundel, persoonIds);
    }

    @Override
    public Persoonslijst selecteerPersoonMetANummer(final String anr, final Autorisatiebundel autorisatiebundel) throws StapMeldingException {
        final List<Long> persoonIds = persoonRepository.zoekIdsPersoonMetAnummer(anr);
        return selecteerPersoon(autorisatiebundel, persoonIds);
    }

    @Override
    public Persoonslijst selecteerPersoonMetId(final Long id, final Autorisatiebundel autorisatiebundel)
            throws StapMeldingException {
        return selecteerPersoon(autorisatiebundel, Collections.singletonList(id));
    }

    private Persoonslijst selecteerPersoon(final Autorisatiebundel autorisatiebundel, final List<Long> persoonIds) throws StapMeldingException {
        if (persoonIds.isEmpty()) {
            // Er moet een persoon bestaan met het opgegeven identiteitsnummer
            throw new StapMeldingException(Collections.singletonList(new Melding(Regel.R1403)));
        }
        final Set<Melding> totaalMeldingen = new HashSet<>();
        final List<Persoonslijst> geldigePersonen = bepaalGeldigePersonen(autorisatiebundel, persoonIds, totaalMeldingen);
        if (geldigePersonen.isEmpty()) {
            // Er moet een persoon bestaan met het opgegeven identiteitsnummer
            throw new StapMeldingException(Lists.newArrayList(totaalMeldingen));
        }
        if (geldigePersonen.size() != 1) {
            //dit kan nooit meer dan 1 persoon opleveren, unique constraint op bsn en anummer en pseudo die gefilterd worden
            // en andere ingang is id
            throw new IllegalArgumentException("selecteer persoon mag niet meer dan 1 persoon uit query opleveren");
        }
        return geldigePersonen.get(0);
    }

    private List<Persoonslijst> bepaalGeldigePersonen(final Autorisatiebundel autorisatiebundel,
                                                      final List<Long> persoonIds,
                                                      final Set<Melding> totaalMeldingen) {
        final List<Persoonslijst> geldigePersonen = new ArrayList<>();
        for (final Long persoonId : persoonIds) {
            final Persoonslijst persoon = persoonslijstService.getById(persoonId);
            //controleer soortpersoon en bijhoudingsaard
            final boolean bestaatPersoon = bestaatPersoon(persoon);
            if (!bestaatPersoon) {
                totaalMeldingen.add(new Melding(Regel.R1403));
                continue;
            }
            //valideer
            final List<RegelValidatie> regels = new ArrayList<>();
            //controleer afnemerindicatie
            final Persoonslijst persoonNu = persoon.getNuNuBeeld();
            regels.add(new R1401Regel(persoonNu, autorisatiebundel));
            if (SoortDienst.VERWIJDERING_AFNEMERINDICATIE != autorisatiebundel.getSoortDienst()) {
                //controleer verstrekkingsbeperking
                regels.add(verstrekkingsbeperkingService.maakRegelValidatie(persoonNu, autorisatiebundel.getPartij()));
                //controleer geldig persoon
                regels.add(new R1403RegelControleerDoelbinding(persoonNu, expressieService, autorisatiebundel));
            }
            final List<Melding> meldingen = regelValidatieService.valideerEnGeefMeldingen(regels);
            if (!meldingen.isEmpty()) {
                totaalMeldingen.addAll(meldingen);
            } else {
                geldigePersonen.add(persoon);
            }

        }
        return geldigePersonen;
    }

    private boolean bestaatPersoon(final Persoonslijst persoon) {
        return persoon != null &&
                persoon.<String>getActueleAttribuutWaarde(ElementHelper.getAttribuutElement(Element.PERSOON_BIJHOUDING_NADEREBIJHOUDINGSAARDCODE.getId()))
                        .map(this::isNietFoutOnbekendOfGewist)
                        .orElse(true)
                &&
                persoon.<String>getActueleAttribuutWaarde(ElementHelper.getAttribuutElement(Element.PERSOON_SOORTCODE.getId()))
                        .map(this::isGeenPseudoPersoon)
                        .orElse(true);
    }

    private boolean isGeenPseudoPersoon(final String soortPersoonCode) {
        return !SoortPersoon.PSEUDO_PERSOON.getCode().equals(soortPersoonCode);
    }

    private boolean isNietFoutOnbekendOfGewist(final String naderebijhoudingsAardCode) {
        return !(NadereBijhoudingsaard.FOUT.getCode().equals(naderebijhoudingsAardCode)
                || NadereBijhoudingsaard.ONBEKEND.getCode().equals(naderebijhoudingsAardCode)
                || NadereBijhoudingsaard.GEWIST.getCode().equals(naderebijhoudingsAardCode));
    }
}
