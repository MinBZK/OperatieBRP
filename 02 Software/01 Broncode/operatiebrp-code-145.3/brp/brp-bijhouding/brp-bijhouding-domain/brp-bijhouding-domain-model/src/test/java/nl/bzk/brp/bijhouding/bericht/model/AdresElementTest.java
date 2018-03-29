/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyChar;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Aangever;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Plaats;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenWijzigingVerblijf;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import org.junit.Before;
import org.junit.Test;

/**
 * UT voor {@link AdresElement}.
 */
public class AdresElementTest extends AbstractElementTest {
    private ElementBuilder.AdresElementParameters params;
    private AdresElement adresElement;
    private ElementBuilder builder;
    private Gemeente gemeente;

    private ElementBuilder.AdresElementParameters defaultParams =  new ElementBuilder.AdresElementParameters("soort", 'p', 20160101, "0017");

    @Before
    public void setup() {
        gemeente = new Gemeente(Short.parseShort("17"), "Groningen",  "0018", new Partij("Gemeente Groningen", "000014"));
        builder = new ElementBuilder();
        params = new ElementBuilder.AdresElementParameters("soort", 'A', 20160101, "0017");

        adresElement = builder.maakAdres("com_adres2", params);

        when(getDynamischeStamtabelRepository().getGemeenteByGemeentecode("0017")).thenReturn(gemeente);
        when(getDynamischeStamtabelRepository().getRedenWijzigingVerblijf(anyChar())).thenReturn(new RedenWijzigingVerblijf('P', "Persoonlijk"));
        when(getDynamischeStamtabelRepository().getRedenWijzigingVerblijf(anyChar())).thenReturn(new RedenWijzigingVerblijf('A', "Ambtshalve"));
        when(getDynamischeStamtabelRepository().getAangeverByCode(anyChar())).thenReturn(new Aangever('C', "Aangever", "test aangever"));
        when(getDynamischeStamtabelRepository().getPlaatsByPlaatsNaam(anyString())).thenReturn(new Plaats("plaats"));
    }


    @Test
    public void controleerGemeenteGeldig() {
        vulAdresElementMetGeldigeBAG();
        final List<MeldingElement> meldingen = adresElement.valideerInhoud();
        assertEquals(0, meldingen.size());
    }
    @Test
    public void controleerGemeenteOnGeldig() {
        vulAdresElementMetGeldigeBAG();
        when(getDynamischeStamtabelRepository().getGemeenteByGemeentecode("0017")).thenReturn(null);
        final List<MeldingElement> meldingen = adresElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1650, meldingen.get(0).getRegel());
    }

    @Test
    public void controleerRedenWijzigingOngeldig() {
        vulAdresElementMetGeldigeBAG();
        when(getDynamischeStamtabelRepository().getRedenWijzigingVerblijf(anyChar())).thenReturn(null);
        final List<MeldingElement> meldingen = adresElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1924, meldingen.get(0).getRegel());
    }

    @Test
    public void R1925() {
        params.setLocatieomschrijving(new StringElement("lokatie omschrijving"));
        params.setRedenWijzigingCode(new CharacterElement('P'));
        params.setAangeverAdreshoudingCode(new CharacterElement('P'));
        adresElement = builder.maakAdres("com_adres", params);
        when(getDynamischeStamtabelRepository().getAangeverByCode(anyChar())).thenReturn(null);
        final List<MeldingElement> meldingen = adresElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1925, meldingen.get(0).getRegel());
    }

    @Test
    public void controleerGemeenteOnGeldigOpDatum() {
        vulAdresElementMetGeldigeBAG();
        gemeente.setDatumAanvangGeldigheid(20150101);
        gemeente.setDatumEindeGeldigheid(20150202);
        final List<MeldingElement> meldingen = adresElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1379, meldingen.get(0).getRegel());
    }

    @Test
    public void controleerGemeenteDatumAanvangAdreshoudingNull() {
        vulParamsMetGeldigeBag();
        params.datumAanvangAdreshouding = null;
        adresElement = builder.maakAdres("com_adres", params);
        List<MeldingElement> meldingen = adresElement.valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void R1374_MaandOnbekend() {
        vulAdresElementMetGeldigeBAG();
        params.datumAanvangAdreshouding =  new DatumElement(1234_00_78);
        adresElement = builder.maakAdres("com_adres", params);
        final List<MeldingElement> meldingen = adresElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1374, meldingen.get(0).getRegel());
    }

    @Test
    public void R1374_DagOnbekend() {
        vulAdresElementMetGeldigeBAG();
        params.datumAanvangAdreshouding =  new DatumElement(1234_56_00);
        adresElement = builder.maakAdres("com_adres", params);
        final List<MeldingElement> meldingen = adresElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1374, meldingen.get(0).getRegel());
    }

    @Test
    public void R1374_GeenGregoriaanseCorrecteDatum_MaarWelToegestaan() {
        vulAdresElementMetGeldigeBAG();
        params.datumAanvangAdreshouding =  new DatumElement(1234_56_78);
        adresElement = builder.maakAdres("com_adres", params);
        final List<MeldingElement> meldingen = adresElement.valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void controleerLocatieOmschrijvingLengteGeldig() {
        params.setLocatieomschrijving(new StringElement("Geldige Locatie Omschrijving"));
        adresElement = builder.maakAdres("com_adres", params);
        final List<MeldingElement> meldingen = adresElement.valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void R1357() {
        vulParamsMetGeldigeBag();
        params.setLocatieomschrijving(new StringElement("Geldige Locatie Omschrijving"));
        adresElement = builder.maakAdres("com_adres", params);
        final List<MeldingElement> meldingen = adresElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1357, meldingen.get(0).getRegel());
    }

    @Test
    public void controleerLocatieOmschrijvingLengtePreciesGeldig() {
        params.setLocatieomschrijving(new StringElement("Een precies geldige locOmschrijving"));
        adresElement = builder.maakAdres("com_adres", params);
        final List<MeldingElement> meldingen = adresElement.valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void controleerLocatieOmschrijvingLengteOnGeldig() {
        params.setLocatieomschrijving(new StringElement("Een ongeldige locatie omschrijving te lang"));
        adresElement = builder.maakAdres("com_adres", params);
        final List<MeldingElement> meldingen = adresElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1375, meldingen.get(0).getRegel());
    }
    @Test
    public void controleerR1358_BAG() {
        vulAdresElementMetGeldigeBAG();
        params.setAfgekorteNaamOpenbareRuimte(null);
        adresElement = builder.maakAdres("com_adres", params);
        final List<MeldingElement> meldingen = adresElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1358, meldingen.get(0).getRegel());
    }

    @Test
    public void controleerR1358_NietBAGOR() {
        params.huisnummer = new StringElement("1");
        adresElement = builder.maakAdres("com_adres", params);
        final List<MeldingElement> meldingen = adresElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1358, meldingen.get(0).getRegel());
    }

    @Test
    public void controleerR2416() {
        params.setAfgekorteNaamOpenbareRuimte(new StringElement("kort"));
        params.setLocatieomschrijving(new StringElement("lokatie omschrijving"));
        adresElement = builder.maakAdres("com_adres", params);
        final List<MeldingElement> meldingen = adresElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2416, meldingen.get(0).getRegel());
    }

    @Test
    public void controleerHuisLetter() {
        params.setLocatieomschrijving(new StringElement("LocOms"));
        params.setHuisletter(new CharacterElement('C'));
        adresElement = builder.maakAdres("com_adres", params);
        final List<MeldingElement> meldingen = adresElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1368, meldingen.get(0).getRegel());
    }

    @Test
    public void controleerHuisnummerToevoeging() {
        params.setHuisnummertoevoeging(new StringElement("C"));
        params.setLocatieomschrijving(new StringElement("LocOms"));
        adresElement = builder.maakAdres("com_adres", params);
        final List<MeldingElement> meldingen = adresElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1369, meldingen.get(0).getRegel());
    }

    @Test
    public void controleerPostcode() {
        params.setPostcode(new StringElement("2312PH"));
        params.setLocatieomschrijving(new StringElement("LocOms"));
        adresElement = builder.maakAdres("com_adres", params);
        final List<MeldingElement> meldingen = adresElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1371, meldingen.get(0).getRegel());
    }

    @Test
    public void controleerHuisnummerR1367() {
        vulParamsMetGeldigeBag();
        params.setHuisnummer(null);
        adresElement = builder.maakAdres("com_adres", params);
        final List<MeldingElement> meldingen = adresElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1367, meldingen.get(0).getRegel());
    }

    @Test
    public void controleerHuisnummerR2417() {
        params.setLocatieomschrijving(new StringElement("lokatie omschrijving"));
        params.setHuisnummer(new StringElement("1"));
        adresElement = builder.maakAdres("com_adres", params);
        final List<MeldingElement> meldingen = adresElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2417, meldingen.get(0).getRegel());
    }

    @Test
    public void controleerLocatieTenOpzichteVanAdresHuisnummer() {
        params.setHuisnummertoevoeging(new StringElement("B"));
        params.setHuisnummer(new StringElement("1"));
        params.setLocatieTenOpzichteVanAdres(new StringElement("locatieToVAdres"));
        params.setAfgekorteNaamOpenbareRuimte(new StringElement("afgekorteNaam"));
        adresElement = builder.maakAdres("com_adres", params);
        final List<MeldingElement> meldingen = adresElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2317, meldingen.get(0).getRegel());
    }

    @Test
    public void controleerLocatieTenOpzichteVanAdresNietBAG() {
        params.locatieomschrijving = new StringElement("omschrijving");
        params.locatieTenOpzichteVanAdres = new StringElement("LtovA");
        adresElement = builder.maakAdres("com_adres", params);
        final List<MeldingElement> meldingen = adresElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1370, meldingen.get(0).getRegel());
    }

    @Test
    public void R2364(){
        params.setLocatieomschrijving(new StringElement("lokatie omschrijving"));
        params.setRedenWijzigingCode(new CharacterElement('U'));
        adresElement = builder.maakAdres("com_adres", params);
        final List<MeldingElement> meldingen = adresElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2364, meldingen.get(0).getRegel());
    }

    @Test
    public void R2420(){
        params.setLocatieomschrijving(new StringElement("lokatie omschrijving"));
        params.setRedenWijzigingCode(new CharacterElement('A'));
        params.setAangeverAdreshoudingCode(new CharacterElement('C'));
        adresElement = builder.maakAdres("com_adres", params);
        final List<MeldingElement> meldingen = adresElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2420, meldingen.get(0).getRegel());
    }

    @Test
    public void R1926(){
        params.setLocatieomschrijving(new StringElement("lokatie omschrijving"));
        params.setRedenWijzigingCode(new CharacterElement('P'));
        params.setAangeverAdreshoudingCode(null);
        adresElement = builder.maakAdres("com_adres", params);
        final List<MeldingElement> meldingen = adresElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1926, meldingen.get(0).getRegel());
    }

    @Test
    public void R2309() {
        vulParamsMetGeldigeBag();
        params.setIdentificatiecodeNummeraanduiding(null);
        adresElement = builder.maakAdres("com_adres", params);
        final List<MeldingElement> meldingen = adresElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2309, meldingen.get(0).getRegel());
    }
    @Test
    public void R2313() {
        vulParamsMetGeldigeBag();
        params.setNaamOpenbareRuimte(null);
        adresElement = builder.maakAdres("com_adres", params);
        final List<MeldingElement> meldingen = adresElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2313, meldingen.get(0).getRegel());
    }
    @Test
    public void R2314() {
        vulParamsMetGeldigeBag();
        params.setWoonplaatsnaam(null);
        adresElement = builder.maakAdres("com_adres", params);
        final List<MeldingElement> meldingen = adresElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2314, meldingen.get(0).getRegel());
    }
    @Test
    public void R2329() {
        vulParamsMetGeldigeBag();
        params.setIdentificatiecodeAdresseerbaarObject(null);
        adresElement = builder.maakAdres("com_adres", params);
        final List<MeldingElement> meldingen = adresElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2329, meldingen.get(0).getRegel());
    }

    @Test
    public void woonplaatsNaamOngeldigStamgegeven(){
        vulParamsMetGeldigeBag();
        adresElement = builder.maakAdres("com_adres", params);
        when(getDynamischeStamtabelRepository().getPlaatsByPlaatsNaam("woonplaatsnaam")).thenReturn(null);
        final List<MeldingElement> meldingen = adresElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2316, meldingen.get(0).getRegel());
    }

    @Test
    public void datumAanvangAdreshoudingInDeToekomst(){
        vulParamsMetGeldigeBag();
        params.setDatumAanvangAdreshouding(new DatumElement(20990101));
        adresElement = builder.maakAdres("com_adres", params);
        final List<MeldingElement> meldingen = adresElement.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2355, meldingen.get(0).getRegel());
    }



    private void vulAdresElementMetGeldigeBAG(){
        vulParamsMetGeldigeBag();
        adresElement = builder.maakAdres("com_adres3", params);
    }

    private void vulParamsMetGeldigeBag() {
        params = defaultParams;
        params.identificatiecodeAdresseerbaarObject = new StringElement("identcodeAddr");
        params.identificatiecodeNummeraanduiding = new StringElement("identcodeNr");
        params.naamOpenbareRuimte = new StringElement("naamOpbR");
        params.huisnummer = new StringElement("1");
        params.woonplaatsnaam= new StringElement("woonplaatsnaam");
        params.afgekorteNaamOpenbareRuimte = new StringElement("kort");
        params.setRedenWijzigingCode(new CharacterElement('A'));
    }


}
