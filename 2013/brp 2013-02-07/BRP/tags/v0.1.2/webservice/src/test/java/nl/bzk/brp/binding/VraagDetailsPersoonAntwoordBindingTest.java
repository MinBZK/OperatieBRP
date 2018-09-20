/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.bzk.brp.business.dto.bevraging.OpvragenPersoonResultaat;
import nl.bzk.brp.model.gedeeld.GeslachtsAanduiding;
import nl.bzk.brp.model.gedeeld.Land;
import nl.bzk.brp.model.gedeeld.Partij;
import nl.bzk.brp.model.gedeeld.Plaats;
import nl.bzk.brp.model.gedeeld.SoortBetrokkenheid;
import nl.bzk.brp.model.gedeeld.SoortIndicatie;
import nl.bzk.brp.model.gedeeld.SoortRelatie;
import nl.bzk.brp.model.logisch.Betrokkenheid;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.Relatie;
import nl.bzk.brp.model.logisch.groep.PersoonGeslachtsAanduiding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import nl.bzk.brp.web.bevraging.VraagDetailsPersoonAntwoord;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Test;


public class VraagDetailsPersoonAntwoordBindingTest
    extends AbstractVraagBerichtBindingUitTest<VraagDetailsPersoonAntwoord>
{

    @Override
    public Class<VraagDetailsPersoonAntwoord> getBindingClass() {
        return VraagDetailsPersoonAntwoord.class;
    }

    @Test
    public void testOutBindingMetLeegResultaat() throws JiBXException {
        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(null);

        final VraagDetailsPersoonAntwoord response = new VraagDetailsPersoonAntwoord(resultaat);
        String xml = marshalObject(response);
        Assert.assertNotNull(xml);

        String verwachteWaarde = bouwVerwachteAntwoordBericht("20120325143506789");
        Assert.assertEquals(verwachteWaarde, xml);

        valideerOutputTegenSchema(xml);
    }

    @Test
    public void testOutBindingMetMelding() throws JiBXException {
        List<Melding> meldingen = new ArrayList<Melding>();
        meldingen.add(new Melding(SoortMelding.INFO, MeldingCode.ALG0001, "TEST"));

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(meldingen);
        VraagDetailsPersoonAntwoord response = new VraagDetailsPersoonAntwoord(resultaat);

        String xml = marshalObject(response);
        Assert.assertNotNull(xml);

        String verwachteWaarde = bouwVerwachteAntwoordBericht("20120325143506789", "I", "ALG0001", "TEST", null);
        Assert.assertEquals(verwachteWaarde, xml);

        valideerOutputTegenSchema(xml);
    }

    @Test
    public void testOutBindingMetPersoonInResultaat() throws JiBXException, ParseException {
        Partij gemeente = new Partij();
        gemeente.setGemeentecode("0010");

        Land nederland = new Land();
        nederland.setLandcode("0031");

        Plaats amsterdam = new Plaats();
        amsterdam.setWoonplaatscode("0020");

        OpvragenPersoonResultaat resultaat = bouwOpvragenPersoonResultaatVoorCompleetPersoonMetRelaties(gemeente,
            nederland, amsterdam, SoortRelatie.HUWELIJK);

        final VraagDetailsPersoonAntwoord response = new VraagDetailsPersoonAntwoord(resultaat);

        String xml = marshalObject(response);
        Assert.assertNotNull(xml);
        //Voor het gemak alle bsn's vervangen.
        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "burgerservicenummer", "123456789");

        String verwachteWaarde = bouwVerwachteAntwoordBericht("20120325143506789", null, null, null,
            "vraagDetailsPersoonMetRelatiesAntwoordBindingResultaat.xml");
        Assert.assertEquals(verwachteWaarde, xml);

        valideerOutputTegenSchema(xml);
    }

    @Test
    public void testOutBindingMinimaalPersoonMetHuwelijk() throws JiBXException, ParseException {
        Land nederland = new Land();
        nederland.setLandcode("0031");

        Persoon minimaalPersoon = bouwMinimaalPersoon();

        Relatie minimaalHuwelijk = new Relatie();
        minimaalHuwelijk.setSoortRelatie(SoortRelatie.HUWELIJK);
        minimaalHuwelijk.setDatumAanvang(20000312);
        minimaalHuwelijk.setLandAanvang(nederland);
        Set<Betrokkenheid> minimaleBetrokkenheden = new HashSet<Betrokkenheid>();
        Betrokkenheid partner1 = new Betrokkenheid();
        partner1.setSoortBetrokkenheid(SoortBetrokkenheid.PARTNER);
        partner1.setBetrokkene(minimaalPersoon);
        partner1.setRelatie(minimaalHuwelijk);
        minimaalPersoon.voegPartnerBetrokkenHedenToe(Arrays.asList(partner1));
        Betrokkenheid partner2 = new Betrokkenheid();
        partner2.setSoortBetrokkenheid(SoortBetrokkenheid.PARTNER);
        partner2.setBetrokkene(new Persoon());
        partner2.setRelatie(minimaalHuwelijk);
        minimaleBetrokkenheden.add(partner1);
        minimaleBetrokkenheden.add(partner2);
        minimaalHuwelijk.setBetrokkenheden(minimaleBetrokkenheden);

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(null);
        Set<Persoon> gevondenPersonen = new HashSet<Persoon>();
        gevondenPersonen.add(minimaalPersoon);
        resultaat.setGevondenPersonen(gevondenPersonen);

        final VraagDetailsPersoonAntwoord response = new VraagDetailsPersoonAntwoord(resultaat);

        String xml = marshalObject(response);
        Assert.assertNotNull(xml);

        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "tijdstipLaatsteWijziging", "2011111111111100");
        String verwachteWaarde = bouwVerwachteAntwoordBericht("20120325143506789", null, null, null,
            "vraagDetailsPersoonMinimaalMetHuwelijkAntwoordBindingResultaat.xml");
        Assert.assertEquals(verwachteWaarde, xml);

        valideerOutputTegenSchema(xml);
    }

    @Test
    public void testOutBindingMinimaalPersoonMetFamRechtBet() throws JiBXException, ParseException {
        Land nederland = new Land();
        nederland.setLandcode("0031");
        Partij gemeente = new Partij();
        gemeente.setGemeentecode("0010");

        Persoon minimaalPersoon = bouwMinimaalPersoon();

        Relatie minimaalOuderschap1 = new Relatie();
        minimaalOuderschap1.setSoortRelatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        Set<Betrokkenheid> minimaleBetrokkenheden = new HashSet<Betrokkenheid>();
        Betrokkenheid ouder1 = new Betrokkenheid();
        ouder1.setSoortBetrokkenheid(SoortBetrokkenheid.OUDER);
        ouder1.setBetrokkene(minimaalPersoon);
        ouder1.setRelatie(minimaalOuderschap1);
        minimaalPersoon.voegOuderBetrokkenhedenToe(Arrays.asList(ouder1));
        Betrokkenheid ouder2 = new Betrokkenheid();
        ouder2.setSoortBetrokkenheid(SoortBetrokkenheid.OUDER);
        ouder2.setBetrokkene(new Persoon());
        ouder2.getBetrokkene().setPersoonGeslachtsAanduiding(new PersoonGeslachtsAanduiding());
        ouder2.getBetrokkene().getPersoonGeslachtsAanduiding().setGeslachtsAanduiding(GeslachtsAanduiding.VROUW);
        ouder2.setRelatie(minimaalOuderschap1);
        Betrokkenheid kind1 = new Betrokkenheid();
        kind1.setSoortBetrokkenheid(SoortBetrokkenheid.KIND);
        kind1.setBetrokkene(new Persoon());
        kind1.getBetrokkene().setPersoonGeslachtsAanduiding(new PersoonGeslachtsAanduiding());
        kind1.getBetrokkene().getPersoonGeslachtsAanduiding().setGeslachtsAanduiding(GeslachtsAanduiding.VROUW);
        kind1.setRelatie(minimaalOuderschap1);

        minimaleBetrokkenheden.add(ouder1);
        minimaleBetrokkenheden.add(ouder2);
        minimaleBetrokkenheden.add(kind1);
        minimaalOuderschap1.setBetrokkenheden(minimaleBetrokkenheden);

        Relatie minimaalOuderschap2 = new Relatie();
        minimaalOuderschap2.setSoortRelatie(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        Betrokkenheid kind2 = new Betrokkenheid();
        kind2.setSoortBetrokkenheid(SoortBetrokkenheid.KIND);
        kind2.setBetrokkene(minimaalPersoon);
        kind2.setRelatie(minimaalOuderschap2);
        minimaalPersoon.voegKindBetrokkenHeidToe(kind2);
        Betrokkenheid ouder3 = new Betrokkenheid();
        ouder3.setSoortBetrokkenheid(SoortBetrokkenheid.OUDER);
        ouder3.setBetrokkene(new Persoon());
        ouder3.getBetrokkene().setPersoonGeslachtsAanduiding(new PersoonGeslachtsAanduiding());
        ouder3.getBetrokkene().getPersoonGeslachtsAanduiding().setGeslachtsAanduiding(GeslachtsAanduiding.VROUW);
        ouder3.setRelatie(minimaalOuderschap2);

        Set<Betrokkenheid> extraBetrokkenheden = new HashSet<Betrokkenheid>();
        extraBetrokkenheden.add(kind2);
        extraBetrokkenheden.add(ouder3);
        minimaalOuderschap2.setBetrokkenheden(extraBetrokkenheden);

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(null);
        Set<Persoon> gevondenPersonen = new HashSet<Persoon>();
        gevondenPersonen.add(minimaalPersoon);
        resultaat.setGevondenPersonen(gevondenPersonen);

        final VraagDetailsPersoonAntwoord response = new VraagDetailsPersoonAntwoord(resultaat);

        String xml = marshalObject(response);
        Assert.assertNotNull(xml);

        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "tijdstipLaatsteWijziging", "2011111111111100");
        String verwachteWaarde = bouwVerwachteAntwoordBericht("20120325143506789", null, null, null,
            "vraagDetailsPersoonMinimaalMetFamRechtBetrAntwoordBindingResultaat.xml");
        Assert.assertEquals(verwachteWaarde, xml);

        valideerOutputTegenSchema(xml);
    }

    @Test
    public void testIndicatieBinding() throws JiBXException, IOException, ParseException {
        Partij gemeente = new Partij();
        gemeente.setGemeentecode("0010");

        Land nederland = new Land();
        nederland.setLandcode("0031");

        Plaats amsterdam = new Plaats();
        amsterdam.setWoonplaatscode("0020");

        Set<Persoon> gevondenPersonen = new HashSet<Persoon>();
        Persoon opTeVragenPersoon = maakPersoon(1L, gemeente, amsterdam, nederland, "persoon");

        // Indicaties
        voegIndicatieToeAanPersoon(opTeVragenPersoon, SoortIndicatie.VERSTREKKINGSBEPERKING, true);
        voegIndicatieToeAanPersoon(opTeVragenPersoon, SoortIndicatie.ONDER_CURATELE, false);

        OpvragenPersoonResultaat resultaat = new OpvragenPersoonResultaat(null);
        gevondenPersonen.add(opTeVragenPersoon);
        resultaat.setGevondenPersonen(gevondenPersonen);

        final VraagDetailsPersoonAntwoord response = new VraagDetailsPersoonAntwoord(resultaat);

        String xml = marshalObject(response);
        Assert.assertNotNull(xml);
        //Voor het gemak alle bsn's vervangen.
        xml = vervangDynamischeWaardeVoorDummyWaarde(xml, "burgerservicenummer", "123456789");

        String verwachteWaarde = bouwVerwachteAntwoordBericht("20120325143506789", null, null, null,
            "vraagDetailsPersoonMetIndicatiesAntwoordBindingResultaat.xml");
        Assert.assertEquals(verwachteWaarde, xml);

        valideerOutputTegenSchema(xml);
    }

    @Override
    public String getBerichtElementNaam() {
        return "bevragen_VraagDetailsPersoon_Antwoord";
    }

    @Override
    protected String getSchemaBestand() {
        return "/xsd/BRP_Bevraging_Berichten.xsd";
    }
}
