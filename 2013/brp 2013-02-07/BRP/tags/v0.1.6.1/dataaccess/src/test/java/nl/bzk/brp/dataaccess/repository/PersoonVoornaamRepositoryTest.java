/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import junit.framework.Assert;
import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.attribuuttype.Volgnummer;
import nl.bzk.brp.model.attribuuttype.Voornaam;
import nl.bzk.brp.model.groep.impl.usr.PersoonVoornaamStandaardGroepMdl;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonVoornaamStandaardGroep;
import nl.bzk.brp.model.objecttype.impl.usr.PersoonMdl;
import nl.bzk.brp.model.objecttype.impl.usr.PersoonVoornaamMdl;
import nl.bzk.brp.model.objecttype.interfaces.usr.Persoon;
import nl.bzk.brp.model.objecttype.statisch.GeslachtsAanduiding;
import nl.bzk.brp.model.objecttype.statisch.SoortPersoon;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


/**
 * Dit is een tijdelijk test om hibernate mappings uit te proberen voor het nieuwe datamodel.
 *
 */
public class PersoonVoornaamRepositoryTest extends AbstractRepositoryTestCase {

    @PersistenceContext
    private EntityManager em;

    @Test
    public void testHaalOpPersoonVoornaam() {

        PersoonVoornaamMdl persoonVoornaam = em.find(PersoonVoornaamMdl.class, 873L);

        Assert.assertEquals(Long.valueOf(873), persoonVoornaam.getId());
        Assert.assertEquals(Integer.valueOf(2), persoonVoornaam.getVolgnummer().getWaarde());
        Assert.assertNotNull(persoonVoornaam.getPersoon());
        Assert.assertEquals("Johan", persoonVoornaam.getGegevens().getVoornaam().getWaarde());

        Persoon persoon = em.find(PersoonMdl.class, 8731137L);


    }

    @Test
    public void testOpslaanPersoonVoornaam() {
        Persoon persoon = em.find(PersoonMdl.class, 8731137L);

        Volgnummer volgnummer = new Volgnummer(3);
        Voornaam voornaam = new Voornaam("Jaap");

        PersoonVoornaamStandaardGroep persoonVoornaamStandaardGroep = new PersoonVoornaamStandaardGroepMdl();
        ReflectionTestUtils.setField(persoonVoornaamStandaardGroep, "voornaam", voornaam);

        PersoonVoornaamMdl persoonVoornaam = new PersoonVoornaamMdl();
        ReflectionTestUtils.setField(persoonVoornaam, "volgnummer", volgnummer);
        ReflectionTestUtils.setField(persoonVoornaam, "gegevens", persoonVoornaamStandaardGroep);

        em.persist(persoonVoornaam);

        Assert.assertNotNull(persoonVoornaam.getId());
    }

    @Test
    public void haalPersoonOp() {
        Persoon persoon = em.find(PersoonMdl.class, 8731137L);

        Assert.assertEquals(SoortPersoon.INGESCHREVENE, persoon.getSoort());

        Assert.assertEquals(JaNee.Ja, persoon.getSamengesteldeNaam().getIndNamenreeksAlsGeslachtsNaam());
        Assert.assertEquals(JaNee.Nee, persoon.getSamengesteldeNaam().getIndAlgorithmischAfgeleid());

        Assert.assertEquals("135867277", persoon.getIdentificatieNummers().getBurgerServiceNummer().getWaarde());
        Assert.assertEquals("1010104351", persoon.getIdentificatieNummers().getAdministratieNummer().getWaarde());

        Assert.assertEquals(GeslachtsAanduiding.MAN, persoon.getGeslachtsAanduiding().getGeslachtsAanduiding());

        Assert.assertEquals(2, persoon.getPersoonVoornaam().size());
    }

}
