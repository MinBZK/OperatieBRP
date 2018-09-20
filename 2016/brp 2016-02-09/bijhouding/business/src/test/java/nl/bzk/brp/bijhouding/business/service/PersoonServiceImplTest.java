/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.util.PersoonBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

/**
 */
@RunWith(MockitoJUnitRunner.class)
public class PersoonServiceImplTest {

    @Mock
    private PersoonRepository persoonRepository;

    @InjectMocks
    private final PersoonService persoonService = new PersoonServiceImpl();

    @Test
    public void testPersoonIdentiekNullNull() {
        // sorry, als een van de twee of beide null => weet niet => false
        assertFalse(persoonService.isPersoonBerichtIdentiekAlsPersoonModel(null, null));
        assertFalse(persoonService.isPersoonBerichtIdentiekAlsPersoonModel(new PersoonBericht(), null));
        assertFalse(persoonService.isPersoonBerichtIdentiekAlsPersoonModel(null, new PersoonModel(new PersoonBericht())));
    }

    @Test
    public void testPersoonIdentiekBeideNietIdentificeerbaar() {
        final PersoonBericht persoonBericht = new PersoonBericht();
        final PersoonModel persoonModel = new PersoonModel(persoonBericht);
        assertFalse(persoonService.isPersoonBerichtIdentiekAlsPersoonModel(persoonBericht, persoonModel));
    }

    @Test
    public void testPersoonIdentiek() {
        final PersoonBericht persoonBericht = PersoonBuilder.bouwRefererendPersoon(124253);
        final PersoonModel persoonModel = new PersoonModel(persoonBericht);
        assertTrue(persoonService.isPersoonBerichtIdentiekAlsPersoonModel(persoonBericht, persoonModel));
    }

    @Test
    public void testPersoonIdentiekZonderBsnAlleenTechnischeSleutel() {
        final PersoonBericht persoonBericht = PersoonBuilder.bouwRefererendPersoon(124253);
        final PersoonModel persoonModel = new PersoonModel(persoonBericht);
        // sloop nu de identificatienrs.bsn (hadden we nodig om model te bouwen); de technische sleutel zit er nog wel.
        persoonBericht.setIdentificatienummers(null);
        assertTrue(persoonService.isPersoonBerichtIdentiekAlsPersoonModel(persoonBericht, persoonModel));
    }

    @Test
    public void testPersoonIdentiekMetBsnZonderTechnischeSleutel() {
        final PersoonBericht persoonBericht = PersoonBuilder.bouwRefererendPersoon(124253);
        final PersoonModel persoonModel = new PersoonModel(persoonBericht);
        // sloop nu de technische sleutel
        persoonBericht.setObjectSleutel((String) null);
        assertTrue(persoonService.isPersoonBerichtIdentiekAlsPersoonModel(persoonBericht, persoonModel));
    }

    @Test
    public void testPersoonIdentiekMetANrZonderTechnischeSleutel() {
        final PersoonBericht persoonBericht = PersoonBuilder.bouwRefererendPersoon(124253);
        persoonBericht.getIdentificatienummers().setAdministratienummer(new AdministratienummerAttribuut(158888L));
        final PersoonModel persoonModel = new PersoonModel(persoonBericht);
        // sloop nu de technische sleutel
        persoonBericht.setObjectSleutel((String) null);
        persoonBericht.getIdentificatienummers().setBurgerservicenummer(null);
        assertTrue(persoonService.isPersoonBerichtIdentiekAlsPersoonModel(persoonBericht, persoonModel));
    }

    @Test
    public void testPersoonIdentiekZonderANrZonderTechnischeSleutelEnModelNiets() {
        final PersoonBericht persoonBericht = PersoonBuilder.bouwRefererendPersoon(124253);
        final PersoonModel persoonModel = new PersoonModel(persoonBericht);
        // sloop nu de technische sleutel
        persoonBericht.setObjectSleutel((String) null);
        persoonBericht.getIdentificatienummers().setAdministratienummer(new AdministratienummerAttribuut(158888L));
        persoonBericht.getIdentificatienummers().setBurgerservicenummer(null);
        // Bericht heeft GEEN bsn, Model GEEN bsn, anr ==> twee niet ingeschreven ==> per definitie anders
        assertFalse(persoonService.isPersoonBerichtIdentiekAlsPersoonModel(persoonBericht, persoonModel));
    }

    @Test
    public void testPersoonIdentiekMetANrZonderTechnischeSleutelEnModelNiets() {
        final PersoonBericht persoonBericht = PersoonBuilder.bouwRefererendPersoon(124253);
        final PersoonModel persoonModel = new PersoonModel(persoonBericht);
        // sloop nu de technische sleutel
        persoonBericht.setObjectSleutel((String) null);
        persoonBericht.getIdentificatienummers().setAdministratienummer(null);
        persoonBericht.getIdentificatienummers().setBurgerservicenummer(null);
        // Bericht heeft GEEN bsn, anr, Model ook GEEN ==> twee niet ingeschreven ==> per definitie anders
        assertFalse(persoonService.isPersoonBerichtIdentiekAlsPersoonModel(persoonBericht, persoonModel));
    }
    @Test
    public void testPersoonAnders() {
        final PersoonBericht persoonBericht = PersoonBuilder.bouwRefererendPersoon(124253);
        persoonBericht.getIdentificatienummers().setAdministratienummer(new AdministratienummerAttribuut(158888L));
        final PersoonModel persoonModel = new PersoonModel(persoonBericht);
        // sloop nu de technische sleutel
        persoonBericht.setObjectSleutel((String) null);
        persoonBericht.setIdentificatienummers(null);
        assertFalse(persoonService.isPersoonBerichtIdentiekAlsPersoonModel(persoonBericht, persoonModel));
    }

    @Test
    public void testAlleenOpID() {
        final PersoonBericht persoonBericht = PersoonBuilder.bouwRefererendPersoon(124253);
        persoonBericht.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        final PersoonModel persoonModel = new PersoonModel(persoonBericht);
        ReflectionTestUtils.setField(persoonModel, "iD", 124253);
        persoonBericht.setIdentificatienummers(null);
        // model heeft alleen een id, en bericht heeft de technische sleutel
        assertTrue(persoonService.isPersoonBerichtIdentiekAlsPersoonModel(persoonBericht, persoonModel));
    }

    @Test
    public void testIdentiekNietIngeschrevene() {
        final PersoonBericht persoonBericht = PersoonBuilder.bouwRefererendPersoon(124253);
        persoonBericht.setSoort(new SoortPersoonAttribuut(SoortPersoon.NIET_INGESCHREVENE));
        final PersoonModel persoonModel = new PersoonModel(persoonBericht);
        ReflectionTestUtils.setField(persoonModel, "iD", 124253);
        persoonBericht.setIdentificatienummers(null);
        persoonBericht.setObjectSleutel("db" + persoonBericht.getObjectSleutel());
        // model heeft alleen een id, en bericht heeft de technische sleutel
        assertTrue(persoonService.isPersoonBerichtIdentiekAlsPersoonModel(persoonBericht, persoonModel));
    }

    @Test
    public void testIsIdentiekPersoonMetNull() {
        // een of beide null ==> false
        assertFalse(persoonService.isIdentiekPersoon(null, null));
        assertFalse(persoonService.isIdentiekPersoon(PersoonBuilder.bouwRefererendPersoon(124253), null));
        assertFalse(persoonService.isIdentiekPersoon(null, PersoonBuilder.bouwRefererendPersoon(124253)));
    }

    @Test
    public void testIsIdentiekPersoon() {
        final PersoonBericht persoonBericht = PersoonBuilder.bouwRefererendPersoon(124253);
        persoonBericht.setObjectSleutelDatabaseID(124253);

        final PersoonBericht persoonRefID = new PersoonBericht();
        persoonRefID.setReferentieID(persoonBericht.getCommunicatieID());

        final PersoonBericht persoonObjectSleutel = new PersoonBericht();
        persoonObjectSleutel.setObjectSleutelDatabaseID(persoonBericht.getObjectSleutelDatabaseID());

        assertTrue(persoonService.isIdentiekPersoon(persoonBericht, persoonBericht));
        assertTrue(persoonService.isIdentiekPersoon(persoonBericht, persoonRefID));
        assertTrue(persoonService.isIdentiekPersoon(persoonRefID, persoonBericht));
        assertTrue(persoonService.isIdentiekPersoon(persoonBericht, persoonObjectSleutel));
        assertTrue(persoonService.isIdentiekPersoon(persoonObjectSleutel, persoonBericht));
    }

    @Test
    public void testIsIdentiekPersoonFalse() {
        final PersoonBericht persoonBericht = PersoonBuilder.bouwRefererendPersoon(124253);
        persoonBericht.setObjectSleutelDatabaseID(124253);
        final PersoonBericht persoonBericht2 = PersoonBuilder.bouwRefererendPersoon(8888888);
        persoonBericht2.setObjectSleutelDatabaseID(8888888);

        final PersoonBericht persoonRefID = new PersoonBericht();
        persoonRefID.setReferentieID("Any RefID");

        final PersoonBericht persoonObjectSleutel = new PersoonBericht();
        persoonObjectSleutel.setObjectSleutel("Any techSleutel");
        persoonObjectSleutel.setObjectSleutelDatabaseID(123);

        assertFalse(persoonService.isIdentiekPersoon(persoonBericht, persoonBericht2));
        assertFalse(persoonService.isIdentiekPersoon(persoonBericht, persoonRefID));
        assertFalse(persoonService.isIdentiekPersoon(persoonBericht, persoonObjectSleutel));
        assertFalse(persoonService.isIdentiekPersoon(persoonBericht, new PersoonBericht()));
    }

    @Test
    public void testIsNevenActiePersoonIdentiekAanHoofdPersoon() {
        final PersoonBericht persoonBericht1 = PersoonBuilder.bouwRefererendPersoon(124253);
        persoonBericht1.setObjectSleutelDatabaseID(124253);
        final PersoonBericht persoonBericht = PersoonBuilder.bouwRefererendPersoon(8888888);
        persoonBericht.setObjectSleutelDatabaseID(8888888);
        final List<PersoonBericht> hoofdPersonen = Arrays.asList(
                persoonBericht, persoonBericht1);

        final PersoonBericht persoonAnders = PersoonBuilder.bouwRefererendPersoon(44444444);

        final PersoonBericht persoonRefID = new PersoonBericht();
        final PersoonBericht persoonObjectSleutel = new PersoonBericht();

        assertFalse(persoonService.isPersoonNevenActieIdentiekHoofdPersoon(null, null));
        assertFalse(persoonService.isPersoonNevenActieIdentiekHoofdPersoon(null, hoofdPersonen));
        assertFalse(persoonService.isPersoonNevenActieIdentiekHoofdPersoon(persoonAnders, null));
        assertFalse(persoonService.isPersoonNevenActieIdentiekHoofdPersoon(persoonAnders, hoofdPersonen));

        persoonRefID.setReferentieID(hoofdPersonen.get(0).getCommunicatieID());
        assertTrue(persoonService.isPersoonNevenActieIdentiekHoofdPersoon(persoonRefID, hoofdPersonen));
        persoonRefID.setReferentieID(hoofdPersonen.get(1).getCommunicatieID());
        assertTrue(persoonService.isPersoonNevenActieIdentiekHoofdPersoon(persoonRefID, hoofdPersonen));
        persoonRefID.setReferentieID("Any Ref");
        assertFalse(persoonService.isPersoonNevenActieIdentiekHoofdPersoon(persoonRefID, hoofdPersonen));

        persoonObjectSleutel.setObjectSleutelDatabaseID(hoofdPersonen.get(0).getObjectSleutelDatabaseID());
        assertTrue(persoonService.isPersoonNevenActieIdentiekHoofdPersoon(persoonObjectSleutel, hoofdPersonen));
        persoonObjectSleutel.setObjectSleutelDatabaseID(hoofdPersonen.get(1).getObjectSleutelDatabaseID());
        assertTrue(persoonService.isPersoonNevenActieIdentiekHoofdPersoon(persoonObjectSleutel, hoofdPersonen));
        persoonObjectSleutel.setObjectSleutelDatabaseID(89741);
        assertFalse(persoonService.isPersoonNevenActieIdentiekHoofdPersoon(persoonObjectSleutel, hoofdPersonen));
    }
}
