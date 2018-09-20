/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3;

import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.act;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.geboorte;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.his;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.inschrijving;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.nationaliteit;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpHistorie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpInschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.regels.AbstractComponentTest;

/**
 * Abstracte test basis class met utility methodes voor het opbouwen van een Brp persoonslijst.
 */
public abstract class AbstractBrpConversieTest extends AbstractComponentTest {
    protected void vulNationaliteit(
        final BrpPersoonslijstBuilder builder,
        final String natCode,
        final String verkrijgCode,
        final String verliesCode)
    {
        vulNationaliteit(builder, natCode, verkrijgCode, verliesCode, his(20100101, 20100102000000L));
    }

    protected void vulNationaliteit(
        final BrpPersoonslijstBuilder builder,
        final String natCode,
        final String verkrijgCode,
        final String verliesCode,
        final BrpHistorie historie)
    {
        final List<BrpGroep<BrpNationaliteitInhoud>> natGroepen = new ArrayList<>();
        final BrpGroep<BrpNationaliteitInhoud> natGroep =
                new BrpGroep<>(nationaliteit(natCode, verkrijgCode, verliesCode), historie, act(1, 20100102), null, null);
        natGroepen.add(natGroep);
        final BrpStapel<BrpNationaliteitInhoud> nationaliteitStapel = new BrpStapel<>(natGroepen);
        builder.nationaliteitStapel(nationaliteitStapel);
    }

    protected void vulVerplichteStapels(final BrpPersoonslijstBuilder builder) {
        addGeboorteStapel(builder);
        addInschrijving(builder);
    }

    private void addInschrijving(final BrpPersoonslijstBuilder builder) {
        final BrpInschrijvingInhoud inschrijving = inschrijving(19800101, 1, 19800103);
        final List<BrpGroep<BrpInschrijvingInhoud>> groepen = new ArrayList<>();
        final BrpGroep<BrpInschrijvingInhoud> groep =
                new BrpGroep<>(inschrijving, his(19800101, 19800102000000L), act(1, 19800102), null, null);
        groepen.add(groep);
        final BrpStapel<BrpInschrijvingInhoud> inschrijvingStapel = new BrpStapel<>(groepen);
        builder.inschrijvingStapel(inschrijvingStapel);
    }

    private void addGeboorteStapel(final BrpPersoonslijstBuilder builder) {
        final List<BrpGroep<BrpGeboorteInhoud>> groepen = new ArrayList<>();
        final BrpGeboorteInhoud geboorte = geboorte(19800101, "042");
        final BrpGroep<BrpGeboorteInhoud> groep = new BrpGroep<>(geboorte, his(19800101, 19800102000000L), act(1, 19800102), null, null);
        groepen.add(groep);
        final BrpStapel<BrpGeboorteInhoud> geboorteStapel = new BrpStapel<>(groepen);
        builder.geboorteStapel(geboorteStapel);
    }
}
