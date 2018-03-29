/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis;

import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.sync.generated.FoutredenType;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenis;
import nl.bzk.migratiebrp.conversie.model.brp.toevalligegebeurtenis.BrpToevalligeGebeurtenisPersoon;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle.ControleException;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle.GeboorteVergelijker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle.GeslachtVergelijker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle.IdentificatieNummerVergelijker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.toevalligegebeurtenis.controle.SamengesteldeNaamVergelijker;
import org.springframework.stereotype.Component;

/**
 * Helper klasse voor het inhoudelijk controleren van een persoon tegen de persoon uit het verzoekbericht.
 */
@Component
public final class PersoonControle implements ToevalligeGebeurtenisControle {

    private final IdentificatieNummerVergelijker identificatieNummerVergelijker;
    private final SamengesteldeNaamVergelijker samengesteldeNaamVergelijker;
    private final GeboorteVergelijker geboorteVergelijker;
    private final GeslachtVergelijker geslachtVergelijker;

    /**
     * Constructor.
     * @param identificatieNummerVergelijker vergelijker voor identificatie nummers
     * @param samengesteldeNaamVergelijker vergelijker voor samengestelde naam
     * @param geboorteVergelijker vergelijker voor geboorte
     * @param geslachtVergelijker vergelijker voor geslacht
     */
    @Inject
    public PersoonControle(final IdentificatieNummerVergelijker identificatieNummerVergelijker,
                           final SamengesteldeNaamVergelijker samengesteldeNaamVergelijker,
                           final GeboorteVergelijker geboorteVergelijker,
                           final GeslachtVergelijker geslachtVergelijker) {
        this.identificatieNummerVergelijker = identificatieNummerVergelijker;
        this.samengesteldeNaamVergelijker = samengesteldeNaamVergelijker;
        this.geboorteVergelijker = geboorteVergelijker;
        this.geslachtVergelijker = geslachtVergelijker;
    }
    @Override
    public void controleer(final BrpPersoonslijst brpPersoon, final BrpToevalligeGebeurtenis brpToevalligeGebeurtenis) throws ControleException {
        boolean result = false;

        final BrpToevalligeGebeurtenisPersoon persoon = brpToevalligeGebeurtenis.getPersoon();
        if (brpPersoon.getIdentificatienummerStapel().bevatActueel()) {
            final BrpIdentificatienummersInhoud inhoud = brpPersoon.getIdentificatienummerStapel().getActueel().getInhoud();
            result = identificatieNummerVergelijker.vergelijk(persoon, inhoud);
        }

        if (brpPersoon.getGeboorteStapel().bevatActueel()) {
            final BrpGeboorteInhoud geboorte = brpPersoon.getGeboorteStapel().getActueel().getInhoud();
            result &= geboorteVergelijker.vergelijk(persoon, geboorte);
        }

        if (brpPersoon.getSamengesteldeNaamStapel().bevatActueel()) {
            final BrpSamengesteldeNaamInhoud samengesteldeNaam = brpPersoon.getSamengesteldeNaamStapel().getActueel().getInhoud();
            result &= samengesteldeNaamVergelijker.vergelijk(persoon, samengesteldeNaam);
        }

        if (brpPersoon.getGeslachtsaanduidingStapel().bevatActueel()) {
            final BrpGeslachtsaanduidingInhoud geslacht = brpPersoon.getGeslachtsaanduidingStapel().getActueel().getInhoud();
            result &= geslachtVergelijker.vergelijk(persoon, geslacht);
        }

        if (!result) {
            throw new ControleException(FoutredenType.N);
        }
    }
}
