/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.Requirement;
import nl.moderniseringgba.migratie.Requirements;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpReisdocumentRedenOntbreken;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBelemmeringVerstrekkingReisdocumentIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBezitBuitenlandsReisdocumentIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpReisdocumentInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3ReisdocumentInhoud;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieGroep;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratiePersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.migratie.MigratieStapel;

import org.springframework.stereotype.Component;

/**
 * Deze class bevat de conversie logica om de LO3 Categorie Reisdocument te converteren naar BRP.
 */
@Component
@Requirement(Requirements.CCA12)
public class ReisdocumentConverteerder {

    @Inject
    private Lo3AttribuutConverteerder converteerder;

    /**
     * Converteert de LO3 Reisdocument categorie naar het BRP model en vult hiermee de migratiePersoonslijstBuilder aan.
     * 
     * @param lo3ReisdocumentStapels
     *            de overlijden stapel, mag leeg zijn, maar niet null
     * @param migratiePersoonslijstBuilder
     *            de migratie builder
     */
    public final void converteer(
            final List<Lo3Stapel<Lo3ReisdocumentInhoud>> lo3ReisdocumentStapels,
            final MigratiePersoonslijstBuilder migratiePersoonslijstBuilder) {
        for (final Lo3Stapel<Lo3ReisdocumentInhoud> lo3ReisdocumentStapel : lo3ReisdocumentStapels) {
            final List<MigratieGroep<BrpReisdocumentInhoud>> migratieReisdocumenten =
                    new ArrayList<MigratieGroep<BrpReisdocumentInhoud>>();
            final List<MigratieGroep<BrpBelemmeringVerstrekkingReisdocumentIndicatieInhoud>> belemmeringIndicaties =
                    new ArrayList<MigratieGroep<BrpBelemmeringVerstrekkingReisdocumentIndicatieInhoud>>();
            final List<MigratieGroep<BrpBezitBuitenlandsReisdocumentIndicatieInhoud>> bezitBuitenlandsReisdocumentInds =
                    new ArrayList<MigratieGroep<BrpBezitBuitenlandsReisdocumentIndicatieInhoud>>();

            if (lo3ReisdocumentStapel.size() != 1) {
                throw new AssertionError(
                        "LO3 reisdocument stapel moet precies 1 record bevatten (geen historie), maar had er: "
                                + lo3ReisdocumentStapel.size());
            }
            final Lo3Categorie<Lo3ReisdocumentInhoud> lo3ReisdocumentCategorie =
                    lo3ReisdocumentStapel.getMeestRecenteElement();
            final Lo3ReisdocumentInhoud lo3Reisdocument = lo3ReisdocumentCategorie.getInhoud();
            final Lo3Historie lo3ReisdocumentHistorie = lo3ReisdocumentCategorie.getHistorie();

            if (lo3Reisdocument.getNummerNederlandsReisdocument() != null) {
                final BrpDatum datumInhouding =
                        converteerder.converteerDatum(lo3Reisdocument
                                .getDatumInhoudingVermissingNederlandsReisdocument());

                final BrpDatum datumIngangDocument =
                        converteerder.converteerDatum(lo3Reisdocument.getDatumUitgifteNederlandsReisdocument());
                final BrpDatum datumUitgifte =
                        converteerder.converteerDatum(lo3ReisdocumentHistorie.getIngangsdatumGeldigheid());

                final String nummer = lo3Reisdocument.getNummerNederlandsReisdocument();

                final BrpDatum eindeGeldigheid =
                        lo3Reisdocument.getDatumEindeGeldigheidNederlandsReisdocument().converteerNaarBrpDatum();

                final Integer lengte = converteerder.converteerLengteHouder(lo3Reisdocument.getLengteHouder());
                final BrpReisdocumentRedenOntbreken redenOntbreken =
                        converteerder.converteerLo3AanduidingInhoudingVermissingNederlandReisdocument(lo3Reisdocument
                                .getAanduidingInhoudingVermissingNederlandsReisdocument());

                final MigratieGroep<BrpReisdocumentInhoud> migratieReisDocument =
                        new MigratieGroep<BrpReisdocumentInhoud>(new BrpReisdocumentInhoud(
                                converteerder.converteerLo3ReisdocumentSoort(lo3Reisdocument
                                        .getSoortNederlandsReisdocument()), nummer, datumIngangDocument,
                                datumUitgifte,
                                converteerder.converteerLo3AutoriteitVanAfgifteNederlandsReisdocument(lo3Reisdocument
                                        .getAutoriteitVanAfgifteNederlandsReisdocument()), eindeGeldigheid,
                                datumInhouding, redenOntbreken, lengte), lo3ReisdocumentCategorie.getHistorie(),
                                lo3ReisdocumentCategorie.getDocumentatie(), lo3ReisdocumentCategorie.getLo3Herkomst());
                migratieReisdocumenten.add(migratieReisDocument);
            }

            belemmeringIndicaties.add(new MigratieGroep<BrpBelemmeringVerstrekkingReisdocumentIndicatieInhoud>(
                    new BrpBelemmeringVerstrekkingReisdocumentIndicatieInhoud(converteerder
                            .converteerSignalering(lo3Reisdocument.getSignalering())), lo3ReisdocumentCategorie
                            .getHistorie(), lo3ReisdocumentCategorie.getDocumentatie(), lo3ReisdocumentCategorie
                            .getLo3Herkomst()));

            bezitBuitenlandsReisdocumentInds.add(new MigratieGroep<BrpBezitBuitenlandsReisdocumentIndicatieInhoud>(
                    new BrpBezitBuitenlandsReisdocumentIndicatieInhoud(converteerder
                            .converteerAanduidingBezitBuitenlandsReisdocument(lo3Reisdocument
                                    .getAanduidingBezitBuitenlandsReisdocument())), lo3ReisdocumentCategorie
                            .getHistorie(), lo3ReisdocumentCategorie.getDocumentatie(), lo3ReisdocumentCategorie
                            .getLo3Herkomst()));

            if (!migratieReisdocumenten.isEmpty()) {
                migratiePersoonslijstBuilder.reisdocumentStapel(new MigratieStapel<BrpReisdocumentInhoud>(
                        migratieReisdocumenten));
            }

            if (!ConverteerderUtils.isLijstMetAlleenLegeInhoud(belemmeringIndicaties)) {
                final MigratieStapel<BrpBelemmeringVerstrekkingReisdocumentIndicatieInhoud> stapel =
                        new MigratieStapel<BrpBelemmeringVerstrekkingReisdocumentIndicatieInhoud>(
                                belemmeringIndicaties);
                migratiePersoonslijstBuilder.belemmeringVerstrekkingReisdocumentIndicatieStapel(stapel);
            }
            if (!ConverteerderUtils.isLijstMetAlleenLegeInhoud(bezitBuitenlandsReisdocumentInds)) {
                final MigratieStapel<BrpBezitBuitenlandsReisdocumentIndicatieInhoud> stapel =
                        new MigratieStapel<BrpBezitBuitenlandsReisdocumentIndicatieInhoud>(
                                bezitBuitenlandsReisdocumentInds);
                migratiePersoonslijstBuilder.bezitBuitenlandsReisdocumentIndicatieStapel(stapel);
            }
        }
    }
}
