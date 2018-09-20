/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.berichtenverwerker.dataaccess;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import nl.bzk.brp.poc.berichtenverwerker.model.Actie;
import nl.bzk.brp.poc.berichtenverwerker.model.Nation;
import nl.bzk.brp.poc.berichtenverwerker.model.Pers;
import nl.bzk.brp.poc.berichtenverwerker.model.Persnation;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Abstracte (generieke) Unit test voor de {@link PersoonsNationaliteitDAO} class en de in deze DAO gedefinieerde
 * methodes.
 */
public abstract class AbstractPersoonsNationaliteitDAOTest extends AbstractDAOTest {

    @Autowired
    private PersoonsNationaliteitDAO persoonsNationaliteitDAO;

    /**
     * Unit test voor de {@link PersoonsNationaliteitDAO#vindPersoonsNationaliteitOpBasisVanId(long)} methode.
     */
    public final void testVindPersoonsNationaliteitOpBasisVanId() {
        Persnation result = persoonsNationaliteitDAO.vindPersoonsNationaliteitOpBasisVanId(2L);

        assertNotNull(result);
        assertEquals("Onverwachte ID gevonden", 2L, result.getId());
        assertEquals("Onverwachte Persoon gevonden", 1L, result.getPers().getId());
        assertEquals("Onverwachte Nationaliteit gevonden", 2, result.getNation().getId());
    }

    /**
     * Unit test voor de {@link PersoonsNationaliteitDAO#voegToePersoonsNationaliteit(Persnation)} methode.
     */
    public final void testVoegToePersoonsNationaliteit() {
        final int aantalPersoonsNationaliteiten = countRowsInTable("Kern.PERSNATION");
        final int aantalHistorischPersoonsNationaliteiten = countRowsInTable("Kern.HISPERSNATION");

        Nation nationaliteit = new Nation(1, null);
        Pers persoon = new Pers();
        persoon.setId(2);
        Persnation persoonsNationaliteit = new Persnation(0, nationaliteit, persoon);
        Actie actie = new Actie();
        actie.setId(2);
        persoonsNationaliteit.setActie(actie);

        persoonsNationaliteitDAO.voegToePersoonsNationaliteit(persoonsNationaliteit);

        assertNotNull(persoonsNationaliteit.getId());
        assertTrue(persoonsNationaliteit.getId() != 0);
        assertEquals("Onverwacht aantal persoonsnationaliteiten in database", aantalPersoonsNationaliteiten + 1,
                countRowsInTable("Kern.PERSNATION"));
        assertEquals("Onverwacht aantal historische persoonsnationaliteiten in database",
                aantalHistorischPersoonsNationaliteiten + 1, countRowsInTable("Kern.HISPERSNATION"));

        // Controleer of de Persoon naam niet is gewijzigd door bovenstaande insert
        String persoonNaam =
                simpleJdbcTemplate.queryForObject("SELECT voornamen FROM Kern.Pers WHERE id = ?", String.class,
                        persoon.getId());
        assertEquals("Persoon naam is onterecht gewijzigd", "Test", persoonNaam);
    }

    /**
     * Unit test voor de {@link PersoonsNationaliteitDAO#verwijderPersoonsNationaliteit(Persnation)} methode.
     */
    public final void testVerwijderPersoonsNationaliteit() {
        final int aantalPersoonsNationaliteiten = countRowsInTable("Kern.PERSNATION");
        final int aantalHistorischPersoonsNationaliteiten = countRowsInTable("Kern.HISPERSNATION");

        Persnation persoonsNationaliteit = persoonsNationaliteitDAO.vindPersoonsNationaliteitOpBasisVanId(2);
        Actie actie = new Actie();
        actie.setId(2);
        persoonsNationaliteitDAO.verwijderPersoonsNationaliteit(actie, persoonsNationaliteit);

        // Controleer of database werkelijk is aangepast
        assertEquals("Onverwacht aantal persoonsnationaliteiten in database", aantalPersoonsNationaliteiten - 1,
                countRowsInTable("Kern.PERSNATION"));
        assertEquals("Onverwacht aantal historische persoonsnationaliteiten in database",
                aantalHistorischPersoonsNationaliteiten, countRowsInTable("Kern.HISPERSNATION"));
    }

    @Override
    protected final String getXmlDataSetFileName() {
        return "PersoonsNationaliteitDAOImpl.xml";
    }

}
