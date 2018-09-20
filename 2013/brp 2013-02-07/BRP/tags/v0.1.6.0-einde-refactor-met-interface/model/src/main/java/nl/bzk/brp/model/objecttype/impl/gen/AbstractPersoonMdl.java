/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.impl.gen;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import nl.bzk.brp.model.attribuuttype.TechnischIdMiddel;
import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonAanschrijvingGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonAfgeleidAdministratiefGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonBijhoudingsGemeenteGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonBijhoudingsVerantwoordelijkheidGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonEUVerkiezingenGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonGeboorteGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonGeslachtsAanduidingGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonIdentificatieNummersGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonImmigratieGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonInschrijvingGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonOpschortingGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonOverlijdenGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonSamengesteldeNaamGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonUitsluitingNLKiesrechtGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonVerblijfsrechtGroep;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonskaartGroep;
import nl.bzk.brp.model.objecttype.impl.usr.PersoonVoornaamMdl;
import nl.bzk.brp.model.objecttype.interfaces.gen.PersoonBasis;
import nl.bzk.brp.model.objecttype.interfaces.usr.Betrokkenheid;
import nl.bzk.brp.model.objecttype.interfaces.usr.PersoonAdres;
import nl.bzk.brp.model.objecttype.statisch.SoortPersoon;

/**
 * De eerste laag implemenetatie van {@link}Persoon interface.
 * Deze class is een onderdeel van de model tree.
 * Deze implementatie wordt door de genrator gegenereerd.
 *
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractPersoonMdl extends AbstractDynamischObjectType implements PersoonBasis {

    //TODO: Dit is een work around omdat attribuut type niet werkt met @EmbeddedId
    @Id
    @SequenceGenerator(name = "seq_Pers", sequenceName = "Kern.seq_Pers")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_Pers")
    @Column(name = "id")
    private Long dbId;

    @Transient
    private TechnischIdMiddel iD;

    @Transient
    private SoortPersoon soort;
    @Transient
    private PersoonIdentificatieNummersGroep identificatieNummers;
    @Transient
    private PersoonGeslachtsAanduidingGroep geslachtsAanduiding;
    @Transient
    private PersoonSamengesteldeNaamGroep samengesteldeNaam;
    @Transient
    private PersoonAanschrijvingGroep aanschrijving;
    @Transient
    private PersoonGeboorteGroep geboorte;
    @Transient
    private PersoonOverlijdenGroep overlijden;
    @Transient
    private PersoonVerblijfsrechtGroep verblijfsrecht;
    @Transient
    private PersoonUitsluitingNLKiesrechtGroep uitsluitingNLKiesrecht;
    @Transient
    private PersoonEUVerkiezingenGroep eUVerkiezingenVerkiezingen;
    @Transient
    private PersoonBijhoudingsVerantwoordelijkheidGroep bijhoudingVerantwoordelijke;
    @Transient
    private PersoonOpschortingGroep opschorting;
    @Transient
    private PersoonBijhoudingsGemeenteGroep bijhoudenGemeente;
    @Transient
    private PersoonskaartGroep persoonsKaart;
    @Transient
    private PersoonImmigratieGroep immigratie;
    @Transient
    private PersoonInschrijvingGroep inschrijving;
    @Transient
    private PersoonAfgeleidAdministratiefGroep afgeleidAdministratief;
    private List<? extends Betrokkenheid> betrokkenheden;
    private List<? extends PersoonAdres> adressen;

    @OneToMany
    @JoinColumn(name = "Pers")
    private List<PersoonVoornaamMdl> persoonVoornaam;



    public List<PersoonVoornaamMdl> getPersoonVoornaam() {
        return persoonVoornaam;
    }


    public void setPersoonVoornaam(final List<PersoonVoornaamMdl> persoonVoornaam) {
        this.persoonVoornaam = persoonVoornaam;
    }

    public TechnischIdMiddel getID() {
        return iD;
    }

    @Override
    public SoortPersoon getSoort() {
        return soort;
    }

    @Override
    public PersoonIdentificatieNummersGroep getIdentificatieNummers() {
        return identificatieNummers;
    }

    @Override
    public PersoonGeslachtsAanduidingGroep getGeslachtsAanduiding() {
        return geslachtsAanduiding;
    }

    @Override
    public PersoonSamengesteldeNaamGroep getSamengesteldeNaam() {
        return samengesteldeNaam;
    }

    @Override
    public PersoonAanschrijvingGroep getAanschrijving() {
        return aanschrijving;
    }

    @Override
    public PersoonGeboorteGroep getGeboorte() {
        return geboorte;
    }

    @Override
    public PersoonOverlijdenGroep getOverlijden() {
        return overlijden;
    }

    @Override
    public PersoonVerblijfsrechtGroep getVerblijfsrecht() {
        return verblijfsrecht;
    }

    @Override
    public PersoonUitsluitingNLKiesrechtGroep getUitsluitingNLKiesrecht() {
        return uitsluitingNLKiesrecht;
    }

    @Override
    public PersoonEUVerkiezingenGroep getEUVerkiezingen() {
        return eUVerkiezingenVerkiezingen;
    }

    @Override
    public PersoonBijhoudingsVerantwoordelijkheidGroep getBijhoudingVerantwoordelijke() {
        return bijhoudingVerantwoordelijke;
    }

    @Override
    public PersoonOpschortingGroep getOpschorting() {
        return opschorting;
    }

    @Override
    public PersoonBijhoudingsGemeenteGroep getBijhoudenGemeente() {
        return bijhoudenGemeente;
    }

    @Override
    public PersoonskaartGroep getPersoonsKaart() {
        return persoonsKaart;
    }

    @Override
    public PersoonImmigratieGroep getImmigratie() {
        return immigratie;
    }

    @Override
    public PersoonInschrijvingGroep getInschrijving() {
        return inschrijving;
    }

    @Override
    public PersoonAfgeleidAdministratiefGroep getAfgeleidAdministratief() {
        return afgeleidAdministratief;
    }

    @Override
    public List<? extends Betrokkenheid> getBetrokkenheden() {
        return this.betrokkenheden;
    }

    @Override
    public List<? extends PersoonAdres> getAdressen() {
        return this.adressen;
    }

}
