/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.gba.persoon;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.service.bevraging.zoekpersoon.ZoekPersoonVraag;
import nl.bzk.brp.service.bevraging.zoekpersoongeneriek.ValideerZoekCriteriaService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Test voor Ad Hoc Zoek Persoon Webservice (GBA) criteria validator.
 */
public class PersoonsvraagWebserviceVerzoekCriteriaValidatorTest {

    private static final ValideerZoekCriteriaService<ZoekPersoonVraag> validator = Mockito.mock(ValideerZoekCriteriaService.class);
    private static final PersoonsvraagWebserviceVerzoek verzoek = Mockito.mock(PersoonsvraagWebserviceVerzoek.class);
    private static final Autorisatiebundel autorisatiebundel =
            new Autorisatiebundel(Mockito.mock(ToegangLeveringsAutorisatie.class), Mockito.mock(Dienst.class));

    PersoonsvraagWebserviceVerzoekCriteriaValidator subject = new PersoonsvraagWebserviceVerzoekCriteriaValidator(validator);

    @Test
    public void test() {
        Melding melding1 = new Melding(Regel.R2288);
        Melding melding2 = new Melding(Regel.R1403);
        Mockito.when(validator.valideerZoekCriteria(Mockito.any(ZoekPersoonVraag.class), Mockito.any(Autorisatiebundel.class)))
                .thenReturn(Collections.emptySet());
        // Geen meldingen
        Set<Melding> meldingen = subject.valideerZoekCriteria(verzoek, autorisatiebundel);
        Assert.assertNotNull(meldingen);
        Assert.assertTrue(meldingen.isEmpty());
        // Een melding
        Mockito.when(validator.valideerZoekCriteria(Mockito.any(ZoekPersoonVraag.class), Mockito.any(Autorisatiebundel.class)))
                .thenReturn(Collections.singleton(melding1));
        meldingen = subject.valideerZoekCriteria(verzoek, autorisatiebundel);
        Assert.assertNotNull(meldingen);
        Assert.assertFalse(meldingen.isEmpty());
        Assert.assertEquals(1, meldingen.size());
        // Meerdere meldingen
        Mockito.when(validator.valideerZoekCriteria(Mockito.any(ZoekPersoonVraag.class), Mockito.any(Autorisatiebundel.class)))
                .thenReturn(new HashSet<>(Arrays.asList(melding1, melding2)));
        meldingen = subject.valideerZoekCriteria(verzoek, autorisatiebundel);
        Assert.assertNotNull(meldingen);
        Assert.assertFalse(meldingen.isEmpty());
        Assert.assertEquals(2, meldingen.size());
    }
}
