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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Volgnummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Voornaam;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.bericht.kern.PersoonVoornaamBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVoornaamStandaardGroepBericht;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.operationeel.kern.PersoonVoornaamModel;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;


/** Dit is een tijdelijk test om hibernate mappings uit te proberen voor het nieuwe datamodel. */
public class PersoonVoornaamRepositoryTest extends AbstractRepositoryTestCase {

    @PersistenceContext(unitName = "nl.bzk.brp")
    private EntityManager em;

    @Test
    public void testHaalOpPersoonVoornaam() {
        PersoonVoornaamModel persoonVoornaam = em.find(PersoonVoornaamModel.class, 873);

        assertEquals(Integer.valueOf(873), persoonVoornaam.getID());
        assertEquals(Integer.valueOf(2), persoonVoornaam.getVolgnummer().getWaarde());
        Assert.assertNotNull(persoonVoornaam.getPersoon());
        assertEquals("Johan", persoonVoornaam.getStandaard().getNaam().getWaarde());

        Persoon persoon = em.find(PersoonModel.class, 8731137);
    }

    @Test
    public void testOpslaanPersoonVoornaam() {
        PersoonModel persoon = em.find(PersoonModel.class, 8731137);

        Volgnummer volgnummer = new Volgnummer(3);
        Voornaam voornaam = new Voornaam("Jaap");

        PersoonVoornaamStandaardGroepBericht persoonVoornaamGroep = new PersoonVoornaamStandaardGroepBericht();
        persoonVoornaamGroep.setNaam(voornaam);

        PersoonVoornaamBericht persoonVoornaam = new PersoonVoornaamBericht();
        persoonVoornaam.setVolgnummer(volgnummer);
        persoonVoornaam.setStandaard(persoonVoornaamGroep);

        PersoonVoornaamModel persVoornaam = new PersoonVoornaamModel(persoonVoornaam, persoon);
        em.persist(persVoornaam);

        Assert.assertNotNull(persVoornaam.getID());
    }

    @Test
    public void haalPersoonOp() {
        Persoon persoon = em.find(PersoonModel.class, 8731137);

        assertEquals(SoortPersoon.INGESCHREVENE, persoon.getSoort());

        assertEquals(JaNee.JA, persoon.getSamengesteldeNaam().getIndicatieNamenreeks());
        assertEquals(JaNee.NEE, persoon.getSamengesteldeNaam().getIndicatieAlgoritmischAfgeleid());

        assertEquals("135867277", persoon.getIdentificatienummers().getBurgerservicenummer().toString());
        assertEquals("1010104351", persoon.getIdentificatienummers().getAdministratienummer().toString());

        assertEquals(Geslachtsaanduiding.MAN, persoon.getGeslachtsaanduiding().getGeslachtsaanduiding());

        assertEquals(3, persoon.getVoornamen().size());
    }

}
