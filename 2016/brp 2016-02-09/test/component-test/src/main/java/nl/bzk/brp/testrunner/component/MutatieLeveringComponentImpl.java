/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.component;

import java.util.List;
import java.util.Map;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.testrunner.omgeving.Link;
import nl.bzk.brp.testrunner.omgeving.LogischeNaam;
import nl.bzk.brp.testrunner.omgeving.Omgeving;
import nl.bzk.brp.testrunner.component.util.VersieUrlChecker;

@LogischeNaam(ComponentNamen.MUTATIELEVERING)
@Link(value = { DatabaseComponentImpl.class })
final class MutatieLeveringComponentImpl extends AbstractDockerComponentMetCache implements MutatieLevering {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    public MutatieLeveringComponentImpl(final Omgeving omgeving) {
        super(omgeving, "levering");
    }

    @Override
    protected Map<String, String> geefInterneLinkOpLogischeLinkMap() {
        final Map<String, String> map = super.geefInterneLinkOpLogischeLinkMap();
        map.put("brpdb", ComponentNamen.BRP_DB);
        map.put("archiveringdb", ComponentNamen.BRP_DB);
        map.put("lockingdb", ComponentNamen.BRP_DB);
        map.put("routeringcentrale", ComponentNamen.ROUTERINGCENTRALE);
        return map;
    }

    @Override
    protected Map<String, String> geefOmgevingsVariabelen() {
        final Map<String, String> map = super.geefOmgevingsVariabelen();
        map.put("JAVA_OPTS", "-Xmx1g");
        return map;
    }

    @Override
    public boolean isFunctioneelBeschikbaar() {
        return super.isFunctioneelBeschikbaar() && VersieUrlChecker.check(this, getLogischeNaam());
    }

    @Override
    protected List<Integer> geefInternePoorten() {
        final List<Integer> internePoorten = super.geefInternePoorten();
        internePoorten.add(Poorten.WEB_POORT_8080);
        return internePoorten;
    }

    @Override
    protected DockerImage geefDockerImage() {
        return new DockerImage("brp/mutatielevering");
    }

}
