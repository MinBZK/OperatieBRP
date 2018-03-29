/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.dal.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AanduidingInhoudingOfVermissingReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Aangever;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Nationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Plaats;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenBeeindigingRelatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerkrijgingNLNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerliesNLNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenWijzigingVerblijf;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortActieBrongebruik;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortActieBrongebruikSleutel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortDocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortNederlandsReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Verblijfsrecht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Voorvoegsel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.VoorvoegselSleutel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.brp.bijhouding.dal.AbstractRepositoryTest;
import nl.bzk.brp.bijhouding.dal.ApplicationContextProvider;
import org.junit.Test;

/**
 * Testen voor {@link DynamischeStamtabelRepositoryImpl}.
 */
public class DynamischeStamtabelRepositoryImplTest extends AbstractRepositoryTest {

    @Inject
    private DynamischeStamtabelRepository dynamischeStamtabelRepository;

    @Test
    public void testApplicationContextProvider() {
        assertNotNull(ApplicationContextProvider.getDynamischeStamtabelRepository());
    }

    @Test
    public void testGetAangeverByCode() {
        final Aangever aangeverG = dynamischeStamtabelRepository.getAangeverByCode('G');
        final Aangever aangeverV = dynamischeStamtabelRepository.getAangeverByCode('V');
        assertNotNull(aangeverG);
        assertNotNull(aangeverV);
        assertEquals("Gezaghouder", aangeverG.getNaam());
        assertEquals("Verzorger", aangeverV.getNaam());
    }

    @Test
    public void testGetLandOfGebiedByCode() {
        final LandOfGebied landOfGebied1 = dynamischeStamtabelRepository.getLandOfGebiedByCode("9076");
        final LandOfGebied landOfGebied2 = dynamischeStamtabelRepository.getLandOfGebiedByCode("7053");
        final LandOfGebied landOfGebied3 = dynamischeStamtabelRepository.getLandOfGebiedByCode("7053");
        final LandOfGebied landOfGebied4 = dynamischeStamtabelRepository.getLandOfGebiedByCode("705a");
        assertNotNull(landOfGebied1);
        assertNotNull(landOfGebied2);
        assertNotNull(landOfGebied3);
        assertNull(landOfGebied4);
        assertEquals("Abessinië", landOfGebied1.getNaam());
        assertEquals("Samoa", landOfGebied2.getNaam());
        assertEquals("Samoa", landOfGebied3.getNaam());
    }

    @Test
    public void testGetLandOfGebiedByCodeNull() {
        final LandOfGebied landOfGebied = dynamischeStamtabelRepository.getLandOfGebiedByCode("tekst");
        assertNull(landOfGebied);
    }

    @Test
    public void testGetNationaliteitByNationaliteitcode() {
        final Nationaliteit nationaliteit1 = dynamischeStamtabelRepository.getNationaliteitByNationaliteitcode("0000");
        final Nationaliteit nationaliteit2 = dynamischeStamtabelRepository.getNationaliteitByNationaliteitcode("0443");
        assertNotNull(nationaliteit1);
        assertNotNull(nationaliteit2);
        assertEquals("Onbekend", nationaliteit1.getNaam());
        assertEquals("Micronesische", nationaliteit2.getNaam());
    }

    @Test
    public void testGetGemeenteByGemeentecode() {
        final Gemeente gemeente1 = dynamischeStamtabelRepository.getGemeenteByGemeentecode("0000");
        final Gemeente gemeente2 = dynamischeStamtabelRepository.getGemeenteByGemeentecode("1921");
        final Gemeente gemeente3 = dynamischeStamtabelRepository.getGemeenteByGemeentecode("1921");
        final Gemeente gemeente4 = dynamischeStamtabelRepository.getGemeenteByGemeentecode("192a");
        assertNotNull(gemeente1);
        assertNotNull(gemeente2);
        assertNotNull(gemeente3);
        assertNull(gemeente4);
        assertEquals("Onbekend", gemeente1.getNaam());
        assertEquals("De Friese Meren", gemeente2.getNaam());
        assertEquals("De Friese Meren", gemeente3.getNaam());
    }

    @Test
    public void testGetGemeenteByGemeentecodeNull() {
        final Gemeente gemeente = dynamischeStamtabelRepository.getGemeenteByGemeentecode("Tekst");
        assertNull(gemeente);
    }

    @Test
    public void testGetGemeenteByPartijcode() {
        final Gemeente gemeente1 = dynamischeStamtabelRepository.getGemeenteByPartijcode("000001");
        final Gemeente gemeente2 = dynamischeStamtabelRepository.getGemeenteByPartijcode("192101");
        assertNotNull(gemeente1);
        assertNotNull(gemeente2);
        assertEquals("Onbekend", gemeente1.getNaam());
        assertEquals("De Friese Meren", gemeente2.getNaam());
    }

    @Test
    public void testGetPartijByCode() {
        final Partij partij1 = dynamischeStamtabelRepository.getPartijByCode("000000");
        final Partij partij2 = dynamischeStamtabelRepository.getPartijByCode("415101");
        final Partij partij3 = dynamischeStamtabelRepository.getPartijByCode("415101");
        assertNull(dynamischeStamtabelRepository.getPartijByCode("abcd"));
        assertNotNull(partij1);
        assertNotNull(partij2);
        assertNotNull(partij3);
        assertEquals("Onbekend", partij1.getNaam());
        assertEquals("Gemeenschappelijke regeling De Bevelanden", partij2.getNaam());
        assertEquals("Gemeenschappelijke regeling De Bevelanden", partij3.getNaam());
    }

    @Test
    public void testGetRedenBeeindigingRelatieByCode() {
        final RedenBeeindigingRelatie beeindigingRelatie1 = dynamischeStamtabelRepository.getRedenBeeindigingRelatieByCode('?');
        final RedenBeeindigingRelatie beeindigingRelatie2 = dynamischeStamtabelRepository.getRedenBeeindigingRelatieByCode('Z');
        assertNotNull(beeindigingRelatie1);
        assertNotNull(beeindigingRelatie2);
        assertEquals("Onbekend", beeindigingRelatie1.getOmschrijving());
        assertEquals("Omzetting", beeindigingRelatie2.getOmschrijving());
    }

    @Test
    public void testGetRedenVerkrijgingNLNationaliteitByCode() {
        final RedenVerkrijgingNLNationaliteit verkrijgingNLNationaliteit1 =
                dynamischeStamtabelRepository.getRedenVerkrijgingNLNationaliteitByCode("000");
        final RedenVerkrijgingNLNationaliteit verkrijgingNLNationaliteit2 =
                dynamischeStamtabelRepository.getRedenVerkrijgingNLNationaliteitByCode("185");
        assertNotNull(verkrijgingNLNationaliteit1);
        assertNotNull(verkrijgingNLNationaliteit2);
        assertEquals("Onbekend", verkrijgingNLNationaliteit1.getOmschrijving());
        assertEquals("Rijkswet Nederlanderschap 1984 ivm wijziging 2010, art. 6, lid 1, sub o", verkrijgingNLNationaliteit2.getOmschrijving());
    }

    @Test
    public void testGetRedenVerliesNLNationaliteitByCode() {
        final RedenVerliesNLNationaliteit verliesNLNationaliteit1 = dynamischeStamtabelRepository.getRedenVerliesNLNationaliteitByCode("034");
        final RedenVerliesNLNationaliteit verliesNLNationaliteit2 = dynamischeStamtabelRepository.getRedenVerliesNLNationaliteitByCode("188");
        assertNotNull(verliesNLNationaliteit1);
        assertNotNull(verliesNLNationaliteit2);
        assertEquals("Toescheidingsovereenkomst Nederland-Suriname, art. 2, lid 1, jo. art. 5, lid 2", verliesNLNationaliteit1.getOmschrijving());
        assertEquals("Rijkswet Nederlanderschap 1984 ivm wijziging 2010, art. 15, lid 1, sub f", verliesNLNationaliteit2.getOmschrijving());
    }

    @Test
    public void testGetAanduidingInhoudingOfVermissingReisdocumentByCode() {
        final AanduidingInhoudingOfVermissingReisdocument ingehouden =
                dynamischeStamtabelRepository.getAanduidingInhoudingOfVermissingReisdocumentByCode('I');
        final AanduidingInhoudingOfVermissingReisdocument onbekend =
                dynamischeStamtabelRepository.getAanduidingInhoudingOfVermissingReisdocumentByCode('?');
        assertNotNull(ingehouden);
        assertNotNull(onbekend);
        assertEquals("Ingehouden, ingeleverd", ingehouden.getNaam());
        assertEquals("Onbekend", onbekend.getNaam());
    }

    @Test
    public void testGetRedenWijzigingVerblijf() {
        final RedenWijzigingVerblijf persoon = dynamischeStamtabelRepository.getRedenWijzigingVerblijf('P');
        final RedenWijzigingVerblijf onbekend = dynamischeStamtabelRepository.getRedenWijzigingVerblijf('?');
        assertNotNull(persoon);
        assertNotNull(onbekend);
        assertEquals("Aangifte door persoon", persoon.getNaam());
        assertEquals("Onbekend", onbekend.getNaam());
    }

    @Test
    public void testGetSoortDocumentByNaam() {
        final SoortDocument geboorteakte = dynamischeStamtabelRepository.getSoortDocumentByNaam("Geboorteakte");
        final SoortDocument aNummer = dynamischeStamtabelRepository.getSoortDocumentByNaam("Besluit wijziging A-nummer");
        assertNotNull(geboorteakte);
        assertNotNull(aNummer);
        assertEquals(new Character('1'), geboorteakte.getRegistersoort());
        assertEquals("Toekenning of wijziging van een administratienummer, zoals bedoeld in artikel 4.9 Wet BRP", aNummer.getOmschrijving());
    }

    @Test
    public void testGetSoortNederlandsReisdocumentByCode() {
        final SoortNederlandsReisdocument onbekend = dynamischeStamtabelRepository.getSoortNederlandsReisdocumentByCode("?");
        final SoortNederlandsReisdocument nationaalPaspoort = dynamischeStamtabelRepository.getSoortNederlandsReisdocumentByCode("ZN");
        assertNotNull(onbekend);
        assertNotNull(nationaalPaspoort);
        assertEquals("Onbekend", onbekend.getOmschrijving());
        assertEquals("Nationaal paspoort (zakenpaspoort)", nationaalPaspoort.getOmschrijving());
    }

    @Test
    public void testGetVerblijfsrechtByCode() {
        final Verblijfsrecht onbekend = dynamischeStamtabelRepository.getVerblijfsrechtByCode("00");
        final Verblijfsrecht geen = dynamischeStamtabelRepository.getVerblijfsrechtByCode("98");
        assertNotNull(onbekend);
        assertNotNull(geen);
        assertEquals("Onbekend", onbekend.getOmschrijving());
        assertEquals("geen verblijfstitel (meer)", geen.getOmschrijving());
    }

    @Test
    public void testGetSoortPartijByNaam() {
        assertNull(dynamischeStamtabelRepository.getSoortPartijByNaam(""));
    }

    @Test
    public void testGetPlaatsByNaam() {
        final Plaats plaats1 = dynamischeStamtabelRepository.getPlaatsByPlaatsNaam("Hoogerheide");
        final Plaats plaats2 = dynamischeStamtabelRepository.getPlaatsByPlaatsNaam("Soest");
        assertNotNull(plaats1);
        assertNotNull(plaats2);
        assertEquals("Hoogerheide", plaats1.getNaam());
        assertEquals("Soest", plaats2.getNaam());
    }

    @Test
    public void testGetPlaatsByPlaatscodeNull() {
        final Plaats plaats = dynamischeStamtabelRepository.getPlaatsByPlaatsNaam("Tekst");
        assertNull(plaats);
    }

    @Test
    public void testGetVoorvoegselBySleutel() {
        final Voorvoegsel voorvoegselMetIngevuldScheidingsteken =
                dynamischeStamtabelRepository.getVoorvoegselByVoorvoegselSleutel(new VoorvoegselSleutel('\'', "dal"));
        final Voorvoegsel voorvoegselMetSpatieScheidingsteken =
                dynamischeStamtabelRepository.getVoorvoegselByVoorvoegselSleutel(new VoorvoegselSleutel(' ', "van"));
        assertNotNull(voorvoegselMetIngevuldScheidingsteken);
        assertNotNull(voorvoegselMetSpatieScheidingsteken);
    }

    @Test
    public void testGetSoortActieBrongebruikBySleutel() {
        final SoortDocument soortDocument = dynamischeStamtabelRepository.getSoortDocumentByNaam("Huwelijksakte");
        assertNotNull(soortDocument);
        final SoortActieBrongebruik soortActieBrongebruik =
                dynamischeStamtabelRepository.getSoortActieBrongebruikBySoortActieBrongebruikSleutel(new SoortActieBrongebruikSleutel(
                        SoortActie.REGISTRATIE_AANVANG_HUWELIJK,
                        SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND,
                        soortDocument));
        assertNotNull(soortActieBrongebruik);
    }
}
