/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.Collections;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIDHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingSituatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import org.junit.Before;
import org.junit.Test;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;

/**
 * Testen voor {@link RegistratieAanvangOnderzoekActieElement}.
 */
public class RegistratieAanvangOnderzoekActieElementTest extends AbstractElementTest {

    private ElementBuilder builder;

    @Before
    public void setup() {
        builder = new ElementBuilder();
    }

    @Test
    public void testGetSoortActie() {
        assertEquals(SoortActie.REGISTRATIE_AANVANG_ONDERZOEK, maakActie(20170101, "X", null).getSoortActie());
    }

    @Test
    public void testVerwerkSuccesvol() {
        //setup
        final String objectSleutel = "P1";
        final Persoon persoonDelegate = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonIDHistorie idHistorie = new PersoonIDHistorie(persoonDelegate);
        idHistorie.setId(1L);
        persoonDelegate.addPersoonIDHistorie(idHistorie);
        final BijhoudingPersoon persoon = BijhoudingPersoon.decorate(persoonDelegate);
        persoon.setBijhoudingSituatie(BijhoudingSituatie.INDIENER_IS_BIJHOUDINGSPARTIJ);
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, objectSleutel)).thenReturn(persoon);
        final RegistratieAanvangOnderzoekActieElement actie = maakActie(20170101, objectSleutel, idHistorie.getId().toString());
        assertEquals(0, persoon.getOnderzoeken().size());
        //execute
        actie.verwerk(getBericht(), getAdministratieveHandeling());
        //verify
        assertEquals(1, persoon.getOnderzoeken().size());
        assertEquals(1, persoon.getOnderzoeken().iterator().next().getGegevenInOnderzoekSet().size());
        assertSame(idHistorie, persoon.getOnderzoeken().iterator().next().getGegevenInOnderzoekSet().iterator().next().getEntiteitOfVoorkomen());
    }

    @Test
    public void testNietVerwerkt() {
        //setup
        final String objectSleutel = "P1";
        final Persoon persoonDelegate = new Persoon(SoortPersoon.INGESCHREVENE);
        final BijhoudingPersoon persoon = BijhoudingPersoon.decorate(persoonDelegate);
        persoon.setBijhoudingSituatie(BijhoudingSituatie.OPNIEUW_INDIENEN);
        when(getBericht().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, objectSleutel)).thenReturn(persoon);
        final RegistratieAanvangOnderzoekActieElement actie = maakActie(20170101, objectSleutel, null);
        assertEquals(0, persoon.getOnderzoeken().size());
        //execute
        actie.verwerk(getBericht(), getAdministratieveHandeling());
        //verify
        assertEquals(0, persoon.getOnderzoeken().size());
    }

    @Test
    public void testGetPeilDatum() {
        final Integer peildatum = 20170101;
        assertEquals(peildatum, maakActie(peildatum, "X", null).getPeilDatum().getWaarde());
    }

    @Test
    public void testValideerSpecifiekeInhoud() {
        assertTrue(maakActie(20170101, "X", null).valideerSpecifiekeInhoud().isEmpty());
    }

    private RegistratieAanvangOnderzoekActieElement maakActie(final int datumAanvangGeldigheid, final String persoonSleutel, final String voorkomenSleutel) {
        final OnderzoekGroepElement
                onderzoekGroepElement =
                OnderzoekGroepElement.getInstance("ci_onderzoek_groep_1", datumAanvangGeldigheid, null, "test omschrijving", null);
        final GegevenInOnderzoekElement
                gegevenInOnderzoekElement =
                GegevenInOnderzoekElement.getInstance("ci_gio_1", Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getNaam(), null, voorkomenSleutel);
        final OnderzoekElement
                onderzoekElement =
                OnderzoekElement.getInstance("ci_onderzoek_1", onderzoekGroepElement, Collections.singletonList(gegevenInOnderzoekElement));
        onderzoekElement.setVerzoekBericht(getBericht());
        final PersoonGegevensElement
                persoonElement =
                builder.maakPersoonGegevensElement("ci_persoon_1", persoonSleutel, null,
                        new ElementBuilder.PersoonParameters().onderzoeken(Collections.singletonList(onderzoekElement)));
        persoonElement.setVerzoekBericht(getBericht());
        final RegistratieAanvangOnderzoekActieElement
                result =
                new RegistratieAanvangOnderzoekActieElement(new AbstractBmrGroep.AttributenBuilder().objecttype("Actie").build(),
                        new DatumElement(datumAanvangGeldigheid), null, null, persoonElement);
        result.setVerzoekBericht(getBericht());
        return result;
    }
}
