/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl;

import javax.inject.Inject;

import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegel;
import nl.bzk.brp.dataaccess.repository.PersoonAdresRepository;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.logisch.PersoonAdres;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoon;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;

/**
 * Bedrijfsregel die waarschuwt indien er reeds personen wonen op het nieuwe adres bij het verwerken van een
 * verhuizing.
 *
 * @brp.bedrijfsregel MR-05-02
 */
public class MR0502 implements BedrijfsRegel<PersistentPersoon, Persoon> {

    @Inject
    private PersoonAdresRepository persoonAdresRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCode() {
        return "MR-05-02";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Melding executeer(final PersistentPersoon huidigeSituatie, final Persoon nieuweSituatie) {
        Melding melding = null;
        final PersoonAdres nieuwAdres = nieuweSituatie.getAdressen().iterator().next();
        if (persoonAdresRepository.isIemandIngeschrevenOpAdres(nieuwAdres)) {
            melding = new Melding(SoortMelding.WAARSCHUWING, MeldingCode.MR0502);
        }
        return melding;
    }
}
