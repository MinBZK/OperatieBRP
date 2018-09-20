/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.afstamming.acties.afstamming;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import nl.bzk.brp.business.regels.NaActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NationaliteitcodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.FamilierechtelijkeBetrekkingView;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.logisch.kern.FamilierechtelijkeBetrekking;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.PersoonNationaliteit;

import nl.bzk.brp.util.RelatieUtils;

/**
 * Implementatie van bedrijfsregel BRBY0105M.
 * <p/>
 * BRBY0105M: Oma/opa artikel (nationaliteit kind)
 * <p/>
 * Ter ondersteuning van het invoeren geboortegegevens op het BZM (pre-validatie meldingen vanuit BRP): eventueel heeft kind NL nationaliteit (keten
 * specs's MR-01-05)
 * <p/>
 * Het Kind moet de Nederlandse Nationaliteit hebben op datum aanvang geldigheid Ouderschap als die Ouder een ingeschrevene is en in Nederland geboren is
 * (Land in de groep Geboorte heeft de waarde Nederland) óf zich voor zijn/haar 18e jaar voor de eerste keer in Nederland heeft gevestigd (datum geboorte +
 * 18 jaar > datum inschrijving in Inschrijving).
 * <p/>
 * Deze bedrijfsregel wordt geimplemneteerd aan het eind van de acties (omdat we de nationaliteit van het kind nodig hebben); daardoor is de signature
 * NaActieRegel.
 * <p/>
 * De basis regel: In kort: als het kind GEEN NL nationaliteit en heeft RECHT OP, geef dan een foutmelding. Het gaat hier niet om, dat een kind TE VEEL
 * heeft gekregen. Deze regel gaat NIET AF als het kind al een NL heeft gekregen.
 *
 * @brp.bedrijfsregel BRBY0105M
 */
@Named("BRBY0105M")
public class BRBY0105M implements NaActieRegelMetMomentopname<FamilierechtelijkeBetrekkingView,
    FamilierechtelijkeBetrekkingBericht>
{

    @Override
    public List<BerichtEntiteit> voerRegelUit(
        final FamilierechtelijkeBetrekkingView familierechtelijkeBetrekking,
        final FamilierechtelijkeBetrekkingBericht familierechtelijkeBetrekkingBericht)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();
        final Persoon kind = RelatieUtils.haalKindUitRelatie(familierechtelijkeBetrekking);
        if (!persoonHeeftNederlandseNationaliteit(kind)
            && kindHeeftRechtOpNederlandseNationaliteit(familierechtelijkeBetrekking))
        {
            objectenDieDeRegelOvertreden.add(
                (PersoonBericht) RelatieUtils.haalKindUitRelatie(familierechtelijkeBetrekkingBericht));
        }
        return objectenDieDeRegelOvertreden;
    }

    /**
     * Controleert of het kind recht heeft op de NL nationaliteit. Een ouder is in NL geboren of voor zijn/haar 18de gevestigd in Nederland.
     *
     * @param familierechtelijkeBetrekking familierechtelijkeBetrekking waar het kind in zit
     * @return true indien het kind recht heeft op de NL nationaliteit anders false
     */
    private boolean kindHeeftRechtOpNederlandseNationaliteit(
        final FamilierechtelijkeBetrekking familierechtelijkeBetrekking)
    {
        boolean resultaat = false;
        for (Betrokkenheid ouderBetr : RelatieUtils.haalOuderBetrokkenhedenUitRelatie(familierechtelijkeBetrekking)) {
            if (isInNederlandGeboren(ouderBetr.getPersoon()) || isVoor18GevestigdInNederland(ouderBetr.getPersoon())) {
                resultaat = true;
            }
        }
        return resultaat;
    }

    /**
     * Controleert of een persoon voor zijn/haar 18de is gevestigd in Nederland.
     *
     * @param persoon de te controleren persoon
     * @return true indien voor 18de gevestigd in Nederland
     */
    private boolean isVoor18GevestigdInNederland(final Persoon persoon) {
        final DatumEvtDeelsOnbekendAttribuut datumInschrijving = persoon.getInschrijving().getDatumInschrijving();
        final DatumEvtDeelsOnbekendAttribuut datumGeboorte = persoon.getGeboorte().getDatumGeboorte();
        if (datumGeboorte.getWaarde() != 0) {
            final DatumAttribuut datumGeboortePlus18Jaar =
                DatumAttribuut.verhoogMetJaren(new DatumAttribuut(datumGeboorte), DatumEvtDeelsOnbekendAttribuut.ACHTTIEN_JAAR);
            return datumInschrijving.voorDatumSoepel(datumGeboortePlus18Jaar);
        } else {
            // iedereen met jaar onbekend wordt goedgekeurd.
            return true;
        }
    }

    /**
     * Controleer of persoon in Nederland is geboren.
     *
     * @param persoon de te controleren persoon
     * @return true indien geboren in Nederland
     */
    private boolean isInNederlandGeboren(final Persoon persoon) {
        return persoon.getGeboorte().getLandGebiedGeboorte().getWaarde().getCode().equals(LandGebiedCodeAttribuut.NEDERLAND);
    }

    /**
     * Controleert of kind de Nederlandse nationaliteit heeft.
     *
     * @param kind te controleren kind.
     * @return true indien de nederlandse nationaliteit aanwezig is voor kind.
     */
    private boolean persoonHeeftNederlandseNationaliteit(final Persoon kind) {
        boolean resultaat = false;
        for (final PersoonNationaliteit persoonNationaliteit : kind.getNationaliteiten()) {
            if (NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE.equals(persoonNationaliteit.
                getNationaliteit().getWaarde().getCode()))
            {
                resultaat = true;
            }
        }
        return resultaat;
    }

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0105M;
    }
}
