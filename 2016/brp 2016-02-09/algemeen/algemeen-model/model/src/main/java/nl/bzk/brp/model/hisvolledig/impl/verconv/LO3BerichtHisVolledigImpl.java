/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.verconv;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ByteaopslagAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ChecksumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.verconv.LO3ReferentieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3BerichtenBronAttribuut;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.verconv.LO3BerichtHisVolledig;

/**
 * HisVolledig klasse voor LO3 Bericht.
 *
 */
@Entity
@Table(schema = "VerConv", name = "LO3Ber")
public class LO3BerichtHisVolledigImpl extends AbstractLO3BerichtHisVolledigImpl implements HisVolledigImpl,
        LO3BerichtHisVolledig
{

    /**
     * Default contructor voor JPA.
     *
     */
    protected LO3BerichtHisVolledigImpl() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param indicatieBerichtsoortOnderdeelLO3Stelsel indicatieBerichtsoortOnderdeelLO3Stelsel van LO3 Bericht.
     * @param referentie referentie van LO3 Bericht.
     * @param bron bron van LO3 Bericht.
     * @param administratienummer administratienummer van LO3 Bericht.
     * @param persoon persoon van LO3 Bericht.
     * @param berichtdata berichtdata van LO3 Bericht.
     * @param checksum checksum van LO3 Bericht.
     */
    public LO3BerichtHisVolledigImpl(
        final JaNeeAttribuut indicatieBerichtsoortOnderdeelLO3Stelsel,
        final LO3ReferentieAttribuut referentie,
        final LO3BerichtenBronAttribuut bron,
        final AdministratienummerAttribuut administratienummer,
        final PersoonHisVolledigImpl persoon,
        final ByteaopslagAttribuut berichtdata,
        final ChecksumAttribuut checksum)
    {
        super(indicatieBerichtsoortOnderdeelLO3Stelsel, referentie, bron, administratienummer, persoon, berichtdata, checksum);
    }

}
