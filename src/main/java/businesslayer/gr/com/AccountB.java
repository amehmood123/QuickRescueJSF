package businesslayer.gr.com;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import dao.gr.com.AccountDao;
import entity.gr.com.Account;

@ManagedBean(name = "account", eager = true)
@SessionScoped
public class AccountB extends AbstractBean {
	private int accountID;
	private String username;
	private String password;
	private String validate;
	private String email;

	public int getAccountID() {
		return accountID;
	}

	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getValidate() {
		return validate;
	}

	public void setValidate(String validate) {
		this.validate = validate;
	}

	private AccountDao accountdao;

	public AccountB() {
		accountdao = new AccountDao();
	}

	public String validateUser() {
		Account account = new Account();
		account.setEmail(username);
		account.setPassword(password);
		if (accountdao.validateAccount(username)) {
			account = accountdao.validateLogin(account);
			if (account != null) {
				validate = "contacts";
				accountID = account.getId();
				email = account.getEmail();
				getSession(true).setAttribute("loggedInAccount", account);

				return validate;
			}
		}
		validate = "index";
		return validate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
