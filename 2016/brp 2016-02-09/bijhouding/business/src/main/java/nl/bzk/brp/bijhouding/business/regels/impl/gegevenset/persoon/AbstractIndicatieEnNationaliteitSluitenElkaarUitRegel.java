/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.business.regels.NaActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NationaliteitcodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIndicatieBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.PersoonNationaliteit;

/**
 * Abstracte super klasse bedrijfsregel voor indicaties en nationaliteiten die elkaar uitsluiten.
 */
public abstract class AbstractIndicatieEnNationaliteitSluitenElkaarUitRegel
        implements NaActieRegelMetMomentopname<PersoonView, PersoonBericht>
{
    /**
     * Geef de indicatie terug waar het in deze regel om gaat.
     *
     * @return de indicatie
     */
    protected abstract SoortIndicatie getIndicatie();

    /**
     * Checkt of de meegegeven nationaliteit code de juiste is voor deze regel.
     *
     * @param nationaliteitCode nationaliteit code
     * @return is de te check nationaleit (true) of niet (false)
     */
    protected abstract boolean heeftTeCheckenNationaliteit(NationaliteitcodeAttribuut nationaliteitCode);

    @Override
    public List<BerichtEntiteit> voerRegelUit(final PersoonView persoon, final PersoonBericht persoonBericht) {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();

        // Als er een indicatie van het juiste type in het oorspronkelijke bericht zat.
        if (persoonBericht.getIndicaties() != null) {
            for (PersoonIndicatieBericht persoonIndicatieBericht : persoonBericht.getIndicaties()) {
                if (getIndicatie() == persoonIndicatieBericht.getSoort().getWaarde()
                        && Ja.J == persoonIndicatieBericht.getStandaard().getWaarde().getWaarde())
                {
                    // Check dan of de persoon na de actie niet een bepaalde nationaliteit bezit.
                    for (PersoonNationaliteit persoonNationaliteit : persoon.getNationaliteiten()) {
                        final Nationaliteit nationaliteit = persoonNationaliteit.getNationaliteit().getWaarde();
                        if (heeftTeCheckenNationaliteit(nationaliteit.getCode())) {
                            objectenDieDeRegelOvertreden.add(persoonIndicatieBericht);
                        }
                    }
                }
            }
        }

        return objectenDieDeRegelOvertreden;
    }
}
