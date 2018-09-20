/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Embeddable;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OnderzoekOmschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.StatusOnderzoekAttribuut;
import nl.bzk.brp.model.logisch.kern.OnderzoekStandaardGroep;


/**
 * Vorm van historie: formeel. Motivatie: 'onderzoek' is een construct om vast te leggen dat een bepaald gegeven onderwerp is van onderzoek. Hierbij is het
 * in principe alleen relevant of een gegeven NU in onderzoek is. Verder is het voldoende om te weten of tijdens een bepaalde levering een gegeven wel of
 * niet als 'in onderzoek' stond geregistreerd. NB: de gegevens over het onderzoek zelf staan niet in de BRP, maar in bijvoorbeeld de zaaksystemen. Omdat
 * formele historie dus volstaat, wordt de materi�le historie onderdrukt. RvdP 17 jan 2012.
 */
@Embeddable
public class OnderzoekStandaardGroepModel extends AbstractOnderzoekStandaardGroepModel implements
    OnderzoekStandaardGroep
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected OnderzoekStandaardGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param datumAanvang           datumAanvang van Standaard.
     * @param verwachteAfhandeldatum verwachteAfhandeldatum van Standaard.
     * @param datumEinde             datumEinde van Standaard.
     * @param omschrijving           omschrijving van Standaard.
     * @param status                 status van Standaard.
     */
    public OnderzoekStandaardGroepModel(final DatumEvtDeelsOnbekendAttribuut datumAanvang,
        final DatumEvtDeelsOnbekendAttribuut verwachteAfhandeldatum,
        final DatumEvtDeelsOnbekendAttribuut datumEinde, final OnderzoekOmschrijvingAttribuut omschrijving,
        final StatusOnderzoekAttribuut status)
    {
        super(datumAanvang, verwachteAfhandeldatum, datumEinde, omschrijving, status);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param onderzoekStandaardGroep te kopieren groep.
     */
    public OnderzoekStandaardGroepModel(final OnderzoekStandaardGroep onderzoekStandaardGroep) {
        super(onderzoekStandaardGroep);
    }

}
