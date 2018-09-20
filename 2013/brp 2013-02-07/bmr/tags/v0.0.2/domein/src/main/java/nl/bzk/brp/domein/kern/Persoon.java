/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 * Gegenereerd uit de BRP Metadata Repository.
 * Gegenereerd op : Wed Dec 14 17:39:23 CET 2011
 * Model : BRP/Kern (0.07)
 * Generator : DomeinModelGenerator
 */
package nl.bzk.brp.domein.kern;

import nl.bzk.brp.domein.kern.basis.BasisPersoon;


public interface Persoon extends BasisPersoon {

    Boolean getBehandeldAlsNederlander();

    Boolean getBelemmeringVerstrekkingReisdocument();

    Boolean getBezitBuitenlandsReisdocument();

    Boolean getDerdeHeeftGezag();

    Boolean getGepriviligeerde();

    Boolean getOnderCuratele();

    Boolean getVastgesteldNietNederlander();

    Boolean getVerstrekkingsBeperking();

    void setBehandeldAlsNederlander(final Boolean waarde);

    void setBelemmeringVerstrekkingReisdocument(final Boolean waarde);

    void setBezitBuitenlandsReisdocument(final Boolean waarde);

    void setDerdeHeeftGezag(final Boolean waarde);

    void setGepriviligeerde(final Boolean waarde);

    void setOnderCuratele(final Boolean waarde);

    void setVastgesteldNietNederlander(final Boolean waarde);

    void setVerstrekkingsBeperking(final Boolean waarde);
}
