package yurii.kulyk.amacportal.service;

import yurii.kulyk.amacportal.dto.AssignmentCreateDTO;
import yurii.kulyk.amacportal.dto.AssignmentDTO;
import yurii.kulyk.amacportal.entity.Assignment;
import yurii.kulyk.amacportal.entity.Lesson;
import yurii.kulyk.amacportal.entity.Tenant;
import yurii.kulyk.amacportal.mapper.AssignmentMapper;
import yurii.kulyk.amacportal.repository.AssignmentRepository;
import yurii.kulyk.amacportal.repository.LessonRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
@Transactional
public class AssignmentService {

    @Inject
    AssignmentRepository repo;

    @Inject
    LessonRepository  lessonRepo;

    @Inject
    TenantService tenantService;

    @Inject
    AssignmentMapper  mapper;

    public AssignmentDTO find(UUID id) {
        return mapper.toDto(repo.findById(id));
    }

    public List<AssignmentDTO> findByLesson(UUID lessonId) {
        Tenant tenant = tenantService.getCurrent();
        Lesson lesson = lessonRepo.find(lessonId);
        return repo.findByLesson(lesson, tenant).stream().map(mapper::toDto).toList();
    }

    /*public List<AssignmentDTO> findAll() {
        return repo.findAll();
    }*/

    public AssignmentDTO create(AssignmentCreateDTO dto) {
        Lesson  lesson = lessonRepo.find(dto.getLessonId());
        Tenant tenantRef = tenantService.getCurrent();
        Assignment assignment = mapper.fromCreateDto(dto);
        assignment.setLesson(lesson);
        assignment.setTenant(tenantRef);
        repo.persist(assignment);
        return mapper.toDto(mapper.fromCreateDto(dto));
    }

    public Assignment update(UUID id, Assignment entity) {
        Assignment assignment = repo.find(id);
        assignment.setDescription(entity.getDescription());
        assignment.setLesson(entity.getLesson());
        assignment.setDueAt(entity.getDueAt());
        return repo.update(assignment);
    }

    public void delete(UUID id) {
        repo.delete(id);
    }
}
