/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.utils.maven.huisregels;

import java.io.File;
import java.io.IOException;
import org.apache.maven.enforcer.rule.api.EnforcerRule;
import org.apache.maven.enforcer.rule.api.EnforcerRuleException;
import org.apache.maven.enforcer.rule.api.EnforcerRuleHelper;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluationException;

/**
 * EnforcerRule die regels kan afdwingen op het gebied van package cycles.
 */
public class PackageCycleRegel implements EnforcerRule {

    /**
     * Het maximum aantal packages dat deel mag uitmaken van een cycle.
     */
    private int maxPackagesWithCycles = 0;

    @Override
    public void execute(final EnforcerRuleHelper helper) throws EnforcerRuleException {
        Log log = helper.getLog();
        try {
            MavenProject project = (MavenProject) helper.evaluate("${project}");
            File targetDir = new File((String) helper.evaluate("${project.build.directory}"));
            File classesDir = new File(targetDir, "classes");

            if (project.getPackaging().equalsIgnoreCase("jar") && classesDir.exists()) {
                DependencyAnalyzer analyzer = new DependencyAnalyzer();
                analyzer.addDirectory(classesDir.getAbsolutePath());
                analyzer.analyze();

                int packagesWithCyclesCount = analyzer.packagesWithCyclesCount();
                if (packagesWithCyclesCount > 0) {
                    log.warn("Aantal packages met cycles: " + packagesWithCyclesCount);
                } else {
                    log.info("Geen package-cycles gevonden");
                }

                if (packagesWithCyclesCount > maxPackagesWithCycles) {
                    String msg = String.format("Er zijn %d packages met cycles gevonden (max %d)", packagesWithCyclesCount, maxPackagesWithCycles);
                    throw new EnforcerRuleException(msg);
                }


            }
            else
            {
                log.warn("Skipping jdepend analysis as " + classesDir + " does not exist.");
            }
        }
        catch (ExpressionEvaluationException e)
        {
            throw new EnforcerRuleException("Unable to lookup an expression "
                + e.getLocalizedMessage(), e);
        }
        catch (IOException e)
        {
            throw new EnforcerRuleException("Unable to access target directory "
                + e.getLocalizedMessage(), e);
        }
    }

    @Override
    public boolean isCacheable() {
        return false;
    }

    @Override
    public boolean isResultValid(final EnforcerRule enforcerRule) {
        return false;
    }

    @Override
    public String getCacheId() {
        return "";
    }
}
