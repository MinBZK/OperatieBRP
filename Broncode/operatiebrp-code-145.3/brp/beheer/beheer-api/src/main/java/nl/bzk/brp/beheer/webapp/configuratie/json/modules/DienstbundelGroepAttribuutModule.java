/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json.modules;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import javax.inject.Inject;
import nl.bzk.brp.beheer.webapp.view.DienstbundelGroepAttribuutView;
import org.springframework.stereotype.Component;

/**
 * Dienstbundelgroep module voor serializen en deserializen van DienstbundelGroep.
 */
@Component
public class DienstbundelGroepAttribuutModule extends SimpleModule {

    /**
     * veld id.
     */
    public static final String ID = "id";
    /**
     * veld dienstbundelgroep.
     */
    public static final String DIENSTBUNDEL_GROEP = "dienstbundelGroep";
    /**
     * veld attribuut.
     */
    public static final String ACTIEF = "actief";
    /**
     * veld attribuut.
     */
    public static final String ATTRIBUUT = "attribuut";
    /**
     * veld attribuut naam.
     */
    public static final String ATTRIBUUT_NAAM = "attribuutNaam";
    /**
     * veld attribuut.
     */
    public static final String SOORT = "soort";

    /**
     * waarde ja.
     */
    public static final String WAARDE_JA = "Ja";
    /**
     * waarde nee.
     */
    public static final String WAARDE_NEE = "Nee";

    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * @param dienstbundelGroepAttribuutDeserializer specifieke deserializer voor dienstbundel groep attributen
     * @param dienstbundelGroepAttribuutSerializer specifieke serializer voor dienstbundel groep attributen
     */
    @Inject
    public DienstbundelGroepAttribuutModule(
            final DienstbundelGroepAttribuutDeserializer dienstbundelGroepAttribuutDeserializer,
            final DienstbundelGroepAttribuutSerializer dienstbundelGroepAttribuutSerializer) {
        addDeserializer(DienstbundelGroepAttribuutView.class, dienstbundelGroepAttribuutDeserializer);
        addSerializer(DienstbundelGroepAttribuutView.class, dienstbundelGroepAttribuutSerializer);
    }

    @Override
    public final String getModuleName() {
        return "DienstbundelGroepAttribuutModule";
    }

    @Override
    public final Version version() {
        return new Version(1, 0, 0, null, null, null);
    }
}
