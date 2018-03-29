/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dockertest.component;

import com.google.common.collect.Maps;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;

/**
 * Container voor het exposen van de "/selectie" volume.
 * Dit dienst puur voor het kopieren van selectiebestanden tbv van de test-client.
 */
@DockerInfo(
        image = "alpine",
        logischeNaam = DockerNaam.SELECTIE_VOLUME,
        dynamicVolumes = {"/selectie", "/selectiebestand"}
)
public class SelectieVolumeDocker extends AbstractDocker {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    protected Map<String, String> getEnvironment() {
        return Maps.newHashMap();
    }

    @Override
    protected List<String> maakDockerStartCommando() {
        final List<String> commandList = super.maakDockerStartCommando();
        commandList.addAll(Arrays.asList("tail", "-f", "/dev/null"));
        return commandList;
    }

    @Override
    protected List<String> maakDockerExtraStartCommando() {
        //voor nu maar even alles voor iedereen (chmod 777). voor selectiebestand is default lezen voldoende
        return getOmgeving().getDockerCommandList(
        "exec", "-d", getDockerInfo().logischeNaam() + "-" + getOmgeving().getNaam(), "chmod", "-R", "777", "/selectie");
    }

    @Override
    protected String geefVolledigeImageNaam() {
        return getDockerInfo().image();
    }

    @Override
    protected void beforeStop() {
        final String schrijfSelectiesNaarTemp = System.getProperty("schrijfSelectiesNaarTemp");
        if (schrijfSelectiesNaarTemp != null && !Boolean.FALSE.equals(Boolean.parseBoolean(schrijfSelectiesNaarTemp))) {
            try {
                final Path tempDir = Files.createTempDirectory(null);
                ProcessHelper.DEFAULT.startProces(getOmgeving().getDockerCommandList("cp", getDockerContainerId() + ":"
                        + getDockerInfo().dynamicVolumes()[0], tempDir.toString()));
            } catch (final AbnormalProcessTerminationException | IOException e) {
                //deze fout negeren we voor nu. Op windows met laatste docker versie ontvangen we een process fout terwijl
                // de kopieer actie goed gaat
                LOGGER.info("fout bij kopieren selectie bestanden", e);
            }
        }
    }
}
