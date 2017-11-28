/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.common;

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
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Versie informatie.
 */
public final class Version {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final String GROUP_ATTRIBUTE = "OperatieBrp-Project-Group";
    private static final String ARTIFACT_ATTRIBUTE = "OperatieBrp-Project-Artifact";
    private static final String NAME_ATTRIBUTE = "OperatieBrp-Project-Name";
    private static final String VERSION_ATTRIBUTE = "OperatieBrp-Project-Version";

    private static final String UNKOWN = "?";

    private final VersionLine mainVersion;
    private final List<VersionLine> components;

    private Version(final VersionLine mainVersion, final List<VersionLine> components) {
        this.mainVersion = mainVersion;
        this.components = components;
    }

    /**
     * Lees de versie informatie.
     * @param mainArtifactId artifact id van de 'main'
     * @param mainGroupId group id van de 'main'
     * @return versie informatie
     */
    public static Version readVersion(final String mainGroupId, final String mainArtifactId) {
        final List<VersionLine> components = new ArrayList<>();

        try {
            final Enumeration<URL> resEnum = Thread.currentThread().getContextClassLoader().getResources(JarFile.MANIFEST_NAME);
            while (resEnum.hasMoreElements()) {
                processManifest(components, resEnum.nextElement());
            }
        } catch (final IOException e) {
            LOGGER.warn("Could not get manifest enumeration.", e);
        }

        Collections.sort(components);
        return new Version(determineMainVersion(components, mainGroupId, mainArtifactId), components);
    }

    private static void processManifest(final List<VersionLine> components, final URL url) {
        try (InputStream is = url.openStream()) {
            final Manifest manifest = new Manifest(is);
            final Attributes attributes = manifest.getMainAttributes();

            final String artifact = attributes.getValue(ARTIFACT_ATTRIBUTE);
            if (artifact != null && !artifact.isEmpty()) {
                components.add(maakVersionLine(attributes));
            }
        } catch (final IOException e) {
            LOGGER.warn("Could not read manifest: {} ", url, e);
        }
    }

    private static VersionLine maakVersionLine(final Attributes attributes) {
        return new VersionLine(attributes.getValue(GROUP_ATTRIBUTE), attributes.getValue(ARTIFACT_ATTRIBUTE), attributes.getValue(NAME_ATTRIBUTE),
                attributes.getValue(VERSION_ATTRIBUTE));
    }

    private static VersionLine determineMainVersion(final List<VersionLine> components, final String mainGroupId, final String mainArtifactId) {
        for (final VersionLine component : components) {
            if (mainArtifactId.equalsIgnoreCase(component.artifact) && mainGroupId.equalsIgnoreCase(component.group)) {
                return component;
            }
        }

        return new VersionLine(mainGroupId, mainArtifactId, UNKOWN, UNKOWN);
    }

    /**
     * Geef de hoofdversie.
     * @return versie
     */
    public VersionLine getMainVersion() {
        return mainVersion;
    }

    /**
     * Geef de component versies.
     * @return versies
     */
    public List<VersionLine> getComponentVersions() {
        return components;
    }

    @Override
    public String toString() {
        return mainVersion.toString();
    }

    /**
     * Toon details.
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
    public static final class VersionLine implements Comparable<VersionLine> {
        private final String group;
        private final String artifact;
        private final String name;
        private final String version;

        /**
         * Constructor.
         * @param group Maven group id
         * @param artifact Maven artifact id
         * @param name Maven project name
         * @param version Maven project version
         */
        public VersionLine(final String group, final String artifact, final String name, final String version) {
            super();
            this.group = group;
            this.artifact = artifact;
            this.name = name;
            this.version = version;
        }

        /**
         * Geef de Maven group id.
         * @return Maven group id
         */
        public String getGroup() {
            return group;
        }

        /**
         * Geef de Maven artifact id.
         * @return Maven artifact id
         */
        public String getArtifact() {
            return artifact;
        }

        /**
         * Geef de Maven project name.
         * @return Maven project name
         */
        public String getName() {
            return name;
        }

        /**
         * Geef de Maven project version.
         * @return Maven project version
         */
        public String getVersion() {
            return version;
        }

        @Override
        public String toString() {
            return name + " version " + version;
        }

        /**
         * toString met details.
         * @return description met details
         */
        public String toDetailsString() {
            return name + " (" + group + ":" + artifact + ") version " + version;
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

            return result;
        }

        @Override
        public boolean equals(final Object obj) {
            boolean result;

            if (obj == null) {
                result = false;
            } else {
                if (obj == this) {
                    result = true;
                } else {
                    if (obj.getClass() != getClass()) {
                        result = false;
                    } else {
                        final VersionLine rhs = (VersionLine) obj;
                        result = new EqualsBuilder().append(group, rhs.group).append(artifact, rhs.artifact).append(version, rhs.version).isEquals();
                    }
                }
            }
            return result;
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(group).append(artifact).append(version).toHashCode();
        }
    }

}
