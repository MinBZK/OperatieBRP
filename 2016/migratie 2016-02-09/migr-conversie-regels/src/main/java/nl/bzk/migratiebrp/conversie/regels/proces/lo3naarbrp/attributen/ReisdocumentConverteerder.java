/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.migratiebrp.conversie.model.Definitie;
import nl.bzk.migratiebrp.conversie.model.Definities;
import nl.bzk.migratiebrp.conversie.model.Requirement;
import nl.bzk.migratiebrp.conversie.model.Requirements;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingInhoudingOfVermissingReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpReisdocumentAutoriteitVanAfgifteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortNederlandsReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3ReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingInhoudingVermissingNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AutoriteitVanAfgifteNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3.Foutmelding;

import org.springframework.stereotype.Component;

/**
 * Deze class bevat de conversie logica om de LO3 Categorie Reisdocument te converteren naar BRP.
 */
@Component
@Requirement(Requirements.CCA12)
public class ReisdocumentConverteerder extends AbstractConverteerder {

    @Inject
    private Lo3AttribuutConverteerder converteerder;

    /**
     * Converteert de LO3 Reisdocument categorie naar het BRP model en vult hiermee de tussenPersoonslijstBuilder aan.
     * 
     * @param lo3ReisdocumentStapels
     *            de overlijden stapel, mag leeg zijn, maar niet null
     * @param tussenPersoonslijstBuilder
     *            de migratie builder
     */
    @Definitie({Definities.DEF063, Definities.DEF065 })
    public final void converteer(
        final List<Lo3Stapel<Lo3ReisdocumentInhoud>> lo3ReisdocumentStapels,
        final TussenPersoonslijstBuilder tussenPersoonslijstBuilder)
    {
        for (final Lo3Stapel<Lo3ReisdocumentInhoud> lo3ReisdocumentStapel : lo3ReisdocumentStapels) {
            final List<TussenGroep<BrpReisdocumentInhoud>> tussenReisdocumenten = new ArrayList<>();
            final List<TussenGroep<BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud>> tussenSignaleringGroep = new ArrayList<>();

            final Lo3Categorie<Lo3ReisdocumentInhoud> lo3Voorkomen = lo3ReisdocumentStapel.getLaatsteElement();
            final Lo3ReisdocumentInhoud lo3Inhoud = lo3Voorkomen.getInhoud();
            final Lo3Historie lo3Historie = lo3Voorkomen.getHistorie();
            final Lo3Herkomst lo3Herkomst = lo3Voorkomen.getLo3Herkomst();
            final Lo3Documentatie lo3Documentatie = lo3Voorkomen.getDocumentatie();

            // DEF063
            if (lo3Inhoud.isNederlandsReisdocument()) {
                verwerkNederlandsReisdocument(tussenReisdocumenten, lo3Inhoud, lo3Historie, lo3Herkomst, lo3Documentatie);
            }

            // DEF065
            if (lo3Inhoud.isBelemmeringVerstrekking()) {
                verwerkBelemmeringVerstrekking(tussenSignaleringGroep, lo3Inhoud, lo3Historie, lo3Herkomst, lo3Documentatie);
            }

            boolean reisdocumentToegevoegd = false;

            if (!tussenReisdocumenten.isEmpty()) {
                tussenPersoonslijstBuilder.reisdocumentStapel(new TussenStapel<>(tussenReisdocumenten));
                reisdocumentToegevoegd = true;
            }

            if (!getUtils().isLijstMetAlleenLegeInhoud(tussenSignaleringGroep)) {
                final TussenStapel<BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud> stapel = new TussenStapel<>(tussenSignaleringGroep);
                tussenPersoonslijstBuilder.signaleringMetBetrekkingTotVerstrekkenReisdocumentStapel(stapel);
                reisdocumentToegevoegd = true;
            }

            if (!reisdocumentToegevoegd) {
                Foutmelding.logMeldingFoutInfo(lo3Herkomst, SoortMeldingCode.BIJZ_CONV_LB027, null);
            }
        }
    }

    private void verwerkNederlandsReisdocument(
        final List<TussenGroep<BrpReisdocumentInhoud>> tussenReisdocumenten,
        final Lo3ReisdocumentInhoud lo3Inhoud,
        final Lo3Historie lo3Historie,
        final Lo3Herkomst lo3Herkomst,
        final Lo3Documentatie lo3Documentatie)
    {
        final BrpSoortNederlandsReisdocumentCode soortNederlandsReisdocumentCode;
        soortNederlandsReisdocumentCode = converteerder.converteerLo3ReisdocumentSoort(lo3Inhoud.getSoortNederlandsReisdocument());
        final BrpString nummer = converteerder.converteerString(lo3Inhoud.getNummerNederlandsReisdocument());
        final BrpDatum datumUitgifte = converteerder.converteerDatum(lo3Inhoud.getDatumUitgifteNederlandsReisdocument());
        final Lo3AutoriteitVanAfgifteNederlandsReisdocument lo3AutoriteitVanAfgifte = lo3Inhoud.getAutoriteitVanAfgifteNederlandsReisdocument();
        final BrpReisdocumentAutoriteitVanAfgifteCode autoriteitVanAfgifte;
        autoriteitVanAfgifte = converteerder.converteerLo3AutoriteitVanAfgifteNederlandsReisdocument(lo3AutoriteitVanAfgifte);
        final BrpDatum datumEindeDocument = BrpDatum.fromLo3Datum(lo3Inhoud.getDatumEindeGeldigheidNederlandsReisdocument());
        final BrpDatum datumInhouding = converteerder.converteerDatum(lo3Inhoud.getDatumInhoudingVermissingNederlandsReisdocument());
        final Lo3AanduidingInhoudingVermissingNederlandsReisdocument lo3AanduidingInhoudingVermissing;
        lo3AanduidingInhoudingVermissing = lo3Inhoud.getAanduidingInhoudingVermissingNederlandsReisdocument();
        final BrpAanduidingInhoudingOfVermissingReisdocumentCode aanduidingInhoudingOfVermissing;
        aanduidingInhoudingOfVermissing = converteerder.converteerLo3AanduidingInhoudingVermissingNederlandReisdocument(lo3AanduidingInhoudingVermissing);
        final BrpDatum datumIngangDocument = converteerder.converteerDatum(lo3Historie.getIngangsdatumGeldigheid());

        final TussenGroep<BrpReisdocumentInhoud> migratieReisDocument =
                new TussenGroep<>(
                    new BrpReisdocumentInhoud(
                        soortNederlandsReisdocumentCode,
                        nummer,
                        datumIngangDocument,
                        datumUitgifte,
                        autoriteitVanAfgifte,
                        datumEindeDocument,
                        datumInhouding,
                        aanduidingInhoudingOfVermissing),
                    lo3Historie,
                    lo3Documentatie,
                    lo3Herkomst);
        tussenReisdocumenten.add(migratieReisDocument);
    }

    private void verwerkBelemmeringVerstrekking(
        final List<TussenGroep<BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud>> tussenSignaleringGroep,
        final Lo3ReisdocumentInhoud lo3Inhoud,
        final Lo3Historie lo3Historie,
        final Lo3Herkomst lo3Herkomst,
        final Lo3Documentatie lo3Documentatie)
    {
        if (!lo3Historie.getDatumVanOpneming().equals(lo3Historie.getIngangsdatumGeldigheid())) {
            Foutmelding.logMeldingFoutInfo(lo3Herkomst, SoortMeldingCode.BIJZ_CONV_LB026, null);
        }
        final BrpBoolean signalering = converteerder.converteerSignalering(lo3Inhoud.getSignalering());
        tussenSignaleringGroep.add(new TussenGroep<>(
                new BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud(signalering, null, null),
            lo3Historie,
            lo3Documentatie,
            lo3Herkomst));
    }
}
