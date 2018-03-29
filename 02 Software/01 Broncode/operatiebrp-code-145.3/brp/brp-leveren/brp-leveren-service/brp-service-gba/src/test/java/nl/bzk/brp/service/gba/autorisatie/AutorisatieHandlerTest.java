/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.gba.autorisatie;

import static org.junit.Assert.assertEquals;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.gba.domain.bevraging.Basisantwoord;
import nl.bzk.brp.service.cache.TestLeveringsAutorisatieCache;
import org.junit.Before;
import org.junit.Test;

public class AutorisatieHandlerTest {

    private static final Integer DATUM_INGANG = Integer.valueOf(20100101);
    private AutorisatieHandler<Basisantwoord> subject;

    @Before
    public void setup() {
        BrpNu.set();
        TestLeveringsAutorisatieCache cache = new TestLeveringsAutorisatieCache();
        GbaAutorisaties gbaAutorisaties = new GbaAutorisaties(cache);
        Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.GBA, false);
        leveringsautorisatie.setDatumIngang(DATUM_INGANG);
        Dienstbundel dienstbundel = new Dienstbundel(leveringsautorisatie);
        dienstbundel.setDatumIngang(DATUM_INGANG);
        Dienst dienst = new Dienst(dienstbundel, SoortDienst.ZOEK_PERSOON);
        dienst.setDatumIngang(DATUM_INGANG);
        dienstbundel.addDienstSet(dienst);
        leveringsautorisatie.addDienstbundelSet(dienstbundel);
        ToegangLeveringsAutorisatie
                toegang =
                new ToegangLeveringsAutorisatie(new PartijRol(new Partij("partij1", "000123"), Rol.AFNEMER), leveringsautorisatie);
        toegang.setDatumIngang(DATUM_INGANG);
        cache.addAutorisatie("000123", toegang);
        subject = new AutorisatieHandler<>(gbaAutorisaties);
    }

    @Test
    public void success() {
        Basisantwoord antwoord = subject.verwerkMetAutorisatie("000123", Rol.AFNEMER, SoortDienst.ZOEK_PERSOON, this::maakSuccessAntwoord, Basisantwoord::new);
        assertEquals("Inhoud", antwoord.getInhoud());
    }

    @Test
    public void failures() {
        Basisantwoord antwoord = subject.verwerkMetAutorisatie("000666", Rol.AFNEMER, SoortDienst.ZOEK_PERSOON, bundel -> new Basisantwoord(), this::maakFailuresAntwoord);
        assertEquals("Fout", antwoord.getFoutreden());
    }

    private Basisantwoord maakFailuresAntwoord() {
        Basisantwoord antwoord = new Basisantwoord();
        antwoord.setFoutreden("Fout");
        return antwoord;
    }

    private Basisantwoord maakSuccessAntwoord(final Autorisatiebundel bundel) {
        Basisantwoord antwoord = new Basisantwoord();
        antwoord.setInhoud("Inhoud");
        return antwoord;
    }
}
