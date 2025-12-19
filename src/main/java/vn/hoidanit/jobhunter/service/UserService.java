package vn.hoidanit.jobhunter.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.response.ResCreateUserDTO;
import vn.hoidanit.jobhunter.domain.response.ResUpdateUserDTO;
import vn.hoidanit.jobhunter.domain.response.ResUserDTO;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User handleCreateUser(User user) {
		return this.userRepository.save(user);
	}

	public User findUser(Long id) {
		Optional<User> user = this.userRepository.findById(id);
		if (user.isPresent()) {
			return user.get();
		}
		return null;
	}

	public void deleteUser(Long id) {
		this.userRepository.deleteById(id);
	}

	public ResultPaginationDTO getUsers(Specification<User> spec, Pageable pageable) {
		Page<User> pageUser = this.userRepository.findAll(spec, pageable);
		ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();
		meta.setPage(pageable.getPageNumber() + 1);
		meta.setPageSize(pageable.getPageSize());
		meta.setPages(pageUser.getTotalPages());
		meta.setTotal(pageUser.getTotalElements());

		ResultPaginationDTO pagi = new ResultPaginationDTO();

		pagi.setMeta(meta);
		List<ResUserDTO> users = pageUser.getContent().stream().map(item -> convertToResUserDTO(item))
				.collect(Collectors.toList());
		pagi.setResult(users);
		return pagi;
	}

	public User handleGetUserByUserName(String username) {
		return this.userRepository.findByEmail(username);
	}

	public User updateUser(User user) {
		return userRepository.findById(user.getId()).map(u -> {
			u.setName(user.getName());
			u.setAddress(user.getAddress());
			u.setGender(user.getGender());
			u.setAge(user.getAge());

			return userRepository.save(u);
		}).orElseThrow(() -> new RuntimeException("User not found"));

	}

	public boolean isEmailExits(String email) {
		return this.userRepository.existsByemail(email);
	}

	public ResCreateUserDTO convertToResCreateUserDTO(User user) {
		ResCreateUserDTO res = new ResCreateUserDTO();
		res.setId(user.getId());
		res.setEmail(user.getEmail());
		res.setName(user.getName());
		res.setAge(user.getAge());
		res.setGender(user.getGender());
		res.setAddress(user.getAddress());
		res.setCreatedAt(user.getCreateAt());
		return res;
	}

	public ResUserDTO convertToResUserDTO(User user) {
		ResUserDTO res = new ResUserDTO();
		res.setId(user.getId());
		res.setEmail(user.getEmail());
		res.setName(user.getName());
		res.setGender(user.getGender());
		res.setAddress(user.getAddress());
		res.setAge(user.getAge());
		res.setUpdatedAt(user.getUpdateAt());
		res.setCreatedAt(user.getCreateAt());

		return res;
	}

	public ResUpdateUserDTO convertToResUpdateDTO(User user) {
		ResUpdateUserDTO res = new ResUpdateUserDTO();
		res.setId(user.getId());
		res.setName(user.getName());
		res.setAddress(user.getAddress());
		res.setAge(user.getAge());
		res.setGender(user.getGender());
		res.setUpdateAt(user.getUpdateAt());
		return res;
	}

	public void updateUserToken(String token, String email) {
		User currentUser = this.handleGetUserByUserName(email);
		if (currentUser != null) {
			currentUser.setRefreshToken(token);
			this.userRepository.save(currentUser);
		}
	}

	public User getUserByRefreshTokenAndEmail(String token, String email) {
		return this.userRepository.findByRefreshTokenAndEmail(token, email);
	}

}
