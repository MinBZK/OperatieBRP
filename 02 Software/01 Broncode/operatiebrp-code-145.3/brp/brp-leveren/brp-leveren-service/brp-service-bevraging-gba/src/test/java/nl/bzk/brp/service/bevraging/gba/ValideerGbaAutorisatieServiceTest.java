/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.gba;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.google.common.collect.Iterables;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.service.bevraging.gba.generiek.ValideerGbaAutorisatieService;
import nl.bzk.brp.service.bevraging.gba.generiek.ValideerGbaAutorisatieServiceImpl;
import nl.bzk.brp.service.bevraging.gba.generiek.ZoekPersoonGbaVerzoek;
import nl.bzk.brp.service.bevraging.gba.generiek.ZoekPersoonGeneriekGbaVerzoek;
import org.junit.Test;

public class ValideerGbaAutorisatieServiceTest {

    private final ValideerGbaAutorisatieService subject = new ValideerGbaAutorisatieServiceImpl();
    private static final String BSN = "01.01.20";

    @Test
    public void test_SoortDienstPlaatsingAfnemerIndicatie() {
        ;
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(SoortDienst.PLAATSING_AFNEMERINDICATIE);
        final ZoekPersoonGeneriekGbaVerzoek verzoek = new ZoekPersoonGbaVerzoek();
        verzoek.getGevraagdeRubrieken().add(BSN);

        Set<Melding> meldingen = subject.valideer(verzoek, autorisatiebundel);

        assertTrue(meldingen.isEmpty());
    }

    @Test
    public void test_SoortDienstPlaatsingAfnemerIndicatie_Autorisatiefout() {
        ;
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(SoortDienst.PLAATSING_AFNEMERINDICATIE);
        final ZoekPersoonGeneriekGbaVerzoek verzoek = new ZoekPersoonGbaVerzoek();
        verzoek.getGevraagdeRubrieken().add("invalid");

        Set<Melding> meldingen = subject.valideer(verzoek, autorisatiebundel);

        assertEquals(Regel.R2343, Iterables.getOnlyElement(meldingen).getRegel());
    }

    @Test
    public void test_SoortDienstVerwijderingAfnemerIndicatie() {
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(SoortDienst.VERWIJDERING_AFNEMERINDICATIE);
        final ZoekPersoonGeneriekGbaVerzoek verzoek = new ZoekPersoonGbaVerzoek();
        verzoek.getGevraagdeRubrieken().add(BSN);

        Set<Melding> meldingen = subject.valideer(verzoek, autorisatiebundel);

        assertTrue(meldingen.isEmpty());
    }

    @Test
    public void test_SoortDienstVerwijderingAfnemerIndicatie_Autorisatiefout() {
        ;
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(SoortDienst.VERWIJDERING_AFNEMERINDICATIE);
        final ZoekPersoonGeneriekGbaVerzoek verzoek = new ZoekPersoonGbaVerzoek();
        verzoek.getGevraagdeRubrieken().add("invalid");

        Set<Melding> meldingen = subject.valideer(verzoek, autorisatiebundel);

        assertEquals(Regel.R2343, Iterables.getOnlyElement(meldingen).getRegel());
    }

    @Test
    public void test_GevraagdeRubriekenLeeg() {
        ;
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(SoortDienst.PLAATSING_AFNEMERINDICATIE);
        final ZoekPersoonGeneriekGbaVerzoek verzoek = new ZoekPersoonGbaVerzoek();

        Set<Melding> meldingen = subject.valideer(verzoek, autorisatiebundel);

        assertTrue(meldingen.isEmpty());
    }

    @Test
    public void test_BsnInZoekRubrieken() {
        ;
        final Autorisatiebundel autorisatiebundel = maakAutorisatiebundel(SoortDienst.PLAATSING_AFNEMERINDICATIE);
        final ZoekPersoonGeneriekGbaVerzoek verzoek = new ZoekPersoonGbaVerzoek();
        verzoek.getZoekRubrieken().add(BSN);

        Set<Melding> meldingen = subject.valideer(verzoek, autorisatiebundel);

        assertTrue(meldingen.isEmpty());
    }


    private Autorisatiebundel maakAutorisatiebundel(final SoortDienst soortDienst) {
        Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.GBA, false);
        Dienstbundel dienstbundel = new Dienstbundel(leveringsautorisatie);
        Dienst adhocDienst = new Dienst(dienstbundel, soortDienst);
        PartijRol partijRol = new PartijRol(new Partij("partij1", "001234"), Rol.AFNEMER);
        ToegangLeveringsAutorisatie toegangLeveringsAutorisatie = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        return new Autorisatiebundel(toegangLeveringsAutorisatie, adhocDienst);
    }
}
