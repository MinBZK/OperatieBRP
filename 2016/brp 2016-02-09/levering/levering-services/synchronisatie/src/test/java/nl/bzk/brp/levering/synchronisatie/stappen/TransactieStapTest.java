/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.synchronisatie.stappen;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Collections;
import nl.bzk.brp.levering.synchronisatie.dto.synchronisatie.SynchronisatieBerichtContext;
import nl.bzk.brp.levering.synchronisatie.dto.synchronisatie.SynchronisatieResultaat;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReferentienummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.CommunicatieIdMap;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.synchronisatie.GeefSynchronisatiePersoonBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionStatus;

@RunWith(MockitoJUnitRunner.class)
public class TransactieStapTest {

    @InjectMocks
    private TransactieStap transactieStap = new TransactieStap();

    @Mock
    private PlatformTransactionManager transactionManager;

    @Test
    public final void testTransactieWordtGecreeerdMetJuisteParameters() {
        final GeefSynchronisatiePersoonBericht onderwerp = maakBericht();
        final SynchronisatieBerichtContext context = new SynchronisatieBerichtContext(new BerichtenIds(1L, 2L),
            StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(), "ref-00001", new CommunicatieIdMap());
        final SynchronisatieResultaat resultaat = new SynchronisatieResultaat(Collections.<Melding>emptyList());

        final ArgumentCaptor<DefaultTransactionDefinition> argument =
            ArgumentCaptor.forClass(DefaultTransactionDefinition.class);

        transactieStap
            .voerStapUit(onderwerp, context, resultaat);

        verify(transactionManager).getTransaction(argument.capture());

        assertFalse(argument.getValue().isReadOnly());
        assertEquals(TransactionDefinition.PROPAGATION_REQUIRES_NEW,
            argument.getValue().getPropagationBehavior());
        assertEquals("Transactie tbv verwerking van bericht: 0000-00000000-0000-0000-0000-000000000000",
            argument.getValue().getName());
    }

    @Test
    public final void testGeenTransactionStatus() {
        final GeefSynchronisatiePersoonBericht onderwerp = maakBericht();
        final SynchronisatieBerichtContext context = new SynchronisatieBerichtContext(new BerichtenIds(1L, 2L),
            StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(), "ref-00002", new CommunicatieIdMap());
        final SynchronisatieResultaat resultaat = new SynchronisatieResultaat(Collections.<Melding>emptyList());

        context.setTransactionStatus(null);

        transactieStap.voerNabewerkingStapUit(onderwerp, context, resultaat);

        verify(transactionManager).rollback(Matchers.any(TransactionStatus.class));
    }

    @Test
    public final void testGeenSuccesvolResultaat() {
        final GeefSynchronisatiePersoonBericht onderwerp = maakBericht();
        final SynchronisatieBerichtContext context = new SynchronisatieBerichtContext(new BerichtenIds(1L, 2L),
            StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(), "ref-00003", new CommunicatieIdMap());
        final SynchronisatieResultaat resultaat = maakResultaatMetFoutMeldingen();

        context.setTransactionStatus(getTransactionStatusCompleted());

        transactieStap.voerNabewerkingStapUit(onderwerp, context, resultaat);

        verify(transactionManager).rollback(Matchers.any(TransactionStatus.class));
    }

    @Test
    public final void testFoutResultaatVeroorzaaktRollbackInNaverwerking() {
        final GeefSynchronisatiePersoonBericht onderwerp = maakBericht();
        final SynchronisatieBerichtContext context = new SynchronisatieBerichtContext(new BerichtenIds(1L, 2L),
            StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(), "ref-00004", new CommunicatieIdMap());
        final SynchronisatieResultaat resultaat = maakResultaatMetFoutMeldingen();

        context.setTransactionStatus(getTransactionStatusCompleted());

        transactieStap.voerNabewerkingStapUit(onderwerp, context, resultaat);

        verify(transactionManager).rollback(Matchers.any(TransactionStatus.class));
    }

    @Test
    public final void testRollbackStatusVeroorzaaktRollbackInNaverwerking() {
        final GeefSynchronisatiePersoonBericht onderwerp = maakBericht();
        final SynchronisatieBerichtContext context = new SynchronisatieBerichtContext(new BerichtenIds(1L, 2L),
            StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(), "ref-00005", new CommunicatieIdMap());
        final SynchronisatieResultaat resultaat = new SynchronisatieResultaat(Collections.<Melding>emptyList());

        context.setTransactionStatus(getTransactionStatusCompleted());

        //Markeer rollback
        context.getTransactionStatus().setRollbackOnly();

        transactieStap.voerNabewerkingStapUit(onderwerp, context, resultaat);

        verify(transactionManager).rollback(Matchers.any(TransactionStatus.class));
    }

    @Test
    public final void testNormaleFlow() {
        final GeefSynchronisatiePersoonBericht onderwerp = maakBericht();
        final SynchronisatieBerichtContext context = new SynchronisatieBerichtContext(new BerichtenIds(1L, 2L),
            StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA.getWaarde(), "ref-00006", new CommunicatieIdMap());
        final SynchronisatieResultaat resultaat = new SynchronisatieResultaat(Collections.<Melding>emptyList());

        context.setTransactionStatus(getTransactionStatusCompleted());

        transactieStap.voerNabewerkingStapUit(onderwerp, context, resultaat);

        verify(transactionManager).commit(Matchers.any(TransactionStatus.class));
    }

    private DefaultTransactionStatus getTransactionStatusCompleted() {
        final DefaultTransactionStatus status = new DefaultTransactionStatus(null, true, true, true, true, null);
        status.setCompleted();
        return status;
    }

    private GeefSynchronisatiePersoonBericht maakBericht() {
        final GeefSynchronisatiePersoonBericht bericht = new GeefSynchronisatiePersoonBericht();

        final BerichtStuurgegevensGroepBericht stuurgegevensGroepBericht = new BerichtStuurgegevensGroepBericht();
        stuurgegevensGroepBericht.setReferentienummer(
            new ReferentienummerAttribuut("0000-00000000-0000-0000-0000-000000000000"));
        bericht.setStuurgegevens(stuurgegevensGroepBericht);

        return bericht;
    }

    private SynchronisatieResultaat maakResultaatMetFoutMeldingen() {
        final SynchronisatieResultaat resultaat = new SynchronisatieResultaat(new ArrayList<Melding>(1));
        resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT, Regel.ALG0001, "Deze fout veroorzaakt een rollback"));
        return resultaat;
    }

}
