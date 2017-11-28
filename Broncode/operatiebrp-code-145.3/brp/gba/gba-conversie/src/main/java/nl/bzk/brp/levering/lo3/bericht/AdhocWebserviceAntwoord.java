/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.bericht;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.gba.dataaccess.IstTabelRepository;
import nl.bzk.brp.levering.lo3.conversie.ConversieCache;
import nl.bzk.brp.levering.lo3.conversie.Converteerder;
import nl.bzk.brp.levering.lo3.filter.Filter;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Representeert een lo3 adhoc webservice antwoord.
 */
public class AdhocWebserviceAntwoord implements WebserviceAntwoord {

    private static final String RUBRIEK_OPSCHORT_CODE = "07.67.20";
    private static final String RUBRIEK_OPSCHORT_DATUM = "07.67.10";

    // Config
    private final IstTabelRepository istTabelRepository;
    private final Converteerder converteerder;
    private final Filter filter;

    // Input
    private final List<Persoonslijst> personen;

    AdhocWebserviceAntwoord(final IstTabelRepository istTabelRepository,
                            final Converteerder converteerder,
                            final Filter filter,
                            final List<Persoonslijst> personen) {
        this.istTabelRepository = istTabelRepository;
        this.converteerder = converteerder;
        this.filter = filter;
        this.personen = new ArrayList<>(personen);
    }

    /**
     * Mapt de lijst van persoonslijsten naar een lijst van geconverteerde en gefilterde lijst van Lo3CategorieWaarde.
     * @param rubrieken lijst van gevraagde rubrieken
     * @return een lijst van geconverteerde en gefilterde lijst van Lo3CategorieWaarde
     */
    public List<List<Lo3CategorieWaarde>> rubrieken(List<String> rubrieken) {
        return personen.stream().map(persoon -> rubriekenVoorPersoon(persoon, rubrieken)).collect(Collectors.toList());
    }

    private List<Lo3CategorieWaarde> rubriekenVoorPersoon(final Persoonslijst persoonslijst, List<String> rubrieken) {
        // Indien de persoonslijst een opschortreden bevat, dienst 07.67.10 en 07.67.20 meegeleverd te worden.
        final List<String> filterRubrieken = new ArrayList<>(rubrieken);
        if (persoonslijst.<String>getActueleAttribuutWaarde(ElementHelper.getAttribuutElement(Element.PERSOON_BIJHOUDING_NADEREBIJHOUDINGSAARDCODE.getId()))
                .isPresent()) {
            if (!rubrieken.contains(RUBRIEK_OPSCHORT_CODE)) {
                filterRubrieken.add(RUBRIEK_OPSCHORT_CODE);
            }
            if (!rubrieken.contains(RUBRIEK_OPSCHORT_DATUM)) {
                filterRubrieken.add(RUBRIEK_OPSCHORT_DATUM);
            }
        }
        List<Stapel> istStapels = istTabelRepository.leesIstStapels(persoonslijst.getMetaObject().getObjectsleutel());
        List<Lo3CategorieWaarde> categorieen =
                converteerder.converteer(persoonslijst, istStapels, null, null, new ConversieCache());
        return filter.filter(persoonslijst, istStapels, null, null, categorieen, filterRubrieken);
    }

}
