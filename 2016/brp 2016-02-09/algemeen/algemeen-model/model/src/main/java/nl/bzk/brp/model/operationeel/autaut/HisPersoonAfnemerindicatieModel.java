/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.autaut;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.autaut.PersoonAfnemerindicatieHisVolledig;
import nl.bzk.brp.model.logisch.autaut.HisPersoonAfnemerindicatieStandaardGroep;
import nl.bzk.brp.model.logisch.autaut.PersoonAfnemerindicatieStandaardGroep;


/**
 *
 *
 */
@Entity
@Table(schema = "AutAut", name = "His_PersAfnemerindicatie")
public class HisPersoonAfnemerindicatieModel extends AbstractHisPersoonAfnemerindicatieModel implements
    ModelIdentificeerbaar<Long>, HisPersoonAfnemerindicatieStandaardGroep
{

    /**
     * Default constructor t.b.v. JPA
     */
    protected HisPersoonAfnemerindicatieModel() {
        super();
    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param persoonAfnemerindicatieHisVolledig
     *                     instantie van A-laag klasse.
     * @param groep        groep
     * @param dienstInhoud Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public HisPersoonAfnemerindicatieModel(final PersoonAfnemerindicatieHisVolledig persoonAfnemerindicatieHisVolledig,
        final PersoonAfnemerindicatieStandaardGroep groep,
        final Dienst dienstInhoud, final DatumTijdAttribuut tijdstipRegistratie)
    {
        super(persoonAfnemerindicatieHisVolledig, groep, dienstInhoud, tijdstipRegistratie);
    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public HisPersoonAfnemerindicatieModel(final AbstractHisPersoonAfnemerindicatieModel kopie) {
        super(kopie);
    }

    /**
     * @param persoonAfnemerindicatie      persoonAfnemerindicatie van HisPersoonAfnemerindicatieModel
     * @param datumAanvangMaterielePeriode datumAanvangMaterielePeriode van HisPersoonAfnemerindicatieModel
     * @param datumEindeVolgen             datumEindeVolgen van HisPersoonAfnemerindicatieModel
     * @param dienstInhoud                 Actie inhoud; de actie die leidt tot dit nieuwe record
     */
    public HisPersoonAfnemerindicatieModel(final PersoonAfnemerindicatieHisVolledig persoonAfnemerindicatie,
        final DatumEvtDeelsOnbekendAttribuut datumAanvangMaterielePeriode,
        final DatumAttribuut datumEindeVolgen, final Dienst dienstInhoud,
        final DatumTijdAttribuut tijdstipRegistratie)
    {
        super(persoonAfnemerindicatie, datumAanvangMaterielePeriode, datumEindeVolgen, dienstInhoud,
            tijdstipRegistratie);
    }
}
