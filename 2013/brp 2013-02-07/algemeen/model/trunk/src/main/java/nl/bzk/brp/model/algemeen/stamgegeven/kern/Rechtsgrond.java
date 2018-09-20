/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RechtsgrondCode;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.basis.AbstractRechtsgrond;


/**
 * In de Wet BRP genoemde rechtsgrond.
 *
 * Voor het bijhouden van gegevens wordt door de bijhoudende partij relevante Wet- en Regelgeving toegepast. Daar waar
 * de Wet BRP dit voorschrijft, wordt deze 'rechtsgrond' vastgelegd. Dit kunnen zowel verdragen (waaronder de verdragen
 * op basis waarvan het aangewezen bestuursorgaan gegevens van een zusterorganisatie overneemt) als Wetten of nadere
 * regelgeving zijn.
 *
 * 1. Een nette definitie van 'rechtsgrond' is niet snel gevonden. Een definitie uit een proefschrift (
 * https://openaccess.leidenuniv.nl/bitstream/handle/1887/11859/02_02.pdf?sequence=6 ) benoemd het als een 'rechtsnorm'.
 * In de Wet BRP komt het wordt in artikel 2.7 lid b sub 2 gesproken over
 * "...rechtsgrond krachtens welke gegevens over het Nederlanderschap...", wat refereert naar de relevante artikelen die
 * van toepassing zijn op het verlenen van het Nederlanderschap. In paragraaf 5.2.4. van de (voorlopige, niet
 * definitieve) memorie van toelichting wordt gesproken over "... rechtsgrond het gegeven is opgenomen (...) Als de
 * gegevens zijn ontleendaan een opgave van een
 * (buitenlands) zusterorgaan van een aangewezen bestuursorgaan" waarbij klaarblijkelijk het verdrag op basis waarvan de
 * gegevens zijn overgenomen worden aangeduid met rechtsgrond.
 * Dit zijn juist de situaties waarin de BRP de ' rechtsgronden' wil vastleggen. Om die reden is voor de naam
 * 'Rechtsgrond' gekozen, en niet voor bijvoorbeeld een term gebaseerd op 'Wet, verdrag of regelgeving'.
 * RvdP 25-9-2012.
 *
 *
 *
 */
@Entity
@Table(schema = "Kern", name = "Rechtsgrond")
public class Rechtsgrond extends AbstractRechtsgrond {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected Rechtsgrond() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code code van Rechtsgrond.
     * @param soort soort van Rechtsgrond.
     * @param omschrijving omschrijving van Rechtsgrond.
     * @param datumAanvangGeldigheid datumAanvangGeldigheid van Rechtsgrond.
     * @param datumEindeGeldigheid datumEindeGeldigheid van Rechtsgrond.
     */
    protected Rechtsgrond(final RechtsgrondCode code, final SoortRechtsgrond soort,
            final OmschrijvingEnumeratiewaarde omschrijving, final Datum datumAanvangGeldigheid,
            final Datum datumEindeGeldigheid)
    {
        super(code, soort, omschrijving, datumAanvangGeldigheid, datumEindeGeldigheid);
    }

}
