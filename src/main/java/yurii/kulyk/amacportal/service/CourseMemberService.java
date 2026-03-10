package yurii.kulyk.amacportal.service;

import yurii.kulyk.amacportal.entity.CourseMember;
import yurii.kulyk.amacportal.repository.CourseMemberRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
@Transactional
public class CourseMemberService {

    @Inject
    CourseMemberRepository repo;

    @Inject
    TenantService tenantService;


    public CourseMember find(UUID id) {
        return repo.find(id);
    }


    public List<CourseMember> findAll() {
        return repo.findAll();
    }


    public CourseMember create(CourseMember entity) {
        entity.setTenant(tenantService.getCurrent());
        return repo.save(entity);
    }


    public CourseMember update(UUID id, CourseMember entity) {
        CourseMember courseMember = repo.find(id);
        courseMember.setCourse(entity.getCourse());
        courseMember.setRole(entity.getRole());
        courseMember.setUser(entity.getUser());
        return repo.update(courseMember);
    }


    public void delete(UUID id) {
        repo.delete(id);
    }
}
