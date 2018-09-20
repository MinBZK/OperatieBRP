/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.migratie;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegel;
import nl.bzk.brp.dataaccess.repository.PersoonAdresRepository;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.objecttype.bericht.PersoonAdresBericht;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;

/**
 * Bedrijfsregel die waarschuwt indien er reeds personen wonen op het nieuwe adres bij het verwerken van een
 * verhuizing.
 *
 * @brp.bedrijfsregel MR-05-02
 */
public class MR0502 implements BedrijfsRegel<Persoon> {

    @Inject
    private PersoonAdresRepository persoonAdresRepository;

    /** {@inheritDoc} */
    @Override
    public String getCode() {
        return "MR-05-02";
    }

    /** {@inheritDoc} */
    @Override
    public List<Melding> executeer(final Persoon huidigeSituatie, final Persoon nieuweSituatie,
                             final Datum datumAanvangGeldigheid)
    {
        List<Melding> meldingen = new ArrayList<Melding>();

        if (nieuweSituatie.getAdressen() != null && !nieuweSituatie.getAdressen().isEmpty()) {
            final PersoonAdresBericht nieuwAdres = (PersoonAdresBericht) nieuweSituatie.getAdressen().iterator().next();
            if (persoonAdresRepository.isIemandIngeschrevenOpAdres(nieuwAdres)) {
                meldingen.add(new Melding(SoortMelding.WAARSCHUWING, MeldingCode.MR0502
                        , nieuwAdres, "huisnummer"));
            }
        }
        return meldingen;
    }
}
