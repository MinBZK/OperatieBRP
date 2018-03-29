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
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.gba.dataaccess.IstTabelRepository;
import nl.bzk.brp.levering.lo3.conversie.ConversieCache;
import nl.bzk.brp.levering.lo3.conversie.Converteerder;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Representeert een lo3 adhoc webservice antwoord.
 */
public class OpvragenPLWebserviceAntwoord implements WebserviceAntwoord {

    // Config
    private final IstTabelRepository istTabelRepository;
    private final Converteerder converteerder;

    // Input
    private final List<Persoonslijst> personen;

    OpvragenPLWebserviceAntwoord(final IstTabelRepository istTabelRepository,
                                 final Converteerder converteerder,
                                 final List<Persoonslijst> personen) {
        this.istTabelRepository = istTabelRepository;
        this.converteerder = converteerder;
        this.personen = new ArrayList<>(personen);
    }

    /**
     * Mapt de lijst van persoonslijsten naar een lijst van geconverteerde en gefilterde lijst van Lo3CategorieWaarde.
     * @return een lijst van geconverteerde en gefilterde lijst van Lo3CategorieWaarde
     */
    public List<List<Lo3CategorieWaarde>> rubrieken(List<String> rubrieken) {
        return personen.stream().map(this::rubriekenVoorPersoon).collect(Collectors.toList());
    }

    private List<Lo3CategorieWaarde> rubriekenVoorPersoon(final Persoonslijst persoonslijst) {
        List<Stapel> istStapels = istTabelRepository.leesIstStapels(persoonslijst.getMetaObject().getObjectsleutel());
        return converteerder.converteer(persoonslijst, istStapels, null, null, new ConversieCache());
    }

}
