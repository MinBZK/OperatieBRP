/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.business.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.inject.Inject;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.SysteemNaamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReferentienummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMeldingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingsresultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.VerwerkingsresultaatAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.bericht.ber.AntwoordBericht;
import nl.bzk.brp.model.bericht.ber.BerichtMeldingBericht;
import nl.bzk.brp.model.bericht.ber.BerichtResultaatGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStuurgegevensGroepBericht;
import nl.bzk.brp.model.bericht.ber.MeldingBericht;
import nl.bzk.brp.model.logisch.ber.Bericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.webservice.business.stappen.BerichtVerwerkingsResultaat;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * Abstract Antwoord Bericht Factory.
 *
 * @brp.bedrijfsregel R1410
 */
@Regels(Regel.R1410)
public abstract class AbstractAntwoordBerichtFactory implements AntwoordBerichtFactory {

    @Inject
    private ReferentieDataRepository referentieDataRepository;

    /**
     * Vul het antwoord bericht aan. Voor bijhouding moet bijvoorbeeld de administratieve handeling erin en voor bevraging de gevonden personen.
     *
     * @param resultaat verwerkingsresultaat
     * @param inkomend  inkomendBericht bericht
     * @param antwoord  antwoord bericht
     */
    protected abstract void vulAntwoordBerichtAan(final BerichtVerwerkingsResultaat resultaat, final Bericht inkomend, final AntwoordBericht antwoord);

    /**
     * Bepaalt op basis van het ingaande bericht het antwoord bericht dat geretourneerd moet worden.
     *
     * @param ingaandBericht Het ingaande bericht.
     * @return Het antwoord bericht behorende bij het ingaande bericht.
     */
    protected abstract AntwoordBericht maakInitieelAntwoordBerichtVoorInkomendBericht(final Bericht ingaandBericht);

    /**
     * Maak een verwerkingResultaat groep aan voor het bericht.
     *
     * @param ingaandBericht het ingaand bericht
     * @param resultaat      het verwerkings verwerkingResultaat
     * @return Resultaat groep voor het uitgaande bericht
     */
    protected abstract BerichtResultaatGroepBericht maakInitieelBerichtResultaatGroepBericht(final Bericht ingaandBericht,
                                                                                             final BerichtVerwerkingsResultaat resultaat);

    @Override
    public AntwoordBericht bouwAntwoordBericht(final Bericht inkomendBericht, final BerichtVerwerkingsResultaat verwerkingResultaat) {
        final AntwoordBericht antwoord = maakInitieelAntwoordBerichtMetStuurgegevens(inkomendBericht, verwerkingResultaat);
        vulAntwoordBerichtAan(verwerkingResultaat, inkomendBericht, antwoord);

        return antwoord;
    }

    /**
     * Maakt het initiele (generieke) antwoordbericht dit is voor zowel bijhouden als bevragen gelijk. Dit moet nog aangevuld worden met bijhoudings- of
     * bevragingsspecifieke gegevens.
     *
     * @param inkomend  Het ingaande bericht.
     * @param resultaat Resultaat van de verwerking uit de business laag.
     * @return Het initiele antwoord bericht.
     */
    @Regels(Regel.R1410)
    private AntwoordBericht maakInitieelAntwoordBerichtMetStuurgegevens(final Bericht inkomend, final BerichtVerwerkingsResultaat resultaat) {
        final AntwoordBericht antwoord = maakInitieelAntwoordBerichtVoorInkomendBericht(inkomend);
        antwoord.setStuurgegevens(maakStuurgegevensVoorAntwoordBericht(inkomend));
        antwoord.setResultaat(bepaalResultaatVoorBerichtVerwerking(inkomend, resultaat));
        antwoord.setMeldingen(bepaalMeldingen(resultaat));
        return antwoord;
    }

    /**
     * Bepaalt de verwerkingResultaat groep voor het uitgaande bericht op basis van het verwerkingResultaat uit de business laag.
     *
     *
     *
     * @param ingaandBericht Het ingaande bericht.
     * @param resultaat      Het verwerkingResultaat van de verwerking uit de business laag.
     * @return Bericht verwerkingResultaat groep.
     */
    @Regels({ Regel.VR00123, Regel.VR00127, Regel.VR00128 })
    private BerichtResultaatGroepBericht bepaalResultaatVoorBerichtVerwerking(
        final Bericht ingaandBericht, final BerichtVerwerkingsResultaat resultaat)
    {
        final BerichtResultaatGroepBericht resultaatGroep = maakInitieelBerichtResultaatGroepBericht(ingaandBericht, resultaat);

        SoortMelding hoogsteMeldingNiveau = bepaalHoogsteMeldingNiveau(resultaat.getMeldingen());
        if (null == hoogsteMeldingNiveau) {
            hoogsteMeldingNiveau = SoortMelding.GEEN;
        }

        resultaatGroep.setHoogsteMeldingsniveau(new SoortMeldingAttribuut(hoogsteMeldingNiveau));

        if (resultaat.bevatStoppendeFouten()) {
            resultaatGroep.setVerwerking(new VerwerkingsresultaatAttribuut(Verwerkingsresultaat.FOUTIEF));
        } else {
            resultaatGroep.setVerwerking(new VerwerkingsresultaatAttribuut(Verwerkingsresultaat.GESLAAGD));
        }
        return resultaatGroep;
    }

    /**
     * Bepaalt het hoogste melding niveau aan de hand van de lijst met meldingen.
     *
     * @param meldingen Lijst van meldingen.
     * @return SoortMelding.
     */
    private SoortMelding bepaalHoogsteMeldingNiveau(final List<Melding> meldingen) {
        final SoortMelding hoogste;
        if (meldingen.isEmpty()) {
            hoogste = null;
        } else {
            int hoogsteOrdinal = -1;
            for (final Melding melding : meldingen) {
                hoogsteOrdinal = Math.max(hoogsteOrdinal, melding.getSoort().ordinal());
            }
            hoogste = SoortMelding.values()[hoogsteOrdinal];
        }
        return hoogste;
    }

    /**
     * Maakt de stuurgegevens aan voor het uitgaande bericht.
     *
     * @param ingaandBericht Het ingaande bericht.
     * @return Bericht stuurgegevens.
     * @brp.bedrijfsregel VR00050
     */
    @Regels(Regel.VR00050)
    private BerichtStuurgegevensGroepBericht maakStuurgegevensVoorAntwoordBericht(final Bericht ingaandBericht) {
        final BerichtStuurgegevensGroepBericht stuurgegevensGroepUitgaand = new BerichtStuurgegevensGroepBericht();

        // Zendende systeem en partij:
        stuurgegevensGroepUitgaand.setZendendeSysteem(SysteemNaamAttribuut.BRP);
        final Partij partijBrpVoorziening = referentieDataRepository.vindPartijOpCode(PartijCodeAttribuut.BRP_VOORZIENING);
        stuurgegevensGroepUitgaand.setZendendePartij(new PartijAttribuut(partijBrpVoorziening));

        // Referentienummers:
        stuurgegevensGroepUitgaand.setReferentienummer(new ReferentienummerAttribuut(UUID.randomUUID().toString()));
        stuurgegevensGroepUitgaand.setCrossReferentienummer(ingaandBericht.getStuurgegevens().getReferentienummer());
        return stuurgegevensGroepUitgaand;
    }

    /**
     * Zet de berichtMeldingen op null als er geen meldingen zijn en anders maak een nieuwe lijst van meldingen.
     *
     * @param berichtResultaat het verwerkingResultaat van een bericht
     * @return lijst van meldingen
     */
    private List<BerichtMeldingBericht> bepaalMeldingen(final BerichtVerwerkingsResultaat berichtResultaat) {
        final List<BerichtMeldingBericht> berichtMeldingen;

        if (berichtResultaat.getMeldingen().isEmpty()) {
            berichtMeldingen = null;
        } else {
            berichtMeldingen = new ArrayList<>();
            for (final Melding melding : berichtResultaat.getMeldingen()) {
                final BerichtMeldingBericht berichtMeldingBericht = new BerichtMeldingBericht(melding);

                if (!bevatMeldingBericht(berichtMeldingen, berichtMeldingBericht.getMelding())) {
                    berichtMeldingen.add(berichtMeldingBericht);
                }
            }
        }

        return berichtMeldingen;
    }

    /**
     * Controleert of de lijst meldingen al een melding bevat.
     *
     * @param meldingen de lijst met meldingen.
     * @param melding   de melding die al mogelijk in de lijst zit.
     * @return true indien melding al in meldingen zit, anders false.
     */
    private boolean bevatMeldingBericht(final List<BerichtMeldingBericht> meldingen,
        final MeldingBericht melding)
    {
        boolean resultaat = false;
        for (final BerichtMeldingBericht meldingBericht : meldingen) {
            final MeldingBericht huidigeMelding = meldingBericht.getMelding();
            if (new EqualsBuilder().append(huidigeMelding.getReferentieID(), melding.getReferentieID())
                .append(huidigeMelding.getMelding(), melding.getMelding())
                .append(huidigeMelding.getSoort(), melding.getSoort())
                .append(huidigeMelding.getRegel(), melding.getRegel())
                .isEquals())
            {
                resultaat = true;
                break;
            }
        }
        return resultaat;
    }

}
