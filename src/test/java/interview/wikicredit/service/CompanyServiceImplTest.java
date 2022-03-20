package interview.wikicredit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import interview.wikicredit.controller.exception.ApplicationException;
import interview.wikicredit.dto.CompanyCreateRequest;
import interview.wikicredit.dto.CompanyResponse;
import interview.wikicredit.entity.Company;
import interview.wikicredit.mapper.CompanyMapper;
import interview.wikicredit.mapper.CompanyMapperImpl;
import interview.wikicredit.repository.CompanyRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class CompanyServiceImplTest {

    private final int ID = 7;
    private final String NAME = "some-fancy-name";

    @MockBean
    private CompanyRepository repository;
    private CompanyMapper mapper;
    private CompanyServiceImpl service;

    @BeforeEach
    void setup() {
        mapper = new CompanyMapperImpl();
        service = new CompanyServiceImpl(repository, mapper);
    }

    @Test
    void createCompany_validInput_createsCompany() {
        CompanyCreateRequest request = new CompanyCreateRequest();
        request.setName(NAME);

        service.createCompany(request);

        verify(repository).save(any(Company.class));
    }

    @Test
    void getCompany_companyNotFound_throwsException() {
        when(repository.findById(ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getCompany(ID))
          .isExactlyInstanceOf(ApplicationException.class)
          .hasMessage("Company not found with id: " + ID);
    }

    @Test
    void getCompany_companyIsFound_returnsMappedResponse() {
        Company company = Company
          .builder()
          .id(ID)
          .name(NAME)
          .build();
        when(repository.findById(ID)).thenReturn(Optional.of(company));

        CompanyResponse result = service.getCompany(ID);

        assertThat(result)
          .isNotNull()
          .extracting("id", "name")
          .contains(ID, NAME);
    }
}
