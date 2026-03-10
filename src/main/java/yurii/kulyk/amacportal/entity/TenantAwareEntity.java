package yurii.kulyk.amacportal.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@Setter
@Getter
@MappedSuperclass
public abstract class TenantAwareEntity extends BaseEntity{


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tenant_id", nullable = false, updatable = false)
    protected Tenant tenant;

    public TenantAwareEntity() {
        super();
    }

    public TenantAwareEntity(Tenant tenant) {
        super();
        this.tenant = tenant;
    }


}
