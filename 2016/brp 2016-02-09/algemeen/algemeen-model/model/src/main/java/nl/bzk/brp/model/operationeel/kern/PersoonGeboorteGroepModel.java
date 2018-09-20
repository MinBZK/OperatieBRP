/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Embeddable;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandsePlaatsAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandseRegioAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieomschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.logisch.kern.PersoonGeboorteGroep;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * Geboortegevens over een Persoon.
 * <p/>
 * Geboortegegevens zijn belangrijke identificerende gegevens. De geboortelocatie is zodanig gespecificeerd dat nagenoeg alle situaties verwerkt kunnen
 * worden. Verreweg de meeste gevallen passen in de structuur 'land - regio - buitenlandse plaats' of indien het een geboorte is in het Europese deel van
 * Nederland in de structuur 'land - gemeente - woonplaats'. In uitzonderingssituaties zijn deze structuren niet toereikend. In dat geval wordt
 * 'omschrijving geboortelocatie' gebruikt. In voorkomende gevallen kan hier een verwijzing naar bijvoorbeeld geografische co�rdinaten staan.
 * <p/>
 * 1. Het is denkbaar om 'Geboorte' als een levensgebeurtenis te beschouwen, c.q. een objecttype te construeren die klinkt als 'Gebeurtenis'. Hiervan
 * zouden dan een aantal relevante attributen onderkend kunnen worden, zoals een punt in de tijddimensie (bijv. datum of datum) en in de ruimte (middels
 * woonplaats, gemeente, land, etc etc). Hier is niet voor gekozen.
 * <p/>
 * Het binnen de scope van de BRP brengen van Levensgebeurtenis heeft echter grote impact, en introduceert vragen waar nu nog geen antwoord op bekend is
 * ('is adoptie een levensgebeurtenis?'). De feiten die we bij bijvoorbeeld de Geboorte, Overlijden, sluiten en ontbinding Huwelijk willen weten is in
 * essentie de aanuiding van ruimte (plaats, gemeente, ...) en tijd. Behalve deze logische identificatie van ruimte en tijd is er echter 'niets' dat we
 * hierover willen weten. Anders gesteld: een dergelijke entiteit heeft g��n bestaansrecht. We onderkennen daarom niet een apart objecttype hiervoor, en
 * modelleren de relevante attributen (datum, gemeente, woonplaats, ..., land) uit daar waar het zich voordoet.
 * <p/>
 * Consequenties: Door gebeurtenis direct 'specifiek' uit te modelleren (en dus bijvoorbeeld gewoon een groepje 'geboortegegevens' op te nemen bij de
 * Persoon) wordt het model eenvoudiger. Het nadeel is dat dezelfde soort gegevens (aanduiding van een punt in tijd en tuimte) herhaaldelijk gespecificeerd
 * moet worden, maar dat is een overzienbaar resultaat. 2. Geboorte kent g��n (aparte) materi�le historie: het refereert naar ��n moment in de (materi�le)
 * tijd, op de datum geboorte; er is verder geen (materi�le) 'geldigheidsperiode' voor bijv. datum geboorte: dit jaar, vorig jaar en volgend jaar ben je
 * nog steeds in 1969 geboren ;-0 RvdP 6-1-2012
 */
@Embeddable
public class PersoonGeboorteGroepModel extends AbstractPersoonGeboorteGroepModel implements PersoonGeboorteGroep {

    private static final int PRIME_BASE = 197;

    private static final int PRIME_ADD = 227;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected PersoonGeboorteGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param datumGeboorte               datumGeboorte van Geboorte.
     * @param gemeenteGeboorte            gemeenteGeboorte van Geboorte.
     * @param woonplaatsGeboorte          woonplaatsGeboorte van Geboorte.
     * @param buitenlandsePlaatsGeboorte  buitenlandsePlaatsGeboorte van Geboorte.
     * @param buitenlandseRegioGeboorte   buitenlandseRegioGeboorte van Geboorte.
     * @param omschrijvingLocatieGeboorte omschrijvingLocatieGeboorte van Geboorte.
     * @param landGebiedGeboorte          landGebiedGeboorte van Geboorte.
     */
    public PersoonGeboorteGroepModel(final DatumEvtDeelsOnbekendAttribuut datumGeboorte,
        final GemeenteAttribuut gemeenteGeboorte, final NaamEnumeratiewaardeAttribuut woonplaatsGeboorte,
        final BuitenlandsePlaatsAttribuut buitenlandsePlaatsGeboorte,
        final BuitenlandseRegioAttribuut buitenlandseRegioGeboorte,
        final LocatieomschrijvingAttribuut omschrijvingLocatieGeboorte,
        final LandGebiedAttribuut landGebiedGeboorte)
    {
        super(datumGeboorte, gemeenteGeboorte, woonplaatsGeboorte, buitenlandsePlaatsGeboorte,
            buitenlandseRegioGeboorte, omschrijvingLocatieGeboorte, landGebiedGeboorte);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonGeboorteGroep te kopieren groep.
     */
    public PersoonGeboorteGroepModel(final PersoonGeboorteGroep persoonGeboorteGroep) {
        super(persoonGeboorteGroep);
    }

    @Override
    public boolean equals(final Object obj) {
        final boolean isGelijk;

        if (obj == null || obj.getClass() != getClass()) {
            isGelijk = false;
        } else if (this == obj) {
            isGelijk = true;
        } else {
            PersoonGeboorteGroepModel that = (PersoonGeboorteGroepModel) obj;
            isGelijk =
                new EqualsBuilder().append(this.getDatumGeboorte(), that.getDatumGeboorte())
                    .append(this.getGemeenteGeboorte(), that.getGemeenteGeboorte())
                    .append(this.getWoonplaatsnaamGeboorte(), that.getWoonplaatsnaamGeboorte())
                    .append(this.getBuitenlandsePlaatsGeboorte(), that.getBuitenlandsePlaatsGeboorte())
                    .append(this.getBuitenlandseRegioGeboorte(), that.getBuitenlandseRegioGeboorte())
                    .append(this.getOmschrijvingLocatieGeboorte(), that.getOmschrijvingLocatieGeboorte())
                    .append(this.getLandGebiedGeboorte(), that.getLandGebiedGeboorte()).isEquals();
        }
        return isGelijk;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(PRIME_BASE, PRIME_ADD).append(this.getDatumGeboorte())
            .append(this.getGemeenteGeboorte()).append(this.getWoonplaatsnaamGeboorte())
            .append(this.getBuitenlandsePlaatsGeboorte()).append(this.getBuitenlandseRegioGeboorte())
            .append(this.getOmschrijvingLocatieGeboorte()).append(this.getLandGebiedGeboorte()).toHashCode();
    }

}
