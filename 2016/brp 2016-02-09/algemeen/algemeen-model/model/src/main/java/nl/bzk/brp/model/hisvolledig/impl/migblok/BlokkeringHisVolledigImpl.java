/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.migblok;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3GemeenteCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.ProcessInstantieIDAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.migblok.RedenBlokkeringAttribuut;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.migblok.BlokkeringHisVolledig;

/**
 * HisVolledig klasse voor Blokkering.
 *
 */
@Entity
@Table(schema = "MigBlok", name = "Blokkering")
public class BlokkeringHisVolledigImpl extends AbstractBlokkeringHisVolledigImpl implements HisVolledigImpl,
        BlokkeringHisVolledig
{

    /**
     * Default contructor voor JPA.
     *
     */
    protected BlokkeringHisVolledigImpl() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param administratienummer administratienummer van Blokkering.
     * @param redenBlokkering redenBlokkering van Blokkering.
     * @param processInstantieID processInstantieID van Blokkering.
     * @param lO3GemeenteVestiging lO3GemeenteVestiging van Blokkering.
     * @param lO3GemeenteRegistratie lO3GemeenteRegistratie van Blokkering.
     * @param tijdstipRegistratie tijdstipRegistratie van Blokkering.
     */
    public BlokkeringHisVolledigImpl(
        final AdministratienummerAttribuut administratienummer,
        final RedenBlokkeringAttribuut redenBlokkering,
        final ProcessInstantieIDAttribuut processInstantieID,
        final LO3GemeenteCodeAttribuut lO3GemeenteVestiging,
        final LO3GemeenteCodeAttribuut lO3GemeenteRegistratie,
        final DatumTijdAttribuut tijdstipRegistratie)
    {
        super(administratienummer, redenBlokkering, processInstantieID, lO3GemeenteVestiging, lO3GemeenteRegistratie, tijdstipRegistratie);
    }

}
