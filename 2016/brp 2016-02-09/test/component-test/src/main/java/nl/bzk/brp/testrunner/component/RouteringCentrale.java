/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.component;

import java.util.List;
import javax.jms.JMSException;
import javax.jms.Session;
import nl.bzk.brp.testrunner.omgeving.Component;
import nl.bzk.brp.testrunner.component.util.Afnemerbericht;

public interface RouteringCentrale extends Component {

    void verstuurHandeling(long administratieveHandeling);

    List<Afnemerbericht> geefAfnemerberichten(String queue) throws JMSException;

    <T> T voerUit(SessionVerzoek<T> t) throws JMSException;

    interface SessionVerzoek<T> {

        T voerUitMetSessie(Session sessie);
    }
}
