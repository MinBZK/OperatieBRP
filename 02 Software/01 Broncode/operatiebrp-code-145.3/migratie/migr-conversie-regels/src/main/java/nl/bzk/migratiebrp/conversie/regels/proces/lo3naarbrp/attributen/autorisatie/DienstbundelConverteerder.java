/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.autorisatie;

import java.util.Collections;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstbundelInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3AutorisatieInhoud;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;
import nl.bzk.migratiebrp.conversie.regels.expressie.ConverteerNaarExpressieService;
import nl.bzk.migratiebrp.conversie.regels.expressie.Lo3VoorwaardeRegelOnvertaalbaarExceptie;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.Converteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.Lo3AttribuutConverteerder;
import org.springframework.stereotype.Component;

/**
 * DienstConverteerder.
 */
@Component
public class DienstbundelConverteerder extends Converteerder {

    private final ConverteerNaarExpressieService converteerNaarExpressieService;

    /**
     * Constructor.
     * @param lo3AttribuutConverteerder
     * @param converteerNaarExpressieService
     */
    @Inject
    public DienstbundelConverteerder(final Lo3AttribuutConverteerder lo3AttribuutConverteerder,
                                     final ConverteerNaarExpressieService converteerNaarExpressieService) {
        super(lo3AttribuutConverteerder);
        this.converteerNaarExpressieService = converteerNaarExpressieService;
    }

    /**
     * Converteer van Lo3 model naar Migratie model.
     * @param categorie {@link Lo3Categorie} van {@link Lo3AutorisatieInhoud}
     * @param naam naam van de dienstbundel
     * @param voorwaarderegel voorwaarderegel van de dienstbundel
     * @return {@link nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel} van {@link BrpDienstbundelInhoud}
     */
    final TussenStapel<BrpDienstbundelInhoud> converteerDienstbundel(
            final Lo3Categorie<Lo3AutorisatieInhoud> categorie,
            final String naam,
            final String voorwaarderegel) {
        return new TussenStapel<>(Collections.singletonList(converteer(categorie, naam, voorwaarderegel)));

    }

    private TussenGroep<BrpDienstbundelInhoud> converteer(
            final Lo3Categorie<Lo3AutorisatieInhoud> categorie,
            final String naam,
            final String voorwaarderegel) {

        final BrpDatum datumIngang = getLo3AttribuutConverteerder().converteerDatum(categorie.getInhoud().getDatumIngang());
        final BrpDatum datumEinde = getLo3AttribuutConverteerder().converteerDatum(categorie.getInhoud().getDatumEinde());

        String naderePopulatiebeperking;
        Boolean indicatieNaderePopulatiebeperkingVolledigGeconverteerd;
        String toelichting;
        try {
            naderePopulatiebeperking = converteerNaarExpressieService.converteerVoorwaardeRegel(voorwaarderegel);
            indicatieNaderePopulatiebeperkingVolledigGeconverteerd = null;
            toelichting = null;
        } catch (final Lo3VoorwaardeRegelOnvertaalbaarExceptie e) {
            naderePopulatiebeperking = e.getGeconverteerdDeel();
            toelichting = "ONGECONVERTEERDE GBA-VOORWAARDEREGEL: " + voorwaarderegel;
            indicatieNaderePopulatiebeperkingVolledigGeconverteerd = Boolean.FALSE;
        }

        final BrpDienstbundelInhoud inhoud =
                new BrpDienstbundelInhoud(
                        naam,
                        datumIngang,
                        datumEinde,
                        naderePopulatiebeperking,
                        indicatieNaderePopulatiebeperkingVolledigGeconverteerd,
                        toelichting,
                        null);
        return new TussenGroep<>(inhoud, categorie.getHistorie(), categorie.getDocumentatie(), categorie.getLo3Herkomst());
    }

}
