/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.persoon;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.levering.lo3.conversie.ConversieCache;
import nl.bzk.brp.levering.lo3.conversie.Converteerder;
import nl.bzk.brp.levering.lo3.conversie.IdentificatienummerMutatie;
import nl.bzk.brp.levering.lo3.mapper.PersoonslijstMapper;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3PersoonslijstFormatter;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerBrpNaarLo3Service;
import org.springframework.stereotype.Component;

/**
 * Converteert een persoon.
 */
@Component("lo3_persoonConverteerder")
public final class PersoonConverteerder implements Converteerder {

    private final ConverteerBrpNaarLo3Service converteerBrpNaarLo3Service;
    private final PersoonslijstMapper persoonslijstMapper;

    private final Lo3PersoonslijstFormatter lo3PersoonslijstFormatter = new Lo3PersoonslijstFormatter();

    /**
     * Constructor.
     * @param converteerBrpNaarLo3Service converteer service
     * @param persoonslijstMapper persoonslijst mapper
     */
    @Inject
    public PersoonConverteerder(final ConverteerBrpNaarLo3Service converteerBrpNaarLo3Service,
                                final PersoonslijstMapper persoonslijstMapper) {
        this.converteerBrpNaarLo3Service = converteerBrpNaarLo3Service;
        this.persoonslijstMapper = persoonslijstMapper;
    }

    @Override
    public List<Lo3CategorieWaarde> converteer(
            // Input
            final Persoonslijst persoon,
            final List<Stapel> opgehaaldeIstStapels,
            final AdministratieveHandeling administratieveHandeling,
            final IdentificatienummerMutatie identificatienummerMutatieResultaat,
            // Variabelen
            final ConversieCache conversieCache) {
        List<Lo3CategorieWaarde> resultaat = conversieCache.getVolledigCategorien();

        if (resultaat == null) {
            // Converteer de lijst naar het gewenste type Set.
            final Set<Stapel> istStapels;
            if (opgehaaldeIstStapels != null && !opgehaaldeIstStapels.isEmpty()) {
                istStapels = new LinkedHashSet<>(opgehaaldeIstStapels);
            } else {
                istStapels = new LinkedHashSet<>();
            }

            // Map PersoonHisVolledig naar BrpPersoonslijst
            final BrpPersoonslijst brpPersoonslijst = persoonslijstMapper.map(persoon, istStapels);

            // Converteer
            final Lo3Persoonslijst lo3Persoonslijst = converteerBrpNaarLo3Service.converteerBrpPersoonslijst(brpPersoonslijst);

            // Geef resultaat
            resultaat = lo3PersoonslijstFormatter.format(lo3Persoonslijst);
            conversieCache.setVolledigCategorien(resultaat);
        }

        return resultaat;
    }
}
