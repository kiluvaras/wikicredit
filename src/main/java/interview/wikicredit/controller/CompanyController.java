package interview.wikicredit.controller;

import interview.wikicredit.dto.CompanyCreateRequest;
import interview.wikicredit.dto.CompanyResponse;
import interview.wikicredit.service.CompanyServiceImpl;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
public class CompanyController {

    private final CompanyServiceImpl service;

    public CompanyController(CompanyServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> createCompany(@Valid @RequestBody CompanyCreateRequest request) {
        service.createCompany(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> getCompany(@PathVariable int id) {
        return ResponseEntity.ok(service.getCompany(id));
    }
}