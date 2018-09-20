/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.EmailadresAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GeslachtsnaamstamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.TelefoonnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornamenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoorvoegselAttribuut;
import nl.bzk.brp.model.annotaties.GroepAccessor;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.hisvolledig.kern.TerugmeldingHisVolledig;
import nl.bzk.brp.model.logisch.kern.HisTerugmeldingContactpersoonGroep;
import nl.bzk.brp.model.logisch.kern.TerugmeldingContactpersoonGroep;


/**
 *
 *
 */
@Entity
@Table(schema = "Kern", name = "His_TerugmeldingContactpers")
@GroepAccessor(dbObjectId = 11095)
public class HisTerugmeldingContactpersoonModel extends AbstractHisTerugmeldingContactpersoonModel implements
    ModelIdentificeerbaar<Integer>, HisTerugmeldingContactpersoonGroep
{

    /**
     * Default constructor t.b.v. JPA
     */
    protected HisTerugmeldingContactpersoonModel() {
        super();
    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param terugmeldingHisVolledig instantie van A-laag klasse.
     * @param groep                   groep
     * @param actieInhoud             Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public HisTerugmeldingContactpersoonModel(final TerugmeldingHisVolledig terugmeldingHisVolledig,
        final TerugmeldingContactpersoonGroep groep, final ActieModel actieInhoud)
    {
        super(terugmeldingHisVolledig, groep, actieInhoud);
    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public HisTerugmeldingContactpersoonModel(final AbstractHisTerugmeldingContactpersoonModel kopie) {
        super(kopie);
    }

    /**
     * CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param terugmelding      terugmelding van HisTerugmeldingContactpersoonModel
     * @param email             email van HisTerugmeldingContactpersoonModel
     * @param telefoonnummer    telefoonnummer van HisTerugmeldingContactpersoonModel
     * @param voornamen         voornamen van HisTerugmeldingContactpersoonModel
     * @param voorvoegsel       voorvoegsel van HisTerugmeldingContactpersoonModel
     * @param scheidingsteken   scheidingsteken van HisTerugmeldingContactpersoonModel
     * @param geslachtsnaamstam geslachtsnaamstam van HisTerugmeldingContactpersoonModel
     * @param actieInhoud       Actie inhoud; de actie die leidt tot dit nieuwe record.
     */
    public HisTerugmeldingContactpersoonModel(final TerugmeldingHisVolledig terugmelding,
        final EmailadresAttribuut email, final TelefoonnummerAttribuut telefoonnummer,
        final VoornamenAttribuut voornamen, final VoorvoegselAttribuut voorvoegsel,
        final ScheidingstekenAttribuut scheidingsteken, final GeslachtsnaamstamAttribuut geslachtsnaamstam,
        final ActieModel actieInhoud)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        super(terugmelding, email, telefoonnummer, voornamen, voorvoegsel, scheidingsteken, geslachtsnaamstam,
            actieInhoud);
    }

}
