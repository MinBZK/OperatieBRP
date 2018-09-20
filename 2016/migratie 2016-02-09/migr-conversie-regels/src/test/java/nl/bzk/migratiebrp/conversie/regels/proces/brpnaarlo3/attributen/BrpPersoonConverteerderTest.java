/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen;

import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.act;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.geslacht;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.groep;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.his;
import static nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpStapelHelper.samengesteldnaam;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.regels.AbstractComponentTest;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.BrpBepalenAdellijkeTitel;
import org.junit.Test;

public class BrpPersoonConverteerderTest extends AbstractComponentTest {

    @Inject
    private BrpPersoonConverteerder persoonConverteerder;
    @Inject
    private BrpBepalenAdellijkeTitel bepalenAdellijkeTitel;

    @Test
    public void testConverteerPredikaatMan() {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();

        final Lo3Stapel<Lo3PersoonInhoud> lo3PersoonStapel =
                persoonConverteerder.converteer(createSamengesteldeNaamStapelPredikaatMan(), createGeslachtsaanduidingStapel("M"));

        builder.persoonStapel(lo3PersoonStapel);
        final Lo3Persoonslijst converteerResultaat = bepalenAdellijkeTitel.converteer(builder.build());

        assertNotNull(converteerResultaat.getPersoonStapel());
        assertEquals(1, converteerResultaat.getPersoonStapel().size());
        final Lo3PersoonInhoud inhoud = converteerResultaat.getPersoonStapel().get(0).getInhoud();
        assertEquals(new Lo3AdellijkeTitelPredikaatCode("JH"), inhoud.getAdellijkeTitelPredikaatCode());
    }

    @Test
    public void testConverteerPredikaatVrouw() {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();

        final Lo3Stapel<Lo3PersoonInhoud> lo3PersoonStapel =
                persoonConverteerder.converteer(createSamengesteldeNaamStapelPredikaatVrouw(), createGeslachtsaanduidingStapel("V"));

        builder.persoonStapel(lo3PersoonStapel);
        final Lo3Persoonslijst converteerResultaat = bepalenAdellijkeTitel.converteer(builder.build());

        assertNotNull(converteerResultaat.getPersoonStapel());
        assertEquals(1, converteerResultaat.getPersoonStapel().size());
        final Lo3PersoonInhoud inhoud = converteerResultaat.getPersoonStapel().get(0).getInhoud();
        assertEquals(new Lo3AdellijkeTitelPredikaatCode("JV"), inhoud.getAdellijkeTitelPredikaatCode());
    }

    @Test
    public void testConverteerAdellijkeTitelMan() {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();

        final Lo3Stapel<Lo3PersoonInhoud> lo3PersoonStapel =
                persoonConverteerder.converteer(createSamengesteldeNaamStapelAdellijkeTitelMan(), createGeslachtsaanduidingStapel("M"));

        builder.persoonStapel(lo3PersoonStapel);
        final Lo3Persoonslijst converteerResultaat = bepalenAdellijkeTitel.converteer(builder.build());

        assertNotNull(converteerResultaat.getPersoonStapel());
        assertEquals(1, converteerResultaat.getPersoonStapel().size());
        final Lo3PersoonInhoud inhoud = converteerResultaat.getPersoonStapel().get(0).getInhoud();
        assertEquals(new Lo3AdellijkeTitelPredikaatCode("B"), inhoud.getAdellijkeTitelPredikaatCode());
    }

    @Test
    public void testConverteerAdellijkeTitelVrouw() {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder();

        final Lo3Stapel<Lo3PersoonInhoud> lo3PersoonStapel =
                persoonConverteerder.converteer(createSamengesteldeNaamStapelAdellijkeTitelVrouw(), createGeslachtsaanduidingStapel("V"));

        builder.persoonStapel(lo3PersoonStapel);
        final Lo3Persoonslijst converteerResultaat = bepalenAdellijkeTitel.converteer(builder.build());

        assertNotNull(converteerResultaat.getPersoonStapel());
        assertEquals(1, converteerResultaat.getPersoonStapel().size());
        final Lo3PersoonInhoud inhoud = converteerResultaat.getPersoonStapel().get(0).getInhoud();
        assertEquals(new Lo3AdellijkeTitelPredikaatCode("BS"), inhoud.getAdellijkeTitelPredikaatCode());
    }

    private BrpStapel<BrpSamengesteldeNaamInhoud> createSamengesteldeNaamStapelPredikaatMan() {
        return new BrpStapel<>(Arrays.asList(groep(samengesteldnaam("Jan", "Klaasen", "J", null), his(20000101), act(1, 20000102))));
    }

    private BrpStapel<BrpSamengesteldeNaamInhoud> createSamengesteldeNaamStapelPredikaatVrouw() {
        return new BrpStapel<>(Arrays.asList(groep(samengesteldnaam("Marie", "Pieters", "J", null), his(20000101), act(1, 20000102))));
    }

    private BrpStapel<BrpSamengesteldeNaamInhoud> createSamengesteldeNaamStapelAdellijkeTitelMan() {
        return new BrpStapel<>(Arrays.asList(groep(samengesteldnaam("Jan", "Klaasen", null, "B"), his(20000101), act(1, 20000102))));
    }

    private BrpStapel<BrpSamengesteldeNaamInhoud> createSamengesteldeNaamStapelAdellijkeTitelVrouw() {
        return new BrpStapel<>(Arrays.asList(groep(samengesteldnaam("Marie", "Pieters", null, "B"), his(20000101), act(1, 20000102))));
    }

    private BrpStapel<BrpGeslachtsaanduidingInhoud> createGeslachtsaanduidingStapel(final String geslacht) {
        return new BrpStapel<>(Arrays.asList(groep(geslacht(geslacht), his(20000101), act(1, 20000102))));
    }
}
