/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc1003;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Ap01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Av01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ZoekPersoonOpActueleGegevensVerzoekBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ZoekPersoonOpHistorischeGegevensVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Maakt een zoek persoon bericht aan.
 */
@Component("uc1003MaakZoekPersoonAction")
public final class MaakZoekPersoonBerichtAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private BerichtenDao berichtenDao;

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.debug("execute(parameters={})", parameters);

        // input bericht
        final Long berichtId = (Long) parameters.get("input");
        final Lo3Bericht input = (Lo3Bericht) berichtenDao.leesBericht(berichtId);

        // verzoek
        final SyncBericht verzoek = maakZoekPersoonVerzoekBericht(input);

        // opslaan
        final Long verzoekId = berichtenDao.bewaarBericht(verzoek);
        final Map<String, Object> result = new HashMap<>();
        result.put("zoekPersoonVerzoek", verzoekId);

        LOG.debug("result: {}", result);
        return result;
    }

    private SyncBericht maakZoekPersoonVerzoekBericht(final Lo3Bericht input) {
        final SyncBericht result;
        if (input instanceof Ap01Bericht) {
            final Ap01Bericht ap01 = (Ap01Bericht) input;

            if (ap01.bevatActueleZoekGegevens()) {
                final ZoekPersoonOpActueleGegevensVerzoekBericht verzoek = new ZoekPersoonOpActueleGegevensVerzoekBericht();
                verzoek.setANummer(ap01.getActueelAnummer());
                verzoek.setBsn(ap01.getActueelBurgerservicenummer());
                verzoek.setGeslachtsnaam(ap01.getActueleGeslachtsnaam());
                verzoek.setPostcode(ap01.getActuelePostcode());

                final List<Lo3CategorieWaarde> categorieen =
                        verwijderElementen(
                            verwijderElementen(
                                ap01.getCategorieen(),
                                Lo3CategorieEnum.CATEGORIE_01,
                                Lo3ElementEnum.ELEMENT_0110,
                                Lo3ElementEnum.ELEMENT_0120,
                                Lo3ElementEnum.ELEMENT_0240),
                            Lo3CategorieEnum.CATEGORIE_08,
                            Lo3ElementEnum.ELEMENT_1160);

                if (!categorieen.isEmpty()) {
                    verzoek.setAanvullendeZoekcriteria(Lo3Inhoud.formatInhoud(categorieen));
                }

                result = verzoek;
            } else {
                final ZoekPersoonOpHistorischeGegevensVerzoekBericht verzoek = new ZoekPersoonOpHistorischeGegevensVerzoekBericht();
                verzoek.setANummer(ap01.getHistorischAnummer());
                verzoek.setBsn(ap01.getHistorischBurgerservicenummer());
                verzoek.setGeslachtsnaam(ap01.getHistorischeGeslachtsnaam());

                final List<Lo3CategorieWaarde> categorieen =
                        verwijderElementen(
                            ap01.getCategorieen(),
                            Lo3CategorieEnum.CATEGORIE_51,
                            Lo3ElementEnum.ELEMENT_0110,
                            Lo3ElementEnum.ELEMENT_0120,
                            Lo3ElementEnum.ELEMENT_0240);

                if (!categorieen.isEmpty()) {
                    verzoek.setAanvullendeZoekcriteria(Lo3Inhoud.formatInhoud(categorieen));
                }
                result = verzoek;
            }

        } else {
            final Av01Bericht av01 = (Av01Bericht) input;

            final ZoekPersoonOpActueleGegevensVerzoekBericht verzoek = new ZoekPersoonOpActueleGegevensVerzoekBericht();
            verzoek.setANummer(av01.getANummer());
            result = verzoek;
        }

        return result;
    }

    private List<Lo3CategorieWaarde> verwijderElementen(
        final List<Lo3CategorieWaarde> categorieen,
        final Lo3CategorieEnum categorie,
        final Lo3ElementEnum... elementen)
    {
        final List<Lo3CategorieWaarde> result = new ArrayList<>();

        for (final Lo3CategorieWaarde teVerwerkenCategorie : categorieen) {
            if (teVerwerkenCategorie.getCategorie().equals(categorie)) {
                for (final Lo3ElementEnum element : elementen) {
                    teVerwerkenCategorie.addElement(element, null);
                }
            }

            if (!teVerwerkenCategorie.isEmpty()) {
                result.add(teVerwerkenCategorie);
            }
        }

        return result;
    }
}
