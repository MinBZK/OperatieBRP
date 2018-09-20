/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.AssociationOverride;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.BijhoudingsaardAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaardAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.logisch.kern.PersoonBijhoudingGroep;
import nl.bzk.brp.model.logisch.kern.PersoonBijhoudingGroepBasis;

/**
 * Groep gegevens over de bijhouding
 *
 * De Wet BRP kent twee afdelingen voor bijhouden, afdeling I ('Ingezetenen') en afdeling II ('Niet ingezetenen'). Deze
 * afdelingen bepalen de wijze waarop de bijhouding plaats vindt. Hierbij is het van belang of iemand thans een
 * ingezetene is (en valt onder afdeling I), dat iemand geëmigreerd is, of dat iemand van begin af aan onder afdeling II
 * werd bijgehouden. Dit onderscheid wordt aangegeven met bijhoudingsaard.
 *
 * Verplicht aanwezig bij persoon
 *
 * 1. In geval van een opgeschorte bijhouding, is 'ingezetene' en 'niet-ingezetene' minder goed gedefinieerd. In het LO
 * BRP wordt dit punt uitgewerkt. 2. Vorm van historie: Beiden. De bijhoudingsverantwoordelijkheid kan verschuiven door
 * bijvoorbeeld een verhuizing die om technische redenen later wordt geregistreerd (maar wellicht op tijd is
 * doorgegeven). De datum ingang geldigheid van de (nieuwe) bijhoudingsverantwoordelijkheid ligt dus vaak vóór de
 * datumtijd registratie. Een formele tijdslijn alleen is dus onvoldoende. Omdat, in geval van verschuivingen van
 * verantwoordelijkheid tussen Minister en College B&W, ook van belang kan zijn wie over een periode in het verleden 'de
 * verantwoordelijke' was (voor het wijzigen van historische gegevens), is de materiële tijdslijn ook relevant, en dus
 * wordt deze vastgelegd. 3. De naam is aangepast naar bijhoudingsaard, tegen de term bijhoudingsverantwoordelijkheid
 * was teveel weerstand.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractPersoonBijhoudingGroepModel implements PersoonBijhoudingGroepBasis {

    @Embedded
    @AssociationOverride(name = PartijAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "Bijhpartij"))
    @JsonProperty
    private PartijAttribuut bijhoudingspartij;

    @Embedded
    @AttributeOverride(name = BijhoudingsaardAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Bijhaard"))
    @JsonProperty
    private BijhoudingsaardAttribuut bijhoudingsaard;

    @Embedded
    @AttributeOverride(name = NadereBijhoudingsaardAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "NadereBijhaard"))
    @JsonProperty
    private NadereBijhoudingsaardAttribuut nadereBijhoudingsaard;

    @Embedded
    @AttributeOverride(name = JaNeeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndOnverwDocAanw"))
    @JsonProperty
    private JaNeeAttribuut indicatieOnverwerktDocumentAanwezig;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractPersoonBijhoudingGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param bijhoudingspartij bijhoudingspartij van Bijhouding.
     * @param bijhoudingsaard bijhoudingsaard van Bijhouding.
     * @param nadereBijhoudingsaard nadereBijhoudingsaard van Bijhouding.
     * @param indicatieOnverwerktDocumentAanwezig indicatieOnverwerktDocumentAanwezig van Bijhouding.
     */
    public AbstractPersoonBijhoudingGroepModel(
        final PartijAttribuut bijhoudingspartij,
        final BijhoudingsaardAttribuut bijhoudingsaard,
        final NadereBijhoudingsaardAttribuut nadereBijhoudingsaard,
        final JaNeeAttribuut indicatieOnverwerktDocumentAanwezig)
    {
        this.bijhoudingspartij = bijhoudingspartij;
        this.bijhoudingsaard = bijhoudingsaard;
        this.nadereBijhoudingsaard = nadereBijhoudingsaard;
        this.indicatieOnverwerktDocumentAanwezig = indicatieOnverwerktDocumentAanwezig;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonBijhoudingGroep te kopieren groep.
     */
    public AbstractPersoonBijhoudingGroepModel(final PersoonBijhoudingGroep persoonBijhoudingGroep) {
        this.bijhoudingspartij = persoonBijhoudingGroep.getBijhoudingspartij();
        this.bijhoudingsaard = persoonBijhoudingGroep.getBijhoudingsaard();
        this.nadereBijhoudingsaard = persoonBijhoudingGroep.getNadereBijhoudingsaard();
        this.indicatieOnverwerktDocumentAanwezig = persoonBijhoudingGroep.getIndicatieOnverwerktDocumentAanwezig();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PartijAttribuut getBijhoudingspartij() {
        return bijhoudingspartij;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BijhoudingsaardAttribuut getBijhoudingsaard() {
        return bijhoudingsaard;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NadereBijhoudingsaardAttribuut getNadereBijhoudingsaard() {
        return nadereBijhoudingsaard;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JaNeeAttribuut getIndicatieOnverwerktDocumentAanwezig() {
        return indicatieOnverwerktDocumentAanwezig;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (bijhoudingspartij != null) {
            attributen.add(bijhoudingspartij);
        }
        if (bijhoudingsaard != null) {
            attributen.add(bijhoudingsaard);
        }
        if (nadereBijhoudingsaard != null) {
            attributen.add(nadereBijhoudingsaard);
        }
        if (indicatieOnverwerktDocumentAanwezig != null) {
            attributen.add(indicatieOnverwerktDocumentAanwezig);
        }
        return attributen;
    }

}
