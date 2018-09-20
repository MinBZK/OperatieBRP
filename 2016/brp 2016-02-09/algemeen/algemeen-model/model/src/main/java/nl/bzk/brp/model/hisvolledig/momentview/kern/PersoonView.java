/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.momentview.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NationaliteitcodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Bijhoudingsaard;
import nl.bzk.brp.model.basis.ModelRootObject;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.PersoonHisMoment;
import nl.bzk.brp.model.logisch.kern.PersoonNationaliteit;



/**
 * View klasse voor Persoon.
 */
public final class PersoonView extends AbstractPersoonView implements PersoonHisMoment, ModelRootObject {

    /**
     * Constructor die het HisVolledig object achter de view proxied.
     *
     * @param persoon             hisVolledig instantie voor deze view.
     * @param formeelPeilmoment   formeel peilmoment.
     * @param materieelPeilmoment materieel peilmoment.
     */
    public PersoonView(final PersoonHisVolledig persoon, final DatumTijdAttribuut formeelPeilmoment,
        final DatumAttribuut materieelPeilmoment)
    {
        super(persoon, formeelPeilmoment, materieelPeilmoment);
    }

    /**
     * Constructor die een view aanmaakt met als het meegegeven formele tijdstip. Het materiele moment, is de datum van het gegeven formele tijdstip.
     *
     * @param persoon           de persoonVolledig instantie die de informatie bevat
     * @param formeelPeilmoment de datumTijd waarde voor formeelPeilmoment en materieelPeilmoment
     */
    public PersoonView(final PersoonHisVolledig persoon, final DatumTijdAttribuut formeelPeilmoment) {
        this(persoon, formeelPeilmoment, new DatumAttribuut(formeelPeilmoment.getWaarde()));
    }

    /**
     * Constructor die een view aanmaakt met als formeel tijdstip nu en materiele datum vandaag.
     *
     * @param persoon persoon proxy
     */
    public PersoonView(final PersoonHisVolledig persoon) {
        this(persoon, DatumTijdAttribuut.nu());
    }

    @Override
    public boolean heeftNederlandseNationaliteit() {
        boolean heeftNederlandseNationaliteit = false;
        for (final PersoonNationaliteit nationaliteit : this.getNationaliteiten()) {
            if (nationaliteit.getNationaliteit().getWaarde().getCode().equals(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE)) {
                heeftNederlandseNationaliteit = true;
            }
        }
        return heeftNederlandseNationaliteit;
    }

    @Override
    public boolean isIngezetene() {
        return this.getBijhouding() != null
            && this.getBijhouding().getBijhoudingsaard().getWaarde() == Bijhoudingsaard.INGEZETENE;
    }

    @Override
    public boolean isPersoonGelijkAan(final Persoon persoon) {
        final boolean resultaat;
        if (!(persoon instanceof PersoonView)) {
            resultaat = false;
        } else {
            final PersoonView persoonView = (PersoonView) persoon;
            resultaat = persoonView.getID().equals(this.getID());
        }

        return resultaat;
    }

    /**
     * De conditie ‘Persoon heeft een verstrekkingsbeperking’ is: Een Persoon 'heeft een verstrekkingsbeperking' als er sprake is van een volledige
     * verstrekkingsbeperking of één of meer specifieke verstrekkingsbeperkingen.
     *
     * @return {@code true}, als aan de conditie wordt voldaan
     */
    public boolean heeftVerstrekkingsbeperking() {
        return (this.getIndicatieVolledigeVerstrekkingsbeperking() != null && this
            .getIndicatieVolledigeVerstrekkingsbeperking().heeftActueleGegevens())
            || !this.getVerstrekkingsbeperkingen().isEmpty();
    }
}
