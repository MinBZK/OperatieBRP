/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.verschilanalyse.analyse;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.List;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.bzk.migratiebrp.init.logging.model.VerschilType;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.VerschilAnalyseRegel;
import nl.bzk.migratiebrp.init.logging.verschilanalyse.service.impl.TestUtil;
import org.junit.Test;

/**
 * Unittest voor @{@link VerschilBepaler}.
 */
public class VerschilBepalerTest {

    private final VerschilBepaler verschilBepaler = new VerschilBepaler();

    @Test
    public void testActueelOnjuist() throws Exception {
        final Lo3PersoonslijstBuilder plBuilder = new Lo3PersoonslijstBuilder();
        Lo3Categorie<Lo3PersoonInhoud> onjuistVoorkomen = maakVoorkomenOnjuist(TestUtil.maakVoorkomen());
        plBuilder.persoonStapel(new Lo3Stapel<>(Collections.singletonList(onjuistVoorkomen)));
        final Lo3Persoonslijst pl = plBuilder.build();
        final List<VergelijkResultaat> verschillen = verschilBepaler.controleerActueelJuist(pl);
        assertEquals(1, verschillen.size());
        final VergelijkResultaat vergelijkResultaat = verschillen.get(0);
        final List<VerschilAnalyseRegel> regels = vergelijkResultaat.getRegels();
        assertEquals(1, regels.size());
        final VerschilAnalyseRegel regel = regels.get(0);
        assertEquals(VerschilType.ACTUAL_ONJUIST,regel.getType());
    }

    @Test
    public void testActueelJuist() throws Exception {
        final Lo3PersoonslijstBuilder plBuilder = new Lo3PersoonslijstBuilder();
        plBuilder.persoonStapel(new Lo3Stapel<>(Collections.singletonList(TestUtil.maakVoorkomen())));
        final Lo3Persoonslijst pl = plBuilder.build();
        final List<VergelijkResultaat> verschillen = verschilBepaler.controleerActueelJuist(pl);
        assertEquals(0, verschillen.size());
    }


    private Lo3Categorie<Lo3PersoonInhoud> maakVoorkomenOnjuist(final Lo3Categorie<Lo3PersoonInhoud> voorkomen) {
        final Lo3Historie historie = voorkomen.getHistorie();
        final Lo3Historie
                onjuisteHistorie =
                new Lo3Historie(new Lo3IndicatieOnjuist("O"), historie.getIngangsdatumGeldigheid(), historie.getDatumVanOpneming());
        return new Lo3Categorie<>(voorkomen.getInhoud(), voorkomen.getDocumentatie(), voorkomen.getOnderzoek(), onjuisteHistorie,
                voorkomen.getLo3Herkomst());
    }
}
