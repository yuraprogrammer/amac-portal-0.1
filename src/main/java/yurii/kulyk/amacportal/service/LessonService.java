package yurii.kulyk.amacportal.service;

import yurii.kulyk.amacportal.dto.LessonCreateDTO;
import yurii.kulyk.amacportal.dto.LessonDTO;
import yurii.kulyk.amacportal.entity.Lesson;
import yurii.kulyk.amacportal.mapper.LessonMapper;
import yurii.kulyk.amacportal.repository.LessonRepository;
import yurii.kulyk.amacportal.repository.ModuleRepository;
import yurii.kulyk.amacportal.entity.Module;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
@Transactional
public class LessonService {

    @Inject LessonRepository repo;
    @Inject
    ModuleRepository moduleRepo;
    @Inject
    LessonMapper mapper;
    @Inject TenantService tenantService;

    public List<LessonDTO> byModule(UUID moduleId) {
        Module module = moduleRepo.findById(moduleId);
        return repo.findByModule(module).stream()
                .map(mapper::toDto)
                .toList();
    }

    public LessonDTO create(LessonCreateDTO dto) {
        Module module = moduleRepo.findById(dto.getModuleId());

        Lesson lesson = mapper.fromCreateDto(dto);
        lesson.setModule(module);
        lesson.setTenant(tenantService.getCurrent());

        repo.persist(lesson);
        return mapper.toDto(lesson);
    }
}
