/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.services.objectsleutel.OngeldigeObjectSleutelException;
import org.junit.Test;

public class AdministratieveHandelingElementTestHGPTest extends AbstractAdministratieveHandelingElementTest {

    @Test
    public void testHuwelijkOK() {
        final List<BetrokkenheidElement> betrokkenen = new LinkedList<>();
        betrokkenen.add(createPartner(null, "p1", "1"));
        betrokkenen.add(createPartner(null, "p2", "2"));

        final AdministratieveHandelingElement ah = createAdministratieveHandelingAanvangHuwelijk(betrokkenen);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(0, meldingen.size());
    }

    @Test
    public void testHuwelijk2IngeschreveneMetZelfdeAnr() throws OngeldigeObjectSleutelException {
        maakPersoon(SoortPersoon.INGESCHREVENE,NEDERLANDS,10,true,"stam3",null,null, Geslachtsaanduiding.MAN,"Peter",19800101,null, "0000000123", "000001234");
        maakPersoon(SoortPersoon.INGESCHREVENE,NEDERLANDS,20,true,"stam3",null,null, Geslachtsaanduiding.VROUW,"Marieke",19810101,null, "0000000123", "000000123");
        final List<BetrokkenheidElement> betrokkenen = new LinkedList<>();
        betrokkenen.add(createPartner(null, "p1", "10"));
        betrokkenen.add(createPartner(null, "p2", "20"));

        final AdministratieveHandelingElement ah = createAdministratieveHandelingAanvangHuwelijk(betrokkenen);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2502, meldingen.get(0).getRegel());
    }


    @Test
    public void testHuwelijk2IngeschreveneMetZelfdeBsn() throws OngeldigeObjectSleutelException {
        maakPersoon(SoortPersoon.INGESCHREVENE,NEDERLANDS,10,true,"stam3",null,null, Geslachtsaanduiding.MAN,"Peter",19800101,null, "0000000123", "000012345");
        maakPersoon(SoortPersoon.INGESCHREVENE,NEDERLANDS,20,true,"stam3",null,null, Geslachtsaanduiding.VROUW,"Marieke",19810101,null, "0000001234", "000012345");
        final List<BetrokkenheidElement> betrokkenen = new LinkedList<>();
        betrokkenen.add(createPartner(null, "p1", "10"));
        betrokkenen.add(createPartner(null, "p2", "20"));

        final AdministratieveHandelingElement ah = createAdministratieveHandelingAanvangHuwelijk(betrokkenen);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2502, meldingen.get(0).getRegel());
    }


    @Test
    public void testHuwelijk1IngeschreveneEn1pseudoMetZelfdeAnr() throws OngeldigeObjectSleutelException {
        maakPersoon(SoortPersoon.INGESCHREVENE,NEDERLANDS,10,true,"stam3",null,null, Geslachtsaanduiding.MAN,"Peter",19800101,null, "0000000123", "000001234");
        final List<BetrokkenheidElement> betrokkenen = new LinkedList<>();
        betrokkenen.add(createPartner(null, "p1", "10"));

        final ElementBuilder.PersoonParameters pParams = new ElementBuilder.PersoonParameters();
        pParams.identificatienummers(builder.maakIdentificatienummersElement("ident_com","0012341241","0000000123"));
        betrokkenen.add(createPartner(pParams, "p2", null));

        final AdministratieveHandelingElement ah = createAdministratieveHandelingAanvangHuwelijk(betrokkenen);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2502, meldingen.get(0).getRegel());
    }

    @Test
    public void testHuwelijk1IngeschreveneEn1pseudoMetZelfdeBsn() throws OngeldigeObjectSleutelException {
        maakPersoon(SoortPersoon.INGESCHREVENE,NEDERLANDS,10,true,"stam3",null,null, Geslachtsaanduiding.MAN,"Peter",19800101,null, "0000000123", "000012345");
        final List<BetrokkenheidElement> betrokkenen = new LinkedList<>();
        betrokkenen.add(createPartner(null, "p1", "10"));

        final ElementBuilder.PersoonParameters pParams = new ElementBuilder.PersoonParameters();
        pParams.identificatienummers(builder.maakIdentificatienummersElement("ident_com","000012345","0012454543"));
        betrokkenen.add(createPartner(pParams, "p2", null));

        final AdministratieveHandelingElement ah = createAdministratieveHandelingAanvangHuwelijk(betrokkenen);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R2502, meldingen.get(0).getRegel());
    }



    @Bedrijfsregel(Regel.R1696)
    @Test
    public void testHuwelijkPersoon2HeeftNamenReeksEnIsNederlandse() {
        final List<BetrokkenheidElement> betrokkenen = new LinkedList<>();
        final ElementBuilder.PersoonParameters pParams = new ElementBuilder.PersoonParameters();
        final ElementBuilder.NaamParameters nParam = new ElementBuilder.NaamParameters();

        nParam.indicatieNamenreeks(true);
        pParams.samengesteldeNaam(builder.maakSamengesteldeNaamElement("snaam", nParam));
        pParams.nationaliteiten(Collections.singletonList(builder.maakNationaliteitElement("nat", "0001", "001")));
        pParams.voornamen(Collections.singletonList(builder.maakVoornaamElement("voornaam", 0, "Piet")));
        betrokkenen.add(createPartner(null, "p1", "1"));
        betrokkenen.add(createPartner(pParams, "p2", "2"));

        final AdministratieveHandelingElement ah = createAdministratieveHandelingAanvangHuwelijk(betrokkenen);

        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(1, meldingen.size());
        assertEquals(Regel.R1696, meldingen.get(0).getRegel());
        assertEquals("ah_id", meldingen.get(0).getReferentie().getCommunicatieId());
    }

    @Bedrijfsregel(Regel.R1696)
    @Test
    public void testHuwelijkPersoon1HeeftNamenReeksMaarHeeftBuitenlandseNationaliteit() {

        final List<BetrokkenheidElement> betrokkenen = new LinkedList<>();
        final ElementBuilder.PersoonParameters pParams = new ElementBuilder.PersoonParameters();
        final ElementBuilder.NaamParameters nParam = new ElementBuilder.NaamParameters();

        nParam.indicatieNamenreeks(true);
        pParams.samengesteldeNaam(builder.maakSamengesteldeNaamElement("snaam", nParam));
        betrokkenen.add(createPartner(pParams, "p1", "1"));
        betrokkenen.add(createPartner(null, "p2", "2"));

        final AdministratieveHandelingElement ah = createAdministratieveHandelingAanvangHuwelijk(betrokkenen);
        final List<MeldingElement> meldingen = ah.valideerInhoud();
        assertEquals(0, meldingen.size());
    }

}
