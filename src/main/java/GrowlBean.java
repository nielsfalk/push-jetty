import org.primefaces.push.PushContext;
import org.primefaces.push.PushContextFactory;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

@ManagedBean
@ApplicationScoped
public class GrowlBean {

    private String summary;

    private String detail;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void save(ActionEvent actionEvent) {
        FacesContext context = FacesContext.getCurrentInstance();

        context.addMessage(null, new FacesMessage("Successful", "Hello "));
        context.addMessage(null, new FacesMessage("Second Message", "Additional Info Here..."));
    }

    public void send() {
        PushContext pushContext = PushContextFactory.getDefault().getPushContext();

        pushContext.push("/notifications", new FacesMessage(summary, detail));
    }
}
                    