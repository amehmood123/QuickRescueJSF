package businesslayer.gr.com;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public abstract class AbstractBean {
	
	public HttpSession getSession(Boolean newSession)
	{
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(newSession);
		return session;
	}

}
