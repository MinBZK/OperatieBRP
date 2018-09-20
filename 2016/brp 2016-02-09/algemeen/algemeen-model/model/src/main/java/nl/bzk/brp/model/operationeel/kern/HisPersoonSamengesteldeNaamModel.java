/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GeslachtsnaamstamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornamenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoorvoegselAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitelAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PredicaatAttribuut;
import nl.bzk.brp.model.basis.MaterieleHistorie;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.kern.HisPersoonSamengesteldeNaamGroep;
import nl.bzk.brp.model.logisch.kern.PersoonSamengesteldeNaamGroep;


/**
 *
 *
 */
@Entity
@Table(schema = "Kern", name = "His_PersSamengesteldeNaam")
public class HisPersoonSamengesteldeNaamModel extends AbstractHisPersoonSamengesteldeNaamModel implements
    HisPersoonSamengesteldeNaamGroep
{

    /**
     * Default constructor t.b.v. JPA
     */
    protected HisPersoonSamengesteldeNaamModel() {
        super();
    }

    /**
     * Copy Constructor die op basis van een interface een C/D laag klasse construeert.
     *
     * @param persoonHisVolledig instantie van A-laag klasse.
     * @param groep              groep
     * @param historie           historie
     * @param actieInhoud        actie inhoud
     */
    public HisPersoonSamengesteldeNaamModel(final PersoonHisVolledig persoonHisVolledig,
        final PersoonSamengesteldeNaamGroep groep, final MaterieleHistorie historie, final ActieModel actieInhoud)
    {
        super(persoonHisVolledig, groep, historie, actieInhoud);
    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie instantie van A-laag klasse.
     */
    public HisPersoonSamengesteldeNaamModel(final AbstractHisPersoonSamengesteldeNaamModel kopie) {
        super(kopie);
    }

    /**
     * His Persoon Samengestelde Naam Model. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param persoon                       persoon van HisPersoonSamengesteldeNaamModel
     * @param indicatieAlgoritmischAfgeleid indicatieAlgoritmischAfgeleid van HisPersoonSamengesteldeNaamModel
     * @param indicatieNamenreeks           indicatieNamenreeks van HisPersoonSamengesteldeNaamModel
     * @param predicaat                     predicaat van HisPersoonSamengesteldeNaamModel
     * @param voornamen                     voornamen van HisPersoonSamengesteldeNaamModel
     * @param adellijkeTitel                adellijkeTitel van HisPersoonSamengesteldeNaamModel
     * @param voorvoegsel                   voorvoegsel van HisPersoonSamengesteldeNaamModel
     * @param scheidingsteken               scheidingsteken van HisPersoonSamengesteldeNaamModel
     * @param geslachtsnaam                 geslachtsnaam van HisPersoonSamengesteldeNaamModel
     * @param actieInhoud                   Actie inhoud; de actie die leidt tot dit nieuwe record.
     * @param historie                      De groep uit een bericht
     */
    public HisPersoonSamengesteldeNaamModel(final PersoonHisVolledig persoon,
        final JaNeeAttribuut indicatieAlgoritmischAfgeleid, final JaNeeAttribuut indicatieNamenreeks,
        final PredicaatAttribuut predicaat, final VoornamenAttribuut voornamen,
        final AdellijkeTitelAttribuut adellijkeTitel, final VoorvoegselAttribuut voorvoegsel,
        final ScheidingstekenAttribuut scheidingsteken, final GeslachtsnaamstamAttribuut geslachtsnaamstam,
        final ActieModel actieInhoud, final MaterieleHistorie historie)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        super(persoon, indicatieAlgoritmischAfgeleid, indicatieNamenreeks, predicaat, voornamen, adellijkeTitel,
            voorvoegsel, scheidingsteken, geslachtsnaamstam, actieInhoud, historie);
    }
}
