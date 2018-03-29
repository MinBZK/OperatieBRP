/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.services.objectsleutel.OngeldigeObjectSleutelException;
import org.junit.Test;

public class AdministratieveHandelingElementTestGeboorteTest extends AbstractAdministratieveHandelingElementTest {



    @Test
    public void testOk() {
        final ElementBuilder.NaamParameters sPara = new ElementBuilder.NaamParameters();
        sPara.indicatieNamenreeks(false);
        sPara.geslachtsnaamstam("Stam");
        final AdministratieveHandelingElement ah = createAdministratieveHandelingRegistratieGeborene(20100101, sPara, "0001", "Karel appel", "Stam", "1", "2", null, null,
                null, null, null, null, null);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(0,meldingen.size());
    }

    @Test
    @Bedrijfsregel(Regel.R1790)
    public void test1790(){
        BijhoudingPersoon persoon = mock(BijhoudingPersoon.class);
        final ElementBuilder.NaamParameters sPara = new ElementBuilder.NaamParameters();
        sPara.indicatieNamenreeks(false);
        final AdministratieveHandelingElement ah = createAdministratieveHandelingRegistratieGeborene(20100101, sPara, "0001", "Karel appel", "Stam", "1", "2", null, null,
                null, null, null, null, null);

    }

    @Test
    public void dagActiesOngelijkAanGeboorteDatum() {
        final ElementBuilder.NaamParameters sPara = new ElementBuilder.NaamParameters();
        sPara.indicatieNamenreeks(false);
        sPara.geslachtsnaamstam("Stam");

        final AdministratieveHandelingElement ah = createAdministratieveHandelingRegistratieGeborene(20100101, sPara, "0001", "Karel appel", "Stam", "1", "2", null, null,
                null, null, null, null, 20100102);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(2,meldingen.size());
        assertEquals(Regel.R1250,meldingen.get(0).getRegel());
        assertEquals(Regel.R1250,meldingen.get(1).getRegel());
    }

    @Bedrijfsregel(Regel.R2502)
    @Test
    public void geboorteInNederlandKindZelfdeBsnAlsOuder() throws OngeldigeObjectSleutelException {
        maakPersoon(SoortPersoon.INGESCHREVENE, NEDERLANDS, 10, true, "Stam", null, null, null, null, null, null, "0000000123", "000000456");
        maakPersoon(SoortPersoon.INGESCHREVENE, NEDERLANDS, 20, true, "Stam", null, null, null, null, null, null, "0000000321", "000000654");
        final ElementBuilder.NaamParameters sPara = new ElementBuilder.NaamParameters();
        sPara.indicatieNamenreeks(false);
        sPara.geslachtsnaamstam("Stam");

        final AdministratieveHandelingElement ah = createAdministratieveHandelingRegistratieGeborene(20100101, sPara, "0001", "Karel appel", "Stam", "10", "20", null, null,
                null, null, "000000456", null, null);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(1,meldingen.size());
        assertEquals(Regel.R2502,meldingen.get(0).getRegel());
    }
    @Bedrijfsregel(Regel.R2502)
    @Test
    public void geboorteInNederlandKindZelfdeAnummerAlsOuder() throws OngeldigeObjectSleutelException {
        maakPersoon(SoortPersoon.INGESCHREVENE, NEDERLANDS, 10, true, "Stam", null, null, null, null, null, null, "0000000123", "000000456");
        maakPersoon(SoortPersoon.INGESCHREVENE, NEDERLANDS, 20, true, "Stam", null, null, null, null, null, null, "0000000321", "000000654");
        final ElementBuilder.NaamParameters sPara = new ElementBuilder.NaamParameters();
        sPara.indicatieNamenreeks(false);
        sPara.geslachtsnaamstam("Stam");

        final AdministratieveHandelingElement ah = createAdministratieveHandelingRegistratieGeborene(20100101, sPara, "0001", "Karel appel", "Stam", "10", "20", null, null,
                null, null, "0000004567", "0000000321", null);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(1,meldingen.size());
        assertEquals(Regel.R2502,meldingen.get(0).getRegel());
    }
    @Bedrijfsregel(Regel.R2502)
    @Test
    public void geboorteInNederlandKindZelfdeBsnAlsOuderPseudo() throws OngeldigeObjectSleutelException {
        maakPersoon(SoortPersoon.INGESCHREVENE, NEDERLANDS, 10, true, "Stam", null, null, null, null, null, null, "0000000123", "000000456");
        final ElementBuilder.NaamParameters sPara = new ElementBuilder.NaamParameters();
        sPara.indicatieNamenreeks(false);
        sPara.geslachtsnaamstam("Stam");

        final ElementBuilder.PersoonParameters ouderPara = new ElementBuilder.PersoonParameters();
        ouderPara.identificatienummers(builder.maakIdentificatienummersElement("cid","888","12123231"));
        final AdministratieveHandelingElement ah = createAdministratieveHandelingRegistratieGeborene(20100101, sPara, "0001", "Karel appel", "Stam", "10", null, null, null,
                null, ouderPara, "888", "123", null);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(1,meldingen.size());
        assertEquals(Regel.R2502,meldingen.get(0).getRegel());
    }
    @Bedrijfsregel(Regel.R2502)
    @Test
    public void geboorteInNederlandKindZelfdeAnrAlsOuderPseudo() throws OngeldigeObjectSleutelException {
        maakPersoon(SoortPersoon.INGESCHREVENE, NEDERLANDS, 10, true, "Stam", null, null, null, null, null, null, "0000000123", "000000456");
        final ElementBuilder.NaamParameters sPara = new ElementBuilder.NaamParameters();
        sPara.indicatieNamenreeks(false);
        sPara.geslachtsnaamstam("Stam");

        final ElementBuilder.PersoonParameters ouderPara = new ElementBuilder.PersoonParameters();
        ouderPara.identificatienummers(builder.maakIdentificatienummersElement("cid","123","888"));
        final AdministratieveHandelingElement ah = createAdministratieveHandelingRegistratieGeborene(20100101, sPara, "0001", "Karel appel", "Stam", "10", null, null, null,
                null, ouderPara, "3453512", "888", null);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(1,meldingen.size());
        assertEquals(Regel.R2502,meldingen.get(0).getRegel());
    }

    @Bedrijfsregel(Regel.R1815)
    @Test
    public void geboorteInNederlandZonderRegNationaliteitEnRegStaatloos(){
        final ElementBuilder.NaamParameters sPara = new ElementBuilder.NaamParameters();
        sPara.indicatieNamenreeks(false);
        sPara.geslachtsnaamstam("Stam");
        final AdministratieveHandelingElement ah = createAdministratieveHandelingRegistratieGeborene(20100101, sPara, null, "Karel appel", "Stam", "1", "3", null, null,
                null, null, null, null, null);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(1,meldingen.size());
        assertEquals(Regel.R1815,meldingen.get(0).getRegel());
    }


    @Bedrijfsregel(Regel.R1726)
    @Test
    public void geboorteInNederlandGeslachtsnaamKindOngelijkAanOuder(){
        final ElementBuilder.NaamParameters sPara = new ElementBuilder.NaamParameters();
        sPara.indicatieNamenreeks(false);
        final AdministratieveHandelingElement ah = createAdministratieveHandelingRegistratieGeborene(20100101, sPara, "0001", "Karel appel", "Stam2", "1", "2", null,
                null, null, null, null, null, null);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(1,meldingen.size());
        assertEquals(Regel.R1726,meldingen.get(0).getRegel());
    }

    @Bedrijfsregel(Regel.R1726)
    @Test
    public void geboorteInNederlandGeslachtsnaamKindOngelijkAanOuderMeNamenreeksKind(){
        final ElementBuilder.NaamParameters sPara = new ElementBuilder.NaamParameters();
        sPara.indicatieNamenreeks(false);
        sPara.geslachtsnaamstam("true");
        final AdministratieveHandelingElement ah = createAdministratieveHandelingRegistratieGeborene(20100101, sPara, "0001", "Karel appel", "Stam", "1", "2", null, null,
                null, null, null, null, null);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(0,meldingen.size());
    }


    @Bedrijfsregel(Regel.R1696)
    @Test
    public void testGeboorteNederlandNamenreeksEnNederlands(){
        final ElementBuilder.NaamParameters sPara = new ElementBuilder.NaamParameters();
        sPara.indicatieNamenreeks(true);
        sPara.geslachtsnaamstam("Stam");
        final AdministratieveHandelingElement ah = createAdministratieveHandelingRegistratieGeborene(2010_01_01, sPara, "0001", "Karel appel", "Stam", "1", "2", null, null,
                null, null, null, null, null);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(1,meldingen.size());
        assertEquals(Regel.R1696,meldingen.get(0).getRegel());
        assertEquals("ah_id",meldingen.get(0).getReferentie().getCommunicatieId());
    }

    @Bedrijfsregel(Regel.R1696)
    @Bedrijfsregel(Regel.R1691)
    /**
     * regel 1696 word vanwege de buitenlandse nationaliteit niet getriggerd,
     * maar de ouder heeft de nederlandse wel, vandaar dat R1691 getriggerd wordt.
     */
    @Test
    public void geboorteInNederlandMetNamenReeksEnNietNederlandse(){
        final ElementBuilder.NaamParameters sPara = new ElementBuilder.NaamParameters();
        sPara.indicatieNamenreeks(true);
        sPara.geslachtsnaamstam("Stam");
        sPara.voornamen("Karel Appel");
        final AdministratieveHandelingElement ah = createAdministratieveHandelingRegistratieGeborene(20100101, sPara, "0036", null, "Stam", "1", "2", null, null, null,
                null, null, null, null);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1691,meldingen.get(0).getRegel());
    }

    @Bedrijfsregel(Regel.R1697)
    @Test
    public void geboorteInNederlandVoornamenEnNederlandse(){
        //samenGesteldeNaam
        final ElementBuilder.NaamParameters sPara = new ElementBuilder.NaamParameters();
        sPara.indicatieNamenreeks(false);
        sPara.geslachtsnaamstam("Stam");
        final AdministratieveHandelingElement ah = createAdministratieveHandelingRegistratieGeborene(20100101, sPara, "0001", null, "Stam", "1", "2", null, null, null,
                null, null, null, null);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(1,meldingen.size());
        assertEquals(Regel.R1697,meldingen.get(0).getRegel());
        assertEquals("ah_id",meldingen.get(0).getReferentie().getCommunicatieId());
    }

    @Bedrijfsregel(Regel.R1697)
    @Test
    public void geboorteInNederlandVoornamenEnNietnederlandse(){
        //samenGesteldeNaam
        final ElementBuilder.NaamParameters sPara = new ElementBuilder.NaamParameters();
        sPara.indicatieNamenreeks(false);
        sPara.geslachtsnaamstam("Stam");
        final AdministratieveHandelingElement ah = createAdministratieveHandelingRegistratieGeborene(20100101, sPara, "0036", null, "Stam", "1", "2", null, null, null,
                null, null, null, null);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(1, meldingen.size());
    }
}
