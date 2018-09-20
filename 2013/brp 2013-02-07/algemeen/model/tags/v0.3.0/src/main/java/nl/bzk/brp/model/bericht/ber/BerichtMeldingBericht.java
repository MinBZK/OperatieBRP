/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.ber;

import nl.bzk.brp.model.algemeen.attribuuttype.ber.Meldingtekst;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import nl.bzk.brp.model.bericht.ber.basis.AbstractBerichtMeldingBericht;
import nl.bzk.brp.model.logisch.ber.BerichtMelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.util.MeldingUtil;


/**
 * Het voorkomen van een melding in een uitgaand bericht.
 *
 * Een bijhoudingsbericht (een inkomend bericht) kan leiden tot ��n of meer meldingen. Deze worden via een uitgaand
 * bericht gecommuniceerd naar de partij die het inkomende bericht had gestuurd.
 *
 * Een melding had zowel aan een inkomend als aan een uitgaand bericht gekoppeld kunnen zijn. De reden voor het opnemen
 * van het objecttype in het BMR is echter vooral om het uitgaand bericht te kunnen vormgeven. Om die reden is bij de
 * definitie aangesloten op het uitgaand bericht (waar de melding in wordt medegedeeld), en niet aan het inkomend
 * bericht (die heeft geleid tot de melding). Indien wordt besloten om de bericht/melding te persisteren, kan dit
 * besluit eventueel worden herzien: het is dan denkbaar dat (direct) aan het inkomend bericht de daaruit ontstane
 * meldingen worden gekoppeld. Vooralsnog is dit niet aan de orde, en is een 'bericht/melding' alleen te koppelen aan
 * een inkomend bericht door vanuit het inkomend bericht te beschouwen welk uitgaand bericht daarop volgde, en welke
 * meldingen daar in werden vermeld.
 * RvdP 4-12-2012.
 *
 *
 *
 */
public class BerichtMeldingBericht extends AbstractBerichtMeldingBericht implements BerichtMelding {

    /**
     * Default empty constructor.
     */
    public BerichtMeldingBericht() {
    }

    /**
     * User klasse constructor die vanuit een BRP 'custom' Melding object werkt.
     *
     * @param melding de melding
     */
    public BerichtMeldingBericht(final Melding melding) {
        MeldingBericht meldingBericht = new MeldingBericht();
        meldingBericht.setAttribuut(Element.DUMMY);
        meldingBericht.setMelding(new Meldingtekst(melding.getOmschrijving()));
        // TODO: Zorgen dat juiste regel gezet wordt. Op basis van code uit melding of regel aan melding toevoegen.
        // op dit ogenblik kennen we alleen EEN enum voor regel, nl. DUMMY.
        meldingBericht.setRegel(MeldingUtil.zoekRegeOpviaMeldingCode(melding.getCode()));
        meldingBericht.setCommunicatieID(melding.getCommunicatieID());
        meldingBericht.setSoort(melding.getSoort());
        setMelding(meldingBericht);
    }

}
