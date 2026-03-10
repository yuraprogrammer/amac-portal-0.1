package yurii.kulyk.amacportal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import yurii.kulyk.amacportal.dto.LessonCreateDTO;
import yurii.kulyk.amacportal.dto.LessonDTO;
import yurii.kulyk.amacportal.entity.Lesson;

@Mapper(config = CentralMapperConfig.class)
public interface LessonMapper {

    @Mapping(source = "module.id", target = "moduleId")
    LessonDTO toDto(Lesson entity);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "tenant", ignore = true),
            @Mapping(target = "module", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true)
    })
    Lesson fromCreateDto(LessonCreateDTO dto);
}


