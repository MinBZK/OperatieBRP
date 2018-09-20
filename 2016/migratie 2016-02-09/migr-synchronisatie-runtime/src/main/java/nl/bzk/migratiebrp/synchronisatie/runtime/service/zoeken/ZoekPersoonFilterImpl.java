/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.zoeken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3PersoonslijstFormatter;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerBrpNaarLo3Service;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Onderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.PersoonRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.BrpPersoonslijstMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapperImpl;

/**
 * Filter personen op aanvullende zoek criteria.
 */
public final class ZoekPersoonFilterImpl implements ZoekPersoonFilter {

    @Inject
    private ConversietabelFactory conversietabelFactory;
    @Inject
    private PersoonRepository persoonRepository;
    @Inject
    private BrpPersoonslijstMapper brpPersoonslijstMapper;
    @Inject
    private ConverteerBrpNaarLo3Service converteerBrpNaarLo3Service;

    @Override
    public List<GevondenPersoon> filter(final List<Persoon> personen, final String aanvullendeZoekCriteria) throws BerichtSyntaxException {
        final List<GevondenPersoon> resultaat = new ArrayList<>();

        if (personen != null && !personen.isEmpty()) {

            // Bepaal filter
            final List<Lo3CategorieWaarde> lo3Filter = parseAanvullendeZoekCriteria(aanvullendeZoekCriteria);

            for (final Persoon persoon : personen) {
                // Map entiteit naar BrpPersoonslijst
                final List<Onderzoek> onderzoeken = persoonRepository.findOnderzoekenVoorPersoon(persoon);
                final BrpOnderzoekMapper brpOnderzoekMapper = new BrpOnderzoekMapperImpl(onderzoeken);
                final BrpPersoonslijst brpPersoonslijst = brpPersoonslijstMapper.mapNaarMigratie(persoon, brpOnderzoekMapper);

                // Converteer
                final Lo3Persoonslijst lo3Persoonslijst = converteerBrpNaarLo3Service.converteerBrpPersoonslijst(brpPersoonslijst);
                final List<Lo3CategorieWaarde> lo3Categorieen = new Lo3PersoonslijstFormatter().format(lo3Persoonslijst);

                if (voldoetAanFilter(lo3Categorieen, lo3Filter)) {
                    resultaat.add(mapPersoonNaarGevondenPersoon(persoon));
                    if (resultaat.size() >= 2) {
                        break;
                    }
                }
            }

        }
        return resultaat;
    }

    private List<Lo3CategorieWaarde> parseAanvullendeZoekCriteria(final String aanvullendeZoekCriteria) throws BerichtSyntaxException {
        if (aanvullendeZoekCriteria == null || "".equals(aanvullendeZoekCriteria)) {
            return Collections.emptyList();
        } else {
            return Lo3Inhoud.parseInhoud(aanvullendeZoekCriteria);
        }
    }

    /**
     * Controleert of een lijst aan lo3 categorieen voldoet aan een filter.
     *
     * @param lo3Categorieen
     *            lijst van lo3 categorieen
     * @param lo3FilterCategorieen
     *            filter
     * @return true, als de gegevens lijst van lo3 categorieen voldoet, anders false
     */
    private boolean voldoetAanFilter(final List<Lo3CategorieWaarde> lo3Categorieen, final List<Lo3CategorieWaarde> lo3FilterCategorieen) {
        for (final Lo3CategorieWaarde lo3Filter : lo3FilterCategorieen) {
            boolean ok = false;

            for (final Lo3CategorieWaarde lo3Categorie : lo3Categorieen) {
                if (lo3Categorie.getCategorie().equals(lo3Filter.getCategorie()) && nietOnjuist(lo3Categorie) && voldoetAanFilter(lo3Categorie, lo3Filter))
                {
                    ok = true;
                    break;
                }

            }

            if (!ok) {
                return false;
            }

        }

        return true;
    }

    private boolean nietOnjuist(final Lo3CategorieWaarde lo3Categorie) {
        final String onjuist = lo3Categorie.getElement(Lo3ElementEnum.ELEMENT_8410);
        return onjuist == null || "".equals(onjuist);
    }

    private boolean voldoetAanFilter(final Lo3CategorieWaarde lo3Categorie, final Lo3CategorieWaarde lo3Filter) {
        boolean result = true;
        for (final Map.Entry<Lo3ElementEnum, String> lo3FilterElement : lo3Filter.getElementen().entrySet()) {
            final Lo3ElementEnum element = lo3FilterElement.getKey();
            final String filterValue = lo3FilterElement.getValue();
            final String lo3Value = lo3Categorie.getElement(element);

            if (filterValue == null || "".equals(filterValue)) {
                if (lo3Value != null && !"".equals(lo3Value)) {
                    result = false;
                    break;
                }
            } else {
                if (!filterValue.equals(lo3Value)) {
                    result = false;
                    break;
                }
            }
        }

        return result;
    }

    private GevondenPersoon mapPersoonNaarGevondenPersoon(final Persoon persoon) {
        final Integer persoonId = persoon.getId();
        final Long administratienummer = persoon.getAdministratienummer();

        final String bijhoudingsgemeente;
        if (persoon.getBijhoudingspartij() != null) {
            final BrpPartijCode partijCode = new BrpPartijCode(persoon.getBijhoudingspartij().getCode());
            final Conversietabel<Lo3GemeenteCode, BrpPartijCode> partijConversietabel = conversietabelFactory.createPartijConversietabel();
            if (partijConversietabel.valideerBrp(partijCode)) {
                final Lo3GemeenteCode lo3GemeenteCode = partijConversietabel.converteerNaarLo3(partijCode);
                if (lo3GemeenteCode.isValideNederlandseGemeenteCode()) {
                    bijhoudingsgemeente = lo3GemeenteCode.getWaarde();
                } else {
                    bijhoudingsgemeente = null;
                }
            } else {
                bijhoudingsgemeente = null;
            }
        } else {
            bijhoudingsgemeente = null;
        }

        return new GevondenPersoon(persoonId, administratienummer, bijhoudingsgemeente);
    }
}
