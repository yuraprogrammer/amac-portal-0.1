package yurii.kulyk.amacportal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import yurii.kulyk.amacportal.dto.AssignmentCreateDTO;
import yurii.kulyk.amacportal.dto.AssignmentDTO;
import yurii.kulyk.amacportal.entity.Assignment;

@Mapper(config = CentralMapperConfig.class)
public interface AssignmentMapper {

    AssignmentDTO toDto(Assignment entity);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "tenant", ignore = true),
            @Mapping(target = "lesson", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true)
    })
    Assignment fromCreateDto(AssignmentCreateDTO dto);
}


