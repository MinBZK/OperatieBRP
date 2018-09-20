/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.objecttype.operationeel.basis;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import nl.bzk.copy.model.attribuuttype.Volgnummer;
import nl.bzk.copy.model.basis.AbstractDynamischObjectType;
import nl.bzk.copy.model.groep.operationeel.actueel.PersoonVoornaamStandaardGroepModel;
import nl.bzk.copy.model.objecttype.logisch.basis.PersoonVoornaamBasis;
import nl.bzk.copy.model.objecttype.operationeel.PersoonModel;
import nl.bzk.copy.model.objecttype.operationeel.statisch.StatusHistorie;


/**
 * Implementatie voor objecttype Persoon voornaam.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class AbstractPersoonVoornaamModel extends AbstractDynamischObjectType implements PersoonVoornaamBasis {

    @Id
    @SequenceGenerator(name = "seq_PersVoornaam", sequenceName = "Kern.seq_PersVoornaam")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_PersVoornaam")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Pers")
    @NotNull
    private PersoonModel persoon;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "volgnr"))
    @NotNull
    private Volgnummer volgnummer;

    @Embedded
    @NotNull
    private PersoonVoornaamStandaardGroepModel gegevens;

    @Column(name = "persvoornaamstatushis")
    @Enumerated(value = EnumType.STRING)
    @NotNull
    private StatusHistorie statusHistorie;

    /**
     * copy cons.
     *
     * @param persVoornaam .
     * @param pers         .
     */
    protected AbstractPersoonVoornaamModel(final PersoonVoornaamBasis persVoornaam,
                                           final PersoonModel pers)
    {
        super(persVoornaam);
        initLegeStatusHistorie();
        volgnummer = persVoornaam.getVolgnummer();
        gegevens = new PersoonVoornaamStandaardGroepModel(persVoornaam.getGegevens());
        statusHistorie = StatusHistorie.A;
        persoon = pers;
    }

    /**
     * initieer alle sttaushistories op waarde X.
     */
    private void initLegeStatusHistorie() {
        statusHistorie = StatusHistorie.X;
    }

    /**
     * default cons.
     */
    protected AbstractPersoonVoornaamModel() {
        initLegeStatusHistorie();
    }

    public Integer getId() {
        return id;
    }

    @Override
    public PersoonModel getPersoon() {
        return persoon;
    }

    @Override
    public Volgnummer getVolgnummer() {
        return volgnummer;
    }

    @Override
    public PersoonVoornaamStandaardGroepModel getGegevens() {
        return gegevens;
    }


    public StatusHistorie getStatusHistorie() {
        return statusHistorie;
    }


    public void setStatusHistorie(final StatusHistorie statusHistorie) {
        this.statusHistorie = statusHistorie;
    }


    public void setId(final Integer id) {
        this.id = id;
    }


    public void setPersoon(final PersoonModel persoon) {
        this.persoon = persoon;
    }


    public void setVolgnummer(final Volgnummer volgnummer) {
        this.volgnummer = volgnummer;
    }


    public void setGegevens(final PersoonVoornaamStandaardGroepModel gegevens) {
        this.gegevens = gegevens;
    }
}
