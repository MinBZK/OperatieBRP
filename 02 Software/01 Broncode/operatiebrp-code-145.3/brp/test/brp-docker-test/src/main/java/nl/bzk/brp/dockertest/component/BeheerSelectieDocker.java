/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Component voor Beheer Selectie.
 */
@DockerInfo(
        image = "brp/beheer-selectie",
        logischeNaam = DockerNaam.BEHEER_SELECTIE,
        afhankelijkheden = {DockerNaam.BRPDB},
        internePoorten = {Poorten.APPSERVER_PORT}
)
final class BeheerSelectieDocker extends AbstractDocker {

    @Override
    protected Integer geefPoortVoorkeur(int internepoort) {
        if (internepoort == Poorten.APPSERVER_PORT) {
            return Poorten.APPSERVER_PORT + 1;
        }
        return super.geefPoortVoorkeur(internepoort);
    }

    @Override
    public boolean isFunctioneelBeschikbaar() {
        try {
            final List<String> commandList = getOmgeving().getDockerCommandList("logs", getDockerContainerId());
            final ProcessOutputReader reader = ProcessHelper.DEFAULT.startProces(commandList);
            final String log = reader.geefOutput();
            return log != null && Pattern.compile("Started BeheerWebApplication").matcher(log).find();
        } catch (AbnormalProcessTerminationException e) {
            //let op: TestclientExceptie wordt alleen gegooid als de log file (tijdelijk) nog niet
            //bestaat. In dat geval moet nog even gewacht worden totdat de log verschijnt.
            return false;
        }
    }
}
