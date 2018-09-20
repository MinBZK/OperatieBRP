/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ontleningstoelichting;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.logisch.kern.AdministratieveHandeling;
import nl.bzk.brp.model.operationeel.kern.basis.AbstractAdministratieveHandelingModel;


/**
 * Een door het bijhoudingsorgaan ge�nitieerde activiteit in de BRP, waarmee persoonsgegevens worden bijgehouden.
 * <p/>
 * De binnen de BRP geadministreerde persoonsgegevens worden bijgehouden doordat wijzigingen worden doorgevoerd vanuit
 * de gemeentelijke of ministeri�le verantwoordelijkheid. Het initiatief gegevens te wijzigen komt vanuit het
 * betreffende bijhoudingsorgaan; deze stuurt daartoe een bericht aan de BRP die de daadwerkelijke bijhouding doet
 * plaatsvinden. Voor de verwerking binnen de BRP wordt dit bericht uiteen gerafeld in ��n of meer Acties. Het geheel
 * aan acties wordt de administratieve handeling genoemd; dit is in de BRP de weerslag van wat in termen van de
 * burgerzakenmodule 'de zaak' zal zijn geweest. Qua niveau staat het op hetzelfde niveau als het bericht; het verschil
 * bestaat eruit dat het bericht het vehikel is waarmee de administratieve handeling wordt bewerkstelligt.
 * <p/>
 * <p/>
 * <p/>
 * Generator: nl.bzk.brp.generatoren.java.OperationeelModelGenerator.
 * Metaregister versie: 1.1.8.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2012-11-27 12:02:51.
 * Gegenereerd op: Tue Nov 27 14:55:35 CET 2012.
 */
@Entity
@Table(schema = "Kern", name = "AdmHnd")
public class AdministratieveHandelingModel extends AbstractAdministratieveHandelingModel implements
        AdministratieveHandeling
{


    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected AdministratieveHandelingModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert.
     *
     * @param soort                soort van Administratieve handeling.
     * @param partij               partij van Administratieve handeling.
     * @param tijdstipOntlening    tijdstipOntlening van Administratieve handeling.
     * @param toelichtingOntlening toelichtingOntlening van Administratieve handeling.
     */
    public AdministratieveHandelingModel(final SoortAdministratieveHandeling soort, final Partij partij,
                                         final DatumTijd tijdstipOntlening,
                                         final Ontleningstoelichting toelichtingOntlening,
                                         final DatumTijd tijdstipRegistratie)
    {
        super(soort, partij, tijdstipOntlening, toelichtingOntlening, tijdstipRegistratie);
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
     * Geef de ID terug voor de technische sleutel
     *
     * @return
     */
    public String getTechnischeSleutel() {
        if (this.getID() == null) {
            return "";
        } else {
            return this.getID().toString();
        }
    }

    /**
     * Nodig voor JIBX
     *
     * @param technischeSleutel
     */
    private void setTechnischeSleutel(final String technischeSleutel) {
    }
}
