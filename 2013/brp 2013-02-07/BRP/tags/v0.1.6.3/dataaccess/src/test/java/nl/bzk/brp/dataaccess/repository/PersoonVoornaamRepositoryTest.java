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
import nl.bzk.brp.model.groep.bericht.PersoonVoornaamStandaardGroepBericht;
import nl.bzk.brp.model.groep.logisch.PersoonVoornaamStandaardGroep;
import nl.bzk.brp.model.objecttype.bericht.PersoonVoornaamBericht;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonVoornaamModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.GeslachtsAanduiding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortPersoon;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


/** Dit is een tijdelijk test om hibernate mappings uit te proberen voor het nieuwe datamodel. */
public class PersoonVoornaamRepositoryTest extends AbstractRepositoryTestCase {

    @PersistenceContext
    private EntityManager em;

    @Test
    public void testHaalOpPersoonVoornaam() {

        PersoonVoornaamModel persoonVoornaam = em.find(PersoonVoornaamModel.class, 873L);

        Assert.assertEquals(Long.valueOf(873), persoonVoornaam.getId());
        Assert.assertEquals(Integer.valueOf(2), persoonVoornaam.getVolgnummer().getWaarde());
        Assert.assertNotNull(persoonVoornaam.getPersoon());
        Assert.assertEquals("Johan", persoonVoornaam.getGegevens().getVoornaam().getWaarde());

        Persoon persoon = em.find(PersoonModel.class, 8731137L);


    }

    @Test
    public void testOpslaanPersoonVoornaam() {
        PersoonModel persoon = em.find(PersoonModel.class, 8731137L);

        Volgnummer volgnummer = new Volgnummer(3);
        Voornaam voornaam = new Voornaam("Jaap");

        PersoonVoornaamStandaardGroep persoonVoornaamStandaardGroep = new PersoonVoornaamStandaardGroepBericht();
        ReflectionTestUtils.setField(persoonVoornaamStandaardGroep, "voornaam", voornaam);

        PersoonVoornaamBericht persoonVoornaam = new PersoonVoornaamBericht();
        ReflectionTestUtils.setField(persoonVoornaam, "volgnummer", volgnummer);
        ReflectionTestUtils.setField(persoonVoornaam, "gegevens", persoonVoornaamStandaardGroep);

        PersoonVoornaamModel persVoornaamMdl = new PersoonVoornaamModel(persoonVoornaam, persoon);
        em.persist(persVoornaamMdl);

        Assert.assertNotNull(persVoornaamMdl.getId());
    }

    @Test
    public void haalPersoonOp() {
        Persoon persoon = em.find(PersoonModel.class, 8731137L);

        Assert.assertEquals(SoortPersoon.INGESCHREVENE, persoon.getSoort());

        Assert.assertEquals(JaNee.Ja, persoon.getSamengesteldeNaam().getIndNamenreeksAlsGeslachtsNaam());
        Assert.assertEquals(JaNee.Nee, persoon.getSamengesteldeNaam().getIndAlgorithmischAfgeleid());

        Assert.assertEquals("135867277", persoon.getIdentificatieNummers().getBurgerServiceNummer().getWaarde());
        Assert.assertEquals("1010104351", persoon.getIdentificatieNummers().getAdministratieNummer().getWaarde());

        Assert.assertEquals(GeslachtsAanduiding.MAN, persoon.getGeslachtsAanduiding().getGeslachtsAanduiding());

        Assert.assertEquals(2, persoon.getPersoonVoornaam().size());
    }

}
