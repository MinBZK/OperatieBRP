/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import nl.bzk.migratiebrp.bericht.model.sync.register.Gemeente;
import nl.bzk.migratiebrp.bericht.model.sync.register.GemeenteRegister;
import nl.bzk.migratiebrp.bericht.model.sync.register.GemeenteRegisterImpl;
import nl.bzk.migratiebrp.register.client.RegisterService;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
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
    private RegisterService<GemeenteRegister> gemeenteService;
    @Mock
    private MailboxRepository mailboxRepository;

    @InjectMocks
    private MailboxConfiguratieImpl subject;

    private final Mailbox mailboxCentraal0001 = maakMailbox(Mailbox.INSTANTIETYPE_CENTRALE_VOORZIENING, 3000200, "3000200");
    private final Mailbox mailboxCentraal0002 = maakMailbox(Mailbox.INSTANTIETYPE_CENTRALE_VOORZIENING, 3000210, "3000210");
    private final Mailbox mailboxGemeente0001 = maakMailbox(Mailbox.INSTANTIETYPE_GEMEENTE, 518, "0518010");
    private final Mailbox mailboxGemeente0002 = maakMailbox(Mailbox.INSTANTIETYPE_GEMEENTE, 519, "0519010");

    private final Gemeente gemeente0001 = maakGemeente("0518", new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 21));
    private final Gemeente gemeente0002 = maakGemeente("0519", null);

    private Mailbox maakMailbox(final String instantieType, final int instantieCode, final String mailboxnr) {
        final Mailbox mailbox = new Mailbox();
        mailbox.setInstantietype(instantieType);
        mailbox.setInstantiecode(instantieCode);
        mailbox.setMailboxnr(mailboxnr);

        return mailbox;
    }

    private Gemeente maakGemeente(final String gemeenteCode, final Date datumOvergangNaarBrp) {
        return new Gemeente(gemeenteCode, null, datumOvergangNaarBrp);
    }

    @Before
    public void setup() {
        final List<Mailbox> centraleMailboxes = Arrays.asList(mailboxCentraal0001, mailboxCentraal0002);
        Mockito.when(mailboxRepository.getCentraleMailboxes()).thenReturn(centraleMailboxes);
        Mockito.when(mailboxRepository.getMailboxByNummer("3000200")).thenReturn(mailboxCentraal0001);
        Mockito.when(mailboxRepository.getMailboxByNummer("3000210")).thenReturn(mailboxCentraal0002);

        final List<Mailbox> gemeenteMailboxes = Arrays.asList(mailboxGemeente0001, mailboxGemeente0002);
        Mockito.when(mailboxRepository.getGemeenteMailboxes()).thenReturn(gemeenteMailboxes);
        Mockito.when(mailboxRepository.getMailboxByNummer("0518010")).thenReturn(mailboxGemeente0001);
        Mockito.when(mailboxRepository.getMailboxByNummer("0519010")).thenReturn(mailboxGemeente0002);

        final List<Gemeente> gemeenten = Arrays.asList(gemeente0001, gemeente0002);
        final GemeenteRegister gemeenteRegister = new GemeenteRegisterImpl(gemeenten);
        Mockito.when(gemeenteService.geefRegister()).thenReturn(gemeenteRegister);
    }

    /**
     * Zet de waarde van te bedienen mailboxen.
     *
     * @param config
     *            te bedienen mailboxen
     * @throws NoSuchFieldException
     *             the no such field exception
     * @throws SecurityException
     *             the security exception
     * @throws IllegalArgumentException
     *             the illegal argument exception
     * @throws IllegalAccessException
     *             the illegal access exception
     */
    private void setTeBedienenMailboxen(final String config) throws NoSuchFieldException, SecurityException, IllegalArgumentException,
        IllegalAccessException
    {
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
        Assert.assertTrue(result.contains(mailboxGemeente0001));
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
        Assert.assertTrue(result.contains(mailboxGemeente0001));
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
        for (final Mailbox mailbox : result) {
            LOG.error("Mailbox: " + mailbox.getMailboxnr());
        }
        Assert.assertEquals(1, result.size());
        Assert.assertTrue(result.contains(mailboxGemeente0001));

    }

}
