package yurii.kulyk.amacportal.service;

import javax.ejb.ObjectNotFoundException;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import yurii.kulyk.amacportal.dto.CourseCreateDTO;
import yurii.kulyk.amacportal.dto.CourseDTO;
import yurii.kulyk.amacportal.dto.CourseUpdateDTO;
import yurii.kulyk.amacportal.entity.Assignment;
import yurii.kulyk.amacportal.entity.Course;
import yurii.kulyk.amacportal.entity.Tenant;
import yurii.kulyk.amacportal.entity.User;
import yurii.kulyk.amacportal.mapper.CourseMapper;
import yurii.kulyk.amacportal.repository.CourseRepository;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
@Transactional
public class CourseService {

    @Inject
    CourseRepository repo;

    @Inject
    TenantService tenantService;

    @Inject
    CourseMapper mapper;

    public Course find(UUID id) {
        return repo.find(id);
    }

    public List<CourseDTO> findForStudent(User user) {
        List<Course> courses = repo.findForStudent(user, tenantService.getCurrent());
        return courses.stream().map(mapper::toDTO).toList();
    }

    public CourseDTO findForStudentById(User user, UUID id) throws ObjectNotFoundException {
        List<Course> courses = Collections.singletonList(repo.findForStudentById(user, tenantService.getCurrent(), id));
        return mapper.toDTO(courses.get(0));
    }

    public List<CourseDTO> findAll() {
        return repo.findByTenant(tenantService.getCurrent()).stream().map(mapper::toDTO).toList();
    }

    public CourseDTO create(CourseCreateDTO c) {
        Course course = mapper.fromCreateDTO(c);
        Tenant tenantRef = tenantService.getCurrent();
        course.setTenant(tenantRef);
        repo.persist(course);
        return mapper.toDTO(course);
    }

    public CourseDTO update(UUID id, CourseUpdateDTO c) {
        Course existing = repo.findById(id);
        mapper.updateEntity(existing, c);
        return mapper.toDTO(existing);
    }

    public void delete(UUID id) {
        repo.delete(id);
    }
}
