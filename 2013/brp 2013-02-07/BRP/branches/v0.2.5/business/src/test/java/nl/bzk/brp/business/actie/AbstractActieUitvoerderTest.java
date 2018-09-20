/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtenIds;
import nl.bzk.brp.dataaccess.repository.ActieRepository;
import nl.bzk.brp.model.objecttype.bericht.ActieBericht;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortmelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;


public abstract class AbstractActieUitvoerderTest {

    @Mock
    private ActieRepository         actieRepository;

    @Mock
    private AbstractActieUitvoerder abstractActieUitvoerder;

    private Actie                   actie;

    private BerichtContext          context;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(abstractActieUitvoerder, "actieRepository", actieRepository);
        actie = new ActieBericht();
        context = new BerichtContext(new BerichtenIds(1L, 2L), 1, new Partij(), "");
    }

    @Test
    public void testVoeruitRoeptJuisteFunctiesAan() {
        List<Melding> meldingen = abstractActieUitvoerder.voerUit(actie, context);
        Mockito.verify(abstractActieUitvoerder, Mockito.times(1)).valideerActieGegevens(actie);
        Mockito.verify(abstractActieUitvoerder, Mockito.times(1)).bereidActieVerwerkingVoor(actie, context);
        Mockito.verify(abstractActieUitvoerder, Mockito.times(1)).verwerkActie(actie, context);

        Assert.assertTrue(meldingen.isEmpty());
    }

    @Test
    public void testVoeruitStoptNaValideerActieGegevensBijMeldingen() {
        List<Melding> meldingen = new ArrayList<Melding>();
        meldingen.add(new Melding(Soortmelding.OVERRULEBAAR, MeldingCode.ALG0002));
        Mockito.when(abstractActieUitvoerder.valideerActieGegevens(actie)).thenReturn(meldingen);

        List<Melding> meldingenUit = abstractActieUitvoerder.voerUit(actie, context);

        Mockito.verify(abstractActieUitvoerder, Mockito.times(1)).valideerActieGegevens(actie);
        Mockito.verify(abstractActieUitvoerder, Mockito.never()).bereidActieVerwerkingVoor(actie, context);
        Mockito.verify(abstractActieUitvoerder, Mockito.never()).verwerkActie(actie, context);

        Assert.assertNotNull(meldingenUit);
        Assert.assertEquals(meldingenUit.size(), meldingen.size());
    }

    @Test
    public void testVoeruitStoptNaBereidActieVerwerkingVoorBijMeldingen() {
        List<Melding> meldingen = new ArrayList<Melding>();
        meldingen.add(new Melding(Soortmelding.OVERRULEBAAR, MeldingCode.ALG0002));
        Mockito.when(abstractActieUitvoerder.bereidActieVerwerkingVoor(actie, context)).thenReturn(meldingen);

        List<Melding> meldingenUit = abstractActieUitvoerder.voerUit(actie, context);

        Mockito.verify(abstractActieUitvoerder, Mockito.times(1)).valideerActieGegevens(actie);
        Mockito.verify(abstractActieUitvoerder, Mockito.times(1)).bereidActieVerwerkingVoor(actie, context);
        Mockito.verify(abstractActieUitvoerder, Mockito.never()).verwerkActie(actie, context);

        Assert.assertNotNull(meldingenUit);
        Assert.assertEquals(meldingenUit.size(), meldingen.size());
    }

    @Test
    public void testPersisteerActie() {
        abstractActieUitvoerder = new AbstractActieUitvoerder() {

            @Override
            List<Melding> valideerActieGegevens(final Actie eenActie) {
                return null;
            }

            @Override
            List<Melding> verwerkActie(final Actie eenActie, final BerichtContext berichtContext) {
                return null;
            }

            @Override
            List<Melding> bereidActieVerwerkingVoor(final Actie eenActie, final BerichtContext berichtContext) {
                return null;
            }

//            @Override
//            void registreerBijgehoudenPersonen(final BerichtContext berichtContext) {
//                // TODO Auto-generated method stub
//            }
        };
        ReflectionTestUtils.setField(abstractActieUitvoerder, "actieRepository", actieRepository);
        abstractActieUitvoerder.persisteerActie(actie, context);
        Mockito.verify(actieRepository, Mockito.times(1)).opslaanNieuwActie(Matchers.any(ActieModel.class));
    }
}
