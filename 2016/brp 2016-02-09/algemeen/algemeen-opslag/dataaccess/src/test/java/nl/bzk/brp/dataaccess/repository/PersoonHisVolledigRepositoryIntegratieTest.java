/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.Date;

import javax.inject.Inject;

import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieNationaliteitBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;

public class PersoonHisVolledigRepositoryIntegratieTest extends AbstractRepositoryTestCase {

    @Inject
    private PersoonHisVolledigRepository persoonHisVolledigRepository;

    @Inject
    private ActieRepository actieRepository;

    @Test
    public void haalPersoonOpTest() throws IOException {
        final int persoonId = 1;

        final PersoonHisVolledig jpaEntity = persoonHisVolledigRepository.leesGenormalizeerdModel(persoonId);

        assertNotNull(jpaEntity);
    }

    @Test
    public void testOpslaanNieuwePersoon() {
        final ActieBericht actieBericht = new ActieRegistratieNationaliteitBericht();
        actieBericht.setTijdstipRegistratie(new DatumTijdAttribuut(new Date()));
        actieBericht.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20010101));

        ActieModel actie = new ActieModel(actieBericht, null);
        actie = actieRepository.opslaanNieuwActie(actie);

        final PersoonHisVolledigImpl persoon = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwIdentificatienummersRecord(actie)
                    .burgerservicenummer(123459876)
                .eindeRecord().build();
        final PersoonHisVolledig opgeslagenPersoon = persoonHisVolledigRepository.opslaanNieuwPersoon(persoon);
        Assert.assertNotNull(opgeslagenPersoon.getID());
    }

}
