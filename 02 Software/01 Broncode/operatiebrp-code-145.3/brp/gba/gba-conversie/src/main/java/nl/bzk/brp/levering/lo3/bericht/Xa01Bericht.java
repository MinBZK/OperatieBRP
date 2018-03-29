/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.bericht;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.gba.dataaccess.IstTabelRepository;
import nl.bzk.brp.levering.lo3.conversie.ConversieCache;
import nl.bzk.brp.levering.lo3.conversie.Converteerder;
import nl.bzk.brp.levering.lo3.filter.Filter;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Header;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Xa01Bericht.
 */
public class Xa01Bericht {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final Lo3Header HEADER = new Lo3Header(Lo3HeaderVeld.RANDOM_KEY, Lo3HeaderVeld.BERICHTNUMMER);
    private static final SoortBericht SOORT_BERICHT = SoortBericht.XA01;

    // Config
    private final IstTabelRepository istTabelRepository;
    private final Converteerder converteerder;
    private final Filter filter;

    // Input
    private final List<Persoonslijst> personen;

    Xa01Bericht(final IstTabelRepository istTabelRepository,
                final Converteerder converteerder,
                final Filter filter,
                final List<Persoonslijst> personen) {
        this.istTabelRepository = istTabelRepository;
        this.converteerder = converteerder;
        this.filter = filter;
        this.personen = new ArrayList<>(personen);
    }

    /**
     * Maakt een Xa01 bericht voor de lijst van gevraagde rubrieken.
     * @param gevraagdeRubrieken lijst van gevraagde rubrieken
     * @return geformatteerd Xa01 bericht
     */
    public String maakUitgaandBericht(final List<String> gevraagdeRubrieken) {
        return header() + inhoud(gevraagdeRubrieken);
    }

    private String header() {
        final String[] headers = new String[]{null, SOORT_BERICHT.getBerichtNummer()};
        return HEADER.formatHeaders(headers);
    }

    private String inhoud(final List<String> gevraagdeRubrieken) {
        return Lo3Inhoud.formatLo3Inhoud(
                personen.stream()
                        .sorted(Comparator.comparing(this::anummer))
                        .map(persoon -> filterRubrieken(persoon, gevraagdeRubrieken))
                        .map(Lo3Inhoud::converteerNaarLo3Inhoud)
                        .flatMap(List::stream)
                        .collect(Collectors.toList()));
    }

    private List<Lo3CategorieWaarde> filterRubrieken(final Persoonslijst persoon, final List<String> rubrieken) {
        LOGGER.debug("Filteren voor {} voor persoon {}", SOORT_BERICHT, persoon.getMetaObject().getObjectsleutel());
        List<Stapel> istStapels = istTabelRepository.leesIstStapels(persoon.getMetaObject().getObjectsleutel());
        List<Lo3CategorieWaarde> categorieen =
                converteerder.converteer(persoon, istStapels, null, null, new ConversieCache());
        return filter.filter(persoon, istStapels, null, null, categorieen, rubrieken);
    }

    private String anummer(final Persoonslijst persoonslijst) {
        return persoonslijst.<String>getActueleAttribuutWaarde(ElementHelper.getAttribuutElement(Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER))
                .orElseThrow(() -> new IllegalStateException("A Nummer mag niet leeg zijn"));
    }
}
