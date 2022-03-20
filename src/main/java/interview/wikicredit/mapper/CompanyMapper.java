package interview.wikicredit.mapper;

import interview.wikicredit.dto.CompanyCreateRequest;
import interview.wikicredit.dto.CompanyResponse;
import interview.wikicredit.entity.Company;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface CompanyMapper {

    Company toEntity(CompanyCreateRequest request);

    CompanyResponse toResponse(Company entity);
}
