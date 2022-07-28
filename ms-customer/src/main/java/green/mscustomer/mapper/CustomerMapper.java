package green.mscustomer.mapper;

import green.mscustomer.dao.entity.CustomerEntity;
import green.mscustomer.model.dto.CustomerDto;
import green.mscustomer.model.view.CustomerView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public abstract class CustomerMapper {

    public static final CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(target = "isDeleted", constant = "false")
    public abstract CustomerEntity mapDtoToEntity(CustomerDto customerDto);

    @Mapping(target = "fullname", source = "entity", qualifiedByName = "fullname")
    public abstract CustomerView mapEntityToView(CustomerEntity entity);

    public abstract List<CustomerView> mapEntitiesToViews(List<CustomerEntity> entities);

    @Named(value = "fullname")
    protected String getFullname(CustomerEntity entity) {
        return entity.getFirstname() + " " + entity.getLastname();
    }
}
