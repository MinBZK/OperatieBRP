/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBijhoudingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Plaats;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortActieBrongebruik;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortActieBrongebruikSleutel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortDocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Voorvoegsel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.VoorvoegselSleutel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.services.objectsleutel.OngeldigeObjectSleutelException;
import org.junit.Before;
import org.mockito.Mockito;

/**
 * Abstracte class waar de mock-stappen in gedefinieerd kan worden voor het bestand
 * "succesvolle_administratieveHandeling.xml".
 */
public abstract class AbstractHuwelijkInNederlandTestBericht extends AbstractElementTest {
    private static final String INGESCHREVEN_PERSOON_OBJECT_SLEUTEL = "212121";
    private static final String INGESCHREVEN_PERSOON_OBJECT_SLEUTEL_PARTNER = "313131";
    private static final String SOORT_DOC_NAAM_NAAMGEBRUIK = "Verklaring of kennisgeving naamgebruik";
    private static final String PARTIJ_GEMEENTE_HELLEVOETSLUIS_CODE = "053001";
    private static final String HELLEVOETSLUIS = "Hellevoetsluis";
    private static final String GEMEENTE_CODE_HELLEVOETSLUIS = "1530";
    private BijhoudingPersoon ingeschrevenPersoon;
    private BijhoudingPersoon ingeschrevenPersoonPartner;
    private Partij partijGemeenteHellevoetsluis;
    private BijhoudingVerzoekBericht succesHuwelijkInNederlandBericht;
    private BijhoudingVerzoekBericht huwelijkTussenTweeIngeschrevenenBericht;

    @Before
    public void setUpSuccesHuwelijkInNederlandBericht() throws OngeldigeObjectSleutelException {
        partijGemeenteHellevoetsluis = new Partij("Gemeente Hellevoetsluis", "053001");
        ingeschrevenPersoon = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE));
        ingeschrevenPersoon.setId(1L);
        ingeschrevenPersoonPartner = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE));
        ingeschrevenPersoonPartner.setId(2L);
        final Gemeente
                gemeenteHellevoetsluis =
                new Gemeente(Short.parseShort(GEMEENTE_CODE_HELLEVOETSLUIS), HELLEVOETSLUIS, GEMEENTE_CODE_HELLEVOETSLUIS, partijGemeenteHellevoetsluis);
        final SoortDocument soortDocumentHuwelijk = new SoortDocument(SOORT_DOC_NAAM_HUWELIJK, SOORT_DOC_NAAM_HUWELIJK);
        soortDocumentHuwelijk.setRegistersoort('3');
        final SoortDocument soortDocumentNaamgebruik = new SoortDocument(SOORT_DOC_NAAM_NAAMGEBRUIK, SOORT_DOC_NAAM_NAAMGEBRUIK);
        final PersoonBijhoudingHistorie bijhoudingHistorie =
                new PersoonBijhoudingHistorie(ingeschrevenPersoon, partijGemeenteHellevoetsluis, Bijhoudingsaard.INGEZETENE, NadereBijhoudingsaard.ACTUEEL);
        ingeschrevenPersoon.addPersoonBijhoudingHistorie(bijhoudingHistorie);

        succesHuwelijkInNederlandBericht = VerzoekBerichtBuilder.maakSuccesHuwelijkInNederlandBericht();
        succesHuwelijkInNederlandBericht.setObjectSleutelIndex(getObjectSleutelIndex());
        huwelijkTussenTweeIngeschrevenenBericht = VerzoekBerichtBuilder.maakSuccesHuwelijkInNederlandBerichtTweeIngeschrevenen();
        huwelijkTussenTweeIngeschrevenenBericht.setObjectSleutelIndex(getObjectSleutelIndex());
        final SoortActieBrongebruikSleutel huwelijksakteHuwelijkSleutel =
                new SoortActieBrongebruikSleutel(
                        SoortActie.REGISTRATIE_AANVANG_HUWELIJK,
                        SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND,
                        soortDocumentHuwelijk);
        final SoortActieBrongebruikSleutel huwelijksakteGeslachtsnaamSleutel =
                new SoortActieBrongebruikSleutel(
                        SoortActie.REGISTRATIE_GESLACHTSNAAM,
                        SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND,
                        soortDocumentHuwelijk);
        final SoortActieBrongebruikSleutel naamgebruikSleutel =
                new SoortActieBrongebruikSleutel(
                        SoortActie.REGISTRATIE_NAAMGEBRUIK,
                        SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND,
                        soortDocumentNaamgebruik);

        when(getDynamischeStamtabelRepository().getPartijByCode(PARTIJ_GEMEENTE_HELLEVOETSLUIS_CODE)).thenReturn(partijGemeenteHellevoetsluis);
        when(getDynamischeStamtabelRepository().getSoortDocumentByNaam(SOORT_DOC_NAAM_HUWELIJK)).thenReturn(soortDocumentHuwelijk);
        when(getDynamischeStamtabelRepository().getSoortDocumentByNaam(SOORT_DOC_NAAM_NAAMGEBRUIK)).thenReturn(soortDocumentNaamgebruik);

        when(getDynamischeStamtabelRepository().getSoortActieBrongebruikBySoortActieBrongebruikSleutel(huwelijksakteHuwelijkSleutel)).thenReturn(
                new SoortActieBrongebruik(huwelijksakteHuwelijkSleutel));
        when(getDynamischeStamtabelRepository().getSoortActieBrongebruikBySoortActieBrongebruikSleutel(huwelijksakteGeslachtsnaamSleutel)).thenReturn(
                new SoortActieBrongebruik(huwelijksakteGeslachtsnaamSleutel));
        when(getDynamischeStamtabelRepository().getSoortActieBrongebruikBySoortActieBrongebruikSleutel(naamgebruikSleutel)).thenReturn(
                new SoortActieBrongebruik(naamgebruikSleutel));

        when(getObjectSleutelIndex().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, INGESCHREVEN_PERSOON_OBJECT_SLEUTEL)).thenReturn(
                ingeschrevenPersoon);
        when(getObjectSleutelIndex().getEntiteitVoorObjectSleutel(BijhoudingPersoon.class, INGESCHREVEN_PERSOON_OBJECT_SLEUTEL_PARTNER)).thenAnswer(
                invocationOnMock -> ingeschrevenPersoonPartner);
        Mockito.doAnswer(invocationOnMock -> {
            ingeschrevenPersoonPartner = (BijhoudingPersoon) invocationOnMock.getArguments()[2];
            return null;
        }).when(getObjectSleutelIndex()).vervangEntiteitMetId(any(), any(Number.class), any(BijhoudingPersoon.class));

        when(getDynamischeStamtabelRepository().getGemeenteByPartijcode(PARTIJ_GEMEENTE_HELLEVOETSLUIS_CODE)).thenReturn(gemeenteHellevoetsluis);
        when(getDynamischeStamtabelRepository().getPlaatsByPlaatsNaam(HELLEVOETSLUIS)).thenReturn(new Plaats(HELLEVOETSLUIS));
        when(getDynamischeStamtabelRepository().getLandOfGebiedByCode("6008")).thenReturn(new LandOfGebied("6008", "Brazilie"));
        when(getDynamischeStamtabelRepository().getGemeenteByGemeentecode(GEMEENTE_CODE_HELLEVOETSLUIS)).thenReturn(gemeenteHellevoetsluis);

        final VoorvoegselSleutel partnerVoorvoegselSleutel = new VoorvoegselSleutel(' ', "dos");
        when(getDynamischeStamtabelRepository().getVoorvoegselByVoorvoegselSleutel(partnerVoorvoegselSleutel)).thenReturn(
                new Voorvoegsel(partnerVoorvoegselSleutel));

        final VoorvoegselSleutel geslachtsnaamVoorvoegselSleutel = new VoorvoegselSleutel(' ', "van");
        when(getDynamischeStamtabelRepository().getVoorvoegselByVoorvoegselSleutel(geslachtsnaamVoorvoegselSleutel)).thenReturn(
                new Voorvoegsel(geslachtsnaamVoorvoegselSleutel));

        final VoorvoegselSleutel naamgebruikVoorvoegselSleutel = new VoorvoegselSleutel('\'', "s");
        when(getDynamischeStamtabelRepository().getVoorvoegselByVoorvoegselSleutel(naamgebruikVoorvoegselSleutel)).thenReturn(
                new Voorvoegsel(naamgebruikVoorvoegselSleutel));
    }

    public BijhoudingVerzoekBericht getSuccesHuwelijkInNederlandBericht() {
        return succesHuwelijkInNederlandBericht;
    }

    public BijhoudingVerzoekBericht getHuwelijkTussenTweeIngeschrevenenBericht() {
        return huwelijkTussenTweeIngeschrevenenBericht;
    }

    public BijhoudingPersoon getIngeschrevenPersoon() {
        return ingeschrevenPersoon;
    }

    public BijhoudingPersoon getIngeschrevenPersoonPartner() {
        return ingeschrevenPersoonPartner;
    }

    public Partij getPartijGemeenteHellevoetsluis() {
        return partijGemeenteHellevoetsluis;
    }

    PersoonGegevensElement createPersoon(final Map<String, String> att, final SamengesteldeNaamElement samnaam, final IdentificatienummersElement identificatienummers,
                                 final GeboorteElement geboorte, final GeslachtsaanduidingElement geslachtsaand) {
        final ElementBuilder builder = new ElementBuilder();
        final ElementBuilder.PersoonParameters params = new ElementBuilder.PersoonParameters();
        params.samengesteldeNaam(samnaam);
        params.identificatienummers(identificatienummers);
        params.geboorte(geboorte);
        params.geslachtsaanduiding(geslachtsaand);
        return builder.maakPersoonGegevensElement(att, params);
    }
}
