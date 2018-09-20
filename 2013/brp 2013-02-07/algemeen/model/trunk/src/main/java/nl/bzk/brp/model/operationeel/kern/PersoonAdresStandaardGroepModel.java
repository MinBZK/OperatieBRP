/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.AanduidingAdresseerbaarObject;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AanduidingBijHuisnummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Adresregel;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AfgekorteNaamOpenbareRuimte;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Gemeentedeel;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Huisletter;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Huisnummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Huisnummertoevoeging;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.IdentificatiecodeNummeraanduiding;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieOmschrijving;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamOpenbareRuimte;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Postcode;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AangeverAdreshouding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Land;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Plaats;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingAdres;
import nl.bzk.brp.model.logisch.kern.PersoonAdresStandaardGroep;
import nl.bzk.brp.model.operationeel.kern.basis.AbstractPersoonAdresStandaardGroepModel;


/**
 * Voor de modellering van buitenlands adres waren enkele opties:
 * - Adres in een attribuut met 'regelovergang' tekens
 * Nadeel:
 * Regelovergangtekens zijn niet platformonafhankelijk en het maximale aantal regels is niet goed af te dwingen.
 * - Adres uitsplitsen volgens een of andere norm (wordt naar gezocht)
 * RNI heeft een actie gestart om te kijken of binnen Europa een werkbare standaard te vinden is. Wereldwijd gaat niet
 * lukken. (Voorlopig) nog geen optie.
 * - Adres per regel opnemen.
 * - Adresregels in een aparte tabel.
 * Is ook mogelijk mits aantal regels beperkt wordt.
 * Uiteindelijk is gekozen voor opname per regel. Dat lijkt minder flexibel dan een vrij veld waarin meerdere regels
 * geplaatst kunnen worden. Het geeft de afnemer echter wel duidelijkheid over het maximale aantal regels en het
 * maximale aantal karakters per regel dat deze kan verwachten. Het aantal zes is afkomstig uit onderzoek door de RNI
 * inzake de maximale grootte van internationale adressen.
 * RvdP 5 september 2011, verplaatst naar nieuwe groep standaard op 13 jan 2012.
 *
 *
 *
 */
@Embeddable
public class PersoonAdresStandaardGroepModel extends AbstractPersoonAdresStandaardGroepModel implements
    PersoonAdresStandaardGroep
{

    /** Standaard constructor (t.b.v. Hibernate/JPA). */
    protected PersoonAdresStandaardGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param soort soort van Standaard.
     * @param redenWijziging redenWijziging van Standaard.
     * @param aangeverAdreshouding aangeverAdreshouding van Standaard.
     * @param datumAanvangAdreshouding datumAanvangAdreshouding van Standaard.
     * @param adresseerbaarObject adresseerbaarObject van Standaard.
     * @param identificatiecodeNummeraanduiding identificatiecodeNummeraanduiding van Standaard.
     * @param gemeente gemeente van Standaard.
     * @param naamOpenbareRuimte naamOpenbareRuimte van Standaard.
     * @param afgekorteNaamOpenbareRuimte afgekorteNaamOpenbareRuimte van Standaard.
     * @param gemeentedeel gemeentedeel van Standaard.
     * @param huisnummer huisnummer van Standaard.
     * @param huisletter huisletter van Standaard.
     * @param huisnummertoevoeging huisnummertoevoeging van Standaard.
     * @param postcode postcode van Standaard.
     * @param woonplaats woonplaats van Standaard.
     * @param locatietovAdres locatietovAdres van Standaard.
     * @param locatieOmschrijving locatieOmschrijving van Standaard.
     * @param datumVertrekUitNederland datumVertrekUitNederland van Standaard.
     * @param buitenlandsAdresRegel1 buitenlandsAdresRegel1 van Standaard.
     * @param buitenlandsAdresRegel2 buitenlandsAdresRegel2 van Standaard.
     * @param buitenlandsAdresRegel3 buitenlandsAdresRegel3 van Standaard.
     * @param buitenlandsAdresRegel4 buitenlandsAdresRegel4 van Standaard.
     * @param buitenlandsAdresRegel5 buitenlandsAdresRegel5 van Standaard.
     * @param buitenlandsAdresRegel6 buitenlandsAdresRegel6 van Standaard.
     * @param land land van Standaard.
     * @param indicatiePersoonNietAangetroffenOpAdres indicatiePersoonNietAangetroffenOpAdres van Standaard.
     */
    public PersoonAdresStandaardGroepModel(final FunctieAdres soort, final RedenWijzigingAdres redenWijziging,
            final AangeverAdreshouding aangeverAdreshouding, final Datum datumAanvangAdreshouding,
            final AanduidingAdresseerbaarObject adresseerbaarObject,
            final IdentificatiecodeNummeraanduiding identificatiecodeNummeraanduiding, final Partij gemeente,
            final NaamOpenbareRuimte naamOpenbareRuimte, final AfgekorteNaamOpenbareRuimte afgekorteNaamOpenbareRuimte,
            final Gemeentedeel gemeentedeel, final Huisnummer huisnummer, final Huisletter huisletter,
            final Huisnummertoevoeging huisnummertoevoeging, final Postcode postcode, final Plaats woonplaats,
            final AanduidingBijHuisnummer locatietovAdres, final LocatieOmschrijving locatieOmschrijving,
            final Datum datumVertrekUitNederland, final Adresregel buitenlandsAdresRegel1,
            final Adresregel buitenlandsAdresRegel2, final Adresregel buitenlandsAdresRegel3,
            final Adresregel buitenlandsAdresRegel4, final Adresregel buitenlandsAdresRegel5,
            final Adresregel buitenlandsAdresRegel6, final Land land,
            final JaNee indicatiePersoonNietAangetroffenOpAdres)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        super(soort, redenWijziging, aangeverAdreshouding, datumAanvangAdreshouding, adresseerbaarObject,
                identificatiecodeNummeraanduiding, gemeente, naamOpenbareRuimte, afgekorteNaamOpenbareRuimte,
                gemeentedeel, huisnummer, huisletter, huisnummertoevoeging, postcode, woonplaats, locatietovAdres,
                locatieOmschrijving, datumVertrekUitNederland, buitenlandsAdresRegel1, buitenlandsAdresRegel2,
                buitenlandsAdresRegel3, buitenlandsAdresRegel4, buitenlandsAdresRegel5, buitenlandsAdresRegel6, land,
                indicatiePersoonNietAangetroffenOpAdres);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonAdresStandaardGroep te kopieren groep.
     */
    public PersoonAdresStandaardGroepModel(final PersoonAdresStandaardGroep persoonAdresStandaardGroep) {
        super(persoonAdresStandaardGroep);
    }

    @Override
    @nl.bzk.brp.model.validatie.constraint.Postcode
    @Transient
    public Postcode getPostcode() {
        return super.getPostcode();
    }
}
