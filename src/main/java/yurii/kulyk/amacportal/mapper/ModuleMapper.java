package yurii.kulyk.amacportal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import yurii.kulyk.amacportal.dto.ModuleCreateDTO;
import yurii.kulyk.amacportal.dto.ModuleDTO;
import yurii.kulyk.amacportal.entity.Module;

@Mapper(config = CentralMapperConfig.class)
public interface ModuleMapper {

    @Mapping(source = "course.id", target = "courseId")
    ModuleDTO toDto(Module entity);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "tenant", ignore = true),
            @Mapping(target = "course", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true)
    })
    Module fromCreateDto(ModuleCreateDTO dto);
}

