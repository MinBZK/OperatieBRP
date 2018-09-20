/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testrunner.component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.testrunner.omgeving.LogischeNaam;
import nl.bzk.brp.testrunner.omgeving.Omgeving;
import nl.bzk.brp.testrunner.component.util.VersieUrlChecker;

/**
 */
@LogischeNaam(ComponentNamen.AFNEMER)
class AfnemerVoorbeeldImpl extends AbstractDockerComponent implements AfnemerVoorbeeld {

    protected AfnemerVoorbeeldImpl(final Omgeving omgeving) {
        super(omgeving);
    }

    @Override
    protected Map<String, String> geefInterneLinkOpLogischeLinkMap() {
        final Map<String, String> map = new HashMap<>();
        map.put("brpdb", ComponentNamen.BRP_DB);
        return map;
    }

    @Override
    public boolean isFunctioneelBeschikbaar() {
        return super.isFunctioneelBeschikbaar() && VersieUrlChecker.check(this, "brp-levering-services-afnemer-voorbeeld");
    }
    @Override
    protected DockerImage geefDockerImage() {
        return new DockerImage("brp/afnemervoorbeeld");
    }

    @Override
    protected List<Integer> geefInternePoorten() {
        final List<Integer> internePoorten = super.geefInternePoorten();
        internePoorten.add(Poorten.WEB_POORT_8080);
        return internePoorten;
    }
}
