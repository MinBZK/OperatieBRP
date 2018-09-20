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
     * Enumeratie met alle velden in version.xml.
     */

    public enum SvnVersionEnum {
        /**
         * mavenbuildtimestamp.
         */

        mavenbuildtimestamp,

        /**
         * pomversion.
         */

        pomversion,

        /**
         * java_version.
         */
        java_version,
        /**
         * java_vendor.
         */
        java_vendor,
        /**
         * java_vendor_url.
         */
        java_vendor_url,
        /**
         * java_home.
         */
        java_home,
        /**
         * java_vm_specification_version.
         */
        java_vm_specification_version,
        /**
         * java_vm_specification_vendor.
         */
        java_vm_specification_vendor,
        /**
         * java_vm_specification_name.
         */
        java_vm_specification_name,
        /**
         * java_vm_version.
         */
        java_vm_version,
        /**
         * java_vm_vendor.
         */
        java_vm_vendor,
        /**
         * java_vm_name.
         */
        java_vm_name,
        /**
         * java_specification_version.
         */
        java_specification_version,
        /**
         * java_specification_vendor.
         */
        java_specification_vendor,
        /**
         * java_specification_name.
         */
        java_specification_name,
        /**
         * java_class_version.
         */
        java_class_version,
        /**
         * java_class_path.
         */

        java_class_path,
        /**
         * java_library_path.
         */
        java_library_path,
        /**
         * java_io_tmpdir.
         */
        java_io_tmpdir,
        /**
         * java_ext_dirs.
         */
        java_ext_dirs,
        /**
         * os_name.
         */
        os_name,
        /**
         * os_arch.
         */
        os_arch,
        /**
         * os_version.
         */
        os_version
    }

    /**
     * Geeft de naam en versie van de applicatie terug.
     *
     * @return De applicatie version String
     */
    String getAppString();
}
