package yurii.kulyk.amacportal.repository;

import javax.enterprise.context.ApplicationScoped;

import yurii.kulyk.amacportal.entity.Course;
import yurii.kulyk.amacportal.entity.Module;

import java.util.List;
import java.util.UUID;


@ApplicationScoped
public class ModuleRepository extends TenantAwareRepository<Module>{

    @Override
    protected Class<Module> type() {
        return Module.class;
    }

    public List<Module> findByCourse(Course course) {
        return em.createQuery("""
            select m from Module m
            where m.course = :course
        """, Module.class)
                .setParameter("course", course)
                .getResultList();
    }

    public void persist(Module module){
        em.persist(module);
    }

    public Module findById(UUID id){
        return em.find(Module.class, id);
    }


}
