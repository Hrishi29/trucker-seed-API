package hrishi.gad.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Alerts {
    @Id
    private String id;

    private String vin;
    private Priority priority;
    private String message;

    public Alerts() {
        this(UUID.randomUUID().toString(), null, null, null);

    }

    public Alerts(String id, String vin, Priority priority, String message){
        this.id = id;
        this.vin = vin;
        this.priority = priority;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Alerts{" +
                "id='" + id + '\'' +
                ", vin='" + vin + '\'' +
                ", priority=" + priority +
                ", message='" + message + '\'' +
                '}';
    }
}
