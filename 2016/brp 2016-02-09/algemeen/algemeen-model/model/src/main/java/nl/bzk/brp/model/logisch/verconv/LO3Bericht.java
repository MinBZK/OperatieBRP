/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.verconv;


/**
 * Bericht, op LO3 achtige wijze opgebouwd.
 * <p/>
 * Naast berichten zoals voorkomend op het GBA-V netwerk (zoals Lg01 en La01 berichten), gaat het ook om berichten vormgegeven op eenzelfde manier. In casu
 * zijn dit 'berichten' gebaseerd op tabel 35, die op een soortgelijke manier zijn vormgegeven, edoch niet op het GBA-V netwerk voorkwamen.
 * <p/>
 * 1. LO3 bericht is gemodelleerd naar aanleiding van de behoefte om 'berichten' te kunnen 'loggen', en hier meldingen van vast te leggen. In eerste
 * instantie was dit toegespitst op de berichten die ook op het GBA-V netwerk werden uitgewisseld. Bij eerste conceptoplevering van het nieuwe model bleek
 * de constructie ook gebruikt te worden voor het kunnen loggen van (meldingen bij) de conversie van tabelregels uit 'tabel 35' (de autorisatietabel uit
 * het LO3 stelsel). Besloten is de definitie van LO3 bericht ietwat op te rekken, zodat deze 'berichten' (die overigens wel op eenzelfde manier zijn
 * opgebouwd) ook onder deze definitie te laten vallen. Nadeel van deze keuze is dat het model net iets minder fraai is; voordeel is dat er minder
 * structuren nodig zijn. RvdP 3 december 2013.
 */
public interface LO3Bericht extends LO3BerichtBasis {

}
