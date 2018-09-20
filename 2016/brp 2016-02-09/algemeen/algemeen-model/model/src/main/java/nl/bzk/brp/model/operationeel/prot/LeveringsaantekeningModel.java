/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.prot;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatieAttribuut;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.prot.Leveringsaantekening;

/**
 * De (voorgenomen) Levering van persoonsgegevens aan een Afnemer.
 *
 * Een Afnemer krijgt gegevens doordat er sprake is van een Abonnement. Vlak voordat de gegevens daadwerkelijk
 * afgeleverd gaan worden, wordt dit geprotocolleerd door een regel weg te schrijven in de Levering tabel.
 *
 * Voorheen was er een link tussen de uitgaande en eventueel inkomende (vraag) berichten. Omdat de bericht tabel
 * geschoond wordt, is deze afhankelijkheid niet wenselijk. Het is daarom ook van belang om alle informatie die
 * noodzakelijk is te kunnen voldoen aan de protocollering hier vast te leggen.
 *
 *
 *
 */
@Entity
@Table(schema = "Prot", name = "Levsaantek")
@Access(value = AccessType.FIELD)
public class LeveringsaantekeningModel extends AbstractLeveringsaantekeningModel implements Leveringsaantekening, ModelIdentificeerbaar<Long> {

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected LeveringsaantekeningModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft. CHECKSTYLE-BRP:OFF -
     * MAX PARAMS
     *
     * @param toegangLeveringsautorisatieId toegangLeveringsautorisatieId van Leveringsaantekening.
     * @param dienstId dienstId van Leveringsaantekening.
     * @param datumTijdKlaarzettenLevering datumTijdKlaarzettenLevering van Leveringsaantekening.
     * @param datumMaterieelSelectie datumMaterieelSelectie van Leveringsaantekening.
     * @param datumAanvangMaterielePeriodeResultaat datumAanvangMaterielePeriodeResultaat van Leveringsaantekening.
     * @param datumEindeMaterielePeriodeResultaat datumEindeMaterielePeriodeResultaat van Leveringsaantekening.
     * @param datumTijdAanvangFormelePeriodeResultaat datumTijdAanvangFormelePeriodeResultaat van Leveringsaantekening.
     * @param datumTijdEindeFormelePeriodeResultaat datumTijdEindeFormelePeriodeResultaat van Leveringsaantekening.
     * @param administratieveHandelingId administratieveHandelingId van Leveringsaantekening.
     * @param soortSynchronisatie soortSynchronisatie van Leveringsaantekening.
     */
    public LeveringsaantekeningModel(
        final Integer toegangLeveringsautorisatieId,
        final Integer dienstId,
        final DatumTijdAttribuut datumTijdKlaarzettenLevering,
        final DatumAttribuut datumMaterieelSelectie,
        final DatumAttribuut datumAanvangMaterielePeriodeResultaat,
        final DatumAttribuut datumEindeMaterielePeriodeResultaat,
        final DatumTijdAttribuut datumTijdAanvangFormelePeriodeResultaat,
        final DatumTijdAttribuut datumTijdEindeFormelePeriodeResultaat,
        final Long administratieveHandelingId,
        final SoortSynchronisatieAttribuut soortSynchronisatie)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        super(toegangLeveringsautorisatieId,
              dienstId,
              datumTijdKlaarzettenLevering,
              datumMaterieelSelectie,
              datumAanvangMaterielePeriodeResultaat,
              datumEindeMaterielePeriodeResultaat,
              datumTijdAanvangFormelePeriodeResultaat,
              datumTijdEindeFormelePeriodeResultaat,
              administratieveHandelingId,
              soortSynchronisatie);
    }

}
