package nl.bzk.brp.model.objecttype.operationeel.statisch;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.attribuuttype.TekstUitBesluit;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Autorisatiebesluit;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortAutorisatiebesluit;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Toestand;

/**
 * Een besluit van de minister van BZK, van een College van Burgemeester en Wethouders, of van de Staat der Nederlanden, dat de juridische grondslag vormt voor het autoriseren van Partijen.Alle bijhoudingsacties op de BRP dienen expliciet toegestaan te worden doordat er een Autorisatiebesluit van het soort 'bijhouden' aanwezig is. Naast de initiële Autorisatie aan gemeenten en aan minister - die als een soort 'bootstrap' is verricht door 'de Staat der Nederlanden' - kunnen gemeenten en minister andere partijen autoriseren voor bepaalde soorten bijhoudingen.
 *
 */
@Entity
@Table(schema = "Kern", name = "Autorisatiebesluit")
@Access(AccessType.FIELD)
public class Autorisatiebesluit extends AbstractStatischObjectType {

    /** Technische sleutel . */
    @Id
    @Column (name = "id")
    private Integer id;

    /** Soort autorisatiebesluit. */
    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "srt"))
    private SoortAutorisatiebesluit soort;

    /** De tekst uit het Autorisatiebesluit. */
    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "besluittekst"))
    private TekstUitBesluit besluittekst;

    /** De Partij, dan wel de verantwoordelijke voor de Partij, die het Autorisatiebesluit heeft genomen. */
    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "autoriseerder"))
    private Partij autoriseerder;

    /** Indicatie die aangeeft of een Autorisatiebesluit WEL of NIET is gebaseerd op een Model besluit. */
    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "indmodelbesluit"))
    private JaNee indicatieModelBesluit;

    /** Het Autorisatiebesluit waarop het onderhavige Autorisatiebesluit gebaseerd is. */
    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "gebaseerdop"))
    private Autorisatiebesluit gebaseerdOp;

    /** Indicatie die aangeeft of het Autorisatiebesluit is ingetrokken. */
    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "indingetrokken"))
    private JaNee indicatieIngetrokken;

    /** Datum waarop het besluit genomen is. */
    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "datbesluit"))
    private Datum datumBesluit;

    /** De datum waarop het autorisatiebesluit ingaat. */
    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "datingang"))
    private Datum datumIngang;

    /** De datum waarop het autorisatiebesluit beëindigd dient te zijn. */
    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "dateinde"))
    private Datum datumEinde;

    /** De toestand. */
    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "toestand"))
    private Toestand toestand;

    /**
     * @return Technische sleutel .
     */
    public Integer getId() {
        return id;
    }

    /**
     * @return Soort autorisatiebesluit.
     */
    public SoortAutorisatiebesluit getSoort() {
        return soort;
    }

    /**
     * @return De tekst uit het Autorisatiebesluit.
     */
    public TekstUitBesluit getBesluittekst() {
        return besluittekst;
    }

    /**
     * @return De Partij, dan wel de verantwoordelijke voor de Partij, die het Autorisatiebesluit heeft genomen.
     */
    public Partij getAutoriseerder() {
        return autoriseerder;
    }

    /**
     * @return Indicatie die aangeeft of een Autorisatiebesluit WEL of NIET is gebaseerd op een Model besluit.
     */
    public JaNee getIndicatieModelBesluit() {
        return indicatieModelBesluit;
    }

    /**
     * @return Het Autorisatiebesluit waarop het onderhavige Autorisatiebesluit gebaseerd is.
     */
    public Autorisatiebesluit getGebaseerdOp() {
        return gebaseerdOp;
    }

    /**
     * @return Indicatie die aangeeft of het Autorisatiebesluit is ingetrokken.
     */
    public JaNee getIndicatieIngetrokken() {
        return indicatieIngetrokken;
    }

    /**
     * @return Datum waarop het besluit genomen is.
     */
    public Datum getDatumBesluit() {
        return datumBesluit;
    }

    /**
     * @return De datum waarop het autorisatiebesluit ingaat.
     */
    public Datum getDatumIngang() {
        return datumIngang;
    }

    /**
     * @return De datum waarop het autorisatiebesluit beëindigd dient te zijn.
     */
    public Datum getDatumEinde() {
        return datumEinde;
    }

    /**
     * @return De toestand.
     */
    public Toestand getToestand() {
        return toestand;
    }

    /**
     * Constructor.
     *
     * @param soort de soort.
     * @param besluittekst de besluittekst.
     * @param autoriseerder de autoriseerder.
     * @param indicatieModelBesluit de indicatieModelBesluit.
     * @param gebaseerdOp de gebaseerdOp.
     * @param indicatieIngetrokken de indicatieIngetrokken.
     * @param datumBesluit de datumBesluit.
     * @param datumIngang de datumIngang.
     * @param datumEinde de datumEinde.
     * @param toestand de toestand.
     *
     */
     Autorisatiebesluit(final Soort soort, final Besluittekst besluittekst, final Autoriseerder autoriseerder, final IndicatieModelBesluit indicatieModelBesluit, 
        final GebaseerdOp gebaseerdOp, final IndicatieIngetrokken indicatieIngetrokken, final DatumBesluit datumBesluit, final DatumIngang datumIngang, 
        final DatumEinde datumEinde, final Toestand toestand) {
        this.soort =  soort;
        this.besluittekst =  besluittekst;
        this.autoriseerder =  autoriseerder;
        this.indicatieModelBesluit =  indicatieModelBesluit;
        this.gebaseerdOp =  gebaseerdOp;
        this.indicatieIngetrokken =  indicatieIngetrokken;
        this.datumBesluit =  datumBesluit;
        this.datumIngang =  datumIngang;
        this.datumEinde =  datumEinde;
        this.toestand =  toestand;
    }

}
