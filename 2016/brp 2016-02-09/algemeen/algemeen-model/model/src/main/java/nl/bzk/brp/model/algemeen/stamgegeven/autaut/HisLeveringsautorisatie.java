/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.PopulatiebeperkingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OnbeperkteOmschrijvingAttribuut;
import nl.bzk.brp.model.basis.BestaansperiodeFormeelImplicietMaterieelAutaut;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.operationeel.kern.ActieModel;

/**
 *
 *
 */
@Entity
@Table(schema = "AutAut", name = "His_Levsautorisatie")
@Access(value = AccessType.FIELD)
public class HisLeveringsautorisatie extends AbstractHisLeveringsautorisatie implements ModelIdentificeerbaar<Integer>,
    BestaansperiodeFormeelImplicietMaterieelAutaut
{

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected HisLeveringsautorisatie() {
        super();
    }

    /**
     * Constructor voor het initialiseren van alle attributen. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param leveringsautorisatie leveringsautorisatie van HisLeveringsautorisatie
     * @param naam naam van HisLeveringsautorisatie
     * @param protocolleringsniveau protocolleringsniveau van HisLeveringsautorisatie
     * @param indicatieAliasSoortAdministratieveHandelingLeveren indicatieAliasSoortAdministratieveHandelingLeveren van
     *            HisLeveringsautorisatie
     * @param datumIngang datumIngang van HisLeveringsautorisatie
     * @param datumEinde datumEinde van HisLeveringsautorisatie
     * @param populatiebeperking populatiebeperking van HisLeveringsautorisatie
     * @param indicatiePopulatiebeperkingVolledigGeconverteerd indicatiePopulatiebeperkingVolledigGeconverteerd van
     *            HisLeveringsautorisatie
     * @param toelichting toelichting van HisLeveringsautorisatie
     * @param indicatieGeblokkeerd indicatieGeblokkeerd van HisLeveringsautorisatie
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     */
    public HisLeveringsautorisatie(
        final Leveringsautorisatie leveringsautorisatie,
        final NaamEnumeratiewaardeAttribuut naam,
        final ProtocolleringsniveauAttribuut protocolleringsniveau,
        final JaNeeAttribuut indicatieAliasSoortAdministratieveHandelingLeveren,
        final DatumAttribuut datumIngang,
        final DatumAttribuut datumEinde,
        final PopulatiebeperkingAttribuut populatiebeperking,
        final NeeAttribuut indicatiePopulatiebeperkingVolledigGeconverteerd,
        final OnbeperkteOmschrijvingAttribuut toelichting,
        final JaAttribuut indicatieGeblokkeerd,
        final ActieModel actieInhoud)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        super(leveringsautorisatie,
              naam,
              protocolleringsniveau,
              indicatieAliasSoortAdministratieveHandelingLeveren,
              datumIngang,
              datumEinde,
              populatiebeperking,
              indicatiePopulatiebeperkingVolledigGeconverteerd,
              toelichting,
              indicatieGeblokkeerd,
              actieInhoud);
    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public HisLeveringsautorisatie(final AbstractHisLeveringsautorisatie kopie) {
        super(kopie);
    }

}
