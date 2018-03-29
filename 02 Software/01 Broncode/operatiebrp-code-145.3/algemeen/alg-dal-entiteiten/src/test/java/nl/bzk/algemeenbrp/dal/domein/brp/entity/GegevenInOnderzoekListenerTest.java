/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import org.junit.Test;

/**
 * Unittest voor {@link GegevenInOnderzoekListener}.
 */
public class GegevenInOnderzoekListenerTest {

    private final GegevenInOnderzoekListener listener = new GegevenInOnderzoekListener();

    @Test
    public void testPostSaveEntiteitIsDezelfdeEntiteitGioNogNietGekoppeld() {
        final Relatie relatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final GegevenInOnderzoek gegevenInOnderzoek = new GegevenInOnderzoek(new Onderzoek(), Element.RELATIE_DATUMAANVANG);
        relatie.setGegevenInOnderzoek(gegevenInOnderzoek);

        assertNull(gegevenInOnderzoek.getEntiteitOfVoorkomen());
        listener.postSave(relatie);
        assertEquals(relatie, gegevenInOnderzoek.getEntiteitOfVoorkomen());
    }

    @Test
    public void testPostSaveEntiteitIsDezelfdeEntiteitGioAlWelGekoppeld() {
        final Relatie relatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final GegevenInOnderzoek gegevenInOnderzoek = new GegevenInOnderzoek(new Onderzoek(), Element.RELATIE_DATUMAANVANG);
        gegevenInOnderzoek.setEntiteitOfVoorkomen(relatie);

        assertSame(relatie, gegevenInOnderzoek.getEntiteitOfVoorkomen());
        listener.postSave(relatie);
        assertSame(relatie, gegevenInOnderzoek.getEntiteitOfVoorkomen());
    }

    /**
     * Hibernate kan een nieuwe instantie genereren bij opslag, Aangezien een {@link Entiteit} en een {@link
     * GegevenInOnderzoek} niet hard aan elkaar gekoppeld zijn, moeten deze in een post-save opnieuw aan elkaar gekoppeld worden.
     */
    @Test
    public void testPostSaveEntiteitIsNieuweInstantie() {
        final Relatie relatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final Relatie relatie2 = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        final GegevenInOnderzoek gegevenInOnderzoek = new GegevenInOnderzoek(new Onderzoek(), Element.RELATIE_DATUMAANVANG);
        gegevenInOnderzoek.setEntiteitOfVoorkomen(relatie);
        relatie2.setGegevenInOnderzoek(gegevenInOnderzoek);


        assertSame(relatie, gegevenInOnderzoek.getEntiteitOfVoorkomen());
        listener.postSave(relatie2);
        assertNotSame(relatie, gegevenInOnderzoek.getEntiteitOfVoorkomen());
        assertEquals(relatie2, gegevenInOnderzoek.getEntiteitOfVoorkomen());
    }

    @Test
    public void testPostSaveGeenEntiteitOfWelEenGegevenInOnderzoek() {
        listener.postSave("");
        final GegevenInOnderzoek entity = new GegevenInOnderzoek(new Onderzoek(), Element.AANDUIDINGINHOUDINGVERMISSINGREISDOCUMENT);
        listener.postSave(entity);
        assertNull(entity.getEntiteitOfVoorkomen());
    }

    @Test
    public void testPostLoad() {
        final Relatie relatie = new Relatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        assertTrue(relatie.getGegevenInOnderzoekPerElementMap().isEmpty());
        listener.postLoad(relatie);
        assertTrue(relatie.getGegevenInOnderzoekPerElementMap().isEmpty());

        final Element soortGegeven = Element.AANDUIDINGINHOUDINGVERMISSINGREISDOCUMENT;
        final GegevenInOnderzoek gegevenInOnderzoek = new GegevenInOnderzoek(new Onderzoek(), soortGegeven);
        gegevenInOnderzoek.setEntiteitOfVoorkomen(relatie);
        assertFalse(relatie.getGegevenInOnderzoekPerElementMap().isEmpty());
        listener.postLoad(gegevenInOnderzoek);
        assertFalse(relatie.getGegevenInOnderzoekPerElementMap().isEmpty());

        relatie.verwijderGegevenInOnderzoek(soortGegeven);
        assertTrue(relatie.getGegevenInOnderzoekPerElementMap().isEmpty());
        listener.postLoad(gegevenInOnderzoek);
        assertFalse(relatie.getGegevenInOnderzoekPerElementMap().isEmpty());
    }

    @Test
    public void testPostLoadGioHeeftGeenEntiteit() {
        final Element soortGegeven = Element.AANDUIDINGINHOUDINGVERMISSINGREISDOCUMENT;
        final GegevenInOnderzoek gegevenInOnderzoek = new GegevenInOnderzoek(new Onderzoek(), soortGegeven);
        listener.postLoad(gegevenInOnderzoek);
        assertNull(gegevenInOnderzoek.getEntiteitOfVoorkomen());
    }

}
