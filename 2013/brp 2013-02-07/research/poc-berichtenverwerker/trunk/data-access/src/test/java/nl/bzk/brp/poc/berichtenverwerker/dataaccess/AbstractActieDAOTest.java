/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.berichtenverwerker.dataaccess;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import nl.bzk.brp.poc.berichtenverwerker.model.Actie;
import nl.bzk.brp.poc.berichtenverwerker.model.Gem;
import nl.bzk.brp.poc.berichtenverwerker.model.Srtactie;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Abstracte (generieke) Unit test voor de {@link ActieDAO} class en de in deze DAO gedefinieerde methodes.
 */
public abstract class AbstractActieDAOTest extends AbstractDAOTest {

    @Autowired
    private ActieDAO actieDao;

    /**
     * Unit test voor de {@link ActieDAO#vindActieOpBasisVanId(long)} methode.
     */
    public final void testVindActieOpBasisVanId() {
        Actie result = actieDao.vindActieOpBasisVanId(1L);

        assertNotNull(result);
        assertEquals("Onverwachte Gemeente gevonden", 2L, result.getGem().getId());
        assertEquals("Onverwachte ID gevonden", 1L, result.getId());
        assertEquals("Onverwachte Actie Soort gevonden", 2L, result.getSrtactie().getId());
    }

    /**
     * Unit test voor de {@link ActieDAO#voegToeActie(Actie)} methode.
     */
    public final void testVoegToeActie() {
        assertEquals("Onverwacht aantal acties reeds in database", 1, countRowsInTable("Kern.ACTIE"));

        Srtactie soort = new Srtactie(2, null);
        Actie actie = new Actie(0, soort, new Date());
        actie.setGem(new Gem(1, null));

        actieDao.voegToeActie(actie);

        assertNotNull(actie.getId());
        assertEquals("Onverwacht aantal acties in database", 2, countRowsInTable("Kern.ACTIE"));

        // Controleer of de Gemeente naam niet is gewijzigd door bovenstaande insert
        String gemeenteNaam = simpleJdbcTemplate.queryForObject("SELECT Naam FROM Kern.GEM WHERE id = ?", String.class,
                actie.getGem().getId());
        assertEquals("Gemeente naam is onterecht gewijzigd", "Gemeente 1", gemeenteNaam);
    }

    @Override
    protected final String getXmlDataSetFileName() {
        return "ActieDAOImpl.xml";
    }

}
