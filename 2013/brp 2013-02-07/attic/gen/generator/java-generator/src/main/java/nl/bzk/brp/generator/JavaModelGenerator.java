/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 *
 */
package nl.bzk.brp.generator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import nl.bzk.brp.generator.java.AbstractJavaGenerator;
import nl.bzk.brp.generator.java.JavaAttribuutTypeBasisGenerator;
import nl.bzk.brp.generator.java.JavaAttribuutTypeGenerator;
import nl.bzk.brp.generator.java.JavaEnumGenerator;
import nl.bzk.brp.generator.java.JavaGroepActueelBasisGenerator;
import nl.bzk.brp.generator.java.JavaGroepLogischBasisGenerator;
import nl.bzk.brp.generator.java.JavaGroepLogischGenerator;
import nl.bzk.brp.generator.java.JavaGroepOperationeelActueelGenerator;
import nl.bzk.brp.generator.java.JavaGroepOperationeelGenerator;
import nl.bzk.brp.generator.java.JavaGroepOperationeelHistorischBasisGenerator;
import nl.bzk.brp.generator.java.JavaGroepOperationeelHistorischGenerator;
import nl.bzk.brp.generator.java.JavaObjectTypeLogischBasisGenerator;
import nl.bzk.brp.generator.java.JavaObjectTypeLogischGenerator;
import nl.bzk.brp.generator.java.JavaObjectTypeStatischGenerator;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 *
 */
@Service
@Transactional
public class JavaModelGenerator implements ModelGenerator {

    private static final Logger                           logger = LoggerFactory.getLogger(JavaModelGenerator.class);

    @Autowired
    private JavaAttribuutTypeGenerator                    attributenGenerator;

    @Autowired
    private JavaAttribuutTypeBasisGenerator               attributenBasisGenerator;

    @Autowired
    private JavaEnumGenerator                             enumGenerator;

    @Autowired
    private JavaGroepActueelBasisGenerator                groepActueelBasisGenerator;

    @Autowired
    private JavaGroepLogischBasisGenerator                groepLogischBasisGenerator;

    @Autowired
    private JavaGroepLogischGenerator                     groepLogischGenerator;

    @Autowired
    private JavaGroepOperationeelActueelGenerator         groepOperationeelActueelGenerator;

    @Autowired
    private JavaGroepOperationeelGenerator                groepOperationeelGenerator;

    @Autowired
    private JavaGroepOperationeelHistorischBasisGenerator groepOperationeelHistorischBasisGenerator;

    @Autowired
    private JavaGroepOperationeelHistorischGenerator      groepOperationeelHistorischGenerator;

    @Autowired
    private JavaObjectTypeLogischGenerator                objectTypeLogischGenerator;

    @Autowired
    private JavaObjectTypeLogischBasisGenerator           objectTypeLogischBasisGenerator;

    @Autowired
    private JavaObjectTypeStatischGenerator               objectTypeStatischGenerator;

    /**
     * (non-Javadoc)
     *
     * @return
     *
     * @see nl.bzk.brp.generator.ModelGenerator#genereer(java.lang.String, java.lang.String)
     */
    @Override
    public List<GenerationReport> genereer(String targetDirectory, String existingCustomizedSourceDirectory,
            GenerationTargetModel targetModel, List<String> generationElements, Properties options)
                    throws MissingArgumentException
                    {
        List<GenerationReport> reports = new ArrayList<GenerationReport>();

        File targetDir = new File(targetDirectory);
        if (targetDir.isDirectory()) {
            try {
                String path =
                        FilenameUtils.concat(targetDir.getAbsolutePath(),
                                GenerationPackageNames.BRP_MODEL_BASEPACKAGE.getPackageAsPath());
                logger.info("Generating sources to " + path);
                FileUtils.forceMkdir(new File(path));
                if (generationElements != null) {
                    if (generationElements.contains(GenerationElements.ENUMERATIONS.name().toLowerCase())) {
                        genereer(enumGenerator, reports, targetDir);
                    }
                    if (generationElements.contains(GenerationElements.ATTRIBUTETYPES.name().toLowerCase())) {
                        genereer(attributenGenerator, reports, targetDir);
                        genereer(attributenBasisGenerator, reports, targetDir);
                    }
                    if (generationElements.contains(GenerationElements.OBJECTTYPES.name().toLowerCase())) {
                        genereer(objectTypeStatischGenerator, reports, targetDir);
                        genereer(objectTypeLogischGenerator, reports, targetDir);
                        genereer(objectTypeLogischBasisGenerator, reports, targetDir);
                    }

                    if (generationElements.contains(GenerationElements.GROUPS.name().toLowerCase())) {
                        genereer(groepActueelBasisGenerator, reports, targetDir);
                        genereer(groepLogischBasisGenerator, reports, targetDir);
                        genereer(groepLogischGenerator, reports, targetDir);
                        genereer(groepOperationeelActueelGenerator, reports, targetDir);
                        genereer(groepOperationeelGenerator, reports, targetDir);
                        genereer(groepOperationeelHistorischBasisGenerator, reports, targetDir);
                        genereer(groepOperationeelHistorischGenerator, reports, targetDir);
                    }
                }
            } catch (IOException e) {
                logger.error("Kon directory structure voor base package niet genereren", e);
            }
        }

        return reports;
                    }

    private void genereer(final AbstractJavaGenerator<?> generator, final List<GenerationReport> reports,
            final File targetDir)
    {
        generator.setGeneratedSourcesPath(targetDir.getAbsolutePath());
        generator.genereerPackageInfo();
        reports.add(generator.genereer());
    }

}
