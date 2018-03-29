/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.berichtmodel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import org.junit.Before;
import org.junit.Test;

public class VrijBerichtVerwerkBerichtTest {

    private Partij ontvangendePartij;
    private Partij zendendePartij;
    private BerichtVrijBericht berichtVrijBericht;
    private BasisBerichtGegevens basisBerichtGegevens;

    @Before
    public void before() {
        ontvangendePartij = TestPartijBuilder.maakBuilder().metId(1).metCode("000123").metDatumOvergangNaarBrp(DatumUtil.datumRondVandaag(1)).build();
        ontvangendePartij.setAfleverpuntVrijBericht("afleverpunt");
        zendendePartij = TestPartijBuilder.maakBuilder().metId(2).metCode("000456").metDatumOvergangNaarBrp(DatumUtil.datumRondVandaag(1)).build();
        zendendePartij.setAfleverpuntVrijBericht("afleverpunt");
        final VrijBericht vrijBericht = new VrijBericht("vrij bericht", null);
        berichtVrijBericht = new BerichtVrijBericht(vrijBericht);
        basisBerichtGegevens = BasisBerichtGegevens.builder()
                .metStuurgegevens()
                .metReferentienummer("refNr")
                .metTijdstipVerzending(DatumUtil.nuAlsZonedDateTime())
                .eindeStuurgegevens().build();
    }

    @Test
    public void test_maakVolledig() {
        final VrijBerichtVerwerkBericht verwerkBericht = new VrijBerichtVerwerkBericht(basisBerichtGegevens, berichtVrijBericht,
                new VrijBerichtParameters(zendendePartij, ontvangendePartij));
        assertEquals(berichtVrijBericht, verwerkBericht.getBerichtVrijBericht());
        assertEquals(basisBerichtGegevens, verwerkBericht.getBasisBerichtGegevens());
    }

    @Test
    public void test_exclusiefParameters() {
        final VrijBericht vrijBericht = new VrijBericht("vrij bericht", null);
        final VrijBerichtVerwerkBericht verwerkBericht = new VrijBerichtVerwerkBericht(basisBerichtGegevens, berichtVrijBericht, null) ;
        assertEquals(berichtVrijBericht, verwerkBericht.getBerichtVrijBericht());
        assertEquals(basisBerichtGegevens, verwerkBericht.getBasisBerichtGegevens());
        assertNull(verwerkBericht.getVrijBerichtParameters());
    }

    @Test
    public void test_exclusiefBerichtVrijBericht() {
        final VrijBericht vrijBericht = new VrijBericht("vrij bericht", null);
        final VrijBerichtVerwerkBericht verwerkBericht = new VrijBerichtVerwerkBericht(basisBerichtGegevens, null, null);
        assertNull(verwerkBericht.getBerichtVrijBericht());
        assertEquals(basisBerichtGegevens, verwerkBericht.getBasisBerichtGegevens());
        assertNull(verwerkBericht.getVrijBerichtParameters());
    }
//
//    @Test
//    public void test_exclusiefBerichtVrijBericht_InclusiefParameters() {
//        final VrijBericht vrijBericht = new VrijBericht("vrij bericht", null);
//        final VrijBerichtVerwerkBericht verwerkBericht = new VrijBerichtVerwerkBericht(basisBerichtGegevens, berichtVrijBericht,
//                new VrijBerichtVerwerkBericht.VrijBerichtParameters(zendendePartij, ontvangendePartij));
//        assertNull(verwerkBericht.getBerichtVrijBericht());
//        assertEquals(basisBerichtGegevens, verwerkBericht.getBasisBerichtGegevens());
//        assertEquals(ontvangendePartij, verwerkBericht.getVrijBerichtParameters().getOntvangerVrijBericht());
//        assertEquals(zendendePartij, verwerkBericht.getVrijBerichtParameters().getZenderVrijBericht());
//    }
}