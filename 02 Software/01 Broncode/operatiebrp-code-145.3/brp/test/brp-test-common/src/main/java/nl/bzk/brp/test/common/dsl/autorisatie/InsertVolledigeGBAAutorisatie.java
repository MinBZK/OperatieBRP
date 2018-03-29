/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.common.dsl.autorisatie;

import java.util.List;
import java.util.function.Consumer;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelLo3Rubriek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Rubriek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;

/**
 * Voegt een autorisatie toe met alle lo3rubrieken.
 */
public class InsertVolledigeGBAAutorisatie implements Consumer<EntityManager> {

    private static final int DATUM_INGANG = 2010_01_01;

    @Override
    public void accept(final EntityManager entityManager) {

        final PartijRol partijRol = entityManager.getReference(PartijRol.class, 1);

        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.GBA, false);
        leveringsautorisatie.setDatumIngang(DATUM_INGANG);
        leveringsautorisatie.setActueelEnGeldig(true);
        leveringsautorisatie.setNaam("GBA leveringautorisatie");
        entityManager.persist(leveringsautorisatie);

        final Dienstbundel dienstbundel = new Dienstbundel(leveringsautorisatie);
        dienstbundel.setDatumIngang(DATUM_INGANG);
        dienstbundel.setActueelEnGeldig(true);
        dienstbundel.setNaam("GBA dienstbundel");
        leveringsautorisatie.addDienstbundelSet(dienstbundel);
        entityManager.persist(dienstbundel);

        final TypedQuery<Lo3Rubriek> query = entityManager.createQuery("SELECT l FROM Lo3Rubriek l", Lo3Rubriek.class);
        final List<Lo3Rubriek> lo3RubriekList = query.getResultList();
        for (Lo3Rubriek lo3Rubriek : lo3RubriekList) {
            final DienstbundelLo3Rubriek dienstbundelLo3Rubriek = new DienstbundelLo3Rubriek(dienstbundel, lo3Rubriek);
            dienstbundel.addDienstbundelLo3RubriekSet(dienstbundelLo3Rubriek);
            entityManager.persist(dienstbundelLo3Rubriek);
        }

        final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        toegangLeveringsAutorisatie.setActueelEnGeldig(true);
        toegangLeveringsAutorisatie.setDatumIngang(DATUM_INGANG);

        entityManager.persist(toegangLeveringsAutorisatie);

    }
}
