/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.MaterieleHistorie;
import nl.bzk.brp.model.logisch.kern.Actie;
import org.apache.commons.lang.builder.EqualsBuilder;


/**
 * Kleinste eenheid van gegevensbewerking in de BRP.
 * <p/>
 * Het bijhouden van de BRP geschiedt door het verwerken van administratieve handelingen. Deze administratieve handelingen vallen uiteen in ��n of meer
 * 'eenheden' van gegevensbewerkingen.
 */
@Entity
@Table(schema = "Kern", name = "Actie")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "iD", scope = ActieModel.class)
public class ActieModel extends AbstractActieModel implements Actie, MaterieleHistorie {

    @Transient
    private boolean magGeleverdWorden;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected ActieModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param soort                    soort van Actie.
     * @param administratieveHandeling administratieveHandeling van Actie.
     * @param partij                   partij van Actie.
     * @param datumAanvangGeldigheid   datumAanvangGeldigheid van Actie.
     * @param datumEindeGeldigheid     datumEindeGeldigheid van Actie.
     * @param tijdstipRegistratie      tijdstipRegistratie van Actie.
     * @param datumOntlening           datumOntlening van de Actie.
     */
    public ActieModel(final SoortActieAttribuut soort, final AdministratieveHandelingModel administratieveHandeling,
        final PartijAttribuut partij, final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid,
        final DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid, final DatumTijdAttribuut tijdstipRegistratie,
        final DatumEvtDeelsOnbekendAttribuut datumOntlening)
    {
        super(soort, administratieveHandeling, partij, datumAanvangGeldigheid, datumEindeGeldigheid,
            tijdstipRegistratie, datumOntlening);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param actie                    Te kopieren object type.
     * @param administratieveHandeling Bijbehorende Administratieve handeling.
     */
    public ActieModel(final Actie actie, final AdministratieveHandelingModel administratieveHandeling) {
        super(actie, administratieveHandeling);
    }

    @Override
    @Transient
    public RootObject getRootObject() {
        throw new UnsupportedOperationException("Java operationeel Model ondersteunt geen root objecten.");
    }

    @Override
    public DatumTijdAttribuut getDatumTijdVerval() {
        // Een actie is altijd voor het maken van nieuwe records, dus geen datum tijd verval.
        return null;
    }

    @Override
    public int hashCode() {
        if (getID() != null) {
            return super.getID().hashCode();
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        final boolean isGelijk;

        if (obj == null || obj.getClass() != getClass()) {
            isGelijk = false;
        } else if (this == obj) {
            isGelijk = true;
        } else {
            final ActieModel ander = (ActieModel) obj;
            isGelijk = new EqualsBuilder().append(this.getID(), ander.getID()).isEquals();
        }

        return isGelijk;
    }

    /**
     * Retourneert de objectsleutel tbv marshalling naar xml. (jibx)
     *
     * @return de object sleutel.
     */
    public String getObjectSleutel() {
        return getID().toString();
    }

    /**
     * Mag deze actie geleverd worden.
     *
     * @return true als deze geleverd mag worden, anders false.
     */
    public boolean isMagGeleverdWorden() {
        final boolean heeftMinimaalEenAttribuutDatGeleverdMagWorden = heeftMinimaal1AttribuutDatGeleverdMagWorden();

        return magGeleverdWorden && heeftMinimaalEenAttribuutDatGeleverdMagWorden;
    }

    /**
     * Controleert of deze actie minimaal 1 attribuut heeft dat geleverd mag worden.
     *
     * @return True als er minimaal 1 attribuut is dat geleverd mag worden, anders false.
     */
    public boolean heeftMinimaal1AttribuutDatGeleverdMagWorden() {
        return attribuutIsNietNullEnMagGeleverdWorden(getPartij())
            || attribuutIsNietNullEnMagGeleverdWorden(getSoort())
            || attribuutIsNietNullEnMagGeleverdWorden(getTijdstipRegistratie())
            || attribuutIsNietNullEnMagGeleverdWorden(getDatumTijdVerval())
            || attribuutIsNietNullEnMagGeleverdWorden(getDatumAanvangGeldigheid())
            || attribuutIsNietNullEnMagGeleverdWorden(getDatumEindeGeldigheid())
            || attribuutIsNietNullEnMagGeleverdWorden(getDatumOntlening());
    }

    /**
     * Controleert of een attribuut niet null is en geleverd mag worden.
     *
     * @param attribuut Het attribuut.
     * @return True als er geleverd mag worden, false als dat niet het geval is of het attribuut null is.
     */
    private boolean attribuutIsNietNullEnMagGeleverdWorden(final Attribuut attribuut) {
        if (attribuut != null) {
            return attribuut.isMagGeleverdWorden();
        }
        return false;
    }

    /**
     * Zet alle attributen op mag geleverd worden.
     */
    public final void zetAlleAttributenOpMagGeleverdWorden() {
        zetMagGeleverdWorden(getPartij());
        zetMagGeleverdWorden(getSoort());
        zetMagGeleverdWorden(getTijdstipRegistratie());
        zetMagGeleverdWorden(getDatumTijdVerval());
        zetMagGeleverdWorden(getDatumAanvangGeldigheid());
        zetMagGeleverdWorden(getDatumEindeGeldigheid());
        zetMagGeleverdWorden(getDatumOntlening());
    }

    /**
     * Zet op mag geleverd worden
     *
     * @param attribuut
     */
    private void zetMagGeleverdWorden(final Attribuut attribuut) {
        if (attribuut != null) {
            attribuut.setMagGeleverdWorden(true);
        }
    }

    /**
     * Plaats de vlag of de actie geleverd mag worden.
     *
     * @param magGeleverdWorden true als mag geleverd worden, anders false
     */
    public void setMagGeleverdWorden(final boolean magGeleverdWorden) {
        this.magGeleverdWorden = magGeleverdWorden;
    }

}
