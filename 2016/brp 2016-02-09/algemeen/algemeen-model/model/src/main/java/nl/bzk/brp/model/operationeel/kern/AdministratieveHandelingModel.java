/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OntleningstoelichtingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.logisch.kern.AdministratieveHandeling;
import nl.bzk.brp.model.logisch.kern.AdministratieveHandelingStandaardGroep;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 * Een door het bijhoudingsorgaan ge�nitieerde activiteit in de BRP, waarmee persoonsgegevens worden bijgehouden.
 * <p/>
 * De binnen de BRP geadministreerde persoonsgegevens worden bijgehouden doordat wijzigingen worden doorgevoerd vanuit de gemeentelijke of ministeri�le
 * verantwoordelijkheid. Het initiatief gegevens te wijzigen komt vanuit het betreffende bijhoudingsorgaan; deze stuurt daartoe een bericht aan de BRP die
 * de daadwerkelijke bijhouding doet plaatsvinden. Voor de verwerking binnen de BRP wordt dit bericht uiteen gerafeld in ��n of meer Acties. Het geheel aan
 * acties wordt de administratieve handeling genoemd; dit is in de BRP de weerslag van wat in termen van de burgerzakenmodule 'de zaak' zal zijn geweest.
 * Qua niveau staat het op hetzelfde niveau als het bericht; het verschil bestaat eruit dat het bericht het vehikel is waarmee de administratieve handeling
 * wordt bewerkstelligt.
 */
@Entity
@Table(schema = "Kern", name = "AdmHnd")
public class AdministratieveHandelingModel extends AbstractAdministratieveHandelingModel implements
    AdministratieveHandeling
{

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "administratieveHandeling")
    @Fetch(value = FetchMode.SELECT)
    private Set<HisPersoonAfgeleidAdministratiefModel> hisPersoonAfgeleidAdministratief = new HashSet<>();

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected AdministratieveHandelingModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param soort                soort van Administratieve handeling.
     * @param partij               partij van Administratieve handeling.
     * @param toelichtingOntlening toelichtingOntlening van Administratieve handeling.
     * @param tijdstipRegistratie  tijdstipRegistratie van Administratieve handeling.
     */
    public AdministratieveHandelingModel(final SoortAdministratieveHandelingAttribuut soort,
        final PartijAttribuut partij,
        final OntleningstoelichtingAttribuut toelichtingOntlening,
        final DatumTijdAttribuut tijdstipRegistratie)
    {
        super(soort, partij, toelichtingOntlening, tijdstipRegistratie);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param administratieveHandeling Te kopieren object type.
     */
    public AdministratieveHandelingModel(final AdministratieveHandeling administratieveHandeling) {
        super(administratieveHandeling);
    }

    /**
     * Geef de ID terug voor de object sleutel.
     *
     * @return de object sleutel
     */
    public final String getObjectSleutel() {
        if (this.getID() == null) {
            return "";
        } else {
            return this.getID().toString();
        }
    }

    /**
     * Bidirectionele relatie naar de HisPersoonAfgeleidAdministratiefModel.
     * @return
     */
    public final Set<HisPersoonAfgeleidAdministratiefModel> getHisPersoonAfgeleidAdministratief() {
        return hisPersoonAfgeleidAdministratief;
    }

    @Override
    public final int hashCode() {
        if (getID() != null) {
            return super.getID().hashCode();
        }
        return super.hashCode();
    }

    @Override
    public final boolean equals(final Object obj) {
        final boolean isGelijk;

        if (obj == null || obj.getClass() != getClass()) {
            isGelijk = false;
        } else if (this == obj) {
            isGelijk = true;
        } else {
            final AdministratieveHandelingModel ander = (AdministratieveHandelingModel) obj;
            isGelijk = new EqualsBuilder().append(this.getID(), ander.getID()).isEquals();
        }

        return isGelijk;
    }


    @Override
    public final AdministratieveHandelingStandaardGroep getStandaard() {
        return null;
    }
}
