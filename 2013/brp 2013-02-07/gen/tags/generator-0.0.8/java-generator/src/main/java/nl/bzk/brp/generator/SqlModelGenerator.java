/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class SqlModelGenerator implements ModelGenerator {

    private static final Logger logger = LoggerFactory.getLogger(SqlModelGenerator.class);

    @Override
    public List<GenerationReport> genereer(String targetDirectory, String existingCustomizedSourceDirectory,
            GenerationTargetModel targetModel, List<String> generationElements, Properties options)
                    throws MissingArgumentException
                    {
        List<GenerationReport> reports = new ArrayList<GenerationReport>();

        File targetDir = new File(targetDirectory);
        if (!targetDir.isDirectory()) {
            try {
                FileUtils.forceMkdir(targetDir);
                logger.info("Generating sql files to " + targetDir.getAbsolutePath());
            } catch (IOException e) {
                logger.error("Kon directory structure voor sql files niet genereren", e);
            }
        }

        return reports;

                    }

}
