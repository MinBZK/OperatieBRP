/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AutoriteitVanAfgifteReisdocumentCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReisdocumentNummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AanduidingInhoudingVermissingReisdocumentAttribuut;
import nl.bzk.brp.model.hisvolledig.kern.PersoonReisdocumentHisVolledig;
import nl.bzk.brp.model.logisch.kern.HisPersoonReisdocumentStandaardGroep;
import nl.bzk.brp.model.logisch.kern.PersoonReisdocumentStandaardGroep;


/**
 *
 *
 */
@Entity
@Table(schema = "Kern", name = "His_PersReisdoc")
public class HisPersoonReisdocumentModel extends AbstractHisPersoonReisdocumentModel implements
    HisPersoonReisdocumentStandaardGroep
{

    /**
     * Default constructor t.b.v. JPA
     */
    protected HisPersoonReisdocumentModel() {
        super();
    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param persoonReisdocumentHisVolledig instantie van A-laag klasse.
     * @param groep                          groep
     * @param actieInhoud                    actie inhoud
     */
    public HisPersoonReisdocumentModel(final PersoonReisdocumentHisVolledig persoonReisdocumentHisVolledig,
        final PersoonReisdocumentStandaardGroep groep, final ActieModel actieInhoud)
    {
        super(persoonReisdocumentHisVolledig, groep, actieInhoud);
    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public HisPersoonReisdocumentModel(final AbstractHisPersoonReisdocumentModel kopie) {
        super(kopie);
    }

    /**
     * CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param persoonReisdocument           persoonReisdocument van HisPersoonReisdocumentModel
     * @param nummer                        nummer van HisPersoonReisdocumentModel
     * @param autoriteitVanAfgifte          autoriteitVanAfgifte van HisPersoonReisdocumentModel
     * @param datumAanvangGeldigheidGegevensReisdocument
     *                                      datumAanvangGeldigheidGegevensReisdocument van HisPersoonReisdocumentModel
     * @param datumEindeDocument            datumEindeDocument van HisPersoonReisdocumentModel
     * @param datumUitgifte                 datumUitgifte van HisPersoonReisdocumentModel
     * @param datumInhoudingVermissing      datumInhoudingVermissing van HisPersoonReisdocumentModel
     * @param aanduidingInhoudingVermissing aanduidingInhoudingVermissing van HisPersoonReisdocumentModel
     * @param actieInhoud                   Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public HisPersoonReisdocumentModel(final PersoonReisdocumentHisVolledig persoonReisdocument,
        final ReisdocumentNummerAttribuut nummer,
        final AutoriteitVanAfgifteReisdocumentCodeAttribuut autoriteitVanAfgifte,
        final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheidGegevensReisdocument,
        final DatumEvtDeelsOnbekendAttribuut datumEindeDocument,
        final DatumEvtDeelsOnbekendAttribuut datumUitgifte,
        final DatumEvtDeelsOnbekendAttribuut datumInhoudingVermissing,
        final AanduidingInhoudingVermissingReisdocumentAttribuut aanduidingInhoudingVermissing,
        final ActieModel actieInhoud)
    {
        super(persoonReisdocument, nummer, autoriteitVanAfgifte,
            datumAanvangGeldigheidGegevensReisdocument, datumEindeDocument, datumUitgifte,
            datumInhoudingVermissing, aanduidingInhoudingVermissing, actieInhoud);
    }

    /**
     * @return de objectsleutel
     */
    @Transient
    public final String getObjectSleutel() {
        return getPersoonReisdocument().getID().toString();
    }
}
