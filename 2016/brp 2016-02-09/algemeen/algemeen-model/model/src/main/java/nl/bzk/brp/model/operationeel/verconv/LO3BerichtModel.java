/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.verconv;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ByteaopslagAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ChecksumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.verconv.LO3ReferentieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3BerichtenBronAttribuut;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.verconv.LO3Bericht;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;


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
@Entity
@Table(schema = "VerConv", name = "LO3Ber")
public class LO3BerichtModel extends AbstractLO3BerichtModel implements LO3Bericht, ModelIdentificeerbaar<Long> {

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected LO3BerichtModel() {
        super();
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param indicatieBerichtsoortOnderdeelLO3Stelsel
     *                            indicatieBerichtsoortOnderdeelLO3Stelsel van LO3 Bericht.
     * @param referentie          referentie van LO3 Bericht.
     * @param bron                bron van LO3 Bericht.
     * @param administratienummer administratienummer van LO3 Bericht.
     * @param persoon             persoon van LO3 Bericht.
     * @param berichtdata         berichtdata van LO3 Bericht.
     * @param checksum            checksum van LO3 Bericht.
     */
    public LO3BerichtModel(final JaNeeAttribuut indicatieBerichtsoortOnderdeelLO3Stelsel,
        final LO3ReferentieAttribuut referentie, final LO3BerichtenBronAttribuut bron,
        final AdministratienummerAttribuut administratienummer, final PersoonModel persoon,
        final ByteaopslagAttribuut berichtdata, final ChecksumAttribuut checksum)
    {
        super(indicatieBerichtsoortOnderdeelLO3Stelsel, referentie, bron, administratienummer, persoon, berichtdata,
            checksum);
    }

}
