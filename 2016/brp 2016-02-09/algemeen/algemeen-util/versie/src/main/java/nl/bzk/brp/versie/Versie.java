/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.versie;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Versie informatie.
 */
public final class Versie {

    private static final Logger LOGGER = LoggerFactory.getLogger(Versie.class);

    private static final String GROUP_ATTRIBUTE = "BRP-Project-Group";
    private static final String ARTIFACT_ATTRIBUTE = "BRP-Project-Artifact";
    private static final String NAME_ATTRIBUTE = "BRP-Project-Name";
    private static final String VERSION_ATTRIBUTE = "BRP-Project-Version";
    private static final String BUILD_ATTRIBUTE = "BRP-Build-number";
    private static final String REVISION_ATTRIBUTE = "BRP-Svn-Revision";

    private static final String UNKOWN = "?";

    private final VersieRegel mainVersie;
    private final List<VersieRegel> components;

    private Versie(final VersieRegel mainVersie, final List<VersieRegel> components) {
        this.mainVersie = mainVersie;
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
    public static Versie leesVersie(final String mainGroupId, final String mainArtifactId) {
        final List<VersieRegel> components = new ArrayList<>();

        try {
            final Enumeration<URL> resEnum = Thread.currentThread().getContextClassLoader().getResources(JarFile.MANIFEST_NAME);
            while (resEnum.hasMoreElements()) {
                final URL url = resEnum.nextElement();
                try (InputStream is = url.openStream()) {
                    final Manifest manifest = new Manifest(is);
                    final Attributes attributes = manifest.getMainAttributes();

                    final String artifact = attributes.getValue(ARTIFACT_ATTRIBUTE);
                    if (artifact != null && !artifact.isEmpty()) {
                        final VersieRegel component =
                                new VersieRegel(
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

        Collections.sort(components, new VersieRegelComparator());
        return new Versie(determineMainVersie(components, mainGroupId, mainArtifactId), components);
    }

    private static VersieRegel determineMainVersie(final List<VersieRegel> components, final String mainGroupId, final String mainArtifactId) {
        for (final VersieRegel component : components) {
            if (mainArtifactId.equalsIgnoreCase(component.artifact) && mainGroupId.equalsIgnoreCase(component.group)) {
                return component;
            }
        }

        return new VersieRegel(mainGroupId, mainArtifactId, UNKOWN, UNKOWN, UNKOWN, UNKOWN);
    }

    public VersieRegel getApplicatieVersie() {
    	return mainVersie;
    }

    public List<VersieRegel> getComponentVersies() {
    	return components;
    }

    @Override
    public String toString() {
        return mainVersie.toString();
    }

    /**
     * Toon details.
     *
     * @return details
     */
    public String toDetailsString() {
        final StringBuilder sb = new StringBuilder();
        for (final VersieRegel component : components) {
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
    public static final class VersieRegel {
        private final String group;
        private final String artifact;
        private final String name;
        private final String versie;
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
         * @param Versie
         *            Versie
         * @param build
         *            build
         * @param revision
         *            revision
         */
        public VersieRegel(final String group, final String artifact, final String name, final String versie, final String build, final String revision) {
            super();
            this.group = group;
            this.artifact = artifact;
            this.name = name;
            this.versie = versie;
            this.build = build == null || "".equals(build) ? UNKOWN : build;
            this.revision =  revision == null || "".equals(revision) ? UNKOWN : revision;
        }

		/**
		 * Geef group.
		 *
		 * @return group
		 */
        public String getGroup() {
			return group;
		}

        /**
		 * Geef artifact.
		 *
		 * @return artifact
		 */
		public String getArtifact() {
			return artifact;
		}

		/**
		 * Geef name.
		 *
		 * @return name
		 */
		public String getName() {
			return name;
		}

		/**
		 * Geef versie.
		 *
		 * @return versie
		 */
		public String getVersie() {
			return versie;
		}

		/**
		 * Geef build.
		 *
		 * @return build
		 */
		public String getBuild() {
			return build;
		}

		/**
		 * Geef revision.
		 *
		 * @return revision
		 */
		public String getRevision() {
			return revision;
		}

		@Override
        public String toString() {
            return name + " (versie " + versie + ")";
        }

        /**
         * toString met details.
         *
         * @return description met details
         */
        public String toDetailsString() {
            return name + " (" + group + ":" + artifact + ") Versie " + versie + " (build " + build + ", revision " + revision + ")";
        }

    }

    /**
     * Sortering van versie regels.
     */
    public static final class VersieRegelComparator implements Comparator<VersieRegel>, Serializable {
		private static final long serialVersionUID = 1L;

		@Override
		public int compare(final VersieRegel object1, final VersieRegel object2) {
            int result = object1.group.compareTo(object2.group);

            if (result == 0) {
                result =  object1.artifact.compareTo(object2.artifact);
            }

            if (result == 0) {
                result =  object1.versie.compareTo(object2.versie);
            }

            if (result == 0) {
                result =  object1.revision.compareTo(object2.revision);
            }

            if (result == 0) {
                result =  object1.build.compareTo(object2.build);
            }

            return result;
        }
    }
}
