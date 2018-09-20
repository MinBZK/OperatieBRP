/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.writer;

import java.io.File;

import nl.bzk.brp.generatoren.java.model.AbstractJavaType;

/**
 * Algemene basis implementatie van java writer logica.
 *
 * @param <T> het specifieke java type dat geschreven wordt.
 */
public abstract class AbstractJavaWriter<T extends AbstractJavaType> implements JavaWriter<T> {

    protected static final String  PAD_SEPARATOR           = System.getProperty("file.separator");
    protected static final String  JAVA_EXTENSIE           = ".java";

    private final String generatieBasisFolder;

    /**
     * Maak een nieuwe abstract java generator aan.
     *
     * @param generatieBasisFolder de generatie bais folder
     */
    public AbstractJavaWriter(final String generatieBasisFolder) {
        this.generatieBasisFolder = generatieBasisFolder;
    }

    /**
     * Bouwt een nieuw bestand voor het opgegeven Java broncode object, waarbij het gehele pad wordt opgegeven. De op
     * de writer geconfigureerde basis map voor generatie zal hier als basis worden gebruikt, waarna verder het package
     * en de naam van het bestand van invloed zijn op de bestandsnaam.
     *
     * @param javaBroncodeObject het Java broncode object waarvoor een bestand gemaakt dient te worden.
     * @return een bestand met een voor het Java broncode object specifieke bestandslocatie.
     */
    protected File bouwBronBestand(final T javaBroncodeObject) {
        StringBuilder bestandNaam = new StringBuilder();
        bestandNaam.append(generatieBasisFolder);
        bestandNaam.append(PAD_SEPARATOR);
        if (javaBroncodeObject.getPackagePad() != null) {
            bestandNaam.append(zetPackageOmNaarPad(javaBroncodeObject.getPackagePad()));
            bestandNaam.append(PAD_SEPARATOR);
        }
        bestandNaam.append(javaBroncodeObject.getNaam());
        bestandNaam.append(JAVA_EXTENSIE);
        return new File(bestandNaam.toString());
    }

    /**
     * Zet de opgegeven package naam om naar een platform specifiek pad. Hierbij wordt elk subpackage een aparte map.
     *
     * @param packageNaam de package naam die omgezet dient te worden.
     * @return het platform specifeke pad voor de opgegeven package naam.
     */
    private String zetPackageOmNaarPad(final String packageNaam) {
        return packageNaam.replace(".", PAD_SEPARATOR);
    }

}
