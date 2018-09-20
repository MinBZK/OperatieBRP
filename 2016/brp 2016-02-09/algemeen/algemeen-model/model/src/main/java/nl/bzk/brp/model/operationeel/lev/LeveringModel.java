/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.lev;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatieAttribuut;
import nl.bzk.brp.model.logisch.lev.Levering;


/**
 * De (voorgenomen) Levering van persoonsgegevens aan een Afnemer.
 * <p/>
 * Een Afnemer krijgt gegevens doordat er sprake is van een Abonnement. Vlak voordat de gegevens daadwerkelijk afgeleverd gaan worden, wordt dit
 * geprotocolleerd door een regel weg te schrijven in de Levering tabel.
 */
@Entity
@Table(schema = "Prot", name = "levsaantek")
public class LeveringModel extends AbstractLeveringModel implements Levering {

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected LeveringModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param toegangAbonnementId          toegangAbonnementId van Levering.
     * @param dienstId                     dienstId van Levering.
     * @param datumTijdKlaarzettenLevering datumTijdKlaarzettenLevering van Levering.
     * @param datumMaterieelSelectie       datumMaterieelSelectie van Levering.
     * @param datumAanvangMaterielePeriodeResultaat
     *                                     datumAanvangMaterielePeriodeResultaat van Levering.
     * @param datumEindeMaterielePeriodeResultaat
     *                                     datumEindeMaterielePeriodeResultaat van Levering.
     * @param datumTijdAanvangFormelePeriodeResultaat
     *                                     datumTijdAanvangFormelePeriodeResultaat van Levering.
     * @param datumTijdEindeFormelePeriodeResultaat
     *                                     datumTijdEindeFormelePeriodeResultaat van Levering.
     * @param administratieveHandelingId   administratieveHandelingId van Levering.
     * @param soortSynchronisatie          soortSynchronisatie van Levering.
     */
    public LeveringModel(final Integer toegangAbonnementId, final Integer dienstId,
        final DatumTijdAttribuut datumTijdKlaarzettenLevering, final DatumAttribuut datumMaterieelSelectie,
        final DatumAttribuut datumAanvangMaterielePeriodeResultaat,
        final DatumAttribuut datumEindeMaterielePeriodeResultaat,
        final DatumTijdAttribuut datumTijdAanvangFormelePeriodeResultaat,
        final DatumTijdAttribuut datumTijdEindeFormelePeriodeResultaat, final Long administratieveHandelingId,
        final SoortSynchronisatieAttribuut soortSynchronisatie)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        super(toegangAbonnementId, dienstId, datumTijdKlaarzettenLevering, datumMaterieelSelectie,
            datumAanvangMaterielePeriodeResultaat, datumEindeMaterielePeriodeResultaat,
            datumTijdAanvangFormelePeriodeResultaat, datumTijdEindeFormelePeriodeResultaat,
            administratieveHandelingId, soortSynchronisatie);
    }
}
