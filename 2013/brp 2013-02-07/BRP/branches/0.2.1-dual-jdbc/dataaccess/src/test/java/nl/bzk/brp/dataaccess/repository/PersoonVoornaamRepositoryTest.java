/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import junit.framework.Assert;
import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.attribuuttype.Volgnummer;
import nl.bzk.brp.model.attribuuttype.Voornaam;
import nl.bzk.brp.model.groep.bericht.PersoonVoornaamStandaardGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonVoornaamBericht;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonVoornaamModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Geslachtsaanduiding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortPersoon;
import org.junit.Test;


/** Dit is een tijdelijk test om hibernate mappings uit te proberen voor het nieuwe datamodel. */
public class PersoonVoornaamRepositoryTest extends AbstractRepositoryTestCase {

    @PersistenceContext(unitName="nl.bzk.brp")
    private EntityManager em;

    @Test
    public void testHaalOpPersoonVoornaam() {

        PersoonVoornaamModel persoonVoornaam = em.find(PersoonVoornaamModel.class, 873);

        Assert.assertEquals(Integer.valueOf(873), persoonVoornaam.getId());
        Assert.assertEquals(Integer.valueOf(2), persoonVoornaam.getVolgnummer().getWaarde());
        Assert.assertNotNull(persoonVoornaam.getPersoon());
        Assert.assertEquals("Johan", persoonVoornaam.getGegevens().getVoornaam().getWaarde());

        Persoon persoon = em.find(PersoonModel.class, 8731137);


    }

    @Test
    public void testOpslaanPersoonVoornaam() {
        PersoonModel persoon = em.find(PersoonModel.class, 8731137);

        Volgnummer volgnummer = new Volgnummer(3);
        Voornaam voornaam = new Voornaam("Jaap");

        PersoonVoornaamStandaardGroepBericht persoonVoornaamGroep = new PersoonVoornaamStandaardGroepBericht();
        persoonVoornaamGroep.setVoornaam(voornaam);

        PersoonVoornaamBericht persoonVoornaam = new PersoonVoornaamBericht();
        persoonVoornaam.setVolgnummer(volgnummer);
        persoonVoornaam.setGegevens(persoonVoornaamGroep);

        PersoonVoornaamModel persVoornaam = new PersoonVoornaamModel(persoonVoornaam, persoon);
        em.persist(persVoornaam);

        Assert.assertNotNull(persVoornaam.getId());
    }

    @Test
    public void haalPersoonOp() {
        Persoon persoon = em.find(PersoonModel.class, 8731137);

        Assert.assertEquals(SoortPersoon.INGESCHREVENE, persoon.getSoort());

        Assert.assertEquals(JaNee.Ja, persoon.getSamengesteldeNaam().getIndNamenreeksAlsGeslachtsNaam());
        Assert.assertEquals(JaNee.Nee, persoon.getSamengesteldeNaam().getIndAlgorithmischAfgeleid());

        Assert.assertEquals("135867277", persoon.getIdentificatienummers().getBurgerservicenummer().getWaarde());
        Assert.assertEquals(Long.valueOf(1010104351L),
            persoon.getIdentificatienummers().getAdministratienummer().getWaarde());

        Assert.assertEquals(Geslachtsaanduiding.MAN, persoon.getGeslachtsaanduiding().getGeslachtsaanduiding());

        Assert.assertEquals(2, persoon.getPersoonVoornaam().size());
    }

}
