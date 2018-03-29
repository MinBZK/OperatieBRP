/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc1003;

import edu.emory.mathcs.backport.java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Ap01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Av01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.SoortDienstType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AdHocZoekPersoonVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import org.springframework.stereotype.Component;

/**
 * Maakt een zoek persoon bericht aan.
 */
@Component("uc1003MaakAdHocZoekPersoonAction")
public final class MaakAdHocZoekPersoonBerichtAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String RUBRIEK_ANUMMER = Lo3CategorieEnum.PERSOON.getCategorie() + Lo3ElementEnum.ANUMMER.getElementNummer();
    private static final String RUBRIEK_BSN = Lo3CategorieEnum.PERSOON.getCategorie() + Lo3ElementEnum.BURGERSERVICENUMMER.getElementNummer();

    private final BerichtenDao berichtenDao;

    /**
     * Constructor.
     * @param berichtenDao berichten dao
     */
    @Inject
    public MaakAdHocZoekPersoonBerichtAction(final BerichtenDao berichtenDao) {
        this.berichtenDao = berichtenDao;
    }

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);

        // input bericht
        final Long berichtId = (Long) parameters.get("input");
        final Lo3Bericht input = (Lo3Bericht) berichtenDao.leesBericht(berichtId);

        // verzoek
        final SyncBericht verzoek = maakAdhocZoekPersoonVerzoekBericht(input);

        // opslaan
        final Long verzoekId = berichtenDao.bewaarBericht(verzoek);
        final Map<String, Object> result = new HashMap<>();
        result.put("adHocZoekPersoonVerzoek", verzoekId);

        LOG.debug("result: {}", result);
        return result;
    }

    private SyncBericht maakAdhocZoekPersoonVerzoekBericht(final Lo3Bericht input) {
        final SyncBericht result;
        if (input instanceof Ap01Bericht) {
            final Ap01Bericht ap01 = (Ap01Bericht) input;
            final AdHocZoekPersoonVerzoekBericht verzoek = maakVerzoek(ap01.getBronPartijCode(), ap01.getCategorieen(), SoortDienst.PLAATSING_AFNEMERINDICATIE);

            result = verzoek;

        } else {
            final Av01Bericht av01 = (Av01Bericht) input;

            final Map<Lo3ElementEnum, String> persoonsIdentificerendeGegevens = Collections.singletonMap(Lo3ElementEnum.ELEMENT_0110, av01.getANummer());
            final AdHocZoekPersoonVerzoekBericht
                    verzoek =
                    maakVerzoek(av01.getBronPartijCode(),
                            Collections.singletonList(new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0, persoonsIdentificerendeGegevens)),
                            SoortDienst.VERWIJDERING_AFNEMERINDICATIE);

            result = verzoek;
        }

        return result;
    }

    private AdHocZoekPersoonVerzoekBericht maakVerzoek(final String partijCode,
                                                       final List<Lo3CategorieWaarde> persoonIdentificerendeGegevens,
                                                       final SoortDienst soortDienst) {
        final AdHocZoekPersoonVerzoekBericht verzoek = new AdHocZoekPersoonVerzoekBericht();
        verzoek.setPartijCode(partijCode);
        verzoek.setPersoonIdentificerendeGegevens(Lo3Inhoud.formatInhoud(persoonIdentificerendeGegevens));
        verzoek.getGevraagdeRubrieken().add(RUBRIEK_ANUMMER);
        verzoek.getGevraagdeRubrieken().add(RUBRIEK_BSN);
        verzoek.setSoortDienst(SoortDienstType.fromValue(soortDienst.name()));
        return verzoek;
    }
}
