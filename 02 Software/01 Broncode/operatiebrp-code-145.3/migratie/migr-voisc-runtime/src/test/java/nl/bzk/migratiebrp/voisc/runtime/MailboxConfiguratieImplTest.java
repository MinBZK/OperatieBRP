/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.sync.register.Partij;
import nl.bzk.migratiebrp.bericht.model.sync.register.PartijRegister;
import nl.bzk.migratiebrp.bericht.model.sync.register.PartijRegisterImpl;
import nl.bzk.migratiebrp.register.client.RegisterService;
import nl.bzk.migratiebrp.voisc.database.entities.Mailbox;
import nl.bzk.migratiebrp.voisc.database.repository.MailboxRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MailboxConfiguratieImplTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Mock
    private RegisterService<PartijRegister> partijService;
    @Mock
    private MailboxRepository mailboxRepository;

    @InjectMocks
    private MailboxConfiguratieImpl subject;

    private final Mailbox mailboxCentraal0001 = maakMailbox("199902", "3000200");
    private final Mailbox mailboxCentraal0002 = maakMailbox("199902", "3000210");
    private final Mailbox mailboxPartij0001 = maakMailbox("051801", "0518010");
    private final Mailbox mailboxPartij0002 = maakMailbox("051901", "0519010");

    private final Partij centraleVoorziening = maakPartij("199902", null, new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 21));
    private final Partij partij0001 = maakPartij("051801", "0518", new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 21));
    private final Partij partij0002 = maakPartij("051901", "0519", null);

    private Mailbox maakMailbox( final String partijCode, final String mailboxnr) {
        final Mailbox mailbox = new Mailbox();
        mailbox.setPartijcode(partijCode);
        mailbox.setMailboxnr(mailboxnr);

        return mailbox;
    }

    private Partij maakPartij(final String partijCode, final String gemeenteCode, final Date datumOvergangNaarBrp) {
        return new Partij(partijCode, gemeenteCode, datumOvergangNaarBrp, Collections.emptyList());
    }

    @Before
    public void setup() {
        final List<Mailbox> alleMailboxen = Arrays.asList(mailboxCentraal0001, mailboxCentraal0002, mailboxPartij0001, mailboxPartij0002);
        Mockito.when(mailboxRepository.getAllMailboxen()).thenReturn(alleMailboxen);
        Mockito.when(mailboxRepository.getMailboxByNummer("0518010")).thenReturn(mailboxPartij0001);
        Mockito.when(mailboxRepository.getMailboxByNummer("0519010")).thenReturn(mailboxPartij0002);
        Mockito.when(mailboxRepository.getMailboxByNummer("3000200")).thenReturn(mailboxCentraal0001);
        Mockito.when(mailboxRepository.getMailboxByNummer("3000210")).thenReturn(mailboxCentraal0002);

        final List<Partij> partijen = Arrays.asList(centraleVoorziening, partij0001, partij0002);
        final PartijRegister partijRegister = new PartijRegisterImpl(partijen);
        Mockito.when(partijService.geefRegister()).thenReturn(partijRegister);
    }

    /**
     * Zet de waarde van te bedienen mailboxen.
     * @param config te bedienen mailboxen
     * @throws NoSuchFieldException the no such field exception
     * @throws SecurityException the security exception
     * @throws IllegalArgumentException the illegal argument exception
     * @throws IllegalAccessException the illegal access exception
     */
    private void setTeBedienenMailboxen(final String config) throws NoSuchFieldException, SecurityException, IllegalArgumentException,
            IllegalAccessException {
        final Field teBedienenMailboxenField = MailboxConfiguratieImpl.class.getDeclaredField("teBedienenMailboxen");
        teBedienenMailboxenField.setAccessible(true);
        teBedienenMailboxenField.set(subject, config);
    }

    @Test
    public void testDefaultConfiguratieNull() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // Config
        setTeBedienenMailboxen(null);

        // Execute
        final Set<Mailbox> result = subject.bepaalMailboxen();

        // Verify
        Assert.assertEquals(3, result.size());
        Assert.assertTrue(result.contains(mailboxCentraal0001));
        Assert.assertTrue(result.contains(mailboxCentraal0002));
        Assert.assertTrue(result.contains(mailboxPartij0001));
    }

    @Test
    public void testDefaultConfiguratieEmpty() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // Config
        setTeBedienenMailboxen("   ");

        // Execute
        final Set<Mailbox> result = subject.bepaalMailboxen();

        // Verify
        Assert.assertEquals(3, result.size());
        Assert.assertTrue(result.contains(mailboxCentraal0001));
        Assert.assertTrue(result.contains(mailboxCentraal0002));
        Assert.assertTrue(result.contains(mailboxPartij0001));
    }

    @Test
    public void testAlleenGeconfigureerde() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // Config
        setTeBedienenMailboxen(" 3000200 , 3000210  ");

        // Execute
        final Set<Mailbox> result = subject.bepaalMailboxen();

        // Verify
        Assert.assertEquals(2, result.size());
        Assert.assertTrue(result.contains(mailboxCentraal0001));
        Assert.assertTrue(result.contains(mailboxCentraal0002));
    }

    @Test
    public void testAllesBehalveGeconfigureerde() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // Config
        setTeBedienenMailboxen(" -3000200 , -3000210  ");

        // Execute
        final Set<Mailbox> result = subject.bepaalMailboxen();

        // Verify
        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.contains(mailboxPartij0001));

    }

}
