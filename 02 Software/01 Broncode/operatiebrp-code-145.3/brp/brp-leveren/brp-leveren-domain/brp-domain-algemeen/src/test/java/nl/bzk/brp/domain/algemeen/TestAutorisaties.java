/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.algemeen;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroep;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroepAttribuut;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.junit.Ignore;

/**
 */
@Ignore
public class TestAutorisaties {

    public static Leveringsautorisatie metGeblokkeerdeDienstbundel(SoortDienst soortDienst) {
        final Leveringsautorisatie leveringsautorisatie = metSoortDienst(1, soortDienst);
        leveringsautorisatie.getDienstbundelSet().iterator().next().setIndicatieGeblokkeerd(true);
        return leveringsautorisatie;
    }

    public static Leveringsautorisatie metVerlopenDienst(SoortDienst soortDienst) {
        final Leveringsautorisatie leveringsautorisatie = metSoortDienst(1, soortDienst);
        final Integer gisteren = DatumUtil.gisteren();
        leveringsautorisatie.setDatumEinde(gisteren);
        for (Dienstbundel dienstbundel : leveringsautorisatie.getDienstbundelSet()) {
            for (Dienst dienst : dienstbundel.getDienstSet()) {
                dienst.setDatumEinde(gisteren);
                dienst.setActueelEnGeldig(true);
            }
        }
        return leveringsautorisatie;
    }

    public static Leveringsautorisatie metSoortDienst(SoortDienst soortDienst) {
        return metSoortDienst(1, soortDienst);
    }

    public static Leveringsautorisatie metSoortDienst(int laId, SoortDienst... soortenDienst) {
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.BRP, false);
        leveringsautorisatie.setId(laId);
        final Dienstbundel dienstbundel = new Dienstbundel(leveringsautorisatie);
        dienstbundel.setActueelEnGeldig(true);
        dienstbundel.setDatumIngang(DatumUtil.gisteren());
        leveringsautorisatie.setDienstbundelSet(Collections.singleton(dienstbundel));
        leveringsautorisatie.setDatumIngang(DatumUtil.gisteren());
        leveringsautorisatie.setActueelEnGeldig(true);
        final Set<Dienst> diensten = new HashSet<>(soortenDienst.length);
        int dienstId = 1;
        for (SoortDienst soortDienst : soortenDienst) {
            final Dienst dienst = new Dienst(dienstbundel, soortDienst);
            dienst.setId(dienstId++);
            dienst.setDatumIngang(DatumUtil.gisteren());
            dienst.setActueelEnGeldig(true);
            diensten.add(dienst);
        }
        dienstbundel.setDienstSet(diensten);
        return leveringsautorisatie;
    }

    public static Leveringsautorisatie metDienst(Dienst dienst) {
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.BRP, false);
        leveringsautorisatie.setId(1);
        final Dienstbundel dienstbundel = new Dienstbundel(leveringsautorisatie);
        leveringsautorisatie.setDienstbundelSet(Collections.singleton(dienstbundel));
        dienstbundel.setDienstSet(Collections.singleton(dienst));

        return leveringsautorisatie;
    }

    public static Leveringsautorisatie maakLeveringsautorisatie(final Element attribuutElement, final SoortDienst soortDienst,
                                                                final GroepDefinitie... groepElementen) {
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.BRP, false);
        leveringsautorisatie.setId(1);
        maakDienst(leveringsautorisatie, attribuutElement, soortDienst, groepElementen);

        return leveringsautorisatie;
    }


    public static Dienst maakDienst(final Leveringsautorisatie leveringsautorisatie, final Element attribuutElement, final SoortDienst soortDienst, final
    GroepDefinitie... groepElementen) {
        final Dienstbundel db = new Dienstbundel(leveringsautorisatie);
        leveringsautorisatie.setDienstbundelSet(Collections.singleton(db));
        final Set<DienstbundelGroep> dienstbundelGroepSet = new HashSet<>();
        for (GroepDefinitie groepDefinitie : groepElementen) {
            final DienstbundelGroep dienstbundelGroep = new DienstbundelGroep(db, groepDefinitie.element, groepDefinitie.indicatieFormeleHistorie,
                    groepDefinitie.indicatieMaterieleHistorie, groepDefinitie.indicatieVerantwoording);
            dienstbundelGroepSet.add(dienstbundelGroep);
            if (attribuutElement != null) {
                final DienstbundelGroepAttribuut dienstbundelGroepAttribuut = new DienstbundelGroepAttribuut(dienstbundelGroep, attribuutElement);
                dienstbundelGroep.setDienstbundelGroepAttribuutSet(Collections.singleton(dienstbundelGroepAttribuut));
            }
        }
        db.setDienstbundelGroepSet(dienstbundelGroepSet);
        final Dienst dienst = new Dienst(db, soortDienst);
        db.setDienstSet(Collections.singleton(dienst));
        return dienst;
    }

    public static Autorisatiebundel bundelMetRol(Rol rol, Dienst dienst) {
        final Partij partij = TestPartijBuilder.maakBuilder().metId(1).metCode("000001").build();
        final PartijRol partijRol = new PartijRol(partij, rol);
        final Leveringsautorisatie leveringsautorisatie = dienst.getDienstbundel().getLeveringsautorisatie();
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        tla.setId(1);
        return new Autorisatiebundel(tla, dienst);
    }

    public static Autorisatiebundel maakAutorisatiebundel(final SoortDienst soortDienst) {
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        final ToegangLeveringsAutorisatie
                tla =
                new ToegangLeveringsAutorisatie(new PartijRol(TestPartijBuilder.maakBuilder().metId(1).metCode("000000").build(), Rol.AFNEMER),
                        leveringsautorisatie);
        return new Autorisatiebundel(tla, AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst));
    }

    public static ToegangLeveringsAutorisatie maak(Rol rol, Dienst dienst) {
        final Partij partij = TestPartijBuilder.maakBuilder().metId(1).metCode("000001").build();
        final PartijRol partijRol = new PartijRol(partij, rol);
        final Leveringsautorisatie leveringsautorisatie = dienst.getDienstbundel().getLeveringsautorisatie();
        final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        toegangLeveringsAutorisatie.setId(1);
        return toegangLeveringsAutorisatie;
    }

    public static class GroepDefinitie {
        public Element element;
        public boolean indicatieFormeleHistorie;
        public boolean indicatieMaterieleHistorie;
        public boolean indicatieVerantwoording;

    }

}
