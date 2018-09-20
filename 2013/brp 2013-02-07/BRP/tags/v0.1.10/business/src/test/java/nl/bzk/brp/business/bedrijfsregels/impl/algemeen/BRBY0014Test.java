/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.algemeen;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.groep.bericht.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

/** Unit test class waarin de bedrijfsregel BRBY0014 wordt getest. */
public class BRBY0014Test {

    private static final String         BESTAAND_BSN_1     = "123456789";
    private static final String         BESTAAND_BSN_2     = "234567890";
    private static final PersoonBericht BESTAAND_PERSOON_1 = maakPersoonBerichtMetBsn(BESTAAND_BSN_1);
    private static final PersoonBericht BESTAAND_PERSOON_2 = maakPersoonBerichtMetBsn(BESTAAND_BSN_2);

    private BRBY0014 bedrijfsregel;

    @Mock
    private PersoonRepository persoonRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer(BESTAAND_BSN_1)))
               .thenReturn(new PersoonModel(BESTAAND_PERSOON_1));
        Mockito.when(persoonRepository.findByBurgerservicenummer(new Burgerservicenummer(BESTAAND_BSN_2)))
               .thenReturn(new PersoonModel(BESTAAND_PERSOON_2));

        bedrijfsregel = new BRBY0014();

        ReflectionTestUtils.setField(bedrijfsregel, "persoonRepository", persoonRepository);
    }

    @Test
    public void testMetOntbrekendRootObject() {
        List<Melding> meldingen = bedrijfsregel.executeer(null, null, null);
        Assert.assertNotNull(meldingen);
        Assert.assertEquals("Onverwacht aantal meldingen", 0, meldingen.size());
    }

    @Test
    public void testMetOnbekendRootObject() {
        List<Melding> meldingen = bedrijfsregel.executeer(null, new RootObject() {
        }, null);
        Assert.assertNotNull(meldingen);
        Assert.assertEquals("Onverwacht aantal meldingen", 1, meldingen.size());
        Assert.assertEquals("Onverwachte meldingcode", MeldingCode.ALG0001, meldingen.get(0).getCode());
    }

    @Test
    public void testPersoonMetBestaandBsn() {
        List<Melding> meldingen = bedrijfsregel.executeer(null, BESTAAND_PERSOON_1, null);
        Assert.assertNotNull(meldingen);
        Assert.assertEquals("Onverwacht aantal meldingen", 0, meldingen.size());
    }

    @Test
    public void testPersoonMetNietBestaandBsn() {
        List<Melding> meldingen = bedrijfsregel.executeer(null, maakPersoonBerichtMetBsn("111111111"), null);
        Assert.assertNotNull(meldingen);
        Assert.assertEquals("Onverwacht aantal meldingen", 1, meldingen.size());
        Assert.assertEquals("Onverwachte meldingcode", MeldingCode.BRBY0014, meldingen.get(0).getCode());
        Assert.assertEquals("Onverwachte melding tekst",
            "De persoon met BSN 111111111 komt niet voor in het systeem.",
            meldingen.get(0).getOmschrijving());
    }

    @Test
    public void testPersoonMetOntbrekendeIdentificerendeGegevens() {
        List<Melding> meldingen = bedrijfsregel.executeer(null, new PersoonBericht(), null);
        Assert.assertNotNull(meldingen);
        Assert.assertEquals("Onverwacht aantal meldingen", 1, meldingen.size());
        Assert.assertEquals("Onverwachte meldingcode", MeldingCode.BRBY0014, meldingen.get(0).getCode());
        Assert.assertEquals("Onverwachte melding tekst",
            "Er is geen identificatie opgegeven van de betreffende persoon.",
            meldingen.get(0).getOmschrijving());
    }

    @Test
    public void testPersoonMetOntbrekendeBsn() {
        List<Melding> meldingen = bedrijfsregel.executeer(null, maakPersoonBerichtMetBsn(null), null);
        Assert.assertNotNull(meldingen);
        Assert.assertEquals("Onverwacht aantal meldingen", 1, meldingen.size());
        Assert.assertEquals("Onverwachte meldingcode", MeldingCode.BRBY0014, meldingen.get(0).getCode());
        Assert.assertEquals("Onverwachte melding tekst",
            "Er is geen identificatie opgegeven van de betreffende persoon.",
            meldingen.get(0).getOmschrijving());
    }

    @Test
    public void testPersoonMetLegeBsn() {
        List<Melding> meldingen = bedrijfsregel.executeer(null, maakPersoonBerichtMetBsn(""), null);
        Assert.assertNotNull(meldingen);
        Assert.assertEquals("Onverwacht aantal meldingen", 1, meldingen.size());
        Assert.assertEquals("Onverwachte meldingcode", MeldingCode.BRBY0014, meldingen.get(0).getCode());
        Assert.assertEquals("Onverwachte melding tekst",
            "De persoon met BSN  komt niet voor in het systeem.",
            meldingen.get(0).getOmschrijving());
    }

    @Test
    public void testRelatieMetBestaandeBsns() {
        List<Melding> meldingen = bedrijfsregel.executeer(null, maakRelatieBerichtMetPersonen(BESTAAND_PERSOON_1,
            BESTAAND_PERSOON_2), null);
        Assert.assertNotNull(meldingen);
        Assert.assertEquals("Onverwacht aantal meldingen", 0, meldingen.size());
    }

    @Test
    public void testRelatieMetEnkeleBestaandeBsn() {
        List<Melding> meldingen = bedrijfsregel.executeer(null, maakRelatieBerichtMetPersonen(BESTAAND_PERSOON_1,
            maakPersoonBerichtMetBsn("111111111")), null);
        Assert.assertNotNull(meldingen);
        Assert.assertEquals("Onverwacht aantal meldingen", 1, meldingen.size());
        Assert.assertEquals("Onverwachte meldingcode", MeldingCode.BRBY0014, meldingen.get(0).getCode());
        Assert.assertEquals("Onverwachte melding tekst",
            "De persoon met BSN 111111111 komt niet voor in het systeem.",
            meldingen.get(0).getOmschrijving());
    }

    @Test
    public void testRelatieMetTweeNietBestaandeBsns() {
        List<Melding> meldingen =
            bedrijfsregel.executeer(null, maakRelatieBerichtMetPersonen(maakPersoonBerichtMetBsn("111111111"),
                maakPersoonBerichtMetBsn("222222222")), null);
        Assert.assertNotNull(meldingen);
        Assert.assertEquals("Onverwacht aantal meldingen", 2, meldingen.size());
        Assert.assertEquals("Onverwachte meldingcode", MeldingCode.BRBY0014, meldingen.get(0).getCode());
        Assert.assertEquals("Onverwachte meldingcode", MeldingCode.BRBY0014, meldingen.get(1).getCode());
        Assert.assertEquals("Onverwachte melding tekst",
            "De persoon met BSN 111111111 komt niet voor in het systeem.",
            meldingen.get(0).getOmschrijving());
        Assert.assertEquals("Onverwachte melding tekst",
            "De persoon met BSN 222222222 komt niet voor in het systeem.",
            meldingen.get(1).getOmschrijving());
    }

    @Test
    public void testRelatieMetBestaandBsnEnMissendeIdentificerendeGegevens() {
        List<Melding> meldingen = bedrijfsregel.executeer(null,
            maakRelatieBerichtMetPersonen(new PersoonBericht(), BESTAAND_PERSOON_1), null);
        Assert.assertNotNull(meldingen);
        Assert.assertEquals("Onverwacht aantal meldingen", 1, meldingen.size());
        Assert.assertEquals("Onverwachte meldingcode", MeldingCode.BRBY0014, meldingen.get(0).getCode());
        Assert.assertEquals("Onverwachte melding tekst",
            "Er is geen identificatie opgegeven van de betreffende persoon.",
            meldingen.get(0).getOmschrijving());
    }

    /**
     * Retourneert een {@link PersoonBericht} instantie met daarin de opgegeven bsn.
     *
     * @param bsn de waarde van de bsn
     * @return de persoon
     */
    private static PersoonBericht maakPersoonBerichtMetBsn(final String bsn) {
        PersoonBericht persoon = new PersoonBericht();
        persoon.setIdentificatienummers(new PersoonIdentificatienummersGroepBericht());
        if (bsn != null) {
            persoon.getIdentificatienummers().setBurgerservicenummer(new Burgerservicenummer(bsn));
        }
        return persoon;
    }

    /**
     * Retourneert een {@link RelatieBericht} instantie met daarin de opgegeven personen.
     *
     * @param personen de personen in de relatie
     * @return de relatie
     */
    private static RelatieBericht maakRelatieBerichtMetPersonen(final PersoonBericht... personen) {
        RelatieBericht relatie = new RelatieBericht();
        relatie.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
        for (PersoonBericht betrokkene : personen) {
            BetrokkenheidBericht betrokkenheid = new BetrokkenheidBericht();
            betrokkenheid.setBetrokkene(betrokkene);
            relatie.getBetrokkenheden().add(betrokkenheid);
        }
        return relatie;
    }

}
