/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.GegevenInOnderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.GegevenInOnderzoekHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Onderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.OnderzoekHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIDHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.*;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.services.objectsleutel.OngeldigeObjectSleutelException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

/**
 * Testen voor {@link RegistratieWijzigingOnderzoekActieElement}.
 */
public class RegistratieWijzigingOnderzoekActieElementTest extends AbstractElementTest {

    private final static String PERSOON_SLEUTEL = "p1";
    private final static String ONDERZOEK_SLEUTEL = "o1";
    private final static int PEILDATUM = 20170101;

    @Mock
    private BijhoudingVerzoekBericht bericht;

    private ElementBuilder builder;
    private BijhoudingPersoon persoon;
    private BijhoudingOnderzoek onderzoek;
    private int elementTeller;

    @Before
    public void setup() throws OngeldigeObjectSleutelException {
        elementTeller = 0;
        persoon = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE));
        onderzoek = BijhoudingOnderzoek.decorate(new Onderzoek(new Partij("test partij", "123456"), persoon.getDelegates().iterator().next()));
        persoon.addOnderzoek(onderzoek.getDelegate());
        final PersoonIDHistorie idHistorie = new PersoonIDHistorie(persoon.getDelegates().iterator().next());
        idHistorie.setId(1L);
        idHistorie.setAdministratienummer("1234567890");
        persoon.addPersoonIDHistorie(idHistorie);
        final OnderzoekHistorie onderzoekHistorie = new OnderzoekHistorie(PEILDATUM, StatusOnderzoek.IN_UITVOERING, onderzoek.getDelegate());
        onderzoekHistorie.setDatumTijdRegistratie(new Timestamp(getAdministratieveHandeling().getDatumTijdRegistratie().getTime() - 1000));
        onderzoek.addOnderzoekHistorie(onderzoekHistorie);
        final GegevenInOnderzoek gegevenInOnderzoek = new GegevenInOnderzoek(onderzoek.getDelegate(), Element.PERSOON_IDENTIFICATIENUMMERS_ADMINISTRATIENUMMER);
        gegevenInOnderzoek.setEntiteitOfVoorkomen(idHistorie);
        final GegevenInOnderzoekHistorie gegevenInOnderzoekHistorie = new GegevenInOnderzoekHistorie(gegevenInOnderzoek);
        gegevenInOnderzoekHistorie.setDatumTijdRegistratie(new Timestamp(getAdministratieveHandeling().getDatumTijdRegistratie().getTime() - 1000));
        gegevenInOnderzoek.addGegevenInOnderzoekHistorie(gegevenInOnderzoekHistorie);
        onderzoek.addGegevenInOnderzoek(gegevenInOnderzoek);

        builder = new ElementBuilder();
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, PERSOON_SLEUTEL)).thenReturn(persoon);
        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingOnderzoek.class, ONDERZOEK_SLEUTEL)).thenReturn(onderzoek);
    }

    @Test
    public void getSoortActie() {
        final RegistratieWijzigingOnderzoekActieElement actie = maakActie(PERSOON_SLEUTEL, ONDERZOEK_SLEUTEL, 20170101, StatusOnderzoek.GESTAAKT);
        assertEquals(SoortActie.REGISTRATIE_WIJZIGING_ONDERZOEK, actie.getSoortActie());
    }

    @Test
    public void verwerkingOK() {
        persoon.setBijhoudingSituatie(BijhoudingSituatie.INDIENER_IS_BIJHOUDINGSPARTIJ);
        final RegistratieWijzigingOnderzoekActieElement actie = maakActie(PERSOON_SLEUTEL, ONDERZOEK_SLEUTEL, 20170101, StatusOnderzoek.GESTAAKT);
        assertEquals(1, onderzoek.getOnderzoekHistorieSet().size());
        assertEquals(StatusOnderzoek.IN_UITVOERING, FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(onderzoek.getOnderzoekHistorieSet()).getStatusOnderzoek());
        actie.verwerk(bericht, getAdministratieveHandeling());
        assertEquals(2, onderzoek.getOnderzoekHistorieSet().size());
        assertEquals(StatusOnderzoek.GESTAAKT, FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(onderzoek.getOnderzoekHistorieSet()).getStatusOnderzoek());
    }

    @Test
    public void verwerkingNOK() {
        persoon.setBijhoudingSituatie(BijhoudingSituatie.OPNIEUW_INDIENEN);
        final RegistratieWijzigingOnderzoekActieElement actie = maakActie(PERSOON_SLEUTEL, ONDERZOEK_SLEUTEL, 20170101, StatusOnderzoek.GESTAAKT);
        assertEquals(1, onderzoek.getOnderzoekHistorieSet().size());
        assertEquals(StatusOnderzoek.IN_UITVOERING, FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(onderzoek.getOnderzoekHistorieSet()).getStatusOnderzoek());
        actie.verwerk(bericht, getAdministratieveHandeling());
        assertEquals(1, onderzoek.getOnderzoekHistorieSet().size());
        assertEquals(StatusOnderzoek.IN_UITVOERING, FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(onderzoek.getOnderzoekHistorieSet()).getStatusOnderzoek());
    }

    @Test
    public void getPeilDatum() {
        final RegistratieWijzigingOnderzoekActieElement actie = maakActie(PERSOON_SLEUTEL, ONDERZOEK_SLEUTEL, 20170101, StatusOnderzoek.GESTAAKT);
        assertEquals(new DatumElement(PEILDATUM), actie.getPeilDatum());
    }

    @Bedrijfsregel(Regel.R2607)
    @Test
    public void valideerSpecifiekeInhoud() {
        final RegistratieWijzigingOnderzoekActieElement actieGoed = maakActie(PERSOON_SLEUTEL, ONDERZOEK_SLEUTEL, 20170101, StatusOnderzoek.GESTAAKT);
        List<MeldingElement> meldingen = actieGoed.valideerSpecifiekeInhoud();
        controleerRegels(meldingen);
        final RegistratieWijzigingOnderzoekActieElement actieFout = maakActie(PERSOON_SLEUTEL, ONDERZOEK_SLEUTEL, 20170101, StatusOnderzoek.IN_UITVOERING);
        meldingen = actieFout.valideerSpecifiekeInhoud();
        controleerRegels(meldingen, Regel.R2607);
    }

    private RegistratieWijzigingOnderzoekActieElement maakActie(final String persoonSleutel, final String onderzoekSleutel, final int datumAanvangGeldigheid,
                                                                final StatusOnderzoek statusOnderzoek) {
        final OnderzoekGroepElement
                onderzoekGroep =
                OnderzoekGroepElement.getInstance("CI_onderzoek_groep_" + elementTeller, null, null, null, statusOnderzoek.getNaam());
        final OnderzoekElement onderzoek = OnderzoekElement.getInstance("CI_onderzoek_" + elementTeller, onderzoekSleutel, onderzoekGroep, null);
        onderzoek.setVerzoekBericht(bericht);
        final PersoonGegevensElement
                persoon =
                builder.maakPersoonGegevensElement("CI_persoon_" + elementTeller, persoonSleutel, null, new ElementBuilder.PersoonParameters().onderzoeken(
                        Collections.singletonList(onderzoek)));
        persoon.setVerzoekBericht(bericht);
        final RegistratieWijzigingOnderzoekActieElement
                result =
                new RegistratieWijzigingOnderzoekActieElement(
                        new AbstractBmrGroep.AttributenBuilder().objecttype("Actie").communicatieId("CI_actie_" + elementTeller).build(),
                        new DatumElement(datumAanvangGeldigheid), null, null, persoon);
        elementTeller++;
        return result;
    }
}
