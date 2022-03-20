package interview.wikicredit.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import interview.wikicredit.dto.CompanyCreateRequest;
import interview.wikicredit.dto.CompanyResponse;
import interview.wikicredit.entity.Company;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class CompanyMapperTest {

    private CompanyMapper mapper;
    private final String NAME = "Awesome Blossom INC";
    private final int ID = 1337;

    @BeforeEach
    void setup() {
        mapper = new CompanyMapperImpl();
    }

    @Test
    void toEntity_validInput_mapsAllFields() {
        CompanyCreateRequest request = new CompanyCreateRequest();
        request.setName(NAME);

        Company company = mapper.toEntity(request);

        assertThat(company)
          .isNotNull()
          .extracting("name")
          .contains(NAME);
    }

    @Test
    void toResponse_validInput_mapsAllFields() {
        Company company = Company
          .builder()
          .id(ID)
          .name(NAME)
          .build();

        CompanyResponse dto = mapper.toResponse(company);

        assertThat(dto)
          .isNotNull()
          .extracting("id", "name")
          .contains(ID, NAME);
    }
}
