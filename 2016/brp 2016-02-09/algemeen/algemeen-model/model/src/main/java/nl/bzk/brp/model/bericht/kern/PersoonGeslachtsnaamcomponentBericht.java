/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import javax.validation.Valid;
import nl.bzk.brp.model.logisch.kern.PersoonGeslachtsnaamcomponent;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * Component van de Geslachtsnaam van een Persoon
 * <p/>
 * De geslachtsnaam van een Persoon kan uit meerdere delen bestaan, bijvoorbeeld ten gevolge van een namenreeks. Ook kan er sprake zijn van het voorkomen
 * van meerdere geslachten, die in de geslachtsnaam terugkomen. In dat geval valt de Geslachtsnaam uiteen in meerdere Geslachtsnaamcomponenten. Een
 * Geslachtsnaamcomponent bestaat vervolgens mogelijkerwijs uit meerdere onderdelen, waaronder Voorvoegsel en Naamdeel. De structuur van de gegevens in de
 * centrale voorzieningen en in de berichtuitwisseling met de centrale voorzieningen is voorbereid op het nauwkeuriger vastleggen van naamgegevens. Dit is
 * bijvoorbeeld te zien aan het feit dat de berichtopbouw van berichten met naamgegevens ([verwijzing nader invullen]) ruimte geeft voor meerdere
 * geslachtsnaamcomponenten en het feit dat de groep geslachtsnaamcomponent een gegeven volgnummer kent.
 * <p/>
 * Indien in de toekomst (elektronische) akten afzonderlijke voornamen identificeren � eventueel met spaties erin � dan kunnen deze dan ook afzonderlijk
 * worden vastgelegd. En als, bijvoorbeeld als gevolg van liberalisering van het naamrecht, het zinvol wordt om ook afzonderlijke geslachtsnaamcomponenten
 * te onderkennen, dan kunnen ook die afzonderlijk worden vastgelegd. Daarbij kunnen voorvoegsels, scheidingstekens en adellijke titels gekoppeld worden
 * aan de specifieke geslachtsnaamcomponent waarop deze van toepassing zijn. Predikaten kunnen worden opgenomen als onderdeel van de gegevens over de
 * specifieke geslachtsnaamcomponent die het gebruik van het predikaat rechtvaardigt.
 * <p/>
 * Vooralsnog kunnen deze mogelijkheden niet worden gebruikt. De GBA kent een beperkte vastlegging van naamgegevens. Zolang de BRP en de GBA naast elkaar
 * bestaan � en/of zolang akten nauwkeuriger registratie nog niet ondersteunen � controleren de centrale voorzieningen dat het gebruik van de mogelijkheden
 * van de BRP beperkt blijft tot een enkel voorkomen van geslachtsnaamcomponent.
 * <p/>
 * 1. Vooruitlopend op liberalisering namenwet, waarbij het waarschijnlijk mogelijk wordt om de (volledige) geslachtsnaam van een kind te vormen door delen
 * van de geslachtsnaam van beide ouders samen te voegen, is het alvast mogelijk gemaakt om deze delen apart te 'kennen'. Deze beslissing is genomen na
 * raadpleging van ministerie van Justitie, in de persoon van Jet Lenters.
 */
public final class PersoonGeslachtsnaamcomponentBericht extends AbstractPersoonGeslachtsnaamcomponentBericht implements
    PersoonGeslachtsnaamcomponent
{

    private static final int HASHCODE_NIET_NUL_ONEVEN_GETAL            = 7;
    private static final int HASHCODE_MULTIPLIER_NIET_NUL_ONEVEN_GETAL = 21;

    @Override
    @Valid
    public PersoonGeslachtsnaamcomponentStandaardGroepBericht getStandaard() {
        return super.getStandaard();
    }

    @Override
    public boolean equals(final Object obj) {
        final boolean resultaat;
        if (!(obj instanceof PersoonGeslachtsnaamcomponentBericht)) {
            resultaat = false;
        } else {
            if (this == obj) {
                resultaat = true;
            } else {
                final PersoonGeslachtsnaamcomponentBericht pg = (PersoonGeslachtsnaamcomponentBericht) obj;
                resultaat =
                    new EqualsBuilder().append(getPersoon(), pg.getPersoon())
                        .append(getVolgnummer(), pg.getVolgnummer()).isEquals();
            }
        }
        return resultaat;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(HASHCODE_NIET_NUL_ONEVEN_GETAL, HASHCODE_MULTIPLIER_NIET_NUL_ONEVEN_GETAL)
            .append(getPersoon()).append(getVolgnummer()).toHashCode();
    }
}
