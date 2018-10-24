package businesslayer.gr.com;

import java.io.IOException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import dao.gr.com.ContactDao;
import entity.gr.com.Account;
import entity.gr.com.Address;
import entity.gr.com.Contact;

@ManagedBean(name = "contact", eager = true)
@ViewScoped
public class ContactB extends AbstractBean {
	private Contact contact;
	private int contactID;
	private List<Contact> contacts;
	private Boolean editcheck;

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public List<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contact> contacts) {
		this.contacts = contacts;
	}

	public int getAccountid() {
		return accountid;
	}

	public void setAccountid(int accountid) {
		this.accountid = accountid;
	}

	private int accountid;

	public ContactB() {
		editcheck = false;
		contacts = getall();
	}

	public String logout() throws IOException {
		/*
		 * getSession(false).invalidate();
		 * FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		 * FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml"
		 * );
		 */
		return "index";
	}

	public List<Contact> getall() {
		ContactDao contactdao = new ContactDao();
		Account account = (Account) getSession(false).getAttribute("loggedInAccount");
		contacts = contactdao.getAll(account.getId());
		return contacts;
	}

	public String deleteC(int id) {
		ContactDao contactdao = new ContactDao();
		contactdao.deleteContact(id);
		contacts = getall();
		return "viewid?faces-redirect=true";
	}

	public void newSetup() {
		contactID = contacts.size();
		Contact c = new Contact();
		Address a=new Address();
		c.setAddress(a);
		contacts.add(c);
		editcheck = false;
	}

	public void togSaveEdit() {
		editcheck = !editcheck;
	}

	public void saveUp() {
		Account account = (Account) getSession(false).getAttribute("loggedInAccount");
		if (editcheck) {
			Contact con = contacts.get(contactID);
			con.setAccount(account);
			con.setAccountId(account.getId());
			ContactDao contactdao = new ContactDao();
			contactdao.updateContact(con);
			contacts = getall();
		} else {
			ContactDao contactdao = new ContactDao();
			Contact contactNew = contacts.get(contactID);
			contactNew.getAddress().setContact(contactNew);
			contactNew.setAccount(account);
			contactNew.setAccountId(account.getId());
			contactdao.addContact(contactNew);
		}
	}

	public void editContact(int id) {
		for (int i = 0; i < contacts.size(); i++) {
			if (contacts.get(i).getId() == id) {
				contactID = i;
				this.togSaveEdit();
			}
		}
	}

	public int getContactID() {
		return contactID;
	}

	public void setContactID(int contactID) {
		this.contactID = contactID;
	}

	public Boolean getEditcheck() {
		return editcheck;
	}

	public void setEditcheck(Boolean editcheck) {
		this.editcheck = editcheck;
	}
}
