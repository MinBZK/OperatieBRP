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

    boolean behandeldAlsNederlander();

    Object belemmeringVerstrekkingReisdocument();

    Object bezitBuitenlandsReisdocument();

    boolean derdeHeeftGezag();

    boolean gepriviligeerde();

    boolean onderCuratele();

    boolean vastgesteldNietNederlander();

    boolean verstrekkingsBeperking();
}
