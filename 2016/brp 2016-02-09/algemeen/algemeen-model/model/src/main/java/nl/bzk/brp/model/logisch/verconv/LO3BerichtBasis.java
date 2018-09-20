/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.verconv;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ByteaopslagAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ChecksumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.verconv.LO3ReferentieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3BerichtenBronAttribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.logisch.kern.Persoon;

/**
 * Bericht, op LO3 achtige wijze opgebouwd.
 *
 * Naast berichten zoals voorkomend op het GBA-V netwerk (zoals Lg01 en La01 berichten), gaat het ook om berichten
 * vormgegeven op eenzelfde manier. In casu zijn dit 'berichten' gebaseerd op tabel 35, die op een soortgelijke manier
 * zijn vormgegeven, edoch niet op het GBA-V netwerk voorkwamen.
 *
 * 1. LO3 bericht is gemodelleerd naar aanleiding van de behoefte om 'berichten' te kunnen 'loggen', en hier meldingen
 * van vast te leggen. In eerste instantie was dit toegespitst op de berichten die ook op het GBA-V netwerk werden
 * uitgewisseld. Bij eerste conceptoplevering van het nieuwe model bleek de constructie ook gebruikt te worden voor het
 * kunnen loggen van (meldingen bij) de conversie van tabelregels uit 'tabel 35' (de autorisatietabel uit het LO3
 * stelsel). Besloten is de definitie van LO3 bericht ietwat op te rekken, zodat deze 'berichten' (die overigens wel op
 * eenzelfde manier zijn opgebouwd) ook onder deze definitie te laten vallen. Nadeel van deze keuze is dat het model net
 * iets minder fraai is; voordeel is dat er minder structuren nodig zijn.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface LO3BerichtBasis extends BrpObject {

    /**
     * Retourneert Berichtsoort onderdeel LO3 stelsel? van LO3 Bericht.
     *
     * @return Berichtsoort onderdeel LO3 stelsel?.
     */
    JaNeeAttribuut getIndicatieBerichtsoortOnderdeelLO3Stelsel();

    /**
     * Retourneert Referentie van LO3 Bericht.
     *
     * @return Referentie.
     */
    LO3ReferentieAttribuut getReferentie();

    /**
     * Retourneert Bron van LO3 Bericht.
     *
     * @return Bron.
     */
    LO3BerichtenBronAttribuut getBron();

    /**
     * Retourneert Administratienummer van LO3 Bericht.
     *
     * @return Administratienummer.
     */
    AdministratienummerAttribuut getAdministratienummer();

    /**
     * Retourneert Persoon van LO3 Bericht.
     *
     * @return Persoon.
     */
    Persoon getPersoon();

    /**
     * Retourneert Berichtdata van LO3 Bericht.
     *
     * @return Berichtdata.
     */
    ByteaopslagAttribuut getBerichtdata();

    /**
     * Retourneert Checksum van LO3 Bericht.
     *
     * @return Checksum.
     */
    ChecksumAttribuut getChecksum();

    /**
     * Retourneert Conversie van LO3 Bericht.
     *
     * @return Conversie.
     */
    LO3BerichtConversieGroep getConversie();

}
