/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.logisch.kern.PersoonAdres;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * Het adres zoals gedefinieerd in artikel 1.1. van de Wet BRP.
 * <p/>
 * Het adres is in essentie de koppeling "van een aanduiding van een bepaalde plek op aarde waarmee die plek kan worden geadresseerd" en de Persoon.
 * Adresgegevens behoren tot de meest gebruikte gegevens binnen administraties van de overheid en semi-overheid. Bij deze gegevens komt de
 * Basisadministratie Adressen en Gebouwen (BAG) nadrukkelijk in beeld, omdat de BRP verplicht gebruik moet maken van de gegevens in de BAG. Historische
 * adressen die vanuit de GBA-periode opgenomen (moeten) worden en die in de GBA geen BAG-gegevens bevatten, worden zonder BAG-verwijzing opgenomen. De
 * adresgegevens worden overgenomen uit de lokale BAG, en niet uit de centrale LV BAG.
 * <p/>
 * In dit objecttype wordt het adres gekoppeld aan de persoon. Dezelfde "plek op aarde", gekoppeld met twee verschillende Personen, heeft dus twee
 * exemplaren van Adres tot gevolg.
 *
 */
@Entity
@Table(schema = "Kern", name = "PersAdres")
public class PersoonAdresModel extends AbstractPersoonAdresModel
    implements PersoonAdres, Comparable<PersoonAdresModel>
{

    private static final int HASHCODE_GETAL1 = 34535;
    private static final int HASHCODE_GETAL2 = 43557;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected PersoonAdresModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param persoon persoon van Persoon \ Adres.
     */
    public PersoonAdresModel(final PersoonModel persoon) {
        super(persoon);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonAdres Te kopieren object type.
     * @param persoon      Bijbehorende Persoon.
     */
    public PersoonAdresModel(final PersoonAdres persoonAdres, final PersoonModel persoon) {
        super(persoonAdres, persoon);
    }

    /**
     * Vervangt de adres gegevens met de gegevens uit de opgegeven adres object.
     *
     * @param nieuwAdres het adres waarvan de gegevens moeten worden overgenomen.
     */
    public void vervang(final AbstractPersoonAdresModel nieuwAdres) {
        vervangGroepen(nieuwAdres.getStandaard());
    }

    /**
     * Vervang de groepen binnen dit ObjectType met een of meerdere groepen.
     *
     * @param groepen een lijst van compatibele groepen.
     */
    protected void vervangGroepen(final Groep... groepen) {
        if (groepen != null) {
            for (Groep groep : groepen) {
                if (groep instanceof PersoonAdresStandaardGroepModel) {
                    setStandaard((PersoonAdresStandaardGroepModel) groep);
                } else {
                    // adres heeft alleen maar 1 groep. Dus eigenlijk hoeven we geen list mee te geven.
                    throw new IllegalArgumentException("Groep van type " + groep.getClass().getName()
                        + " wordt hier niet ondersteund");
                }
            }
        }
    }

    @Override
    public int compareTo(final PersoonAdresModel o) {
        // Om twee nieuwe objecten (zonder ID) aan een SortedSet toe te kunnen voegen
        if (getID() == null && o.getID() == null) {
            return 1;
        }
        return new CompareToBuilder().append(getID(), o.getID()).toComparison();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        final boolean isGelijk;

        if (obj == null || obj.getClass() != getClass()) {
            isGelijk = false;
        } else if (this == obj) {
            isGelijk = true;
        } else {
            final PersoonAdresModel adresModel = (PersoonAdresModel) obj;
            isGelijk = new EqualsBuilder().append(this.getID(), adresModel.getID()).isEquals();
        }
        return isGelijk;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder(HASHCODE_GETAL1, HASHCODE_GETAL2).append(this.getID()).hashCode();
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
