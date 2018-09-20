/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIstRelatieGroepInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AdellijkeTitel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Geslachtsaanduiding;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Predicaat;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortAdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortDocument;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortPersoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortRelatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Stapel;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.StapelVoorkomen;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("synchronisatie-brp-mapper-beans.xml")
public class BrpIstOuder2MapperTest {
    private static final AdministratieveHandeling ADMINISTRATIEVE_HANDELING = new AdministratieveHandeling(
        new Partij("Bierum", 8),
        SoortAdministratieveHandeling.GBA_INITIELE_VULLING);
    private static final String DOC_NAAM = "doc_naam";
    private static final String DOC_OMSCHRIJVING = "doc_omschrijving";

    @Inject
    private BrpIstOuder2Mapper mapper;

    @Test
    public void testMap() {
        final Set<Stapel> stapels = new LinkedHashSet<>();
        final Stapel stapel = new Stapel(new Persoon(SoortPersoon.INGESCHREVENE), "03", 0);
        final StapelVoorkomen stapelVoorkomen1 = new StapelVoorkomen(stapel, 0, ADMINISTRATIEVE_HANDELING);
        final StapelVoorkomen stapelVoorkomen2 = new StapelVoorkomen(stapel, 1, ADMINISTRATIEVE_HANDELING);

        stapelVoorkomen1.setSoortDocument(new SoortDocument(DOC_NAAM, DOC_OMSCHRIJVING));
        stapelVoorkomen1.setPredicaat(Predicaat.J);
        stapelVoorkomen1.setGeslachtsaanduidingBijAdellijkeTitelOfPredikaat(Geslachtsaanduiding.VROUW);
        stapelVoorkomen1.setAdellijkeTitel(AdellijkeTitel.H);
        stapelVoorkomen1.setSoortRelatie(SoortRelatie.HUWELIJK);

        stapelVoorkomen2.setSoortDocument(new SoortDocument(DOC_NAAM, DOC_OMSCHRIJVING));
        stapelVoorkomen2.setPredicaat(Predicaat.J);
        stapelVoorkomen2.setGeslachtsaanduidingBijAdellijkeTitelOfPredikaat(Geslachtsaanduiding.VROUW);
        stapelVoorkomen2.setAdellijkeTitel(AdellijkeTitel.H);
        stapelVoorkomen2.setSoortRelatie(SoortRelatie.HUWELIJK);

        stapel.addStapelVoorkomen(stapelVoorkomen1);
        stapel.addStapelVoorkomen(stapelVoorkomen2);
        stapels.add(stapel);
        final BrpStapel<BrpIstRelatieGroepInhoud> result = mapper.map(stapels);
        assertNotNull(result);
        assertEquals(2, result.size());
        final BrpGroep<BrpIstRelatieGroepInhoud> brpGroep1 = result.get(0);
        final BrpGroep<BrpIstRelatieGroepInhoud> brpGroep2 = result.get(1);
        final BrpIstRelatieGroepInhoud inhoud1 = brpGroep1.getInhoud();
        final BrpIstRelatieGroepInhoud inhoud2 = brpGroep2.getInhoud();

        // groep 1

        // wel gemapped want standaard groep
        assertEquals(DOC_NAAM, inhoud1.getStandaardGegevens().getSoortDocument().getWaarde());
        // wel gemapped want gerelateerde groep
        assertNotNull(inhoud1.getPredicaatCode());
        assertNotNull(inhoud1.getAdellijkeTitelCode());
        assertEquals(Predicaat.J.toString(), inhoud1.getPredicaatCode().getWaarde());
        assertEquals(AdellijkeTitel.H.toString(), inhoud1.getAdellijkeTitelCode().getWaarde());
        assertEquals(Geslachtsaanduiding.VROUW.getCode(), inhoud1.getPredicaatCode().getGeslachtsaanduiding().getWaarde());
        assertEquals(Geslachtsaanduiding.VROUW.getCode(), inhoud1.getAdellijkeTitelCode().getGeslachtsaanduiding().getWaarde());

        // groep 2

        // wel gemapped want standaard groep
        assertEquals(DOC_NAAM, inhoud2.getStandaardGegevens().getSoortDocument().getWaarde());
        // wel gemapped want gerelateerde groep
        assertNotNull(inhoud2.getPredicaatCode());
        assertNotNull(inhoud2.getAdellijkeTitelCode());
        assertEquals(Predicaat.J.toString(), inhoud2.getPredicaatCode().getWaarde());
        assertEquals(AdellijkeTitel.H.toString(), inhoud2.getAdellijkeTitelCode().getWaarde());
        assertEquals(Geslachtsaanduiding.VROUW.getCode(), inhoud2.getPredicaatCode().getGeslachtsaanduiding().getWaarde());
        assertEquals(Geslachtsaanduiding.VROUW.getCode(), inhoud2.getAdellijkeTitelCode().getGeslachtsaanduiding().getWaarde());
    }

    @Test
    public void testMapVerkeerdeStapelType() {
        final Set<Stapel> stapels = new LinkedHashSet<>();
        final Stapel stapel = new Stapel(new Persoon(SoortPersoon.INGESCHREVENE), "01", 0);
        final StapelVoorkomen stapelVoorkomen = new StapelVoorkomen(stapel, 0, ADMINISTRATIEVE_HANDELING);
        stapel.addStapelVoorkomen(stapelVoorkomen);
        stapels.add(stapel);
        assertNull(mapper.map(stapels));
    }

    @Test
    public void testMapJuisteStapelType() {
        final Set<Stapel> stapels = new LinkedHashSet<>();
        final Stapel stapel = new Stapel(new Persoon(SoortPersoon.INGESCHREVENE), "03", 0);
        final StapelVoorkomen stapelVoorkomen = new StapelVoorkomen(stapel, 0, ADMINISTRATIEVE_HANDELING);
        stapel.addStapelVoorkomen(stapelVoorkomen);
        stapels.add(stapel);
        assertNotNull(mapper.map(stapels));
    }

    @Test(expected = NullPointerException.class)
    public void testMapNull() {
        mapper.map(null);
    }

    @Test
    public void testMapEmpty() {
        assertNull(mapper.map(Collections.<Stapel>emptySet()));
    }
}
