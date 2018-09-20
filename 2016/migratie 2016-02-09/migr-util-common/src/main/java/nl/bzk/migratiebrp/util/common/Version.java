/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.util.common;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Versie informatie.
 */
public final class Version {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final String GROUP_ATTRIBUTE = "Migratie-Project-Group";
    private static final String ARTIFACT_ATTRIBUTE = "Migratie-Project-Artifact";
    private static final String NAME_ATTRIBUTE = "Migratie-Project-Name";
    private static final String VERSION_ATTRIBUTE = "Migratie-Project-Version";
    private static final String BUILD_ATTRIBUTE = "Migratie-Build-number";
    private static final String REVISION_ATTRIBUTE = "Migratie-Svn-Revision";

    private static final String UNKOWN = "?";

    private final VersionLine mainVersion;
    private final List<VersionLine> components;

    private Version(final VersionLine mainVersion, final List<VersionLine> components) {
        this.mainVersion = mainVersion;
        this.components = components;
    }

    /**
     * Lees de versie informatie.
     * 
     * @param mainArtifactId
     *            artifact id van de 'main'
     * @param mainGroupId
     *            group id van de 'main'
     * @return versie informatie
     */
    public static Version readVersion(final String mainGroupId, final String mainArtifactId) {
        final List<VersionLine> components = new ArrayList<>();

        try {
            final Enumeration<URL> resEnum = Thread.currentThread().getContextClassLoader().getResources(JarFile.MANIFEST_NAME);
            while (resEnum.hasMoreElements()) {
                final URL url = resEnum.nextElement();
                try (InputStream is = url.openStream()) {
                    final Manifest manifest = new Manifest(is);
                    final Attributes attributes = manifest.getMainAttributes();

                    final String artifact = attributes.getValue(ARTIFACT_ATTRIBUTE);
                    if (artifact != null && !artifact.isEmpty()) {
                        final VersionLine component =
                                new VersionLine(
                                    attributes.getValue(GROUP_ATTRIBUTE),
                                    attributes.getValue(ARTIFACT_ATTRIBUTE),
                                    attributes.getValue(NAME_ATTRIBUTE),
                                    attributes.getValue(VERSION_ATTRIBUTE),
                                    attributes.getValue(BUILD_ATTRIBUTE),
                                    attributes.getValue(REVISION_ATTRIBUTE));
                        components.add(component);
                    }
                } catch (final IOException e) {
                    LOGGER.warn("Could not read manifest: {} ", url, e);
                }
            }
        } catch (final IOException e) {
            LOGGER.warn("Could not get manifest enumeration.", e);
        }

        Collections.sort(components);
        return new Version(determineMainVersion(components, mainGroupId, mainArtifactId), components);
    }

    private static VersionLine determineMainVersion(final List<VersionLine> components, final String mainGroupId, final String mainArtifactId) {
        for (final VersionLine component : components) {
            if (mainArtifactId.equalsIgnoreCase(component.artifact) && mainGroupId.equalsIgnoreCase(component.group)) {
                return component;
            }
        }

        return new VersionLine(mainGroupId, mainArtifactId, UNKOWN, UNKOWN, UNKOWN, UNKOWN);
    }

    @Override
    public String toString() {
        return mainVersion.toString();
    }

    /**
     * Toon details.
     * 
     * @return details
     */
    public String toDetailsString() {
        final StringBuilder sb = new StringBuilder();
        for (final VersionLine component : components) {
            if (sb.length() != 0) {
                sb.append("\n");
            }

            sb.append(component.toDetailsString());
        }

        return sb.toString();
    }

    /**
     * Component.
     */
    private static final class VersionLine implements Comparable<VersionLine> {
        private final String group;
        private final String artifact;
        private final String name;
        private final String version;
        private final String build;
        private final String revision;

        /**
         * Constructor.
         * 
         * @param group
         *            group
         * @param artifact
         *            artifact
         * @param name
         *            name
         * @param version
         *            version
         * @param build
         *            build
         * @param revision
         *            revision
         */
        public VersionLine(final String group, final String artifact, final String name, final String version, final String build, final String revision) {
            super();
            this.group = group;
            this.artifact = artifact;
            this.name = name;
            this.version = version;
            this.build = build;
            this.revision = revision;
        }

        @Override
        public String toString() {
            return name + " version " + version;
        }

        /**
         * toString met details.
         * 
         * @return description met details
         */
        public String toDetailsString() {
            return name + " (" + group + ":" + artifact + ") version " + version + " (build " + build + ", revision " + revision + ")";
        }

        @Override
        public int compareTo(final VersionLine that) {
            int result = group.compareTo(that.group);

            if (result == 0) {
                result = artifact.compareTo(that.artifact);
            }

            if (result == 0) {
                result = version.compareTo(that.version);
            }

            if (result == 0) {
                result = revision.compareTo(that.revision);
            }

            if (result == 0) {
                result = build.compareTo(that.build);
            }

            return result;
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            if (obj.getClass() != getClass()) {
                return false;
            }
            final VersionLine rhs = (VersionLine) obj;
            return new EqualsBuilder().append(this.group, rhs.group)
                                      .append(this.artifact, rhs.artifact)
                                      .append(this.version, rhs.version)
                                      .append(this.build, rhs.build)
                                      .append(this.revision, rhs.revision)
                                      .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(group).append(artifact).append(version).append(build).append(revision).toHashCode();
        }
    }

}
