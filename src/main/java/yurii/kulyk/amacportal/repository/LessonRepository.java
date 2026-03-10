package yurii.kulyk.amacportal.repository;

import yurii.kulyk.amacportal.entity.Lesson;
import  yurii.kulyk.amacportal.entity.Module;
import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class LessonRepository extends TenantAwareRepository<Lesson>{
    @Override
    protected Class<Lesson> type() {
        return Lesson.class;
    }

    public void persist(Lesson lesson) {
        em.persist(lesson);
    }

    public List<Lesson> findByModule(Module module) {
        return em.createQuery("""
            select l from Lesson l
            where l.module = :module
        """, Lesson.class)
                .setParameter("module", module)
                .getResultList();
    }
}
