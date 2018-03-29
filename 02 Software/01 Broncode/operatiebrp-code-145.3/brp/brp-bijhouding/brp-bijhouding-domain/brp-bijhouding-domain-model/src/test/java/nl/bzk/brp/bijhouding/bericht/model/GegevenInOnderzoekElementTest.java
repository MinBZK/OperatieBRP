/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Entiteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIDHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutel;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutelServiceImpl;
import nl.bzk.algemeenbrp.services.objectsleutel.OngeldigeObjectSleutelException;
import org.junit.Test;

/**
 * Testen voor {@link GegevenInOnderzoekElement}.
 */
public class GegevenInOnderzoekElementTest extends AbstractElementTest {

    @Test
    public void getElementNaam() {
        final String elementNaam = "elementNaam";
        final GegevenInOnderzoekElement gegevenInOnderzoekElement = GegevenInOnderzoekElement.getInstance("CI_GIO_1", elementNaam, null, null);
        assertEquals(elementNaam, BmrAttribuut.getWaardeOfNull(gegevenInOnderzoekElement.getElementNaam()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getElementNaamFout() {
        final GegevenInOnderzoekElement gegevenInOnderzoekElement = GegevenInOnderzoekElement.getInstance("CI_GIO_1", null, null, null);
    }

    @Test
    public void getObjectSleutelGegeven() {
        final String objectSleutelGegeven = "1234";
        final GegevenInOnderzoekElement
                gegevenInOnderzoekElement =
                GegevenInOnderzoekElement.getInstance("CI_GIO_1", "elementNaam", objectSleutelGegeven, null);
        assertEquals(objectSleutelGegeven, BmrAttribuut.getWaardeOfNull(gegevenInOnderzoekElement.getObjectSleutelGegeven()));
    }

    @Test
    public void getVoorkomenSleutelGegeven() {
        final String voorkomenSleutelGegeven = "1234";
        final GegevenInOnderzoekElement
                gegevenInOnderzoekElement =
                GegevenInOnderzoekElement.getInstance("CI_GIO_1", "elementNaam", null, voorkomenSleutelGegeven);
        assertEquals(voorkomenSleutelGegeven, BmrAttribuut.getWaardeOfNull(gegevenInOnderzoekElement.getVoorkomenSleutelGegeven()));
    }

    @Test
    public void valideerInhoud() {
        //setup
        final GegevenInOnderzoekElement
                gegevenInOnderzoekElement =
                GegevenInOnderzoekElement.getInstance("CI_GIO_1", "elementNaam", "1", "2");
        //execute
        final List<MeldingElement> meldingen = gegevenInOnderzoekElement.valideerInhoud();
        //verify
        controleerRegels(meldingen, Regel.R2596, Regel.R2612);
    }

    @Test
    public void controleerGeldigheidElement() {
        //setup
        final nl.bzk.algemeenbrp.dal.domein.brp.enums.Element
                element =
                nl.bzk.algemeenbrp.dal.domein.brp.enums.Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER;
        final GegevenInOnderzoekElement
                gegevenInOnderzoekElement =
                GegevenInOnderzoekElement.getInstance("CI_GIO_1", element.getNaam(), null, "2");
        List<MeldingElement> meldingen = new ArrayList<>();
        //execute
        gegevenInOnderzoekElement.controleerGeldigheidElement(meldingen, 10000000);
        //verify
        controleerRegels(meldingen, Regel.R2602);
        //setup
        meldingen.clear();
        //execute
        gegevenInOnderzoekElement.controleerGeldigheidElement(meldingen, 20170101);
        //verify
        controleerRegels(meldingen);
        final OnderzoekGroepElement onderzoekGroep = OnderzoekGroepElement.getInstance("ci_onderzoek_groep_1", 10000000, null, null, null);
        final OnderzoekElement
                onderzoekElement =
                OnderzoekElement.getInstance("ci_onderzoek_1", onderzoekGroep, Collections.singletonList(gegevenInOnderzoekElement));
        meldingen = onderzoekElement.valideerInhoud();
        //verify
        controleerRegels(meldingen, Regel.R2602);
    }

    @Test
    public void controleerAangewezenObject() throws OngeldigeObjectSleutelException {
        //setup
        final long persoonSleutel = 1L;
        final String objectSleutelString = "ABC";
        final String objectSleutelStringAnders = "anders";
        final ObjectSleutel objectSleutel = new ObjectSleutelServiceImpl().maakPersoonObjectSleutel(persoonSleutel, 1L);
        final ObjectSleutel objectSleutelAnders = new ObjectSleutelServiceImpl().maakPersoonObjectSleutel(99L, 1L);
        when(getObjectSleutelService().maakPersoonObjectSleutel(objectSleutelString)).thenReturn(objectSleutel);
        when(getObjectSleutelService().maakPersoonObjectSleutel(objectSleutelStringAnders)).thenReturn(objectSleutelAnders);
        when(getObjectSleutelService().maakPersoonObjectSleutel("fout")).thenThrow(new OngeldigeObjectSleutelException(Regel.R1833));
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        persoon.setId(persoonSleutel);
        final Element element = Element.PERSOON;
        final String communicatieId = "CI_GIO_1";
        final GegevenInOnderzoekElement
                gegevenInOnderzoekElement =
                GegevenInOnderzoekElement.getInstance(communicatieId, element.getNaam(), objectSleutelString, null);
        final List<MeldingElement> meldingen = new ArrayList<>();
        //execute 'goed scenario'
        gegevenInOnderzoekElement.controleerAangewezenObject(meldingen, persoon);
        //verify 'goed scenario'
        controleerRegels(meldingen);
        //setup 'fout scenario 1'
        final GegevenInOnderzoekElement gegevenInOnderzoekElementFout =
                GegevenInOnderzoekElement.getInstance(communicatieId, element.getNaam(), "fout", null);
        meldingen.clear();
        //execute 'fout scenario 1'
        gegevenInOnderzoekElementFout.controleerAangewezenObject(meldingen, persoon);
        //verify 'fout scenario 1'
        controleerRegels(meldingen, Regel.R2597);
        //setup 'fout scenario 2'
        final GegevenInOnderzoekElement gegevenInOnderzoekElementFout2 =
                GegevenInOnderzoekElement.getInstance(communicatieId, element.getNaam(), objectSleutelStringAnders, null);
        meldingen.clear();
        //execute 'fout scenario 2'
        gegevenInOnderzoekElementFout2.controleerAangewezenObject(meldingen, persoon);
        //verify 'fout scenario 2'
        controleerRegels(meldingen, Regel.R2597);
        //setup 'fout scenario 3'
        final Element elementFout = Element.PERSOON_ADRES;
        final GegevenInOnderzoekElement gegevenInOnderzoekElementFout3 =
                GegevenInOnderzoekElement.getInstance(communicatieId, elementFout.getNaam(), objectSleutelString, null);
        meldingen.clear();
        //execute 'fout scenario 3'
        gegevenInOnderzoekElementFout3.controleerAangewezenObject(meldingen, persoon);
        //verify 'fout scenario 3'
        controleerRegels(meldingen, Regel.R2597);
        //setup 'goed scenario 2'
        final Element elementOokGoed = Element.OUDER_PERSOON;
        final GegevenInOnderzoekElement gegevenInOnderzoekElement2 =
                GegevenInOnderzoekElement.getInstance(communicatieId, element.getNaam(), objectSleutelString, null);
        meldingen.clear();
        //execute 'goed scenario 2'
        gegevenInOnderzoekElement2.controleerAangewezenObject(meldingen, persoon);
        //verify 'goed scenario 2'
        controleerRegels(meldingen);
    }

    @Test
    public void controleerAangewezenVoorkomen() {
        //setup
        final long voorkomenSleutel = 1L;
        final String voorkomenSleutelString = String.valueOf(voorkomenSleutel);
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonIDHistorie idHistorie = new PersoonIDHistorie(persoon);
        idHistorie.setAdministratienummer("1234567890");
        idHistorie.setId(voorkomenSleutel);
        persoon.addPersoonIDHistorie(idHistorie);
        final Element element = Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER;
        final String communicatieId = "CI_GIO_1";
        final GegevenInOnderzoekElement
                gegevenInOnderzoekElement =
                GegevenInOnderzoekElement.getInstance(communicatieId, element.getNaam(), null, voorkomenSleutelString);
        final List<MeldingElement> meldingen = new ArrayList<>();
        //execute 'goed scenario'
        gegevenInOnderzoekElement.controleerAangewezenVoorkomen(meldingen, persoon);
        //verify 'goed scenario'
        controleerRegels(meldingen);
        //setup 'fout 1 scenario'
        final Element elementFout = Element.PERSOON_GEBOORTE_DATUM;
        final GegevenInOnderzoekElement
                gegevenInOnderzoekElementFout1 =
                GegevenInOnderzoekElement.getInstance(communicatieId, elementFout.getNaam(), null, voorkomenSleutelString);
        meldingen.clear();
        //execute 'fout 1 scenario'
        gegevenInOnderzoekElementFout1.controleerAangewezenVoorkomen(meldingen, persoon);
        //verify 'fout 1 scenario'
        controleerRegels(meldingen, Regel.R2599);
        //setup 'fout 2 scenario'
        final GegevenInOnderzoekElement
                gegevenInOnderzoekElementFout2 =
                GegevenInOnderzoekElement.getInstance(communicatieId, element.getNaam(), null, "X");
        meldingen.clear();
        //execute 'fout 2 scenario'
        gegevenInOnderzoekElementFout2.controleerAangewezenVoorkomen(meldingen, persoon);
        //verify 'fout 2 scenario'
        controleerRegels(meldingen, Regel.R2599);
    }

    @Test
    public void testBepaalObjectOfVoorkomen() {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonIDHistorie idHistorie = new PersoonIDHistorie(persoon);
        idHistorie.setId(1L);
        idHistorie.setAdministratienummer("1234567890");
        persoon.addPersoonIDHistorie(idHistorie);
        final GegevenInOnderzoekElement
                gegevenInOnderzoekElement =
                GegevenInOnderzoekElement
                        .getInstance("ci_gio_1", Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER.getNaam(), null, "" + idHistorie.getId());
        final Entiteit gegevenInOnderzoek = gegevenInOnderzoekElement.bepaalObjectOfVoorkomen(persoon);
        assertTrue(gegevenInOnderzoek instanceof PersoonIDHistorie);

    }

    @Test
    public void testBepaalObjectOfVoorkomenRelatie() throws OngeldigeObjectSleutelException {
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final Relatie relatie = new Relatie(SoortRelatie.HUWELIJK);
        relatie.setId(1L);
        final Betrokkenheid ikBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatie);
        final Betrokkenheid partnerBetrokkenheid = new Betrokkenheid(SoortBetrokkenheid.PARTNER, relatie);
        final BetrokkenheidHistorie ikBetrokkenheidHistorie = new BetrokkenheidHistorie(ikBetrokkenheid);
        final BetrokkenheidHistorie partnerBetrokkenheidHistorie = new BetrokkenheidHistorie(partnerBetrokkenheid);
        ikBetrokkenheid.addBetrokkenheidHistorie(ikBetrokkenheidHistorie);
        partnerBetrokkenheid.addBetrokkenheidHistorie(partnerBetrokkenheidHistorie);
        relatie.addBetrokkenheid(ikBetrokkenheid);
        relatie.addBetrokkenheid(partnerBetrokkenheid);
        persoon.addBetrokkenheid(ikBetrokkenheid);
        when(getObjectSleutelService().maakRelatieObjectSleutel("" + relatie.getId()))
                .thenReturn(new ObjectSleutelServiceImpl().maakRelatieObjectSleutel(relatie.getId()));

        final GegevenInOnderzoekElement
                gegevenInOnderzoekElement =
                GegevenInOnderzoekElement
                        .getInstance("ci_gio_1", Element.RELATIE.getNaam(), "" + relatie.getId(), null);
        final Entiteit gegevenInOnderzoek = gegevenInOnderzoekElement.bepaalObjectOfVoorkomen(persoon);
        assertTrue(gegevenInOnderzoek instanceof Relatie);
    }
}
