/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import javax.validation.Valid;
import nl.bzk.brp.model.logisch.kern.PersoonVoornaam;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * Voornaam van een Persoon
 * <p/>
 * Voornamen worden in de BRP los van elkaar geregistreerd. Het LO BRP is voorbereid op het kunnen vastleggen van voornamen zoals 'Jan Peter', 'Aberto di
 * Maria' of 'Wonder op aarde' als ��n enkele voornaam. In de BRP is het namelijk niet noodzakelijk (conform LO 3.x) om de verschillende woorden aan elkaar
 * te plakken met een koppelteken.
 * <p/>
 * Het gebruik van de spatie als koppelteken is echter (nog) niet toegestaan.
 * <p/>
 * Indien er sprake is van een namenreeks wordt dit opgenomen als geslachtsnaam; er is dan geen sprake van een Voornaam.
 * <p/>
 * Een voornaam mag voorlopig nog geen spatie bevatten. Hiertoe dient eerst de akten van burgerlijke stand aangepast te worden (zodat voornamen individueel
 * kunnen worden vastgelegd, en er geen interpretatie meer nodig is van de ambtenaar over waar de ene voornaam eindigt en een tweede begint). Daarnaast is
 * er ook nog geen duidelijkheid over de wijze waarop bestaande namen aangepast kunnen worden: kan de burger hier simpelweg om verzoeken en wordt het dan
 * aangepast?
 * <p/>
 * De BRP is wel al voorbereid op het kunnen bevatten van spaties. RvdP 5 augustus 2011
 */
public final class PersoonVoornaamBericht extends AbstractPersoonVoornaamBericht implements PersoonVoornaam {

    private static final int HASHCODE_NIET_NUL_ONEVEN_GETAL            = 11;
    private static final int HASHCODE_MULTIPLIER_NIET_NUL_ONEVEN_GETAL = 21;

    @Valid
    @Override
    public PersoonVoornaamStandaardGroepBericht getStandaard() {
        return super.getStandaard();
    }

    @Override
    public boolean equals(final Object obj) {
        final boolean resultaat;
        if (!(obj instanceof PersoonVoornaamBericht)) {
            resultaat = false;
        } else {
            if (this == obj) {
                resultaat = true;
            } else {
                final PersoonVoornaamBericht pv = (PersoonVoornaamBericht) obj;
                resultaat =
                    new EqualsBuilder().append(getPersoon(), pv.getPersoon())
                        .append(getVolgnummer(), pv.getVolgnummer()).isEquals();
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
