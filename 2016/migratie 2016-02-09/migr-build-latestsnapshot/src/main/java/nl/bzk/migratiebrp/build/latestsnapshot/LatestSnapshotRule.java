/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.build.latestsnapshot;

import edu.emory.mathcs.backport.java.util.Arrays;
import edu.emory.mathcs.backport.java.util.Collections;
import java.util.List;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.artifact.manager.WagonManager;
import org.apache.maven.artifact.metadata.ArtifactMetadataRetrievalException;
import org.apache.maven.artifact.metadata.ArtifactMetadataSource;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.ArtifactResolver;
import org.apache.maven.artifact.versioning.ArtifactVersion;
import org.apache.maven.artifact.versioning.InvalidVersionSpecificationException;
import org.apache.maven.artifact.versioning.VersionRange;
import org.apache.maven.enforcer.rule.api.EnforcerRuleException;
import org.apache.maven.enforcer.rule.api.EnforcerRuleHelper;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Dependency;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.path.PathTranslator;
import org.apache.maven.settings.Settings;
import org.codehaus.mojo.versions.api.ArtifactVersions;
import org.codehaus.mojo.versions.api.DefaultVersionsHelper;
import org.codehaus.mojo.versions.api.UpdateScope;
import org.codehaus.mojo.versions.api.VersionsHelper;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluationException;
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;

/**
 * Rule die controleert of een dependency de laatset snapshot gebruikt.
 */
public final class LatestSnapshotRule extends AbstractUncacheableRule {

    private static final String PROPERTY_NOT_FOUND = "Property %s required but not found";
    private static final String DEPENDENCY_NOT_FOUND = "Dependency '%s:%s' not found in project.";
    private static final String NOT_LATEST_VERSION = "Artifact %s:%s is not on the latest version (current=%s, latest=%s).";

    /** groupId to check. */
    private String groupId;
    /** artifactId to check. */
    private String artifactId;

    /**
     * Controleer dat de gegeven groupId:artifactId dependency de laatste snapshot gebruikt.
     *
     * @param helper
     *            helper
     * @throws EnforcerRuleException
     *             als de rule faalt
     */
    @Override
    public void execute(final EnforcerRuleHelper helper) throws EnforcerRuleException {

        try {
            final Dependency dependency = findDependency(helper);
            helper.getLog().info("Dependency: " + dependency);

            final VersionsHelper versionsHelper = getVersionsHelper(helper);
            final ArtifactVersions versions = findVersions(versionsHelper, dependency);
            helper.getLog().debug("Versions: " + versions);
            final ArtifactVersion[] updates = versions.getAllUpdates(UpdateScope.ANY);
            final int availableUpdates = updates == null ? 0 : updates.length;
            helper.getLog().info(availableUpdates + " updates available");

            if (availableUpdates > 0) {
                final List<ArtifactVersion> updateList = Arrays.asList(updates);
                Collections.sort(updateList, versions.getVersionComparator());
                Collections.reverse(updateList);

                throw new EnforcerRuleException(String.format(NOT_LATEST_VERSION, groupId, artifactId, dependency.getVersion(), updateList.get(0)));
            }

        } catch (final ComponentLookupException e) {
            throw new EnforcerRuleException("Unable to lookup a component: " + e.getLocalizedMessage(), e);
        } catch (final ExpressionEvaluationException e) {
            throw new EnforcerRuleException("Unable to lookup an expression: " + e.getLocalizedMessage(), e);
        } catch (final MojoExecutionException e) {
            throw new EnforcerRuleException("Unable to create version helper: " + e.getLocalizedMessage(), e);
        } catch (final ArtifactMetadataRetrievalException e) {
            throw new EnforcerRuleException("Unable to lookup artifact: " + e.getLocalizedMessage(), e);
        } catch (final InvalidVersionSpecificationException e) {
            throw new EnforcerRuleException("Unable to lookup versions: " + e.getLocalizedMessage(), e);
        }
    }

    private ArtifactVersions findVersions(final VersionsHelper versionsHelper, final Dependency dependency)
        throws InvalidVersionSpecificationException, ArtifactMetadataRetrievalException
    {
        final VersionRange versionRange = VersionRange.createFromVersionSpec(dependency.getVersion());

        final Artifact artifact =
                versionsHelper.getArtifactFactory().createDependencyArtifact(
                    dependency.getGroupId(),
                    dependency.getArtifactId(),
                    versionRange,
                    dependency.getType(),
                    dependency.getClassifier(),
                    dependency.getScope());
        versionsHelper.getLog().info("Getting available versions for " + artifact);

        return versionsHelper.lookupArtifactUpdates(artifact, true, false);
    }

    private VersionsHelper getVersionsHelper(final EnforcerRuleHelper helper)
        throws ComponentLookupException, ExpressionEvaluationException, MojoExecutionException
    {
        final ArtifactFactory artifactFactory = getComponent(helper, ArtifactFactory.class);
        final ArtifactResolver artifactResolver = getComponent(helper, ArtifactResolver.class);
        final ArtifactMetadataSource artifactMetadataSource = getComponent(helper, ArtifactMetadataSource.class);
        final List<?> remoteArtifactRepositories = (List<?>) getProperty(helper, "project.remoteArtifactRepositories", false, null);
        final List<?> remotePluginRepositories = (List<?>) getProperty(helper, "project.pluginArtifactRepositories", false, null);
        final ArtifactRepository localRepository = (ArtifactRepository) getProperty(helper, "localRepository", false, null);
        final WagonManager wagonManager = getComponent(helper, WagonManager.class);
        final Settings settings = (Settings) getProperty(helper, "project.settings", false, null);
        final String serverId = (String) getProperty(helper, "maven.version.rules.serverId", true, "serverId");
        final String rulesUri = (String) getProperty(helper, "maven.version.rules", false, null);
        final Log log = helper.getLog();
        final MavenSession session = (MavenSession) getProperty(helper, "session", true, null);
        final PathTranslator pathTranslator = getComponent(helper, PathTranslator.class);

        final VersionsHelper versionsHelper =
                new DefaultVersionsHelper(
                    artifactFactory,
                    artifactResolver,
                    artifactMetadataSource,
                    remoteArtifactRepositories,
                    remotePluginRepositories,
                    localRepository,
                    wagonManager,
                    settings,
                    serverId,
                    rulesUri,
                    log,
                    session,
                    pathTranslator);
        return versionsHelper;
    }

    private Object getProperty(final EnforcerRuleHelper helper, final String property, final boolean required, final Object defaultValue)
        throws ExpressionEvaluationException
    {
        final Object evalautedResult = helper.evaluate("${" + property + "}");
        final Object result = evalautedResult == null ? defaultValue : evalautedResult;
        if (required && result == null) {
            throw new ExpressionEvaluationException(String.format(PROPERTY_NOT_FOUND, property));
        }
        return result;
    }

    private <T> T getComponent(final EnforcerRuleHelper helper, final Class<T> clazz) throws ComponentLookupException {
        return (T) helper.getComponent(clazz);
    }

    private Dependency findDependency(final EnforcerRuleHelper helper) throws ExpressionEvaluationException, EnforcerRuleException {
        final MavenProject project = (MavenProject) helper.evaluate("${project}");

        for (final Dependency dependency : (List<Dependency>) project.getDependencies()) {
            if (dependency.getGroupId().equals(groupId) && dependency.getArtifactId().equals(artifactId)) {
                return dependency;
            }
        }

        throw new EnforcerRuleException(String.format(DEPENDENCY_NOT_FOUND, groupId, artifactId));
    }

}
