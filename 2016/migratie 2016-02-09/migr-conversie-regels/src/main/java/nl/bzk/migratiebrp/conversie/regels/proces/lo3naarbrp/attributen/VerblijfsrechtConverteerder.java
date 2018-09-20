/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.Requirement;
import nl.bzk.migratiebrp.conversie.model.Requirements;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerblijfsrechtInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfstitelInhoud;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenGroep;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.tussen.TussenStapel;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3.Foutmelding;
import org.springframework.stereotype.Component;

/**
 * Deze class implementeert de logica om een LO3 verblijfstitel te converteren naar een BRP verblijfstitel.
 *
 */
@Component
@Requirement(Requirements.CCA10)
public class VerblijfsrechtConverteerder {

    @Inject
    private Lo3AttribuutConverteerder converteerder;

    /**
     * Converteert de LO3 Verblijfstitel categorie naar het BRP model en vult hiermee de tussenPersoonslijstBuilder aan.
     *
     * @param lo3VerblijfstitelStapel
     *            de LO3 verblijfstitel stapel, mag null zijn
     * @param tussenPersoonslijstBuilder
     *            de migratie builder
     */
    public final void converteer(
        final Lo3Stapel<Lo3VerblijfstitelInhoud> lo3VerblijfstitelStapel,
        final TussenPersoonslijstBuilder tussenPersoonslijstBuilder)
    {
        if (lo3VerblijfstitelStapel == null) {
            return;
        }
        final List<TussenGroep<BrpVerblijfsrechtInhoud>> verblijfsrechtGroepen = new ArrayList<>();
        for (final Lo3Categorie<Lo3VerblijfstitelInhoud> lo3VerblijfstitelCategorie : lo3VerblijfstitelStapel) {
            if (lo3VerblijfstitelCategorie.getLo3Herkomst().isLo3ActueelVoorkomen() && !lo3VerblijfstitelCategorie.getInhoud().isLeeg()) {
                final Lo3VerblijfstitelInhoud lo3Verblijfstitel = lo3VerblijfstitelCategorie.getInhoud();
                final Lo3Historie lo3Historie = lo3VerblijfstitelCategorie.getHistorie();
                verblijfsrechtGroepen.add(
                    new TussenGroep<>(
                        new BrpVerblijfsrechtInhoud(
                            converteerder.converteerLo3AanduidingVerblijfstitelCode(lo3Verblijfstitel.getAanduidingVerblijfstitelCode()),
                            converteerder.converteerDatum(lo3Historie.getIngangsdatumGeldigheid()),
                            converteerder.converteerDatum(lo3Verblijfstitel.getDatumEindeVerblijfstitel()),
                            converteerder.converteerDatum(lo3Verblijfstitel.getDatumAanvangVerblijfstitel())),
                        lo3VerblijfstitelCategorie.getHistorie(),
                        lo3VerblijfstitelCategorie.getDocumentatie(),
                        lo3VerblijfstitelCategorie.getLo3Herkomst()));
                final TussenStapel<BrpVerblijfsrechtInhoud> verblijfsrechtStapel = new TussenStapel<>(verblijfsrechtGroepen);
                tussenPersoonslijstBuilder.verblijfsrechtStapel(verblijfsrechtStapel);
            } else {
                Foutmelding.logMeldingFoutInfo(lo3VerblijfstitelCategorie.getLo3Herkomst(), SoortMeldingCode.BIJZ_CONV_LB027, null);
            }
        }
    }
}
