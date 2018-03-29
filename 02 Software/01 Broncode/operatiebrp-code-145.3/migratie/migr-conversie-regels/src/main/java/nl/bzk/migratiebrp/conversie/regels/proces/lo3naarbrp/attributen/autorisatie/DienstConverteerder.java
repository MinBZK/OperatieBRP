/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.autorisatie;

import java.util.Collections;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.LeverwijzeSelectie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSelectie;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstAttenderingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.autorisatie.BrpDienstSelectieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3AutorisatieInhoud;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;
import nl.bzk.migratiebrp.conversie.regels.expressie.ConverteerNaarExpressieService;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.Converteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.Lo3AttribuutConverteerder;
import org.springframework.stereotype.Component;

/**
 * DienstConverteerder.
 */
@Component
public class DienstConverteerder extends Converteerder {

    private final ConverteerNaarExpressieService converteerNaarExpressieService;

    /**
     * Constructor.
     * @param lo3AttribuutConverteerder lo3 attribuut converteerder
     * @param converteerNaarExpressieService converteer naar expressie service
     */
    @Inject
    public DienstConverteerder(final Lo3AttribuutConverteerder lo3AttribuutConverteerder, final ConverteerNaarExpressieService converteerNaarExpressieService) {
        super(lo3AttribuutConverteerder);
        this.converteerNaarExpressieService = converteerNaarExpressieService;
    }

    /**
     * Converteer van Lo3 model naar Migratie model.
     * @param categorie {@link Lo3Categorie} van {@link Lo3AutorisatieInhoud}
     * @return {@link nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel} van {@link BrpDienstInhoud}
     */
    final TussenStapel<BrpDienstInhoud> converteerDienst(final Lo3Categorie<Lo3AutorisatieInhoud> categorie) {

        final BrpDatum datumIngang = getLo3AttribuutConverteerder().converteerDatum(categorie.getInhoud().getDatumIngang());
        final BrpDatum datumEinde = getLo3AttribuutConverteerder().converteerDatum(categorie.getInhoud().getDatumEinde());

        final BrpDienstInhoud brpInhoud = new BrpDienstInhoud(datumIngang, datumEinde, null);

        return new TussenStapel<>(Collections.singletonList(new TussenGroep<>(
                brpInhoud,
                categorie.getHistorie(),
                categorie.getDocumentatie(),
                categorie.getLo3Herkomst())));

    }

    /**
     * Converteer van Lo3 model naar Migratie model.
     * @param categorie {@link Lo3Categorie} van {@link Lo3AutorisatieInhoud}
     * @return {@link nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel} van {@link BrpDienstInhoud}
     */
    final TussenStapel<BrpDienstAttenderingInhoud> converteerAttenderingDienst(final Lo3Categorie<Lo3AutorisatieInhoud> categorie) {
        final String attenderingsCriterium =
                converteerNaarExpressieService.converteerSleutelRubrieken(categorie.getInhoud().getSleutelrubriek(), categorie.getLo3Herkomst());

        final BrpDienstAttenderingInhoud inhoud = new BrpDienstAttenderingInhoud(attenderingsCriterium);

        return new TussenStapel<>(Collections.singletonList(new TussenGroep<>(
                inhoud,
                categorie.getHistorie(),
                categorie.getDocumentatie(),
                categorie.getLo3Herkomst())));

    }

    /**
     * Converteer van Lo3 model naar Migratie model.
     * @param categorie {@link Lo3Categorie} van {@link Lo3AutorisatieInhoud}
     * @return {@link nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel} van {@link BrpDienstInhoud}
     */
    final TussenStapel<BrpDienstSelectieInhoud> converteerSelectieDienst(final Lo3Categorie<Lo3AutorisatieInhoud> categorie) {

        final SoortSelectie soortSelectie = getLo3AttribuutConverteerder().converteerSoortSelectie(categorie.getInhoud().getSelectiesoort());
        final BrpDatum eersteSelectieDatum = getLo3AttribuutConverteerder().converteerDatum(categorie.getInhoud().getEersteSelectiedatum());
        final Short selectiePeriodeInMaanden =
                categorie.getInhoud().getSelectieperiode() != null ? categorie.getInhoud().getSelectieperiode().shortValue() : null;
        final Boolean indVerzendenVolledigBerichtBijPlaatsen = getLo3AttribuutConverteerder().converteerBerichtaanduiding(
                categorie.getInhoud().getBerichtaanduiding());
        final LeverwijzeSelectie medium = getLo3AttribuutConverteerder().converteerMediumSelectie(categorie.getInhoud().getMediumSelectie());

        final BrpDienstSelectieInhoud inhoud =
                new BrpDienstSelectieInhoud(soortSelectie, eersteSelectieDatum, selectiePeriodeInMaanden, indVerzendenVolledigBerichtBijPlaatsen, medium);

        return new TussenStapel<>(Collections.singletonList(new TussenGroep<>(
                inhoud,
                categorie.getHistorie(),
                categorie.getDocumentatie(),
                categorie.getLo3Herkomst())));

    }
}
