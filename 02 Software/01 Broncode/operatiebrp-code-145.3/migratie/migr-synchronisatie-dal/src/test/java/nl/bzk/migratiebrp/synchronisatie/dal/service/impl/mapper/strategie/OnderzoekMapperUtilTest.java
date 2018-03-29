/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie;

import static org.junit.Assert.assertEquals;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIDHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;

import org.junit.Test;

public class OnderzoekMapperUtilTest {

    @Test
    public void testBepaalElement() throws Exception {

        // Eigen Persoon
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonSamengesteldeNaamHistorie samengesteldeNaamHistorie = new PersoonSamengesteldeNaamHistorie(persoon, "stam", false, false);
        final PersoonIDHistorie idHistorie = new PersoonIDHistorie(persoon);

        assertEquals(
                Element.PERSOON_SAMENGESTELDENAAM_DATUMAANVANGGELDIGHEID,
                OnderzoekMapperUtil.bepaalElement(samengesteldeNaamHistorie, OnderzoekMapperUtil.Historieveldnaam.AANVANG));
        assertEquals(
                Element.PERSOON_IDENTIFICATIENUMMERS_TIJDSTIPREGISTRATIE,
                OnderzoekMapperUtil.bepaalElement(idHistorie, OnderzoekMapperUtil.Historieveldnaam.REGISTRATIE));

        // Gerelateerde geregistreerde partner
        assertEquals(
                Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_SAMENGESTELDENAAM_DATUMAANVANGGELDIGHEID,
                OnderzoekMapperUtil.bepaalElement(
                        samengesteldeNaamHistorie,
                        OnderzoekMapperUtil.Historieveldnaam.AANVANG,
                        Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON));
        assertEquals(
                Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_IDENTIFICATIENUMMERS_TIJDSTIPREGISTRATIE,
                OnderzoekMapperUtil
                        .bepaalElement(idHistorie, OnderzoekMapperUtil.Historieveldnaam.REGISTRATIE, Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON));

        // Gerelateerde huwelijks partner
        assertEquals(
                Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_SAMENGESTELDENAAM_DATUMAANVANGGELDIGHEID,
                OnderzoekMapperUtil.bepaalElement(
                        samengesteldeNaamHistorie,
                        OnderzoekMapperUtil.Historieveldnaam.AANVANG,
                        Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON));
        assertEquals(
                Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTIFICATIENUMMERS_TIJDSTIPREGISTRATIE,
                OnderzoekMapperUtil.bepaalElement(idHistorie, OnderzoekMapperUtil.Historieveldnaam.REGISTRATIE, Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON));

        // Gerelateerde ouder
        assertEquals(
                Element.GERELATEERDEOUDER_PERSOON_SAMENGESTELDENAAM_DATUMAANVANGGELDIGHEID,
                OnderzoekMapperUtil.bepaalElement(
                        samengesteldeNaamHistorie,
                        OnderzoekMapperUtil.Historieveldnaam.AANVANG,
                        Element.GERELATEERDEOUDER_PERSOON));
        assertEquals(
                Element.GERELATEERDEOUDER_PERSOON_IDENTIFICATIENUMMERS_TIJDSTIPREGISTRATIE,
                OnderzoekMapperUtil.bepaalElement(idHistorie, OnderzoekMapperUtil.Historieveldnaam.REGISTRATIE, Element.GERELATEERDEOUDER_PERSOON));

        // Gerelateerde kind
        assertEquals(
                Element.GERELATEERDEKIND_PERSOON_SAMENGESTELDENAAM_DATUMAANVANGGELDIGHEID,
                OnderzoekMapperUtil.bepaalElement(
                        samengesteldeNaamHistorie,
                        OnderzoekMapperUtil.Historieveldnaam.AANVANG,
                        Element.GERELATEERDEKIND_PERSOON));
        assertEquals(
                Element.GERELATEERDEKIND_PERSOON_IDENTIFICATIENUMMERS_TIJDSTIPREGISTRATIE,
                OnderzoekMapperUtil.bepaalElement(idHistorie, OnderzoekMapperUtil.Historieveldnaam.REGISTRATIE, Element.GERELATEERDEKIND_PERSOON));
    }
}
