package yurii.kulyk.amacportal.service;

import yurii.kulyk.amacportal.dto.ModuleCreateDTO;
import yurii.kulyk.amacportal.dto.ModuleDTO;
import yurii.kulyk.amacportal.entity.Course;
import yurii.kulyk.amacportal.entity.Module;
import yurii.kulyk.amacportal.mapper.ModuleMapper;
import yurii.kulyk.amacportal.repository.CourseRepository;
import yurii.kulyk.amacportal.repository.ModuleRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
@Transactional
public class ModuleService {

    @Inject
    ModuleRepository repo;

    @Inject
    CourseRepository courseRepo;

    @Inject
    TenantService tenantService;

    @Inject
    ModuleMapper mapper;

    public List<ModuleDTO> byCourse(UUID courseId) {
        Course course = courseRepo.findById(courseId);
        return repo.findByCourse(course).stream()
                .map(mapper::toDto)
                .toList();
    }

    public ModuleDTO create(ModuleCreateDTO dto) {
        Course course = courseRepo.findById(dto.getCourseId());

        Module module = mapper.fromCreateDto(dto);
        module.setCourse(course);
        module.setTenant(tenantService.getCurrent());

        repo.persist(module);
        return mapper.toDto(module);
    }
}
