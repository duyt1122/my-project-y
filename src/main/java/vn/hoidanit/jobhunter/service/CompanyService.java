package vn.hoidanit.jobhunter.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.domain.dto.Meta;
import vn.hoidanit.jobhunter.domain.dto.ResultPaginationDTO;
import vn.hoidanit.jobhunter.repository.CompanyRepository;

@Service
public class CompanyService {

	private final CompanyRepository companyRepository;

	public CompanyService(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}

	public Company createCompany(Company company) {
		return this.companyRepository.save(company);
	}

	public ResultPaginationDTO findAllCompany(Pageable pageable) {
		Page<Company> page = this.companyRepository.findAll(pageable);
		Meta meta = new Meta();
		meta.setPage(page.getNumber() + 1);
		meta.setPageSize(page.getSize());
		meta.setPages(page.getTotalPages());
		meta.setTotal(page.getTotalElements());

		ResultPaginationDTO pagi = new ResultPaginationDTO();

		pagi.setMeta(meta);
		pagi.setResult(page.getContent());
		return pagi;
	}

	public Company findById(Long id) {
		return this.companyRepository.findById(id).orElseThrow(() -> new RuntimeException("Company không tồn tại"));
	}

	public Company updateCompany(Company company) {
		Company currentCompany = findById(company.getId());
		currentCompany.setName(company.getName());
		currentCompany.setDescription(company.getDescription());
		currentCompany.setAddress(company.getAddress());
		currentCompany.setLogo(company.getLogo());

		return this.companyRepository.save(currentCompany);

	}

	public void deleteACompany(Long id) {
		this.companyRepository.deleteById(id);
	}
}
