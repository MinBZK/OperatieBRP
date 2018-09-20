/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatieAttribuut;
import nl.bzk.brp.model.basis.AbstractBerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtEntiteitGroep;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.RelatieBasis;

/**
 * De relatie tussen personen.
 *
 * Een relatie tussen twee of meer personen is als aparte object opgenomen. Het relatie-object beschrijft om wat voor
 * soort relatie het gaat, en waar en wanneer deze begonnen en/of beëindigd is. Het koppelen van een persoon aan een
 * relatie gebeurt via een object van het type betrokkenheid.
 *
 * 1. Naast de onderkende relatievormen (Huwelijk, geregistreerd partnerschap en familierechtelijkebetrekking) is er
 * lange tijd sprake geweest van nog een aantal binaire relatievormen: erkenning ongeboren vrucht, ontkenning ouderschap
 * en naamskeuze ongeboren vrucht. Deze relatievormen zijn in een laat stadium alsnog geschrapt uit de gegevensset. In
 * een nog later stadium zijn ze weer teruggekomen als 'gegevens van de ABS' (ambtenaar burgerzaken systeem). De gekozen
 * constructie van o.a. Relatie is mede daarom nog steeds gebaseerd op mogelijke toevoegingen: het is nu eenvoudig om in
 * de toekomst eventuele nieuwe (binaire) relatievormen toe te voegen.
 *
 * 2. Naar Nederlands recht heeft een kind altijd tenminste een moeder, die eventueel onbekend kan zijn. Naar
 * buitenlands recht kan het echter voorkomen dat een kind vastgesteld geen moeder heeft. In de BRP leggen we dat vast
 * door alleen een familierechtelijke betrekking aan te maken zonder dat die ook een betrokkenheid 'Ouder' heeft. Er kan
 * dan geen verantwoording vastgelegd worden over dit feit. Dit is volledig terecht. In tegenstelling tot het GBA-model,
 * waar de 02 Ouder 1 en 03 Ouder 2 verplichte categorieën waren. Dat heeft tot gevolg dat er iets moet worden opgenomen
 * in deze categorieën, ook al zou die juridisch gezien gewoon niet moeten voorkomen.
 *
 * 3. Er is redelijk wat discussie geweest over de correcte modellering van relaties; had dit niet geimplementeerd
 * moeten worden middels (meerdere) binaire relaties tussen twee personen? De overweging voor de huidige wijze (relatie
 * met betrokkenheden) is vanuit het perspectief dat een Huwelijk, Partnerschap etc. een concept is waar je feiten over
 * vast wilt leggen, ongeacht de betrokkenheden. Dat is bij binaire relaties niet eenduidig mogelijk. Daarnaast hebben
 * binaire relaties altijd volgorde. Dit is niet het geval bij de 'set van betrokkenheden'.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractRelatieBericht extends AbstractBerichtEntiteit implements BrpObject, BerichtEntiteit, MetaIdentificeerbaar, RelatieBasis {

    private static final Integer META_ID = 3184;
    private SoortRelatieAttribuut soort;
    private RelatieStandaardGroepBericht standaard;
    private List<BetrokkenheidBericht> betrokkenheden;

    /**
     * Constructor die het discriminator attribuut zet of doorgeeft.
     *
     * @param soort de waarde van het discriminator attribuut
     */
    public AbstractRelatieBericht(final SoortRelatieAttribuut soort) {
        this.soort = soort;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortRelatieAttribuut getSoort() {
        return soort;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RelatieStandaardGroepBericht getStandaard() {
        return standaard;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BetrokkenheidBericht> getBetrokkenheden() {
        return betrokkenheden;
    }

    /**
     * Zet Standaard van Relatie.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final RelatieStandaardGroepBericht standaard) {
        this.standaard = standaard;
    }

    /**
     * Zet Betrokkenheden van Relatie.
     *
     * @param betrokkenheden Betrokkenheden.
     */
    public void setBetrokkenheden(final List<BetrokkenheidBericht> betrokkenheden) {
        this.betrokkenheden = betrokkenheden;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getMetaId() {
        return META_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BerichtEntiteit> getBerichtEntiteiten() {
        final List<BerichtEntiteit> berichtEntiteiten = new ArrayList<>();
        if (betrokkenheden != null) {
            berichtEntiteiten.addAll(getBetrokkenheden());
        }
        return berichtEntiteiten;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BerichtEntiteitGroep> getBerichtEntiteitGroepen() {
        final List<BerichtEntiteitGroep> berichtGroepen = new ArrayList<>();
        berichtGroepen.add(getStandaard());
        return berichtGroepen;
    }

}
