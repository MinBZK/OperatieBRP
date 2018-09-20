/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.SynchroniseerbaarStamgegeven;

/**
 * Categorisatie van rechtsgrond.
 *
 * De verschillende rechtsgronden laten zich categoriseren naar (o.a.) het doel waarvoor de rechtsgrond wordt gebruikt:
 * zo hebben sommige rechtsgronden betrekking op het verkrijgen van de (Nederlandse) Nationaliteit, andere hebben
 * betrekking op het kunnen/mogen vastleggen van gegevens over niet-ingezetenen.
 *
 * Het opnemen van de Soorten rechtsgronden gebeurt zeer schaars. In geen enkele bijhouding wordt een rechtsgrond als
 * bron onderkend (behalve dan bij verkrijging/verlies NL nationaliteit).
 *
 * Dat komt ook doordat ooit is besloten om "triviale" rechtsgronden niet in de BRP te registreren. Daarvan is sprake
 * als een bepaald gegeven altijd wordt ontleend aan dezelfde rechtsgrond en het daarom geen toegevoegde waarde heeft om
 * het vast te leggen.
 *
 * Neem bijvoorbeeld de groep 'Behandeld als Nederlander': de inhoud daarvan wordt altijd verantwoord door de 'Wet
 * betreffende de positie van Molukkers'. Omdat dat per definitie altijd zo is, willen we dat niet vastleggen. Ander
 * voorbeeld: als er een vreemde nationaliteit van iemand wordt vastgelegd (d.w.z. niet-NL), dan leggen gemeenten in de
 * GBA daar vaak als bron een vrije tekst bij vast in de trant van "vreemde nationaliteitswetgeving", maar dat doen ze
 * allemaal verschillend. Voor de BRP hebben we gezegd dat hier sprake is van een triviale rechtsgrond, die we dus niet
 * vastleggen. Het voegt namelijks niks toe.
 *
 * Vermoedelijk geeft de RNI nog wel aanleiding tot het opnemen van rechtsgronden, bij de analyse van het
 * bijhoudingskoppelvlak is de RNI nog (grotendeels) buiten scope gebleven. We hebben daarom een tijd een tuple gehad
 * 'ID 1, Naam: Internationaal verdrag RNI'. Maar aangezien dit nog niet 100% zeker is, is dit tuple weer verwijderd.
 *
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.StatischeStamgegevensGenerator")
public enum SoortRechtsgrond implements SynchroniseerbaarStamgegeven, ElementIdentificeerbaar {

    /**
     * Dummy eerste waarde, omdat enum ordinals bij 0 beginnen te tellen, maar id's in de database bij 1.
     */
    DUMMY("Dummy");

    private final String naam;

    /**
     * Private constructor daar enums niet van buitenaf geinstantieerd mogen/kunnen worden.
     *
     * @param naam Naam voor SoortRechtsgrond
     */
    private SoortRechtsgrond(final String naam) {
        this.naam = naam;
    }

    /**
     * Retourneert Naam van Soort rechtsgrond.
     *
     * @return Naam.
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.SOORTRECHTSGROND;
    }

}
