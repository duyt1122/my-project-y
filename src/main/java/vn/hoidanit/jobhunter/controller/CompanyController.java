package vn.hoidanit.jobhunter.controller;

import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.service.CompanyService;
import vn.hoidanit.jobhunter.util.annotation.ApiMessage;

@RestController
@RequestMapping("/api/v1")
public class CompanyController {
	private final CompanyService companyService;

	public CompanyController(CompanyService companyService) {
		this.companyService = companyService;
	}

	@PostMapping("/companies")
	public ResponseEntity<Company> createACompany(@Valid @RequestBody Company company) {
		Company cpn = this.companyService.createCompany(company);
		return ResponseEntity.ok().body(cpn);
	}

	@GetMapping("/companies")
	@ApiMessage("fetch companies")
	public ResponseEntity<ResultPaginationDTO> getAllCompany(@RequestParam("current") Optional<String> currentOptional,
			@RequestParam("pageSize") Optional<String> pageSizeOptional) {
		String sCurrent = currentOptional.isPresent() == true ? currentOptional.get() : "";
		String sPageSize = pageSizeOptional.isPresent() == true ? pageSizeOptional.get() : "";

		int current = Integer.parseInt(sCurrent);
		int pageSize = Integer.parseInt(sPageSize);
		Pageable pageable = PageRequest.of(current - 1, pageSize);
		ResultPaginationDTO companies = this.companyService.findAllCompany(pageable);
		return ResponseEntity.status(HttpStatus.OK).body(companies);
	}

	@GetMapping("companies/{id}")
	public ResponseEntity<Company> getACompany(@PathVariable("id") Long id) {
		Company company = this.companyService.findById(id);
		return ResponseEntity.ok().body(company);
	}

	@DeleteMapping("companies/{id}")
	public ResponseEntity<Void> deleteCompany(@PathVariable("id") Long id) {
		this.companyService.deleteACompany(id);
		return ResponseEntity.ok().body(null);
	}

	@PutMapping("companies")
	public ResponseEntity<Company> updateCompany(@Valid @RequestBody Company company) {
		Company updateCompany = this.companyService.updateCompany(company);
		return ResponseEntity.ok(updateCompany);
	}
}
