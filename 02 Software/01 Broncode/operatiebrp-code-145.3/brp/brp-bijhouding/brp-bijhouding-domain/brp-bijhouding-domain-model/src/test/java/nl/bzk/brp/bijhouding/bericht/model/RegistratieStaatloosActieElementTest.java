/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static nl.bzk.brp.bijhouding.bericht.model.BmrAttribuutEnum.COMMUNICATIE_ID_ATTRIBUUT;
import static nl.bzk.brp.bijhouding.bericht.model.BmrAttribuutEnum.OBJECTTYPE_ATTRIBUUT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Nationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeboorteHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponent;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponentHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteitHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AdellijkeTitel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingSituatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutel;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutelServiceImpl;
import nl.bzk.algemeenbrp.services.objectsleutel.OngeldigeObjectSleutelException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

/**
 * Unittests voor {@link RegistratieStaatloosActieElement}.
 */
public class RegistratieStaatloosActieElementTest extends AbstractElementTest {

    private static final DatumElement PEIL_DATUM = new DatumElement(20160101);
    @Mock
    private BijhoudingVerzoekBericht bericht;
    @Mock
    private ActieElement dummyActie;

    private ElementBuilder builder;
    private Map<String, String> attr;

    @Before
    public void setup() {
        builder = new ElementBuilder();
        attr = new LinkedHashMap<>();
        attr.put(OBJECTTYPE_ATTRIBUUT.toString(), "objecttype");
        attr.put(COMMUNICATIE_ID_ATTRIBUUT.toString(), "ci_test");
        final ElementBuilder.AdministratieveHandelingParameters ahParams = new ElementBuilder.AdministratieveHandelingParameters();
        ahParams.acties(Collections.singletonList(dummyActie)).soort(AdministratieveHandelingElementSoort.GEBOORTE_IN_NEDERLAND).partijCode("5100").bronnen(
                Collections.emptyList());
        final AdministratieveHandelingElement administratieveHandelingElement = builder.maakAdministratieveHandelingElement("com_ah", ahParams);
        when(bericht.getAdministratieveHandeling()).thenReturn(administratieveHandelingElement);
    }

    @Test
    public void testSoortActie() {
        assertEquals(SoortActie.REGISTRATIE_STAATLOOS, maakElement(1112, null).getSoortActie());
    }

    @Test
    public void testPeildatum() {
        assertEquals(PEIL_DATUM, maakElement(1112, null).getPeilDatum());
    }

    @Test
    public void testVerwerk_persoonNietVerwerkbaar() {
        final RegistratieStaatloosActieElement actieElement = maakElement(1112, null);
        final PersoonElement persoon = actieElement.getPersoon();
        final BijhoudingPersoon persoonEntiteit = persoon.getPersoonEntiteit();
        persoonEntiteit.setBijhoudingSituatie(BijhoudingSituatie.AANVULLEN_EN_OPNIEUW_INDIENEN);
        final BRPActie actie = actieElement.verwerk(bericht, getAdministratieveHandeling());
        assertNull(actie);
        assertNull(persoonEntiteit.getPersoonIndicatie(SoortIndicatie.STAATLOOS));
    }

    @Test
    public void testVerwerk_verwerkbaar() {
        final RegistratieStaatloosActieElement actieElement = maakElement(1112, null);
        final PersoonElement persoon = actieElement.getPersoon();
        final BijhoudingPersoon persoonEntiteit = persoon.getPersoonEntiteit();
        persoonEntiteit.setBijhoudingSituatie(BijhoudingSituatie.INDIENER_IS_BIJHOUDINGSPARTIJ);
        final BRPActie actie = actieElement.verwerk(bericht, getAdministratieveHandeling());
        assertNotNull(actie);
        final PersoonIndicatie persoonIndicatie = persoonEntiteit.getPersoonIndicatie(SoortIndicatie.STAATLOOS);
        assertNotNull(persoonIndicatie);
    }

    @Test
    public void testValideer() {
        assertEquals(0, maakElement(1112, null).valideerSpecifiekeInhoud().size());
    }

    @Test
    public void testPersoonHeeftAlNationaliteit() {
        final List<MeldingElement> meldingen = maakElement(1112, NEDERLANDS).valideerSpecifiekeInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1816, meldingen.get(0).getRegel());
    }


    private RegistratieStaatloosActieElement maakElement(final Integer persoonObjectSleutel, final Nationaliteit nationaliteit) {
        try {
            final ElementBuilder.PersoonParameters persoonParameters = new ElementBuilder.PersoonParameters();
            persoonParameters.indicaties(maakIndicatie(true));
            final PersoonGegevensElement persoonElement = builder.maakPersoonGegevensElement("ci_persoon", persoonObjectSleutel.toString(), null, persoonParameters);
            RegistratieStaatloosActieElement element =
                    new RegistratieStaatloosActieElement(attr, PEIL_DATUM, null, Collections.emptyList(), persoonElement);
            persoonElement.setVerzoekBericht(bericht);
            element.setVerzoekBericht(bericht);

            maakPersoon(SoortPersoon.INGESCHREVENE, nationaliteit, persoonObjectSleutel, false, "Stam", null, null, Geslachtsaanduiding.MAN, "Arie", 19800526,
                    null);
            return element;
        } catch (OngeldigeObjectSleutelException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<IndicatieElement> maakIndicatie(final Boolean indicatie) {
        final String commId = indicatie != null ? "comm_id" : "comm_id2";
        return Collections.singletonList(builder
                .maakStaatloosIndicatieElement(commId, new ElementBuilder.IndicatieElementParameters().heeftIndicatie(indicatie)));
    }

    public BijhoudingPersoon maakPersoon(final SoortPersoon soortPersoon, final Nationaliteit nationaliteit, final long persoonId,
                                         final boolean voegBijhoudingHistorieToe, final String stam,
                                         final Predicaat predicaat, final AdellijkeTitel adelijkeTitel,
                                         final Geslachtsaanduiding geslachtsAanduiding, final String voornamen, final Integer geboortedatum,
                                         final Integer datumOverleden) throws OngeldigeObjectSleutelException {
        final Persoon delegate = new Persoon(soortPersoon);
        BijhoudingPersoon persoon = new BijhoudingPersoon(delegate);
        if (nationaliteit != null) {
            final PersoonNationaliteit nat = new PersoonNationaliteit(persoon, nationaliteit);
            nat.getPersoonNationaliteitHistorieSet().add(new PersoonNationaliteitHistorie(nat));
            persoon.getPersoonNationaliteitSet().add(nat);
        }
        if (geboortedatum != null) {
            final PersoonGeboorteHistorie
                    persoonGeboorteHistorie =
                    new PersoonGeboorteHistorie(persoon, geboortedatum, new LandOfGebied(LandOfGebied.CODE_NEDERLAND, "Nederland"));
            persoon.addPersoonGeboorteHistorie(persoonGeboorteHistorie);
        }

        if (voegBijhoudingHistorieToe || datumOverleden != null) {
            final PersoonBijhoudingHistorie bijHis;
            if (datumOverleden == null) {
                bijHis =
                        new PersoonBijhoudingHistorie(persoon, Z_PARTIJ, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL);
            } else {
                bijHis =
                        new PersoonBijhoudingHistorie(persoon, Z_PARTIJ, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.OVERLEDEN);
                bijHis.setDatumAanvangGeldigheid(datumOverleden);
            }
            persoon.getPersoonBijhoudingHistorieSet().add(bijHis);
        }
        persoon.setId(persoonId);
        persoon.setGeslachtsaanduiding(geslachtsAanduiding);
        final PersoonGeslachtsnaamcomponent geslachtsnaamcomponent = new PersoonGeslachtsnaamcomponent(persoon, 1);
        PersoonGeslachtsnaamcomponentHistorie persoonGeslachtsnaamcomponentHistorie = new PersoonGeslachtsnaamcomponentHistorie(geslachtsnaamcomponent, stam);
        persoonGeslachtsnaamcomponentHistorie.setPredicaat(predicaat);
        persoonGeslachtsnaamcomponentHistorie.setAdellijkeTitel(adelijkeTitel);
        geslachtsnaamcomponent.addPersoonGeslachtsnaamcomponentHistorie(persoonGeslachtsnaamcomponentHistorie);
        persoon.addPersoonGeslachtsnaamcomponent(geslachtsnaamcomponent);
        final PersoonSamengesteldeNaamHistorie psnHistorie = new PersoonSamengesteldeNaamHistorie(persoon, stam, false, false);
        psnHistorie.setVoornamen(voornamen);
        psnHistorie.setPredicaat(predicaat);
        psnHistorie.setAdellijkeTitel(adelijkeTitel);
        persoon.getPersoonSamengesteldeNaamHistorieSet().add(psnHistorie);

        when(bericht.getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, Long.toString(persoonId))).thenReturn(persoon);

        //OBJECTSLEUTELS BEHORENDE BIJ PERSONEN
        final ObjectSleutel sleutel = new ObjectSleutelServiceImpl().maakPersoonObjectSleutel(persoonId, persoonId);
        when(getObjectSleutelService().maakPersoonObjectSleutel(Long.toString(persoonId))).thenReturn(sleutel);
        return persoon;
    }
}
