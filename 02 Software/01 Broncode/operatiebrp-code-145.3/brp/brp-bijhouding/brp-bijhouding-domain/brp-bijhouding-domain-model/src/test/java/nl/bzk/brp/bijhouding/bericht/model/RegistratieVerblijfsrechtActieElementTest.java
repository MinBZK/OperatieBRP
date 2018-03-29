/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static nl.bzk.brp.bijhouding.bericht.model.BmrAttribuutEnum.COMMUNICATIE_ID_ATTRIBUUT;
import static nl.bzk.brp.bijhouding.bericht.model.BmrAttribuutEnum.OBJECTTYPE_ATTRIBUUT;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Nationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteitHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Verblijfsrecht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingSituatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RegistratieVerblijfsrechtActieElementTest extends AbstractElementTest {
    private ElementBuilder builder;
    private BijhoudingVerzoekBericht bericht;
    private Map<String, String> attributen;
    private BijhoudingPersoon persoon;
    private AdministratieveHandelingElement ah;
    private RegistratieVerblijfsrechtActieElement actie;

    @Before
    public void setUpTests() {
        builder = new ElementBuilder();
        bericht = mock(BijhoudingVerzoekBericht.class);

        // actie
        final ElementBuilder.VerblijfsrechtParameters vrechtParams = new ElementBuilder.VerblijfsrechtParameters();
        vrechtParams.datumAanvang(20010101).datumMededeling(20010101).aanduidingCode("21").datumVoorzienEinde(20100101);
        final VerblijfsrechtElement vRecht = builder.maakVerblijfsrechtElement("com_vr", vrechtParams);
        final ElementBuilder.PersoonParameters params = new ElementBuilder.PersoonParameters();
        params.verblijfsrecht(vRecht);
        final PersoonGegevensElement persoonElement = builder.maakPersoonGegevensElement("p_id", "1234", null, params);
        persoonElement.setVerzoekBericht(bericht);
        actie = builder.maakRegistratieVerblijfsrechtActieElement("com_id", 20100101, persoonElement);
        actie.setVerzoekBericht(bericht);

        //administratieve handeling
        final ElementBuilder.AdministratieveHandelingParameters ahParams = new ElementBuilder.AdministratieveHandelingParameters();
        ahParams.soort(AdministratieveHandelingElementSoort.WIJZIGING_VERBLIJFSRECHT);
        ahParams.partijCode("1");
        final List<ActieElement> acties = new LinkedList<>();
        acties.add(actie);
        ahParams.acties(acties);
        ah = builder.maakAdministratieveHandelingElement("ah_id", ahParams);
        when(bericht.getAdministratieveHandeling()).thenReturn(ah);
        attributen = new LinkedHashMap<>();
        attributen.put(OBJECTTYPE_ATTRIBUUT.toString(), "objecttype");
        attributen.put(COMMUNICATIE_ID_ATTRIBUUT.toString(), "ci_test");

        //mocks
        final Partij partij = new Partij("partij", "000001");
        partij.addPartijRol(new PartijRol(partij, Rol.BIJHOUDINGSORGAAN_COLLEGE));
        persoon = new BijhoudingPersoon(new Persoon(SoortPersoon.INGESCHREVENE));
        persoon.setId(1L);

        final PersoonBijhoudingHistorie
                bijhoudingHistorie =
                new PersoonBijhoudingHistorie(persoon, partij, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL);
        bijhoudingHistorie.setDatumTijdRegistratie(Timestamp.from(Instant.now()));
        persoon.addPersoonBijhoudingHistorie(bijhoudingHistorie);

        when(bericht.getStuurgegevens()).thenReturn(getStuurgegevensElement());

        when(bericht.getEntiteitVoorObjectSleutel(any(Class.class), anyString())).thenReturn(persoon);
        when(bericht.getDatumOntvangst()).thenReturn(new DatumElement(DatumUtil.vandaag()));
        when(bericht.getZendendePartij()).thenReturn(partij);

    }

    @Test
    public void R2326_Indicatie_behandeldAlsNederlander() {
        final BijhoudingPersoon persoonEntiteit = actie.getPersoon().getPersoonEntiteit();
        final PersoonIndicatie indicatie = new PersoonIndicatie(persoonEntiteit, SoortIndicatie.BEHANDELD_ALS_NEDERLANDER);
        final PersoonIndicatieHistorie his = new PersoonIndicatieHistorie(indicatie, true);
        indicatie.getPersoonIndicatieHistorieSet().add(his);
        persoonEntiteit.getPersoonIndicatieSet().add(indicatie);
        final List<MeldingElement> meldingen = actie.valideer();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2326, meldingen.get(0).getRegel());
    }

    @Test
    public void R2326_Indicatie_behandeldAlsNederlander_vervallen() {
        final BijhoudingPersoon persoonEntiteit = actie.getPersoon().getPersoonEntiteit();
        final PersoonIndicatie indicatie = new PersoonIndicatie(persoonEntiteit, SoortIndicatie.BEHANDELD_ALS_NEDERLANDER);
        final PersoonIndicatieHistorie his = new PersoonIndicatieHistorie(indicatie, true);
        his.setDatumTijdVerval(new Timestamp(DatumUtil.nu().getTime()));
        indicatie.getPersoonIndicatieHistorieSet().add(his);
        persoonEntiteit.getPersoonIndicatieSet().add(indicatie);
        final List<MeldingElement> meldingen = actie.valideer();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void R2326_Indicatie_behandeldAlsNederlander_voor_Peildatum() {
        final BijhoudingPersoon persoonEntiteit = actie.getPersoon().getPersoonEntiteit();
        final PersoonIndicatie indicatie = new PersoonIndicatie(persoonEntiteit, SoortIndicatie.BEHANDELD_ALS_NEDERLANDER);
        final PersoonIndicatieHistorie his = new PersoonIndicatieHistorie(indicatie, true);
        his.setDatumEindeGeldigheid(20001231);
        indicatie.getPersoonIndicatieHistorieSet().add(his);
        persoonEntiteit.getPersoonIndicatieSet().add(indicatie);
        final List<MeldingElement> meldingen = actie.valideer();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void R2326_Indicatie_behandeldAlsNederlander_na_Peildatum() {
        final BijhoudingPersoon persoonEntiteit = actie.getPersoon().getPersoonEntiteit();
        final PersoonIndicatie indicatie = new PersoonIndicatie(persoonEntiteit, SoortIndicatie.BEHANDELD_ALS_NEDERLANDER);
        final PersoonIndicatieHistorie his = new PersoonIndicatieHistorie(indicatie, true);
        his.setDatumAanvangGeldigheid(20010102);
        indicatie.getPersoonIndicatieHistorieSet().add(his);
        persoonEntiteit.getPersoonIndicatieSet().add(indicatie);
        final List<MeldingElement> meldingen = actie.valideer();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void R1901_Nationaliteit_NL() {
        final BijhoudingPersoon persoonEntiteit = actie.getPersoon().getPersoonEntiteit();
        final PersoonNationaliteit nationaliteit = new PersoonNationaliteit(persoonEntiteit, new Nationaliteit("Nederlandse", Nationaliteit.NEDERLANDSE));
        final PersoonNationaliteitHistorie his = new PersoonNationaliteitHistorie(nationaliteit);
        his.setDatumTijdRegistratie(Timestamp.from(Instant.now()));
        his.setDatumAanvangGeldigheid(20000101);
        nationaliteit.getPersoonNationaliteitHistorieSet().add(his);
        persoonEntiteit.getPersoonNationaliteitSet().add(nationaliteit);
        final List<MeldingElement> meldingen = actie.valideer();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1901, meldingen.get(0).getRegel());
    }

    @Test
    public void R1901_Nationaliteit_NL_na_peildatum() {
        final BijhoudingPersoon persoonEntiteit = actie.getPersoon().getPersoonEntiteit();
        final PersoonNationaliteit nationaliteit = new PersoonNationaliteit(persoonEntiteit, new Nationaliteit("Nederlandse", Nationaliteit.NEDERLANDSE));
        final PersoonNationaliteitHistorie his = new PersoonNationaliteitHistorie(nationaliteit);
        his.setDatumTijdRegistratie(Timestamp.from(Instant.now()));
        his.setDatumAanvangGeldigheid(20010102);
        nationaliteit.getPersoonNationaliteitHistorieSet().add(his);
        persoonEntiteit.getPersoonNationaliteitSet().add(nationaliteit);
        final List<MeldingElement> meldingen = actie.valideer();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void R1901_Nationaliteit_NL_beeindigd_voor_peildatum() {
        final BijhoudingPersoon persoonEntiteit = actie.getPersoon().getPersoonEntiteit();
        final PersoonNationaliteit nationaliteit = new PersoonNationaliteit(persoonEntiteit, new Nationaliteit("Nederlandse", Nationaliteit.NEDERLANDSE));
        final PersoonNationaliteitHistorie his = new PersoonNationaliteitHistorie(nationaliteit);
        his.setDatumTijdRegistratie(Timestamp.from(Instant.now()));
        his.setDatumEindeGeldigheid(20001231);
        nationaliteit.getPersoonNationaliteitHistorieSet().add(his);
        persoonEntiteit.getPersoonNationaliteitSet().add(nationaliteit);
        final List<MeldingElement> meldingen = actie.valideer();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void R1901_Nationaliteit_NL_Vervallen() {
        final BijhoudingPersoon persoonEntiteit = actie.getPersoon().getPersoonEntiteit();
        final PersoonNationaliteit nationaliteit = new PersoonNationaliteit(persoonEntiteit, new Nationaliteit("Nederlandse", Nationaliteit.NEDERLANDSE));
        final PersoonNationaliteitHistorie his = new PersoonNationaliteitHistorie(nationaliteit);
        his.setDatumTijdRegistratie(Timestamp.from(Instant.now()));
        his.setDatumTijdVerval(new Timestamp(DatumUtil.nu().getTime()));
        nationaliteit.getPersoonNationaliteitHistorieSet().add(his);
        persoonEntiteit.getPersoonNationaliteitSet().add(nationaliteit);
        final List<MeldingElement> meldingen = actie.valideer();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void testVerwerking() {
        BijhoudingVerzoekBericht verzoekBerichtMock = mock(BijhoudingVerzoekBericht.class);
        final BijhoudingPersoon persoonEntiteit = actie.getPersoon().getPersoonEntiteit();
        persoonEntiteit.setBijhoudingSituatie(BijhoudingSituatie.AUTOMATISCHE_FIAT);
        AdministratieveHandeling ahMock = mock(AdministratieveHandeling.class);
        when(ahMock.getPartij()).thenReturn(new Partij("partijNaam", "001234"));
        when(ahMock.getDatumTijdRegistratie()).thenReturn(new Timestamp(System.currentTimeMillis()));
        final Verblijfsrecht verblijfsRecht = new Verblijfsrecht("22", "verblijfsRecht");
        when(getDynamischeStamtabelRepository().getVerblijfsrechtByCode(anyString())).thenReturn(verblijfsRecht);

        assertEquals(0, persoonEntiteit.getPersoonVerblijfsrechtHistorieSet().size());

        actie.verwerk(verzoekBerichtMock, ahMock);

        assertEquals(1, persoonEntiteit.getPersoonVerblijfsrechtHistorieSet().size());
        assertEquals(actie.getPersoon().getVerblijfsrecht().getDatumAanvang().getWaarde(), persoonEntiteit.getPersoonVerblijfsrechtHistorieSet().
                iterator().next().getDatumAanvangVerblijfsrecht());
    }

}
