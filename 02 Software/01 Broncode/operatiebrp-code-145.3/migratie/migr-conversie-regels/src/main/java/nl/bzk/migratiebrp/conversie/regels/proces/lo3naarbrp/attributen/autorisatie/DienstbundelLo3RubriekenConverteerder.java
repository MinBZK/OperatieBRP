/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.autorisatie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstbundelLo3RubriekInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3AutorisatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;
import nl.bzk.migratiebrp.conversie.model.tussen.autorisatie.TussenDienstbundelLo3Rubriek;
import nl.bzk.migratiebrp.conversie.regels.proces.foutmelding.Foutmelding;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.Converteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.Lo3AttribuutConverteerder;
import org.springframework.stereotype.Component;

/**
 * DienstConverteerder.
 */
@Component
public class DienstbundelLo3RubriekenConverteerder extends Converteerder {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final AutorisatieConversieHelper autorisatieConversieHelper;

    /**
     * constructor.
     * @param autorisatieConversieHelper
     * @param lo3AttribuutConverteerder
     */
    @Inject
    public DienstbundelLo3RubriekenConverteerder(final AutorisatieConversieHelper autorisatieConversieHelper,
                                                 final Lo3AttribuutConverteerder lo3AttribuutConverteerder) {
        super(lo3AttribuutConverteerder);
        this.autorisatieConversieHelper = autorisatieConversieHelper;
    }

    /**
     * Converteer van Lo3 model naar Migratie model.
     * @param categorie {@link Lo3Categorie} van {@link Lo3AutorisatieInhoud}
     * @param lo3Rubrieken de lo3 rubrieken
     * @return {@link nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel} van {@link BrpDienstbundelLo3RubriekInhoud}
     */
    final List<TussenDienstbundelLo3Rubriek> converteerDienstbundelLo3Rubriek(
            final Lo3Categorie<Lo3AutorisatieInhoud> categorie,
            final String lo3Rubrieken) {
        final List<String> lo3RubriekenLijst = maakLo3Rubrieken(lo3Rubrieken, categorie.getLo3Herkomst());
        final List<TussenDienstbundelLo3Rubriek> dienstbundelLo3Rubrieken = new ArrayList<>();
        for (final String lo3Rubriek : lo3RubriekenLijst) {
            dienstbundelLo3Rubrieken.add(
                    new TussenDienstbundelLo3Rubriek(lo3Rubriek, true, new TussenStapel<>(Collections.singletonList(converteer(categorie)))));
        }

        return dienstbundelLo3Rubrieken;
    }

    private List<String> maakLo3Rubrieken(final String autorisatieRubrieken, final Lo3Herkomst herkomst) {
        final List<String> result = new ArrayList<>();
        final Set<String> rubrieken = autorisatieConversieHelper.bepaalRubrieken(autorisatieRubrieken);
        for (final String rubriek : rubrieken) {
            try {
                result.add(getLo3AttribuutConverteerder().converteerFilterRubriek(rubriek));
            } catch (final IllegalArgumentException e) {
                LOGGER.warn("Fout tijdens maken LO3 rubrieken", e);
                Foutmelding.logMeldingFout(herkomst, LogSeverity.WARNING, SoortMeldingCode.AUT008, null);
            }
        }
        return result;
    }

    private TussenGroep<BrpDienstbundelLo3RubriekInhoud> converteer(final Lo3Categorie<Lo3AutorisatieInhoud> categorie) {

        final BrpDienstbundelLo3RubriekInhoud inhoud = new BrpDienstbundelLo3RubriekInhoud(false);
        return new TussenGroep<>(inhoud, categorie.getHistorie(), categorie.getDocumentatie(), categorie.getLo3Herkomst());
    }

}
