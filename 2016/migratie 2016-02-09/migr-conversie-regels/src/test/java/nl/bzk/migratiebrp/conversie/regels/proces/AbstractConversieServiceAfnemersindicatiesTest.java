/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAfnemersindicaties;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Afnemersindicatie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3AfnemersindicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.conversie.regels.AbstractComponentTest;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import org.junit.After;
import org.junit.Before;

public abstract class AbstractConversieServiceAfnemersindicatiesTest extends AbstractComponentTest {

    @Inject
    private ConverteerLo3NaarBrpService conversieService;

    @Before
    public void setUp() {
        Logging.initContext();
    }

    @After
    public void tearDown() {
        Logging.destroyContext();
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    protected BrpAfnemersindicaties test(final String ident, final Lo3Afnemersindicatie lo3) {
        return conversieService.converteerLo3Afnemersindicaties(lo3);
    }

    protected Lo3Categorie<Lo3AfnemersindicatieInhoud> maakLo3Categorie(
        final Integer afnemersindicatie,
        final int datumIngang,
        final int stapel,
        final int voorkomen)
    {
        final Lo3AfnemersindicatieInhoud inhoud = new Lo3AfnemersindicatieInhoud(afnemersindicatie);
        final Lo3Historie historie = Lo3StapelHelper.lo3His(null, datumIngang, datumIngang);
        return Lo3StapelHelper.lo3Cat(inhoud, null, historie, Lo3StapelHelper.lo3Her(14, stapel, voorkomen));
    }

}
