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
@Table(schema = "AutAut", name = "His_Dienstbundel")
@Access(value = AccessType.FIELD)
public class HisDienstbundel extends AbstractHisDienstbundel implements ModelIdentificeerbaar<Integer>, BestaansperiodeFormeelImplicietMaterieelAutaut {

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected HisDienstbundel() {
        super();
    }

    /**
     * Constructor voor het initialiseren van alle attributen. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param dienstbundel dienstbundel van HisDienstbundel
     * @param naam naam van HisDienstbundel
     * @param datumIngang datumIngang van HisDienstbundel
     * @param datumEinde datumEinde van HisDienstbundel
     * @param naderePopulatiebeperking naderePopulatiebeperking van HisDienstbundel
     * @param indicatieNaderePopulatiebeperkingVolledigGeconverteerd
     *            indicatieNaderePopulatiebeperkingVolledigGeconverteerd van HisDienstbundel
     * @param toelichting toelichting van HisDienstbundel
     * @param indicatieGeblokkeerd indicatieGeblokkeerd van HisDienstbundel
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     */
    public HisDienstbundel(
        final Dienstbundel dienstbundel,
        final NaamEnumeratiewaardeAttribuut naam,
        final DatumAttribuut datumIngang,
        final DatumAttribuut datumEinde,
        final PopulatiebeperkingAttribuut naderePopulatiebeperking,
        final NeeAttribuut indicatieNaderePopulatiebeperkingVolledigGeconverteerd,
        final OnbeperkteOmschrijvingAttribuut toelichting,
        final JaAttribuut indicatieGeblokkeerd,
        final ActieModel actieInhoud)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        super(dienstbundel,
              naam,
              datumIngang,
              datumEinde,
              naderePopulatiebeperking,
              indicatieNaderePopulatiebeperkingVolledigGeconverteerd,
              toelichting,
              indicatieGeblokkeerd,
              actieInhoud);
    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public HisDienstbundel(final AbstractHisDienstbundel kopie) {
        super(kopie);
    }

}
