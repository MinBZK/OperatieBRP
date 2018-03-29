/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime;

import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VoiscJMXImplTest {

    @Mock
    private VoiscService voiscService;

    @InjectMocks
    private VoiscJMXImpl subject;

    @Test
    public void berichtenVerzendenNaarIsc() {
        subject.berichtenVerzendenNaarIsc();
        Mockito.verify(voiscService, Mockito.timeout(1000)).berichtenVerzendenNaarIsc();
    }

    @Test
    public void berichtenVerzendenNaarIscException() {
        Mockito.doThrow(new IllegalArgumentException()).when(voiscService).berichtenVerzendenNaarIsc();
        subject.berichtenVerzendenNaarIsc();
    }

    @Test
    public void berichtenVerzendenNaarEnOntvangenVanMailbox() {
        subject.berichtenVerzendenNaarEnOntvangenVanMailbox();
        Mockito.verify(voiscService, Mockito.timeout(1000)).berichtenVerzendenNaarEnOntvangenVanMailbox();
    }

    @Test
    public void berichtenVerzendenNaarEnOntvangenVanMailboxException() {
        Mockito.doThrow(IllegalArgumentException.class).when(voiscService).berichtenVerzendenNaarEnOntvangenVanMailbox();
        subject.berichtenVerzendenNaarEnOntvangenVanMailbox();
    }

    @Test
    public void opschonenVoiscBerichten() {
        subject.opschonenVoiscBerichten();
        Mockito.verify(voiscService, Mockito.timeout(1000)).opschonenVoiscBerichten(Matchers.any(Date.class));
    }

    @Test
    public void opschonenVoiscBerichtenException() {
        Mockito.when(voiscService.opschonenVoiscBerichten(Matchers.any(Date.class))).thenThrow(new IllegalArgumentException());
        subject.opschonenVoiscBerichten();
    }

    @Test
    public void herstellenVoiscBerichten() {
        subject.herstellenVoiscBerichten();
        Mockito.verify(voiscService, Mockito.timeout(1000)).herstellenVoiscBerichten(Matchers.any(Date.class));
    }

    @Test
    public void herstellenVoiscBerichtenException() {
        Mockito.when(voiscService.herstellenVoiscBerichten(Matchers.any(Date.class))).thenThrow(new IllegalArgumentException());
        subject.herstellenVoiscBerichten();
    }
}
