/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
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
@SuppressWarnings("serial")
@Entity
@Table(schema = "Kern", name = "PersVoornaam")
public class PersoonVoornaamModel extends AbstractPersoonVoornaamModel implements PersoonVoornaam {

    private static final int HASHCODE_MULTIPLIER1 = 3;
    private static final int HASHCODE_MULTIPLIER2 = 65;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected PersoonVoornaamModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param persoon    persoon van Persoon \ Voornaam.
     * @param volgnummer volgnummer van Persoon \ Voornaam.
     */
    public PersoonVoornaamModel(final PersoonModel persoon, final VolgnummerAttribuut volgnummer) {
        super(persoon, volgnummer);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonVoornaam Te kopieren object type.
     * @param persoon         Bijbehorende Persoon.
     */
    public PersoonVoornaamModel(final PersoonVoornaam persoonVoornaam, final PersoonModel persoon) {
        super(persoonVoornaam, persoon);
    }

    @Override
    public boolean equals(final Object obj) {
        final boolean isGelijk;
        if (obj == null) {
            isGelijk = false;
        } else if (obj == this) {
            isGelijk = true;
        } else if (obj.getClass() != getClass()) {
            isGelijk = false;
        } else {
            final PersoonVoornaamModel voornaam = (PersoonVoornaamModel) obj;
            isGelijk =
                new EqualsBuilder().append(getPersoon(), voornaam.getPersoon())
                    .append(getVolgnummer(), voornaam.getVolgnummer()).isEquals();
        }
        return isGelijk;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(HASHCODE_MULTIPLIER1, HASHCODE_MULTIPLIER2).append(getPersoon())
            .append(getVolgnummer()).toHashCode();
    }

    /**
     * Geef de object sleutel van dit object.
     *
     * @return de object sleutel.
     */
    public String getObjectSleutel() {
        if (null == getID()) {
            return "X";
        } else {
            return getID().toString();
        }
    }

}
