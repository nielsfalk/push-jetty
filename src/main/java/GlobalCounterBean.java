import org.primefaces.push.PushContextFactory;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;

@ManagedBean(name = "globalCounter")
@ApplicationScoped
public class GlobalCounterBean implements Serializable {

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public synchronized void increment() {
        count++;
        PushContextFactory.getDefault().getPushContext().push("/counter", String.valueOf(count));
    }
}

