/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornaamAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.bericht.kern.PersoonVoornaamBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVoornaamStandaardGroepBericht;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.operationeel.kern.PersoonVoornaamModel;
import org.junit.Assert;
import org.junit.Test;

/** Dit is een tijdelijk test om hibernate mappings uit te proberen voor het nieuwe datamodel. */
public class PersoonVoornaamRepositoryTest extends AbstractRepositoryTestCase {

    @PersistenceContext(unitName = "nl.bzk.brp.lezenschrijven")
    private EntityManager em;

    @Test
    public void testHaalOpPersoonVoornaam() {
        final PersoonVoornaamModel persoonVoornaam = em.find(PersoonVoornaamModel.class, 873);

        Assert.assertEquals(Integer.valueOf(873), persoonVoornaam.getID());
        Assert.assertEquals(Integer.valueOf(2), persoonVoornaam.getVolgnummer().getWaarde());
        Assert.assertNotNull(persoonVoornaam.getPersoon());
        Assert.assertEquals("Johan", persoonVoornaam.getStandaard().getNaam().getWaarde());

        em.find(PersoonModel.class, 8731137);
    }

    @Test
    public void testOpslaanPersoonVoornaam() {
        final PersoonModel persoon = em.find(PersoonModel.class, 8731137);

        final VolgnummerAttribuut volgnummer = new VolgnummerAttribuut(3);
        final VoornaamAttribuut voornaam = new VoornaamAttribuut("Jaap");

        final PersoonVoornaamStandaardGroepBericht persoonVoornaamGroep = new PersoonVoornaamStandaardGroepBericht();
        persoonVoornaamGroep.setNaam(voornaam);

        final PersoonVoornaamBericht persoonVoornaam = new PersoonVoornaamBericht();
        persoonVoornaam.setVolgnummer(volgnummer);
        persoonVoornaam.setStandaard(persoonVoornaamGroep);

        final PersoonVoornaamModel persVoornaam = new PersoonVoornaamModel(persoonVoornaam, persoon);
        em.persist(persVoornaam);

        Assert.assertNotNull(persVoornaam.getID());
    }

    @Test
    public void haalPersoonOp() {
        final Persoon persoon = em.find(PersoonModel.class, 8731137);

        Assert.assertEquals(SoortPersoon.INGESCHREVENE, persoon.getSoort().getWaarde());

        Assert.assertEquals(JaNeeAttribuut.JA, persoon.getSamengesteldeNaam().getIndicatieNamenreeks());
        Assert.assertEquals(JaNeeAttribuut.NEE, persoon.getSamengesteldeNaam().getIndicatieAfgeleid());

        Assert.assertEquals("135867277", persoon.getIdentificatienummers().getBurgerservicenummer().toString());
        Assert.assertEquals("1010104351", persoon.getIdentificatienummers().getAdministratienummer().toString());

        Assert.assertEquals(Geslachtsaanduiding.MAN,
                            persoon.getGeslachtsaanduiding().getGeslachtsaanduiding().getWaarde());

        Assert.assertEquals(3, persoon.getVoornamen().size());
    }

}
