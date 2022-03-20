package interview.wikicredit.service;

import interview.wikicredit.dto.CompanyCreateRequest;
import interview.wikicredit.dto.CompanyResponse;

public interface CompanyService {
    void createCompany(CompanyCreateRequest request);
    CompanyResponse getCompany(int id);
}
