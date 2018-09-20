/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding;

import java.io.IOException;
import java.util.Iterator;
import java.util.SortedSet;

import nl.bzk.brp.business.dto.BerichtStuurgegevens;
import nl.bzk.brp.business.dto.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.business.dto.bijhouding.InschrijvingGeboorteBericht;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.binding.AbstractBindingInTest;
import nl.bzk.brp.model.gedeeld.GeslachtsAanduiding;
import nl.bzk.brp.model.gedeeld.SoortActie;
import nl.bzk.brp.model.logisch.BRPActie;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonGeslachtsnaamcomponent;
import nl.bzk.brp.model.logisch.PersoonVoornaam;
import nl.bzk.brp.model.logisch.groep.PersoonGeboorte;
import nl.bzk.brp.model.logisch.groep.PersoonGeslachtsAanduiding;
import nl.bzk.brp.model.logisch.groep.PersoonIdentificatienummers;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Test;

/** Binding test voor een {@link BijhoudingsBericht} met daarin een inschrijving geboorte bericht. */
public class InschrijvingGeboorteBerichtBindingTest extends AbstractBindingInTest<InschrijvingGeboorteBericht> {

    @Test
    public void testBindingMaxBericht() throws JiBXException, IOException {
        String xml = leesBestand("afstamming_InschrijvingAangifteGeboorte_Bijhouding_v0001_MAX.xml");
        BijhoudingsBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);
        //Check stuurgegevens
        BerichtStuurgegevens berichtStuurgegevens = bericht.getBerichtStuurgegevens();
        Assert.assertNotNull(berichtStuurgegevens);
        Assert.assertEquals("organisatie", berichtStuurgegevens.getOrganisatie());
        Assert.assertEquals("applicatie", berichtStuurgegevens.getApplicatie());
        Assert.assertEquals("referentienummer", berichtStuurgegevens.getReferentienummer());
        Assert.assertEquals("crossReferentienummer", berichtStuurgegevens.getCrossReferentienummer());


        Assert.assertNotNull(bericht.getBrpActies());
        Assert.assertEquals(1, bericht.getBrpActies().size());
        BRPActie actie = bericht.getBrpActies().get(0);
        Assert.assertEquals(20120101, actie.getDatumAanvangGeldigheid().intValue());
        Assert.assertEquals(SoortActie.AANGIFTE_GEBOORTE, actie.getSoort());
        Assert.assertNotNull(actie.getRootObjecten());
        Assert.assertEquals(1, actie.getRootObjecten().size());
        RootObject rootObject = actie.getRootObjecten().get(0);
        Assert.assertTrue(rootObject instanceof Persoon);
        Persoon kind = (Persoon) rootObject;

        PersoonIdentificatienummers persoonIdentificatienummers = kind.getIdentificatienummers();
        Assert.assertEquals("123456789", persoonIdentificatienummers.getBurgerservicenummer());
        Assert.assertEquals("123456789", persoonIdentificatienummers.getAdministratienummer());

        PersoonGeslachtsAanduiding persoonGeslachtsAanduiding = kind.getPersoonGeslachtsAanduiding();
        Assert.assertEquals(GeslachtsAanduiding.ONBEKEND, persoonGeslachtsAanduiding.getGeslachtsAanduiding());

        PersoonGeboorte geboorte = kind.getPersoonGeboorte();
        Assert.assertEquals(20120101, geboorte.getDatumGeboorte().intValue());
        Assert.assertEquals("1111", geboorte.getGemeenteGeboorte().getGemeentecode());
        Assert.assertEquals("1111", geboorte.getWoonplaatsGeboorte().getWoonplaatscode());
        Assert.assertEquals("verweggiestan", geboorte.getBuitenlandsePlaats());
        Assert.assertEquals("derde berg links", geboorte.getBuitenlandseRegio());
        Assert.assertEquals("1111", geboorte.getLandGeboorte().getLandcode());
        Assert.assertEquals("hoop zand", geboorte.getOmschrijvingLocatie());

        SortedSet<PersoonVoornaam> persoonVoornamen = kind.getPersoonVoornamen();
        Assert.assertEquals(2, persoonVoornamen.size());
        Iterator<PersoonVoornaam> persoonVoornaamIterator = persoonVoornamen.iterator();
        PersoonVoornaam voornaam1 = persoonVoornaamIterator.next();
        Assert.assertEquals(1, voornaam1.getVolgnummer().intValue());
        Assert.assertEquals("Piet", voornaam1.getNaam());
        PersoonVoornaam voornaam2 = persoonVoornaamIterator.next();
        Assert.assertEquals(2, voornaam2.getVolgnummer().intValue());
        Assert.assertEquals("Veerman", voornaam2.getNaam());

        SortedSet<PersoonGeslachtsnaamcomponent> persoonGeslachtsnaamcomponenten = kind.getGeslachtsnaamcomponenten();
        Iterator<PersoonGeslachtsnaamcomponent> iterator = persoonGeslachtsnaamcomponenten.iterator();
        PersoonGeslachtsnaamcomponent persoonGeslachtsnaamcomponent1 = iterator.next();
        Assert.assertEquals(1, persoonGeslachtsnaamcomponent1.getVolgnummer().intValue());
        Assert.assertEquals("Bokkel", persoonGeslachtsnaamcomponent1.getNaam());
        Assert.assertEquals("van", persoonGeslachtsnaamcomponent1.getVoorvoegsel());
        Assert.assertEquals("/", persoonGeslachtsnaamcomponent1.getScheidingsTeken());
    }

    @Test
    public void testBindingMinBericht() throws IOException, JiBXException {
        String xml = leesBestand("afstamming_InschrijvingAangifteGeboorte_Bijhouding_v0001_MIN.xml");

        BijhoudingsBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);
        Assert.assertNotNull(bericht.getBerichtStuurgegevens());
        Assert.assertNotNull(bericht.getBrpActies());
        Assert.assertEquals(1, bericht.getBrpActies().size());

        BRPActie actie = bericht.getBrpActies().get(0);
        Assert.assertEquals(1012012, actie.getDatumAanvangGeldigheid().intValue());
        Assert.assertEquals(SoortActie.AANGIFTE_GEBOORTE, actie.getSoort());
        Assert.assertNotNull(actie.getRootObjecten());

        RootObject rootObject = actie.getRootObjecten().get(0);
        Assert.assertNotNull(rootObject);
    }

    @Override
    public Class<InschrijvingGeboorteBericht> getBindingClass() {
        return InschrijvingGeboorteBericht.class;
    }

}
