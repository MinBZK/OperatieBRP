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
import static org.mockito.Mockito.when;

import java.util.Collections;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPredicaatCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsaanduidingInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.BrpBepalenAdellijkeTitel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BrpPersoonConverteerderTest {

    private BrpPersoonConverteerder persoonConverteerder;
    @Mock
    private BrpAttribuutConverteerder attribuutConverteerder;
    private BrpBepalenAdellijkeTitel bepalenAdellijkeTitel = new BrpBepalenAdellijkeTitel();

    @Before
    public void setUp() {
        persoonConverteerder = new BrpPersoonConverteerder(attribuutConverteerder);
    }

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
        final String predicaat = "J";
        final String voornamen = "Jan";
        final String geslachtsnaam = "Klaasen";
        when(attribuutConverteerder.converteerAdellijkeTitelPredikaatCode(null, new BrpPredicaatCode(predicaat)))
                .thenReturn(new Lo3AdellijkeTitelPredikaatCode("JH"));
        when(attribuutConverteerder.converteerString(new BrpString(voornamen))).thenReturn(new Lo3String(voornamen));
        when(attribuutConverteerder.converteerString(new BrpString(geslachtsnaam))).thenReturn(new Lo3String(geslachtsnaam));

        return new BrpStapel<>(Collections.singletonList(groep(samengesteldnaam(voornamen, geslachtsnaam, predicaat, null), his(20000101), act(1, 20000102))));
    }

    private BrpStapel<BrpSamengesteldeNaamInhoud> createSamengesteldeNaamStapelPredikaatVrouw() {
        final String predicaat = "J";
        final String voornamen = "Marie";
        final String geslachtsnaam = "Pieters";
        when(attribuutConverteerder.converteerAdellijkeTitelPredikaatCode(null, new BrpPredicaatCode(predicaat)))
                .thenReturn(new Lo3AdellijkeTitelPredikaatCode("JV"));
        when(attribuutConverteerder.converteerString(new BrpString(voornamen))).thenReturn(new Lo3String(voornamen));
        when(attribuutConverteerder.converteerString(new BrpString(geslachtsnaam))).thenReturn(new Lo3String(geslachtsnaam));
        return new BrpStapel<>(Collections.singletonList(groep(samengesteldnaam(voornamen, geslachtsnaam, predicaat, null), his(20000101), act(1, 20000102))));
    }

    private BrpStapel<BrpSamengesteldeNaamInhoud> createSamengesteldeNaamStapelAdellijkeTitelMan() {
        final String adellijkeTitel = "B";
        final String voornamen = "Jan";
        final String geslachtsnaam = "Klaasen";
        when(attribuutConverteerder.converteerAdellijkeTitelPredikaatCode(new BrpAdellijkeTitelCode(adellijkeTitel), null))
                .thenReturn(new Lo3AdellijkeTitelPredikaatCode(adellijkeTitel));
        when(attribuutConverteerder.converteerString(new BrpString(voornamen))).thenReturn(new Lo3String(voornamen));
        when(attribuutConverteerder.converteerString(new BrpString(geslachtsnaam))).thenReturn(new Lo3String(geslachtsnaam));
        return new BrpStapel<>(
                Collections.singletonList(groep(samengesteldnaam(voornamen, geslachtsnaam, null, adellijkeTitel), his(20000101), act(1, 20000102))));
    }

    private BrpStapel<BrpSamengesteldeNaamInhoud> createSamengesteldeNaamStapelAdellijkeTitelVrouw() {
        final String adellijkeTitel = "B";
        final String voornamen = "Marie";
        final String geslachtsnaam = "Pieters";
        when(attribuutConverteerder.converteerAdellijkeTitelPredikaatCode(new BrpAdellijkeTitelCode(adellijkeTitel), null))
                .thenReturn(new Lo3AdellijkeTitelPredikaatCode("BS"));
        when(attribuutConverteerder.converteerString(new BrpString(voornamen))).thenReturn(new Lo3String(voornamen));
        when(attribuutConverteerder.converteerString(new BrpString(geslachtsnaam))).thenReturn(new Lo3String(geslachtsnaam));
        return new BrpStapel<>(
                Collections.singletonList(groep(samengesteldnaam(voornamen, geslachtsnaam, null, adellijkeTitel), his(20000101), act(1, 20000102))));
    }

    private BrpStapel<BrpGeslachtsaanduidingInhoud> createGeslachtsaanduidingStapel(final String geslacht) {
        return new BrpStapel<>(Collections.singletonList(groep(geslacht(geslacht), his(20000101), act(1, 20000102))));
    }
}
