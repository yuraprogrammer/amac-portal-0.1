package yurii.kulyk.amacportal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import yurii.kulyk.amacportal.dto.CourseCreateDTO;
import yurii.kulyk.amacportal.dto.CourseDTO;
import yurii.kulyk.amacportal.dto.CourseUpdateDTO;
import yurii.kulyk.amacportal.entity.Course;

@Mapper(componentModel = "cdi")
public interface CourseMapper {
    CourseDTO toDTO(Course entity);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "tenant", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true)
    })
    Course fromCreateDTO(CourseCreateDTO dto);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "tenant", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true)
    })
    void updateEntity(@MappingTarget Course entity, CourseUpdateDTO dto);
}
