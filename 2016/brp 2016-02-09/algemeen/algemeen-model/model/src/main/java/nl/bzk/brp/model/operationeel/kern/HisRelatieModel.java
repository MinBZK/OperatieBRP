/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandsePlaatsAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandseRegioAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieomschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenEindeRelatieAttribuut;
import nl.bzk.brp.model.annotaties.GroepAccessor;
import nl.bzk.brp.model.basis.BestaansperiodeFormeelImplicietMaterieel;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig;
import nl.bzk.brp.model.logisch.kern.HisRelatieStandaardGroep;
import nl.bzk.brp.model.logisch.kern.RelatieStandaardGroep;

/**
 *
 *
 */
@Entity
@Table(schema = "Kern", name = "His_Relatie")
@Access(value = AccessType.FIELD)
@GroepAccessor(dbObjectId = 3599)
public class HisRelatieModel extends AbstractHisRelatieModel implements HisRelatieStandaardGroep, ModelIdentificeerbaar<Integer>,
        BestaansperiodeFormeelImplicietMaterieel, ElementIdentificeerbaar
{

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected HisRelatieModel() {
        super();
    }

    /**
     * Constructor voor het initialiseren van alle attributen. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param relatie relatie van HisRelatieModel
     * @param datumAanvang datumAanvang van HisRelatieModel
     * @param gemeenteAanvang gemeenteAanvang van HisRelatieModel
     * @param woonplaatsnaamAanvang woonplaatsnaamAanvang van HisRelatieModel
     * @param buitenlandsePlaatsAanvang buitenlandsePlaatsAanvang van HisRelatieModel
     * @param buitenlandseRegioAanvang buitenlandseRegioAanvang van HisRelatieModel
     * @param omschrijvingLocatieAanvang omschrijvingLocatieAanvang van HisRelatieModel
     * @param landGebiedAanvang landGebiedAanvang van HisRelatieModel
     * @param redenEinde redenEinde van HisRelatieModel
     * @param datumEinde datumEinde van HisRelatieModel
     * @param gemeenteEinde gemeenteEinde van HisRelatieModel
     * @param woonplaatsnaamEinde woonplaatsnaamEinde van HisRelatieModel
     * @param buitenlandsePlaatsEinde buitenlandsePlaatsEinde van HisRelatieModel
     * @param buitenlandseRegioEinde buitenlandseRegioEinde van HisRelatieModel
     * @param omschrijvingLocatieEinde omschrijvingLocatieEinde van HisRelatieModel
     * @param landGebiedEinde landGebiedEinde van HisRelatieModel
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     */
    public HisRelatieModel(
        final RelatieHisVolledig relatie,
        final DatumEvtDeelsOnbekendAttribuut datumAanvang,
        final GemeenteAttribuut gemeenteAanvang,
        final NaamEnumeratiewaardeAttribuut woonplaatsnaamAanvang,
        final BuitenlandsePlaatsAttribuut buitenlandsePlaatsAanvang,
        final BuitenlandseRegioAttribuut buitenlandseRegioAanvang,
        final LocatieomschrijvingAttribuut omschrijvingLocatieAanvang,
        final LandGebiedAttribuut landGebiedAanvang,
        final RedenEindeRelatieAttribuut redenEinde,
        final DatumEvtDeelsOnbekendAttribuut datumEinde,
        final GemeenteAttribuut gemeenteEinde,
        final NaamEnumeratiewaardeAttribuut woonplaatsnaamEinde,
        final BuitenlandsePlaatsAttribuut buitenlandsePlaatsEinde,
        final BuitenlandseRegioAttribuut buitenlandseRegioEinde,
        final LocatieomschrijvingAttribuut omschrijvingLocatieEinde,
        final LandGebiedAttribuut landGebiedEinde,
        final ActieModel actieInhoud)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        super(relatie,
              datumAanvang,
              gemeenteAanvang,
              woonplaatsnaamAanvang,
              buitenlandsePlaatsAanvang,
              buitenlandseRegioAanvang,
              omschrijvingLocatieAanvang,
              landGebiedAanvang,
              redenEinde,
              datumEinde,
              gemeenteEinde,
              woonplaatsnaamEinde,
              buitenlandsePlaatsEinde,
              buitenlandseRegioEinde,
              omschrijvingLocatieEinde,
              landGebiedEinde,
              actieInhoud);
    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public HisRelatieModel(final AbstractHisRelatieModel kopie) {
        super(kopie);
    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param relatieHisVolledig instantie van A-laag klasse.
     * @param groep groep
     * @param actieInhoud Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public HisRelatieModel(final RelatieHisVolledig relatieHisVolledig, final RelatieStandaardGroep groep, final ActieModel actieInhoud) {
        super(relatieHisVolledig, groep, actieInhoud);
    }

}
