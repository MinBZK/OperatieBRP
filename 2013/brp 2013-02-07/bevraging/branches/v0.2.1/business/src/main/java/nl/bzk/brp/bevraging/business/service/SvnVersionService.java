/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.service;

/**
 * Service die de applicatie naam en versie string terug geeft.
 */

public interface SvnVersionService {

    /**
     * Enumeratie met alle velden uit het version.xml bestand. Dit bestand wordt middels Maven gefilterd waarbij de in
     * het bestand aanwezige placeholders door Maven worden vervangen in de binnen de Maven omgeving bekende waardes
     * voor deze placeholders, zoals de versie van de pom, tijdstip van de build etc.
     * Deze enumeratiewaardes zijn dus de keys van
     */
    public enum SvnVersionEnum {
        /**
         * Tijdstip waarop de maven build is gedraaid.
         */
        MAVENBUILDTIMESTAMP,
        /**
         * Versie (nummer) van de Maven Pom en daarmee van het project.
         */
        POMVERSION,
        /**
         * Java Runtime Environment (JRE) versie welke gebruikt is voor de build.
         */
        JAVA_VERSION,
        /**
         * Java Runtime Environment (JRE) leverancier welke gebruikt is voor de build.
         */
        JAVA_VENDOR,
        /**
         * URL naar de website van de JRE leverancier.
         */
        JAVA_VENDOR_URL,
        /**
         * Java installatie folder op de machine waar de build is uitgevoerd.
         */
        JAVA_HOME,
        /**
         * Java VM specificatie versie.
         */
        JAVA_VM_SPECIFICATION_VERSION,
        /**
         * Java VM specificatie leverancier.
         */
        JAVA_VM_SPECIFICATION_VENDOR,
        /**
         * Java VM specificatie naam.
         */
        JAVA_VM_SPECIFICATION_NAME,
        /**
         * Java VM versie.
         */
        JAVA_VM_VERSION,
        /**
         * JAVA_VM_VENDOR.
         */
        JAVA_VM_VENDOR,
        /**
         * Java VM naam.
         */
        JAVA_VM_NAME,
        /**
         * JRE specificatie versie.
         */
        JAVA_SPECIFICATION_VERSION,
        /**
         * JRE specificatie leverancier.
         */
        JAVA_SPECIFICATION_VENDOR,
        /**
         * JRE specificatie naam.
         */
        JAVA_SPECIFICATION_NAME,
        /**
         * Java class formaat versie.
         */
        JAVA_CLASS_VERSION,
        /**
         * Java classpath.
         */
        JAVA_CLASS_PATH,
        /**
         * Pad naar gebruikte Java libraries.
         */
        JAVA_LIBRARY_PATH,
        /**
         * Tijdelijke directory.
         */
        JAVA_IO_TMPDIR,
        /**
         * Pad naar extensie folder(s).
         */
        JAVA_EXT_DIRS,
        /**
         * Naam van het Operating Systeem.
         */
        OS_NAME,
        /**
         * Architectuur van het Operating Systeem.
         */
        OS_ARCH,
        /**
         * Versie van het Operating Systeem.
         */
        OS_VERSION
    }

    /**
     * Geeft de naam en versie van de applicatie terug.
     *
     * @return De applicatie version String
     */
    String getAppString();
}
