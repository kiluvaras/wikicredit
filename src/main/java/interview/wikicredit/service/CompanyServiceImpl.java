package interview.wikicredit.service;

import interview.wikicredit.controller.exception.ApplicationException;
import interview.wikicredit.controller.exception.ErrorCode;
import interview.wikicredit.dto.CompanyCreateRequest;
import interview.wikicredit.dto.CompanyResponse;
import interview.wikicredit.entity.Company;
import interview.wikicredit.mapper.CompanyMapper;
import interview.wikicredit.repository.CompanyRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository repository;
    private final CompanyMapper mapper;

    public CompanyServiceImpl(CompanyRepository repository,
      CompanyMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public void createCompany(CompanyCreateRequest request) {
        Company company = mapper.toEntity(request);
        repository.save(company);
    }

    public CompanyResponse getCompany(int id) {
        Optional<Company> company = repository.findById(id);
        if (company.isPresent()) {
            return mapper.toResponse(company.get());
        } else {
            throw new ApplicationException(
              ErrorCode.ENTITY_NOT_FOUND,
              "Company not found with id: " + id);
        }
    }
}
