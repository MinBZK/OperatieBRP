/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OINAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.basis.BestaansPeriodeStamgegeven;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 * Een voor de BRP relevant overheidsorgaan of derde, zoals bedoeld in de Wet BRP, of onderdeel daarvan, die met een bepaalde gerechtvaardigde doelstelling
 * is aangesloten op de BRP.
 * <p/>
 * Dit betreft - onder andere - gemeenten, (overige) overheidsorganen en derden.
 * <p/>
 * 1. Er is voor gekozen om gemeenten, overige overheidsorganen etc te zien als soorten partijen, en ze allemaal op te nemen in een partij tabel. Van
 * oudsher voorkomende tabellen zoals 'de gemeentetabel' is daarmee een subtype van de partij tabel geworden. RvdP 29 augustus 2011
 * <p/>
 * Voor Partij, maar ook Partij/Rol worden toekomst-mutaties toegestaan. Dat betekent dat een normale 'update via trigger' methode NIET werkt voor de A
 * laag: het zetten op "Materieel = Nee" gaat NIET via een update trigger op de C(+D) laag tabel. Derhalve suggestie voor de DBA: de 'materieel?' velden
 * niet via een trigger maar via een view c.q. een 'do instead' actie?? RvdP 10 oktober 2011
 */
@Entity
@Table(schema = "Kern", name = "Partij")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Partij extends AbstractPartij implements BestaansPeriodeStamgegeven {

    /**
     * Het voorvoegsel dat gebruikt wordt bij de opbouw van een afnemer queue naam.
     */
    public static final String PREFIX_AFNEMER_QUEUE_NAAM = "AFNEMER-";

    @Fetch(FetchMode.SELECT)
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "partij", referencedColumnName = "id")
    private Set<PartijRol> partijrollen;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     */
    protected Partij() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param naam                                    naam van Partij.
     * @param soort                                   soort van Partij.
     * @param code                                    code van Partij.
     * @param datumIngang                            datumIngang van Partij.
     * @param datumEinde                              datumEinde van Partij.
     * @param oIN oIN van Partij.
     * @param indicatieVerstrekkingsbeperkingMogelijk indicatieVerstrekkingsbeperkingMogelijk van Partij.
     * @param indicatieAutomatischFiatteren           indicatieAutomatischFiatteren van Partij.
     * @param datumOvergangNaarBRP                    datumOvergangNaarBRP van Partij.
     */
    public Partij(final NaamEnumeratiewaardeAttribuut naam,
        final SoortPartijAttribuut soort,
        final PartijCodeAttribuut code,
        final DatumEvtDeelsOnbekendAttribuut datumIngang,
        final DatumEvtDeelsOnbekendAttribuut datumEinde,
        final OINAttribuut oIN,
        final JaNeeAttribuut indicatieVerstrekkingsbeperkingMogelijk,
        final JaNeeAttribuut indicatieAutomatischFiatteren,
        final DatumAttribuut datumOvergangNaarBRP)
    {
        super(code, naam, datumIngang, datumEinde, oIN, soort, indicatieVerstrekkingsbeperkingMogelijk, indicatieAutomatischFiatteren, datumOvergangNaarBRP);
    }

    @Override
    // Public override van de getID, zodat die gebruikt kan worden in de dataaccess en business.
    @Transient
    public Short getID() {
        return super.getID();
    }

    /**
     * Haalt de queue naam op voor deze partij wanneer deze een afnemer is. Dit is o.a. voor gebruik in de JMS broker(s) van de levering mutaties modules.
     *
     * @return queue naam
     */
    @Transient
    public String getQueueNaam() {
        return PREFIX_AFNEMER_QUEUE_NAAM + getCode().getWaarde();
    }

    /* De interface implementatie : doorlussen van de methodes. */

    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumAanvangGeldigheid() {
        return this.getDatumIngang();
    }

    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumEindeGeldigheid() {
        return this.getDatumEinde();
    }


    /**
     * Retourneert de partijrollen, dwz. de rollen die de partij heeft.
     *
     * @return Partijrollen.
     */
    public Set<PartijRol> getPartijrollen() {
        return this.partijrollen;
    }

    /**
     * Retourneert de rollen die een partij heeft.
     *
     * @return rollen
     */
    public Set<Rol> getRollen() {
        final Set<Rol> rollen = new HashSet<>();
        if (getPartijrollen() != null) {
            for (final PartijRol partijRol : getPartijrollen()) {
                rollen.add(partijRol.getRol());
            }
        }
        return rollen;
    }

}
