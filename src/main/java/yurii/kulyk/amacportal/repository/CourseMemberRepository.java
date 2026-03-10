package yurii.kulyk.amacportal.repository;

import yurii.kulyk.amacportal.entity.CourseMember;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CourseMemberRepository extends TenantAwareRepository<CourseMember>{

    @Override
    protected Class<CourseMember> type() {
        return CourseMember.class;
    }
}
