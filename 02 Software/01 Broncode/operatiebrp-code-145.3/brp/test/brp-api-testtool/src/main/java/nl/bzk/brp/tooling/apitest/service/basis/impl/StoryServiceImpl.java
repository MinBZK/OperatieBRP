/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.basis.impl;

import com.google.common.collect.Lists;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.test.common.TestclientExceptie;
import nl.bzk.brp.tooling.apitest.service.basis.StoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

/**
 * StoryServiceImpl.
 */
final class StoryServiceImpl implements StoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private String story;
    private String scenario;
    private String step;
    private File outputDir;

    @Override
    public void setStory(final String story) {
        this.story = story;
    }

    @Override
    public void setScenario(final String scenario) {
        this.scenario = scenario;
        outputDir = null;
    }

    @Override
    public void setStep(final String step) {
        this.step = step;
    }

    @Override
    public String getStory() {
        return story;
    }

    @Override
    public String getScenario() {
        return scenario;
    }

    @Override
    public String getStep() {
        return step;
    }

    @Override
    public File getOutputDir() {
        if (outputDir != null) {
            return outputDir;
        }
        outputDir = bepaalOutputDir();
        return  outputDir;
    }

    @Override
    public Resource resolvePath(final String path) {
        final Resource resource;
        if (path.startsWith("/")) {
            LOGGER.debug("resolve van classpath: " + path);
            resource = new ClassPathResource(path);
        } else {
            final File file = new File(new File(story).getParentFile(), path);
            LOGGER.debug("resolve relatief van story: " + path);
            resource = new ClassPathResource(file.getPath());
        }
        Assert.isTrue(resource.exists(), "Bestand niet gevonden: " + path);
        return resource;
    }

    private File bepaalOutputDir() {
        final File target = new File("target");
        final File actuals = new File(target, "actuals");

        final File storyFile;
        try {
             storyFile = new ClassPathResource(story).getFile();
        } catch (IOException e) {
            throw new TestclientExceptie(e);
        }
        final List<String> parentFiles = Lists.newLinkedList();
        File dir = storyFile.getParentFile();
        while (dir != null) {
            if ("testcases".equals(dir.getName())) {
                break;
            }
            parentFiles.add(dir.getName());
            dir = dir.getParentFile();
        }
        parentFiles.sort(Collections.reverseOrder());

        //add story naam als dir
        parentFiles.add(getIdentifier(storyFile.getName().replace(".story", "")));

        //add scenario als dir
        parentFiles.add(getIdentifier(StringUtils.substring(scenario, 0, 20)));

        final File tempOutputDir = new File(actuals, StringUtils.join(parentFiles, File.separatorChar));
        tempOutputDir.mkdirs();
        return tempOutputDir;
    }

    /**
     * Geeft de identifier van dit resultaat.
     */
    private String getIdentifier(final String input) {
        return input.replaceAll(" ", "_").replaceAll("/", "_");
    }
}
