/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.autorisatie;

import java.util.Collections;

import javax.inject.Inject;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpLeveringsautorisatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3AutorisatieInhoud;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.Lo3AttribuutConverteerder;

import org.springframework.stereotype.Component;

/**
 * LeveringsautorisatieInhoudConverteerder.
 */
@Component
public class LeveringsautorisatieConverteerder {

    @Inject
    private AutorisatieConversieHelper autorisatieConversieHelper;
    @Inject
    private Lo3AttribuutConverteerder lo3AttribuutConverteerder;

    /**
     * Converteer van Lo3 model naar Migratie model.
     *
     * @param categorie
     *            {@link Lo3Categorie} van {@link Lo3AutorisatieInhoud}
     * @param index
     *            de index binnen de stapel
     * @return {@link nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel} van
     *         {@link nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep} van {@link BrpLeveringsautorisatieInhoud}
     */
    public final TussenStapel<BrpLeveringsautorisatieInhoud> converteerLeveringsautorisatie(
        final Lo3Categorie<Lo3AutorisatieInhoud> categorie,
        final int index)
    {
        final String naam = autorisatieConversieHelper.maakNaamUniek(categorie.getInhoud().getAfnemersindicatie(), index, null);
        return new TussenStapel<>(Collections.singletonList(converteer(categorie, naam)));
    }

    private TussenGroep<BrpLeveringsautorisatieInhoud> converteer(final Lo3Categorie<Lo3AutorisatieInhoud> categorie, final String naam) {
        final BrpDatum datumIngang = lo3AttribuutConverteerder.converteerDatum(categorie.getInhoud().getDatumIngang());
        final BrpDatum datumEinde = lo3AttribuutConverteerder.converteerDatum(categorie.getInhoud().getDatumEinde());

        // Toestand is altijd definitief
        final BrpLeveringsautorisatieInhoud inhoud =
                new BrpLeveringsautorisatieInhoud(
                    naam,
                    lo3AttribuutConverteerder.converteerVerstrekkingsbeperking(categorie.getInhoud().getVerstrekkingsbeperking()),
                    Boolean.TRUE,
                    null,
                    null,
                    null,
                    datumIngang,
                    datumEinde,
                    null);

        return new TussenGroep<>(inhoud, categorie.getHistorie(), categorie.getDocumentatie(), categorie.getLo3Herkomst());

    }

}
