/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.xsd.util;

import nl.bzk.brp.generatoren.algemeen.common.GeneratieUtil;
import nl.bzk.brp.metaregister.model.Attribuut;

import org.apache.commons.lang.StringUtils;

/**
 * Helper klasse voor het bepalen van de in de XSD te gebruiken elementnamen voor attributen.
 */
public final class XsdNaamgevingUtil {

    /**
     * Util klasse, private constructor.
     */
    private XsdNaamgevingUtil() {
    }

    /**
     * Bepaalt XSD element naam voor een stamgegeven attribuut. (Verwijzend attribuut)
     *
     * @param attribuut Het attribuut.
     * @param logischeIdentiteitAttribuut het attribuut met de logische identiteit
     * @return Naam van xsd element voor attribuut.
     */
    public static String bepaalElementNaamVoorStamgegevenAttribuut(final Attribuut attribuut,
        final Attribuut logischeIdentiteitAttribuut)
    {
        String elementNaam = bepaalBasisElementNaamVoorAttribuut(attribuut);

        elementNaam += logischeIdentiteitAttribuut.getIdentCode();
        // Misschien is de logische identiteit attribuut naam de enige naam, dus opnieuw eerste karakter 'verlagen'.
        elementNaam = GeneratieUtil.lowerTheFirstCharacter(elementNaam);
        return vervangEventueelDoorCustomOverride(attribuut, elementNaam);
    }

    /**
     * Bepaalt XSD element naam voor een attribuut met als type een attribuut type.
     *
     * @param attribuut Het attribuut.
     * @return Naam van xsd element voor attribuut.
     */
    public static String bepaalElementNaamVoorAttribuutTypeAttribuut(final Attribuut attribuut) {
        String elementNaam = bepaalBasisElementNaamVoorAttribuut(attribuut);
        return vervangEventueelDoorCustomOverride(attribuut, elementNaam);
    }

    /**
     * Bepaalt de basis XSD element naam voor een attribuut. Dat is de ident code,
     * waaruit de groepsnaam gehaald wordt en de eerste letter naar lower case gezet wordt.
     *
     * @param attribuut het attribuut
     * @return de xsd element naam
     */
    private static String bepaalBasisElementNaamVoorAttribuut(final Attribuut attribuut) {
        return GeneratieUtil.lowerTheFirstCharacter(
            attribuut.getIdentCode().replace(attribuut.getGroep().getIdentCode(), ""));
    }

    /**
     * Als er een custom element naam is geconfigureerd in het BMR, neem dan die naam.
     * Zo niet, return dan de parameter element naam.
     *
     * @param attribuut het attribuut
     * @param elementNaam de afgeleide element naam
     * @return de afgeleide element naam of de custom override naam (indien aanwezig)
     */
    private static String vervangEventueelDoorCustomOverride(final Attribuut attribuut, final String elementNaam) {
        String nieuweElementNaam = elementNaam;
        // Er kan ook een gehele custom override zijn van de naam (identificatie) van deze tag. (ROMEO-167)
        if (StringUtils.isNotBlank(attribuut.getXsdIdentificatie())) {
            nieuweElementNaam = GeneratieUtil.lowerTheFirstCharacter(attribuut.getXsdIdentificatie());
        }
        return nieuweElementNaam;
    }

}
