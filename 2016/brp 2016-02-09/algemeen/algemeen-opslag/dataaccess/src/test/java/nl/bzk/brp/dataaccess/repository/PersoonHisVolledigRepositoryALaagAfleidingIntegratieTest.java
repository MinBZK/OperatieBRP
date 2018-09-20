/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Date;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.brp.dataaccess.AbstractRepositoryTestCase;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdresregelAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandseRegioAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Nee;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdresAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebied;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingVerblijf;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingVerblijfAttribuut;
import nl.bzk.brp.model.basis.ALaagAfleidingInterceptor;
import nl.bzk.brp.model.basis.MaterieleHistorieImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonAdresHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAdresModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeboorteModel;
import nl.bzk.brp.model.operationeel.kern.PersoonAdresStandaardGroepModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import org.hibernate.EmptyInterceptor;
import org.hibernate.internal.SessionImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Test klasse die de A laag afleiding test. De hibernate interceptor wordt aangezet en vervolgens weer uitgezet.
 * Andere tests die niks met de A laag afleiding van doen hebben horen hier niet!
 * Omdat we de onderliggende hibernate Session manipuleren is deze test class gemarkeerd als Spring context vervuilend.
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class PersoonHisVolledigRepositoryALaagAfleidingIntegratieTest extends AbstractRepositoryTestCase {

    private static final String INTERCEPTOR = "interceptor";
    private static final String TEST = "TEST";
    private static final String TEST_5 = "TEST5";

    @PersistenceContext(unitName = "nl.bzk.brp.lezenschrijven")
    private EntityManager em;

    @Inject
    private PersoonHisVolledigRepository persoonHisVolledigRepository;

    @Before
    public void initialiseerAlaagAfleidingInHibernate() {
        //Zet de A laag afleiding aan:
        final SessionImpl sessionImpl = (SessionImpl) em.getDelegate();
        ReflectionTestUtils.setField(sessionImpl, INTERCEPTOR, new ALaagAfleidingInterceptor());
    }

    @After
    public void deactiveerAlaagAfleidingInHibernate() {
        //Zet de A laag afleiding uit:
        final SessionImpl sessionImpl = (SessionImpl) em.getDelegate();
        ReflectionTestUtils.setField(sessionImpl, INTERCEPTOR, EmptyInterceptor.INSTANCE);
    }

    @Test
    public void testALaagAfleiding() {
        // Persoon uit de A-Laag ophalen en controleren dat buitenlands adres 5 null is en de geboortedatum bekend is.
        PersoonModel aLaagPersoon = em.find(PersoonModel.class, 1);
        assertNull(aLaagPersoon.getAdressen().iterator().next().getStandaard().getBuitenlandsAdresRegel5());
        assertEquals(Integer.valueOf(18890426), aLaagPersoon.getGeboorte().getDatumGeboorte().getWaarde());

        // Persoon His Volledig ophalen uit de Database
        final PersoonHisVolledig persoon = persoonHisVolledigRepository.leesGenormalizeerdModel(1);

        // Geboorte groep aanpassen
        final HisPersoonGeboorteModel geboorte = new HisPersoonGeboorteModel(
                persoon.getPersoonGeboorteHistorie().getActueleRecord());
        ReflectionTestUtils.setField(geboorte, "buitenlandseRegioGeboorte", new BuitenlandseRegioAttribuut(TEST));
        ReflectionTestUtils.setField(geboorte, "datumGeboorte", new DatumEvtDeelsOnbekendAttribuut(20130202));
        geboorte.getFormeleHistorie().setDatumTijdRegistratie(new DatumTijdAttribuut(new Date()));
        geboorte.setVerantwoordingInhoud(em.find(ActieModel.class, 101L));
        persoon.getPersoonGeboorteHistorie().voegToe(geboorte);

        final RedenWijzigingVerblijf redenWijzigingVerblijf = em.find(RedenWijzigingVerblijf.class, (short) 1);

        // Nieuw Adres toevoegen
        final PersoonAdresHisVolledig adresHisVolledig = persoon.getAdressen().iterator().next();
        final PersoonAdresStandaardGroepModel nieuwAdres = new PersoonAdresStandaardGroepModel(
                new FunctieAdresAttribuut(FunctieAdres.WOONADRES),
                new RedenWijzigingVerblijfAttribuut(redenWijzigingVerblijf),
                null,
                new DatumEvtDeelsOnbekendAttribuut(20130409), null, null, null, null, null, null, null, null, null, null, null,
                null, null,
                null, null, null, null, new AdresregelAttribuut(TEST_5),
                new AdresregelAttribuut("Test6"), new LandGebiedAttribuut(em.find(LandGebied.class, 2)),
                new NeeAttribuut(Nee.N));
        final MaterieleHistorieImpl materieleHistorie = new MaterieleHistorieImpl();
        materieleHistorie.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20130410));
        materieleHistorie.setDatumTijdRegistratie(new DatumTijdAttribuut(new Date()));
        final ActieModel actieInhoud = em.find(ActieModel.class, 101L);
        final HisPersoonAdresModel hisPersoonAdresModel = new HisPersoonAdresModel(adresHisVolledig, nieuwAdres,
                materieleHistorie,
                actieInhoud);
        hisPersoonAdresModel.setVerantwoordingInhoud(actieInhoud);
        adresHisVolledig.getPersoonAdresHistorie().voegToe(hisPersoonAdresModel);

        // Persoon volledig wegschrijven (en flushen) waardoor A-Laag automatisch afgeleid zou moeten worden.
        persoonHisVolledigRepository.schrijfGenormalizeerdModel((PersoonHisVolledigImpl) persoon);
        em.flush();

        // A-Laag persoon detachen en dan ophalen (indien we niet eerst detachen, krijg je gecachte en dus niet
        // aangepaste versie).
        em.detach(aLaagPersoon);
        aLaagPersoon = em.find(PersoonModel.class, 1);

        // Test of nieuwe adres en geboorte informatie in de A-Laag correct is afgeleid.
        assertNotNull(aLaagPersoon.getGeboorte().getBuitenlandseRegioGeboorte());
        assertEquals(TEST, aLaagPersoon.getGeboorte().getBuitenlandseRegioGeboorte().getWaarde());
        assertNotNull(aLaagPersoon.getGeboorte().getDatumGeboorte());
        assertEquals(Integer.valueOf(20130202), aLaagPersoon.getGeboorte().getDatumGeboorte().getWaarde());
        assertNotNull(aLaagPersoon.getAdressen().iterator().next().getStandaard().getBuitenlandsAdresRegel5());
        assertEquals(TEST_5,
                aLaagPersoon.getAdressen().iterator().next().getStandaard().getBuitenlandsAdresRegel5().getWaarde());
    }
}
