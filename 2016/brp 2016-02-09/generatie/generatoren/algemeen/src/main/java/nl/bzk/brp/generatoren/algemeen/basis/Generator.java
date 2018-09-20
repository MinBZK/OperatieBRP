/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.algemeen.basis;


/**
 * Standaard interface voor alle generatoren. Een generator biedt standaard een enkele operatie voor het starten
 * van de generatie.
 */
public interface Generator {

    /**
     * Genereert de voor de generator specifieke bestanden conform de opgegeven configuratie (o.a. pad waar naar
     * toe gegenereerd dient te worden).
     *
     * @param generatorConfiguratie de configuratie voor de generator (o.a. het pad waar naar toe gegenereerd dient
     * te worden).
     */
    void genereer(GeneratorConfiguratie generatorConfiguratie);

    /**
     * Retourneert de naam van de generator.
     *
     * @return de naam van de generator.
     */
    String getNaam();

    /**
     * Retourneert de versie van het meta register project dat gebruikt wordt door deze generator.
     *
     * @return de versie van het gebruikte meta register.
     */
    String getMetaRegisterVersie();

    /**
     * Retourneert de versie van de generator.
     *
     * @return de versie van de generator.
     */
    String getVersie();

    /**
     * Retourneert de timestamp van de build (als tekst String).
     *
     * @return de timestamp van de build.
     */
    String getBuildTimestamp();

}
