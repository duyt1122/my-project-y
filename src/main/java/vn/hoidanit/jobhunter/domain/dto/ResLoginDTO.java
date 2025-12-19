package vn.hoidanit.jobhunter.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ResLoginDTO {
	@JsonProperty("access_token")
	private String accessToken;
	private UserLogin user;

	@Setter
	@Getter
	
	public static class UserLogin {
		private long id;
		private String email;
		private String name;

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public UserLogin(long id, String email, String name) {
			super();
			this.id = id;
			this.email = email;
			this.name = name;
		}

		public UserLogin() {

		}

	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public UserLogin getUser() {
		return user;
	}

	public void setUser(UserLogin user) {
		this.user = user;
	}

	public static class UserGetAccount {
		private UserLogin user;

		public UserLogin getUser() {
			return user;
		}

		public void setUser(UserLogin user) {
			this.user = user;
		}

		public UserGetAccount(UserLogin user) {
			super();
			this.user = user;
		}

		public UserGetAccount() {

		}

	}

}
