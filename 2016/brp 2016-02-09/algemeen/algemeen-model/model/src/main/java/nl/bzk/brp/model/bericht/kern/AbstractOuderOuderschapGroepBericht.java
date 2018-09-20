/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.basis.AbstractMaterieleHistorieGroepBericht;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.OuderOuderschapGroepBasis;

/**
 * 1. Vorm van historie: beiden. Reden: alhoewel zeldzaam, is het denkbaar dat een ouder eerst betrokken is in een
 * familierechtelijke betrekking met een kind, daarna ouder 'af' wordt (bijvoorbeeld door adoptie), en later, door
 * herroeping van de adoptie, weer 'ouder aan'. Volgens de HUP 3.7 dient dan als datum ingang van de familierechtelijke
 * betrekking de datum te worden genomen waarop de herroeping definitief is. Anders gezegd: er is een TWEEDE
 * betrokkenheid van dezelfde ouder in dezelfde fam.recht.betrekking. Dit is opgelost door de groep 'beiden' te maken,
 * EN de attributen datum aanvang geldigheid/einde geldigheid uit het LGM te verwijderen.
 *
 * Conversie toelichting: De groep kent materiële historie; er is echter niet altijd een (goede) bron voor de datum
 * ingang. In LO3 is dit gegeven NORMALITER onderdeel van categorie 02 of 03 van de persoonslijst van het Kind(!). Soms,
 * echter, is er alleen de persoonslijst van de ouder, en is er een BOVENGRENS voor de datum ingang: in geval van
 * bijvoorbeeld een (niet-ingeschreven) adoptiekind is de datum van de adoptie vaak gelijk aan de datum ingang
 * geldigheid van de categorie 09 (op de persoonslijst van de OUDER!). Bij conversie is echter niet zichtbaar of de
 * datum ... een dergelijke BOVENGRENS is, of ... een goede inschatting van de echte datum waarop het ouderschap is
 * begonnen. Om die reden vullen we het gegeven vanuit LO3 altijd in, met als consequentie dat bij geconverteerde
 * gegevens waarbij het Kind een niet-ingeschrevene betref, we mogelijkerwijs een BOVENGRENS hebben ingevuld voor de
 * datum aanvang van de groep ouderschap. Dit is een afwijking van de BRP werkwijze: daar zouden we bij een goede
 * inschatting (bijv. als er een adoptieakte aan ten grondslag lag) WEL een echte datum hebben ingevuld, en bij een
 * bovengrens de ´00000000´ waarde hebben gehanteerd.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractOuderOuderschapGroepBericht extends AbstractMaterieleHistorieGroepBericht implements Groep, OuderOuderschapGroepBasis,
        MetaIdentificeerbaar
{

    private static final Integer META_ID = 3858;
    private static final List<Integer> ONDERLIGGENDE_ATTRIBUTEN = Arrays.asList(6088, 6176);
    private JaAttribuut indicatieOuder;
    private JaNeeAttribuut indicatieOuderUitWieKindIsGeboren;

    /**
     * {@inheritDoc}
     */
    @Override
    public JaAttribuut getIndicatieOuder() {
        return indicatieOuder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JaNeeAttribuut getIndicatieOuderUitWieKindIsGeboren() {
        return indicatieOuderUitWieKindIsGeboren;
    }

    /**
     * Zet Ouder? van Ouderschap.
     *
     * @param indicatieOuder Ouder?.
     */
    public void setIndicatieOuder(final JaAttribuut indicatieOuder) {
        this.indicatieOuder = indicatieOuder;
    }

    /**
     * Zet Ouder uit wie het kind is geboren? van Ouderschap.
     *
     * @param indicatieOuderUitWieKindIsGeboren Ouder uit wie het kind is geboren?.
     */
    public void setIndicatieOuderUitWieKindIsGeboren(final JaNeeAttribuut indicatieOuderUitWieKindIsGeboren) {
        this.indicatieOuderUitWieKindIsGeboren = indicatieOuderUitWieKindIsGeboren;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (indicatieOuder != null) {
            attributen.add(indicatieOuder);
        }
        if (indicatieOuderUitWieKindIsGeboren != null) {
            attributen.add(indicatieOuderUitWieKindIsGeboren);
        }
        return attributen;
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
    public boolean bevatElementMetMetaId(final Integer metaId) {
        return ONDERLIGGENDE_ATTRIBUTEN.contains(metaId);
    }

}
